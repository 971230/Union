package com.ztesoft.net.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import util.EssOperatorInfoUtil;

import com.ztesoft.ibss.common.util.Const;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.service.IGonghaoBindManager;
import com.ztesoft.net.sqls.SF;

public class GonghaoBindManager extends BaseSupport implements IGonghaoBindManager{
	
	
	@Override
	public Page searchAdminUser(Map params, int pageNo, int pageSize) {
		
		String sql = "";
		List pList = new ArrayList();
		StringBuilder where = new StringBuilder();
		if(!ManagerUtils.isAdminUser()){
			String userid = ManagerUtils.getUserId();
			where.append(" and userid like ? ");
			pList.add(userid);
		}
		sql=SF.orderSql("ES_ADMINUSER_LIST");
		
		where.append(" order by username desc ");
		Page page = this.baseDaoSupport.queryForPage(sql+where.toString(), pageNo, pageSize, pList.toArray());
		return page;		
	}
	
	@Override
	public Page searchOperator_Rel(Map params, int pageNo, int pageSize) {
		String username = Const.getStrValue(params, "username");
		
		String sql = "";
		List pList = new ArrayList();
		StringBuilder where = new StringBuilder();
		
		if(username != null && !username.trim().equals("")){
			where.append(" and order_gonghao like (select userid from es_adminuser where username like ?) ");
			pList.add(username);
		}
		
		sql=SF.orderSql("ES_OPERATOR_REL_EXT_LIST");
		
		where.append(" order by order_gonghao desc ");
		Page page = this.baseDaoSupport.queryForPage(sql+where.toString(), pageNo, pageSize, pList.toArray());
		return page;		
	}
	
	@Override
	public void do_binding(Map param){
		String order_gonghao =  Const.getStrValue(param,"order_gonghao");
		String ess_emp_id =  Const.getStrValue(param,"ess_emp_id");
		String province = Const.getStrValue(param,"province");
		String city = Const.getStrValue(param,"city");
		
		String sql = SF.orderSql("ES_OPERATOR_REL_EXT_INSERT");
		this.baseDaoSupport.execute(sql, order_gonghao, ess_emp_id, province, city);
		EssOperatorInfoUtil.refreshorderOperIdInfoCache(order_gonghao);
			
	}
	
	@Override
	public void unbinding_gonghao(Map param) {
		String order_gonghao =  Const.getStrValue(param,"order_gonghao");
		String city =  Const.getStrValue(param,"city");
		String sql = SF.orderSql("UNBINDING_GONGHAO");
		this.baseDaoSupport.execute(sql, order_gonghao,city);
	}

	@Override
	public String searchUserId(Map params) {
		String username_1 = Const.getStrValue(params,"username");
		String sql = SF.orderSql("ADMINUSER_USERID");
		String userid = this.baseDaoSupport.queryForString(sql, username_1);
		return userid;
	}
	

	@Override
	public Page searchUser(Map params, int pageNo, int pageSize) {
		String username_search = Const.getStrValue(params, "username_search");
		String realname = Const.getStrValue(params, "realname");
		String phone_num = Const.getStrValue(params, "phone_num");
		
		String sql = "";
		List pList = new ArrayList();
		StringBuilder where = new StringBuilder();
		sql = SF.orderSql("ES_ADMINUSER_LIST");
		if(username_search != null && !username_search.trim().equals("")){
			where.append(" and username like ? " );
			pList.add(username_search);
		}
		if(realname != null && !realname.trim().equals("")){
			where.append(" and realname like ? ");
			pList.add(realname);
		}
		if(phone_num != null && !phone_num.trim().equals("")){
			where.append(" and phone_num like ? ");
			pList.add(phone_num);
		}
		where.append("order by username desc ");
		Page page = this.baseDaoSupport.queryForPage(sql+where.toString(), pageNo, pageSize, pList.toArray());
		
		return page;
	}

	@Override
	public boolean validate_order_gonghao_city(Map params) {
		String order_gonghao = Const.getStrValue(params, "order_gonghao");
		String city = Const.getStrValue(params, "city");
		String sql = SF.orderSql("VALIDATE_ORDER_GONGHAO_CITY");
		List validate_List = this.baseDaoSupport.queryForList(sql, order_gonghao);
		
		for(int i=0; i<validate_List.size(); i++){
			Map city_map = (Map) validate_List.get(i);
			if(city.equals(city_map.get("city"))){
				return true;
			}
		}
		return false;
	}

	@Override
	public void haha() {
		logger.info("123");
	}
	
	

}
