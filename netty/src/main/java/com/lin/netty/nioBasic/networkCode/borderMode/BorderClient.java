package com.lin.netty.nioBasic.networkCode.borderMode;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

public class BorderClient {
    public static void main(String[] args) throws IOException {
        SocketChannel sc = SocketChannel.open();
        sc.connect(new InetSocketAddress("localhost", 8080));
        // 输出那里打断点,输出表达式
        sc.write(Charset.defaultCharset().encode("hello1234678world\n")); // 超过buffer长度的

        // 第二条消息
        sc.write(Charset.defaultCharset().encode("hello65r65r6564e"));
        sc.write(Charset.defaultCharset().encode("second65r65r6564e\n")); // 两个消息,换行符一个字节

        // 阻塞先别结束，等待输入
        System.in.read();
    }
}
