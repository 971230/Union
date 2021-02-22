package zte.net.iservice;

import rule.params.coqueue.req.CoQueueRuleReq;
import rule.params.coqueue.resp.CoQueueRuleResp;
import zte.net.ecsord.params.order.req.StartOrderPlanReq;
import zte.net.ecsord.params.order.resp.StartOrderPlanResp;
import zte.params.order.resp.OrderAddResp;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotation;

@ZteSoftCommentAnnotation(type="class",desc="订单标准化API",summary="订单标准化API")
public interface IOrderStandardizing {
	public CoQueueRuleResp coQueuePerform(CoQueueRuleReq coQueueRuleReq);
	
	public  void startOrderStandingPlan(String service_code, OrderAddResp resp);
	
	public StartOrderPlanResp syncOrderStandardizing(StartOrderPlanReq req);
}
