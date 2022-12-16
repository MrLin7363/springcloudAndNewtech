package com.lin.netty.nettyoptimize.c3rpc;

import java.util.concurrent.atomic.AtomicInteger;

public class SequenceIdGenerator {
    private final static AtomicInteger id=new AtomicInteger(1);

    public static int getSequenceId(){
        return id.getAndIncrement();
    }

}
