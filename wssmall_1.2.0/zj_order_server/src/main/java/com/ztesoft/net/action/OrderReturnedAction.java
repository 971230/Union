package com.ztesoft.net.action;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.taobao.api.response.AlibabaTianjiSupplierOrderQueryResponse.DistributionOrderInfo;
import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.ibss.common.util.Const;
import com.ztesoft.net.ecsord.params.ecaop.resp.O2OStatusUpdateResp;
import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.IDaoSupport;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.consts.ZjEcsOrderConsts;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.rule.util.RuleFlowUtil;
import com.ztesoft.net.service.IEcsOrdManager;
import com.ztesoft.net.service.IOrdIntentManager;
import com.ztesoft.net.service.IOrderFlowManager;
import com.ztesoft.net.service.workCustom.interfaces.IWorkCustomCfgManager;
import com.ztesoft.net.service.workCustom.interfaces.IWorkCustomEngine;

import consts.ConstsCore;
import params.order.req.OrderHandleLogsReq;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.StypeConsts;
import zte.net.ecsord.params.base.req.BusiCompRequest;
import zte.net.ecsord.params.base.resp.BusiCompResponse;
import zte.net.ecsord.params.busi.req.OrderBusiRequest;
import zte.net.ecsord.params.busi.req.OrderExtBusiRequest;
import zte.net.ecsord.params.busi.req.OrderLockBusiRequest;
import zte.net.ecsord.params.busi.req.OrderRealNameInfoBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;
import zte.net.ecsord.params.ecaop.req.OrderCancelReq;
import zte.net.ecsord.params.ecaop.resp.OrderCancelResp;
import zte.net.ecsord.params.taobao.req.TbTianjiOrderInfoGetReq;
import zte.net.ecsord.params.taobao.resp.TbTianjiOrderInfoGetResp;
import zte.net.ecsord.params.workCustom.po.ES_WORK_CUSTOM_NODE_INS;
import zte.net.ecsord.params.workCustom.po.WORK_CUSTOM_FLOW_DATA;
import zte.net.ecsord.rule.tache.TacheFact;
import zte.net.ecsord.utils.AttrUtils;
import zte.net.iservice.IRuleService;
import zte.net.params.req.RuleExeLogDelReq;
import zte.net.params.resp.PlanRuleTreeExeResp;
import zte.net.params.resp.RuleTreeExeResp;

import com.ztesoft.net.mall.core.utils.ICacheUtil;

public class OrderReturnedAction extends WWAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5602860936921673445L;
	@Resource
	private IRuleService ruleService;
	@Resource
	private IOrderFlowManager ordFlowManager;
	@Resource
	private IWorkCustomEngine workCustomEngine;
	
	private String order_id;
	private String dealDesc;
	private String returnedReasonCode;//退单原因code，用于爬虫传个总商
	private String dealDescIE8;//IE8 无法取到页面表单的这个数据，临时增加一个变量
	private String applyFrom;//页面按钮来源取值
	
	private String is_refund;//是否退款
	private String orders;//多个订单id
	private OrderTreeBusiRequest orderTree;
	@Resource
	private IEcsOrdManager ecsOrdManager;

	public static final DateFormat DF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//用来格式化日期
	
	@Resource
	private IWorkCustomEngine workCustomEngineManager;
	
	@Resource
	private IWorkCustomCfgManager workCustomCfgManager;
	
	@Resource
	private IOrdIntentManager ordIntentManager;
	
	/**
	 * 自定义流程 退单 只修改订单状态和逻辑归档 关闭流程
	 * @return
	 * @author cqq 20181221
	 */
	public String customeRefundOrder(){
		
		if(ecsOrdManager.isOrderHasAchive(order_id)){
			this.json="{status:1,msg:'订单已归档,不能确认退单!'}";
			return JSON_MESSAGE;
		}
		AdminUser user = ManagerUtils.getAdminUser();
		OrderTreeBusiRequest orderTree= CommonDataFactory.getInstance().getOrderTree(order_id);
		OrderExtBusiRequest orderExtBusiRequest = orderTree.getOrderExtBusiRequest();

		String platType = orderExtBusiRequest.getPlat_type();
		if(StringUtils.equals(platType, EcsOrderConsts.PLAT_TYPE_10061)) {
    		this.json="{status:1,msg:'华盛订单不允许操作退单！'}";
    		return JSON_MESSAGE;
		}
		
		
		/*if(StringUtils.isEmpty(is_refund)&&(this.ifNeedIsRefundBtn(order_id))) {
    		this.json="{status:1,msg:'请选择是否退款'}";
    		return JSON_MESSAGE;
		}
		
		CommonDataFactory.getInstance().updateAttrFieldValue(order_id, 
    			new String[]{AttrConsts.IS_REFUND}, 
    			new String[]{is_refund});//更新是否退款标记
		//记录退单原因    	
		if(!StringUtils.isEmpty(getDealDesc())){
        	String olddesc = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.REFUND_DESC);
        	if(StringUtils.isEmpty(olddesc))
        		dealDesc = ZjEcsOrderConsts.REFUND_CONFIRM_DESC+""+dealDesc;
        	else 
        		dealDesc = olddesc+"|"+ZjEcsOrderConsts.REFUND_CONFIRM_DESC+""+dealDesc;
        	CommonDataFactory.getInstance().updateAttrFieldValue(order_id, 
        			new String[]{AttrConsts.REFUND_DESC}, 
        			new String[]{dealDesc});
    	}else{
    		dealDesc = ZjEcsOrderConsts.REFUND_CONFIRM_DESC+"默认";
    	}
		TacheFact fact = new TacheFact();
		fact.setOrder_id(orderTree.getOrder_id());
		Map m = AttrUtils.getInstance().getRefundPlanInfo(order_id);
		String plan_id = m.get("plan_id").toString();
		String rule_id = m.get("confirm_rule_id").toString();		
	
		RuleTreeExeResp resp = RuleFlowUtil.executeRuleTree(plan_id, rule_id, fact,true,true,ZjEcsOrderConsts.DEAL_FROM_PAGE,null,dealDesc);
		//如果需要退款，调退款方案发起退款流程
		if(EcsOrderConsts.IS_DEFAULT_VALUE.equals(is_refund) && this.ifNeedIsRefundBtn(order_id)) {
			TacheFact refundFact = new TacheFact();
			refundFact.setOrder_id(order_id);
			//执行爬虫退款方案,执行之前，清除上一次的记录
			plan_id = CommonDataFactory.getInstance().getOtherDictVodeValue(StypeConsts.ORDER_REFUND_PLAN, "CRAWLER_REFUND");
			RuleFlowUtil.executePlanRuleTree(plan_id, 0, refundFact, true, ZjEcsOrderConsts.DEAL_FROM_PAGE, "", "退款申请");
		}
		
		this.json="{status:"+resp.getError_code()+",msg:'"+resp.getError_msg()+"'}";
		//本地商城退单之后，如果停留在带退款规则，则不弹出提示，为了方便设置，借用了字典转换来实现配置
		String refundAlert = CommonDataFactory.getInstance().getOtherDictVodeValue("refund_error_alert", orderExtBusiRequest.getOrder_from());
		if(StringUtils.equals(refundAlert, ZjEcsOrderConsts.NO_DEFAULT_VALUE)){
			if(resp.getError_msg().contains("待退款")){
				this.json="{status:0,msg:'退单已确认,等待商城退款结果通知'}";
			}
		}*/
		
		
		
		//结束流程
		try {
			
			//添加退单人员工号和退单时间到es_order_extvtl表中,便于订单报表导出
			Calendar date = new GregorianCalendar();
			String refund_time=DF.format(date.getTime());
			String user_id = user.getUsername();
			if(!StringUtil.isEmpty(user_id)) {
					CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { "refund_userid" }, new String[] { user_id });
				}
			String update_county_extvtl_sql = "update es_order_extvtl set refund_time = to_date('"+refund_time+"','yyyy-MM-dd HH24:mi:ss') where order_id='" + order_id + "'";
			IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
			baseDaoSupport.execute(update_county_extvtl_sql);
			
			//更新订单状态为退单完成
			BusiCompRequest busi = new BusiCompRequest();
			Map queryParams = new HashMap();
			queryParams.put("order_id", order_id);
			busi.setEnginePath("zteCommonTraceRule.setOrdRefund");
			busi.setOrder_id(order_id);
			busi.setQueryParams(queryParams);
			BusiCompResponse busiResp = CommonDataFactory.getInstance().execBusiComp(busi);

			//调用归档规则
			BusiCompRequest busi_archiving = new BusiCompRequest();
			Map queryParams_archiving = new HashMap();
			queryParams_archiving.put("order_id", order_id);
			busi_archiving.setEnginePath("ZteOrderArchiveTraceRule.orderDataArchive");
			busi_archiving.setOrder_id(order_id);
			busi_archiving.setQueryParams(queryParams);
			BusiCompResponse busiResp_archiving = CommonDataFactory.getInstance().execBusiComp(busi_archiving);
			
			workCustomEngineManager.cancelWorkFlow(order_id);
			
			this.json="{status:0,msg:'退单成功!'}";
		} catch (Exception e) {
			this.json="{status:1,msg:'退单异常：'"+e.getMessage()+"}";
			e.printStackTrace();
			return this.JSON_MESSAGE;
		}
		
		return this.JSON_MESSAGE;
		
	}
	
	/**
	 * 环节退单判断
	 * @throws Exception
	 */
	private void cancelOrderCheck() throws Exception {
		IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
		
		ES_WORK_CUSTOM_NODE_INS param = new ES_WORK_CUSTOM_NODE_INS();
		String node_code="isResourceRelease";
		param.setOrder_id(this.order_id);
		param.setNode_code(node_code);
		
		
		List<ES_WORK_CUSTOM_NODE_INS> cancel_order_starts = this.workCustomCfgManager.
				qryInsList(param , null);
		//判断是否是宁波退单
		if(!(cancel_order_starts!=null && cancel_order_starts.size()>0)) {
			node_code="judge_pay";
			param.setNode_code(node_code);
			//查询是否泛智能终端退单
			cancel_order_starts = this.workCustomCfgManager.
					qryInsList(param , null);
		}
		if(cancel_order_starts!=null && cancel_order_starts.size()>0){
			// 跳转到退单统一入口环节
			workCustomEngine.gotoNode(order_id, node_code,"退单自动跳转");
			
			Map map_param = new HashMap();
			map_param.put("dealDesc", dealDesc);
			map_param.put("returnedReasonCode", returnedReasonCode);
			map_param.put("applyFrom", applyFrom);
			String json_param = JSON.toJSONString(map_param);
			
			// 执行退单流程
			WORK_CUSTOM_FLOW_DATA flow_data = workCustomEngine.runNodeManualByCode(order_id, 
					node_code, true, "", "", json_param);
			
			if(ConstsCore.ERROR_FAIL.equals(flow_data.getRun_result()))
				throw new Exception(flow_data.getRun_msg());
		}else{
			
			O2OStatusUpdateResp refundResp = ecsOrdManager.returnToMobileStore(order_id);
			if (!EcsOrderConsts.INF_RESP_CODE_00000.equals(refundResp.getError_code())) {
				throw new Exception(refundResp.getError_msg());
			}
			
			cancelOrder4CustomOrder();
		}
	}
	
	/**
	 * 自定义流程退单
	 * @throws Exception
	 */
	private void cancelOrder4CustomOrder() throws Exception {
		//当前操作员信息
		AdminUser user = ManagerUtils.getAdminUser();
		
		//退单状态
		OrderExtBusiRequest orderExtBusiRequest = this.orderTree.getOrderExtBusiRequest();
		orderExtBusiRequest.setRefund_deal_type(EcsOrderConsts.REFUND_DEAL_TYPE_02);
		orderExtBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
		orderExtBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
		orderExtBusiRequest.store();

		//添加退单人员工号和退单时间到es_order_extvtl表中,便于订单报表导出
		Calendar date = new GregorianCalendar();
		String refund_time=DF.format(date.getTime());
		String user_id = user.getUsername();
		if(!StringUtil.isEmpty(user_id)) {
				CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { "refund_userid" }, new String[] { user_id });
			}
		String update_county_extvtl_sql = "update es_order_extvtl set refund_time = to_date('"+refund_time+"','yyyy-MM-dd HH24:mi:ss') where order_id='" + order_id + "'";
		IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
		baseDaoSupport.execute(update_county_extvtl_sql);
		
		//记录退单原因
    	CommonDataFactory.getInstance().updateAttrFieldValue(this.order_id, 
    			new String[]{AttrConsts.REFUND_DESC,AttrConsts.RETURNED_REASON_CODE}, 
    			new String[]{EcsOrderConsts.REFUND_APPLY_DESC+""+this.dealDesc,this.returnedReasonCode});
		
    	//标记为非商城主动发起的退单
		CommonDataFactory.getInstance().updateAttrFieldValue(this.order_id, 
				new String[]{AttrConsts.ZB_REFUND_STATUS}, 
				new String[]{EcsOrderConsts.NO_DEFAULT_VALUE});
		
		//订单状态
		OrderBusiRequest orderBusiReq = orderTree.getOrderBusiRequest();
		orderBusiReq.setStatus(ZjEcsOrderConsts.DIC_ORDER_STATUS_13);
		orderBusiReq.setIs_dirty(ConstsCore.IS_DIRTY_YES);
		orderBusiReq.setDb_action(ConstsCore.DB_ACTION_UPDATE);
		orderBusiReq.store();
		
		//解锁订单
		ecsOrdManager.unLock(this.order_id);
		
		//写日志
		String handler_comments = ZjEcsOrderConsts.REFUND_APPLY_DESC+""+this.dealDesc;
		CommonDataFactory.getInstance().updateAttrFieldValue(this.order_id, 
    			new String[]{AttrConsts.REFUND_DESC}, 
    			new String[]{handler_comments});
		
		OrderHandleLogsReq req = new OrderHandleLogsReq();
		String flow_id = this.orderTree.getOrderExtBusiRequest().getFlow_id();
		String flowTraceId = this.orderTree.getOrderExtBusiRequest().getFlow_trace_id();
		req.setOrder_id(this.order_id);
		req.setFlow_id(flow_id);
		req.setFlow_trace_id(flowTraceId);
		req.setComments(handler_comments);
		req.setHandler_type(Const.ORDER_HANDLER_TYPE_RETURNED);
		req.setType_code(EcsOrderConsts.REFUND_STATUS_04);
		req.setOp_id(user.getUserid());
		req.setOp_name(user.getUsername());
		
		this.ordFlowManager.insertOrderHandLog(req);
		
		//取消流程
		this.workCustomEngineManager.cancelWorkFlow(this.order_id);
	}
	
	/**
	 * 申请退单
	 * @作者 MoChunrun
	 * @创建日期 2014-11-3 
	 * @return
	 */
	@SuppressWarnings("static-access")
	public String returnedApply(){
		IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
		AdminUser user = ManagerUtils.getAdminUser();
		//判断是否有未完成或处理成功的工单，不允许退单
		String sql = "select * from es_work_order where order_id='" + this.order_id.trim() + "' and status in('0','1')";
		List list = baseDaoSupport.queryForList(sql);
		if (list!=null&&list.size() > 0) {
			this.json = "{status:1,msg:'有未完成或处理成功工单，不允许退单'}";
			return this.JSON_MESSAGE;
		}
		StringBuffer sql_Intent=new  StringBuffer();
		sql_Intent.append("select a.instance_id from ES_WORK_CUSTOM_NODE_INS a where a.order_id='").append(order_id).append("' and a.node_code in ('isResourceRelease','refund_money') ");
		String instance_id=baseDaoSupport.queryForString(sql_Intent.toString());
		
		
		if(!(instance_id==null||"".equals(instance_id))) {
			Map retMap=new HashMap();
			Map map_param=new HashMap();
			map_param.put("dealDesc", dealDesc);
			map_param.put("returnedReasonCode", returnedReasonCode);
			map_param.put("applyFrom", applyFrom);
			String json_param=JSON.toJSONString(map_param);
			try {
				
				ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
				String node_code="";
				StringBuffer cfg_sql=new StringBuffer();
				cfg_sql.append("select distinct a.cfg_id from ES_WORK_CUSTOM_NODE_INS a where a.order_id='").append(order_id).append("' ");
				String cfg_id=baseDaoSupport.queryForString(cfg_sql.toString());
				String flow_ids = cacheUtil.getConfigInfo("is_mobile_release");
				if("9372".equals(cfg_id)) {
					//泛智能退单
					//泛智能终端是否走支付判断环节
					StringBuffer sql_ins=new  StringBuffer();
					sql_ins.append("select a.instance_id from ES_WORK_CUSTOM_NODE_INS a where a.order_id='").append(this.order_id).append("' and a.node_code ='judge_pay' ");
					String ins_id=baseDaoSupport.queryForString(sql_ins.toString());
					if(!(ins_id==null||"".equals(ins_id))) {
						node_code = "judge_pay";
					}else {
						node_code = "refund_money";
					}
					
					
				}else if(flow_ids.indexOf(cfg_id)>=0){
					//号码资源释放退单
					node_code = "isResourceRelease";
				}
				
				if(StringUtils.isEmpty(dealDesc)){        	
		    		this.json="{status:1,msg:'请填写订单备注！'}";
		    		return JSON_MESSAGE;
		    	}
				
				//添加退单人员工号和退单时间到es_order_extvtl表中,便于订单报表导出
				Calendar date = new GregorianCalendar();
				String refund_time=DF.format(date.getTime());
				String user_id = user.getUsername();
				if(!StringUtil.isEmpty(user_id)) {
						CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { "refund_userid" }, new String[] { user_id });
					}
				String update_county_extvtl_sql = "update es_order_extvtl set refund_time = to_date('"+refund_time+"','yyyy-MM-dd HH24:mi:ss') where order_id='" + order_id + "'";
				baseDaoSupport.execute(update_county_extvtl_sql);
				//进入退单流程
				workCustomEngine.gotoNode(order_id, node_code,"");
				//执行退单流程
				WORK_CUSTOM_FLOW_DATA flow_data = workCustomEngine.runNodeManualByCode(order_id, node_code, true, "", "", json_param);
				//退单失败直接throw
				if(ConstsCore.ERROR_FAIL.equals(flow_data.getRun_result()))
					throw new Exception(flow_data.getRun_msg());
				
				this.json = "{status:0,msg:'退单成功'}";
			} catch (Exception e) {
				this.json = "{status:1,msg:'退单失败" + e.getMessage() + "'}";
				e.printStackTrace();
			}
			return JSON_MESSAGE;
		}
		String btnSource = applyFrom;
		orderTree= CommonDataFactory.getInstance().getOrderTree(order_id);
		if("1".equals(btnSource)){//如果applyFrom=2不需要做锁定和环节权限校验
			
			String lockMsg = ecsOrdManager.checkLockUser(order_id, null);
			if(!StringUtils.isEmpty(lockMsg)){
				this.json="{status:1,msg:'"+lockMsg+"'}";
	    		return JSON_MESSAGE;
			}
		}
		
		if(StringUtils.isEmpty(getDealDesc())){        	
    		this.json="{status:1,msg:'请填写订单备注！'}";
    		return JSON_MESSAGE;
    	}
		String order_model = orderTree.getOrderExtBusiRequest().getOrder_model();
		if(EcsOrderConsts.ORDER_MODEL_07.equals(order_model)){
			if(StringUtils.isEmpty(returnedReasonCode)){        	
				this.json="{status:1,msg:'请填选择退单原因！'}";
				return JSON_MESSAGE;
			}
		}
		
		OrderExtBusiRequest orderExtBusiRequest = orderTree.getOrderExtBusiRequest();
    	if(EcsOrderConsts.REFUND_DEAL_TYPE_02.equals(orderExtBusiRequest.getRefund_deal_type())){        	
    		this.json="{status:1,msg:'此单已申请退单，不能重复申请退单。'}";
    		return JSON_MESSAGE;
    	}
    	if(orderTree==null || StringUtil.isEmpty(orderTree.getOrder_id())){
    		this.json="{status:1,msg:'没找到相应的订单。'}";
    		return JSON_MESSAGE;
    	}

		String platType = orderExtBusiRequest.getPlat_type();
		if(StringUtils.equals(platType, EcsOrderConsts.PLAT_TYPE_10061)) {
    		this.json="{status:1,msg:'华盛订单不允许操作退单！'}";
    		return JSON_MESSAGE;
		}
		
		if("1".equals(this.orderTree.getOrderExtBusiRequest().getIs_work_custom())){
			//自定义流程退单直接翻转状态
			try{
				this.cancelOrder4CustomOrder();
				
				this.json="{status:0,msg:'成功'}";
			}catch(Exception e){
				this.json="{status:1,msg:'失败["+e.getMessage()+"]'}";
			}

			return this.JSON_MESSAGE;
		}
		
		//添加退单人员工号和退单时间到es_order_extvtl表中,便于订单报表导出

		Calendar date = new GregorianCalendar();
		String refund_time=DF.format(date.getTime());
		String user_id = user.getUsername();
		if(!StringUtil.isEmpty(user_id)) {
				CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { "refund_userid" }, new String[] { user_id });
			}
		String update_county_extvtl_sql = "update es_order_extvtl set refund_time = to_date('"+refund_time+"','yyyy-MM-dd HH24:mi:ss') where order_id='" + order_id + "'";
		baseDaoSupport.execute(update_county_extvtl_sql);
		
    	//记录退单原因
    	CommonDataFactory.getInstance().updateAttrFieldValue(order_id, 
    			new String[]{AttrConsts.REFUND_DESC,AttrConsts.RETURNED_REASON_CODE}, 
    			new String[]{EcsOrderConsts.REFUND_APPLY_DESC+""+dealDesc,returnedReasonCode});
		
    	//标记为非商城主动发起的退单
		CommonDataFactory.getInstance().updateAttrFieldValue(order_id, 
				new String[]{AttrConsts.ZB_REFUND_STATUS}, 
				new String[]{EcsOrderConsts.NO_DEFAULT_VALUE});
    	/*退单申请和确认都不需要判断是否被锁定，都可以退单（shaoxiong需求）*/
		//if(user.getUserid().equals(orderExtBusiRequest.getLock_user_id())){
			//PlanRuleTreeExeResp resp = RuleFlowUtil.executePlanRuleTree(EcsOrderConsts.ORDER_RETURNED_PLAN, fact);
			
		//获取审核模式，并保存
		String refundMode = AttrUtils.getInstance().getRefundModeByRule(order_id);
		CommonDataFactory.getInstance().updateAttrFieldValue(order_id, 
				new String[]{AttrConsts.REFUND_AUDIT_MODE}, new String[]{refundMode});
		
		//获取方案信息
		Map m = AttrUtils.getInstance().getRefundPlanInfo(order_id);
		String plan_mode = m.get("plan_id").toString();
		String rule_mode = m.get("app_rule_id").toString();	
		
		//清除之前的日志
		CommonDataFactory.getInstance().delRuleExeLog(plan_mode, null, order_id);
		
		//修改订单状态为退单中的状态
		OrderBusiRequest orderBusiReq = orderTree.getOrderBusiRequest();
		orderBusiReq.setStatus(ZjEcsOrderConsts.DIC_ORDER_STATUS_12);
		orderBusiReq.setIs_dirty(ConstsCore.IS_DIRTY_YES);
		orderBusiReq.setDb_action(ConstsCore.DB_ACTION_UPDATE);
		orderBusiReq.store();
		ecsOrdManager.unLock(order_id);
		TacheFact fact = new TacheFact();
		fact.setOrder_id(orderTree.getOrder_id());			
		RuleTreeExeResp resp = RuleFlowUtil.executeRuleTree(plan_mode, rule_mode, fact,true,true,EcsOrderConsts.DEAL_FROM_PAGE);
		if(resp!=null && "0".equals(resp.getError_code())){
			String handler_comments = ZjEcsOrderConsts.REFUND_APPLY_DESC+""+dealDesc;
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, 
        			new String[]{AttrConsts.REFUND_DESC}, 
        			new String[]{handler_comments});
			//写日志
			OrderHandleLogsReq req = new OrderHandleLogsReq();
			String flow_id = orderTree.getOrderExtBusiRequest().getFlow_id();
			String flowTraceId = orderTree.getOrderExtBusiRequest().getFlow_trace_id();
			req.setOrder_id(order_id);
			req.setFlow_id(flow_id);
			req.setFlow_trace_id(flowTraceId);
			req.setComments(handler_comments);
			req.setHandler_type(Const.ORDER_HANDLER_TYPE_RETURNED);
			req.setType_code(EcsOrderConsts.REFUND_STATUS_08);
			req.setOp_id(user.getUserid());
			req.setOp_name(user.getUsername());
			this.ordFlowManager.insertOrderHandLog(req);
			this.json="{status:0,msg:'成功'}";
			
		}else{
			String handler_comments = ZjEcsOrderConsts.REFUND_APPLY_DESC+""+dealDesc;
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, 
        			new String[]{AttrConsts.REFUND_DESC}, 
        			new String[]{handler_comments});
			//写日志
			OrderHandleLogsReq req = new OrderHandleLogsReq();
			String flow_id = orderTree.getOrderExtBusiRequest().getFlow_id();
			String flowTraceId = orderTree.getOrderExtBusiRequest().getFlow_trace_id();
			req.setOrder_id(order_id);
			req.setFlow_id(flow_id);
			req.setFlow_trace_id(flowTraceId);
			req.setComments(handler_comments);
			req.setHandler_type(Const.ORDER_HANDLER_TYPE_RETURNED);
			req.setType_code(EcsOrderConsts.REFUND_STATUS_08);
			req.setOp_id(user.getUserid());
			req.setOp_name(user.getUsername());
			this.ordFlowManager.insertOrderHandLog(req);
			String msg = resp==null?"":resp.getError_msg();
			this.json="{status:1,msg:'失败["+msg+"]'}";
		}
//		}else{
//			this.json="{status:1,msg:'此订单已被其它用户锁定'}";
//		}
		return this.JSON_MESSAGE;
	}

	   
    /**
     * 取消退单
     * @author ZX
	 * @创建日期 2014-11-3 
     * @return
     */
    public String cancelReturned() {
    	
    	   
    	
    	try {
//    		if(StringUtils.isEmpty(getDealDesc())){        	
//        		this.json="{status:1,msg:'请填写处理意见！'}";
//        		return JSON_MESSAGE;
//    			dealDesc = "默认";
//        	}
    		if(ecsOrdManager.isOrderHasAchive(order_id)){
    			this.json="{status:1,msg:'订单已归档,不能取消退单!'}";
    			return JSON_MESSAGE;
    		}
        	orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);    	
        	OrderExtBusiRequest orderExtBusiRequest = orderTree.getOrderExtBusiRequest();
        	if(EcsOrderConsts.REFUND_DEAL_TYPE_01.equals(orderExtBusiRequest.getRefund_deal_type())){        	
        		this.json="{status:1,msg:'此单已取消退单，不能重复操作。'}";
        		return JSON_MESSAGE;
        	}
        	if(!EcsOrderConsts.REFUND_STATUS_08.equals(orderExtBusiRequest.getRefund_status())){        	
        		this.json="{status:1,msg:'此单已确认退单，不能取消退单。'}";
        		return JSON_MESSAGE;
        	}
    		String platType = orderExtBusiRequest.getPlat_type();
    		if(StringUtils.equals(platType, EcsOrderConsts.PLAT_TYPE_10061)) {
        		this.json="{status:1,msg:'华盛订单不允许操作退单！'}";
        		return JSON_MESSAGE;
    		}
        	//记录退单原因
        	if(!StringUtils.isEmpty(getDealDesc())){
	        	String olddesc = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.REFUND_DESC);
	        	if(StringUtils.isEmpty(olddesc))dealDesc = EcsOrderConsts.REFUND_CANCEL_DESC+""+dealDesc;
	        	else dealDesc = olddesc+"|"+EcsOrderConsts.REFUND_CANCEL_DESC+""+dealDesc;
	        	CommonDataFactory.getInstance().updateAttrFieldValue(order_id, 
	        			new String[]{AttrConsts.REFUND_DESC}, 
	        			new String[]{dealDesc});
        	}else{
        		dealDesc = EcsOrderConsts.REFUND_CANCEL_DESC+"默认";
        	}
			
        	AdminUser user = ManagerUtils.getAdminUser();
        	/*退单申请和确认都不需要判断是否被锁定，都可以退单（shaoxiong需求）*/
        	//if(user.getUserid().equals(orderExtBusiRequest.getLock_user_id())){    		
        		TacheFact fact = new TacheFact();
    			fact.setOrder_id(orderTree.getOrder_id());
    			
    			String order_model = orderTree.getOrderExtBusiRequest().getOrder_model();
    			String plan_id = "";
    			String cancel_rule_id = "";
    			if(EcsOrderConsts.ORDER_MODEL_07.equals(order_model)){//爬虫订单
    				plan_id = ZjEcsOrderConsts.ORDER_CANCELRETURNED_PLAN_PC;
    				cancel_rule_id = ZjEcsOrderConsts.ORDER_CANCELRETURNED_RULE_PC;
    			}else{
    				plan_id = EcsOrderConsts.ORDER_CANCELRETURNED_PLAN;
    				cancel_rule_id = EcsOrderConsts.ORDER_CANCELRETURNED_RULE;
    			}
    			//删除取消退单 方案执行日志
        		RuleExeLogDelReq dreq = new RuleExeLogDelReq();
    			dreq.setPlan_id(new String[]{plan_id});
    			dreq.setObj_id(orderTree.getOrder_id());
    			ruleService.delRuleExeLog(dreq);
    			
    			RuleTreeExeResp resp = RuleFlowUtil.executeRuleTree(plan_id,cancel_rule_id, fact,true,true,EcsOrderConsts.DEAL_FROM_PAGE);
    			
    			if(resp!=null && "0".equals(resp.getError_code())){
    				//写日志
    				OrderHandleLogsReq req = new OrderHandleLogsReq();
    				String flow_id = orderTree.getOrderExtBusiRequest().getFlow_id();
    				String flowTraceId = orderTree.getOrderExtBusiRequest().getFlow_trace_id();
    				req.setOrder_id(order_id);
    				req.setFlow_id(flow_id);
    				req.setFlow_trace_id(flowTraceId);
    				req.setComments(dealDesc);
    				req.setHandler_type(Const.ORDER_HANDLER_TYPE_CANCELRETURNED);
    				req.setType_code(EcsOrderConsts.REFUND_STATUS_00);
    				req.setOp_id(user.getUserid());
    				req.setOp_name(user.getUsername());
    				this.ordFlowManager.insertOrderHandLog(req);
    			}
    			ecsOrdManager.updateEsOrderOutallLog(order_id);//取消退单将之前的外呼记录状态给反转成已完成  add by 20190618
    			this.json="{status:"+resp.getError_code()+",msg:'"+resp.getError_msg()+"'}";
    			
//        	}else{
//        		this.json="{status:1,msg:'订单已被其它用户锁定。'}";
//        	}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			this.json="{status:1,msg:'处理失败。'}";
		}
    	return JSON_MESSAGE;
    }

	
	/**
	 * 确认退单
	 * @作者 MoChunrun
	 * @创建日期 2014-11-3 
	 * @return
	 */
	public String cfmReturned(){
		if(ecsOrdManager.isOrderHasAchive(order_id)){
			this.json="{status:1,msg:'订单已归档,不能确认退单!'}";
			return JSON_MESSAGE;
		}
		AdminUser user = ManagerUtils.getAdminUser();
		OrderTreeBusiRequest orderTree= CommonDataFactory.getInstance().getOrderTree(order_id);
		OrderExtBusiRequest orderExtBusiRequest = orderTree.getOrderExtBusiRequest();
		if(!EcsOrderConsts.REFUND_STATUS_08.equals(orderExtBusiRequest.getRefund_status())&&!EcsOrderConsts.REFUND_STATUS_00.equals(orderExtBusiRequest.getRefund_status())){
    		this.json="{status:1,msg:'订单非退单申请状态或已驳回退单。'}";
    		return JSON_MESSAGE;
    	}

		String platType = orderExtBusiRequest.getPlat_type();
		if(StringUtils.equals(platType, EcsOrderConsts.PLAT_TYPE_10061)) {
    		this.json="{status:1,msg:'华盛订单不允许操作退单！'}";
    		return JSON_MESSAGE;
		}
		if(StringUtils.isEmpty(is_refund)&&(this.ifNeedIsRefundBtn(order_id))) {
    		this.json="{status:1,msg:'请选择是否退款'}";
    		return JSON_MESSAGE;
		}
		
		CommonDataFactory.getInstance().updateAttrFieldValue(order_id, 
    			new String[]{AttrConsts.IS_REFUND}, 
    			new String[]{is_refund});//更新是否退款标记
		
		//添加退单人员工号和退单时间到es_order_extvtl表中,便于订单报表导出
		Calendar date = new GregorianCalendar();
		String refund_time=DF.format(date.getTime());
		String user_id = user.getUsername();
		if(!StringUtil.isEmpty(user_id)) {
				CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { "refund_userid" }, new String[] { user_id });
			}
		String update_county_extvtl_sql = "update es_order_extvtl set refund_time = to_date('"+refund_time+"','yyyy-MM-dd HH24:mi:ss') where order_id='" + order_id + "'";
		IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
		baseDaoSupport.execute(update_county_extvtl_sql);
		
		//记录退单原因    	
		if(!StringUtils.isEmpty(getDealDesc())){
        	String olddesc = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.REFUND_DESC);
        	if(StringUtils.isEmpty(olddesc))
        		dealDesc = ZjEcsOrderConsts.REFUND_CONFIRM_DESC+""+dealDesc;
        	else 
        		dealDesc = olddesc+"|"+ZjEcsOrderConsts.REFUND_CONFIRM_DESC+""+dealDesc;
        	CommonDataFactory.getInstance().updateAttrFieldValue(order_id, 
        			new String[]{AttrConsts.REFUND_DESC}, 
        			new String[]{dealDesc});
    	}else{
    		dealDesc = ZjEcsOrderConsts.REFUND_CONFIRM_DESC+"默认";
    	}
		TacheFact fact = new TacheFact();
		fact.setOrder_id(orderTree.getOrder_id());
		Map m = AttrUtils.getInstance().getRefundPlanInfo(order_id);
		String plan_id = m.get("plan_id").toString();
		String rule_id = m.get("confirm_rule_id").toString();		
	
		RuleTreeExeResp resp = RuleFlowUtil.executeRuleTree(plan_id, rule_id, fact,true,true,ZjEcsOrderConsts.DEAL_FROM_PAGE,null,dealDesc);
		//如果需要退款，调退款方案发起退款流程
		if(EcsOrderConsts.IS_DEFAULT_VALUE.equals(is_refund) && this.ifNeedIsRefundBtn(order_id)) {
			TacheFact refundFact = new TacheFact();
			refundFact.setOrder_id(order_id);
			//执行爬虫退款方案,执行之前，清除上一次的记录
			plan_id = CommonDataFactory.getInstance().getOtherDictVodeValue(StypeConsts.ORDER_REFUND_PLAN, "CRAWLER_REFUND");
			RuleFlowUtil.executePlanRuleTree(plan_id, 0, refundFact, true, ZjEcsOrderConsts.DEAL_FROM_PAGE, "", "退款申请");
		}
		
		this.json="{status:"+resp.getError_code()+",msg:'"+resp.getError_msg()+"'}";
		//本地商城退单之后，如果停留在带退款规则，则不弹出提示，为了方便设置，借用了字典转换来实现配置
		String refundAlert = CommonDataFactory.getInstance().getOtherDictVodeValue("refund_error_alert", orderExtBusiRequest.getOrder_from());
		if(StringUtils.equals(refundAlert, ZjEcsOrderConsts.NO_DEFAULT_VALUE)){
			if(resp.getError_msg().contains("待退款")){
				this.json="{status:0,msg:'退单已确认,等待商城退款结果通知'}";
			}
		}
		
		return this.JSON_MESSAGE;
	}
	//如果是爬虫并且(货到付款||金额是0)  就显示按钮
		private boolean ifNeedIsRefundBtn(String order_id){
			boolean falg=false;
			String pay_type = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.PAY_TYPE);
			String order_model = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ORDER_MODEL);
			String order_amount = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ORDER_AMOUNT);
			if(ZjEcsOrderConsts.ORDER_MODEL_07.equals(order_model)||ZjEcsOrderConsts.ORDER_MODEL_08.equals(order_model)) {
				if(!ZjEcsOrderConsts.PAY_TYPE_HDFK.equals(pay_type)){//货到付款
					falg=true;
				}if(Double.valueOf(order_amount)!=0){//或者金额是0的就不显示
					falg=true;
				}
			}
			return falg;
		}
	/**
	 * BSS返销
	 * @作者 MoChunrun
	 * @创建日期 2014-11-4 
	 * @return
	 */
	public String bssReBack(){
		if(ecsOrdManager.isOrderHasAchive(order_id)){
			this.json="{status:1,msg:'订单已归档,不能返销!'}";
			return JSON_MESSAGE;
		}
		AdminUser user = ManagerUtils.getAdminUser();
		OrderTreeBusiRequest orderTree= CommonDataFactory.getInstance().getOrderTree(order_id);
		OrderExtBusiRequest orderExtBusiRequest = orderTree.getOrderExtBusiRequest();
		orderExtBusiRequest.getFlow_trace_id();
		List<OrderLockBusiRequest> orderLockRequest=orderTree.getOrderLockBusiRequests();
		
		String lock_status = "0";
		String lock_user_id= "";
		for(int i=0;i<orderLockRequest.size();i++){
			if(orderExtBusiRequest.getFlow_trace_id().equals(orderLockRequest.get(i).getTache_code())){
				OrderLockBusiRequest orderLockBusiRequest=orderLockRequest.get(i);
				lock_status = orderLockBusiRequest.getLock_status();
				lock_user_id = orderLockBusiRequest.getLock_user_name();
			}
		}
		
		if(!EcsOrderConsts.REFUND_STATUS_01.equals(orderExtBusiRequest.getRefund_status())){   
			if(EcsOrderConsts.REFUND_STATUS_08.equals(orderExtBusiRequest.getRefund_status())){
				this.json="{status:1,msg:'请先确认退单。'}";
			}else{
				this.json="{status:1,msg:'订单BSS已返销。'}";
			}
    		return JSON_MESSAGE;
    	}
		String sending_type =CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.SENDING_TYPE);
		if(EcsOrderConsts.SHIPPING_TYPE_XJ.equals(sending_type)){
			this.json = "{result:1,msg:'是现场交付的单,不能bss返销'}";
			return this.JSON_MESSAGE;
		}
		if(user.getUserid().equals(lock_user_id)||EcsOrderConsts.UNLOCK_STATUS.equals(lock_status)){
			TacheFact fact = new TacheFact();
			fact.setOrder_id(orderTree.getOrder_id());
			RuleTreeExeResp resp = RuleFlowUtil.executeRuleTree(EcsOrderConsts.ORDER_RETURNED_PLAN, EcsOrderConsts.ORDER_RETURN_BSSREBACK_RULE, fact,true,true,EcsOrderConsts.DEAL_FROM_PAGE);
			if(resp!=null && "0".equals(resp.getError_code())){
				//写日志
				OrderHandleLogsReq req = new OrderHandleLogsReq();
				String flow_id = orderTree.getOrderExtBusiRequest().getFlow_id();
				String flowTraceId = orderTree.getOrderExtBusiRequest().getFlow_trace_id();
				req.setOrder_id(order_id);
				req.setFlow_id(flow_id);
				req.setFlow_trace_id(flowTraceId);
				req.setComments(dealDesc);
				req.setHandler_type(Const.ORDER_HANDLER_TYPE_RETURNED);
				req.setType_code(EcsOrderConsts.REFUND_STATUS_02);
				req.setOp_id(user.getUserid());
				req.setOp_name(user.getUsername());
				this.ordFlowManager.insertOrderHandLog(req);
			}
			this.json="{status:"+resp.getError_code()+",msg:'"+resp.getError_msg()+"'}";
		}else{
			this.json="{status:1,msg:'此订单已被其它用户锁定'}";
		}
		return this.JSON_MESSAGE;
	}
	
	/**
	 * ESS返销
	 * @作者 MoChunrun
	 * @创建日期 2014-11-4 
	 * @return
	 */
	public String essReBack(){
		if(ecsOrdManager.isOrderHasAchive(order_id)){
			this.json="{status:1,msg:'订单已归档,不能返销!'}";
			return JSON_MESSAGE;
		}
		AdminUser user = ManagerUtils.getAdminUser();
		OrderTreeBusiRequest orderTree= CommonDataFactory.getInstance().getOrderTree(order_id);
		OrderExtBusiRequest orderExtBusiRequest = orderTree.getOrderExtBusiRequest();
		List<OrderLockBusiRequest> orderLockRequest=orderTree.getOrderLockBusiRequests();
		
		
		OrderLockBusiRequest orderLockBusiRequest = null;
		for(int i=0;i<orderLockRequest.size();i++){
			if(orderExtBusiRequest.getFlow_trace_id().equals(orderLockRequest.get(i).getTache_code())){
				orderLockBusiRequest=orderLockRequest.get(i);
			}
		}
		
		
		if(!EcsOrderConsts.REFUND_STATUS_02.equals(orderExtBusiRequest.getRefund_status())){   
			if(EcsOrderConsts.REFUND_STATUS_08.equals(orderExtBusiRequest.getRefund_status())){
				this.json="{status:1,msg:'请先确认退单。'}";
			}else if(EcsOrderConsts.REFUND_STATUS_01.equals(orderExtBusiRequest.getRefund_status())){
				this.json="{status:1,msg:'请先BSS返销。'}";
			}else{
				this.json="{status:1,msg:'订单ESS已返销。'}";
			}
    		return JSON_MESSAGE;
    	}
		String  sending_type =   CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.SENDING_TYPE);
		if(EcsOrderConsts.SHIPPING_TYPE_XJ.equals(sending_type)){
			this.json = "{result:1,msg:'是现场交付的单,不能bss返销'}";
			return this.JSON_MESSAGE;
		}
		if(orderLockBusiRequest == null || user.getUserid().equals(orderLockBusiRequest.getLock_user_id())||EcsOrderConsts.UNLOCK_STATUS.equals(orderLockBusiRequest.getLock_status())){
			TacheFact fact = new TacheFact();
			fact.setOrder_id(orderTree.getOrder_id());
			RuleTreeExeResp resp = RuleFlowUtil.executeRuleTree(EcsOrderConsts.ORDER_RETURNED_PLAN, EcsOrderConsts.ORDER_RETURN_ESSREBACK_RULE, fact,true,true,EcsOrderConsts.DEAL_FROM_PAGE);
			if(resp!=null && "0".equals(resp.getError_code())){
				//写日志
				OrderHandleLogsReq req = new OrderHandleLogsReq();
				String flow_id = orderTree.getOrderExtBusiRequest().getFlow_id();
				String flowTraceId = orderTree.getOrderExtBusiRequest().getFlow_trace_id();
				req.setOrder_id(order_id);
				req.setFlow_id(flow_id);
				req.setFlow_trace_id(flowTraceId);
				req.setComments(dealDesc);
				req.setHandler_type(Const.ORDER_HANDLER_TYPE_RETURNED);
				req.setType_code(EcsOrderConsts.REFUND_STATUS_03);
				req.setOp_id(user.getUserid());
				req.setOp_name(user.getUsername());
				this.ordFlowManager.insertOrderHandLog(req);
			}
			this.json="{status:"+resp.getError_code()+",msg:'"+resp.getError_msg()+"'}";
		}else{
			this.json="{status:1,msg:'此订单已被其它用户锁定'}";
		}
		return this.JSON_MESSAGE;
	}
	
	/**
	 * 线下退款
	 * @作者 MoChunrun
	 * @创建日期 2014-11-4 
	 * @return
	 */
	public String refund(){
		if(ecsOrdManager.isOrderHasAchive(order_id)){
			this.json="{status:1,msg:'订单已归档,不能返销!'}";
			return JSON_MESSAGE;
		}
		AdminUser user = ManagerUtils.getAdminUser();
		OrderTreeBusiRequest orderTree= CommonDataFactory.getInstance().getOrderTree(order_id);
		OrderExtBusiRequest orderExtBusiRequest = orderTree.getOrderExtBusiRequest();
		if(!EcsOrderConsts.REFUND_STATUS_03.equals(orderExtBusiRequest.getRefund_status())){   
			if(EcsOrderConsts.REFUND_STATUS_08.equals(orderExtBusiRequest.getRefund_status())){
				this.json="{status:1,msg:'请先确认退单。'}";
			}else if(EcsOrderConsts.REFUND_STATUS_01.equals(orderExtBusiRequest.getRefund_status())){
				this.json="{status:1,msg:'请先做BSS返销。'}";
			}else if(EcsOrderConsts.REFUND_STATUS_02.equals(orderExtBusiRequest.getRefund_status())){
				this.json="{status:1,msg:'请先做ESS返销。'}";
			}else{
				this.json="{status:1,msg:'此单已退款。'}";
			}
    		return JSON_MESSAGE;
    	}
//		if(user.getUserid().equals(orderExtBusiRequest.getLock_user_id())){
//			/* -------------- ZX add 2016-02-01 start -------------- */
//			String order_from = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ORDER_FROM);
//			if (order_from.equals(EcsOrderConsts.ORDER_FROM_10003)) {
//				if (EcsOrderConsts.REFUND_STATUS_04.equals(orderExtBusiRequest.getRefund_status())) {
//					json="{status:1,msg:'此单已经退款。'}";
//					return JSON_MESSAGE;
//				}
//				if(!EcsOrderConsts.REFUND_STATUS_01.equals(orderExtBusiRequest.getRefund_status())
//						&& !EcsOrderConsts.REFUND_STATUS_02.equals(orderExtBusiRequest.getRefund_status())
//						&& !EcsOrderConsts.REFUND_STATUS_03.equals(orderExtBusiRequest.getRefund_status())){
//					json="{status:1,msg:'请先确认退单。'}";
//					return JSON_MESSAGE;
//				}
//				ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
//				BusiCompRequest busiCompRequest = new BusiCompRequest();
//				busiCompRequest.setEnginePath("zteCommonTraceRule.beforeBssRefund");
//				busiCompRequest.setOrder_id(order_id);
//				Map queryParams = new HashMap();
//				queryParams.put(EcsOrderConsts.REFUND_FLAG_KEY, EcsOrderConsts.REFUND_FLAG_OFRETURN);
//				busiCompRequest.setQueryParams(queryParams);
//				BusiCompResponse busiCompResponse = client.execute(busiCompRequest, BusiCompResponse.class);
//				this.json="{status:"+busiCompResponse.getError_code()+",msg:'"+busiCompResponse.getError_msg()+"'}";
//				return JSON_MESSAGE;
//			}
//			/* -------------- ZX add 2016-02-01 end ---------------- */
//			TacheFact fact = new TacheFact();
//			fact.setOrder_id(orderTree.getOrder_id());
//			RuleTreeExeResp resp = RuleFlowUtil.executeRuleTree(EcsOrderConsts.ORDER_RETURNED_PLAN, EcsOrderConsts.ORDER_RETURN_REFUND_RULE, fact,true,true,EcsOrderConsts.DEAL_FROM_PAGE);
//			if(resp!=null && "0".equals(resp.getError_code())){
//				//写日志
//				OrderHandleLogsReq req = new OrderHandleLogsReq();
//				String flow_id = orderTree.getOrderExtBusiRequest().getFlow_id();
//				String flowTraceId = orderTree.getOrderExtBusiRequest().getFlow_trace_id();
//				req.setOrder_id(order_id);
//				req.setFlow_id(flow_id);
//				req.setFlow_trace_id(flowTraceId);
//				req.setComments(dealDesc);
//				req.setHandler_type(Const.ORDER_HANDLER_TYPE_RETURNED);
//				req.setType_code(EcsOrderConsts.REFUND_STATUS_04);
//				req.setOp_id(user.getUserid());
//				req.setOp_name(user.getUsername());
//				this.ordFlowManager.insertOrderHandLog(req);
//			}
//			this.json="{status:"+resp.getError_code()+",msg:'"+resp.getError_msg()+"'}";
//		}else{
//			this.json="{status:1,msg:'此订单已被其它用户锁定'}";
//		}
		
		TacheFact fact = new TacheFact();
		fact.setOrder_id(orderTree.getOrder_id());
		
		String is_aop = orderTree.getOrderExtBusiRequest().getIs_aop();
		String shippingType = orderTree.getOrderBusiRequest().getShipping_type();
		String refundMode = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.REFUND_AUDIT_MODE);
		String plan_id = "";
		String rule_id = "";
		
		//执行线下退款方案,执行之前，清除上一次的记录
		plan_id = EcsOrderConsts.REFUND_OFFLINE;
		PlanRuleTreeExeResp resp = RuleFlowUtil.executePlanRuleTree(plan_id, -1, fact, true, EcsOrderConsts.DEAL_FROM_PAGE, null, null);
				
		//调一下退单方案，继续往下走
		Map m = AttrUtils.getInstance().getRefundPlanInfo(order_id);
		plan_id = m.get("plan_id").toString();
		rule_id = m.get("confirm_rule_id").toString();	
		
		RuleTreeExeResp resp2 = RuleFlowUtil.executeRuleTree(plan_id, rule_id, fact,true,true,EcsOrderConsts.DEAL_FROM_PAGE);
		this.json="{status:"+resp2.getError_code()+",msg:'"+resp2.getError_msg()+"'}";
		return JSON_MESSAGE;
	}
	
	/**
	 * 从淘宝同步订单状态，将需要退单的进行退单处理
	 * @return wujiayong
	 */
	public String returnedApplyFromTaobao(){
		try {
			OrderTreeBusiRequest tree = CommonDataFactory.getInstance().getOrderTree(order_id);
			String order_from = tree.getOrderExtBusiRequest().getOrder_from();
			if("10012".equals(order_from)){
				TbTianjiOrderInfoGetReq req = new TbTianjiOrderInfoGetReq();
				req.setNotNeedReqStrOrderId(order_id);
				
				ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
				TbTianjiOrderInfoGetResp resp = client.execute(req, TbTianjiOrderInfoGetResp.class);
				if("0000".equals(resp.getError_code())){
					DistributionOrderInfo order = resp.getOrderInfo();
					if(order!=null){
						String tbStatus = order.getOrderStatus();//得到淘宝侧订单状态
						if("FAILURE".equals(tbStatus) || "CANCEL".equals(tbStatus)){
							//订购失败，订单取消执行退单退款
							OrderHandleLogsReq logReq = new OrderHandleLogsReq();
							logReq.setComments("淘宝退单");
							logReq.setHandler_type(Const.ORDER_HANDLER_TYPE_RETURNED);
							logReq.setType_code(EcsOrderConsts.REFUND_STATUS_08);
							logReq.setOp_id("1");
							logReq.setOp_name("系统管理员");
							boolean flag = ordFlowManager.executeOrderReRule(order_id, logReq);
							if(flag){
								json = "{result:0,message:'退单处理成功'}";
							}else{
								json = "{result:1,message:'退单处理失败'}";
							}
							
						}else{
							json = "{result:1,message:'正常订单'}";
						}
					}
					
				}
				else{
					json = "{result:1,message:'获取淘宝订单信息失败'}";
				}
			}
			else{
				json = "{result:1,message:'该订单不是天猫分销订单'}";
			}
		} catch (Exception e) {
			json = "{result:1,message:'获取淘宝订单信息失败'}";
		}
		return this.JSON_MESSAGE;
	}

	/**
	 * ZX add 2016-02-01
	 * 线上退款
	 * @return
	 */
	public String upRefund() {
		OrderTreeBusiRequest orderTree= CommonDataFactory.getInstance().getOrderTree(order_id);
		OrderExtBusiRequest orderExtBusiRequest = orderTree.getOrderExtBusiRequest();
		if (EcsOrderConsts.REFUND_STATUS_04.equals(orderExtBusiRequest.getRefund_status())) {
			json="{status:1,msg:'此单已经退款。'}";
			return JSON_MESSAGE;
		}
		if(!EcsOrderConsts.REFUND_STATUS_01.equals(orderExtBusiRequest.getRefund_status())
				&& !EcsOrderConsts.REFUND_STATUS_02.equals(orderExtBusiRequest.getRefund_status())
				&& !EcsOrderConsts.REFUND_STATUS_03.equals(orderExtBusiRequest.getRefund_status())){
			json="{status:1,msg:'请先确认退单。'}";
			return JSON_MESSAGE;
		}

		TacheFact fact = new TacheFact();
		fact.setOrder_id(orderTree.getOrder_id());
		
		
//		String is_aop = orderTree.getOrderExtBusiRequest().getIs_aop();
//		String shippingType = orderTree.getOrderBusiRequest().getShipping_type();
//		String refundMode = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.REFUND_AUDIT_MODE);
		String plan_id = "";
		String rule_id = "";
		
		//执行线上退款方案,执行之前，清除上一次的记录
		plan_id = EcsOrderConsts.REFUND_ONLINE;
		PlanRuleTreeExeResp resp = RuleFlowUtil.executePlanRuleTree(plan_id, -1, fact, true, EcsOrderConsts.DEAL_FROM_PAGE, "", "");
			
		String order_from = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ORDER_FROM);
		if(StringUtils.equals(order_from, EcsOrderConsts.ORDER_FROM_10003)){
			//调一下退单方案，继续往下走			
			Map m = AttrUtils.getInstance().getRefundPlanInfo(order_id);
			plan_id = m.get("plan_id").toString();
			rule_id = m.get("confirm_rule_id").toString();	
			
			RuleTreeExeResp resp2 = RuleFlowUtil.executeRuleTree(plan_id, rule_id, fact,true,true,EcsOrderConsts.DEAL_FROM_PAGE);
			this.json="{status:"+resp2.getError_code()+",msg:'"+resp2.getError_msg()+"'}";			
		}else{
			//本地商城的线上退款通知是一个单向通知，商城会异步通知我们的退款结果，因此，不需要调退款方案。
			this.json="{status:"+resp.getError_code()+",msg:'退款请求已发送到本地商城！请稍候刷新查看退款结果'}";	
		}
		return JSON_MESSAGE;
	}
	
	
	public String laterActiveOrderCancel(){
		OrderRealNameInfoBusiRequest orderRealNameBusi = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderRealNameInfoBusiRequest();
		if(EcsOrderConsts.NO_DEFAULT_VALUE.equals(orderRealNameBusi.getCancel_type())
				|| StringUtils.isEmpty(orderRealNameBusi.getCancel_type())){
			//线上调撤单接口
			ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
			OrderCancelReq cancelReq = new OrderCancelReq();
			cancelReq.setNotNeedReqStrOrderId(order_id);
			OrderCancelResp rsp = client.execute(cancelReq, OrderCancelResp.class);
			if(!StringUtils.equals(rsp.getError_code(), EcsOrderConsts.AOP_HTTP_STATUS_CODE_200+"")){
				//撤单失败
				this.json="{status:"+rsp.getCode()+",msg:'"+rsp.getDetail()+"'}";
				return JSON_MESSAGE;
			}
			orderRealNameBusi.setCancel_type(EcsOrderConsts.NO_DEFAULT_VALUE);//线上
			orderRealNameBusi.setCancel_flag(EcsOrderConsts.LATER_CANCEL_STATUS_3);//线上撤单成功
		}else{
			orderRealNameBusi.setCancel_type(EcsOrderConsts.IS_DEFAULT_VALUE);//线下
			orderRealNameBusi.setCancel_flag(EcsOrderConsts.LATER_CANCEL_STATUS_2);//线下撤单成功
		}
		//修改状态
		orderRealNameBusi.setIs_dirty(ConstsCore.IS_DIRTY_YES);
		orderRealNameBusi.setDb_action(ConstsCore.DB_ACTION_UPDATE);
		orderRealNameBusi.store();
		json = "{status:0,msg:'撤单处理成功'}";
		
		return JSON_MESSAGE;
	}
	/**
	 * 退单处理页面，批量或单个订单退单
	 * @return
	 */
	public String batchOrOneReturned(){

		String[] arr_orders = orders.split(",");
		for(String orderId : arr_orders){
			if(ecsOrdManager.isOrderHasAchive(orderId)){
				this.json="{status:1,msg:'单号为："+orderId+"的订单已归档,不能确认退单!'}";
				return JSON_MESSAGE;
			}
			
			OrderTreeBusiRequest orderTree= CommonDataFactory.getInstance().getOrderTree(orderId);
			OrderExtBusiRequest orderExtBusiRequest = orderTree.getOrderExtBusiRequest();
			if(!ZjEcsOrderConsts.REFUND_STATUS_08.equals(orderExtBusiRequest.getRefund_status())&&!ZjEcsOrderConsts.REFUND_STATUS_01.equals(orderExtBusiRequest.getRefund_status())){
	    		this.json="{status:1,msg:'单号为："+orderId+"的订单非退单申请状态或已驳回退单。'}";
	    		return JSON_MESSAGE;
	    	}

			String platType = orderExtBusiRequest.getPlat_type();
			if(StringUtils.equals(platType, ZjEcsOrderConsts.PLAT_TYPE_10061)) {
	    		this.json="{status:1,msg:'单号为："+orderId+"的订单,华盛订单不允许操作退单！'}";
	    		return JSON_MESSAGE;
			}
			if(StringUtils.isEmpty(is_refund)&&(this.ifNeedIsRefundBtn(orderId))) {
	    		this.json="{status:1,msg:'请选择是否退款'}";
	    		return JSON_MESSAGE;
			}
			
			CommonDataFactory.getInstance().updateAttrFieldValue(orderId, 
	    			new String[]{AttrConsts.IS_REFUND}, 
	    			new String[]{is_refund});//更新是否退款标记
			
			//添加退单人员工号和退单时间到es_order_extvtl表中,便于订单报表导出
			AdminUser user = ManagerUtils.getAdminUser();
			Calendar date = new GregorianCalendar();
			String refund_time=DF.format(date.getTime());
			String user_id = user.getUsername();
			if(!StringUtil.isEmpty(user_id)) {
					CommonDataFactory.getInstance().updateAttrFieldValue(orderId, new String[] { "refund_userid" }, new String[] { user_id });
				}
			String update_county_extvtl_sql = "update es_order_extvtl set refund_time = to_date('"+refund_time+"','yyyy-MM-dd HH24:mi:ss') where order_id='" + orderId + "'";
			IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
			baseDaoSupport.execute(update_county_extvtl_sql);
			
			//记录退单原因    	
	    	if(!StringUtils.isEmpty(getDealDesc())){
	        	String olddesc = CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.REFUND_DESC);
	        	if(StringUtils.isEmpty(olddesc))dealDesc = ZjEcsOrderConsts.REFUND_CONFIRM_DESC+""+dealDesc;
	        	else dealDesc = olddesc+"|"+ZjEcsOrderConsts.REFUND_CONFIRM_DESC+""+dealDesc;
	        	CommonDataFactory.getInstance().updateAttrFieldValue(orderId, 
	        			new String[]{AttrConsts.REFUND_DESC}, 
	        			new String[]{dealDesc});
	    	}else{
	    		dealDesc = ZjEcsOrderConsts.REFUND_CONFIRM_DESC+"默认";
	    	}
			
			/*退单申请和确认都不需要判断是否被锁定，都可以退单（shaoxiong需求）*/
				TacheFact fact = new TacheFact();
				fact.setOrder_id(orderTree.getOrder_id());
				
				Map m = AttrUtils.getInstance().getRefundPlanInfo(orderId);
				String plan_id = m.get("plan_id").toString();
				String rule_id = m.get("confirm_rule_id").toString();		
			
				RuleTreeExeResp resp = RuleFlowUtil.executeRuleTree(plan_id, rule_id, fact,true,true,ZjEcsOrderConsts.DEAL_FROM_PAGE);
				//如果需要退款，调退款方案发起退款流程
				if(EcsOrderConsts.IS_DEFAULT_VALUE.equals(is_refund) && this.ifNeedIsRefundBtn(orderId)) {
					TacheFact refundFact = new TacheFact();
					refundFact.setOrder_id(orderId);
					//执行爬虫退款方案,执行之前，清除上一次的记录
					plan_id = CommonDataFactory.getInstance().getOtherDictVodeValue(StypeConsts.ORDER_REFUND_PLAN, "CRAWLER_REFUND");
					RuleFlowUtil.executePlanRuleTree(plan_id, 0, refundFact, true, ZjEcsOrderConsts.DEAL_FROM_PAGE, "", "退款申请");
				}
				this.json="{status:"+resp.getError_code()+",msg:'"+resp.getError_msg()+"'}";
				//本地商城退单之后，如果停留在带退款规则，则不弹出提示，为了方便设置，借用了字典转换来实现配置
				String refundAlert = CommonDataFactory.getInstance().getOtherDictVodeValue("refund_error_alert", orderExtBusiRequest.getOrder_from());
				if(StringUtils.equals(refundAlert, ZjEcsOrderConsts.NO_DEFAULT_VALUE)){
					if(resp.getError_msg().contains("待退款")){
						this.json="{status:0,msg:'退单已确认,等待商城退款结果通知'}";
					}
				}
		}
		
		
		return this.JSON_MESSAGE;
	}
	/**
	 * 批量退单校验
	 * @param orderId
	 * @return
	 * @throws Exception
	 */
	private String batchBackCheck(String orderId){

		String btnSource = applyFrom;
		
		if("1".equals(btnSource)){//如果applyFrom=2不需要做锁定和环节权限校验
			
			String lockMsg = ecsOrdManager.checkLockUser(orderId, null);
			return lockMsg;
		}
		
		if(StringUtils.isEmpty(getDealDesc())){        	
    		return "请填写订单备注！";
    	}if(StringUtils.isEmpty(returnedReasonCode)){        	
    		return "请填选择退单原因！";
    	}
		
		orderTree= CommonDataFactory.getInstance().getOrderTree(orderId);
		OrderExtBusiRequest orderExtBusiRequest = orderTree.getOrderExtBusiRequest();
    	if(ZjEcsOrderConsts.REFUND_DEAL_TYPE_02.equals(orderExtBusiRequest.getRefund_deal_type())){        	
    		return "单号为："+orderId+"的订单,已申请退单，不能重复申请退单。";
    	}
    	
    	if(orderTree==null || StringUtil.isEmpty(orderTree.getOrder_id())){
    		return "单号为："+orderId+"的订单,没找到此订单。";
    	}

		String platType = orderExtBusiRequest.getPlat_type();
		if(StringUtils.equals(platType, ZjEcsOrderConsts.PLAT_TYPE_10061)) {
    		return "单号为："+orderId+"的订单，华盛订单不允许操作退单！";
		}
		
		return "";
	}
	/**
	 * 非自定义退单
	 * @param orderId
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	private String doNormalOrderCancel(String orderId) throws Exception{
		// TODO 非自定义退单
		AdminUser user = ManagerUtils.getAdminUser();//获得账号信息
		
		//添加退单人员工号和退单时间到es_order_extvtl表中,便于订单报表导出
		Calendar date = new GregorianCalendar();
		String refund_time=DF.format(date.getTime());
		String user_id = user.getUsername();
		if(!StringUtil.isEmpty(user_id)) {
			CommonDataFactory.getInstance().updateAttrFieldValue(orderId, new String[] { "refund_userid" }, new String[] { user_id });
		}
		
		String update_county_extvtl_sql = "update es_order_extvtl set refund_time = to_date('"+refund_time+"','yyyy-MM-dd HH24:mi:ss') where order_id='" + orderId + "'";
		IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
		baseDaoSupport.execute(update_county_extvtl_sql);
		
    	//记录退单原因
    	CommonDataFactory.getInstance().updateAttrFieldValue(orderId, 
    			new String[]{AttrConsts.REFUND_DESC,AttrConsts.RETURNED_REASON_CODE}, 
    			new String[]{ZjEcsOrderConsts.REFUND_APPLY_DESC+""+dealDesc,returnedReasonCode});
		
    	//标记为非商城主动发起的退单
		CommonDataFactory.getInstance().updateAttrFieldValue(orderId, 
				new String[]{AttrConsts.ZB_REFUND_STATUS}, 
				new String[]{ZjEcsOrderConsts.NO_DEFAULT_VALUE});
    	/*退单申请和确认都不需要判断是否被锁定，都可以退单（shaoxiong需求）*/
		//if(user.getUserid().equals(orderExtBusiRequest.getLock_user_id())){
			//PlanRuleTreeExeResp resp = RuleFlowUtil.executePlanRuleTree(HnEcsOrderConsts.ORDER_RETURNED_PLAN, fact);
			
		//获取审核模式，并保存
		String refundMode = AttrUtils.getInstance().getRefundModeByRule(orderId);
		CommonDataFactory.getInstance().updateAttrFieldValue(orderId, 
				new String[]{AttrConsts.REFUND_AUDIT_MODE}, new String[]{refundMode});
		
		//获取方案信息
		Map m = AttrUtils.getInstance().getRefundPlanInfo(orderId);
		String plan_mode = m.get("plan_id").toString();
		String rule_mode = m.get("app_rule_id").toString();	
		
		//清除之前的日志
		CommonDataFactory.getInstance().delRuleExeLog(plan_mode, null, orderId);
		
		//修改订单状态为退单中的状态
		OrderBusiRequest orderBusiReq = orderTree.getOrderBusiRequest();
		orderBusiReq.setStatus(ZjEcsOrderConsts.DIC_ORDER_STATUS_12);
		orderBusiReq.setIs_dirty(ConstsCore.IS_DIRTY_YES);
		orderBusiReq.setDb_action(ConstsCore.DB_ACTION_UPDATE);
		orderBusiReq.store();
		ecsOrdManager.unLock(orderId);
		TacheFact fact = new TacheFact();
		fact.setOrder_id(orderTree.getOrder_id());			
		RuleTreeExeResp resp = RuleFlowUtil.executeRuleTree(plan_mode, rule_mode, fact,true,true,ZjEcsOrderConsts.DEAL_FROM_PAGE);
		if(resp!=null && "0".equals(resp.getError_code())){
			//写日志
			OrderHandleLogsReq req = new OrderHandleLogsReq();
			String flow_id = orderTree.getOrderExtBusiRequest().getFlow_id();
			String flowTraceId = orderTree.getOrderExtBusiRequest().getFlow_trace_id();
			req.setOrder_id(orderId);
			req.setFlow_id(flow_id);
			req.setFlow_trace_id(flowTraceId);
			req.setComments(ZjEcsOrderConsts.REFUND_APPLY_DESC+""+dealDesc);
			req.setHandler_type(Const.ORDER_HANDLER_TYPE_RETURNED);
			req.setType_code(ZjEcsOrderConsts.REFUND_STATUS_08);
			req.setOp_id(user.getUserid());
			req.setOp_name(user.getUsername());
			this.ordFlowManager.insertOrderHandLog(req);
		}else{
			String msg = resp==null?"":resp.getError_msg();
			throw new Exception(msg);
		}
					
		return "";
	}
	
	private String doOrderCancel(String orderId) throws Exception{
		orderTree = CommonDataFactory.getInstance().getOrderTree(orderId);
		String is_work_custom=orderTree.getOrderExtBusiRequest().getIs_work_custom();
		if(StringUtil.equals("1", is_work_custom)){
			// TODO 自定义流程
			this.cancelOrderCheck();
		}else{
			// 非自定义流程
			this.doNormalOrderCancel(orderId);
		}
		
		return "";
	}
	/**
	 * 订单申请页面，批量或单个订单申请退单
	 * @return
	 */
	public String batchBackApply(){
		String[] arr_orders = orders.split(",");
		for(String orderId : arr_orders){
			this.order_id=orderId;
			String error = this.batchBackCheck(orderId);
			
			
			if(org.apache.commons.lang.StringUtils.isNotBlank(error)){
				this.json = "{status:1,msg:'"+orderId+"订单退单校验失败："+error+"'}";
				return JSON_MESSAGE;
			}
			
			// 退单
			try{
				error = this.doOrderCancel(orderId);
			}catch(Exception e){
				error = orderId+"订单退单失败："+e.getMessage();
			}
			
			if(org.apache.commons.lang.StringUtils.isNotBlank(error)){
				this.json="{status:1,msg:'"+error+"'}";
				return JSON_MESSAGE;
			}
	    	
			
		}
		this.json="{status:0,msg:'退单成功'}";
		return this.JSON_MESSAGE;
	}
	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getDealDesc() {
		if(StringUtils.isEmpty(dealDesc) 
				&& !StringUtils.equals("undefined", dealDescIE8)
				&& !StringUtils.isEmpty(dealDescIE8)){
			try {
				dealDescIE8 = java.net.URLDecoder.decode(dealDescIE8, "utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}  
			dealDesc = dealDescIE8;
		}
		return dealDesc;
	}

	public void setDealDesc(String dealDesc) {
		this.dealDesc = dealDesc;
	}

	public OrderTreeBusiRequest getOrderTree() {
		return orderTree;
	}

	public void setOrderTree(OrderTreeBusiRequest orderTree) {
		this.orderTree = orderTree;
	}


	public String getDealDescIE8() {
		return dealDescIE8;
	}


	public void setDealDescIE8(String dealDescIE8) {
		this.dealDescIE8 = dealDescIE8;
	}


	public String getApplyFrom() {
		return applyFrom;
	}


	public void setApplyFrom(String applyFrom) {
		this.applyFrom = applyFrom;
	}


	public String getReturnedReasonCode() {
		return returnedReasonCode;
	}


	public void setReturnedReasonCode(String returnedReasonCode) {
		this.returnedReasonCode = returnedReasonCode;
	}


	public String getIs_refund() {
		return is_refund;
	}


	public void setIs_refund(String is_refund) {
		this.is_refund = is_refund;
	}
	public String getOrders() {
		return orders;
	}


	public void setOrders(String orders) {
		this.orders = orders;
	}
}
