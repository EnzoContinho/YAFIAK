from microbit import *
import radio
# Constante
SENSOR_IP = "192.58.65.158"
SENSOR_RECEIVER_IP = "164.4.1.1"

SENSOR_BAUDRATE = radio.RATE_1MBIT
SENSOR_RECEIVER_BAUDRATE = radio.RATE_1MBIT
DATA_RADIO_LENGTH = 25
START_BYTES = 1 # SOH
END_BYTES = 4  # EOT
RADIO_FRAME_LENGTH = 34

def receiveDataFromUART(arrayData):
    # recupère un paquet de données soit 25 octets
    nbBytesRead = 0
    byteRead = ""
    strByteRead= ""
    while nbBytesRead != 25:
        byteRead = uart.read(25-nbBytesRead)
        if str(byteRead) != "None":
            # vérification du nombre d'octet restant à lire
            strByteRead += str(byteRead)
            nbBytesRead += len(byteRead)
        else:
            return False
    # suppresion des caractères en trop
    strByteRead = strByteRead.replace('b','')
    strByteRead = strByteRead.replace('(','')
    strByteRead = strByteRead.replace(')','')
    strByteRead = strByteRead.replace("'",'')
    # création du tableau de valeur
    arrayData[0] = (strByteRead.split(","))[0]
    arrayData[1] = (strByteRead.split(","))[1]
    arrayData[2] = (strByteRead.split(","))[2]
    return True
def sendDataToUART(dataSent):
    uart.write(dataSent)
def createHeadingRF():
    sourceBytes = [0 for i in range(4)]
    destinationBytes = [0 for i in range(4)]
    for i in range(len(sourceBytes)):
        sourceBytes[i] = int(SENSOR_IP.split(".")[i])
    for i in range(len(destinationBytes)):
        destinationBytes[i] = int(SENSOR_RECEIVER_IP.split(".")[i])
    radioFrame = bytes(sourceBytes)
    radioFrame += bytes(destinationBytes)
    return radioFrame
def createDataRF(arrayData):
    bytesDataX = [0 for i in range(11)]
    bytesDataY = [0 for i in range(11)]
    bytesDataIntensity = [0 for i in range(1)]
    for i in range(len(bytesDataX)):
        if(len(bytesDataX)-(len(arrayData[0])+i)) > 0:
            bytesDataX[i] = 0
        else:
            bytesDataX[i] = ord((arrayData[0])[i-(len(bytesDataX)-len(arrayData[0]))])
    for i in range(len(bytesDataY)):
        if(len(bytesDataY)-(len(arrayData[1])+i)) > 0:
            bytesDataY[i] = 0
        else:
            bytesDataY[i] = ord((arrayData[1])[i-(len(bytesDataY)-len(arrayData[1]))])
    bytesDataIntensity[0] = int(arrayData[2])
    radioFrame = bytes([(len(bytesDataX)+len(bytesDataY)+len(bytesDataY))])
    radioFrame += bytes(bytesDataX)
    radioFrame += bytes(bytesDataY)
    radioFrame += bytes(bytesDataIntensity)
    return radioFrame
def createInitRF():
    # Ajout de la taille des données
    radioFrame = bytes([len(bytes([SENSOR_BAUDRATE]))])
    #Ajout des données
    radioFrame += bytes([SENSOR_BAUDRATE])
    return radioFrame
def initRadioSocket():
    radioAdress = int(hex(int(SENSOR_IP.replace(".",""))),16)
    radioSocketMono.config(length=RADIO_FRAME_LENGTH,address=radioAdress,data_rate=SENSOR_BAUDRATE)
    radioSocketMono.on()

def sendRF(socketRF,dataRF):
    FrameSend = createHeadingRF()
    FrameSend += dataRF
    sleep(500)
    socketRF.send_bytes(FrameSend)

def receiveRF(bytesArrayData,frameLengthWaited):
    nbBytesRead = 0
    byteRead = ""
    # Récupération de la trame entière
    while nbBytesRead < frameLengthWaited:
        byteRead = radioSocket.receive_bytes()
        if str(byteRead) != "None":
            # vérification du nombre d'octet restant à lire
            bytesArrayData += byteRead
            nbBytesRead += len(byteRead)
        else:
            return False
    return True

def checkIdentity(sourceRF,destionationRF):
    if destionationRF != SENSOR_IP:return False
    if sourceRF != SENSOR_RECEIVER_IP:return False
    # Vérification de la source
    return True

# initialisation de l'uart
uart.init(baudrate=115200)
arrayData = [0 for i in range(3)]

# initilaisation des 2 sockets Radio
radioSocket = radio
radioSocket.config(length=RADIO_FRAME_LENGTH,data_rate=SENSOR_RECEIVER_BAUDRATE)
radioSocket.on()


while True:
    bytesArrayData = bytearray()
    # Si des données sont reçue sur l'UART
    if(receiveDataFromUART(arrayData)):
        sendRF(radioSocket,createInitRF())
        radioSocket.config(address=int(hex(int(SENSOR_IP.replace(".",""))),16))
        sleep(500)
        while receiveRF(bytesArrayData,RADIO_FRAME_LENGTH) == False:
            sendRF(radioSocket,createDataRF(arrayData))
            sleep(500)
        display.set_pixel(1,1,9)
        sendDataToUART(bytes(1))
        radioSocket.config(length=RADIO_FRAME_LENGTH,data_rate=SENSOR_RECEIVER_BAUDRATE,address=0x75626974)
    else:
        display.set_pixel(1,1,0)
    sleep(1000)
