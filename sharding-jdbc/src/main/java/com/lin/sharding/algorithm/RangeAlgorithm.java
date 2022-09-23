package com.lin.sharding.algorithm;

import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;

import java.util.Arrays;
import java.util.Collection;

/**
 * ⽤于处理使⽤单⼀键作为分⽚键的BETWEEN AND、>、<、>=、<=进⾏分⽚的场景
 */
public class RangeAlgorithm implements RangeShardingAlgorithm<Long> {

    @Override
    public Collection<String> doSharding(Collection<String> collection, RangeShardingValue<Long> rangeShardingValue) {
        // 返回两个物理表
        final String logicTableName = rangeShardingValue.getLogicTableName();
        return Arrays.asList(logicTableName + "_0", logicTableName + "_1");
    }
}
