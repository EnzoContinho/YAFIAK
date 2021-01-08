import serial, time
import threading,random
import multiprocessing as mp

# CONSTANT
SERIALPORT = "COM7"
BAUDRATE = 115200

# GLOBAL
nValue = 0
sX,sY = 0,0
ser = serial.Serial()
semaphoreSendingData = mp.Semaphore(1)
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

# Function that send data from sever to micobit via the serial port
def sendUARTMessage(msg):
    print("----- Data sended -----")
    print("-> "+msg)
    ser.write(msg.encode())

# Function that take in charge the data receive on serial port
def receiveUARTMessage():
        while ser.isOpen() : 
            if (ser.inWaiting() > 0): # if incoming bytes are waiting 
                if str(ser.read(3)) == "b'FIN'":
                    semaphoreSendingData.release()
               
# Function to get the data to send
# return a string in format :4 digits | 1 digit | 1 digit | 3 digit
def getDataToSend():
    global sX,sY
    sDataToSend = ""
    # Get X and Y
    if sY == 6:
        sX +=1
        sY = 0
    if sX == 10:
        sX = 0
    # Get id and FireIntensity
    sId = str(random.randint(1,60))
    sIntensity = str(random.randint(0,100))
    # Format data
    for i in range(4-len(sId)):
        sId = "0"+sId
    for i in range(3-len(sIntensity)):
        sIntensity = "0"+sIntensity
    # Put together all information
    sDataToSend = "("+sId+","+str(sX)+","+str(sY)+","+sIntensity+")"
    # Increment Y
    sY += 1
    return sDataToSend

if __name__ == "__main__" :
    print("----- Program's initializing -----")
    initUART()
    print("----- UART Receiver Thread's initializing -----")
    serverSerial_thread = threading.Thread(target=receiveUARTMessage)
    serverSerial_thread.daemon = True
    print("----- UART Receiver Thread starts -----")
    serverSerial_thread.start()
    print("----- Program starts -----")
    while(True):
        # Ask to send data
        semaphoreSendingData.acquire()
        # Get data
        sDataToSend = getDataToSend()
        if sDataToSend != "":
            # Send data
            sendUARTMessage(sDataToSend)
            # Block sending