package com.lin.netty.nettyadvance.message;

import com.lin.netty.nettyadvance.message.request.RpcRequestMessage;
import com.lin.netty.nettyadvance.message.response.RpcResponseMessage;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Data
public abstract class Message implements Serializable {
    /**
     * 根据消息类型字节，获得对应的消息 class
     * @param messageType 消息类型字节
     * @return 消息 class
     */
    public static Class<? extends Message> getMessageClass(int messageType) {
        return messageClasses.get(messageType);
    }

    private int sequenceId;

    private int messageType;

    public abstract int getMessageType();

    public static final int LoginRequestMessage = 0;
    public static final int LoginResponseMessage = 1;
    public static final int ChatRequestMessage = 2;
    public static final int ChatResponseMessage = 3;
    public static final int GroupCreateRequestMessage = 4;
    public static final int GroupCreateResponseMessage = 5;
    public static final int GroupJoinRequestMessage = 6;
    public static final int GroupJoinResponseMessage = 7;
    public static final int GroupQuitRequestMessage = 8;
    public static final int GroupQuitResponseMessage = 9;
    public static final int GroupChatRequestMessage = 10;
    public static final int GroupChatResponseMessage = 11;
    public static final int GroupMembersRequestMessage = 12;
    public static final int GroupMembersResponseMessage = 13;
    public static final int PingMessage = 14;
    public static final int PongMessage = 15;

    public static final int RPC_MESSAGE_TYPE_REQUEST = 101;
    public static final int  RPC_MESSAGE_TYPE_RESPONSE = 102;

    private static final Map<Integer, Class<? extends Message>> messageClasses = new HashMap<>();

    static {
        messageClasses.put(LoginRequestMessage, com.lin.netty.nettyadvance.message.request.LoginRequestMessage.class);
        messageClasses.put(LoginResponseMessage, com.lin.netty.nettyadvance.message.response.LoginResponseMessage.class);
        messageClasses.put(ChatRequestMessage, com.lin.netty.nettyadvance.message.request.ChatRequestMessage.class);
        messageClasses.put(ChatResponseMessage, com.lin.netty.nettyadvance.message.response.ChatResponseMessage.class);
        messageClasses.put(GroupCreateRequestMessage, com.lin.netty.nettyadvance.message.request.GroupCreateRequestMessage.class);
        messageClasses.put(GroupCreateResponseMessage, com.lin.netty.nettyadvance.message.response.GroupCreateResponseMessage.class);
        messageClasses.put(GroupJoinRequestMessage, com.lin.netty.nettyadvance.message.request.GroupJoinRequestMessage.class);
        messageClasses.put(GroupJoinResponseMessage, com.lin.netty.nettyadvance.message.response.GroupJoinResponseMessage.class);
        messageClasses.put(GroupQuitRequestMessage, com.lin.netty.nettyadvance.message.request.GroupQuitRequestMessage.class);
//        messageClasses.put(GroupQuitResponseMessage, GroupQuitResponseMessage.class);
        messageClasses.put(GroupChatRequestMessage, com.lin.netty.nettyadvance.message.request.GroupChatRequestMessage.class);
        messageClasses.put(GroupChatResponseMessage, com.lin.netty.nettyadvance.message.response.GroupChatResponseMessage.class);
        messageClasses.put(GroupMembersRequestMessage, com.lin.netty.nettyadvance.message.request.GroupMembersRequestMessage.class);
        messageClasses.put(GroupMembersResponseMessage, com.lin.netty.nettyadvance.message.response.GroupMembersResponseMessage.class);

        messageClasses.put(RPC_MESSAGE_TYPE_REQUEST, RpcRequestMessage.class);
        messageClasses.put(RPC_MESSAGE_TYPE_RESPONSE, RpcResponseMessage.class);
    }
}
