package com.lin.netty.nettybasic.c4Future;

import io.netty.channel.EventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

@Slf4j
public class TestNettyFuture {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        NioEventLoopGroup group = new NioEventLoopGroup();
        EventLoop eventLoop = group.next();
        Future<Integer> future = eventLoop.submit(new Callable<Integer>() {

            @Override
            public Integer call() throws Exception {
                log.debug("执行计算");
                Thread.sleep(1500);
                return 50;
                // 任务执行完了，线程会把信息填入future
            }
        });
        // 1.同步方式
//        log.debug("等待结果");
//        log.debug("结果是{}", future.get());

        // 2. 异步方式
        future.addListener(new GenericFutureListener<Future<? super Integer>>() {
            @Override
            public void operationComplete(Future<? super Integer> future) throws Exception {
                // getNow无就返回null,不阻塞，因为进入监听器是已经结束
                log.debug("结果是{}",future.getNow());
            }
        });
        log.debug("exe...");
    }
}
