package com.ztesoft.net.server.jsonserver.internetpojo;

public class InternetBusiOrder {
	private String serial_no;//序列号
	private String serial_time;//create_time
	private String source_system;//发起方系统标识
	private String source_system_type;//发起方系统标识小类
	private String mall_order_id;//商城系统单号
	private String order_prov_code;//order_prov_code
	private String order_city_code;//order_city_code
	private String buyer_message;//买家留言
	private String order_amount;//订单应收金额
	private String pay_amount;//订单实收金额
	private String discount_amount;//订单减免金额
	private String discount_reason;//订单减免原因
	private String ship_name;//联系人姓名
	private String ship_phone;//联系人电话
	private String ship_addr;//联系人地址
	private InternetBusiPayInfo pay_info;//支付信息
	private InternetBusiCustInfo cust_info;//客户信息
	private InternetBusiPhoneInfo phone_info;//开户号码信息
	private InternetBusiDeveloperInfo developer_info;//人员信息
	private InternetBusiNiceInfo nice_info;//靓号信息
	private InternetBusiSubProdInfo sub_prod_info;//子产品信息
	private InternetBusiContactInfo contact_info ; //收货人信息
	public String getSerial_no() {
		return serial_no;
	}
	public void setSerial_no(String serial_no) {
		this.serial_no = serial_no;
	}
	public String getSerial_time() {
		return serial_time;
	}
	public void setSerial_time(String serial_time) {
		this.serial_time = serial_time;
	}
	public String getSource_system() {
		return source_system;
	}
	public void setSource_system(String source_system) {
		this.source_system = source_system;
	}
	public String getSource_system_type() {
		return source_system_type;
	}
	public void setSource_system_type(String source_system_type) {
		this.source_system_type = source_system_type;
	}
	public String getMall_order_id() {
		return mall_order_id;
	}
	public void setMall_order_id(String mall_order_id) {
		this.mall_order_id = mall_order_id;
	}
	public String getOrder_prov_code() {
		return order_prov_code;
	}
	public void setOrder_prov_code(String order_prov_code) {
		this.order_prov_code = order_prov_code;
	}
	public String getOrder_city_code() {
		return order_city_code;
	}
	public void setOrder_city_code(String order_city_code) {
		this.order_city_code = order_city_code;
	}
	public String getBuyer_message() {
		return buyer_message;
	}
	public void setBuyer_message(String buyer_message) {
		this.buyer_message = buyer_message;
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
	public String getShip_name() {
		return ship_name;
	}
	public void setShip_name(String ship_name) {
		this.ship_name = ship_name;
	}
	public String getShip_phone() {
		return ship_phone;
	}
	public void setShip_phone(String ship_phone) {
		this.ship_phone = ship_phone;
	}
	public String getShip_addr() {
		return ship_addr;
	}
	public InternetBusiPayInfo getPay_info() {
		return pay_info;
	}
	public void setPay_info(InternetBusiPayInfo pay_info) {
		this.pay_info = pay_info;
	}
	public InternetBusiCustInfo getCust_info() {
		return cust_info;
	}
	public void setCust_info(InternetBusiCustInfo cust_info) {
		this.cust_info = cust_info;
	}
	public InternetBusiPhoneInfo getPhone_info() {
		return phone_info;
	}
	public void setPhone_info(InternetBusiPhoneInfo phone_info) {
		this.phone_info = phone_info;
	}
	public InternetBusiDeveloperInfo getDeveloper_info() {
		return developer_info;
	}
	public void setDeveloper_info(InternetBusiDeveloperInfo developer_info) {
		this.developer_info = developer_info;
	}
	public InternetBusiNiceInfo getNice_info() {
		return nice_info;
	}
	public void setNice_info(InternetBusiNiceInfo nice_info) {
		this.nice_info = nice_info;
	}
	public InternetBusiSubProdInfo getSub_prod_info() {
		return sub_prod_info;
	}
	public void setSub_prod_info(InternetBusiSubProdInfo sub_prod_info) {
		this.sub_prod_info = sub_prod_info;
	}
	public void setShip_addr(String ship_addr) {
		this.ship_addr = ship_addr;
	}
	public InternetBusiContactInfo getContact_info() {
		return contact_info;
	}
	public void setContact_info(InternetBusiContactInfo contact_info) {
		this.contact_info = contact_info;
	}

	
	
	
}
