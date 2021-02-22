package com.ztesoft.net.ecsord.params.ecaop.resp;

import params.ZteResponse;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.ecsord.params.ecaop.vo.BssOrderId;

public class FuKaPreOpenResp extends ZteResponse{

	@ZteSoftCommentAnnotationParam(name="调用结果",type="String",isNecessary="Y",desc="调用结果，非空，00000表示成功，其他具体见附录返回编码列表，长度：[5]")
	private String code;
	
	@ZteSoftCommentAnnotationParam(name="错误描述，非空",type="String",isNecessary="Y",desc="错误描述，非空")
	private String msg;
	
	@ZteSoftCommentAnnotationParam(name="最外层节点",type="String",isNecessary="Y",desc="最外层节点,如果查询失败，则resp参数为空")
	private BssOrderId respJson;

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

	public BssOrderId getRespJson() {
		return respJson;
	}

	public void setResp(BssOrderId respJson) {
		this.respJson = respJson;
	}
}
