package com.qiwan.researchtec.entity;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <br>类 名: Son
 * <br>描 述: 
 * <br>作 者: wangkefengname@163.com
 * <br>创 建: 2019年6月18日 上午10:37:38
 * <br>版 本: v1.0.0
 */
@ConfigurationProperties(prefix = "test")
public class Son extends Parent {
	
	private String userName;
	
	private Integer age;
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "Son [userName=" + userName + ", age=" + age + ", toString()=" + super.toString() + "]";
	}
}
