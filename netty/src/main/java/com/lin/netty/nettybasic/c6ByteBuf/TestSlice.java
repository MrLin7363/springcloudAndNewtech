package com.lin.netty.nettybasic.c6ByteBuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

public class TestSlice {

    public static void main(String[] args) {
        ByteBuf origin = ByteBufAllocator.DEFAULT.buffer(10);
        origin.writeBytes(new byte[]{'a','b','c','d','e','f','g','h','i','j'});
        TestByteBuf.log(origin);

        // 切片过程中没有发生数据复制,最大长度固定
        ByteBuf slice1 = origin.slice(0, 5);
        ByteBuf slice2 = origin.slice(5, 5);
        TestByteBuf.log(slice1);
        TestByteBuf.log(slice2);

        // 切片和原来是同一块内存
        slice1.setByte(0,'z');
        TestByteBuf.log(origin);

        // 切片增加引用+1=2
        slice1.retain();
        slice2.retain();

        System.out.println("释放原有ByteBuf内存");
        origin.release(); // 释放原始引用 会使切片引用-1

        TestByteBuf.log(slice1); // 由于原有内存释放了，如果没有retain则这里报错
        TestByteBuf.log(slice2);

        // 正确写法：先释放原始，再释放切片
        slice1.release();
        slice2.release();
    }
}
