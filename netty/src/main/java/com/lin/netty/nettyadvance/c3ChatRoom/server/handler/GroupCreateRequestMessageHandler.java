package com.lin.netty.nettyadvance.c3ChatRoom.server.handler;

import com.lin.netty.nettyadvance.c3ChatRoom.server.session.Group;
import com.lin.netty.nettyadvance.c3ChatRoom.server.session.GroupFactory;
import com.lin.netty.nettyadvance.c3ChatRoom.server.session.GroupSession;
import com.lin.netty.nettyadvance.message.request.GroupCreateRequestMessage;
import com.lin.netty.nettyadvance.message.response.GroupCreateResponseMessage;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.List;
import java.util.Set;

@ChannelHandler.Sharable
public class GroupCreateRequestMessageHandler extends SimpleChannelInboundHandler<GroupCreateRequestMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupCreateRequestMessage msg) throws Exception {
        String groupName = msg.getGroupName();
        Set<String> members = msg.getMembers();
        // 群管理器
        GroupSession groupSession = GroupFactory.getSession();
        Group group = groupSession.createGroup(groupName, members);
        if (group != null) {
            // 发生成功消息
            ctx.writeAndFlush(new GroupCreateResponseMessage(true, groupName + " 创建成功"));
            // 发送拉群消息
            List<Channel> membersChannel = groupSession.getMembersChannel(groupName);
            membersChannel.forEach(channel -> {
                channel.writeAndFlush(new GroupCreateResponseMessage(true, "您已被拉入 " + groupName));
            });
        } else {
            ctx.writeAndFlush(new GroupCreateResponseMessage(false, groupName + " 已经存在"));
        }
    }
}
