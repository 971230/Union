package zte.params.goods.req;

import com.ztesoft.api.ApiRuleException;

import params.ZteRequest;

public class SkuQueryReq extends ZteRequest {
	private String activityCode;
	
	public String getActivityCode(){
		return activityCode;
	}

	public void setActivityCode(String activityCode){
		this.activityCode = activityCode;
	}
	
	@Override
	public void check() throws ApiRuleException {
		
	}

	@Override
	public String getApiMethodName() {
		return "com.goodsService.sku.query";
	}
}
