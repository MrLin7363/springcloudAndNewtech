package com.lin.sharding.algorithm.statistic;

import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 查询时的范围分片
 */
public class RangeAlgorithm implements RangeShardingAlgorithm<Long> {
    public static final Map<String, List<String>> TABLES_MAP = new HashMap<>();

    private static final String TABLE_NAME = "proxy_statistic_new";



    /**
     * 计算范围内需要查几张表
     *
     * @param collection collection
     * @param rangeShardingValue rangeShardingValue
     * @return Collection<String>
     */
    @Override
    public Collection<String> doSharding(Collection<String> collection, RangeShardingValue<Long> rangeShardingValue) {
        String logicTableName = rangeShardingValue.getLogicTableName();
        String beginStr = PreciseAlgorithm.getPreciseTime(rangeShardingValue.getValueRange().lowerEndpoint());
        String endStr = PreciseAlgorithm.getPreciseTime(rangeShardingValue.getValueRange().upperEndpoint());
        List<String> rangeTables = getRangeTableInCollection(collection, beginStr, endStr, logicTableName);
//        return Arrays.asList("proxy_statistic_20230501","proxy_statistic_2023051");
        return rangeTables;
    }

    private static List<String> getRangeTableInCollection(Collection<String> statisticTables, String beginStr,
        String endStr, String logicTableName) {
        List<String> tables = new ArrayList<>();
        boolean reach = false;
        for (String t : statisticTables) {
            if (t.equals(logicTableName + "_" + beginStr)) {
                reach = true;
            }
            if (reach) {
                tables.add(t);
                if (t.equals(logicTableName + "_" + endStr)) {
                    break;
                }
            }
        }
        return tables;
    }

    public static List<String> getRangeTableNative(long beginTime, long endTime) {
        String beginStr = PreciseAlgorithm.getPreciseTime(beginTime);
        String endStr = PreciseAlgorithm.getPreciseTime(endTime);
        String beginYear = beginStr.substring(0, 4);
        String endYear = endStr.substring(0, 4);
        List<String> tables = new ArrayList<>();
        if (beginYear.equals(endYear)) {
            tables.addAll(TABLES_MAP.get(beginYear.substring(0, 4)));
        } else {
            tables.addAll(TABLES_MAP.get(beginYear.substring(0, 4)));
            tables.addAll(TABLES_MAP.get(endYear.substring(0, 4)));
        }
        return getRangeTableInCollection(tables, beginStr, endStr, TABLE_NAME);
    }


    /**
     * 这个方法是自己根据yaml文件自己初始化表，这样不好，直接通过已经初始化好的shardingDataSource里获取就可以
     */
    static {
//        String year;
//        String month;
//        for (int i = 2023; i < 2033; i++) {
//            year = String.valueOf(i);
//            TABLES_MAP.put(year, new LinkedList<>());
//            for (int j = 1; j <= 12; j++) {
//                if (j < 10) {
//                    month = "0" + j;
//                } else {
//                    month = String.valueOf(j);
//                }
//                for (int k = 0; k < 2; k++) {
//                    if (k == 0) {
//                        TABLES_MAP.get(year).add("proxy_statistic_new_" + year + month + "01");
//                    } else {
//                        TABLES_MAP.get(year).add("proxy_statistic_new_" + year + month + "15");
//                    }
//                }
//            }
//        }
    }

    /**
     * 计算范围内需要查几张表- 这种方法不适合后期改动分表频率， 建议从ShardingData获取
     *
     * @param beginTime beginTime
     * @param endTime endTime
     * @return 表名
     */
    public static List<String> getPreciseTable(long beginTime, long endTime, String logicTableName) {
        if (beginTime > endTime) {
            throw new UnsupportedOperationException(("范围查询错误：开始时间>结束时间"));
        }
        String beginStr = PreciseAlgorithm.getPreciseTime(beginTime);
        int beginYear = Integer.valueOf(beginStr.substring(0, 4));
        int beginMonth = Integer.valueOf(beginStr.substring(4, 6));
        int beginDay = Integer.valueOf(beginStr.substring(6));
        String endStr = PreciseAlgorithm.getPreciseTime(endTime);
        int endYear = Integer.valueOf(beginStr.substring(0, 4));
        int endMonth = Integer.valueOf(endStr.substring(4, 6));
        int endDay = Integer.valueOf(endStr.substring(6));
        String year;
        String month;
        String day;
        Set<String> dates = new HashSet<>();
        for (int i = beginYear; i <= endYear; i++) {
            year = String.valueOf(i);
            for (int j = beginMonth; j <= endMonth; j++) {
                if (j < 10) {
                    month = "0" + j;
                } else {
                    month = String.valueOf(j);
                }
                for (int k = 0; k < 2; k++) {
                    if (i == beginYear && j == beginMonth && i == endYear && j == endMonth) {
                        if (k == 0) {
                            day = beginDay == 1 ? "01" : "15";
                        } else {
                            day = endDay == 1 ? "01" : "15";
                        }
                    } else if (i == beginYear && j == beginMonth) {
                        day = beginDay == 1 ? "01" : "15";
                        if (k == 1) {
                            day = beginDay == 1 ? "15" : "01";
                        }
                    } else if (i == endYear && j == endMonth) {
                        if (endDay == 15) {
                            day = k == 0 ? "01" : "15";
                        } else {
                            day = endDay == 1 ? "01" : "15";
                        }
                    } else {
                        day = k == 0 ? "01" : "15";
                    }
                    dates.add(year + month + day);
                }
            }
        }
        dates.forEach(e -> System.out.println(e));
        return new ArrayList<>(dates);
    }
}
