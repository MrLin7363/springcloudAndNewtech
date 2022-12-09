package com.lin.netty.nettyoptimize.c2parameter.block;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * bio 测试全连接队列
 */
public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(8888, 2);
        Socket accept = ss.accept();
        System.out.println(accept);
        System.in.read();
    }
}
