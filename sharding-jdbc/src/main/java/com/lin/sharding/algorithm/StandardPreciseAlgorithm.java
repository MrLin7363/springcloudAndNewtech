package com.lin.sharding.algorithm;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;

/**
 * ⽤于处理使⽤单⼀键作为分⽚键的=与IN进⾏分⽚的场景
 */
public class StandardPreciseAlgorithm implements PreciseShardingAlgorithm<Long> {

    /**
     * 每个分片可以同时定义，当插入的时候会选择精确分片
     *
     * @param collection 表名列表
     * @param preciseShardingValue
     * @return
     */
    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<Long> preciseShardingValue) {
        final String logicTableName = preciseShardingValue.getLogicTableName();
        final Long colValue = preciseShardingValue.getValue();
        final String shardingKey = logicTableName + "_" + (colValue % 2);
        if (!collection.contains(shardingKey)) {
            throw new UnsupportedOperationException(("表不存在" + shardingKey));
        }
        return shardingKey;
    }
}
