package zte.params.goods.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import commons.CommonTools;

import consts.ConstsCore;

import params.ZteError;
import params.ZteRequest;

public class SelectorGetReq extends ZteRequest {
	@ZteSoftCommentAnnotationParam(name="搜索uri",type="String",isNecessary="Y",desc="uri：搜索uri")	
	private String uri;
	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	@Override
	public void check() throws ApiRuleException {
		if(uri==null) CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL,"uri不能为空"));
	}

	@Override
	public String getApiMethodName() {
		return "com.goodsService.selector.get";
	}

}
