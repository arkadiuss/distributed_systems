import sys, Ice
from device_cmd import *
import IotController


def get_cmd(type, proxy):
    if type == 'radio':
        return RadioCmd(proxy)
    elif type == 'fridge':
        return FridgeCmd(proxy)
    elif type == 'smartfridge':
        return SmartFridgeCmd(proxy)
    elif type == 'camera':
        return CameraCmd(proxy)
    elif type == 'ptzcamera':
        return PtzCameraCmd(proxy)
    elif type == 'aicamera':
        return AiCameraCmd(proxy)
    return None


with Ice.initialize(sys.argv) as communicator:
    while True:
        print("Select device (type device): ")
        type, name = input().split()
        base = communicator.stringToProxy("devices/{0}:tcp -h localhost -p 10000".format(name))
        try:
            cmd = get_cmd(type, base)
        except:
            print("Invalid id")
            continue

        if cmd is None:
            print("Invalid type")
            continue

        while True:
            print("{0}>".format(name), end=" ")
            command = input().split()
            cmdstr, args = command[0], command[1:]
            if cmdstr == 'change':
                break
            elif cmdstr == 'usage':
                print(cmd.usage())
            else:
                try:
                    res = cmd.invoke(cmdstr, args)
                    print(res)
                except IotController.BaseException as e:
                    print("Error occurred: ", e.message)