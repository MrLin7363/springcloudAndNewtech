package com.junlin.bean;

import org.springframework.context.annotation.Bean;

/**
 * 没有spring注解
 */
public class UserConfiguration {
	// bean名为User，方法名无所谓
	@Bean
	public User getUser() {
		User user = new User();
		user.setAge(12);
		user.setUsername("传智播客");
		return user;
	}
}
