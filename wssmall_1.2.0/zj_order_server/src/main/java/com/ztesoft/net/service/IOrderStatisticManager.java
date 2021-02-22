package com.ztesoft.net.service;

import java.util.Map;

public interface IOrderStatisticManager {

	/**
	 * 插入订单统计环节操作信息
	 * @param orderId
	 */
	public void insertOrderStatisticTacheItem(String orderId ,String create_time);
	
	/**
	 * 更新订单环节操作人、时间
	 * @param orderId  内部订单ID
	 * @param flowId	下一环节
	 * @param opId
	 */
	public void updateOrderTacheItems(String orderId,String flowId,String opId);
	
	/**
	 * 更新订单环节操作人、时间
	 * @param orderId  内部订单ID
	 * @param beforeFlowId	上一环节
	 * @param flowId	下一环节
	 * @param opId
	 */
	public void updateOrderTacheItems(String orderId,String beforeFlowId,String flowId,String opId);
	//查个人订单情况
	public Map<String,String> queryOrderStatisticsData(String user_id , int founder , String start_time , String end_time);
	public Map<String,String> queryDayOrders(String user_id , int founder , String start_time , String end_time);
	public void initMapNullToZero(Map<String,String> m);
	public Map<String,String> queryPieData(String start_time , String end_time);
	//查所胡订单情况
	public Map<String,String> queryOrderStatisticsDataAll(String user_id , int founder , String start_time , String end_time);
	
}
