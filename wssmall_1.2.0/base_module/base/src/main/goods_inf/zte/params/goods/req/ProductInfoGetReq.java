package zte.params.goods.req;

import org.apache.commons.lang3.StringUtils;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import commons.CommonTools;

import consts.ConstsCore;

import params.ZteError;
import params.ZteRequest;

public class ProductInfoGetReq extends ZteRequest {

	@ZteSoftCommentAnnotationParam(name="货品ID",type="Goods",isNecessary="Y",desc="product_id：货品ID")	
	private String product_id;
	
	public String getProduct_id() {
		return product_id;
	}

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}

	@Override
	public void check() throws ApiRuleException {
		if(StringUtils.isEmpty(product_id)) CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL,"product_id不能为空"));
	}

	@Override
	public String getApiMethodName() {
		return "com.goodsService.product.get";
	}

}
