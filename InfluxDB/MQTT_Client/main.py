import serial, time
import threading,random
import multiprocessing as mp
import paho.mqtt.client as mqtt
import mysql.connector
import influxdb_client
import datetime
from influxdb_client.client.write_api import SYNCHRONOUS

# CONSTANT
INFLUXDB_BUCKET = "FireData"
INFLUXDB_ORG = "YAFIAK"
INFLUXDB_TOKEN = "1_oZenNoEJpRwAXH0k7yn3GT7DZOKf6f-hFHrBYoDrbZeyUILpDhvgdkIcjldSmQ4O-9HIuXTpNjgx2-0lqE6w=="
INFLUXDB_URL="http://localhost:2003"
IP_SERVER_MYSQL = "127.0.0.1"
MYSQL_USER_NAME = "Mqtt_Client"
MYSQL_USER_PASSWORD = "dzZMBFabo6PMSpB8"
MYSQL_DATABASE = "influxdb"
connexionObjectMySQL = mysql.connector.connect(host=IP_SERVER_MYSQL,user=MYSQL_USER_NAME, password=MYSQL_USER_PASSWORD,database=MYSQL_DATABASE)
NAME_MQTT_CLIENT_SUBSCRIBER = "MQTT InfluxDB Subscriber"
MQTT_TOPIC = "Current_Fire"
IP_MQTT_BROKER = "127.0.0.1"

# GLOBAL
cursorMySQL = connexionObjectMySQL.cursor()
clientInfluxDB = influxdb_client.InfluxDBClient(url=INFLUXDB_URL,token=INFLUXDB_TOKEN,org=INFLUXDB_ORG)
write_apiInfluxDB = clientInfluxDB.write_api(write_options=SYNCHRONOUS)
semaphoreWaiting = mp.Semaphore(0)

# Function that take in charge the insertion of data on MySQL
def updateInfluxDBMySQL(locationX,locationY,intensityFire):
    # Check  if the record exists
    requeteSQL = "SELECT  idFireData FROM fireData WHERE FD_LocationX=%s AND FD_LocationY=%s"
    cursorMySQL.execute(requeteSQL,((str(locationX),str(locationY))))
    myresult = cursorMySQL.fetchall()
    print("----- Insertion in MySQL -----")
    if (len(myresult) == 1):
        # Get the record's id
        for x in myresult:
            val = x[0]
        # Update the record's value
        requeteSQL = "UPDATE fireData SET FD_Intensity = %s WHERE idFireData=%s"
        cursorMySQL.execute(requeteSQL,(str(intensityFire),val))
        connexionObjectMySQL.commit()
        print("-> Update the record in MySQL")
    elif (len(myresult) == 0):
        # Insert the record's value
        requeteSQL = "INSERT INTO fireData (FD_Intensity,FD_LocationX,FD_LocationY) VALUES (%s,%s,%s)"
        cursorMySQL.execute(requeteSQL,(str(intensityFire),str(locationX),str(locationY)))
        connexionObjectMySQL.commit()
        print("-> Add the record in MySQL")
    else:
        # Error
        print("-> Error impossible to execute SQL request")

# Function that take in charge the insertion of data on INFLUXDB
def updateInfluxDBINFLUX(locationX,locationY,intensityFire):
    # Create Point
    pointToInsert = influxdb_client.Point("CurrentFire").field("Fire("+str(locationX)+","+str(locationY)+")",int(intensityFire))
    print("----- Insertion in InfluxDB -----")
    print("-> Measurement : CurrentFire")
    print("-> Field Name: Fire("+str(locationX)+","+str(locationY)+")")
    print("-> Field Value: "+intensityFire)
    # Write Point
    write_apiInfluxDB.write(bucket=INFLUXDB_BUCKET, org=INFLUXDB_ORG, record=pointToInsert)

# Function that take in charge the reception of a message from MQTT Broker
def on_message(client, userdata, message):
    print("----- Message receive from Broker -----")
    msg = str(message.payload.decode("utf-8"))
    print("-> Message received: " + msg)
    # Split of data
    sId = msg.split("|")[0]
    sLocationX = msg.split("|")[1]
    sLocationY = msg.split("|")[2]
    sIntensity = msg.split("|")[3]
    # Update on the different base
    updateInfluxDBMySQL(sLocationX,sLocationY,sIntensity)
    updateInfluxDBINFLUX(sLocationX,sLocationY,sIntensity)


if __name__ == "__main__" :
    print("----- Program's initializing -----")
    print("----- MQTT Client's initializing -----")
    clientMQTT = mqtt.Client(NAME_MQTT_CLIENT_SUBSCRIBER)
    clientMQTT.connect(IP_MQTT_BROKER)
    clientMQTT.subscribe(MQTT_TOPIC)
    print("-> MQTT Client Name: "+NAME_MQTT_CLIENT_SUBSCRIBER)
    print("-> MQTT Client Connected to: "+IP_MQTT_BROKER )
    print("----- MQTT Client Connected -----")
    clientMQTT.on_message=on_message 
    clientMQTT.loop_start()
    print("----- MQTT Client starts -----")
    print("----- Program starts -----")
    semaphoreWaiting.acquire()
        