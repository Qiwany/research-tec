package com.qiwan.researchtec.entity;

import lombok.Data;

/**
 * <br>类 名: User
 * <br>描 述: 
 * <br>作 者: wangkefengname@163.com
 * <br>创 建: 2019年7月5日 上午10:31:51
 * <br>版 本: v1.0.0
 */
//@Getter
//@Setter
//@ToString
//@EqualsAndHashCode
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
@Data
//@Accessors
public class User {
	
//	@ToString.Exclude
	private Long id;
//	
	private String name;
//	
	private Integer age;
	
//	private @Singular List<String> books;
	
//	@ToString.Include
//	private static String country = "China";
//	
//	private final String addr = "qqq";
//	@Synchronized("name")
//	public void test(){
//		System.out.println("test @Synchronized");
//	}
}
