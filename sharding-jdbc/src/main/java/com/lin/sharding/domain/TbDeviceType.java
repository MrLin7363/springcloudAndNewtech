package com.lin.sharding.domain;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/*
CREATE TABLE `tb_device_type` (
`type_id` int NOT NULL AUTO_INCREMENT,
`type_name` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
PRIMARY KEY (`type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
 */
@Data
@TableName("tb_device_type")
public class TbDeviceType {
    private Integer typeId;

    private String typeName;

    private String deviceIntro;
}
