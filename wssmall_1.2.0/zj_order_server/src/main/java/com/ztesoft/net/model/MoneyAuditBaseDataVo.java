package com.ztesoft.net.model;

import java.io.Serializable;
/**
 * @Description财务稽核基础数据对象
 * @author  zhangJun
 * @date    2016年10月18日
 */
public class MoneyAuditBaseDataVo implements Serializable {

	private static final long serialVersionUID = 1L;
	private String		  order_id;
    private String        order_type_return;
	private String		  order_from;
    private String        pay_type;
    private String        lock_user_id;
    private String        order_city_code;
    private String        audit_busi_money_status;
    private String        audit_receive_money_status;
    private String        bss_account_time;
    private String        phone_num;
    private String        invoice_no;
    private String        goodsName;
    private String        phone_owner_name;
    private String        payproviderid;
    private String        payprovidername;
    private String        is_old;
    private String        terminal_num;
    private String        audit_udp;
    private String        audit_remark;
    private Float 		  busi_money;
    private Float   	  paymoney;
    private Float 		  busi_money_sum;
    private String 		  logi_no;
    private String 		  audit_related_field;
    //列表数据结束
    
    //字典值翻译
    private String        order_type_name;
    private String		  order_from_name;
    private String        pay_type_name;
    private String        lock_user_id_name;
    private String        order_city_code_name;
    private String        audit_busi_money_status_name;
    private String        audit_receive_money_status_name;
    private String        is_old_name;
    private String  out_tid;



    
    
    
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	
	public String getOrder_from() {
		return order_from;
	}
	public void setOrder_from(String order_from) {
		this.order_from = order_from;
	}
	public String getPay_type() {
		return pay_type;
	}
	public void setPay_type(String pay_type) {
		this.pay_type = pay_type;
	}
	public String getLock_user_id() {
		return lock_user_id;
	}
	public void setLock_user_id(String lock_user_id) {
		this.lock_user_id = lock_user_id;
	}
	public String getOrder_city_code() {
		return order_city_code;
	}
	public void setOrder_city_code(String order_city_code) {
		this.order_city_code = order_city_code;
	}
	public String getAudit_busi_money_status() {
		return audit_busi_money_status;
	}
	public void setAudit_busi_money_status(String audit_busi_money_status) {
		this.audit_busi_money_status = audit_busi_money_status;
	}
	public String getAudit_receive_money_status() {
		return audit_receive_money_status;
	}
	public void setAudit_receive_money_status(String audit_receive_money_status) {
		this.audit_receive_money_status = audit_receive_money_status;
	}
	public String getBss_account_time() {
		return bss_account_time;
	}
	public void setBss_account_time(String bss_account_time) {
		this.bss_account_time = bss_account_time;
	}
	public String getPhone_num() {
		return phone_num;
	}
	public void setPhone_num(String phone_num) {
		this.phone_num = phone_num;
	}
	public String getInvoice_no() {
		return invoice_no;
	}
	public void setInvoice_no(String invoice_no) {
		this.invoice_no = invoice_no;
	}
	
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getPhone_owner_name() {
		return phone_owner_name;
	}
	public void setPhone_owner_name(String phone_owner_name) {
		this.phone_owner_name = phone_owner_name;
	}
	public String getPayproviderid() {
		return payproviderid;
	}
	public void setPayproviderid(String payproviderid) {
		this.payproviderid = payproviderid;
	}
	public String getPayprovidername() {
		return payprovidername;
	}
	public void setPayprovidername(String payprovidername) {
		this.payprovidername = payprovidername;
	}
	public String getIs_old() {
		return is_old;
	}
	public void setIs_old(String is_old) {
		this.is_old = is_old;
	}
	public String getTerminal_num() {
		return terminal_num;
	}
	public void setTerminal_num(String terminal_num) {
		this.terminal_num = terminal_num;
	}
	public String getOrder_type_name() {
		return order_type_name;
	}
	public void setOrder_type_name(String order_type_name) {
		this.order_type_name = order_type_name;
	}
	public String getOrder_from_name() {
		return order_from_name;
	}
	public void setOrder_from_name(String order_from_name) {
		this.order_from_name = order_from_name;
	}
	public String getPay_type_name() {
		return pay_type_name;
	}
	public void setPay_type_name(String pay_type_name) {
		this.pay_type_name = pay_type_name;
	}
	public String getLock_user_id_name() {
		return lock_user_id_name;
	}
	public void setLock_user_id_name(String lock_user_id_name) {
		this.lock_user_id_name = lock_user_id_name;
	}
	public String getOrder_city_code_name() {
		return order_city_code_name;
	}
	public void setOrder_city_code_name(String order_city_code_name) {
		this.order_city_code_name = order_city_code_name;
	}
	public String getAudit_busi_money_status_name() {
		return audit_busi_money_status_name;
	}
	public void setAudit_busi_money_status_name(String audit_busi_money_status_name) {
		this.audit_busi_money_status_name = audit_busi_money_status_name;
	}
	public String getAudit_receive_money_status_name() {
		return audit_receive_money_status_name;
	}
	public void setAudit_receive_money_status_name(
			String audit_receive_money_status_name) {
		this.audit_receive_money_status_name = audit_receive_money_status_name;
	}
	public String getIs_old_name() {
		return is_old_name;
	}
	public void setIs_old_name(String is_old_name) {
		this.is_old_name = is_old_name;
	}
	public String getAudit_udp() {
		return audit_udp;
	}
	public void setAudit_udp(String audit_udp) {
		this.audit_udp = audit_udp;
	}
	public String getAudit_remark() {
		return audit_remark;
	}
	public void setAudit_remark(String audit_remark) {
		this.audit_remark = audit_remark;
	}
	public Float getBusi_money() {
		return busi_money;
	}
	public void setBusi_money(Float busi_money) {
		this.busi_money = busi_money;
	}
	public Float getPaymoney() {
		return paymoney;
	}
	public void setPaymoney(Float paymoney) {
		this.paymoney = paymoney;
	}
	public Float getBusi_money_sum() {
		return busi_money_sum;
	}
	public void setBusi_money_sum(Float busi_money_sum) {
		this.busi_money_sum = busi_money_sum;
	}
	public String getLogi_no() {
		return logi_no;
	}
	public void setLogi_no(String logi_no) {
		this.logi_no = logi_no;
	}
	public String getOrder_type_return() {
		return order_type_return;
	}
	public void setOrder_type_return(String order_type_return) {
		this.order_type_return = order_type_return;
	}
	public String getAudit_related_field() {
		return audit_related_field;
	}
	public void setAudit_related_field(String audit_related_field) {
		this.audit_related_field = audit_related_field;
	}
	public String getOut_tid() {
		return out_tid;
	}
	public void setOut_tid(String out_tid) {
		this.out_tid = out_tid;
	}
	
	
	
	
	
}
