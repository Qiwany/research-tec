package com.qiwan.researchtec.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.system.ApplicationHome;

import com.alibaba.fastjson.JSON;

/**
 * 类 名: HttpClientUtil <br>
 * 描 述: http请求工具类（含单双向认证） <br>
 * 作 者: wangkefengname@163.com <br>
 * 创 建: 2019年3月4日 下午2:58:46 <br>
 * 版 本: v1.0.0
 */
public class HttpClientUtil {

	private static Logger log = LoggerFactory.getLogger(HttpClientUtil.class);
	
	private static String CLIENT_CERT_FILE = "";// 客户端证书路径
	
	private static String SERVER_CER_FILE = "";// 服务端证书路径

	private final static String CLIENT_PWD = "cert";// 客户端证书密码

	private static String TRUST_STRORE_FILE = "";// 信任库路径

	private final static String TRUST_STORE_PWD = "selfgen";// 信任库密码
	
	private static RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(60000)
			.setConnectionRequestTimeout(30000).setSocketTimeout(60000).build();
	
	static {
		ApplicationHome home = new ApplicationHome();
		String serverDir = home.getDir().getParentFile().getAbsolutePath();// 获取项目所在绝对路径
		log.info("serverDir：{}", serverDir);
		CLIENT_CERT_FILE = serverDir + File.separator + "client.p12";
		TRUST_STRORE_FILE = serverDir + File.separator + "selfgen.truststore";
		SERVER_CER_FILE = serverDir + File.separator + "server.crt";
	}

	/**
	 * @Description: 发送post请求
	 * @param url 请求地址
	 * @param paramEntity 请求参数
	 * @param headers 请求头（没有可设空）
	 * @param config 请求设置（没有可设空）
	 * @return
	 */
	public static String sendPost(String url, Object paramEntity, Header[] headers, RequestConfig config) {
		if (StringUtils.isBlank(url)) {
			throw new NullPointerException("请求地址为空异常！：url is null Exception.");
		}
		HttpEntity httpEntity = null;
		if (null != paramEntity) {
			String param = JSON.toJSONString(paramEntity);
			httpEntity = new StringEntity(param, "UTF-8");
		}
		return sendPostRequest(url, httpEntity, headers, config);
	}

	/**
	 * @Description: 发送post请求
	 * @param url 请求地址
	 * @param paramEntity 请求参数
	 * @param headers 请求头
	 * @param config 请求设置
	 * @return
	 */
	private static String sendPostRequest(String url, HttpEntity paramEntity, Header[] headers, RequestConfig config) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost();
		try {
			// 设置请求URL
			httpPost.setURI(new URI(url));
			if (null != headers){
				httpPost.setHeaders(headers);
			}
			// 设置请求参数
			if (null != paramEntity){
				httpPost.setEntity(paramEntity);
			}
			if (null != config){
				httpPost.setConfig(config);
			} else {
				httpPost.setConfig(requestConfig);
			}
			// 请求结果处理器
			CloseableHttpResponse response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			String result = "";
			// 按指定编码转换结果实体为String类型
			if (entity != null) {
				result = EntityUtils.toString(entity, "UTF-8");
			}
			EntityUtils.consume(entity);
			return result;
		} catch (URISyntaxException e) {
			log.error("URI 不正确", e);
		} catch (ClientProtocolException e) {
			log.error("执行Http请求异常", e);
		} catch (IOException e) {
			log.error("执行Http请求异常", e);
		} catch (Exception e) {
			log.error("执行Http请求异常", e);
		} finally {
			httpPost.releaseConnection();
			HttpClientUtils.closeQuietly(httpClient);
		}
		return null;
	}

	/**
	 * @Description:发送post请求
	 * @param url
	 *            请求地址
	 * @return
	 */
	public static String sendPost(String url) {
		return sendPostRequest(url, null, null, null);
	}
	
	/**
	 * @Description:绕过https单向认证
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 */
    private static SSLContext createIgnoreVerifySSL() throws NoSuchAlgorithmException, KeyManagementException {
        SSLContext sc = SSLContext.getInstance("SSLv3");
        // 实现一个X509TrustManager接口，用于绕过验证，不用修改里面的方法
        X509TrustManager trustManager = new X509TrustManager() {
            @Override
            public void checkClientTrusted(
                    java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                    String paramString) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(
                    java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                    String paramString) throws CertificateException {
            }

            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };
        sc.init(null, new TrustManager[] { trustManager }, null);
        return sc;
    }
    
    
    
    /**
     * @Description:忽略https的单向认证接口调用
     * @param url 接口地址
     * @param paramEntity 请求参数
     * @return
     */
    public static String sendPostIgnoreUniSSL(String url, Object paramEntity) {
    	CloseableHttpClient httpClient = null;
        HttpPost httpPost = new HttpPost();
        String body = "";
        try {
        	httpPost.setURI(new URI(url));
			SSLContext sslcontext = createIgnoreVerifySSL();
			// 设置协议http和https对应的处理socket链接工厂的对象
			Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
			    .register("http", PlainConnectionSocketFactory.INSTANCE)
			    .register("https", new SSLConnectionSocketFactory(sslcontext))
			    .build();
			PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
			HttpClients.custom().setConnectionManager(connManager);
			//创建自定义的httpclient对象
			httpClient = HttpClients.custom().setConnectionManager(connManager).build();
			HttpEntity httpEntity = null;
			if (null != paramEntity) {
				String param = JSON.toJSONString(paramEntity);
				httpEntity = new StringEntity(param, "UTF-8");
			}
			//设置参数到请求对象中
			httpPost.setEntity(httpEntity);
			//设置header信息，指定报文头【Content-type】、【User-Agent】
			httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");
			httpPost.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
			//执行请求操作，并拿到结果（同步阻塞）
			CloseableHttpResponse response = httpClient.execute(httpPost);
			//获取结果实体
			HttpEntity entity = response.getEntity();
			if (entity != null) {
			    body = EntityUtils.toString(entity, "UTF-8");
			}
			EntityUtils.consume(entity);
			//释放链接
			response.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			httpPost.releaseConnection();
			if(httpClient != null) HttpClientUtils.closeQuietly(httpClient);
		}
        return body;
    }
    
    public static String sendGet(String url) {
    	return sendGet(url, null);
    }
    
    /**
     * @Description:发送get请求
     * @param url 请求地址
     * @param map 请求参数（没有可设空）
     * @return
     */
	public static String sendGet(String url, Map<String, Object> paramsMap) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet();
		List<NameValuePair> paramsList = new ArrayList<NameValuePair>();// 创建参数队列
		String body = "";
		if (null != paramsMap && paramsMap.size() > 0) {
			for (Map.Entry<String, Object> entry : paramsMap.entrySet()) {
				paramsList.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
			}
		}
		try {
			String params = "";
			if(paramsList.size() > 0){
				params = EntityUtils.toString(new UrlEncodedFormEntity(paramsList, "UTF-8"));
				url = url + "?" + params;
			}
			httpGet.setURI(new URI(url));
			httpGet.setConfig(requestConfig);
			CloseableHttpResponse response = httpClient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				body = EntityUtils.toString(entity);
			}
			EntityUtils.consume(entity);
			response.close();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			httpGet.releaseConnection();
			HttpClientUtils.closeQuietly(httpClient);
		}
		return body;
	}
	
	/**
	 * @Description: get方式使用证书请求双向认证接口
	 * @param url 请求地址
	 * @param paramsMap 请求参数（没有可设空）
	 * @return
	 */
	public static String sendBiAuthGet(String url, Map<String, Object> paramsMap) {
		CloseableHttpClient httpClient = null;
		HttpGet httpGet = new HttpGet();
		String body = "";
		try {
			// 初始化密钥库
			KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
			KeyStore keyStore = getKeyStore(CLIENT_CERT_FILE, CLIENT_PWD, "PKCS12");
			keyManagerFactory.init(keyStore, CLIENT_PWD.toCharArray());
			// 初始化信任库
			TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance("SunX509");
			KeyStore trustkeyStore = getKeyStore(TRUST_STRORE_FILE, TRUST_STORE_PWD, "JKS");
			trustManagerFactory.init(trustkeyStore);
			SSLContext sslContext = SSLContext.getInstance("SSL");
			sslContext.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(),
					new SecureRandom());
			httpClient = HttpClients.custom().setSSLContext(sslContext).build();
			List<NameValuePair> paramsList = new ArrayList<NameValuePair>();// 创建参数队列
			if (null != paramsMap && paramsMap.size() > 0) {
				for (Map.Entry<String, Object> entry : paramsMap.entrySet()) {
					paramsList.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
				}
			}
			String params = "";
			if (paramsList.size() > 0) {
				params = EntityUtils.toString(new UrlEncodedFormEntity(paramsList, "UTF-8"));
				url = url + "?" + params;
			}
			httpGet.setURI(new URI(url));
			CloseableHttpResponse response = httpClient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				body = EntityUtils.toString(entity);
			}
			EntityUtils.consume(entity);
			response.close();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			httpGet.releaseConnection();
			HttpClientUtils.closeQuietly(httpClient);
		}
		return body;
	}
	
	/**
	 * @Description: post方式使用证书请求双向认证接口
	 * @param url 请求地址
	 * @param paramsMap 请求参数（没有可设空）
	 * @return
	 */
	public static String sendBiAuthPost(String url, Object paramEntity) {
		CloseableHttpClient httpClient = null;
		HttpPost httpPost = new HttpPost();
		String body = "";
		try {
			// 初始化密钥库
			KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
			KeyStore keyStore = getKeyStore(CLIENT_CERT_FILE, CLIENT_PWD, "PKCS12");
			keyManagerFactory.init(keyStore, CLIENT_PWD.toCharArray());
			// 初始化信任库
			TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance("SunX509");
			KeyStore trustkeyStore = getKeyStore(TRUST_STRORE_FILE, TRUST_STORE_PWD, "JKS");
			trustManagerFactory.init(trustkeyStore);
			SSLContext sslContext = SSLContext.getInstance("SSL");
			sslContext.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(),
					new SecureRandom());
			httpClient = HttpClients.custom().setSSLContext(sslContext).build();
			if (null != paramEntity) {
				String param = JSON.toJSONString(paramEntity);
				HttpEntity httpEntity = new StringEntity(param, "UTF-8");
				httpPost.setEntity(httpEntity);
			}
			httpPost.setURI(new URI(url));
			CloseableHttpResponse response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				body = EntityUtils.toString(entity);
			}
			EntityUtils.consume(entity);
			response.close();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			httpPost.releaseConnection();
			HttpClientUtils.closeQuietly(httpClient);
		}
		return body;
	}

	/**
	 * 获得KeyStore
	 *
	 * @param keyStorePath
	 * @param password
	 * @return
	 * @throws Exception
	 */
	private static KeyStore getKeyStore(String keyStorePath, String password, String type) throws Exception {
		FileInputStream is = new FileInputStream(keyStorePath);
		KeyStore ks = KeyStore.getInstance(type);
		ks.load(is, password.toCharArray());
		is.close();
		return ks;
	}
	
	/**
	 * @Description: post方式请求单向认证接口（需验证服务端证书）
	 * @param url 接口地址
	 * @param paramEntity 参数对象
	 * @return
	 */
	public static String sendUniAuthPost(String url, Object paramEntity){
		CloseableHttpClient httpClient = null;
		HttpPost httpPost = new HttpPost();
		String body = "";
		try {
			CertificateFactory cAf = CertificateFactory.getInstance("X.509");
			FileInputStream caIn = new FileInputStream(SERVER_CER_FILE);
			X509Certificate ca = (X509Certificate) cAf.generateCertificate(caIn);
			KeyStore keyStore = KeyStore.getInstance("JKS");
			keyStore.load(null, null);
			keyStore.setCertificateEntry("ca-certificate", ca);
			TrustManagerFactory tmf = TrustManagerFactory.getInstance("PKIX");
			tmf.init(keyStore);
			SSLContext sslContext = SSLContext.getInstance("TLSv1");
			sslContext.init(null, tmf.getTrustManagers(), new SecureRandom());
			httpClient = HttpClients.custom().setSSLContext(sslContext).build();
			if (null != paramEntity) {
				String param = JSON.toJSONString(paramEntity);
				HttpEntity httpEntity = new StringEntity(param, "UTF-8");
				httpPost.setEntity(httpEntity);
			}
			httpPost.setURI(new URI(url));
			CloseableHttpResponse response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				body = EntityUtils.toString(entity);
			}
			EntityUtils.consume(entity);
			response.close();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}  finally {
			httpPost.releaseConnection();
			HttpClientUtils.closeQuietly(httpClient);
		}
		return body;
	}
}