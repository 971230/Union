package com.ztesoft.net.ecsord.params.ecaop.resp;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteResponse;

public class DownloadRecordResp extends ZteResponse{

	@ZteSoftCommentAnnotationParam(name="结果编码",type="String",isNecessary="Y",desc="0：成功 1：失败")
	private String resultCode;
	@ZteSoftCommentAnnotationParam(name="录音文件名",type="String",isNecessary="Y",desc="录音文件名")
	private String callOnlyMark;
	@ZteSoftCommentAnnotationParam(name="调用结果",type="String",isNecessary="Y",desc="调用结果，非空，00000表示成功，其他具体见附录返回编码列表，长度：[5]")
	private String resultMsg;
	
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public String getCallOnlyMark() {
		return callOnlyMark;
	}
	public void setCallOnlyMark(String callOnlyMark) {
		this.callOnlyMark = callOnlyMark;
	}
	public String getResultMsg() {
		return resultMsg;
	}
	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}
}
