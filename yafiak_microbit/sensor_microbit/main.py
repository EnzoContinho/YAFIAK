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
RADIO_FRAME_LENGTH = 32
KEY_ENCODING = 150
ID_SENSOR = ""

def encodeFrame(frameToEncode):
    frameEncoded = [0 for i in range(len(frameToEncode))]
    for i in range(len(frameToEncode)):
        frameEncoded[i] = frameToEncode[i] ^ KEY_ENCODING
    return bytes(frameEncoded)

def decodeFrame(frameEncoded):
    frameDecoded = [0 for i in range(len(frameEncoded))]
    for i in range(len(frameEncoded)):
        frameDecoded[i] = frameEncoded[i] ^ KEY_ENCODING
    return bytes(frameDecoded)

def receiveDataFromUART(arrayData):
    # recupère un paquet de données soit 25 octets
    nbBytesRead = 14
    byteRead = ""
    strByteRead= ""
    while nbBytesRead != 0:
        byteRead = uart.read(nbBytesRead)
        if str(byteRead) != "None":
            # vérification du nombre d'octet restant à lire
            strByteRead += str(byteRead)
            nbBytesRead -= len(byteRead)
        else:
            return False
    # suppresion des caractères en trop
    strByteRead = strByteRead.replace('b','')
    strByteRead = strByteRead.replace('(','')
    strByteRead = strByteRead.replace(')','')
    strByteRead = strByteRead.replace("'",'')
    # création du tableau de valeur
    global ID_SENSOR
    ID_SENSOR = str((strByteRead.split(","))[0])
    arrayData[0] = (strByteRead.split(","))[1]
    arrayData[1] = (strByteRead.split(","))[2]
    arrayData[2] = (strByteRead.split(","))[3]
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
    bytesData = [0 for i in range(11)]
    bytesDataIntensity = [0 for i in range(1)]
    radioFrame = bytes([(2*len(bytesData)+len(bytesDataIntensity))])
    for i in range(len(bytesData)):
        if(len(bytesData)-(len(arrayData[0])+i)) > 0:
            bytesData[i] = 0
        else:
            bytesData[i] = ord((arrayData[0])[i-(len(bytesData)-len(arrayData[0]))])
    radioFrame += bytes(bytesData)
    for i in range(len(bytesData)):
        if(len(bytesData)-(len(arrayData[1])+i)) > 0:
            bytesData[i] = 0
        else:
            bytesData[i] = ord((arrayData[1])[i-(len(bytesData)-len(arrayData[1]))])
    radioFrame += bytes(bytesData)
    bytesDataIntensity[0] = int(arrayData[2])
    radioFrame += bytes(bytesDataIntensity)
    return radioFrame

def createInitRF(ID_SENSOR):
    # Ajout de la taille des données
    radioFrame = bytes([len([ID_SENSOR])])
    idFire = [0 for i in range(4)]
    #Ajout des données
    for i in range(len(ID_SENSOR)):
        idFire[i] = ord(ID_SENSOR[i])
    radioFrame += bytes(idFire)
    return radioFrame

def sendRF(socketRF,dataRF):
    FrameSend = createHeadingRF()
    FrameSend += dataRF
    FrameSend = encodeFrame(FrameSend)
    socketRF.send_bytes(FrameSend)

def receiveRF(bytesArrayData,frameLengthWaited):
    nbBytesRead = 0
    byteRead = ""
    # Récupération de la trame entière
    while nbBytesRead < frameLengthWaited:
        byteRead = radioSocket.receive_bytes()
        if str(byteRead) != "None":
            byteRead = decodeFrame(byteRead)
            # vérification du nombre d'octet restant à lire
            bytesArrayData += byteRead
            nbBytesRead += len(byteRead)
        else:
            return False
    return True

# initialisation de l'uart
uart.init(baudrate=115200)
arrayData = [0 for i in range(3)]

# initilaisation des 2 sockets Radio
radioSocket = radio
radioSocket.on()
while True:
    display.clear()
    arrayData = [0 for i in range(3)]
    bOk = False
    display.set_pixel(0,0,9)
    bytesArrayData = bytearray()
    radioSocket.config(length=RADIO_FRAME_LENGTH,data_rate=SENSOR_RECEIVER_BAUDRATE,address=0x75626974)
    # Si des données sont reçue sur l'UART
    if(receiveDataFromUART(arrayData)):
        display.set_pixel(1,0,9)
        sendRF(radioSocket,createInitRF(ID_SENSOR))
        sleep(5)
        radioSocket.config(address=int(hex(int(SENSOR_IP.replace(".",""))),16))
        for i in range(2):
            bOk = receiveRF(bytesArrayData,RADIO_FRAME_LENGTH)
            if bOk :
                break
            display.set_pixel(2,0,9)
            sendRF(radioSocket,createDataRF(arrayData))
            sleep(5)
        display.set_pixel(3,0,9)
        sendDataToUART(bytes("FIN",'utf-8'))
    else:
        display.set_pixel(1,1,0)
    sleep(1)
