package zte.net.ecsord.params.bss.resp;

import java.util.Map;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import zte.net.ecsord.params.base.resp.ZteBusiResponse;

/**
 * 客户信息校验
 * 
 * @author fan.qijie
 * 
 * @author song.qi 
 * 2017年12月27日
 */
public class CustomerCheckResp extends ZteBusiResponse {

	private static final long serialVersionUID = 1L;

	@ZteSoftCommentAnnotationParam(name = "返回参数", type = "String", isNecessary = "N", desc = "")
	private Map respJson; 

	@ZteSoftCommentAnnotationParam(name = "", type = "String", isNecessary = "N", desc = "")
	private String msg;
	@ZteSoftCommentAnnotationParam(name = "", type = "String", isNecessary = "N", desc = "")
	private String code;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Map getRespJson() {
		return respJson;
	}

	public void setRespJson(Map respJson) {
		this.respJson = respJson;
	}

}
