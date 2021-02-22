package com.ztesoft.net.server.jsonserver.hlwrhywpojo;

import java.util.List;


public class InternetGroupBusiOrder {
	private String serial_no;//序列号
	private String create_time;//create_time
	private String source_system;//发起方系统标识
	private String source_system_type;
	private String receive_system;//接收方
	private String mall_order_id;//商城系统单号
	private String order_province_code;//order_prov_code
	private String order_city_code;//order_city_code
	private String order_county_code ; //订单归属县分
	private String order_amount;//订单应收金额
	private String pay_amount;//订单实收金额
	private String discount_amount;//订单减免金额
	private String discount_reason;//订单减免原因
	private String buyer_message;//买家留言
	private String order_remark;//订单备注
	private InterGroupBusiOrderContactInfo contact_info;//联系人信息
	private List<InterGroupBusiOrderGoodsInfo> goods_info;//商品信息节点
	private InterGroupBusiOrderDeveloperInfo developer_info;//人员信息节点
	private List<InterGroupBusiOrderBroadInfo> broad_info;//固网信息节点
	private InterGroupBusiOrderCustInfo cust_info;//客户信息节点
	private InterGroupBusiOrderPayInfo pay_info;//支付信息节点
	private List<InterGroupBusiOrderSubProdInfo> sub_prod_info;//关联商品信息节点
	private InterGroupBusiOrderSubsOtherInfo subs_other_info;//集客业务专属信息节点
	private String result_url;//异步通知url
	private String syn_mode;
	private String kingcard_status;
	private List<InterGroupBusiOrderPhoneInfo> phone_info;//移网信息节点
	private String rel_order_id;
	
	public String getSource_system_type() {
		return source_system_type;
	}
	public void setSource_system_type(String source_system_type) {
		this.source_system_type = source_system_type;
	}
	public String getSerial_no() {
		return serial_no;
	}
	public void setSerial_no(String serial_no) {
		this.serial_no = serial_no;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getSource_system() {
		return source_system;
	}
	public void setSource_system(String source_system) {
		this.source_system = source_system;
	}
	public String getMall_order_id() {
		return mall_order_id;
	}
	public void setMall_order_id(String mall_order_id) {
		this.mall_order_id = mall_order_id;
	}
	public String getReceive_system() {
		return receive_system;
	}
	public void setReceive_system(String receive_system) {
		this.receive_system = receive_system;
	}
	public String getOrder_province_code() {
		return order_province_code;
	}
	public void setOrder_province_code(String order_province_code) {
		this.order_province_code = order_province_code;
	}
	public String getOrder_city_code() {
		return order_city_code;
	}
	public void setOrder_city_code(String order_city_code) {
		this.order_city_code = order_city_code;
	}
	public String getOrder_county_code() {
		return order_county_code;
	}
	public void setOrder_county_code(String order_county_code) {
		this.order_county_code = order_county_code;
	}
	public String getOrder_amount() {
		return order_amount;
	}
	public void setOrder_amount(String order_amount) {
		this.order_amount = order_amount;
	}
	public String getPay_amount() {
		return pay_amount;
	}
	public void setPay_amount(String pay_amount) {
		this.pay_amount = pay_amount;
	}
	public String getDiscount_amount() {
		return discount_amount;
	}
	public void setDiscount_amount(String discount_amount) {
		this.discount_amount = discount_amount;
	}
	public String getDiscount_reason() {
		return discount_reason;
	}
	public void setDiscount_reason(String discount_reason) {
		this.discount_reason = discount_reason;
	}
	public String getBuyer_message() {
		return buyer_message;
	}
	public void setBuyer_message(String buyer_message) {
		this.buyer_message = buyer_message;
	}
	public String getOrder_remark() {
		return order_remark;
	}
	public void setOrder_remark(String order_remark) {
		this.order_remark = order_remark;
	}
	public InterGroupBusiOrderContactInfo getContact_info() {
		return contact_info;
	}
	public void setContact_info(InterGroupBusiOrderContactInfo contact_info) {
		this.contact_info = contact_info;
	}
	public List<InterGroupBusiOrderGoodsInfo> getGoods_info() {
		return goods_info;
	}
	public void setGoods_info(List<InterGroupBusiOrderGoodsInfo> goods_info) {
		this.goods_info = goods_info;
	}
	public InterGroupBusiOrderDeveloperInfo getDeveloper_info() {
		return developer_info;
	}
	public void setDeveloper_info(InterGroupBusiOrderDeveloperInfo developer_info) {
		this.developer_info = developer_info;
	}
	public List<InterGroupBusiOrderBroadInfo> getBroad_info() {
		return broad_info;
	}
	public void setBroad_info(List<InterGroupBusiOrderBroadInfo> broad_info) {
		this.broad_info = broad_info;
	}
	public InterGroupBusiOrderCustInfo getCust_info() {
		return cust_info;
	}
	public void setCust_info(InterGroupBusiOrderCustInfo cust_info) {
		this.cust_info = cust_info;
	}
	public InterGroupBusiOrderPayInfo getPay_info() {
		return pay_info;
	}
	public void setPay_info(InterGroupBusiOrderPayInfo pay_info) {
		this.pay_info = pay_info;
	}
	public List<InterGroupBusiOrderSubProdInfo> getSub_prod_info() {
		return sub_prod_info;
	}
	public void setSub_prod_info(List<InterGroupBusiOrderSubProdInfo> sub_prod_info) {
		this.sub_prod_info = sub_prod_info;
	}
	public InterGroupBusiOrderSubsOtherInfo getSubs_other_info() {
		return subs_other_info;
	}
	public void setSubs_other_info(InterGroupBusiOrderSubsOtherInfo subs_other_info) {
		this.subs_other_info = subs_other_info;
	}
	public String getResult_url() {
		return result_url;
	}
	public void setResult_url(String result_url) {
		this.result_url = result_url;
	}
	public List<InterGroupBusiOrderPhoneInfo> getPhone_info() {
		return phone_info;
	}
	public void setPhone_info(List<InterGroupBusiOrderPhoneInfo> phone_info) {
		this.phone_info = phone_info;
	}
	public String getSyn_mode() {
		return syn_mode;
	}
	public void setSyn_mode(String syn_mode) {
		this.syn_mode = syn_mode;
	}
	public String getKingcard_status() {
		return kingcard_status;
	}
	public void setKingcard_status(String kingcard_status) {
		this.kingcard_status = kingcard_status;
	}
    public String getRel_order_id() {
        return rel_order_id;
    }
    public void setRel_order_id(String rel_order_id) {
        this.rel_order_id = rel_order_id;
    }
	
}
