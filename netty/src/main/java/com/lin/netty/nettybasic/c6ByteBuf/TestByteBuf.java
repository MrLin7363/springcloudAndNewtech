package com.lin.netty.nettybasic.c6ByteBuf;

import static io.netty.buffer.ByteBufUtil.appendPrettyHexDump;
import static io.netty.util.internal.StringUtil.NEWLINE;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;

// 自动扩容
@Slf4j
public class TestByteBuf {
    public static void main(String[] args) throws UnsupportedEncodingException {
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer(10); // 默认是直接内存  heapBuffer()堆内存  ; 默认是256字节capacity
        System.out.println(buffer.getClass()); // PooledUnsafeDirectByteBuf 池化直接内存
        StringBuilder sb = new StringBuilder();
        log(buffer);
        for (int i = 0; i < 9; i++) {
            sb.append(i);
        }
        buffer.writeBytes(sb.toString().getBytes());
        buffer.readByte(); // 读取1个字节
        buffer.markReaderIndex();
        System.out.println(buffer.readBytes(5)); // 读取5个字节
        buffer.resetReaderIndex(); // 回到刚才的读标志位
        System.out.println(new String(new byte[]{buffer.readByte()}, "UTF-8"));
        log(buffer);
    }

    public static void log(ByteBuf buffer) {
        int length = buffer.readableBytes();
        int rows = length / 16 + (length % 15 == 0 ? 0 : 1) + 4;
        StringBuilder buf = new StringBuilder( rows * 80 * 2)
            .append("read index:").append(buffer.readerIndex())
            .append(" write index:").append(buffer.writerIndex())
            .append(" capacity:").append(buffer.capacity())
            .append(NEWLINE);
        appendPrettyHexDump(buf, buffer);
        System.out.println(buf);
    }
}
