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