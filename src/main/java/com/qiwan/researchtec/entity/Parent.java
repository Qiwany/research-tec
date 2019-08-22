package com.qiwan.researchtec.entity;

/**
 * <br>类 名: Parent
 * <br>描 述: 
 * <br>作 者: wangkefengname@163.com
 * <br>创 建: 2019年6月18日 上午10:40:21
 * <br>版 本: v1.0.0
 */
public class Parent {
	
	private String userType;

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	@Override
	public String toString() {
		return "Parent [userType=" + userType + "]";
	}
}
