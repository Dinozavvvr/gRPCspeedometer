// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: WorkerRequests.proto

package ru.itis.workernode.grpc;

/**
 * Protobuf type {@code ru.itis.workernode.grpc.RequestConfig}
 */
public final class RequestConfig extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:ru.itis.workernode.grpc.RequestConfig)
    RequestConfigOrBuilder {
private static final long serialVersionUID = 0L;
  // Use RequestConfig.newBuilder() to construct.
  private RequestConfig(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private RequestConfig() {
    flowType_ = 0;
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new RequestConfig();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private RequestConfig(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    this();
    if (extensionRegistry == null) {
      throw new java.lang.NullPointerException();
    }
    com.google.protobuf.UnknownFieldSet.Builder unknownFields =
        com.google.protobuf.UnknownFieldSet.newBuilder();
    try {
      boolean done = false;
      while (!done) {
        int tag = input.readTag();
        switch (tag) {
          case 0:
            done = true;
            break;
          case 8: {

            depthLevel_ = input.readInt32();
            break;
          }
          case 16: {

            dataSize_ = input.readInt32();
            break;
          }
          case 24: {
            int rawValue = input.readEnum();

            flowType_ = rawValue;
            break;
          }
          default: {
            if (!parseUnknownField(
                input, unknownFields, extensionRegistry, tag)) {
              done = true;
            }
            break;
          }
        }
      }
    } catch (com.google.protobuf.InvalidProtocolBufferException e) {
      throw e.setUnfinishedMessage(this);
    } catch (java.io.IOException e) {
      throw new com.google.protobuf.InvalidProtocolBufferException(
          e).setUnfinishedMessage(this);
    } finally {
      this.unknownFields = unknownFields.build();
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return ru.itis.workernode.grpc.WorkerRequests.internal_static_ru_itis_workernode_grpc_RequestConfig_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return ru.itis.workernode.grpc.WorkerRequests.internal_static_ru_itis_workernode_grpc_RequestConfig_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            ru.itis.workernode.grpc.RequestConfig.class, ru.itis.workernode.grpc.RequestConfig.Builder.class);
  }

  public static final int DEPTHLEVEL_FIELD_NUMBER = 1;
  private int depthLevel_;
  /**
   * <code>int32 depthLevel = 1;</code>
   * @return The depthLevel.
   */
  @java.lang.Override
  public int getDepthLevel() {
    return depthLevel_;
  }

  public static final int DATASIZE_FIELD_NUMBER = 2;
  private int dataSize_;
  /**
   * <code>int32 dataSize = 2;</code>
   * @return The dataSize.
   */
  @java.lang.Override
  public int getDataSize() {
    return dataSize_;
  }

  public static final int FLOWTYPE_FIELD_NUMBER = 3;
  private int flowType_;
  /**
   * <code>.ru.itis.workernode.grpc.FlowType flowType = 3;</code>
   * @return The enum numeric value on the wire for flowType.
   */
  @java.lang.Override public int getFlowTypeValue() {
    return flowType_;
  }
  /**
   * <code>.ru.itis.workernode.grpc.FlowType flowType = 3;</code>
   * @return The flowType.
   */
  @java.lang.Override public ru.itis.workernode.grpc.FlowType getFlowType() {
    @SuppressWarnings("deprecation")
    ru.itis.workernode.grpc.FlowType result = ru.itis.workernode.grpc.FlowType.valueOf(flowType_);
    return result == null ? ru.itis.workernode.grpc.FlowType.UNRECOGNIZED : result;
  }

  private byte memoizedIsInitialized = -1;
  @java.lang.Override
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  @java.lang.Override
  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    if (depthLevel_ != 0) {
      output.writeInt32(1, depthLevel_);
    }
    if (dataSize_ != 0) {
      output.writeInt32(2, dataSize_);
    }
    if (flowType_ != ru.itis.workernode.grpc.FlowType.WATERFALL.getNumber()) {
      output.writeEnum(3, flowType_);
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (depthLevel_ != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt32Size(1, depthLevel_);
    }
    if (dataSize_ != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt32Size(2, dataSize_);
    }
    if (flowType_ != ru.itis.workernode.grpc.FlowType.WATERFALL.getNumber()) {
      size += com.google.protobuf.CodedOutputStream
        .computeEnumSize(3, flowType_);
    }
    size += unknownFields.getSerializedSize();
    memoizedSize = size;
    return size;
  }

  @java.lang.Override
  public boolean equals(final java.lang.Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof ru.itis.workernode.grpc.RequestConfig)) {
      return super.equals(obj);
    }
    ru.itis.workernode.grpc.RequestConfig other = (ru.itis.workernode.grpc.RequestConfig) obj;

    if (getDepthLevel()
        != other.getDepthLevel()) return false;
    if (getDataSize()
        != other.getDataSize()) return false;
    if (flowType_ != other.flowType_) return false;
    if (!unknownFields.equals(other.unknownFields)) return false;
    return true;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    hash = (37 * hash) + DEPTHLEVEL_FIELD_NUMBER;
    hash = (53 * hash) + getDepthLevel();
    hash = (37 * hash) + DATASIZE_FIELD_NUMBER;
    hash = (53 * hash) + getDataSize();
    hash = (37 * hash) + FLOWTYPE_FIELD_NUMBER;
    hash = (53 * hash) + flowType_;
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static ru.itis.workernode.grpc.RequestConfig parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static ru.itis.workernode.grpc.RequestConfig parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static ru.itis.workernode.grpc.RequestConfig parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static ru.itis.workernode.grpc.RequestConfig parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static ru.itis.workernode.grpc.RequestConfig parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static ru.itis.workernode.grpc.RequestConfig parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static ru.itis.workernode.grpc.RequestConfig parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static ru.itis.workernode.grpc.RequestConfig parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static ru.itis.workernode.grpc.RequestConfig parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static ru.itis.workernode.grpc.RequestConfig parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static ru.itis.workernode.grpc.RequestConfig parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static ru.itis.workernode.grpc.RequestConfig parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  @java.lang.Override
  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(ru.itis.workernode.grpc.RequestConfig prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  @java.lang.Override
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @java.lang.Override
  protected Builder newBuilderForType(
      com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * Protobuf type {@code ru.itis.workernode.grpc.RequestConfig}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:ru.itis.workernode.grpc.RequestConfig)
      ru.itis.workernode.grpc.RequestConfigOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return ru.itis.workernode.grpc.WorkerRequests.internal_static_ru_itis_workernode_grpc_RequestConfig_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return ru.itis.workernode.grpc.WorkerRequests.internal_static_ru_itis_workernode_grpc_RequestConfig_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              ru.itis.workernode.grpc.RequestConfig.class, ru.itis.workernode.grpc.RequestConfig.Builder.class);
    }

    // Construct using ru.itis.workernode.grpc.RequestConfig.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private Builder(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }
    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessageV3
              .alwaysUseFieldBuilders) {
      }
    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      depthLevel_ = 0;

      dataSize_ = 0;

      flowType_ = 0;

      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return ru.itis.workernode.grpc.WorkerRequests.internal_static_ru_itis_workernode_grpc_RequestConfig_descriptor;
    }

    @java.lang.Override
    public ru.itis.workernode.grpc.RequestConfig getDefaultInstanceForType() {
      return ru.itis.workernode.grpc.RequestConfig.getDefaultInstance();
    }

    @java.lang.Override
    public ru.itis.workernode.grpc.RequestConfig build() {
      ru.itis.workernode.grpc.RequestConfig result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public ru.itis.workernode.grpc.RequestConfig buildPartial() {
      ru.itis.workernode.grpc.RequestConfig result = new ru.itis.workernode.grpc.RequestConfig(this);
      result.depthLevel_ = depthLevel_;
      result.dataSize_ = dataSize_;
      result.flowType_ = flowType_;
      onBuilt();
      return result;
    }

    @java.lang.Override
    public Builder clone() {
      return super.clone();
    }
    @java.lang.Override
    public Builder setField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.setField(field, value);
    }
    @java.lang.Override
    public Builder clearField(
        com.google.protobuf.Descriptors.FieldDescriptor field) {
      return super.clearField(field);
    }
    @java.lang.Override
    public Builder clearOneof(
        com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return super.clearOneof(oneof);
    }
    @java.lang.Override
    public Builder setRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        int index, java.lang.Object value) {
      return super.setRepeatedField(field, index, value);
    }
    @java.lang.Override
    public Builder addRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.addRepeatedField(field, value);
    }
    @java.lang.Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof ru.itis.workernode.grpc.RequestConfig) {
        return mergeFrom((ru.itis.workernode.grpc.RequestConfig)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(ru.itis.workernode.grpc.RequestConfig other) {
      if (other == ru.itis.workernode.grpc.RequestConfig.getDefaultInstance()) return this;
      if (other.getDepthLevel() != 0) {
        setDepthLevel(other.getDepthLevel());
      }
      if (other.getDataSize() != 0) {
        setDataSize(other.getDataSize());
      }
      if (other.flowType_ != 0) {
        setFlowTypeValue(other.getFlowTypeValue());
      }
      this.mergeUnknownFields(other.unknownFields);
      onChanged();
      return this;
    }

    @java.lang.Override
    public final boolean isInitialized() {
      return true;
    }

    @java.lang.Override
    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      ru.itis.workernode.grpc.RequestConfig parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (ru.itis.workernode.grpc.RequestConfig) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private int depthLevel_ ;
    /**
     * <code>int32 depthLevel = 1;</code>
     * @return The depthLevel.
     */
    @java.lang.Override
    public int getDepthLevel() {
      return depthLevel_;
    }
    /**
     * <code>int32 depthLevel = 1;</code>
     * @param value The depthLevel to set.
     * @return This builder for chaining.
     */
    public Builder setDepthLevel(int value) {
      
      depthLevel_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>int32 depthLevel = 1;</code>
     * @return This builder for chaining.
     */
    public Builder clearDepthLevel() {
      
      depthLevel_ = 0;
      onChanged();
      return this;
    }

    private int dataSize_ ;
    /**
     * <code>int32 dataSize = 2;</code>
     * @return The dataSize.
     */
    @java.lang.Override
    public int getDataSize() {
      return dataSize_;
    }
    /**
     * <code>int32 dataSize = 2;</code>
     * @param value The dataSize to set.
     * @return This builder for chaining.
     */
    public Builder setDataSize(int value) {
      
      dataSize_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>int32 dataSize = 2;</code>
     * @return This builder for chaining.
     */
    public Builder clearDataSize() {
      
      dataSize_ = 0;
      onChanged();
      return this;
    }

    private int flowType_ = 0;
    /**
     * <code>.ru.itis.workernode.grpc.FlowType flowType = 3;</code>
     * @return The enum numeric value on the wire for flowType.
     */
    @java.lang.Override public int getFlowTypeValue() {
      return flowType_;
    }
    /**
     * <code>.ru.itis.workernode.grpc.FlowType flowType = 3;</code>
     * @param value The enum numeric value on the wire for flowType to set.
     * @return This builder for chaining.
     */
    public Builder setFlowTypeValue(int value) {
      
      flowType_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>.ru.itis.workernode.grpc.FlowType flowType = 3;</code>
     * @return The flowType.
     */
    @java.lang.Override
    public ru.itis.workernode.grpc.FlowType getFlowType() {
      @SuppressWarnings("deprecation")
      ru.itis.workernode.grpc.FlowType result = ru.itis.workernode.grpc.FlowType.valueOf(flowType_);
      return result == null ? ru.itis.workernode.grpc.FlowType.UNRECOGNIZED : result;
    }
    /**
     * <code>.ru.itis.workernode.grpc.FlowType flowType = 3;</code>
     * @param value The flowType to set.
     * @return This builder for chaining.
     */
    public Builder setFlowType(ru.itis.workernode.grpc.FlowType value) {
      if (value == null) {
        throw new NullPointerException();
      }
      
      flowType_ = value.getNumber();
      onChanged();
      return this;
    }
    /**
     * <code>.ru.itis.workernode.grpc.FlowType flowType = 3;</code>
     * @return This builder for chaining.
     */
    public Builder clearFlowType() {
      
      flowType_ = 0;
      onChanged();
      return this;
    }
    @java.lang.Override
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.setUnknownFields(unknownFields);
    }

    @java.lang.Override
    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.mergeUnknownFields(unknownFields);
    }


    // @@protoc_insertion_point(builder_scope:ru.itis.workernode.grpc.RequestConfig)
  }

  // @@protoc_insertion_point(class_scope:ru.itis.workernode.grpc.RequestConfig)
  private static final ru.itis.workernode.grpc.RequestConfig DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new ru.itis.workernode.grpc.RequestConfig();
  }

  public static ru.itis.workernode.grpc.RequestConfig getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<RequestConfig>
      PARSER = new com.google.protobuf.AbstractParser<RequestConfig>() {
    @java.lang.Override
    public RequestConfig parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new RequestConfig(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<RequestConfig> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<RequestConfig> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public ru.itis.workernode.grpc.RequestConfig getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

