package zte.net.ecsord.params.nd.resp;

import params.ZteResponse;

public class OrderResultNottifyRspZte  extends ZteResponse {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 能力开发平台返回的错误码
	 */
	private String res_code;
	
	/**
	 * 能力开发平台返回的错误信息
	 */
	private String res_message;
	
	
	/**
	 * 中兴平台错误信息
	 */
	private Result resultMsg ;
	
	
	

	public String getRes_code() {
		return res_code;
	}

	public void setRes_code(String res_code) {
		this.res_code = res_code;
	}

	public String getRes_message() {
		return res_message;
	}

	public void setRes_message(String res_message) {
		this.res_message = res_message;
	}

	public Result getResultMsg() {
		return resultMsg;
	}

	public void setResultMsg(Result resultMsg) {
		this.resultMsg = resultMsg;
	}
	
}
