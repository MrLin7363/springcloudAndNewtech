package com.lin.netty.nettyadvance.message.response;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true) // toString包含父类
public class GroupChatResponseMessage extends AbstractResponseMessage{
    private String from;

    private String content;

    public GroupChatResponseMessage(String from, String content) {
        super(true, "");
        this.from = from;
        this.content = content;
    }
    @Override
    public int getMessageType() {
        return GroupChatResponseMessage;
    }
}
