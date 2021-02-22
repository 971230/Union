package zte.net.ecsord.params.wms.resp;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import zte.net.ecsord.params.base.resp.ZteBusiResponse;


/**
 * 接收WMS订单状态通知
 * 返回对象
 */
public class SynchronousCheckFromWMSResp extends ZteBusiResponse {
	
	@ZteSoftCommentAnnotationParam(name="访问流水",type="String",isNecessary="Y",desc="访问流水")
	private String activeNo;
	@ZteSoftCommentAnnotationParam(name="结果编码",type="String",isNecessary="Y",desc="结果编码[0000：成功、0001：失败]")
	private String errorCode;
	@ZteSoftCommentAnnotationParam(name="结果描述",type="String",isNecessary="Y",desc="结果描述")
	private String errorMessage;

	public String getActiveNo() {
		return activeNo;
	}

	public void setActiveNo(String activeNo) {
		this.activeNo = activeNo;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
