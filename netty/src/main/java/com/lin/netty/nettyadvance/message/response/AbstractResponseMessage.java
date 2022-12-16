package com.lin.netty.nettyadvance.message.response;

import com.lin.netty.nettyadvance.message.Message;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true) // toString包含父类
public abstract class AbstractResponseMessage extends Message {
    private boolean success;

    private String reason;

    public AbstractResponseMessage() {}

    public AbstractResponseMessage(boolean success, String reason) {
        this.success = success;
        this.reason = reason;
    }

}
