package com.ztesoft.net.mall.core.timer;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import com.ztesoft.net.mall.core.service.IOrderHisManager;

/**
 * 订单归档定时任务
 * @作者 MoChunrun
 * @创建日期 2014-1-22 
 * @版本 V 1.0
 */
public class OrderHistoryTimer {
	private static Logger logger = Logger.getLogger(OrderHistoryTimer.class);
	@Resource
	private IOrderHisManager orderHisManager;
	
	public void startOrderHistoryThread(){
		if(!CheckTimerServer.isMatchServer(this.getClass().getName(),"startOrderHistoryThread"))
  			return ;
		logger.info("============订单归档开始============");
		orderHisManager.syncAllOrderHistory();
		logger.info("============订单归档完成============");
	}
	
	
}
