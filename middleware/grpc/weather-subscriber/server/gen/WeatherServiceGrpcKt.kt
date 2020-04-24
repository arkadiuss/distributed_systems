import io.grpc.CallOptions
import io.grpc.CallOptions.DEFAULT
import io.grpc.Channel
import io.grpc.Metadata
import io.grpc.ServerServiceDefinition
import io.grpc.ServerServiceDefinition.builder
import io.grpc.Status.UNIMPLEMENTED
import io.grpc.StatusException
import io.grpc.kotlin.AbstractCoroutineServerImpl
import io.grpc.kotlin.AbstractCoroutineStub
import io.grpc.kotlin.ClientCalls.unaryRpc
import io.grpc.kotlin.ServerCalls.unaryServerMethodDefinition
import io.grpc.kotlin.StubFor
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.jvm.JvmOverloads

/**
 * Holder for Kotlin coroutine-based client and server APIs for WeatherService.
 */
object WeatherServiceGrpcKt {
  /**
   * A stub for issuing RPCs to a(n) WeatherService service as suspending coroutines.
   */
  @StubFor(WeatherServiceGrpc::class)
  class WeatherServiceCoroutineStub @JvmOverloads constructor(
    channel: Channel,
    callOptions: CallOptions = DEFAULT
  ) : AbstractCoroutineStub<WeatherServiceCoroutineStub>(channel, callOptions) {
    override fun build(channel: Channel, callOptions: CallOptions): WeatherServiceCoroutineStub =
        WeatherServiceCoroutineStub(channel, callOptions)

    /**
     * Executes this RPC and returns the response message, suspending until the RPC completes
     * with [`Status.OK`][io.grpc.Status].  If the RPC completes with another status, a
     * corresponding
     * [StatusException] is thrown.  If this coroutine is cancelled, the RPC is also cancelled
     * with the corresponding exception as a cause.
     *
     * @param request The request message to send to the server.
     *
     * @return The single response from the server.
     */
    suspend fun subscribe(request: WeatherServiceOuterClass.SubscribeRequest): WeatherServiceOuterClass.SubscribeReply =
        unaryRpc(
      channel,
      WeatherServiceGrpc.getSubscribeMethod(),
      request,
      callOptions,
      Metadata()
    )}

  /**
   * Skeletal implementation of the WeatherService service based on Kotlin coroutines.
   */
  abstract class WeatherServiceCoroutineImplBase(
    coroutineContext: CoroutineContext = EmptyCoroutineContext
  ) : AbstractCoroutineServerImpl(coroutineContext) {
    /**
     * Returns the response to an RPC for WeatherService.Subscribe.
     *
     * If this method fails with a [StatusException], the RPC will fail with the corresponding
     * [io.grpc.Status].  If this method fails with a [java.util.concurrent.CancellationException],
     * the RPC will fail
     * with status `Status.CANCELLED`.  If this method fails for any other reason, the RPC will
     * fail with `Status.UNKNOWN` with the exception as a cause.
     *
     * @param request The request from the client.
     */
    open suspend fun subscribe(request: WeatherServiceOuterClass.SubscribeRequest):
            WeatherServiceOuterClass.SubscribeReply = throw
        StatusException(UNIMPLEMENTED.withDescription("Method WeatherService.Subscribe is unimplemented"))

    final override fun bindService(): ServerServiceDefinition =
        builder(WeatherServiceGrpc.getServiceDescriptor())
      .addMethod(unaryServerMethodDefinition(
      context = this.context,
      descriptor = WeatherServiceGrpc.getSubscribeMethod(),
      implementation = ::subscribe
    )).build()
  }
}
