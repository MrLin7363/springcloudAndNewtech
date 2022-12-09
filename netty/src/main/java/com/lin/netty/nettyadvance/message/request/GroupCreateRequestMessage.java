package com.lin.netty.nettyadvance.message.request;

import com.lin.netty.nettyadvance.message.Message;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

/**
 * 创建群聊
 */
@Data
@AllArgsConstructor
public class GroupCreateRequestMessage extends Message {
    private String groupName;

    private Set<String> members;

    @Override
    public int getMessageType() {
        return GroupCreateRequestMessage;
    }
}
