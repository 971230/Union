package zte.net.ecsord.params.bss.resp;

import zte.net.ecsord.params.base.resp.ZteBusiResponse;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class NumberResourceChangePreCaptureZjResponse extends ZteBusiResponse {
	
	@ZteSoftCommentAnnotationParam(name = "调用结果", type = "String", isNecessary = "Y", desc = "调用结果，非空，00000表示成功")
	private String code;

	@ZteSoftCommentAnnotationParam(name = "错误描述", type = "String", isNecessary = "Y", desc = "错误描述，非空")
	private String msg;
	
	@ZteSoftCommentAnnotationParam(name = "具体数据", type = "String", isNecessary = "Y", desc = "具体数据")
	private String respJson;

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

	public String getRespJson() {
		return msg;
	}

	public void setResp(String respJson) {
		this.respJson = respJson;
	}
	
}
