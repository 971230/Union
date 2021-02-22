package com.ztesoft.net.mall.core.model;

import java.io.Serializable;

import com.ztesoft.net.framework.database.NotDbField;

/*
 * 货品/商品库存分配管理
 */
public class GoodsInventoryApply implements Serializable {
	
	private String goods_id; //货品ID
	private String goods_name;//货品名称
	private String type;//类型
	private String sku;//货品sku（商品编码）
	private String org_id;//销售组织ID
	private String  house_id          ;   //物理仓标识
	private String  virtual_house_id  ;   //虚拟仓标识
	private String house_id_out;//分配仓库(联通仓库功能之前没有，后来仓库系统需要，加上去的（2015-07-13）)
	private int inventory_num;//已分配库存
	private String house_id_in;//接受仓库(联通仓库功能之前没有，后来仓库系统需要，加上去的（2015-07-13）)
	private String is_share;//是否共享库存
	private String apply_type;//分配类型
	private String apply_mode;//分配方式
	private String source_from;//平台
	private String autoStr;//保存自动分配的比例和值
	private int    apply_num;//已分配的库存
	private int    no_apply_num;//未分配的库存
	private int    log_inventory_num;//逻辑仓的库存是上级分配下来的  需要在分配表里面求和
	private String log_apply_mode;//查询逻辑向虚拟仓分配的方式
	private String  product_id; // 货品ID(联通之前的代码，不晓得做什么用的)
	private String order_id;
	
	@NotDbField
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getHouse_id_out() {
		return house_id_out;
	}
	public void setHouse_id_out(String house_id_out) {
		this.house_id_out = house_id_out;
	}
	public String getHouse_id_in() {
		return house_id_in;
	}
	public void setHouse_id_in(String house_id_in) {
		this.house_id_in = house_id_in;
	}
	public String getHouse_id() {
		return house_id;
	}
	public void setHouse_id(String house_id) {
		this.house_id = house_id;
	}
	public String getVirtual_house_id() {
		return virtual_house_id;
	}
	public void setVirtual_house_id(String virtual_house_id) {
		this.virtual_house_id = virtual_house_id;
	}
	@NotDbField
	public String getProduct_id() {
		return product_id;
	}
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}
	public String getGoods_id() {
		return goods_id;
	}
	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}
	public String getGoods_name() {
		return goods_name;
	}
	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public String getOrg_id() {
		return org_id;
	}
	public void setOrg_id(String org_id) {
		this.org_id = org_id;
	}
//	public String getHouse_id_out() {
//		return house_id_out;
//	}
//	public void setHouse_id_out(String house_id_out) {
//		this.house_id_out = house_id_out;
//	}
	public int getInventory_num() {
		return inventory_num;
	}
	public void setInventory_num(int inventory_num) {
		this.inventory_num = inventory_num;
	}
//	public String getHouse_id_in() {
//		return house_id_in;
//	}
//	public void setHouse_id_in(String house_id_in) {
//		this.house_id_in = house_id_in;
//	}
	public String getIs_share() {
		return is_share;
	}
	public void setIs_share(String is_share) {
		this.is_share = is_share;
	}
	public String getApply_type() {
		return apply_type;
	}
	public void setApply_type(String apply_type) {
		this.apply_type = apply_type;
	}
	public String getApply_mode() {
		return apply_mode;
	}
	public void setApply_mode(String apply_mode) {
		this.apply_mode = apply_mode;
	}
	public String getSource_from() {
		return source_from;
	}
	public void setSource_from(String source_from) {
		this.source_from = source_from;
	}
	@NotDbField
	public String getAutoStr() {
		return autoStr;
	}
	public void setAutoStr(String autoStr) {
		this.autoStr = autoStr;
	}
	@NotDbField
	public int getApply_num() {
		return apply_num;
	}
	public void setApply_num(int apply_num) {
		this.apply_num = apply_num;
	}
	@NotDbField
	public int getNo_apply_num() {
		return no_apply_num;
	}
	public void setNo_apply_num(int no_apply_num) {
		this.no_apply_num = no_apply_num;
	}
	@NotDbField
	public int getLog_inventory_num() {
		return log_inventory_num;
	}
	public void setLog_inventory_num(int log_inventory_num) {
		this.log_inventory_num = log_inventory_num;
	}
	@NotDbField
	public String getLog_apply_mode() {
		return log_apply_mode;
	}
	public void setLog_apply_mode(String log_apply_mode) {
		this.log_apply_mode = log_apply_mode;
	}
	
}
