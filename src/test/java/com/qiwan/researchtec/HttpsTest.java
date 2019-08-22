package com.qiwan.researchtec;

import org.junit.Test;

import com.qiwan.researchtec.utils.HttpClientUtil;

/**
 * <br>类 名: TestHttps
 * <br>描 述: 
 * <br>作 者: wangkefengname@163.com
 * <br>创 建: 2019年3月4日 上午11:06:12
 * <br>版 本: v1.0.0
 */
public class HttpsTest {
	
	/**
	 * @Description:验证通过证书调用https双向认证接口
	 */
	@Test
	public void test1(){
//		String content = HttpClientUtil.sendBiAuthGet("https://www.qisoft.com", null);//双向认证get请求正常
//		String content = HttpClientUtil.sendBiAuthPost("https://www.qisoft.com", null);//双向认证post请求正常（若请求的是静态资源则需nginx额外配置否则405）
		String content = HttpClientUtil.sendPostIgnoreUniSSL("https://www.qisoft.com", null);//采用忽略单向认证的方式请求异常
		System.out.println("content：" + content);
	}
	
	/**
	 * @Description:验证普通方式调用双向认证接口：出异常
	 */
	@Test
	public void test2(){
		String content = HttpClientUtil.sendPost("https://www.qisoft.com");
		System.out.println("content：" + content);
	}
	
	/**
	 * @Description:采用绕过https证书的策略调用双向认证接口：400 No required SSL certificate was sent
	 */
	@Test
	public void test3(){
		try {
			String content = HttpClientUtil.sendPostIgnoreUniSSL("https://www.qisoft.com", null);
			System.out.println("content：" + content);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @Description:不通过nginx用get和post方式直接请求服务
	 */
	@Test
	public void test4(){
//		String content = HttpClientUtil.sendGet("http://www.qisoft.com:8082/");
		String content = HttpClientUtil.sendPost("http://www.qisoft.com:8082/");
		System.out.println("content：" + content);
	}
	
	/**
	 * @Description:测试https单向认证（nginx只开启单向认证的情况下）
	 */
	@Test
	public void test5(){
		String content = HttpClientUtil.sendUniAuthPost("https://www.qisoft.com", null);//利用服务端证书单向认证请求正常
//		String content = HttpClientUtil.sendPost("https://www.qisoft.com");//普通post请求出异常
//		String content = HttpClientUtil.sendPostIgnoreUniSSL("https://www.qisoft.com", null);//利用忽略SSL认证的方式请求正常
		System.out.println("content：" + content);
	}
}
