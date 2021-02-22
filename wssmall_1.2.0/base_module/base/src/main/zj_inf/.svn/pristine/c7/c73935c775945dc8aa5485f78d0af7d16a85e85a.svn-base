package zte.net.ecsord.params.sf.resp;

import params.ZteResponse;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class ArtificialSelectResponse extends ZteResponse {

	private static final long serialVersionUID = 2923088842511086333L;

	@ZteSoftCommentAnnotationParam(name = "成功接收的人工筛单", type = "String", isNecessary = "N", desc = "成功接收的人工筛单 订单号 订单号 ， 如果有多个订单号，以逗分 如果有多个订单号，以逗分 隔，如 ”123,124123,124 123,124 123,124 ”")
	private String orderid;

	@ZteSoftCommentAnnotationParam(name = "未成功接收的人工筛单", type = "String", isNecessary = "N", desc = "未成功接收的人工筛单订单号，如果有多个订单号，以逗号 分 隔 ， 如 ”123,124” ， 这部分订单的筛单数据顺丰会定时重发。")
	private String orderid_error;
	
	@ZteSoftCommentAnnotationParam(name = "结果编码", type = "String", isNecessary = "N", desc = "返回结果编码:OK成功;ERR:失败")
	private String respOrderStatus; 
	 
	@ZteSoftCommentAnnotationParam(name = "结果描述", type = "String", isNecessary = "N", desc = "结果描述信息")
	private String respOrderDesc; 

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public String getOrderid_error() {
		return orderid_error;
	}

	public void setOrderid_error(String orderid_error) {
		this.orderid_error = orderid_error;
	}

	public String getRespOrderStatus() {
		return respOrderStatus;
	}

	public void setRespOrderStatus(String respOrderStatus) {
		this.respOrderStatus = respOrderStatus;
	}

	public String getRespOrderDesc() {
		return respOrderDesc;
	}

	public void setRespOrderDesc(String respOrderDesc) {
		this.respOrderDesc = respOrderDesc;
	}
	

}
