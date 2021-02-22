package zte.net.ecsord.params.zb.resp;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import zte.net.ecsord.params.base.resp.ZteBusiResponse;


public class SynchronousOrderZBResponse  extends ZteBusiResponse{
	@ZteSoftCommentAnnotationParam(name = "访问流水", type = "String", isNecessary = "Y", desc = "ActiveNo：访问流水")
	private String ActiveNo;
	
	@ZteSoftCommentAnnotationParam(name = "应答编码", type = "String", isNecessary = "Y", desc = "RespCode：应答编码")
	private String RespCode;
	
	@ZteSoftCommentAnnotationParam(name = "应答信息", type = "String", isNecessary = "Y", desc = "RespDesc：应答信息")
	private String RespDesc;
	
	@ZteSoftCommentAnnotationParam(name = "总部订单号", type = "String", isNecessary = "Y", desc = "OrderId：总部订单号")
	private String OrderId;
	
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
		RespDesc = respDesc;
	}
	public String getOrderId() {
		return OrderId;
	}
	public void setOrderId(String orderId) {
		OrderId = orderId;
	}
	
}
