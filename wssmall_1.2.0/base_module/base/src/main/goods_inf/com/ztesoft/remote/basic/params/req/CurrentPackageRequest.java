package com.ztesoft.remote.basic.params.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.api.internal.utils.ApiUtils;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.remote.basic.params.resp.CurrentPackageResponse;
import com.ztesoft.remote.basic.utils.BasicConst;
import params.ZteRequest;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2014-02-12 09:25
 * To change this template use File | Settings | File Templates.
 */
public class CurrentPackageRequest extends ZteRequest<CurrentPackageResponse> {
	
	@ZteSoftCommentAnnotationParam(name="号码",type="String",isNecessary="Y",desc="号码")
	private String phone_no;
	
	@ZteSoftCommentAnnotationParam(name="产品id",type="String",isNecessary="Y",desc="产品id(查询当前套餐)")
	private String product_id;
	
	@ZteSoftCommentAnnotationParam(name="规则编码",type="String",isNecessary="Y",desc="规则编码")
	private String service_code;
	
	@Override
    public void check() throws ApiRuleException {
		if (ApiUtils.isBlank(phone_no)) {
			throw new ApiRuleException("-1", "查询号码不能为空!");
		}
    }
    
    @Override
    public String getApiMethodName() {
        return BasicConst.API_PREFIX+"qryCurrentPackage";
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

	public String getPhone_no() {
		return phone_no;
	}

	public void setPhone_no(String phone_no) {
		this.phone_no = phone_no;
	}
}
