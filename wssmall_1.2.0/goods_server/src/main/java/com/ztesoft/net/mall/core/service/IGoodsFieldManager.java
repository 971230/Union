package com.ztesoft.net.mall.core.service;

import com.ztesoft.net.mall.core.plugin.field.GoodsField;

import java.util.List;


/**
 * 商品字段管理
 * @author kingapex
 *
 */
public interface IGoodsFieldManager {
	
	
	/**
	 * 获取某个商品类型的商品字段
	 * @param catid
	 * @return
	 */
	public List<GoodsField> list(String typeid);
	
	
	
	/**
	 * 添加某字段
	 * @param dataField
	 * @return
	 */
	public String add(GoodsField goodsField );
	
	
	
	
	
	/**
	 * 修改某字段信息
	 * @param dataField
	 */
	public void edit(GoodsField goodsField );
	
 
	/**
	 * 获取某字段的字段详细
	 * @param fieldid
	 * @return
	 */
	public GoodsField  get(String fieldid);	
	
	
	
	/**
	 * 删除某个字段
	 * @param modelid
	 */
	public void deleteF(String fieldid);
	
	
	/**
	 *批量刪除某个
	 * @param typeids
	 */
	public void delete(String typeids);
	
}
