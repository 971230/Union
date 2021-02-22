package zte.params.goodscats.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import commons.CommonTools;

import consts.ConstsCore;

import params.ZteError;
import params.ZteRequest;

public class CatListByCondReq extends ZteRequest {
	@ZteSoftCommentAnnotationParam(name="分类路径",type="String",isNecessary="Y",desc="catPath：分类路径")
	private String catPath;
	
	public String getCatPath() {
		return catPath;
	}

	public void setCatPath(String catPath) {
		this.catPath = catPath;
	}

	@Override
	public void check() throws ApiRuleException {
		if(catPath==null) CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL,"catPath不能为空"));
	}

	@Override
	public String getApiMethodName() {
		return "com.goodsService.cat.listByCond";
	}

}
