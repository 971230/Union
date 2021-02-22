/**
 * 
 */
package zte.params.order.resp;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.mall.core.model.PaymentLog;

import params.ZteResponse;

/**
 * TODO
 *
 * @author Musoon
 * 2014-4-20 下午4:29:06
 * 
 */
public class PaySuccResp extends ZteResponse {

	@ZteSoftCommentAnnotationParam(name="支付日志", type="PaymentLog", isNecessary="Y", desc="支付日志")
	private PaymentLog paymentLog;

	public PaymentLog getPaymentLog() {
		return paymentLog;
	}

	public void setPaymentLog(PaymentLog paymentLog) {
		this.paymentLog = paymentLog;
	}
	
}
