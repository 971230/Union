package com.ztesoft.net.ecsord.params.ecaop.resp;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import params.ZteResponse;

/**
 * 2.6.12 客户下用户数据查询接口
 * 
 * @author 宋琪
 *
 */
public class UserDataQryResp extends ZteResponse {

	private static final long serialVersionUID = 1L;
	@ZteSoftCommentAnnotationParam(name = "调用结果", type = "String", isNecessary = "Y", desc = "调用结果，非空，00000表示成功")
	private String code;
	@ZteSoftCommentAnnotationParam(name = "错误描述，非空", type = "String", isNecessary = "Y", desc = "错误描述，非空")
	private String msg;
	@ZteSoftCommentAnnotationParam(name = "具体数据", type = "String", isNecessary = "Y", desc = "具体数据")
	private Object respJson;

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

	public Object getRespJson() {
		return respJson;
	}

	public void setRespJson(String respJson) {
		this.respJson = respJson;
	}

}
