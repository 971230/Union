package zte.params.order.resp;

import params.ZteResponse;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.mall.core.model.PaymentLog;

public class PaymentLogResp extends ZteResponse {
	
	@ZteSoftCommentAnnotationParam(name="支付日志", type="PaymentLog", isNecessary="Y", desc="支付日志")
	private PaymentLog paymentLog;

	public PaymentLog getPaymentLog() {
		return paymentLog;
	}

	public void setPaymentLog(PaymentLog paymentLog) {
		this.paymentLog = paymentLog;
	}
}
