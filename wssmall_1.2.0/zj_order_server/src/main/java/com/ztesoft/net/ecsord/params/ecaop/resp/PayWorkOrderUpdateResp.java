package com.ztesoft.net.ecsord.params.ecaop.resp;

import java.util.Map;

import params.ZteResponse;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

/**
 * 收费单同步接口
 * 
 * @author 宋琪
 * @date 2017年12月1日
 */
public class PayWorkOrderUpdateResp extends ZteResponse {

	private static final long serialVersionUID = 1L;

	@ZteSoftCommentAnnotationParam(name = "返回代码", type = "String", isNecessary = "Y", desc = "1失败、0成功")
	private String resp_code;

	@ZteSoftCommentAnnotationParam(name = "返回描述", type = "String", isNecessary = "Y", desc = "失败原因")
	private String resp_msg;

	@ZteSoftCommentAnnotationParam(name = "返回结果组", type = "List", isNecessary = "Y", desc = "返回结果组")
	private Map<String, String> info_resp;

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

	public Map<String, String> getInfo_resp() {
		return info_resp;
	}

	public void setInfo_resp(Map<String, String> info_resp) {
		this.info_resp = info_resp;
	}

}