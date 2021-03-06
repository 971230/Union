package com.ztesoft.net.mall.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.powerise.ibss.util.DBTUtil;
import com.ztesoft.ibss.common.util.StringUtils;
import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.framework.util.StringUtil;
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
import com.ztesoft.net.mall.core.service.IRuleConfigManager;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.sqls.SF;
import com.ztesoft.rule.core.util.RuleScriptUtil;

/**
 * 规则管理
 * @作者 MoChunrun
 * @创建日期 2013-12-12 
 * @版本 V 1.0
 */
public class RuleConfigManager extends BaseSupport implements IRuleConfigManager {

	@Override
	public void insertRuleConfig(RuleConfig ruleConfig) {
		this.baseDaoSupport.insert("es_rule_config", ruleConfig);
	}

	@Override
	public void editRuleConfig(RuleConfig ruleConfig) {
		this.baseDaoSupport.update("es_rule_config", ruleConfig, "rule_id='"+ruleConfig.getRule_id()+"'");
	}

	@Override
	public void delRuleConfig(String rule_id) {
		String sql = "delete from es_rule_config where rule_id=?";
		this.baseDaoSupport.execute(sql, rule_id);
	}

	@Override
	public RuleConfig getRuleConfigById(String rule_id) {
		String sql = "select * from es_rule_config where rule_id=?";
		return (RuleConfig) this.baseDaoSupport.queryForObject(sql, RuleConfig.class, rule_id);
	}

	@Override
	public Page qryRuleConfigPage(String rule_name, String rule_code,int pageNo,int pageSize) {
		List param = new ArrayList();
		String sql = "select * from es_rule_config where 1=1 ";
		if(!StringUtil.isEmpty(rule_name)){
			sql += " and upper(rule_name) like ?";
			param.add("%"+rule_name.trim().toUpperCase()+"%");
		}
		if(!StringUtil.isEmpty(rule_code)){
			sql += " and rule_code=?";
			param.add(rule_code);
		}
		String countSql = sql.replace("*", " count(*) "); 
		sql += " order by create_date desc";
		return this.baseDaoSupport.queryForCPage(sql, pageNo, pageSize, RuleConfig.class, countSql, param.toArray());
	}

	@Override
	public Page qryRuleObjPage(String obj_name,int pageNo,int pageSize) {
		List param = new ArrayList();
		String sql = "select * from es_rule_obj where status_cd='00A'";
		if(!StringUtil.isEmpty(obj_name)){
			sql += " and upper(obj_name) like ?";
			param.add("%"+obj_name.trim().toUpperCase()+"%");
		}
		String countSql = sql.replace("*", " count(*) "); 
		return this.baseDaoSupport.queryForCPage(sql, pageNo, pageSize, RuleObj.class, countSql, param.toArray());
	}

	@Override
	public RuleObj getRuleObjById(String obj_id) {
		String sql = "select * from es_rule_obj where obj_id=?";
		return (RuleObj) this.baseDaoSupport.queryForObject(sql, RuleObj.class, obj_id);
	}
	
	@Override
	public List<RuleObj> qryRuleObjByRuleId(String rule_id){
		String sql = "select distinct e.* from es_rule_obj e,es_rule_cond c where e.obj_id=c.obj_id and c.rule_id=? and e.source_from=?";
		return this.baseDaoSupport.queryForList(sql, RuleObj.class, rule_id,ManagerUtils.getSourceFrom());
	}
	
	@Override
	public List<RuleObj> qryRuleObjByAuditRuleId(String rule_id){
		String sql = "select distinct e.* from es_rule_obj e,es_rule_cond_audit c where e.obj_id=c.obj_id and c.rule_id=? and e.source_from = c.source_from and e.source_from=? and c.status='00A'";
		return this.baseDaoSupport.queryForList(sql, RuleObj.class, rule_id,ManagerUtils.getSourceFrom());
	}
	
	@Override
	public List<RuleObj> qryRuleObjByAuditRuleId(String rule_id,String list_id){
		String sql = "select distinct e.* from es_rule_obj e,es_rule_cond_audit c where (e.obj_id=c.obj_id or e.obj_id=c.z_obj_id) and c.rule_id=? and e.source_from=? and c.list_id=? and c.status='00A'";
		return this.baseDaoSupport.queryForList(sql, RuleObj.class, rule_id,ManagerUtils.getSourceFrom(),list_id);
	}

	@Override
	public List<RuleObjAttr> qryRuleObjAttrByObjId(String obj_id) {
		String sql = "select * from es_rule_obj_attr where obj_id=? and status_cd='00A'";
		return this.baseDaoSupport.queryForList(sql, RuleObjAttr.class, obj_id);
	}
	
	@Override
	public List<RuleObjAttr> qryRuleObjAttr(String obj_id,String rule_id){
		String sql = "select a.*,c.rule_id from es_rule_obj_attr a left join es_rule_cond c on(c.attr_id=a.attr_id and c.obj_id=a.obj_id and c.rule_id=?) where a.obj_id=? and a.source_from=?";
		return this.baseDaoSupport.queryForList(sql, RuleObjAttr.class, rule_id,obj_id,ManagerUtils.getSourceFrom());
	}

	@Override
	public List<RuleCond> qryRuleCondByRuleId(String rule_id) {
		String sql = "select rc.*,ra.attr_name as attr_cname from es_rule_cond rc left join es_rule_obj_attr ra on(ra.attr_id=rc.attr_id) where rc.rule_id=? and rc.source_from=? order by rc.attr_index asc";
		return this.baseDaoSupport.queryForList(sql, RuleCond.class, rule_id,ManagerUtils.getSourceFrom());
	}

	@Override
	public void insertRuleCond(RuleCond ruleCond) {
		this.baseDaoSupport.insert("es_rule_cond", ruleCond);
	}

	@Override
	public void delRuleCondByRuleId(String rule_id) {
		String sql = "delete from es_rule_cond where rule_id=?";
		this.baseDaoSupport.execute(sql, rule_id);
	}

	@Override
	public void delRuleCondById(String cond_id) {
		String sql = "delete from es_rule_cond where cond_id=?";
		this.baseDaoSupport.execute(sql, cond_id);
	}

	@Override
	public void editRuleCond(RuleCond ruleCond) {
		this.baseDaoSupport.update("es_rule_cond", ruleCond, "cond_id='"+ruleCond.getCond_id()+"'");
	}

	@Override
	public void insertRuleAction(RuleAction ruleAction) {
		this.baseDaoSupport.insert("es_rule_action", ruleAction);
	}

	@Override
	public void editRuleAction(RuleAction ruleAction) {
		this.baseDaoSupport.update("es_rule_action", ruleAction, "action_id='"+ruleAction.getAction_id()+"'");
	}

	@Override
	public void delRuleAction(String action_id) {
		String sql = "delete from es_rule_action where action_id=?";
		this.baseDaoSupport.execute(sql, action_id);
	}

	@Override
	public void delRuleActionByRuleId(String rule_id) {
		String sql = "delete from es_rule_action where rule_id=?";
		this.baseDaoSupport.execute(sql, rule_id);
	}

	@Override
	public List<RuleAction> qryRuleActionByRuleId(String rule_id) {
		String sql = "select * from es_rule_action where rule_id=?";
		return this.baseDaoSupport.queryForList(sql, RuleAction.class, rule_id);
	}

	@Override
	public List<RuleObj> qryRuleObjByIds(String rule_ids) {
		String sql = "select e.* from es_rule_obj e where e.obj_id in("+rule_ids+")";
		return this.baseDaoSupport.queryForList(sql, RuleObj.class);
	}

	@Override
	public PlanService getPlanService(String plan_id) {
		String sql = "select p.eff_date, p.exp_date, p.plan_id, p.plan_name, p.plan_code, s.service_id, s.service_offer_name, s.service_code from es_rule_biz_plan p,es_commission_service_offer s where p.service_type=s.service_id and p.plan_id=? and p.source_from=?";
		return (PlanService) this.baseDaoSupport.queryForObject(sql, PlanService.class, plan_id,ManagerUtils.getSourceFrom());
	}

	@Override
	public List<RuleObj> queryRuleObjByRuleId(String rule_id) {
		String sql = "select distinct o.* from es_rule_obj o,es_rule_cond rc where ((o.obj_id=rc.obj_id and rc.z_obj_id is null) or (o.obj_id=rc.z_obj_id and rc.z_obj_id is not null)) and rc.rule_id=? and o.source_from=?";
		return this.baseDaoSupport.queryForList(sql, RuleObj.class, rule_id,ManagerUtils.getSourceFrom());
	}
	
	@Override
	public List<RuleObj> queryRuleObjByAuditRuleId(String rule_id) {
		String sql = "select distinct o.* from es_rule_obj o,es_rule_cond_audit rc where ((o.obj_id=rc.obj_id and rc.z_obj_id is null) or (o.obj_id=rc.z_obj_id and rc.z_obj_id is not null)) and rc.rule_id=? and o.source_from=? and rc.status='00A'";
		return this.baseDaoSupport.queryForList(sql, RuleObj.class, rule_id,ManagerUtils.getSourceFrom());
	}

	@Override
	public RuleObjAttr getRuleObjAttr(String objAttr_id,String obj_id) {
		String sql = "select t.*,t.rowid from es_rule_obj_attr t where t.attr_id=? and t.obj_id=?";
		return (RuleObjAttr) this.baseDaoSupport.queryForObject(sql, RuleObjAttr.class, objAttr_id,obj_id);
	}
	
	@Override
	public String getRuleConfigId(){
		return this.baseDaoSupport.getSequences("s_es_rule_config");
	}

	@Override
	public void insertRuleObj(RuleObj obj) {
		this.baseDaoSupport.insert("es_rule_obj", obj);
	}

	@Override
	public void insertRuleObjAttr(RuleObjAttr attr) {
		this.baseDaoSupport.insert("es_rule_obj_attr", attr);
	}

	@Override
	public RuleObj queryRuleObjByClassName(String className) {
		String sql = "select * from es_rule_obj where clazz=?";
		return (RuleObj) this.baseDaoSupport.queryForObject(sql, RuleObj.class, className);
	}

	@Override
	public RuleObjAttr queryRuleObjAttrByObjIdAndAttrCode(String obj_id,
			String attr_code) {
		String sql = "select * from es_rule_obj_attr where obj_id=? and attr_code=?";
		List<RuleObjAttr> list = this.baseDaoSupport.queryForList(sql, RuleObjAttr.class, obj_id,attr_code);
		return list.size()>0?list.get(0):null;
	}

	@Override
	public void updateRuleObj(RuleObj obj) {
		this.baseDaoSupport.update("es_rule_obj", obj, "obj_id='"+obj.getObj_id()+"'");
	}

	@Override
	public void updateRuleObjAttr(RuleObjAttr attr) {
		this.baseDaoSupport.update("es_rule_obj_attr", attr, "attr_id='"+attr.getAttr_id()+"'");
	}

	@Override
	public void deleteOldAttrs(String obj_id, String inAttrCodes) {
		String sql = "delete from es_rule_obj_attr where obj_id=?";
		if(!StringUtils.isEmpty(inAttrCodes)){
			sql += " and attr_code not in("+inAttrCodes+")";
		}
		this.baseDaoSupport.execute(sql, obj_id);
	}

	@Override
	public void insertRuleConfigAudit(RuleConfigAudit ruleConfig) {
		this.baseDaoSupport.insert("es_rule_config_audit", ruleConfig);
	}

	@Override
	public void editRuleConfigAudit(RuleConfigAudit ruleConfig) {
		this.baseDaoSupport.update("es_rule_config_audit", ruleConfig, "rule_id='"+ruleConfig.getRule_id()+"' and status_cd='00A'");
	}

	@Override
	public RuleConfigAudit getRuleConfigAuditById(String rule_id) {
		String sql = "select * from es_rule_config_audit where rule_id=? and status_cd='00A'";
		return (RuleConfigAudit) this.baseDaoSupport.queryForObject(sql, RuleConfigAudit.class, rule_id);
	}

	@Override
	public Page qryRuleConfigAuditPage(String rule_name, String rule_code,
			int pageNo, int pageSize) {
		List param = new ArrayList();
		String sql = "select * from es_rule_config_audit where 1=1 ";
		if(!StringUtil.isEmpty(rule_name)){
			sql += " and upper(rule_name) like ?";
			param.add("%"+rule_name.trim().toUpperCase()+"%");
		}
		if(!StringUtil.isEmpty(rule_code)){
			sql += " and rule_code=?";
			param.add(rule_code);
		}
		sql += "and status_cd='00A'";
		String countSql = sql.replace("*", " count(*) "); 
		sql += " order by create_date desc";
		return this.baseDaoSupport.queryForCPage(sql, pageNo, pageSize, RuleConfigAudit.class, countSql, param.toArray());
	}

	@Override
	public List<RuleCondAudit> qryRuleCondAuditByRuleId(String rule_id) {
		String sql = "select rc.*,ra.attr_name as attr_cname, ra.ele_type from es_rule_cond_audit rc left join es_rule_obj_attr ra on(ra.attr_id=rc.attr_id and rc.source_from = ra.source_from) where rc.rule_id=? and rc.source_from=? and rc.status='00A' order by rc.attr_index asc";
		return this.baseDaoSupport.queryForList(sql, RuleCondAudit.class, rule_id,ManagerUtils.getSourceFrom());
	}

	@Override
	public void insertRuleCondAudit(RuleCondAudit ruleCond) {
		this.baseDaoSupport.insert("es_rule_cond_audit", ruleCond);
	}

	@Override
	public void delRuleCondAuditByRuleId(String rule_id) {
		String sql = "delete from es_rule_cond_audit where rule_id=? and status='00A'";
		this.baseDaoSupport.execute(sql, rule_id);
	}

	@Override
	public void delRuleCondAuditById(String cond_id) {
		String sql = "delete from es_rule_cond_audit where cond_id=?";
		this.baseDaoSupport.execute(sql, cond_id);
	}

	@Override
	public void editRuleCondAudit(RuleCondAudit ruleCond) {
		this.baseDaoSupport.update("es_rule_cond_audit", ruleCond, "cond_id='"+ruleCond.getCond_id()+"'");
	}

	@Override
	public void insertRuleActionAudit(RuleActionAudit ruleAction) {
		this.baseDaoSupport.insert("es_rule_action_audit", ruleAction);
	}

	@Override
	public void editRuleActionAudit(RuleActionAudit ruleAction) {
		this.baseDaoSupport.update("es_rule_action_audit", ruleAction, "action_id='"+ruleAction.getAction_id()+"'");
	}

	@Override
	public void delRuleActionAudit(String action_id) {
		String sql = "delete from es_rule_action_audit where action_id=?";
		this.baseDaoSupport.execute(sql, action_id);
	}

	@Override
	public void delRuleActionAuditByRuleId(String rule_id) {
		String sql = "delete from es_rule_action_audit where rule_id=? and status='00A'";
		this.baseDaoSupport.execute(sql, rule_id);
	}

	@Override
	public List<RuleActionAudit> qryRuleActionAuditByRuleId(String rule_id) {
		String sql = "select * from es_rule_action_audit where rule_id=? and status='00A'";
		return this.baseDaoSupport.queryForList(sql, RuleActionAudit.class, rule_id);
	}

	@Override
	public void insertRuleConfigListAudit(RuleConfigListAudit configList) {
		this.baseDaoSupport.insert("es_rule_config_list_audit", configList);
	}

	@Override
	public List<RuleConfigListAudit> listRuleConfigListAudit(String rule_id) {
		String sql = "select * from es_rule_config_list_audit t where t.rule_id=? and t.status='00A' order by t.sort asc";
		return this.baseDaoSupport.queryForList(sql, RuleConfigListAudit.class,rule_id);
	}

	@Override
	public void deleteRuleConfigListByRuleAuditId(String rule_id) {
		String sql = "delete from es_rule_config_list_audit t where t.rule_id=? and t.status='00A'";
		this.baseDaoSupport.execute(sql, rule_id);
	}

	@Override
	public List<RuleCondAudit> listRuleCondAuditByListId(String list_id) {
		String sql = "select * from es_rule_cond_audit t where t.list_id=? and t.status='00A'";
		return this.baseDaoSupport.queryForList(sql, RuleCondAudit.class, list_id);
	}

	@Override
	public List<RuleActionAudit> listRuleActionAuditByListId(String list_id) {
		String sql = "select * from es_rule_action_audit t where t.list_id=? and t.status='00A'";
		return this.baseDaoSupport.queryForList(sql, RuleActionAudit.class, list_id);
	}

	@Override
	public void updateRuleConfigAuditStatus(String rule_id, String audit_status) {
		String sql = "update es_rule_config_audit t set t.audit_status=? where t.rule_id=? and t.status_cd='00A'";
		this.baseDaoSupport.execute(sql, audit_status,rule_id);
	}
	
	@Override
	public Page qryRuleAuditList(RuleConfigAudit ruleConfigAudit,int pageNo,int pageSize){
		StringBuffer sql = new StringBuffer();
		StringBuffer cSql = new StringBuffer();
		StringBuffer where = new StringBuffer();
		
		
		sql.append("SELECT * FROM es_rule_config_audit");
		cSql.append("SELECT count(rule_id) FROM es_rule_config_audit");
		where.append(" WHERE source_from = '"+ManagerUtils.getSourceFrom()+"'");
		where.append(" AND status_cd = '00A'");
		where.append(" AND audit_status = '00S'");
		
		if(StringUtils.isNotEmpty(ruleConfigAudit.getRule_name())){
			where.append(" AND rule_name LIKE '%"+ruleConfigAudit.getRule_name()+"%'");
		}
		if(StringUtils.isNotEmpty(ruleConfigAudit.getRule_code())){
			where.append(" AND rule_code LIKE '%"+ruleConfigAudit.getRule_code()+"%'");
		}
		
		return this.baseDaoSupport.queryForCPage(sql.append(where).toString(), pageNo, pageSize, 
				RuleConfigAudit.class, cSql.append(where).toString());
	}
	
	
	@Override
	public void ruleAudit(String rule_id){
		
		String batch_id = this.baseDaoSupport.getSequences("SEQ_ES_RULE_AUDIT_BATCH");
		AdminUser adminUser = ManagerUtils.getAdminUser();
		String user_id = adminUser.getUserid();
		String user_name = adminUser.getUsername();
		String current = DBTUtil.current();
		
		/************************ es_rule_config & es_rule_config_audit *****************************/
		//先把正式表的数据变成审核表的历史数据
		StringBuffer sql_1 = new StringBuffer();
		sql_1.append("INSERT INTO es_rule_config_audit (rule_id,rule_code,rule_name,");
		sql_1.append("resource_type,impl_type,exp_date,eff_date,rule_desc,create_date,create_user,");
		sql_1.append("status_date,status_user,status_cd,rule_script,source_from,audit_status,");
		sql_1.append("batch_id,batch_time,audit_user_id,audit_user_name, ass_id, pid) ");
		sql_1.append("SELECT rule_id,rule_code,rule_name,resource_type,impl_type,exp_date,");
		sql_1.append("eff_date,rule_desc,create_date,create_user,status_date,status_user,'00H',");
		sql_1.append("rule_script,source_from,'00A','"+batch_id+"',"+current+",'"+user_id+"',");
		sql_1.append("'"+user_name+"', ass_id, pid FROM es_rule_config WHERE rule_id = '"+rule_id+"'");
		this.baseDaoSupport.execute(sql_1.toString());
		
		//删除正式表的数据
		StringBuffer sql_2 = new StringBuffer();
		sql_2.append("DELETE FROM es_rule_config WHERE rule_id = '"+rule_id+"'");
		this.baseDaoSupport.execute(sql_2.toString());
		
		//插入正式表的新数据
		StringBuffer sql_3 = new StringBuffer();
		sql_3.append("INSERT INTO es_rule_config(rule_id,rule_code,rule_name,resource_type,");
		sql_3.append("impl_type,exp_date,eff_date,rule_desc,create_date,create_user,status_date,");
		sql_3.append("status_user,status_cd,rule_script,source_from, ass_id, pid) SELECT rule_id,rule_code,rule_name,");
		sql_3.append("resource_type,impl_type,exp_date,eff_date,rule_desc,create_date,create_user,");
		sql_3.append("status_date,status_user,status_cd,rule_script,source_from, ass_id, pid FROM es_rule_config_audit");
		sql_3.append(" WHERE status_cd = '00A' AND audit_status = '00S' AND rule_id = '"+rule_id+"'");
		this.baseDaoSupport.execute(sql_3.toString());
		
		//更新审核表的状态
		StringBuffer sql_4 = new StringBuffer();
		sql_4.append("UPDATE es_rule_config_audit SET audit_status = '00A' WHERE");
		sql_4.append(" status_cd = '00A' AND audit_status = '00S' AND rule_id = '"+rule_id+"'");
		this.baseDaoSupport.execute(sql_4.toString());
		/************************ es_rule_config & es_rule_config_audit *****************************/
		
		
		/************************ es_rule_cond & es_rule_cond_audit *****************************/
		//先把正式表的数据变成审核表的历史数据
		StringBuffer sql_5 = new StringBuffer();
		sql_5.append("INSERT INTO es_rule_cond_audit (cond_id,rule_id,obj_id,attr_id,attr_script,attr_index,");
		sql_5.append("opt_type,pre_log,z_obj_type,z_attr_id,z_obj_id,z_value,left_brackets,right_brackets,");
		sql_5.append("z_cvalue,source_from,list_id,batch_id,batch_time,status) SELECT cond_id,rule_id,");
		sql_5.append("obj_id,attr_id,attr_script,attr_index,opt_type,pre_log,z_obj_type,z_attr_id,z_obj_id,z_value,");
		sql_5.append("left_brackets,right_brackets,z_cvalue,source_from,list_id,'"+batch_id+"',"+current+",'00H'");
		sql_5.append(" FROM es_rule_cond WHERE rule_id = '"+rule_id+"'");
		this.baseDaoSupport.execute(sql_5.toString());
		
		//删除正式表的数据
		StringBuffer sql_6 = new StringBuffer();
		sql_6.append("DELETE FROM es_rule_cond WHERE rule_id = '"+rule_id+"'");
		this.baseDaoSupport.execute(sql_6.toString());
		
		//插入正式表的新数据
		StringBuffer sql_7 = new StringBuffer();
		sql_7.append("INSERT INTO es_rule_cond (cond_id,rule_id,obj_id,attr_id,attr_script,attr_index,");
		sql_7.append("opt_type,pre_log,z_obj_type,z_attr_id,z_obj_id,z_value,left_brackets,right_brackets,");
		sql_7.append("z_cvalue,source_from,list_id) SELECT cond_id,rule_id,obj_id,attr_id,attr_script,");
		sql_7.append("attr_index,opt_type,pre_log,z_obj_type,z_attr_id,z_obj_id,z_value,left_brackets,");
		sql_7.append("right_brackets,z_cvalue,source_from,list_id FROM es_rule_cond_audit WHERE");
		sql_7.append(" rule_id = '"+rule_id+"' AND status = '00A'");
		this.baseDaoSupport.execute(sql_7.toString());
		/************************ es_rule_cond & es_rule_cond_audit *****************************/
		
		
		/************************ es_rule_action & es_rule_action_audit *****************************/
		//先把正式表的数据变成审核表的历史数据
		StringBuffer sql_8 = new StringBuffer();
		sql_8.append("INSERT INTO es_rule_action_audit (action_id,rule_id,action_script,action_index,");
		sql_8.append("source_from,list_id,batch_id,batch_time,status) SELECT action_id,rule_id,");
		sql_8.append("action_script,action_index,source_from,list_id,'"+batch_id+"',"+current+",'00H'");
		sql_8.append(" FROM es_rule_action WHERE rule_id = '"+rule_id+"'");
		this.baseDaoSupport.execute(sql_8.toString());
		
		//删除正式表的数据
		StringBuffer sql_9 = new StringBuffer();
		sql_9.append("DELETE FROM es_rule_action WHERE rule_id = '"+rule_id+"'");
		this.baseDaoSupport.execute(sql_9.toString());
		
		//插入正式表的新数据
		StringBuffer sql_10 = new StringBuffer();
		sql_10.append("INSERT INTO es_rule_action (action_id,rule_id,action_script,action_index,");
		sql_10.append("source_from,list_id) SELECT action_id,rule_id,action_script,");
		sql_10.append("action_index,source_from,list_id FROM es_rule_action_audit");
		sql_10.append(" WHERE rule_id = '"+rule_id+"' AND status = '00A'");
		this.baseDaoSupport.execute(sql_10.toString());
		/************************ es_rule_action & es_rule_action_audit *****************************/
		
		
		/************************ es_rule_config_list & es_rule_config_list_audit *****************************/
		//先把正式表的数据变成审核表的历史数据
		StringBuffer sql_11 = new StringBuffer();
		sql_11.append("INSERT INTO es_rule_config_list_audit (rule_id,list_name,sort,list_id,source_from,");
		sql_11.append("batch_id,batch_time,status) SELECT rule_id,list_name,sort,list_id,source_from,");
		sql_11.append("'"+batch_id+"',"+current+",'00H' FROM es_rule_config_list WHERE");
		sql_11.append(" rule_id = '"+rule_id+"'");
		this.baseDaoSupport.execute(sql_11.toString());
		
		//删除正式表的数据
		StringBuffer sql_12 = new StringBuffer();
		sql_12.append("DELETE FROM es_rule_config_list WHERE rule_id = '"+rule_id+"'");
		this.baseDaoSupport.execute(sql_12.toString());
		
		//插入正式表的新数据
		StringBuffer sql_13 = new StringBuffer();
		sql_13.append("INSERT INTO es_rule_config_list (rule_id,list_name,sort,list_id,source_from");
		sql_13.append(") SELECT rule_id,list_name,sort,list_id,source_from FROM");
		sql_13.append(" es_rule_config_list_audit WHERE rule_id = '"+rule_id+"' AND status = '00A'");
		this.baseDaoSupport.execute(sql_13.toString());
		/************************ es_rule_config_list & es_rule_config_list_audit *****************************/
		
		
		/************************ es_rule_config_consts & es_rule_config_consts_audit *****************************/
		//先把正式表的数据变成审核表的历史数据
		StringBuffer sql_14 = new StringBuffer();
		sql_14.append("INSERT INTO es_rule_config_consts_audit(rule_id,list_id,action_id,obj_id,");
		sql_14.append("attr_id,const_value,batch_id,batch_time,status,source_from) SELECT rule_id,list_id,");
		sql_14.append("action_id,obj_id,attr_id,const_value,'"+batch_id+"',"+current+",'00H',source_from");
		sql_14.append(" FROM es_rule_config_consts WHERE rule_id = '"+rule_id+"'");
		this.baseDaoSupport.execute(sql_14.toString());
		
		//删除正式表的数据
		StringBuffer sql_15 = new StringBuffer();
		sql_15.append("DELETE FROM es_rule_config_consts WHERE rule_id = '"+rule_id+"'");
		this.baseDaoSupport.execute(sql_15.toString());
		
		//插入正式表的新数据
		StringBuffer sql_16 = new StringBuffer();
		sql_16.append("INSERT INTO es_rule_config_consts(rule_id,list_id,action_id,obj_id,");
		sql_16.append("attr_id,const_value,source_from) SELECT rule_id,list_id,action_id,obj_id,");
		sql_16.append("attr_id,const_value,source_from FROM es_rule_config_consts_audit");
		sql_16.append(" WHERE rule_id = '"+rule_id+"' AND status = '00A'");
		this.baseDaoSupport.execute(sql_16.toString());
		/************************ es_rule_config_consts & es_rule_config_consts_audit *****************************/
	}
	
	@Override
	public boolean ruleColeIsExists(String rule_code){
		String sql = "select count(*) from es_rule_config_audit t where t.rule_code=?";
		return this.baseDaoSupport.queryForInt(sql, rule_code)>0;
	}

	@Override
	public void addRuleConfigConstAudit(RuleConfigConstAudit constAudit) {
		this.baseDaoSupport.insert("ES_RULE_CONFIG_CONSTS_AUDIT", constAudit);
	}

	@Override
	public void delRuleConfigConstAuditByListId(String list_id) {
		String sql = "delete from ES_RULE_CONFIG_CONSTS_AUDIT t where t.status='00A' and t.list_id=?";
		this.baseDaoSupport.execute(sql, list_id);
	}

	@Override
	public List<RuleConfigConstAudit> queryRuleConfigConstAuditByListId(
			String list_id) {
		String sql = "select t.*,a.attr_name,a.attr_code,o.obj_code, a.ele_type from ES_RULE_CONFIG_CONSTS_AUDIT t,es_rule_obj_attr a,es_rule_obj o where t.obj_id=o.obj_id and t.attr_id=a.attr_id and t.status='00A' and t.list_id=? and t.source_from=?";
		return this.baseDaoSupport.queryForList(sql, RuleConfigConstAudit.class, list_id,ManagerUtils.getSourceFrom());
	}

	@Override
	public void delRuleConfigConstAuditByRuleId(String rule_id) {
		String sql = "delete from ES_RULE_CONFIG_CONSTS_AUDIT t where t.status='00A' and t.rule_id=?";
		this.baseDaoSupport.execute(sql, rule_id);
	}
	
	@Override
	public String getRuleAttrType(String rule_id, String obj_id, String attr_id, String condType){
		String attrSql = SF.ruleSql("RULE_ATTR_TYPE");
		return this.baseDaoSupport.queryForString(attrSql, new String[]{attr_id, obj_id, rule_id, condType});
	}

	@Override
	public String getRuleConfigScript(RuleConfigAudit ruleConfig) {
		List<RuleConfigListAudit> configListAuditList = new ArrayList<RuleConfigListAudit>();
		String script = "";
		try{
			//规则配置列表
			RuleConfigListAudit configListAudit = new RuleConfigListAudit();
			configListAudit.setList_name(ruleConfig.getRule_code() + 0);
			configListAudit.setSort(0);
			configListAudit.setStatus("00A");
			configListAudit.setRule_id(ruleConfig.getRule_id());
			this.insertRuleConfigListAudit(configListAudit);
			configListAuditList.add(configListAudit);
			
			//规则动作
			RuleActionAudit ra = new RuleActionAudit();
			ra.setAction_index(0);
			ra.setAction_script("");
			ra.setRule_id(ruleConfig.getRule_id());
			ra.setStatus("00A");
			ra.setList_id(configListAuditList.get(0).getList_id());
			this.insertRuleActionAudit(ra);
			configListAuditList.get(0).setRuleActionAudit(ra);
			
			//生成规则脚本
			script = RuleScriptUtil.parseRuleScript(ruleConfig,configListAuditList);
		}catch(Exception e ){
			e.printStackTrace();
			throw new RuntimeException("生成规则脚本失败");
		}
		return script;
	}

	@Override
	public RuleObjAttrRel getRuleObjAttr(String rule_id, String condType,
			String[] attr_code) {
		String sql = SF.ruleSql("RuleObjAttr");
		if(null != attr_code && attr_code.length > 0){
			String attrCodes = "";
			for(int i = 0; i < attr_code.length; i++){
				if(i == 0){
					attrCodes += attr_code[i];
				}else {
					attrCodes += "," + attr_code[i];
				}
			}
			attrCodes = attrCodes.replaceAll(",", "','");
			sql += "and a.attr_code in ('" + attrCodes + "')";
		}
		return (RuleObjAttrRel)this.baseDaoSupport.queryForObject(sql, RuleObjAttrRel.class, new String[]{rule_id, condType});
	}
	
}
