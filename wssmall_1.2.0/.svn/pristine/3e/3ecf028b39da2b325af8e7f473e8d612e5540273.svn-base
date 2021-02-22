package com.ztesoft.net.auto.rule.exe;

import java.util.Map;

import com.ztesoft.api.ApiBusiException;
import com.ztesoft.net.auto.rule.fact.AutoFact;
import com.ztesoft.net.auto.rule.vo.PlanRule;
import com.ztesoft.net.auto.rule.vo.RuleExeLog;

public interface IAutoRuleCaller {
	
	/**
	 * 执行目录下的方案
	 * @作者 MoChunrun
	 * @创建日期 2014-10-13 
	 * @param catalogue_id
	 */
	public void exeCataloguePlan(String catalogue_id,AutoFact fact,boolean deleteLogs,Map params)throws Exception;

	/**
	 * 执行方案规则
	 * @作者 MoChunrun
	 * @创建日期 2014-9-10 
	 * @param plan_id
	 * @param fact
	 * @param executeAll 强制重新执行所有规则，不管有没有执行过  些处有问题，所以不使用该参数
	 */
	public void exePlan(String plan_id,AutoFact fact,boolean reCurrRule,boolean executeAll,int auto_exe,boolean deleteLogs,RuleThreadStatus threadStatus,Map params)throws ApiBusiException;
	/**
	 * 执行方案规则
	 * @作者 MoChunrun
	 * @创建日期 2014-9-12 
	 * @param plan_id
	 * @param rule_id
	 * @param fact
	 * @param executeAll
	 * @throws Exception
	 */
	public void exePlanRule(String plan_id,String rule_id,AutoFact fact,boolean reCurrRule,boolean executeAll,boolean isExeAfterPeerRule,boolean isExeParentsPeerAfRules,boolean checkCurrRelyOnRule,boolean checkAllRelyOnRule,Map params)throws Exception;
	/**
	 * 根据规则执行记录（重新执行）执行已执行过的规则
	 * @作者 MoChunrun
	 * @创建日期 2014-9-12 
	 * @param log_id
	 * @param fact
	 * @param executeAll
	 * @throws Exception
	 */
	public void exePlanRule(String log_id,AutoFact fact,boolean reCurrRule,boolean executeAll,boolean isExeAfterPeerRule,boolean isExeParentsPeerAfRules,boolean checkCurrRelyOnRule,boolean checkAllRelyOnRule,Map params)throws Exception;
	/**
	 * 执行规则 并记录执行日志
	 * @作者 MoChunrun
	 * @创建日期 2014-9-10 
	 * @param rule_id
	 * @param fact
	 */
	public int exeRule(PlanRule rule,AutoFact fact,RuleThreadStatus threadStatus) throws Exception;
	public int exeRule(String plan_id,String rule_id,AutoFact fact,RuleThreadStatus threadStatus) throws Exception;
	
	public RuleExeLog getRuleExecuteLog(String plan_id,String rule_id,String obj_id);
	public boolean exeRuleTree(PlanRule rule,AutoFact fact,RuleExeLog log,boolean isRuleExe,RuleThreadStatus threadStatus) throws ApiBusiException;
	
}
