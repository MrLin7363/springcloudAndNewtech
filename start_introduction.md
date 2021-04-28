项目说明文档

### 单独启动product测试

注释掉eureka

```
 <!--引入EurekaClient 启动文件要开启注解-->
<!-- <dependency>
     <groupId>org.springframework.cloud</groupId>
     <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
 </dependency>-->
```

启动类注释掉eureka的东西

```
//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
//import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EntityScan("cn.itcast.product.entity")
//激活eurekaClient   新版本spring中这下面两个注解不写都行，只要pom加入eureka开启就是检测eureka， EnableDiscoveryClient包含EnableEurekaClient
//@EnableEurekaClient
//@EnableDiscoveryClient
public class ProductApplication {

   public static void main(String[] args) {
      SpringApplication.run(ProductApplication.class,args);
      System.out.println("ProductApplication start success!");
   }
}
```

### 启动多服务eureka

注释加上

先启动eureka-server才能启动product-server 和 order-server 

import-server是没用的