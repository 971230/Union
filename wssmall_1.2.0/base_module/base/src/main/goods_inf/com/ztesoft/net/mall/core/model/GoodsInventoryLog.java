package com.ztesoft.net.mall.core.model;

import java.io.Serializable;

/*
 * 货品库存管理日志
 */
public class GoodsInventoryLog implements Serializable {

	private String  log_id         ; 
	private String  product_id     ; 
	private String  sku            ; 
	private String  house_id       ; 
	private Integer  inventory_num ; 
	private Integer  no_apply_num  ; 
	private Integer  apply_num     ; 
	private String  status_date    ; 
	private String  action         ; 
	private Integer  change_num    ; 
	private String  change_reason  ; 
	private String  source_from    ;
	private String purchase_prices; // 联通仓库功能之前没有，后来仓库系统需要，加上去的（2015-07-13）
	private String product_name; // 联通仓库功能之前没有，后来仓库系统需要，加上去的（2015-07-13）
	
	public String getPurchase_prices() {
		return purchase_prices;
	}
	public void setPurchase_prices(String purchase_prices) {
		this.purchase_prices = purchase_prices;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public String getLog_id() {
		return log_id;
	}
	public void setLog_id(String log_id) {
		this.log_id = log_id;
	}
	public String getProduct_id() {
		return product_id;
	}
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public String getHouse_id() {
		return house_id;
	}
	public void setHouse_id(String house_id) {
		this.house_id = house_id;
	}
	public Integer getInventory_num() {
		return inventory_num;
	}
	public void setInventory_num(Integer inventory_num) {
		this.inventory_num = inventory_num;
	}
	public Integer getNo_apply_num() {
		return no_apply_num;
	}
	public void setNo_apply_num(Integer no_apply_num) {
		this.no_apply_num = no_apply_num;
	}
	public Integer getApply_num() {
		return apply_num;
	}
	public void setApply_num(Integer apply_num) {
		this.apply_num = apply_num;
	}
	public String getStatus_date() {
		return status_date;
	}
	public void setStatus_date(String status_date) {
		this.status_date = status_date;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public Integer getChange_num() {
		return change_num;
	}
	public void setChange_num(Integer change_num) {
		this.change_num = change_num;
	}
	public String getChange_reason() {
		return change_reason;
	}
	public void setChange_reason(String change_reason) {
		this.change_reason = change_reason;
	}
	public String getSource_from() {
		return source_from;
	}
	public void setSource_from(String source_from) {
		this.source_from = source_from;
	} 

	
	
	
}
