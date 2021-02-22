package params.paycfg.resp;

import params.ZteResponse;

import com.ztesoft.net.mall.core.model.PayCfg;

public class PaymentCfgResp extends ZteResponse{

	private PayCfg paymentCfg;

	public PayCfg getPaymentCfg() {
		return paymentCfg;
	}

	public void setPaymentCfg(PayCfg paymentCfg) {
		this.paymentCfg = paymentCfg;
	}
	
}
