class WeatherServiceImpl : WeatherServiceGrpcKt.WeatherServiceCoroutineImplBase() {

    override suspend fun subscribe(request: WeatherServiceOuterClass.SubscribeRequest): WeatherServiceOuterClass.SubscribeReply {
        return super.subscribe(request)
    }
}