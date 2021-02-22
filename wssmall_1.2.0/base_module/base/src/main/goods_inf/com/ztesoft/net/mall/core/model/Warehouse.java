package com.ztesoft.net.mall.core.model;

import java.io.Serializable;

/*
 * 仓储管理
 */
public class Warehouse implements Serializable {
	private String house_id;                   //仓库ID
	private String house_name;                 //仓库名称
	private String house_code;                 //仓库编号
	private String attr_inline_type;           //仓库属性( 线上T  , 线下F)
	private String deliver_goods_attr;         //发货属性( 发货仓库T  , 备货仓库F)
	private String attribution;                //仓库类型 1自营仓库,2营业厅,3代理商
	private String shop;                       //仓库对应店铺
	private String contact_name;               //联系人姓名
	private String sex;                        //性别
	private String address;                    //地址
	private String zip_code;                   //邮编
	private String telephone;                  //电话
	private String phone_num;                  //手机
	private String threshold;                  //阀值
	private String remarks;                    //备注
	private int weight;                        //权重
	private String create_time;                //创建时间
	private String user_id;                    //仓库归属人
	private String status;                     //仓库状态00A-正常； 00X-停用
	private String status_date;                //状态时间
	private String scope_code;                 //配送范围编码 多个范围用逗号分隔
	private String scope_name;                 //配送范围名称 多个范围用逗号分隔
	private int    priority; // 联通仓库功能之前没有，后来仓库系统需要，加上去的（2015-07-13）
	private String p_house_id; // 联通仓库功能之前没有，后来仓库系统需要，加上去的（2015-07-13）
	private String house_type; // 联通仓库功能之前没有，后来仓库系统需要，加上去的（2015-07-13）  //"PHY物理仓；LOG逻辑仓：CET仓库中心
	private String 	attr; // 联通仓库功能之前没有，后来仓库系统需要，加上去的（2015-07-13）
	private String  comp_code; //货主（2016-03-11）
	private String  source_from; // 联通仓库功能之前没有，后来仓库系统需要，加上去的（2015-07-13）
	
	
	public String getHouse_id() {
		return house_id;
	}
	public void setHouse_id(String house_id) {
		this.house_id = house_id;
	}
	public String getHouse_name() {
		return house_name;
	}
	public void setHouse_name(String house_name) {
		this.house_name = house_name;
	}
	
	public String getHouse_code() {
		return house_code;
	}
	public void setHouse_code(String house_code) {
		this.house_code = house_code;
	}
	public String getAttr_inline_type() {
		return attr_inline_type;
	}
	public void setAttr_inline_type(String attr_inline_type) {
		this.attr_inline_type = attr_inline_type;
	}
	public String getDeliver_goods_attr() {
		return deliver_goods_attr;
	}
	public void setDeliver_goods_attr(String deliver_goods_attr) {
		this.deliver_goods_attr = deliver_goods_attr;
	}
	public String getAttribution() {
		return attribution;
	}
	public void setAttribution(String attribution) {
		this.attribution = attribution;
	}
	public String getShop() {
		return shop;
	}
	public void setShop(String shop) {
		this.shop = shop;
	}
	public String getContact_name() {
		return contact_name;
	}
	public void setContact_name(String contact_name) {
		this.contact_name = contact_name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getZip_code() {
		return zip_code;
	}
	public void setZip_code(String zip_code) {
		this.zip_code = zip_code;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getPhone_num() {
		return phone_num;
	}
	public void setPhone_num(String phone_num) {
		this.phone_num = phone_num;
	}
	public String getThreshold() {
		return threshold;
	}
	public void setThreshold(String threshold) {
		this.threshold = threshold;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStatus_date() {
		return status_date;
	}
	public void setStatus_date(String status_date) {
		this.status_date = status_date;
	}
	public String getScope_code() {
		return scope_code;
	}
	public void setScope_code(String scope_code) {
		this.scope_code = scope_code;
	}
	public String getScope_name() {
		return scope_name;
	}
	public void setScope_name(String scope_name) {
		this.scope_name = scope_name;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	public String getP_house_id() {
		return p_house_id;
	}
	public void setP_house_id(String p_house_id) {
		this.p_house_id = p_house_id;
	}
	public String getHouse_type() {
		return house_type;
	}
	public void setHouse_type(String house_type) {
		this.house_type = house_type;
	}
	public String getAttr() {
		return attr;
	}
	public void setAttr(String attr) {
		this.attr = attr;
	}
	public String getComp_code() {
		return comp_code;
	}
	public void setComp_code(String comp_code) {
		this.comp_code = comp_code;
	}
	public String getSource_from() {
		return source_from;
	}
	public void setSource_from(String source_from) {
		this.source_from = source_from;
	}
	
}
