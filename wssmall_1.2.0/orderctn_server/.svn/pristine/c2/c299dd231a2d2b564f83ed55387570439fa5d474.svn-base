package com.ztesoft.net.mall.outter.inf.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.drools.core.util.StringUtils;

import com.powerise.ibss.util.DBTUtil;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.mall.outter.inf.iservice.IOutterECSTmplCtnManager;
import com.ztesoft.net.mall.outter.inf.model.OutterTmpl;
import com.ztesoft.net.mall.outter.inf.model.PublicConst;
import com.ztesoft.net.outter.inf.model.OuterError;

public class OutterECSTmplCtnManager extends BaseSupport implements IOutterECSTmplCtnManager {

	@Override
	public OutterTmpl getTmplByOrderFrom(String order_from) {
		//String sql = SF.orderSql("GET_OUTTER_TMPL_BY_ORDER_FROM");
		String sql = "select * from es_outer_execute_tmpl_ctn t where t.order_from=? and t.source_from=?";
		return (OutterTmpl) this.baseDaoSupport.queryForObject(sql, OutterTmpl.class, order_from,ManagerUtils.getSourceFrom());
	}

	@Override
	public void updateTmplExecuteInfo(String exe_id, int sync_num,
			int status, int execute_min,String data_end_time,String error_msg) {
		String sql = "update es_outer_execute_tmpl_ctn t set t.last_status=?,t.last_execute="+DBTUtil.to_sql_date("?", 2)+",t.next_execute="+DBTUtil.to_sql_date("?", 2)+",t.sync_num=? ";
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
		sql += " where t.tmpl_id=? and t.source_from=?";
		param.add(exe_id);
		param.add(ManagerUtils.getSourceFrom());
		this.baseDaoSupport.execute(sql, param.toArray());
	}

	@Override
	public List<OutterTmpl> listOuterTmpl(int status) {
		//String sql = SF.orderSql("LIST_OUTER_EXECUTE_TMPL");
		String sql = "select * from es_outer_execute_tmpl_ctn t where t.run_status='00A' and t.next_execute<="+DBTUtil.current();
		if(status!=-1){
			sql += " and t.status="+status;
		}
		sql += " and t.source_from=?";
		String source_from = ManagerUtils.getSourceFrom();
		return this.baseDaoSupport.queryForList(sql, OutterTmpl.class, source_from);
	}

	@Override
	public PublicConst queryPublicConst(String order_from, String object_type,
			String outer_code) {
		//String sql = SF.orderSql("QUERY_PUBLIC_CONST_BY_SYS");
		String sql = "select t.* from ES_PUBLIC_CONSTS t where t.sys_from=? and t.outer_code=? and t.object_type=? and t.source_from=?";
		List<PublicConst> list = this.baseDaoSupport.queryForList(sql, PublicConst.class, order_from,outer_code,object_type,ManagerUtils.getSourceFrom());
		return (list==null||list.size()==0)?null:list.get(0);
	}

	@Override
	public void updateRunStatus(String run_status, String tmpl_id) {
		//String sql = SF.orderSql("UPDATE_EXECUTE_RUN_STATUS");
		String sql = "update es_outer_execute_tmpl_ctn t set t.run_status=? where t.tmpl_id=?";
		this.baseDaoSupport.execute(sql, run_status,tmpl_id);
	}
	
	@Override
	public void updateRunStatus(String run_status,String msg,int sync_num,String tmpl_id) {
		//String sql = SF.orderSql("UPDATE_EXECUTE_RUN_STATUS");
		String sql = "update es_outer_execute_tmpl_ctn t set t.run_status=?,t.error_msg=?,t.sync_num=? where t.tmpl_id=?";
		this.baseDaoSupport.execute(sql, run_status,msg,sync_num,tmpl_id);
	}

	@Override
	public void insertOuterError(OuterError nogoods) {
		this.baseDaoSupport.insert("ES_OUTER_ERRORS", nogoods);
	}
	
	@Override
	public List<Map> listNotSyncError(String order_from){
		//String sql = "select t.* from es_outer_errors t where t.deal_flag='0' and t.error_type='orderinfoerror' and t.deal_num<4 and t.order_from=?";
		String sql = "select ao.* from ( "+
				" select distinct t.source_from,t.tid,t.outer_pagkage_id,t.outer_sku_id from es_outer_errors t,es_goods_package p where t.outer_pagkage_id=p.p_code and t.outer_sku_id=p.sn and t.outer_pagkage_id is not null and t.outer_sku_id is not null and t.error_type='nogoods' and t.deal_flag='0' and t.order_from=? "+
				" union "+
				" select distinct t.source_from,t.tid,t.outer_pagkage_id,t.outer_sku_id from es_outer_errors t,es_goods_package p where t.outer_pagkage_id=p.p_code and t.outer_pagkage_id is not null and t.outer_sku_id is null and t.error_type='nogoods' and t.deal_flag='0' and t.order_from=? "+
				" union "+
				" select distinct t.source_from,t.tid,t.outer_pagkage_id,t.outer_sku_id from es_outer_errors t,es_goods_package p where t.outer_sku_id=p.sn and t.outer_pagkage_id is null and t.outer_sku_id is not null and t.error_type='nogoods' and t.deal_flag='0' and t.order_from=? "+
				" union "+
				" select distinct t.source_from,t.tid,t.outer_pagkage_id,t.outer_sku_id from es_outer_errors t where t.error_type<>'nogoods' and t.deal_flag='0' and t.order_from=?) ao where ao.source_from=? "+DBTUtil.andRownum("10");
		return this.baseDaoSupport.queryForList(sql,order_from,order_from,order_from,order_from,ManagerUtils.getSourceFrom());
	}
	@Override
	public void updateErrorDelFlag(String order_from,String tid,String deal_flag){
		String sql = "update es_outer_errors t set t.deal_flag=? where t.order_from=? and t.tid=?";
		this.baseDaoSupport.execute(sql, deal_flag,order_from,tid);
	}

	@Override
	public Map getPhoneInfo(String phoneNo) {
		String sql = "select t.* from es_no t where t.dn_no=?";
		List<Map> list = this.baseDaoSupport.queryForList(sql, phoneNo);
		return list.size()>0?list.get(0):null;
	}

	@Override
	public void updateOutterOrderId(String updateOutId,String outId) {
		String sql = "update es_order_outer t set t.old_sec_order_id=? where t.old_sec_order_id=?";
		List param = new ArrayList();
		param.add(updateOutId);
		param.add(outId);
		this.baseDaoSupport.execute(sql, param.toArray());
	}
}
