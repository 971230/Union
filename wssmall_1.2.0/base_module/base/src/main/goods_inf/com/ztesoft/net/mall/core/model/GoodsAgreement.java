package com.ztesoft.net.mall.core.model;

public class GoodsAgreement {
   private String agreement_id;//协议ID
   private String agreement_code;//协议编码
   private String agreement_name;//协议名称
   private String supplier_id ;//供应商
   private String pay_type; //支付方式
   private int    flow_id;//流程ID
   private int    order_manager;//订单管理员
   private String portion;//份额控制
   private String sub_portion;//子控制类型
   private String agreement_file;//协议文件
   private String status;//状态
   private String create_time;
   
public String getAgreement_id() {
	return agreement_id;
}
public void setAgreement_id(String agreement_id) {
	this.agreement_id = agreement_id;
}
public String getAgreement_code() {
	return agreement_code;
}
public void setAgreement_code(String agreement_code) {
	this.agreement_code = agreement_code;
}
public String getAgreement_name() {
	return agreement_name;
}
public void setAgreement_name(String agreement_name) {
	this.agreement_name = agreement_name;
}

public String getSupplier_id() {
	return supplier_id;
}
public void setSupplier_id(String supplier_id) {
	this.supplier_id = supplier_id;
}
public String getPay_type() {
	return pay_type;
}
public void setPay_type(String pay_type) {
	this.pay_type = pay_type;
}
public int getFlow_id() {
	return flow_id;
}
public void setFlow_id(int flow_id) {
	this.flow_id = flow_id;
}
public int getOrder_manager() {
	return order_manager;
}
public void setOrder_manager(int order_manager) {
	this.order_manager = order_manager;
}
public String getPortion() {
	return portion;
}
public void setPortion(String portion) {
	this.portion = portion;
}
public String getAgreement_file() {
	return agreement_file;
}
public void setAgreement_file(String agreement_file) {
	this.agreement_file = agreement_file;
}
public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}
public String getSub_portion() {
	return sub_portion;
}
public void setSub_portion(String sub_portion) {
	this.sub_portion = sub_portion;
}
public String getCreate_time() {
	return create_time;
}
public void setCreate_time(String create_time) {
	this.create_time = create_time;
}

   
}
