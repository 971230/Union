package com.ztesoft.net.mall.core.service;

import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.model.Attribute;
import com.ztesoft.net.mall.core.model.Goods;

import java.util.List;
import java.util.Map;

public interface IGoodsSearchManager {
	
	
	public Page search( int page, int pageSize,Map<String,String> params);

	public Page searchProxyGoods( int page, int pageSize,Map<String,String> params);
	
	public List<Attribute> getpPropInstsByGoods(Goods goods);
	/**
	 * 读取某个类别下的属性列表<br/> 同时计算每个属性的商品数量
	 * 
	 * @param type_id
	 * @param cat_path
	 * @return
	 */
	public List[] getPropListByCat(final String type_id, String cat_path,
			String brand_str, String propStr,String attrStr);
	
	
	public Page listByCatId(String user_id,String typeid,String cat_path, int page, int pageSize,
			String order,String time_order, String brand_str, String propStr, String keyword,
			String minPrice, String maxPrice, String tagids,String attrStr,String agn,String search_key,String has_stock,boolean is_proxy,String is_sale);
	
	/**
	 * 查询产品列表
	 * @作者 MoChunrun
	 * @创建日期 2013-10-28 
	 * @return
	 */
	public Page qryProducts(String typeid,String cat_path, int page, int pageSize,
			String order,String time_order, String brand_str, String propStr, String keyword,
			String minPrice, String maxPrice, String tagids,String attrStr,String agn,String member_lv_id,String search_key);
	
}
