package com.ztesoft.net.mall.core.action.order.service.contract;

import com.ztesoft.net.consts.OrderStatus;
import com.ztesoft.net.mall.core.action.order.audit.CommonAuditHander;
import com.ztesoft.net.mall.core.model.GoodsAudit;

public  class ContractAuditHander  extends CommonAuditHander {


	
	@Override
	public void audit() {
		
	}

	
	@Override
	public void display() {
		
	}

	@Override
	public void execute() {
		if(isTaobaoAgent()){
			//TODO 外系统直接到已收费环节
			super.syOrderAutoAudit();
		}else{
			GoodsAudit goodsAudit = getOrderRequst().getGoodsAudit();
			String orderId = getOrderRequst().getOrderId();
			if(goodsAudit.getAudit_state().equals(OrderStatus.AUDIT_STATE_NO)){
				orderNFlowManager.audit_not_through(orderId,goodsAudit.getAudit_desc());
				setCanNext(false);//结束流程
			}else{ 
				orderNFlowManager.audit_through(orderId,goodsAudit.getAudit_desc());
				super.execute();
			}
		}
	}

}
