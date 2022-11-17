package com.lin.netty.nioBasic.aio;

import com.lin.netty.nioBasic.byteBuffer.ByteBufferUtil;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

@Slf4j
public class AioDemo {

    public static void main(String[] args) throws IOException {
        try {
            AsynchronousFileChannel s =
                AsynchronousFileChannel.open(
                    Paths.get("netty/helloword/data.txt"), StandardOpenOption.READ);
            ByteBuffer buffer = ByteBuffer.allocate(2);
            log.debug("begin...");
            s.read(buffer, 0, buffer, new CompletionHandler<Integer, ByteBuffer>() {
                @Override
                public void completed(Integer result, ByteBuffer attachment) {
                    log.debug("read completed...{}", result);
                    log.debug("isDaemon {}", Thread.currentThread().isDaemon()); // 主线程结束守护线程也结束
                    attachment.flip();
                    ByteBufferUtil.debugRead(attachment);
                }

                @Override
                public void failed(Throwable exc, ByteBuffer attachment) {
                    log.debug("read failed...");
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
        log.debug("end...");
        System.in.read();
    }
}
