package com.lin.netty.nettyadvance.c3ChatRoom.server.session;

import com.lin.netty.nettyadvance.c3ChatRoom.server.service.UserServiceFactory;

import io.netty.channel.Channel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 单例模式，此处变量共享
 */
public class GroupSessionImpl implements GroupSession {
    private final Map<String, Set<String>> groups = new HashMap<>();

    @Override
    public Group createGroup(String name, Set<String> members) {
        if (groups.containsKey(name)) {
            return null;
        }
        groups.put(name, members);
        return new Group(name, members);
    }

    @Override
    public Group joinMember(String name, String member) {
        if (!groups.containsKey(name)) {
            return null;
        }
        Set<String> members = groups.get(name);
        members.add(member);
        return new Group(name, members);
    }

    @Override
    public Group removeMember(String name, String member) {
        if (!groups.containsKey(name)) {
            return null;
        }
        Set<String> members = groups.get(name);
        members.remove(member);
        return new Group(name, members);
    }

    @Override
    public Group removeGroup(String name) {
        return null;
    }

    @Override
    public Set<String> getMembers(String name) {
        return groups.get(name);
    }

    @Override
    public List<Channel> getMembersChannel(String name) {
        Set<String> users = groups.get(name);
        List<Channel> channelList = new ArrayList<>();
        Session session = SessionFactory.getSession();
        users.forEach(u -> {
            Channel channel = session.getChannel(u);
            channelList.add(channel);
        });
        return channelList;
    }
}
