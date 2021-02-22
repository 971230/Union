package com.ztesoft.net.ecsord.params.ecaop.vo.generalorderinfo;

public class GeneralOrderInfo {
	private String out_order_id;
	private String order_time;
	private String order_type;
	private String order_id;
	private String acc_nbr;
	private OrderContacrInfo contact_info;
	private OrderCustInfoVO cust_info;
	private OrderGoodInfo good_info;
	private OrderPartenersInfo partners_info;
	private OrderDeveloperInfoVO developer_info;
	public String getOut_order_id() {
		return out_order_id;
	}
	public void setOut_order_id(String out_order_id) {
		this.out_order_id = out_order_id;
	}
	public String getOrder_time() {
		return order_time;
	}
	public void setOrder_time(String order_time) {
		this.order_time = order_time;
	}
	public String getOrder_type() {
		return order_type;
	}
	public void setOrder_type(String order_type) {
		this.order_type = order_type;
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getAcc_nbr() {
		return acc_nbr;
	}
	public void setAcc_nbr(String acc_nbr) {
		this.acc_nbr = acc_nbr;
	}
	public OrderContacrInfo getContact_info() {
		return contact_info;
	}
	public void setContact_info(OrderContacrInfo contact_info) {
		this.contact_info = contact_info;
	}
	public OrderCustInfoVO getCust_info() {
		return cust_info;
	}
	public void setCust_info(OrderCustInfoVO cust_info) {
		this.cust_info = cust_info;
	}
	public OrderGoodInfo getGood_info() {
		return good_info;
	}
	public void setGood_info(OrderGoodInfo good_info) {
		this.good_info = good_info;
	}
	public OrderPartenersInfo getPartners_info() {
		return partners_info;
	}
	public void setPartners_info(OrderPartenersInfo partners_info) {
		this.partners_info = partners_info;
	}
	public OrderDeveloperInfoVO getDeveloper_info() {
		return developer_info;
	}
	public void setDeveloper_info(OrderDeveloperInfoVO developer_info) {
		this.developer_info = developer_info;
	}
}
