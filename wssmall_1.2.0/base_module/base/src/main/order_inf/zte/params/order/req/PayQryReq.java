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
public class PayQryReq extends ZteRequest {
	
	@ZteSoftCommentAnnotationParam(name="支付流水ID",type="String",isNecessary="Y",desc="transactionId：支付流水ID不能为空。")
	private String transactionId;

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.paymentService.payment.getPayment";
	}

}
