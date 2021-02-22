package com.ztesoft.net.mall.core.action.order.service.cloud;

import com.ztesoft.net.consts.OrderStatus;
import com.ztesoft.net.mall.core.action.order.dilvery.CommonDilveryHander;


public  class CloudCardDilveryHander extends CommonDilveryHander{

@Override
public void dilvery() {
		
	}
	@Override
	public void display() {
		
	}

	/**
	 * 物流处理
	 */
	@Override
	public void execute() {
		if(getOrderRequst().getShip_action().equals(OrderStatus.SHIP_ACTION_SHIP))
			super.ship();
		else if(getOrderRequst().getShip_action().equals(OrderStatus.SHIP_ACTION_CONFIRM_SHIP))
			confirm_ship();
		setCanNext(false);
	}
	
	//确认收货
	private void confirm_ship(){
		orderNFlowManager.confirm_ship(getOrder().getOrder_id());
		
		//发货后更新卡状态可展示
		cloudManager.updateCloudByOrderId(getOrder().getOrder_id());
	}
	
	

	@Override
	public boolean isCanExecute() {
		return this.getOrderRequst().isCan_execute();
	}

}
