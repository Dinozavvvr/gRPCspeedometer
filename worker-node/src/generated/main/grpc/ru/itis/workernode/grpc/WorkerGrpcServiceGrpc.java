package ru.itis.workernode.grpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.42.1)",
    comments = "Source: WorkerRequests.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class WorkerGrpcServiceGrpc {

  private WorkerGrpcServiceGrpc() {}

  public static final String SERVICE_NAME = "ru.itis.workernode.grpc.WorkerGrpcService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<ru.itis.workernode.grpc.WorkerConfigInfo,
      ru.itis.workernode.grpc.WorkerConfigInfo> getTestPostDataMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "testPostData",
      requestType = ru.itis.workernode.grpc.WorkerConfigInfo.class,
      responseType = ru.itis.workernode.grpc.WorkerConfigInfo.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<ru.itis.workernode.grpc.WorkerConfigInfo,
      ru.itis.workernode.grpc.WorkerConfigInfo> getTestPostDataMethod() {
    io.grpc.MethodDescriptor<ru.itis.workernode.grpc.WorkerConfigInfo, ru.itis.workernode.grpc.WorkerConfigInfo> getTestPostDataMethod;
    if ((getTestPostDataMethod = WorkerGrpcServiceGrpc.getTestPostDataMethod) == null) {
      synchronized (WorkerGrpcServiceGrpc.class) {
        if ((getTestPostDataMethod = WorkerGrpcServiceGrpc.getTestPostDataMethod) == null) {
          WorkerGrpcServiceGrpc.getTestPostDataMethod = getTestPostDataMethod =
              io.grpc.MethodDescriptor.<ru.itis.workernode.grpc.WorkerConfigInfo, ru.itis.workernode.grpc.WorkerConfigInfo>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "testPostData"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ru.itis.workernode.grpc.WorkerConfigInfo.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ru.itis.workernode.grpc.WorkerConfigInfo.getDefaultInstance()))
              .setSchemaDescriptor(new WorkerGrpcServiceMethodDescriptorSupplier("testPostData"))
              .build();
        }
      }
    }
    return getTestPostDataMethod;
  }

  private static volatile io.grpc.MethodDescriptor<ru.itis.workernode.grpc.WorkerConfigInfo,
      ru.itis.workernode.grpc.WorkerConfigInfo> getTestGetDataMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "testGetData",
      requestType = ru.itis.workernode.grpc.WorkerConfigInfo.class,
      responseType = ru.itis.workernode.grpc.WorkerConfigInfo.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<ru.itis.workernode.grpc.WorkerConfigInfo,
      ru.itis.workernode.grpc.WorkerConfigInfo> getTestGetDataMethod() {
    io.grpc.MethodDescriptor<ru.itis.workernode.grpc.WorkerConfigInfo, ru.itis.workernode.grpc.WorkerConfigInfo> getTestGetDataMethod;
    if ((getTestGetDataMethod = WorkerGrpcServiceGrpc.getTestGetDataMethod) == null) {
      synchronized (WorkerGrpcServiceGrpc.class) {
        if ((getTestGetDataMethod = WorkerGrpcServiceGrpc.getTestGetDataMethod) == null) {
          WorkerGrpcServiceGrpc.getTestGetDataMethod = getTestGetDataMethod =
              io.grpc.MethodDescriptor.<ru.itis.workernode.grpc.WorkerConfigInfo, ru.itis.workernode.grpc.WorkerConfigInfo>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "testGetData"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ru.itis.workernode.grpc.WorkerConfigInfo.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ru.itis.workernode.grpc.WorkerConfigInfo.getDefaultInstance()))
              .setSchemaDescriptor(new WorkerGrpcServiceMethodDescriptorSupplier("testGetData"))
              .build();
        }
      }
    }
    return getTestGetDataMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static WorkerGrpcServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<WorkerGrpcServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<WorkerGrpcServiceStub>() {
        @java.lang.Override
        public WorkerGrpcServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new WorkerGrpcServiceStub(channel, callOptions);
        }
      };
    return WorkerGrpcServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static WorkerGrpcServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<WorkerGrpcServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<WorkerGrpcServiceBlockingStub>() {
        @java.lang.Override
        public WorkerGrpcServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new WorkerGrpcServiceBlockingStub(channel, callOptions);
        }
      };
    return WorkerGrpcServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static WorkerGrpcServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<WorkerGrpcServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<WorkerGrpcServiceFutureStub>() {
        @java.lang.Override
        public WorkerGrpcServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new WorkerGrpcServiceFutureStub(channel, callOptions);
        }
      };
    return WorkerGrpcServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class WorkerGrpcServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void testPostData(ru.itis.workernode.grpc.WorkerConfigInfo request,
        io.grpc.stub.StreamObserver<ru.itis.workernode.grpc.WorkerConfigInfo> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getTestPostDataMethod(), responseObserver);
    }

    /**
     */
    public void testGetData(ru.itis.workernode.grpc.WorkerConfigInfo request,
        io.grpc.stub.StreamObserver<ru.itis.workernode.grpc.WorkerConfigInfo> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getTestGetDataMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getTestPostDataMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                ru.itis.workernode.grpc.WorkerConfigInfo,
                ru.itis.workernode.grpc.WorkerConfigInfo>(
                  this, METHODID_TEST_POST_DATA)))
          .addMethod(
            getTestGetDataMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                ru.itis.workernode.grpc.WorkerConfigInfo,
                ru.itis.workernode.grpc.WorkerConfigInfo>(
                  this, METHODID_TEST_GET_DATA)))
          .build();
    }
  }

  /**
   */
  public static final class WorkerGrpcServiceStub extends io.grpc.stub.AbstractAsyncStub<WorkerGrpcServiceStub> {
    private WorkerGrpcServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected WorkerGrpcServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new WorkerGrpcServiceStub(channel, callOptions);
    }

    /**
     */
    public void testPostData(ru.itis.workernode.grpc.WorkerConfigInfo request,
        io.grpc.stub.StreamObserver<ru.itis.workernode.grpc.WorkerConfigInfo> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getTestPostDataMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void testGetData(ru.itis.workernode.grpc.WorkerConfigInfo request,
        io.grpc.stub.StreamObserver<ru.itis.workernode.grpc.WorkerConfigInfo> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getTestGetDataMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class WorkerGrpcServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<WorkerGrpcServiceBlockingStub> {
    private WorkerGrpcServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected WorkerGrpcServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new WorkerGrpcServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public ru.itis.workernode.grpc.WorkerConfigInfo testPostData(ru.itis.workernode.grpc.WorkerConfigInfo request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getTestPostDataMethod(), getCallOptions(), request);
    }

    /**
     */
    public ru.itis.workernode.grpc.WorkerConfigInfo testGetData(ru.itis.workernode.grpc.WorkerConfigInfo request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getTestGetDataMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class WorkerGrpcServiceFutureStub extends io.grpc.stub.AbstractFutureStub<WorkerGrpcServiceFutureStub> {
    private WorkerGrpcServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected WorkerGrpcServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new WorkerGrpcServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<ru.itis.workernode.grpc.WorkerConfigInfo> testPostData(
        ru.itis.workernode.grpc.WorkerConfigInfo request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getTestPostDataMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<ru.itis.workernode.grpc.WorkerConfigInfo> testGetData(
        ru.itis.workernode.grpc.WorkerConfigInfo request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getTestGetDataMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_TEST_POST_DATA = 0;
  private static final int METHODID_TEST_GET_DATA = 1;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final WorkerGrpcServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(WorkerGrpcServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_TEST_POST_DATA:
          serviceImpl.testPostData((ru.itis.workernode.grpc.WorkerConfigInfo) request,
              (io.grpc.stub.StreamObserver<ru.itis.workernode.grpc.WorkerConfigInfo>) responseObserver);
          break;
        case METHODID_TEST_GET_DATA:
          serviceImpl.testGetData((ru.itis.workernode.grpc.WorkerConfigInfo) request,
              (io.grpc.stub.StreamObserver<ru.itis.workernode.grpc.WorkerConfigInfo>) responseObserver);
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

  private static abstract class WorkerGrpcServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    WorkerGrpcServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return ru.itis.workernode.grpc.WorkerRequests.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("WorkerGrpcService");
    }
  }

  private static final class WorkerGrpcServiceFileDescriptorSupplier
      extends WorkerGrpcServiceBaseDescriptorSupplier {
    WorkerGrpcServiceFileDescriptorSupplier() {}
  }

  private static final class WorkerGrpcServiceMethodDescriptorSupplier
      extends WorkerGrpcServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    WorkerGrpcServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (WorkerGrpcServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new WorkerGrpcServiceFileDescriptorSupplier())
              .addMethod(getTestPostDataMethod())
              .addMethod(getTestGetDataMethod())
              .build();
        }
      }
    }
    return result;
  }
}
