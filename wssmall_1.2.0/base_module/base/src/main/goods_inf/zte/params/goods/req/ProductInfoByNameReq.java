package zte.params.goods.req;

import org.apache.commons.lang3.StringUtils;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import commons.CommonTools;

import consts.ConstsCore;

import params.ZteError;
import params.ZteRequest;

public class ProductInfoByNameReq extends ZteRequest {

	@ZteSoftCommentAnnotationParam(name="货品名称",type="String",isNecessary="Y",desc="product_name：货品名称")	
	private String product_name;
	
	

	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	@Override
	public void check() throws ApiRuleException {
		if(StringUtils.isEmpty(product_name)) CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL,"product_name不能为空"));
	}

	@Override
	public String getApiMethodName() {
		return "com.goodsService.product.getByName";
	}

}
