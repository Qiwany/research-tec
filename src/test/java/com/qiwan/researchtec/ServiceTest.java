package com.qiwan.researchtec;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.qiwan.researchtec.config.TestConfig;
import com.qiwan.researchtec.config.TestConfig2;
import com.qiwan.researchtec.config.TestConfig3;
import com.qiwan.researchtec.entity.Son;

/**
 * <br>类 名: ServiceTest
 * <br>描 述: 
 * <br>作 者: wangkefengname@163.com
 * <br>创 建: 2019年6月13日 下午2:45:42
 * <br>版 本: v1.0.0
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ServiceTest {
	
	private static final Logger log = LoggerFactory.getLogger(ServiceTest.class);
	
	@Autowired
	private FastFileStorageClient fastFileStorageClient;
	
	@Autowired
	private TestConfig config;
	
	@Autowired
	private TestConfig2 config2;
	
	@Autowired
	private TestConfig3 config3;
	
	@Autowired
	private Son son;
	
	@Test
	public void test1(){
		try {
			File file = new File("F:\\test.png");
			FileInputStream fis = new FileInputStream(file);
			StorePath storePath = fastFileStorageClient.uploadFile(fis, file.length(), "png", null);
			String fullPath = storePath.getFullPath();
			log.info("fullPath：{}", fullPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test2(){
		log.info("{}", config);//测试结果：@Component使用默认的无参构造方法实例化对象
	}
	
	@Test
	public void test3(){//子类构造方法都会隐式地调用（没有显式调用的情况下）super()，如果父类没有无参构造函数，那么在编译的时候就会报错
		log.info("{}", config2);//测试结果（出现异常）：父类的构造方法不能被继承
	}
	
	@Test
	public void test4(){
		log.info("{}", config3);//测试结果：
	}
	
	@Test
	public void test5(){//测试@ConfigurationProperties
		//经测试：
		//1.配置key（除前缀外）与属性名相同可注入
		//2.以‘-’连接的配置key（除前缀外）自动处理成驼峰格式与属性名相同可注入
		//3.父类私有属性拥有公共set方法的，符合上述两条可注入
		log.info("===============================");
		log.info("SON：{}", son);
		log.info("===============================");
	}
}
