package com.ztesoft.net.ecsord.params.ecaop.resp;

import java.util.HashMap;
import java.util.Map;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteResponse;

public class CardInfoGetAPPResp extends ZteResponse{
	@ZteSoftCommentAnnotationParam(name = "接口返回代码", type = "String", isNecessary = "Y", desc = "接口返回代码  ")
	private String resp_code = "";
	@ZteSoftCommentAnnotationParam(name = "返回描述", type = "String", isNecessary = "Y", desc = "返回描述  ")
	private String resp_msg = "";
	@ZteSoftCommentAnnotationParam(name = "业务返回结果", type = "String", isNecessary = "Y", desc = "业务返回结果  ")
	private Object card_Info ;
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
	public Object getCard_Info() {
		return card_Info;
	}
	public void setCard_Info(Object card_Info) {
		this.card_Info = card_Info;
	}

	/**
	* get the value from Map
	*/
	public void fromMap(Map map) {
		setResp_code((map.get("resp_code") == null?"":(map.get("resp_code").toString())));
		setResp_msg((map.get("resp_msg") == null?"":(map.get("resp_msg").toString())));
		setCard_Info((map.get("card_Info")));
	}
	/**
	* set the value from Map
	*/
	public Map toMap() {
		Map map = new HashMap();
		map.put("resp_code",getResp_code());
		map.put("resp_msg",getResp_msg());
		map.put("card_Info",getCard_Info());
		return map;
	}
}