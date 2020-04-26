import IotController


class Cmd:
    def __init__(self, cmd, description, argc, *args):
        self.cmd = cmd
        self.description = description
        self.argc = argc
        self.args = args


class DeviceCmd:
    def __init__(self, proxy, commands):
        self.proxy = proxy
        self.commands = commands

    def invoke(self, cmdstr, args):
        found = False
        for cmd, func in self.commands:
            if cmd.cmd == cmdstr:
                found = True
                if cmd.argc > len(args):
                    print("Too less arguments. Usage: {0} {1}".format(cmd.cmd, " ".join(cmd.args)))
                    break
                else:
                    return func(*args)

        if not found:
            print("Command not found")

    def usage(self):
        res = "Usage:\n"
        for cmd, _ in self.commands:
            res += "{0} {1} - {2} \n".format(cmd.cmd, " ".join(cmd.args), cmd.description)
        return res


class RadioCmd(DeviceCmd):
    def __init__(self, proxy):
        prx = IotController.RadioPrx.checkedCast(proxy)
        if not prx:
            raise RuntimeError("Invalid proxy")

        commands = [
            (Cmd("vol", "set volume to x", 1, "x"), self.set_volume),
            (Cmd("freq", "set frequency to x", 1, "x"), self.set_freq),
            (Cmd("on", "turn radio on", 0), self.on),
            (Cmd("off", "turn radio off", 0), self.off),
            (Cmd("info", "display info", 0), self.info),
        ]
        super().__init__(prx, commands)

    def set_volume(self, volume):
        self.proxy.setVolume(int(volume))

    def set_freq(self, freq):
        self.proxy.setFrequency(float(freq))

    def on(self):
        self.proxy.turnOn()

    def off(self):
        self.proxy.turnOff()

    def info(self):
        return self.proxy.getInfo()


class CameraCmd(DeviceCmd):
    def __init__(self, proxy):
        prx = IotController.CameraPrx.checkedCast(proxy)
        if not prx:
            raise RuntimeError("Invalid proxy")

        commands = [
            (Cmd("on", "turn camera on", 0), self.on),
            (Cmd("off", "turn camera off", 0), self.off),
            (Cmd("info", "display info", 0), self.info),
            (Cmd("takepic", "take screenshot", 0), self.screenshot),
        ]
        super().__init__(prx, commands)

    def on(self):
        self.proxy.turnOn()

    def off(self):
        self.proxy.turnOff()

    def info(self):
        return self.proxy.getInfo()

    def screenshot(self):
        response = self.proxy.takePicture()
        return [ response[i*6:(i+1)*6] for i in range(6) ]


class PtzCameraCmd(DeviceCmd):
    def __init__(self, proxy):
        prx = IotController.PtzCameraPrx.checkedCast(proxy)
        if not prx:
            raise RuntimeError("Invalid proxy")

        commands = [
            (Cmd("on", "turn camera on", 0), self.on),
            (Cmd("off", "turn camera off", 0), self.off),
            (Cmd("info", "display info", 0), self.info),
            (Cmd("zoom", "zoom in or out", 1, "in/out"), self.zoom),
            (Cmd("tilt", "tilt camera", 1, "angle"), self.tilt),
            (Cmd("pan", "pan camera", 1, "angle"), self.pan),
        ]
        super().__init__(prx, commands)

    def on(self):
        self.proxy.turnOn()

    def off(self):
        self.proxy.turnOff()

    def info(self):
        return self.proxy.getInfo()

    def zoom(self, arg):
        if arg == "in":
            return self.proxy.zoomIn()
        if arg == "out":
            return self.proxy.zoomOut()
        return "Invalid arg"

    def pan(self, angle):
        return self.proxy.pan(float(angle))

    def tilt(self, angle):
        return self.proxy.tilt(float(angle))


class AiCameraCmd(DeviceCmd):
    def __init__(self, proxy):
        prx = IotController.AICameraPrx.checkedCast(proxy)
        if not prx:
            raise RuntimeError("Invalid proxy")

        commands = [
            (Cmd("on", "turn camera on", 0), self.on),
            (Cmd("off", "turn camera off", 0), self.off),
            (Cmd("info", "display info", 0), self.info),
            (Cmd("detect", "detect if there are ant cats", 0), self.detect),
        ]
        super().__init__(prx, commands)

    def on(self):
        self.proxy.turnOn()

    def off(self):
        self.proxy.turnOff()

    def info(self):
        return self.proxy.getInfo()

    def detect(self):
        return self.proxy.detectCats()


class FridgeCmd(DeviceCmd):
    def __init__(self, proxy):
        prx = IotController.FridgePrx.checkedCast(proxy)
        if not prx:
            raise RuntimeError("Invalid proxy")

        commands = [
            (Cmd("on", "turn fridge on", 0), self.on),
            (Cmd("off", "turn fridge off", 0), self.off),
            (Cmd("info", "display info", 0), self.info),
            (Cmd("temp", "set temperature", 1, "x"), self.temp),
        ]
        super().__init__(prx, commands)

    def on(self):
        self.proxy.turnOn()

    def off(self):
        self.proxy.turnOff()

    def info(self):
        return self.proxy.getInfo()

    def temp(self, temp):
        return self.proxy.setTemp(float(temp))


class SmartFridgeCmd(DeviceCmd):
    def __init__(self, proxy):
        prx = IotController.SmartFridgePrx.checkedCast(proxy)
        if not prx:
            raise RuntimeError("Invalid proxy")

        commands = [
            (Cmd("on", "turn fridge on", 0), self.on),
            (Cmd("off", "turn fridge off", 0), self.off),
            (Cmd("info", "device info", 0), self.info),
            (Cmd("temp", "set temperature", 1, "x"), self.temp),
            (Cmd("get", "get current items", 0), self.check),
            (Cmd("recipe", "add recipe ", 2, "name", "items..."), self.recipe),
            (Cmd("need", "check what you need for recipe", 1, "name"), self.need),
        ]
        super().__init__(prx, commands)

    def on(self):
        self.proxy.turnOn()

    def off(self):
        self.proxy.turnOff()

    def info(self):
        return self.proxy.getInfo()

    def temp(self, temp):
        return self.proxy.setTemp(float(temp))

    def check(self):
        return self.proxy.getCurrentItems()

    def recipe(self, name, *items):
        itemsParsed = []
        for item in items:
            n, q = item.split(";")
            itemsParsed.append(IotController.Item(n, int(q)))
        return self.proxy.addRecipe(name, itemsParsed)

    def need(self, name):
        return self.proxy.whatINeedFor(name)