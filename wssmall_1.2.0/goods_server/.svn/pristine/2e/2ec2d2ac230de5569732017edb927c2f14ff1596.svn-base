package com.ztesoft.net.mall.core.service;

import com.ztesoft.net.mall.core.model.GoodsAdjunct;

import java.util.List;
import java.util.Map;

/**
 * 商品配件
 * 
 * @author lzf<br/>
 *         2010-3-30 下午05:39:37<br/>
 *         version 1.0<br/>
 */
public interface IGoodsAdjunctManager {
	
	public List<GoodsAdjunct> listAdjunct(String goods_id);

	/**
	 * 商品配件组列表
	 * 
	 * @param goods_id
	 * @return
	 */
	public List<Map> list(String goods_id);

	/**
	 * 更新商品配件
	 * 
	 * @param goods_id
	 * @param list
	 */
	public void save(String goods_id, List<GoodsAdjunct> list);
	
	public int isExistsAjdunct(List list);
	
}
