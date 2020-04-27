import com.google.common.collect.Lists
import com.google.common.collect.Lists.newCopyOnWriteArrayList
import io.grpc.StatusRuntimeException
import io.grpc.stub.StreamObserver
import kotlin.random.Random

typealias Observer = (String, String, Int, String) -> Unit

class WeatherServiceImpl : WeatherServiceGrpc.WeatherServiceImplBase() {
    private var observers: MutableList<Observer> = newCopyOnWriteArrayList()

    private val cities = listOf("warszawa", "krakow", "bielsko","toronto", "la")
    private val factors = listOf("temp", "wind", "humidity")

    fun generateEvents() {
        while(true) {
            val city = cities.random()
            val factor = factors.random()
            val value = Random.nextInt(1,10)
            val message = "In ${city.capitalize()} $factor changed to $value"
            println("Event occurred: $message")
            val toRemove = mutableListOf<Observer>()
            observers.forEach {
                try {
                    it(city, factor, value, message)
                } catch (e: StatusRuntimeException) {
                    toRemove.add(it)
                }
            }
            observers.removeAll(toRemove)
            val timeToSleep = Random.nextLong(500, 2000)
            Thread.sleep(timeToSleep)
        }
    }

    override fun getCities(request: WeatherServiceOuterClass.ValueRequest?, responseObserver: StreamObserver<WeatherServiceOuterClass.ValueResponse>?) {
        val response = WeatherServiceOuterClass.ValueResponse.newBuilder()
                .addAllValues(cities)
                .build()
        responseObserver?.onNext(response)
        responseObserver?.onCompleted()
    }

    override fun getFactors(request: WeatherServiceOuterClass.ValueRequest?, responseObserver: StreamObserver<WeatherServiceOuterClass.ValueResponse>?) {
        val response = WeatherServiceOuterClass.ValueResponse.newBuilder()
                .addAllValues(factors)
                .build()
        responseObserver?.onNext(response)
        responseObserver?.onCompleted()
    }

    override fun subscribe(request: WeatherServiceOuterClass.SubscribeRequest?, responseObserver: StreamObserver<WeatherServiceOuterClass.Notification>?) {
        val cities = request?.citiesList?.toList() ?: listOf()
        val factors = request?.factorsList?.toList() ?: listOf()
        observers.add { city, factor, value, message ->
            if(city in cities && factors.any { it?.name == factor && it.range?.min?:-1 <= value && it.range?.max?:-1 >= value }) {
                val notification = WeatherServiceOuterClass.Notification.newBuilder()
                        .setMessage(message)
                        .build()
                responseObserver?.onNext(notification)
            }
        }
    }
}