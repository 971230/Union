package com.ztesoft.net.sqls;

import com.ztesoft.net.mall.core.utils.ManagerUtils;

/**
 * 
 * @author wu.i
 * 移植CRM代码
 *
 */
public class Sqls_Rule extends Sqls {

	
	public Sqls_Rule(){
		//SqlUtil.initSqls(Sqls_Rule.class, this, sqls) ;
	}
	
	public String getQUERY_RULE_CONSTS(){
		return "select c.*,o.obj_name,o.obj_code,a.attr_name,a.attr_code from es_rule_config_consts c,es_rule_obj o,es_rule_obj_attr a "+
			" where c.obj_id=o.obj_id and c.attr_id=a.attr_id and c.source_from=o.source_from and c.source_from=a.source_from "+
			" and c.rule_id=? and c.action_id=? and c.source_from=?";
	}

	public String getQUERY_PLAN_BY_ID(){
		return "select * from es_rule_biz_plan p where p.plan_id=?";
	}
	
	public String getQUERY_PLAN_RULE(){
		String sql = "select c.rule_id,c.rule_code,c.rule_name,c.resource_type,c.rule_script,c.status_cd,c.pid,rr.rel_type,rr.relyon_rule_id,rr.plan_id,rr.auto_exe,a.exe_path,c.pid,rr.priority "+
				" from es_rule_config c,es_rule_rel rr,es_assembly a where c.rule_id=rr.rule_id and c.ass_id=a.ass_id and c.source_from=rr.source_from and c.source_from=a.source_from "+
				" and c.source_from=? and rr.plan_id=? and c.pid='0' and c.status_cd='00A' ";
		return sql;
	}
	
	public String getQUERY_PLAN_RULE_BY_ID(){
		String sql = "select c.rule_id,c.rule_code,c.rule_name,c.resource_type,c.rule_script,c.status_cd,c.pid,rr.rel_type,rr.relyon_rule_id,rr.plan_id,rr.auto_exe,a.exe_path,c.pid,rr.priority "+
				" from es_rule_config c,es_rule_rel rr,es_assembly a where c.rule_id=rr.rule_id and c.ass_id=a.ass_id and c.source_from=rr.source_from and c.source_from=a.source_from "+
				" and c.source_from=? and c.rule_id=? and c.status_cd='00A' ";
		return sql;
	}
	
	public String getQUERY_CHILDREN_RULE(){
		String sql = "select c.rule_id,c.rule_code,c.rule_name,c.resource_type,c.rule_script,c.status_cd,c.pid,rr.rel_type,rr.relyon_rule_id,rr.plan_id,rr.auto_exe,a.exe_path,c.pid,rr.priority "+
				" from es_rule_config c,es_rule_rel rr,es_assembly a where c.rule_id=rr.rule_id and c.ass_id=a.ass_id and c.source_from=rr.source_from and c.source_from=a.source_from "+
				" and c.source_from=? and rr.plan_id=? and c.pid=? and c.status_cd='00A' ";
		return sql;
	}
	
	public String getQUERY_RULE_RELY_ON_RULE(){
		return "select c.rule_id, c.rule_code, c.rule_name, c.resource_type, c.rule_script, "+
			" c.status_cd, c.pid, rr2.rel_type, rr2.relyon_rule_id, rr2.plan_id,rr2.auto_exe, a.exe_path,c.pid,rr2.priority "+
			" from es_rule_config c, es_rule_rel rr, es_assembly a,es_rule_rel rr2 where "+ 
			" c.rule_id = rr.relyon_rule_id and c.ass_id = a.ass_id and c.source_from=rr.source_from " +
			" and c.source_from = a.source_from and c.source_from=rr2.source_from and c.source_from = ? "+
			" and rr.plan_id = ? and rr.rule_id = ? and rr.relyon_rule_id=rr2.rule_id and rr.plan_id=rr2.plan_id and "+
			" rr.source_from=rr2.source_from and c.status_cd='00A' ";
	}
	
	public String getQUERY_PLAN_RULE_CONFIG(){
		return "select distinct c.rule_id, c.rule_code, c.rule_name, c.resource_type, "+
			" c.impl_type, c.exp_date, c.eff_date, c.rule_desc, c.create_date, c.create_user, "+
			" c.status_date, c.status_user, c.status_cd, c.source_from, c.pid, "+
			" c.ass_id,min(r.priority),r.auto_exe,r.rel_type from es_rule_config c, es_rule_rel r where c.rule_id = r.rule_id and c.source_from = r.source_from "+
			" and r.plan_id = ? and c.source_from = ? and c.pid = ? group by c.rule_id, c.rule_code, "+
			" c.rule_name, c.resource_type, c.impl_type, c.exp_date, c.eff_date, c.rule_desc, "+
			" c.create_date, c.create_user, c.status_date, c.status_user, c.status_cd, "+
			"c.source_from, c.pid, c.ass_id,r.auto_exe,r.rel_type order by min(r.priority) asc";
	}
	
	public String getQUERY_PEER_PLAN_RULE(){
		return "select c.rule_id, c.rule_code, c.rule_name, c.resource_type, c.rule_script, "+
			" c.status_cd, c.pid, rr.rel_type, rr.relyon_rule_id, rr.plan_id, rr.auto_exe, "+
			" a.exe_path, c.pid,rr.priority from es_rule_config c, es_rule_rel rr, es_assembly "+
			" a where c.rule_id = rr.rule_id and c.ass_id = a.ass_id and c.source_from = rr.source_from "+
			" and c.source_from = a.source_from and c.source_from = ? and rr.plan_id = ? and c.pid = ? and rr.priority>? and c.status_cd='00A' ";
	}
	
	public String getRULE_OBJ_LIST(){
		return "select * from es_rule_obj where status_cd='00A' ";
	}
	
	public String getRULE_OBJ_ATTR_LIST(){
		return "select * from es_rule_obj_attr where obj_id=? and status_cd='00A' ";
	}
	
	public String getRULE_OBJ_ATTR_REL_LIST(){
		return "insert into es_rule_obj_attr_rel(rule_id, attr_id, obj_id, attr_code, attr_name, ele_type, ele_value, source_from, obj_name, cond_type, obj_code) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
	}
	
	public String getDEL_OBJ_ATTR_REL_LIST(){
		return "delete from es_rule_obj_attr_rel where rule_id = ? and cond_type = ? and source_from = '" + ManagerUtils.getSourceFrom() + "'";
	}
	
	public String getRULE_SELECT_ATTR(){
		return "select * from es_rule_obj_attr_rel where rule_id = ? and cond_type = ? ";
	}
	
	public String getRULE_ATTR_TYPE(){
		return "select a.ele_type from es_rule_obj_attr_rel a where a.attr_id = ? and a.obj_id = ? and a.rule_id = ? and a.cond_type = ?";
	}
	
	
	public String getRuleObjAttr(){
		return "select a.* from es_rule_obj_attr_rel a where a.rule_id = ? and a.cond_type = ?";
	}
	
	public String getQueryPlanByCatalogueId(){
		return "select t.* from es_rule_biz_plan t where t.catalogue_id = ? order by col1";
	}
	
	public String getRULE_COND_QUERY(){
		return "select rc.*,ro.obj_code,ro.clazz,roa.attr_code,a.action_id from es_rule_cond rc,es_rule_obj ro,es_rule_obj_attr roa,es_rule_action a "+
			" where rc.obj_id=ro.obj_id and rc.attr_id=roa.attr_id and rc.source_from=ro.source_from and ro.source_from=roa.source_from and rc.rule_id=a.rule_id and rc.source_from=a.source_from "+
			" and rc.rule_id=? and rc.source_from=? order by rc.attr_index asc";
	}
	
	public String getRULE_ACTION_QUERY(){
		return " select t.* from es_rule_action t where t.rule_id=?";
	}
	
	public String getUnContRuleCond(){
		return " select a.rule_id,a.attr_code,ro.obj_code, ro.clazz,ra.action_id from es_rule_obj_attr_rel a,es_rule_obj ro,es_rule_action ra where "+
			" a.obj_id=ro.obj_id and a.source_from=ro.source_from and a.rule_id=ra.rule_id and a.source_from=ra.source_from "+
			" and a.cond_type = 'cal_cond' and a.rule_id = ? and a.source_from=?";
	}
	public String getQRY_MATCH_DICT_LOGS() {
		return "SELECT T.* FROM ES_DATA_PRASER_INST T, ES_DATA_PRASER_DICT E WHERE T.DICT_ID=E.DICT_ID AND T.SOURCE_FROM=E.SOURCE_FROM";
	}
	public String getQRY_DICT_CATALOG() {
		return "SELECT T.* FROM ES_DATA_PRASER_CATALOG T WHERE 1=1";
	}
	
	//------------------------------------------------- 操作规则日志表 start ------------------------------------------------
	
	public String getRULE_IS_EXE_SUCCESS(){
		//return "select count(*) from es_rule_exe_log t where t.rule_id=? and t.plan_id=? and t.obj_id=? and t.exe_result=0";
		return "select count(*) from (select * from es_rule_exe_log union select * from es_rule_exe_log_bak) t where t.rule_id=? and t.plan_id=? and t.obj_id=? and t.exe_result=0";
	}
	
	public String getQUERY_RULE_EXE_LOG_BY_PLAN_RULE(){
		//return "select * from es_rule_exe_log t where t.rule_id=? and t.plan_id=? and t.obj_id=? order by t.create_time desc";
		return "select * from (select * from es_rule_exe_log union select * from es_rule_exe_log_bak) t where t.rule_id=? and t.plan_id=? and t.obj_id=? order by t.create_time desc";
	}
	
	public String getQUERY_RULE_EXE_LOG_BY_ID(){
		//return "select t.* from es_rule_exe_log t where t.obj_id=? and t.log_id=? ";
		return "select t.* from (select * from es_rule_exe_log union select * from es_rule_exe_log_bak) t where t.obj_id=? and t.log_id=? ";
	}
	
	public String getHAS_EXE_RULE_MUTEX_RULE(){
		/*return "select count(*) from es_rule_rel rr,es_rule_exe_log l where rr.rel_type=2 and (rr.rule_id=l.rule_id or rr.relyon_rule_id=l.rule_id)"+
		" and rr.plan_id=l.plan_id and rr.source_from=l.source_from and l.exe_result=0 and l.rule_id!=? and l.plan_id=? and l.obj_id=? and (rr.rule_id=? or rr.relyon_rule_id=?) and rr.source_from=?";*/
		return "select count(*) from es_rule_rel rr,(select * from es_rule_exe_log union select * from es_rule_exe_log_bak) l where rr.rel_type=2 and (rr.rule_id=l.rule_id or rr.relyon_rule_id=l.rule_id)"+
		" and rr.plan_id=l.plan_id and rr.source_from=l.source_from and l.exe_result=0 and l.rule_id!=? and l.plan_id=? and l.obj_id=? and (rr.rule_id=? or rr.relyon_rule_id=?) and rr.source_from=?";
	}
	
	public String getUPDATE_LOG_CHILD_ERROR(){
		return "update es_rule_exe_log t set t.children_error=?,t.children_info=? where t.log_id=?";
	}
	
	public String getUPDATE_LOG_CHILD_ERROR2(){
		return "update es_rule_exe_log l set l.children_error=?,l.children_info=? where l.plan_id=? and l.rule_id=? and l.obj_id=?";
	}
	
	public String getQUERY_PLAN_RULE_EXE_LOG(){
		//return "select t.* from es_rule_exe_log t where t.plan_id=? and t.rule_id=? and t.obj_id=? order by t.create_time desc";
		return "select t.* from (select * from es_rule_exe_log union select * from es_rule_exe_log_bak) t where t.plan_id=? and t.rule_id=? and t.obj_id=? order by t.create_time desc";
	}
	public String getQUERY_PLAN_RULE_EXE_LOG_HIS(){
		return "select t.* from es_rule_exe_log_his t where t.plan_id=? and t.rule_id=? and t.obj_id=? order by t.create_time desc";
	}
	
	public String getQUERY_RULE_EXE_LOG(){
		//return "select t.* from es_rule_exe_log t where t.rule_id=? and t.obj_id=? order by t.create_time desc";
		return "select t.* from (select * from es_rule_exe_log union select * from es_rule_exe_log_bak) t where t.rule_id=? and t.obj_id=? order by t.create_time desc";
	}
	public String getQUERY_RULE_EXE_LOG_HIS(){
		return "select t.* from es_rule_exe_log_his t where t.rule_id=? and t.obj_id=? order by t.create_time desc";
	}
	
	public String getUpdateParentRuleExeLogChildErrorInfo(){
		return "update es_rule_exe_log t set t.children_error=?,t.children_info=? where t.plan_id=? and t.obj_id=? and t.children_error!=0 and t.children_info=?";
	}
	
	public String getHasAllMutexRuleExe(){
		//return "select count(*) from es_rule_config r,es_rule_exe_log t where t.rule_id=r.rule_id and t.source_from=r.source_from and t.exe_result=0 and r.pid=? and t.plan_id=? and t.obj_id=? and t.source_from=? and t.rule_id <> ?";
		return "select count(*) from es_rule_config r,(select * from es_rule_exe_log union select * from es_rule_exe_log_bak) t where t.rule_id=r.rule_id and t.source_from=r.source_from and t.exe_result=0 and r.pid=? and t.plan_id=? and t.obj_id=? and t.source_from=? and t.rule_id <> ?";
	}
	
	public String getIsPlanHasRuleExecute(){
		//return "select count(*) from es_rule_biz_plan p,es_rule_exe_log t where t.plan_id=p.plan_id and t.source_from=p.source_from and p.catalogue_id=? and t.plan_id!=? and t.obj_id=? and t.exe_result = 0 and t.source_from=?";
		return "select count(*) from es_rule_biz_plan p,(select * from es_rule_exe_log union select * from es_rule_exe_log_bak) t where t.plan_id=p.plan_id and t.source_from=p.source_from and p.catalogue_id=? and t.plan_id!=? and t.obj_id=? and t.exe_result = 0 and t.source_from=?";
	}
	
	public String getDELETE_RULE_EXECUTE_LOG(){
		return "delete from es_rule_exe_log t where t.obj_id=?";
	}
	//------------------------------------------------- 操作规则日志表 end ------------------------------------------------
}
