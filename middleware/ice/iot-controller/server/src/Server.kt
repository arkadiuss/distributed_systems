import com.zeroc.Ice.Identity
import com.zeroc.Ice.Util

class Server {

    fun start(args: Array<String>) {
        Util.initialize(args).use {
            val adapter = it.createObjectAdapter("IotAdapter")
            var device = DeviceI()
            adapter.add(device, Identity("device", "devices"))
            adapter.activate()

            println("Activated")

            it.waitForShutdown()
        }
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val server = Server()
            server.start(args)
        }
    }
}