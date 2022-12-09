package com.lin.netty.nettyadvance.message.request;

import com.lin.netty.nettyadvance.message.Message;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 单聊发送
 */
@Data
@AllArgsConstructor
public class ChatRequestMessage extends Message {
    private String from;

    private String to;

    private String content;

    @Override
    public int getMessageType() {
        return ChatRequestMessage;
    }
}
