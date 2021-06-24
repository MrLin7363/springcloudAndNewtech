package com.product.test;

import com.junlin.product.controller.VO.ProductReqVO;
import com.junlin.product.entity.Product;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: chenjunlin
 * @since: 2021/04/28
 * @descripe:
 */
public class NoSpringTest {

    @Test
    public void streamMap(){
        ProductReqVO reqVO=new ProductReqVO();
        List<ProductReqVO.ProductId> productLists=new ArrayList<>();
        ProductReqVO.ProductId produc=new ProductReqVO.ProductId();
        produc.setPid(3L);
        ProductReqVO.ProductId produc2=new ProductReqVO.ProductId();
        produc2.setPid(3L);
        productLists.add(produc);
        productLists.add(produc2);
        reqVO.setProductList(productLists);
        // 选出一个值
        List<Product> resList = reqVO.getProductList().stream().map(
                productListOne -> {
                    Product product = new Product();
                    product.setId(productListOne.getPid());
                    return product;
                }
        ).collect(Collectors.toList());
    }

}
