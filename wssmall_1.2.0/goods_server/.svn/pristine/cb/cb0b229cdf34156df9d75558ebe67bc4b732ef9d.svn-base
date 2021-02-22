package com.ztesoft.net.mall.core.service.impl.cache;

import com.ztesoft.net.framework.cache.AbstractCacheProxy;
import com.ztesoft.net.framework.cache.CacheFactory;
import com.ztesoft.net.mall.core.model.GoodsType;
import com.ztesoft.net.mall.core.service.IGoodsTypeManager;

import java.util.List;

public class GoodsTypeInfoCacheProxy extends AbstractCacheProxy<List<GoodsType>>{
	private IGoodsTypeManager goodsTypeManager;
	
	public static final String LIST_KEY_PREFIX ="goodstype_list";
	public GoodsTypeInfoCacheProxy(IGoodsTypeManager goodsTypeManager) {
		
		super(CacheFactory.GOODS_TYPE_CACHE_NAME_KEY);
		this.goodsTypeManager = goodsTypeManager;
	}
	
	/**
	 * 获取es_config_info配置信息
	 * @param goodsid
	 */
	public GoodsType getGoodsTypeById(String typeid){
		GoodsType gtd=new GoodsType();
		//首先获取列表
		List<GoodsType> goodsTypeList=this.cache.get(LIST_KEY_PREFIX);
		if(goodsTypeList == null || goodsTypeList.isEmpty()){
			goodsTypeList=this.goodsTypeManager.getGoodsTypeList();
			this.cache.put(LIST_KEY_PREFIX, goodsTypeList);
		}
		
		//得到列表后获取key值
		if(goodsTypeList != null && !goodsTypeList.isEmpty()){
			for (int i = 0; i < goodsTypeList.size(); i++) {
				if(goodsTypeList.get(i).getType_id().equals(typeid)){
					gtd = goodsTypeList.get(i);
				}
			}
		}
		return gtd;
	}
	
	/**
	 * 
	 * 刷新缓存方法，可添加其他数据表缓存刷新
	 */
	public void refreshCache(){
		List<GoodsType> goodstypeList = this.goodsTypeManager.getGoodsTypeList();
		this.cache.put(LIST_KEY_PREFIX, goodstypeList);
	}

	public IGoodsTypeManager getGoodsTypeManager() {
		return goodsTypeManager;
	}

	public void setGoodsTypeManager(IGoodsTypeManager goodsTypeManager) {
		this.goodsTypeManager = goodsTypeManager;
	}
	
}
