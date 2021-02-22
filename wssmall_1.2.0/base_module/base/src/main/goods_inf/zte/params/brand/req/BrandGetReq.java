package zte.params.brand.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import commons.CommonTools;
import consts.ConstsCore;

import params.ZteError;
import params.ZteRequest;

public class BrandGetReq extends ZteRequest {
	@ZteSoftCommentAnnotationParam(name="品牌ID",type="String",isNecessary="N",desc="根据brand_id获取品牌，如果goods_id也不为空，就根据goods_id找出商品所属brand_id，再根据这个brand_id获取品牌")
	String brand_id;
	@ZteSoftCommentAnnotationParam(name="商品ID",type="String",isNecessary="N",desc="根据goods_id获取品牌")
	String goods_id;
	public String getBrand_id() {
		return brand_id;
	}

	public void setBrand_id(String brandId) {
		brand_id = brandId;
	}

	public String getGoods_id() {
		return goods_id;
	}

	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}

	@Override
	public void check() throws ApiRuleException {
		if(goods_id==null && brand_id==null) CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL,"brand_id或goods_id不能为空"));
	}

	@Override
	public String getApiMethodName() {
		return "zte.goodsService.brand.get";
	}

}
