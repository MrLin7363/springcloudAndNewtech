package com.lin.netty.nettybasic.c4Future;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Slf4j
public class TestJdkFuture {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        Future<Integer> future = executorService.submit(new Callable<Integer>() {

            @Override
            public Integer call() throws Exception {
                log.debug("执行计算");
                Thread.sleep(1500);
                return 50;
                // 任务执行完了，线程会把信息填入future
            }
        });
        log.debug("等待结果");
        // 主线程通过future获取结果，阻塞在get
        log.debug("结果是{}", future.get());
    }
}
