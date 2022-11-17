package com.lin.netty.nioBasic.networkMultiThread;

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
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 多线程版本 nio
 * <p>
 * selector 注册事件需要等select()方法不阻塞
 * 单个Boss负责 接收连接
 * 多个Worker 负责读写事件
 */
@Slf4j
public class MultiThreadServer {

    public static void main(String[] args) throws IOException {
        Thread.currentThread().setName("boss");
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);
        ssc.bind(new InetSocketAddress(8080));

        Selector selector = Selector.open();
        ssc.register(selector, SelectionKey.OP_ACCEPT);

        // 创建多个Worker
        Worker[] workers = new Worker[2];
        for (int i = 0; i < 2; i++) {
            workers[i] = new Worker("worker" + i);
        }
        AtomicInteger index = new AtomicInteger(0);
        while (true) {
            selector.select();
            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> iter = keys.iterator();

            while (iter.hasNext()) {
                SelectionKey key = iter.next();
                if (key.isAcceptable()) {
                    key.channel();
                    SocketChannel sc = ssc.accept();
                    sc.configureBlocking(false);
                    // 注册到Worker的Selector
                    log.debug("connected {}", sc.getRemoteAddress());
                    log.debug("before register...{}", sc.getRemoteAddress());
                    workers[index.getAndIncrement() % workers.length].register(sc);
                    log.debug("after register...{}", sc.getRemoteAddress());
                }
            }

        }
    }

    static class Worker implements Runnable {
        private Selector selector;

        private volatile boolean start = false;

        private String name;

        private final ConcurrentLinkedQueue<Runnable> tasks = new ConcurrentLinkedQueue<>();

        public Worker(String name) {
            this.name = name;
        }

        public void register(SocketChannel sc) throws IOException {
            if (!start) {
                selector = Selector.open();
                new Thread(this, name).start();
                start = true;
            }
            // 消息队列方法 添加队列后wakeup selector
            //            tasks.add(() -> {
            //                try {
            //                    sc.register(selector, SelectionKey.OP_READ);
            //                } catch (IOException e) {
            //                    e.printStackTrace();
            //                }
            //            });
            // wake面up给selector一个信号量，相当于select()方法执行时，检查如果有信号量就不阻塞，所以下能注册成功
            selector.wakeup();
            sc.register(selector, SelectionKey.OP_READ);
        }

        @Override
        public void run() {
            while (true) {
                try {
                    // sc.register(selector, SelectionKey.OP_READ); 注册时间需要在select不阻塞的时候才能注册上去
                    selector.select();
                    // 消息队列方法 , 也可以使用消息队列来解决
                    //                    Runnable task = tasks.poll();
                    //                    if (task != null) {
                    //                        task.run();
                    //                    }
                    Set<SelectionKey> selectionKeys = selector.selectedKeys();
                    Iterator<SelectionKey> iter = selectionKeys.iterator();
                    while (iter.hasNext()) {
                        SelectionKey key = iter.next();
                        iter.remove();
                        if (key.isReadable()) {
                            SocketChannel sc = (SocketChannel) key.channel();
                            ByteBuffer buffer = ByteBuffer.allocate(1024);
                            sc.read(buffer);
                            buffer.flip();
                            ByteBufferUtil.debugRead(buffer);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
