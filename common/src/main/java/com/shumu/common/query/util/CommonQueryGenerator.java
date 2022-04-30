package com.shumu.common.query.util;

import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
/**
* @Description: 自定义查询
* @Author: Li
* @Date: 2022-01-09
* @LastEditTime: 2022-01-09
* @LastEditors: Li
*/
public class CommonQueryGenerator {
    public static  QueryWrapper<Map<String,Object>> initQueryWrapper(){
        QueryWrapper<Map<String,Object>> queryWrapper = new QueryWrapper<>();
        return queryWrapper;
    }
}
