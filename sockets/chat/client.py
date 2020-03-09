import socket
import logging 
from common import *
import settings
import select
import threading
import struct

server_address = ('localhost', settings.SETTINGS['port'])
BUFFER_SIZE = settings.SETTINGS['buffer_size']
INIT_MSG = settings.SETTINGS['init_msg']
INIT_ACK_MSG = settings.SETTINGS['init_ack_msg']
MULTICAST_ADDRESS = settings.SETTINGS['multicast_address']
MULTICAST_PORT = settings.SETTINGS['multicast_port']

tcp_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
tcp_socket.connect(server_address)

buff = tcp_socket.recv(BUFFER_SIZE)
if decode(buff) != INIT_MSG:
    raise Exception()
logging.info("Init message received")
udp_socket = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
udp_address = ('localhost', tcp_socket.getsockname()[1])
#udp_socket.connect(udp_address)
udp_socket.sendto(encode(INIT_ACK_MSG), udp_address)

mlt_socket = socket.socket(socket.AF_INET, socket.SOCK_DGRAM, socket.IPPROTO_UDP)
mlt_socket.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
mlt_socket.bind(('', MULTICAST_PORT))
group = socket.inet_aton(MULTICAST_ADDRESS)
multicast_req = struct.pack('4sl', group, socket.INADDR_ANY)
mlt_socket.setsockopt(socket.IPPROTO_IP, socket.IP_ADD_MEMBERSHIP, multicast_req)

def read_thread(tcp_socket, udp_socket):
    epoll = select.epoll()
    epoll.register(tcp_socket.fileno(), select.EPOLLIN)
    epoll.register(udp_socket.fileno(), select.EPOLLIN)
    epoll.register(mlt_socket.fileno(), select.EPOLLIN)
    while True:
        events = epoll.poll(1)
        for fd, event in events:
            if fd == tcp_socket.fileno() and event & select.EPOLLIN:
                buff = tcp_socket.recv(BUFFER_SIZE)
                if len(buff)==0:
                    epoll.unregister(tcp_socket.fileno())
                    logging.info("TCP connection with server closed")
                print(decode(buff))
            elif fd == udp_socket.fileno() and event & select.EPOLLIN:
                buff, address = udp_socket.recvfrom(BUFFER_SIZE)
                if len(buff)==0:
                    epoll.unregister(udp_socket.fileno())
                    logging.info("UDP connection with server closed")
                print(decode(buff))
            elif fd == mlt_socket.fileno() and event & select.EPOLLIN:
                buff, address = mlt_socket.recvfrom(BUFFER_SIZE)
                print(decode(buff))

t = threading.Thread(target=read_thread,args=(tcp_socket, udp_socket))
t.start()

while True:
    command = input().split()
    if command[0] == 'U':
        udp_socket.sendto(encode(command[1]), udp_address)
    elif command[0] == 'M':
        print("Sending", MULTICAST_ADDRESS, MULTICAST_PORT)
        mlt_socket.sendto(encode(command[1]), (MULTICAST_ADDRESS, MULTICAST_PORT))
    else:
        tcp_socket.send(encode(command[0]))

