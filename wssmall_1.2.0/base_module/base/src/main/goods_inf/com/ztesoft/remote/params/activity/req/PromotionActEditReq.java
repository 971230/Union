package com.ztesoft.remote.params.activity.req;

import params.ZteRequest;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

/**
 * 
 * @author wui
 * 活动请求实体-根据活动标签获取
 *
 */
public class PromotionActEditReq extends ZteRequest {
	
	
	@ZteSoftCommentAnnotationParam(name="活动表状态修改", type="String", isNecessary="Y", desc="团购秒杀同步修改promotion_activity 活动状态")
	private  String pmt_solution;
	private  String enable ;
	



	public String getPmt_solution() {
		return pmt_solution;
	}

	public void setPmt_solution(String pmt_solution) {
		this.pmt_solution = pmt_solution;
	}

	public String getEnable() {
		return enable;
	}

	public void setEnable(String enable) {
		this.enable = enable;
	}

	@Override
    public void check() throws ApiRuleException {
		
    }

    @Override
    public String getApiMethodName() {
        return "com.ztesoft.remote.activity.getPromotionActivityEnable";
    }



}
