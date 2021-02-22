package com.ztesoft.net.action;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.ztesoft.ibss.common.util.Const;
import com.ztesoft.net.cache.common.INetCache;
import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.mall.core.service.IAttrTypeRelaManager;
import com.ztesoft.net.mall.core.service.IBusinessCatManager;
import com.ztesoft.net.mall.core.service.IBusinessTypeManager;
import com.ztesoft.net.mall.core.service.IRuleTypeRelaManager;
import com.ztesoft.net.service.IBusinessRefreshManager;

import zte.net.ecsord.common.SpecConsts;

public class BusinessRefreshAction extends WWAction{
	private static final long serialVersionUID = 1L;
	@Autowired
	private IBusinessTypeManager businessTypeManager;
	@Autowired
    private IBusinessCatManager businessCatManager;
	@Autowired
	private IAttrTypeRelaManager attrTypeRelaManager;
	@Autowired
	private IRuleTypeRelaManager ruleTypeRelaManager;
	static INetCache cache;
	private final static int space = Const.CACHE_SPACE_ORDERSTD;
	private final static int EXPIRE_TIME = Const.CACHE_TIME_ORDERSTD;
	static{
		cache = (INetCache) com.ztesoft.net.cache.common.CacheFactory.getCacheByType("");
	}

	public static int NAMESPACE = Const.CACHE_SPACE_ORDERSTD;
	static int time = Const.CACHE_TIME_ORDERSTD;//缺省缓存5天,memcache最大有效期是30天

	/**
	 * 刷新业务类型相关表数据
	 * @return
	 */
	public String refreshBusinessCache() {
		try{
			businessTypeManager.refreshCache();
			attrTypeRelaManager.refreshCache();
			ruleTypeRelaManager.refreshCache();
			businessCatManager.refreshCache();
			json = "{status:0,msg:'刷新成功'}";
		}catch(Exception ex){
			json = "{status:1,msg:'刷新失败'}";
		}
		return JSON_MESSAGE;
	}
	/**
	 * 刷新入库中进行订单校验的缓存信息，包括商品信息，促销信息
	 */
	public String refreshValidateCache(){
		
		try{
			IBusinessRefreshManager businessRefressManager = SpringContextHolder.getBean("businessRefreshManager");
			List<Map> goodsIdListMap = businessRefressManager.getAllGoodsId();
			for(Map goodsIdMap:goodsIdListMap) {
			String goods_id = (String) goodsIdMap.get(SpecConsts.GOODS_ID);
			String GOODSGETRESP__CACHE_KEY = "GOODSGETRESP_"+goods_id;
			String PROMOTIONLIST_CACHE_KEY = "PROMOTIONLIST_"+goods_id;
			//清空数据
			cache.delete(NAMESPACE, GOODSGETRESP__CACHE_KEY);
			cache.delete(NAMESPACE, PROMOTIONLIST_CACHE_KEY);
			}
			json = "{status:0,msg:'刷新成功'}";		
		}catch(Exception ex){
			ex.printStackTrace();
			json = "{status:1,msg:'刷新失败'}";
		}
		return JSON_MESSAGE;
	}
	
}
