package com.lin.netty.netty.eventloop;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;

import java.io.IOException;

public class EventClient {
    public static void main(String[] args) throws InterruptedException, IOException {
        Channel channel = new Bootstrap()
            .group(new NioEventLoopGroup())
            .channel(NioSocketChannel.class)
            .handler(new ChannelInitializer<Channel>() {

                // 12. 连接建立后调用
                @Override
                protected void initChannel(Channel ch) throws Exception {
                    ch.pipeline().addLast(new StringEncoder());
                }
            })
            .connect("localhost", 8080)
            .sync()
            .channel();
        System.out.println(channel);
        // 多个channel发 channel.writeAndFlush("hello")
        // 左侧dubug 线程改为只阻塞当前main线程而不是All
        System.in.read();
    }
}
