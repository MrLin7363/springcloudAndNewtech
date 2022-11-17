package com.lin.netty.nioBasic.networkCode.borderMode;

import com.lin.netty.nioBasic.byteBuffer.ByteBufferUtil;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * 处理消息边界 4
 */
@Slf4j
public class BorderServer {
    private static void split(ByteBuffer source) {
        source.flip();
        for (int i = 0; i < source.limit(); i++) {
            if (source.get(i) == '\n') {
                int length = i + 1 - source.position();
                ByteBuffer target = ByteBuffer.allocate(length);
                for (int j = 0; j < length; j++) {
                    target.put(source.get());
                }
                ByteBufferUtil.debugAll(target);
            }
        }
        // 如果没遇到 \n就不会读
        source.compact();
    }

    public static void main(String[] args) throws IOException {
        ServerSocketChannel channel = ServerSocketChannel.open();
        channel.bind(new InetSocketAddress(8080));
        System.out.println(channel);
        Selector selector = Selector.open();
        channel.configureBlocking(false);
        channel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            int count = selector.select();
            log.debug("select count: {}", count);

            Set<SelectionKey> keys = selector.selectedKeys();

            Iterator<SelectionKey> iter = keys.iterator();

            while (iter.hasNext()) {
                SelectionKey key = iter.next();
                log.debug("key= " + key);
                iter.remove();
                if (key.isAcceptable()) {
                    ServerSocketChannel c = (ServerSocketChannel) key.channel();
                    SocketChannel sc = c.accept();
                    sc.configureBlocking(false);
                    ByteBuffer buffer = ByteBuffer.allocate(10); // 附件attachment
                    final SelectionKey selectionKey = sc.register(selector, SelectionKey.OP_READ, buffer);
                    // selectionKey.interestOps( SelectionKey.OP_READ);  register已经绑定了
                    log.debug("连接已建立: {}", sc);
                } else if (key.isReadable()) {
                    try {
                        SocketChannel sc = (SocketChannel) key.channel();
                        // 获取selectionKey上关联的附件
                        ByteBuffer buffer = (ByteBuffer) key.attachment();
                        int read = sc.read(buffer);
                        if (read == -1) {
                            key.cancel();
                        } else {
                            split(buffer);
                            if (buffer.position() == buffer.limit()) {
                                ByteBuffer newBuffer = ByteBuffer.allocate(buffer.capacity()*2); // 新buffer扩容
                                buffer.flip();
                                newBuffer.put(buffer);
                                key.attach(newBuffer);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        key.cancel();
                    }
                }
            }
        }
    }
}
