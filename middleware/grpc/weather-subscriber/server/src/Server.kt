import io.grpc.ServerBuilder

class Server private constructor(
    val port: Int,
    val server: io.grpc.Server
) {

    constructor(port: Int) :
            this(
                serverBuilder = ServerBuilder.forPort(port),
                port = port
            )

    constructor(
        serverBuilder: ServerBuilder<*>,
        port: Int
    ) : this(
        port = port,
        server = serverBuilder.addService(WeatherServiceImpl()).build()
    )

    fun start() {
        server.start()
        println("Server started, listening on $port")
        /* ... */
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val port = 8980
            val server = Server(port)
            server.start()
        }
    }

}