package com.ztesoft.net.mall.core.service;

import java.util.List;

import com.ztesoft.net.mall.core.model.OrderComments;
import com.ztesoft.net.mall.core.model.OrderMessage;
import com.ztesoft.net.mall.core.model.OrderUncomments;

/**
 * 订单日志管理类
 * @作者 MoChunrun
 * @创建日期 2013-10-17 
 * @版本 V 1.0
 */
public interface IOrderCommentManager {

	/**
	 * 查询订单备注
	 * @作者 MoChunrun
	 * @创建日期 2013-10-17 
	 * @param order_id
	 * @return
	 */
	public List<OrderComments> qryOrderComments(String order_id);
	
	/**
	 * 增加订单备注
	 * @作者 MoChunrun
	 * @创建日期 2013-10-17 
	 * @param comment
	 */
	public void add(OrderComments comment);
	
	/**
	 * 查询订单异常信息
	 * @作者 MoChunrun
	 * @创建日期 2013-10-17 
	 * @param order_id
	 * @return
	 */
	public List listUnComments(String order_id);
	
	/**
	 * 添加订单异常信息
	 * @作者 MoChunrun
	 * @创建日期 2013-10-17 
	 * @param orderUnComments
	 */
	public void addUnComments(OrderUncomments orderUnComments);
	
	/**
	 * 订单附言查询
	 * @作者 MoChunrun
	 * @创建日期 2013-10-18 
	 * @param order_id
	 * @return
	 */
	public List<OrderMessage> listOrderMessage(String order_id);
	
	/**
	 * 添加订单附言
	 * @作者 MoChunrun
	 * @创建日期 2013-10-18 
	 * @param message
	 */
	public void addOrderMessage(OrderMessage message);
	
	/**
	 * 修改订单确认状态
	 * @作者 MoChunrun
	 * @创建日期 2013-10-18 
	 * @param confirm_status
	 * @param order_id
	 */
	public void updateConfirmStatus(int confirm_status,String order_id);
	
	public void updateConfirmStatus(int status,int confirm_status,String order_id);
	
}
