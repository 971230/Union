package com.ztesoft.net.mall.core.action.order.service.recharge;

import com.ztesoft.net.mall.core.action.order.payment.CommonPaymentHander;
import com.ztesoft.net.mall.core.model.Order;

public  class RechargeCardPaymentHander extends CommonPaymentHander  {

	public void payment() {
	}

	@Override
	public void display() {

	}

	@Override
	public void execute() { // 云卡支付处理

		if (isTaobaoAgent()) {
			Order order = getOrder();//自动支付,淘宝平台已经支付
			orderNFlowManager.pay_auto(order.getOrder_id());
		} else {
			Order order = getOrder();//自动支付
			orderNFlowManager.pay_auto(order.getOrder_id());
		}

	}

}
