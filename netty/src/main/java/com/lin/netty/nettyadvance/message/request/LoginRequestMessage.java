package com.lin.netty.nettyadvance.message.request;

import com.lin.netty.nettyadvance.message.Message;

import lombok.Data;

@Data
public class LoginRequestMessage extends Message {

    private String username;

    private String password;

    private String nickname;

    public LoginRequestMessage(String username, String password, String nickname) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
    }

    public LoginRequestMessage(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public int getMessageType() {
        return LoginRequestMessage;
    }
}
