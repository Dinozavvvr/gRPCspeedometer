// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: WorkerRequests.proto

package ru.itis.workernode.grpc;

/**
 * Protobuf type {@code ru.itis.workernode.grpc.WorkerConfigInfo}
 */
public final class WorkerConfigInfo extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:ru.itis.workernode.grpc.WorkerConfigInfo)
    WorkerConfigInfoOrBuilder {
private static final long serialVersionUID = 0L;
  // Use WorkerConfigInfo.newBuilder() to construct.
  private WorkerConfigInfo(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private WorkerConfigInfo() {
    data_ = java.util.Collections.emptyList();
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new WorkerConfigInfo();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private WorkerConfigInfo(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    this();
    if (extensionRegistry == null) {
      throw new java.lang.NullPointerException();
    }
    int mutable_bitField0_ = 0;
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
          case 10: {
            ru.itis.workernode.grpc.RequestConfig.Builder subBuilder = null;
            if (config_ != null) {
              subBuilder = config_.toBuilder();
            }
            config_ = input.readMessage(ru.itis.workernode.grpc.RequestConfig.parser(), extensionRegistry);
            if (subBuilder != null) {
              subBuilder.mergeFrom(config_);
              config_ = subBuilder.buildPartial();
            }

            break;
          }
          case 18: {
            if (!((mutable_bitField0_ & 0x00000001) != 0)) {
              data_ = new java.util.ArrayList<ru.itis.workernode.grpc.ExampleData>();
              mutable_bitField0_ |= 0x00000001;
            }
            data_.add(
                input.readMessage(ru.itis.workernode.grpc.ExampleData.parser(), extensionRegistry));
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
      if (((mutable_bitField0_ & 0x00000001) != 0)) {
        data_ = java.util.Collections.unmodifiableList(data_);
      }
      this.unknownFields = unknownFields.build();
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return ru.itis.workernode.grpc.WorkerRequests.internal_static_ru_itis_workernode_grpc_WorkerConfigInfo_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return ru.itis.workernode.grpc.WorkerRequests.internal_static_ru_itis_workernode_grpc_WorkerConfigInfo_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            ru.itis.workernode.grpc.WorkerConfigInfo.class, ru.itis.workernode.grpc.WorkerConfigInfo.Builder.class);
  }

  public static final int CONFIG_FIELD_NUMBER = 1;
  private ru.itis.workernode.grpc.RequestConfig config_;
  /**
   * <code>.ru.itis.workernode.grpc.RequestConfig config = 1;</code>
   * @return Whether the config field is set.
   */
  @java.lang.Override
  public boolean hasConfig() {
    return config_ != null;
  }
  /**
   * <code>.ru.itis.workernode.grpc.RequestConfig config = 1;</code>
   * @return The config.
   */
  @java.lang.Override
  public ru.itis.workernode.grpc.RequestConfig getConfig() {
    return config_ == null ? ru.itis.workernode.grpc.RequestConfig.getDefaultInstance() : config_;
  }
  /**
   * <code>.ru.itis.workernode.grpc.RequestConfig config = 1;</code>
   */
  @java.lang.Override
  public ru.itis.workernode.grpc.RequestConfigOrBuilder getConfigOrBuilder() {
    return getConfig();
  }

  public static final int DATA_FIELD_NUMBER = 2;
  private java.util.List<ru.itis.workernode.grpc.ExampleData> data_;
  /**
   * <code>repeated .ru.itis.workernode.grpc.ExampleData data = 2;</code>
   */
  @java.lang.Override
  public java.util.List<ru.itis.workernode.grpc.ExampleData> getDataList() {
    return data_;
  }
  /**
   * <code>repeated .ru.itis.workernode.grpc.ExampleData data = 2;</code>
   */
  @java.lang.Override
  public java.util.List<? extends ru.itis.workernode.grpc.ExampleDataOrBuilder> 
      getDataOrBuilderList() {
    return data_;
  }
  /**
   * <code>repeated .ru.itis.workernode.grpc.ExampleData data = 2;</code>
   */
  @java.lang.Override
  public int getDataCount() {
    return data_.size();
  }
  /**
   * <code>repeated .ru.itis.workernode.grpc.ExampleData data = 2;</code>
   */
  @java.lang.Override
  public ru.itis.workernode.grpc.ExampleData getData(int index) {
    return data_.get(index);
  }
  /**
   * <code>repeated .ru.itis.workernode.grpc.ExampleData data = 2;</code>
   */
  @java.lang.Override
  public ru.itis.workernode.grpc.ExampleDataOrBuilder getDataOrBuilder(
      int index) {
    return data_.get(index);
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
    if (config_ != null) {
      output.writeMessage(1, getConfig());
    }
    for (int i = 0; i < data_.size(); i++) {
      output.writeMessage(2, data_.get(i));
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (config_ != null) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(1, getConfig());
    }
    for (int i = 0; i < data_.size(); i++) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(2, data_.get(i));
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
    if (!(obj instanceof ru.itis.workernode.grpc.WorkerConfigInfo)) {
      return super.equals(obj);
    }
    ru.itis.workernode.grpc.WorkerConfigInfo other = (ru.itis.workernode.grpc.WorkerConfigInfo) obj;

    if (hasConfig() != other.hasConfig()) return false;
    if (hasConfig()) {
      if (!getConfig()
          .equals(other.getConfig())) return false;
    }
    if (!getDataList()
        .equals(other.getDataList())) return false;
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
    if (hasConfig()) {
      hash = (37 * hash) + CONFIG_FIELD_NUMBER;
      hash = (53 * hash) + getConfig().hashCode();
    }
    if (getDataCount() > 0) {
      hash = (37 * hash) + DATA_FIELD_NUMBER;
      hash = (53 * hash) + getDataList().hashCode();
    }
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static ru.itis.workernode.grpc.WorkerConfigInfo parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static ru.itis.workernode.grpc.WorkerConfigInfo parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static ru.itis.workernode.grpc.WorkerConfigInfo parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static ru.itis.workernode.grpc.WorkerConfigInfo parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static ru.itis.workernode.grpc.WorkerConfigInfo parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static ru.itis.workernode.grpc.WorkerConfigInfo parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static ru.itis.workernode.grpc.WorkerConfigInfo parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static ru.itis.workernode.grpc.WorkerConfigInfo parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static ru.itis.workernode.grpc.WorkerConfigInfo parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static ru.itis.workernode.grpc.WorkerConfigInfo parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static ru.itis.workernode.grpc.WorkerConfigInfo parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static ru.itis.workernode.grpc.WorkerConfigInfo parseFrom(
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
  public static Builder newBuilder(ru.itis.workernode.grpc.WorkerConfigInfo prototype) {
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
   * Protobuf type {@code ru.itis.workernode.grpc.WorkerConfigInfo}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:ru.itis.workernode.grpc.WorkerConfigInfo)
      ru.itis.workernode.grpc.WorkerConfigInfoOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return ru.itis.workernode.grpc.WorkerRequests.internal_static_ru_itis_workernode_grpc_WorkerConfigInfo_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return ru.itis.workernode.grpc.WorkerRequests.internal_static_ru_itis_workernode_grpc_WorkerConfigInfo_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              ru.itis.workernode.grpc.WorkerConfigInfo.class, ru.itis.workernode.grpc.WorkerConfigInfo.Builder.class);
    }

    // Construct using ru.itis.workernode.grpc.WorkerConfigInfo.newBuilder()
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
        getDataFieldBuilder();
      }
    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      if (configBuilder_ == null) {
        config_ = null;
      } else {
        config_ = null;
        configBuilder_ = null;
      }
      if (dataBuilder_ == null) {
        data_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000001);
      } else {
        dataBuilder_.clear();
      }
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return ru.itis.workernode.grpc.WorkerRequests.internal_static_ru_itis_workernode_grpc_WorkerConfigInfo_descriptor;
    }

    @java.lang.Override
    public ru.itis.workernode.grpc.WorkerConfigInfo getDefaultInstanceForType() {
      return ru.itis.workernode.grpc.WorkerConfigInfo.getDefaultInstance();
    }

    @java.lang.Override
    public ru.itis.workernode.grpc.WorkerConfigInfo build() {
      ru.itis.workernode.grpc.WorkerConfigInfo result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public ru.itis.workernode.grpc.WorkerConfigInfo buildPartial() {
      ru.itis.workernode.grpc.WorkerConfigInfo result = new ru.itis.workernode.grpc.WorkerConfigInfo(this);
      int from_bitField0_ = bitField0_;
      if (configBuilder_ == null) {
        result.config_ = config_;
      } else {
        result.config_ = configBuilder_.build();
      }
      if (dataBuilder_ == null) {
        if (((bitField0_ & 0x00000001) != 0)) {
          data_ = java.util.Collections.unmodifiableList(data_);
          bitField0_ = (bitField0_ & ~0x00000001);
        }
        result.data_ = data_;
      } else {
        result.data_ = dataBuilder_.build();
      }
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
      if (other instanceof ru.itis.workernode.grpc.WorkerConfigInfo) {
        return mergeFrom((ru.itis.workernode.grpc.WorkerConfigInfo)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(ru.itis.workernode.grpc.WorkerConfigInfo other) {
      if (other == ru.itis.workernode.grpc.WorkerConfigInfo.getDefaultInstance()) return this;
      if (other.hasConfig()) {
        mergeConfig(other.getConfig());
      }
      if (dataBuilder_ == null) {
        if (!other.data_.isEmpty()) {
          if (data_.isEmpty()) {
            data_ = other.data_;
            bitField0_ = (bitField0_ & ~0x00000001);
          } else {
            ensureDataIsMutable();
            data_.addAll(other.data_);
          }
          onChanged();
        }
      } else {
        if (!other.data_.isEmpty()) {
          if (dataBuilder_.isEmpty()) {
            dataBuilder_.dispose();
            dataBuilder_ = null;
            data_ = other.data_;
            bitField0_ = (bitField0_ & ~0x00000001);
            dataBuilder_ = 
              com.google.protobuf.GeneratedMessageV3.alwaysUseFieldBuilders ?
                 getDataFieldBuilder() : null;
          } else {
            dataBuilder_.addAllMessages(other.data_);
          }
        }
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
      ru.itis.workernode.grpc.WorkerConfigInfo parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (ru.itis.workernode.grpc.WorkerConfigInfo) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }
    private int bitField0_;

    private ru.itis.workernode.grpc.RequestConfig config_;
    private com.google.protobuf.SingleFieldBuilderV3<
        ru.itis.workernode.grpc.RequestConfig, ru.itis.workernode.grpc.RequestConfig.Builder, ru.itis.workernode.grpc.RequestConfigOrBuilder> configBuilder_;
    /**
     * <code>.ru.itis.workernode.grpc.RequestConfig config = 1;</code>
     * @return Whether the config field is set.
     */
    public boolean hasConfig() {
      return configBuilder_ != null || config_ != null;
    }
    /**
     * <code>.ru.itis.workernode.grpc.RequestConfig config = 1;</code>
     * @return The config.
     */
    public ru.itis.workernode.grpc.RequestConfig getConfig() {
      if (configBuilder_ == null) {
        return config_ == null ? ru.itis.workernode.grpc.RequestConfig.getDefaultInstance() : config_;
      } else {
        return configBuilder_.getMessage();
      }
    }
    /**
     * <code>.ru.itis.workernode.grpc.RequestConfig config = 1;</code>
     */
    public Builder setConfig(ru.itis.workernode.grpc.RequestConfig value) {
      if (configBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        config_ = value;
        onChanged();
      } else {
        configBuilder_.setMessage(value);
      }

      return this;
    }
    /**
     * <code>.ru.itis.workernode.grpc.RequestConfig config = 1;</code>
     */
    public Builder setConfig(
        ru.itis.workernode.grpc.RequestConfig.Builder builderForValue) {
      if (configBuilder_ == null) {
        config_ = builderForValue.build();
        onChanged();
      } else {
        configBuilder_.setMessage(builderForValue.build());
      }

      return this;
    }
    /**
     * <code>.ru.itis.workernode.grpc.RequestConfig config = 1;</code>
     */
    public Builder mergeConfig(ru.itis.workernode.grpc.RequestConfig value) {
      if (configBuilder_ == null) {
        if (config_ != null) {
          config_ =
            ru.itis.workernode.grpc.RequestConfig.newBuilder(config_).mergeFrom(value).buildPartial();
        } else {
          config_ = value;
        }
        onChanged();
      } else {
        configBuilder_.mergeFrom(value);
      }

      return this;
    }
    /**
     * <code>.ru.itis.workernode.grpc.RequestConfig config = 1;</code>
     */
    public Builder clearConfig() {
      if (configBuilder_ == null) {
        config_ = null;
        onChanged();
      } else {
        config_ = null;
        configBuilder_ = null;
      }

      return this;
    }
    /**
     * <code>.ru.itis.workernode.grpc.RequestConfig config = 1;</code>
     */
    public ru.itis.workernode.grpc.RequestConfig.Builder getConfigBuilder() {
      
      onChanged();
      return getConfigFieldBuilder().getBuilder();
    }
    /**
     * <code>.ru.itis.workernode.grpc.RequestConfig config = 1;</code>
     */
    public ru.itis.workernode.grpc.RequestConfigOrBuilder getConfigOrBuilder() {
      if (configBuilder_ != null) {
        return configBuilder_.getMessageOrBuilder();
      } else {
        return config_ == null ?
            ru.itis.workernode.grpc.RequestConfig.getDefaultInstance() : config_;
      }
    }
    /**
     * <code>.ru.itis.workernode.grpc.RequestConfig config = 1;</code>
     */
    private com.google.protobuf.SingleFieldBuilderV3<
        ru.itis.workernode.grpc.RequestConfig, ru.itis.workernode.grpc.RequestConfig.Builder, ru.itis.workernode.grpc.RequestConfigOrBuilder> 
        getConfigFieldBuilder() {
      if (configBuilder_ == null) {
        configBuilder_ = new com.google.protobuf.SingleFieldBuilderV3<
            ru.itis.workernode.grpc.RequestConfig, ru.itis.workernode.grpc.RequestConfig.Builder, ru.itis.workernode.grpc.RequestConfigOrBuilder>(
                getConfig(),
                getParentForChildren(),
                isClean());
        config_ = null;
      }
      return configBuilder_;
    }

    private java.util.List<ru.itis.workernode.grpc.ExampleData> data_ =
      java.util.Collections.emptyList();
    private void ensureDataIsMutable() {
      if (!((bitField0_ & 0x00000001) != 0)) {
        data_ = new java.util.ArrayList<ru.itis.workernode.grpc.ExampleData>(data_);
        bitField0_ |= 0x00000001;
       }
    }

    private com.google.protobuf.RepeatedFieldBuilderV3<
        ru.itis.workernode.grpc.ExampleData, ru.itis.workernode.grpc.ExampleData.Builder, ru.itis.workernode.grpc.ExampleDataOrBuilder> dataBuilder_;

    /**
     * <code>repeated .ru.itis.workernode.grpc.ExampleData data = 2;</code>
     */
    public java.util.List<ru.itis.workernode.grpc.ExampleData> getDataList() {
      if (dataBuilder_ == null) {
        return java.util.Collections.unmodifiableList(data_);
      } else {
        return dataBuilder_.getMessageList();
      }
    }
    /**
     * <code>repeated .ru.itis.workernode.grpc.ExampleData data = 2;</code>
     */
    public int getDataCount() {
      if (dataBuilder_ == null) {
        return data_.size();
      } else {
        return dataBuilder_.getCount();
      }
    }
    /**
     * <code>repeated .ru.itis.workernode.grpc.ExampleData data = 2;</code>
     */
    public ru.itis.workernode.grpc.ExampleData getData(int index) {
      if (dataBuilder_ == null) {
        return data_.get(index);
      } else {
        return dataBuilder_.getMessage(index);
      }
    }
    /**
     * <code>repeated .ru.itis.workernode.grpc.ExampleData data = 2;</code>
     */
    public Builder setData(
        int index, ru.itis.workernode.grpc.ExampleData value) {
      if (dataBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureDataIsMutable();
        data_.set(index, value);
        onChanged();
      } else {
        dataBuilder_.setMessage(index, value);
      }
      return this;
    }
    /**
     * <code>repeated .ru.itis.workernode.grpc.ExampleData data = 2;</code>
     */
    public Builder setData(
        int index, ru.itis.workernode.grpc.ExampleData.Builder builderForValue) {
      if (dataBuilder_ == null) {
        ensureDataIsMutable();
        data_.set(index, builderForValue.build());
        onChanged();
      } else {
        dataBuilder_.setMessage(index, builderForValue.build());
      }
      return this;
    }
    /**
     * <code>repeated .ru.itis.workernode.grpc.ExampleData data = 2;</code>
     */
    public Builder addData(ru.itis.workernode.grpc.ExampleData value) {
      if (dataBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureDataIsMutable();
        data_.add(value);
        onChanged();
      } else {
        dataBuilder_.addMessage(value);
      }
      return this;
    }
    /**
     * <code>repeated .ru.itis.workernode.grpc.ExampleData data = 2;</code>
     */
    public Builder addData(
        int index, ru.itis.workernode.grpc.ExampleData value) {
      if (dataBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureDataIsMutable();
        data_.add(index, value);
        onChanged();
      } else {
        dataBuilder_.addMessage(index, value);
      }
      return this;
    }
    /**
     * <code>repeated .ru.itis.workernode.grpc.ExampleData data = 2;</code>
     */
    public Builder addData(
        ru.itis.workernode.grpc.ExampleData.Builder builderForValue) {
      if (dataBuilder_ == null) {
        ensureDataIsMutable();
        data_.add(builderForValue.build());
        onChanged();
      } else {
        dataBuilder_.addMessage(builderForValue.build());
      }
      return this;
    }
    /**
     * <code>repeated .ru.itis.workernode.grpc.ExampleData data = 2;</code>
     */
    public Builder addData(
        int index, ru.itis.workernode.grpc.ExampleData.Builder builderForValue) {
      if (dataBuilder_ == null) {
        ensureDataIsMutable();
        data_.add(index, builderForValue.build());
        onChanged();
      } else {
        dataBuilder_.addMessage(index, builderForValue.build());
      }
      return this;
    }
    /**
     * <code>repeated .ru.itis.workernode.grpc.ExampleData data = 2;</code>
     */
    public Builder addAllData(
        java.lang.Iterable<? extends ru.itis.workernode.grpc.ExampleData> values) {
      if (dataBuilder_ == null) {
        ensureDataIsMutable();
        com.google.protobuf.AbstractMessageLite.Builder.addAll(
            values, data_);
        onChanged();
      } else {
        dataBuilder_.addAllMessages(values);
      }
      return this;
    }
    /**
     * <code>repeated .ru.itis.workernode.grpc.ExampleData data = 2;</code>
     */
    public Builder clearData() {
      if (dataBuilder_ == null) {
        data_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000001);
        onChanged();
      } else {
        dataBuilder_.clear();
      }
      return this;
    }
    /**
     * <code>repeated .ru.itis.workernode.grpc.ExampleData data = 2;</code>
     */
    public Builder removeData(int index) {
      if (dataBuilder_ == null) {
        ensureDataIsMutable();
        data_.remove(index);
        onChanged();
      } else {
        dataBuilder_.remove(index);
      }
      return this;
    }
    /**
     * <code>repeated .ru.itis.workernode.grpc.ExampleData data = 2;</code>
     */
    public ru.itis.workernode.grpc.ExampleData.Builder getDataBuilder(
        int index) {
      return getDataFieldBuilder().getBuilder(index);
    }
    /**
     * <code>repeated .ru.itis.workernode.grpc.ExampleData data = 2;</code>
     */
    public ru.itis.workernode.grpc.ExampleDataOrBuilder getDataOrBuilder(
        int index) {
      if (dataBuilder_ == null) {
        return data_.get(index);  } else {
        return dataBuilder_.getMessageOrBuilder(index);
      }
    }
    /**
     * <code>repeated .ru.itis.workernode.grpc.ExampleData data = 2;</code>
     */
    public java.util.List<? extends ru.itis.workernode.grpc.ExampleDataOrBuilder> 
         getDataOrBuilderList() {
      if (dataBuilder_ != null) {
        return dataBuilder_.getMessageOrBuilderList();
      } else {
        return java.util.Collections.unmodifiableList(data_);
      }
    }
    /**
     * <code>repeated .ru.itis.workernode.grpc.ExampleData data = 2;</code>
     */
    public ru.itis.workernode.grpc.ExampleData.Builder addDataBuilder() {
      return getDataFieldBuilder().addBuilder(
          ru.itis.workernode.grpc.ExampleData.getDefaultInstance());
    }
    /**
     * <code>repeated .ru.itis.workernode.grpc.ExampleData data = 2;</code>
     */
    public ru.itis.workernode.grpc.ExampleData.Builder addDataBuilder(
        int index) {
      return getDataFieldBuilder().addBuilder(
          index, ru.itis.workernode.grpc.ExampleData.getDefaultInstance());
    }
    /**
     * <code>repeated .ru.itis.workernode.grpc.ExampleData data = 2;</code>
     */
    public java.util.List<ru.itis.workernode.grpc.ExampleData.Builder> 
         getDataBuilderList() {
      return getDataFieldBuilder().getBuilderList();
    }
    private com.google.protobuf.RepeatedFieldBuilderV3<
        ru.itis.workernode.grpc.ExampleData, ru.itis.workernode.grpc.ExampleData.Builder, ru.itis.workernode.grpc.ExampleDataOrBuilder> 
        getDataFieldBuilder() {
      if (dataBuilder_ == null) {
        dataBuilder_ = new com.google.protobuf.RepeatedFieldBuilderV3<
            ru.itis.workernode.grpc.ExampleData, ru.itis.workernode.grpc.ExampleData.Builder, ru.itis.workernode.grpc.ExampleDataOrBuilder>(
                data_,
                ((bitField0_ & 0x00000001) != 0),
                getParentForChildren(),
                isClean());
        data_ = null;
      }
      return dataBuilder_;
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


    // @@protoc_insertion_point(builder_scope:ru.itis.workernode.grpc.WorkerConfigInfo)
  }

  // @@protoc_insertion_point(class_scope:ru.itis.workernode.grpc.WorkerConfigInfo)
  private static final ru.itis.workernode.grpc.WorkerConfigInfo DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new ru.itis.workernode.grpc.WorkerConfigInfo();
  }

  public static ru.itis.workernode.grpc.WorkerConfigInfo getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<WorkerConfigInfo>
      PARSER = new com.google.protobuf.AbstractParser<WorkerConfigInfo>() {
    @java.lang.Override
    public WorkerConfigInfo parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new WorkerConfigInfo(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<WorkerConfigInfo> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<WorkerConfigInfo> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public ru.itis.workernode.grpc.WorkerConfigInfo getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

