package com.junlin.product.controller;

import com.junlin.product.entity.Product;
import com.junlin.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {

	@Autowired
	private ProductService productService;

	/*@Value("${server.port}")
	private String port;

	@Value("${spring.cloud.client.ip-address}") //spring cloud 自动的获取当前应用的ip地址
	private String ip;*/

	@RequestMapping(value = "/{id}",method = RequestMethod.GET)
	public Product findById(@PathVariable Long id) {
//		Product product = productService.findById(id);
		Product product=new Product();
//		product.setPname("访问的服务地址:"+ip + ":" + port);
		product.setPname("访问的服务地址:");
		return product;
	}

	@RequestMapping(value = "",method = RequestMethod.POST)
	public String save(@RequestBody Product product) {
		productService.save(product);
		return "保存成功";
	}
}
