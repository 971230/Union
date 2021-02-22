package com.ztesoft.net.mall.core.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.service.IRuleObjectManager;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

public class RuleObjectManager extends BaseSupport implements IRuleObjectManager{

	@Override
	public List getObjAttr(String obj_id) {
		
		String sql = " select a.obj_id, b.attr_id, b.attr_code, b.attr_name, b.create_date from es_rule_obj a, es_rule_obj_attr b where a.obj_id = b.obj_id " +
					 " and a.status_cd='00A' and b.status_cd='00A' and a.obj_id=? ";
		
		return this.daoSupport.queryForList(sql, obj_id);
	}

	@Override
	public Page searchGoods(String name, int pageIndex, int pageSize) {
		
		String sql = " select g.goods_id, g.type_id, g.name, g.sn, g.price, g.store, g.market_enable, t.name as type_name," +
					 " t.have_stock as have_stock, t.have_price as have_price," +
					 " g.creator_user  apply_userid, to_char(g.create_time, 'YYYY-MM-DD HH24:MI:SS') create_time,  u.username apply_username, g.audit_state "+
					 " from es_goods g " +
					 " left join es_goods_type t on g.type_id=t.type_id " +
					 " inner join es_adminuser u on g.creator_user = u.userid " +
					 " where g.disabled='0' and g.audit_state='00A' and g.market_enable=1 and g.source_from=?" ;
		if(StringUtils.isNotEmpty(name)){
			sql += " and g.name like '%"+name.trim()+"%' ";
		}
		return this.daoSupport.queryForPage(sql, pageIndex, pageSize, ManagerUtils.getSourceFrom());
	}

	@Override
	public Page searchPartner(String partner_name, int pageInx, int pageSize) {

		String sql = " select a.userid, a.username, a.realname, b.region_name lan_name, c.region_name city_name from es_adminuser a" +
					 " left outer join es_common_region b on a.lan_id = b.region_id " +
					 " left outer join es_common_region c on a.city_id = c.region_id " +
					 " where a.founder=3 and a.state='1' and a.source_from=?";
		
		if(StringUtils.isNotEmpty(partner_name)){
			sql += " and a.realname like '%"+partner_name.trim()+"%' ";
		}
		return this.daoSupport.queryForPage(sql, pageInx, pageSize, ManagerUtils.getSourceFrom());
	}
	
	
}
