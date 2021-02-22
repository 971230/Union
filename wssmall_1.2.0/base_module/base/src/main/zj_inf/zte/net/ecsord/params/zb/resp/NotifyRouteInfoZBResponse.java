package zte.net.ecsord.params.zb.resp;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import zte.net.ecsord.params.base.resp.ZteBusiResponse;


/**
 * 
 * @author 路由推送
 * 
 */

public class NotifyRouteInfoZBResponse extends ZteBusiResponse {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2389323880842459161L;

	@ZteSoftCommentAnnotationParam(name = "返回代码", type = "String", isNecessary = "Y", desc = "resp_code：返回代码")
	private String RespCode;
	
	@ZteSoftCommentAnnotationParam(name = "返回描述", type = "String", isNecessary = "Y", desc = "resp_msg：返回描述")
	private String RespDesc;

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

}
