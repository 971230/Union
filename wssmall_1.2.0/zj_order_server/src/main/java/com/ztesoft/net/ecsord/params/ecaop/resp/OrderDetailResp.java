package com.ztesoft.net.ecsord.params.ecaop.resp;

import java.util.List;

import params.ZteResponse;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.ecsord.params.ecaop.vo.OrderDetail;

public class OrderDetailResp extends ZteResponse {

	private static final long serialVersionUID = 1L;

	@ZteSoftCommentAnnotationParam(name="返回代码",type="String",isNecessary="Y",desc="1失败、0成功")
	private String resp_code;//1失败、0成功
	
	@ZteSoftCommentAnnotationParam(name="返回描述",type="String",isNecessary="Y",desc="失败原因")
	private String resp_msg;
	
	@ZteSoftCommentAnnotationParam(name="返回结果组",type="List",isNecessary="Y",desc="返回结果组")
	private List<OrderDetail> query_resp;

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

	public List<OrderDetail> getQuery_resp() {
		return query_resp;
	}

	public void setQuery_resp(List<OrderDetail> query_resp) {
		this.query_resp = query_resp;
	}

	
}
