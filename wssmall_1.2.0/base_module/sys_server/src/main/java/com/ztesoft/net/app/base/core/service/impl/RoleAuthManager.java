package com.ztesoft.net.app.base.core.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ztesoft.ibss.common.util.Const;
import com.ztesoft.net.app.base.core.model.RoleData;
import com.ztesoft.net.app.base.core.service.IRoleAuthManager;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import commons.CommonTools;

public class RoleAuthManager extends BaseSupport<RoleData> implements IRoleAuthManager{

	@Override
	public RoleData getRoleData(HashMap param) {
		String sql = "select d.*, r.rolename, r.rolememo"+
					"  from es_role_data d"+
					"  left join es_role r on r.roleid = d.role_code"+
					" where d.id =? and d.source_from=? ";
		List arr = new ArrayList();
		arr.add(Const.getStrValue(param, "id"));
		arr.add(CommonTools.getSourceForm());
		String countSql = "select count(1) from ("+sql+")";
		return this.baseDaoSupport.queryForObject(sql, RoleData.class, arr.toArray());
	}

	@Override
	public List getOrdReceiveUser() {
		String sql = "select a.userid value,username||'（'||a.realname||'）' value_desc from es_adminuser a where a.usertype=3";
		return this.baseDaoSupport.queryForList(sql);
	}

	@Override
	public List getLockOrderUser() {
		String sql = "select a.userid value,username||'（'||a.realname||'）' value_desc from es_adminuser a where a.usertype=1";
		return this.baseDaoSupport.queryForList(sql);
	}
	
	@Override
	public List<Map> getDcSqlByDcName(String dcName) {
		List<Map> list = new ArrayList<Map>();
		StringBuilder sql = new StringBuilder("select t.dc_sql from es_dc_sql t where t.dc_name='"+dcName+"'");
		List<Map> dc_sql_list = baseDaoSupport.queryForList(sql.toString());
		if (dc_sql_list != null && dc_sql_list.size() > 0) {
			String dc_sql = (dc_sql_list.get(0).get("dc_sql")).toString();
			list = baseDaoSupport.queryForList(dc_sql);
		}
		return list;
	}
	
}