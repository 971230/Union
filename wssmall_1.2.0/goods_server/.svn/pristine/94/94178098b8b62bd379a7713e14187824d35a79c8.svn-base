package com.ztesoft.net.mall.core.service;

import com.ztesoft.net.mall.core.model.CatComplex;

import java.util.List;


/**
 * 商品类型管理接口
 * @author Administrator
 *
 */
public interface ICatComplexManager {

	/**
	 *保存商品类型 
	 * @param catcom
	 */
	public void save(CatComplex catcom) ;
	
	/**
	 * 查询相关商品是否已存在
	 * @param goods_id,cat_id
	 */
	public int findCatComp(String goods_id,String cat_id) ;
	
	/**
	 * 查询相关商品list
	 * @param cat_id
	 */
	public List<CatComplex> findListByCatId(String cat_id);
	
	public void delByCatId(String cat_id, String goods_id); 
	
	public List<CatComplex> listCatComplex();
}
