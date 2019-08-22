package com.qiwan.researchtec;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.junit.Test;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;

import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.qiwan.researchtec.utils.ExcelUtils;
import com.qiwan.researchtec.utils.Pdf2PngUtil;

import net.coobird.thumbnailator.Thumbnails;

/**
 * <br>类 名: OtherTest
 * <br>描 述: 
 * <br>作 者: wangkefengname@163.com
 * <br>创 建: 2019年1月9日 下午3:22:05
 * <br>版 本: v1.0.0
 */
public class OtherTest {
	
	@Test
	public void test1(){
		try {
			File file = new File("F:\\1.png");//拍摄原图
//			File file = new File("F:\\3.jpg");//非原图
//			File file = new File("F:\\qiwany_转图.jpg");//非原图
//			File file = new File("F:\\Qiwan4.png");//非原图
			Metadata metadata = ImageMetadataReader.readMetadata(file);
			for (Directory d : metadata.getDirectories()) {//打印原版头源信息
//				Collection<Tag> tags = d.getTags();
				for (Tag tag : d.getTags()) {
	                System.out.println(tag);
	            }
            }
			
//			Directory directory = metadata.getDirectory(ExifIFD0Directory.class);//若非数码设备拍摄原图则该处代码为空
//			int orientation = 1;//1：不用旋转
//			int num = ExifIFD0Directory.TAG_ORIENTATION;
//			orientation = directory.getInt(num);
//			System.out.println("orientation："+orientation);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test2(){
		try {
			File file = new File("F:\\1.png");//拍摄原图
//			File file = new File("F:\\3.jpg");//非原图
//			File file = new File("F:\\qiwany_转图.jpg");//非原图
//			File file = new File("F:\\Qiwan4.png");//非原图
			Metadata metadata = ImageMetadataReader.readMetadata(file);
			OK:for (Directory d : metadata.getDirectories()) {//打印原版头源信息
                for (Tag tag : d.getTags()) {
                	String tagName = tag.getTagName();
                	if("Orientation".equals(tagName)){
                		int num = ExifIFD0Directory.TAG_ORIENTATION;
                		int orientation = d.getInt(num);
                		System.out.println("num："+num+"；orientation："+orientation);
                		break OK;
                	}
                	String description = tag.getDescription();
                	System.out.println(tagName + "：" + description);
                }
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test3(){
		double random = Math.random();
		System.out.println("random："+random);
	}
	
	@Test
	public void test4(){
		URL resource = this.getClass().getClassLoader().getResource("ca.crt");//获取resource目录下文件路径
		String path = resource.getPath();
		System.out.println("path："+path);
	}
	
	@Test
	public void test5(){
		ApplicationHome home = new ApplicationHome();
		String dir = home.getDir().getParentFile().getAbsolutePath();//获取项目所在绝对路径
		System.out.println("dir："+dir);
	}
	
	@Test
	public void test6(){
		try {
			String path = ResourceUtils.getURL("classpath:").getPath();
			System.out.println("path："+path);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test7(){
		String str = "0 0 8,9,10   2 * ?";
		
		String[] array = StringUtils.tokenizeToStringArray(str, " ");//以一个或多个空格分隔
		Arrays.stream(array).forEach(System.out::println);
		System.out.println("=============================\n");
		
		String[] split1 = str.split(" ");//以一个空格分隔
		Arrays.stream(split1).forEach(System.out::println);
		System.out.println("=============================\n");
		
		String[] split2 = str.split("\\s+");//以一个或多个空格分隔
		Arrays.stream(split2).forEach(System.out::println);
	}
	
	@Test
	public void test8(){
		List<List<List<String>>> sheets = ExcelUtils.doParse("F:\\data.xlsx", 1, 0, 0);
		for (List<List<String>> sheet : sheets) {
			for (List<String> row : sheet) {
				for (String cell : row) {
					System.out.println(cell);
				}
				System.out.println("=========================");
			}
		}
	}
	
	@Test
	public void test9(){
		List<String> list = new ArrayList<String>();
		list.add("aaa");
		list.add("bbb");
		list.add("aaa");
		list.add("ccc");
		list.add("");
		list.add("");
		list.add(null);
		list.add(null);
		list.add("");
		list.add(null);
		list.forEach(System.out::println);
	}
	
	@Test
	public void test10(){
		String str1 = UUID.randomUUID().toString().replace("-", "");
		String str2 = UUID.randomUUID().toString().replace("-", "");
		System.out.println(str1+str2);
	}
	
	@Test
	public void test11(){
		long currentTimeMillis1 = System.currentTimeMillis();
		Pdf2PngUtil.toPng("F:\\bq.pdf", "F:\\bq.jpg");
		long currentTimeMillis2 = System.currentTimeMillis();
		System.out.println(currentTimeMillis2-currentTimeMillis1);
	}
	
	@Test
	public void test12(){
		try {
			Thumbnails.of("F:\\self.jpg").scale(0.1f).toFile("F:\\111.jpg");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test13(){
		String path = "a/b/c\\d\\e";
		path = path.replaceAll("\\\\|/", "");
		System.out.println("path："+path);
	}
	
	@Test
	public void test14(){
//		List<String> list = null;
		List<String> list = new ArrayList<>();
		for (String string : list) {
			System.out.println("============"+string);
		}
	}
	
	@Test
	public void test15(){
		String str = UUID.randomUUID().toString().replace("-", "");
		System.out.println("str："+str);
	}
	
	@Test
	public void test16(){
		String extension = FilenameUtils.getExtension("group1/M00/00/00/wKgDPVvJsUCAXPFtAAWoYw5yXNs654.png");
		System.out.println("extension："+extension);//png
	}
	
	@Test
	public void test17(){
		Object balance = "0.05";
		String money = "0.01";
		BigDecimal bd1 = new BigDecimal(balance.toString());
		BigDecimal bd2 = new BigDecimal(money);
		Double doubleValue = bd1.add(bd2).doubleValue();
		System.out.println("doubleValue："+doubleValue);
	}
	
	@Test
	public void test18(){
		EnumTest tue = EnumTest.TUE;
		System.out.println("Enum："+tue+"========"+tue.name());
		
		System.out.println(tue.getDes()+"====="+tue.getCode());
	}
}
