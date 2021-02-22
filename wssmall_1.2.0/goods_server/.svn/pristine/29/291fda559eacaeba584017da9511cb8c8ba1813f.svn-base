package com.ztesoft.net.mall.core.service.impl.cache;

import com.ztesoft.net.framework.cache.AbstractCacheProxy;
import com.ztesoft.net.framework.cache.CacheFactory;
import com.ztesoft.net.mall.core.model.Goods;
import com.ztesoft.net.mall.core.service.IGoodsManager;

import java.util.List;

public class GoodsInfoCacheProxy extends AbstractCacheProxy<List<Goods>>{
	private IGoodsManager goodsManager;
	
	public static final String LIST_KEY_PREFIX ="goods_list";
	public GoodsInfoCacheProxy(IGoodsManager goodsManager) {
		
		super(CacheFactory.GOODS_INFO_CACHE_NAME_KEY);
		
		this.goodsManager = goodsManager;
	}
	
	/**
	 * 获取es_config_info配置信息
	 * @param cfId
	 */
	public List<Goods> getGoods(){
		//首先获取列表
		List<Goods> goodsList=this.cache.get(LIST_KEY_PREFIX);
		if(goodsList == null || goodsList.isEmpty()){
			goodsList=this.goodsManager.listGoods();
			this.cache.put(LIST_KEY_PREFIX, goodsList);
		}
		return goodsList;
	}
	
	/**
	 * 获取es_config_info配置信息
	 * @param goodsid
	 */
	public Goods getGoodsById(String goodsid){
		
		Goods gs=new Goods();
		//首先获取列表
		List<Goods> goodsList=this.cache.get(LIST_KEY_PREFIX);
		if(goodsList == null || goodsList.isEmpty()){
			goodsList=this.goodsManager.listGoods();
			this.cache.put(LIST_KEY_PREFIX, goodsList);
		}
		
		//得到列表后获取key值
		if(goodsList != null && !goodsList.isEmpty()){
			Goods goodsinfo = null;
			for (int i = 0; i < goodsList.size(); i++) {
				goodsinfo = goodsList.get(i);
				if(goodsinfo.getGoods_id().equals(goodsid)){
					gs = goodsinfo;
				}
			}
		}
		return gs;
	}
	
	/**
	 * 
	 * 刷新缓存方法，可添加其他数据表缓存刷新
	 */
	public void refreshCache(){
		List<Goods> configList = this.goodsManager.listGoods();
		this.cache.put(LIST_KEY_PREFIX, configList);
	}
}
