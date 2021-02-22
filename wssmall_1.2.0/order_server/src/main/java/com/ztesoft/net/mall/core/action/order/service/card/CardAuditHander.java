package com.ztesoft.net.mall.core.action.order.service.card;

import com.ztesoft.net.consts.OrderStatus;
import com.ztesoft.net.mall.core.action.order.audit.CommonAuditHander;
import com.ztesoft.net.mall.core.model.GoodsAudit;
import com.ztesoft.net.mall.core.model.OrderAuditRequest;

public  class CardAuditHander  extends CommonAuditHander{

	@Override
	public void audit() {
			
		}

	@Override
	public void display() {
		super.display();
	}

	@Override
	public void execute() {
		
		if (isTaobaoAgent()) {
			// orderNFlowManager.audit_through( getOrderRequst().getOrderId(),"淘宝充值卡支付订单审核通过");
		} else {
//			logger.info("222222222222222222222222222222222222CardAuditHander");
			GoodsAudit goodsAudit = getOrderRequst().getGoodsAudit();
			String orderId = getOrderRequst().getOrderId();
			if (goodsAudit ==null || OrderStatus.AUDIT_STATE_NO.equals(goodsAudit.getAudit_state())) {
				setCanNext(false);// 结束流程
				orderNFlowManager.audit_not_through(orderId, goodsAudit.getAudit_desc());	
				if(OrderStatus.ORDER_TYPE_2.equals(getOrder().getOrder_type())) //退费订单,审核不通过直接更新状态
				{
					OrderAuditRequest orderAudit = new OrderAuditRequest();
					orderAudit.setOrder_id(getOrder().getOrder_id());
					orderAudit.setState(OrderStatus.ORDER_AUDIT_STATE_3);
					orderAuditManager.audit(orderAudit);
				}
				
			} else {
				orderNFlowManager.audit_through(orderId, goodsAudit.getAudit_desc());
				super.execute();
			}
			
			if(isFirstPartnerOrder()) //一级分销商需要现在支付充值卡
				setCanNext(false);
			
		}
		
	}

}
