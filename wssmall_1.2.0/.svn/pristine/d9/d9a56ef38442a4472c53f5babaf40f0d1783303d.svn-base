package params.payment;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.mall.core.model.PayCfg;
import com.ztesoft.net.mall.core.model.PaymentLog;
import params.ZteRequest;
import params.ZteResponse;

public class PayReq extends ZteRequest<ZteResponse>{

	private String payType = "0";
	private String objectId;
	private String bankId;
	private String paySource; //001 002
	private PayCfg paymentCfg;//可为空
	private PaymentLog paymentLog;//可为空
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public String getObjectId() {
		return objectId;
	}
	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}
	public String getBankId() {
		return bankId;
	}
	public void setBankId(String bankId) {
		this.bankId = bankId;
	}
	public String getPaySource() {
		return paySource;
	}
	public void setPaySource(String paySource) {
		this.paySource = paySource;
	}
	public PayCfg getPaymentCfg() {
		return paymentCfg;
	}
	public void setPaymentCfg(PayCfg paymentCfg) {
		this.paymentCfg = paymentCfg;
	}
	public PaymentLog getPaymentLog() {
		return paymentLog;
	}
	public void setPaymentLog(PaymentLog paymentLog) {
		this.paymentLog = paymentLog;
	}
	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public String getApiMethodName() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
