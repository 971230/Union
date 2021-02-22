package com.ztesoft.net.mall.core.service;

import com.ztesoft.net.mall.core.model.GoodsPrice;

import java.util.List;
import java.util.Map;

/**
 * 商品定价CRUD
 * 
 * @author easonwu 2013-08-20
 * 
 */
public interface IGoodsPriceManager {

	/**
	 * 商品定价列表
	 * 
	 * @param goods_id
	 * @return
	 */
	public List<Map> list(String goods_id);

	/**
	 * 更新商品定价
	 * 
	 * @param goods_id
	 * @param list
	 */
	public void save(String goods_id, List<GoodsPrice> list);
}
