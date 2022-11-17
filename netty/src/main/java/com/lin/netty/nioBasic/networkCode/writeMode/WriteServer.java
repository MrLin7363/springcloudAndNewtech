package com.lin.netty.nioBasic.networkCode.writeMode;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;

/**
 * 写服务 5
 */
public class WriteServer {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);
        ssc.bind(new InetSocketAddress(8080));

        Selector selector = Selector.open();
        ssc.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            selector.select();
            Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
            while (iter.hasNext()) {
                SelectionKey key = iter.next();
                iter.remove();
                if (key.isAcceptable()) {
                    final SocketChannel sc = ssc.accept();
                    sc.configureBlocking(false);
                    final SelectionKey scKey = sc.register(selector, 0);
                    scKey.interestOps(SelectionKey.OP_READ);

                    // 1. 向客户端发送大量数据，由于数据过大，不会一次性发完
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < 7000000; i++) {
                        sb.append('a');
                    }
                    ByteBuffer buffer = Charset.defaultCharset().encode(sb.toString());

                    // 2. 返回代表实际写入的字节数
                    int write = sc.write(buffer);
                    System.out.println(write);

                    // 3. 判断是否有剩余内容，不用while循环写，因为可能系统缓存区满了，写为0，
                    // 下面逻辑监听写事件，等缓存区够时再写，此时可以处理其他时间
                    if (buffer.hasRemaining()) {
                        // 4. 关注可写事件 避免影响原来的事件，所以加上原来的
                        scKey.interestOps(scKey.interestOps() + SelectionKey.OP_WRITE);
                        // scKey.interestOps(scKey.interestOps() | SelectionKey.OP_WRITE);
                        // 5.未写完的数据挂到scKey上
                        scKey.attach(buffer);
                    }
                } else if (key.isWritable()) {
                    ByteBuffer buffer = (ByteBuffer) key.attachment();
                    SocketChannel sc = (SocketChannel) key.channel();
                    int write = sc.write(buffer);
                    System.out.println(write);
                    // 6.清理操作
                    if (!buffer.hasRemaining()) {
                        key.attach(null);
                        // 7. 不需再关注写事件
                        key.interestOps(key.interestOps() - SelectionKey.OP_WRITE);
                    }
                }
            }
        }
    }
}
