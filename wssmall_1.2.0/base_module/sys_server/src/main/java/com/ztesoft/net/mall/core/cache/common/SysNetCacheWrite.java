package com.ztesoft.net.mall.core.cache.common;

import java.util.List;

import javax.annotation.Resource;
import com.ztesoft.net.mall.core.model.GoodsOrg;
import com.ztesoft.net.mall.core.service.ISysCacheManager;
public class SysNetCacheWrite extends SysNetCache{
	

	@Resource
	protected ISysCacheManager  sysCacheManager;
	
	/**
	 * @title:发布组织缓存
	 * @desc:缓存发布组织的基本信息
	 */
	public void loadAllGoodsOrg(){
		List<GoodsOrg> list = sysCacheManager.listGoodsOrg();
		for(GoodsOrg org : list){
			this.set(this.CACHE_GOODS_ORG + org.getParty_id(), org,60*24*60);
		}
	}
}
