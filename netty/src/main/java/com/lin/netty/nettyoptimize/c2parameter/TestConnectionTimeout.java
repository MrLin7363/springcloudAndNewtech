package com.lin.netty.nettyoptimize.c2parameter;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestConnectionTimeout {
    public static void main(String[] args) {
        /**
         * Bootstrap  childoption设置的是SocketChannel
         * ServerBootstrap  option设置的是ServerSocketChannel
         * ServerBootstrap  childOption设置的是SocketChannel
         */
        NioEventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap()
                .group(group)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 300)
                .channel(NioSocketChannel.class)
                .handler(new LoggingHandler());
            ChannelFuture future = bootstrap.connect("127.0.0.1", 8080);
            future.sync().channel().closeFuture().sync(); // 断点1  thread类型，否则一个断点停下来影响其他断点执行
        } catch (Exception e) {
            e.printStackTrace();
            log.debug("timeout");
        } finally {
            group.shutdownGracefully();
        }
    }
}
