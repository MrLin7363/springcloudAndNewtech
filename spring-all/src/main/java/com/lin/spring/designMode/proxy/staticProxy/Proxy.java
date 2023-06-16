/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2023-2023. All rights reserved.
 */

package com.lin.spring.designMode.proxy.staticProxy;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * 代理角色
 */
@AllArgsConstructor
@NoArgsConstructor
public class Proxy implements Rent{

    private Host host;

    @Override
    public void rent() {
        seeHouse();
        host.rent();
        contract();
        fare();
    }

    //看房子
    public void seeHouse(){
        System.out.println("客户看房子");
    }

    //收中介费
    public void fare(){
        System.out.println("收中介费");
    }

    //合同
    public void contract(){
        System.out.println("租赁合同");
    }
}
