package rule.impl;

import rule.AbstractRuleHander;
import rule.params.order.req.OrderFinishRuleReq;
import rule.params.order.resp.OrderFinishRuleResp;

public abstract class AbsOrderFinishRule extends AbstractRuleHander{

	public static final String SERVICE_CODE = "orderfinish";
	public static final String SERVICE_TYPE = "order_bussiness";
	
	public abstract OrderFinishRuleResp execute(OrderFinishRuleReq req);
	
	public OrderFinishRuleResp afOrderFinishPerform(OrderFinishRuleReq req){
		return this.execute(req);
	}
	
}
