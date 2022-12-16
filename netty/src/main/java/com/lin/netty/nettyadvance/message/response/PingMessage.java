package com.lin.netty.nettyadvance.message.response;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true) // toString包含父类
public class PingMessage extends AbstractResponseMessage {

    public PingMessage(boolean success, String reason) {
        super(success, reason);
    }

    @Override
    public int getMessageType() {
        return PingMessage;
    }
}
