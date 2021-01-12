from microbit import *
import radio
# Constante
SENSOR_RECEIVER_IP = "164.4.1.1"
SENSOR_RECEIVER_BAUDRATE = radio.RATE_1MBIT
DATA_RADIO_LENGTH = 25
START_BYTES = 1  # SOH
END_BYTES = 4  # EOT
RADIO_FRAME_LENGTH = 32
KEY_ENCODING = 150

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

def sendDataToUART(dataSent):
    uart.write(dataSent)

def createHeadingRF(IPDestinationRF):
    sourceBytes = [0 for i in range(4)]
    destinationBytes = [0 for i in range(4)]
    for i in range(len(sourceBytes)):
        sourceBytes[i] = int(SENSOR_RECEIVER_IP.split(".")[i])
    for i in range(len(destinationBytes)):
        destinationBytes[i] = int(IPDestinationRF.split(".")[i])
    radioFrame = bytes(sourceBytes)
    radioFrame += bytes(destinationBytes)
    return radioFrame

def sendRF(dataRF, IPDestinationRF):
    FrameSend = createHeadingRF(IPDestinationRF)
    FrameSend += dataRF
    FrameSend = encodeFrame(FrameSend)
    radioSocket.send_bytes(FrameSend)

def checkIdentity(sourceRF, destionationRF):
    if destionationRF != SENSOR_RECEIVER_IP:
        return False
    # Vérification de la source
    return True

def getSourceRF(bytesArrayData):
    sourceRF = str(bytesArrayData[0])+"."+str(bytesArrayData[1])+"."
    sourceRF += str(bytesArrayData[2])+"."+str(bytesArrayData[3])
    return sourceRF

def getDestinationRF(bytesArrayData):
    destionationRF = str(bytesArrayData[4])+"."+str(bytesArrayData[5])+"."
    destionationRF += str(bytesArrayData[6])+"."+str(bytesArrayData[7])
    return destionationRF

def getDataLengthRF(bytesArrayData):
    dataLength = int(str(bytesArrayData[8]), 16)
    return dataLength

def receiveRF(bytesArrayData, frameLengthWaited):
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
# Socket radio
radioSocket = radio
radioSocket.on()
while True:
    display.clear()
    display.set_pixel(0, 1, 9)
    bytesArrayDataUART = bytearray()
    idSensor = bytearray()
    radioSocket.config(length=RADIO_FRAME_LENGTH,data_rate=SENSOR_RECEIVER_BAUDRATE,address=0x75626974)
    # Si des données sont reçue en radio
    if(receiveRF(bytesArrayDataUART, 13)):
        display.set_pixel(1, 1, 9)
        sourceIP = getSourceRF(bytesArrayDataUART)
        destinationIP = getDestinationRF(bytesArrayDataUART)
        dataLength = getDataLengthRF(bytesArrayDataUART)
        idSensor = bytesArrayDataUART[9:13]
        # Récupération du baudrate de l'énvoyeur
        if checkIdentity(sourceIP, destinationIP):
            display.set_pixel(2, 1, 9)
            # On link nos deux cartes pour une communication monodirectionnelle
            radioSocket.config(address=int(hex(int(sourceIP.replace(".", ""))), 16))
            # Début de la communication
            bytesArrayDataRF = bytearray()
            while (receiveRF(bytesArrayDataRF, RADIO_FRAME_LENGTH)) == False:
                sleep(1)
            # Envoie de l'acusé de réception
            display.set_pixel(3, 1, 9)
            sendDataToUART(idSensor+bytesArrayDataRF[9:32])
            sendRF(bytesArrayDataRF[9:32],sourceIP)
            display.set_pixel(4, 1, 9)
    else:
        display.set_pixel(1, 1, 0)
    sleep(1)