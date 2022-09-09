package com.lin.multi.database;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 多数据源
 */
@SpringBootApplication
public class MultiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MultiApplication.class,args);
		System.out.println("ProductApplication start success!");
	}
}
