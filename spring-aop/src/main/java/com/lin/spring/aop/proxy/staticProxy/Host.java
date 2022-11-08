package com.lin.spring.aop.proxy.staticProxy;

import org.apache.tomcat.util.net.jsse.JSSEUtil;

/**
 * 房东 真实角色
 */
public class Host implements Rent{
    @Override
    public void rent() {
        System.out.println("房东租房子");
    }
}
