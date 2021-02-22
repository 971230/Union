package com.ztesoft.remote.params.notice.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.api.internal.utils.ApiUtils;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import commons.CommonTools;

import consts.ConstsCore;
import params.ZteError;
import params.ZteRequest;
/**
 * 
 * @author wui
 * 公告、消息详情信息
 *
 */
public class NoticeDetailReq extends ZteRequest {
	
	@ZteSoftCommentAnnotationParam(name="公告分类标识",type="String",isNecessary="Y",desc="公告分类标识")
	private String catid; //分类
	
	@ZteSoftCommentAnnotationParam(name="文章标识",type="String",isNecessary="Y",desc="文章标识")
	private String articleid; // 文章id
	
	public String getCatid() {
		return catid;
	}
	public void setCatid(String catid) {
		this.catid = catid;
	}
	public String getArticleid() {
		return articleid;
	}
	public void setArticleid(String articleid) {
		this.articleid = articleid;
	}

	@Override
	public void check() throws ApiRuleException {
		if (ApiUtils.isBlank(catid)) {
			CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "分类【catid】不能为空!"));
        }
		
		if (ApiUtils.isBlank(articleid)) {
			CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "文章标识【articleid】不能为空!"));
        }
	}

	@Override
	public String getApiMethodName() {
		return "com.ztesoft.remote.notice.queryNoticeDetails";
	}
}
