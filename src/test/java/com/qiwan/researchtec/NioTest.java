package com.qiwan.researchtec;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import org.junit.Test;

/**
 * <br>类 名: NIOTest
 * <br>描 述: java nio 测试类
 * <br>作 者: wangkefengname@163.com
 * <br>创 建: 2018年7月30日 上午10:29:55
 * <br>版 本: v1.0.0
 */
//@RunWith(SpringRunner.class)
//@SpringBootTest
public class NioTest {
	
	/**
	 * @Description:从FileChannel读到ByteBuffer
	 */
	@Test
	public void test1(){
		try {
			RandomAccessFile raFile = new RandomAccessFile(new File("F:\\固定对象信息.txt"), "rw");
			FileChannel channel = raFile.getChannel();
			ByteBuffer buf = ByteBuffer.allocate(48);
			int read = channel.read(buf);
			while (read != -1) {
				System.out.println("Read：" + read);
				buf.flip();
				while (buf.hasRemaining()) {
					System.out.print(buf.get());
				}
				buf.clear();
				read = channel.read(buf);
			}
			raFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @Description:从channel把数据传输到当前FileChannel的文件中：transferFrom
	 */
	@Test
	public void test2(){
		try {
			RandomAccessFile fromFile = new RandomAccessFile(new File("F:\\固定对象信息.txt"), "rw");
			FileChannel fromChannel = fromFile.getChannel();
			
			RandomAccessFile toFile = new RandomAccessFile(new File("F:\\固定对象信息1.txt"), "rw");
			FileChannel toChannel = toFile.getChannel();
			
			toChannel.transferFrom(fromChannel, 0, fromChannel.size());
			
			fromChannel.close();
			toChannel.close();
			
			fromFile.close();
			toFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @Description:从channel把数据传输到当前FileChannel的文件中：transferTo
	 */
	@Test
	public void test3(){
		try {
			RandomAccessFile fromFile = new RandomAccessFile("F:\\固定对象信息.txt", "rw");
			FileChannel fromChannel = fromFile.getChannel();
			
			RandomAccessFile toFile = new RandomAccessFile("F:\\固定对象信息1.txt", "rw");
			FileChannel toChannel = toFile.getChannel();
			
			fromChannel.transferTo(0, fromChannel.size(), toChannel);
			
			fromChannel.close();
			toChannel.close();
			
			fromFile.close();
			toFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @Description:往channel中写
	 */
	@Test
	public void test4(){
		long currentTimeMillis = System.currentTimeMillis();
		System.out.println("currentTimeMillis："+currentTimeMillis);
		String newData = "New String to write to file..." + currentTimeMillis;
		ByteBuffer buf = ByteBuffer.allocate(48);
		buf.put(newData.getBytes());
		buf.flip();
		try {
			RandomAccessFile raFile = new RandomAccessFile("F:\\测试文本.txt", "rw");
			FileChannel channel = raFile.getChannel();
			while (buf.hasRemaining()) channel.write(buf);
			raFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @Description:测试size、position和read方法及返回值
	 */
	@Test
	public void test5(){
		try {
			RandomAccessFile raFile = new RandomAccessFile(new File("F:\\固定对象信息.txt"), "rw");
			FileChannel channel = raFile.getChannel();
			ByteBuffer buf = ByteBuffer.allocate(48);
			long size = channel.size();
			System.out.println("size："+size);
			long position = channel.position();
			System.out.println("position："+position);
			channel.position(position + 20);
			int read = channel.read(buf);
			System.out.println("read："+read);
			raFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @Description:测试channel的truncate方法
	 */
	@Test
	public void test6(){
		try {
			RandomAccessFile fromFile = new RandomAccessFile("F:\\固定对象信息.txt", "rw");
			FileChannel fromChannel = fromFile.getChannel();
			fromChannel.truncate(10);
			
			RandomAccessFile toFile = new RandomAccessFile("F:\\固定对象信息2.txt", "rw");
			FileChannel toChannel = toFile.getChannel();
			
			fromChannel.transferTo(0, fromChannel.size(), toChannel);
			
			fromChannel.close();
			toChannel.close();
			
			fromFile.close();
			toFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
