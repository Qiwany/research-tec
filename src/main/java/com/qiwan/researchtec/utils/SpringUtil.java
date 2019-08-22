package com.qiwan.researchtec.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

/**
 * Spring Web 处理
 * 
 * @author maomh
 *
 */
public class SpringUtil {
	
	/**
	 * 获取当前线程绑定的 {@link ServletRequestAttributes} 实例
	 * 
	 * @return
	 */
	public static ServletRequestAttributes sra() {
		return (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
	}
	
	
	/**
	 * 获取当前线程绑定的 HttpServletRequest 实例
	 * 
	 * @return
	 */
	public static HttpServletRequest req() {
		return sra().getRequest();
	}
	
	
	/**
	 * 获取当前线程绑定的 HttpServletResponse 实例
	 * 
	 * @return
	 */
	public static HttpServletResponse resp() {
		return sra().getResponse();
	}

	
	/**
	 * 获取当前会话
	 * 
	 * @param create - 如果当前会话不存在 是否创建一个新的
	 * @return
	 */
	public static HttpSession s(boolean create) {
		return req().getSession(create);
	}
	
	/**
	 * 获取当前会话 - 如果不存在则创建一个新的会话
	 * 
	 * @return
	 */
	public static HttpSession s() {
		return s(true);
	}
	
	/**
	 * 获取当前请求上下文里的 WebApplicationContext 实例
	 * 
	 * @return
	 */
	public static WebApplicationContext wac() {
		return RequestContextUtils.findWebApplicationContext(req());
	}
}
