package com.ztesoft.net.mall.core.timer;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import com.ztesoft.net.mall.core.service.IOrderQueueManager;

/**
 * 处理订单失败队列表数据定时任务
 * @作者 MoChunrun
 * @创建日期 2014-1-22 
 * @版本 V 1.0
 */
public class OrderFailQueueTimer {
	private static Logger logger = Logger.getLogger(OrderFailQueueTimer.class);
	@Resource
	private IOrderQueueManager orderQueueManager;
	
	public void run(){
		//logger.info("处理失败队列定时任务");
		if(!CheckTimerServer.isMatchServer(this.getClass().getName(),"run"))
  			return ;
		logger.info("处理失败队列定时任务执行开始");
		orderQueueManager.orderCollectionFailData();
		
	}
	
	
}
