package com.ztesoft.net.mall.core.service;

import java.util.List;

import com.ztesoft.net.mall.core.model.OrderAccept;

public interface IOrderAcceptManager {

	/**
	 * 查询受理记录
	 * @param accept_id
	 * @return
	 */
	public OrderAccept get(String accept_id);
	
	/**
	 * 按订单ID查询受理表
	 * @param order_id
	 * @return
	 */
	public OrderAccept qryByOrderId(String order_id);
	
	/**
	 * 新增受理主表
	 * @param accept
	 */
	public void add(OrderAccept accept);
	
	/**
	 * user_id=-1时查询所有
	 * @作者 MoChunrun 
	 * @创建日期 2013-10-10 
	 * @param user_id
	 * @return
	 */
	public List<OrderAccept> listOrderAccept(String user_id,int accept_status);
	
	/**
	 * 修改状态
	 * @param accept_id
	 * @param accept_status
	 */
	public void updateAcceptStatus(String accept_id,int accept_status);
	/**
	 * 按订单ID修改状态
	 * @param order_id
	 * @param accept_status
	 */
	public void updateAcceptStatusByOrderId(String order_id,int accept_status);
	
	/**
	 * 更新订单主表accept_status
	 * @作者 MoChunrun
	 * @创建日期 2013-10-14 
	 * @param order_id
	 * @param accept_status
	 */
	public void updateOrderAcceeptStatusByOrderId(String order_id,int accept_status);
	
}
