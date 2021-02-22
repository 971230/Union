package com.ztesoft.net.ecsord.params.ecaop.resp;

import params.ZteResponse;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.ecsord.params.ecaop.vo.BusiHandleCheckBSSVO;

public class BusiHandleCheckBSSResp extends ZteResponse {

	@ZteSoftCommentAnnotationParam(name="调用结果",type="String",isNecessary="Y",desc="00000表示成功")
	private String code;
	
	@ZteSoftCommentAnnotationParam(name="结果描述",type="String",isNecessary="Y",desc="结果描述")
	private String msg;
	
	@ZteSoftCommentAnnotationParam(name="数据内容",type="BusiHandleCheckBSSVO",isNecessary="Y",desc="数据内容")
	private BusiHandleCheckBSSVO respJson;

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

	public BusiHandleCheckBSSVO getRespJson() {
		return respJson;
	}

	public void setRespJson(BusiHandleCheckBSSVO respJson) {
		this.respJson = respJson;
	}

}
