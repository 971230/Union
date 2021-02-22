package rule.impl;

import params.order.req.PaymentLogReq;
import params.order.resp.PaymentLogResp;
import rule.AbstractRuleHander;
import rule.IPayRule;

/**
 * 支付规则处理器
 * @author wu.i
 *
 */
public  abstract class  PayBaseRule extends AbstractRuleHander implements IPayRule {

	
	
	//受理数据保存公共入口
	public PaymentLogResp afSavePaymentInstPerform(PaymentLogReq payRuleReq) {
		PaymentLogResp payRuleResp = this.computePayStatus(payRuleReq);
		//TODO 处理
		//根据受理成功状态更新主订单的标识
		return payRuleResp;
		
	}
	
}