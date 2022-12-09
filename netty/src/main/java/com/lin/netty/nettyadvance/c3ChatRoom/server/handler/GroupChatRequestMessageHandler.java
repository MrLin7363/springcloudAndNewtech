package com.lin.netty.nettyadvance.c3ChatRoom.server.handler;

import com.lin.netty.nettyadvance.c3ChatRoom.server.session.GroupFactory;
import com.lin.netty.nettyadvance.c3ChatRoom.server.session.GroupSession;
import com.lin.netty.nettyadvance.message.request.GroupChatRequestMessage;
import com.lin.netty.nettyadvance.message.response.GroupChatResponseMessage;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.List;

@ChannelHandler.Sharable
public class GroupChatRequestMessageHandler extends SimpleChannelInboundHandler<GroupChatRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupChatRequestMessage msg) throws Exception {
        final String groupName = msg.getGroupName();
        GroupSession groupSession = GroupFactory.getSession();
        List<Channel> membersChannel = groupSession.getMembersChannel(groupName);
        membersChannel.forEach(channel -> {
            channel.writeAndFlush(new GroupChatResponseMessage(msg.getUsername(),msg.getContent()));
        });
    }
}
