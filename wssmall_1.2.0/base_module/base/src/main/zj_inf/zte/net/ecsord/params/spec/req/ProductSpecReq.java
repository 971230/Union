package zte.net.ecsord.params.spec.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.mall.core.model.Goods;
import commons.CommonTools;

import consts.ConstsCore;

import params.ZteError;
import params.ZteRequest;

public class ProductSpecReq extends ZteRequest {

	private Goods product ;
	
	public Goods getProduct() {
		return product;
	}

	public void setProduct(Goods product) {
		this.product = product;
	}

	@Override
	public void check() throws ApiRuleException {
		if(product == null) CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "货品对象不允许为空"));
	}

	@Override
	public String getApiMethodName() {
		return "com.goodsService.productSpec.get";
	}

}
