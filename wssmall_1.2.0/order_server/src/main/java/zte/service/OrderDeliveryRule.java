package zte.service;

import javax.annotation.Resource;

import params.order.req.DeliveryReq;
import params.order.resp.DeliveryResp;
import rule.impl.DelveryBaseRule;
import zte.net.iservice.IOrderServices;
import zte.params.order.req.OrderConfirmReq;
import zte.params.order.req.OrderShipReq;
import zte.params.order.req.OrderStatusEditReq;
import zte.params.order.req.OrderStockingReq;

import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.model.Order;
import com.ztesoft.net.mall.core.service.IOrderManager;

public class OrderDeliveryRule extends DelveryBaseRule {

	@Resource
	private IOrderServices orderServices;
	@Resource
	private IOrderManager orderManager;
	
	@Override
	public DeliveryResp computeDelivery(DeliveryReq req) {
		String status =req.getOrderOuters().get(0).getStatus();
		DeliveryResp resp = new DeliveryResp();
		resp.setError_code("0");
		if(status==null)return resp;
		if(StringUtil.isEmpty(status))
			return null;
		String reg = "4|5|6|7";
		Order order = req.getOrder();
    	//备货
    	if(status.matches(reg)){
    		OrderStockingReq stockReq = new OrderStockingReq();
    		stockReq.setUserSessionId(req.getUserSessionId());
    		stockReq.setOrder_id(order.getOrder_id());
    		orderServices.stokingOrder(stockReq);
    	}
    	//发货
    	reg = "5|6|7";
    	if(status.matches(reg)){
    		OrderShipReq shipReq = new OrderShipReq();
    		shipReq.setUserSessionId(req.getUserSessionId());
    		shipReq.setShip_zip(order.getShip_zip());
    		shipReq.setShip_addr(order.getShip_addr());
    		shipReq.setShip_name(order.getShip_name());
    		shipReq.setShip_mobile(order.getShip_mobile());
    		shipReq.setOrder_id(order.getOrder_id());
    		orderServices.shipOrder(shipReq);
    	}
    	//确认收货
    	reg = "6|7";
    	if(status.matches(reg)){
    		OrderConfirmReq confirmReq = new OrderConfirmReq();
    		confirmReq.setUserSessionId(req.getUserSessionId());
    		confirmReq.setOrder_id(order.getOrder_id());
    		orderServices.confirmOrder(confirmReq);
    	}
    	//完成订单
    	if(status.equals("7")){
    		OrderStatusEditReq finishReq = new OrderStatusEditReq();
    		finishReq.setUserSessionId(req.getUserSessionId());
    		finishReq.setOrder_id(order.getOrder_id());
    		finishReq.setOrder_status("7");
    		orderServices.editOrderStatus(finishReq);
    	}
    	//取消订单
    	//OrderStatus.ORDER_CONFIRM_CANCEL
    	if("-10".equals(status)){
    		orderManager.cancel(order.getOrder_id());
    	}
    	
		return resp;
	}

}
