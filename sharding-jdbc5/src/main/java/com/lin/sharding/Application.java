package com.lin.sharding;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 目前这个AlgorithmProvidedShardingRuleConfiguration获取不到，可能需要springboot 2.7.11以上
 */
@SpringBootApplication
@MapperScan("com.lin.sharding.mapper")
public class Application {
    /**
     * 需要输入active profiles启动 后缀使用响应的
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        System.out.println("Application start success!");
    }
}
