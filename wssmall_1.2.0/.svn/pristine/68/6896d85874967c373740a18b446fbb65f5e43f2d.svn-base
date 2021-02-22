package zte.params.brand.req;

import params.ZteRequest;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class BrandModelListAllReq extends ZteRequest {
	
	@ZteSoftCommentAnnotationParam(name="型号编码",type="String",isNecessary="N",desc="如果-1或为空时则取全部的型号，否则取对应的型号信息")
	private
	String model_code;

	@Override
	public void check() throws ApiRuleException {
	}

	@Override
	public String getApiMethodName() {
		return "com.goodsService.brandModel.list.all";
	}

	public String getModel_code() {
		return model_code;
	}

	public void setModel_code(String model_code) {
		this.model_code = model_code;
	}

}
