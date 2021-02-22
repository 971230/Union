/**
 * 
 */
package com.ztesoft.net.service;

/**
 * @author ZX
 * IRefreshPlanRuleManager.java
 * 刷新方案规则管理类
 * 2014-11-14
 */
public interface IRefreshPlanRuleManager {

	/**
	 * 根据方案ID刷新方案
	 * @param plan_id
	 * @return
	 */
	boolean refreshPlan(String plan_id);
	
	/**
	 * 根据规则ID刷新规则
	 * @param rule_id
	 * @return
	 */
	boolean refreshRule(String rule_id);
	
	/**
	 * 根据规则code查询规则id
	 * @author liu.quan
	 * @date 2017年4月19日
	 * @param rule_id
	 * @return
	 */
	String queryRuleId(String rule_id);
	
}
