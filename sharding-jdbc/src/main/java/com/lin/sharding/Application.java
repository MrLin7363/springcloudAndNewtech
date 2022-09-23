package com.lin.sharding;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.lin.sharding.mapper")
public class Application {

    /**
     * 输入active profiles 后缀使用响应的
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
        System.out.println("Application start success!");
    }
}
