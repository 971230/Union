package params.brand.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteRequest;

public class BrandGetReq extends ZteRequest {
	@ZteSoftCommentAnnotationParam(name="品牌ID",type="String",isNecessary="Y",desc="品牌ID")
	String brand_id;
	public String getBrand_id() {
		return brand_id;
	}

	public void setBrand_id(String brandId) {
		brand_id = brandId;
	}

	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub

	}

	@Override
	public String getApiMethodName() {
		return "zte.goodsService.brand.get";
	}

}
