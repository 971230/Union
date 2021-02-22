package com.ztesoft.net.mall.core.service;

import params.order.resp.OrderWorkItemQueryResp;


/**
 * 订单工作项处理类
 *
 * @author zhangJun
 */

public interface IOrderWorkItemManager  {
	
	/**
	 * 根据order_id 查出当前未处理的工作项
	 */
	public OrderWorkItemQueryResp QueryOrderWorkItemByOrderId(String order_id);
	
}
