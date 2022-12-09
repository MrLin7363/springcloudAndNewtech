package com.lin.netty.nettyadvance.c3ChatRoom.server.handler;

import com.lin.netty.nettyadvance.c3ChatRoom.server.session.Group;
import com.lin.netty.nettyadvance.c3ChatRoom.server.session.GroupFactory;
import com.lin.netty.nettyadvance.c3ChatRoom.server.session.GroupSession;
import com.lin.netty.nettyadvance.message.request.GroupJoinRequestMessage;
import com.lin.netty.nettyadvance.message.response.GroupJoinResponseMessage;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

@ChannelHandler.Sharable
public class GroupJoinRequestMessageHandler extends SimpleChannelInboundHandler<GroupJoinRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupJoinRequestMessage msg) throws Exception {
        GroupSession groupSession = GroupFactory.getSession();
        Group group = groupSession.joinMember(msg.getGroupName(), msg.getUsername());
        if (group == null) {
            ctx.writeAndFlush(new GroupJoinResponseMessage(true, msg.getGroupName() + "群不存在"));
        } else {
            ctx.writeAndFlush(new GroupJoinResponseMessage(true, msg.getGroupName() + "群加入成功"));
        }
    }
}
