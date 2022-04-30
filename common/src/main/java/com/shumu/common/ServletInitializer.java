/*
 * @Author: your name
 * @Date: 2021-12-26 00:13:55
 * @LastEditTime: 2021-12-28 22:21:17
 * @LastEditors: your name
 * @Description: 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 * @FilePath: \cloud\common\src\main\java\com\shumu\common\ServletInitializer.java
 */
package com.shumu.common;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
/**
* @Description:
* @Author: Li
* @Date: 2021-12-28
* @LastEditTime: 2021-12-28
* @LastEditors: Li
*/
public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(CommonApplication.class);
	}

}
