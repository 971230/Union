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
 * 
 * @author Musoon 2014-4-20 下午4:29:59
 * 
 */
public class PaySuccReq extends ZteRequest {

	@ZteSoftCommentAnnotationParam(name="支付日志",type="PaymentLog",isNecessary="Y",desc="paymentLog：支付日志不能为空。")
	private PaymentLog paymentLog;
	
	@ZteSoftCommentAnnotationParam(name="支付日志类型",type="String",isNecessary="Y",desc="logType：支付日志类型不能为空。")
	private String logType;
	
	@ZteSoftCommentAnnotationParam(name="支付日志类别代码",type="String",isNecessary="N",desc="")
	private String typeCode;
	
	public PaymentLog getPaymentLog() {
		return paymentLog;
	}

	public void setPaymentLog(PaymentLog paymentLog) {
		this.paymentLog = paymentLog;
	}

	public String getLogType() {
		return logType;
	}

	public void setLogType(String logType) {
		this.logType = logType;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	@Override
	@NotDbField
	public void check() throws ApiRuleException {

	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.paymentService.payment.paySucc";
	}

}
