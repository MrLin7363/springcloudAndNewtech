package com.lin.sharding.algorithm.statistic;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

/**
 * ⽤于处理使⽤单⼀键作为分⽚键的=与IN进⾏分⽚的场景 插入时
 */
public class PreciseAlgorithm implements PreciseShardingAlgorithm<Long> {

    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<Long> preciseShardingValue) {
        final String logicTableName = preciseShardingValue.getLogicTableName();
        final Long colValue = preciseShardingValue.getValue();
        String shardingKey = logicTableName + "_" + (colValue); // colValue 是传入的proxy_start
        shardingKey = "proxy_statistic_2023051";
        if (!collection.contains(shardingKey)) {
            throw new UnsupportedOperationException(("表不存在" + shardingKey));
        }
        return shardingKey;
    }

    public static String getPreciseTime(long proxyStart) {
        Date date = new Date(proxyStart);
        SimpleDateFormat sd = new SimpleDateFormat("yyyyMMdd");
        String oldDate = sd.format(date);
        String dayString = oldDate.substring(6);
        String finalString = oldDate.substring(0, 6);
        Integer days = Integer.valueOf(dayString);
        if (days <= 15) {
            finalString += "01";
        } else {
            finalString += "15";
        }
        return finalString;
    }

}
