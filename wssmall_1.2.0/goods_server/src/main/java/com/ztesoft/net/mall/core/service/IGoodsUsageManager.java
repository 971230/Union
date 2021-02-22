package com.ztesoft.net.mall.core.service;


import com.ztesoft.net.mall.core.model.GoodsUsage;

import java.util.List;
import java.util.Map;


/**
 * 商品使用
 * 
 * @author lqy
 * 
 */
public interface IGoodsUsageManager {
	
	/**
	 * 修改商品阀值
	 * 
	 * @param 
	 */
	public void edit(GoodsUsage usage,String goods_id);
	public GoodsUsage getGoodsUsageByGoodsid(String goods_id,String userid,String usageid);
	public List<GoodsUsage> findGoodsUsageList(String userid,String goods_id);
	/**
	 * 获取云卡、充值卡的库存
	 * @return
	 */
	public List<Map> getCardStock();
	
	/**
	 * 更新es_goods_usage库存字段 同时对库存告急的商家进行短信通知
	 * @param cardStockList
	 */
	public void stockWarning(List<Map> cardStockList);
	
	
	/**
	 * 根据商品id获取商品本地网
	 * @param goodsId
	 * @return
	 */
	public List getLanByGoodsId(String goodsId);
	
}
