package com.ztesoft.net.mall.core.timer;

import javax.annotation.Resource;

import com.ztesoft.net.service.IUnimallOrderQueryManager;


public class UnimallOrderTimer {
	@SuppressWarnings("static-access")
	@Resource
	private IUnimallOrderQueryManager unimallOrderQueryManager;
	public void run(){
		
//		if (!CheckTimerServer.isMatchServer(this.getClass().getName(), "run")) {
//  			return;
//		}
//		
//		try {
//			unimallOrderQueryManager.orderCacheSet();
//			
//		} catch(Exception e) {
//			
//			throw new RuntimeException(e.getMessage());
//		}
	}
}
