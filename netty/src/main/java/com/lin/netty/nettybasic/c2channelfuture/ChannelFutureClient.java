package com.lin.netty.nettybasic.c2channelfuture;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

// EventLoopServer
@Slf4j
public class ChannelFutureClient {
    public static void main(String[] args) throws InterruptedException, IOException {
        // 带 future ,promise都是异步方法配套使用,用来处理结果
        ChannelFuture channelFuture = new Bootstrap()
            .group(new NioEventLoopGroup())
            .channel(NioSocketChannel.class)
            .handler(new ChannelInitializer<Channel>() {

                @Override
                protected void initChannel(Channel ch) throws Exception {
                    ch.pipeline().addLast(new StringEncoder());
                }
            })
            // 1.连接到服务器， 异步非阻塞,main 发起调用, 真正执行连接的是另一个nio线程NioEventLoopGroup
            .connect("localhost", 8080); // 1s后连接，但是主线程如果不阻塞，main瞬间获取channel（空）

        // 2.1 使用sync main主线程阻塞等待nio建立完连接
        //                channelFuture.sync();
        //                Channel channel = channelFuture.channel();
        //                channel.writeAndFlush("hello");

        // 2.2 使用addListener方法处理异步结果， 主线程也不阻塞,
        channelFuture.addListener(new ChannelFutureListener() {
            // 这个对象传递给nio线程,连接建立好调用operationComplete
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                Channel channel = channelFuture.channel();
                log.debug("channel = {}", channel);
                channel.writeAndFlush("hello");
            }
        });
    }
}
