package com.lin.netty.nettyoptimize.c1serializer;

import com.lin.netty.nettyadvance.message.Config;
import com.lin.netty.nettyadvance.message.MessageCodecSharable;
import com.lin.netty.nettyadvance.message.request.LoginRequestMessage;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.logging.LoggingHandler;

public class TestSerializer {

    public static void main(String[] args) {
        MessageCodecSharable messageCodecSharable = new MessageCodecSharable();
        LoggingHandler loggingHandler = new LoggingHandler();
        EmbeddedChannel embeddedChannel = new EmbeddedChannel(loggingHandler, messageCodecSharable);

        LoginRequestMessage requestMessage = new LoginRequestMessage("user", "test");
        embeddedChannel.writeOutbound(requestMessage);

        ByteBuf buf = messageToByteBuf(requestMessage);
        embeddedChannel.writeInbound(buf);
    }

    private static ByteBuf messageToByteBuf( LoginRequestMessage msg) {
        ByteBuf out = ByteBufAllocator.DEFAULT.buffer();
        // 1. 4 字节的魔数
        out.writeBytes(new byte[]{1, 2, 3, 4});
        // 2. 1 字节的版本,
        out.writeByte(1);
        // 3. 1 字节的序列化方式 jdk 0 , json 1
        out.writeByte(Config.getSerializerAlgorithm().ordinal());
        // 4. 1 字节的指令类型
        out.writeByte(msg.getMessageType());
        // 5. 4 个字节
        out.writeInt(msg.getSequenceId());
        // 无意义，对齐填充 一般是2的次数倍刚好16个字节
        out.writeByte(0xff);
        // 6. 获取内容的字节数组    对象转字节数组
        byte[] bytes = Config.getSerializerAlgorithm().serialize(msg);
        // 7. 长度
        out.writeInt(bytes.length);
        // 8. 写入内容
        out.writeBytes(bytes);
        return out;
    }
}
