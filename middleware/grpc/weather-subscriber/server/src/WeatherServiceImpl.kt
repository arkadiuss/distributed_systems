import io.grpc.stub.StreamObserver
import kotlin.random.Random

class WeatherServiceImpl : WeatherServiceGrpc.WeatherServiceImplBase() {
    private var observers: MutableList<(String) -> Unit> = mutableListOf()

    fun generateEvents() {
        while(true) {
            val event = "Message about city ${Random.nextDouble()}"
            observers.forEach {
                it(event)
            }
            val timeToSleep = Random.nextLong(1000, 3000)
            Thread.sleep(timeToSleep)
        }
    }

    override fun subscribe(request: WeatherServiceOuterClass.SubscribeRequest?, responseObserver: StreamObserver<WeatherServiceOuterClass.Notification>?) {
        observers.add {
            val notification = WeatherServiceOuterClass.Notification.newBuilder()
                    .setMessage("Just works $it")
                    .build()
            responseObserver?.onNext(notification)
        }
    }
}