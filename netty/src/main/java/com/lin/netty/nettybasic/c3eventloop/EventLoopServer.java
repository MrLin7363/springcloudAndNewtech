package com.lin.netty.nettybasic.c3eventloop;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.DefaultEventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;

@Slf4j
public class EventLoopServer {
    public static void main(String[] args) {
        DefaultEventLoop defaultEventLoop = new DefaultEventLoop();
        new ServerBootstrap()
            // boss只负责accept 事件, worker只负责socketChannel上读写,1个loop可以管理多个channel，但是绑定后channel只会由这个loop管理
            .group(new NioEventLoopGroup(), new NioEventLoopGroup(2))
            // NioServerSocketChannel只有1个，只会占用 1个NioEventLoopGroup
            .channel(NioServerSocketChannel.class)
            .childHandler(
                new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                ByteBuf byteBuf = (ByteBuf) msg;
                                // 每个eventloop处理一个channel不会变,建立绑定关系
                                log.debug(byteBuf.toString(Charset.defaultCharset()));
                                // 传递给下一个处理
                                ctx.fireChannelRead(msg);
                            }
                        });

                        // 假如这个IO处理很久，可以新建个eventloop处理，不影响主要的eventloop
                        ch.pipeline().addLast(defaultEventLoop, new ChannelInboundHandlerAdapter() {
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                ByteBuf byteBuf = (ByteBuf) msg;
                                log.debug(byteBuf.toString(Charset.defaultCharset()));
                            }
                        });
                    }
                }
            )
            .bind(8080);
    }
}
