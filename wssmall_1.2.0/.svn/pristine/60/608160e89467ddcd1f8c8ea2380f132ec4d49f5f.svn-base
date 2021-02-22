package params.payment.req;

import params.ZteError;
import params.ZteRequest;
import params.ZteResponse;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.net.framework.database.NotDbField;
import com.ztesoft.net.mall.core.model.PaymentList;
import commons.CommonTools;

import consts.ConstsCore;

public class EditPaymentListReq extends ZteRequest<ZteResponse>{

	private PaymentList paymentList;
	private String dealFlag;//可为空
	private String transactionId;//不能为空
	
	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public PaymentList getPaymentList() {
		return paymentList;
	}

	public void setPaymentList(PaymentList paymentList) {
		this.paymentList = paymentList;
	}

	public String getDealFlag() {
		return dealFlag;
	}

	public void setDealFlag(String dealFlag) {
		this.dealFlag = dealFlag;
	}

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		if(StringUtil.isEmpty(transactionId)){
			CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "transactionId不能为空"));
		}
		if(StringUtil.isEmpty(dealFlag) || paymentList==null){
			CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "dealFlag或paymentList不能同时为空"));
		}
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		// TODO Auto-generated method stub
		return null;
	}

}
