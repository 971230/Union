package com.ztesoft.net.mall.core.service;

import com.ztesoft.api.ApiBusiException;
import params.RuleParams;

/**

 */
public interface IRuleManager {
	
	public <T> T process(RuleParams ruleParams) throws ApiBusiException;
}
