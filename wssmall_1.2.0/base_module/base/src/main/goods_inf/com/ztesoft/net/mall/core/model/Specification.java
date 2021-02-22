package com.ztesoft.net.mall.core.model;

import com.ztesoft.net.framework.database.NotDbField;
import com.ztesoft.net.mall.core.model.SpecValue;

import java.io.Serializable;
import java.util.List;

/**
 * 规格实体
 * @author kingapex
 *2010-3-6上午12:30:54
 */
public class Specification implements Serializable {
	private String spec_id;
	private String spec_name;
	private Integer spec_show_type;
	private Integer spec_type;
	private String spec_memo;
 
	private List<SpecValue> valueList;
	
	public String getSpec_id() {
		return spec_id;
	}
	public void setSpec_id(String specId) {
		spec_id = specId;
	}
	public String getSpec_name() {
		return spec_name;
	}
	public void setSpec_name(String specName) {
		spec_name = specName;
	}
	public Integer getSpec_show_type() {
		return spec_show_type;
	}
	public void setSpec_show_type(Integer specShowType) {
		spec_show_type = specShowType;
	}
	public Integer getSpec_type() {
		return spec_type;
	}
	public void setSpec_type(Integer specType) {
		spec_type = specType;
	}
	public String getSpec_memo() {
		return spec_memo;
	}
	public void setSpec_memo(String specMemo) {
		spec_memo = specMemo;
	}
	
	@NotDbField
	public List<SpecValue> getValueList() {
		return valueList;
	}
	public void setValueList(List<SpecValue> valueList) {
		this.valueList = valueList;
	}
	
 
 
	
	
}
