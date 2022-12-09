package com.lin.netty.nettyadvance.c3ChatRoom.server.service;

import com.lin.netty.nettyadvance.c3ChatRoom.server.service.UserService;

public class UserServiceImpl implements UserService {
    @Override
    public boolean login(String username, String password) {
        return true; // default success
    }
}
