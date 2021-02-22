package com.ztesoft.remote.basic.params.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.remote.basic.params.resp.PayFeeRecordsResponse;
import com.ztesoft.remote.basic.utils.BasicConst;
import params.ZteRequest;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2014-02-12 09:12
 * To change this template use File | Settings | File Templates.
 */
public class PayFeeRecordsRequest extends ZteRequest<PayFeeRecordsResponse> {
	
	@ZteSoftCommentAnnotationParam(name="产品id",type="String",isNecessary="Y",desc="产品id(查询实时话费)")
	private String product_id;
	
	@ZteSoftCommentAnnotationParam(name="规则编码",type="String",isNecessary="Y",desc="规则编码")
	private String service_code;
	
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

	@Override
    public void check() throws ApiRuleException {

    }

    @Override
    public String getApiMethodName() {
        return BasicConst.API_PREFIX+"payFeeRecords";
    }
}
