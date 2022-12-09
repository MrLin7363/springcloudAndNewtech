package com.lin.netty.nettyadvance.message.response;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true) // toString包含父类
public class GroupCreateResponseMessage extends AbstractResponseMessage{

    public  GroupCreateResponseMessage(boolean success,String name){
        super(success,name);
    }

    @Override
    public int getMessageType() {
        return GroupCreateResponseMessage;
    }
}
