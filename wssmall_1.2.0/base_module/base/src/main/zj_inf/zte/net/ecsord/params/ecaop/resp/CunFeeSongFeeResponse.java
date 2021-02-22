package zte.net.ecsord.params.ecaop.resp;

import params.ZteResponse;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class CunFeeSongFeeResponse extends ZteResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7823061356332673364L;
	
	@ZteSoftCommentAnnotationParam(name = "AOP接口返回码 ", type = "String", isNecessary = "Y", desc = "aop_result：AOP接口返回码")
	private String aop_result;
	@ZteSoftCommentAnnotationParam(name = "AOP接口返回描述 ", type = "String", isNecessary = "Y", desc = "aop_des:AOP接口返回描述")
	private String aop_des;
	@ZteSoftCommentAnnotationParam(name = "对应accessSEQ流水号 ", type = "String", isNecessary = "Y", desc = "seqNo：对应accessSEQ流水号 ")
	private String seqNo;
	@ZteSoftCommentAnnotationParam(name = "会话ID ", type = "String", isNecessary = "Y", desc = "sessionid：会话ID")
	private String sessionid;
	@ZteSoftCommentAnnotationParam(name = "订购号码 ", type = "String", isNecessary = "Y", desc = "SERIAL_NUMBER：订购号码 ")
	private String serial_number;
	@ZteSoftCommentAnnotationParam(name = "返回码 ", type = "String", isNecessary = "Y", desc = "RESULTCODE：返回码")
	private String resultcode;
	@ZteSoftCommentAnnotationParam(name = "返回描述 ", type = "String", isNecessary = "Y", desc = "RESULTDESC：1：返回描述")
	private String resultdesc;
	

	@ZteSoftCommentAnnotationParam(name = "预留字段:返回代码", type = "String", isNecessary = "Y", desc = "code：返回代码")
	private String code;	
	@ZteSoftCommentAnnotationParam(name = "预留字段:返回描述", type = "String", isNecessary = "Y", desc = "detail：返回描述")
	private String detail;
	public String getAop_result() {
		return aop_result;
	}
	public void setAop_result(String aop_result) {
		this.aop_result = aop_result;
	}
	public String getAop_des() {
		return aop_des;
	}
	public void setAop_des(String aop_des) {
		this.aop_des = aop_des;
	}
	public String getSeqNo() {
		return seqNo;
	}
	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}
	public String getSessionid() {
		return sessionid;
	}
	public void setSessionid(String sessionid) {
		this.sessionid = sessionid;
	}
	public String getSerial_number() {
		return serial_number;
	}
	public void setSerial_number(String serial_number) {
		this.serial_number = serial_number;
	}
	public String getResultcode() {
		return resultcode;
	}
	public void setResultcode(String resultcode) {
		this.resultcode = resultcode;
	}
	public String getResultdesc() {
		return resultdesc;
	}
	public void setResultdesc(String resultdesc) {
		this.resultdesc = resultdesc;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	
}
