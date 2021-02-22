package com.ztesoft.remote.params.activity.req;

import params.ZteError;
import params.ZteRequest;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.remote.params.activity.resp.ActResp;
import com.ztesoft.api.ApiRuleException;
import com.ztesoft.api.internal.utils.ApiUtils;
import com.ztesoft.api.internal.utils.StringUtils;
import commons.CommonTools;

import consts.ConstsCore;

/**
 * 
 * @author wui
 * 活动请求实体-根据活动标签获取
 *
 */
public class ActByTagIdReq extends ZteRequest {
	
	
	@ZteSoftCommentAnnotationParam(name="标签标识", type="String", isNecessary="Y", desc="tagId：标签标识")
	private String tagId;
	
	@ZteSoftCommentAnnotationParam(name="用户标识", type="String", isNecessary="N", desc="userId：用户标识")
	private String userId;
	
	@ZteSoftCommentAnnotationParam(name="分页总页数",type="int",isNecessary="Y",desc="分页总页数")
	private int pageSize = 0;
	
	@ZteSoftCommentAnnotationParam(name="分页第几页",type="int",isNecessary="Y",desc="分页第几页")
	private int pageNo = 0;
	
	
	public Class<ActResp> getResponseClass() {
		return ActResp.class;
	}


	@Override
    public void check() throws ApiRuleException {
		
        if (ApiUtils.isBlank(getTagId())) {
            throw new ApiRuleException("-1", "标签类型【cat_type】不能为空!");
        }
        
        if (!StringUtils.isNumeric(pageSize)) {
			CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "总页数必须是数字"));
		}
		
		if (!StringUtils.isNumeric(pageNo)) {
			CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "第几页必须是数字"));
		}
    }

    @Override
    public String getApiMethodName() {
        return "com.ztesoft.remote.activity.queryActByTagId";
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


	public String getTagId() {
		return tagId;
	}


	public void setTagId(String tagId) {
		this.tagId = tagId;
	}


	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}
}
