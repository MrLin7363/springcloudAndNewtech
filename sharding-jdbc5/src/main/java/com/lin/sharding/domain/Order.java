package com.lin.sharding.domain;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * CREATE TABLE `order_20230516` (
 *   `id` int(11) NOT NULL,
 *   `order_time` bigint(20) DEFAULT NULL,
 *   `order_name` varchar(255) DEFAULT NULL,
 *   PRIMARY KEY (`id`)
 * ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
 */
@Data
@TableName("order")
public class Order {
    private Long id;

    private Long orderTime;

    private String orderName;
}
