package com.lin.sharding.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lin.sharding.domain.TbDeviceInfo;
import com.lin.sharding.domain.TbDeviceType;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceInfoMapper extends BaseMapper<TbDeviceInfo> {
    @Select("select a.id,a.device_id,a.device_intro,b.device_type from" +
        " tb_device_info a left join tb_device b on a.device_id = b.device_id ")
        List<TbDeviceInfo> queryDeviceInfo();

    @Select("select t.type_id,t.type_name,info.device_intro from tb_device_type t inner join tb_device_info info on" +
        " t.type_id=info.device_id ")
    List<TbDeviceType> queryDeviceType();

    @Select("select t.type_id,t.type_name,info.device_intro from tb_device_info info inner join tb_device_type t on" +
        " t.type_id=info.device_id ")
    List<TbDeviceType> queryDeviceType2();
}
