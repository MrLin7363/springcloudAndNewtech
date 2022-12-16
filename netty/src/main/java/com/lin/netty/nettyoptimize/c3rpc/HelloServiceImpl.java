package com.lin.netty.nettyoptimize.c3rpc;

public class HelloServiceImpl implements HelloService {
    public String sayHello(String name) {
        System.out.println("hello " + name);
//        int i = 1 / 0;
        return "hello " + name;
    }
}
