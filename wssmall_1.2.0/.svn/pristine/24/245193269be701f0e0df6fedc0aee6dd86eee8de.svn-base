package rule.impl;

import rule.AbstractRuleHander;
import rule.params.order.req.OrderPayRuleReq;
import rule.params.order.resp.OrderPayRuleResp;

/**
 * 订单支付规则类
 * @作者 MoChunrun
 * @创建日期 2014-6-13 
 * @版本 V 1.0
 */
public abstract class AbsOrderPayRule extends AbstractRuleHander{

	public static final String SERVICE_CODE = "orderpay";
	public static final String SERVICE_TYPE = "order_bussiness";
	
	public abstract OrderPayRuleResp execute(OrderPayRuleReq req);
	
	public OrderPayRuleResp afOrderPayPerform(OrderPayRuleReq req){
		return this.execute(req);
	}
	
}
