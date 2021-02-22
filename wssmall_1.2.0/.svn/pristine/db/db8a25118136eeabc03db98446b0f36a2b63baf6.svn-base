package rule.impl;

//import org.apache.log4j.Logger;

import rule.AbstractRuleHander;
import rule.IOrderOwnerUserRule;
import rule.params.userstaff.req.OrderOwnerUserReq;
import rule.params.userstaff.resp.OrderOwnerUserResp;

/**
 * 缺省订单归属用户
 * @author wu.i
 *
 */
public  abstract class  OrderOwnerUserBaseRule extends AbstractRuleHander implements IOrderOwnerUserRule {
   // protected Logger logger=Logger.getLogger(OrderOwnerUserBaseRule.class);

	
	//受理数据保存公共入口
	public OrderOwnerUserResp setOrderOwnerUserPerform(OrderOwnerUserReq orderOwnerUserReq) {
		@SuppressWarnings("unused")
		OrderOwnerUserResp orderOwnerUserResp = this.setOrderOwnerUser(orderOwnerUserReq);
		return orderOwnerUserResp;
		
	}
	
	
	
}
