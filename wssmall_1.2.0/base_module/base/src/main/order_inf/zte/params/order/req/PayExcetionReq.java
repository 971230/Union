/**
 * 
 */
package zte.params.order.req;

import params.ZteRequest;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;
import com.ztesoft.net.mall.core.model.PaymentLog;

/**
 * TODO
 *
 * @author Musoon
 * 2014-4-20 下午8:13:56
 * 
 */
public class PayExcetionReq extends ZteRequest {
	
	@ZteSoftCommentAnnotationParam(name="支付日志",type="PaymentLog",isNecessary="Y",desc="paymentLog：支付日志不能为空。")
	private PaymentLog paymentLog;
	
	@ZteSoftCommentAnnotationParam(name="订单金额",type="Integer",isNecessary="Y",desc="orderAmount：订单金额不能为空。")
	private Integer orderAmount;

	public PaymentLog getPaymentLog() {
		return paymentLog;
	}

	public void setPaymentLog(PaymentLog paymentLog) {
		this.paymentLog = paymentLog;
	}

	public Integer getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(Integer orderAmount) {
		this.orderAmount = orderAmount;
	}

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.paymentService.payment.payExcetion";
	}

}
