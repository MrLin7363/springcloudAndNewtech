package com.lin.netty.nettybasic.c3eventloop;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;

import java.util.concurrent.TimeUnit;

public class TestEventLoop {
    public static void main(String[] args) {
        // 内部创建了两个 EventLoop, 每个 EventLoop 维护一个线程
        EventLoopGroup group = new NioEventLoopGroup(2); // io任务,普通任务,定时任务
        //        EventLoopGroup group = new DefaultEventLoopGroup(2); // 普通任务,定时任务
        System.out.println(group.next());
        System.out.println(group.next());
        System.out.println(group.next()); // 循环会回到第一个,也可以使用增强for循环一次读完
        // 1.执行普通任务
        group.next().execute(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("普通任务");
        });

        // 2.定时任务
        group.next().scheduleAtFixedRate(()->{
            System.out.println("scheduleAtFixedRate");
        },1,1, TimeUnit.SECONDS); // 延迟1s,间隔1s
        System.out.println("main");
    }
}
