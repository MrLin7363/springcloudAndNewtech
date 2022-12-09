package com.lin.netty.nettyadvance.c3ChatRoom.server.session;

public class SessionFactory {

    private static final SessionImpl sessionImpl = new SessionImpl();

    public static Session getSession() {
        return sessionImpl;
    }
}
