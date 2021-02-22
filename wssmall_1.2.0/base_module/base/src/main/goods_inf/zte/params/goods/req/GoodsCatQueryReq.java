package zte.params.goods.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;
import commons.CommonTools;

import consts.ConstsCore;

import params.ZteError;
import params.ZteRequest;

public class GoodsCatQueryReq extends ZteRequest {
	@ZteSoftCommentAnnotationParam(name="分类ID",type="String",isNecessary="Y",desc="tag_id：查询分类商品（门户展示一级分类下所有商品）分类ID 不能为空。")
	private String tag_id;

	public String getTag_id() {
		return tag_id;
	}

	public void setTag_id(String tag_id) {
		this.tag_id = tag_id;
	}

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		if(tag_id==null || "".equals(tag_id))CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "分类ID不能为空"));
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "com.goodsService.goods.cat.get";
	}
}
