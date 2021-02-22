package com.ztesoft.net.service;

import java.util.List;
import java.util.Map;

import zte.net.ecsord.params.busi.req.OrderBusiRequest;

import com.ztesoft.net.model.CBSSDealRequest;

public interface IOrdArchiveManager {
	
	/**
	 * 查询2前需要归档的订单
	 * @return
	 */
	public List<Map<String,String>> ordArchiveList();
	
	public List<Map<String,String>> ordDataArchiveList();

	public List<CBSSDealRequest> getGiftListByBssStatus(String order_id);
	
	public Boolean isAllCBSSDeal(String orderId);
	
	public List getOrderByCbss();
	
	public List<Map<String,String>> getRefreshOrders();
	
	public void insertArchiveFailLog(String order_id , String message);
	
	public List<Map<String,Object>> getCancelOrder(String goods_id);
	
}
