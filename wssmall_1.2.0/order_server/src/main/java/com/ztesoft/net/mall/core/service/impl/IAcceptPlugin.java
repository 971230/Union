package com.ztesoft.net.mall.core.service.impl;

import java.util.List;

import com.ztesoft.net.mall.core.action.order.FlowEntry;
import com.ztesoft.net.mall.core.action.order.OrderRequst;
import com.ztesoft.net.mall.core.action.order.OrderResult;

/**
 * 订单业务流程
 * 
 * @author wui
 */
public interface IAcceptPlugin {
	
	public OrderResult perform(OrderRequst orderRequst, List<FlowEntry> flowEntrys);
	
	
}