package rule.impl;

import params.order.req.DeliveryReq;
import params.order.resp.DeliveryResp;
import rule.AbstractRuleHander;
import rule.IDelveryRule;

/**
 * 支付规则处理器
 * @author wu.i
 *
 */
public  abstract class  DelveryBaseRule extends AbstractRuleHander implements IDelveryRule {
	
	//受理数据保存公共入口
	public DeliveryResp afSaveDeliverInstPerform(DeliveryReq delveryRuleReq) {
		
		return this.computeDelivery(delveryRuleReq);
		
	}
	
}
