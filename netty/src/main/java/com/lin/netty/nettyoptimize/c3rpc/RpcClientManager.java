package com.lin.netty.nettyoptimize.c3rpc;

import com.lin.netty.nettyadvance.message.MessageCodecSharable;
import com.lin.netty.nettyadvance.message.ProcotolFrameDecoder;
import com.lin.netty.nettyadvance.message.request.RpcRequestMessage;
import com.lin.netty.nettyoptimize.c3rpc.rpchandler.RpcResponseMessageHandler;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.DefaultPromise;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Proxy;

/**
 * 第二版RPC RpcServer
 */
@Slf4j
public class RpcClientManager {
    public static void main(String[] args) {
        HelloService helloService = getServiceProxy(HelloService.class);
        // 主线程发起调用
        System.out.println(helloService.sayHello("霖"));
        System.out.println(helloService.sayHello("lin"));
    }

    // 获取代理对，
    public static <T> T getServiceProxy(Class<T> serviceClass) {
        ClassLoader loader = serviceClass.getClassLoader();
        Class<?>[] interfaces = new Class[]{serviceClass};
        // 代理对象执行方法时，会执行下面的逻辑
        Object o = Proxy.newProxyInstance(loader, interfaces, (proxy, method, args) -> {
            // 1. 方法转换为 消息对象
            int sequenceId = SequenceIdGenerator.getSequenceId();
            RpcRequestMessage rpcRequestMessage = new RpcRequestMessage(
                sequenceId,
                serviceClass.getName(),
                method.getName(),
                method.getReturnType(),
                method.getParameterTypes(),
                args);
            // 2. 消息对象发送出去
            getChannel().writeAndFlush(rpcRequestMessage);

            // 3.准备一个空Promise对象，来接收结果  指定promise对象异步接收结果线程
            DefaultPromise<Object> promise = new DefaultPromise<>(getChannel().eventLoop());
            RpcResponseMessageHandler.PROMISES.put(sequenceId, promise);

            promise.await();
            // 3. 返回结果
            if (promise.isSuccess()) {
                return promise.getNow();
            } else {
                throw new RuntimeException(promise.cause());
            }
        });
        return (T) o;
    }

    // 获取唯一的channel对象
    public static Channel getChannel() {
        if (channel != null) {
            return channel;
        }
        synchronized (LOCK) { // t2
            if (channel != null) { // t1
                return channel;
            }
            initChannel();
            return channel;
        }
    }

    private static Channel channel = null;

    private static final Object LOCK = new Object();

    private static void initChannel() {
        NioEventLoopGroup group = new NioEventLoopGroup();
        LoggingHandler LOGGING_HANDLER = new LoggingHandler(LogLevel.DEBUG);
        MessageCodecSharable MESSAGE_CODEC = new MessageCodecSharable();
        // rpc 响应消息处理器，待实现
        RpcResponseMessageHandler RPC_HANDLER = new RpcResponseMessageHandler(); // nio线程
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.group(group);
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(new ProcotolFrameDecoder());
                ch.pipeline().addLast(LOGGING_HANDLER);
                ch.pipeline().addLast(MESSAGE_CODEC);
                ch.pipeline().addLast(RPC_HANDLER);
            }
        });
        try {
            channel = bootstrap.connect("localhost", 8080).sync().channel();
            channel.closeFuture().addListener(future -> {
                group.shutdownGracefully();
            });
            // channel.closeFuture().sync(); channel不阻塞
        } catch (Exception e) {
            log.error("client error", e);
        }
    }
}
