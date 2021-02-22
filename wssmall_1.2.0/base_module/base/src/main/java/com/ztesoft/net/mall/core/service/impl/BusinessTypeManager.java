package com.ztesoft.net.mall.core.service.impl;


import java.util.List;

import com.ztesoft.ibss.common.util.Const;
import com.ztesoft.net.cache.common.INetCache;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.mall.core.model.BusinessType;
import com.ztesoft.net.mall.core.service.IBusinessTypeManager;

/***
 * 业务类型
 * @author huang.xiaoming
 *
 */
public class BusinessTypeManager extends BaseSupport<BusinessType> implements IBusinessTypeManager {
	static INetCache cache;
	static{
		cache = com.ztesoft.net.cache.common.CacheFactory.getCacheByType("");
	}
	public static int NAMESPACE = Const.CACHE_SPACE_ORDERSTD;
	static int time = Const.CACHE_TIME_ORDERSTD;//缺省缓存5天,memcache最大有效期是30天

	@Override
	public BusinessType getBusiType(String goods_type_id,String order_from) {
		BusinessType type = null;
		String preKey = goods_type_id + order_from + "_ES_BUSINESS_TYPE";
		String sql = "SELECT BUSI_TYPE_ID,OPER_MODE,IS_AOP,FLOW_ID FROM ES_BUSINESS_TYPE WHERE GOODS_TYPE_ID=? AND ORDER_FROM=?";
		type = (BusinessType)cache.get(NAMESPACE,preKey);
		if(null != type) {
			return type;
		} else {
			List<BusinessType> typeList = baseDaoSupport.queryForList(sql, BusinessType.class, goods_type_id,order_from);
			if(null != typeList && typeList.size()>0) {
				type = typeList.get(0);
				cache.set(NAMESPACE, preKey, type, time);
			}
		}
		return type;
	}

	@Override
	public void refreshCache() {
		String sql = "SELECT BUSI_TYPE_ID,OPER_MODE,IS_AOP,FLOW_ID FROM ES_BUSINESS_TYPE";
		List<BusinessType> list = baseDaoSupport.queryForList(sql, BusinessType.class);
		for(BusinessType businessType : list) {
			String preKey = businessType.getGoods_type_id()+businessType.getOrder_from()+ "_ES_BUSINESS_TYPE";
			cache.set(NAMESPACE, preKey, businessType, time);
		}
			
	}
	
}
