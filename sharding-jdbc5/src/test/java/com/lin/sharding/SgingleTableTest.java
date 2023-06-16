package com.lin.sharding;

import com.lin.sharding.mapper.OrderMapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SgingleTableTest {

    @Autowired
    private OrderMapper orderMapper;

    @Test
    public void queryDeviceByRange() {
        List<Map<String, Object>> orderByTime = orderMapper.getOrderByTime(1683767809000l,1684286209000l);
        System.out.println(orderByTime);
    }
}
