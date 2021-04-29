package com.product.test;

import cn.itcast.product.controller.VO.ProductReqVO;
import cn.itcast.product.dao.ProductDao;
import cn.itcast.product.entity.Product;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

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
