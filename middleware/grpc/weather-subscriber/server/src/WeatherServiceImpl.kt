import io.grpc.stub.StreamObserver

class WeatherServiceImpl : WeatherServiceGrpc.WeatherServiceImplBase() {
    override fun subscribe(request: WeatherServiceOuterClass.SubscribeRequest?, responseObserver: StreamObserver<WeatherServiceOuterClass.SubscribeReply>?) {
        println("extra")
        super.subscribe(request, responseObserver)
    }
}