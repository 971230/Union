package com.ztesoft.rule.core.ext;

import java.util.ArrayList;
import java.util.List;

import com.ztesoft.rule.core.bo.IRuleDBAccess;
import com.ztesoft.rule.core.module.filter.IFactFilter;
import com.ztesoft.rule.core.util.LocalBeanFactory;


/**
 * 方案涉及规则所需的cache类型数据
 * @author easonwu 
 * @creation Dec 13, 2013
 * 
 */
public class DefFactFilterLoader implements IFactFilterLoader{

	private IRuleDBAccess ruleDBAccess ;
	
	public IRuleDBAccess getRuleDBAccess() {
		return ruleDBAccess;
	}

	public void setRuleDBAccess(IRuleDBAccess ruleDBAccess) {
		this.ruleDBAccess = ruleDBAccess;
	}

	@Override
	public List<IFactFilter> loadFactFilters(String planId) {
		List<String> cfgs =  ruleDBAccess.loadFactFilterConfigs(planId) ;
		List<IFactFilter> filters = new ArrayList<IFactFilter>() ;
		for(String cfg : cfgs){
			IFactFilter filter = LocalBeanFactory.createFactFilter(cfg ) ;
			filters.add(filter) ;
		}
		return filters ;
	}

}
