import socket
import threading
import logging
import sys

logging.basicConfig(stream=sys.stdout, level=logging.DEBUG)
PORT = 9090

def client_thread(clientsocket):
    while True:
        buff = clientsocket.recv(1024)
        logging.info('Received messsage %s', buff)


tcp_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
tcp_socket.bind(('', PORT))
tcp_socket.listen(10)
logging.info('Server is listening connections')

while True:
    (client_socket, client_address) = tcp_socket.accept()
    logging.info('Received connection from %s', client_address)
    ct = threading.Thread(target=client_thread, args=(client_socket,))
    ct.start()
