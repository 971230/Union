package rule.impl;

import rule.AbstractRuleHander;
import rule.IOrdUserRule;
import rule.params.userstaff.req.OrdUserRuleReq;
import rule.params.userstaff.resp.OrdUserRuleResp;

/**
 * 订单工号信息设置基础规则
 * @author wu.i
 *
 */
public  abstract class  OrdUserBaseRule extends AbstractRuleHander implements IOrdUserRule {

	
	//受理订单工号信息基础规则设置
	public OrdUserRuleResp setOrderUsersPerform(OrdUserRuleReq ordUserRuleReq) {
		OrdUserRuleResp orderUserRuleResp = new OrdUserRuleResp();
		return orderUserRuleResp;
	}
	
	
}
