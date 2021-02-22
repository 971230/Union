package rule;

import rule.params.confirm.req.ConfirmRuleReq;
import rule.params.confirm.resp.ConfirmRuleResp;

public interface IConfirmRule {
	
	/**
	 * 订单确认状态获取，判断是否需要订单确认
	 * @param confirmRuleReq
	 * @return
	 */
	public ConfirmRuleResp computeConfirmStatus(ConfirmRuleReq confirmRuleReq);
	
	
}
