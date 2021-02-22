package com.ztesoft.net.mall.core.service;

import java.util.List;

import com.ztesoft.net.mall.core.action.order.orderc.OrderApply;
import com.ztesoft.net.mall.core.model.OrderApplyItem;

public interface IOrderApplycMamager {

	/**
	 * 按orderID查询退货单
	 * @作者 MoChunrun 
	 * @创建日期 2013-8-29 
	 * @param orderId
	 * @return
	 */
	public List<OrderApply> queryApplyByOrderId(String orderId,String serviceType,String applyState);
	
	public List<OrderApply> queryApplyByOrderId(String orderId);
	
	public OrderApply qryByAppliId(String applyId);
	
	public void insert(String tableName,OrderApply apply);
	
	public void updateApplyState(String applyId,String applyState);
	
	public int countAmountByServiceType(String serviceType,String orderId);
	
	public List<OrderApplyItem> queryApplyItems(String appli_id,
			String item_type);
	
}
