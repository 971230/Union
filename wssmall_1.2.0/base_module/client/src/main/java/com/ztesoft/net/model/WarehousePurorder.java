package com.ztesoft.net.model;

import java.io.Serializable;

import com.ztesoft.net.framework.database.NotDbField;

/**
 * 采购单表
 * @作者 MoChunrun
 * @创建日期 2013-11-11 
 * @版本 V 1.0
 */
public class WarehousePurorder implements Serializable {

	private String z_order_id;
	private String supper_id;
	private String house_id;
	private String audit_time;
	private String audit_status;
	private String store_action_type;
	private String audit_oper_id;
	private String pru_delvery_finish_time;
	private String pru_type;
	private String pru_stock_status;
	private String pru_order_id;
	private String pru_order_name;
	private String pru_status;
	private Double deposit;
	private String create_time;
	private String mod_time;
	private String create_type;//5采购入订单,采购订单订单写入改造,6采购出订单,采购订单订单写入改造
	
	private String company_name;
	private String order_id;
	private String house_name;
	private String delivery_id;
	
	@NotDbField
	public String getCompany_name() {
		return company_name;
	}
	
	@NotDbField
	public String getDelivery_id() {
		return delivery_id;
	}

	@NotDbField
	public void setDelivery_id(String delivery_id) {
		this.delivery_id = delivery_id;
	}


	@NotDbField
	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}
	
	@NotDbField
	public String getOrder_id() {
		return order_id;
	}
	
	@NotDbField
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	
	@NotDbField
	public String getHouse_name() {
		return house_name;
	}
	
	@NotDbField
	public void setHouse_name(String house_name) {
		this.house_name = house_name;
	}
	
	public String getZ_order_id() {
		return z_order_id;
	}
	public void setZ_order_id(String z_order_id) {
		this.z_order_id = z_order_id;
	}
	public String getSupper_id() {
		return supper_id;
	}
	public void setSupper_id(String supper_id) {
		this.supper_id = supper_id;
	}
	public String getHouse_id() {
		return house_id;
	}
	public void setHouse_id(String house_id) {
		this.house_id = house_id;
	}
	public String getAudit_time() {
		return audit_time;
	}
	public void setAudit_time(String audit_time) {
		this.audit_time = audit_time;
	}
	public String getAudit_status() {
		return audit_status;
	}
	public void setAudit_status(String audit_status) {
		this.audit_status = audit_status;
	}
	public String getStore_action_type() {
		return store_action_type;
	}
	public void setStore_action_type(String store_action_type) {
		this.store_action_type = store_action_type;
	}
	public String getAudit_oper_id() {
		return audit_oper_id;
	}
	public void setAudit_oper_id(String audit_oper_id) {
		this.audit_oper_id = audit_oper_id;
	}
	public String getPru_delvery_finish_time() {
		return pru_delvery_finish_time;
	}
	public void setPru_delvery_finish_time(String pru_delvery_finish_time) {
		this.pru_delvery_finish_time = pru_delvery_finish_time;
	}
	public String getPru_type() {
		return pru_type;
	}
	public void setPru_type(String pru_type) {
		this.pru_type = pru_type;
	}
	public String getPru_stock_status() {
		return pru_stock_status;
	}
	public void setPru_stock_status(String pru_stock_status) {
		this.pru_stock_status = pru_stock_status;
	}
	public String getPru_order_id() {
		return pru_order_id;
	}
	public void setPru_order_id(String pru_order_id) {
		this.pru_order_id = pru_order_id;
	}
	public String getPru_status() {
		return pru_status;
	}
	public void setPru_status(String pru_status) {
		this.pru_status = pru_status;
	}
	public Double getDeposit() {
		return deposit;
	}
	public void setDeposit(Double deposit) {
		this.deposit = deposit;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getMod_time() {
		return mod_time;
	}
	public void setMod_time(String mod_time) {
		this.mod_time = mod_time;
	}
	public String getCreate_type() {
		return create_type;
	}
	public void setCreate_type(String create_type) {
		this.create_type = create_type;
	}
	public String getPru_order_name() {
		return pru_order_name;
	}
	public void setPru_order_name(String pru_order_name) {
		this.pru_order_name = pru_order_name;
	}
	
}
