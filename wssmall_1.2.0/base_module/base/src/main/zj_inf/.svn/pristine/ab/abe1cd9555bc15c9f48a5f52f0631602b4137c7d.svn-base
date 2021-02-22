package zte.net.ecsord.params.pub.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteRequest;

public class PostRegionGetReq extends ZteRequest {
	@ZteSoftCommentAnnotationParam(name="本地网编码",type="String",isNecessary="Y",desc="lan_code：本地网编码，如东莞市0769")
	private String lan_code;
	
	public String getLan_code() {
		return lan_code;
	}

	public void setLan_code(String lan_code) {
		this.lan_code = lan_code;
	}

	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub

	}

	@Override
	public String getApiMethodName() {
		return "com.dcPublicService.postRegion.get";
	}

}
