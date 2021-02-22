package com.ztesoft.remote.basic.params.req;

import params.ZteRequest;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.api.internal.utils.ApiUtils;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;
import com.ztesoft.remote.basic.params.resp.QryProdInstResponse;
import com.ztesoft.remote.basic.utils.BasicConst;

/**
 * 
 * @author chenlijun
 *
 */
public class QryProdInstRequest extends ZteRequest<QryProdInstResponse>{

	@ZteSoftCommentAnnotationParam(name = "手机号码", type = "String", isNecessary = "Y", desc = "手机号码")
	private String phoneNum;
	
	@ZteSoftCommentAnnotationParam(name = "产品id", type = "String", isNecessary = "Y", desc = "产品id(产品资料查询)")
	private String product_id;

	@ZteSoftCommentAnnotationParam(name = "规则编码", type = "String", isNecessary = "Y", desc = "规则编码")
	private String service_code;
	
	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		if(ApiUtils.isBlank(phoneNum)){
			throw new ApiRuleException("-1", "查询手机号码不能为空!");
		}
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return BasicConst.API_PREFIX + "qryProdInst";
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
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
