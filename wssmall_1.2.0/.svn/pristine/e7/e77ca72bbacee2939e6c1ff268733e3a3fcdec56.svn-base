package com.ztesoft.remote.basic.params.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.api.internal.utils.ApiUtils;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.remote.basic.params.resp.ArrearageResponse;
import com.ztesoft.remote.basic.utils.BasicConst;
import params.ZteRequest;

/**
 * Created with IntelliJ IDEA. User: guangping Date: 2014-02-12 09:15 To change
 * this template use File | Settings | File Templates.
 */
public class ArrearageRequest extends ZteRequest<ArrearageResponse> {

	@ZteSoftCommentAnnotationParam(name = "查询条件标识", type = "String", isNecessary = "Y", desc = "查询条件标识")
	private String destinationAccount;
	
	@ZteSoftCommentAnnotationParam(name = "产品id", type = "String", isNecessary = "Y", desc = "产品id(欠费查询)")
	private String product_id;

	@ZteSoftCommentAnnotationParam(name = "规则编码", type = "String", isNecessary = "Y", desc = "规则编码")
	private String service_code;

	@Override
	public void check() throws ApiRuleException {
		if(ApiUtils.isBlank(destinationAccount)){
			throw new ApiRuleException("-1", "查询条件标识不能为空!");
		}
	}

	@Override
	public String getApiMethodName() {
		return BasicConst.API_PREFIX + "arrearage";
	}

	public String getDestinationAccount() {
		return destinationAccount;
	}

	public void setDestinationAccount(String destinationAccount) {
		this.destinationAccount = destinationAccount;
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
