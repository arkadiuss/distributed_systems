import socket;

serverIP = "127.0.0.1"
serverPort = 9008

print('PYTHON UDP CLIENT')
client = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
client.sendto((300).to_bytes(4, byteorder='big'), (serverIP, serverPort))

buff, address = client.recvfrom(1024)
print("Received response: " + str(int.from_bytes(buff, byteorder='big')))




