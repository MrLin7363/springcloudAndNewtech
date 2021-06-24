package com.junlin.product.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品实体类
 */
@Data
@Entity
@Table(name="product")
public class Product {

	@Id

	private Long id;
	private String productName;
	private Integer status;
	private BigDecimal price;
	private String productDesc;
	private String caption;
	private Integer inventory;
}
