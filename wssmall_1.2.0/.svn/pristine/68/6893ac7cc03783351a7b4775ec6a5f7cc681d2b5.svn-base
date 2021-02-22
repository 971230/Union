package zte.params.coupon.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import commons.CommonTools;
import consts.ConstsCore;
import params.ZteError;
import params.ZteRequest;

public class FreeOfferCategoryGetReq extends ZteRequest {
	@ZteSoftCommentAnnotationParam(name="赠品分类ID",type="String",isNecessary="Y",desc="fo_category_id：赠品分类ID")	
	private String fo_category_id;
	public String getFo_category_id() {
		return fo_category_id;
	}

	public void setFo_category_id(String fo_category_id) {
		this.fo_category_id = fo_category_id;
	}

	@Override
	public void check() throws ApiRuleException {
		if(fo_category_id==null) CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL,"fo_category_id不能为空"));
	}

	@Override
	public String getApiMethodName() {
		return "com.goodsService.fo.category.get";
	}

}
