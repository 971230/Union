package params.pay.req;

import params.ZteRequest;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.mall.core.model.PaymentLog;

public class PaySucessReq extends ZteRequest {

	private String order_id;
	private String ip;
	private String userid;
	private String type_code;
	private String pay_source;
	private String deal_flag;
	
	private PaymentLog paymentLog;//可为空，当不为空时以这个为准去付订单

	public String getPay_source() {
		return pay_source;
	}

	public void setPay_source(String pay_source) {
		this.pay_source = pay_source;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public PaymentLog getPaymentLog() {
		return paymentLog;
	}

	public void setPaymentLog(PaymentLog paymentLog) {
		this.paymentLog = paymentLog;
	}
	
	public String getType_code() {
		return type_code;
	}

	public void setType_code(String type_code) {
		this.type_code = type_code;
	}

	@Override
	public void check() throws ApiRuleException {
		
	}
	
	public String getDeal_flag() {
		return deal_flag;
	}

	public void setDeal_flag(String deal_flag) {
		this.deal_flag = deal_flag;
	}

	@Override
	public String getApiMethodName() {
		// TODO Auto-generated method stub
		return null;
	}
}
