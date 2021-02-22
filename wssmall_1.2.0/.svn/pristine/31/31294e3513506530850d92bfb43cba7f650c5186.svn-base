package zte.params.cfg.req;

import com.ztesoft.api.internal.utils.StringUtils;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;

import commons.CommonTools;

import consts.ConstsCore;

import params.ZteError;
import params.ZteRequest;
import zte.params.cfg.resp.PaymentBankListResp;

public class PaymentBankListReq extends ZteRequest<PaymentBankListResp> {
	
	@ZteSoftCommentAnnotationParam(name="支付方式编号",type="String",isNecessary="N",desc="支付方式编号")
	private String payment_id;
	@ZteSoftCommentAnnotationParam(name="银行编号",type="String",isNecessary="N",desc="银行编号")
	private String bank_id;

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		if(StringUtils.isEmpty(payment_id) && StringUtils.isEmpty(bank_id))CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "payment_id与bank_id不能同时为空"));
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.paymentConfigService.bank.list";
	}

	public String getBank_id() {
		return bank_id;
	}

	public void setBank_id(String bank_id) {
		this.bank_id = bank_id;
	}

	public String getPayment_id() {
		return payment_id;
	}

	public void setPayment_id(String payment_id) {
		this.payment_id = payment_id;
	}

}
