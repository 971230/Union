package com.ztesoft.net.mall.core.service.impl.cache;

import java.util.List;

import com.ztesoft.net.mall.core.model.Goods;
import com.ztesoft.net.mall.core.model.GoodsType;


public interface  IGoodsCacheUtil {
	
	public  List<Goods> getGoods();
	public void refreshGoodsCache();
	
	/**
	 * 获取es_goods信息
	 * @param goodsid
	 */
	public GoodsType getGoodsTypeById(String goodsid);
	
	/**
	 * 获取es_goods信息
	 * @param goodsid
	 */
	public Goods getGoodsById(String goodsid);
	
}

