package com.lin.netty.nettybasic.c5HandlerPipeline;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * CloseFutureClient 运行客户端
 *
 * ChannelHandlerContext write  从当前节点往前找
 * Channel write   从tail往前找
 */
public class TestPipeline {

    public static void main(String[] args) {
        new ServerBootstrap()
            .group(new NioEventLoopGroup())
            .channel(NioServerSocketChannel.class)
            .childHandler(new ChannelInitializer<NioSocketChannel>() {
                protected void initChannel(NioSocketChannel ch) {
                    // hear 1 > 2 > 3 > 4 > 5 > 6 > tail
                    // 4 5 6 是出站操作，代码里控制了从后往前
                    ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                        @Override
                        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                            System.out.println(1);
                            // 将数据传递给下一个hanlder,如果不传递，则链路断开
                            // = ctx.fireChannelRead(msg)
                            super.channelRead(ctx, msg); // 1
                        }
                    });
                    ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                        @Override
                        public void channelRead(ChannelHandlerContext ctx, Object msg) {
                            System.out.println(2);
                            ctx.fireChannelRead(msg); // 2
                        }
                    });
                    ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                        @Override
                        public void channelRead(ChannelHandlerContext ctx, Object msg) {
                            System.out.println(3);
                            // ch.write channel是从tail找出站处理器, 如果处理顺序不对，会找不到执行链路执行下去
                            ch.write(msg); // 3
                        }
                    });
                    ch.pipeline().addLast(new ChannelOutboundHandlerAdapter() {
                        @Override
                        public void write(ChannelHandlerContext ctx, Object msg,
                            ChannelPromise promise) throws Exception {
                            System.out.println(4);
                            // = ctx.write  是向前找出站处理器
                            super.write(ctx, msg, promise); // 4
                        }
                    });
                    ch.pipeline().addLast(new ChannelOutboundHandlerAdapter() {
                        @Override
                        public void write(ChannelHandlerContext ctx, Object msg,
                            ChannelPromise promise) {
                            System.out.println(5);
                            ctx.write(msg, promise); // 5
                        }
                    });
                    ch.pipeline().addLast(new ChannelOutboundHandlerAdapter() {
                        @Override
                        public void write(ChannelHandlerContext ctx, Object msg,
                            ChannelPromise promise) {
                            System.out.println(6);
                            ctx.write(msg, promise); // 6
                        }
                    });
                }
            })
            .bind(8080);
    }
}
