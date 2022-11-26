package com.lin.netty.nettybasic.c6ByteBuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;

public class TestCompositBuf {
    public static void main(String[] args) {
        ByteBuf buf1 = ByteBufAllocator.DEFAULT.buffer(5);
        ByteBuf buf2 = ByteBufAllocator.DEFAULT.buffer(5);
        buf1.writeBytes(new byte[]{1, 2, 3, 4, 5});
        buf2.writeBytes(new byte[]{6, 7, 8, 9, 10});

        // 进行了数据的内存复制操作
//        ByteBuf buf3 = ByteBufAllocator.DEFAULT.buffer(buf1.readableBytes() + buf2.readableBytes());
//        buf3.writeBytes(buf1);
//        buf3.writeBytes(buf2);

        CompositeByteBuf buf3 = ByteBufAllocator.DEFAULT.compositeBuffer();
        // true 表示增加新的 ByteBuf 自动递增 write index, 否则 write index 会始终为 0
        buf3.addComponents(true, buf1, buf2);

        System.out.println(ByteBufUtil.prettyHexDump(buf1));
        System.out.println(ByteBufUtil.prettyHexDump(buf2));
        System.out.println(ByteBufUtil.prettyHexDump(buf3));

        // unpooled
        // 当包装 ByteBuf 个数超过一个时, 底层使用了 CompositeByteBuf
        ByteBuf buffer3 = Unpooled.wrappedBuffer(buf1, buf2);
        System.out.println(ByteBufUtil.prettyHexDump(buffer3));

    }
}
