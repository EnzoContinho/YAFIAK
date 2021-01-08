import serial, time
import threading
import multiprocessing as mp
import paho.mqtt.client as mqtt
import json
import requests 

# CONSTANT
SERIALPORT = "COM9"
BAUDRATE = 115200
API_SENSOR_URL = "http://localhost:8080/api/sensors"
NAME_MQTT_CLIENT_PUBLISHER = "SP_EmergencyManager"
IP_MQTT_BROKER = "127.0.0.1" 
MQTT_TOPIC = "Current_Fire"
# GLOBAL
ser = serial.Serial()

# Function that verify the good value of data received
# return a boolean that correspond to the result
def valuesCorrect(sIdFire,sLocationX,sLocationY,sFireIntensity):
    if sIdFire == "" or sLocationX == "" or sLocationY == "" or sFireIntensity == "":
        return False
    if int(sIdFire) <= 0 :
        return False
    if int(sLocationX) < 0 or int(sLocationX) > 9 :
        return False
    if int(sLocationY) < 0 or int(sLocationY) > 5 :
        return False
    if int(sFireIntensity) < 0 or int(sFireIntensity) > 100 :
        return False
    return True

# Function that use API to push data on apache server
def pushFireHTTP(sLocationX,sLocationY,sFireIntensity):
    headers = {"Content-Type": "application/json"}
    paramForAPI = json.dumps({'lX': int(sLocationX), 'cY': int(sLocationY), 'intensity' : int(sFireIntensity)})
    requests.put(url = API_SENSOR_URL, data = paramForAPI, headers=headers)

# Function in charge of initialize the serial port
def initUART():     
    ser.port=SERIALPORT
    ser.baudrate=BAUDRATE
    ser.bytesize = serial.EIGHTBITS #number of bits per bytes
    ser.parity = serial.PARITY_NONE #set parity check: no parity
    ser.stopbits = serial.STOPBITS_ONE #number of stop bits
    ser.timeout = None          #block read
    ser.xonxoff = False     #disable software flow control
    ser.rtscts = False     #disable hardware (RTS/CTS) flow control
    ser.dsrdtr = False       #disable hardware (DSR/DTR) flow control
    print("----- Socket's initializing -----")
    try:
            ser.open()
            print("-> Port: "+SERIALPORT)
            print("-> Baudrate: "+str(BAUDRATE))
            print("-> Byte size: "+str(serial.EIGHTBITS))
            print("-> Parity: "+ serial.PARITY_NONE)
            print("-> Number of stop bits: "+str(serial.STOPBITS_ONE))
            print("-> Timeout: NONE")
            print("-> Flow control: NONE")
    except serial.SerialException:
            print("-> Serial {} port not available".format(SERIALPORT))
            exit()

# Function that take in charge the data receive on serial port
def receiveUARTMessage():
    nMessageReceived,nMessageFailed = 0,0
    while ser.isOpen() : 
        if (ser.inWaiting() > 0): # if incoming bytes are waiting 
            print("----- Data received -----")
            # Initialisation of the length waited
            dataLength = 27
            dataBytes = bytearray()
            # Read 27 bytes
            while dataLength != 0:
                dataBytes += (ser.read(dataLength))
                dataLength -= len((dataBytes))
            print("-> (bytes format) | "+str(dataBytes))
            # Get the different data
            sid = str((dataBytes[0:4]))
            strLocationX = str((dataBytes[4:15]))
            strLocationY = str((dataBytes[16:26]))
            strFireIntensity = str((dataBytes[26]))
            # Delete additionnal string
            strLocationX = strLocationX.replace('bytearray(b','').replace('\\x00','').replace("'","").replace(')','')
            strLocationY = strLocationY.replace('bytearray(b','').replace('\\x00','').replace("'","").replace(')','')
            sid = sid.replace('bytearray(b','').replace('\\x00','').replace("'","").replace(')','')
            print("-> (string format) | id: "+sid+" LocationX: "+strLocationX+" LocationY: "+strLocationY+" Fire intensity: "+strFireIntensity)
            # Check the values
            if valuesCorrect(sid,strLocationX,strLocationY,strFireIntensity):
                print("-> Data Correct")
                # Publish on MQTT Brocker
                print("----- Publish on MQTT BROKER (topic: "+MQTT_TOPIC+") -----")
                client.publish(MQTT_TOPIC,sid+"|"+strLocationX+"|"+strLocationY+"|"+strFireIntensity)
                # Push data on database with the API
                print("----- Publish on APACHE -----")
                pushFireHTTP(strLocationX,strLocationY,strFireIntensity)
                # Increment the number of message received
                nMessageReceived+= 1
            else:
                print("-> Data Wrong")
                # Increment the number of message fail to received
                nMessageFailed += 1
            print("-> Message Received / Message Fail to Received: " + str(nMessageReceived) + " / " + str(nMessageFailed))


if __name__ == "__main__" :
    print("----- Program's initializing -----")
    initUART()
    print("----- UART Receiver Thread's initializing -----")
    serverSerial_thread = threading.Thread(target=receiveUARTMessage)
    serverSerial_thread.daemon = True
    print("----- MQTT Client's initializing -----")
    client = mqtt.Client(NAME_MQTT_CLIENT_PUBLISHER)
    client.connect(IP_MQTT_BROKER)
    print("-> MQTT Client Name: "+NAME_MQTT_CLIENT_PUBLISHER)
    print("-> MQTT Client Connected to: "+IP_MQTT_BROKER )
    print("----- MQTT Client Connected -----")
    print("----- UART Receiver Thread starts -----")
    serverSerial_thread.start()
    print("----- Program starts -----")
    while(True):
        time.sleep(10)