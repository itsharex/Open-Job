// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: ClientService.proto

package org.open.job.core.grpc.proto;

public final class ClientServiceProto {
  private ClientServiceProto() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_com_pro_message_pipe_core_grpc_ClientRegisterRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_com_pro_message_pipe_core_grpc_ClientRegisterRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_com_pro_message_pipe_core_grpc_ClientHeartBeatRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_com_pro_message_pipe_core_grpc_ClientHeartBeatRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_com_pro_message_pipe_core_grpc_ClientResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_com_pro_message_pipe_core_grpc_ClientResponse_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    String[] descriptorData = {
      "\n\023ClientService.proto\022\036com.pro.message.p" +
      "ipe.core.grpc\"O\n\025ClientRegisterRequest\022\027" +
      "\n\017messagePipeName\030\001 \001(\t\022\017\n\007address\030\002 \001(\t" +
      "\022\014\n\004port\030\003 \001(\005\"7\n\026ClientHeartBeatRequest" +
      "\022\017\n\007address\030\001 \001(\t\022\014\n\004port\030\002 \001(\005\"\036\n\016Clien" +
      "tResponse\022\014\n\004body\030\001 \001(\t2\367\001\n\rClientServic" +
      "e\022q\n\010register\0225.com.pro.message.pipe.cor" +
      "e.grpc.ClientRegisterRequest\032..com.pro.m" +
      "essage.pipe.core.grpc.ClientResponse\022s\n\t" +
      "heartbeat\0226.com.pro.message.pipe.core.gr" +
      "pc.ClientHeartBeatRequest\032..com.pro.mess" +
      "age.pipe.core.grpc.ClientResponseB\026B\022Cli" +
      "entServiceProtoP\001b\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_com_pro_message_pipe_core_grpc_ClientRegisterRequest_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_com_pro_message_pipe_core_grpc_ClientRegisterRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_com_pro_message_pipe_core_grpc_ClientRegisterRequest_descriptor,
        new String[] { "MessagePipeName", "Address", "Port", });
    internal_static_com_pro_message_pipe_core_grpc_ClientHeartBeatRequest_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_com_pro_message_pipe_core_grpc_ClientHeartBeatRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_com_pro_message_pipe_core_grpc_ClientHeartBeatRequest_descriptor,
        new String[] { "Address", "Port", });
    internal_static_com_pro_message_pipe_core_grpc_ClientResponse_descriptor =
      getDescriptor().getMessageTypes().get(2);
    internal_static_com_pro_message_pipe_core_grpc_ClientResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_com_pro_message_pipe_core_grpc_ClientResponse_descriptor,
        new String[] { "Body", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}