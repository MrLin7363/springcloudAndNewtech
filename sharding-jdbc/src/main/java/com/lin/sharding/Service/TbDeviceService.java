package com.lin.sharding.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lin.sharding.mapper.DeviceMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class TbDeviceService {

    @Autowired
    private DeviceMapper deviceMapper;

    @PostConstruct
    void init() {
        System.out.println(deviceMapper.selectList(new LambdaQueryWrapper<>()));
    }
}
