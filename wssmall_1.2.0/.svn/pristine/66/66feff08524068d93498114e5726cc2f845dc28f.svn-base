package com.ztesoft.net.auto.rule.i;

import java.util.List;

import com.ztesoft.net.auto.rule.vo.Plan;
import com.ztesoft.net.auto.rule.vo.PlanRule;
import com.ztesoft.net.auto.rule.vo.RuleConsts;
import com.ztesoft.net.auto.rule.vo.RuleExeLog;
import com.ztesoft.net.mall.core.model.CoQueue;
import com.ztesoft.net.mall.core.model.RuleAction;
import com.ztesoft.net.mall.core.model.RuleCondCache;

/**
 * 订单自动化业务
 * @作者 MoChunrun
 * @创建日期 2014-9-10 
 * @版本 V 1.0
 */
public interface IAutoRule {

	/**
	 * 查询规则执行成功后调用业务组件需要的常量
	 * @作者 MoChunrun
	 * @创建日期 2014-9-10 
	 * @param rule_id
	 * @param action_id
	 * @return
	 */
	public List<RuleConsts> queryRuleConsts(String rule_id,String action_id);
	
	/**
	 * 与规则日志
	 * @作者 MoChunrun
	 * @创建日期 2014-9-10 
	 * @param log
	 */
	public void log(RuleExeLog log);
	
	public String getSeq(String seq_name);
	
	/**
	 * 查询方案
	 * @作者 MoChunrun
	 * @创建日期 2014-9-10 
	 * @param plan_id
	 * @return
	 */
	public Plan getPlan(String plan_id);
	
	/**
	 * 按规则关系查询 排序
	 * @作者 MoChunrun
	 * @创建日期 2014-9-10 
	 * @param plan_id
	 * @return
	 */
	public List<PlanRule> listPlanRule(String plan_id,int auto_exe);
	
	/**
	 * 根据规则ID查询方案规则 
	 * @作者 MoChunrun
	 * @创建日期 2014-9-10 
	 * @param rule_id
	 * @return
	 */
	public PlanRule getPlanRule(String rule_id,String plan_id,int auto_exe);
	
	/**
	 * 查询实例被依赖规则是否执行成功
	 * @作者 MoChunrun
	 * @创建日期 2014-9-10 
	 * @param parentRuleId
	 * @param obj_id
	 * @return
	 */
	public boolean isRuleExeSuccess(String plan_id,String rule_id,String obj_id);
	
	/**
	 * 查询规则执行日志 获取时新的一条记录
	 * @作者 MoChunrun
	 * @创建日期 2014-9-10 
	 * @param rule_id
	 * @param obj_id
	 * @return
	 */
	public RuleExeLog getRuleExecuteLog(String plan_id,String rule_id,String obj_id);
	public RuleExeLog getRuleExeLog(String obj_id,String log_id);
	
	/**
	 * 查询子规则
	 * @作者 MoChunrun
	 * @创建日期 2014-9-11 
	 * @param rule_id
	 * @return
	 */
	public List<PlanRule> queryChildrenRule(String plan_id,String rule_id,int auto_exe);
	
	/**
	 * 查询方案规则依赖的所有规则
	 * @作者 MoChunrun
	 * @创建日期 2014-9-12 
	 * @param plan_id
	 * @param rule_id
	 * @return
	 */
	public List<PlanRule> queryRuleRelyOnRule(String plan_id,String rule_id,int auto_exe);
	
	/**
	 * 查询是否有互斥方案已经执行过
	 * @作者 MoChunrun
	 * @创建日期 2014-9-13 
	 * @param rule_id
	 * @param plan_id
	 * @param obj_id
	 * @return
	 */
	public boolean hasExeMutexRule(String rule_id,String plan_id,String obj_id);
	
	/**
	 * 是否有全部互斥规则已执行
	 * @作者 MoChunrun
	 * @创建日期 2014-10-11 
	 * @param rule_pid
	 * @param plan_id
	 * @param obj_id
	 * @return
	 */
	public boolean hasAllMutexRuleExe(String rule_pid,String plan_id,String obj_id,String curr_rule_id);
	
	/**
	 * 修改子环节错误信息
	 * @作者 MoChunrun
	 * @创建日期 2014-9-13 
	 * @param log_id
	 * @param status
	 * @param info
	 */
	public void updateLogChildrenErrorInfo(String log_id,int status,String info);
	/**
	 * 修改子环节错误信息
	 * @作者 MoChunrun
	 * @创建日期 2014-9-15 
	 * @param plan_id
	 * @param rule_id
	 * @param obj_id
	 */
	public void updateLogChildrenErrorInfo(String plan_id,String rule_id,String obj_id,int status,String info);
	
	/**
	 * 查询同级并执行在当前规则后面的所有规则
	 * @作者 MoChunrun
	 * @创建日期 2014-9-18 
	 * @param p_rule_id
	 * @param priority -1表示查询所有同级规则 
	 * @return
	 */
	public List<PlanRule> queryPeerPlanRule(String plan_id,String p_rule_id,int priority,int auto_exe);
	
	/**
	 * 子规则或被依赖规则或手动规则再次执行执行，并执行成功或失败后修改父级规则的日志的子规则错误信息
	 * @作者 MoChunrun
	 * @创建日期 2014-9-19 
	 * @param plan_id
	 * @param chileren_rule_id
	 * @param obj_id
	 * @param status
	 * @param info
	 */
	public void updateParentRuleExeLogChildErrorInfo(String plan_id,String chileren_rule_id,String obj_id,int status,String info);
	
	/**
	 * 按目录ID查询方案
	 * @作者 MoChunrun
	 * @创建日期 2014-10-13 
	 * @param catalogue_id
	 * @return
	 */
	public List<Plan> queryPlanByCatalogueId(String catalogue_id);
	
	/**
	 * 判断方案的规则是否有被执行(其中有一个执行了就返回true)
	 * @作者 MoChunrun
	 * @创建日期 2014-10-14 
	 * @param catalogue_id
	 * @param obj_id
	 * @return
	 */
	public boolean isPlanHasRuleExecute(String catalogue_id,String plan_id,String obj_id);
	
	/**
	 * 删除执行日志
	 * @作者 MoChunrun
	 * @创建日期 2014-10-20 
	 * @param plan_id
	 * @param rule_id
	 * @param obj_id
	 */
	public void deleteRuleExeLogs(String[] plan_id,String rule_id,String obj_id);
	
	public List<RuleCondCache> getRuleCond(String rule_id);
	
	public List<RuleAction> getRuleActions(String rule_id);
	
	/**
	 * 查询不参与计算的条件
	 * @作者 MoChunrun
	 * @创建日期 2014-12-8 
	 * @param rule_id
	 * @return
	 */
	public List<RuleCondCache> getUnContRuleCond(String rule_id);
	
	public void refershCache();
	
	/**
	 * 查询消息队列
	 * @作者 MoChunrun
	 * @创建日期 2014-10-21 
	 * @param co_queue_id
	 * @return
	 */
	public CoQueue getQueueById(String co_queue_id);
	public CoQueue getQueueById(String co_queue_id,boolean isBak);
	
	/**
	 * 根据环节编码，查找方案
	 * @param tacheCode
	 * @return
	 */
	public Plan getPlanByTacheCode(String tacheCode);
	
}
