package com.lin.netty.nettyadvance.c2Protocol;

import com.lin.netty.nettyadvance.message.request.LoginRequestMessage;
import com.lin.netty.nettyadvance.message.MessageCodec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LoggingHandler;

public class TestMessageCodec {
    public static void main(String[] args) throws Exception {
        // 工人1 工人2  LengthFieldBasedFrameDecoder是线程不安全的，会保存上一次的状态信息，不能多个线程使用handler
        LengthFieldBasedFrameDecoder lengthFieldBasedFrameDecoder = new LengthFieldBasedFrameDecoder(1024, 12,
            4, 0, 0);

        // 调试ChannelHandler是否成功等
        EmbeddedChannel channel = new EmbeddedChannel(
            new LoggingHandler(), // 线程安全
            // 解决沾包半包问题
            new LengthFieldBasedFrameDecoder(1024, 12, 4, 0, 0),
//            new MessageCodec()
            new MessageCodec()
        );
        // encode
        LoginRequestMessage message = new LoginRequestMessage("zhangsan", "123", "张三");
        // 出站会encode后出站
        channel.writeOutbound(message);

        // encode获得buf与出站一致
        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();
        new MessageCodec().encode(null, message, buf);


        ByteBuf s1 = buf.slice(0, 100);
        ByteBuf s2 = buf.slice(100, buf.readableBytes() - 100);
        s1.retain(); // 引用计数 2

        // 入站 decode  LengthFieldBasedFrameDecoder已经处理好成完整的消息了
        channel.writeInbound(s1); // release 1 ;  s1 s2和 buf同一块内存，如果不retain   wirte s2会报错
        channel.writeInbound(s2);
    }
}
