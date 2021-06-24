package com.junlin.product.controller.VO;

import lombok.Data;

import java.util.List;

/**
 * @author: chenjunlin
 * @since: 2021/04/28
 * @descripe:
 */
@Data
public class ProductReqVO {

    public List<ProductId> productList;

    @Data
    public static class ProductId {
        private Long pid;
    }

}
