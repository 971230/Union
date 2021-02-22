package com.ztesoft.mall.core.action.backend;


import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.mall.core.model.CatComplex;
import com.ztesoft.net.mall.core.service.ICatComplexManager;

import net.sf.json.JSONArray;

import java.util.List;

/**
 * 商品分类列表相关商品action 
 * @author Administrator
 *
 */
public class CatComplexAction extends WWAction {
	private ICatComplexManager catComplexManager;
	private List<CatComplex> catComList=null;
	private String cat_id;
	private String goods_id;
	private String goodsId;
	
	/**
	 * 查询相关商品list
	 * @param cat_id
	 */
	public String findListByCatId(){
		catComList = catComplexManager.findListByCatId(cat_id);
		json=JSONArray.fromObject(catComList).toString();
		return WWAction.JSON_MESSAGE;
	}
	
	public String delByCatId(){
		this.catComplexManager.delByCatId(cat_id, goodsId);
		this.json = "{'result':1,'message':'修改成功'}";
		return WWAction.JSON_MESSAGE;
	}
	
	
	public List getCatComList() {
		return catComList;
	}
	public void setCatComList(List catComList) {
		this.catComList = catComList;
	}
	public void setCatComplexManager(ICatComplexManager catComplexManager) {
		this.catComplexManager = catComplexManager;
	}


	public String getCat_id() {
		return cat_id;
	}


	public void setCat_id(String cat_id) {
		this.cat_id = cat_id;
	}

	public String getGoods_id() {
		return goods_id;
	}

	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}

	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	
	
}
