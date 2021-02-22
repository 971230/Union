package com.ztesoft.rule.core.module.fact;

import java.util.ArrayList;
import java.util.List;


/**
 * 规则结果列表：记录整个方案下，对某个Fact,规则列表执行结果集
 * @author easonwu 
 * @creation Dec 17, 2013
 * 
 */
public class RuleResults {
	
	//规则结果集
	private List<RuleResult> results = new ArrayList<RuleResult>() ;
	
	//方案编码
	private String planCode ;
	
	
	/**
	 * 添加一个规则执行结果
	 * @param aResult
	 */
	public void addResult(RuleResult aResult){
		results.add(aResult) ;
	}
	
	/**
	 * 添加一个规则结果
	 * @param ruleCode 规则编码
	 * @param aRuleResult 规则执行结果
	 */
	public void addResult(String ruleCode , Object aRuleResult){
		addResult(new RuleResult(ruleCode , aRuleResult )) ;
	}
	
	/**
	 * 添加一个规则结果
	 * @param ruleCode 规则编码
	 * @param aRuleResult 规则执行结果
	 */
	public void addResult(String ruleCode ,String rule_id, Object aRuleResult){
		addResult(new RuleResult(ruleCode,rule_id , aRuleResult )) ;
	}
	
	
	
	public RuleResults(){
		
	}

	public RuleResults(String planCode){
		this.planCode = planCode ;
	}

	
	
	public String getPlanCode() {
		return planCode;
	}



	public void setPlanCode(String planCode) {
		this.planCode = planCode;
	}



	public List<RuleResult> getResults() {
		return results;
	}

	public void setResults(List<RuleResult> results) {
		this.results = results;
	}
	
}
