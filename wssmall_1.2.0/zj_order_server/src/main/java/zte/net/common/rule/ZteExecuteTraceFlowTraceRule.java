package zte.net.common.rule;

import java.util.HashMap;
import java.util.Map;

import zte.net.common.annontion.context.action.ZteMethodAnnontion;
import zte.net.common.annontion.context.action.ZteServiceAnnontion;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.base.req.BusiCompRequest;
import zte.net.ecsord.params.base.resp.BusiCompResponse;
import zte.net.ecsord.params.busi.req.OrderExtBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;
import zte.net.ecsord.rule.mode.PreRuleFact;
import zte.net.params.req.PlanRuleTreeExeReq;
import zte.net.params.resp.PlanRuleTreeExeResp;
import zte.params.order.req.OrderFlowExceptionBusiDoReq;
import zte.params.order.resp.OrderFlowExceptionBusiDoResp;

import com.ztesoft.api.ApiBusiException;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.ibss.common.util.Const;
import com.ztesoft.net.auto.rule.fact.AutoFact;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;

import commons.CommonTools;
import consts.ConstsCore;

@ZteServiceAnnontion(trace_name="ZteExecuteTraceFlowTraceRule",trace_id="0",version="1.0",desc="环节入口")
public class ZteExecuteTraceFlowTraceRule extends ZteTraceBaseRule {
	
	
	@Override
	@ZteMethodAnnontion(name="执行订单方案-界面调用(请不要关联规则)",group_name="所有模式",order="1",page_show=true,path="enterTraceRule.exec")
	public BusiCompResponse engineDo(BusiCompRequest traceRequest) {
		Map map = traceRequest.getQueryParams();
		if(map==null)CommonTools.addError("1", "参数不能为空");
		String order_id = (String)map.get("order_id");
		String plan_id = (String)map.get("plan_id");
		String rule_id = (String)map.get("rule_id");
		AutoFact fact = (AutoFact) map.get("fact");
		String deal_from = (String) map.get("deal_from");//page 界面来源 inf接口来源
		String deal_type = map.get("deal_type")==null?Const.ORDER_HANDLER_TYPE_DEFAULT:map.get("deal_type").toString();
		String deal_desc = map.get("deal_desc")==null?null:map.get("deal_desc").toString();
		Map<String, Object> dealMap = new HashMap<String, Object>();
		dealMap.put("deal_type", deal_type);
		dealMap.put("deal_desc", deal_desc);
		if(StringUtil.isEmpty(deal_from))deal_from = EcsOrderConsts.DEAL_FROM_INF;
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		OrderExtBusiRequest orderExtBusiRequest = orderTree.getOrderExtBusiRequest();
		String order_mode = orderExtBusiRequest.getOrder_model();
		String abnormal_status = orderExtBusiRequest.getAbnormal_status();
		String abnormal_type = orderExtBusiRequest.getAbnormal_type();
		String curr_trace_id = orderExtBusiRequest.getFlow_trace_id();
		//自动化模式预检货环节如果是自动化异常，点下一步转为人工集中模式，订单流程并转为异常流程
		if(EcsOrderConsts.DIC_ORDER_NODE_C.equals(curr_trace_id) && EcsOrderConsts.OPER_MODE_ZD.equals(order_mode) && EcsOrderConsts.ORDER_ABNORMAL_STATUS_1.equals(abnormal_status) && EcsOrderConsts.ORDER_ABNORMAL_TYPE_3.equals(abnormal_type)){
			orderExtBusiRequest.setOrder_model(EcsOrderConsts.OPER_MODE_RG);//如果为自动化模式侧修改为人工集中模式
			orderExtBusiRequest.setOld_order_model(order_mode);
			orderExtBusiRequest.setVisible_status(EcsOrderConsts.VISIBLE_STATUS_0);//订单设置为可见订单
			orderExtBusiRequest.setIs_exception_flow(EcsOrderConsts.IS_EXCEPTION_FLOW_1);//修改为异常单流程
			if(EcsOrderConsts.ORDER_ABNORMAL_TYPE_3.equals(abnormal_type)){//如果为自动化异常修改为伪自动化异常
				orderExtBusiRequest.setAbnormal_type(EcsOrderConsts.ORDER_ABNORMAL_TYPE_2);
			}
			orderExtBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			orderExtBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
			orderExtBusiRequest.store();
			CommonDataFactory.dataFactory.updateAttrFieldValue(order_id, new String[]{AttrConsts.ORDER_MODEL}, new String[]{EcsOrderConsts.OPER_MODE_RG});
			orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		}
		String flow_trace_id = orderTree.getOrderExtBusiRequest().getFlow_trace_id();
		String is_exception_flow = orderTree.getOrderExtBusiRequest().getIs_exception_flow();
		
		//如果是写卡环节侧修改为正常流程 
		if(((EcsOrderConsts.DIC_ORDER_NODE_B.equals(flow_trace_id)&& !EcsOrderConsts.OPER_MODE_XK.equals(order_mode) )|| EcsOrderConsts.DIC_ORDER_NODE_C.equals(flow_trace_id)
				|| EcsOrderConsts.DIC_ORDER_NODE_X.equals(flow_trace_id) || EcsOrderConsts.DIC_ORDER_NODE_F.equals(flow_trace_id) 
				|| EcsOrderConsts.DIC_ORDER_NODE_H.equals(flow_trace_id) || EcsOrderConsts.DIC_ORDER_NODE_J.equals(flow_trace_id)
				|| EcsOrderConsts.DIC_ORDER_NODE_L.equals(flow_trace_id)) && EcsOrderConsts.IS_EXCEPTION_FLOW_1.equals(is_exception_flow)){
			orderExtBusiRequest = orderTree.getOrderExtBusiRequest();
			orderExtBusiRequest.setOrder_id(order_id);
			orderExtBusiRequest.setIs_exception_flow(EcsOrderConsts.IS_EXCEPTION_FLOW_0);//修改为正常单流程
			orderExtBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			orderExtBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
			orderExtBusiRequest.store();
		}
		
//		orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		orderExtBusiRequest = orderTree.getOrderExtBusiRequest();
		abnormal_status = orderExtBusiRequest.getAbnormal_status();
		abnormal_type = orderExtBusiRequest.getAbnormal_type();
		is_exception_flow = orderExtBusiRequest.getIs_exception_flow();
		
		//如果不是异常流程侧恢复异常
		if(!EcsOrderConsts.IS_EXCEPTION_FLOW_1.equals(is_exception_flow)){
			//如果是异常单走异常单处理
			if(EcsOrderConsts.ORDER_ABNORMAL_STATUS_1.equals(abnormal_status)){
				if(EcsOrderConsts.ORDER_ABNORMAL_TYPE_3.equals(abnormal_type) && !EcsOrderConsts.DIC_ORDER_NODE_F.equals(flow_trace_id)){
					//如果是自动化异常，侧卡在质检环节
					map.put("is_auto",EcsOrderConsts.RULE_EXE_0);
					//清空方案ID与规则ID自动按环节执行方案
					plan_id = null;
					rule_id = null;
				}
				OrderFlowExceptionBusiDoReq req = new OrderFlowExceptionBusiDoReq();
				req.setOrder_id(order_id);
				OrderFlowExceptionBusiDoResp resp = orderServices.doOrderFlowExceptionBusi(req);
				
				try {
					//设置可见性
					this.setOrderVisable(order_id);
				} catch (ApiBusiException e) {
					e.printStackTrace();
				}
				orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
				orderExtBusiRequest = orderTree.getOrderExtBusiRequest();
				orderExtBusiRequest.setOrder_id(order_id);
				orderExtBusiRequest.setIs_exception_flow(EcsOrderConsts.IS_EXCEPTION_FLOW_0);//修改为异常单流程
				orderExtBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
				orderExtBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
				orderExtBusiRequest.store();
			}
		}
		
		Object obj = map.get("is_auto");
		int is_auto = 0;
		if(obj!=null)is_auto = Integer.parseInt(obj.toString());
		Object delObject = map.get("deleteLogs");
		boolean dflag = false;
		if(delObject!=null)dflag = (Boolean)delObject;
		BusiCompResponse  resp = this.exePlan(orderTree,plan_id, rule_id, order_id, fact,is_auto,deal_from,dflag,dealMap);
		return resp;
		
	}
	
	@ZteMethodAnnontion(name="工作流回调",group_name="工作流回调",order="1",page_show=true,path="flowCallback.exec")
	public BusiCompResponse flowCallBackBusi(BusiCompRequest busiCompRequest){
		return this.flowCallBack(busiCompRequest);
	}
	/**
	 * 收单框架改造后，启动标准化方案入口
	 * @return
	 */
	@ZteMethodAnnontion(name="订单标准化方案入口（新）",group_name="所有模式",order="1",page_show=true,path="enterTraceRule.startOrderStandingPlan")
	public BusiCompResponse startOrderStandingPlan(BusiCompRequest busiCompRequest){
		BusiCompResponse resp = new BusiCompResponse();
		int is_auto = 0;
		boolean dflag = false;
//		TacheFact fact = new TacheFact();
//		fact.setOrder_id(order_id);
//		fact.setFlow_id(null);
		PreRuleFact fact = new PreRuleFact();
		fact.setOrder_id(busiCompRequest.getOrder_id());
		Map params=busiCompRequest.getQueryParams();
		//执行方案所有规则
		PlanRuleTreeExeReq planRuleTree = new PlanRuleTreeExeReq();
		planRuleTree.setDeal_from( EcsOrderConsts.DEAL_FROM_INF);
		planRuleTree.setDeal_type(Const.ORDER_HANDLER_TYPE_DEFAULT);
		planRuleTree.setDeal_desc(null);
		planRuleTree.setDeleteLogs(dflag);
		planRuleTree.setPlan_id(params.get("plan_id").toString());
		planRuleTree.setFact(fact);
		planRuleTree.setAuto_exe(is_auto);
		PlanRuleTreeExeResp rresp = ruleService.exePlanRuleTree(planRuleTree);
		resp.setError_code("0");
		resp.setError_msg("成功");
		return resp;
	}
}
