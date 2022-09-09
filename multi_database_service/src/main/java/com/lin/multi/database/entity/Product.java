package com.lin.multi.database.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 商品实体类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_person_liable")
public class Product {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String productName;

    private Integer status;

    private BigDecimal price;

    private String productDesc;

    private String caption;

    private Integer inventory;
}
