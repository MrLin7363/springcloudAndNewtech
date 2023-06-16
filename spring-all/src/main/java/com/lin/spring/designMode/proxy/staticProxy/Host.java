/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2023-2023. All rights reserved.
 */

package com.lin.spring.designMode.proxy.staticProxy;

/**
 * 房东 真实角色
 */
public class Host implements Rent{
    @Override
    public void rent() {
        System.out.println("房东租房子");
    }
}
