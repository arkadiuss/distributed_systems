import socket
import threading
import logging
import sys
import settings
from common import *
import select
import clients_manager
import time
logging.basicConfig(stream=sys.stdout, level=logging.DEBUG)

INIT_MSG = settings.SETTINGS['init_msg']
INIT_ACK_MSG = settings.SETTINGS['init_ack_msg']
cl_mngr = clients_manager.ClientsManager()

def send_tcp(from_address, msg):
    sender = cl_mngr.find_by_tcp_address(from_address)
    for client in cl_mngr.get_clients():
        if client != sender:
            client.tcp_socket.send(encode("{}: {}".format(sender.id, msg)))

def send_udp(from_address, msg):
    sender = cl_mngr.find_by_udp_address(from_address)
    if msg in ["1", "2", "3", "4"]:
        with open("asciiart/{}.aa".format(msg), "r") as f:
            msg = f.read()
    for client in cl_mngr.get_clients():
        if client != sender:
            client.udp_socket.sendto(encode("{}: {}".format(sender.id, msg)), client.udp_address)

       # print("Sending multicat")
       # client.udp_socket.sendto(encode("{}: {}".format(sender.id, msg)), ('224.5.5.1', 6212))
def client_thread(tcp_socket):
    udp_socket = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
    address = ('', tcp_socket.getpeername()[1])
    udp_socket.bind(address)
    logging.info('Server is listening for UDP connections from %s', address)
    tcp_socket.send(encode(INIT_MSG))
    buff, client_udp_address = udp_socket.recvfrom(1024)
    if decode(buff) != INIT_ACK_MSG:
        logging.error('Unable to connect by UDP')
        return
    logging.info("Hello received, connection established") 
    cl_mngr.add(tcp_socket, udp_socket, client_udp_address)

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
                        epoll.unregister(tcp_socket.fileno())
                        cl_mngr.remove_by_tcp_address(tcp_socket.getpeername())
                    logging.info('Received TCP messsage %s', decode(buff))
                    send_tcp(tcp_socket.getpeername(), decode(buff))    
                elif fileno == udp_socket.fileno() and event & select.EPOLLIN:
                    buff, address = udp_socket.recvfrom(1024)
                    if len(buff) == 0:
                        logging.info('Connection UDP with %s broken', address)
                        epoll.unregister(udp_socket.fileno())
                        cl_mngr.remove_by_udp_address(address)
                    logging.info('Received UDP message %s', decode(buff))
                    send_udp(address, decode(buff))
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
