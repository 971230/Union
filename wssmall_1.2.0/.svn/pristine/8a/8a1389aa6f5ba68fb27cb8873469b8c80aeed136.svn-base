package zte.params.brand.req;

import org.apache.commons.lang3.StringUtils;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import commons.CommonTools;

import consts.ConstsCore;

import params.ZteError;
import params.ZteRequest;

public class BrandModelListByBrandReq extends ZteRequest {
	@ZteSoftCommentAnnotationParam(name="品牌ID",type="String",isNecessary="Y",desc="品牌ID")
	private String brand_id;
	
	public String getBrand_id() {
		return brand_id;
	}

	public void setBrand_id(String brand_id) {
		this.brand_id = brand_id;
	}

	@Override
	public void check() throws ApiRuleException {
		if(StringUtils.isEmpty(brand_id)) CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL,"brand_id不能为空"));
	}

	@Override
	public String getApiMethodName() {
		return "com.goodsService.brandModel.listbybrand";
	}

}
