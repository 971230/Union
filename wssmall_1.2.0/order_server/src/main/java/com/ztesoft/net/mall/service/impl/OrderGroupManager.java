package com.ztesoft.net.mall.service.impl;

import java.util.List;

import org.drools.core.util.StringUtils;

import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.mall.service.IOrderGroupManager;
import com.ztesoft.net.model.OrderGroup;
import com.ztesoft.net.sqls.SF;

public class OrderGroupManager extends BaseSupport implements IOrderGroupManager {

	@Override
	public List<OrderGroup> listGroupByGroupType(String group_type) {
		String sql = SF.orderSql("OrderGroupListByGroupType");
		if(!StringUtils.isEmpty(group_type)){
			sql += " and t.group_type='"+group_type+"' ";
		}
		return this.baseDaoSupport.queryForList(sql, OrderGroup.class);
	}

	@Override
	public Page queryGroupUsers(String group_id, String user_name,int pageNo,int pageSize) {
		if(user_name==null)user_name="";
		user_name = "'%"+user_name.toUpperCase()+"%'";
		//String sql = SF.orderSql("QUERY_ORDER_GROUP_USERS");
		String sql = "select * from (select t.* from es_adminuser t,es_order_group_role_rel ogr where upper(t.username) like "+user_name+" and t.userid=ogr.userid and ogr.group_id=? and t.source_from=ogr.source_from and t.source_from=? "+
				" union "+
				" select t.* from es_adminuser t,es_user_role ur,es_order_group_role_rel ogr where t.userid=ur.userid and ur.roleid=ogr.role_id and upper(t.username) like "+user_name+" and ogr.group_id=? "+
				" and t.source_from=? and t.source_from=ur.source_from and ur.source_from=ogr.source_from and t.source_from=ogr.source_from) ee";
		
		String countSQL = "select count(*) from ("+sql+")";
		String source_from = ManagerUtils.getSourceFrom();
		return this.baseDaoSupport.queryForCPage(sql, pageNo, pageSize, AdminUser.class, countSQL,group_id,source_from,group_id,source_from);
	}

}
