package params.paycfg.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.framework.database.NotDbField;
import com.ztesoft.net.framework.util.StringUtil;

import commons.CommonTools;

import consts.ConstsCore;

import params.ZteError;
import params.ZteRequest;
import params.ZteResponse;

public class PaymentCfgReq extends ZteRequest<ZteResponse>{
	
	private String payment_cfg_id;

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		if(StringUtil.isEmpty(payment_cfg_id))
			CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL,"支付方式不能为空"));
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "serv.payment.cfg";
	}

	public String getPayment_cfg_id() {
		return payment_cfg_id;
	}

	public void setPayment_cfg_id(String payment_cfg_id) {
		this.payment_cfg_id = payment_cfg_id;
	}

}
