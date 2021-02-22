package com.ztesoft.net.ecsord.params.ecaop.resp;

import java.util.List;
import java.util.Map;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteResponse;

public class OrderListActivateResp extends ZteResponse{
	
	private static final long serialVersionUID = 1L;

	@ZteSoftCommentAnnotationParam(name = "返回代码", type = "String", isNecessary = "Y", desc = "1失败、0成功")
	private String resp_code;

	@ZteSoftCommentAnnotationParam(name = "返回描述", type = "String", isNecessary = "Y", desc = "失败原因")
	private String resp_msg;

	@ZteSoftCommentAnnotationParam(name = "返回结果组", type = "List", isNecessary = "Y", desc = "返回结果组")
	private List<Map<String, Object>> query_list;

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

	public List<Map<String, Object>> getQuery_list() {
		return query_list;
	}

	public void setQuery_list(List<Map<String, Object>> query_list) {
		this.query_list = query_list;
	}

	

}
