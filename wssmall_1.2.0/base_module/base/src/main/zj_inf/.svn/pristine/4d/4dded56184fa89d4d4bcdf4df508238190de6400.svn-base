package zte.net.ecsord.params.busiopen.ordinfo.resp;

import java.util.List;
import java.util.Map;

import params.ZteResponse;
import zte.net.ecsord.params.busi.req.OrderBusiRequest;
import zte.net.ecsord.params.busi.req.OrderExtBusiRequest;
import zte.net.ecsord.params.busi.req.OrderItemBusiRequest;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

/**
 * 订单查询能力提供
 * 
 * @作者 wui
 * @创建日期 2014-11-04
 * @版本 V 1.0
 */
public class OrderTreesInfoResp extends ZteResponse {
	
	@ZteSoftCommentAnnotationParam(name = "订单基本信息", type = "String", isNecessary = "N", desc = "订单基本信息")
	OrderBusiRequest  orderBusiRequest;
	@ZteSoftCommentAnnotationParam(name = "订单扩展信息", type = "String", isNecessary = "N", desc = "订单扩展信息")
	OrderExtBusiRequest orderExtBusiRequest;
	@ZteSoftCommentAnnotationParam(name = "订单商品单信息", type = "String", isNecessary = "N", desc = "订单商品单信息")
	List<OrderItemBusiRequest> orderItemBusiRequests;
	
	@ZteSoftCommentAnnotationParam(name = "订单其他信息", type = "String", isNecessary = "N", desc = "订单其他信息")
	Map extMap;
	
	public OrderBusiRequest getOrderBusiRequest() {
		return orderBusiRequest;
	}
	public void setOrderBusiRequest(OrderBusiRequest orderBusiRequest) {
		this.orderBusiRequest = orderBusiRequest;
	}
	public OrderExtBusiRequest getOrderExtBusiRequest() {
		return orderExtBusiRequest;
	}
	public void setOrderExtBusiRequest(OrderExtBusiRequest orderExtBusiRequest) {
		this.orderExtBusiRequest = orderExtBusiRequest;
	}
	public List<OrderItemBusiRequest> getOrderItemBusiRequests() {
		return orderItemBusiRequests;
	}
	public void setOrderItemBusiRequests(
			List<OrderItemBusiRequest> orderItemBusiRequests) {
		this.orderItemBusiRequests = orderItemBusiRequests;
	}
	public Map getExtMap() {
		return extMap;
	}
	public void setExtMap(Map extMap) {
		this.extMap = extMap;
	}

}
