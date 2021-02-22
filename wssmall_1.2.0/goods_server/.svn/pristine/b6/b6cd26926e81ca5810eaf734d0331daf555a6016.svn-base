package com.ztesoft.net.mall.core.service;

import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.model.WarhouseGoodsStore;

import java.util.List;

/**
 * 仓库商品库存管理类
 * @作者 MoChunrun
 * @创建日期 2013-11-14 
 * @版本 V 1.0
 */
public interface IWarhouseGoodsStoreManager {

	/**
	 * @作者 MoChunrun
	 * @创建日期 2013-11-14 
	 * @param goods_id
	 * @return
	 */
	public int sumStoreByGoodsId(String goods_id,String house_id);
	
	public List<WarhouseGoodsStore> sumStoreByGoodsAndHouse(String goodsIds,String hous_id);
	
	public void addStore(String goods_id,String house_id,int addStore);
	
	public void descStore(String goods_id,String house_id,int descStore);
	
	public Page goodsStoreList(String goods_id,String goods_name,String sn,String house_name,int page, int pageSize);
	
	public Page warhouseStoreList(String house_name,String goods_name,String sn,String transit_store_compare,String transit_store,String store_compare, String store,int page, int pageSize);
	
	public Page sumStoreList(String sn,String goods_name,int pageNo, int pageSize);
	
	/**
	 * 修改库存 正数为增加库存 负数为减少库存
	 * @作者 MoChunrun
	 * @创建日期 2013-11-26 
	 * @param goods_id
	 * @param num
	 */
	public void updateGoodsStore(String goods_id,int num);
	
}
