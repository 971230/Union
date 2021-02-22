package services;

import params.order.req.OrderSyReq;
import params.order.resp.OrderSyResp;



/**
 * 订单同步
 * 
 * @作者 wu.i
 * @创建日期 2013-9-27
 * @版本 V 1.0
 */
public interface OrderSyInf {

	public OrderSyResp syNOrder(OrderSyReq syReq);
	

}
