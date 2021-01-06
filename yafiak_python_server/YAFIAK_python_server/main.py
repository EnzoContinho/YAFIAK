import serial, time
import threading
import multiprocessing as mp
import paho.mqtt.client as mqtt
import json
# importing the requests library 
import requests 
# send serial message 
# Don't forget to establish the right serial port ******** ATTENTION

SERIALPORT = "COM9"
BAUDRATE = 115200
ser = serial.Serial()
bOk = mp.Value("i",True)

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


def pushFireHTTP(sLocationX,sLocationY,sFireIntensity):
    # api-endpoint 
    URL = "http://localhost:8080/api/sensors"
    # defining a params dict for the parameters to be sent to the API 
    # sending get request and saving the response as response object 
    PARAM = json.dumps({'lX': int(sLocationX), 'cY': int(sLocationY), 'intensity' : int(sFireIntensity)})
    print(PARAM)
    headers = {"Content-Type": "application/json"}
    r = requests.put(url = URL, data = PARAM, headers=headers)
    # extracting data in json format 
    print(r)

def initUART():     
    # ser = serial.Serial(SERIALPORT, BAUDRATE)
    ser.port=SERIALPORT
    ser.baudrate=BAUDRATE
    ser.bytesize = serial.EIGHTBITS #number of bits per bytes
    ser.parity = serial.PARITY_NONE #set parity check: no parity
    ser.stopbits = serial.STOPBITS_ONE #number of stop bits
    ser.timeout = None          #block read
    # ser.timeout = 0             #non-block read
    # ser.timeout = 2              #timeout block read
    ser.xonxoff = False     #disable software flow control
    ser.rtscts = False     #disable hardware (RTS/CTS) flow control
    ser.dsrdtr = False       #disable hardware (DSR/DTR) flow control
    #ser.writeTimeout = 0     #timeout for write
    print ("Starting Up Serial Monitor")
    try:
            ser.open()
    except serial.SerialException:
            print("Serial {} port not available".format(SERIALPORT))
            exit()


def sendUARTMessage(msg):
    ser.write(msg.encode())
    bOk.value = False

def receiveUARTMessage():
    nMessageRecu,nMessageEchec = 0,0
    while ser.isOpen() : 
            dataLength = 27
            time.sleep(1)
            dataBytes = bytearray()
            if (ser.inWaiting() > 0): # if incoming bytes are waiting 
                print("------------------------")
                print("Message reçu")
                bOk.value = True
                while dataLength != 0:
                    dataBytes += (ser.read(dataLength))
                    dataLength -= len((dataBytes))
                sid = str((dataBytes[0:4]))
                strLocationX = str((dataBytes[4:15]))
                strLocationY = str((dataBytes[16:26]))
                strFireIntensity = str((dataBytes[26]))
                strLocationX = strLocationX.replace('bytearray(b','').replace('\\x00','').replace("'","").replace(')','')
                strLocationY = strLocationY.replace('bytearray(b','').replace('\\x00','').replace("'","").replace(')','')
                sid = sid.replace('bytearray(b','').replace('\\x00','').replace("'","").replace(')','')
                print("------------------------")
                if valuesCorrect(sid,strLocationX,strLocationY,strFireIntensity):
                    nMessageRecu+= 1
                    print("Id: " + sid)
                    print("Location X: " + strLocationX)
                    print("Location Y: " + strLocationY)
                    print("Intensité Fire: " + strFireIntensity)
                   # client.publish("Fire",sid+"|"+strLocationX+"|"+strLocationY+"|"+strFireIntensity)
                    pushFireHTTP(strLocationX,strLocationY,strFireIntensity)
                else:
                    nMessageEchec += 1
                print("Nombre de message reçu / Nombre de message non reçu : " + str(nMessageRecu) + " / " + str(nMessageEchec))
                    # Requête HTTP POUR PUSH LE FEU


if __name__ == "__main__" :
    print("Début du programme")
    initUART()
    serverSerialmp = mp.Process(target=receiveUARTMessage,args=(bOk,))
    serverSerial_thread = threading.Thread(target=receiveUARTMessage)
    serverSerial_thread.daemon = True
    serverSerial_thread.start()
    client = mqtt.Client("SP_EmergencyManager")
    client.connect("127.0.0.1")
    while(True):
        time.sleep(10)