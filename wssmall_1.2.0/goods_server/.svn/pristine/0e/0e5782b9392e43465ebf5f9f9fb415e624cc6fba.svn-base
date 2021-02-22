package com.ztesoft.net.mall.core.service;

import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.model.GoodsArea;

import java.util.List;


/**
 * 商品发布地区管理接口
 * 
 * @author lqy
 * 
 */
public interface IGoodsAreaManager {
	/**
	 * 添加商品到发布区域
	 * 
	 * @param goods
	 */
	public void add(GoodsArea area);
	
	
	public void deleteByLanIdAndGoodsId(GoodsArea area);
	/**
	 * 修改商品发布区
	 * 
	 * @param goods
	 */
	public void edit(GoodsArea area);
	/**
	 * 通过goods_id查询此商品的发布区域
	 * @param goods_id
	 */
	public List<GoodsArea> serach(String goodsid);
	public Page serachGoodsAreaLog(String goodsid,int pageNo,int pageSize) ;
	/**
	 * 通过goods_id查询此商品的发布成功区域
	 * @param goods_id
	 */
	public Page serachGoodsAreaSucc(String goods_id,int pageNo,int pageSize);
	/**
	 * 通过goods_id查询此商品的发布待审核区域
	 * @param goods_id
	 */
	public Page serachGoodsWaitAudit(String goods_id,int pageNo,int pageSize);
	/**
	 * 通过goods_id和lan_id修改商品的发布区域状态
	 * @param lan_id,goods_id
	 */
	public void editAreaState(String lan_id,String goods_id);
	/**
	 * 通过goods_id和lan_id查询该区域该商品状态
	 * @param lan_id,goods_id
	 */
	public GoodsArea getGoodsAreaById(String goods_id,String lan_id);
}
