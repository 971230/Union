package com.ztesoft.rule.core.exe;

import com.ztesoft.rule.core.module.cfg.ConfigData;

public interface IRuleExecutorBuilder {
	public void setRuleConfigs(IRuleConfigs ruleConfigs) ;
	
	public void makeRuleContext() ;
	public void makeRulePartners() ;
	public void makeRuleFacts() ;
	public void makeRuleFactDatas() ;
	public void makeRuleResultHandler() ;
	public void makePartnerExecutorService() ;
	
	public IRuleExecutor build() ;
	
	
	public void setConfigData(ConfigData configData);
}
