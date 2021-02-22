package com.ztesoft.net.model;

import java.io.Serializable;

/**
 * 订单批量导入
 * @author MCC
 *
 */
public class OrderBatchImport implements Serializable{

	private static final long serialVersionUID = -8370434690639454562L;
	
	private String out_tid;//外部订单号
	private String create_time;//创建时间
	private String acc_nbr;//用户号码
	private String cust_name;//用户姓名
	private String special_busi_type;//业务类型
	private String is_realname;//是否实名
	private String order_city_code;//地市
	private String certi_type;//证件类型
	private String certi_num;//证件号码
	private String is_old;//新老用户
	private String offer_eff_type;//首月
	private String pay_method;//支付方式
	private String prod_offer_type;//商品类型
	private String prod_offer_code;//商品编码
	private String prod_offer_name;//商品名称
	private String pay_money;//商城实收
	private String contract_month ;//合约期
	private String ship_name;//收货人姓名
	private String ship_addr;//收货人地址
	private String ship_phone;//收货人电话
	private String ess_money;//营业款
	private String logi_no ;//物流单号
	private String invoice_no; //发票号码
	private String terminal_num;//手机串号
	private String buyer_message;//买家留言
	private String service_remarks;//订单备注
	private String import_status;//接收状态 1: 导入成功 0: 导入失败
	private String fail_reason;//失败的原因
	private String import_id;//主键
	private String order_from ;//订单来源
	
	private String start_time;//开始时间
	private String end_time;//结束时间
	
	
	public String getStart_time() {
		return start_time;
	}
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}
	public String getEnd_time() {
		return end_time;
	}
	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}
	public String getOut_tid() {
		return out_tid;
	}
	public void setOut_tid(String out_tid) {
		this.out_tid = out_tid;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
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
	public String getSpecial_busi_type() {
		return special_busi_type;
	}
	public void setSpecial_busi_type(String special_busi_type) {
		this.special_busi_type = special_busi_type;
	}
	public String getIs_realname() {
		return is_realname;
	}
	public void setIs_realname(String is_realname) {
		this.is_realname = is_realname;
	}
	public String getOrder_city_code() {
		return order_city_code;
	}
	public void setOrder_city_code(String order_city_code) {
		this.order_city_code = order_city_code;
	}
	public String getCerti_type() {
		return certi_type;
	}
	public void setCerti_type(String certi_type) {
		this.certi_type = certi_type;
	}
	public String getCerti_num() {
		return certi_num;
	}
	public void setCerti_num(String certi_num) {
		this.certi_num = certi_num;
	}
	public String getIs_old() {
		return is_old;
	}
	public void setIs_old(String is_old) {
		this.is_old = is_old;
	}
	public String getOffer_eff_type() {
		return offer_eff_type;
	}
	public void setOffer_eff_type(String offer_eff_type) {
		this.offer_eff_type = offer_eff_type;
	}
	public String getPay_method() {
		return pay_method;
	}
	public void setPay_method(String pay_method) {
		this.pay_method = pay_method;
	}
	public String getProd_offer_type() {
		return prod_offer_type;
	}
	public void setProd_offer_type(String prod_offer_type) {
		this.prod_offer_type = prod_offer_type;
	}
	public String getProd_offer_code() {
		return prod_offer_code;
	}
	public void setProd_offer_code(String prod_offer_code) {
		this.prod_offer_code = prod_offer_code;
	}
	public String getProd_offer_name() {
		return prod_offer_name;
	}
	public void setProd_offer_name(String prod_offer_name) {
		this.prod_offer_name = prod_offer_name;
	}
	public String getPay_money() {
		return pay_money;
	}
	public void setPay_money(String pay_money) {
		this.pay_money = pay_money;
	}
	public String getContract_month() {
		return contract_month;
	}
	public void setContract_month(String contract_month) {
		this.contract_month = contract_month;
	}
	public String getShip_name() {
		return ship_name;
	}
	public void setShip_name(String ship_name) {
		this.ship_name = ship_name;
	}
	public String getShip_addr() {
		return ship_addr;
	}
	public void setShip_addr(String ship_addr) {
		this.ship_addr = ship_addr;
	}
	public String getShip_phone() {
		return ship_phone;
	}
	public void setShip_phone(String ship_phone) {
		this.ship_phone = ship_phone;
	}
	public String getEss_money() {
		return ess_money;
	}
	public void setEss_money(String ess_money) {
		this.ess_money = ess_money;
	}
	public String getLogi_no() {
		return logi_no;
	}
	public void setLogi_no(String logi_no) {
		this.logi_no = logi_no;
	}
	public String getInvoice_no() {
		return invoice_no;
	}
	public void setInvoice_no(String invoice_no) {
		this.invoice_no = invoice_no;
	}
	public String getTerminal_num() {
		return terminal_num;
	}
	public void setTerminal_num(String terminal_num) {
		this.terminal_num = terminal_num;
	}
	public String getBuyer_message() {
		return buyer_message;
	}
	public void setBuyer_message(String buyer_message) {
		this.buyer_message = buyer_message;
	}
	public String getService_remarks() {
		return service_remarks;
	}
	public void setService_remarks(String service_remarks) {
		this.service_remarks = service_remarks;
	}
	public String getImport_status() {
		return import_status;
	}
	public void setImport_status(String import_status) {
		this.import_status = import_status;
	}
	public String getFail_reason() {
		return fail_reason;
	}
	public void setFail_reason(String fail_reason) {
		this.fail_reason = fail_reason;
	}
	public String getImport_id() {
		return import_id;
	}
	public void setImport_id(String import_id) {
		this.import_id = import_id;
	}
	public String getOrder_from() {
		return order_from;
	}
	public void setOrder_from(String order_from) {
		this.order_from = order_from;
	}
	
	
}
