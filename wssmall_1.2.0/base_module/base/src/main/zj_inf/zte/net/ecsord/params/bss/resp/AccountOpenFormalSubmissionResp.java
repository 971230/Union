package zte.net.ecsord.params.bss.resp;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteResponse;

public class AccountOpenFormalSubmissionResp extends ZteResponse {

	/**
	 * 沃受理开户正式提交接响应参数
	 */
	private static final long serialVersionUID = 2333407864510101489L;

	@ZteSoftCommentAnnotationParam(name="调用结果",type="String",isNecessary="Y",desc="非空，00000表示成功，其他具体见附录3错误码列表，[5]")
	private String code;
	
	@ZteSoftCommentAnnotationParam(name="错误描述，非空",type="String",isNecessary="Y",desc="错误描述，非空")
	private String msg;	
	

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

	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
