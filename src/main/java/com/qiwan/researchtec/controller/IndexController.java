package com.qiwan.researchtec.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.qiwan.researchtec.entity.User;

import lombok.extern.slf4j.Slf4j;

/**
 * <br>类 名: IndexController
 * <br>描 述: 
 * <br>作 者: wangkefengname@163.com
 * <br>创 建: 2019年3月8日 上午11:41:05
 * <br>版 本: v1.0.0
 */
@Slf4j
@Controller
public class IndexController {
	
	@RequestMapping("/")
	public String index(){
		return "index";
	}
	
	/**
	 * @Description: 测试全局异常处理
	 * @return String
	 */
	@RequestMapping("/testException")
	public String testException() {
		int a = 1/0;
		return "index";
	}
	
	/**
	 * @Description: 测试请求入参和回显
	 * @param user
	 * @return String
	 */
	@GetMapping("/testRequestEcho")
	public String testRequestEcho(User user, String name, Integer age){
		log.info("User：{}", user);
		return "test-request-echo";
	}
}
