package zte.net.ecsord.params.zb.resp;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import zte.net.ecsord.params.base.resp.ZteBusiResponse;

public class QryCRMInfo2CardResponse extends ZteBusiResponse {
	@ZteSoftCommentAnnotationParam(name="访问流水",type="String",isNecessary="Y",desc="ActiveNo：访问流水")
	private String ActiveNo;
	@ZteSoftCommentAnnotationParam(name="应答编码",type="String",isNecessary="Y",desc="RespCode：应答编码")
	private String RespCode;
	@ZteSoftCommentAnnotationParam(name="错误描述",type="String",isNecessary="Y",desc="RespDesc：错误描述")
	private String RespDesc;
	@ZteSoftCommentAnnotationParam(name="大卡卡号",type="String",isNecessary="Y",desc="ICCID：大卡卡号")
	private String ICCID;
	@ZteSoftCommentAnnotationParam(name="IMSI号",type="String",isNecessary="Y",desc="IMSI：IMSI号")
	private String IMSI;
	@ZteSoftCommentAnnotationParam(name="制卡脚本",type="String",isNecessary="Y",desc="ScriptSeq：制卡脚本")
	private String ScriptSeq;
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
		RespCode = respCode;
	}
	public String getRespDesc() {
		return RespDesc;
	}
	public void setRespDesc(String respDesc) {
		RespDesc = respDesc;
	}
	public String getICCID() {
		return ICCID;
	}
	public void setICCID(String iCCID) {
		ICCID = iCCID;
	}
	public String getIMSI() {
		return IMSI;
	}
	public void setIMSI(String iMSI) {
		IMSI = iMSI;
	}
	public String getScriptSeq() {
		return ScriptSeq;
	}
	public void setScriptSeq(String scriptSeq) {
		ScriptSeq = scriptSeq;
	}
	
}
