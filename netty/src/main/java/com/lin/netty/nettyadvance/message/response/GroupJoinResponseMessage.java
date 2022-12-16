package com.lin.netty.nettyadvance.message.response;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true) // toString包含父类
public class GroupJoinResponseMessage extends AbstractResponseMessage {
    public GroupJoinResponseMessage(boolean success, String name) {
        super(success, name);
    }

    @Override
    public int getMessageType() {
        return GroupCreateResponseMessage;
    }
}
