package com.qiwan.researchtec.aspect;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * <br>类 名: ShowParamsAspect
 * <br>描 述: 打印方法入参注解，入参为对象时才能打印参数名和参数值
 * <br>作 者: wangkefengname@163.com
 * <br>创 建: 2018年9月7日 下午4:23:19
 * <br>版 本: v1.0.0
 */
@Aspect
@Component
public class ShowParamsAspect {
	
	private static final Logger log = LoggerFactory.getLogger(ShowParamsAspect.class);
    
    public static final String dateformat = "yyyy:MM:dd HH:mm:ss";
    
    @Pointcut("@annotation(com.qiwan.researchtec.annotation.ShowParams)")
    public void showParams(){}
    
    @Around("showParams()")
    public Object around(ProceedingJoinPoint joinPoint){
    	log.info("*********************************");
    	try {
			// 获取方法签名
			MethodSignature signature = (MethodSignature) joinPoint.getSignature();
			//java reflect 相关类，通过反射得到注解
			Method method = signature.getMethod();
			Class<?> targetClass = method.getDeclaringClass();
			//拼凑目标类名和参数名
			String target = targetClass.getName() + "#" + method.getName();
			log.info("joinPoint.getArgs()：{}", joinPoint.getArgs());
	        String params = JSONObject.toJSONStringWithDateFormat(joinPoint.getArgs(), dateformat, SerializerFeature.WriteMapNullValue);
	        log.info("调用方法：{}，入参为：{}", target, params);
			return joinPoint.proceed();
		} catch (Throwable e) {
			log.error(e.getMessage(), e);
		}
    	return null;
    }
}
