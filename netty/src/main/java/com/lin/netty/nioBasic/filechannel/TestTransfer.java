package com.lin.netty.nioBasic.filechannel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

public class TestTransfer {
    public static void main(String[] args) {
        try (
            FileChannel from = new FileInputStream("netty/helloword/data.txt").getChannel();
            final FileChannel to = new FileOutputStream("netty/helloword/to.txt").getChannel();
        ) {
            // 效率高，底层利用操作系统零拷贝技术优化， 限制最大2g
//            from.transferTo(0, from.size(), to);
            long size = from.size();
            // left 变量代表还剩余多少字节
            for (long left = size; left > 0; ) {
                System.out.println("position:" + (size - left) + " left:" + left);
                left -= from.transferTo((size - left), left, to);
            }
        } catch (Exception e) {

        }
    }
}
