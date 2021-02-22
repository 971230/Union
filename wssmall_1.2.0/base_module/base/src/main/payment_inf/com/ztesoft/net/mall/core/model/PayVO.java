package com.ztesoft.net.mall.core.model;

import java.io.Serializable;

public class PayVO implements Serializable{
	private String id;
	
	private String name;
	private float pay_fee;
	private String type;
	private String config;
	private String biref;
	private String source_from ;
	private String cfg_prop;
	private String tip_info;
	private String online_flag;
	private String bank_adss;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public float getPay_fee() {
		return pay_fee;
	}
	public void setPay_fee(float pay_fee) {
		this.pay_fee = pay_fee;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getConfig() {
		return config;
	}
	public void setConfig(String config) {
		this.config = config;
	}
	public String getBiref() {
		return biref;
	}
	public void setBiref(String biref) {
		this.biref = biref;
	}
	public String getSource_from() {
		return source_from;
	}
	public void setSource_from(String source_from) {
		this.source_from = source_from;
	}
	public String getCfg_prop() {
		return cfg_prop;
	}
	public void setCfg_prop(String cfg_prop) {
		this.cfg_prop = cfg_prop;
	}
	public String getTip_info() {
		return tip_info;
	}
	public void setTip_info(String tip_info) {
		this.tip_info = tip_info;
	}
	public String getOnline_flag() {
		return online_flag;
	}
	public void setOnline_flag(String online_flag) {
		this.online_flag = online_flag;
	}
	public String getBank_adss() {
		return bank_adss;
	}
	public void setBank_adss(String bank_adss) {
		this.bank_adss = bank_adss;
	}
	
}
