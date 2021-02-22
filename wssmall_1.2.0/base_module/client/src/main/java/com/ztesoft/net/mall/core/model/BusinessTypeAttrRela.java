package com.ztesoft.net.mall.core.model;

/***
 * 业务类型属性关联对象
 * 
 * @author huang.xiaoming
 *
 */
public class BusinessTypeAttrRela implements java.io.Serializable {
	private static final long serialVersionUID = 8962412369613813865L;
	
	private String busi_type_rule_rela_id;
	private String busi_type_id;// 业务类型ID
	private String attr_id;//属性id
	
	public String getBusi_type_rule_rela_id() {
		return busi_type_rule_rela_id;
	}
	public void setBusi_type_rule_rela_id(String busi_type_rule_rela_id) {
		this.busi_type_rule_rela_id = busi_type_rule_rela_id;
	}
	public String getBusi_type_id() {
		return busi_type_id;
	}
	public void setBusi_type_id(String busi_type_id) {
		this.busi_type_id = busi_type_id;
	}
	public String getAttr_id() {
		return attr_id;
	}
	public void setAttr_id(String attr_id) {
		this.attr_id = attr_id;
	}
	
	
}