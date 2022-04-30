package com.shumu.common.query.util;

import lombok.extern.slf4j.Slf4j;

/**
* @Description: SQL注入处理工具
* @Author: Li
* @Date: 2022-01-05
* @LastEditTime: 2022-01-05
* @LastEditors: Li
*/
@Slf4j
public class SqlInjectionCheck {
    private final static String XSS_STRING = "'|and |exec |insert |select |delete |update |drop |count |chr |mid |master |truncate |char |declare |;|or |+";
    /**
	 * sql注入过滤处理，遇到注入关键字抛异常
	 * 
	 * @param value
	 */
	public static void filterContent(String value) {
		if (value != null && !"".equals(value)) {
			value = value.toLowerCase();
		    String[] xssArr = XSS_STRING.split("\\|");
            for (int i = 0; i < xssArr.length; i++) {
                if (value.indexOf(xssArr[i]) > -1) {
                    log.error("请注意，存在SQL注入关键词---> {}", xssArr[i]);
                    log.error("请注意，值可能存在SQL注入风险!---> {}", value);
                    throw new RuntimeException("请注意，值可能存在SQL注入风险!--->" + value);
                }
            }
		}
	}
    /**
	 * sql注入过滤处理，遇到注入关键字抛异常
	 * 
	 * @param values
	 */
	public static void filterContent(String[] values) {
        for (String value : values) {
            filterContent(value);
        }
    }

}
