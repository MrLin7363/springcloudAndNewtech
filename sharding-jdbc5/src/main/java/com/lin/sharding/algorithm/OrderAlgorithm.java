package com.lin.sharding.algorithm;

import com.google.common.collect.Maps;

import org.apache.shardingsphere.sharding.api.sharding.standard.PreciseShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.RangeShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.StandardShardingAlgorithm;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class OrderAlgorithm implements StandardShardingAlgorithm<Long> {
    public static final Map<String, List<String>> TABLES_MAP = Maps.newHashMap();

    private static final String TABLE_NAME = "order";

    /**
     * 获取时间点所在表
     *
     * @param proxyStart proxyStart
     * @return String
     */
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
            finalString += "16";
        }
        return finalString;
    }

    private static List<String> getRangeTableInCollection(Collection<String> statisticTables, String beginStr,
        String endStr, String logicTableName) {
        if (beginStr.compareTo(endStr) > 0) {
            throw new UnsupportedOperationException(("OrderAlgorithm error: beginTime>endTime"));
        }
        List<String> tables = new ArrayList<>();
        boolean reach = false;
        for (String table : statisticTables) {
            if (table.equals(logicTableName + "_" + beginStr)) {
                reach = true;
            }
            if (reach) {
                tables.add(table);
                if (table.equals(logicTableName + "_" + endStr)) {
                    break;
                }
            }
        }
        return tables;
    }

    /**
     * 获取范围内的表集合
     *
     * @param beginTime beginTime
     * @param endTime endTime
     * @return List<String>
     */
    public static List<String> getRangeTableNative(long beginTime, long endTime) {
        String beginStr = OrderAlgorithm.getPreciseTime(beginTime);
        String endStr = OrderAlgorithm.getPreciseTime(endTime);
        String beginYear = beginStr.substring(0, 4);
        String endYear = endStr.substring(0, 4);
        List<String> tables = new ArrayList<>();
        if (beginYear.equals(endYear)) {
            tables.addAll(TABLES_MAP.get(beginYear));
        } else {
            tables.addAll(TABLES_MAP.get(beginYear));
            tables.addAll(TABLES_MAP.get(endYear));
        }
        return getRangeTableInCollection(tables, beginStr, endStr, TABLE_NAME);
    }

    @Override
    public void init(Properties properties) {
    }

    @Override
    public String getType() {
        return "CLASS_BASED ";
    }

    @Override
    public Properties getProps() {
        return null;
    }

    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<Long> preciseShardingValue) {
        String logicTableName = preciseShardingValue.getLogicTableName();
        Long colValue = preciseShardingValue.getValue();
        String shardingKey = logicTableName + "_" + getPreciseTime(colValue);
        if (!collection.contains(shardingKey)) {
            throw new UnsupportedOperationException(("OrderAlgorithm error table not exist:" + shardingKey));
        }
        return shardingKey;
    }

    @Override
    public Collection<String> doSharding(Collection<String> collection, RangeShardingValue<Long> rangeShardingValue) {
        String logicTableName = rangeShardingValue.getLogicTableName();
        String beginStr = getPreciseTime(rangeShardingValue.getValueRange().lowerEndpoint());
        String endStr;
        if (rangeShardingValue.getValueRange().hasUpperBound()) {
            endStr = getPreciseTime(rangeShardingValue.getValueRange().upperEndpoint());
        } else {
            endStr = getPreciseTime(System.currentTimeMillis());
        }
        return getRangeTableInCollection(collection, beginStr, endStr, logicTableName);
    }
}
