package com.qiwan.researchtec.utils;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.RollingFileAppender;

public class LoggerUtil {
	
	/**
	 * @Description:实例化logger
	 * @param clazz
	 * @param path 上场文件输出目录
	 * @return
	 */
	public static Logger getLog(Class<?> clazz, String path) {
		Logger logger = Logger.getLogger(clazz); // 生成新的Logger
		logger.removeAllAppenders(); // 清空Appender，特別是不想使用現存實例時一定要初期化
		logger.setLevel(Level.INFO); // 设定Logger級別。
		logger.setAdditivity(true); // 设定是否继承父Logger。默认为true，继承root输出；设定false后将不输出root。
		FileAppender appender = new RollingFileAppender(); // 生成新的Appender
		PatternLayout layout = new PatternLayout();
		layout.setConversionPattern("[%d{yyyy-MM-dd HH:mm:ss}]: %m%n"); // log的输出形式
		appender.setLayout(layout);
		appender.setFile(path + "固定过程日志.txt"); // log输出路径
		appender.setEncoding("UTF-8"); // log的字符编码
		appender.setAppend(true); // 日志合并方式： true:在已存在log文件后面追加，false:新log覆盖以前的log
		appender.activateOptions(); // 适用当前配置
		logger.addAppender(appender); // 将新的Appender加到Logger中
		return logger;
	}
}
