package com.ztesoft.remote.params.news.resp;

import params.ZteResponse;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.Page;

public class NewsPageResp extends ZteResponse {
	
	@ZteSoftCommentAnnotationParam(name="分页器",type="Page",isNecessary="N",desc="分页器：newsPage.getResult()获取讯息数据")
	private Page newsPage;

	public Page getNewsPage() {
		return newsPage;
	}

	public void setNewsPage(Page newsPage) {
		this.newsPage = newsPage;
	}

}
