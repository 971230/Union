package com.ztesoft.remote.basic.params.req;

import java.util.Map;

import params.ZteRequest;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.api.internal.utils.ApiUtils;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;
import com.ztesoft.remote.basic.params.resp.PaymentInformationResponse;
import com.ztesoft.remote.basic.utils.BasicConst;

public class PaymentInformationRequest extends
		ZteRequest<PaymentInformationResponse> {

	@ZteSoftCommentAnnotationParam(name = "用户号码", type = "String", isNecessary = "Y", desc = "用户号码")
	private String accNbr;

	@ZteSoftCommentAnnotationParam(name = "用户属性", type = "String", isNecessary = "Y", desc = "用户属性 2为移动用户")
	private String destinationAttr;

	@ZteSoftCommentAnnotationParam(name = "查询账期", type = "String", isNecessary = "Y", desc = "查询账期")
	private String billingCycle;

	@ZteSoftCommentAnnotationParam(name = "产品id", type = "String", isNecessary = "Y", desc = "产品id(充值记录查询)")
	private String product_id;

	@ZteSoftCommentAnnotationParam(name = "规则编码", type = "String", isNecessary = "Y", desc = "规则编码")
	private String service_code;

	@ZteSoftCommentAnnotationParam(name = "连连钱包查询参数列表", type = "String", isNecessary = "Y", desc = "查询偏移量:offset,每页记录数:maxrecordes,开始时间:dt_start,结束时间:dt_end")
	private Map param;

	@ZteSoftCommentAnnotationParam(name = "查询类型", type = "String", isNecessary = "Y", desc = "查询类型(001:电信充值记录查询,002:连连钱包收支明细查询)")
	private String charge_type;
	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		if (ApiUtils.isBlank(accNbr)) {
			throw new ApiRuleException("-1", "查询用户号码不能不空!");
		}
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return BasicConst.API_PREFIX + "paymentInformation";
	}

	public String getAccNbr() {
		return accNbr;
	}

	public void setAccNbr(String accNbr) {
		this.accNbr = accNbr;
	}

	public String getDestinationAttr() {
		return destinationAttr;
	}

	public void setDestinationAttr(String destinationAttr) {
		this.destinationAttr = destinationAttr;
	}

	public String getBillingCycle() {
		return billingCycle;
	}

	public void setBillingCycle(String billingCycle) {
		this.billingCycle = billingCycle;
	}

	public String getProduct_id() {
		return product_id;
	}

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}

	public String getService_code() {
		return service_code;
	}

	public void setService_code(String service_code) {
		this.service_code = service_code;
	}

	public Map getParam() {
		return param;
	}

	public void setParam(Map param) {
		this.param = param;
	}

	public String getCharge_type() {
		return charge_type;
	}

	public void setCharge_type(String charge_type) {
		this.charge_type = charge_type;
	}
	
	

}
