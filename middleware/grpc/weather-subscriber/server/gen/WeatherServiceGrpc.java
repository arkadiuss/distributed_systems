import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.29.0)",
    comments = "Source: weather-service.proto")
public final class WeatherServiceGrpc {

  private WeatherServiceGrpc() {}

  public static final String SERVICE_NAME = "WeatherService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<WeatherServiceOuterClass.ValueRequest,
      WeatherServiceOuterClass.ValueResponse> getGetCitiesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getCities",
      requestType = WeatherServiceOuterClass.ValueRequest.class,
      responseType = WeatherServiceOuterClass.ValueResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<WeatherServiceOuterClass.ValueRequest,
      WeatherServiceOuterClass.ValueResponse> getGetCitiesMethod() {
    io.grpc.MethodDescriptor<WeatherServiceOuterClass.ValueRequest, WeatherServiceOuterClass.ValueResponse> getGetCitiesMethod;
    if ((getGetCitiesMethod = WeatherServiceGrpc.getGetCitiesMethod) == null) {
      synchronized (WeatherServiceGrpc.class) {
        if ((getGetCitiesMethod = WeatherServiceGrpc.getGetCitiesMethod) == null) {
          WeatherServiceGrpc.getGetCitiesMethod = getGetCitiesMethod =
              io.grpc.MethodDescriptor.<WeatherServiceOuterClass.ValueRequest, WeatherServiceOuterClass.ValueResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "getCities"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  WeatherServiceOuterClass.ValueRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  WeatherServiceOuterClass.ValueResponse.getDefaultInstance()))
              .setSchemaDescriptor(new WeatherServiceMethodDescriptorSupplier("getCities"))
              .build();
        }
      }
    }
    return getGetCitiesMethod;
  }

  private static volatile io.grpc.MethodDescriptor<WeatherServiceOuterClass.ValueRequest,
      WeatherServiceOuterClass.ValueResponse> getGetFactorsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getFactors",
      requestType = WeatherServiceOuterClass.ValueRequest.class,
      responseType = WeatherServiceOuterClass.ValueResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<WeatherServiceOuterClass.ValueRequest,
      WeatherServiceOuterClass.ValueResponse> getGetFactorsMethod() {
    io.grpc.MethodDescriptor<WeatherServiceOuterClass.ValueRequest, WeatherServiceOuterClass.ValueResponse> getGetFactorsMethod;
    if ((getGetFactorsMethod = WeatherServiceGrpc.getGetFactorsMethod) == null) {
      synchronized (WeatherServiceGrpc.class) {
        if ((getGetFactorsMethod = WeatherServiceGrpc.getGetFactorsMethod) == null) {
          WeatherServiceGrpc.getGetFactorsMethod = getGetFactorsMethod =
              io.grpc.MethodDescriptor.<WeatherServiceOuterClass.ValueRequest, WeatherServiceOuterClass.ValueResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "getFactors"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  WeatherServiceOuterClass.ValueRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  WeatherServiceOuterClass.ValueResponse.getDefaultInstance()))
              .setSchemaDescriptor(new WeatherServiceMethodDescriptorSupplier("getFactors"))
              .build();
        }
      }
    }
    return getGetFactorsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<WeatherServiceOuterClass.SubscribeRequest,
      WeatherServiceOuterClass.Notification> getSubscribeMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "subscribe",
      requestType = WeatherServiceOuterClass.SubscribeRequest.class,
      responseType = WeatherServiceOuterClass.Notification.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<WeatherServiceOuterClass.SubscribeRequest,
      WeatherServiceOuterClass.Notification> getSubscribeMethod() {
    io.grpc.MethodDescriptor<WeatherServiceOuterClass.SubscribeRequest, WeatherServiceOuterClass.Notification> getSubscribeMethod;
    if ((getSubscribeMethod = WeatherServiceGrpc.getSubscribeMethod) == null) {
      synchronized (WeatherServiceGrpc.class) {
        if ((getSubscribeMethod = WeatherServiceGrpc.getSubscribeMethod) == null) {
          WeatherServiceGrpc.getSubscribeMethod = getSubscribeMethod =
              io.grpc.MethodDescriptor.<WeatherServiceOuterClass.SubscribeRequest, WeatherServiceOuterClass.Notification>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "subscribe"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  WeatherServiceOuterClass.SubscribeRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  WeatherServiceOuterClass.Notification.getDefaultInstance()))
              .setSchemaDescriptor(new WeatherServiceMethodDescriptorSupplier("subscribe"))
              .build();
        }
      }
    }
    return getSubscribeMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static WeatherServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<WeatherServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<WeatherServiceStub>() {
        @java.lang.Override
        public WeatherServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new WeatherServiceStub(channel, callOptions);
        }
      };
    return WeatherServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static WeatherServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<WeatherServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<WeatherServiceBlockingStub>() {
        @java.lang.Override
        public WeatherServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new WeatherServiceBlockingStub(channel, callOptions);
        }
      };
    return WeatherServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static WeatherServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<WeatherServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<WeatherServiceFutureStub>() {
        @java.lang.Override
        public WeatherServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new WeatherServiceFutureStub(channel, callOptions);
        }
      };
    return WeatherServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class WeatherServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void getCities(WeatherServiceOuterClass.ValueRequest request,
        io.grpc.stub.StreamObserver<WeatherServiceOuterClass.ValueResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getGetCitiesMethod(), responseObserver);
    }

    /**
     */
    public void getFactors(WeatherServiceOuterClass.ValueRequest request,
        io.grpc.stub.StreamObserver<WeatherServiceOuterClass.ValueResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getGetFactorsMethod(), responseObserver);
    }

    /**
     */
    public void subscribe(WeatherServiceOuterClass.SubscribeRequest request,
        io.grpc.stub.StreamObserver<WeatherServiceOuterClass.Notification> responseObserver) {
      asyncUnimplementedUnaryCall(getSubscribeMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getGetCitiesMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                WeatherServiceOuterClass.ValueRequest,
                WeatherServiceOuterClass.ValueResponse>(
                  this, METHODID_GET_CITIES)))
          .addMethod(
            getGetFactorsMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                WeatherServiceOuterClass.ValueRequest,
                WeatherServiceOuterClass.ValueResponse>(
                  this, METHODID_GET_FACTORS)))
          .addMethod(
            getSubscribeMethod(),
            asyncServerStreamingCall(
              new MethodHandlers<
                WeatherServiceOuterClass.SubscribeRequest,
                WeatherServiceOuterClass.Notification>(
                  this, METHODID_SUBSCRIBE)))
          .build();
    }
  }

  /**
   */
  public static final class WeatherServiceStub extends io.grpc.stub.AbstractAsyncStub<WeatherServiceStub> {
    private WeatherServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected WeatherServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new WeatherServiceStub(channel, callOptions);
    }

    /**
     */
    public void getCities(WeatherServiceOuterClass.ValueRequest request,
        io.grpc.stub.StreamObserver<WeatherServiceOuterClass.ValueResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetCitiesMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getFactors(WeatherServiceOuterClass.ValueRequest request,
        io.grpc.stub.StreamObserver<WeatherServiceOuterClass.ValueResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetFactorsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void subscribe(WeatherServiceOuterClass.SubscribeRequest request,
        io.grpc.stub.StreamObserver<WeatherServiceOuterClass.Notification> responseObserver) {
      asyncServerStreamingCall(
          getChannel().newCall(getSubscribeMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class WeatherServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<WeatherServiceBlockingStub> {
    private WeatherServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected WeatherServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new WeatherServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public WeatherServiceOuterClass.ValueResponse getCities(WeatherServiceOuterClass.ValueRequest request) {
      return blockingUnaryCall(
          getChannel(), getGetCitiesMethod(), getCallOptions(), request);
    }

    /**
     */
    public WeatherServiceOuterClass.ValueResponse getFactors(WeatherServiceOuterClass.ValueRequest request) {
      return blockingUnaryCall(
          getChannel(), getGetFactorsMethod(), getCallOptions(), request);
    }

    /**
     */
    public java.util.Iterator<WeatherServiceOuterClass.Notification> subscribe(
        WeatherServiceOuterClass.SubscribeRequest request) {
      return blockingServerStreamingCall(
          getChannel(), getSubscribeMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class WeatherServiceFutureStub extends io.grpc.stub.AbstractFutureStub<WeatherServiceFutureStub> {
    private WeatherServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected WeatherServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new WeatherServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<WeatherServiceOuterClass.ValueResponse> getCities(
        WeatherServiceOuterClass.ValueRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGetCitiesMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<WeatherServiceOuterClass.ValueResponse> getFactors(
        WeatherServiceOuterClass.ValueRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGetFactorsMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_CITIES = 0;
  private static final int METHODID_GET_FACTORS = 1;
  private static final int METHODID_SUBSCRIBE = 2;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final WeatherServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(WeatherServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_CITIES:
          serviceImpl.getCities((WeatherServiceOuterClass.ValueRequest) request,
              (io.grpc.stub.StreamObserver<WeatherServiceOuterClass.ValueResponse>) responseObserver);
          break;
        case METHODID_GET_FACTORS:
          serviceImpl.getFactors((WeatherServiceOuterClass.ValueRequest) request,
              (io.grpc.stub.StreamObserver<WeatherServiceOuterClass.ValueResponse>) responseObserver);
          break;
        case METHODID_SUBSCRIBE:
          serviceImpl.subscribe((WeatherServiceOuterClass.SubscribeRequest) request,
              (io.grpc.stub.StreamObserver<WeatherServiceOuterClass.Notification>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class WeatherServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    WeatherServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return WeatherServiceOuterClass.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("WeatherService");
    }
  }

  private static final class WeatherServiceFileDescriptorSupplier
      extends WeatherServiceBaseDescriptorSupplier {
    WeatherServiceFileDescriptorSupplier() {}
  }

  private static final class WeatherServiceMethodDescriptorSupplier
      extends WeatherServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    WeatherServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (WeatherServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new WeatherServiceFileDescriptorSupplier())
              .addMethod(getGetCitiesMethod())
              .addMethod(getGetFactorsMethod())
              .addMethod(getSubscribeMethod())
              .build();
        }
      }
    }
    return result;
  }
}
