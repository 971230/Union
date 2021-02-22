package com.ztesoft.net.mall.core.model;


/**
 * 合约机实体
 * 
 * @author wui 2010-4-6上午11:11:27
 */
public class Contract implements java.io.Serializable {
	// 合约名称、终端名称、用户号码、客户名称、证件类型、证件号码、支付金额

	private String offer_name;
	private String terminal_code;
	private String acc_nbr;
	private String cust_name;
	private String certi_type;
	private String certi_number;
	private String create_date;
	private String terminal_name;
	private String status_date;
	private String order_id;
	private String accept_id;
	private String crm_offer_id;
	private Double crm_fee;
	private Double sec_fee;
	private String state;
	private String crm_order_id;
	
	//冗余字段，方便统计处理
	private String lan_id;
	private String lan_name;
	private String username;
	private String userid;
	
	//合约计划id,合约计划名称
	private String plan_id;
	private String plan_name;
	
	private String first_userid; //冗余字段、统计使用，追踪一级分销商来源
	
	public String getOffer_name() {
		return offer_name;
	}
	public void setOffer_name(String offer_name) {
		this.offer_name = offer_name;
	}
	public String getTerminal_code() {
		return terminal_code;
	}
	public void setTerminal_code(String terminal_code) {
		this.terminal_code = terminal_code;
	}
	public String getAcc_nbr() {
		return acc_nbr;
	}
	public void setAcc_nbr(String acc_nbr) {
		this.acc_nbr = acc_nbr;
	}
	public String getCust_name() {
		return cust_name;
	}
	public void setCust_name(String cust_name) {
		this.cust_name = cust_name;
	}
	public String getCerti_type() {
		return certi_type;
	}
	public void setCerti_type(String certi_type) {
		this.certi_type = certi_type;
	}
	public String getCerti_number() {
		return certi_number;
	}
	public void setCerti_number(String certi_number) {
		this.certi_number = certi_number;
	}
	public String getCreate_date() {
		return create_date;
	}
	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}
	public String getTerminal_name() {
		return terminal_name;
	}
	public void setTerminal_name(String terminal_name) {
		this.terminal_name = terminal_name;
	}
	public String getStatus_date() {
		return status_date;
	}
	public void setStatus_date(String status_date) {
		this.status_date = status_date;
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getAccept_id() {
		return accept_id;
	}
	public void setAccept_id(String accept_id) {
		this.accept_id = accept_id;
	}
	public String getCrm_offer_id() {
		return crm_offer_id;
	}
	public void setCrm_offer_id(String crm_offer_id) {
		this.crm_offer_id = crm_offer_id;
	}
	
	public Double getCrm_fee() {
		return crm_fee;
	}
	public void setCrm_fee(Double crm_fee) {
		this.crm_fee = crm_fee;
	}
	public Double getSec_fee() {
		return sec_fee;
	}
	public void setSec_fee(Double sec_fee) {
		this.sec_fee = sec_fee;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCrm_order_id() {
		return crm_order_id;
	}
	public void setCrm_order_id(String crm_order_id) {
		this.crm_order_id = crm_order_id;
	}
	public String getLan_id() {
		return lan_id;
	}
	public void setLan_id(String lan_id) {
		this.lan_id = lan_id;
	}
	public String getLan_name() {
		return lan_name;
	}
	public void setLan_name(String lan_name) {
		this.lan_name = lan_name;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getPlan_id() {
		return plan_id;
	}
	public void setPlan_id(String plan_id) {
		this.plan_id = plan_id;
	}
	public String getPlan_name() {
		return plan_name;
	}
	public void setPlan_name(String plan_name) {
		this.plan_name = plan_name;
	}
	public String getFirst_userid() {
		return first_userid;
	}
	public void setFirst_userid(String first_userid) {
		this.first_userid = first_userid;
	}
	

}