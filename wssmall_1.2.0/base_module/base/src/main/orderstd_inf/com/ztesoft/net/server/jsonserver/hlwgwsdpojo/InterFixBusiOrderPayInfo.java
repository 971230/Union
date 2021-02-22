package com.ztesoft.net.server.jsonserver.hlwgwsdpojo;

public class InterFixBusiOrderPayInfo {
	private String pay_type;//支付类型
	private String pay_method;//支付方式
	private String pay_sequ ; //支付发起流水
	private String pay_back_sequ ; //支付返回流水
	private String pay_status ; //0-未支付   1-已支付
	private String remark;//
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
	public String getPay_sequ() {
		return pay_sequ;
	}
	public void setPay_sequ(String pay_sequ) {
		this.pay_sequ = pay_sequ;
	}
	public String getPay_back_sequ() {
		return pay_back_sequ;
	}
	public void setPay_back_sequ(String pay_back_sequ) {
		this.pay_back_sequ = pay_back_sequ;
	}
	public String getPay_status() {
		return pay_status;
	}
	public void setPay_status(String pay_status) {
		this.pay_status = pay_status;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
