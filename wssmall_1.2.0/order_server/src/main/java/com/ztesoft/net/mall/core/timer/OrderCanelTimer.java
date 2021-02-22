package com.ztesoft.net.mall.core.timer;

import java.util.List;

import com.ztesoft.net.mall.core.model.Order;
import com.ztesoft.net.mall.core.service.IOrderManager;

public class OrderCanelTimer {
	
	private IOrderManager orderManager;

	public void canel(){
		if(!CheckTimerServer.isMatchServer(this.getClass().getName(),"canel"))
  			return ;
		List<Order> list = orderManager.qryTimeOutOrders(5);
		if(list!=null && list.size()>0){
			for(Order o:list){
				orderManager.cancel(o.getOrder_id());
				orderManager.log(o.getOrder_id(), "订时任务取消过期订单", "-999", "订时任务");
			}
		}
	}

	public IOrderManager getOrderManager() {
		return orderManager;
	}

	public void setOrderManager(IOrderManager orderManager) {
		this.orderManager = orderManager;
	}
	
}
