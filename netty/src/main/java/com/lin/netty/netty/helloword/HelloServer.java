package com.lin.netty.netty.helloword;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;

public class HelloServer {
    public static void main(String[] args) {
        // 启动器，负责组装nettry组件，启动服务器
        new ServerBootstrap()
            // BossEventLoop,workerEventLoop(selector,thread), group 组
            .group(new NioEventLoopGroup())
            // 选择服务器的ServerSocketChannel实现
            .channel(NioServerSocketChannel.class)
            // boss 负责处理连接 worker(child) 负责处理读写，决定了worker(child)能执行什么
            .childHandler(
                new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new StringDecoder());
                        ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {

                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                System.out.println(msg);
                            }
                        });
                    }
                }
            )
            .bind(8080);
    }
}
