package com.lin.netty.nettyadvance.c3ChatRoom.server.handler;

import com.lin.netty.nettyadvance.c3ChatRoom.server.session.GroupFactory;
import com.lin.netty.nettyadvance.c3ChatRoom.server.session.GroupSession;
import com.lin.netty.nettyadvance.message.request.GroupMembersRequestMessage;
import com.lin.netty.nettyadvance.message.response.GroupMembersResponseMessage;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Set;

@ChannelHandler.Sharable
public class GroupMembersRequestMessageHandler extends SimpleChannelInboundHandler<GroupMembersRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupMembersRequestMessage msg) throws Exception {
        GroupSession groupSession = GroupFactory.getSession();
        Set<String> members = groupSession.getMembers(msg.getGroupName());
        ctx.writeAndFlush(new GroupMembersResponseMessage(members));
    }
}
