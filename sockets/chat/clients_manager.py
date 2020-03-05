import threading

class Client:

    def __init__(self, id, tcp_socket, udp_socket, udp_address):
        self.id = id
        self.tcp_socket = tcp_socket
        self.udp_socket = udp_socket
        self.udp_address = udp_address


class ClientsManager:

    def __init__(self):
        self.clients = []
        self.nid = 0
        self.lock = threading.RLock()

    def add(self, tcp_socket, udp_socket, udp_address):
        client = Client(self.nid, tcp_socket, udp_socket, udp_address) 
        with self.lock:    
            self.clients.append(client)
            self.nid = self.nid + 1
        return client

    def remove(self, client):
        with self.lock:
            self.clients.remove(client)

    def get_clients(self):
        with self.lock:
            return list(self.clients)
    
    def find_by_tcp_address(self, address):
        return [c for c in self.clients if c.tcp_socket.getpeername() == address][0]

    def find_by_udp_address(self, address):
        return [c for c in self.clients if c.udp_address == address][0]
    
    def remove_by_tcp_address(self, address):
        client = self.find_by_tcp_address(address)
        self.remove(client)

    def remove_by_udp_address(self, address):
        client = self.find_by_udp_address(address)
        self.remove(client)    
