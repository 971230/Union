package com.ztesoft.net.mall.core.action.order.service.contract;

import com.ztesoft.net.mall.core.action.order.finish.CommonFinishHander;

public  class ContractFinishHander  extends CommonFinishHander {

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
