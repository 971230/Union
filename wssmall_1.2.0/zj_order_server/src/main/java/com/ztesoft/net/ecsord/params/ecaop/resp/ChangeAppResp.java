package com.ztesoft.net.ecsord.params.ecaop.resp;

import params.ZteResponse;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.ecsord.params.ecaop.vo.OrderIdVO;

public class ChangeAppResp extends ZteResponse {

	@ZteSoftCommentAnnotationParam(name="调用结果",type="String",isNecessary="Y",desc="00000表示成功")
	private String code;
	
	@ZteSoftCommentAnnotationParam(name="错误描述",type="String",isNecessary="Y",desc="错误描述")
	private String msg;
	
	@ZteSoftCommentAnnotationParam(name="具体数据",type="String",isNecessary="Y",desc="具体数据")
	private OrderIdVO respJson;

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

	public OrderIdVO getRespJson() {
		return respJson;
	}

	public void setRespJson(OrderIdVO respJson) {
		this.respJson = respJson;
	}

}

