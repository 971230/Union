package zte.net.ecsord.params.sr.resp;

import params.ZteResponse;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class RceiveICCIDResp  extends ZteResponse {
	@ZteSoftCommentAnnotationParam(name="唯一流水号",type="String",isNecessary="Y",desc="serial_no：唯一流水号")
	private String serial_no;	
	@ZteSoftCommentAnnotationParam(name="时间",type="String",isNecessary="Y",desc="time：时间")
	private String time;	
	@ZteSoftCommentAnnotationParam(name="返回代码",type="String",isNecessary="Y",desc="resp_code：返回代码")
	private String resp_code;	
	@ZteSoftCommentAnnotationParam(name="返回描述",type="String",isNecessary="Y",desc="resp_msg：返回描述")
	private String resp_msg;
	public String getSerial_no() {
		return serial_no;
	}
	public void setSerial_no(String serial_no) {
		this.serial_no = serial_no;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
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
	
}
