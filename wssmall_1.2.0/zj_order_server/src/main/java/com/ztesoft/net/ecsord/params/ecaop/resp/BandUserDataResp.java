package com.ztesoft.net.ecsord.params.ecaop.resp;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.ecsord.params.ecaop.vo.BandUserDataRespVO;

import params.ZteResponse;

public class BandUserDataResp extends ZteResponse{

	@ZteSoftCommentAnnotationParam(name="调用结果",type="String",isNecessary="Y",desc="调用结果，非空，00000表示成功，其他具体见附录返回编码列表，长度：[5]")
	private String code;
	
	@ZteSoftCommentAnnotationParam(name="错误描述，非空",type="String",isNecessary="Y",desc="错误描述，非空")
	private String msg;
	
	@ZteSoftCommentAnnotationParam(name="具体数据",type="String",isNecessary="Y",desc="具体数据")
	private BandUserDataRespVO respJson;

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

	public BandUserDataRespVO getRespJson() {
		return respJson;
	}

	public void setRespJson(BandUserDataRespVO respJson) {
		this.respJson = respJson;
	}
	
}
