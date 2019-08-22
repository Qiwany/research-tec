package com.qiwan.researchtec.config;

import org.springframework.stereotype.Component;

/**
 * <br>类 名: TestConfig2
 * <br>描 述: 
 * <br>作 者: wangkefengname@163.com
 * <br>创 建: 2019年6月14日 上午9:58:00
 * <br>版 本: v1.0.0
 */
@Component
public class TestConfig2 extends Test {
	
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
	
	public TestConfig2() {//没有无参构造方法，注解获取Bean时会报错
		super("444444");
	}
	
	public TestConfig2(String name) {
		super("444444");
		this.name = (name="22222222222");
	}

	@Override
	public String toString() {
		return "TestConfig2 [name=" + name + "]";
	}
}
