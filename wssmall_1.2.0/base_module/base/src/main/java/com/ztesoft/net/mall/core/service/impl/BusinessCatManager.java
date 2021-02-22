package com.ztesoft.net.mall.core.service.impl;


import java.util.List;

import com.ztesoft.form.util.StringUtil;
import com.ztesoft.ibss.common.util.Const;
import com.ztesoft.net.cache.common.INetCache;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.mall.core.model.BusinessCatId;
import com.ztesoft.net.mall.core.service.IBusinessCatManager;

/***
 * 业务类型
 * @author huang.xiaoming
 *
 */
public class BusinessCatManager extends BaseSupport<BusinessCatId> implements IBusinessCatManager {
	static INetCache cache;
	static{
		cache = com.ztesoft.net.cache.common.CacheFactory.getCacheByType("");
	}
	public static int NAMESPACE = Const.CACHE_SPACE_ORDERSTD;
	static int time = Const.CACHE_TIME_ORDERSTD;//缺省缓存5天,memcache最大有效期是30天
	
	//若本次不上线 代码回退
  	@Override
	public BusinessCatId getBusiCat(String goods_cat_id,String order_from,String optType) {
	    BusinessCatId type = null;
	    String preKey="";
	    String sql = "SELECT BUSI_CAT_ID,OPER_MODE,IS_AOP,FLOW_ID,match_type FROM ES_BUSINESS_CAT WHERE GOODS_CAT_ID=? AND ORDER_FROM=?";
	    if(!StringUtil.isEmpty(optType)){
	       preKey = goods_cat_id + order_from +optType+"_ES_BUSINESS_CAT";
	       sql += " AND MATCH_TYPE=? ";
	    }else{
	       preKey = goods_cat_id + order_from + "_ES_BUSINESS_CAT";
	    }
		type = (BusinessCatId)cache.get(NAMESPACE,preKey);
		if(null != type) {
			return type;
		} else {
		    if(StringUtil.isEmpty(optType)){
		        List<BusinessCatId> typeList = baseDaoSupport.queryForList(sql, BusinessCatId.class, goods_cat_id,order_from);
		        if(null != typeList && typeList.size()>0) {
		            type = typeList.get(0);
		            cache.set(NAMESPACE, preKey, type, time);
		        }
		    }else{
		        List<BusinessCatId> typeList = baseDaoSupport.queryForList(sql, BusinessCatId.class, goods_cat_id,order_from,optType);
                if(null != typeList && typeList.size()>0) {
                    type = typeList.get(0);
                    cache.set(NAMESPACE, preKey, type, time);
                } 
		    }
		}
		return type;
	}

	@Override
	public void refreshCache() {
		String sql = "SELECT BUSI_CAT_ID,OPER_MODE,IS_AOP,FLOW_ID,match_type FROM ES_BUSINESS_CAT";
		List<BusinessCatId> list = baseDaoSupport.queryForList(sql, BusinessCatId.class);
		for(BusinessCatId businessCat : list) {
		    String preKey="";
		    if(StringUtil.isEmpty(businessCat.getMatch_type())){
		        preKey = businessCat.getGoods_cat_id()+businessCat.getOrder_from()+ "_ES_BUSINESS_CAT";
		    }else{
	            preKey = businessCat.getGoods_cat_id()+businessCat.getOrder_from()+businessCat.getMatch_type()+"_ES_BUSINESS_CAT";
		    }
			cache.set(NAMESPACE, preKey, businessCat, time);
		}
			
	}
	
}
