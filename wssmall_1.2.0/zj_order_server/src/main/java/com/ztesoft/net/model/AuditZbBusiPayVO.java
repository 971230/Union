package com.ztesoft.net.model;

import java.io.Serializable;

/**
 * @Description 总部订单详情（字段还需要增减）   es_order_audit_zb_details
 * @author zhangJun
 * @date 2016年10月1日
 */
public class AuditZbBusiPayVO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String zb_id;              
	private String order_num;          
	private String order_id;           
	private String ess_order_id;       
	private String order_create_date;  
	private String order_status;       
	private String province;           
	private String city;               
	private String goods_type;         
	private String goods_name;         
	private String combo_name;         
	private String net_type;           
	private String phone_brand;        
	private String phone_model;        
	private String phone_colour;       
	private String activity_type;      
	private Integer store_receive_money;
	private String source_from;        
	private String batch_number;       
	private String create_date;        
	private String  create_user;
	public String getZb_id() {
		return zb_id;
	}
	public void setZb_id(String zb_id) {
		this.zb_id = zb_id;
	}
	public String getOrder_num() {
		return order_num;
	}
	public void setOrder_num(String order_num) {
		this.order_num = order_num;
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getEss_order_id() {
		return ess_order_id;
	}
	public void setEss_order_id(String ess_order_id) {
		this.ess_order_id = ess_order_id;
	}
	public String getOrder_create_date() {
		return order_create_date;
	}
	public void setOrder_create_date(String order_create_date) {
		this.order_create_date = order_create_date;
	}
	public String getOrder_status() {
		return order_status;
	}
	public void setOrder_status(String order_status) {
		this.order_status = order_status;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getGoods_type() {
		return goods_type;
	}
	public void setGoods_type(String goods_type) {
		this.goods_type = goods_type;
	}
	public String getGoods_name() {
		return goods_name;
	}
	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}
	public String getCombo_name() {
		return combo_name;
	}
	public void setCombo_name(String combo_name) {
		this.combo_name = combo_name;
	}
	public String getNet_type() {
		return net_type;
	}
	public void setNet_type(String net_type) {
		this.net_type = net_type;
	}
	public String getPhone_brand() {
		return phone_brand;
	}
	public void setPhone_brand(String phone_brand) {
		this.phone_brand = phone_brand;
	}
	public String getPhone_model() {
		return phone_model;
	}
	public void setPhone_model(String phone_model) {
		this.phone_model = phone_model;
	}
	public String getPhone_colour() {
		return phone_colour;
	}
	public void setPhone_colour(String phone_colour) {
		this.phone_colour = phone_colour;
	}
	public String getActivity_type() {
		return activity_type;
	}
	public void setActivity_type(String activity_type) {
		this.activity_type = activity_type;
	}
	public Integer getStore_receive_money() {
		return store_receive_money;
	}
	public void setStore_receive_money(Integer store_receive_money) {
		this.store_receive_money = store_receive_money;
	}
	public String getSource_from() {
		return source_from;
	}
	public void setSource_from(String source_from) {
		this.source_from = source_from;
	}
	public String getBatch_number() {
		return batch_number;
	}
	public void setBatch_number(String batch_number) {
		this.batch_number = batch_number;
	}
	public String getCreate_date() {
		return create_date;
	}
	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}
	public String getCreate_user() {
		return create_user;
	}
	public void setCreate_user(String create_user) {
		this.create_user = create_user;
	}          


}
