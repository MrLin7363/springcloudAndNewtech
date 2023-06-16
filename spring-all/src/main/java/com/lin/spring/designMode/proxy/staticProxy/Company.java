 

package com.lin.spring.designMode.proxy.staticProxy;

/**
 * 公司-真实角色2  静态代理需要再实现一个代理类麻烦
 */
public class Company implements Rent {
    @Override
    public void rent() {
        System.out.println("公司租房子");
    }
}
