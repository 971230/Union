package params.payment.resp;

import params.ZteResponse;

import com.ztesoft.net.mall.core.model.PaymentList;

public class GetPaymentListResp  extends ZteResponse{

	private PaymentList paymentList;

	public PaymentList getPaymentList() {
		return paymentList;
	}

	public void setPaymentList(PaymentList paymentList) {
		this.paymentList = paymentList;
	}
	
}
