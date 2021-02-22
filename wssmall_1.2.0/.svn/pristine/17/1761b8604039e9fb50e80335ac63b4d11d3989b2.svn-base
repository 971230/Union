package rule;

import com.ztesoft.api.ApiBusiException;
import rule.params.accept.req.AcceptRuleReq;
import rule.params.accept.resp.AcceptRuleResp;

public interface IAcceptRule {
	
	//验证受理数据
	public AcceptRuleResp validateAcceptInst(AcceptRuleReq acceptRuleReq);
	
	//保存完成后调用的规则
	public AcceptRuleResp afSaveAcceptInst(AcceptRuleReq acceptRuleReq) throws ApiBusiException;
	
}
