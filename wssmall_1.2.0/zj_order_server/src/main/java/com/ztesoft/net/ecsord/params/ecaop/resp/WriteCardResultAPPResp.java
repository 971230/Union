package com.ztesoft.net.ecsord.params.ecaop.resp;

import java.util.HashMap;
import java.util.Map;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteResponse;

public class WriteCardResultAPPResp extends ZteResponse{
	@ZteSoftCommentAnnotationParam(name="返回代码",type="String",isNecessary="Y",desc="1失败、0成功 表示接口调用结果")
	private String res_code = "";
	@ZteSoftCommentAnnotationParam(name="返回描述",type="String",isNecessary="Y",desc="失败原因")
	private String res_msg = "";
	public String getRes_code() {
		return res_code;
	}
	public void setRes_code(String res_code) {
		this.res_code = res_code;
	}
	public String getRes_msg() {
		return res_msg;
	}
	public void setRes_msg(String res_msg) {
		this.res_msg = res_msg;
	}
	
	/**
	* get the value from Map
	*/
	public void fromMap(Map map) {
		setRes_code((map.get("res_code") == null?"":(map.get("res_code").toString())));
		setRes_msg((map.get("res_msg") == null?"":(map.get("res_msg").toString())));
	}
	/**
	* set the value from Map
	*/
	public Map toMap() {
		Map map = new HashMap();
		map.put("res_code",getRes_code());
		map.put("res_msg",getRes_msg());
		return map;
	}
}
