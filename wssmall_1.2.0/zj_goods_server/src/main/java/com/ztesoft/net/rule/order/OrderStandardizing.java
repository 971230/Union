package com.ztesoft.net.rule.order;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import rule.impl.CoQueueBaseRule;
import rule.params.coqueue.req.CoQueueRuleReq;
import rule.params.coqueue.resp.CoQueueRuleResp;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.base.req.BusiCompRequest;
import zte.net.ecsord.params.base.resp.BusiCompResponse;
import zte.net.ecsord.params.order.req.StartOrderPlanReq;
import zte.net.ecsord.params.order.resp.StartOrderPlanResp;
import zte.net.ecsord.rule.tache.TacheFact;
import zte.net.iservice.IOrderStandardizing;
import zte.net.params.req.PlanRuleTreeExeReq;
import zte.net.params.resp.PlanRuleTreeExeResp;
import zte.params.order.req.OrderCollect;
import zte.params.order.resp.OrderAddResp;
import zte.params.req.CheckReq;
import zte.params.resp.CheckResp;

import com.alibaba.rocketmq.common.ThreadFactoryImpl;
import com.ztesoft.api.ApiBusiException;
import com.ztesoft.common.util.BeanUtils;
import com.ztesoft.common.util.StringUtils;
import com.ztesoft.net.consts.EccConsts;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.model.CoQueue;
import com.ztesoft.net.mall.core.model.Order;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import commons.CommonTools;

import consts.ConstsCore;

public class OrderStandardizing extends CoQueueBaseRule implements IOrderStandardizing {

	private OrderStandarService orderStandarService;
			
	public static final String ORDERSTANDARDIZE_CODE = "ORDERSTANDARDIZE";
	@Resource
	private ICacheUtil cacheUtil;
	private static ExecutorService threadSingleExecutor =Executors.newSingleThreadExecutor(new ThreadFactoryImpl("ThreadPushTestMemExector"));
	private static ExecutorService threadPlanExeExecutor =null;
	private static boolean initFixThreadCount =false;
	public OrderAddResp syncOrderStandardizing(OrderCollect oc,String userSessionId,String service_code) throws ApiBusiException {
		//订单归集
		OrderAddResp resp = orderStandarService.syncOrderStandardizing(oc, userSessionId,service_code);
		String order_id =resp.getOrderList().get(0).getOrder_id();
		String source_from =resp.getOrderList().get(0).getSource_from();
		
		String ship_name = resp.getOrderList().get(0).getShip_name();
		String remark = resp.getOrderList().get(0).getRemark();
		String order_channel = oc.getOrderOuterList().get(0).getOrder_channel();
		String zb_inf_id = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ZB_INF_ID);
		//订单关联环境信息<<begin
		HashMap p = new HashMap();
		p.put("source_from", source_from);
		p.put("order_id", order_id);
		p.put("op_code", "syncOrderStandardizing");
		p.put("out_tid", oc.getOuter().getOut_tid());
		p.put("zb_inf_id", zb_inf_id);
		logger.info("OrderStandardizing:订单标准化写入主机环境信息：外部单号"+oc.getOuter().getOut_tid()+",订单信息"+order_id+",订单渠道:"+order_channel+","+"收货人："+ship_name+",客户留言:"+remark+"总部单号："+zb_inf_id);
		String env_code="";
		if(Consts.TAOBAO_CODE_ID.equals(order_channel)){//淘宝单需要单独处理测试单和灰度单，淘宝单只在1.0中存在
			if(Consts.ZTE_CESHI.equals(ship_name)||Consts.ZTE_CESHI.equals(remark)){
				 env_code =  Consts.ZTE_CESH_ENV_TYPE_SERVER;
			}else if(Consts.ZTE_GRAY.equals(ship_name)||Consts.ZTE_GRAY.equals(remark)){
				String sql ="select env_code from es_abgray_hostenv where env_type='"+Consts.ZTE_ENV_TYPE_SERVER+"'  and env_status='00X' and rownum=1";
				env_code = baseDaoSupport.queryForString(sql);
			}
		}
		if(StringUtil.isEmpty(env_code)){
			env_code = BeanUtils.getCurrHostEnv().get("env_code").toString();
		}
		p.put("env_code", env_code);
		BeanUtils.ordBindEvn(p);
		BeanUtils.ordBindEvnLog(p);
		//订单关联环境信息<<end
		
		logger.info("服务编码"+service_code+"订单归集--订单编号"+order_id+"======================");
		if(Consts.SERVICE_CODE_CO_GUIJI_NEW.equals(service_code)){
			String rule_thread_count = cacheUtil.getConfigInfo(EcsOrderConsts.RULE_THREAD_COUNT);
			if(StringUtil.isEmpty(rule_thread_count) || new Integer(rule_thread_count).intValue() ==0) //add by wui
			{
				startOrderStandingPlan(service_code, resp);
			}else{
				
				final BusiCompRequest request = new BusiCompRequest();
				Map param = new HashMap();
				param.put("service_code", service_code);
				param.put("orderAddResp", resp);
				request.setQueryParams(param);
				
				String shipping_type = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.SENDING_TYPE);
				if("XJ".equals(shipping_type)){ //现场交付订单
					String PLAN_FIEXE_COUNT = cacheUtil.getConfigInfo("PLAN_FIEXE_COUNT");
					if(!initFixThreadCount && new Integer(PLAN_FIEXE_COUNT).intValue() !=0)
					{
						PLAN_FIEXE_COUNT = cacheUtil.getConfigInfo("PLAN_FIEXE_COUNT");
						threadPlanExeExecutor = new ThreadPoolExecutor(new Integer(PLAN_FIEXE_COUNT).intValue(),new Integer(PLAN_FIEXE_COUNT).intValue(),1000, TimeUnit.MILLISECONDS,new ArrayBlockingQueue<Runnable>(new Integer(PLAN_FIEXE_COUNT).intValue()),new ThreadPoolExecutor.CallerRunsPolicy());
						initFixThreadCount = true;
					}
//					//配置数据大于0,则执行,否则不执行
					if(new Integer(PLAN_FIEXE_COUNT).intValue()>0){
						threadPlanExeExecutor.execute(new Runnable() {
				             @Override
				             public void run() {
				            	Map param = request.getQueryParams();
								String service_code = (String)param.get("service_code");
								OrderAddResp resp = (OrderAddResp)param.get("orderAddResp");
								startOrderStandingPlan(service_code, resp);
				             }
				         });
					}
					
				}else{
					CoQueue queBak = new CoQueue();
					queBak.setService_code("CO_STANDING_NEW");			//service_code改为老系统
					queBak.setCo_id("");
					queBak.setCo_name("新订单标准化");
					queBak.setObject_id(order_id);
					queBak.setObject_type("CO_GUIJI_NEW");
					queBak.setStatus(Consts.CO_QUEUE_STATUS_XYSB);
					this.baseDaoSupport.insert("es_co_queue", queBak);
				}
			}
		}
		return resp;
	}
	
	@Override
	public StartOrderPlanResp syncOrderStandardizing(StartOrderPlanReq req){
		StartOrderPlanResp resp = new StartOrderPlanResp();
		List<String> orderIdList = req.getOrderIdList();
		String order_id =orderIdList.get(0);
		String service_code = req.getService_code();
		logger.info("服务编码"+service_code+"订单归集--订单编号"+order_id+"======================");
		if(Consts.SERVICE_CODE_CO_GUIJI_NEW.equals(service_code)){
			startOrderStandingPlan(service_code, orderIdList);
		}
		resp.setError_code(ConstsCore.ERROR_SUCC);
		resp.setError_msg("订单归集方案调用成功");
		return resp;
	}
	/**
	 * 启动订单归集方案
	 * @param service_code
	 * @param resp
	 */
	@Override
	public void startOrderStandingPlan(String service_code, OrderAddResp resp) {
		for (Order order:resp.getOrderList()) {
			   //添加订单推送消息队列================================
	    		//更新缓存
	    		String order_id = order.getOrder_id();
	    		ordStardPre(order_id);
//	    		CheckResp res = ordStardPre(order_id);
//	    		if(!ConstsCore.ERROR_SUCC.equals(res.getError_code())){
//	    			continue;
//	    		}
	    		//去除强制更新，手动设置
	    		//启动订单归集方案
	    		BusiCompRequest busi = new BusiCompRequest();
	    		busi.setOrder_id(order.getOrder_id());
	    		Map queryParams = new HashMap();
	    		queryParams.put("order_id", order.getOrder_id());
	    		queryParams.put("plan_id", EcsOrderConsts.ORDER_COLLECT_PLAN);
	    		busi.setEnginePath("enterTraceRule.exec");
	    		busi.setQueryParams(queryParams);
	    		
	    		long start = System.currentTimeMillis();
	    		BusiCompResponse busiComp = CommonDataFactory.getInstance().execBusiComp(busi);
	    		long end = System.currentTimeMillis();
	    		logger.info("服务编码："+service_code+"订单编号:"+resp.getOrderList().get(0).getOrder_id()+"订单归集方案执行完成时间：=====>"+(end-start));
				
				//add by wui追加值更新
				String order_model = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ORDER_MODEL);
				String is_send_wms = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.IS_SEND_WMS);
				String flow_inst_id = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest().getFlow_inst_id();
				String zb_status = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ZB_STATUS);
				String flow_id = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.FLOW_ID);
				String flow_trace_id = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest().getFlow_trace_id();
				
				String sys_code = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.SYS_CODE);
				String syn_ord_zb = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.SYN_ORD_ZB);
				String is_aop = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.IS_AOP);
				this.baseDaoSupport.execute("update es_order_ext a set a.order_model = ?,a.is_send_wms = ?,flow_inst_id = ? , zb_status = ? ,flow_id = ?, flow_trace_id= ?, is_aop = ?  where order_id = ? ", order_model,is_send_wms,flow_inst_id,zb_status,flow_id,flow_trace_id,is_aop,order_id);
				this.baseDaoSupport.execute("update es_order_extvtl a set a.sys_code = ?,a.syn_ord_zb = ? where order_id = ? ", sys_code,syn_ord_zb,order_id);
				logger.info("订单编号:"+order.getOrder_id()+"order_model:"+order_model+",is_send_wms:"+is_send_wms+",flow_inst_id:"+flow_inst_id+",:"+zb_status+"flow_id:"+flow_id+",flow_trace_id:"+flow_trace_id+",sys_code:"+sys_code+",:syn_ord_zb"+syn_ord_zb);
				
			//标准化通知
    		if(ConstsCore.ERROR_SUCC.equals(busiComp.getError_code())){
				// 调用沃云购2.0预校验方案，成功通知新商城通过，否则不处理
				PlanRuleTreeExeReq plan = new PlanRuleTreeExeReq();
				if (EcsOrderConsts.ACCOUNT_OPEN_CHANNEL_AOP.equals(is_aop)) {
					plan.setPlan_id(EcsOrderConsts.ORDER_PRE_VALIDATE_AOP);
				}else if(EcsOrderConsts.ACCOUNT_OPEN_CHANNEL_BSS.equals(is_aop)){
					plan.setPlan_id(EcsOrderConsts.ORDER_PRE_VALIDATE_BSS);
				} else {
					plan.setPlan_id(EcsOrderConsts.WORDER__PRE_DEAL_PLAN);
				}
				TacheFact fact = new TacheFact();
				fact.setOrder_id(order_id);
				plan.setFact(fact);
				plan.setDeleteLogs(true);
				PlanRuleTreeExeResp preResp = CommonDataFactory.getInstance().exePlanRuleTree(plan);
				BusiCompResponse busiResp = CommonDataFactory.getInstance().getRuleTreeresult(preResp);

				// 标准化成功,通知信商城
	    		/*if(ConstsCore.ERROR_SUCC.equals(busiResp.getError_code())){
	    			StatuSynchReq statuSyn = new StatuSynchReq(order_id,EcsOrderConsts.DIC_ORDER_NODE_O,EcsOrderConsts.DIC_ORDER_NODE_O_DESC,EcsOrderConsts.ORDER_STANDARDING_1,EcsOrderConsts.ORDER_STANDARDING_1_DESC,"");
					CommonDataFactory.getInstance().notifyNewShop(statuSyn);
	    		}*/
	    		
    		}
    		ordStardAft(order_id);
		}
	}
	
	/**
	 * 启动订单归集方案--新代码
	 * @param service_code
	 * @param resp
	 */
	public void startOrderStandingPlan(String service_code, List<String> orderIdList) {
		for (String order_id : orderIdList) {
			//添加订单推送消息队列================================
    		//更新缓存
    		//去除强制更新，手动设置
    		//启动订单归集方案
			ordStardPre(order_id);
			//判断订单是否已经执行过轨迹方案---去重校验 add by ricky
			String flow_id = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.FLOW_ID);
			if(StringUtils.isNotEmpty(flow_id)){//订单已经执行过归集方案 
				continue;
			}
    		BusiCompRequest busi = new BusiCompRequest();
    		busi.setOrder_id(order_id);
    		Map queryParams = new HashMap();
    		queryParams.put("order_id", order_id);
    		queryParams.put("plan_id", EcsOrderConsts.ORDER_COLLECT_PLAN);
    		busi.setEnginePath("enterTraceRule.exec");
    		busi.setQueryParams(queryParams);
    		
    		long start = System.currentTimeMillis();
    		BusiCompResponse busiComp = CommonDataFactory.getInstance().execBusiComp(busi);
    		long end = System.currentTimeMillis();
    		logger.info("服务编码："+service_code+"订单编号:"+order_id+"订单归集方案执行完成时间：=====>"+(end-start));
			
			//add by wui追加值更新
			String order_model = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ORDER_MODEL);
			String is_send_wms = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.IS_SEND_WMS);
			String flow_inst_id = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest().getFlow_inst_id();
			String zb_status = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ZB_STATUS);
			flow_id = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.FLOW_ID);
			String flow_trace_id = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest().getFlow_trace_id();
			
			String sys_code = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.SYS_CODE);
			String syn_ord_zb = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.SYN_ORD_ZB);
			String is_aop = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.IS_AOP);
			this.baseDaoSupport.execute("update es_order_ext a set a.order_model = ?,a.is_send_wms = ?,flow_inst_id = ? , zb_status = ? ,flow_id = ?, flow_trace_id= ?, is_aop = ?  where order_id = ? ", order_model,is_send_wms,flow_inst_id,zb_status,flow_id,flow_trace_id,is_aop,order_id);
			this.baseDaoSupport.execute("update es_order_extvtl a set a.sys_code = ?,a.syn_ord_zb = ? where order_id = ? ", sys_code,syn_ord_zb,order_id);
			logger.info("订单编号:"+order_id+"order_model:"+order_model+",is_send_wms:"+is_send_wms+",flow_inst_id:"+flow_inst_id+",:"+zb_status+"flow_id:"+flow_id+",flow_trace_id:"+flow_trace_id+",sys_code:"+sys_code+",:syn_ord_zb"+syn_ord_zb);
		
    		//订单预校验
    		ordStardAft(order_id);
		}
	}
	@Override
	public CoQueueRuleResp coQueue(CoQueueRuleReq coQueueRuleReq) {
		CoQueue queue = coQueueRuleReq.getCoQueue();
		CoQueueRuleResp resp = new CoQueueRuleResp();
		String json ="";
		try{
			if(queue!=null){
				json = queue.getContents(); 
				OrderCollect oc =CommonTools.jsonToBean(json, OrderCollect.class);
				OrderAddResp resp1 = this.syncOrderStandardizing(oc,coQueueRuleReq.getUserSessionId(),coQueueRuleReq.getService_code());
				resp.setOrderAddResp(resp1);
			}
			resp.setError_code("0");
			resp.setError_msg("成功");
			resp.setResp_code(Consts.RESP_CODE_000);
			resp.setResp_msg("成功");
		}catch(ApiBusiException ex){
			ex.printStackTrace();
			resp.setError_code("1");
			resp.setError_msg("失败");
		}
		return resp;
	}


	public OrderStandarService getOrderStandarService() {
		return orderStandarService;
	}


	public void setOrderStandarService(OrderStandarService orderStandarService) {
		this.orderStandarService = orderStandarService;
	}
	public ICacheUtil getCacheUtil() {
		return cacheUtil;
	}
	public void setCacheUtil(ICacheUtil cacheUtil) {
		this.cacheUtil = cacheUtil;
	}

	/**
	 * 正式单系统流转统一定义业务
	 */
	public static CheckResp ordStardPre(String order_id){
		CheckReq req = new CheckReq();
		req.setBiz_id(EccConsts.ORDER_PLAN_FLOW_BIZ);
		req.setExe_time(EccConsts.EXE_TIME_BEFORE);
		req.setObj_id(order_id);
		req.setObj_type("order");
		req.setTrace_code(ConstsCore.TRACE_R);
        CheckResp checkRsp = CommonDataFactory.getInstance().checkProxy(req);
        return checkRsp;
	}
	public static CheckResp ordStardAft(String order_id){
		CheckReq req = new CheckReq();
		req.setBiz_id(EccConsts.ORDER_PLAN_FLOW_BIZ);
		req.setExe_time(EccConsts.EXE_TIME_AFTER);
		req.setObj_id(order_id);
		req.setObj_type("order");
		req.setTrace_code(ConstsCore.TRACE_R);
        CheckResp checkRsp = CommonDataFactory.getInstance().checkProxy(req);
        if(!ConstsCore.ERROR_SUCC.equals(checkRsp.getError_code())){
            return checkRsp;
        }
        
        return null;
	}
}
