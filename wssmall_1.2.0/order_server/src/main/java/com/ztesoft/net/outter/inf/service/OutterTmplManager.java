package com.ztesoft.net.outter.inf.service;

import java.util.List;

import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.outter.inf.iservice.IOutterTmplManager;
import com.ztesoft.net.outter.inf.model.OuterError;
import com.ztesoft.net.outter.inf.model.PublicConst;
import com.ztesoft.net.sqls.SF;

public class OutterTmplManager extends BaseSupport implements IOutterTmplManager {

	/*@Override
	public OutterTmpl getTmplByOrderFrom(String order_from) {
		String sql = SF.orderSql("GET_OUTTER_TMPL_BY_ORDER_FROM");
		return (OutterTmpl) this.baseDaoSupport.queryForObject(sql, OutterTmpl.class, order_from,ManagerUtils.getSourceFrom());
	}*/

	/*@Override
	public void updateTmplExecuteInfo(String order_from, int sync_num,
			int status, int execute_min,String data_end_time,String error_msg) {
		String sql = "update es_outer_execute_tmpl t set t.last_status=?,t.last_execute="+DBTUtil.to_sql_date("?", 2)+",t.next_execute="+DBTUtil.to_sql_date("?", 2)+",t.sync_num=? ";
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String last_execute = df.format(new Date());
		String next_execute = df.format(new Date(System.currentTimeMillis()+execute_min*1000*60));
		try {
			next_execute = df.format(new Date(df.parse(DBTUtil.getDBCurrentTime()).getTime()+execute_min*1000*60));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		List param = new ArrayList();
		param.add(status);
		param.add(last_execute);
		param.add(next_execute);
		param.add(sync_num);
		if(!StringUtils.isEmpty(data_end_time)){
			sql += ",t.data_end_time="+DBTUtil.to_sql_date("?", 2);
			param.add(data_end_time);
		}
		if(!StringUtils.isEmpty(error_msg)){
			sql += " ,t.error_msg=?";
			param.add(error_msg);
		}
		sql += " where t.order_from=? and t.source_from=?";
		param.add(order_from);
		param.add(ManagerUtils.getSourceFrom());
		this.baseDaoSupport.execute(sql, param.toArray());
	}*/

	/*@Override
	public List<OutterTmpl> listOuterTmpl(int status) {
		String sql = SF.orderSql("LIST_OUTER_EXECUTE_TMPL");
		if(status!=-1){
			sql += " and t.status="+status;
		}
		sql += " and t.source_from=?";
		return this.baseDaoSupport.queryForList(sql, OutterTmpl.class, ManagerUtils.getSourceFrom());
	}*/

	@Override
	public PublicConst queryPublicConst(String order_from, String object_type,
			String outer_code) {
		String sql = SF.orderSql("QUERY_PUBLIC_CONST_BY_SYS");
		List<PublicConst> list = this.baseDaoSupport.queryForList(sql, PublicConst.class, order_from,outer_code,object_type,ManagerUtils.getSourceFrom());
		return (list==null||list.size()==0)?null:list.get(0);
	}

	@Override
	public void updateRunStatus(String run_status, String tmpl_id) {
		String sql = SF.orderSql("UPDATE_EXECUTE_RUN_STATUS");
		this.baseDaoSupport.execute(sql, run_status,tmpl_id);
	}

	@Override
	public void insertOuterError(OuterError nogoods) {
		this.baseDaoSupport.insert("ES_OUTER_ERRORS", nogoods);
	}
	
	@Override
	public List<OuterError> listNotSyncError(String order_from){
		String sql = "select t.* from es_outer_errors t where t.deal_flag='0' and t.error_type='orderinfoerror' and t.deal_num<4 and t.order_from=?";
		return this.baseDaoSupport.queryForList(sql, OuterError.class,order_from);
	}
	@Override
	public void updateErrorDelFlag(String order_from,String tid){
		String sql = "update es_outer_errors t set t.deal_flag='1' where t.order_from=? and t.tid=?";
		this.baseDaoSupport.execute(sql, order_from,tid);
	}
}
