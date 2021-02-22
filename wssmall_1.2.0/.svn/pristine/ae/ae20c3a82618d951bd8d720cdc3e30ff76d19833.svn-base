package zte.net.ecsord.params.pub.req;

import params.ZteRequest;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;

public class DictRelationListReq  extends ZteRequest {
	@ZteSoftCommentAnnotationParam(name="字典编码",type="String",isNecessary="Y",desc="stype：字典编码")
	private String stype;
    
	public String getStype() {
		return stype;
	}

	public void setStype(String stype) {
		this.stype = stype;
	}

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		
		return "com.dcPublicService.platFormStatus.list";
	}
}
