package zte.params.order.resp;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.mall.core.model.Order;
import params.ZteResponse;

import java.util.List;

public class OrderAddResp extends ZteResponse {

	@ZteSoftCommentAnnotationParam(name="订单批次ID",type="String",isNecessary="Y",desc="订单批次ID")
	private String batch_id;
	@ZteSoftCommentAnnotationParam(name="购买订单列表",type="List",isNecessary="Y",desc="购买订单列表")
	private List<Order> orderList;
	private boolean payFlag;//是否需要支付 true 需要
	private ZteResponse zteResponse =new ZteResponse();
	public String getBatch_id() {
		return batch_id;
	}
	public void setBatch_id(String batch_id) {
		this.batch_id = batch_id;
	}
	public List<Order> getOrderList() {
		return orderList;
	}
	public void setOrderList(List<Order> orderList) {
		this.orderList = orderList;
	}
	public boolean isPayFlag() {
		return payFlag;
	}
	public void setPayFlag(boolean payFlag) {
		this.payFlag = payFlag;
	}
	public ZteResponse getZteResponse() {
		return zteResponse;
	}
	public void setZteResponse(ZteResponse zteResponse) {
		this.zteResponse = zteResponse;
	}
	
}
