package com.qiwan.researchtec.utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.LogManager;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.ImmediateRefreshHandler;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.TextPage;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.Thumbnails.Builder;

/**
 * <br>
 * 类 名: SpiderUtils <br>
 * 描 述: 网页离线保存及截图工具类<br>
 * 作 者: wangkefengname@163.com <br>
 * 创 建: 2018年7月11日 下午4:01:33 <br>
 * 版 本: v1.0.0
 */
public class SpiderUtils {
	
	private static final Logger log = LoggerFactory.getLogger(SpiderUtils.class);
	
	private static Runtime runtime = Runtime.getRuntime();
	private static String pluginPath = "E:"+File.separator+"Develop"+File.separator+"phantomjs-2.1.1-windows"+File.separator+"bin"+File.separator+"phantomjs.exe";
	private static String spiderJsPath = "E:"+File.separator+"Develop"+File.separator+"phantomjs-2.1.1-windows"+File.separator+"examples"+File.separator+"spider.js";
	private static String blank = " ";
	private static String baseDir = "E:" + File.separator + "离线网页"+File.separator;
	private static String resourceDir = "资源文件目录_files";
	private static String fileRootPath = null;
	
	private static String osInfo = "获取服务器操作系统信息";
	private static String networkConfig = "获取服务器网络配置信息";
	private static String hardware = "获取服务器硬件信息";
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
	
	private static org.apache.log4j.Logger logger = null;//用于输出日志到文件的logger
	
	private static WebClient webClient = new WebClient(BrowserVersion.BEST_SUPPORTED);
	
	static {
		webClient.setRefreshHandler(new ImmediateRefreshHandler());
		webClient.getOptions().setThrowExceptionOnScriptError(false);// 当JS执行出错的时候是否抛出异常, 这里选择不需要
		webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);// 当HTTP的状态非200时是否抛出异常, 这里选择不需要
		webClient.getOptions().setActiveXNative(false);
		webClient.getOptions().setJavaScriptEnabled(true);
		webClient.getOptions().setCssEnabled(false);
		webClient.setAjaxController(new NicelyResynchronizingAjaxController());
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		webClient.getOptions().setRedirectEnabled(true);
		webClient.setJavaScriptTimeout(Integer.MAX_VALUE);
		webClient.getOptions().setUseInsecureSSL(true);
		webClient.waitForBackgroundJavaScript(0);// 异步JS执行需要耗时,所以这里线程要阻塞30秒,等待异步JS执行结束
	}
	
	/**
	 * @Description:离线保存网页并处理页面上的网络资源文件
	 * @param url 需要离线的网页地址
	 * @throws Exception
	 * return 返回抓取网页的存放目录（域名+时间串，非空时则抓取页面成功）
	 */
	public static Map<String, String> spiderPage(String url, String readmePath) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		Page page = webClient.getPage(url);
		if(page.isHtmlPage()){
			HtmlPage htmlPage = (HtmlPage)page;
			String protocol = (String)htmlPage.executeJavaScript("window.location.protocol").getJavaScriptResult();
			String host = (String)htmlPage.executeJavaScript("window.location.host").getJavaScriptResult();
			Charset charset = htmlPage.getCharset();//页面字符集
			log.info("protocol：{}，host：{}，charset：{}", protocol, host, charset);
			fileRootPath = baseDir + host + "_" + sdf.format(new Date()) + File.separator;//文件根目录
			logger = LoggerUtil.getLog(SpiderUtils.class, fileRootPath);//初始化logger
			String htmlContent = getHtmlContent(url, fileRootPath + "screenshot.png", charset);
			if(StringUtils.isNotBlank(htmlContent)){
				Document doc = Jsoup.parse(htmlContent, protocol + "//" + host);
				String html = doc.html();
				String fileName = doc.title();//保存时的文件名
				List<String> cssList = new ArrayList<String>();
				Elements links = doc.getElementsByTag("link");
				for (Element link : links) {
					String linkUrl = link.attr("abs:href");
					if(StringUtils.isBlank(linkUrl)) continue;
					String linkFileName = linkUrl.substring(linkUrl.lastIndexOf("/") + 1);//获取js文件名
					if (linkUrl.endsWith(".css")) {
						cssList.add(linkUrl);
						link.attr("href", "./" + resourceDir + "/" + linkFileName);
					} else if (linkUrl.toLowerCase().endsWith(".ico")||linkUrl.toLowerCase().endsWith(".svg")||linkUrl.toLowerCase().endsWith(".png")){//可能是ico或者svg
						copyURLToFile(linkUrl, fileRootPath + resourceDir + File.separator + linkFileName, protocol, host);//下载文件
						link.attr("href", "./" + resourceDir + "/" + linkFileName);
					}
				}
				Elements scripts = doc.getElementsByTag("script");
				for (Element script : scripts) {
					String scriptUrl = script.attr("abs:src");
					if(StringUtils.isBlank(scriptUrl)) continue;
					if(scriptUrl.endsWith(".js")){
						String jsFileName = scriptUrl.substring(scriptUrl.lastIndexOf("/") + 1);//获取js文件名
						if(StringUtils.isBlank(jsFileName)) continue;
						script.attr("src", "./" + resourceDir + "/" + jsFileName + ".下载");
						copyURLToFile(scriptUrl, fileRootPath + resourceDir + File.separator + jsFileName + ".下载", protocol, host);//下载js文件
					}
				}
				Elements imgs = doc.getElementsByTag("img");
				for (Element img : imgs) {//处理img标签的src属性
					String imgUrl = img.attr("abs:src");
					if(StringUtils.isBlank(imgUrl)) continue;
					if(imgUrl.startsWith("data:image")) continue;
					if(imgUrl.contains("?")) imgUrl = imgUrl.substring(0, imgUrl.indexOf("?"));
					String imgFileName = imgUrl.substring(imgUrl.lastIndexOf("/")+1);
					if(StringUtils.isBlank(imgFileName)) continue;
					img.attr("src", "./" + resourceDir + "/" + imgFileName);
					copyURLToFile(imgUrl, fileRootPath + resourceDir + File.separator + imgFileName, protocol, host);//下载图片
				}
				for (Element img : imgs) {//处理img标签的original
					String originalUrl = img.attr("abs:original");
					if(StringUtils.isBlank(originalUrl)) continue;
					if(originalUrl.startsWith("data:image")) continue;
					String imgFileName = originalUrl.substring(originalUrl.lastIndexOf("/")+1);
					if(originalUrl.contains("?")) imgFileName = originalUrl.substring(originalUrl.lastIndexOf("/")+1, originalUrl.indexOf("?"));
					if(StringUtils.isBlank(imgFileName)) continue;
					img.attr("src", "./" + resourceDir + "/" + imgFileName);
					copyURLToFile(originalUrl, fileRootPath + resourceDir + File.separator + imgFileName, protocol, host);//下载图片
				}
				List<String> styleUrlList = retrieveUrlFromStyle(html);
				html = doc.html()/*.replaceAll(">\\s+<", "><")*/;//重新获取变更url后的html文本
				for (String styleUrl : styleUrlList) {
					String trimSpace = trimSpace(styleUrl);//去掉空格换行等空字符
					String styleImgUrl = trimAnnotion(trimSpace);//去掉各类注解得到样式表中的图片相对url
					if(StringUtils.isBlank(styleImgUrl)) continue;
					if(styleImgUrl.contains("?")) styleImgUrl = styleImgUrl.substring(0, styleImgUrl.indexOf("?"));
					String styleImgFilePath = handleUrlToFilePath(styleImgUrl);//处理得到style中图片即将保存的本地位置（不包括baseDir和resourceDir）
					html = html.replace(styleUrl, "./" + resourceDir + styleImgFilePath);
					handleUrlAndDownCssImage(protocol, host, styleImgUrl, null);//下载css文件中的图片
				}
				cssList = removeDuplicate(cssList);
				for (String cssUrl : cssList) {
					TextPage cssPage = webClient.getPage(cssUrl);//根据css地址读取css内容
					String cssContent = cssPage.getContent();
					List<String> imgUrlList = retrieveUrlFromStyle(cssContent);//从css中提取图片url
					for (String baseImgUrl : imgUrlList) {
						String trimSpace = trimSpace(baseImgUrl);//去掉空格换行等空字符
						String styleImgUrl = trimAnnotion(trimSpace);//去掉各类注解得到样式表中的图片相对url
						if(StringUtils.isBlank(styleImgUrl)) continue;
						if(styleImgUrl.contains("?")) styleImgUrl = styleImgUrl.substring(0, styleImgUrl.indexOf("?"));
						log.info("baseImgUrl：{}；trimSpace：{}；styleImgUrl：{}",baseImgUrl,trimSpace,styleImgUrl);
						handleUrlAndDownCssImage(protocol, host, styleImgUrl, cssUrl);//下载css文件中的图片
					}
					cssContent = cssContent.replace("\\/", "/").replace("//", "./").replaceAll("\\(\\s*/", "(./").replace(protocol, "").replace("../", "./");//处理css中的资源url
					String cssFileName = cssUrl.substring(cssUrl.lastIndexOf("/"));//待下载的css文件名（名称前有‘/’）
					FileUtils.writeStringToFile(new File(fileRootPath + resourceDir + cssFileName), cssContent, charset);//把字符串写进文件
				}
				downHtml(html, fileName, charset, host);//下载网站页面
				if(StringUtils.isNotBlank(readmePath)) FileUtils.copyFile(new File(readmePath), new File(fileRootPath + "readme.txt"));//把模版文件放入证据包
				FileUtils.writeStringToFile(new File(fileRootPath + "固定对象信息.txt"), url, "UTF-8");
				//writeServerInfoToFile();//把服务器相关信息写入文件（windows系统时不适用）
				log.info("=================网站抓取完成！=================");
				LogManager.shutdown();
				map.put("fileRootPath", fileRootPath);
				map.put("host", host);
				map.put("protocol", protocol);
				return map;
			}
		}else{
			log.info("=================入参网址不是正规的网站页面地址=================");
		}
		return null;
	}
	
	private static void copyURLToFile(String urlStr, String filePath, String protocol, String host) {
		try {
			if (StringUtils.isNotBlank(urlStr) && StringUtils.isNotBlank(filePath)) {
				urlStr = urlStr.replace("\\", "/");
				if(urlStr.endsWith("\\")||urlStr.endsWith("/")) urlStr = urlStr.substring(0, urlStr.length() - 1);
				File file = new File(filePath);
				if (!file.exists()) {
					// 创建httpClient实例
					CloseableHttpClient httpClient = HttpClients.createDefault();
					// 创建httpGet实例
					HttpGet httpGet = new HttpGet(urlStr);
					httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:0.9.4)");
					httpGet.setHeader("Referer", protocol + "//" + host);
					httpGet.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
					httpGet.setHeader("Accept-Charset", "GB2312,utf-8;q=0.7,*;q=0.7");
					httpGet.setHeader("Accept-Encoding", "gzip, deflate");
					httpGet.setHeader("Accept-Language", "zh-cn,zh;q=0.5");
					httpGet.setHeader("Connection", "keep-alive");
					Header[] requestHeaders = httpGet.getAllHeaders();
					StringBuffer sb1 = new StringBuffer();
					for (Header header : requestHeaders) {
						String name = header.getName();
						String value = header.getValue();
						sb1.append(name + "：" + value + "\n");
					}
					CloseableHttpResponse response = httpClient.execute(httpGet);
					Header[] responseHeaders = response.getAllHeaders();
					StringBuffer sb2 = new StringBuffer();
					for (Header header : responseHeaders) {
						String name = header.getName();
						String value = header.getValue();
						sb2.append(name + "：" + value + "\n");
					}

					if (response != null) {
						HttpEntity entity = response.getEntity(); // 获取网页内容
						byte[] byteArray = EntityUtils.toByteArray(entity);
						FileUtils.writeByteArrayToFile(file, byteArray);
						String md5Hex = DigestUtils.md5Hex(new FileInputStream(filePath));
						logger.info("处理网络资源文件\n\tHTTP请求URL：" + urlStr + "\n\tHTTP请求方法：GET\n\tHTTP请求头：\n"
								+ sb1.toString() + "\n\tHTTP响应头：\n" + response.getStatusLine().toString()+"\n"
								+ sb2.toString() + "\n\tHTTP响应数据文件路径：" + filePath.replace(baseDir, "") + "\n\tHTTP响应数据文件md5：" + md5Hex+"\n\n");// 需输出到日志文件
					}
					if (response != null)
						response.close();
					if (httpClient != null)
						httpClient.close();
				}
			}
		} catch (ParseException | IOException e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * @Description:处理css文件中的图片url并下载
	 * @param host 域名
	 * @param baseUrl 图片资源的uri或url
	 * @param resourceUrl 根据baseUrl处理后的资源的网络路径
	 * @param cssUrl css文件的网络地址
	 */
	private static void handleUrlAndDownCssImage(String protocol, String host, String baseUrl, String cssUrl){
		try {
			String resourceUrl = null;
			if (StringUtils.isNotBlank(cssUrl) && baseUrl.startsWith("../")) {
				String baseUrl1 = baseUrl.replace("../", "/");
				String cssUrl1 = cssUrl.substring(0, cssUrl.lastIndexOf("/"));//截取后结尾没有‘/’
				String cssUrl2 = cssUrl1.substring(0, cssUrl1.lastIndexOf("/"));//截取后结尾没有‘/’
				String parentDir = cssUrl2.substring(cssUrl2.lastIndexOf("/"));
				resourceUrl = protocol + "//" + host + parentDir + baseUrl1;
				baseUrl = baseUrl.replace("../", "");
			} else if (!baseUrl.startsWith("//") && baseUrl.startsWith("/")) {
				resourceUrl = protocol + "//" + host + baseUrl;
				baseUrl = baseUrl.replaceFirst("/", "");
			} else if (baseUrl.startsWith("http:")||baseUrl.startsWith("https:")) {
				resourceUrl = baseUrl;
				baseUrl = baseUrl.replace("http://", "").replace("https://", "");
			} else if (baseUrl.startsWith("//")) {
				resourceUrl = protocol + baseUrl;
				baseUrl = baseUrl.substring(2);
			}
			baseUrl = baseUrl.replace("/", File.separator);
			int indexOf = baseUrl.indexOf("?");// 有的图片地址后有问号和参数（如聚美）
			if (indexOf > 0) baseUrl = baseUrl.substring(0, indexOf);
			String staticFilePath = fileRootPath + resourceDir + File.separator + baseUrl;
			copyURLToFile(resourceUrl, staticFilePath, protocol, host);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @Description:处理资源的uri或url（去掉前面的'/'、'//'、'http'或'https'等）
	 * @param baseUrl 资源的相对路径
	 * @param baseDir 文件保存的基本目录
	 * @return
	 */
	private static String handleUrlToFilePath(String baseUrl){
		if (baseUrl.startsWith("//")) {
			baseUrl = baseUrl.substring(1);
		} else if (baseUrl.startsWith("https://") || baseUrl.startsWith("http://")) {// 防止资源路径为网络路径
			baseUrl = baseUrl.replace("http:/", "").replace("https:/", "");
		} else if(baseUrl.startsWith("../")){
			baseUrl = baseUrl.substring(2);
		}
		int indexOf = baseUrl.indexOf("?");// 有的图片地址后有问号和参数（如聚美）
		if (indexOf > 0) baseUrl = baseUrl.substring(0, indexOf);
		return baseUrl;
	}
	
	/**
	 * @Description:保存html页面
	 * @param baseDir 文件保存的基础目录
	 * @param htmlText 抓取的网页内容（处理后的）
	 * @param fileName html名称（根据页面title处理获得）
	 */
	private static void downHtml(String htmlText, String fileName, Charset charset, String host){
		String regEx = "[`~!@#$%^&*()\\-+={}':;,\\[\\].<>/?￥%…（）+|【】‘；：”“’。，、？\\s]";// 特殊字符正则
		Pattern p = Pattern.compile(regEx);
		Matcher matcher = p.matcher(fileName);
		fileName = matcher.replaceAll("").trim();
		if ("www.baidu.com".equals(host)) htmlText = htmlText.replaceAll(">\\s+<", "><");
		try {
			FileUtils.writeStringToFile(new File(fileRootPath + fileName + ".html"), htmlText, charset);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @Description:根据url获取网页的html内容及对应的截图、缩略图
	 * @param url 网页地址
	 * @param screenShotPath 截图保存路径
	 * @param encoding 网页内容编码
	 * @return
	 */
	private static String getHtmlContent(String url, String screenShotPath, Charset encoding){
		try {
			Process p = runtime.exec(pluginPath + blank + spiderJsPath + blank + url + blank + screenShotPath);
			InputStream inputStream = p.getInputStream();
			String content = IOUtils.toString(inputStream, encoding);
			return content;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * @Description:记录服务器操作系统信息
	 * @param cmd 命令
	 * @return
	 */
	private static void exeCmd(String cmd, String title){
		try {
			Process p = runtime.exec(cmd);
			InputStream inputStream = p.getInputStream();
			String content = IOUtils.toString(inputStream, "UTF-8");
			logger.info(title + "\n\t指令："+cmd+"\n\t运行输出："+content);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @Description:把服务器信息写入文件
	 */
	@SuppressWarnings("unused")
	private static void writeServerInfoToFile(){
		exeCmd("uname -a", osInfo);//获取服务器操作系统信息
		exeCmd("ifconfig", networkConfig);//获取服务器网络配置信息
		//以下获取服务器硬件信息
		exeCmd("sudo dmidecode -s system-manufacturer", hardware);
		exeCmd("sudo dmidecode -s system-product-name", hardware);
		exeCmd("sudo dmidecode -s system-serial-number", hardware);
		exeCmd("sudo dmidecode -s system-uuid", hardware);
		exeCmd("sudo dmidecode -s baseboard-serial-number", hardware);
		exeCmd("sudo dmidecode -s processor-version", hardware);
	}
	
	/**
	 * @Description:去除list中重复数据
	 * @param cssList 储存样式表文件的网络地址
	 * @return
	 */
	private static List<String> removeDuplicate(List<String> cssList) {
	    HashSet<String> set = new HashSet<String>(cssList);
	    cssList.clear();
	    cssList.addAll(set);
	    return cssList;
	}
	
	/**
	 * @Description:从样式中检索并提取图片url，格式为：url(\"../images/iph_solutebg.jpg\")
	 * @param str 待提取的文本
	 * @return
	 */
	private static List<String> retrieveUrlFromStyle(String str){
		str = str.replace("\\/", "/");
		List<String> list = new ArrayList<String>();
		Pattern pattern = Pattern.compile("(?<=url\\()(.*?)(?=\\))");
		Matcher matcher = pattern.matcher(str);
		while(matcher.find()){
			String url = trimSpace(matcher.group()).replace("\"", "").replace("'", "");
			if(StringUtils.isBlank(url)) continue;
			if(url.contains("#")) continue;//包含#的用于缓存的url样式排除
			if(url.startsWith("data:image")) continue;//以data:image开头的base64图片数据不抓取
			list.add(url);
		}
		return list;
	}
	
	/**
	 * @Description:去除空格等
	 * @param str 待处理的字符串
	 * @return
	 */
	private static String trimSpace(String str) {
		Pattern p = Pattern.compile("\\s*|\t|\r|\n");
		Matcher m = p.matcher(str);
		return m.replaceAll("").replace("\\/", "/");//把\/替换成/
	}
	
	/**
	 * @Description:去掉文本中以下三种注释的内容
	 * @param str 待trim的字符串
	 * @return
	 */
	private static String trimAnnotion(String str) {
		String reg1 = "<!--[\\s\\S]*?-->";
		String reg2 = "<%--[\\s\\S]*?--%>";
		String reg3 = "/\\*[\\s\\S]*?\\*/";
		Pattern pattern1 = Pattern.compile(reg1);
		Pattern pattern2 = Pattern.compile(reg2);
		Pattern pattern3 = Pattern.compile(reg3);
		Matcher matcher1 = pattern1.matcher(str);
		String replace1 = matcher1.replaceAll("");
		Matcher matcher2 = pattern2.matcher(replace1);
		String replace2 = matcher2.replaceAll("");
		Matcher matcher3 = pattern3.matcher(replace2);
		String replace3 = matcher3.replaceAll("");
		return replace3;
	}
	
	/**
	 * @Description:根据原图片获取300*450的缩略图
	 * @param fileRootPath 文件根目录
	 * @return 返回缩略图路径
	 */
	public static byte[] getThumbnail(String fileRootPath){
		if(StringUtils.isBlank(fileRootPath)) return null;
		try {
			String imgPath = fileRootPath + "screenshot.png";
			BufferedImage read = ImageIO.read(new File(imgPath));
			Builder<File> builder = Thumbnails.of(imgPath);
			int width = read.getWidth();
			int height = read.getHeight();
			log.info("原图宽：{}，高：{}", width, height);
			BufferedImage imageBuffered = null;
			if(height > 1220) {
				imageBuffered = builder.sourceRegion(350, 0, 1220, 1220).width(300).asBufferedImage();
			} else {
				imageBuffered = builder.sourceRegion(510, 0, 900, 900).width(300).asBufferedImage();
			}
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			ImageIO.write(imageBuffered, "png", os);
			return os.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * @Description:获取原始截图
	 * @param fileRootPath 原始截图保存的根目录
	 * @return 返回文件数据
	 */
	public static byte[] getScreenShot(String fileRootPath){
		try {
			byte[] bytes = FileUtils.readFileToByteArray(new File(fileRootPath + "screenshot.png"));
			return bytes;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
