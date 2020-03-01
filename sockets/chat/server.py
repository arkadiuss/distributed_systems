import socket
import threading
import logging
import sys
import settings
from common import *
import select
import clients_manager

logging.basicConfig(stream=sys.stdout, level=logging.DEBUG)

cl_mngr = clients_manager.ClientsManager()

def send_tcp(from, msg, socket):
    sender = cl_mngr.find_by_tcp_address(from)
    for client in cl_mngr.get_clients():
        if client != sender:
            socket.send(client.tcp_address, encode("{}: {}".format(sender.id, msg))


def client_thread(tcp_socket):
    udp_socket = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
    address = ('', tcp_socket.getpeername()[1])
    udp_socket.bind(address)
    logging.info('Server is listening for UDP connections from %s', address)
   
    cl_mngr.add(tcp_socket.getsockname(), address)

    epoll = select.epoll()
    epoll.register(tcp_socket.fileno(), select.EPOLLIN)
    epoll.register(udp_socket.fileno(), select.EPOLLIN)
    try:
        while True:
            events = epoll.poll(1)
            for fileno, event in events:
                if fileno == tcp_socket.fileno() and event & select.EPOLLIN:
                    buff = tcp_socket.recv(1024)
                    if len(buff) == 0:
                        logging.info('Connection with s closed') 
                        break
                    logging.info('Received TCP messsage %s', decode(buff))
                    send_tcp(tcp_socket.getsockname(), decode(buff), tcp_socket)    
                elif fileno == udp_socket.fileno() and event & select.EPOLLIN:
                    buff, address = udp_socket.recvfrom(1024)
                    if len(buff) == 0:
                        logging.info('Connection UDP with %s broken', address)
                        break
                    logging.info('Received UDP message %s', decode(buff))
    finally:
        epoll.unregister(tcp_socket.fileno())
        epoll.unregister(udp_socket.fileno())
        epoll.close()
        tcp_socket.close()
        udp_socket.close()

listen_address = ('', settings.SETTINGS['port'])

tcp_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
tcp_socket.bind(listen_address)
tcp_socket.listen(10)
logging.info('Server is listening for TCP connections')

try:
    while True:
        (client_socket, client_address) = tcp_socket.accept()
        logging.info('TCP connection with %s extablished', client_address)
        ct = threading.Thread(target=client_thread, args=(client_socket,))
        ct.start()
finally:
    tcp_socket.close()
