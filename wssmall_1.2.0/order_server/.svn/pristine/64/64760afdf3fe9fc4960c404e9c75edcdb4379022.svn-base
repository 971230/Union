package com.ztesoft.net.mall.core.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ztesoft.net.framework.database.ObjectNotFoundException;
import com.ztesoft.net.mall.core.model.Delivery;
import com.ztesoft.net.mall.core.model.DeliveryItem;
import com.ztesoft.net.mall.core.model.GoodsActionRule;
import com.ztesoft.net.mall.core.model.Order;
import com.ztesoft.net.mall.core.model.OrderChange;
import com.ztesoft.net.mall.core.model.OrderItem;
import com.ztesoft.net.mall.core.model.OrderOuter;
import com.ztesoft.net.mall.core.model.OrderRel;
import com.ztesoft.net.mall.core.model.PaymentLog;

/**
 * 订单流程管理<br>
 * 负责订单后台管理中的：支付、退款、发货、退货、完成、作废操作<br>
 * 总体关系为：
 * <li>
 * 未支付不可以退款，支付后才能退款<br>
 * 未支付可以支付，支付完成后可以再支付
 * </li>
 * 
 * <li>
 * 未发货不可以退货，发货后可以退货<br>
 * 未发货可以发货，发货完成后不可以再发货
 * </li>
 * 
 *<li>
 * 订单标记为完成状态后不可以进行其它操作<br>
 * 订单标记为作废状态后不可以进行其它操作
 * </li>
 * 
 * @author kingapex
 * 2010-4-8上午09:07:00
 * @see com.ztesoft.net.net.test.shop.order.OrderFlowTest
 */
public interface IOrderNFlowManager {
	
	/**
	 * 为某订单付款<br/>
	 * 对订单状态有如下影响：<br>
	 * 		<li>如果全额付款订单状态为<b>已付款</b>，付款状态为已付款</li>
	 * 		<li>如果部分付款订单状态为<b>已付款</b>，付款状态为部分已付款</li>
	 * 如果订单为非匿名购买且支付方式(paymentLog.pay_method)为“预存款支付”则影响到会员预存款：<br>
	 * 订单预存款为当前预存款<b>减</b>去支付费用
	 * @param paymentLog 付款日志对象<br/>
	 * @throws IllegalArgumentException 下列情形之一抛出此异常:
	 * <li>paymentLog为null</li> 
	 * <li>order_id(订单id)为null</li> 
	 * <li>money(付款金额)为null</li> 
	 * @throws ObjectNotFoundException 如果要支付的订单不存在
	 * @throws IllegalStateException 如果订单支付状态为已支付
	 * @throws RuntimeException  当使用预存款支付，且用户预存款不够支付金额时
	 * @see com.ztesoft.net.net.mall.service.support.OrderStatus
	 */
	public void pay(PaymentLog paymentLog,boolean isOnline,Object...args);
	public void updateOrderItemByOrderId(OrderItem orderItem);
	public void paySucc(String transaction_id);
	public void paySucc(String transaction_id,String type_code);
	public void payExcetion(String transaction_id,Integer payAmount,String type_code);
	
	public PaymentLog getPaymentLogByTransaction_id(String transaction_id);
	
	/**
	 * 订单受理成功直接发货
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void accept_ship_auto(String orderId);
	
	/**
	 * 为某订单退款<br>
	 * 对订单状态有如下影响：<br>
	 * 		<li>如果全额退款订单状态为<b>已退款</b>，付款状态为<b>已退款</b></li>
	 * 		<li>如果部分退款订单状态为<b>已退款</b>，付款状态为<b>部分退付款</b></li>
	 * 如果订单为非匿名购买且支付方式(paymentLog.pay_method)为“预存款支付”则影响到会员预存款：<br>
	 * 订单预存款为当前预存款<b>加</b>支付费用
	 * @param paymentLog 退款日志对象
	 * @throws IllegalArgumentException 下列情形之一抛出此异常:
	 * <li>paymentLog为null</li> 
	 *  <li>order_id(订单id)为null</li> 
	 * <li>money(退款金额)为null</li>  
	 * @throws ObjectNotFoundException 如果要退款的订单不存在
	 * @throws IllegalStateException 如果订单支付状态为已退款
	 * @throws RuntimeException 当退款金额超过订单支付金额时
	 * @see com.ztesoft.net.net.mall.service.support.OrderStatus
	 */
	public void refund(PaymentLog paymentLog);
	public void refund(PaymentLog paymentLog,String type_code);
	
	public void saveChange(OrderChange orderChange);
	
	public OrderChange getChangeByFieldName(String order_id,String order_item_id,String table_name,String field_name);
	
	/**
	 * 发货
	 * @param delivery 货运单对象</br>
	 * 如果不指定物流费用和保价费用，则其默认值为0
	 * @param itemList 本次发货的明细
	 * @throws ObjectNotFoundException 如果要发货的订单不存在
	 * @throws IllegalStateException 如果订单发货状态为已发货
	 * @throws IllegalArgumentException 下列情形之一抛出此异常:<br>
	 * <li>delivery为null</li> 
	 *  <li>delivery 对象的 order_id(订单id)为null</li> 
	 * <li>itemList为null 或为空</li> 
	 * <li>发货明细列表中的DeliveryItem对象下列属情有一个为空则抛出异常
	 *  goods_id, product_id、num
	 *  </li>
	 * @see com.ztesoft.net.net.mall.service.support.OrderStatus
	 */
	public void shipping(Delivery delivery ,List<DeliveryItem> itemList,List<DeliveryItem> giftItemList,String ship_action);
	
	
	
	/**
	 * 申请退换货
	 * @param orderid
	 * @param state 0为退货 1为换货 2为返修
	 * @param specids
	 */
	public void applyReturns(String orderId,int state,Integer[] specids);
	
	/**
	 * 拒绝退货或换货
	 */
	public void refuseRorC(String orderId);
	
	
	/**
	 * 退货
	 * @param delivery 货运单对象</br>type属性要指定为1(发货)
	 * @param itemList 本次发货的明细
	 * @throws ObjectNotFoundException 如果要发货的订单不存在
	 * @throws IllegalStateException 如果订单支付状态为已退货
	 * @throws IllegalArgumentException 下列情形之一抛出此异常:
	 * <li>delivery为null</li> 
	 *  <li>delivery 对象的 order_id(订单id)为null</li> 
	 * <li>itemList为null 或为空</li> 
	 * <li>发货明细列表中的DeliveryItem对象下列属情有一个为空则抛出异常
	 *  goods_id, product_id、num
	 *  </li>
	 *  @see  com.ztesoft.net.net.mall.service.support.OrderStatus
	 */
	public void returned(Delivery delivery,List<DeliveryItem> itemList,List<DeliveryItem> gifitemList);
	
	
	
	/**
	 * 换货
	 * @param delivery
	 * @param itemList
	 * @param gifitemList
	 */
	public void change(Delivery delivery,List<DeliveryItem> itemList,List<DeliveryItem> gifitemList);	
	
	
	
	/**
	 * 完成订单:
	 * 标记一个订单为完成状态
	 * @param  orderId 订单id
	 * @throws IllegalArgumentException 当orderId为null时
	 */
	public void complete(String orderId);
	
	
	//撤单
	public void withdraw(String orderId);
	
	//取消订单
	public void cancel_order(String orderId);
	
	/**
	 * 自动发货
	 * @param orderId
	 */
	public void ship_auto(String orderId);
	
	
	/**
	 * 确认收货
	 * @param orderId
	 */
	public void confirm_ship(String orderId);
	
	
	
	
	/**
	 * 订单支付，修改状态
	 * @param orderId
	 */
	public void pay_auto(String orderId);
	
	/**
	 * 采集单审核通过
	 * @param orderId
	 */
	public void audit_through(String orderId,String message);
	
	/**
	 * 采集单审核不通过
	 * @param orderId
	 */
	public void audit_not_through(String orderId,String message);
	
	
	public void accept_through(String orderId);
	public void accept_not_through(String orderId);
	public void accept(String orderId);
	
	public void acceptFail(String orderId,String message);
	public void accept_ing(String orderId);
	
	/**
	 * 作废订单 
	 * 标记一个订单为作废状态
	 * @param  orderId 订单id
	 * @throws IllegalArgumentException 当orderId为null时
	 */
	public void cancel(String orderId);
	
	
	/**
	 * 读取某订单未发货的货物(商品)列表
	 * @param orderId 订单id
	 * @return 此订单未发货的货物(商品)列表
	 */
	public List<OrderItem> listNotShipGoodsItem(String orderId);
	
	
	//获取未受理的订单
	public List<OrderItem> listNotAcceptGoodsItem(String orderId);
	
	/**
	 * 读取此订单未发货的赠品列表
	 * @param orderId 订单id
	 * @return 此订单未发货的赠品列表
	 */
	public List<Map> listNotShipGiftItem(String orderId);
	
	
	/**
	 * 读取某订单发货的商品明细列表
	 * @param orderId 订单id
	 * @return 此订单发货商品货物的列表
	 */
	public List<OrderItem> listShipGoodsItem(String orderId);	
	
	/**
	 * 读取某订单发货的赠品明细列表
	 * @param orderId 订单id
	 * @return 此订单发货赠品货物的列表
	 */
	public List<Map> listShipGiftItem(String orderId);	
	
	public void updateOrderItem(OrderItem orderItem);
	
	public void updateOrderFNR(Order order);
	
	
	
	//获取关联订单
	@Transactional(propagation = Propagation.REQUIRED)
	public String getZOrderId(String order_id,String rel_type);
	
	//更新外系统订单
	@Transactional(propagation = Propagation.REQUIRED)
	public void updateOrderOuter(OrderOuter orderOuter);
	public void updateOrderRel(OrderRel orderRel);
	
	//插入订单关系【退费如】
	public void saveOrderRel(OrderRel orderRel);
	
	public PaymentLog queryPaymentLogByOrderId(String order_id,String pay_type);
	
	
	public String getAOrderId(String order_id,String rel_type);
	//更新订单信息
	@Transactional(propagation = Propagation.REQUIRED)
	public void updateOrder(Order order);

	
	
	//获取订单id
	public String getOrderOuterOrderIdByOldOrderId(String order_id);
	
	public OrderItem getOrderItemByOrderId(String orderId);
	
	/**
	 * 查询物流
	 * @作者 MoChunrun 
	 * @创建日期 2013-8-13 
	 * @param orderID
	 * @return
	 */
	public Delivery qryDeliverByOrderID(String orderID);
	
	public List<OrderItem> listOrderItemActionCode(String order_id,String buy_type);
	public List<GoodsActionRule> listGoodsActionRule(String order_id);
	public List<GoodsActionRule> listGoodsActionRule(String order_id,String service_code);
	public List<GoodsActionRule> listGoodsActionRuleByGoodsId(String goods_id,String service_codde);
	public List<GoodsActionRule> listGoodsActionRuleByGoodsIds(String goods_ids,String service_codde);
	public PaymentLog paySucc(PaymentLog paymentLog);
	public int processGoodsShipItem(String orderId,String delivery_id, List<DeliveryItem> itemList,String ship_action,Map params,Order ...order);
	public int processGiftShipItem(String orderId,String delivery_id, List<DeliveryItem> itemList);
}
