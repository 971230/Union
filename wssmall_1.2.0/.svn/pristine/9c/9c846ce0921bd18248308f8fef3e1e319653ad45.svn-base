package rule.impl;

import rule.AbstractRuleHander;
import rule.params.order.req.OrderShippingRuleReq;
import rule.params.order.resp.OrderShippingRuleResp;

/**
 * 订单发货规则类
 * @作者 MoChunrun
 * @创建日期 2014-6-13 
 * @版本 V 1.0
 */
public abstract class AbsOrderShippingRule extends AbstractRuleHander{

	public static final String SERVICE_CODE = "ordershipping";
	public static final String SERVICE_TYPE = "order_bussiness";
	
	public abstract OrderShippingRuleResp execute(OrderShippingRuleReq req);
	
	public OrderShippingRuleResp afOrderShippingPerform(OrderShippingRuleReq req){
		return this.execute(req);
	}
	
}
