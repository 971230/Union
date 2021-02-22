/**
 * 
 */
package zte.params.order.req;

import params.ZteRequest;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;

/**
 * TODO
 *
 * @author Musoon
 * 2014-4-20 下午9:10:41
 * 
 */
public class PayUptReq extends ZteRequest {
	
	//order_id,transactionId,pay_money,paymentLog.getPayment_id()	
	@ZteSoftCommentAnnotationParam(name="订单ID",type="String",isNecessary="Y",desc="orderId：订单ID不能为空。")
	private String orderId;
	
	@ZteSoftCommentAnnotationParam(name="交易流水ID",type="String",isNecessary="Y",desc="transactionId：交易流水ID不能为空。")
	private String transactionId;
	
	@ZteSoftCommentAnnotationParam(name="订单金额",type="double",isNecessary="Y",desc="payAmount：订单金额不能为空。")
	private double payAmount;
	
	@ZteSoftCommentAnnotationParam(name="支付流水ID",type="String",isNecessary="Y",desc="paymentId：支付流水ID不能为空。")
	private String paymentId;
	
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public double getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(double payAmount) {
		this.payAmount = payAmount;
	}

	public String getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.paymentService.payment.updatePaymentMoney";
	}

}
