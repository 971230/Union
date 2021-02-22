package com.ztesoft.net.mall.core.service;

import java.util.List;
import java.util.Map;

import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.model.Order;
import com.ztesoft.net.mall.core.model.OrderQueryParam;

public interface IOrderHisManager {

	/**
	 * 获取数据库当前时间
	 * @作者 MoChunrun
	 * @创建日期 2014-1-21 
	 * @return
	 */
	public String getCurrentTime();
	
	/**
	 * 同步订单历史记录
	 * @作者 MoChunrun
	 * @创建日期 2014-1-21 
	 * @param endTime
	 */
	public void syncOrderHis(String endTime);
	
	/**
	 * 同步子订单历史表
	 * @作者 MoChunrun
	 * @创建日期 2014-1-21 
	 * @param endTime
	 */
	public void syncOrderItemsHis(String endTime);
	
	/**
	 * 同步订单属性历史表
	 * @作者 MoChunrun
	 * @创建日期 2014-1-21 
	 * @param endTime
	 */
	public void syncOrderAttrInstHis(String endTime);
	
	/**
	 * 同步物流历史表
	 * @作者 MoChunrun
	 * @创建日期 2014-1-21 
	 * @param endTime
	 */
	public void syncDeliveryHis(String endTime);
	
	/**
	 * 同步子物流历史表
	 * @作者 MoChunrun
	 * @创建日期 2014-1-21 
	 * @param endTime
	 */
	public void syncDeliveryItemHis(String endTime);
	
	/**
	 * 同步支付晶志
	 * @作者 MoChunrun
	 * @创建日期 2014-6-27 
	 * @param endTime
	 */
	public void syncPaymentLogs(String endTime);
	
	public void deletePaymentLogs(String endTime);
	
	/**
	 * 删除订单表数据
	 * @作者 MoChunrun
	 * @创建日期 2014-1-22 
	 * @param endTime
	 */
	public void deleteOrders(String endTime);
	
	/**
	 * 删除订单子表数据
	 * @作者 MoChunrun
	 * @创建日期 2014-1-22 
	 * @param endTime
	 */
	public void deleteOrderItems(String endTime);
	
	/**
	 * 删除物流数据
	 * @作者 MoChunrun
	 * @创建日期 2014-1-22 
	 * @param endTime
	 */
	public void deleteDelivery(String endTime);
	
	/**
	 * 删除物流子表
	 * @作者 MoChunrun
	 * @创建日期 2014-1-22 
	 * @param endTime
	 */
	public void deleteDeliveryItem(String endTime);
	
	/**
	 * 删除订单属性表
	 * @作者 MoChunrun
	 * @创建日期 2014-1-22 
	 * @param endTime
	 */
	public void deleteOrderAttrInst(String endTime);
	
	/**
	 * 历史订单归档
	 * @作者 MoChunrun
	 * @创建日期 2014-1-22
	 */
	public void syncAllOrderHistory();
	
	/**
	 * 查询历史订单
	 * @作者 MoChunrun
	 * @创建日期 2014-6-27 
	 * @param pageNO
	 * @param pageSize
	 * @param disabled
	 * @param ordParam
	 * @param order
	 * @return
	 */
	public Page queryHisOrder(int pageNO, int pageSize,int disabled, OrderQueryParam ordParam, String order);
	
	public Order getHisOrder(String order_id);
	
	public List<Map> listHisGoodsItems(String order_id);
	
	public List listDelivery(String orderId, Integer type);
	
	public List listPayLogs(String orderId, Integer type);
	
}
