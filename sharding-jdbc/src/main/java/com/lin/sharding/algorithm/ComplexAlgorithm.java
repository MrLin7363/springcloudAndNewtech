package com.lin.sharding.algorithm;

import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingValue;

import java.util.ArrayList;
import java.util.Collection;

/**
 * ⽤于处理使⽤多键作为分⽚键进⾏分⽚的场景，包含多个分⽚键的逻辑较复杂
 */
public class ComplexAlgorithm implements ComplexKeysShardingAlgorithm<Integer> {


    @Override
    public Collection<String> doSharding(Collection<String> collection, ComplexKeysShardingValue<Integer> complexKeysShardingValue) {
        Collection<Integer> deviceTypes = complexKeysShardingValue.getColumnNameAndShardingValuesMap().get(
            "device_type");

        //存放指定的表
        Collection<String> tables = new ArrayList<>();
        for (Integer deviceType : deviceTypes) {
            //根据deviceType的奇偶选择哪个数据库
            String tableName =
                complexKeysShardingValue.getLogicTableName()+ "_" + (deviceType % 2);
            tables.add(tableName);
        }
        return tables;
    }
}
