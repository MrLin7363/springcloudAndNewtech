package com.lin.netty.nettybasic.c1helloword;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;

import java.io.IOException;
import java.util.Date;

public class HelloClient {
    public static void main(String[] args) throws InterruptedException, IOException {
        new Bootstrap()
            // 8.创建 NioEventLoopGroup，同 Server
            .group(new NioEventLoopGroup())
            // 9.选择客户 Socket 实现类，NioSocketChannel 表示基于 NIO 的客户端实现
            .channel(NioSocketChannel.class)
            // 10.添加 SocketChannel 的处理器，ChannelInitializer 处理器（仅执行一次）
            //，它的作用是待客户端 SocketChannel 建立连接后，执行 initChannel 以便添加更多的处理器
            .handler(new ChannelInitializer<Channel>() {

                // 12. 连接建立后调用
                @Override
                protected void initChannel(Channel ch) throws Exception {
                    // 15. 消息会经过通道 handler 处理，这里是将 String => ByteBuf 发出
                    ch.pipeline().addLast(new StringEncoder());
                }
            })
            // 11. 连接到服务器
            .connect("localhost", 8080)
            // 13. 阻塞方法，直到连接建立
            .sync() // Netty 中很多方法都是异步的，如 connect，这时需要使用 sync 方法等待 connect 建立连接完毕
            .channel() // 获取 channel 对象，它即为通道抽象，可以进行数据读写操作
            .writeAndFlush(new Date() + ": hello world!"); // 14.发送消息并清空缓冲区
        System.in.read();
    }
}
