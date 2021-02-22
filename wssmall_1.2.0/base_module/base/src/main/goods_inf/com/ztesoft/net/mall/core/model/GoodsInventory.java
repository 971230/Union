package com.ztesoft.net.mall.core.model;

import com.ztesoft.net.framework.database.NotDbField;

import java.io.Serializable;

/**
 * 商品盘点
 * @作者 MoChunrun
 * @创建日期 2013-11-25 
 * @版本 V 1.0
 */
public class GoodsInventory implements Serializable {

	private String inventory_id;
	private String goods_id;
	private Long store;
	private String create_time;
	private String goodsName;
	private Long original_store;
	@NotDbField
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getInventory_id() {
		return inventory_id;
	}
	public void setInventory_id(String inventory_id) {
		this.inventory_id = inventory_id;
	}
	public String getGoods_id() {
		return goods_id;
	}
	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}
	public Long getStore() {
		return store;
	}
	public void setStore(Long store) {
		this.store = store;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public Long getOriginal_store() {
		return original_store;
	}
	public void setOriginal_store(Long original_store) {
		this.original_store = original_store;
	}
	
}
