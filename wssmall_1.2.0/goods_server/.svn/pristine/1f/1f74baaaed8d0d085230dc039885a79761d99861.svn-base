package com.ztesoft.net.cache.common;

import java.io.Serializable;

import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

public abstract class GoodsNetCache {
	
	 
	//包含cachefactory
	private INetCache cache = CacheFactory.getCacheByType("");
	
	//100以下是保留，最大值不能超过10000
	private final int NAMESPACE = 100;
	
	
	/**
	 * @title 商品缓存
	 * @desc 只缓存商品基本信息、商品分类、商品品牌、商品类型、商品服务类型、商品会员售卖价格、商品规格产品列表，不包括详情页属性信息，详情信息
	 */
	protected final String CACHE_GOODS = "CACHE_GOODS_ID_";
	
	protected final String CACHE_GOODS_SKU = "CACHE_GOODS_SKU_";
	
	protected final String CACHE_ACTIVITY = "CACHE_ACTIVITY_";
	
	/**
	 * 货品缓存
	 */
	protected final String CACHE_PRODUCTS = "CACHE_PRODUCTS_ID_";
	
	/**
	 * 货品缓存
	 */
	protected final String CACHE_PRODUCTS_NAME = "CACHE_PRODUCTS_NAME_";
	
	/**
	 * 商品的货品缓存
	 */
	protected final String CACHE_GOODS_REL_PRODUCTS = "CACHE_A_GOODS_ID_";
	
	
	/**
	 * @title 限时抢购
	 * @
	 */
	protected final String LIMIT_GOODS = "LIMIT_GOODS_ID_";
	
	
	/**
	 * @title 终端缓存
	 * @desc  
	 */
	protected final String GOODS_TERMINAL = "CACHE_GOODS_TERMINAL_";
	
	/**
	 * @title 套餐缓存
	 * 
	 */
	protected final String GOODS_TC = "CACHE_GOODS_TC_";	
	
	/**
	 * @title 合约机缓存
	 * 
	 */
	protected final String GOODS_CONTRACT = "CACHE_GOODS_CONTRACT_";	
	
	/**
	 * @title 商品促销
	 * 
	 */
	protected final String GOODS_PROMOTION = "CACHE_GOODS_PROMOTION_";	
	
	/**
	 * @title 商品标签
	 * 
	 */
	protected final String GOODS_TAGS = "GOODS_TAGS_";	
	
	/**
	 * @title 标签关联商品
	 * 
	 */
	protected final String GOODS_TAGS_REL_GOODS = "CAHCE_TAGS_REL_GOODS_";	

	/**
	 * @title 商品分类
	 * 
	 */
	protected final String GOODS_MEMBER_CATS = "GOODS_MEMBER_CATS_";	
	
	/**
	 * @title 商品品牌
	 * 
	 */
	protected final String GOODS_BRANDS = "CACHE_GOODS_BRANDS_";	

	/**
	 * @title 商品类型
	 * 
	 */
	protected final String GOODS_TYPE = "CACHE_GOODS_TYPE_";
	
	/**
	 * @title 货品类型
	 * 
	 */
	protected final String PRODUCT_TYPE = "CACHE_PRODUCT_TYPE_";
	
	/**
	 * @title 广告缓存
	 * 
	 */
	protected final String CACHE_ADV = "CACHE_ADV_";	

	/**
	 * @title 分类推荐商品
	 * 
	 */
	protected final String CATS_REMDGOODS = "CACHE_CATS_REMDGOODS_";	
	
	/**
	 * @title 分类商品
	 */
	protected final String CAT_GOODS_LIST = "CAT_GOODS_LIST_";
	
	/**
	 * @title 商品配件
	 * 
	 */
	protected final String GOODS_ADJUNCT = "CACHE_GOODS_ADJUNCT_";	
	
	/**
	 * @title 绑定商品
	 * 
	 */
	protected final String GOODS_BINDING = "CACHE_GOODS_BINDING_";	
		
	/**
	 * @title 门户展示一级分类下所有商品
	 * 
	 */
	protected final String GOODS_CAT_I = "CACHE_GOODS_CAT_I_";	
	
	/**
	 * @title 获取所有服务类型下的商品，及"select * from es_goods where stype_id=?"
	 * 
	 */
	protected final String GOODS_SERV = "CACHE_GOODS_SERV_";	

	/**
	 * @title 获取所有服务类型下的商品，及"select * from es_goods where sub_stype_id=?"
	 * 
	 */
	protected final String GOODS_SUB_SERV = "CACHE_GOODS_SUB_SERV_";	
	
	/**
	 * @title 商品规格数据缓存
	 */
	protected final String CACHE_GOODS_SPEC = "CACHE_GOODS_SPEC_";
	
	/**
	 * 类型列表
	 */
	protected final String GOODS_TYPE_LIST = "goods_type_list";
	
	protected final String GOODS_CAT_ID = "goods_cat_";
	
	/**
	 * 类型关联的品牌
	 */
	protected final String BRAND_LIST_BY_TYPE = "brand_list_by_type_";
	
	
	protected final String BRAND_MODEL_LIST = "brand_model_list_";
	
	protected final String CAT_LIST_BY_TYPE = "cat_list_by_type_";
	
	protected final String TERMINAL_NUM_LIST = "terminal_num_list";
	
	/**
	 * 永久保留缓存
	 * @param key
	 * @param value
	 */
	public void set(String key, Serializable value){
		cache.set(NAMESPACE, key+ManagerUtils.getSourceFrom(), value);
	}
	
	/**
	 * 缓存设定有效时间time，没有设置时间，将永久保留缓存
	 * @param key
	 * @param value
	 * @param time 单位秒
	 */
	public void set(String key, Serializable value, int time){
		String source_from =ManagerUtils.getSourceFrom();
		cache.set(NAMESPACE, key+source_from, value, time);
	}
	
	public Object get(String key){
		String source_from =ManagerUtils.getSourceFrom();
        Object obj=cache.get(NAMESPACE, key+source_from);
        if(obj ==null)
        	obj=cache.get(NAMESPACE, key);
        if(obj ==null)
        {
        	//根据配置获取
        	ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
    		String planDBSourceFroms = cacheUtil.getConfigInfo("PLAN_SOURCE_FROM");
    		if(!StringUtil.isEmpty(planDBSourceFroms)){
	    		for (String plan_dbsourcefrom :planDBSourceFroms.split(",")) {
	    			   obj=cache.get(NAMESPACE, key+plan_dbsourcefrom);
	    			   if(obj != null)
	    				 break;
	    		}
    		}
        }
		return obj;
	}
	
}
