package com.ztesoft.net.mall.core.action.order.service.recharge;

import com.ztesoft.net.mall.core.action.order.finish.CommonFinishHander;

public  class RechargeCardFinishHander  extends CommonFinishHander {

	@Override
	public void finish() {
	}

	@Override
	public void display() {
		
	}

	@Override
	public void execute() {
		super.execute();
	}

	@Override
	public boolean isCanExecute() {
		return this.getOrderRequst().isCan_execute();
	}

}
