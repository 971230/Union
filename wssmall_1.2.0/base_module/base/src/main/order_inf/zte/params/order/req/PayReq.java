/**
 * 
 */
package zte.params.order.req;

import java.util.Map;

import params.ZteRequest;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;
import com.ztesoft.net.mall.core.model.PaymentLog;

/**
 * TODO
 *
 * @author Musoon
 * 2014-4-20 下午7:39:03
 * 
 */
public class PayReq extends ZteRequest {
	
	@ZteSoftCommentAnnotationParam(name="支付日志",type="PaymentLog",isNecessary="Y",desc="paymentLog：支付日志不能为空。")
	private PaymentLog paymentLog;
	
	@ZteSoftCommentAnnotationParam(name="在线标识",type="boolean",isNecessary="Y",desc="onLine: 在线标识不能为空")
	private boolean onLine;
	
	@ZteSoftCommentAnnotationParam(name="参数集合",type="Map",isNecessary="N",desc="参数集合")
	private Map param;
	
	public PaymentLog getPaymentLog() {
		return paymentLog;
	}

	public void setPaymentLog(PaymentLog paymentLog) {
		this.paymentLog = paymentLog;
	}

	public boolean isOnLine() {
		return onLine;
	}

	public void setOnLine(boolean onLine) {
		this.onLine = onLine;
	}

	public Map getParam() {
		return param;
	}

	public void setParam(Map param) {
		this.param = param;
	}

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.paymentService.payment.pay";
	}

}
