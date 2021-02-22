package com.ztesoft.net.mall.core.service.impl;

import java.util.List;

import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.mall.core.model.GoodsOrg;
import com.ztesoft.net.mall.core.service.ISysCacheManager;

public class SysCacheManager extends BaseSupport  implements ISysCacheManager {

	@Override
	public List<GoodsOrg> listGoodsOrg() {
		String sql = "select * from es_goods_org where 1=1 ";
		return this.baseDaoSupport.queryForList(sql,GoodsOrg.class);
	}

	@Override
	public GoodsOrg getGoodsOrgByPartyId(String party_id) {
		GoodsOrg goodsOrg = new GoodsOrg();	
		String sql = "select * from es_goods_org where 1=1 and party_id=?";
		List<GoodsOrg> list = this.baseDaoSupport.queryForList(sql,GoodsOrg.class,party_id);
		if(list.size()>0){
			goodsOrg = list.get(0);
		}
		return goodsOrg;
	}
	

}
