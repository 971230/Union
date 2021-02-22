package com.ztesoft.remote.params.news.req;

import params.ZteError;
import params.ZteRequest;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.api.internal.utils.StringUtils;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import commons.CommonTools;

import consts.ConstsCore;

public class NewsPageReq extends ZteRequest{
   
	@ZteSoftCommentAnnotationParam(name="分页总页数",type="int",isNecessary="Y",desc="分页总页数")
	private int pageSize=0;
	
	@ZteSoftCommentAnnotationParam(name="分页第几页",type="int",isNecessary="Y",desc="分页第几页")
	private int pageNo=0;
	
	@Override
	public void check() throws ApiRuleException {
		if (!StringUtils.isNumeric(pageSize)) {
			CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "总页数必须是数字"));
		}
		
		if (!StringUtils.isNumeric(pageNo)) {
			CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "第几页必须是数字"));
		}
		
	}

	@Override
	public String getApiMethodName() {
		return "com.ztesoft.remote.news.getNewsPage";
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	

}
