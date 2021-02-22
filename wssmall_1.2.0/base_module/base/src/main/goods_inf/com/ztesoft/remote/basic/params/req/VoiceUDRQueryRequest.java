package com.ztesoft.remote.basic.params.req;

import params.ZteRequest;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.api.internal.utils.ApiUtils;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;
import com.ztesoft.remote.basic.params.resp.VoiceUDRQueryResponse;
import com.ztesoft.remote.basic.utils.BasicConst;

/**
 * 
 * @author chenlijun
 * 
 */
public class VoiceUDRQueryRequest extends ZteRequest<VoiceUDRQueryResponse> {

	@ZteSoftCommentAnnotationParam(name = "手机号码", type = "String", isNecessary = "Y", desc = "手机号码")
	private String accNbr;

	@ZteSoftCommentAnnotationParam(name = "帐期", type = "String", isNecessary = "Y", desc = "帐期 YYYYMM")
	private String billingCycle;

	@ZteSoftCommentAnnotationParam(name = "呼叫类型", type = "String", isNecessary = "Y", desc = "呼叫类型  1主叫 2被叫  （不写表示主被叫都查）")
	private String servType;

	@ZteSoftCommentAnnotationParam(name = "产品id", type = "String", isNecessary = "Y", desc = "产品id(语音清单查询)")
	private String product_id;

	@ZteSoftCommentAnnotationParam(name = "规则编码", type = "String", isNecessary = "Y", desc = "规则编码")
	private String service_code;

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		if (ApiUtils.isBlank(accNbr)) {
			throw new ApiRuleException("-1", "手机号码不能为空!");
		}
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return BasicConst.API_PREFIX + "voiceUDRQuery";
	}

	public String getAccNbr() {
		return accNbr;
	}

	public void setAccNbr(String accNbr) {
		this.accNbr = accNbr;
	}

	public String getBillingCycle() {
		return billingCycle;
	}

	public void setBillingCycle(String billingCycle) {
		this.billingCycle = billingCycle;
	}

	public String getServType() {
		return servType;
	}

	public void setServType(String servType) {
		this.servType = servType;
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

}
