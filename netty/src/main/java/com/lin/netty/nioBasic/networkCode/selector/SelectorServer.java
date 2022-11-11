package com.lin.netty.nioBasic.networkCode.selector;

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
 * 先注册一个ServerSocketChannel进selector里专门获取连接生成SokertChannel的，然后生成的SokertChannel注册进selector里，有事件进来就迭代处理
 *
 * SelectorImpl 里的 publicSelectedKeys  publicKeys
 *
 *     // Public views of the key sets
 *     private Set<SelectionKey> publicKeys;             // Immutable    整个selector对应的事件，不会被移除  selector.keys()
 *     private Set<SelectionKey> publicSelectedKeys;     // Removal allowed, but not addition    selector.selectedKeys()
 *     需要移除的事件，不然下次获取上一次事件，而上次事件取不到值
 *
 */
@Slf4j
public class SelectorServer {

    public static void main(String[] args) throws IOException {
        ServerSocketChannel channel = ServerSocketChannel.open();
        channel.bind(new InetSocketAddress(8080));
        System.out.println(channel);
        Selector selector = Selector.open();
        channel.configureBlocking(false); // select模式 channel必须是非阻塞模式
        channel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            // 阻塞直到有绑定事件发生
            int count = selector.select();
            log.debug("select count: {}", count);

            // 获取所有事件
            Set<SelectionKey> keys = selector.selectedKeys();

            // 遍历所有事件，逐一处理
            Iterator<SelectionKey> iter = keys.iterator();
            while (iter.hasNext()) {
                SelectionKey key = iter.next();
                log.debug("key= "+key);
                // 判断事件类型
                if (key.isAcceptable()) {
                    ServerSocketChannel c = (ServerSocketChannel) key.channel();
                    // 必须处理,如果不处理，会一直循环
                    SocketChannel sc = c.accept();
                    sc.configureBlocking(false);
                    sc.register(selector, SelectionKey.OP_READ);
                    log.debug("连接已建立: {}", sc);
                } else if (key.isReadable()) {
                    SocketChannel sc = (SocketChannel) key.channel();
                    ByteBuffer buffer = ByteBuffer.allocate(128);
                    int read = sc.read(buffer);
                    if (read == -1) {
                        key.cancel();
                        sc.close();
                    } else {
                        buffer.flip();
                        ByteBufferUtil.debugRead(buffer);
                    }
                }
                // 处理完毕，必须将事件移除
                iter.remove();
            }
        }
    }
}
