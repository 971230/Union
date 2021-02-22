package com.ztesoft.net.outter.inf.iservice;

import java.util.List;

import params.ZteResponse;
import params.coqueue.resp.CoQueueAddResp;
import params.order.req.OrderOuterSyAttrReq;
import zte.params.order.req.OrderStandardPushReq;
import zte.params.order.resp.OrderAddResp;
import zte.params.order.resp.OrderStandardPushResp;

import com.ztesoft.net.model.Outer;

public interface IOuterService {

	public List<CoQueueAddResp> perform(List<Outer> outerList,String service_code,String service_name) throws RuntimeException;
	
	public List<OrderAddResp> orderStanding(List<CoQueueAddResp> coQueueAddResps,String service_code,String service_name);
	
	public ZteResponse insertOuterInstAttr(OrderOuterSyAttrReq req) throws Exception;
	
	public OrderStandardPushResp pushOrderStandard(OrderStandardPushReq pushReq)  throws Exception;
	
}
