package zte.net.ecsord.params.zb.resp;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import zte.net.ecsord.params.base.resp.ZteBusiResponse;

public class NotifyOrderAbnormalToSystemResponse extends ZteBusiResponse{
	@ZteSoftCommentAnnotationParam(name = "访问流水", type = "String", isNecessary = "Y", desc = "ActiveNo：访问流水")
	private String ActiveNo;
	@ZteSoftCommentAnnotationParam(name = "应答编码", type = "String", isNecessary = "Y", desc = "RespCode：应答编码")
	private String RespCode;
	@ZteSoftCommentAnnotationParam(name = "错误描述", type = "String", isNecessary = "Y", desc = "RespDesc：错误描述")
	private String RespDesc;
	public String getActiveNo() {
		return ActiveNo;
	}
	public void setActiveNo(String activeNo) {
		ActiveNo = activeNo;
	}
	public String getRespCode() {
		return RespCode;
	}
	public void setRespCode(String respCode) {
		this.error_code = respCode;
		RespCode = respCode;
	}
	public String getRespDesc() {
		return RespDesc;
	}
	public void setRespDesc(String respDesc) {
		this.error_msg = respDesc;
		RespDesc = respDesc;
	}
	
	
}
