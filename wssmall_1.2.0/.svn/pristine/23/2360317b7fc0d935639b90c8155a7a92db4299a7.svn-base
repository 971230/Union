package com.ztesoft.remote.params.news.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.api.internal.utils.ApiUtils;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import commons.CommonTools;

import consts.ConstsCore;
import params.ZteError;
import params.ZteRequest;

public class NewsVoReq extends ZteRequest{
    
	@ZteSoftCommentAnnotationParam(name="消息快讯标识",type="String",isNecessary="Y",desc="消息快讯标识")
	private String news_id;
	
	@Override
	public void check() throws ApiRuleException {
		if (ApiUtils.isBlank(news_id)) {
            CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "消息快讯标识【news_id】不能为空!"));
        }
	}

	@Override
	public String getApiMethodName() {
		return "com.ztesoft.remote.news.getNewsVo";
	}

	public String getNews_id() {
		return news_id;
	}

	public void setNews_id(String news_id) {
		this.news_id = news_id;
	}

}
