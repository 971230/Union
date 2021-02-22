package com.ztesoft.net.mall.core.service;

import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.model.GoodsInventory;
import com.ztesoft.net.mall.core.model.WarhouseInventory;

import java.util.List;

/**
 * 盘点管理
 * @作者 MoChunrun
 * @创建日期 2013-11-25 
 * @版本 V 1.0
 */
public interface IGoodsInventoryManager {
	
	/**
	 * 添加仓库盘点
	 * @作者 MoChunrun
	 * @创建日期 2013-11-25 
	 * @param warhouseInventory
	 */
	public void saveWarhouseInventory(WarhouseInventory warhouseInventory);

	/**
	 * 增加盘点
	 * @作者 MoChunrun
	 * @创建日期 2013-11-25 
	 */
	public void saveGoodsInventory(GoodsInventory goodsInventory);
	
	/**
	 * 按ID查询
	 * @作者 MoChunrun
	 * @创建日期 2013-11-25 
	 * @return
	 */
	public WarhouseInventory getWarhouseInventory(String inventory_id);
	public Page qryWarhouseInventoryPage(int pageNo,int pageSize,Integer status,String name,String houseId);
	
	/**
	 * 按商品ID查询
	 * @作者 MoChunrun
	 * @创建日期 2013-11-25 
	 * @param status null为查询全部
	 * @return
	 */
	public List<GoodsInventory> qryGoodsInventoryByInventoryId(String inventory_id);
	
	/**
	 * 按创库统计
	 * @作者 MoChunrun
	 * @创建日期 2013-11-25 
	 * @param status null为查询全部
	 * @return
	 */
	public List<WarhouseInventory> qryByHouseId(String house_id,Integer status);
	
	/**
	 * 查询仓库的所有商品
	 * @作者 MoChunrun
	 * @创建日期 2013-11-26 
	 * @param house_id
	 * @return
	 */
	public Page qryHouseGoods(String house_id,String goodsName,int pageNo,int pageSize);
	
	/**
	 * 按ID删除盘点明细
	 * @作者 MoChunrun
	 * @创建日期 2013-11-26 
	 * @param inventory_id
	 */
	public void delGoodsInventoryByInventoryId(String inventory_id);
	
	/**
	 * 修改盘点名称
	 * @作者 MoChunrun
	 * @创建日期 2013-11-26 
	 * @param name
	 * @param inventory_id
	 */
	public void updateInventoryName(String name,String inventory_id);
	
	/**
	 * 修改状态
	 * @作者 MoChunrun
	 * @创建日期 2013-11-26 
	 * @param inventory_id
	 * @param status
	 * @param remark
	 */
	public void updateWarhouseInventoryStatus(String inventory_id,Integer status,String remark);
	
	/**
	 * 检查商品是否在盘点
	 * @作者 MoChunrun
	 * @创建日期 2013-12-9 
	 * @param goods_id
	 * @return
	 */
	public boolean goodsIsInventory(String house_id,String goods_id);
	
}
