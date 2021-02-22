package zte.net.ecsord.params.wyg.resp;


import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteResponse;

public class NotifyOrderInfoWYGResponse  extends ZteResponse {
	@ZteSoftCommentAnnotationParam(name="返回代码",type="String",isNecessary="Y",desc="resp_code：返回代码")
	private String resp_code;
	
	@ZteSoftCommentAnnotationParam(name="返回描述",type="String",isNecessary="Y",desc="resp_msg：返回描述")
	private String resp_msg;
	
	public String getResp_code() {
		return resp_code;
	}
	
	public void setResp_code(String resp_code) {
		this.resp_code = resp_code;
	}
	
	public String getResp_msg() {
		return resp_msg;
	}
	
	public void setResp_msg(String resp_msg) {
		this.resp_msg = resp_msg;
	}

}
