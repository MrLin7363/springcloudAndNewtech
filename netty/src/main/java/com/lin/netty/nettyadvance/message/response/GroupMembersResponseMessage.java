package com.lin.netty.nettyadvance.message.response;

import lombok.Data;
import lombok.ToString;

import java.util.Set;

@Data
@ToString(callSuper = true) // toString包含父类
public class GroupMembersResponseMessage extends AbstractResponseMessage {
    private Set<String> members;

    public GroupMembersResponseMessage(Set<String> members) {
        this.members = members;
    }

    @Override
    public int getMessageType() {
        return GroupMembersResponseMessage;
    }
}
