import sys, Ice
from device_cmd import RadioCmd


def get_cmd(type, proxy):
    if type == 'radio':
        return RadioCmd(proxy)


with Ice.initialize(sys.argv) as communicator:
    while True:
        print("Select device (type device): ")
        type, name = input().split()
        base = communicator.stringToProxy("devices/{0}:tcp -h localhost -p 10000".format(name))
        cmd = get_cmd(type, base)
        while True:
            print(">", end=" ")
            command = input().split()
            cmdstr, args = command[0], command[1:]
            if cmdstr == 'change':
                break
            elif cmdstr == 'usage':
                print(cmd.usage())
            else:
                res = cmd.invoke(cmdstr, args)
                print(res)