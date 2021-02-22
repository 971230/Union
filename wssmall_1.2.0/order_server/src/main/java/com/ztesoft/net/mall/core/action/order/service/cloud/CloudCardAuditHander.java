package com.ztesoft.net.mall.core.action.order.service.cloud;

import com.ztesoft.net.consts.OrderStatus;
import com.ztesoft.net.mall.core.action.order.audit.CommonAuditHander;
import com.ztesoft.net.mall.core.model.GoodsAudit;

/**
 * 云卡审核
 * @author wui
 *
 */
public  class CloudCardAuditHander extends CommonAuditHander {

	
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
			if(OrderStatus.AUDIT_STATE_NO.equals(goodsAudit.getAudit_state())){
				setCanNext(false);//结束流程
				orderNFlowManager.audit_not_through(orderId,goodsAudit.getAudit_desc());
				
				cloudManager.resetCloudByOrderId(orderId);
			}else{ 
				orderNFlowManager.audit_through(orderId,goodsAudit.getAudit_desc());
				super.execute();
			}
			if(isFirstPartnerOrder()) //一级分销商需要现在支付云卡
				setCanNext(false);
			
		}
	}
}
