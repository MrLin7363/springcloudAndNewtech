package com.lin.sharding;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lin.sharding.domain.Statistic;
import com.lin.sharding.mapper.StatisticMapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles(value = "rangeTimeStrategy")
public class RangeTimeTest {

    @Autowired
    private StatisticMapper statisticMapper;

    @Test
    public void testInsert() {
        Statistic statistic = new Statistic();
        statistic.setRequestIp("127.0.0.1");
        statistic.setProxySystem("iiii");
        statistic.setProxyStart(1682870400306l);
        statistic.setProxyUri("/test");
        statistic.setDuring(1);
        statistic.setStatus(200);
        statistic.setProxyFrom("REQUEST");
        statisticMapper.insert(statistic);
    }

    @Test
    public void testRangeQuery() {
        QueryWrapper<Statistic> queryWrapper = new QueryWrapper<>();
        queryWrapper.between("proxy_start", 1682870400306l, 1682880400306l);
        List<Statistic> statisticList = statisticMapper.selectList(queryWrapper);
        System.out.println(statisticList);
    }

    @Test
    public void testNativeSqlQuery() {
        // 包含子查询会使用原始SQL
        List<Map<String, Object>> systemStatistic = statisticMapper.getSystemStatistic();
        System.out.println(systemStatistic.toString());
    }

    @Test
    public void test() {
        List<Map<String, Object>> systemStatistic = statisticMapper.getSystem();
        System.out.println(systemStatistic.toString());
    }

    @Test
    public void testNativeTimeSqlQuery() {
        List<Map<String, Object>> systemStatistic = statisticMapper.getSystemStatisticFromTime();
        System.out.println(systemStatistic.toString());
    }
}
