package com.qiwan.researchtec.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * <br>类 名: QRedisConfiguration 
 * <br>描 述: 描述类完成的主要功能  redis配置，初始化cluster工厂
 * <br>作 者: lijiansheng 
 * <br>创 建: 2017年11月24日 
 * <br>版 本: v1.0.0 
 * <br>历 史: (版本) 作者 时间 注释
 */
@Configuration
@EnableCaching
public class QRedisConfiguration {

	@Autowired
	private RedisConnectionFactory factory;

	@Bean
	public RedisTemplate<String, Object> redisTemplate() {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setHashKeySerializer(new StringRedisSerializer());
		redisTemplate.setHashValueSerializer(new StringRedisSerializer());
		redisTemplate.setValueSerializer(new StringRedisSerializer());
		redisTemplate.setConnectionFactory(factory);
		return redisTemplate;
	}
}