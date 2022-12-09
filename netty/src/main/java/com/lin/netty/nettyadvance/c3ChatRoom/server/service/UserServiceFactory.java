package com.lin.netty.nettyadvance.c3ChatRoom.server.service;

public class UserServiceFactory {
    private static final UserServiceImpl userService = new UserServiceImpl();

    public static UserService getUserService() {
        return userService;
    }
}
