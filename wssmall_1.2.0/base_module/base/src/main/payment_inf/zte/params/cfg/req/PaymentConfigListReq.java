package zte.params.cfg.req;

import params.ZteRequest;
import zte.params.cfg.resp.PaymentConfigListResp;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;

public class PaymentConfigListReq extends ZteRequest<PaymentConfigListResp> {
	@ZteSoftCommentAnnotationParam(name="支付方式编号",type="String",isNecessary="N",desc="支付方式编号")
	private String payment_id;

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.paymentConfigService.paymentcfg.list";
	}

	public String getPayment_id() {
		return payment_id;
	}

	public void setPayment_id(String payment_id) {
		this.payment_id = payment_id;
	}

}
