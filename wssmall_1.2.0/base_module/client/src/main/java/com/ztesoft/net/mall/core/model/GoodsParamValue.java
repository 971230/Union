package com.ztesoft.net.mall.core.model;

import java.io.Serializable;

public class GoodsParamValue implements Serializable {

	private String goods_id;
	private String param_code;
	private String param_name;
	private String param_value_code;
	private String param_value_desc;
	private String type;
	public String getGoods_id() {
		return goods_id;
	}
	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}
	public String getParam_code() {
		return param_code;
	}
	public void setParam_code(String param_code) {
		this.param_code = param_code;
	}
	public String getParam_name() {
		return param_name;
	}
	public void setParam_name(String param_name) {
		this.param_name = param_name;
	}
	public String getParam_value_code() {
		return param_value_code;
	}
	public void setParam_value_code(String param_value_code) {
		this.param_value_code = param_value_code;
	}
	public String getParam_value_desc() {
		return param_value_desc;
	}
	public void setParam_value_desc(String param_value_desc) {
		this.param_value_desc = param_value_desc;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}
