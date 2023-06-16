package com.lin.sharding.algorithm;

import org.apache.shardingsphere.infra.expr.InlineExpressionParser;
import org.apache.shardingsphere.sharding.algorithm.config.AlgorithmProvidedShardingRuleConfiguration;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * 目前这个AlgorithmProvidedShardingRuleConfiguration获取不到，可能需要springboot 2.7.11以上
 */
@Component
public class ShardingData {
    @Resource
    private AlgorithmProvidedShardingRuleConfiguration ruleConfiguration;

    /**
     * init tables
     */
    @PostConstruct
    public void initTables() {
        Map<String, List<String>> tablesMap = OrderAlgorithm.TABLES_MAP;
        List<String> tableList = new InlineExpressionParser(
            ruleConfiguration.getTables().stream().findFirst().get().getActualDataNodes()).splitAndEvaluate();

        List<String> tableNameList = tableList.stream()
            .map(t -> t.substring(t.indexOf('.') + 1))
            .collect(Collectors.toList());

        tableNameList.forEach(tableName -> {
            String year = tableName.substring(20, 24);
            if (!tablesMap.containsKey(year)) {
                tablesMap.put(year, new LinkedList<>());
            }
            tablesMap.get(year).add(tableName);
        });
    }
}
