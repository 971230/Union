package com.ztesoft.rule.core.ext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.ztesoft.rule.core.bo.IRuleDBAccess;


/**
 * 方案涉及规则所需的cache类型数据
 * @author easonwu 
 * @creation Dec 13, 2013
 * 
 */
public class DefFactDataConfigLoader {

	private IRuleDBAccess ruleDBAccess ;
	private ConcurrentHashMap<String,List<Map<String,String>>> FactDataConfigCache = 
		new ConcurrentHashMap<String,List<Map<String,String>>>() ;
	
	
	public IRuleDBAccess getRuleDBAccess() {
		return ruleDBAccess;
	}

	public void setRuleDBAccess(IRuleDBAccess ruleDBAccess) {
		this.ruleDBAccess = ruleDBAccess;
	}

	public List<Map<String,String>> loadFactDataConfigs(String planCode) {
		if(FactDataConfigCache.containsKey(planCode))
			return FactDataConfigCache.get(planCode) ;
		
		List<Map<String,String>> configs = ruleDBAccess.loadFactDataConfigs(planCode);
		if(configs == null )
			configs = new ArrayList<Map<String,String>>() ;
		FactDataConfigCache.put(planCode , configs) ;
		
		return configs ;
	}
	
	public void expireCache(String planCode){
		FactDataConfigCache.remove(planCode) ;
	}

	
}
