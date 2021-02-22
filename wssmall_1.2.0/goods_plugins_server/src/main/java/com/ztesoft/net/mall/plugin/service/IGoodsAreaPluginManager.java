package com.ztesoft.net.mall.plugin.service;

import java.util.List;
import java.util.Map;

/**
 * 商品地域插件处理接口
 * @author hu.yi
 * @date 2014.01.21
 */
public interface IGoodsAreaPluginManager {

	/**
	 * 查询地域列表
	 * @return
	 */
	public List<Map<String, Object>> qryAreaList();
	
	
	/**
	 * 更新商品表的外系统id
	 * @param goods_id
	 * @param crm_offer_id
	 */
	public void updateCrmOfferId(String goods_id,String crm_offer_id);
	
	
	/**
	 * 商品地域表插入数据
	 * @param goods_id
	 * @param lan_id
	 */
	public void insertGoodsArea(String goods_id,String lan_id);
	
	
	/**
	 * 查询商品地域信息
	 * @param goods_id
	 * @return
	 */
	public Map<String, Object> qryGoodsAreaMap(String goods_id);
	
	
	/**
	 * 更新商品地域表数据
	 * @param goods_id
	 * @param lan_id
	 */
	public void updateGoodsArea(String goods_id,String lan_id);
	
}
