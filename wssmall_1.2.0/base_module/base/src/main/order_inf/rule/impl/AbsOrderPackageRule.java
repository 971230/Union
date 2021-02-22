package rule.impl;

import rule.AbstractRuleHander;
import rule.params.order.req.OrderPackageRuleReq;
import rule.params.order.resp.OrderPackageRuleResp;

public abstract class AbsOrderPackageRule extends AbstractRuleHander{

	public static final String SERVICE_CODE = "orderpackage";
	public static final String SERVICE_TYPE = "order_bussiness";
	
	public abstract OrderPackageRuleResp execute(OrderPackageRuleReq req);
	
	public OrderPackageRuleResp afOrderPackagePerform(OrderPackageRuleReq req){
		return this.execute(req);
	}
	
}
