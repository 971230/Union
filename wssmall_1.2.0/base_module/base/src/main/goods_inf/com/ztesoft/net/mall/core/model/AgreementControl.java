package com.ztesoft.net.mall.core.model;

import java.io.Serializable;

public class AgreementControl implements Serializable {
   private String agreement_id;
   private String sub_control_type;
   private String control_type;
   private String control_lan_code;
   private double    control_value;
   private String control_name;
   private String create_date;
public String getAgreement_id() {
	return agreement_id;
}
public void setAgreement_id(String agreement_id) {
	this.agreement_id = agreement_id;
}
public String getSub_control_type() {
	return sub_control_type;
}
public void setSub_control_type(String sub_control_type) {
	this.sub_control_type = sub_control_type;
}
public String getControl_type() {
	return control_type;
}
public void setControl_type(String control_type) {
	this.control_type = control_type;
}
public String getControl_lan_code() {
	return control_lan_code;
}
public void setControl_lan_code(String control_lan_code) {
	this.control_lan_code = control_lan_code;
}
public double getControl_value() {
	return control_value;
}
public void setControl_value(double control_value) {
	this.control_value = control_value;
}
public String getControl_name() {
	return control_name;
}
public void setControl_name(String control_name) {
	this.control_name = control_name;
}
public String getCreate_date() {
	return create_date;
}
public void setCreate_date(String create_date) {
	this.create_date = create_date;
}
   
   
}
