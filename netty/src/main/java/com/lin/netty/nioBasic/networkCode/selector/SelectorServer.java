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
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;

/**
 * selector 3
 *
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
            // 阻塞直到有绑定事件发生  根据 publicKeys 的监听事件处理
            int count = selector.select();
            log.debug("select count: {}", count);

            // 获取所有事件  publicSelectedKeys
            Set<SelectionKey> keys = selector.selectedKeys();

            // 遍历所有事件，逐一处理
            Iterator<SelectionKey> iter = keys.iterator();
            while (iter.hasNext()) {
                SelectionKey key = iter.next();
                log.debug("key= "+key);
                // 处理完毕，必须将事件移除  Set<SelectionKey> publicSelectedKeys
                iter.remove();
                // 判断事件类型
                if (key.isAcceptable()) {
                    ServerSocketChannel c = (ServerSocketChannel) key.channel();
                    // 必须处理,如果不处理事件，会一直循环；因为selectors的accpet事件不会删除，
                    // 如果不处理，虽然下面的publicSelectedKeys删除了，但是select()时又会加入新的publicSelectedKeys
                    SocketChannel sc = c.accept();
                    sc.configureBlocking(false);
                    sc.register(selector, SelectionKey.OP_READ);
                    log.debug("连接已建立: {}", sc);
                } else if (key.isReadable()) {
                    try {
                        SocketChannel sc = (SocketChannel) key.channel();
                        ByteBuffer buffer = ByteBuffer.allocate(4);
                        int read = sc.read(buffer);
                        if (read == -1) { // 正常断开 read 方法返回值-1 ， 如果buffer没有读完也会继续读
                            key.cancel(); // 标识下次不再处理这种事件， select()
                        } else {
                            buffer.flip();
                            ByteBufferUtil.debugRead(buffer);
                            System.out.println(Charset.defaultCharset().decode(buffer)); // buffer 4个字节只能输出半个数字
                        }
                    } catch (IOException e) {
                        // 如果客户端异常断开，需要取消掉，否则造成服务端出现异常 sc.read(buffer);
                        e.printStackTrace();
                        // 如果不处理这个key， 客户端断开，会产生一个读事件,不断循环
                        key.cancel();
                    }
                }
            }
        }
    }
}
