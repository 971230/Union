package com.ztesoft.net.mall.core.action.order.service.recharge;

import com.ztesoft.net.mall.core.action.order.accept.CommonAcceptHander;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.model.Order;
import com.ztesoft.net.mall.core.model.RateInfRequest;

public  class RechargeCardAcceptHander extends CommonAcceptHander {

	@Override
	public void display() {
		
		
	}

	@Override
	public void execute() {
		Order order = getOrder();
		if (isTaobaoAgent()) {
			//调用计费接口给号码充值,充值成功设置
			RateInfRequest rateInfRequest = getOrderRequst().getRateInfRequest();
			//调用计费接口充值
			if(rateManager.billRecharge(rateInfRequest)){
				//更新订单编号
				orderNFlowManager.accept(order.getOrder_id());
				
			}else{
				getOrderResult().setCode(Consts.CODE_FAIL);
				getOrderResult().setMessage("号码计费充值失败");
				orderNFlowManager.acceptFail(order.getOrder_id(), getOrderResult().getMessage());
				setCanNext(false);
			}
			
		}else{
			rateManager.transfer_card(getOrderRequst(),getOrderResult()); //生成充值卡接口链接地址
			
			if(Consts.CODE_SUCC.equals(getOrderResult().getCode())){
				orderNFlowManager.accept(order.getOrder_id());
			}else if(Consts.CODE_FAIL.equals(getOrderResult().getCode())){
				orderNFlowManager.acceptFail(order.getOrder_id(), getOrderResult().getMessage());
				setCanNext(false);
				return;
			}
				
		}
	}

	
	@Override
	public void accept() {
		
	}

}
