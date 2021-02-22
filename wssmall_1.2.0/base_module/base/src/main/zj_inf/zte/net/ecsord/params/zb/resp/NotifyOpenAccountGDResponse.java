package zte.net.ecsord.params.zb.resp;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import zte.net.ecsord.params.base.resp.ZteBusiResponse;

public class NotifyOpenAccountGDResponse extends ZteBusiResponse {
	
	@ZteSoftCommentAnnotationParam(name = "访问流水", type = "String", isNecessary = "Y", desc = "ActiveNo：访问流水")
	private String ActiveNo;
	@ZteSoftCommentAnnotationParam(name = "返回代码", type = "String", isNecessary = "Y", desc = "resp_code：返回代码")
	private String resp_code;
	@ZteSoftCommentAnnotationParam(name = "返回描述", type = "String", isNecessary = "Y", desc = "resp_msg：返回描述")
	private String resp_msg;
	
	public String getResp_code() {
		return resp_code;
	}
	
	public void setResp_code(String resp_code) {
		this.error_code = resp_code;
		this.resp_code = resp_code;
	}
	
	public String getResp_msg() {
		return resp_msg;
	}
	
	public void setResp_msg(String resp_msg) {
		this.error_msg = resp_msg;
		this.resp_msg = resp_msg;
	}

	public String getActiveNo() {
		return ActiveNo;
	}

	public void setActiveNo(String activeNo) {
		ActiveNo = activeNo;
	}
}
