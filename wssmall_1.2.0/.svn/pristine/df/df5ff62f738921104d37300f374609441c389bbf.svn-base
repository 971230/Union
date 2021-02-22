package com.ztesoft.net.mall.core.model;

import java.util.List;

import com.ztesoft.net.framework.database.NotDbField;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.model.Order;


/**
 * 支付/退款日志
 * @author kingapex
 *2010-4-8上午09:09:43
 */
public class PaymentLogHis implements java.io.Serializable {

	private String payment_id;

	private String order_id;

	private String member_id;

	private String account;

	private String bank;

	private String pay_user;

	private Double money;

	private Double pay_cost;

	private String pay_type;

	private String pay_method;

	private String payMethod;
	
	private String remark;

	private String op_id;

	private Integer type;

	private Integer status;
	private String create_time;
	
	//支付、退款会员名
	private String member_name;
	
	private String user_name;
	
	private String sn;
	
	private String bank_id;
	
	private String userid;
	
	private String status_time;
	
	private String transaction_id;
	/**
	 * 0订单ID 1批量ID
	 */
	private Integer paytype = 0;
	
	/**
	 * 用于记录日志
	 */
	private List<Order> orderList;
	
	private String o_order_id;
	
	private String pay_time;
	private String returned_account;
	private Integer returned_type;
	private Integer returned_kind;
	
	public String getPay_time() {
		return pay_time;
	}

	public void setPay_time(String pay_time) {
		this.pay_time = pay_time;
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

	@NotDbField
	public String getO_order_id() {
		return o_order_id;
	}

	public void setO_order_id(String o_order_id) {
		this.o_order_id = o_order_id;
	}

	@NotDbField
	public List<Order> getOrderList() {
		return orderList;
	}

	public void setOrderList(List<Order> orderList) {
		this.orderList = orderList;
	}

	public Integer getPaytype() {
		return paytype;
	}

	public void setPaytype(Integer paytype) {
		this.paytype = paytype;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getMember_id() {
		return member_id;
	}

	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public String getOp_id() {
		return op_id;
	}

	public void setOp_id(String op_id) {
		this.op_id = op_id;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public Double getPay_cost() {
		return pay_cost;
	}

	public void setPay_cost(Double pay_cost) {
		this.pay_cost = pay_cost;
	}

	public String getPay_method() {
		return pay_method;
	}

	public void setPay_method(String pay_method) {
		this.pay_method = pay_method;
	}
 
	public String getPay_user() {
		return pay_user;
	}

	public void setPay_user(String pay_user) {
		this.pay_user = pay_user;
	}

	public String getPayment_id() {
		return payment_id;
	}

	public void setPayment_id(String payment_id) {
		this.payment_id = payment_id;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getPay_type() {
		return pay_type;
	}

	public void setPay_type(String pay_type) {
		this.pay_type = pay_type;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	@NotDbField
	public String getMember_name() {
		return member_name;
	}

	public void setMember_name(String memberName) {
		member_name = memberName;
	}

	@NotDbField
	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getBank_id() {
		return bank_id;
	}

	public void setBank_id(String bank_id) {
		this.bank_id = bank_id;
	}

	public String getTransaction_id() {
		return transaction_id;
	}

	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}

	public String getStatus_time() {
		return status_time;
	}

	public void setStatus_time(String status_time) {
		this.status_time = status_time;
	}
	@NotDbField
	public String getPayMethod() {
		if(pay_method==null || "".equals(pay_method))
			return payMethod;
		if(Consts.PAYMENT_ID_BANK==new Integer(pay_method).intValue())
			return "在线支付";
		if(Consts.PAYMENT_ID_DEPOST==new Integer(pay_method).intValue())
			return "预存金支付";
		if(Consts.PAYMENT_ID_AUTO ==new Integer(pay_method).intValue())
			return "免支付";
	
		return payMethod;
	}
	@NotDbField
	public void setPayMethod(String payMethod) {
		this.payMethod = payMethod;
	}
	
	@NotDbField
	public String getUser_name() {
		return user_name;
	}
	@NotDbField
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	
}