package zte.net.ecsord.params.ecaop.resp;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteResponse;

public class BusinessAcceptenceAndVerificationResponse extends ZteResponse {

	/**
	 * 2.1.15	续约活动校验和受理接口响应
	 */
	private static final long serialVersionUID = -3947947450707581290L;
	
	
	@ZteSoftCommentAnnotationParam(name="调用结果",type="String",isNecessary="Y",desc="非空，00000表示成功，其他具体见附录3错误码列表，[5]")
	private String code;
	
	@ZteSoftCommentAnnotationParam(name="错误描述，非空",type="String",isNecessary="Y",desc="错误描述，非空")
	private String msg;	
	
	@ZteSoftCommentAnnotationParam(name="返回内容",type="String",isNecessary="Y",desc="返回内容")
	private String orderID;

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

	public String getOrderID() {
		return orderID;
	}

	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}	
	
	

}
