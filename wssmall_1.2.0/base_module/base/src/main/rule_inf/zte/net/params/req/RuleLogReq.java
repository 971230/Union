package zte.net.params.req;

import java.util.List;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.auto.rule.vo.RuleExeLog;

import params.ZteRequest;

public class RuleLogReq extends ZteRequest{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2602309512876552283L;
	
	private RuleExeLog ruleLog;
	
	private List<String> orderIds;
	
	private List<String> ruleIds;

	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getApiMethodName() {
		return "zte.ruleService.qryRuleLog";
	}

	public RuleExeLog getRuleLog() {
		return ruleLog;
	}

	public void setRuleLog(RuleExeLog ruleLog) {
		this.ruleLog = ruleLog;
	}

	public List<String> getOrderIds() {
		return orderIds;
	}

	public void setOrderIds(List<String> orderIds) {
		this.orderIds = orderIds;
	}

	public List<String> getRuleIds() {
		return ruleIds;
	}

	public void setRuleIds(List<String> ruleIds) {
		this.ruleIds = ruleIds;
	}

}
