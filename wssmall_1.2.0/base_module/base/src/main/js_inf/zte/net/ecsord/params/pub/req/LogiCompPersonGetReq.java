package zte.net.ecsord.params.pub.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;

import params.ZteRequest;

public class LogiCompPersonGetReq extends ZteRequest{
	
	@ZteSoftCommentAnnotationParam(name="物流公司编码",type="String",isNecessary="Y",desc="post_id：物流公司编码")
	private String post_id;
	
	@ZteSoftCommentAnnotationParam(name="地市编码",type="String",isNecessary="Y",desc="city_code：地市编码")
	private String city_code;
	public String getPost_id() {
		return post_id;
	}

	public void setPost_id(String post_id) {
		this.post_id = post_id;
	}

	public String getCity_code() {
		return city_code;
	}

	public void setCity_code(String city_code) {
		this.city_code = city_code;
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
		return "com.dcPublicService.logiCompPerson.list";
	}

}
