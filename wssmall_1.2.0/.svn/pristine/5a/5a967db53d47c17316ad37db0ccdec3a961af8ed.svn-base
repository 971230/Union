package zte.params.goodscats.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import commons.CommonTools;

import consts.ConstsCore;

import params.ZteError;
import params.ZteRequest;

public class GoodsViewListByCatIdReq extends ZteRequest {
	@ZteSoftCommentAnnotationParam(name="分类ID",type="String",isNecessary="Y",desc="cat_id：分类ID。附：-1为未分类")
	private String cat_id;
	@ZteSoftCommentAnnotationParam(name="分销商ID",type="String",isNecessary="Y",desc="cat_id：分销商ID。")
	private String user_id;
	
	public String getCat_id() {
		return cat_id;
	}

	public void setCat_id(String cat_id) {
		this.cat_id = cat_id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	@Override
	public void check() throws ApiRuleException {
		if(cat_id == null || "".equals(cat_id)) CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "cat_id：分类ID不允许为空"));
		if(user_id == null || "".equals(user_id)) CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "user_id：分销商ID不允许为空"));
	}

	@Override
	public String getApiMethodName() {
		return "com.goodsService.goods.listViewByCatId";
	}

}
