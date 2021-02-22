package zte.net.ecsord.params.sr.resp;

import params.ZteResponse;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class SimulationResultReceiveResponse  extends ZteResponse {
	
	@ZteSoftCommentAnnotationParam(name = "serial_no", type = "String", isNecessary = "Y", desc = "serial_no：请求序列号")
	private String serial_no;
	
	@ZteSoftCommentAnnotationParam(name = "time", type = "String", isNecessary = "Y", desc = "time：yyyymmddhhmiss")
	private String time;
	
	@ZteSoftCommentAnnotationParam(name = "resp_code", type = "String", isNecessary = "Y", desc = "resp_code：返回结果编码0000：成功;0001：失败")
	private String resp_code;
	
	@ZteSoftCommentAnnotationParam(name = "resp_msg", type = "String", isNecessary = "Y", desc = "resp_msg：失败原因")
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
