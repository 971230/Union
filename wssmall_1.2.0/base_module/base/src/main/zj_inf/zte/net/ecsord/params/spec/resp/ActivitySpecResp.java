package zte.net.ecsord.params.spec.resp;

import java.util.Map;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteResponse;

public class ActivitySpecResp extends ZteResponse {
	@ZteSoftCommentAnnotationParam(name="活动规格信息Map",type="String",isNecessary="Y",desc="活动规格信息Map")
	private Map specs;

	public Map getSpecs() {
		return specs;
	}

	public void setSpecs(Map specs) {
		this.specs = specs;
	}
}
