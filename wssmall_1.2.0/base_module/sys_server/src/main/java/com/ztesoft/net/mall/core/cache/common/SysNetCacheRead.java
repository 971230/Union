package com.ztesoft.net.mall.core.cache.common;

import com.ztesoft.net.mall.core.model.GoodsOrg;
import com.ztesoft.net.mall.core.service.ISysCacheManager;

import javax.annotation.Resource;

public class SysNetCacheRead extends SysNetCache{
	
	@Resource
	protected ISysCacheManager  sysCacheManager;
	
	/**
	 * 读取发布组织缓存
	 * @param party_id
	 * @return
	 */
	public GoodsOrg getCachesGoodsOrg(String party_id){
		Object obj = this.get(this.CACHE_GOODS_ORG+party_id);
		if(null != obj){//如果缓存存在
            GoodsOrg goodsOrg=(GoodsOrg)obj;
			return goodsOrg;
		}else{//如果缓存不存在,则数据库中查询放到缓存
			GoodsOrg goodsOrg = sysCacheManager.getGoodsOrgByPartyId(party_id);
			this.set(this.CACHE_GOODS_ORG + party_id, goodsOrg ,60*24*60);
			return goodsOrg;
		}
	}

}
