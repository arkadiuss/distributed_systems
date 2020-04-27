import com.zeroc.Ice.Util

class Server {

    fun start(args: Array<String>) {
        Util.initialize(args).use {
            val adapter = it.createObjectAdapter("IotAdapter")

            adapter.addServantLocator(DevicesLocator(), "devices")
            adapter.activate()
            println("Ready.")
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