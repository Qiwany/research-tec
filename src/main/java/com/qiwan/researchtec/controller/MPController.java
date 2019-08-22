package com.qiwan.researchtec.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.qiwan.researchtec.annotation.ShowParams;
import com.qiwan.researchtec.entity.MPUser;

/**
 * <br>类 名: MPController
 * <br>描 述: 用于小程序测试开发的类
 * <br>作 者: wangkefengname@163.com
 * <br>创 建: 2019年2月18日 下午1:30:07
 * <br>版 本: v1.0.0
 */
@Controller
@RequestMapping("/mp/")
public class MPController {
	
	private final static Logger log = LoggerFactory.getLogger(MPController.class);
	
	/**
	 * @Description:测试微信小程序获取数据列表接口
	 * @return
	 */
	@ResponseBody
	@RequestMapping("list")
	public Object list(){
		log.info("=============微信小程序请求获取数据列表接口！=============");
		List<Object> list = new ArrayList<>();
		int i = 1;
		while (i <= 10) {
			MPUser mpUser = new MPUser();
			mpUser.setId(i);
			mpUser.setName("kitty"+i);
			mpUser.setSex(i/2==0?'男':'女');
			mpUser.setAge((int)(15+Math.random()*(20-15+1)));
			list.add(mpUser);
			i++;
		}
		return list;
	}
	
	/**
	 * @Description:测试微信小程序上传文件接口
	 * @param file 小程序选择上传的文件
	 * @param username 上传的用户姓名
	 * @param userId 用户编号
	 * @param userSex 用户性别
	 * @param userAge 用户年龄
	 * @return
	 */
	@ShowParams
	@ResponseBody
	@RequestMapping("upload")
	public Object upload(MultipartFile file, String username, Integer userId, Character userSex, Integer userAge){
		log.info("=============微信小程序请求上传文件接口！=============");
		if(file == null){
			return "fail";
		}else{
			String originalFilename = file.getOriginalFilename();
			String fileName = file.getName();
			log.info("originalFilename：{}；fileName：{}", originalFilename, fileName);
			return "success";
		}
	}
}
