package com.shumu.common.redis.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import lombok.extern.slf4j.Slf4j;

/**
* @Description: 
* @Author: Li
* @Date: 2022-02-11
* @LastEditTime: 2022-02-11
* @LastEditors: Li
*/
@Slf4j
@EnableCaching
@Configuration
public class RedisConfig {
    @Bean
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
		log.info(" --- redis config init --- ");
        RedisTemplate<String, Object> template = new RedisTemplate<>();
		template.setKeySerializer(new StringRedisSerializer());
		template.setHashKeySerializer(new StringRedisSerializer());
		template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
		template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
		template.setEnableTransactionSupport(true);
		template.setConnectionFactory(factory);
		return template;
	}

}
