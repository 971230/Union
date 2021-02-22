package params.brand.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteRequest;

public class BrandGoodsListReq extends ZteRequest {
	@ZteSoftCommentAnnotationParam(name="品牌ID",type="String",isNecessary="Y",desc="品牌ID")
	private String brand_id; 
	
	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub

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
