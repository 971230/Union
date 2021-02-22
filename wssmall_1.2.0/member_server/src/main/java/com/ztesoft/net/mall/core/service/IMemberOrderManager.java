package com.ztesoft.net.mall.core.service;

import java.util.List;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.model.Delivery;
import com.ztesoft.net.mall.core.model.Order;

/**
 * 用户中心-我的订单
 * @author lzf<br/>
 * 2010-3-15 上午10:21:45<br/>
 * version 1.0<br/>
 */
public interface IMemberOrderManager {
	
	/**
	 * 订单列表
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Page pageOrders(int pageNo, int pageSize);
	public Page pageOrders(int pageNo, int pageSize, String status);
	public Page pageOrdersOngoning(int pageNo, int pageSize, String status);	
	public Order getOrder(int order_id);
	
	public Delivery getOrderDelivery(String order_id);
	
	public List listOrderLog(String order_id);
	
	public int totalCount();
	
	public int orderCount(String status);
	
	/**
	 * 读取订单货物（商品）
	 * @param order_id
	 * @return
	 */
	public List listGoodsItems(String order_id);
	
	
	/**
	 * 读取订单赠品列表
	 * @param orderid
	 * @return
	 */
	public List listGiftItems(String orderid);
	
	/***
	 * 判断当前会员是否购买过此商品
	 * @param goodsid
	 * @return 如果当前用户未登陆，返回假
	 */
	public boolean isBuy(String goodsid);
	
}
