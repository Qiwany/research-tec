package com.qiwan.researchtec.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <br>类 名: ShowParams
 * <br>描 述: 打印方法入参的注解
 * <br>作 者: wangkefengname@163.com
 * <br>创 建: 2018年9月7日 下午3:46:28
 * <br>版 本: v1.0.0
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ShowParams {
	
}
