package com.ztesoft.lucence;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

import com.ztesoft.net.framework.context.spring.SpringContextHolder;

public class SearchExecuter {
	private static Logger logger = Logger.getLogger(SearchExecuter.class);
	/**
	 * 创建固定大小为5的线程池
	 */
	private static final ExecutorService executor = Executors.newFixedThreadPool(5);
	
	public static void executeOrderTask(SearchInitTask task){
		executor.execute(task);
	}
	
	public static class SearchInitTask implements Runnable{

		private LucenceConfig config;
		
		public SearchInitTask(LucenceConfig config){
			this.config = config;
		}
		
		@Override
		public void run() {
			ILucenceManager lm = SpringContextHolder.getBean("lucenceManager");
			AbsLucence lucenceService = SpringContextHolder.getBean(config.getSearch_bean());
			try{
				logger.info(config.getSearch_bean()+":===开始执行=====");
				lm.updateConfigStatus(config.getId(), "00R");
				String end_time = lucenceService.perform(config);
				lm.updateConfigStatus(config.getId(), "00A", config.getExe_min(), end_time, "执行成功");
				logger.info(config.getSearch_bean()+":===执行成功=====");
			}catch(Exception ex){
				ex.printStackTrace();
				lm.updateConfigStatus(config.getId(), "00A", config.getExe_min(), null, "执行失败["+ex.getMessage()+"]");
				logger.info(config.getSearch_bean()+":===执行失败=====");
			}
		}
		
	}
}
