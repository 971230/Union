package services;

import params.order.req.ActionRuleReq;
import params.order.resp.ActionRuleResp;
import zte.params.order.resp.OrderAddResp;

public interface OrderActionRuleInf {

	public ActionRuleResp confirmOrder(OrderAddResp orderAddResp,ActionRuleReq req) throws Exception;
	
}
