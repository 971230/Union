package com.ztesoft.net.ecsord.params.ecaop.resp;

import params.ZteResponse;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.ecsord.params.ecaop.vo.PayPreCheckRespVO;

public class PayPreCheckResp extends ZteResponse {

	@ZteSoftCommentAnnotationParam(name="返回代码",type="String",isNecessary="Y",desc="1失败、0成功")
	private String resp_code;
	
	@ZteSoftCommentAnnotationParam(name="返回描述",type="String",isNecessary="Y",desc="失败原因")
	private String resp_msg;
	
	@ZteSoftCommentAnnotationParam(name="返回报文",type="String",isNecessary="Y",desc="返回结果组")
	private PayPreCheckRespVO query_resp;

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

	public PayPreCheckRespVO getQuery_resp() {
		return query_resp;
	}

	public void setQuery_resp(PayPreCheckRespVO query_resp) {
		this.query_resp = query_resp;
	}

}