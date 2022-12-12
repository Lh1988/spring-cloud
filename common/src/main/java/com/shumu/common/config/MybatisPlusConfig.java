package com.shumu.common.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
/**
* @description: Mybatis Plus Config
* @author: Li
* @date: 2022-12-11
*/
@Configuration
@MapperScan("com/shumu/**/mapper/xml/*Mapper.xml")
public class MybatisPlusConfig {
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor((InnerInterceptor) new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }
}
