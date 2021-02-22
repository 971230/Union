package zte.net.ecsord.params.pub.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;

import params.ZteRequest;

public class LogiCompCodeGetReq extends ZteRequest{
	
	@ZteSoftCommentAnnotationParam(name="物流公司ID",type="String",isNecessary="Y",desc="post_id：物流公司ID")
	private String post_id;

	public String getPost_id() {
		return post_id;
	}

	public void setPost_id(String post_id) {
		this.post_id = post_id;
	}

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		// TODO Auto-generated method stub
		return "com.dcPublicService.logiCompCode.get";
	}

}
