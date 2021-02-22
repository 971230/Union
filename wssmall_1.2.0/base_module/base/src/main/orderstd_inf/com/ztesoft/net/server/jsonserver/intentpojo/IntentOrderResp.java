package com.ztesoft.net.server.jsonserver.intentpojo;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

/**
 * 
 * @author song.qi
 *
 */
public class IntentOrderResp{

	@ZteSoftCommentAnnotationParam(name = "", type = "String", isNecessary = "Y", desc = "")
	private String resp_code;
	@ZteSoftCommentAnnotationParam(name = "", type = "String", isNecessary = "Y", desc = "")
	private String resp_msg;
	@ZteSoftCommentAnnotationParam(name = "", type = "String", isNecessary = "Y", desc = "")
	private String order_id;

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

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

}
