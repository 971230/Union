package com.ztesoft.net.mall.core.service.impl;

import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.model.Logi;
import com.ztesoft.net.mall.core.service.ILogiManager;
import com.ztesoft.net.sqls.SF;

import java.util.List;


public class LogiManager extends BaseSupport<Logi> implements ILogiManager{
	
	@Override
	public void delete(String id) {
		if (id == null || id.equals(""))
			return;
		String sql = SF.orderSql("SERVICE_LOGI_COMP_DELETE")+" and id in (" + id + ")";
		this.baseDaoSupport.execute(sql);
	}

	
	@Override
	public Logi getLogiById(String id) {
		String sql  = SF.orderSql("SERVICE_LOGI_COMP_DELETE_BY_ID");
		Logi a =  this.baseDaoSupport.queryForObject(sql, Logi.class, id);
		return a;
	}

	
	@Override
	public Page pageLogi(String order, Integer page, Integer pageSize) {
		order = order == null ? " id desc" : order;
		String sql = SF.orderSql("SERVICE_LOGI_COMP_SELECT");
		sql += " order by  " + order;
		Page webpage = this.baseDaoSupport.queryForPage(sql, page, pageSize);
		return webpage;
	}

	
	@Override
	public void saveAdd(String name) {
		Logi logi = new Logi();
		logi.setName(name);
		this.baseDaoSupport.insert("logi_company", logi);
		
	}

	
	@Override
	public void saveEdit(String id, String name) {
		Logi logi = new Logi();
		logi.setName(name);
		logi.setId(id);
		this.baseDaoSupport.update("logi_company", logi, "id="+id);
	}

	
	@Override
	public List list() {
		String sql = SF.orderSql("SERVICE_LOGI_COMP_SELECT");
		return this.baseDaoSupport.queryForList(sql);
	}

	
}
