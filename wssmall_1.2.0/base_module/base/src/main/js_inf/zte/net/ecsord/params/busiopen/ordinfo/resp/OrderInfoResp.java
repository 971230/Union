package zte.net.ecsord.params.busiopen.ordinfo.resp;

import java.util.List;

import params.ZteResponse;

import zte.net.ecsord.params.busiopen.ordinfo.vo.OrderInfo;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

/**
 * 订单信息查询返回对象
 * 
 * @作者 zhou.qiangang
 * @创建日期 2014-12-11
 * @版本 V 1.0
 */
public class OrderInfoResp extends ZteResponse {

	private static final long serialVersionUID = 4434175563193221523L;

	@ZteSoftCommentAnnotationParam(name = "访问流水", type = "String", isNecessary = "Y", desc = "访问流水")
	private String active_no;

	@ZteSoftCommentAnnotationParam(name = "应答编码", type = "String", isNecessary = "Y", desc = "应答编码:0000：成功 0001：失败")
	private String rsp_code;

	@ZteSoftCommentAnnotationParam(name = "应答描述", type = "String", isNecessary = "Y", desc = "应答描述")
	private String rsp_msg;

	@ZteSoftCommentAnnotationParam(name = "订单信息和支付信息", type = "String", isNecessary = "N", desc = "订单信息和支付信息")
	private List<OrderInfo> orderInfo;

	public String getActive_no() {
		return active_no;
	}

	public void setActive_no(String active_no) {
		this.active_no = active_no;
	}

	public String getRsp_code() {
		return rsp_code;
	}

	public void setRsp_code(String rsp_code) {
		this.rsp_code = rsp_code;
	}

	public String getRsp_msg() {
		return rsp_msg;
	}

	public void setRsp_msg(String rsp_msg) {
		this.rsp_msg = rsp_msg;
	}

	public List<OrderInfo> getOrderInfo() {
		return orderInfo;
	}

	public void setOrderInfo(List<OrderInfo> orderInfo) {
		this.orderInfo = orderInfo;
	}

}
