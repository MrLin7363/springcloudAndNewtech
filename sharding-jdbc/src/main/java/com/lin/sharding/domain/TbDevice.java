package com.lin.sharding.domain;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/*
CREATE TABLE `tb_device_0` (
  `device_id` bigint(20) NOT NULL,
  `device_type` int(11) DEFAULT NULL,
  PRIMARY KEY (`device_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 */
@Data
@TableName("tb_device")
public class TbDevice {
    private Long deviceId;

    private int deviceType;
}
