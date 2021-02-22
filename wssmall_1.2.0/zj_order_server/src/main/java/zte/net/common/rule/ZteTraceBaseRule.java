package zte.net.common.rule;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.ztesoft.api.ApiBusiException;
import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.Task;
import com.ztesoft.api.TaskThreadPool;
import com.ztesoft.api.ThreadPoolFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.common.util.CrmConstants;
import com.ztesoft.common.util.DateFormatUtils;
import com.ztesoft.common.util.date.DateUtil; 
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.ibss.common.util.Const;
import com.ztesoft.net.auto.rule.fact.AutoFact;
import com.ztesoft.net.cache.common.INetCache;
import com.ztesoft.net.consts.OrderStatus;
import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.eop.sdk.context.MqEnvGroupConfigSetting;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.IDaoSupport;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.service.IDcPublicInfoManager;
import com.ztesoft.net.mall.core.service.impl.RequestStoreManager;
import com.ztesoft.net.mall.core.service.impl.cache.DcPublicInfoCacheProxy;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.service.IOrderStatisticManager;
import com.ztesoft.net.util.ZjCommonUtils;
import com.ztesoft.util.CacheUtils;

import commons.CommonTools;
import consts.ConstsCore;
import params.ZteRequest;
import params.ZteResponse;
import params.orderqueue.req.AsynExecuteMsgWriteMqReq;
import params.req.OrderExpMarkProcessedReq;
import params.resp.OrderExpMarkProcessedResp;
import rule.AbstractRuleHander;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.base.req.BusiCompRequest;
import zte.net.ecsord.params.base.resp.BusiCompResponse;
import zte.net.ecsord.params.busi.req.OrderBusiRequest;
import zte.net.ecsord.params.busi.req.OrderDeliveryBusiRequest;
import zte.net.ecsord.params.busi.req.OrderExtBusiRequest;
import zte.net.ecsord.params.busi.req.OrderLockBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;
import zte.net.ecsord.rule.tache.TacheFact;
import zte.net.iservice.IOrderQueueBaseManager;
import zte.net.iservice.IOrderServices;
import zte.net.iservice.IRuleService;
import zte.net.iservice.IUosService;
import zte.net.params.req.PlanRuleTreeExeReq;
import zte.net.params.req.RuleTreeExeReq;
import zte.net.params.resp.PlanRuleTreeExeResp;
import zte.net.params.resp.RuleTreeExeResp;
import zte.params.ecsord.req.InsertOrderHandLogReq;
import zte.params.order.req.HandlerOrderRoleReq;
import zte.params.order.req.WorkFlowCallBackReq;
import zte.params.process.req.UosDealReq;
import zte.params.process.req.UosStartReq;
import zte.params.process.resp.UosDealResp;
import zte.params.process.resp.UosStartResp;
import zte.params.process.resp.WorkItem;

//import org.apache.log4j.Logger;

/**
 * 环节处理规则
 * 
 * @author wu.i
 */
public abstract class ZteTraceBaseRule extends AbstractRuleHander implements IZteTraceBaseRule {
	
	/**
	 * 订单状态常量数据
	 */
	public static final String DIC_ORDER_STATUS = "DIC_ORDER_STATUS";
	/**
	 * 工作流用户配置
	 */
	public static final String work_flow_staff_no = EcsOrderConsts.work_flow_staff_no;
	
	//public static final String MATCH_FLOW_PLAN = "MATCH_WORK_FLOW_PLAN";
	
	@Resource
	protected IDcPublicInfoManager dcPublicInfoManager;
	@Resource
	protected IUosService uosService;
	@Resource
	protected IRuleService ruleService;
	@Resource
	protected IOrderServices orderServices;
	@Resource
	protected IOrderStatisticManager orderStatisticManager;
	private static Logger logger = Logger.getLogger(ZteTraceBaseRule.class);
	public void init(){
		dcPublicInfoManager = SpringContextHolder.getBean("dcPublicInfoManager");
		uosService = SpringContextHolder.getBean("uosService");
		ruleService = SpringContextHolder.getBean("ruleService");
		orderServices = SpringContextHolder.getBean("orderServices");
	}
	
//	// 执行环节
//	public BusiCompResponse engineDoPerform(BusiCompRequest busiCompRequest) {
//		Map<String,String> queryParams = busiCompRequest.getQueryParams();
//		String order_id =queryParams.get("order_id");
//		if(StringUtil.isEmpty(order_id))
//			CommonTools.addFailError("订单编号不能为空");
//		//首次进入获取订单树
//		CommonDataFactory.getInstance().getOrderTree(order_id);
//		return this.engineDo(busiCompRequest);
//
//	}
	
	/**
	 * 流程匹配公用方法
	 * @作者 MoChunrun
	 * @创建日期 2014-10-18 
	 * @param busiCompRequest
	 * @return
	 */
	/*public void matchWorkFlow(String order_id){
		List<Map> list = this.getConsts(MATCH_FLOW_PLAN);
		if(list!=null && list.size()>0){
			Map map = list.get(0);
			String plan_id = (String) map.get("pkey");
			if(StringUtil.isEmpty(plan_id))CommonTools.addError("1", "没有配置流程匹配静态数据");
			this.exePlan(plan_id, null, order_id, null);
		}else{
			CommonTools.addError("1", "没有配置流程匹配静态数据");
		}
	}*/
	
	public void putNextTraceId(BusiCompRequest busiCompRequest,String nextTraceId){
		busiCompRequest.getQueryParams().put("next_trace_id", nextTraceId);
	}
	
	private void orderNextBusi(String order_id){
		OrderTreeBusiRequest orderTree= CommonDataFactory.getInstance().getOrderTree(order_id);
		String flow_trace = orderTree.getOrderExtBusiRequest().getFlow_trace_id();
		if(ConstsCore.TRACE_H.equals(flow_trace)){
			//发货
			List<OrderDeliveryBusiRequest> list = orderTree.getOrderDeliveryBusiRequests();
			if(list!=null && list.size()>0){
				for(OrderDeliveryBusiRequest o:list){
					if(OrderStatus.DELIVERY_TYPE_0.equals(o.getDelivery_type())){
						 o.setShip_status(OrderStatus.DELIVERY_SHIP_SATUS_1);
						 o.setIs_dirty(ConstsCore.IS_DIRTY_YES);
						 o.setDb_action(ConstsCore.DB_ACTION_UPDATE);
						 o.store();
					}
				}
			}
		}
	}
	
	static INetCache cache;
	public static int NAMESPACE = 308;
	public static String CURR_ORDER_CLICK_PAGE = "CURR_ORDER_CLICK_PAGE_";
	static{
		cache = com.ztesoft.net.cache.common.CacheFactory.getCacheByType("");
	}
	
	/**
	 * 流程跳转下一步修改订单流程状态
	 * @作者 MoChunrun
	 * @创建日期 2014-10-10 
	 * @param busiCompRequest
	 * @throws ApiBusiException 
	 */
	public BusiCompResponse nextflow(BusiCompRequest busiCompRequest,boolean isEndFlow,String flowTraceId){
		
		String order_id = getOrderId(busiCompRequest);
		if(StringUtil.isEmpty(order_id))order_id = busiCompRequest.getOrder_id();
		Map map = busiCompRequest.getQueryParams();
		String next_trace_id = null;//(String) map.get("next_trace_id");
		String deal_from = null;//page 界面来源 inf接口来源
		String deal_type = null;
		String deal_desc = null;
		if(map!=null){
			deal_from = (String) map.get("deal_from");
			deal_type = (map.get("deal_type")==null||"".equals(map.get("deal_type")))?Const.ORDER_HANDLER_TYPE_DEFAULT:map.get("deal_type").toString();
			deal_desc = map.get("deal_desc")==null?null:map.get("deal_desc").toString();
		}
		
		String flowStatus = Const.FLOW_DEAL_TYPE_AM;
		if(isEndFlow){
			flowStatus = Const.FLOW_DEAL_TYPE_END;
		}
		
		//add by wui问题定位追踪
		try{
			String page_trace_id = (String) cache.get(NAMESPACE, CURR_ORDER_CLICK_PAGE+order_id); //获取进入页面时的环节id
			if(EcsOrderConsts.DEAL_FROM_PAGE.equals(deal_from) &&
				!StringUtil.isEmpty(page_trace_id) && EcsOrderConsts.DIC_ORDER_NODE_F.equals(page_trace_id)
				&& !flowTraceId.equals(page_trace_id)){
				CommonTools.addError("1", "nextflow:订单"+order_id+"正在处理页面为质检页面，实际处理流程为"+flowTraceId+"与实际不一致，请确认是否重复执行");
			}
		}catch(Exception e){
			
		}
				
		if(StringUtil.isEmpty(order_id))CommonTools.addError("1", "order_id不能为空");
		OrderTreeBusiRequest orderTree= CommonDataFactory.getInstance().getOrderTree(order_id);
		String processInstanceId =  orderTree.getOrderExtBusiRequest().getFlow_inst_id();
		final String trace_id = orderTree.getOrderExtBusiRequest().getFlow_trace_id();
		if(CacheUtils.getIsCheckTrace()&&!flowTraceId.equals(trace_id)){
			CommonTools.addError("1", "不能执行当前环节以外的下一步规则");
		}
		
		OrderExtBusiRequest orderExtBusiRequest = orderTree.getOrderExtBusiRequest();
		orderExtBusiRequest.setOrder_id(order_id);
		orderExtBusiRequest.setFlow_status(Const.ORDER_FLOW_STATUS_1);
		orderExtBusiRequest.setLast_deal_time("sysdate");
		orderExtBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
		orderExtBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
		//修改订单流程状态
		orderExtBusiRequest.store();
		BusiCompResponse resp = new BusiCompResponse();
		resp.setError_code("0");
		resp.setError_msg("成功");
		//记录环节日志
		String flow_id = orderTree.getOrderExtBusiRequest().getFlow_id();
		String flow_trace_id = orderTree.getOrderExtBusiRequest().getFlow_trace_id();
		InsertOrderHandLogReq logReq = new InsertOrderHandLogReq();
		logReq.setOrder_id(order_id);
		
		List<OrderLockBusiRequest> orderLockRequestList = orderTree.getOrderLockBusiRequests();
		String lock_user_id = "";
		String lock_user_name = "";
		for (OrderLockBusiRequest orderLockRequest : orderLockRequestList) {
			if(trace_id.equals(orderLockRequest.getTache_code()) ) {
				lock_user_id = orderLockRequest.getLock_user_id();
				lock_user_name = orderLockRequest.getLock_user_name();
				break;
			}
		}
		logReq.setOp_id(lock_user_id);
		logReq.setOp_name(lock_user_name);
		logReq.setFlow_id(flow_id);
		logReq.setFlow_trace_id(flow_trace_id);
		logReq.setHandler_type(Const.ORDER_HANDLER_TYPE_DIC_HANDLE_TYPE);
		logReq.setType_code(deal_type);
		logReq.setComments(deal_desc);
		logReq.setCreate_time("sysdate");
		logReq.setSource_from(ManagerUtils.getSourceFrom());
		CommonDataFactory.getInstance().insertOrderHandlerLogs(logReq);
		
		String userId = lock_user_id;//orderTree.getOrderExtBusiRequest().getLock_user_id();
		
		try{
			AdminUser user = new AdminUser();
			user = ManagerUtils.getAdminUser();
			if(StringUtils.isEmpty(userId)&&null != user){
				userId = user.getUserid();
			}				
		}catch(Exception e){
			
		}		
		if(StringUtil.isEmpty(userId)){
			userId = "1";//系统管理员;
		}
		orderStatisticManager.updateOrderTacheItems(order_id, flow_trace_id, userId);		
		
		if(!StringUtil.isEmpty(next_trace_id)){
			//测试
			if(!isEndFlow){
				orderExtBusiRequest.setFlow_status(Const.ORDER_FLOW_STATUS_0);
				orderExtBusiRequest.setFlow_trace_id(next_trace_id);
				orderExtBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
				orderExtBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
				//修改订单流程状态
				orderExtBusiRequest.store();
				final ZteTraceBaseRule trace = this;
				final String _order_id = order_id;
				final String _deal_from = deal_from;
				new Thread(new Runnable() {
					@Override
					public void run() {
						trace.exePlan(null,null, null, _order_id, null,0,_deal_from,false,null);
					}
				}).start();
			}
			return resp;
		}
		UosDealReq req = new UosDealReq();
		List<Map> workNo = this.getConsts(work_flow_staff_no);
		Map workNoMap = workNo.get(0);
		int staffId = Integer.parseInt(workNoMap.get("pkey").toString());
		String staffName = (String) workNoMap.get("pname");
		
		req.setStaffId(staffId);
		req.setStaffName("");
		req.setProcessInstanceId(processInstanceId);
		req.setTacheCode(trace_id);
		req.setDealType(flowStatus);//环节处理状态
		UosDealResp flowresp = uosService.dealProcess(req);//工作流返回下一个环节的信息
		boolean end = flowresp.isEnd();
		boolean isTerminal = flowresp.isTerminal();
		String tar_tache_code = null;
		
		orderLockRequestList = orderTree.getOrderLockBusiRequests();
		String lock_user_id1 = "";
		String lock_user_name2 = "";
		for (OrderLockBusiRequest orderLockRequest : orderLockRequestList) {
			if( orderExtBusiRequest.getFlow_trace_id().equals(orderLockRequest.getTache_code()) ) {
				lock_user_id1 = orderLockRequest.getLock_user_id();
				lock_user_name2 = orderLockRequest.getLock_user_name();
				break;
			}
		}
		
		
		if(!end && !isTerminal){
			ArrayList<WorkItem> workItems = flowresp.getWorkItems();
			//修改环节为下一个环节
			//修改订单处理状态为未处理
			if(workItems!=null && workItems.size()>0){
				//目前只有一个不节，所以只拿第一个就行了
				WorkItem wi = workItems.get(0);
				tar_tache_code = wi.getTacheCode();
				orderExtBusiRequest.setFlow_status(Const.ORDER_FLOW_STATUS_0);
				orderExtBusiRequest.setFlow_trace_id(wi.getTacheCode());
				//orderExtBusiRequest.setFlow_trace_inst_id(wi.getProcessInstanceId());
				//orderExtBusiRequest.setFlow_trace_name(wi.getProcessInstanceName());
				orderExtBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
				orderExtBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
				//修改订单流程状态
				orderExtBusiRequest.store();
				
				/*
				 * 订单生产完成，进去‘订单归档’环节时，将订单处理状态翻转为4（已受理）
				 */
				String goodsId = orderTree.getOrderItemBusiRequests().get(0).getGoods_id();
				if(StringUtils.equals(wi.getTacheCode(), EcsOrderConsts.DIC_ORDER_NODE_L) && 
						(StringUtils.equals(orderExtBusiRequest.getPlat_type(), "AO") || StringUtils.equals(orderExtBusiRequest.getOrder_from(), "10078")) &&
						 !StringUtils.equals(goodsId, "180101547042001934")) {//商品类型不为宽带极简受理
					OrderTreeBusiRequest order_tree = CommonDataFactory.getInstance().getOrderTree(order_id);
					OrderBusiRequest orderBusiReq = order_tree.getOrderBusiRequest();
					orderBusiReq.setOrder_state("4");
					String setSql = "order_state='4'";
					updateOrderTree(setSql, "es_order", order_id, order_tree);
					/*
					 * 在预约单处理状态同步总商消息表增加一条记录，状态为2（已派单），处理时间为当前时间。
					 * ES_KD_ORDER_STATUS_SYN
					 */
					IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
					List results = baseDaoSupport.queryForList("select order_id,syn_status from ES_KD_ORDER_STATUS_SYN where order_id = '"+order_id+"'");
					//状态同步表没有记录则新增
					if(null == results || results.size() == 0) {
						baseDaoSupport.execute("insert into ES_KD_ORDER_STATUS_SYN(id,bespeakid,dealstate,order_id,"
								+ "source_from,dealtime,syn_num,syn_status) values('"+baseDaoSupport.getSequences("seq_kd_status_syn")+"',"
								+"'"+orderTree.getOrderExtBusiRequest().getOut_tid()+"',"+"'2',"+"'"+order_id+"',"+"'"+ManagerUtils.getSourceFrom()+"','"+DateFormatUtils.formatDate(CrmConstants.DATE_TIME_FORMAT)+"',"
								+0+","+"'WCL')");
					} else {
						Map resultMap = (Map) results.get(0);
						if(MapUtils.getString(resultMap, "syn_status").equals("WCL") || 
								MapUtils.getString(resultMap, "syn_status").equals("CLSB")) {
							//更新syn_status->WCL，SYN_NUM->0,DEALTIME->now
							baseDaoSupport.execute("update ES_KD_ORDER_STATUS_SYN set syn_status='WCL',SYN_NUM=0,DEALTIME='"+DateFormatUtils.formatDate(CrmConstants.DATE_TIME_FORMAT)+"'"
									+" where order_id='"+order_id+"'");
						}
					}
				}
			}
			
			orderLockRequestList = orderTree.getOrderLockBusiRequests();
			lock_user_id = "";
			lock_user_name = "";
			for (OrderLockBusiRequest orderLockRequest : orderLockRequestList) {
				if(tar_tache_code.equals(orderLockRequest.getTache_code()) ) {
					lock_user_id = orderLockRequest.getLock_user_id();
					lock_user_name = orderLockRequest.getLock_user_name();
					break;
				}
			}
			
			/*
			 * Add By Wcl
			 * 特定订单下一步不解锁
			 */
			ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
			String unlockOrderFrom = cacheUtil.getConfigInfo("UNLOCK_ORDER_FROM");
//			orderExtBusiRequest
			String[] unlockOrderFroms = unlockOrderFrom.split(",");
			for(int i = 0;i<unlockOrderFroms.length;i++) {
				if(StringUtils.equals(orderExtBusiRequest.getOrder_from(), unlockOrderFroms[i])) {
					//资料归档J，订单归档L，不需再次上锁
//					if(!StringUtils.equals(EcsOrderConsts.DIC_ORDER_NODE_J, orderNode) && !StringUtils.equals(EcsOrderConsts.DIC_ORDER_NODE_L, orderNode)) {
						IDaoSupport support = SpringContextHolder.getBean("baseDaoSupport");
						List results = support.queryForList("select order_id from es_order_lock where order_id ='"+order_id+"' and tache_code='"+tar_tache_code+"'");
						String dbAction = ConstsCore.DB_ACTION_UPDATE;
						if(results.size() == 0 || results == null) {
							dbAction = ConstsCore.DB_ACTION_INSERT;
						}
						OrderLockBusiRequest orderLockRequestL = new OrderLockBusiRequest();
						orderLockRequestL.setLock_user_id(lock_user_id1);
						orderLockRequestL.setOrder_id(order_id);
						orderLockRequestL.setLock_user_name(lock_user_name2);
						orderLockRequestL.setLock_id(support.getSequences("o_outcall_log"));
						orderLockRequestL.setTache_code(orderExtBusiRequest.getFlow_trace_id());
						orderLockRequestL.setLock_status(EcsOrderConsts.LOCK_STATUS);
						orderLockRequestL.setSource_from(ManagerUtils.getSourceFrom());
						orderLockRequestL.setLock_time(DateUtil.getDateTime(new Date()));
						
						orderLockRequestL.setDb_action(dbAction);
						orderLockRequestL.setIs_dirty(ConstsCore.IS_DIRTY_YES);
						orderLockRequestL.store();
						break;
//					}
				}
			}
			
			
			userId = lock_user_id;//orderTree.getOrderExtBusiRequest().getLock_user_id();
//			
			try{
				AdminUser user = new AdminUser();
				user = ManagerUtils.getAdminUser();
				if(StringUtils.isEmpty(userId)&&null != user){
					userId = user.getUserid();
				}				
			}catch(Exception e){
				
			}		
			if(StringUtil.isEmpty(userId)){
				userId = "1";//系统管理员;
			}
			//记录订单环节操作时间、操作人 add by duan.shaochu
			orderStatisticManager.updateOrderTacheItems(order_id, flow_trace_id, tar_tache_code, userId);
			//恢复异常后 异常系统标记订单已处理
			this.markExpProcced(order_id,userId,orderExtBusiRequest.getAbnormal_type(),orderExtBusiRequest.getException_type(),orderExtBusiRequest.getException_desc());
			//设置订单可见性
			try{
				this.setOrderVisable(order_id);
			}catch(Exception ex){
				ex.printStackTrace();
				CommonTools.addError("1", ex.getMessage());
			}
			
			//处理订单查看权限
			try{
				//handlerOrderRoleData(order_id);
			}catch(Exception ex){
				
			}
			logger.info("----订单："+order_id+"当前环节："+flowTraceId+"工作流反馈环节："+tar_tache_code+"当前时间："+DateUtil.currentDateTime());
			//环节跳转后通知老系统
			/*RuleFlowUtil.delRuleExeLogs(EcsOrderConsts.ORDER_STATUS_SYN_OLD_SYS_PLAN, EcsOrderConsts.ORDER_STATUS_SYN_OLD_SYS_RULE, order_id);
			PlanRuleTreeExeReq plan = new PlanRuleTreeExeReq();
			TacheFact fact = new TacheFact();
			fact.setOrder_id(order_id);
			fact.setSrc_tache_code(trace_id);
			fact.setTar_tache_code(tar_tache_code);
			fact.setProcess_type(EcsOrderConsts.PROCESS_TYPE_1);
			plan.setFact(fact);
			plan.setPlan_id(EcsOrderConsts.ORDER_STATUS_SYN_OLD_SYS_PLAN);
			CommonDataFactory.getInstance().exePlanRuleTree(plan);*/
			
			//如果流和还没有结束侧执执行下一个环节
			final String _deal_from = deal_from;
			if(EcsOrderConsts.DEAL_FROM_PAGE.equals(_deal_from)){
				this.exePlan(null,null, null, order_id, null,0,_deal_from,false,null);;
			}else{
				final ZteTraceBaseRule trace = this;
				final String _order_id = order_id;
				new Thread(new Runnable() {
					@Override
					public void run() {
						trace.exePlan(null,null, null, _order_id, null,0,_deal_from,false,null);
					}
				}).start();
			}
			
		}
		return resp;
	}
	
	//调用异常单系统标记已处理
    public void markExpProcced(String order_id,String user_id,String abnormal_type,String exception_type,String exception_desc){
//    	String abnormal_type = orderTree.getOrderExtBusiRequest().getAbnormal_type();
		if (!StringUtil.isEmpty(abnormal_type)&& !abnormal_type.equals(EcsOrderConsts.ORDER_ABNORMAL_TYPE_0)) {
			// 恢复订单异常
//			OrderExtBusiRequest orderExtBusiReq = orderTree.getOrderExtBusiRequest();
			BusiCompRequest busi = new BusiCompRequest();
			Map queryParams = new HashMap();
			queryParams.put("order_id", order_id);
			queryParams.put(EcsOrderConsts.EXCEPTION_FROM,EcsOrderConsts.EXCEPTION_FROM_ORD);
			queryParams.put(EcsOrderConsts.EXCEPTION_TYPE,exception_type);
			queryParams.put(EcsOrderConsts.EXCEPTION_REMARK,exception_desc);
			busi.setEnginePath("zteCommonTraceRule.restorationException");
			busi.setOrder_id(order_id);
			busi.setQueryParams(queryParams);
			try {
				ZteResponse response = orderServices.execBusiComp(busi);
				if (!EcsOrderConsts.RULE_EXE_FLAG_SUCC.equals(response.getError_code())) {
					CommonTools.addBusiError(response.getError_code(),response.getError_msg());
				}
			} catch (ApiBusiException busiE) {
				CommonTools.addError("1", busiE.getMessage());
			} catch (Exception e) {
				CommonTools.addError("1", e.getMessage());
			}
		}
			try{
				ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
				String is_exception_run = cacheUtil.getConfigInfo(EcsOrderConsts.IS_EXCEPTION_RUN);//是否启用异常系统
				if(!StringUtil.isEmpty(is_exception_run) && new Integer(is_exception_run).intValue() != 0 ){//异常系统启动标识
					OrderExpMarkProcessedReq req = new OrderExpMarkProcessedReq();
					req.setRel_obj_id(order_id);
					req.setRel_obj_type("order");
					req.setDeal_result("恢复异常业务处理");
					req.setDeal_staff_no(user_id);
					req.setRequest_time(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
					String exe_type = MqEnvGroupConfigSetting.ORD_EXP_EXEC;//调用方式 m/d  m是走mq d是走dubbo
					if(ConstsCore.DECOUPLING_EXEC_D.equals(exe_type)){
						markExpProccedByDubbo(req);
					}else{
						markExpProccedByMq(req);
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
    
    private void markExpProccedByDubbo(OrderExpMarkProcessedReq req){
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		OrderExpMarkProcessedResp response = client.execute(req, OrderExpMarkProcessedResp.class);
	}
	
	private ZteResponse markExpProccedByMq(OrderExpMarkProcessedReq req){
		AsynExecuteMsgWriteMqReq mqReq = new AsynExecuteMsgWriteMqReq();
		mqReq.setService_code(req.getApiMethodName());
		mqReq.setVersion(ConstsCore.DUBBO_DEFAULT_VERSION);
		mqReq.setZteRequest((ZteRequest)req);
		mqReq.setConsume_env_code(com.ztesoft.common.util.BeanUtils.getHostEnvCodeByEnvStatus(ConstsCore.MACHINE_EVN_CODE_ECSORD_EXP));
		IOrderQueueBaseManager  orderQueueBaseManager = SpringContextHolder.getBean("orderQueueBaseManager");
	    return orderQueueBaseManager.asynExecuteMsgWriteMq(mqReq);
	}
		
	/**
	 * 工作流回调
	 * @作者 MoChunrun
	 * @创建日期 2014-10-10 
	 * @param busiCompRequest
	 */
	public BusiCompResponse flowCallBack(BusiCompRequest busiCompRequest){
		Map params = busiCompRequest.getQueryParams();
		String order_id = getOrderId(busiCompRequest);
		if(StringUtil.isEmpty(order_id))order_id = busiCompRequest.getOrder_id();
		String flow_id = (String)params.get("flow_id");//工作流ID
		String flow_trace_id = (String)params.get("next_trace_id");//工作流通知下一个环节的ID
		WorkFlowCallBackReq req = (WorkFlowCallBackReq) params.get("flow_req");
		boolean isEnd = req.isEndFlow();
		if(!isEnd && StringUtil.isEmpty(flow_trace_id)){
			CommonTools.addError("1", "下个不节工作流不能为空");
		}
		if(StringUtil.isEmpty(order_id))CommonTools.addError("1", "order_id不能为空");
		OrderTreeBusiRequest orderTree= CommonDataFactory.getInstance().getOrderTree(order_id);
		OrderExtBusiRequest orderExtBusiRequest = orderTree.getOrderExtBusiRequest();
		orderExtBusiRequest.setOrder_id(order_id);
		if(!StringUtil.isEmpty(flow_id))orderExtBusiRequest.setFlow_id(flow_id);
		if(!isEnd){
			orderExtBusiRequest.setFlow_trace_id(flow_trace_id);
			orderExtBusiRequest.setFlow_status(Const.ORDER_FLOW_STATUS_0);
		}
		orderExtBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
		orderExtBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
		//修改订单流程状态
		orderExtBusiRequest.store();
		BusiCompResponse resp = new BusiCompResponse();
		resp.setError_code("0");
		resp.setError_msg("成功");
		
		if(!isEnd){
			//如果处理成功侧进入下一个环节
		}
		return resp;
	}
	
	/**
	 * 获取常量数据
	 * @作者 MoChunrun
	 * @创建日期 2014-10-10 
	 * @param key
	 * @return
	 */
	public List<Map> getConsts(String key){
		DcPublicInfoCacheProxy dcPublicCache = new DcPublicInfoCacheProxy(dcPublicInfoManager);
        List<Map> list = dcPublicCache.getList(key);
        return list;
	}
	
	public String getOrderId(BusiCompRequest busiCompRequest) {
		Map queryParams = busiCompRequest.getQueryParams();
		String order_id ="";
		if(queryParams != null){
			order_id =(String)queryParams.get("order_id");
		}else{
			order_id = busiCompRequest.getOrder_id();
		}

		return order_id;
	}
	
	/**
	 * 获取流程fact
	 * @作者 MoChunrun
	 * @创建日期 2014-10-14 
	 * @param orderTree
	 * @return
	 */
	public AutoFact getOrderFlowFact(String order_id){
		TacheFact fact = new TacheFact();
		//String flow_trace_id = orderTree.getOrderExtBusiRequest().getFlow_trace_id();
		//TODO 如果有多个fact需要按flow_trace_id判断
		fact.setOrder_id(order_id);
		return fact;
	}
	
	public BusiCompResponse exePlan(OrderTreeBusiRequest orderTree ,String plan_id,String rule_id,String order_id,AutoFact fact,int is_auto,String deal_from,boolean deLogs,Map<String, Object> params){
		BusiCompResponse resp = new BusiCompResponse();
		if(StringUtil.isEmpty(order_id))CommonTools.addError("1", "order_id不能为空");
		if(orderTree==null){
			orderTree=CommonDataFactory.getInstance().getOrderTree(order_id);
		}
		OrderExtBusiRequest orderExt = orderTree.getOrderExtBusiRequest();
		if(fact==null)fact = this.getOrderFlowFact(order_id);
		String trace_id = orderExt.getFlow_trace_id();
		fact.setFlow_id(trace_id);
		
		String deal_type = (params==null || params.get("deal_type")==null)?null:params.get("deal_type").toString();
		String deal_desc = (params==null || params.get("deal_desc")==null)?null:params.get("deal_desc").toString();
		//pageLoad属性验证方法校验
		//List<AttrInstLoadResp> attrInstLoadResps = AttrBusiInstTools.validateOrderAttrInstsForPage(order_id,ConstsCore.ATTR_ACTION_lOAD);
		//if(attrInstLoadResps.size()>0)CommonTools.addError("1", "调用load方法，返回错误校验信息");
		//attrInstLoadResps =AttrBusiInstTools.validateOrderAttrInstsForPage(order_id, ConstsCore.ATTR_ACTION_UPDATE);
		//if(attrInstLoadResps.size()>0)CommonTools.addError("1", "调用update方法，返回错误校验信息");
		//数据校验
		
		String flow_trace_id = orderExt.getFlow_trace_id();
		if(StringUtil.isEmpty(plan_id)){
			plan_id = this.getPlanId(flow_trace_id);
		}
		
		
		
		
		if(StringUtil.isEmpty(rule_id)){
			//执行方案所有规则
			PlanRuleTreeExeReq req = new PlanRuleTreeExeReq();
			req.setDeal_from(deal_from);
			req.setDeal_type(deal_type);
			req.setDeal_desc(deal_desc);
			req.setDeleteLogs(deLogs);
			if(EcsOrderConsts.DIC_ORDER_NODE_B.equals(flow_trace_id)){
				req.setDeleteLogs(true);
			}
			req.setPlan_id(plan_id);
			req.setFact(fact);
			req.setAuto_exe(is_auto);
			PlanRuleTreeExeResp rresp = ruleService.exePlanRuleTree(req);
			resp.setError_code(rresp.getError_code());
			resp.setError_msg(rresp.getError_msg());
			resp.setFact(rresp.getFact());
			resp.setResponse(rresp);
		}else{
			//执行规则
			RuleTreeExeReq req = new RuleTreeExeReq();
			req.setDeal_from(deal_from);
			req.setDeal_type(deal_type);
			req.setDeal_desc(deal_desc);
			req.setPlan_id(plan_id);
			req.setRule_id(rule_id);
			req.setFact(fact);
			boolean checkCurr = false;
			boolean checkAll = false;
			
			req.setCheckAllRelyOnRule(checkAll);
			req.setCheckCurrRelyOnRule(checkCurr);
			RuleTreeExeResp rresp = ruleService.exeRuleTree(req);
			resp.setError_code(rresp.getError_code());
			resp.setError_msg(rresp.getError_msg());
			resp.setFact(rresp.getFact());
			resp.setResponse(rresp);
		}
		
		
		
		resp.setFact(fact);
		return resp;
	}
	
	/**
	 * 启动订单工作流
	 * @作者 MoChunrun
	 * @创建日期 2014-10-18 
	 * @param busiCompRequest
	 * @return
	 */
	public BusiCompResponse startOrderWorkFlow(BusiCompRequest busiCompRequest,OrderTreeBusiRequest orderTree){
		//测试订单ID 201410166559860470
		 logger.info("启动工作流==================================================>");
		 long start = System.currentTimeMillis();
		 
		Map map = busiCompRequest.getQueryParams();
		if(map==null)CommonTools.addError("1", "参数不能为空");
		String deal_from = (String) map.get("deal_from");//page 界面来源 inf接口来源
		String order_id = (String)map.get("order_id");
		if(StringUtil.isEmpty(order_id))order_id = busiCompRequest.getOrder_id();
		if(StringUtil.isEmpty(order_id))CommonTools.addError("1", "order_id不能为空");
//		OrderTreeBusiRequest orderTree= CommonDataFactory.getInstance().getOrderTree(order_id);
		//orderTree.getOrderExtBusiRequest().setFlow_trace_id("B");
		String next_tace_code = null;//orderTree.getOrderExtBusiRequest().getFlow_trace_id();
		BusiCompResponse bresp = new BusiCompResponse();
		bresp.setError_code("0");
		bresp.setError_msg("成功");
		UosStartReq req = new UosStartReq();
//		List<Map> workNo = this.getConsts(work_flow_staff_no);
//		Map workNoMap = workNo.get(0);
//		int staffId = Integer.parseInt(workNoMap.get("pkey").toString());
//		String staffName = (String) workNoMap.get("pname");
		req.setStaffId(100);//工作流用户名
		req.setStaffName("");
		//插入订单统计环节时间记录
		String current_tache_code = orderTree.getOrderExtBusiRequest().getFlow_trace_id();
//		if(StringUtils.isEmpty(next_tace_code)){
		String create_time = orderTree.getOrderBusiRequest().getCreate_time();
		orderStatisticManager.insertOrderStatisticTacheItem(order_id,create_time);
//		}
		//TODO 走规则去配配流程  流程配置的时后要把工作流ID写到订单扩展表上
		req.setProcessId(orderTree.getOrderExtBusiRequest().getFlow_id());
//		req.setProcessId("3299");
		logger.info("=========测试测试===================="+req.getProcessId());
		UosStartResp resp = uosService.startProcess(req);
		ArrayList<WorkItem> list = resp.getWorkItems();
		if(list!=null && list.size()>0){
			//现在工作流都是单线跑的，所以只取第一个就行了
			WorkItem i = list.get(0);
			next_tace_code = i.getTacheCode();
			String processInstanceId = i.getProcessInstanceId();//流程实例ID
			OrderExtBusiRequest orderExtBusiRequest = orderTree.getOrderExtBusiRequest();
			orderExtBusiRequest.setOrder_id(order_id);
			orderExtBusiRequest.setFlow_status(Const.ORDER_FLOW_STATUS_0);
			orderExtBusiRequest.setFlow_trace_id(next_tace_code);//修改下一环节环节编码
			orderExtBusiRequest.setFlow_inst_id(processInstanceId);//修改工作流实例ID
//			orderExtBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
//			orderExtBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
//			orderExtBusiRequest.store();
			
			String sql ="update es_order_ext set flow_status=? ,Flow_trace_id=?,Flow_inst_id=? where order_id=?";
			if(this.baseDaoSupport==null){
				this.baseDaoSupport=SpringContextHolder.getBean("baseDaoSupport");
			}
			this.baseDaoSupport.execute(sql, Const.ORDER_FLOW_STATUS_0,next_tace_code,processInstanceId,order_id);
			//更新订单树缓存
			cache.set(RequestStoreManager.NAMESPACE, RequestStoreManager.DC_TREE_CACHE_NAME_KEY+"order_id"+ order_id,orderTree, RequestStoreManager.time);
			
			List<OrderLockBusiRequest> orderLockRequestList = orderTree.getOrderLockBusiRequests();
			String trace_id = orderExtBusiRequest.getFlow_trace_id();
			String lock_user_id = "";
			for (OrderLockBusiRequest orderLockRequest : orderLockRequestList) {
				if(trace_id.equals(orderLockRequest.getTache_code()) ) {
					lock_user_id = orderLockRequest.getLock_user_id();
					break;
				}
			}
		
			String userId = lock_user_id;//orderTree.getOrderExtBusiRequest().getLock_user_id();			
			try{
				AdminUser user = new AdminUser();
				user = ManagerUtils.getAdminUser();
				if(StringUtils.isEmpty(userId)&&null != user){
					userId = user.getUserid();
				}
			}catch(Exception e){
				
			}
			if(StringUtil.isEmpty(userId)){
				userId = "1";//系统管理员;
			}
			
			//记录订单环节操作时间、操作人 add by duan.shaochu
			orderStatisticManager.updateOrderTacheItems(order_id, current_tache_code, next_tace_code, userId);
			//恢复异常后 异常系统标记订单已处理
			this.markExpProcced(order_id,userId,orderExtBusiRequest.getAbnormal_type(),orderExtBusiRequest.getException_type(),orderExtBusiRequest.getException_desc());
			
			long end = System.currentTimeMillis();
			//计算订单权限
			/*try{
				handlerOrderRoleData(order_id);
			}catch(Exception ex){
				
			}*/
			boolean exe_plan = true;//启动完工作流后是否执行方案 默认为执行
			Map queryParams = busiCompRequest.getQueryParams();
			if(queryParams!=null){
				Object obj = queryParams.get("exe_plan");//启动完工作流后是否执行方案
				if(obj!=null)
					exe_plan = (Boolean) obj;
			}
			if(exe_plan){
				//启动成功后执行环节方案
				final ZteTraceBaseRule trace = this;
//				final String _order_id = order_id;
//				final String _deal_from = deal_from;
//				new Thread(new Runnable() {
//					@Override
//					public void run() {
						trace.exePlan(orderTree,null, null, order_id, null,0,deal_from,false,null);
//					}
//				}).start();
			}
			logger.info("====启动工作流业务组件=======>"+(end-start));
		}
		return bresp;
	}
	
	/**
	 * 通用获取订单树
	 * @param busiCompRequest
	 * @return
	 */
	public OrderTreeBusiRequest getOrderTree(BusiCompRequest busiCompRequest) {
		return CommonDataFactory.getInstance().getOrderTree(getOrderId(busiCompRequest));
	}
	
	public String getPlanId(String flowid){
		//====mochunrun=====20150115 核心功能======================
		//如果是主单归集侧返回订单归集方案ID
		if(EcsOrderConsts.ORDER_COLLECT_PLAN.equals(flowid))return EcsOrderConsts.ORDER_COLLECT_PLAN;
		//如果没有启动工作流则执行订单归集方案
		if(StringUtil.isEmpty(flowid))return EcsOrderConsts.ORDER_COLLECT_PLAN;
		//====mochunrun=====20150115 核心功能======================
		//获取环节对应的方案ID
		String _plan_id = CommonDataFactory.getInstance().getPlanIdOfTache(flowid);
        return _plan_id;
	}
	
	public void setOrderVisable(String order_id)throws ApiBusiException{
		ZjCommonUtils.setOrderVisable(order_id);
	}
	public static void handlerOrderRoleData(String order_id){
		HandlerOrderRoleReq zteRequest = new HandlerOrderRoleReq() ;
		zteRequest.setOrder_id(order_id);
		TaskThreadPool taskThreadPool = new TaskThreadPool(new Task(zteRequest) {
			@Override
			public ZteResponse execute(ZteRequest zteRequest) {
				HandlerOrderRoleReq hreq = (HandlerOrderRoleReq) zteRequest;
				long start = System.currentTimeMillis();
				IOrderServices os = SpringContextHolder.getBean("orderServices");
				os.handlerOrderRole(hreq);
				long end = System.currentTimeMillis();
				logger.info("订单权限处理使用时间------>"+(end-start));
				return new ZteResponse();
			}
		});
		ThreadPoolFactory.getRoleDataThreadPoolExector().execute(taskThreadPool);
	}
	
	/**
	 * 部分数据值，需要模式确认以后才能确定
	 * @param order_id
	 */
	public void setAttrAfterModel(String order_id){
		//是否WMS交互
		String is_send_wms = EcsOrderConsts.NO_DEFAULT_VALUE;
		String has_entity_prod = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.IS_PHISICS);
		String order_model = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ORDER_MODEL);
		if(EcsOrderConsts.IS_DEFAULT_VALUE.equals(has_entity_prod) && EcsOrderConsts.OPER_MODE_ZD.equals(order_model)){
			is_send_wms = EcsOrderConsts.IS_DEFAULT_VALUE;
		}
		
		//更新属性值
		CommonDataFactory.getInstance().updateAttrFieldValue(order_id, 
				new String[]{AttrConsts.IS_SEND_WMS}, 
				new String[]{is_send_wms});
	}
	

	public void updateOrderTree(String set_sql,String table_name,String order_id,OrderTreeBusiRequest orderTree){
		String sql ="update "+table_name+" set "+set_sql+" where order_id=?";
		if(this.baseDaoSupport==null){
			this.baseDaoSupport=SpringContextHolder.getBean("baseDaoSupport");
		}
		this.baseDaoSupport.execute(sql, order_id);
		//更新订单树缓存
		cache.set(RequestStoreManager.NAMESPACE, RequestStoreManager.DC_TREE_CACHE_NAME_KEY+"order_id"+ order_id,orderTree, RequestStoreManager.time);
		
	}
	public IUosService getUosService() {
		return uosService;
	}

	public void setUosService(IUosService uosService) {
		this.uosService = uosService;
	}
	
}
