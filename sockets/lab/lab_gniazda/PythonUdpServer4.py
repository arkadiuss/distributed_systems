import socket;

serverPort = 9009
serverSocket = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
serverSocket.bind(('', serverPort))
buff = []

print('PYTHON UDP SERVER')

while True:

    buff, address = serverSocket.recvfrom(1024)
    msg = str(buff, 'cp1250')
    if msg == "Ping java":
        print("Pong java")
        serverSocket.sendto(bytes("Pong java", 'cp1250'), address)
    elif msg == "Ping python":
        print("Pong python")
        serverSocket.sendto(bytes("Pong python", 'cp1250'), address)



