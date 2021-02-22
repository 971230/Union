package zte.params.goodscats.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import commons.CommonTools;

import consts.ConstsCore;

import params.ZteError;
import params.ZteRequest;

public class CatListByGoodsIdReq extends ZteRequest {
	@ZteSoftCommentAnnotationParam(name="商品ID",type="String",isNecessary="Y",desc="good_id：商品ID。")
	private String good_id;
	
	public String getGood_id() {
		return good_id;
	}

	public void setGood_id(String good_id) {
		this.good_id = good_id;
	}

	@Override
	public void check() throws ApiRuleException {
		if(good_id==null) CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL,"good_id不能为空"));
	}

	@Override
	public String getApiMethodName() {
		return "com.goodsService.cat.list";
	}

}
