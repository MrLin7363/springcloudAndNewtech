package com.lin.netty.nettyadvance.message.response;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true) // toString包含父类
public class ChatResponseMessage extends AbstractResponseMessage {
    private String from;

    private String content;

    public ChatResponseMessage(boolean success, String reason) {
        super(success, reason);
    }

    public ChatResponseMessage(String from, String content) {
        super(true, "");
        this.from = from;
        this.content = content;
    }

    @Override
    public int getMessageType() {
        return LoginResponseMessage;
    }
}
