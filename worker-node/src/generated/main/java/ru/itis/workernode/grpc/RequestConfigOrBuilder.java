// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: WorkerRequests.proto

package ru.itis.workernode.grpc;

public interface RequestConfigOrBuilder extends
    // @@protoc_insertion_point(interface_extends:ru.itis.workernode.grpc.RequestConfig)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>int32 depthLevel = 1;</code>
   * @return The depthLevel.
   */
  int getDepthLevel();

  /**
   * <code>int32 dataSize = 2;</code>
   * @return The dataSize.
   */
  int getDataSize();

  /**
   * <code>.ru.itis.workernode.grpc.FlowType flowType = 3;</code>
   * @return The enum numeric value on the wire for flowType.
   */
  int getFlowTypeValue();
  /**
   * <code>.ru.itis.workernode.grpc.FlowType flowType = 3;</code>
   * @return The flowType.
   */
  ru.itis.workernode.grpc.FlowType getFlowType();
}
