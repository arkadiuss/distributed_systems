import sys, Ice
import IotController

with Ice.initialize() as communicator:
    base = communicator.stringToProxy("devices/device:default -p 10000")
    device = IotController.DevicePrx.checkedCast(base)
    if not device:
        raise RuntimeError("Invalid proxy")

    print(device.getId())