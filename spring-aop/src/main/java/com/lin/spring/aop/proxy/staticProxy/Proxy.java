package com.lin.spring.aop.proxy.staticProxy;

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
