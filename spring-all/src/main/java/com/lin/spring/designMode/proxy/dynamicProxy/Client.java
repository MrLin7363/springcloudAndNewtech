/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2023-2023. All rights reserved.
 */

package com.lin.spring.designMode.proxy.dynamicProxy;

import com.lin.spring.designMode.proxy.staticProxy.Company;
import com.lin.spring.designMode.proxy.staticProxy.Host;
import com.lin.spring.designMode.proxy.staticProxy.Rent;

public class Client {
    public static void main(String[] args) {
        //真实角色
        Host host = new Host();

        //代理角色：现在没有
        ProxyInvocationHandler pih = new ProxyInvocationHandler();

        //通过调用程序处理角色来处理我们要调用的接口对象
        pih.setRent(host);
        Rent proxyHost = (Rent) pih.getProxy();//这里的Proxy就是动态生成的，我们没有写
        proxyHost.rent();

        System.out.println("=======================");

        //真实角色2
        Company company = new Company();

        //代理角色：现在没有
        ProxyInvocationHandler proxyInvocationHandler = new ProxyInvocationHandler();

        //通过调用程序处理角色来处理我们要调用的接口对象
        proxyInvocationHandler.setRent(company);
        Rent proxyCompany = (Rent) proxyInvocationHandler.getProxy();//这里的Proxy就是动态生成的，我们没有写
        proxyCompany.rent();
    }
}
