package com.qiwan.researchtec;

import org.junit.Test;

import com.qiwan.researchtec.entity.User;

import lombok.val;
import lombok.var;
import lombok.extern.slf4j.Slf4j;

/**
 * <br>类 名: LombokTest
 * <br>描 述: 
 * <br>作 者: wangkefengname@163.com
 * <br>创 建: 2019年7月5日 上午10:29:19
 * <br>版 本: v1.0.0
 */
@Slf4j
//@Log
//@Log4j2
//@CommonsLog
//@JBossLog
public class LombokTest {
	
	@Test
	public void test1(){//var
		var a = "abc";
		log.info("{}", a instanceof String);
		Integer b = 123;
		var c = b;
		log.info("{}", c instanceof Integer);
		var user = new User();
		log.info("{}", user instanceof User);
	}
	
	@Test
	public void test2(){//val
		val a = "abc";//等价于：final var a = "abc";
		log.info("{}", a instanceof String);//true
	}
	
	@Test
	public void test3(){
		var user = new User();
		log.info("{}", user);
	}
	
	@Test
	public void test4(){
		
	}
}
