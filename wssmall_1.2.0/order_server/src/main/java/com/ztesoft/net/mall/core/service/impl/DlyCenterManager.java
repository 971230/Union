package com.ztesoft.net.mall.core.service.impl;

import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.model.DlyCenter;
import com.ztesoft.net.mall.core.service.IDlyCenterManager;
import com.ztesoft.net.sqls.SF;

import java.util.List;

/**
 * 发货中心
 * @author lzf<br/>
 * 2010-4-30上午10:14:35<br/>
 * version 1.0
 */
public class DlyCenterManager extends BaseSupport<DlyCenter> implements IDlyCenterManager {

	
	@Override
	public void add(DlyCenter dlyCenter) {
		this.baseDaoSupport.insert("dly_center", dlyCenter);
	}

	
	@Override
	public void delete(String[] id) {
		if(id== null  || id.length==0  ) return ;
		String ids = StringUtil.arrayToString(id, ",");
		this.baseDaoSupport.execute(SF.orderSql("SERVICE_DLY_CENTER_DELETE") + " and dly_center_id in (" + ids + ")");

	}

	
	@Override
	public void edit(DlyCenter dlyCenter) {
		this.baseDaoSupport.update("dly_center", dlyCenter, "dly_center_id = " + dlyCenter.getDly_center_id());

	}

	
	@Override
	public List<DlyCenter> list() {
		return this.baseDaoSupport.queryForList(SF.orderSql("SERVICE_DLY_CENTER_SELECT"), DlyCenter.class);
	}

	
	@Override
	public DlyCenter get(String dlyCenterId) {
		return this.baseDaoSupport.queryForObject(SF.orderSql("SERVICE_DLY_CENTER_SELECT_BY_ID"), DlyCenter.class, dlyCenterId);
	}

}
