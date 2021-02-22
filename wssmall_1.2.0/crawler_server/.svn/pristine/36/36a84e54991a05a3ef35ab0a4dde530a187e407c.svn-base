package com.ztesoft.autoprocess.base;

import org.apache.log4j.Logger;

import zte.net.service.IOrderToExamineService;

import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.mall.core.timer.AuditOrderZBTask;

public class CrawlerThread extends Thread {
	private static Logger logger = Logger.getLogger(CrawlerThread.class);
	private String threadName;
	
	    @Override
	    public void run() {
	    	IOrderToExamineService orderToExamineService=SpringContextHolder.getBean("orderToExamineService");
	    	String username = ZBSystemClient.getLoginZbName();
			ZBSystemClient client = ClientPool.get(username);//获取总商登录客户端对象
			if (client != null) {//对象不为空则进行审核操作
				try {
					logger.info("run thread Name:"+threadName);
					//调用抓取新用户逻辑
					orderToExamineService.parseOrderVerifyOrderCtn(threadName,AuditOrderZBTask.queryType,null);
					//调用抓取老用户逻辑
					orderToExamineService.getOrderAllocationInfoOrderCtn(threadName);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				logger.info("======[线程："+threadName+"]批量订单抓取标准化获取到客户端对象不为空！");
	        } else {
	            logger.info("======[线程："+threadName+"]批量订单抓取标准化获取不到客户端对象，请检查自动登录的cookie是否已失效!");
	        }
	    	
	    }

		public CrawlerThread(String threadName) {
			super();
			this.threadName = threadName;
		}
		
	}