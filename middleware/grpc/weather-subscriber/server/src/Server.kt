import io.grpc.ServerBuilder
import java.io.IOException
import java.util.concurrent.Executors
import java.util.logging.Logger

class Server {
    private val port = 50051
    private var server: io.grpc.Server? = null

    @Throws(IOException::class)
    private fun start() {
        val weatherService = WeatherServiceImpl()
        server = ServerBuilder.forPort(port)
				.addService(weatherService)
                .build()
                .start()
        logger.info("Server started, listening on $port")
        Runtime.getRuntime().addShutdownHook(object : Thread() {
            override fun run() { // Use stderr here since the logger may have been reset by its JVM shutdown hook.
                System.err.println("*** shutting down gRPC server since JVM is shutting down")
                this@Server.stop()
                System.err.println("*** server shut down")
            }
        })
        Executors.newFixedThreadPool(1).submit { weatherService.generateEvents() }
    }

    private fun stop() {
        if (server != null) {
            server!!.shutdown()
        }
    }

    @Throws(InterruptedException::class)
    private fun blockUntilShutdown() {
        if (server != null) {
            server!!.awaitTermination()
        }
    }

    companion object {
        private val logger = Logger.getLogger(Server::class.java.name)

        @Throws(IOException::class, InterruptedException::class)
        @JvmStatic
        fun main(args: Array<String>) {
            val server = Server()
            server.start()
            server.blockUntilShutdown()
        }
    }
}