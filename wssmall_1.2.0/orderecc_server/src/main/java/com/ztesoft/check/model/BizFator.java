package com.ztesoft.check.model;

import java.io.Serializable;

public class BizFator implements Serializable{
	private String order_id;
	private String need_open_act;
	private String user_flag;
	private String goods_type;
	private String busi_type;
	private String net_type;
	private String wm_isreservation_from;
	private String flow_id;
	
	//要过滤的业务因子
	private String delivery_method;
	private String order_source;
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getNeed_open_act() {
		return need_open_act;
	}
	public void setNeed_open_act(String need_open_act) {
		this.need_open_act = need_open_act;
	}
	public String getUser_flag() {
		return user_flag;
	}
	public void setUser_flag(String user_flag) {
		this.user_flag = user_flag;
	}
	public String getGoods_type() {
		return goods_type;
	}
	public void setGoods_type(String goods_type) {
		this.goods_type = goods_type;
	}
	public String getBusi_type() {
		return busi_type;
	}
	public void setBusi_type(String busi_type) {
		this.busi_type = busi_type;
	}
	public String getNet_type() {
		return net_type;
	}
	public void setNet_type(String net_type) {
		this.net_type = net_type;
	}
	public String getWm_isreservation_from() {
		return wm_isreservation_from;
	}
	public void setWm_isreservation_from(String wm_isreservation_from) {
		this.wm_isreservation_from = wm_isreservation_from;
	}
	public String getFlow_id() {
		return flow_id;
	}
	public void setFlow_id(String flow_id) {
		this.flow_id = flow_id;
	}
	public String getDelivery_method() {
		return delivery_method;
	}
	public void setDelivery_method(String delivery_method) {
		this.delivery_method = delivery_method;
	}
	public String getOrder_source() {
		return order_source;
	}
	public void setOrder_source(String order_source) {
		this.order_source = order_source;
	}
	
	
}
