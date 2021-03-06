package com.ztesoft.net.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.powerise.ibss.util.DBTUtil;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.model.InfLog;
import com.ztesoft.net.service.IInfLogsManager;
import com.ztesoft.net.sqls.SF;

public class InfLogsManager extends BaseSupport implements IInfLogsManager{

	@Override
	public Page queryInfLogsByDates(int pageNo,int pageSize,Map<String, String> params) {
		String start_date = params.get("start_date");
		String end_date = params.get("end_date");
		String rel_obj_id = params.get("rel_obj_id");//内部订单号
		List sqlParams = new ArrayList();
		String sql = SF.orderExpSql("InfLogsByDates");
		if(!StringUtil.isEmpty(start_date)){
			sql += " and req_time>="+DBTUtil.to_sql_date("?", 2);
			sqlParams.add(start_date);
		}
		if(!StringUtil.isEmpty(end_date)){
			sql += " and req_time<="+DBTUtil.to_sql_date("?", 2);
			sqlParams.add(end_date);
		}
		if(null != rel_obj_id){
			sql += " and col3 = ?";
			sqlParams.add(rel_obj_id);
		}
		String countSql = "select count(1) from ("+sql+") cord";
		Page page  = this.baseDaoSupport.queryForCPage(sql, pageNo, pageSize, InfLog.class, countSql, sqlParams.toArray());
		return page;
	}
	
	@Override
	public InfLog get(String log_id){
		String sql = "select * from inf_comm_client_calllog where log_id = ?";
		return (InfLog) this.baseDaoSupport.queryForObject(sql, InfLog.class, log_id);
	}
	
}
