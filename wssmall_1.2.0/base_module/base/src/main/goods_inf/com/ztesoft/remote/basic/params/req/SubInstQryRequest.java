package com.ztesoft.remote.basic.params.req;

import params.ZteRequest;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.api.internal.utils.ApiUtils;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;
import com.ztesoft.remote.basic.params.resp.SubInstQryResponse;
import com.ztesoft.remote.basic.utils.BasicConst;

public class SubInstQryRequest extends ZteRequest<SubInstQryResponse>{

	@ZteSoftCommentAnnotationParam(name = "用户号码", type = "String", isNecessary = "Y", desc = "用户号码")
	private String productNo;
	
	@ZteSoftCommentAnnotationParam(name = "产品id", type = "String", isNecessary = "Y", desc = "产品id(增值订购关系查询)")
	private String product_id;

	@ZteSoftCommentAnnotationParam(name = "规则编码", type = "String", isNecessary = "Y", desc = "规则编码")
	private String service_code;
	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		if(ApiUtils.isBlank(productNo)){
			throw new ApiRuleException("-1", "查询号码不能为空!");
		}
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return BasicConst.API_PREFIX + "subInstQry";
	}

	public String getProductNo() {
		return productNo;
	}

	public void setProductNo(String productNo) {
		this.productNo = productNo;
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