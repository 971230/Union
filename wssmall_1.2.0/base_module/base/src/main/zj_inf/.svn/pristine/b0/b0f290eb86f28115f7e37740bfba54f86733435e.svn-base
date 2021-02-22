package zte.net.iservice;

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

import com.ztesoft.api.ApiBusiException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotation;

@ZteSoftCommentAnnotation(type="class",desc="正常单与标准化系统交互API",summary="封装正常单与标准化系统交互逻辑")
public interface IOrderPlanService {

	@ZteSoftCommentAnnotation(type = "method", desc = "启动订单归集方案", summary = "启动订单归集方案")
	public StartOrderPlanResp startOrderStandingPlan(StartOrderPlanReq req);
	@ZteSoftCommentAnnotation(type = "method", desc = "启动订单归集方案（规则前置）", summary = "启动订单归集方案（规则前置）")
	public ZteResponse startOrderStandingPlan(NewStartOrderPlanReq req);
	
	@ZteSoftCommentAnnotation(type = "method", desc = "订单同步属性列表验证", summary = "订单同步属性列表验证")
	public AttrSyLoadListResp attrSyValiList(AttrSyLoadListReq req) throws ApiBusiException;
	
	@ZteSoftCommentAnnotation(type = "method", desc = "获取订单属性列表", summary = "获取订单属性列表")
	public AttrInstLoadListResp attrInstLoadList(AttrInstLoadListReq req)throws ApiBusiException ;
	
	@ZteSoftCommentAnnotation(type = "method", desc = "批量插入属性列表", summary = "批量插入属性列表")
	public GroupAttrInstLoadResp groupAndInsertAttrByTable(GroupAttrInstLoadReq req)throws ApiBusiException ;
	
	@ZteSoftCommentAnnotation(type = "method", desc = "订单锁定", summary = "订单锁定")
	public ZteResponse order_lock(OrderLockReq req);
	
	@ZteSoftCommentAnnotation(type = "method", desc = "根据环节获取订单跳转页面", summary = "根据环节获取订单跳转页面")
	public  QueryUrlByOrderIdResp getJspPath(QueryUrlByOrderIdReq req);
	
	@ZteSoftCommentAnnotation(type = "method", desc = "启动自定义流程", summary = "启动自定义流程")
	public StartWorkflowRsp doStartWorkflow(StartWorkflowReq req) throws Exception;
	
	@ZteSoftCommentAnnotation(type = "method", desc = "自定义流程匹配", summary = "自定义流程匹配")
	public WorkflowMatchRsp doWorkflowMatch(WorkflowMatchReq req) throws Exception;
	@ZteSoftCommentAnnotation(type = "method", desc = "渠道转换", summary = "渠道转换")
    public ChannelConvertQrySubResp channelConvertSubInfo (ChannelConvertQrySubReq req)throws ApiBusiException;
	
	@ZteSoftCommentAnnotation(type = "method", desc = "执行自定义流程", summary = "执行自定义流程")
	public RunWorkflowRsp doRunWorkflow(RunWorkflowReq req) throws Exception;
	
	@ZteSoftCommentAnnotation(type = "method", desc = "更新订单树对象", summary = "更新订单树对象")
	public UpdateOrderTreeRsp updateOrderTree(UpdateOrderTreeReq req) throws Exception;
}
