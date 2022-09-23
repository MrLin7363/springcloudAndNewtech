package com.lin.sharding.domain;

import lombok.Data;

/*
CREATE TABLE `tb_device_info_0` (
  `id` bigint(20) NOT NULL,
  `device_id` bigint(20) DEFAULT NULL,
  `device_intro` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
 */
@Data
public class TbDeviceInfo {
    private Long id;

    private Long deviceId;

    private String deviceIntro;
}
