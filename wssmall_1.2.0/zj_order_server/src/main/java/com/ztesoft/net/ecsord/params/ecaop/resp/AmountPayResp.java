package com.ztesoft.net.ecsord.params.ecaop.resp;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteResponse;

public class AmountPayResp extends ZteResponse{

	@ZteSoftCommentAnnotationParam(name="调用结果",type="String",isNecessary="Y",desc="调用结果，非空，00000表示成功，其他具体见附录返回编码列表，长度：[5]")
	private String code;
	
	@ZteSoftCommentAnnotationParam(name="错误描述，非空",type="String",isNecessary="Y",desc="错误描述，非空")
	private String msg;
	
	@ZteSoftCommentAnnotationParam(name="省份订单编号",type="String",isNecessary="Y",desc="省份订单编号")
	private String bms_accept_id;

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

	public String getBms_accept_id() {
		return bms_accept_id;
	}

	public void setBms_accept_id(String bms_accept_id) {
		this.bms_accept_id = bms_accept_id;
	}
	
}
