package com.lin.netty.nioBasic.byteBuffer;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class TestStringtoByteBuffer {
    public static void main(String[] args) {
        // 1. 字符串转buffer , 此时还处于写模式
        ByteBuffer buffer1 = ByteBuffer.allocate(16);
        buffer1.put("hello".getBytes());
        ByteBufferUtil.debugAll(buffer1);

        // 2. charset转buffer  自动切换到读模式
        ByteBuffer buffer2 = StandardCharsets.UTF_8.encode("hello");
        ByteBufferUtil.debugAll(buffer2);

        // 3. wrap  自动切换到读模式
        final ByteBuffer buffer3 = ByteBuffer.wrap("hello".getBytes());
        ByteBufferUtil.debugAll(buffer3);

        // 4. decode  能解析读模式的
        final String s = StandardCharsets.UTF_8.decode(buffer2).toString();
        System.out.println(s);

        // 5. decode  只能解析读模式的,buffer1还是写模式所以要手动切换
        buffer1.flip();
        final String s2 = StandardCharsets.UTF_8.decode(buffer1).toString();
        System.out.println(s2);
    }
}
