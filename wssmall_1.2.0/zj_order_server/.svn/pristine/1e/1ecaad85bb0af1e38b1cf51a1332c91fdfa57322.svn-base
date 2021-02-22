package com.ztesoft.net.service.impl;

import java.util.List;
import java.util.Map;

import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.service.IBusinessRefreshManager;

public class BusinessRefreshManager extends BaseSupport implements IBusinessRefreshManager{
	
	
	@Override
	public List<Map> getAllGoodsId() {
		String sql = "select t.goods_id from es_goods t ";
		List list = this.baseDaoSupport.queryForList(sql);
		return list;
	}

}
