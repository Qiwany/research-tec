package com.qiwan.researchtec;

import java.util.Arrays;
import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.junit.Test;

/**
 * <br>类 名: LambdaTest
 * <br>描 述: Lambda及函数式接口测试
 * <br>作 者: wangkefengname@163.com
 * <br>创 建: 2019年3月26日 下午1:31:17
 * <br>版 本: v1.0.0
 */
public class LambdaTest {
	
	private static List<String> strings = Arrays.asList("Java", "Scala", "C++", "Python", "Go", "false", "true", "Swift");
	
	private static List<Integer> integers = Arrays.asList(1, 2, 1, 1, 3, 4, 3, 5);
	
	/**
	 * @Description:Lambda表达式遍历List集合
	 */
	@Test
	public void test1(){
		strings.forEach(n -> System.out.println(n));
		strings.forEach(System.out::println);
	}
	
	/**
	 * @Description:测试Lambda表达式和函数接口
	 */
	@Test
	public void test2(){
		System.out.println("输出长度 >=4 的字符串：");
		strings.stream().filter(name -> name.length() >= 4).forEach(System.out::println);
		
		System.out.println("输出以“S”开头的字符串：");
		strings.stream().filter(name -> name.startsWith("S")).forEach(System.out::println);
		
		System.out.println("输出以“n”开头的字符串：");
		strings.stream().filter(name -> name.endsWith("a")).forEach(System.out::println);
		
		System.out.println("输出所有字符串：");
		strings.stream().filter(name -> true).forEach(System.out::println);;
		
		System.out.println("不输出任何字符串：");
		strings.stream().filter(name -> false).forEach(System.out::println);
	}
	
	/**
	 * @Description:测试 java.util.function.Predicate 的条件组合
	 */
	@Test
	public void test3(){
		Predicate<String> p1 = n -> n.endsWith("a");//条件：以“a”结尾的字符串
		Predicate<String> p2 = n -> n.length() == 4;//条件：长度等于4的字符串
		System.out.println("输出以“a”结尾且长度为4的字符串：");
		strings.stream().filter(p1.and(p2)).forEach(System.out::println);
		
		System.out.println("输出以“a”结尾或长度为4的字符串：");
		strings.stream().filter(p1.or(p2)).forEach(System.out::println);
	}
	
	
	/**
	 * @Description:测试map、reduce和filter方法
	 */
	@Test
	public void test4(){
		System.out.println("输出集合中每个数字的平方数：");
		integers.stream().map(num -> Math.pow(num, 2)).forEach(n -> System.out.println(n.intValue()));
		
		System.out.println("输出集合中的最大数字：");
		Integer max = integers.stream().reduce(Integer::max).get();
		System.out.println(max);
		
		System.out.println("输出集合中的数字总和：");
		Integer sum = integers.stream().reduce((a, b) -> a + b).get();
		System.out.println(sum);
		
		System.out.println("根据条件过滤并重新组装成集合输出：");
		integers.stream().filter(n -> n >= 3).collect(Collectors.toList()).forEach(System.out::println);
		
		System.out.println("把集合中的每个数字转成字符串并拼接输出：");
		String str = integers.stream().map(x -> x.toString()).collect(Collectors.joining(","));
		System.out.println(str);
		
		System.out.println("去除集合中的重复元素放入新集合并输出：");
		integers.stream().distinct().collect(Collectors.toList()).forEach(System.out::println);
		
		IntSummaryStatistics stat = integers.stream().mapToInt(x -> x).summaryStatistics();
		System.out.println("集合中最大的数字为："+stat.getMax());
		System.out.println("集合中最小的数字为："+stat.getMin());
		System.out.println("集合中数字的平均数为："+stat.getAverage());
		System.out.println("集合中数字总和为："+stat.getSum());
		System.out.println("集合中数字个数为："+stat.getCount());
	}
	
	/**
	 * @Description:测试数组排序sort方法
	 */
	@Test
	public void test5(){
		System.out.println("数字集合排序输出：");
		integers.sort((a, b) -> a - b);
		integers.forEach(System.out::println);
		
		System.out.println("字符串集合排序输出：");
		strings.sort((a, b) -> a.compareTo(b));
		strings.forEach(System.out::println);
	}
	
	/**
	 * @Description:测试sorted方法和Comparator接口
	 */
	@Test
	public void test6(){
		System.out.println("集合元素升序之后放入新集合：");
		List<Integer> collect1 = integers.stream().sorted(Comparator.naturalOrder()).collect(Collectors.toList());
		collect1.forEach(System.out::println);
		
		System.out.println("集合元素降序之后放入新集合：");
		List<String> collect2 = strings.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
		collect2.forEach(System.out::println);
	}
	
	/**
	 * @Description:测试并行数组（parallelSort方法可以显著加快多核机器上的数组排序）
	 */
	@Test
	public void test7(){
		Integer[] arr = new Integer [20000];
		//生成20000个20000以内的随机数并放入数组
		Arrays.parallelSetAll(arr, i -> ThreadLocalRandom.current().nextInt(20000));
		
		System.out.println("数组元素默认升序排序：");
		Arrays.parallelSort(arr);
		Arrays.stream(arr).limit(10).forEach(System.out::println);
		
		System.out.println("数组元素默认降序排序：");
		Arrays.parallelSort(arr, Comparator.reverseOrder());
		Arrays.stream(arr).limit(10).forEach(System.out::println);
	}
}
