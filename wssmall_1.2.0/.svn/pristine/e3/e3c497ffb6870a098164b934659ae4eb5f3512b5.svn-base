package zte.params.goods.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import commons.CommonTools;

import consts.ConstsCore;

import params.ZteError;
import params.ZteRequest;

public class ProductListReq extends ZteRequest {
	@ZteSoftCommentAnnotationParam(name="业务服务ID",type="String",isNecessary="Y",desc="service_id：业务服务ID， 不能为空。")
	private String service_id;
	
	public String getService_id() {
		return service_id;
	}

	public void setService_id(String service_id) {
		this.service_id = service_id;
	}

	@Override
	public void check() throws ApiRuleException {
		if(service_id==null) CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL,"service_id不能为空"));
	}

	@Override
	public String getApiMethodName() {
		return "com.goodsService.product.id.list";
	}

}
