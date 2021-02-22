package com.ztesoft.net.mall.core.model;

import java.io.Serializable;

import com.ztesoft.net.framework.database.NotDbField;

/*
 * 货品/商品库存分配日志
 */
public class GoodsInventoryApplyLog implements Serializable {

	private String  log_id            ;   //日志标识
	private String  obj_id            ;   //商品或货品ID
	private String  type              ;   //类型  货品PRODUCT_ID; 商品GOODS_ID
	private String  sku               ;   //货品sku（商品编码）
	private String  org_id_str      ;     //销售组织编码串   多个则用逗号分隔
	private String  house_id          ;   //物理仓标识
	private String  action            ;   //动作 0-出库  1-入库 2-回收
	private String  action_name       ;   //动作名称  NotDbField
	private Integer  inventory_num    ;   //分配库存 出库填负数
	private String  virtual_house_id  ;   //虚拟仓标识
	private String  is_share          ;   //是否共享库存
	private String  oper_id           ;   //操作员
	private String  status_date       ;   //最后操作时间
	private String  order_id          ;   //订单标识
	private String  source_from       ;   //
	private String apply_type; // 联通仓库功能之前没有，后来仓库系统需要，加上去的（2015-07-13）
	private String apply_mode;	 // 联通仓库功能之前没有，后来仓库系统需要，加上去的（2015-07-13）
	private String obj_name; // 联通仓库功能之前没有，后来仓库系统需要，加上去的（2015-07-13）
	private String house_id_out; // 联通仓库功能之前没有，后来仓库系统需要，加上去的（2015-07-13）
	private String house_id_in; // 联通仓库功能之前没有，后来仓库系统需要，加上去的（2015-07-13）
	
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
	public String getObj_name() {
		return obj_name;
	}
	public void setObj_name(String obj_name) {
		this.obj_name = obj_name;
	}
	public String getLog_id() {
		return log_id;
	}
	public void setLog_id(String log_id) {
		this.log_id = log_id;
	}
	public String getObj_id() {
		return obj_id;
	}
	public void setObj_id(String obj_id) {
		this.obj_id = obj_id;
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

	public String getOrg_id_str() {
		return org_id_str;
	}
	public void setOrg_id_str(String org_id_str) {
		this.org_id_str = org_id_str;
	}
	public String getHouse_id() {
		return house_id;
	}
	public void setHouse_id(String house_id) {
		this.house_id = house_id;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public Integer getInventory_num() {
		return inventory_num;
	}
	public void setInventory_num(Integer inventory_num) {
		this.inventory_num = inventory_num;
	}
	public String getVirtual_house_id() {
		return virtual_house_id;
	}
	public void setVirtual_house_id(String virtual_house_id) {
		this.virtual_house_id = virtual_house_id;
	}
	public String getIs_share() {
		return is_share;
	}
	public void setIs_share(String is_share) {
		this.is_share = is_share;
	}
	public String getOper_id() {
		return oper_id;
	}
	public void setOper_id(String oper_id) {
		this.oper_id = oper_id;
	}
	public String getStatus_date() {
		return status_date;
	}
	public void setStatus_date(String status_date) {
		this.status_date = status_date;
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getSource_from() {
		return source_from;
	}
	public void setSource_from(String source_from) {
		this.source_from = source_from;
	}
	
	@NotDbField
	public String getAction_name() {
		return action_name;
	}
	public void setAction_name(String action_name) {
		this.action_name = action_name;
	}

	
}