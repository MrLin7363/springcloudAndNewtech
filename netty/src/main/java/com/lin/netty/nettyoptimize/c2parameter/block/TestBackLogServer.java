package com.lin.netty.nettyoptimize.c2parameter.block;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LoggingHandler;

import java.nio.channels.ServerSocketChannel;

/**
 * windows没指定backlog  所以取系统
 * <p>
 * TestConnectionTimeout
 * <p>
 * 通过断点方法不调accept 验证全连接队列大小
 */
public class TestBackLogServer {
    public static void main(String[] args) {
        new ServerBootstrap()
            .group(new NioEventLoopGroup())
            .option(ChannelOption.SO_BACKLOG, 2) // 设置2个全连接队列
            /*
            NioEventLoop  源码里这里断点阻塞accept连接 ，开启第三个客户端失败
              if ((readyOps & 17) != 0 || readyOps == 0) {
                    unsafe.read();
                }

            查看默认值 NioServerSocketChannel
             */
            .channel(NioServerSocketChannel.class)
            .childHandler(
                new ChannelInitializer<NioSocketChannel>() {

                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new LoggingHandler());
                    }
                }
            )
            .bind(8080);
    }
}
