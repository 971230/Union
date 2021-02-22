package zte.params.goods.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import commons.CommonTools;

import consts.ConstsCore;

import params.ZteError;
import params.ZteRequest;

public class GoodsCopyReq extends ZteRequest {
	@ZteSoftCommentAnnotationParam(name="商品ID",type="String",isNecessary="Y",desc="goods_id：商品ID。")
	private String goods_id;
	@ZteSoftCommentAnnotationParam(name="用户ID",type="String",isNecessary="Y",desc="user_id：用户ID。")
	private String user_id;
	
	public String getGoods_id() {
		return goods_id;
	}

	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	@Override
	public void check() throws ApiRuleException {
		if(goods_id == null || "".equals(goods_id)) CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "goods_id：商品ID不允许为空"));
		if(user_id == null || "".equals(user_id)) CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "user_id：用户ID不允许为空"));

	}

	@Override
	public String getApiMethodName() {
		return "com.goodsService.goods.copy";
	}

}
