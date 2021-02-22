package zte.params.order.req;

import params.ZteRequest;
import zte.params.order.resp.PaymentLogResp;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;

/**
 * 订单支付成功状态
 * @创建日期 2014-1-8 
 * @版本 V 1.0
 */
public class PaymentLogReq extends ZteRequest<PaymentLogResp> {

	@ZteSoftCommentAnnotationParam(name="支付类型",type="String",isNecessary="Y",desc="支付类型")
	private String payType;
	
	@ZteSoftCommentAnnotationParam(name="订单号",type="String",isNecessary="Y",desc="订单号")
	private String orderId;
	
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		
	}
	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.paymentService.payment.qryNotPayPaymentLog";
	}
}
