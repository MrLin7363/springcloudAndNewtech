package com.lin.sharding.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lin.sharding.domain.TbDeviceInfo;
import com.lin.sharding.domain.TbDeviceType;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceTypeMapper extends BaseMapper<TbDeviceType> {
    @Select("select a.id,a.device_id,a.device_intro,b.device_type from" +
        " tb_device_info a left join tb_device b on a.device_id = b.device_id ")
        List<TbDeviceInfo> queryDeviceInfo();
}
