package com.ztesoft.rule.core.exe;

import com.ztesoft.rule.core.exe.thread.IPartnerExecutorService;
import com.ztesoft.rule.core.module.cfg.ConfigData;



public interface IRuleExecutor {
	public IRuleContext getRuleContext() ;
	public IRulePartners getRulePartners() ;
	public IRuleConfigs getRuleConfigs() ;
	
	
	public IRuleFacts getRuleFacts() ;
	public IRuleFactDatas getRuleFactDatas() ;
	
	public void setRuleConfigs(IRuleConfigs ruleConfigs) ;

	public void setRuleFactDatas(IRuleFactDatas ruleFactDatas) ;
	public void setRuleFacts(IRuleFacts ruleFacts) ;
	
	public void setRuleContext(IRuleContext context) ;
	public void setRulePartners(IRulePartners rulePartners) ;

	public void setRuleResultHandler(IRuleResultHandler ruleResultHandler);
	
	public Object execute() ;
	
	public void setPartnerExecutorService(IPartnerExecutorService partnerExecutorService);
	public void coreProcess(ConfigData configData ,  int mold) ;
}
