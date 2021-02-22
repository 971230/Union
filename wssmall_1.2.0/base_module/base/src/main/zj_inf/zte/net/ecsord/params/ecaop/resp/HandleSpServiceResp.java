package zte.net.ecsord.params.ecaop.resp;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteResponse;

public class HandleSpServiceResp extends ZteResponse {
	private static final long serialVersionUID = 1L;

	@ZteSoftCommentAnnotationParam(name="应答编码",type="String",isNecessary="Y",desc="respCode：应答编码")
	private String RespCode;
	@ZteSoftCommentAnnotationParam(name="错误描述",type="String",isNecessary="Y",desc="respDesc：错误描述")
	private String RespDesc;
	
	public String getRespCode() {
		return RespCode;
	}
	public void setRespCode(String respCode) {
		RespCode = respCode;
	}
	public String getRespDesc() {
		return RespDesc;
	}
	public void setRespDesc(String respDesc) {
		RespDesc = respDesc;
	}
}
