/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2023-2023. All rights reserved.
 */

package com.lin.spring.designMode.proxy.dynamicProxy;

import com.lin.spring.designMode.proxy.staticProxy.Rent;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyInvocationHandler implements InvocationHandler {

    //被代理的接口
    private Rent rent;

    public void setRent(Rent rent) {
        this.rent = rent;
    }

    //生成得到代理类
    // 1.类加载器 2.接口实现类 3.当前类
    public Object getProxy() {
        return Proxy.newProxyInstance(this.getClass().getClassLoader(), rent.getClass().getInterfaces(), this);
    }

    //处理代理实例，并返回结果
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //动态代理的本质就是使用反射机制实现
        seeHouse();
        Object invoke = method.invoke(rent, args);
        contract();
        fare();
        return invoke;
    }

    public void seeHouse() {
        System.out.println("客户看房子");
    }

    public void fare() {
        System.out.println("收中介费");
    }

    //合同
    public void contract(){
        System.out.println("租赁合同");
    }
}
