def encode(msg):
    return bytes(msg, encoding='utf-8')

def decode(msg):
    return str(msg, 'utf-8')
