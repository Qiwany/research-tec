package com.qiwan.researchtec.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.qiwan.researchtec.entity.Son;

/**
 * <br>类 名: SonConfig
 * <br>描 述: 
 * <br>作 者: wangkefengname@163.com
 * <br>创 建: 2019年6月18日 下午2:24:16
 * <br>版 本: v1.0.0
 */
@Configuration
public class SonConfig {
	
	@Bean
	@ConfigurationProperties(prefix = "test")
	public Son getSon(){
		return new Son();
	}
}
