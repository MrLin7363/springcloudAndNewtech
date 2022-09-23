package com.lin.sharding;

import com.lin.sharding.domain.TbDevice;
import com.lin.sharding.domain.TbDeviceInfo;
import com.lin.sharding.domain.TbDeviceType;
import com.lin.sharding.mapper.DeviceInfoMapper;
import com.lin.sharding.mapper.DeviceMapper;
import com.lin.sharding.mapper.DeviceTypeMapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles(value = "subTableStrategy_multi")
public class BingtableTest {
    @Autowired
    private DeviceInfoMapper deviceInfoMapper;

    @Autowired
    private DeviceMapper deviceMapper;

    @Autowired
    private DeviceTypeMapper deviceTypeMapper;

    @Test
    public void testInsertType() {
        for (int i = 10; i < 15; i++) {
            TbDevice device = new TbDevice();
            device.setDeviceId((long) i);
            device.setDeviceType(i);
            deviceMapper.insert(device);
            TbDeviceInfo deviceInfo = new TbDeviceInfo();
            deviceInfo.setDeviceId((long) i);
            deviceInfo.setDeviceIntro("" + i);
            deviceInfoMapper.insert(deviceInfo);
        }
    }

    /*
    笛卡尔积-需要绑定表
    绑定表
指的是分片规则一致的关系表（主表、子表），例如b_order和b_order_item，均按照
order_id分片，则此两个表互为绑定表关系。绑定表之间的多表关联查询不会出现笛卡尔积
关联，可以提升关联查询效率。
     */
    @Test
    public void testQueryDeviceInfo() {
        List<TbDeviceInfo> deviceInfos = deviceInfoMapper.queryDeviceInfo();
        deviceInfos.forEach(deviceInfo -> System.out.println(deviceInfo));
    }

    /*
    如果分库用广播表   插入时，向所有数据源广播发送sql语句查询时，只查询其中的一个数据源
     比如只有订单啥的数据分库，大部分数据在一个主库，这时候主库需要这个表，读库也需要这个表，就要设置为广播表
     */
    @Test
    public void testAddDeviceType() {
        TbDeviceType deviceType1 = new TbDeviceType();
        deviceType1.setTypeId(1);
        deviceType1.setTypeName("⼈脸考勤");
        deviceTypeMapper.insert(deviceType1);
        TbDeviceType deviceType2 = new TbDeviceType();
        deviceType2.setTypeId(2);
        deviceType2.setTypeName("⼈脸通道");
        deviceTypeMapper.insert(deviceType2);
    }

    @Test
    public void testAddDeviceTypeTest() {
        final List<TbDeviceType> tbDeviceTypes = deviceInfoMapper.queryDeviceType();
        tbDeviceTypes.forEach(deviceInfo -> System.out.println(deviceInfo));
    }
}
