package params.payment;

import com.ztesoft.net.mall.core.model.PayCfg;
import com.ztesoft.net.mall.core.model.PaymentLog;
import params.ZteResponse;

import java.util.Map;

public class PayResp extends ZteResponse{

	private String online_flag; //0 线上 1线下
	private Map<String,String> bankParams;
	private PayCfg payCfg;
	private PaymentLog paymentLog;

	public Map<String, String> getBankParams() {
		return bankParams;
	}

	public void setBankParams(Map<String, String> bankParams) {
		this.bankParams = bankParams;
	}

	public String getOnline_flag() {
		return online_flag;
	}

	public void setOnline_flag(String online_flag) {
		this.online_flag = online_flag;
	}

	public PayCfg getPayCfg() {
		return payCfg;
	}

	public void setPayCfg(PayCfg payCfg) {
		this.payCfg = payCfg;
	}

	public PaymentLog getPaymentLog() {
		return paymentLog;
	}

	public void setPaymentLog(PaymentLog paymentLog) {
		this.paymentLog = paymentLog;
	}
	
}
