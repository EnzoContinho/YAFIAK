import serial, time
import threading
import multiprocessing as mp
import paho.mqtt.client as mqtt

# send serial message 
# Don't forget to establish the right serial port ******** ATTENTION
# SERIALPORT = "/dev/ttyUSB0"
SERIALPORT = "COM9"
BAUDRATE = 115200
ser = serial.Serial()
bOk = mp.Value("i",True)
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
        while ser.isOpen() : 
                dataLength = 27
                time.sleep(1)
                dataBytes = bytearray()
                if (ser.inWaiting() > 0): # if incoming bytes are waiting 
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
                    print(str(strFireIntensity))
                    print((strLocationX))
                    print((strLocationY))
                    print(sid)
                    client.publish("test",sid+"|"+strLocationX+"|"+strLocationY+"|"+strFireIntensity)


# 48.862725,60.854657,255
# id = random()
# latitude X float
# Longitude Y float
# Intensité float

if __name__ == "__main__" :
    print("Début du programme")
    initUART()
    serverSerialmp = mp.Process(target=receiveUARTMessage,args=(bOk,))
    serverSerial_thread = threading.Thread(target=receiveUARTMessage)
    serverSerial_thread.daemon = True
    serverSerial_thread.start()
    client =mqtt.Client("SP_EmergencyManager")
    client.connect("127.0.0.1")
    while(True):
        time.sleep(10)
        #client.publish("test","ON")