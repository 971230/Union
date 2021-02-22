package com.ztesoft.net.mall.core.model.support;

import com.ztesoft.net.framework.database.NotDbField;

import java.io.Serializable;

/**
 * 配送地区费用表
 * @author kingapex
 *2010-3-28下午11:32:01
 */
public class TypeArea implements Serializable{
 		
	private String type_id	;
	private String area_id_group;	
	private String area_name_group;
	private String expressions;		
	private Integer  has_cod;
	private String config;
	private TypeAreaConfig typeAreaConfig;
	
	public String getArea_id_group() {
		return area_id_group;
	}
	public void setArea_id_group(String areaIdGroup) {
		area_id_group = areaIdGroup;
	}
	public String getArea_name_group() {
		return area_name_group;
	}
	public void setArea_name_group(String areaNameGroup) {
		area_name_group = areaNameGroup;
	}
	public String getExpressions() {
		return expressions;
	}
	public void setExpressions(String expressions) {
		this.expressions = expressions;
	}
	public Integer getHas_cod() {
		return has_cod;
	}
	public void setHas_cod(Integer hasCod) {
		has_cod = hasCod;
	}
	public String getConfig() {
		return config;
	}
	public void setConfig(String config) {
		this.config = config;
	}
	
	@NotDbField
	public TypeAreaConfig getTypeAreaConfig() {
		return typeAreaConfig;
	}
	public void setTypeAreaConfig(TypeAreaConfig typeAreaConfig) {
		this.typeAreaConfig = typeAreaConfig;
	}
	public String getType_id() {
		return type_id;
	}
	public void setType_id(String type_id) {
		this.type_id = type_id;
	}
	
	
}
