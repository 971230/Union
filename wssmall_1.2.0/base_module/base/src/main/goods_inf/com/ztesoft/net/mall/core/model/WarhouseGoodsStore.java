package com.ztesoft.net.mall.core.model;

import com.ztesoft.net.framework.database.NotDbField;

import java.io.Serializable;

/**
 * 仓库商品库存
 * @作者 MoChunrun
 * @创建日期 2013-11-14 
 * @版本 V 1.0
 */
public class WarhouseGoodsStore implements Serializable {

	private String house_id;
	private String goods_id;
	private Integer store;
	private Integer freeze_store;
	private String goodsName;
	@NotDbField
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getHouse_id() {
		return house_id;
	}
	public void setHouse_id(String house_id) {
		this.house_id = house_id;
	}
	public String getGoods_id() {
		return goods_id;
	}
	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}
	public Integer getStore() {
		return store;
	}
	public void setStore(Integer store) {
		this.store = store;
	}
	public Integer getFreeze_store() {
		return freeze_store;
	}
	public void setFreeze_store(Integer freeze_store) {
		this.freeze_store = freeze_store;
	}
	
}
