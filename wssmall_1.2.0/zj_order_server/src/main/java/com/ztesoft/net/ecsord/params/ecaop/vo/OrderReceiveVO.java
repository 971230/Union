package com.ztesoft.net.ecsord.params.ecaop.vo;

public class OrderReceiveVO {

	private String order_id;
	private String out_tid;
	private String tid_time;
	private String order_from_n;
	private String goods_name;
	private String paymoney;
	private String phone_num;
	private String flow_trace_n;
	private String order_city_code;
	private String order_county_code;
	private String orderType;//订单分配页面，区分意向单和其他单子
	private String is_work_custom;
	private String deal_name;//处理人员相关配置
	private String ship_name;
    private String  ship_tel;
     private String ship_addr;
    private String  acc_nbr;
    private String sell_price;
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getOut_tid() {
		return out_tid;
	}
	public void setOut_tid(String out_tid) {
		this.out_tid = out_tid;
	}
	public String getTid_time() {
		return tid_time;
	}
	public void setTid_time(String tid_time) {
		this.tid_time = tid_time;
	}
	public String getOrder_from_n() {
		return order_from_n;
	}
	public void setOrder_from_n(String order_from_n) {
		this.order_from_n = order_from_n;
	}
	public String getGoods_name() {
		return goods_name;
	}
	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}
	public String getPaymoney() {
		return paymoney;
	}
	public void setPaymoney(String paymoney) {
		this.paymoney = paymoney;
	}
	public String getPhone_num() {
		return phone_num;
	}
	public void setPhone_num(String phone_num) {
		this.phone_num = phone_num;
	}
	public String getFlow_trace_n() {
		return flow_trace_n;
	}
	public void setFlow_trace_n(String flow_trace_n) {
		this.flow_trace_n = flow_trace_n;
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
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
    public String getIs_work_custom() {
        return is_work_custom;
    }
    public void setIs_work_custom(String is_work_custom) {
        this.is_work_custom = is_work_custom;
    }
    public String getDeal_name() {
        return deal_name;
    }
    public void setDeal_name(String deal_name) {
        this.deal_name = deal_name;
    }
	public String getShip_name() {
		return ship_name;
	}
	public void setShip_name(String ship_name) {
		this.ship_name = ship_name;
	}
	public String getShip_tel() {
		return ship_tel;
	}
	public void setShip_tel(String ship_tel) {
		this.ship_tel = ship_tel;
	}
	public String getShip_addr() {
		return ship_addr;
	}
	public void setShip_addr(String ship_addr) {
		this.ship_addr = ship_addr;
	}
	public String getAcc_nbr() {
		return acc_nbr;
	}
	public void setAcc_nbr(String acc_nbr) {
		this.acc_nbr = acc_nbr;
	}
	public String getSell_price() {
		return sell_price;
	}
	public void setSell_price(String sell_price) {
		this.sell_price = sell_price;
	}
	
}
