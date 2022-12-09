package com.lin.netty.nettyadvance.c3ChatRoom.server.session;

import io.netty.channel.Channel;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * 单例模式，此处变量共享
 */
public class SessionImpl implements Session {
    private final Map<String, Channel> userChannels = new HashMap<>();

    @Override
    public void bind(Channel channel, String username) {
        userChannels.put(username,channel);
    }

    @Override
    public void unbind(Channel channel) {
        Iterator<Map.Entry<String, Channel>> iterator = userChannels.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String, Channel> next = iterator.next();
            if (next.getValue().equals(channel)){
                iterator.remove();
            }
        }
    }

    @Override
    public Object getAttribute(Channel channel, String name) {
        return null;
    }

    @Override
    public void setAttribute(Channel channel, String name, Object value) {

    }

    @Override
    public Channel getChannel(String username) {
        return userChannels.get(username);
    }
}
