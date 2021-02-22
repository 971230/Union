package com.ztesoft.net.server;

import java.util.ArrayList;
import java.util.List;

import com.ztesoft.net.mall.core.model.Order;
import com.ztesoft.net.mall.core.model.OrderOuter;

import params.order.resp.OrderOuterResp;
import zte.params.order.req.OrderSyncReq;
import zte.params.order.resp.OrderAddResp;

public class StdOrderService {


	private StdOrderServ stdOrderServ;
	public OrderAddResp syncAddOrder(OrderSyncReq req) throws Exception {
        OrderAddResp orderAddResp = new OrderAddResp();
		orderAddResp.setUserSessionId(req.getUserSessionId());
	   
		List<OrderOuterResp> orderOuterResps = new ArrayList<OrderOuterResp>();
		for (OrderOuter or : req.getOrderOuterList()) {
			OrderOuterResp osresp = new OrderOuterResp();
			osresp.setOrderOuter(or);
			orderOuterResps.add(osresp);
		}
		stdOrderServ.initParam(req);
		List<Order> list = stdOrderServ.syOuterOrder(req, orderAddResp,orderOuterResps);
		orderAddResp.setOrderList(list);
	    return orderAddResp;
	}
	
	public StdOrderServ getStdOrderServ(){
		return stdOrderServ;
	}
	public void setStdOrderServ(StdOrderServ stdOrderServ){
		this.stdOrderServ = stdOrderServ;
	}
}
