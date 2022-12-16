package com.lin.netty.nettyoptimize.c3rpc.rpchandler;

import com.lin.netty.nettyadvance.message.request.RpcRequestMessage;
import com.lin.netty.nettyadvance.message.response.RpcResponseMessage;
import com.lin.netty.nettyoptimize.c3rpc.HelloService;
import com.lin.netty.nettyoptimize.c3rpc.ServicesFactory;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;

@Slf4j
@ChannelHandler.Sharable
public class RpcRequestMessageHandler extends SimpleChannelInboundHandler<RpcRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcRequestMessage message) {
        RpcResponseMessage response = new RpcResponseMessage();
        response.setSequenceId(message.getSequenceId());
        try {
            // 获取真正的实现对象
            HelloService service = (HelloService)
                ServicesFactory.getService(Class.forName(message.getInterfaceName()));

            // 获取要调用的方法
            Method method = service.getClass().getMethod(message.getMethodName(), message.getParameterTypes());

            // 调用方法
            Object invoke = method.invoke(service, message.getParameterValue());
            // 调用成功
            response.setReturnValue(invoke);
        } catch (Exception e) {
            e.printStackTrace();
            // Adjusted frame length exceeds 1024: 11652 - discarded
            // 如果直接set e 返回给客户端，客户端接收不到，因为堆栈太大，  长度解析器最大1024
            response.setExceptionValue(new Exception("远程调用出错" + e.getCause().getMessage()));
        }
        // 返回结果
        ctx.writeAndFlush(response);
    }
}
