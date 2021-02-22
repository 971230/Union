package com.ztesoft.net.mall.core.service;

import java.util.List;

import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.model.PlanService;
import com.ztesoft.net.mall.core.model.RuleAction;
import com.ztesoft.net.mall.core.model.RuleActionAudit;
import com.ztesoft.net.mall.core.model.RuleCond;
import com.ztesoft.net.mall.core.model.RuleCondAudit;
import com.ztesoft.net.mall.core.model.RuleConfig;
import com.ztesoft.net.mall.core.model.RuleConfigAudit;
import com.ztesoft.net.mall.core.model.RuleConfigConstAudit;
import com.ztesoft.net.mall.core.model.RuleConfigListAudit;
import com.ztesoft.net.mall.core.model.RuleObj;
import com.ztesoft.net.mall.core.model.RuleObjAttr;
import com.ztesoft.net.mall.core.model.RuleObjAttrRel;

/**
 * 规则管理
 * @作者 MoChunrun
 * @创建日期 2013-12-11 
 * @版本 V 1.0
 */
public interface IRuleConfigManager {

	/**
	 * 添加规则
	 * @作者 MoChunrun
	 * @创建日期 2013-12-12 
	 * @param ruleConfig
	 */
	public void insertRuleConfig(RuleConfig ruleConfig);
	
	/**
	 * 添加规则
	 * @作者 MoChunrun
	 * @创建日期 2013-12-12 
	 * @param ruleConfig
	 */
	public void insertRuleConfigAudit(RuleConfigAudit ruleConfig);
	
	/**
	 * 修改规则
	 * @作者 MoChunrun
	 * @创建日期 2013-12-12 
	 * @param ruleConfig
	 */
	public void editRuleConfig(RuleConfig ruleConfig);
	
	/**
	 * 修改规则
	 * @作者 MoChunrun
	 * @创建日期 2013-12-12 
	 * @param ruleConfig
	 */
	public void editRuleConfigAudit(RuleConfigAudit ruleConfig);
	
	/**
	 * 删除规则
	 * @作者 MoChunrun
	 * @创建日期 2013-12-12 
	 * @param rule_id
	 */
	public void delRuleConfig(String rule_id);
	/**
	 * 按ID查询规则
	 * @作者 MoChunrun
	 * @创建日期 2013-12-12 
	 * @param rule_id
	 * @return
	 */
	public RuleConfig getRuleConfigById(String rule_id);
	
	/**
	 * 按ID查询规则
	 * @作者 MoChunrun
	 * @创建日期 2013-12-12 
	 * @param rule_id
	 * @return
	 */
	public RuleConfigAudit getRuleConfigAuditById(String rule_id);
	
	/**
	 * 分页查询规则列表
	 * @作者 MoChunrun
	 * @创建日期 2013-12-12 
	 * @param rule_name 规则名称
	 * @param rule_code 规则编码
	 * @return
	 */
	public Page qryRuleConfigPage(String rule_name,String rule_code,int pageNo,int pageSize);
	
	/**
	 * 分页查询规则列表
	 * @作者 MoChunrun
	 * @创建日期 2013-12-12 
	 * @param rule_name 规则名称
	 * @param rule_code 规则编码
	 * @return
	 */
	public Page qryRuleConfigAuditPage(String rule_name,String rule_code,int pageNo,int pageSize);
	
	/**
	 * 查询规则对像
	 * @作者 MoChunrun
	 * @创建日期 2013-12-12 
	 * @param obj_name
	 * @return
	 */
	public Page qryRuleObjPage(String obj_name,int pageNo,int pageSize);
	/**
	 * 按ID查询对像
	 * @作者 MoChunrun
	 * @创建日期 2013-12-12 
	 * @param obj_id
	 * @return
	 */
	public RuleObj getRuleObjById(String obj_id);
	
	public List<RuleObj> qryRuleObjByRuleId(String rule_id);
	public List<RuleObj> qryRuleObjByAuditRuleId(String rule_id);
	public List<RuleObj> qryRuleObjByAuditRuleId(String rule_id,String list_id);
	public List<RuleObj> qryRuleObjByIds(String rule_ids);
	/**
	 * 按对像ID查询对象所有属性
	 * @作者 MoChunrun
	 * @创建日期 2013-12-12 
	 * @param obj_id
	 * @return
	 */
	public List<RuleObjAttr> qryRuleObjAttrByObjId(String obj_id);
	/**
	 * 查询对象属性，与规则是否选中
	 * @作者 MoChunrun
	 * @创建日期 2013-12-12 
	 * @param obj_id
	 * @param rule_id
	 * @return
	 */
	public List<RuleObjAttr> qryRuleObjAttr(String obj_id,String rule_id);
	
	/**
	 * 按规则ID查询规则条件
	 * @作者 MoChunrun
	 * @创建日期 2013-12-12 
	 * @param rule_id
	 * @return
	 */
	public List<RuleCond> qryRuleCondByRuleId(String rule_id);
	
	/**
	 * 按规则ID查询规则条件
	 * @作者 MoChunrun
	 * @创建日期 2013-12-12 
	 * @param rule_id
	 * @return
	 */
	public List<RuleCondAudit> qryRuleCondAuditByRuleId(String rule_id);
	
	/**
	 * 插入规则条件
	 * @作者 MoChunrun
	 * @创建日期 2013-12-12 
	 * @param ruleCond
	 */
	public void insertRuleCond(RuleCond ruleCond);
	
	/**
	 * 插入规则条件
	 * @作者 MoChunrun
	 * @创建日期 2013-12-12 
	 * @param ruleCond
	 */
	public void insertRuleCondAudit(RuleCondAudit ruleCond);
	
	/**
	 * 按规则ID删除规则条件
	 * @作者 MoChunrun
	 * @创建日期 2013-12-12 
	 * @param rule_id
	 */
	public void delRuleCondByRuleId(String rule_id);
	
	/**
	 * 按规则ID删除规则条件
	 * @作者 MoChunrun
	 * @创建日期 2013-12-12 
	 * @param rule_id
	 */
	public void delRuleCondAuditByRuleId(String rule_id);
	
	/**
	 * 按ID删除规则
	 * @作者 MoChunrun
	 * @创建日期 2013-12-12 
	 * @param cond_id
	 */
	public void delRuleCondById(String cond_id);
	
	/**
	 * 按ID删除规则
	 * @作者 MoChunrun
	 * @创建日期 2013-12-12 
	 * @param cond_id
	 */
	public void delRuleCondAuditById(String cond_id);
	/**
	 * 修改规则条件
	 * @作者 MoChunrun
	 * @创建日期 2013-12-12 
	 * @param ruleCond
	 */
	public void editRuleCond(RuleCond ruleCond);
	
	/**
	 * 修改规则条件
	 * @作者 MoChunrun
	 * @创建日期 2013-12-12 
	 * @param ruleCond
	 */
	public void editRuleCondAudit(RuleCondAudit ruleCond);
	
	/**
	 * 插入规则动作
	 * @作者 MoChunrun
	 * @创建日期 2013-12-12 
	 * @param ruleAction
	 */
	public void insertRuleAction(RuleAction ruleAction);
	
	/**
	 * 插入规则动作
	 * @作者 MoChunrun
	 * @创建日期 2013-12-12 
	 * @param ruleAction
	 */
	public void insertRuleActionAudit(RuleActionAudit ruleAction);
	
	/**
	 * 修改规则动作
	 * @作者 MoChunrun
	 * @创建日期 2013-12-12 
	 * @param ruleAction
	 */
	public void editRuleAction(RuleAction ruleAction);
	
	/**
	 * 修改规则动作
	 * @作者 MoChunrun
	 * @创建日期 2013-12-12 
	 * @param ruleAction
	 */
	public void editRuleActionAudit(RuleActionAudit ruleAction);
	
	/**
	 * 删除规则动作
	 * @作者 MoChunrun
	 * @创建日期 2013-12-12 
	 * @param action_id
	 */
	public void delRuleAction(String action_id);
	
	/**
	 * 删除规则动作
	 * @作者 MoChunrun
	 * @创建日期 2013-12-12 
	 * @param action_id
	 */
	public void delRuleActionAudit(String action_id);
	
	/**
	 * 按规则ID删除规则动作
	 * @作者 MoChunrun
	 * @创建日期 2013-12-12 
	 * @param rule_id
	 */
	public void delRuleActionByRuleId(String rule_id);
	
	/**
	 * 按规则ID删除规则动作
	 * @作者 MoChunrun
	 * @创建日期 2013-12-12 
	 * @param rule_id
	 */
	public void delRuleActionAuditByRuleId(String rule_id);
	
	/**
	 * 按规则ID查询规则动作
	 * @作者 MoChunrun
	 * @创建日期 2013-12-12 
	 * @param rule_id
	 * @return
	 */
	public List<RuleAction> qryRuleActionByRuleId(String rule_id);
	
	/**
	 * 按规则ID查询规则动作
	 * @作者 MoChunrun
	 * @创建日期 2013-12-12 
	 * @param rule_id
	 * @return
	 */
	public List<RuleActionAudit> qryRuleActionAuditByRuleId(String rule_id);
	
	
	/**
	 * 按方案ID查询方案与方案服务类型
	 * @作者 MoChunrun
	 * @创建日期 2013-12-25 
	 * @param plan_id
	 * @return
	 */
	public PlanService getPlanService(String plan_id);
	
	/**
	 * 按规则ID查询所有对象
	 * @作者 MoChunrun
	 * @创建日期 2014-2-24 
	 * @param rule_id
	 * @return
	 */
	public List<RuleObj> queryRuleObjByRuleId(String rule_id);
	
	/**
	 * 按规则ID查询所有对象
	 * @作者 MoChunrun
	 * @创建日期 2014-2-24 
	 * @param rule_id
	 * @return
	 */
	public List<RuleObj> queryRuleObjByAuditRuleId(String rule_id);
	
	/**
	 * 按ID查询对象属性
	 * @作者 MoChunrun
	 * @创建日期 2014-2-24 
	 * @param objAttr_id
	 * @return
	 */
	public RuleObjAttr getRuleObjAttr(String objAttr_id,String obj_id);
	
	public String getRuleConfigId();
	
	/**
	 * 按className查询RuleObj
	 * @作者 MoChunrun
	 * @创建日期 2014-3-4 
	 * @param className
	 * @return
	 */
	public RuleObj queryRuleObjByClassName(String className);
	/**
	 * 插入ruleObj
	 * @作者 MoChunrun
	 * @创建日期 2014-3-4 
	 * @param obj
	 */
	public void insertRuleObj(RuleObj obj);
	
	/**
	 * 按obj_id与attr_code要询属性
	 * @作者 MoChunrun
	 * @创建日期 2014-3-4 
	 * @param obj_id
	 * @param attr_code
	 * @return
	 */
	public RuleObjAttr queryRuleObjAttrByObjIdAndAttrCode(String obj_id,String attr_code);
	/**
	 * 插入对象属性
	 * @作者 MoChunrun
	 * @创建日期 2014-3-4 
	 * @param attr
	 */
	public void insertRuleObjAttr(RuleObjAttr attr);
	/**
	 * 修改对象
	 * @作者 MoChunrun
	 * @创建日期 2014-3-4 
	 * @param obj
	 */
	public void updateRuleObj(RuleObj obj);
	/**
	 * 修改对象属性
	 * @作者 MoChunrun
	 * @创建日期 2014-3-4 
	 * @param attr
	 */
	public void updateRuleObjAttr(RuleObjAttr attr);
	/**
	 * 删除旧属性 inAttrCodes 为空时删除对象所有属性
	 * @作者 MoChunrun
	 * @创建日期 2014-3-4 
	 * @param obj_id
	 * @param inAttrCodes
	 */
	public void deleteOldAttrs(String obj_id,String inAttrCodes);
	
	/**
	 * 插入es_rule_config_list
	 * @作者 MoChunrun
	 * @创建日期 2014-3-25 
	 * @param configList
	 */
	public void insertRuleConfigListAudit(RuleConfigListAudit configList);
	
	/**
	 * 按rule_id查询RuleConfigList
	 * @作者 MoChunrun
	 * @创建日期 2014-3-25 
	 * @param rule_id
	 * @return
	 */
	public List<RuleConfigListAudit> listRuleConfigListAudit(String rule_id);
	
	/**
	 * 按rule_id删除RuleConfigList
	 * @作者 MoChunrun
	 * @创建日期 2014-3-25 
	 * @param rule_id
	 */
	public void deleteRuleConfigListByRuleAuditId(String rule_id);
	
	/**
	 * 按listid查询条污件
	 * @作者 MoChunrun
	 * @创建日期 2014-3-25 
	 * @param list_id
	 * @return
	 */
	public List<RuleCondAudit> listRuleCondAuditByListId(String list_id);
	
	/**
	 * 按listid查询规则执行动作
	 * @作者 MoChunrun
	 * @创建日期 2014-3-25 
	 * @param list_id
	 * @return
	 */
	public List<RuleActionAudit> listRuleActionAuditByListId(String list_id);
	
	/**
	 * 修改审核状态
	 * @作者 MoChunrun
	 * @创建日期 2014-3-27 
	 * @param rule_id
	 * @param audit_status
	 */
	public void updateRuleConfigAuditStatus(String rule_id,String audit_status);
	
	/**
	 * 规则审核
	 * @param rule_id
	 */
	public void ruleAudit(String rule_id);
	
	/**
	 * 分页查询规则审核列表
	 * @param ruleConfigAudit
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Page qryRuleAuditList(RuleConfigAudit ruleConfigAudit,int pageNo,int pageSize);
	
	
	public boolean ruleColeIsExists(String rule_code);
	
	public void addRuleConfigConstAudit(RuleConfigConstAudit constAudit);
	
	public void delRuleConfigConstAuditByListId(String list_id);
	
	public List<RuleConfigConstAudit> queryRuleConfigConstAuditByListId(String list_id);
	
	public void delRuleConfigConstAuditByRuleId(String rule_id);
	
	public String getRuleAttrType(String rule_id, String obj_id, String attr_id, String condType);
	
	public String getRuleConfigScript(RuleConfigAudit ruleConfig);
	
	public RuleObjAttrRel getRuleObjAttr(String rule_id, String condType, String[] attr_code);
}
