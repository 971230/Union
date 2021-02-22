package zte.params.brand.req;

import org.apache.commons.lang3.StringUtils;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import commons.CommonTools;

import consts.ConstsCore;

import params.ZteError;
import params.ZteRequest;

public class BrandModelListByModelCodeReq extends ZteRequest {
	@ZteSoftCommentAnnotationParam(name="型号编码",type="String",isNecessary="Y",desc="型号编码")
	private String model_code;

	public String getModel_code() {
		return model_code;
	}

	public void setModel_code(String model_code) {
		this.model_code = model_code;
	}

	@Override
	public void check() throws ApiRuleException {
		if(StringUtils.isEmpty(model_code)) CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL,"model_code不能为空"));
	}

	@Override
	public String getApiMethodName() {
		return "com.goodsService.brandModel.listbymodel";
	}

}
