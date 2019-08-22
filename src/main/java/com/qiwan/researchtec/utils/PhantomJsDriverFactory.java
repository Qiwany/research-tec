package com.qiwan.researchtec.utils;

import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * <br>
 * 类 名: PhantomJsDriver <br>
 * 描 述: 获取PhantomJsDriver实例 作 者: wangkefengname@163.com <br>
 * 创 建: 2018年7月11日 下午4:04:00 <br>
 * 版 本: v1.0.0
 */
public class PhantomJsDriverFactory {

	private static String installPath = "E:\\Develop\\phantomjs-2.1.1-windows\\bin\\phantomjs.exe";// 本地phantomJs的安装路径

	public static PhantomJSDriver getPhantomJSDriver() {
		// 设置必要参数
		DesiredCapabilities dcaps = new DesiredCapabilities();
		dcaps.setCapability("acceptSslCerts", true);// ssl证书支持
		dcaps.setCapability("takesScreenshot", false);// 截屏支持
		dcaps.setCapability("cssSelectorsEnabled", true);// css搜索支持
		dcaps.setJavascriptEnabled(true);// js支持
//		dcaps.setCapability("phantomjs.page.settings.userAgent",
//				"Mozilla/5.0 (Windows NT 6.3; Win64; x64; rv:50.0) Gecko/20100101 Firefox/50.0");
//		dcaps.setCapability("phantomjs.page.customHeaders.User-Agent",
//				"Mozilla/5.0 (Windows NT 6.3; Win64; x64; rv:50.0) Gecko/20100101 Firefox/50.0");
		dcaps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, installPath);// 驱动支持
		PhantomJSDriver driver = new PhantomJSDriver(dcaps);
		return driver;
	}
}
