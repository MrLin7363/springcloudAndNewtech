/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2023-2023. All rights reserved.
 */

package com.lin.spring.designMode.proxy.staticProxy;

/**
 * 客户端访问 代理角色
 */
public class Client {
    public static void main(String[] args) {
        // 房东租房子
        Host host = new Host();
        // 代理，中介帮忙租房子，但是代理角色有附属操作，比如收中介费
        Proxy proxy = new Proxy(host);
        // 不用面对房东直接找中介租房
        proxy.rent();
    }
}
