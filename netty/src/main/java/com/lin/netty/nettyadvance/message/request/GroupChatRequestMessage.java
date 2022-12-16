package com.lin.netty.nettyadvance.message.request;

import com.lin.netty.nettyadvance.message.Message;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 发送到群聊
 */
@Data
@AllArgsConstructor
public class GroupChatRequestMessage extends Message {
    private String username;

    private String groupName;

    private String content;

    @Override
    public int getMessageType() {
        return GroupChatRequestMessage;
    }
}
