package com.ztesoft.net.mall.core.action.order;

import com.ztesoft.net.mall.core.model.Order;


public  interface  IOrderDirector {
	
	
	
	/**
	 * 流程处理
	 * @param orderRequst
	 * @return
	 */
	public  OrderResult perform(OrderRequst orderRequst);
	
	/**
	 * 绘制操作按钮
	 * @param orderRequst
	 * @return
	 */
	public  OrderResult display(OrderRequst orderRequst,Order order);
	

	public OrderBuilder getOrderBuilder();
}
