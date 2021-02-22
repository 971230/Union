package com.ztesoft.net.ecsord.params.ecaop.vo;

import java.io.Serializable;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

/**
 * 2.3.8. 订单收单结果查询接口
 * @author 宋琪
 * @date 2017年6月29日
 */
public class OrderResultResp implements Serializable {

	private static final long serialVersionUID = 1L;

	// 订单中心单号 如果收单失败，该节点为空
	@ZteSoftCommentAnnotationParam(name = "order_id", type = "String", isNecessary = "N", desc = "order_id：订单中心单号")
	private String order_id;

	// 订单收单状态 01收单成功未处理 02 处理中 03 处理成功 04 处理失败
	@ZteSoftCommentAnnotationParam(name = "order_receive_status", type = "String", isNecessary = "N", desc = "order_receive_status：订单状态")
	private String order_receive_status;

	// 收单描述 收单失败时，返回具体失败信息。成功时，返回”收单成功”
	@ZteSoftCommentAnnotationParam(name = "order_receive_msg", type = "String", isNecessary = "N", desc = "order_receive_msg：收单描述")
	private String order_receive_msg;

	public String getOrder_receive_status() {
		return order_receive_status;
	}

	public void setOrder_receive_status(String order_receive_status) {
		this.order_receive_status = order_receive_status;
	}

	public String getOrder_receive_msg() {
		return order_receive_msg;
	}

	public void setOrder_receive_msg(String order_receive_msg) {
		this.order_receive_msg = order_receive_msg;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

}
