package com.lin.netty.nioBasic.networkMultiThread;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

public class MultiThreadClient {
    public static void main(String[] args) throws IOException {
        SocketChannel sc = SocketChannel.open();
        sc.connect(new InetSocketAddress("localhost", 8080));
        // 输出那里打断点,输出表达式
        sc.write(Charset.defaultCharset().encode("65346536\n"));

        // 阻塞先别结束，等待输入
        System.in.read();
    }
}
