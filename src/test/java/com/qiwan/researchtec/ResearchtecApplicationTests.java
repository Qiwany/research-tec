package com.qiwan.researchtec;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import com.qiwan.researchtec.utils.ImgCompress;
import com.qiwan.researchtec.utils.ScreenShotUtils;
import com.qiwan.researchtec.utils.SpiderUtils;

import net.coobird.thumbnailator.Thumbnails;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ResearchtecApplicationTests {
	
	private static Logger log = LoggerFactory.getLogger(ResearchtecApplicationTests.class);
	
	private ValueOperations<String, String> stringRedis;

//	@Autowired
//	private QRedisTemplate template;
	
	@Autowired
	private StringRedisTemplate redisTempate;
	
//	private static final String namespace = "_test_redis_cluster_";
	
	@Before
	public void init() {
		stringRedis = redisTempate.opsForValue();
		log.info("stringRedis：{}", stringRedis);
	}
	
//	@Test
//	public void test1() {
//		log.info("template：{}", template);
//		template.put("_test_redis_cluster_", "name", "Qiwan");
//	}
//	
//	@Test
//	public void test2(){
//		String name = template.get(namespace, "name");
//		log.info("name：{}",name);
//	}
	
	@Test
	public void test2_1(){
		stringRedis.set("money", "100");
		String money = stringRedis.get("money");
		log.info("money：{}", money);
	}
	
	@Test
	public void test3(){
		ImgCompress.createThumbnail("F:\\temp\\ency1.jpg", "F:\\temp1\\ency1.png", 244, 138);
	}
	
	@Test
	public void test4(){
		try {
//			SpiderUtils.spiderPage("https://blog.csdn.net/index.html", null);
//			SpiderUtils.spiderPage("https://blog.csdn.net", null);
//			SpiderUtils.spiderPage("https://im.qq.com/index.shtml", null);
//			SpiderUtils.spiderPage("http://cq.qq.com/", null);
//			SpiderUtils.spiderPage("https://www.taobao.com", null);
//			SpiderUtils.spiderPage("https://im.qq.com/index.shtml", null);
//			SpiderUtils.spiderPage("http://bj.jumei.com/", null);
//			SpiderUtils.spiderPage("https://www.baidu.com", null);
//			SpiderUtils.spiderPage("http://www.sohu.com/", null);
//			SpiderUtils.spiderPage("https://test.tianxiaj.com", null);
			SpiderUtils.spiderPage("http://www.niceloo.com", null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test5(){
		ScreenShotUtils.spider("https://www.taobao.com");
	}
	
	@Test
	public void test6(){
		ScreenShotUtils.screenShot("https://www.taobao.com", "F:\\taobao.png");
	}
	
	@Test
	public void test7(){
		String jsPath = "E:\\workspace\\researchtec\\src\\main\\resources\\static\\js\\spider.js";
		String outputPath = "E:\\shot1.png";
		ScreenShotUtils.exec("https://www.taobao.com", jsPath, outputPath);
	}
	
	@Test
	public void test8(){
		String url = "https://test.91jianzheng.com/static/js/jquery-1.10.2.js";
		int lastIndexOf = url.lastIndexOf("/");
		String subUrl = url.substring(lastIndexOf+1);
		log.info("subUrl：{}",subUrl);
	}
	
	@Test
	public void test9(){
		String sourceUrl = "http://192.168.3.159:8080/";//js、css、图片等静态给点的网络地址
		int lastIndexOf = sourceUrl.lastIndexOf("/");
		String sourceName = sourceUrl.substring(lastIndexOf+1);//资源文件全名
		log.info("sourceName：{}",sourceName);
	}
	
	@Test
	public void test10(){
		try {
			InetAddress[] addresses = InetAddress.getAllByName("www.91jianzheng.com");//DNS解析域名对应IP
			for (InetAddress inetAddress : addresses) {
				log.info("inetAddress：{}",inetAddress);
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} 
	}
	
	@Test
	public void test11() {
		String str = "<img hidefocus=\"true\" class=\"index-logo-src\" src=\"//www.baidu.com/img/bd_logo1.png\" width=\"//270\" height=\"/129\" usemap=\"#mp\">";
		str = str.replace("\"//", "\"").replace("'//", "'").replace("\"/", "\"").replace("'/", "'");
		System.out.println(str);
	}
	
	@Test
	public void test12(){
		String filePath = "F:\\离线网页\\static\\css\\css.css";
		boolean contains1 = filePath.contains("\\");
		boolean contains2 = filePath.contains("\\\\");
		System.out.println("contains1："+contains1);
		System.out.println("contains2："+contains2);
		String[] split = filePath.split("\\\\");
		System.out.println("==========================");
		for (String string : split) {
			System.out.println(string);
		}
		System.out.println("==========================");
	}
	
	@Test
	public void test13(){
		String baseUrl = "https://www.91jianzheng.com/static/lib/2.1/skin/layer.css";
		if(baseUrl.startsWith("https://")||baseUrl.startsWith("http://")){//防止资源路径为网络路径
			baseUrl = baseUrl.replace("http://", "").replace("https://", "");
			int indexOf = baseUrl.indexOf("/");
			baseUrl = baseUrl.substring(indexOf+1);
		}
		System.out.println("baseUrl："+baseUrl);
	}
	
	@Test
	public void test14(){
		String url = "url(\"../images/iph_solutebg.jpg\"   /*tpa=http://www.sicdt.com/r/cms/www/sicdt/images/iph_solutebg.jpg*/)";
		String regex = "(/\\*)(\\S+)(\\*/)";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(url);
		while(matcher.find()){
			String group = matcher.group();
			url = url.replace(group, "").trim();
		}
		System.out.println("url================"+url);
	}
	
	@Test
	public void test15(){
		String url1 = "http://p12.jmstatic.com/jmstore/image/000/005/5493_std/5b1dd192678cb_1920_350.png";
		String url2 = "http://p12.jmstatic.com/jmstore/image/000/005/5493_std/5b19fe60e40b4_97_50.jpg";
		String url3 = "http://p12.jmstatic.com/jmstore/image/000/005/5493_std/5b19fe61410a3_118_68.jpg";
		String url4 = "http://p12.jmstatic.com/jmstore/image/000/005/5493_std/5b19fe2beec40_400_267.jpg";
		String baseDir = "F:"+File.separator+"jumei"+File.separator;
		List<String> list = new ArrayList<String>();
		list.add(url1);
		list.add(url2);
		list.add(url3);
		list.add(url4);
		for (String url : list) {
			String replace = url.replace("http://", "");
			String imgPath = replace.replace("/", File.separator);
			String filePath = baseDir + imgPath;
			System.out.println("filePath： "+filePath);
			File file = null;
			try {
				file = new File(filePath);
				if(file.exists()){
					System.out.println("文件已存在！");
				}else{
					System.out.println("文件不存在！");
				}
			} catch (Exception e) {
				System.out.println("Exception："+e);
			}
			URL address = null;
			try {
				address = new URL(url);
			} catch (MalformedURLException e) {
				System.out.println("Exception："+e);
			}
			try {
				FileUtils.copyURLToFile(address, file);
			} catch (IOException e) {
				System.out.println("Exception："+e);
			}
		}
	}
	
	/**
	 * @Description:后台截图测试
	 */
	@Test
	public void test17(){
		String BLANK = " ";
		String pluginPath = "E:/Develop/phantomjs-2.1.1-windows/bin/phantomjs.exe";
		String jsPath = "E:/Develop/phantomjs-2.1.1-windows/examples/rasterize.js";
		String url = "http://192.168.3.243:8080/personalCenter";//personalCenter
		String savePath = "D:/screenshot/";
		String fileName = "截图.png";
		System.out.println(System.currentTimeMillis());
		try {
			Runtime.getRuntime().exec(pluginPath+BLANK+jsPath+BLANK+url+BLANK+savePath+fileName);//+BLANK+savePath+fileName
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(System.currentTimeMillis());
		try {
			Thread.sleep(5000);
			System.out.println("完成！");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test19(){
		try {
			//创建httpClient实例
			CloseableHttpClient httpClient = HttpClients.createDefault();
			//创建httpGet实例
			HttpGet httpGet = new HttpGet("http://test.tianxiaj.com/static/css/css.css");
			CloseableHttpResponse response = httpClient.execute(httpGet);
			if (response != null){
			    HttpEntity entity =  response.getEntity();  //获取网页内容
			    String result = EntityUtils.toString(entity, "UTF-8");
			    System.out.println("网页内容:"+result);
			}
			if (response != null){
			    response.close();
			}
			if (httpClient != null){
			    httpClient.close();
			}
		} catch (ParseException | IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test20(){
		String str = "about:blank";
		String regex = "(http|https)://[\\w+\\.?/?]+\\.[A-Za-z]+";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		System.out.println("qqq："+matcher.find());
	}
	
	@Test
	public void test22(){
		String url = "http://www.baidu.com?name=qiwan";
		if(url.contains("?")){
			String[] split = url.split("[?]");
			System.out.println(split[0]);
		}
	}
	
	@Test
	public void test23(){
		String url = "http://test.tianxiaj.com/index/industryEncyclopedia";
		try {
			URL net = new URL(url);
			String host = net.getHost();
			System.out.println("host："+host);
			String protocol = net.getProtocol();
			System.out.println("protocol："+protocol);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test24(){
		String str = "(//abc";
		String replaceAll = str.replaceAll("\\(\\s*//", "(");
		System.out.println(replaceAll);
	}
	
	@Test
	public void test25(){
		try {  
            URL url = new URL("http://ss1.bdstatic.com/5eN1bjq8AAUYm2zgoY3K/r/www/cache/static/protocol/https/home/img/qrcode/zbios_efde696.png");  
            HttpURLConnection conn =  (HttpURLConnection)url.openConnection();
            conn.setConnectTimeout(2000);
            conn.connect();
            System.out.println("连接可用！");
            conn.disconnect();
       } catch (Exception e1) {  
            System.out.println("连接打不开！");  
       }
	}
	
	@Test
	public void test28(){
		String pageText = "\"//www.baidu.com\"";
		pageText = pageText.replace("\"//", "\"**").replace("'//", "'**");
		pageText = pageText.replace("\"/", "\"") .replace("'/", "'").replace("\"**", "\"//");
		System.out.println(pageText);
	}
	
	@Test
	public void test29(){
		String str = "<!DOCTYPE html>   \n<html xmlns=\"www.w3.org/1999/xhtml\">";
		Pattern pattern = Pattern.compile("([\\s\\S]+)(?=<html)");
		Matcher matcher = pattern.matcher(str);
		while (matcher.find()) {
			String group = matcher.group();
			System.out.println("group："+group);
		}
	}
	
	@Test
	public void test30(){
//		ImgCompress.createThumbnail("F:\\离线网页2\\www.taobao.com\\网页截图.png", "F:\\离线网页2\\www.taobao.com\\thumb.png", 100, 100);
		try {
//			Thumbnails.of("F:\\离线网页2\\www.taobao.com\\网页截图.png").size(200, 300).toFile("F:\\离线网页2\\www.taobao.com\\thumb1.png");
//			Thumbnails.of("F:\\离线网页2\\www.taobao.com\\网页截图.png").size(200, 300).keepAspectRatio(false).toFile("F:\\离线网页2\\www.taobao.com\\thumb2.png");
			Thumbnails.of("F:\\离线网页2\\www.taobao.com\\网页截图.png").sourceRegion(0, 0, 1920, 2880).size(300, 450).keepAspectRatio(false).toFile("F:\\离线网页2\\www.taobao.com\\thumb5.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
