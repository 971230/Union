package com.ztesoft.net.scheme.service;

import java.util.List;

import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.model.MidDataObjConfig;
import com.ztesoft.net.mall.core.model.ObjJava;
import com.ztesoft.net.mall.core.model.ObjSql;
import com.ztesoft.net.mall.core.model.PlanEntityRel;
import com.ztesoft.net.mall.core.model.PlanRuleAttr;
import com.ztesoft.net.mall.core.model.ProcessObjMid;
import com.ztesoft.net.mall.core.model.RuleObjPlan;

public interface ISchemeManager {
	public Page schemeList(String status_cd,String plan_code,String plan_name, int page,int pageSize);
	public void addScheme(RuleObjPlan rule);
	public void addRule(PlanRuleAttr attr);
	public void updateScheme(RuleObjPlan rule);
	public void updateRule(PlanRuleAttr rule);
	public RuleObjPlan qryScheme(String plan_id);
	public PlanRuleAttr qryAtrr(String plan_id);
	public List<PlanRuleAttr> query(String plan_id);
	public List service();
	
	public List midDateList(String plan_id);
	public void addMid(MidDataObjConfig mid);
	public void addSql(ObjSql sql);
	public void addJava(ObjJava java);
	
	public MidDataObjConfig queryMid(String mid_data_code);
	public void updateMid(MidDataObjConfig mid);
	
	public ObjSql querySql(String sql_code);
	public void updateSql(ObjSql sql);
	
	public ObjJava  queryJava(String java_code);
	public void updateJava(ObjJava java);
	
	public List midList(String plan_id);
	public void delMid(String mid_data_code);
	public void delProcess(String mid_data_code);
	public void addProcess(ProcessObjMid process);
	public ProcessObjMid queryProcess(String mid_data_code);
	public void updateProcess(ProcessObjMid midPro);
	public Page ruleList(String rule_id,int page,int pageSize);
	
	
	/**
	 * 删除方案规则关联数据
	 * @作者 MoChunrun
	 * @创建日期 2014-2-25 
	 * @param plan_id
	 */
	public void deletePlanRuleAttr(String plan_id);
	
	/**
	 * 删除方案中间数据
	 * @作者 MoChunrun
	 * @创建日期 2014-2-25 
	 * @param plan_id
	 */
	public void deletePlanMidData(String plan_id);
	
	/**
	 * 删除方案结果处理数据
	 * @作者 MoChunrun
	 * @创建日期 2014-2-25 
	 * @param plan_id
	 */
	public void deletePlanDataProcess(String plan_id);
	
	/**
	 * 查询java bean
	 * @作者 MoChunrun
	 * @创建日期 2014-2-25 
	 * @param bean
	 * @return
	 */
	public ObjJava queryJavaByJavaBean(String bean);
	
	/**
	 * 查询sql 
	 * @作者 MoChunrun
	 * @创建日期 2014-2-25 
	 * @param sql
	 * @return
	 */
	public ObjSql querySqlBySql(String sql);
	
	/**
	 * 添加方案参与者
	 * @作者 MoChunrun
	 * @创建日期 2014-2-27 
	 * @param entty
	 */
	public void insertPlanEntityRel(PlanEntityRel entty);
	
	/**
	 * 查询方案实体
	 * @作者 MoChunrun
	 * @创建日期 2014-2-27 
	 * @param rel_id
	 * @return
	 */
	public PlanEntityRel getPlanEntityRel(String rel_id);
	
	/**
	 * 修改方案参与者
	 * @作者 MoChunrun
	 * @创建日期 2014-2-27
	 */
	public void editPlanEntityRel(PlanEntityRel entity);
	
	/**
	 * 删除方案参与者
	 * @作者 MoChunrun
	 * @创建日期 2014-2-27 
	 * @param entity_id
	 */
	public void deletePlanEntityRel(String rel_id);
	
	/**
	 * 按方案ID查询方案参与者
	 * @作者 MoChunrun
	 * @创建日期 2014-2-27 
	 * @param plan_id
	 * @return
	 */
	public Page queryPlanEntityRelForPage(String plan_id,int pageNo,int pageSize);
	
	public boolean planCodeIsExists(String plan_code);
	
}
