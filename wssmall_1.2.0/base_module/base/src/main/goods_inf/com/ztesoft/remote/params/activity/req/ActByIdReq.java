package com.ztesoft.remote.params.activity.req;

import params.ZteRequest;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.remote.params.activity.resp.ActResp;
import com.ztesoft.api.ApiRuleException;
import com.ztesoft.api.internal.utils.ApiUtils;

/**
 * 
 * @author wui
 * 活动请求实体
 *
 */
public class ActByIdReq extends ZteRequest {
	
	@ZteSoftCommentAnnotationParam(name="活动标识", type="String", isNecessary="Y", desc="id：活动标识")
	private String id;
	
	public Class<ActResp> getResponseClass() {
		return ActResp.class;
	}


	@Override
    public void check() throws ApiRuleException {
        if (ApiUtils.isBlank(id)) {
            throw new ApiRuleException("-1","活动标识【id】不能为空!");
        }
    }

    @Override
    public String getApiMethodName() {
        return "com.ztesoft.remote.activity.queryActById";
    }


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}
}
