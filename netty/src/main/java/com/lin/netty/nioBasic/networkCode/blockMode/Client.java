package com.lin.netty.nioBasic.networkCode.blockMode;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

/**
 * debug启动server 可以开启多个客户端
 */
public class Client {

    public static void main(String[] args) throws IOException {
        SocketChannel sc = SocketChannel.open();
        sc.connect(new InetSocketAddress("localhost", 8080));
        // 输出那里打断点,输出表达式
        // sc.write(Charset.defaultCharset().encode("lin"))
        sc.write(Charset.defaultCharset().encode("lin"));
        System.out.println("waiting...");
    }
}
