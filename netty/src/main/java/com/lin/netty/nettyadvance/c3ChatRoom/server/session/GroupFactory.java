package com.lin.netty.nettyadvance.c3ChatRoom.server.session;

public class GroupFactory {

    private static final GroupSessionImpl groupSession = new GroupSessionImpl();

    public static GroupSession getSession() {
        return groupSession;
    }
}
