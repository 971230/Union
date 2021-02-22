package com.ztesoft.net.mall.core.service;

import java.util.List;

import com.ztesoft.net.mall.core.model.GoodsOrg;

public interface ISysCacheManager {
	
	/**
	 * 查询所有的发布组织
	 * @return
	 */
	public List<GoodsOrg> listGoodsOrg();
	
	/**
	 * 通过party_id查找发布组织
	 * @return
	 */
	public GoodsOrg getGoodsOrgByPartyId(String party_id);
}
