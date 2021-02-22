package zte.net.iservice.impl;

import params.ZteResponse;
import zte.net.ecsord.params.base.req.AttrInstLoadListReq;
import zte.net.ecsord.params.base.req.AttrSyLoadListReq;
import zte.net.ecsord.params.base.req.GroupAttrInstLoadReq;
import zte.net.ecsord.params.base.req.OrderLockReq;
import zte.net.ecsord.params.base.req.QueryUrlByOrderIdReq;
import zte.net.ecsord.params.base.resp.AttrInstLoadListResp;
import zte.net.ecsord.params.base.resp.AttrSyLoadListResp;
import zte.net.ecsord.params.base.resp.GroupAttrInstLoadResp;
import zte.net.ecsord.params.base.resp.QueryUrlByOrderIdResp;
import zte.net.ecsord.params.ecaop.req.ChannelConvertQrySubReq;
import zte.net.ecsord.params.ecaop.resp.ChannelConvertQrySubResp;
import zte.net.ecsord.params.order.req.NewStartOrderPlanReq;
import zte.net.ecsord.params.order.req.RunWorkflowReq;
import zte.net.ecsord.params.order.req.StartOrderPlanReq;
import zte.net.ecsord.params.order.req.StartWorkflowReq;
import zte.net.ecsord.params.order.req.UpdateOrderTreeReq;
import zte.net.ecsord.params.order.req.WorkflowMatchReq;
import zte.net.ecsord.params.order.resp.RunWorkflowRsp;
import zte.net.ecsord.params.order.resp.StartOrderPlanResp;
import zte.net.ecsord.params.order.resp.StartWorkflowRsp;
import zte.net.ecsord.params.order.resp.UpdateOrderTreeRsp;
import zte.net.ecsord.params.order.resp.WorkflowMatchRsp;
import zte.net.iservice.IOrderPlanService;

import com.ztesoft.api.ApiBusiException;
import com.ztesoft.api.spring.ApiContextHolder;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotation;
import com.ztesoft.rop.annotation.NeedInSessionType;
import com.ztesoft.rop.annotation.ServiceMethod;

public class ZteOrderPlanOpenService implements IOrderPlanService {

	private IOrderPlanService orderPlanService;
	private void init() {
		if (null == orderPlanService) orderPlanService = ApiContextHolder.getBean("orderPlanService");
	}
	@ServiceMethod(method = "zte.orderService.orderstandingplan.start", version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public StartOrderPlanResp startOrderStandingPlan(StartOrderPlanReq req){
		this.init();
		return orderPlanService.startOrderStandingPlan(req);
	}
	@ServiceMethod(method="zte.net.ecsord.params.attr.req.attrsyloadListReq",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public AttrSyLoadListResp attrSyValiList(AttrSyLoadListReq req) throws ApiBusiException{
		this.init();
		return orderPlanService.attrSyValiList(req);
	}
	@ServiceMethod(method="zte.net.ecsord.params.attr.req.AttrInstLoadListReq",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public AttrInstLoadListResp attrInstLoadList(AttrInstLoadListReq req)throws ApiBusiException{
		this.init();
		return orderPlanService.attrInstLoadList(req);
	}
	@ServiceMethod(method="zte.net.ecsord.params.attr.req.groupAttrInstLoadReq",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public GroupAttrInstLoadResp groupAndInsertAttrByTable(GroupAttrInstLoadReq req)throws ApiBusiException {
		this.init();
		return orderPlanService.groupAndInsertAttrByTable(req);
	}
	@ServiceMethod(method="zte.net.ecsord.OrderLockReq",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public ZteResponse order_lock(OrderLockReq req){
		this.init();
		return orderPlanService.order_lock(req);
	}
	@ServiceMethod(method="zte.net.ecsord.QueryUrlByOrderIdReq",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public  QueryUrlByOrderIdResp getJspPath(QueryUrlByOrderIdReq req){
		this.init();
		return orderPlanService.getJspPath(req);
	}
	@Override
	@ServiceMethod(method="zte.orderService.orderstandingplan.start.new",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public ZteResponse startOrderStandingPlan(NewStartOrderPlanReq req) {
		// TODO Auto-generated method stub
		this.init();
		return orderPlanService.startOrderStandingPlan(req);
	}
	
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "启动自定义流程", summary = "启动自定义流程")
	@ServiceMethod(method = "zte.orderService.orderstandingplan.startWorkflow", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public StartWorkflowRsp doStartWorkflow(StartWorkflowReq req)
			throws Exception {
		this.init();
		return this.orderPlanService.doStartWorkflow(req);
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "自定义流程匹配", summary = "自定义流程匹配")
	@ServiceMethod(method = "zte.orderService.orderstandingplan.workflowMatch", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public WorkflowMatchRsp doWorkflowMatch(WorkflowMatchReq req)
			throws Exception {
		this.init();
		return this.orderPlanService.doWorkflowMatch(req);
	}
	
	@Override
    @ZteSoftCommentAnnotation(type = "method", desc = "渠道转换接口", summary = "渠道转换接口")
    @ServiceMethod(method = "ecaop.trades.query.channel.convert.qry", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
    public ChannelConvertQrySubResp channelConvertSubInfo (ChannelConvertQrySubReq req) throws ApiBusiException {
        this.init();
        return this.orderPlanService.channelConvertSubInfo(req);
    }
	
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "执行自定义流程", summary = "执行自定义流程")
	@ServiceMethod(method = "zte.orderService.orderstandingplan.runWorkflow", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public RunWorkflowRsp doRunWorkflow(RunWorkflowReq req)
			throws Exception {
		this.init();
		return this.orderPlanService.doRunWorkflow(req);
	}
	
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "更新订单树对象", summary = "更新订单树对象")
	@ServiceMethod(method = "zte.orderService.orderstandingplan.updateOrderTree", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public UpdateOrderTreeRsp updateOrderTree(UpdateOrderTreeReq req)
			throws Exception {
		this.init();
		return this.orderPlanService.updateOrderTree(req);
	}
}
