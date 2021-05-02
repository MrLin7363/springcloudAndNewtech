package com.junlin.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
//import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EntityScan("com.junlin.product.entity")//    用于扫描JPA实体类 @Entity
//@ComponentScan(basePackages = "com.junlin.product.*")  //   用于扫描@Controller @Service   可不加
//@EnableJpaRepositories(basePackages = "com.junlin.product.dao")    //   用于扫描Dao @Repository   可不加
//激活eurekaClient   新版本spring中这下面两个注解不写都行，只要pom加入eureka开启就是检测eureka， EnableDiscoveryClient包含EnableEurekaClient
//@EnableEurekaClient
//@EnableDiscoveryClient
public class ProductApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductApplication.class,args);
		System.out.println("ProductApplication start success!");
	}
}
