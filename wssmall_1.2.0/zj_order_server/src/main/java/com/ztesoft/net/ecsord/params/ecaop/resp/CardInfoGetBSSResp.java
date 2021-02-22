package com.ztesoft.net.ecsord.params.ecaop.resp;

import java.util.HashMap;
import java.util.Map;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.ecsord.params.ecaop.vo.WhiteCardInfoVO;

import params.ZteResponse;

public class CardInfoGetBSSResp extends ZteResponse{
	@ZteSoftCommentAnnotationParam(name="返回代码",type="String",isNecessary="Y",desc="白卡获取->调用结果，非空，00000表示成功，其他具体见附录3错误码列表，[5]")
	private String code = "";
	
	@ZteSoftCommentAnnotationParam(name="返回描述",type="String",isNecessary="Y",desc="白卡获取->错误描述，非空")
	private String msg = "";
	@ZteSoftCommentAnnotationParam(name="白卡具体数据",type="String",isNecessary="Y",desc="白卡获取->具体数据")
	private WhiteCardInfoVO respJson = new WhiteCardInfoVO();
	
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public WhiteCardInfoVO getRespJson() {
		return respJson;
	}
	public void setRespJson(WhiteCardInfoVO respJson) {
		this.respJson = respJson;
	}
	
	
	/**
	* get the value from Map
	*/
	public void fromMap(Map map) {
		setCode((map.get("res_code").toString().equals("null")?"":(map.get("res_code").toString())));
		setMsg((map.get("res_message").toString().equals("null")?"":(map.get("res_message").toString())));
	}
	/**
	* set the value from Map
	*/
	public Map toMap() {
		Map map = new HashMap();
		map.put("res_code",getCode());
		map.put("res_message",getMsg());
		return map;
	}
}