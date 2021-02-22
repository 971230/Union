package com.ztesoft.net.mall.core.action.order.service.card;

import com.ztesoft.net.consts.OrderStatus;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.action.order.dilvery.CommonDilveryHander;


public  class CardDilveryHander extends CommonDilveryHander{
	@Override
	public void display() {
		//流量卡不需要展示按钮
	}

	/**
	 * 物流处理
	 */
	@Override
	public void execute() {
	
		if(StringUtil.isEmpty(getOrderRequst().getShip_action()) || OrderStatus.SHIP_ACTION_SHIP.equals(getOrderRequst().getShip_action())){
			orderNFlowManager.ship_auto(getOrder().getOrder_id());
		}else if(OrderStatus.SHIP_ACTION_CONFIRM_SHIP.equals(getOrderRequst().getShip_action()))
		{	
			orderNFlowManager.confirm_ship(getOrder().getOrder_id());
			//发货后更新卡状态可展示
			cardServ.updateCardByOrderId(getOrder().getOrder_id());
			
		}
		
	}
	

	@Override
	public boolean isCanExecute() {
		return this.getOrderRequst().isCan_execute();
	}


}
