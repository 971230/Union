package com.ztesoft.net.mall.core.action.order.finish;

import com.ztesoft.net.mall.core.action.order.AbstractHander;

public  class CommonFinishHander extends AbstractHander implements IFinishHander{

	@Override
	public void finish() {
	}


	@Override
	public void display() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void execute() {
		/*订单完成*/
		String orderId =getOrderRequst().getOrderId();
		this.orderNFlowManager.complete(orderId);
		//发送消息提醒
	}
	

	@Override
	public boolean isCanExecute() {
		return this.getOrderRequst().isCan_execute();
	}
	

}
