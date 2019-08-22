package com.qiwan.researchtec;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <br>类 名: EnumTest
 * <br>描 述: 
 * <br>作 者: wangkefengname@163.com
 * <br>创 建: 2019年8月15日 上午11:04:46
 * <br>版 本: v1.0.0
 */
@Getter
@AllArgsConstructor
public enum EnumTest {
	MON("星期一", 1),
	TUE("星期二", 2),
	WED("星期三", 3),
	THU("星期四", 4),
	FRI("星期五", 5),
	SAT("星期六", 6),
	SUN("星期日", 7);
	
	private String des;
	
	private int code;
}