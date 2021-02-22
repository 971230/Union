package com.ztesoft.remote.basic.params.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.api.internal.utils.ApiUtils;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.remote.basic.params.resp.HisBillsResponse;
import com.ztesoft.remote.basic.utils.BasicConst;
import params.ZteRequest;

/**
 * Created with IntelliJ IDEA. User: guangping Date: 2014-02-12 09:22 To change
 * this template use File | Settings | File Templates.
 */
public class HisBillsRequest extends ZteRequest<HisBillsResponse> {

	@ZteSoftCommentAnnotationParam(name = "用户号码", type = "String", isNecessary = "yes", desc = "用户号码")
	public String accNbr;

	@ZteSoftCommentAnnotationParam(name = "帐期", type = "String", isNecessary = "yes", desc = "帐期 格式 YYYYMM(默认查询当月)")
	public String billingCycle;
	
	@ZteSoftCommentAnnotationParam(name = "产品id", type = "String", isNecessary = "Y", desc = "产品id(账单查询)")
	private String product_id;

	@ZteSoftCommentAnnotationParam(name = "规则编码", type = "String", isNecessary = "Y", desc = "规则编码")
	private String service_code;

	@Override
	public void check() throws ApiRuleException {
		if (ApiUtils.isBlank(accNbr)) {
			throw new ApiRuleException("-1", "用户号码不能为空!");
		}
	}

	@Override
	public String getApiMethodName() {
		return BasicConst.API_PREFIX + "hisBills";
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
