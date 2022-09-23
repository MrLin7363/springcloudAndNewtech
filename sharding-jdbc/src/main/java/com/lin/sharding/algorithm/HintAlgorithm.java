package com.lin.sharding.algorithm;

import org.apache.shardingsphere.api.sharding.hint.HintShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.hint.HintShardingValue;

import java.util.Arrays;
import java.util.Collection;

public class HintAlgorithm implements HintShardingAlgorithm<Long> {

    @Override
    public Collection<String> doSharding(Collection<String> collection, HintShardingValue<Long> hintShardingValue) {
        // 根据指定参数强制路由
        String tableName = hintShardingValue.getLogicTableName() + "_" + hintShardingValue.getValues().toArray()[0];
        if (!collection.contains(tableName)) {
            throw new UnsupportedOperationException("表：" + tableName
                + "，不存在");
        }
        return Arrays.asList(tableName);
    }
}
