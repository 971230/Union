package com.ztesoft.net.ecsord.params.ecaop.req;

import params.ZteRequest;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;

public class OrderInfoUpdateReq  extends ZteRequest{
	@ZteSoftCommentAnnotationParam(name="序列号",type="String",isNecessary="Y",desc="serial_no：序列号")
	private String serial_no;
	@ZteSoftCommentAnnotationParam(name="时间",type="String",isNecessary="Y",desc="time：时间")
	private String time;
	@ZteSoftCommentAnnotationParam(name="发起方系统标识",type="String",isNecessary="Y",desc="source_system：发起方系统标识")
	private String source_system;
	@ZteSoftCommentAnnotationParam(name="接收方系统标识",type="String",isNecessary="Y",desc="receive_system：接收方系统标识")
	private String receive_system;
	@ZteSoftCommentAnnotationParam(name="业务类型",type="String",isNecessary="Y",desc="busi_type：01 宽带开通类 02 号卡类业务")
	private String busi_type;
	@ZteSoftCommentAnnotationParam(name="订单中心订单号",type="String",isNecessary="Y",desc="order_id：订单中心订单号")
	private String order_id;
	@ZteSoftCommentAnnotationParam(name="开户号码",type="String",isNecessary="Y",desc="phone_num：针对有些订单可能没有订单中心单号，但是也要激活的情况")
	private String phone_num;
	@ZteSoftCommentAnnotationParam(name="支付结果",type="String",isNecessary="Y",desc="pay_result：0 成功，已支付 -1 支付超时")
	private String pay_result;
	@ZteSoftCommentAnnotationParam(name="施工结果",type="String",isNecessary="Y",desc="work_result：0 竣工成功 -1 竣工失败")
	private String work_result;
	@ZteSoftCommentAnnotationParam(name="认证结果",type="String",isNecessary="Y",desc="auth_result：0认证成功 -1认证失败")
	private String auth_result;
	@ZteSoftCommentAnnotationParam(name="备注",type="String",isNecessary="Y",desc="remark：备注")
	private String remark;
	@ZteSoftCommentAnnotationParam(name="支付流水",type="String",isNecessary="Y",desc="pay_sequ：支付流水,在回调支付结果时必填")
	private String pay_sequ;
	@ZteSoftCommentAnnotationParam(name="外系统订单号",type="String",isNecessary="Y",desc="out_order_id：订单同步时，外系统同步订单中心的订单号")
	private String out_order_id;
	@ZteSoftCommentAnnotationParam(name="支付类型",type="String",isNecessary="Y",desc="支付类型")
	private String pay_type;
	@ZteSoftCommentAnnotationParam(name="支付方式",type="String",isNecessary="Y",desc="out_order_id：支付方式")
	private String pay_method;
	@ZteSoftCommentAnnotationParam(name="支付返回流水",type="String",isNecessary="Y",desc="支付返回流水")
	private String pay_back_sequ;
	@ZteSoftCommentAnnotationParam(name="办理操作员",type="String",isNecessary="Y",desc="办理操作员")
	private String deal_operator;
	@ZteSoftCommentAnnotationParam(name="办理操作点",type="String",isNecessary="Y",desc="办理操作点")
	private String deal_office_id;
	@ZteSoftCommentAnnotationParam(name="是否收费",type="String",isNecessary="Y",desc="是否收费")
	private String is_pay;
	@ZteSoftCommentAnnotationParam(name="操作员",type="String",isNecessary="Y",desc="操作员")
	private String out_operator;
	@ZteSoftCommentAnnotationParam(name="操作点",type="String",isNecessary="Y",desc="操作点")
	private String out_office;
	@ZteSoftCommentAnnotationParam(name="缴费单的cb单号",type="String",isNecessary="Y",desc="缴费单的cb单号")
	private String cbss_order_id;
	@ZteSoftCommentAnnotationParam(name="缴费单的bss单号",type="String",isNecessary="Y",desc="缴费单的bss单号")
	private String bss_order_id;
	@ZteSoftCommentAnnotationParam(name="",type="String",isNecessary="Y",desc="")
	private String is_bss;
	
	public String getOut_order_id() {
		return out_order_id;
	}
	public void setOut_order_id(String out_order_id) {
		this.out_order_id = out_order_id;
	}
	public String getPay_sequ() {
		return pay_sequ;
	}
	public void setPay_sequ(String pay_sequ) {
		this.pay_sequ = pay_sequ;
	}
	public String getSerial_no() {
		return serial_no;
	}
	public void setSerial_no(String serial_no) {
		this.serial_no = serial_no;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getSource_system() {
		return source_system;
	}
	public void setSource_system(String source_system) {
		this.source_system = source_system;
	}
	public String getReceive_system() {
		return receive_system;
	}
	public void setReceive_system(String receive_system) {
		this.receive_system = receive_system;
	}
	public String getBusi_type() {
		return busi_type;
	}
	public void setBusi_type(String busi_type) {
		this.busi_type = busi_type;
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getPhone_num() {
		return phone_num;
	}
	public void setPhone_num(String phone_num) {
		this.phone_num = phone_num;
	}
	public String getPay_result() {
		return pay_result;
	}
	public void setPay_result(String pay_result) {
		this.pay_result = pay_result;
	}
	public String getWork_result() {
		return work_result;
	}
	public void setWork_result(String work_result) {
		this.work_result = work_result;
	}
	public String getAuth_result() {
		return auth_result;
	}
	public void setAuth_result(String auth_result) {
		this.auth_result = auth_result;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getPay_type() {
		return pay_type;
	}
	public void setPay_type(String pay_type) {
		this.pay_type = pay_type;
	}
	public String getPay_method() {
		return pay_method;
	}
	public void setPay_method(String pay_method) {
		this.pay_method = pay_method;
	}
	public String getPay_back_sequ() {
		return pay_back_sequ;
	}
	public void setPay_back_sequ(String pay_back_sequ) {
		this.pay_back_sequ = pay_back_sequ;
	}
	public String getDeal_operator() {
		return deal_operator;
	}
	public void setDeal_operator(String deal_operator) {
		this.deal_operator = deal_operator;
	}
	public String getDeal_office_id() {
		return deal_office_id;
	}
	public void setDeal_office_id(String deal_office_id) {
		this.deal_office_id = deal_office_id;
	}
	public String getIs_pay() {
		return is_pay;
	}
	public void setIs_pay(String is_pay) {
		this.is_pay = is_pay;
	}
	public String getOut_operator() {
		return out_operator;
	}
	public void setOut_operator(String out_operator) {
		this.out_operator = out_operator;
	}
	public String getOut_office() {
		return out_office;
	}
	public void setOut_office(String out_office) {
		this.out_office = out_office;
	}
	public String getCbss_order_id() {
		return cbss_order_id;
	}
	public void setCbss_order_id(String cbss_order_id) {
		this.cbss_order_id = cbss_order_id;
	}
	public String getBss_order_id() {
		return bss_order_id;
	}
	public void setBss_order_id(String bss_order_id) {
		this.bss_order_id = bss_order_id;
	}
	public String getIs_bss() {
		return is_bss;
	}
	public void setIs_bss(String is_bss) {
		this.is_bss = is_bss;
	}
	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}
	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.net.iservice.impl.ZJInfServices.updateOrderInfoByRequ";
	}

}
