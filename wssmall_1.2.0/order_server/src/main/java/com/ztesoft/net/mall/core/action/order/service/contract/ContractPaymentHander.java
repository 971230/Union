package com.ztesoft.net.mall.core.action.order.service.contract;

import com.ztesoft.net.mall.core.action.order.payment.CommonPaymentHander;
import com.ztesoft.net.mall.core.model.Order;

public  class ContractPaymentHander extends CommonPaymentHander  {

	public void payment() {
	}
	
	@Override
	public void display() {
		
		
	}
	
	
	@Override
	public void execute() { //云卡支付处理
		if(isTaobaoAgent())
		{
			//TODO 外系统直接到已收费环节
			super.syOrderAutoPay();
			setCanNext(false); //合约机受理申请
		}else{
			Order order = getOrder();
			orderNFlowManager.pay_auto(order.getOrder_id()); //自动支付
			setCanNext(false);
		}
	}
}
