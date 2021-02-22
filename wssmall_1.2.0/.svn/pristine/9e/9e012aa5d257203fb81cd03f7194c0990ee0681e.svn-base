package com.ztesoft.remote.params.adv.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteRequest;

public class AdColumnReq extends ZteRequest{

	@ZteSoftCommentAnnotationParam(name="广告位标识",type="String",isNecessary="N",desc="广告位标识")
	private String acid;
	
	@ZteSoftCommentAnnotationParam(name="广告位类别",type="String",isNecessary="N",desc="广告位类别")
	private String catid;
	
	@ZteSoftCommentAnnotationParam(name="广告位类别",type="String",isNecessary="N",desc="广告位类别：lc-楼层位置；lb-（商品）类别位置")
	private String position;
	
	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getApiMethodName() {
		return "com.ztesoft.remote.adv.getAdColumnDetail";
	}

	public String getAcid() {
		return acid;
	}

	public void setAcid(String acid) {
		this.acid = acid;
	}

	public String getCatid() {
		return catid;
	}

	public void setCatid(String catid) {
		this.catid = catid;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

}
