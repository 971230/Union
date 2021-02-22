package zte.params.coupon.req;

import params.ZteError;
import params.ZteRequest;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import commons.CommonTools;

import consts.ConstsCore;

public class FreeOfferGetReq extends ZteRequest {


	@ZteSoftCommentAnnotationParam(name="赠品ID",type="Integer",isNecessary="Y",desc="fo_id：赠品ID")	
	private int fo_id;

	
	public int getFo_id() {
		return fo_id;
	}

	public void setFo_id(int fo_id) {
		this.fo_id = fo_id;
	}

	@Override
	public void check() throws ApiRuleException {
		if(fo_id<=0) CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL,"goods_id不能小于等于0"));


	}

	@Override
	public String getApiMethodName() {
		return "com.goodsService.fo.get";
	}

}
