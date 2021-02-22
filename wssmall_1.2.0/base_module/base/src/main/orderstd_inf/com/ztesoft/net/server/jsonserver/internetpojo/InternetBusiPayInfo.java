package com.ztesoft.net.server.jsonserver.internetpojo;

public class InternetBusiPayInfo {

	private String pay_type;//支付类型
	
	private String pay_method;//支付方式 WX–微信支付 ZFB-支付宝 XX-线下付款

	private String pay_sequ;//支付流水
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
	
	

}
