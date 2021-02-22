package zte.net.ecsord.params.busiopen.zbinfexec.resp;

import params.ZteResponse;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

/**
 * 订单接口调用
 * 
 * @作者 wui
 * @创建日期 2014-11-04
 * @版本 V 1.0
 */
public class OrderInfExecResp extends ZteResponse {
	@ZteSoftCommentAnnotationParam(name = "返回代码", type = "String", isNecessary = "N", desc = "0000成功、其他失败，")
	private String resp_code;
	@ZteSoftCommentAnnotationParam(name = "返回描述", type = "String", isNecessary = "N", desc = "复用接口执行返回结果")
	private String resp_msg;
	@ZteSoftCommentAnnotationParam(name = "Response返回JSON串", type = "String", isNecessary = "N", desc = "Response返回JSON串")
	private String respObj;

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

	public String getRespObj() {
		return respObj;
	}

	public void setRespObj(String respObj) {
		this.respObj = respObj;
	}


}
