package com.ztesoft.net.ecsord.params.ecaop.resp;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.ecsord.params.ecaop.vo.PreCommitVO;

import params.ZteResponse;

public class PreCommitBSSResp extends ZteResponse{
	@ZteSoftCommentAnnotationParam(name = "调用结果", type = "String", isNecessary = "Y", desc = "开户预提交->code:调用结果，非空，00000表示成功，其他具体见附录3错误码列表，[5]")
	private String code = "";
	@ZteSoftCommentAnnotationParam(name = "错误描述", type = "String", isNecessary = "Y", desc = "开户预提交->msg:错误描述，非空")
	private String msg = "";
	@ZteSoftCommentAnnotationParam(name = "具体数据", type = "String", isNecessary = "Y", desc = "开户预提交->respJson:具体数据")
	private PreCommitVO respJson ;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public PreCommitVO getRespJson() {
		if (respJson == null) {
			respJson = new PreCommitVO();
		}
		return respJson;
	}
	public void setRespJson(PreCommitVO respJson) {
		this.respJson = respJson;
	}

}
