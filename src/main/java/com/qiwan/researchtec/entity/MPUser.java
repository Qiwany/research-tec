package com.qiwan.researchtec.entity;

/**
 * <br>类 名: MPUser
 * <br>描 述: 用于测试微信小程序数据回显的类
 * <br>作 者: wangkefengname@163.com
 * <br>创 建: 2019年2月18日 下午1:39:49
 * <br>版 本: v1.0.0
 */
public class MPUser {
	
	private Integer id;
	
	private String name;
	
	private Character sex;
	
	private Integer age;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Character getSex() {
		return sex;
	}

	public void setSex(Character sex) {
		this.sex = sex;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "MPUser [id=" + id + ", name=" + name + ", sex=" + sex + ", age=" + age + "]";
	}
}
