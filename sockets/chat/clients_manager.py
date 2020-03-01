import threading

class Client:

    def __init__(self, id, tcp_address, udp_address):
        self.id = id
        self.tcp_address = tcp_address
        self.udp_address = udp_address


class ClientsManager:

    def __init__(self):
        self.clients = []
        self.nid = 0
        self.lock = threading.RLock()

    def add(self, tcp_address, udp_address):
        client = new Client(self.nid, tcp_address, udp_address) 
        with self.lock:    
            self.clients.append(client)
            self.nid = self.nid + 1
        return client

    def remove(self, client):
        with self.lock:
            self.clients.remove(client)

    def get_clients():
        with self.lock:
            return list(self.clients)
    
    def find_by_tcp_address(address):
        return [c for c in self.clients where c.tcp_address == address][0]

    def find_by_udp_address(address):
        return [c for c in self.clients where c.udp_address == address][0]
        
