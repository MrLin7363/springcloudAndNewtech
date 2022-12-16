package com.lin.netty.nettyoptimize.c3rpc.rpchandler;

import com.lin.netty.nettyadvance.message.response.RpcResponseMessage;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.concurrent.Promise;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@ChannelHandler.Sharable  // 这里不允许有共享的变量，不保证安全，但是我们自己保证了线程安全，所以可以有共享变量
public class RpcResponseMessageHandler extends SimpleChannelInboundHandler<RpcResponseMessage> {
    // 序号  用来接收结果的promise对象
    public static final Map<Integer, Promise<Object>> PROMISES = new ConcurrentHashMap<>();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcResponseMessage msg) throws Exception {
        log.debug("{}", msg);
        // 取到对象后 移除对象，不然越来越多
        Promise<Object> promise = PROMISES.remove(msg.getSequenceId());
        if (promise != null) {
            Exception exceptionValue = msg.getExceptionValue();
            Object returnValue = msg.getReturnValue();
            if (exceptionValue != null) {
                promise.setFailure(exceptionValue);
            } else {
                promise.setSuccess(returnValue);
            }
        }
    }
}
