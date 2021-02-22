package rule.impl;

import rule.AbstractRuleHander;
import rule.IConfirmRule;
import rule.params.confirm.req.ConfirmRuleReq;
import rule.params.confirm.resp.ConfirmRuleResp;

/**
 * 缺省受理规则处理器
 * @author wu.i
 *
 */
public  abstract class  ConfirmBaseRule extends AbstractRuleHander implements IConfirmRule {

	
	//受理数据保存公共入口
	public ConfirmRuleResp computeConfirmStatusPerform(ConfirmRuleReq confirmRuleReq) {
		
		@SuppressWarnings("unused")
		ConfirmRuleResp confirmRuleResp = this.computeConfirmStatus(confirmRuleReq);
		//TODO 处理
		//根据受理成功状态更新主订单的标识
		return confirmRuleResp;
		
	}
	
	
}
