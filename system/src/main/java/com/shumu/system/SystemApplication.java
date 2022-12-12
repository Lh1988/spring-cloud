package com.shumu.system;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
* @Description: 启动
* @Author: Li
* @Date: 2022-01-12
* @LastEditTime: 2022-01-12
* @LastEditors: Li
*/
@SpringBootApplication
@EnableWebMvc
@ComponentScan(basePackages = {"com.shumu.**.service","com.shumu.**.config","com.shumu.**.controller","com.shumu.**.model"})
@MapperScan("com.shumu.**.mapper")
public class SystemApplication {
	public static void main(String[] args) {
		SpringApplication.run(SystemApplication.class, args);
	}
}
