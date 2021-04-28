package com.product.test;

import cn.itcast.product.dao.ProductDao;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author: chenjunlin
 * @since: 2021/04/28
 * @descripe:
 */
public class SpringTest extends BaseTest {

    @Autowired
    private ProductDao productDao;

    @Test
    public void stringTest(){
        Long id=2L;
        System.out.println(productDao.findById(id).get().toString());
    }

}
