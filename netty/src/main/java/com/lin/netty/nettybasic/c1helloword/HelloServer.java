package com.lin.netty.nettybasic.c1helloword;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;

public class HelloServer {
    public static void main(String[] args) {
        // 1. 启动器，负责组装nettry组件，启动服务器
        new ServerBootstrap()
            // 2. BossEventLoop,workerEventLoop(selector,thread), group 组
            .group(new NioEventLoopGroup()) // 16. 由某个Eventloop处理read事件，接收到ByteBuffer
            // 3. 选择服务器的ServerSocketChannel实现
            .channel(NioServerSocketChannel.class)
            // 4. boss 负责处理连接 worker(child) 负责处理读写，决定了worker(child)能执行什么
            .childHandler(
                // 5. channel 代表和客户端进行数据读写的通道初始化
                new ChannelInitializer<NioSocketChannel>() {

                    // 12. 连接建立后，调用初始化方法
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        // 17. SocketChannel 的处理器，解码 ByteBuf => String
                        ch.pipeline().addLast(new StringDecoder());
                        // SocketChannel 的处理器，解码 ByteBuf => String
                        ch.pipeline().addLast(new SimpleChannelInboundHandler<String>() {

                            // 18. 执行 read方法，打印Hello
                            @Override
                            protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
                                System.out.println(msg);
                            }
                        });
                    }
                }
            )
            // 6. ServerSocketChannel 绑定的监听端口
            .bind(8080);
    }
}
