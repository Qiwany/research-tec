package com.qiwan.researchtec.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * <br>类 名: TestConfig3
 * <br>描 述: 
 * <br>作 者: wangkefengname@163.com
 * <br>创 建: 2019年6月14日 上午9:58:00
 * <br>版 本: v1.0.0
 */
@Component
public class TestConfig3 {
	
	private String name = "1111111111111111";
	
	private Test test;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Test getTest() {
		return test;
	}
	
	public void setTest(Test test) {
		this.test = test;
	}
	
	@Autowired
	public TestConfig3(Test test) {//参数test为@Autowire获取，在没有无参构造方法的情况下，注解获取Bean时会使用该类型的构造方法
		this.test = test;
	}
	
	public TestConfig3(String name) {
		this.name = (name="22222222222");
	}

	@Override
	public String toString() {
		return "TestConfig3 [name=" + name + "]";
	}
}
