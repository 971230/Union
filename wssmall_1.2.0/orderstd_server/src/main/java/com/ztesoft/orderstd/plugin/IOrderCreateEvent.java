package com.ztesoft.orderstd.plugin;

import com.ztesoft.net.mall.core.model.Order;



/**
 * 订单创建事件
 * @author kingapex
 *
 */
public interface IOrderCreateEvent {
	
	
	public void onOrderCreate(Order order ,String sessionid);
	
	
}
