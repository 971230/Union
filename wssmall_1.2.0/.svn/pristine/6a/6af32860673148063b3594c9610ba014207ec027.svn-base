package com.ztesoft.net.mall.core.model;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;
import com.ztesoft.net.mall.core.model.support.DlyTypeConfig;

import java.util.List;

/**
 * 配送方式
 * 
 * @author kingapex 2010-3-28下午06:03:46
 */
public class DlyType implements java.io.Serializable {

	@ZteSoftCommentAnnotationParam(name="配送方式ID",type="String",isNecessary="N",desc="配送方式ID")
	private String type_id;
	@ZteSoftCommentAnnotationParam(name="配送方式名称",type="String",isNecessary="N",desc="配送方式名称")
	private String name;
	private Integer protect;
	private Float protect_rate;
	@ZteSoftCommentAnnotationParam(name="配送金额",type="String",isNecessary="N",desc="配送金额")
	private Float min_price;
	
	private Integer has_cod;
	
	private String corp_id;
	private Integer ordernum;
	private Integer disabled;
	private Integer is_same;
	private String detail;
	private String config;
	private String  expressions;
	
	private String json;
	
	//地区配置对象
	private DlyTypeConfig typeConfig;

	//地区费用配置列表
	private List typeAreaList;
	
	
	private Double price; //需要对订单增加的价格
	
	
	@NotDbField
	public String getJson(){
		
		return this.json;
	}
	
	
	public void setJson(String json) {
		this.json = json;
	}


	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public Float getMin_price() {
		return min_price;
	}

	public void setMin_price(Float min_price) {
		this.min_price = min_price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Float getProtect_rate() {
		return protect_rate;
	}

	public void setProtect_rate(Float protect_rate) {
		this.protect_rate = protect_rate;
	}


	public Integer getProtect() {
		return protect==null?0:protect;
	}

	public void setProtect(Integer protect) {
		this.protect = protect;
	}

	public Integer getHas_cod() {
		return has_cod;
	}

	public void setHas_cod(Integer hasCod) {
		has_cod = hasCod;
	}

	public String getCorp_id() {
		return corp_id;
	}

	public void setCorp_id(String corpId) {
		corp_id = corpId;
	}

	public Integer getOrdernum() {
		return ordernum;
	}

	public void setOrdernum(Integer ordernum) {
		this.ordernum = ordernum;
	}

	public Integer getDisabled() {
		return disabled;
	}

	public void setDisabled(Integer disabled) {
		this.disabled = disabled;
	}

	public Integer getIs_same() {
		return is_same;
	}

	public void setIs_same(Integer isSame) {
		is_same = isSame;
	}

 
	public String getExpressions() {
		return expressions;
	}

	public void setExpressions(String expressions) {
		this.expressions = expressions;
	}

	public String getConfig() {
		return config;
	}

	public void setConfig(String config) {
		this.config = config;
	}

	@NotDbField
	public DlyTypeConfig getTypeConfig() {
		return typeConfig;
	}

	public void setTypeConfig(DlyTypeConfig typeConfig) {
		this.typeConfig = typeConfig;
	}

	@NotDbField
	public List getTypeAreaList() {
		return typeAreaList;
	}

	public void setTypeAreaList(List typeAreaList) {
		this.typeAreaList = typeAreaList;
	}

	@NotDbField
	public Double getPrice() {
		return price;
	}


	public void setPrice(Double price) {
		this.price = price;
	}


	public String getType_id() {
		return type_id;
	}


	public void setType_id(String type_id) {
		this.type_id = type_id;
	}
	
	

}