package com.ztesoft.net.mall.core.model;

import java.io.Serializable;

/**
 * 规格值
 * @author kingapex
 *2010-3-7上午11:41:10
 */
public class SpecValue implements Serializable {
	private String spec_name;
	private String spec_value_id;
	private String  spec_id;
	private String spec_value;
	private String spec_image;
	private Integer spec_order;
	private Integer spec_type;
	
	
	 
	public String getSpec_name() {
		return spec_name;
	}
	public void setSpec_name(String spec_name) {
		this.spec_name = spec_name;
	}
	public Integer getSpec_type() {
		return spec_type;
	}
	public void setSpec_type(Integer specType) {
		spec_type = specType;
	}
	public String getSpec_value_id() {
		return spec_value_id;
	}
	public void setSpec_value_id(String specValueId) {
		spec_value_id = specValueId;
	}
	public String getSpec_id() {
		return spec_id;
	}
	public void setSpec_id(String specId) {
		spec_id = specId;
	}
	public String getSpec_value() {
		return spec_value;
	}
	public void setSpec_value(String specValue) {
		spec_value = specValue;
	}
	public String getSpec_image() {
		return spec_image;
	}
	public void setSpec_image(String specImage) {
		spec_image = specImage;
	}
	public Integer getSpec_order() {
		return spec_order;
	}
	public void setSpec_order(Integer specOrder) {
		spec_order = specOrder;
	}
	
	
}
