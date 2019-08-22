package com.qiwan.researchtec.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

/**
 * <br>类 名: ScreenShotUtils
 * <br>描 述: 服务器端根据 phantomJs组件截图的工具类
 * <br>作 者: wangkefengname@163.com
 * <br>创 建: 2018年6月28日 下午4:40:23
 * <br>版 本: v1.0.0
 */
public class ScreenShotUtils {
	
	private static Runtime runtime = Runtime.getRuntime();
	private static String pluginPath = "E:/Develop/phantomjs-2.1.1-windows/bin/phantomjs.exe";
	private static String rasterizeJsPath = "E:/Develop/phantomjs-2.1.1-windows/examples/rasterize.js";
	private static String spiderJsPath = "E:/workspace/researchtec/src/main/resources/static/js/spider.js";
	private static String blank = " ";
	
	public static void screenShot(String url, String picPath){
//		url = "http://192.168.3.243:8080/personalCenter";//personalCenter
//		picPath = "D:/screenshot/截图.png";
		try {
			runtime.exec(pluginPath + blank + rasterizeJsPath + blank + url + blank + picPath);
			Thread.sleep(5000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void spider(String url){
		try {
			Process p = runtime.exec(pluginPath + blank + spiderJsPath + blank + url);
			InputStream inputStream = p.getInputStream();
			byte[] byteArray = IOUtils.toByteArray(inputStream);
			FileUtils.writeByteArrayToFile(new File("F:\\taobao.html"), byteArray);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void exec(String url, String jsPath, String screenShotPath){
		try {
			Process p = runtime.exec(pluginPath + blank + jsPath + blank + url + blank + screenShotPath);
			InputStream inputStream = p.getInputStream();
			byte[] byteArray = IOUtils.toByteArray(inputStream);
			FileUtils.writeByteArrayToFile(new File("E:\\taobao.html"), byteArray);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
