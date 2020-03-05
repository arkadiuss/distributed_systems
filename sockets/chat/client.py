import socket
import logging 
from common import *
import settings
import select
import threading

server_address = ('localhost', settings.SETTINGS['port'])
BUFFER_SIZE = settings.SETTINGS['buffer_size']

tcp_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
tcp_socket.connect(server_address)

buff = tcp_socket.recv(BUFFER_SIZE)
if decode(buff) != "Hello TCP":
    raise Exception()
logging.info("Hello TCP received")
udp_socket = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
udp_address = ('localhost', tcp_socket.getsockname()[1])
#udp_socket.connect(udp_address)
udp_socket.sendto(encode("Hello UDP"), udp_address)

def read_thread(tcp_socket, udp_socket):
    epoll = select.epoll()
    epoll.register(tcp_socket.fileno(), select.EPOLLIN)
    epoll.register(udp_socket.fileno(), select.EPOLLIN)
    while True:
        events = epoll.poll(1)
        for fd, event in events:
            if fd == tcp_socket.fileno() and event & select.EPOLLIN:
                buff = tcp_socket.recv(BUFFER_SIZE)
                if len(buff)==0:
                    epoll.unregister(tcp_socket.fileno())
                    logging.info("TCP connection with server closed")
                print(decode(buff))
            elif fd == udp_socket.fileno() and event:
                buff, address = udp_socket.recvfrom(BUFFER_SIZE)
                if len(buff)==0:
                    epoll.unregister(udp_socket.fileno())
                    logging.info("UDP connection with server closed")
                print(decode(buff))

t = threading.Thread(target=read_thread,args=(tcp_socket, udp_socket))
t.start()

while True:
    command = input().split()
    if command[0] == 'U':
        udp_socket.sendto(encode(command[1]), udp_address)
    else:
        tcp_socket.send(encode(command[0]))
