package com.ztesoft.net.mall.core.model;

import com.ztesoft.net.framework.database.NotDbField;

import java.io.Serializable;

public class WarehouseGoodsStore implements Serializable {

	private String specifications;
	private String name;
	private String house_name;
	private String seat_name;
	private String sn;
	private String freeze_store;
	private String transit_store;
	private String store;
	private String last_modify;
	private String create_time;
	private String unit;
	private String weight;
	private String goods_id;
	
	/**
	 * 非数据库字段
	 */
	private String sum_store; //可用库存总和
	private String sum_freeze_store; //冻结库存总和	
	private String sum_transit_store;//在途库存总和
	
	
	public String getSpecifications() {
		return specifications;
	}
	public void setSpecifications(String specifications) {
		this.specifications = specifications;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getHouse_name() {
		return house_name;
	}
	public void setHouse_name(String house_name) {
		this.house_name = house_name;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public String getFreeze_store() {
		return freeze_store;
	}
	public void setFreeze_store(String freeze_store) {
		this.freeze_store = freeze_store;
	}
	public String getTransit_store() {
		return transit_store;
	}
	public void setTransit_store(String transit_store) {
		this.transit_store = transit_store;
	}
	public String getStore() {
		return store;
	}
	public void setStore(String store) {
		this.store = store;
	}
	public String getLast_modify() {
		return last_modify;
	}
	public void setLast_modify(String last_modify) {
		this.last_modify = last_modify;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	@NotDbField
	public String getSum_store() {
		return sum_store;
	}
	public void setSum_store(String sum_store) {
		this.sum_store = sum_store;
	}
	@NotDbField
	public String getSum_freeze_store() {
		return sum_freeze_store;
	}
	public void setSum_freeze_store(String sum_freeze_store) {
		this.sum_freeze_store = sum_freeze_store;
	}
	@NotDbField
	public String getSum_transit_store() {
		return sum_transit_store;
	}
	public void setSum_transit_store(String sum_transit_store) {
		this.sum_transit_store = sum_transit_store;
	}
	public String getGoods_id() {
		return goods_id;
	}
	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}
	public String getSeat_name() {
		return seat_name;
	}
	public void setSeat_name(String seat_name) {
		this.seat_name = seat_name;
	}
	
	
}
