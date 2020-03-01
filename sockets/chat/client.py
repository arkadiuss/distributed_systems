import socket
import logging 
from common import *
import settings

server_address = ('localhost', settings.SETTINGS['port'])

tcp_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
tcp_socket.connect(server_address)

udp_socket = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
udp_address = ('localhost', tcp_socket.getsockname()[1])
print('created with {}'.format(udp_address))
while True:
    command = input().split()
    if command[0] == 'U':
        udp_socket.sendto(encode(command[1]), udp_address)
    else:
        tcp_socket.send(encode(command[0]))
