package com.ztesoft.net.mall.core.model;

import com.ztesoft.net.framework.database.NotDbField;

import java.util.List;

/**
 * 商品配件实体
 * 
 * @author lzf<br/>
 *         2010-4-1 上午09:43:29<br/>
 *         version 1.0<br/>
 */
public class GoodsAdjunct implements java.io.Serializable {
	private String adjunct_id;
	private String goods_id;
	private String adjunct_name;
	private int min_num;
	private int max_num;
	private String set_price; //enum('discount','minus') DEFAULT NULL
	private Double price;
	private String items;
	private List<AdjunctItem> itemList;
	@NotDbField
    public List<AdjunctItem> getItemList() {
		return itemList;
	}
	public void setItemList(List<AdjunctItem> itemList) {
		this.itemList = itemList;
	}
	public String getAdjunct_id() {
		return adjunct_id;
	}
	public void setAdjunct_id(String adjunctId) {
		adjunct_id = adjunctId;
	}
	public String getGoods_id() {
		return goods_id;
	}
	public void setGoods_id(String goodsId) {
		goods_id = goodsId;
	}
	public String getAdjunct_name() {
		return adjunct_name;
	}
	public void setAdjunct_name(String adjunctName) {
		adjunct_name = adjunctName;
	}
	public int getMin_num() {
		return min_num;
	}
	public void setMin_num(int minNum) {
		min_num = minNum;
	}
	public int getMax_num() {
		return max_num;
	}
	public void setMax_num(int max_num) {
		this.max_num = max_num;
	}
	public String getSet_price() {
		return set_price;
	}
	public void setSet_price(String setPrice) {
		set_price = setPrice;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getItems() {
		return items;
	}
	public void setItems(String items) {
		this.items = items;
	}
	
}
