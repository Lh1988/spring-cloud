package com.shumu.common.util;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.toolkit.JdbcUtils;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.SQLException;
/**
* @description: database
* @author: Li
* @date: 2022-12-12
*/
@Slf4j
public class DbUtil {
    /** 当前系统数据库类型 */
    private static DbType dbTypeEnum = null;


     /**
     * 全局获取平台数据库类型（对应mybaisPlus枚举）
     * @return
     */
    public static DbType getDatabaseTypeEnum() {
        if (null!=dbTypeEnum) {
            return dbTypeEnum;
        }
        try {
            DataSource dataSource = SpringContextUtil.getApplicationContext().getBean(DataSource.class);
            dbTypeEnum = JdbcUtils.getDbType(dataSource.getConnection().getMetaData().getURL());
            return dbTypeEnum;
        } catch (SQLException e) {
            log.warn(e.getMessage(), e);
            return null;
        }
    }
    
}
