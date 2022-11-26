package com.lin.netty.nettyadvance.message;

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

    @Override
    public int getMessageType() {
        return LoginRequestMessage;
    }
}
