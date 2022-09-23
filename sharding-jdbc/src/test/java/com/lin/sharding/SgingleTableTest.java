package com.lin.sharding;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lin.sharding.domain.TbDevice;
import com.lin.sharding.mapper.DeviceMapper;

import org.apache.shardingsphere.api.hint.HintManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles(value = "subTableStrategy")
public class SgingleTableTest {

    @Autowired
    private DeviceMapper deviceMapper;

    @Test
    public void initData() {
        System.out.println(deviceMapper.selectList(new LambdaQueryWrapper<>()));
        for (int i = 0; i < 10; i++) {
            TbDevice device = new TbDevice();
            device.setDeviceId((long) i);
            device.setDeviceType(i);
            deviceMapper.insert(device);
        }
    }

    @Test
    public void queryDeviceByRange() {
        QueryWrapper<TbDevice> queryWrapper = new QueryWrapper<>();
        queryWrapper.between("device_id", 1, 10);
        List<TbDevice> deviceList = deviceMapper.selectList(queryWrapper);
        System.out.println(deviceList);
    }

    /*
    复合分片
     */
    @Test
    public void queryDeviceByRangeAndDeviceType() {
        QueryWrapper<TbDevice> queryWrapper = new QueryWrapper<>();
        queryWrapper.between("device_id", 1, 10);
        queryWrapper.eq("device_type", 5);
        List<TbDevice> deviceList = deviceMapper.selectList(queryWrapper);
        System.out.println(deviceList);
    }

    /*
    强制分片
     */
    @Test
    public void queryByHint() {
        HintManager hintManager = HintManager.getInstance();
        //指定强制路由的表
        hintManager.addTableShardingValue("tb_device", 0);
        List<TbDevice> deviceList = deviceMapper.selectList(null);
        System.out.println(deviceList);
        hintManager.close();
    }

    /*
    分页查询  过滤  帅选
     */
    @Test
    public void queryByPage() {
        QueryWrapper<TbDevice> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("device_id", Arrays.asList((long)10, (long)13));
        queryWrapper.last("order by device_id limit 0,6");
        List<TbDevice> deviceList = deviceMapper.selectList(queryWrapper);
        System.out.println(deviceList);
    }
}
