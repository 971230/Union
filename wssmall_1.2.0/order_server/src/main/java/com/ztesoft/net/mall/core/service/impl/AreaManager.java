package com.ztesoft.net.mall.core.service.impl;

import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.model.DlyArea;
import com.ztesoft.net.mall.core.service.IAreaManager;
import com.ztesoft.net.sqls.SF;

import java.util.List;


public class AreaManager extends BaseSupport<DlyArea> implements IAreaManager{
	
	@Override
	public void delete(String id) {
		if (id == null || id.equals(""))
			return;
		String sql = SF.orderSql("SERVICE_AREA_DELETE")+ " and area_id in (" + id + ")";
		this.baseDaoSupport.execute(sql);
	}

	
	@Override
	public List getAll() {
		String sql = SF.orderSql("SERVICE_AREA_SELECT")+" order by area_id desc";
		List list = this.baseDaoSupport.queryForList(sql);
		return list;
	}

	
	@Override
	public Page pageArea(String order, int page, int pageSize) {
		order = order == null ? " area_id desc" : order;
		String sql = SF.orderSql("SERVICE_AREA_SELECT");
		sql += " order by  " + order;
		Page webpage = this.baseDaoSupport.queryForPage(sql, page, pageSize);
		return webpage;
	}

	
	@Override
	public void saveAdd(String name) {
		DlyArea dlyArea = new DlyArea();
		dlyArea.setName(name);
		this.baseDaoSupport.insert("dly_area", dlyArea);
	}

	
	@Override
	public void saveEdit(Integer areaId, String name) {
		String sql = SF.orderSql("SERVICE_AREA_UPDATE");
		this.baseDaoSupport.execute(sql,name,areaId);
	}

	
	@Override
	public DlyArea getDlyAreaById(Integer areaId) {
		String sql  = SF.orderSql("SERVICE_AREA_SELECT")+" where area_id=?";
		DlyArea a =  this.baseDaoSupport.queryForObject(sql, DlyArea.class, areaId);
		return a;
	}
    
}
