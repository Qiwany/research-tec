package com.qiwan.researchtec.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * <br>类 名: TestSchedule
 * <br>描 述: 
 * <br>作 者: wangkefengname@163.com
 * <br>创 建: 2019年5月6日 上午10:00:47
 * <br>版 本: v1.0.0
 */
@Component
@ConditionalOnProperty(name = { "enable.schedule" }, havingValue = "true", matchIfMissing = false)
public class TestSchedule {
	
	private static Logger log = LoggerFactory.getLogger(TestSchedule.class);
	
	@Scheduled(fixedDelay = 1000)
	public void test1(){
		String name = Thread.currentThread().getName();
		log.info("{}=========Test1执行=========", name);
	}
	
	@Scheduled(fixedRate = 1000)
	public void test2(){
		String name = Thread.currentThread().getName();
		log.info("{}=========Test2执行=========", name);
	}
	
	@Scheduled(initialDelay = 1000, fixedRate = 3000)
	public void test3(){
		String name = Thread.currentThread().getName();
		log.info("{}=========Test3执行=========", name);
	}
	
	@Scheduled(fixedDelayString = "2000")
	public void test4(){
		String name = Thread.currentThread().getName();
		log.info("{}=========Test4执行=========", name);
	}
	
	@Scheduled(cron = "0/5 * * * * ?")
	public void test5(){
		String name = Thread.currentThread().getName();
		log.info("{}=========Test5执行=========", name);
	}
}
