package com.lin.netty.nettyadvance.c1package;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

// t4 LTC解码器 最大长度，长度偏移，长度占用字节，长度调整，剥离字节数
public class t3LengthFieldDecoder {

    public static void main(String[] args) {
        EmbeddedChannel channel = new EmbeddedChannel(
            // 最后一个4:从头开始剥离，跳过int四个字节；  1: 从长度占用末位开始多少个字节后是真正的内容  5:从头开始剥离字节数
            new LengthFieldBasedFrameDecoder(1024, 0, 4, 1, 5),
            new LoggingHandler(LogLevel.DEBUG)
        );

        // 4 个字节的内容长度，实际内容
        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();
        send(buf, "Hello, world");
        send(buf, "Hi!");
        channel.writeInbound(buf);
    }

    private static void send(ByteBuf buf, String content) {
        byte[] bytes = content.getBytes();
        int length = content.length();
        buf.writeInt(length);
        buf.writeByte(1); // 附加内容1个字节
        buf.writeBytes(bytes);
    }
}
