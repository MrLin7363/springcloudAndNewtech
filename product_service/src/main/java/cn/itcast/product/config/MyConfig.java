package cn.itcast.product.config;

import cn.itcast.product.entity.Product;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: chenjunlin
 * @since: 2021/04/29
 * @descripe:
 */
@Configuration
public class MyConfig {

    @Bean("myProduct")
    public Product initProduct(){
        return new Product();
    }
}
