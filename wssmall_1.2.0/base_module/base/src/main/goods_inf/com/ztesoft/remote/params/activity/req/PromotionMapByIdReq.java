package com.ztesoft.remote.params.activity.req;

import params.ZteError;
import params.ZteRequest;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.api.internal.utils.StringUtils;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.remote.params.activity.resp.PromotionMapByIdResp;
import commons.CommonTools;

import consts.ConstsCore;

/**
 * 
 * @author wui
 * 活动请求实体-根据活动标签获取
 *
 */
public class PromotionMapByIdReq extends ZteRequest<PromotionMapByIdResp> {
	
	
	@ZteSoftCommentAnnotationParam(name="活动标识", type="String", isNecessary="Y", desc="activity_id：活动标识")
	private String activity_id;
	
	public Class<PromotionMapByIdResp> getResponseClass() {
		return PromotionMapByIdResp.class;
	}


	@Override
    public void check() throws ApiRuleException {
		
        if (StringUtils.isEmpty((this.getActivity_id()))) {
        	CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "活动标识不能为空"));
        }
    }

    @Override
    public String getApiMethodName() {
        return "com.ztesoft.remote.activity.getPromotionMap";
    }


	public String getActivity_id() {
		return activity_id;
	}


	public void setActivity_id(String activity_id) {
		this.activity_id = activity_id;
	}
}
