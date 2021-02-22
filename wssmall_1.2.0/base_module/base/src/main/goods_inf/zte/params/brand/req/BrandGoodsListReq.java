package zte.params.brand.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import commons.CommonTools;

import consts.ConstsCore;

import params.ZteError;
import params.ZteRequest;

public class BrandGoodsListReq extends ZteRequest {
	@ZteSoftCommentAnnotationParam(name="品牌ID",type="String",isNecessary="Y",desc="品牌ID，根据品牌ID获取品牌下的商品信息")
	private String brand_id; 
	
	@Override
	public void check() throws ApiRuleException {
		if(brand_id==null) CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL,"brand_id不能为空"));
	}

	@Override
	public String getApiMethodName() {
		return "zte.goodsService.brandGoods.list";
	}

	public String getBrand_id() {
		return brand_id;
	}

	public void setBrand_id(String brandId) {
		brand_id = brandId;
	}

}
