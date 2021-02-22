/**
 * 
 */
package zte.params.order.resp;

import params.ZteResponse;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.mall.core.model.PaymentLog;

/**
 * TODO
 *
 * @author Musoon
 * 2014-4-20 下午9:51:08
 * 
 */
public class PayQryResp extends ZteResponse {

	@ZteSoftCommentAnnotationParam(name="查询支付信息",type="PaymentLog",isNecessary="N",desc="查询支付信息")
	private PaymentLog paymentLog;

	public PaymentLog getPaymentLog() {
		return paymentLog;
	}

	public void setPaymentLog(PaymentLog paymentLog) {
		this.paymentLog = paymentLog;
	}
	
}
