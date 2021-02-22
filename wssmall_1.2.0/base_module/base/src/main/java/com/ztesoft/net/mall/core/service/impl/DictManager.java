package com.ztesoft.net.mall.core.service.impl;

import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.service.IDictManager;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 订单静态数据装载
 * 
 * @author wui
 */

public class DictManager extends BaseSupport implements IDictManager {

	@Override
	@SuppressWarnings("unchecked")
	public Map loadData(String attr_code) {
		String sql = "select * from dc_sql  where dc_name=? "+ManagerUtils.apSFromCond(""); //add by wui
		if(!(sql.indexOf("source_from")>-1))
			sql = "select * from dc_sql  where dc_name=?  and source_from is not null";
		List<Map<String, String>> dcList = this.baseDaoSupport.queryForList(
				sql, attr_code);
		String dc_sql = (String) ((Map) dcList.get(0)).get("dc_sql");
		String dc_name = (String) ((Map) dcList.get(0)).get("dc_name");
		List staticDatas = this.baseDaoSupport.queryForList(dc_sql);
		
		Map dataMap = new HashMap();
		dataMap.put(dc_name, staticDatas);
		return dataMap;
	}

	@Override
	public Map loadAllData() {
		String sql = "select * from dc_sql "
				+ " where source_from ='" + ManagerUtils.getSourceFrom() + "'"
				+ " or source_from = '-1'";
		List<Map<String, String>> dcList = this.baseDaoSupport.queryForList(sql);
		Map dataMap = new HashMap();
		String dc_sql =null; 
		List staticDatas =null; 
		for(int i=0;i<dcList.size();i++){
			dc_sql=(String) ((Map) dcList.get(i)).get("dc_sql");
			String attrCode = (String) ((Map) dcList.get(i)).get("dc_name");
			try{
				staticDatas=this.baseDaoSupport.queryForList(dc_sql);
			}catch(Exception e){
				e.printStackTrace();
			}
			dataMap.put(attrCode,staticDatas);
		}
		return dataMap;
	}
	
	
	@Override
	@SuppressWarnings("unchecked")
	public Map loadUserRole(String attr_code) {
		String sql = "SELECT btn_role_id from es_role_button WHERE btn_role_code=? AND disabled=0 AND source_from = '"+ManagerUtils.getSourceFrom()+"'";
		Map staticDatas = this.baseDaoSupport.queryForMap(sql,attr_code);
		
		return staticDatas;
	}
	
	
	@Override
	public List<Map> listRoleBtn(){
		String sql = "SELECT * FROM es_role_button WHERE disabled='0' AND source_from = '"+ManagerUtils.getSourceFrom()+"'";
		return this.baseDaoSupport.queryForList(sql);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List loadUserRoleList(String role_id) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM es_auth_action");
		sql.append(" WHERE actid IN");
		sql.append(" (SELECT authid FROM es_role_auth WHERE roleid IN ("+role_id+"");
		sql.append(" )) AND type = '"+Consts.AUTH_BTN+"' AND source_from = '"+ManagerUtils.getSourceFrom()+"'");
		
		return this.baseDaoSupport.queryForList(sql.toString());
	}
	
	
	@Override
	public List<Map> qryUserRole(String user_id){
		
		String sql = "SELECT * FROM es_user_role WHERE userid = '"+user_id+"' AND source_from = '"+ManagerUtils.getSourceFrom()+"'";
		return this.baseDaoSupport.queryForList(sql);
	}

}
