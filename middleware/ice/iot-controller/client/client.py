import sys, Ice
import IotController

with Ice.initialize(sys.argv) as communicator:
    while(True):
        print("Select device (type device): ")
        type, name = input().split()
        base = communicator.stringToProxy("devices/{0}:tcp -h localhost -p 10000".format(name))
        if type == 'radio':
            device = IotController.RadioPrx.checkedCast(base)
            if not device:
                raise RuntimeError("Invalid proxy")

            print("Device Info:")
            print(device.getInfo())
            print("""Usage:
                vol x - set volume to x
                freq x - set freq to x
                info - show info
                change - change device
            """)
            while(True):
                command = input().split()
                if command[0] == 'change':
                    break
                elif command[0]  == 'info':
                    print(device.getInfo())
                elif command[0] == 'vol':
                    device.setVolume(int(command[1]))
                elif command[0] == 'freq':
                    device.setFrequency(float(command[1]))
                else:
                    print("Unknown command")
