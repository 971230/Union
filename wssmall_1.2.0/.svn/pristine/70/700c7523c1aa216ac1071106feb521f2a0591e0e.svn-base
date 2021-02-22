package zte.params.goodscats.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import commons.CommonTools;

import consts.ConstsCore;

import params.ZteError;
import params.ZteRequest;

public class ChildrenCatsListReq extends ZteRequest {
	@ZteSoftCommentAnnotationParam(name="分类ID",type="String",isNecessary="Y",desc="cat_id：分类ID 不能为空。")
	private String cat_id;
	
	public String getCat_id() {
		return cat_id;
	}

	public void setCat_id(String cat_id) {
		this.cat_id = cat_id;
	}

	@Override
	public void check() throws ApiRuleException {
		if(cat_id==null) CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL,"cat_id不能为空"));
	}

	@Override
	public String getApiMethodName() {
		return "com.goodsService.cat.children.list";
	}

}
