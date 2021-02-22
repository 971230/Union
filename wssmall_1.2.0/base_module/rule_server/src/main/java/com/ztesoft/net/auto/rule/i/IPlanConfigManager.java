package com.ztesoft.net.auto.rule.i;

import java.util.List;

import com.ztesoft.net.auto.rule.vo.Plan;
import com.ztesoft.net.auto.rule.vo.RuleExeLog;
import com.ztesoft.net.framework.database.SqlBuilderInterface;
import com.ztesoft.net.mall.core.model.RuleConfig;

public interface IPlanConfigManager {

	/**
	 * 是询方案
	 * @作者 MoChunrun
	 * @创建日期 2014-9-15 
	 * @param plan_id
	 * @return
	 */
	public Plan getPlan(String plan_id);
	
	/**
	 * 查询方案的规则
	 * @作者 MoChunrun
	 * @创建日期 2014-9-15 
	 * @param plan_id
	 * @param level 0查询第一级的规则 
	 * @return
	 */
	public List<RuleConfig> queryRuleConfigByConfig(String plan_id,String p_rule_id);
	
	/**
	 * 查询方案的规则树
	 * @作者 MoChunrun
	 * @创建日期 2014-9-15 
	 * @param plan_id
	 * @param level 0查询第一级的规则 1查询所有规则
	 * @return
	 */
	public List<RuleConfig> queryLoopRuleConfigByConfig(String plan_id,String p_rule_id,String is_his_order);
	/**
	 * 查询实例数据规则树以及执行记录
	 * @作者 MoChunrun
	 * @创建日期 2014-9-15 
	 * @param plan_id
	 * @param p_rule_id
	 * @param obj_id 数据实例ID
	 * @return
	 */
	public List<RuleConfig> queryLoopRuleConfigByConfig(String plan_id,String p_rule_id,String obj_id,String is_his_order);
	
	/**
	 * 查询实例数据的规则执行日志
	 * @作者 MoChunrun
	 * @创建日期 2014-9-15 
	 * @param plan_id
	 * @param rule_id
	 * @param obj_id
	 * @return
	 */
	public List<RuleExeLog> queryRuleExeLog(String plan_id,String rule_id,String obj_id,String is_his_order);
	
	/**
	 * 根据pojo查询规则执行日志
	 * @param pojo
	 * @param sqlBuilds
	 * @return
	 * @throws Exception
	 */
	public List<RuleExeLog> qryRuleLogByPojo(RuleExeLog pojo,List<SqlBuilderInterface> sqlBuilds) throws Exception;
}
