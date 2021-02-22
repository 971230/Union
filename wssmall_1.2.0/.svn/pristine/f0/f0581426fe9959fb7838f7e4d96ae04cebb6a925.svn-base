package com.ztesoft.net.mall.core.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * 商品参数
 * @author apexking
 *
 */
public class GoodsParam implements Serializable {
	
	private String name; //参数名
	private String value; //参数值
	private String ename;//参数英文名
	private String attrvaltype;//参数取值类型
	private String attrtype;//参数类型(attrtype)[商品参数(goodsparam)、关系参数（relparam)]
	private String attrcode;//参数选择取值(attrcode)（下拉框需要展示该字段）
	private String attrdefvalue;//参数缺省值(attrdefvalue)
	private String required;
	
	private List valueList; //多个商品的参数值，用于商品对比 
	
	private List dropdownValues;
	
	
	public void addValue(String _value){
		if(valueList == null)  valueList  = new ArrayList();
		valueList.add(_value);
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public List getValueList() {
		return valueList;
	}
	public void setValueList(List valueList) {
		this.valueList = valueList;
	}

	public String getEname() {
		return ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}

	public String getAttrvaltype() {
		return attrvaltype;
	}

	public void setAttrvaltype(String attrvaltype) {
		this.attrvaltype = attrvaltype;
	}

	public String getAttrtype() {
		return attrtype;
	}

	public void setAttrtype(String attrtype) {
		this.attrtype = attrtype;
	}

	public String getAttrcode() {
		return attrcode;
	}

	public void setAttrcode(String attrcode) {
		this.attrcode = attrcode;
	}

	public String getAttrdefvalue() {
		return attrdefvalue;
	}

	public void setAttrdefvalue(String attrdefvalue) {
		this.attrdefvalue = attrdefvalue;
	}

	public String getRequired() {
		return required;
	}

	public void setRequired(String required) {
		this.required = required;
	}

	public List getDropdownValues() {
		return dropdownValues;
	}

	public void setDropdownValues(List dropdownValues) {
		this.dropdownValues = dropdownValues;
	}
	
	
	
}
