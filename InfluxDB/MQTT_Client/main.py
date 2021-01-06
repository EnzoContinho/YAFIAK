import serial, time
import threading,random
import multiprocessing as mp
import paho.mqtt.client as mqtt
import mysql.connector
conn = mysql.connector.connect(host="127.0.0.1",user="Mqtt_Client", password="ClientMQTT",database="influxdb")


def updateInfluxDB(locationX,locationY,intensityFire):
    cursor = conn.cursor()
    requeteSQL = "SELECT  idFireData FROM fireData WHERE FD_LocationX=%s AND FD_LocationY=%s"
    cursor.execute(requeteSQL,((str(locationX),str(locationY))))
    myresult = cursor.fetchall()
    if (len(myresult) == 1):
        for x in myresult:
            val = x[0]
        requeteSQL = "UPDATE fireData SET FD_Intensity = %s WHERE idFireData=%s"
        cursor.execute(requeteSQL,(str(intensityFire),val))
        conn.commit()
        print("UPDATE")
    elif (len(myresult) == 0):
        requeteSQL = "INSERT INTO fireData (FD_Intensity,FD_LocationX,FD_LocationY) VALUES (%s,%s,%s)"
        cursor.execute(requeteSQL,(str(intensityFire),str(locationX),str(locationY)))
        conn.commit()
        print("INSERT")
    else:
        print("erreur")
    requeteSQL = "INSERT INTO firedata_all (FDA_Intensity) VALUES ('%s')"
    cursor.execute(requeteSQL,str(intensityFire))
    conn.commit()
    print("INSERT")
    


def on_message(client, userdata, message):
    print("message received " ,str(message.payload.decode("utf-8")))
    msg = str(message.payload.decode("utf-8"))
    sId = msg.split("|")[0]
    sLocationX = msg.split("|")[1]
    sLocationY = msg.split("|")[2]
    sIntensity = msg.split("|")[3]
    print(sId,sLocationX,sLocationY,sIntensity)
    updateInfluxDB(random.randint(0,9),random.randint(0,5),random.randint(0,100))

if __name__ == "__main__" :
    print("Début du programme")
    client =mqtt.Client("InfluxDb_MQTT_Client")
    client.connect("127.0.0.1")
    client.subscribe("Fire")
    client.on_message=on_message 
    client.loop_start()
    mycursor = conn.cursor()
    while(True):
        time.sleep(10)
        