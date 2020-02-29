import socket
import logging 

PORT=9090

tcp_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
tcp_socket.connect(('localhost', PORT))

while True:
    command = input().split()
    tcp_socket.send(bytes(command[0], encoding='utf-8'))
