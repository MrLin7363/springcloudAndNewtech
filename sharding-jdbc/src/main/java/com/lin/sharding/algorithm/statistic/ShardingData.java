/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2023-2023. All rights reserved.
 */

package com.lin.sharding.algorithm.statistic;

import org.apache.shardingsphere.core.rule.TableRule;
import org.apache.shardingsphere.shardingjdbc.jdbc.core.datasource.ShardingDataSource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * desc:
 *
 * @author c30021507
 * @since 2023/5/15
 **/
@Component
public class ShardingData {
    @Resource
    private ShardingDataSource shardingDataSource;

    @PostConstruct
    public void initTables(){
        Map<String, List<String>> tablesMap = RangeAlgorithm.TABLES_MAP;
        Collection<TableRule> tableRules = shardingDataSource.getRuntimeContext().getRule().getTableRules();
        TableRule rule = tableRules.stream().findFirst().orElse(null);
        rule.getActualDataNodes().forEach(table->{
            String tableName = table.getTableName();
            String year = tableName.substring(20, 24);
            if (!tablesMap.containsKey(year)){
                tablesMap.put(year,new ArrayList<>());
            }
            tablesMap.get(year).add(tableName);
        });
    }
}
