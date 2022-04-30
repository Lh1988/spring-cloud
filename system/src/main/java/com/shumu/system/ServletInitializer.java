package com.shumu.system;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
/**
* @Description: 初始化
* @Author: Li
* @Date: 2022-01-12
* @LastEditTime: 2022-01-12
* @LastEditors: Li
*/
public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(SystemApplication.class);
	}

}
