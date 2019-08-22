package com.qiwan.researchtec.config;

import org.springframework.stereotype.Component;

/**
 * <br>类 名: TestConfig
 * <br>描 述: 
 * <br>作 者: wangkefengname@163.com
 * <br>创 建: 2019年6月14日 上午9:58:00
 * <br>版 本: v1.0.0
 */
@Component
public class TestConfig {
	
	private String name = "1111111111111111";
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public TestConfig() {
		
	}
	
	public TestConfig(String name) {
		this.name = (name="22222222222");
	}

	@Override
	public String toString() {
		return "TestConfig [name=" + name + "]";
	}
}
