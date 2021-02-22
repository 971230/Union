package com.ztesoft.net.model;

import zte.net.common.annontion.context.request.RequestFieldAnnontion;

/**
 * table_name = "es_order_items_extvtl"
 * service_desc = "订单扩展信息横表-多商品"
 * @author ZX Add 2015-11-07
 *
 */
public class OrderItemExtvtlVo {
	
	@RequestFieldAnnontion()
	private String items_id;
	@RequestFieldAnnontion()
	private String order_id;
	@RequestFieldAnnontion()
	private String goods_id;
	@RequestFieldAnnontion()
	private String goods_name;
	@RequestFieldAnnontion()
	private String goods_type;
	@RequestFieldAnnontion()
	private String resources_code;
	@RequestFieldAnnontion()
	private String resources_brand_code;
	@RequestFieldAnnontion()
	private String resources_brand_name;
	@RequestFieldAnnontion()
	private String resources_model_code;
	@RequestFieldAnnontion()
	private String resources_model_name;
	@RequestFieldAnnontion()
	private String resources_color;
	@RequestFieldAnnontion()
	private String machine_type_code;
	@RequestFieldAnnontion()
	private String machine_type_name;
	@RequestFieldAnnontion()
	private String source_from;
	@RequestFieldAnnontion()
	private String ess_oper_id;
	@RequestFieldAnnontion()
	private String operation_status;
	@RequestFieldAnnontion()
	private String operation_desc;
	@RequestFieldAnnontion()
	private String sku;
	@RequestFieldAnnontion()
	private String product_id;
	
	//非数据库操作字段
	private String goods_type_name;//商品类型名称
	
	//非数据库操作字段
	private int goods_num; // 多商品数量
	
	//非数据库操作字段
	private String ship_city; // 发货地市区县
	
	//非数据库操作字段
	private String channel_name; // 渠道名称

	public String getItems_id() {
		return items_id;
	}

	public void setItems_id(String items_id) {
		this.items_id = items_id;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
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

	public String getGoods_type() {
		return goods_type;
	}

	public void setGoods_type(String goods_type) {
		this.goods_type = goods_type;
	}

	public String getResources_code() {
		return resources_code;
	}

	public void setResources_code(String resources_code) {
		this.resources_code = resources_code;
	}

	public String getResources_brand_code() {
		return resources_brand_code;
	}

	public void setResources_brand_code(String resources_brand_code) {
		this.resources_brand_code = resources_brand_code;
	}

	public String getResources_brand_name() {
		return resources_brand_name;
	}

	public void setResources_brand_name(String resources_brand_name) {
		this.resources_brand_name = resources_brand_name;
	}

	public String getResources_model_code() {
		return resources_model_code;
	}

	public void setResources_model_code(String resources_model_code) {
		this.resources_model_code = resources_model_code;
	}

	public String getResources_model_name() {
		return resources_model_name;
	}

	public void setResources_model_name(String resources_model_name) {
		this.resources_model_name = resources_model_name;
	}

	public String getResources_color() {
		return resources_color;
	}

	public void setResources_color(String resources_color) {
		this.resources_color = resources_color;
	}

	public String getMachine_type_code() {
		return machine_type_code;
	}

	public void setMachine_type_code(String machine_type_code) {
		this.machine_type_code = machine_type_code;
	}

	public String getMachine_type_name() {
		return machine_type_name;
	}

	public void setMachine_type_name(String machine_type_name) {
		this.machine_type_name = machine_type_name;
	}

	public String getSource_from() {
		return source_from;
	}

	public void setSource_from(String source_from) {
		this.source_from = source_from;
	}

	public String getEss_oper_id() {
		return ess_oper_id;
	}

	public void setEss_oper_id(String ess_oper_id) {
		this.ess_oper_id = ess_oper_id;
	}

	public String getOperation_status() {
		return operation_status;
	}

	public void setOperation_status(String operation_status) {
		this.operation_status = operation_status;
	}

	public String getOperation_desc() {
		return operation_desc;
	}

	public void setOperation_desc(String operation_desc) {
		this.operation_desc = operation_desc;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getProduct_id() {
		return product_id;
	}

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}

	public String getGoods_type_name() {
		return goods_type_name;
	}

	public void setGoods_type_name(String goods_type_name) {
		this.goods_type_name = goods_type_name;
	}

	public int getGoods_num() {
		return goods_num;
	}

	public void setGoods_num(int goods_num) {
		this.goods_num = goods_num;
	}

	public String getShip_city() {
		return ship_city;
	}

	public void setShip_city(String ship_city) {
		this.ship_city = ship_city;
	}

	public String getChannel_name() {
		return channel_name;
	}

	public void setChannel_name(String channel_name) {
		this.channel_name = channel_name;
	}
	
	

}
