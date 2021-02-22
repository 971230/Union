package zte.net.ecsord.params.busiopen.separteflow.resp;

import java.util.Map;

import params.ZteResponse;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

/**
 * 订单分流判断
 * 
 * @作者 wui
 * @创建日期 2014-11-04
 * @版本 V 1.0
 */
public class OrderSeparteResp extends ZteResponse {
	@ZteSoftCommentAnnotationParam(name = "返回代码", type = "String", isNecessary = "N", desc = "new新系统执行分流、old老系统执行分流")
	private String resp_code;
	@ZteSoftCommentAnnotationParam(name = "返回描述", type = "String", isNecessary = "N", desc = "新系统执行分流、老系统执行分流")
	private String resp_msg;
	
	@ZteSoftCommentAnnotationParam(name = "扩展信息，后续扩展", type = "String", isNecessary = "N", desc = "扩展信息，后续扩展")
	Map extMap;
	
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

	public Map getExtMap() {
		return extMap;
	}

	public void setExtMap(Map extMap) {
		this.extMap = extMap;
	}

}
