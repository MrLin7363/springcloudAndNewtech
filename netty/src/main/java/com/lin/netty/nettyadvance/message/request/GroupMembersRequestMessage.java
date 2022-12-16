package com.lin.netty.nettyadvance.message.request;

import com.lin.netty.nettyadvance.message.Message;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 获取组成员
 */
@Data
@AllArgsConstructor
public class GroupMembersRequestMessage extends Message {
    private String groupName;

    @Override
    public int getMessageType() {
        return GroupMembersRequestMessage;
    }
}
