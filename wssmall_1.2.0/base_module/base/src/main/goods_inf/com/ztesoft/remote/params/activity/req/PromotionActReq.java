package com.ztesoft.remote.params.activity.req;

import java.util.Map;

import params.ZteRequest;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

/**
 * 
 * @author wui
 * 活动请求实体-根据活动标签获取
 *
 */
public class PromotionActReq extends ZteRequest {
	
	
	@ZteSoftCommentAnnotationParam(name="活动表数据", type="Map", isNecessary="Y", desc="活动表数据")
	private Map map ;
	


	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}

	@Override
    public void check() throws ApiRuleException {
		
    }

    @Override
    public String getApiMethodName() {
        return "com.ztesoft.remote.activity.getPromotionMap";
    }



}
