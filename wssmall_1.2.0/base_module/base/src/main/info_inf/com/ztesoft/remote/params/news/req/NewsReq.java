package com.ztesoft.remote.params.news.req;


import com.ztesoft.api.ApiRuleException;
import com.ztesoft.api.internal.utils.ApiUtils;
import com.ztesoft.api.internal.utils.StringUtils;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import commons.CommonTools;

import consts.ConstsCore;
import params.ZteError;
import params.ZteRequest;

public class NewsReq extends ZteRequest{
    
	@ZteSoftCommentAnnotationParam(name="会员类型",type="String",isNecessary="Y",desc="会员类型")
	private String lv_id;
	
	@ZteSoftCommentAnnotationParam(name="需要显示的条数",type="String",isNecessary="N",desc="需要显示的条数")
	private int size = 1;
	
	@Override
	public void check() throws ApiRuleException {
		if (ApiUtils.isBlank(lv_id)) {
			CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "会员类型【lv_id】不能为空!"));
        }
		if (!StringUtils.isNumeric(size)) {
			CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "显示条数【size】必须是数字!"));
        }
	}

	@Override
	public String getApiMethodName() {
		return "com.ztesoft.remote.news.queryNews";
	}

	public String getLv_id() {
		return lv_id;
	}

	public void setLv_id(String lv_id) {
		this.lv_id = lv_id;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

}
