package com.ztesoft.remote.params.article.req;

import params.ZteRequest;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.api.internal.utils.ApiUtils;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class ArticleReq extends ZteRequest {

	@ZteSoftCommentAnnotationParam(name="帮助与支持标识",type="String",isNecessary="Y",desc="帮助与支持标识")
	private String id;

	@Override
	public void check() throws ApiRuleException {
		if (ApiUtils.isBlank(id)) {
			throw new ApiRuleException("-1", "【id】不能为空!");
		}

	}

	@Override
	public String getApiMethodName() {
		return "com.ztesoft.remote.article.getArticleById";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
