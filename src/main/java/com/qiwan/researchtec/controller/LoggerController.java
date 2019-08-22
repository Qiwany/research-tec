package com.qiwan.researchtec.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

/**
 * @Description:
 * @author:	wangkefengname@163.com
 * @date:	2018年2月9日 下午1:28:14
 */
@Controller
public class LoggerController {

	private static final Logger log = LoggerFactory.getLogger(LoggerController.class);
	
	@ResponseBody
	@RequestMapping("/test1")
	public String test1(){
		log.info("测试LOG：{}", "测试LOG！");
		String json = JSON.toJSONString("测试LOG！");
		return json;
	}
}
