import serial, time
import threading
import multiprocessing as mp
# send serial message 
# Don't forget to establish the right serial port ******** ATTENTION
# SERIALPORT = "/dev/ttyUSB0"
SERIALPORT = "COM7"
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
    
    # print("Message <" + msg + "> sent to micro-controller." )

def receiveUARTMessage():
        while ser.isOpen() : 
                time.sleep(1)
                if (ser.inWaiting() > 0): # if incoming bytes are waiting 
                    bOk.value = True
                    data_str = (ser.read(ser.inWaiting()))

if __name__ == "__main__" :
    print("Début du programme")
    initUART()
    serverSerialmp = mp.Process(target=receiveUARTMessage,args=(bOk,))
    serverSerial_thread = threading.Thread(target=receiveUARTMessage)
    serverSerial_thread.daemon = True
    serverSerial_thread.start()
    while(True):
        if(bOk.value):
            print("Message envoyé")
            sendUARTMessage("(48.862725,60.854657,255)")
            bOk.value = False
        time.sleep(9)