package com.ztesoft.rule.manager.service;

import java.util.List;
import java.util.Map;

import com.ztesoft.net.auto.rule.vo.Assembly;
import com.ztesoft.net.auto.rule.vo.PlanRule;
import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.model.RuleConfig;
import com.ztesoft.net.mall.core.model.RuleObj;
import com.ztesoft.net.mall.core.model.RuleObjAttrRel;
import com.ztesoft.net.mall.core.model.RuleObjPlan;
import com.ztesoft.rule.manager.action.DirectoryVo;
import com.ztesoft.rule.manager.action.EditChildrenRuleVo;
import com.ztesoft.rule.manager.action.RuleNodeVo;
import com.ztesoft.rule.manager.action.SchemeNodeVo;
import com.ztesoft.rule.manager.action.SearchObjAttrVo;

public interface IRuleManagerService {

	
	/**
	 * 查询根节点
	 * @param id
	 * @param pid
	 * @return
	 */
	List<DirectoryVo> qryDirectory(String id, String pid);
	
	/**
	 * 添加目录
	 * @param directoryVo
	 */
	void saveDirectory (DirectoryVo directoryVo) ;
	
	/**
	 * 修改目录
	 * @param directoryVo
	 */
	void editDirectory (DirectoryVo directoryVo);
	
	/**
	 * 显示目录
	 * @param directoryVo
	 */
	DirectoryVo qryDirectory(String id);
	
	/**
	 * 删除目录
	 * @param id
	 */
	void deleteDirectory (String id);
	
	/**
	 * 显示方案节点
	 * @return
	 */
	List<SchemeNodeVo> qrySchemeNode(String catalogueId);
	
	/**
	 * 显示规则节点
	 * @param plan_id
	 * @param pid
	 * @return
	 */
	List<RuleNodeVo> qryRuleNode(String plan_id, String pid);
	
	/**
	 * 使用规则名称模糊搜索规则
	 * @param ruleName
	 * @return
	 */
	List<RuleNodeVo> qryRuleNodeByNames(String ruleName);
	
	/**
	 * 查询方案详细信息
	 * @param plan_id
	 * @return
	 */
	RuleObjPlan qrySchemeObj(String plan_id);
	
	/**
	 * 编辑方案信息
	 * @param schemeObj
	 */
	void editSchemeObj(RuleObjPlan schemeObj);
	
	/**
	 * 删除方案信息
	 * @param ruleObjPlan
	 */
	void delScheme(RuleObjPlan ruleObjPlan);
	
	/**
	 * 保存方案信息
	 * @param schemeObj
	 */
	void saveSchemeObj(RuleObjPlan schemeObj);
	
	/**
	 * 查询目录集合-除根目录
	 * @return
	 */
	List<Map> qryPidList(String id);
	
	/**
	 * 查询规则对象
	 * @param rule_id
	 * @param plan_id
	 * @return
	 */
	List<RuleConfig> qryRuleObj(String rule_id, String plan_id, String pid);
	
	/**
	 * 获取组件对象集合
	 * @param ass_id
	 * @return
	 */
	Page qryAssemblyLs(int pageNO, int pageSize, Assembly assembly, AdminUser adminUser);
	/**
	 * 获取组件对象
	 * @param ass_id
	 * @return
	 */
	Assembly qryAssembly(String ass_id);
	
	/**
	 * 修改规则
	 * @param ruleConfig
	 */
	void editRuleConfig(RuleConfig ruleConfig);
	
	/**
	 * 新增规则
	 * @param ruleConfig
	 */
	void saveRuleConfig(RuleConfig ruleConfig);
	
	
	/**
	 * 查询规则对象列表
	 * @return
	 */
	public List<RuleObj> getRuleObjList();
	
	/**
	 * 保存规则属性关系
	 * @param ruleAttrList
	 */
	public void saveRuleAttr(String ruleAttrStr, String cond_type, String rule_id );
	
	/**
	 * 获取规则上已经选择的条件
	 * @return
	 */
	public List<RuleObjAttrRel> getSelectedAttr(String rule_id, String cond_type);
	
	/**
	 * 编辑子规则
	 * @param editChildrenRuleVo
	 */
	void editChildrenRule(EditChildrenRuleVo editChildrenRuleVo);
	
	/**
	 * 刷新业务组件
	 * @return
	 */
	Integer refreshAssembly();
	
	/**
	 * 删除规则
	 * @param rule_id
	 * @param plan_id
	 */
	void delRule(String rule_id, String plan_id);
	
	/**
	 * 查询方案列表
	 * @return
	 */
	List<RuleObjPlan> getRuleObjPlanList();
	
	/**
	 * 查询目录列表
	 * @return
	 */
	List<DirectoryVo> getRuleObjDirList();
	
	/**
	 * 查询规则对象列表
	 * @param plan_id
	 * @author ZX(2014-12-15)
	 * @return
	 */
	List<RuleObj> getRuleObjListByPlanId(String plan_id, String dir_id);
	
	/**
	 * 根据条件搜索规则
	 * @param searchCond
	 * @return
	 */
	Page searchRuleConfig(String searchCond, String no_include, String plan_id, String dir_id, int pageNo, int pageSize, String queryRuleName);
	
	boolean savePlanCond(String plan_id, String plan_cond);
	
	
	@SuppressWarnings("unchecked")
	public List<PlanRule> filterRuleByAttrCode(List<PlanRule> planRuleList,String plan_id,String order_id);
	
	public List<SearchObjAttrVo> getSearchObjAttrList(String plan_id, String order_id);
	
	
}
