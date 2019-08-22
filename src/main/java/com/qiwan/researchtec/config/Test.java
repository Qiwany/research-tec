package com.qiwan.researchtec.config;

import org.springframework.stereotype.Component;

/**
 * <br>类 名: Test
 * <br>描 述: 
 * <br>作 者: wangkefengname@163.com
 * <br>创 建: 2019年6月14日 上午10:46:36
 * <br>版 本: v1.0.0
 */
@Component//交给Spring管理的情况下，没有默认的无参构造方法时，注解获取该类的对象时会出错
public class Test {
	
	private String str;
	
	public Test() {
		
	}
	
	public Test(String str) {
		this.str = str;
	}

	@Override
	public String toString() {
		return "Test [str=" + str + "]";
	}
}
