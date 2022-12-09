package com.lin.netty.nettyadvance.message.request;

import com.lin.netty.nettyadvance.message.Message;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 加入群聊
 */
@Data
@AllArgsConstructor
public class GroupJoinRequestMessage extends Message {
    private String username;

    private String groupName;

    @Override
    public int getMessageType() {
        return GroupJoinRequestMessage;
    }
}
