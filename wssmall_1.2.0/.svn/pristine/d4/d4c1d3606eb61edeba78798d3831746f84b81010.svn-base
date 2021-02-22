package com.ztesoft.net.mall.core.model;

import java.io.Serializable;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;

public class OrderApply implements Serializable{
	private String order_apply_id;
	@ZteSoftCommentAnnotationParam(name="申请单类型",type="String",isNecessary="Y",desc="申请单类型")
	private String service_type;
	private String submit_num;
	private String apply_proof;
	@ZteSoftCommentAnnotationParam(name="原因",type="String",isNecessary="N",desc="原因")
	private String return_reson;
	@ZteSoftCommentAnnotationParam(name="问题描述",type="String",isNecessary="N",desc="问题描述")
	private String question_desc;
	private String upload_img_url;
	private String good_return_type;
	private String refund_type;
	private String refund_value;
	@ZteSoftCommentAnnotationParam(name="银行ID",type="String",isNecessary="N",desc="银行ID")
	private String bank_info;
	@ZteSoftCommentAnnotationParam(name="开户人姓名",type="String",isNecessary="N",desc="开户人姓名")
	private String account_holder_name;
	@ZteSoftCommentAnnotationParam(name="银行账号",type="String",isNecessary="N",desc="银行账号")
	private String bank_account;
	@ZteSoftCommentAnnotationParam(name="联系人姓名",type="String",isNecessary="N",desc="联系人姓名")
	private String linkman;
	@ZteSoftCommentAnnotationParam(name="联系人手机号码",type="String",isNecessary="N",desc="联系人手机号码")
	private String phone_num;
	private String user_id;
	private String create_time;
	@ZteSoftCommentAnnotationParam(name="省份ID",type="String",isNecessary="N",desc="省份ID")
	private String province_id;
	@ZteSoftCommentAnnotationParam(name="城市ID",type="String",isNecessary="N",desc="城市ID")
	private String city_id;
	@ZteSoftCommentAnnotationParam(name="区、县ID",type="String",isNecessary="N",desc="区、县ID")
	private String region_id;
	@ZteSoftCommentAnnotationParam(name="省份名称",type="String",isNecessary="N",desc="省份名称")
	private String province;
	@ZteSoftCommentAnnotationParam(name="城市名称",type="String",isNecessary="N",desc="城市名称")
	private String city;
	@ZteSoftCommentAnnotationParam(name="区、县名称",type="String",isNecessary="N",desc="区、县名称")
	private String region;
	@ZteSoftCommentAnnotationParam(name="联系人地址",type="String",isNecessary="N",desc="联系人地址")
	private String address;
	private String apply_state;
	private String a_order_item_id;//订单ID
	
	private String returned_account;
	private Integer returned_type;
	private Integer returned_kind;
	
	//新加字段
	@ZteSoftCommentAnnotationParam(name="物流费用",type="String",isNecessary="N",desc="物流费用")
	private Double ship_price = 0d;
	@ZteSoftCommentAnnotationParam(name="折旧价",type="String",isNecessary="N",desc="折旧价")
	private Double depreciation_price = 0d;
	private Double pay_price=0d;
	@ZteSoftCommentAnnotationParam(name="会员ID",type="String",isNecessary="N",desc="会员ID")
	private String member_id;
	
	public String getMember_id() {
		return member_id;
	}
	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}
	private String order_id;
	private String memeber_name;
	@NotDbField
	public String getMemeber_name() {
		return memeber_name;
	}
	public void setMemeber_name(String memeber_name) {
		this.memeber_name = memeber_name;
	}
	@NotDbField
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public Double getShip_price() {
		return ship_price;
	}
	public void setShip_price(Double ship_price) {
		this.ship_price = ship_price;
	}
	public Double getDepreciation_price() {
		return depreciation_price;
	}
	public void setDepreciation_price(Double depreciation_price) {
		this.depreciation_price = depreciation_price;
	}
	public Double getPay_price() {
		return pay_price;
	}
	public void setPay_price(Double pay_price) {
		this.pay_price = pay_price;
	}
	public String getReturned_account() {
		return returned_account;
	}
	public void setReturned_account(String returned_account) {
		this.returned_account = returned_account;
	}
	public Integer getReturned_type() {
		return returned_type;
	}
	public void setReturned_type(Integer returned_type) {
		this.returned_type = returned_type;
	}
	public Integer getReturned_kind() {
		return returned_kind;
	}
	public void setReturned_kind(Integer returned_kind) {
		this.returned_kind = returned_kind;
	}
	public String getOrder_apply_id() {
		return order_apply_id;
	}
	public void setOrder_apply_id(String order_apply_id) {
		this.order_apply_id = order_apply_id;
	}
	public String getService_type() {
		return service_type;
	}
	public void setService_type(String service_type) {
		this.service_type = service_type;
	}
	public String getSubmit_num() {
		return submit_num;
	}
	public void setSubmit_num(String submit_num) {
		this.submit_num = submit_num;
	}
	public String getApply_proof() {
		return apply_proof;
	}
	public void setApply_proof(String apply_proof) {
		this.apply_proof = apply_proof;
	}
	public String getReturn_reson() {
		return return_reson;
	}
	public void setReturn_reson(String return_reson) {
		this.return_reson = return_reson;
	}
	public String getQuestion_desc() {
		return question_desc;
	}
	public void setQuestion_desc(String question_desc) {
		this.question_desc = question_desc;
	}
	public String getUpload_img_url() {
		return upload_img_url;
	}
	public void setUpload_img_url(String upload_img_url) {
		this.upload_img_url = upload_img_url;
	}
	public String getGood_return_type() {
		return good_return_type;
	}
	public void setGood_return_type(String good_return_type) {
		this.good_return_type = good_return_type;
	}
	public String getRefund_type() {
		return refund_type;
	}
	public void setRefund_type(String refund_type) {
		this.refund_type = refund_type;
	}
	public String getRefund_value() {
		return refund_value;
	}
	public void setRefund_value(String refund_value) {
		this.refund_value = refund_value;
	}
	public String getBank_info() {
		return bank_info;
	}
	public void setBank_info(String bank_info) {
		this.bank_info = bank_info;
	}
	public String getAccount_holder_name() {
		return account_holder_name;
	}
	public void setAccount_holder_name(String account_holder_name) {
		this.account_holder_name = account_holder_name;
	}
	public String getBank_account() {
		return bank_account;
	}
	public void setBank_account(String bank_account) {
		this.bank_account = bank_account;
	}
	public String getLinkman() {
		return linkman;
	}
	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}
	public String getPhone_num() {
		return phone_num;
	}
	public void setPhone_num(String phone_num) {
		this.phone_num = phone_num;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getProvince_id() {
		return province_id;
	}
	public void setProvince_id(String province_id) {
		this.province_id = province_id;
	}
	public String getCity_id() {
		return city_id;
	}
	public void setCity_id(String city_id) {
		this.city_id = city_id;
	}
	public String getRegion_id() {
		return region_id;
	}
	public void setRegion_id(String region_id) {
		this.region_id = region_id;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getApply_state() {
		return apply_state;
	}
	public void setApply_state(String apply_state) {
		this.apply_state = apply_state;
	}
	public String getA_order_item_id() {
		return a_order_item_id;
	}
	public void setA_order_item_id(String a_order_item_id) {
		this.a_order_item_id = a_order_item_id;
	}
	
	

}
