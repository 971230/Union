package com.ztesoft.net.service;

import java.util.List;
import java.util.Map;

import com.ztesoft.net.model.BusiDealResult;



public interface IOrdArchiveTacheManager {
	/**
	 * 逻辑归档
	 * @param order_id
	 * @return
	 * @throws Exception
	 */
	public BusiDealResult ordLogisArchive(String order_id) throws Exception;
	
	/**
	 * 订单信息归档
	 * @param order_id
	 * @return
	 */
	public BusiDealResult ordArchive(String order_id) throws Exception;
	
	/**
	 * 退库存
	 * @param order_id
	 * @return
	 */
	public BusiDealResult removeRepertory(String order_id);
	
	/**
	 * 归档完成状态通知新商城
	 * @param order_id
	 * @return
	 */
	public BusiDealResult notifyFinishToOuterShop(String order_id);
	
	/**
	 * 该方法不从缓存中取订单数据（非特殊不要使用这种方法）
	 * @param order_id
	 * @return
	 */
	public Map getArchiveCondition(String order_id);
	
//	/**
//	 * 更新SP服务的状态(支持批量)
//	 * @param params
//	 */
//	public void updateOrderSpProductsState(List<Map<String,String>> batchArgs);
//	
//	/**
//	 * 更新SP服务的状态(单条)
//	 * @param map
//	 */
//	public void updateOrderSpProductState(Map<String, String> map);
	/**
	 * 积压订单恢复
	 * @param order_id
	 * @return
	 * @throws Exception
	 */
	public BusiDealResult ordRecover(String order_id) throws Exception;
	public void zbOrderDataArchive(String orderId);
}
