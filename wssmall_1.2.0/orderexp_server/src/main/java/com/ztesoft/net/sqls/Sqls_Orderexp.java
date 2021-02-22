package com.ztesoft.net.sqls;

import com.powerise.ibss.util.DBTUtil;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

/**
 * 
 * @author shenqiyu
 * 
 *
 */
public class Sqls_Orderexp extends Sqls {
	
	public Sqls_Orderexp(){
		
	}

	public String getExpInstByInstId(){return "select * from es_esearch_expinst where excp_inst_id = ?";}
	
	public String getExpInstList(){return "select * from es_esearch_expinst where 1=1";}
	
	public String getExpInstAndExtList(){return "select eei.*, eeie.*,oe.order_id as ext_order_id,nvl(oe.flow_trace_id,'NULL') as flow_trace_id, nvl(oe.refund_deal_type,'NULL') as refund_deal_type from es_esearch_expinst eei, es_esearch_expinst_ext eeie, es_order_ext oe where eei.excp_inst_id = eeie.excp_inst_id(+) and eei.out_tid = oe.out_tid(+) and eei.source_from = '"+ManagerUtils.getSourceFrom()+"' ";}
	
	public String getExpInstListForLOrder(){return "select eei.* from es_esearch_expinst eei where eei.source_from = '"+ManagerUtils.getSourceFrom()+"' ";}
	
	public String getUpdateExpInstByInstId(){return "update es_esearch_expinst set record_status=?,deal_result=?,deal_staff_no=?,deal_time=sysdate,process_times=(process_times+1) where excp_inst_id = ?";}
	
	public String getDeleteEsearchCatalog(){return "DELETE FROM es_esearch_catalog WHERE catalog_id = ?";}
	
	public String getQueryEsearchCatalog(){return "SELECT * FROM es_esearch_catalog WHERE disabled=0";}
	
	public String getQueryEsearchCatalogById(){return "SELECT eec.*,ea.username FROM es_esearch_catalog eec LEFT JOIN es_adminuser ea ON eec.staff_no=ea.userid WHERE eec.disabled=0 AND eec.source_from='" +ManagerUtils.getSourceFrom()+"'";}
	
	public String getSpecvaluesBySearchCodeAndKey(){return "select * from es_esearch_specvalues where 1=1";}
	
	public String getSpecBySearchCode(){return "select * from es_esearch_spec where 1=1";}
	
	public String getSpecvaluesRulesBySearchId(){return "select * from es_esearch_specvalues_rules where search_id=? order by seq";}
	
	public String getDelelteSpecvaluesBySearchAndKey(){return "delete from es_esearch_specvalues where search_code=? and match_content=?";}
	
	public String getSeq(){return "select max(t.seq) from es_esearch_expinst t where t.rel_obj_id=? and t.search_id=? and t.key_id=?";}

	public String getCatalogId(){return "select t.catalog_id,eec.catalog_name from es_esearch_specvalues_relc t left join es_esearch_catalog eec on t.catalog_id = eec.catalog_id where t.source_from='"+ManagerUtils.getSourceFrom()+"' AND t.key_id=?";}
	
	public String getExpInst(){
		StringBuffer sb = new StringBuffer();
		/*sb.append("select a.excp_inst_id,a.rel_obj_id,a.rel_obj_type,a.seq,a.excp_create_time,a.log_id,a.search_id,a.key_id,a.catalog_id as catalog_id, ");
		sb.append("a.record_status,a.deal_result,a.deal_staff_no,a.deal_time,a.rel_obj_create_time,a.out_tid,a.excp_update_time,");
		sb.append("b.tache_code,b.lan_id,b.order_mode,b.order_from,b.staff_code,b.dispatch_time,");
		sb.append("es.search_code,es.search_name,es.search_field,esp.match_content,esp.warming_flag,");
		sb.append("esp.warming_limit,esp.alarm_flag,esp.alarm_limit,esp.alarm_time_interval,ec.solution_id,ees.solution_type,ees.solution_value,ees.is_batch_process,");
		sb.append("round(-(a.excp_create_time-(trunc(sysdate,'HH24')+esp.warming_limit/24))*24,1) as warming_time ");
		sb.append("from es_esearch_expinst a left join es_esearch_expinst_ext b on a.excp_inst_id = b.excp_inst_id ");
		sb.append("left join es_order eo on a.rel_obj_id = eo.order_id ");
		sb.append("left join es_order_ext eoe on eo.order_id = eoe.order_id ");
		sb.append("left join es_esearch_spec es on a.search_id = es.search_id ");
		sb.append("left join es_esearch_specvalues esp on a.key_id = esp.key_id ");
		sb.append("left join es_esearch_catalog ec on a.catalog_id = ec.catalog_id ");
		sb.append("left join es_esearch_expinst_solution ees on ec.solution_id = ees.solution_id ");
		sb.append("where a.source_from = '").append(ManagerUtils.getSourceFrom()).append("' ");*/	
		
		sb.append("select a.excp_inst_id,a.rel_obj_id,a.rel_obj_type,a.seq,a.excp_create_time,a.log_id,a.search_id,a.key_id,a.catalog_id as catalog_id, ");
		sb.append("a.record_status,a.deal_result,a.deal_staff_no,a.deal_time,a.rel_obj_create_time,a.out_tid,a.excp_update_time,");
		sb.append("b.tache_code,b.lan_id,b.order_mode,b.order_from,b.staff_code,b.dispatch_time,");
		sb.append("es.search_code,es.search_name,es.search_field,esp.match_content,esp.warming_flag,");
		sb.append("esp.warming_limit,esp.alarm_flag,esp.alarm_limit,esp.alarm_time_interval,ec.solution_id,ees.solution_type,ees.solution_value,ees.is_batch_process,");
		sb.append("round(-(a.excp_create_time-(trunc(sysdate,'HH24')+esp.warming_limit/24))*24,1) as warming_time ");
		sb.append("from (");
		sb.append("select MAX (a1.excp_inst_id) as excp_inst_id from es_esearch_expinst a1 where a1.excp_create_time > trunc (sysdate, 'DD') and a1.out_tid is not null group by a1.key_id, a1.rel_obj_id");
		sb.append(") a2 ");
		sb.append("left join es_esearch_expinst a on a.excp_inst_id = a2.excp_inst_id ");
		sb.append("left join es_esearch_expinst_ext b on a.excp_inst_id = b.excp_inst_id ");
		sb.append("left join es_order eo on a.rel_obj_id = eo.order_id ");
		sb.append("left join es_order_ext eoe on eo.order_id = eoe.order_id ");
		sb.append("left join es_esearch_spec es on a.search_id = es.search_id ");
		sb.append("left join es_esearch_specvalues esp on a.key_id = esp.key_id ");
		sb.append("left join es_esearch_catalog ec on a.catalog_id = ec.catalog_id ");
		sb.append("left join es_esearch_expinst_solution ees on ec.solution_id = ees.solution_id ");
		sb.append("where a.source_from = '").append(ManagerUtils.getSourceFrom()).append("' ");
		return sb.toString();
	}
	
	public String getExpInstHis(){//查询历史单
		StringBuffer sb = new StringBuffer();
		sb.append("select a.excp_inst_id,a.rel_obj_id,a.rel_obj_type,a.seq,a.excp_create_time,a.log_id,a.search_id,a.key_id,a.catalog_id as catalog_id, ");
		sb.append("a.record_status,a.deal_result,a.deal_staff_no,a.deal_time,a.rel_obj_create_time,a.out_tid,a.excp_update_time,");
		sb.append("b.tache_code,b.lan_id,b.order_mode,b.order_from,b.staff_code,b.dispatch_time,");
		sb.append("es.search_code,es.search_name,es.search_field,esp.match_content,esp.warming_flag,");
		sb.append("esp.warming_limit,esp.alarm_flag,esp.alarm_limit,esp.alarm_time_interval,ec.solution_id,ees.solution_type,ees.solution_value,ees.is_batch_process,");
		sb.append("round(-(a.excp_create_time-(trunc(sysdate,'HH24')+esp.warming_limit/24))*24,1) as warming_time ");
		sb.append("from (");
		sb.append("select MAX (a1.excp_inst_id) as excp_inst_id from es_esearch_expinst_his a1 where a1.excp_create_time > trunc (sysdate, 'DD') and a1.out_tid is not null group by a1.key_id, a1.rel_obj_id");
		sb.append(") a2 ");
		sb.append("left join es_esearch_expinst_his a on a.excp_inst_id = a2.excp_inst_id ");
		sb.append("left join es_esearch_expinst_ext_his b on a.excp_inst_id = b.excp_inst_id ");
		sb.append("left join es_order eo on a.rel_obj_id = eo.order_id ");
		sb.append("left join es_order_ext eoe on eo.order_id = eoe.order_id ");
		sb.append("left join es_esearch_spec es on a.search_id = es.search_id ");
		sb.append("left join es_esearch_specvalues esp on a.key_id = esp.key_id ");
		sb.append("left join es_esearch_catalog ec on a.catalog_id = ec.catalog_id ");
		sb.append("left join es_esearch_expinst_solution ees on ec.solution_id = ees.solution_id ");
		sb.append("where a.source_from = '").append(ManagerUtils.getSourceFrom()).append("' ");
		return sb.toString();
	}
	
	public String getOrderExpList(){
		StringBuffer sb = new StringBuffer();
		sb.append("select a.excp_inst_id,a.rel_obj_id,a.rel_obj_type,a.seq,a.excp_create_time,a.log_id,a.search_id,a.key_id,a.catalog_id as catalog_id, ");
		sb.append("a.record_status,a.deal_result,a.deal_staff_no,a.deal_time,a.rel_obj_create_time,a.out_tid,a.excp_update_time,");
		sb.append("b.tache_code,b.lan_id,b.order_mode,b.order_from,b.staff_code,b.dispatch_time,");
		sb.append("es.search_code,es.search_name,es.search_field,esp.match_content,esp.warming_flag,");
		sb.append("esp.warming_limit,esp.alarm_flag,esp.alarm_limit,esp.alarm_time_interval,");
		sb.append("round(-(a.excp_create_time-(trunc(sysdate,'HH24')+esp.warming_limit/24))*24,1) as warming_time ");
		sb.append("from es_esearch_expinst a left join es_esearch_expinst_ext b on a.excp_inst_id = b.excp_inst_id ");
		sb.append("left join es_order eo on a.rel_obj_id = eo.order_id ");
		sb.append("left join es_order_ext eoe on eo.order_id = eoe.order_id ");
		sb.append("left join es_esearch_spec es on a.search_id = es.search_id ");
		sb.append("left join es_esearch_specvalues esp on a.key_id = esp.key_id ");
		//sb.append("left join es_esearch_catalog ec on a.catalog_id = ec.catalog_id ");
		//sb.append("left join es_esearch_expinst_solution ees on ec.solution_id = ees.solution_id ");
		sb.append("where a.source_from = '").append(ManagerUtils.getSourceFrom()).append("' ");
		return sb.toString();
	}
	
	public String getExpInstByProcessModeA(){
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT ee.* FROM es_esearch_expinst ee ");
		sb.append("left join es_esearch_catalog ec on ee.catalog_id = ec.catalog_id ");
		sb.append("where ee.record_status = ? and ee.process_times < ? ");
		sb.append("and ec.process_mode = ? ");
		sb.append(" and ee.source_from = '").append(ManagerUtils.getSourceFrom()).append("' ");
		return sb.toString();
	}
	
	public String getExpInstDetailById(){
		StringBuffer sb = new StringBuffer();
		sb.append("select a.excp_inst_id,a.rel_obj_id,a.rel_obj_type,a.seq,a.excp_create_time,a.log_id,a.search_id,a.key_id,a.catalog_id, ec.catalog_name,  ");
		sb.append("a.record_status,a.deal_result,a.deal_staff_no,a.deal_time,a.rel_obj_create_time,a.out_tid, ");
		sb.append("b.tache_code,b.lan_id,b.order_mode,b.order_from,b.staff_code,b.dispatch_time, ");
		sb.append("es.search_code,es.search_name,esp.match_content,esp.warming_flag,");
		sb.append("esp.warming_limit,esp.alarm_flag,esp.alarm_limit,esp.alarm_time_interval,ec.solution_id,");
		sb.append("round(-(a.excp_create_time-(trunc(sysdate,'HH24')+esp.warming_limit/24))*24,1) as warming_time ");
		sb.append("from es_esearch_expinst a left join es_esearch_expinst_ext b on a.excp_inst_id = b.excp_inst_id ");
		sb.append("left join es_esearch_spec es on a.search_id = es.search_id ");
		sb.append("left join es_esearch_specvalues esp on a.key_id = esp.key_id ");
		sb.append("left join es_esearch_catalog ec on a.catalog_id = ec.catalog_id ");
		sb.append("where a.source_from = '").append(ManagerUtils.getSourceFrom()).append("' ");
		sb.append(" and a.excp_inst_id = ?");
		return sb.toString();
	}
	
	public String getExpInstById(){return "select * from es_esearch_expinst where excp_inst_id = ?";}
	
	public String getQueryEsearchCatalogBySpecv(){return "SELECT eec.* FROM es_esearch_catalog eec,es_esearch_specvalues_relc eesr WHERE eec.source_from='"+ManagerUtils.getSourceFrom()+"' AND eec.catalog_id=eesr.catalog_id AND eesr.key_id = ?";}

	public String getQueryEesByCatalog(){return "SELECT eees.* FROM es_esearch_catalog eec,es_esearch_expinst_solution eees WHERE eec.solution_id=eees.solution_id AND eec.catalog_id=? and eec.source_from='"+ManagerUtils.getSourceFrom()+"' and eees.source_from = '"+ManagerUtils.getSourceFrom()+"'";}

	public String getQueryEsearchExpInstSolution(){return "SELECT * FROM es_esearch_expinst_solution WHERE 1=1";}
	
	public String getDeleteEsearchCatalogSolution(){return "DELETE FROM es_esearch_expinst_solution WHERE solution_id=?";}
	
	public String getQueryKeyRulePage(){return "SELECT eesr.*,ees.search_name,ees.search_code FROM es_esearch_specvalues_rules eesr LEFT JOIN es_esearch_spec ees ON eesr.search_id=ees.search_id WHERE eesr.source_from='"+ManagerUtils.getSourceFrom()+"'";}
	
	public String getDeleteKeyRuleById(){return "DELETE FROM es_esearch_specvalues_rules WHERE key_rule_id=?";};
	
	public String getQueryEsearchSpecvaluesRelc(){return "SELECT * FROM es_esearch_specvalues_relc where key_id=?";}
	
	public String getDeleteEsearchSpecvaluesRelc(){return "delete from es_esearch_specvalues_relc where key_id=?";}
	
	public String getCountOrderExpGroupByKeyID(){return "select count(1) as counts,key_id from es_esearch_expinst group by key_id";}
	
	public String getSpecDefineList(){return "SELECT * FROM es_esearch_spec";}
	
	public String getFindKeyRuleByKeyId(){return "SELECT * FROM es_esearch_specvalues_rules eesr WHERE key_rule_id=?";}
	
	public String getInfLogsByDates(){return "select log_id,op_code,req_time,rsp_time,ep_address,state,state_msg,param_desc,result_desc,col1,col2,col3,col4,col5,source_from from inf_comm_client_calllog where 1=1";}
	
	public String getQuerySpecvaluesPage(){return "SELECT ees.*,eesr.catalog_id,eec.catalog_name FROM es_esearch_specvalues ees LEFT JOIN es_esearch_specvalues_relc eesr ON ees.key_id=eesr.key_id LEFT JOIN es_esearch_catalog eec ON eec.catalog_id=eesr.catalog_id WHERE ees.source_from='"+ManagerUtils.getSourceFrom()+"'";}
	
	public String getQuerySpecvaluesPageIsCatalog(){return "SELECT ees.*,eesr.catalog_id,eec.catalog_name FROM es_esearch_specvalues ees,es_esearch_specvalues_relc eesr,es_esearch_catalog eec WHERE eesr.source_from='"+ManagerUtils.getSourceFrom()+"' AND eec.source_from='"+ManagerUtils.getSourceFrom()+"' AND ees.source_from='"+ManagerUtils.getSourceFrom()+"' AND ees.key_id=eesr.key_id AND eesr.catalog_id=eec.catalog_id";}
	
	public String getQuerySpecvaluesPageNotCatalog(){return "SELECT ees.* FROM (SELECT tem.*,eesr.catalog_id,eec.catalog_name FROM es_esearch_specvalues tem LEFT JOIN es_esearch_specvalues_relc eesr ON tem.key_id=eesr.key_id LEFT JOIN es_esearch_catalog eec ON eesr.catalog_id=eec.catalog_id) ees WHERE ees.catalog_id is null AND ees.source_from ='"+ ManagerUtils.getSourceFrom() +"'";}
	
	public String getQuerySpec(){return "select e.* from es_esearch_spec e where e.flag = '0'";};
	
	public String getAddProcessTimesByInstId(){return "update es_esearch_expinst set process_times = (process_times+1) where excp_inst_id=?";};
	
	public String getFindSpecvalues(){return "SELECT * FROM es_esearch_specvalues WHERE key_id=?";};
	
	public String getArchiveExpHis(){
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO es_esearch_expinst_his(excp_inst_id,rel_obj_id,rel_obj_type,seq,excp_create_time,log_id,search_id,key_id,catalog_id,record_status,deal_result,deal_staff_no,deal_time,rel_obj_create_time,source_from,process_times,out_tid,excp_update_time,is_visible) ");
		sql.append("SELECT excp_inst_id,rel_obj_id,rel_obj_type,seq,excp_create_time,log_id,search_id,key_id,catalog_id,record_status,deal_result,deal_staff_no,deal_time,rel_obj_create_time,source_from,process_times,out_tid,excp_update_time,is_visible FROM es_esearch_expinst WHERE excp_inst_id = ?");
		return sql.toString();
	};
	public String getArchiveExpExtHis(){return "INSERT INTO es_esearch_expinst_ext_his SELECT * FROM es_esearch_expinst_ext WHERE excp_inst_id = ?";};
	public String getDelExp(){return "DELETE FROM es_esearch_expinst WHERE excp_inst_id = ?";};
	public String getDelExpExt(){return "DELETE FROM es_esearch_expinst_ext WHERE excp_inst_id = ?";};

	public String getArchiveExpHisHis(){return "INSERT INTO es_esearch_expinst_his_his SELECT * FROM es_esearch_expinst_his WHERE excp_inst_id = ?";};
	public String getArchiveExpExtHisHis(){return "INSERT INTO es_esearch_expinst_ext_his_his SELECT * FROM es_esearch_expinst_ext_his WHERE excp_inst_id = ?";};
	public String getDelExpHis(){return "DELETE FROM es_esearch_expinst_his WHERE excp_inst_id = ?";};
	public String getDelExpExtHis(){return "DELETE FROM es_esearch_expinst_ext_his WHERE excp_inst_id = ?";};
	
	public String getAllExpInsts(){return "SELECT * FROM es_esearch_expinst WHERE record_status = ? ORDER BY excp_create_time asc";};
	
	public String getAllExpHisInsts(){return "SELECT * FROM es_esearch_expinst_his WHERE excp_create_time <= "+DBTUtil.to_sql_date("?", 2)+"  ORDER BY excp_create_time asc";};

	public String getFindEsearchSpec(){return "SELECT * FROM es_esearch_spec WHERE search_code = ? AND search_id = ?";};

	public String getDeleteSpecvaluesRelcByKeyId(){return "DELETE FROM es_esearch_specvalues_relc WHERE key_id = ?";};
}
