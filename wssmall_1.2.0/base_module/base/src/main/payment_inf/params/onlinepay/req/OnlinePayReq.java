package params.onlinepay.req;

import java.util.Map;

import params.ZteRequest;
import params.onlinepay.resp.OnlinePayResp;

import com.ztesoft.api.ApiRuleException;

/**
 * 在线支付跳转银行界面请求入参
 * @作者 MoChunrun
 * @创建日期 2013-12-23 
 * @版本 V 1.0
 */
public class OnlinePayReq extends ZteRequest<OnlinePayResp> {

	private String order_id;//支付订单ID
	private String payment_cfg_id;//支付方式ID
	private String bank_id;//银行ID
	private double pay_money;//支付金额,最多两位小数
	private String client_ip;//客户端IP地址
	private String staff_user_id;//商家管理员ID
	private String type_code;//业务类型
	private String bean_name;
	
	private Map<String,String> extParams;//其它相关参数

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getPayment_cfg_id() {
		return payment_cfg_id;
	}

	public void setPayment_cfg_id(String payment_cfg_id) {
		this.payment_cfg_id = payment_cfg_id;
	}

	public String getBank_id() {
		return bank_id;
	}

	public void setBank_id(String bank_id) {
		this.bank_id = bank_id;
	}

	public String getClient_ip() {
		return client_ip;
	}

	public void setClient_ip(String client_ip) {
		this.client_ip = client_ip;
	}

	public Map<String, String> getExtParams() {
		return extParams;
	}

	public void setExtParams(Map<String, String> extParams) {
		this.extParams = extParams;
	}

	public String getStaff_user_id() {
		return staff_user_id;
	}

	public void setStaff_user_id(String staff_user_id) {
		this.staff_user_id = staff_user_id;
	}

	public double getPay_money() {
		return pay_money;
	}

	public void setPay_money(double pay_money) {
		this.pay_money = pay_money;
	}


	public String getType_code() {
		return type_code;
	}

	public void setType_code(String type_code) {
		this.type_code = type_code;
	}

	public String getBean_name() {
		return bean_name;
	}

	public void setBean_name(String bean_name) {
		this.bean_name = bean_name;
	}

	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getApiMethodName() {
		// TODO Auto-generated method stub
		return "zte.paymentService.payment.goToPay";
	}
	
}
