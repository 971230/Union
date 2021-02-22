package zte.net.common.rule;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import params.ZteRequest;
import params.ZteResponse;
import params.coqueue.req.CoQueueAddReq;
import params.coqueue.resp.CoQueueAddResp;
import params.order.req.OrderExceptionLogsReq;
import params.order.req.OrderHandleLogsReq;
import params.orderqueue.req.AsynExecuteMsgWriteMqReq;
import params.req.OrderExpMarkProcessedReq;
import params.resp.OrderExpMarkProcessedResp;
import rule.RuleInvoker;
import rule.params.coqueue.req.CoQueueRuleReq;
import util.ChannelidProductcompRefUtil;
import util.Utils;
import utils.GlobalThreadLocalHolder;
import zte.net.common.annontion.context.action.ZteMethodAnnontion;
import zte.net.common.annontion.context.action.ZteServiceAnnontion;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.InfConst;
import zte.net.ecsord.common.SpecConsts;
import zte.net.ecsord.common.StypeConsts;
import zte.net.ecsord.params.base.req.BusiCompRequest;
import zte.net.ecsord.params.base.resp.BusiCompResponse;
import zte.net.ecsord.params.bss.req.NumberResourceQueryZjBssRequest;
import zte.net.ecsord.params.bss.resp.NumberResourceQueryZjBssResponse;
import zte.net.ecsord.params.bss.vo.NumInfoZjBss;
import zte.net.ecsord.params.busi.req.AttrDiscountInfoBusiRequest;
import zte.net.ecsord.params.busi.req.AttrFeeInfoBusiRequest;
import zte.net.ecsord.params.busi.req.OrderAdslBusiRequest;
import zte.net.ecsord.params.busi.req.OrderBusiRequest;
import zte.net.ecsord.params.busi.req.OrderDeliveryBusiRequest;
import zte.net.ecsord.params.busi.req.OrderExtBusiRequest;
import zte.net.ecsord.params.busi.req.OrderInternetBusiRequest;
import zte.net.ecsord.params.busi.req.OrderItemExtBusiRequest;
import zte.net.ecsord.params.busi.req.OrderItemExtvtlBusiRequest;
import zte.net.ecsord.params.busi.req.OrderItemProdBusiRequest;
import zte.net.ecsord.params.busi.req.OrderLockBusiRequest;
import zte.net.ecsord.params.busi.req.OrderPhoneInfoBusiRequest;
import zte.net.ecsord.params.busi.req.OrderPhoneInfoFukaBusiRequest;
import zte.net.ecsord.params.busi.req.OrderRealNameInfoBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;
import zte.net.ecsord.params.ecaop.req.AopSmsSendReq;
import zte.net.ecsord.params.ecaop.req.ResourcePreCheckReq;
import zte.net.ecsord.params.ecaop.req.vo.EmpOperInfoVo;
import zte.net.ecsord.params.ecaop.resp.AopSmsSendResp;
import zte.net.ecsord.params.ecaop.resp.ResourcePreCheckResp;
import zte.net.ecsord.params.ecaop.resp.vo.BrandInfoList;
import zte.net.ecsord.params.ems.req.EmsRoutePushServiceReq;
import zte.net.ecsord.params.nd.req.DispatchingNumReceivingNDRequset;
import zte.net.ecsord.params.nd.req.NotifyOrderStatuNDRequset;
import zte.net.ecsord.params.nd.req.OrderDealSuccessNDRequset;
import zte.net.ecsord.params.sf.req.ArtificialSelectRequest;
import zte.net.ecsord.params.sf.req.RoutePushServiceRequset;
import zte.net.ecsord.params.sr.req.RevertCardRequset;
import zte.net.ecsord.params.sr.resp.RevertCardResponse;
import zte.net.ecsord.params.wms.req.NotifyOrderStatusToWMSReq;
import zte.net.ecsord.params.wms.resp.NotifyOrderStatusToWMSResp;
import zte.net.ecsord.params.wyg.req.StatuSynchReq;
import zte.net.ecsord.params.wyg.req.WYGTestRequest;
import zte.net.ecsord.params.wyg.resp.WYGTestResponse;
import zte.net.ecsord.params.zb.req.NotifyOrderAbnormalToZBRequest;
import zte.net.ecsord.params.zb.req.StateSynToZBRequest;
import zte.net.ecsord.params.zb.req.StateSynchronizationToSystemRequest;
import zte.net.ecsord.params.zb.req.StateUtil;
import zte.net.ecsord.params.zb.resp.NotifyOrderAbnormalToZBResponse;
import zte.net.ecsord.params.zb.resp.StateSynToZBResponse;
import zte.net.ecsord.rule.comm.ExceptionFact;
import zte.net.ecsord.rule.logistics.LogisticsFact;
import zte.net.ecsord.rule.mode.ModeFact;
import zte.net.ecsord.rule.mode.PreRuleFact;
import zte.net.ecsord.rule.ordersy.OrderSyFact;
import zte.net.ecsord.rule.tache.TacheFact;
import zte.net.ecsord.rule.workflow.WorkFlowFact;
import zte.net.ecsord.utils.AttrUtils;
import zte.net.iservice.IEcsOrdServices;
import zte.net.iservice.IOrderQueueBaseManager;
import zte.net.params.req.PlanRuleTreeExeReq;
import zte.net.params.resp.PlanRuleTreeExeResp;
import zte.net.params.resp.RuleTreeExeResp;
import zte.params.ecsord.req.HasAutoExceptionReq;
import zte.params.ecsord.req.InsertOrderHandLogReq;
import zte.params.ecsord.resp.HasAutoExceptionResp;
import zte.params.goods.req.DcPublicInfoCacheProxyReq;
import zte.params.goods.req.IncreaseInventoryNumReq;
import zte.params.goods.req.SubtractInventoryNumReq;
import zte.params.goods.resp.DcPublicInfoCacheProxyResp;
import zte.params.goods.resp.IncreaseInventoryNumResp;
import zte.params.goods.resp.SubtractInventoryNumResp;
import zte.params.order.req.SmsSendReq;

import com.powerise.ibss.framework.Const;
import com.ztesoft.api.ApiBusiException;
import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.api.spring.ApiContextHolder;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.ibss.common.util.DateUtil;
import com.ztesoft.net.ecsord.params.ecaop.req.BusinessFeeQryReq;
import com.ztesoft.net.ecsord.params.ecaop.req.CmcSmsSendReq;
import com.ztesoft.net.ecsord.params.ecaop.req.CustInfoOrderBindReq;
import com.ztesoft.net.ecsord.params.ecaop.req.KdNumberCheckReq;
import com.ztesoft.net.ecsord.params.ecaop.req.KdnumberQryReq;
import com.ztesoft.net.ecsord.params.ecaop.req.O2OStatusUpdateReq;
import com.ztesoft.net.ecsord.params.ecaop.req.OldUserCheckReq;
import com.ztesoft.net.ecsord.params.ecaop.req.PromoteProductReq;
import com.ztesoft.net.ecsord.params.ecaop.req.QryFtthObjIdReq;
import com.ztesoft.net.ecsord.params.ecaop.req.SchemeFeeQryReq;
import com.ztesoft.net.ecsord.params.ecaop.req.SelfspreadOrderinfoSynReq;
import com.ztesoft.net.ecsord.params.ecaop.req.UserActivationReq;
import com.ztesoft.net.ecsord.params.ecaop.resp.BusinessFeeQryResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.CmcSmsSendResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.CustInfoOrderBindResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.KdNumberCheckResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.KdnumberQryResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.O2OStatusUpdateResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.OldUserCheckResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.PromoteProductResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.QryFtthObjIDResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.SchemeFeeQryResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.SelfspreadOrderinfoSynResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.UserActivationResp;
import com.ztesoft.net.ecsord.params.ecaop.vo.BusinessFeeVO;
import com.ztesoft.net.ecsord.params.ecaop.vo.OldUserCheckVO;
import com.ztesoft.net.ecsord.params.ecaop.vo.OldUserInfo;
import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.eop.sdk.context.MqEnvGroupConfigSetting;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.IDaoSupport;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.consts.ZjEcsOrderConsts;
import com.ztesoft.net.mall.core.model.CoQueue;
import com.ztesoft.net.mall.core.model.Logi;
import com.ztesoft.net.mall.core.service.ICoQueueManager;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.model.BusiDealResult;
import com.ztesoft.net.model.Outer;
import com.ztesoft.net.rule.fact.OrderPageFact;
import com.ztesoft.net.rule.util.RuleFlowUtil;
import com.ztesoft.net.service.ICommonManager;
import com.ztesoft.net.service.IEcsOrdManager;
import com.ztesoft.net.service.IOrdCollectManagerManager;
import com.ztesoft.net.service.IOrdOpenAccountTacheManager;
import com.ztesoft.net.service.IOrdPickingTacheManager;
import com.ztesoft.net.service.IOrdShipTacheManager;
import com.ztesoft.net.service.IOrdVisitTacheManager;
import com.ztesoft.net.service.IOrdWriteCardTacheManager;
import com.ztesoft.net.service.IOrderFlowManager;
import com.ztesoft.net.service.IOrderSDSModelManager;
import com.ztesoft.net.service.IQueueCardMateManager;
import com.ztesoft.remote.inf.IRemoteSmsService;

import commons.CommonTools;
import consts.ConstsCore;

/**
 * 通用业务组件处理类
 * 
 * @author xuefeng
 */
@ZteServiceAnnontion(trace_name = "ZteCommonTraceRule", trace_id = "1", version = "1.0", desc = "通用业务组件")
public class ZteCommonTraceRule extends ZteTraceBaseRule {
	private static Logger logger = Logger.getLogger(ZteCommonTraceRule.class);
	ICacheUtil cacheUtil;
	@Resource
	private IOrdWriteCardTacheManager ordWriteCardTacheManager;
	@Resource
	private IOrdOpenAccountTacheManager ordOpenAccountTacheManager;
	@Resource
	private IOrdShipTacheManager ordShipTacheManager;
	@Resource
	private IEcsOrdManager ecsOrdManager;
	@Resource
	private ICommonManager commonManager;
	@Resource
	private IOrderFlowManager ordFlowManager;

	@Resource
	private IOrdVisitTacheManager ordVisitTacheManager;

	@Resource
	private IOrderSDSModelManager orderSDSModelManager;

	@Resource
	private IOrdPickingTacheManager ordPickingTacheManager;

	@Resource
	private ICoQueueManager coQueueManager;

	@Resource
	private IOrdCollectManagerManager ordCollectManager;
	@Resource
	private IQueueCardMateManager queueCardMateManager;

	@Override
	@ZteMethodAnnontion(name = "通用组件入口", group_name = "自动化模式", order = "1", page_show = true, path = "zteCommonTraceRule.exec")
	public BusiCompResponse engineDo(BusiCompRequest busiCompRequest) {
		BusiCompResponse resp = new BusiCompResponse();
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}

	/**
	 * 拆单
	 * 
	 * @param busiCompRequest
	 * @return
	 */
	@ZteMethodAnnontion(name = "订单拆单", group_name = "通用组件", order = "2", page_show = true, path = "zteCommonTraceRule.ordSplit")
	public BusiCompResponse ordSplit(BusiCompRequest busiCompRequest) {
		BusiCompResponse resp = new BusiCompResponse();
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}

	/**
	 * 属性标准化
	 * 
	 * @param busiCompRequest
	 * @return
	 */
	@ZteMethodAnnontion(name = "订单属性标准化", group_name = "通用组件", order = "3", page_show = true, path = "zteCommonTraceRule.ordAttrFormat")
	public BusiCompResponse ordAttrFormat(BusiCompRequest busiCompRequest) {
		BusiCompResponse resp = new BusiCompResponse();
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}

	/**
	 * 启动流程
	 * 
	 * @param busiCompRequest
	 * @return
	 * @throws ApiBusiException
	 */
	@ZteMethodAnnontion(name = "启动工作流", group_name = "通用组件", order = "4", page_show = true, path = "zteCommonTraceRule.startWorkFlow")
	public BusiCompResponse startWorkFlow(BusiCompRequest busiCompRequest) throws ApiBusiException {

		String order_id = (String) busiCompRequest.getQueryParams().get("order_id");

		if (StringUtil.isEmpty(order_id))
			order_id = busiCompRequest.getOrder_id();
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		String order_from = orderTree.getOrderExtBusiRequest().getOrder_from();
		if (EcsOrderConsts.ORDER_FROM_10003.equals(order_from)) {
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.SYN_ORD_ZB }, new String[] { EcsOrderConsts.SYN_ORD_ZB_1 });
		}

		String old_flow_id = orderTree.getOrderExtBusiRequest().getOld_flow_id();
		String flow_id = orderTree.getOrderExtBusiRequest().getFlow_id();
		String flow_inst_id = orderTree.getOrderExtBusiRequest().getFlow_inst_id();
		boolean start = false;// 强制启动工作流
		Map queryParams = busiCompRequest.getQueryParams();
		if (queryParams != null) {
			Object obj = queryParams.get("start");// 强制启动工作流
			if (obj != null)
				start = (Boolean) obj;
		}
		if (StringUtil.isEmpty(flow_id))
			CommonTools.addBusiError("1", "订单:" + order_id + "流程flow_id为空，启动流程失败");
		try {
			if (start) {
				// 强制启动工作流
				return this.startOrderWorkFlow(busiCompRequest,orderTree);
			} else if (!StringUtil.isEmpty(old_flow_id) && !old_flow_id.equals(flow_id)) {
				return this.startOrderWorkFlow(busiCompRequest,orderTree);
			} else if (StringUtil.isEmpty(flow_inst_id)) {
				return this.startOrderWorkFlow(busiCompRequest,orderTree);
			} else {
				BusiCompResponse resp = new BusiCompResponse();
				resp.setError_code("0");
				resp.setError_msg("成功");
				return resp;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			CommonTools.addError("1", "启动工作流失败");
			return null;
		}
	}

	/**
	 * 匹配环节页面
	 * 
	 * @param busiCompRequest
	 * @return
	 */
	@ZteMethodAnnontion(name = "环节界面匹配", group_name = "通用组件", order = "5", page_show = true, path = "zteCommonTraceRule.mathTachPageUrl")
	public BusiCompResponse mathTachPageUrl(BusiCompRequest busiCompRequest) {
		BusiCompResponse resp = new BusiCompResponse();
		// 获取规则对象
		Map params = busiCompRequest.getQueryParams();
		OrderPageFact fact = (OrderPageFact) params.get("fact");
		resp.setFact(fact);
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}

	@ZteMethodAnnontion(name = "模式匹配", group_name = "通用组件", order = "6", page_show = true, path = "zteCommonTraceRule.matchingOrderModel")
	public BusiCompResponse matchingOrderModel(BusiCompRequest busiCompRequest) {

		long start = System.currentTimeMillis();
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		// 获取规则对象
		Map params = busiCompRequest.getQueryParams();
		ModeFact fact = (ModeFact) params.get("fact");
		String oper_mode = fact.getOper_mode();
		// 更新订单模式
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		OrderExtBusiRequest orderExtBusiReq = orderTree.getOrderExtBusiRequest();
		//String shipping_quick = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.SHIPPING_QUICK);
		String t_oper_mode = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ORDER_MODEL);
		// 判断订单是否闪电送
		/*if (EcsOrderConsts.SHIPPING_QUICK_01.equals(shipping_quick)) {
			// 闪电送
			String dealResult = ShipDealResult.getInstance().getShip_deal_result(order_id);
			if (EcsOrderConsts.SHIP_DEAL_RESULT_SUCC.equals(dealResult) || EcsOrderConsts.SHIP_DEAL_RESULT_NULL.equals(dealResult)) {
				oper_mode = EcsOrderConsts.ORDER_MODEL_04;
			} else if (EcsOrderConsts.SHIP_DEAL_RESULT_FAIL.equals(dealResult)) {
				oper_mode = EcsOrderConsts.ORDER_MODEL_02;
			} else {
				CommonTools.addError("-999999", "闪电送，未指定物流公司，请检查数据");
			}
		} else {
			// 判断是否物流模式
			if (EcsOrderConsts.ORDER_MODEL_04.equals(oper_mode) && !StringUtils.isEmpty(t_oper_mode)) {
				oper_mode = EcsOrderConsts.ORDER_MODEL_02;
			}
		}*/
		// 根据配置开关确定是否把自动化模式转为人工集中模式
		if (EcsOrderConsts.ORDER_MODEL_01.equals(oper_mode)) {
			ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
			String is_stop_auto_mode = cacheUtil.getConfigInfo(EcsOrderConsts.IS_STOP_AUTO_MODE);
			if (ConstsCore.CONSTS_YES.equals(is_stop_auto_mode)) {
				oper_mode = EcsOrderConsts.ORDER_MODEL_02;
			}
		}
		/*
		 * 共享子号订单，不强制改独立模式
		 * if(!StringUtils.isEmpty(CommonDataFactory.getInstance().
		 * getAttrFieldValue(order_id, AttrConsts.SUB_NO))){ oper_mode =
		 * EcsOrderConsts.ORDER_MODEL_05; }
		 */
		if (!t_oper_mode.equals(oper_mode)) {
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.ORDER_MODEL }, new String[] { oper_mode });
			String set_sql = " order_model = '"+oper_mode+"' ";
			String table_name = "es_order_ext";
			orderExtBusiReq.setOrder_model(oper_mode);
			this.updateOrderTree(set_sql, table_name, order_id, orderTree);
		}
		
		// 设置模式匹配完成后才能确定的属性值
		this.setAttrAfterModel(order_id);
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}
	
	/***
	 * 前置规则--模式匹配
	 * add by hxm 2018.5.23
	 * @param busiCompRequest
	 * @return
	 */
	@ZteMethodAnnontion(name = "模式匹配(新)", group_name = "通用组件", order = "59", page_show = true, path = "zteCommonTraceRule.matchingOrderModelNew")
	public BusiCompResponse matchingOrderModelNew(BusiCompRequest busiCompRequest) {

		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		// 获取规则对象
		Map params = busiCompRequest.getQueryParams();
		PreRuleFact fact = (PreRuleFact) params.get("fact");
		String oper_mode = fact.getOper_mode();
		Map<String,String> objMap = fact.getParamMap();
		String is_physics = objMap.get("is_physics");
		//String t_oper_mode = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ORDER_MODEL);
		
		// 根据配置开关确定是否把自动化模式转为人工集中模式
		if (EcsOrderConsts.ORDER_MODEL_01.equals(oper_mode)) {
			ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
			String is_stop_auto_mode = cacheUtil.getConfigInfo(EcsOrderConsts.IS_STOP_AUTO_MODE);
			if (ConstsCore.CONSTS_YES.equals(is_stop_auto_mode)) {
				oper_mode = EcsOrderConsts.ORDER_MODEL_02;
			}
		}
		/*
		 * 共享子号订单，不强制改独立模式
		 * if(!StringUtils.isEmpty(CommonDataFactory.getInstance().
		 * getAttrFieldValue(order_id, AttrConsts.SUB_NO))){ oper_mode =
		 * EcsOrderConsts.ORDER_MODEL_05; }
		 */
//		if (!t_oper_mode.equals(oper_mode)) {
//			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.ORDER_MODEL }, new String[] { oper_mode });
//		}
		Map paramMap = new HashMap();
		paramMap.put("oper_mode", oper_mode);
//		fact.setParamMap(paramMap);
		// 设置模式匹配完成后才能确定的属性值
		//this.setAttrAfterModel(order_id);
		
		String is_send_wms = EcsOrderConsts.NO_DEFAULT_VALUE;
		if(EcsOrderConsts.IS_DEFAULT_VALUE.equals(is_physics) && EcsOrderConsts.OPER_MODE_ZD.equals(oper_mode)){
			is_send_wms = EcsOrderConsts.IS_DEFAULT_VALUE;
		}
		paramMap.put("is_send_wms", is_send_wms);
		fact.setValueResponse(paramMap);
		resp.setFact(fact);
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}

	public static int matchingWorkflow = 0;

	@ZteMethodAnnontion(name = "匹配工作流", group_name = "通用组件", order = "7", page_show = true, path = "zteCommonTraceRule.matchingWorkflow")
	public BusiCompResponse matchingWorkflow(BusiCompRequest busiCompRequest) {
		long aa = System.currentTimeMillis();
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		Map params = busiCompRequest.getQueryParams();
		WorkFlowFact fact = (WorkFlowFact) params.get("fact");
		logger.info("\n模式匹配规格因子******是否开户：" + fact.getNeed_open_act() + " 用户类型：" + fact.getUser_flag() + " 商品类型：" + fact.getGoods_type() + " " + "业务类型：" + fact.getBusi_type() + " 网别："
				+ fact.getNet_type() + " 流程定义：" + fact.getFlow_id() + "\n");
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		OrderExtBusiRequest orderExtBusiRequest = orderTree.getOrderExtBusiRequest();
		String flow_id = orderExtBusiRequest.getFlow_id();
		String o_flow_id = orderExtBusiRequest.getOld_flow_id();
		String m_flow_id = fact.getFlow_id();

		if (StringUtil.isEmpty(m_flow_id))
			m_flow_id = (String) params.get("flow_id");
		// 如果订单匹配到的工作流ID与订单树上的工作流ID不一样 更新订单树===mochunrun=======2014-11-20
		if (StringUtil.isEmpty(flow_id) || !flow_id.equals(m_flow_id)) {
			// 更新老的工作流ID
			if (StringUtil.isEmpty(flow_id)) {
				CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.FLOW_ID }, new String[] { m_flow_id });
				orderExtBusiRequest.setOrder_id(order_id);
				orderExtBusiRequest.setFlow_id(m_flow_id);
				orderExtBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
				orderExtBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
				orderExtBusiRequest.store();
			} else {
				// add by wui不为空，才需要更新老的，优化处理
				orderExtBusiRequest.setOrder_id(order_id);
				orderExtBusiRequest.setOld_flow_id(flow_id);
				orderExtBusiRequest.setFlow_id(m_flow_id);
				orderExtBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
				orderExtBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
				orderExtBusiRequest.store();
				CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.FLOW_ID }, new String[] { m_flow_id });
			}
		}
		long end = System.currentTimeMillis();
		logger.info("匹配工作流matchingWorkflowmatchingWorkflowmatchingWorkflowmatchingWorkflowmatchingWorkflowmatchingWorkflowmatchingWorkflowmatchingWorkflow==>" + m_flow_id);
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}
	
	/***
	 * 前置规则匹配
	 * add by hxm 2018.5.23
	 * @param busiCompRequest
	 * @return
	 */
	@ZteMethodAnnontion(name = "匹配工作流(新)", group_name = "通用组件", order = "60", page_show = true, path = "zteCommonTraceRule.matchingWorkflowNew")
	public BusiCompResponse matchingWorkflowNew(BusiCompRequest busiCompRequest) {
		long aa = System.currentTimeMillis();
		BusiCompResponse resp = new BusiCompResponse();
//		String order_id = busiCompRequest.getOrder_id();
		Map params = busiCompRequest.getQueryParams();
		PreRuleFact fact = (PreRuleFact) params.get("fact");
		Map<String,String> objMap = fact.getParamMap();
		//OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		//OrderExtBusiRequest orderExtBusiRequest = orderTree.getOrderExtBusiRequest();
		String flow_id = fact.getFlow_id();
//		String m_flow_id = fact.getFlow_id();

		if (StringUtil.isEmpty(flow_id))
			flow_id = objMap.get("flow_id");
		// 如果订单匹配到的工作流ID与订单树上的工作流ID不一样 更新订单树===mochunrun=======2014-11-20
//		if (StringUtil.isEmpty(flow_id) || !flow_id.equals(m_flow_id)) {
//			// 更新老的工作流ID
//			if (StringUtil.isEmpty(flow_id)) {
//				CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.FLOW_ID }, new String[] { m_flow_id });
//				orderExtBusiRequest.setOrder_id(order_id);
//				orderExtBusiRequest.setFlow_id(m_flow_id);
//				orderExtBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
//				orderExtBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
//				orderExtBusiRequest.store();
//			} else {
//				// add by wui不为空，才需要更新老的，优化处理
//				orderExtBusiRequest.setOrder_id(order_id);
//				orderExtBusiRequest.setOld_flow_id(flow_id);
//				orderExtBusiRequest.setFlow_id(m_flow_id);
//				orderExtBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
//				orderExtBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
//				orderExtBusiRequest.store();
//				CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.FLOW_ID }, new String[] { m_flow_id });
//			}
//		}
		fact.setFlow_id(flow_id);
		resp.setFact(fact);
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}

	@ZteMethodAnnontion(name = "身份证预校验", group_name = "身份证预校验", order = "8", page_show = true, path = "zteCommonTraceRule.certiValide")
	public BusiCompResponse certiValide(BusiCompRequest busiCompRequest) throws ApiBusiException {

		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		// 身份证预校验
		BusiDealResult certi_valide = ordVisitTacheManager.certiValide(order_id);
		if (!certi_valide.getError_code().equals(EcsOrderConsts.BUSI_DEAL_RESULT_0000)) {
			// 标记异常
			Utils.markException(order_id, false, "GZTCHECKFAIL", certi_valide.getError_msg());
			// 调用组件
			// BusiCompRequest busi = new BusiCompRequest();
			// Map queryParams = new HashMap();
			// //queryParams.put(EcsOrderConsts.EXCEPTION_FROM,
			// EcsOrderConsts.EXCEPTION_FROM_ORD);
			// queryParams.put("order_id", order_id);
			// busi.setEnginePath("zteCommonTraceRule.notifyOrderAbnormalToZB");
			// busi.setOrder_id(order_id);
			// busi.setQueryParams(queryParams);
			// BusiCompResponse busiResp =
			// CommonDataFactory.getInstance().execBusiComp(busi);
			ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
			String notifyOrderAbnormalToZB = cacheUtil.getConfigInfo("notifyOrderAbnormalToZB");
			// 配置 订单异常通知总部 开关 0：关 1：开
			if (!"0".equals(notifyOrderAbnormalToZB) && !"".equals(notifyOrderAbnormalToZB)) {
				OrderExtBusiRequest orderExt = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest();
				if (EcsOrderConsts.ORDER_FROM_10003.equals(orderExt.getOrder_from())) {
					// 写入定时任务
					CoQueueAddReq req = new CoQueueAddReq();
					req.setCo_name("订单异常通知");
					req.setService_code("CO_ABNORMALTOZB");
					req.setAction_code("A");
					req.setObject_type("DINGDAN");
					String msg = certi_valide.getError_msg();
					if (msg.lastIndexOf("接口异常接口异常") > -1) {// 调用出错
						msg = "-1";
					} else {
						msg = certi_valide.getError_msg().substring(certi_valide.getError_msg().lastIndexOf("*") + 1);
					}

					req.setContents(msg);// 同步的数据内容(json格式的字符串)
					req.setObject_id(order_id);
					String batch_id = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.SHIP_BATCH);
					if (StringUtil.isEmpty(batch_id)) {
						batch_id = "1";
						for (int i = 0; i < 17; i++) {
							batch_id += (int) (Math.random() * 10);
						}
					}
					req.setBatch_id(batch_id);
					// 信息写入es_co_queue
					ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
					CoQueueAddResp coresp = client.execute(req, CoQueueAddResp.class);
				}
			}

			CommonTools.addBusiError(certi_valide.getError_code(), certi_valide.getError_msg());
		}

		resp.setError_code("0");
		resp.setError_msg("身份证预校验成功");
		// resp.setResp(certi_valide.getResponse());
		resp.setResponse(certi_valide.getResponse());
		return resp;
	}

	/**
	 * 宋琪 订单异常通知总部 异步通知 通知/接收异常状态，挂起或恢复订单生产
	 */
	@ZteMethodAnnontion(name = "订单异常通知总部", group_name = "订单异常通知", order = "8", page_show = true, path = "zteCommonTraceRule.notifyOrderAbnormalToZB")
	public BusiCompResponse notifyOrderAbnormalToZB(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		BusiDealResult result = ordVisitTacheManager.notifyOrderAbnormalToZB(busiCompRequest, order_id);
		if (!result.getError_code().equals(EcsOrderConsts.BUSI_DEAL_RESULT_0000)) {
			CommonTools.addBusiError(result.getError_code(), result.getError_msg());
		}
		resp.setError_code("0");
		resp.setError_msg("订单异常通知总部成功");
		resp.setResponse(result.getResponse());
		return resp;
	}

	/*
	 * 宋琪 一证五户校验不过进入异常单 组件 客户下用户数据查询接口
	 */
	@ZteMethodAnnontion(name = "客户下用户数据查询接口", group_name = "客户下用户数据查询接口", order = "8", page_show = true, path = "zteCommonTraceRule.userDataQry")
	public BusiCompResponse userDataQry(BusiCompRequest busiCompRequest) throws ApiBusiException {

		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		// 客户下用户数据查询接口
		BusiDealResult userDataResult = ordVisitTacheManager.userDataQry(order_id);
		if (!userDataResult.getError_code().equals(EcsOrderConsts.BUSI_DEAL_RESULT_0000)) {
			// 标记异常
			Utils.markException(order_id, false, EcsOrderConsts.ABNORMAL_TYPE_BSS, userDataResult.getError_msg());
			CommonTools.addBusiError(userDataResult.getError_code(), userDataResult.getError_msg());
		}

		resp.setError_code("0");
		resp.setError_msg("符合一证5卡");
		resp.setResponse(userDataResult.getResponse());
		return resp;
	}

	/**
	 * 12岁校验 最小年龄
	 * 
	 * @author 宋琪
	 * @param busiCompRequest
	 * @return
	 * @throws ApiBusiException
	 */
	@ZteMethodAnnontion(name = "12周岁校验", group_name = "12周岁校验", order = "8", page_show = true, path = "zteCommonTraceRule.ageValide")
	public BusiCompResponse ageValide(BusiCompRequest busiCompRequest) throws ApiBusiException {

		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		String max = cacheUtil.getConfigInfo("AGE_MIN");
		Integer max_num = 12;
		if ("".equals(max)) {
			max = "12";
		}
		max_num = Integer.parseInt(max);
		// 12岁校验 最小年龄
		BusiDealResult age_valide = ordVisitTacheManager.ageValide(order_id, max_num);
		if (!age_valide.getError_code().equals(EcsOrderConsts.BUSI_DEAL_RESULT_0000))
			CommonTools.addBusiError(age_valide.getError_code(), age_valide.getError_msg());

		resp.setError_code("0");
		resp.setError_msg("12周岁校验成功");
		resp.setResponse(age_valide.getResponse());
		return resp;
	}

	/**
	 * 16岁校验 码上购限制
	 * 
	 * @author 宋琪
	 * @param busiCompRequest
	 * @return
	 * @throws ApiBusiException
	 */
	@ZteMethodAnnontion(name = "16周岁校验", group_name = "16周岁校验", order = "8", page_show = true, path = "zteCommonTraceRule.ageValide4MSG")
	public BusiCompResponse ageValide4MSG(BusiCompRequest busiCompRequest) throws ApiBusiException {

		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		// 16岁校验
		BusiDealResult age_valide = ordVisitTacheManager.ageValide(order_id, 16);
		if (!age_valide.getError_code().equals(EcsOrderConsts.BUSI_DEAL_RESULT_0000)) {
			// 标记异常
			Utils.markException(order_id, false, EcsOrderConsts.ABNORMAL_TYPE_BSS, age_valide.getError_msg());
			CommonTools.addBusiError(age_valide.getError_code(), age_valide.getError_msg());
		}

		resp.setError_code("0");
		resp.setError_msg("16周岁校验成功");
		resp.setResponse(age_valide.getResponse());
		return resp;
	}
	
	/**
	 * 业务类型校验
	 * 
	 * @author 喻天琦
	 * @param busiCompRequest
	 * @return
	 * @throws ApiBusiException
	 */
	@ZteMethodAnnontion(name = "业务类型校验", group_name = "业务类型校验", order = "8", page_show = true, path = "zteCommonTraceRule.serviceTypeCheckMG")
	public BusiCompResponse serviceTypeCheckMG(BusiCompRequest busiCompRequest) throws ApiBusiException {

		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		BusiDealResult service_type_check = ordVisitTacheManager.serviceTypeCheck(order_id);
		if (!service_type_check.getError_code().equals(EcsOrderConsts.BUSI_DEAL_RESULT_0000)){
			CommonTools.addBusiError(service_type_check.getError_code(), service_type_check.getError_msg());
		}
			
		resp.setError_code("0");
		resp.setError_msg("业务类型校验成功");
		resp.setResponse(service_type_check.getResponse());
		return resp;
	}

	/**
	 * 用户返档
	 * 
	 * @author Wcl
	 * @param busiCompRequest
	 * @return
	 * @throws ApiBusiException
	 */
	@ZteMethodAnnontion(name = "用户返档", group_name = "用户返档", order = "8", page_show = true, path = "zteCommonTraceRule.returnFile")
	public BusiCompResponse returnFile(BusiCompRequest busiCompRequest) throws ApiBusiException {

		BusiCompResponse resp = new BusiCompResponse();
		BusiDealResult result_resp = new BusiDealResult();
		String order_id = busiCompRequest.getOrder_id();

		result_resp = ordVisitTacheManager.returnFile(order_id);// 调用返档接口

		if (result_resp.getError_code().equals(EcsOrderConsts.BUSI_DEAL_RESULT_0000)) {
			resp.setError_code("0");
			resp.setError_msg("成功");
		} else {
			CommonTools.addBusiError(result_resp.getError_code(), result_resp.getError_msg());
		}

		return resp;
	}

	@ZteMethodAnnontion(name = "证件照片信息查询", group_name = "证件照片信息查询", order = "8", page_show = true, path = "zteCommonTraceRule.queryCertPicinfo")
	public BusiCompResponse queryPicinfo(BusiCompRequest busiCompRequest) throws ApiBusiException {

		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		// 身份证预校验
		BusiDealResult result = ordVisitTacheManager.queryPicinfo(order_id);// result不可能为空的
		if (!result.getError_code().equals(EcsOrderConsts.BUSI_DEAL_RESULT_0000))
			CommonTools.addBusiError(result.getError_code(), result.getError_msg());

		resp.setError_code(ConstsCore.ERROR_SUCC);
		resp.setError_msg("获取总部证件照成功");
		// resp.setResp(certi_valide.getResponse());
		resp.setResponse(result.getResponse());
		return resp;
	}

	@ZteMethodAnnontion(name = "标记异常-兼容之前直接走业务组件没有调用日志", group_name = "通用组件", order = "8", page_show = true, path = "zteCommonTraceRule.markedException")
	public BusiCompResponse markedException(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		Map params = busiCompRequest.getQueryParams();
		String order_id = busiCompRequest.getOrder_id();
		String exception_from = Const.getStrValue(params, EcsOrderConsts.EXCEPTION_FROM);// 异常来源
		String exception_type = Const.getStrValue(params, EcsOrderConsts.EXCEPTION_TYPE);
		String exception_remark = Const.getStrValue(params, EcsOrderConsts.EXCEPTION_REMARK);
		String notify_sr = Const.getStrValue(params, EcsOrderConsts.NOTIFY_SR_DK); // 通知森锐掉卡
		PlanRuleTreeExeReq plan = new PlanRuleTreeExeReq();
		plan.setPlan_id(EcsOrderConsts.MARKED_EXCEPTION_PLAN);
		ExceptionFact excep = new ExceptionFact();
		excep.setOrder_id(order_id);
		excep.setException_from(exception_from);
		excep.setException_type(exception_type);
		excep.setException_desc(exception_remark);
		excep.setNotify_sr(notify_sr);
		plan.setFact(excep);
		plan.setDeleteLogs(true);
		PlanRuleTreeExeResp planResp = CommonDataFactory.getInstance().exePlanRuleTree(plan);
		BusiCompResponse busiResp = CommonDataFactory.getInstance().getRuleTreeresult(planResp);
		resp.setError_code(busiResp.getError_code());
		resp.setError_msg(busiResp.getError_msg());
		return resp;
	}

	@ZteMethodAnnontion(name = "标记异常", group_name = "通用组件", order = "8", page_show = true, path = "zteCommonTraceRule.markedExceptionRule")
	public BusiCompResponse markedExceptionRule(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		Map params = busiCompRequest.getQueryParams();
		ExceptionFact excep = (ExceptionFact) params.get("fact");
		String order_id = excep.getOrder_id();
		String exception_from = excep.getException_from();
		String exception_id = excep.getException_type();
		OrderExtBusiRequest orderExt = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest();

		// 获取异常原因
		String flow_trace_id = orderExt.getFlow_trace_id();
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		DcPublicInfoCacheProxyReq req = new DcPublicInfoCacheProxyReq();
		req.setStype(EcsOrderConsts.MARK_EXCEPTION_REL);
		req.setValue(flow_trace_id);
		req.setValue_from(EcsOrderConsts.VALUE_FROM_COL_2);
		DcPublicInfoCacheProxyResp response = client.execute(req, DcPublicInfoCacheProxyResp.class);
		String exceptionType = response.getDict_relation_value();
		String exceptionDesc = response.getDict_relation_value_desc();

		String abnormal_type = EcsOrderConsts.ORDER_ABNORMAL_TYPE_0; // 默认正常
		// 生产模式
		if (EcsOrderConsts.ORDER_MODEL_06.equals(orderExt.getOrder_model())) {
			// 标记自动化异常
			abnormal_type = EcsOrderConsts.ORDER_ABNORMAL_TYPE_3;
			// 是否已发送到WMS
			String syn_ord_wms = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.SYN_ORD_WMS);
			// 通知WMS自动化异常
			/*
			 * if(!EcsOrderConsts.EXCEPTION_FROM_WMS.equals(exception_from) &&
			 * EcsOrderConsts.SYN_ORD_WMS_1.equals(syn_ord_wms)){ String
			 * currTraceId = orderExt.getFlow_trace_id(); //当前环节ID String
			 * wms_status = "";
			 * if(EcsOrderConsts.DIC_ORDER_NODE_C.equals(currTraceId) ||
			 * EcsOrderConsts.DIC_ORDER_NODE_D.equals(currTraceId)){ //预检货、开户异常
			 * wms_status = EcsOrderConsts.ORDER_STATUS_WMS_17; }else
			 * if(EcsOrderConsts.DIC_ORDER_NODE_X.equals(currTraceId)){ //写卡异常
			 * wms_status = EcsOrderConsts.ORDER_STATUS_WMS_18; }else
			 * if(EcsOrderConsts.DIC_ORDER_NODE_Y.equals(currTraceId)){ //业务办理异常
			 * wms_status = EcsOrderConsts.ORDER_STATUS_WMS_19; }
			 * NotifyOrderStatusToWMSReq notifyWMS = new
			 * NotifyOrderStatusToWMSReq(); notifyWMS.setOrderId(order_id);
			 * notifyWMS.setNotNeedReqStrWms_status(wms_status);
			 * client.execute(notifyWMS, NotifyOrderStatusToWMSResp.class); }
			 */
			// 通知森锐掉卡
			ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
			String is_open = cacheUtil.getConfigInfo("RECYCLECARD_IS_OPEN");
			if (!StringUtil.isEmpty(is_open) && StringUtil.equals(is_open, "0")) {
				if (EcsOrderConsts.NOTIFY_SR_DK_VAL.equals(excep.getNotify_sr())) {
					RevertCardRequset request = new RevertCardRequset();
					request.setNotNeedReqStrOrderId(order_id);
					request.setRequest(InfConst.SR_RECYCLE_CARD);
					client.execute(request, RevertCardResponse.class);
				}
			}

		} else {
			// 订单系统 标记伪自动化异常
			abnormal_type = EcsOrderConsts.ORDER_ABNORMAL_TYPE_2;
		}

		// 通知总部自动化异常
		if (EcsOrderConsts.SEND_ZB_1.equals(orderExt.getSend_zb()) && !EcsOrderConsts.EXCEPTION_FROM_ZB.equals(exception_from) && !StringUtil.isEmpty(exceptionType)) {
			NotifyOrderAbnormalToZBRequest zbReq = new NotifyOrderAbnormalToZBRequest();
			zbReq.setNotNeedReqStrOrderId(order_id);
			zbReq.setNoticeType(EcsOrderConsts.Notice_Type_MarkException);
			zbReq.setExceptionType(exceptionType);
			zbReq.setExceptionDesc(exceptionDesc);
			client.execute(zbReq, NotifyOrderAbnormalToZBResponse.class);
		}
		if (StringUtil.isEmpty(exceptionType))
			abnormal_type = EcsOrderConsts.ORDER_ABNORMAL_TYPE_1;
		// 异常标记后处理add by wui
		// if(!StringUtils.isEmpty(excep.getException_desc())){
		// HashMap para = new HashMap();
		// para.put("order_id", busiCompRequest.getOrder_id());
		// para.put("error_msg",excep.getException_desc());
		// CommonDataFactory.getInstance().notifyBusiException(para);
		// }
		exceptionType = StringUtil.isEmpty(exceptionType) ? excep.getException_type() : exceptionType;
		exceptionDesc = StringUtil.isEmpty(excep.getException_desc()) ? exceptionDesc : excep.getException_desc();
		orderExt = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest();
		orderExt.setException_type(exceptionType);
		orderExt.setException_desc(exceptionDesc);
		orderExt.setAbnormal_type(abnormal_type);
		orderExt.setAbnormal_status(EcsOrderConsts.ABNORMAL_STATUS_1);
		orderExt.setIs_dirty(ConstsCore.IS_DIRTY_YES);
		orderExt.setDb_action(ConstsCore.DB_ACTION_UPDATE);
		orderExt.store();

		// 人工标记异常、自动化异常通知老系统
		String process_type = null;
		if (EcsOrderConsts.ORDER_ABNORMAL_TYPE_3.equals(abnormal_type) || EcsOrderConsts.ORDER_ABNORMAL_TYPE_2.equals(abnormal_type)) {// 自动化异常
			process_type = EcsOrderConsts.PROCESS_TYPE_2;
		} else if (EcsOrderConsts.ORDER_ABNORMAL_TYPE_1.equals(abnormal_type)) {
			process_type = EcsOrderConsts.PROCESS_TYPE_3;
		}
		if (!StringUtils.isEmpty(process_type)) {
			RuleFlowUtil.delRuleExeLogs(EcsOrderConsts.ORDER_STATUS_SYN_OLD_SYS_PLAN, EcsOrderConsts.ORDER_STATUS_SYN_OLD_SYS_RULE, order_id);
			PlanRuleTreeExeReq plan = new PlanRuleTreeExeReq();
			TacheFact fact = new TacheFact();
			fact.setOrder_id(order_id);
			fact.setSrc_tache_code(CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest().getFlow_trace_id());
			fact.setTar_tache_code(CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest().getFlow_trace_id());
			fact.setProcess_type(process_type);
			fact.setException_from(exception_from);
			fact.setException_type(exceptionType);
			fact.setException_desc(exceptionDesc);
			plan.setFact(fact);
			plan.setPlan_id(EcsOrderConsts.ORDER_STATUS_SYN_OLD_SYS_PLAN);
			PlanRuleTreeExeResp planResp = CommonDataFactory.getInstance().exePlanRuleTree(plan);
		}

		// 写日志
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		OrderHandleLogsReq logReq = new OrderHandleLogsReq();
		String flow_id = orderTree.getOrderExtBusiRequest().getFlow_id();
		String flowTraceId = orderTree.getOrderExtBusiRequest().getFlow_trace_id();
		logReq.setOrder_id(order_id);
		logReq.setFlow_id(flow_id);
		logReq.setFlow_trace_id(flowTraceId);
		logReq.setComments(orderExt.getException_desc());
		logReq.setHandler_type(EcsOrderConsts.ORDER_HANDLER_TYPE_EXCEPTION);
		logReq.setException_id(exception_id);
		logReq.setType_code(exceptionType);
		AdminUser user = new AdminUser();
		user = ManagerUtils.getAdminUser();
		if (null != user) {
			logReq.setOp_id(user.getUserid());
			logReq.setOp_name(user.getUsername());
		}
		this.ordFlowManager.insertOrderHandLog(logReq);

		// 异常记录
		OrderExceptionLogsReq exceptionLogReq = new OrderExceptionLogsReq();
		exceptionLogReq.setOrder_id(order_id);
		exceptionLogReq.setFlow_id(flow_id);
		exceptionLogReq.setFlow_trace_id(flowTraceId);
		if (null != user) {
			exceptionLogReq.setMark_op_id(user.getUserid());
			exceptionLogReq.setMark_op_name(user.getUsername());
		}
		if (EcsOrderConsts.ORDER_ABNORMAL_TYPE_2.equals(abnormal_type)) {
			abnormal_type = EcsOrderConsts.ORDER_ABNORMAL_TYPE_3;
		}
		exceptionLogReq.setAbnormal_type(abnormal_type);
		exceptionLogReq.setException_desc(exceptionDesc);
		exceptionLogReq.setException_type(exceptionType);
		this.ordFlowManager.insertOrderExceptionLog(exceptionLogReq);

		// 调用异常系统
		Utils.writeExp(order_id, exceptionType, exceptionDesc, (String) params.get("write_exp_flag"));

		// 可见性设置
		this.setOrderVisable(order_id);
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}

	@ZteMethodAnnontion(name = "异常恢复-兼容之前直接走业务组件没有调用日志", group_name = "通用组件", order = "9", page_show = true, path = "zteCommonTraceRule.restorationException")
	public BusiCompResponse restorationException(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		Map params = busiCompRequest.getQueryParams();
		String order_id = busiCompRequest.getOrder_id();
		String exception_from = Const.getStrValue(params, EcsOrderConsts.EXCEPTION_FROM);// 异常来源
		String exception_type = Const.getStrValue(params, EcsOrderConsts.EXCEPTION_TYPE);
		String exception_remark = Const.getStrValue(params, EcsOrderConsts.EXCEPTION_REMARK);
		PlanRuleTreeExeReq plan = new PlanRuleTreeExeReq();
		plan.setPlan_id(EcsOrderConsts.RESTORE_EXCEPTION_PLAN);
		ExceptionFact excep = new ExceptionFact();
		excep.setOrder_id(order_id);
		excep.setException_from(exception_from);
		excep.setException_type(exception_type);
		excep.setException_desc(exception_remark);
		plan.setFact(excep);
		plan.setDeleteLogs(true);
		PlanRuleTreeExeResp planResp = CommonDataFactory.getInstance().exePlanRuleTree(plan);
		BusiCompResponse busiResp = CommonDataFactory.getInstance().getRuleTreeresult(planResp);
		resp.setError_code(busiResp.getError_code());
		resp.setError_msg(busiResp.getError_msg());
		return resp;
	}

	@ZteMethodAnnontion(name = "异常恢复", group_name = "通用组件", order = "9", page_show = true, path = "zteCommonTraceRule.restorationExceptionRule")
	public BusiCompResponse restorationExceptionRule(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		Map params = busiCompRequest.getQueryParams();
		ExceptionFact excep = (ExceptionFact) params.get("fact");
		String order_id = excep.getOrder_id();
		String exception_from = excep.getException_from();
		OrderExtBusiRequest orderExt = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest();

		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());

		// 获取异常原因
		String flow_trace_id = orderExt.getFlow_trace_id();
		DcPublicInfoCacheProxyReq req = new DcPublicInfoCacheProxyReq();
		req.setStype(EcsOrderConsts.MARK_EXCEPTION_REL);
		req.setValue(flow_trace_id);
		req.setValue_from(EcsOrderConsts.VALUE_FROM_COL_2);
		DcPublicInfoCacheProxyResp response = client.execute(req, DcPublicInfoCacheProxyResp.class);
		String exceptionType = response.getDict_relation_value();
		String exceptionDesc = response.getDict_relation_value_desc();
		String abnormal_type = orderExt.getAbnormal_type();
		// 自动化模式需要通知WMS恢复异常
		if (EcsOrderConsts.ORDER_MODEL_01.equals(orderExt.getOrder_model()) && !EcsOrderConsts.EXCEPTION_FROM_WMS.equals(exception_from)
				&& EcsOrderConsts.IS_SEND_WMS_1.equals(orderExt.getIs_send_wms())) {
			NotifyOrderStatusToWMSReq notifyWMS = new NotifyOrderStatusToWMSReq();
			notifyWMS.setOrderId(order_id);
			notifyWMS.setNotNeedReqStrWms_status(EcsOrderConsts.ORDER_STATUS_WMS_4);
			NotifyOrderStatusToWMSResp notifyWMSResp = client.execute(notifyWMS, NotifyOrderStatusToWMSResp.class);
			if (EcsOrderConsts.WMS_INF_RESP_CODE_0000.equals(notifyWMSResp.getError_code())) {
				CommonTools.addBusiError(notifyWMSResp.getError_code(), notifyWMSResp.getError_msg());
			}
		}

		HasAutoExceptionReq autoreq = new HasAutoExceptionReq();
		autoreq.setOrder_id(order_id);
		IEcsOrdServices ecsOrdServices = SpringContextHolder.getBean("ecsOrdServices");
		HasAutoExceptionResp autoresp = ecsOrdServices.hasAutoException(autoreq);
		// 通知总部异常恢复,自动化,伪自动化需要通知总部异常恢复
		if ((EcsOrderConsts.ORDER_ABNORMAL_TYPE_2.equals(orderExt.getAbnormal_type()) || EcsOrderConsts.ORDER_ABNORMAL_TYPE_3.equals(orderExt.getAbnormal_type())
				|| EcsOrderConsts.IS_DEFAULT_VALUE.equals(autoresp.getHasAutoException())) && EcsOrderConsts.SEND_ZB_1.equals(orderExt.getSend_zb())
				&& !EcsOrderConsts.EXCEPTION_FROM_ZB.equals(exception_from) && !StringUtil.isEmpty(exceptionType)) {
			NotifyOrderAbnormalToZBRequest notifyZB = new NotifyOrderAbnormalToZBRequest();
			notifyZB.setNotNeedReqStrOrderId(order_id);
			notifyZB.setNoticeType(EcsOrderConsts.Notice_Type_RestorationException);
			notifyZB.setExceptionType(exceptionType);
			notifyZB.setExceptionDesc(StringUtil.isEmpty(excep.getException_desc()) ? exceptionDesc : excep.getException_desc());
			NotifyOrderAbnormalToZBResponse zbResp = client.execute(notifyZB, NotifyOrderAbnormalToZBResponse.class);
			if (!EcsOrderConsts.ZB_INF_RESP_CODE_0000.equals(zbResp.getRespCode())) {
				CommonTools.addBusiError(zbResp.getRespCode(), zbResp.getRespDesc());
			}
		}

		// 自动化、伪自动化异常恢复为正常订单
		orderExt.setException_type(ConstsCore.NULL_VAL);
		orderExt.setException_desc(ConstsCore.NULL_VAL);
		orderExt.setAbnormal_type(EcsOrderConsts.ORDER_ABNORMAL_TYPE_0);
		orderExt.setAbnormal_status(EcsOrderConsts.ABNORMAL_STATUS_0);
		orderExt.setIs_dirty(ConstsCore.IS_DIRTY_YES);
		orderExt.setDb_action(ConstsCore.DB_ACTION_UPDATE);
		orderExt.store();

		String trace_id = orderExt.getFlow_trace_id();
		String lock_user_id = "";
		String lock_user_name = "";
		List<OrderLockBusiRequest> orderLockRequestList = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderLockBusiRequests();
		for (OrderLockBusiRequest orderLockRequest : orderLockRequestList) {
			if (trace_id.equals(orderLockRequest.getTache_code())) {
				lock_user_id = orderLockRequest.getLock_user_id();
				lock_user_name = orderLockRequest.getLock_user_name();
				break;
			}
		}

		// 异常处理记录
		this.ordFlowManager.updateOrderExceptionLog(order_id);
		// 恢复异常后 异常系统标记订单已处理
		this.markExpProcced(order_id, lock_user_id);

		// 恢复异常通知老系统
		RuleFlowUtil.delRuleExeLogs(EcsOrderConsts.ORDER_STATUS_SYN_OLD_SYS_PLAN, EcsOrderConsts.ORDER_STATUS_SYN_OLD_SYS_RULE, order_id);
		PlanRuleTreeExeReq plan = new PlanRuleTreeExeReq();
		TacheFact fact = new TacheFact();
		fact.setOrder_id(order_id);
		fact.setSrc_tache_code(CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest().getFlow_trace_id());
		fact.setTar_tache_code(CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest().getFlow_trace_id());
		fact.setProcess_type(EcsOrderConsts.PROCESS_TYPE_5);
		fact.setException_from(exception_from);
		fact.setException_type(exceptionType);
		fact.setException_desc(exceptionDesc);
		fact.setAbnormal_type(abnormal_type);
		plan.setFact(fact);
		plan.setPlan_id(EcsOrderConsts.ORDER_STATUS_SYN_OLD_SYS_PLAN);
		PlanRuleTreeExeResp planResp = CommonDataFactory.getInstance().exePlanRuleTree(plan);

		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}

	// 调用异常单系统标记已处理
	public void markExpProcced(String order_id, String user_id) {
		try {
			ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
			String is_exception_run = cacheUtil.getConfigInfo(EcsOrderConsts.IS_EXCEPTION_RUN);// 是否启用异常系统
			if (!StringUtil.isEmpty(is_exception_run) && new Integer(is_exception_run).intValue() != 0) {// 异常系统启动标识
				OrderExpMarkProcessedReq req = new OrderExpMarkProcessedReq();
				req.setRel_obj_id(order_id);
				req.setRel_obj_type("order");
				req.setDeal_result("恢复异常业务处理");
				req.setDeal_staff_no(user_id);
				req.setRequest_time(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
				String exe_type = MqEnvGroupConfigSetting.ORD_EXP_EXEC;// 调用方式
																		// m/d
																		// m是走mq
																		// d是走dubbo
				if (ConstsCore.DECOUPLING_EXEC_D.equals(exe_type)) {
					markExpProccedByDubbo(req);
				} else {
					markExpProccedByMq(req);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void markExpProccedByDubbo(OrderExpMarkProcessedReq req) {
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		OrderExpMarkProcessedResp response = client.execute(req, OrderExpMarkProcessedResp.class);
	}

	private ZteResponse markExpProccedByMq(OrderExpMarkProcessedReq req) {
		AsynExecuteMsgWriteMqReq mqReq = new AsynExecuteMsgWriteMqReq();
		mqReq.setService_code(req.getApiMethodName());
		mqReq.setVersion(ConstsCore.DUBBO_DEFAULT_VERSION);
		mqReq.setZteRequest((ZteRequest) req);
		mqReq.setConsume_env_code(com.ztesoft.common.util.BeanUtils.getHostEnvCodeByEnvStatus(ConstsCore.MACHINE_EVN_CODE_ECSORD_EXP));
		IOrderQueueBaseManager orderQueueBaseManager = SpringContextHolder.getBean("orderQueueBaseManager");
		return orderQueueBaseManager.asynExecuteMsgWriteMq(mqReq);
	}

	@ZteMethodAnnontion(name = "订单可见性设置", group_name = "通用组件", order = "10", page_show = true, path = "zteCommonTraceRule.setOrderVisible")
	public BusiCompResponse setOrderVisible(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		this.setOrderVisable(order_id);
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}

	@ZteMethodAnnontion(name = "写卡完成校验", group_name = "通用组件", order = "11", page_show = true, path = "zteCommonTraceRule.writeCardFinshVali")
	public BusiCompResponse writeCardFinshVali(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		Boolean can_run = false;
		OrderExtBusiRequest orderExt = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest();
		if (EcsOrderConsts.ORDER_ABNORMAL_TYPE_2.equals(orderExt.getAbnormal_type())) {
			can_run = true;
			this.restorationException(busiCompRequest); // 恢复异常
		}
		// 写卡成功继续流转
		String writeCardResult = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.WRITE_CARD_RESULT);
		if (EcsOrderConsts.WRITE_CARD_RESULT_SUCC.equals(writeCardResult)) {
			can_run = true;
		}
		if (!can_run)
			CommonTools.addBusiError("-99999", "写卡完成校验失败");
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}

	@ZteMethodAnnontion(name = "信息稽核校验", group_name = "通用组件", order = "12", page_show = true, path = "zteCommonTraceRule.qualityFinishVali")
	public BusiCompResponse qualityFinishVali(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		String prodAuditStatus = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.PROD_AUDIT_STATUS);
		if (!EcsOrderConsts.PROD_AUDIT_STATUS_1.equals(prodAuditStatus)) {
			CommonTools.addBusiError("-99999", "信息稽核不通过");
		}
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}

	@ZteMethodAnnontion(name = "DT卡信息自动稽核校验", group_name = "通用组件", order = "12", page_show = true, path = "zteCommonTraceRule.qualityFinishValiDT")
	public BusiCompResponse qualityFinishValiDT(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.PROD_AUDIT_STATUS }, new String[] { "1" });
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}

	@ZteMethodAnnontion(name = "接收回单完成", group_name = "通用组件", order = "13", page_show = true, path = "zteCommonTraceRule.recBackFinsh")
	public BusiCompResponse recBackFinsh(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		Map params = busiCompRequest.getQueryParams();
		String triggerType = Const.getStrValue(params, EcsOrderConsts.TRACE_TRIGGER_TYPE);
		String order_id = busiCompRequest.getOrder_id();
		// 页面触发继续流转
		if (!EcsOrderConsts.TRACE_TRIGGER_PAGE.equals(triggerType)) {
			// 接口触发处理
			TacheFact fact = (TacheFact) params.get("fact");
			@SuppressWarnings("unused")
			StateSynchronizationToSystemRequest req = (StateSynchronizationToSystemRequest) fact.getRequest();
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.ZB_STATUS }, new String[] { EcsOrderConsts.ZB_ORDER_STATE_N10 });
		}
		OrderBusiRequest orderBusi = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderBusiRequest();
		orderBusi.setStatus(EcsOrderConsts.DIC_ORDER_STATUS_10);

		orderBusi.setIs_dirty(ConstsCore.IS_DIRTY_YES);
		orderBusi.setDb_action(ConstsCore.DB_ACTION_UPDATE);
		orderBusi.store();
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}

	@ZteMethodAnnontion(name = "退单申请", group_name = "通用组件", order = "16", page_show = true, path = "zteCommonTraceRule.ordRefundApply")
	public BusiCompResponse ordRefundApply(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		String syn_ord_zb = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.SYN_ORD_ZB);
		// if(EcsOrderConsts.SYN_ORD_ZB_1.equals(syn_ord_zb)){ // ZX add
		// 2016年3月18日 17:43:19 start
		// //如果已经同步给总部侧通知总部
		// BusiDealResult result = commonManager.setOrdRefund(busiCompRequest);
		// if(EcsOrderConsts.ZB_INF_RESP_CODE_0000.equals(result.getError_code())){
		// OrderExtBusiRequest orderExt =
		// CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest();//退单申请，只登记不走接口
		// orderExt.setRefund_deal_type(EcsOrderConsts.REFUND_DEAL_TYPE_02);
		// orderExt.setRefund_status(EcsOrderConsts.REFUND_STATUS_08);
		// orderExt.setIs_dirty(ConstsCore.IS_DIRTY_YES);
		// orderExt.setDb_action(ConstsCore.DB_ACTION_UPDATE);
		// orderExt.store();
		//
		// //返销
		//// String subRefundType =
		// CommonDataFactory.getInstance().getAttrFieldValue(order_id,
		// AttrConsts.SUB_REFUND_DEAL_TYPE);
		//// if(!EcsOrderConsts.NEW_SHOP_OFFLINE_DEAL.equals(subRefundType)){
		////// //资金返销
		//// PlanRuleTreeExeReq plan = new PlanRuleTreeExeReq();
		//// plan.setPlan_id(EcsOrderConsts.AGENT_REFUND_PLAN_ID); //资金返销
		//// TacheFact factFee = new TacheFact();
		//// factFee.setOrder_id(order_id);
		//// plan.setFact(factFee);
		//// plan.setDeleteLogs(true);
		//// CommonDataFactory.getInstance().exePlanRuleTree(plan);
		//// }
		// resp.setError_code("0");
		// resp.setError_msg("执行成功");
		// }else{
		// CommonTools.addBusiError(result.getError_code(),
		// result.getError_msg());
		// }
		// }else{// ZX add 2016年3月18日 17:42:14 end
		OrderExtBusiRequest orderExt = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest();// 退单申请，只登记不走接口
		orderExt.setRefund_deal_type(EcsOrderConsts.REFUND_DEAL_TYPE_02);
		orderExt.setRefund_status(EcsOrderConsts.REFUND_STATUS_08);
		orderExt.setIs_dirty(ConstsCore.IS_DIRTY_YES);
		orderExt.setDb_action(ConstsCore.DB_ACTION_UPDATE);
		orderExt.store();
		resp.setError_code("0");
		resp.setError_msg("执行成功");

		// 记录退单原因
		// CommonDataFactory.getInstance().updateAttrFieldValue(order_id,
		// new String[]{AttrConsts.REFUND_DESC},
		// new String[]{"退单申请原因：默认"});

		// } // ZX add 2016年3月18日 17:43:39 end
		// 退單申請通知老系統
		/*
		 * RuleFlowUtil.delRuleExeLogs(EcsOrderConsts.
		 * ORDER_STATUS_SYN_OLD_SYS_PLAN,
		 * EcsOrderConsts.ORDER_STATUS_SYN_OLD_SYS_RULE, order_id);
		 * PlanRuleTreeExeReq plan = new PlanRuleTreeExeReq(); TacheFact fact =
		 * new TacheFact(); fact.setOrder_id(order_id);
		 * fact.setSrc_tache_code(CommonDataFactory.getInstance().getOrderTree(
		 * order_id).getOrderExtBusiRequest().getFlow_trace_id());
		 * fact.setTar_tache_code("T"); plan.setFact(fact);
		 * plan.setPlan_id(EcsOrderConsts.ORDER_STATUS_SYN_OLD_SYS_PLAN);
		 * PlanRuleTreeExeResp planResp =
		 * CommonDataFactory.getInstance().exePlanRuleTree(plan);
		 */
		return resp;
	}

	@ZteMethodAnnontion(name = "退单确认", group_name = "通用组件", order = "17", page_show = true, path = "zteCommonTraceRule.ordRefundConfirm")
	public BusiCompResponse ordRefundConfirm(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		Map params = busiCompRequest.getQueryParams();
		TacheFact fact = (TacheFact) params.get("fact");
		StateSynchronizationToSystemRequest stateReq = (StateSynchronizationToSystemRequest) fact.getRequest();
		OrderExtBusiRequest orderExt = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest();
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);

		String comments = "";
		String handler_type = "";
		String type_code = "";

		String col3 = orderTree.getCol3();
		if (null != col3 && EcsOrderConsts.TRACE_TRIGGER_INF.equals(col3) && stateReq != null) {// 接口触发
			String stateTag = stateReq.getNotNeedReqStrzb_status();
			if (stateTag.equals(EcsOrderConsts.STATE_CHG_RETURN)) {// 已退单[STATE_CHG_RETURN]（退单审核通过，并完成退单）
				orderExt.setRefund_status(EcsOrderConsts.REFUND_STATUS_01);
				CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.REFUND_STATUS }, new String[] { EcsOrderConsts.REFUND_STATUS_01 });// 更新属性

				comments = "退单确认";
				handler_type = com.ztesoft.ibss.common.util.Const.ORDER_HANDLER_TYPE_RETURNED;
				type_code = EcsOrderConsts.REFUND_STATUS_01;
				//
				// //保存退单原因
				// String olddesc =
				// CommonDataFactory.getInstance().getAttrFieldValue(order_id,
				// AttrConsts.REFUND_DESC);
				// comments = olddesc+";"+comments;
				// CommonDataFactory.getInstance().updateAttrFieldValue(order_id,
				// new String[]{AttrConsts.REFUND_DESC},
				// new String[]{"确认退单原因：接口"+comments});
				//
			} else if (stateTag.equals(EcsOrderConsts.STATE_CHG_REJECT)) {// 退单申请驳回[STATE_CHG_REJECT]
				orderExt.setRefund_status(EcsOrderConsts.REFUND_STATUS_00);
				CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.REFUND_STATUS }, new String[] { EcsOrderConsts.REFUND_STATUS_00 });// 更新属性

				comments = "退单驳回";
				handler_type = com.ztesoft.ibss.common.util.Const.ORDER_HANDLER_TYPE_CANCELRETURNED;
				type_code = EcsOrderConsts.REFUND_STATUS_00;

				// 保存退单原因
				// String olddesc =
				// CommonDataFactory.getInstance().getAttrFieldValue(order_id,
				// AttrConsts.REFUND_DESC);
				// comments = olddesc+";"+comments;
				// CommonDataFactory.getInstance().updateAttrFieldValue(order_id,
				// new String[]{AttrConsts.REFUND_DESC},
				// new String[]{"退单驳回原因：接口"+comments});
			} else {// 未定义编码
				// orderExt.setRefund_status(EcsOrderConsts.REFUND_STATUS_01);
				// CommonDataFactory.getInstance().updateAttrFieldValue(order_id,
				// new String[]{AttrConsts.REFUND_STATUS}, new
				// String[]{EcsOrderConsts.REFUND_STATUS_00});//更新属性

				comments = "退单未定义编码";
				handler_type = com.ztesoft.ibss.common.util.Const.ORDER_HANDLER_TYPE_DEFAULT;
				type_code = "none";
			}
		} else {
			orderExt.setRefund_status(EcsOrderConsts.REFUND_STATUS_01);
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.REFUND_STATUS }, new String[] { EcsOrderConsts.REFUND_STATUS_01 });// 更新属性

			if (StringUtils.equals(orderExt.getPlat_type(), EcsOrderConsts.PLAT_TYPE_10061)) {
				String flowTraceId = orderTree.getOrderExtBusiRequest().getFlow_trace_id();
				if (StringUtils.equals(flowTraceId, EcsOrderConsts.DIC_ORDER_NODE_J) || StringUtils.equals(flowTraceId, EcsOrderConsts.DIC_ORDER_NODE_L)) {
					comments = "WMS退货入库确认退单";
				} else {
					comments = "SAP退货拦截成功";
				}
			} else {
				comments = "手动退单确认";
			}
			String refundDesc = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.REFUND_DESC);
			// refundDesc ="退单申请原因|测试的";
			if (!StringUtils.isEmpty(refundDesc)) {
				String[] list = refundDesc.split("[|]");
				for (int i = 0; i < list.length; i++) {
					if (list[i].contains(EcsOrderConsts.REFUND_CONFIRM_DESC)) {
						comments = comments + "|" + refundDesc;
					}
				}
			}
			handler_type = com.ztesoft.ibss.common.util.Const.ORDER_HANDLER_TYPE_RETURNED;
			type_code = EcsOrderConsts.REFUND_STATUS_01;
		}
		orderExt.setLast_deal_time(Consts.SYSDATE);
		orderExt.setIs_dirty(ConstsCore.IS_DIRTY_YES);
		orderExt.setDb_action(ConstsCore.DB_ACTION_UPDATE);
		orderExt.store();

		// 淘宝订单/华盛来源订单退单确认就默认线上退款成功
		if (CommonDataFactory.getInstance().isTbOrder(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ORDER_FROM))
				|| StringUtils.equals(orderExt.getPlat_type(), EcsOrderConsts.PLAT_TYPE_10061)) {
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.BSS_REFUND_STATUS }, new String[] { EcsOrderConsts.BSS_REFUND_STATUS_ONLINE_SUCC });
		}

		// 写日志
		OrderHandleLogsReq req = new OrderHandleLogsReq();
		String flow_id = orderTree.getOrderExtBusiRequest().getFlow_id();
		String flowTraceId = orderTree.getOrderExtBusiRequest().getFlow_trace_id();
		req.setOrder_id(order_id);
		req.setFlow_id(flow_id);
		req.setFlow_trace_id(flowTraceId);
		req.setComments(comments);
		req.setHandler_type(handler_type);
		req.setType_code(type_code);
		AdminUser user = ManagerUtils.getAdminUser();
		if (null != user) {
			req.setOp_id(user.getUserid());
			req.setOp_name(user.getUsername());
		} else {
			req.setOp_id("1");
			req.setOp_name("admin");
		}
		this.ordFlowManager.insertOrderHandLog(req);

		// 退單確認通知老系統
		/*
		 * RuleFlowUtil.delRuleExeLogs(EcsOrderConsts.
		 * ORDER_STATUS_SYN_OLD_SYS_PLAN,
		 * EcsOrderConsts.ORDER_STATUS_SYN_OLD_SYS_RULE, order_id);
		 * PlanRuleTreeExeReq plan = new PlanRuleTreeExeReq(); TacheFact fact2 =
		 * new TacheFact(); fact2.setOrder_id(order_id);
		 * fact2.setSrc_tache_code("T"); fact2.setTar_tache_code("M");
		 * plan.setFact(fact2);
		 * plan.setPlan_id(EcsOrderConsts.ORDER_STATUS_SYN_OLD_SYS_PLAN);
		 * PlanRuleTreeExeResp planResp =
		 * CommonDataFactory.getInstance().exePlanRuleTree(plan);
		 */

		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}

	@ZteMethodAnnontion(name = "BSS返销", group_name = "通用组件", order = "18", page_show = true, path = "zteCommonTraceRule.bssBuyBack")
	public BusiCompResponse bssBuyBack(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		OrderExtBusiRequest orderExt = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest();
		orderExt.setBss_cancel_status(EcsOrderConsts.BSS_CANCEL_STATUS_1);
		orderExt.setRefund_status(EcsOrderConsts.REFUND_STATUS_02);
		orderExt.setBss_reset_time("sysdate");
		orderExt.setIs_dirty(ConstsCore.IS_DIRTY_YES);
		orderExt.setDb_action(ConstsCore.DB_ACTION_UPDATE);
		orderExt.store();
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}

	@ZteMethodAnnontion(name = "ESS返销", group_name = "通用组件", order = "19", page_show = true, path = "zteCommonTraceRule.essBuyBack")
	public BusiCompResponse essBuyBack(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		OrderExtBusiRequest orderExt = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest();
		orderExt.setEss_cancel_status(EcsOrderConsts.ESS_CANCEL_STATUS_1);
		orderExt.setRefund_status(EcsOrderConsts.REFUND_STATUS_03);
		orderExt.setEss_reset_time("sysdate");
		orderExt.setIs_dirty(ConstsCore.IS_DIRTY_YES);
		orderExt.setDb_action(ConstsCore.DB_ACTION_UPDATE);
		orderExt.store();
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}

	/**
	 * ZX add 2016-02-01 PM
	 * 
	 * @param busiCompRequest
	 * @return
	 * @throws ApiBusiException
	 */
	@ZteMethodAnnontion(name = "退款规则前", group_name = "通用组件", order = "53", page_show = true, path = "zteCommonTraceRule.beforeBssRefund")
	public BusiCompResponse beforeBssRefund(BusiCompRequest busiCompRequest) throws ApiBusiException {
		String order_id = busiCompRequest.getOrder_id();
		BusiCompResponse busiCompResponse = new BusiCompResponse();
		String order_from = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ORDER_FROM);
		if (order_from.equals(EcsOrderConsts.ORDER_FROM_10003)) {
			Map queryParams = busiCompRequest.getQueryParams();
			// refund_flag : 1-退款线上处理， 2-退款线下处理
			String refund_flag = queryParams.get(EcsOrderConsts.REFUND_FLAG_KEY).toString();
			String respCode = "";
			StateSynToZBRequest req = new StateSynToZBRequest();
			ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
			StateSynToZBResponse stateSynToZBResponse = new StateSynToZBResponse();
			if (refund_flag.equals(EcsOrderConsts.REFUND_FLAG_ONRETURN)) {
				req.setNotNeedReqStrOrderId(order_id);
				req.setNotNeedReqStrStateTag(EcsOrderConsts.STATE_CHG_ONRETURN);
				req.setNotNeedReqStrStateChgReason(EcsOrderConsts.STATE_CHG_REASON_CANLODR);
				req.setNotNeedReqStrStateChgDesc("线上退款");
			} else if (refund_flag.equals(EcsOrderConsts.REFUND_FLAG_OFRETURN)) {
				req.setNotNeedReqStrStateTag(EcsOrderConsts.STATE_CHG_OFRETURN);
				req.setNotNeedReqStrStateChgReason(EcsOrderConsts.STATE_CHG_REASON_CANLODR);
				req.setNotNeedReqStrStateChgDesc("线下退款");
				req.setNotNeedReqStrOrderId(order_id);
			}
			stateSynToZBResponse = client.execute(req, StateSynToZBResponse.class);
			respCode = stateSynToZBResponse.getRespCode();
			if (respCode.equals(EcsOrderConsts.ZB_INF_RESP_CODE_0000)) {
				try {
					Map m = AttrUtils.getInstance().getRefundPlanInfo(order_id);
					String plan_mode = m.get("plan_id").toString();
					String rule_mode = m.get("refund_rule_id").toString();

					OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
					TacheFact fact = new TacheFact();
					fact.setOrder_id(order_id);
					RuleTreeExeResp ruleTreeExeResp = RuleFlowUtil.executeRuleTree(plan_mode, rule_mode, fact, true, true, EcsOrderConsts.DEAL_FROM_PAGE);
					if (ruleTreeExeResp != null && "0".equals(ruleTreeExeResp.getError_code())) {
						if (refund_flag.equals(EcsOrderConsts.REFUND_FLAG_OFRETURN)) {
							logger.info("退款状态跟踪====R1====order_id：" + order_id + ",更新前：" + CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.BSS_REFUND_STATUS));

							CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.BSS_REFUND_STATUS },
									new String[] { EcsOrderConsts.BSS_REFUND_STATUS_ONLINE_SUCC });

							logger.info("退款状态跟踪====R1===order_id：" + order_id + ",更新后：" + CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.BSS_REFUND_STATUS));

						} else if (refund_flag.equals(EcsOrderConsts.REFUND_FLAG_ONRETURN)) {
							logger.info("退款状态跟踪====R2====order_id：" + order_id + ",更新前：" + CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.BSS_REFUND_STATUS));

							CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.BSS_REFUND_STATUS },
									new String[] { EcsOrderConsts.BSS_REFUND_STATUS_ONLINE_SUCC });

							logger.info("退款状态跟踪====R2====order_id：" + order_id + ",更新后：" + CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.BSS_REFUND_STATUS));
						}
						// 写日志
						AdminUser user = ManagerUtils.getAdminUser();
						OrderHandleLogsReq orderHandleLogsReq = new OrderHandleLogsReq();
						String flow_id = orderTree.getOrderExtBusiRequest().getFlow_id();
						String flowTraceId = orderTree.getOrderExtBusiRequest().getFlow_trace_id();
						orderHandleLogsReq.setOrder_id(order_id);
						orderHandleLogsReq.setFlow_id(flow_id);
						orderHandleLogsReq.setFlow_trace_id(flowTraceId);
						orderHandleLogsReq.setComments("退款处理");
						orderHandleLogsReq.setHandler_type(EcsOrderConsts.ORDER_HANDLER_TYPE_RETURNED);
						orderHandleLogsReq.setType_code(EcsOrderConsts.REFUND_STATUS_04);
						orderHandleLogsReq.setOp_id(user.getUserid());
						orderHandleLogsReq.setOp_name(user.getUsername());
						ordFlowManager.insertOrderHandLog(orderHandleLogsReq);
					}
				} catch (Exception e) {
					e.printStackTrace();
					busiCompResponse.setError_code("1");
					busiCompResponse.setError_msg("退款规则执行失败！");
				}
				busiCompResponse.setError_code("0");
				busiCompResponse.setError_msg("退款成功");
			} else {
				busiCompResponse.setError_code("-1");
				busiCompResponse.setError_msg("手动退款按钮，退款失败！");
				if (refund_flag.equals(EcsOrderConsts.REFUND_FLAG_OFRETURN)) {
					logger.info("退款状态跟踪====R3====order_id：" + order_id + ",更新前：" + CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.BSS_REFUND_STATUS));

					CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.BSS_REFUND_STATUS }, new String[] { EcsOrderConsts.BSS_REFUND_STATUS_OFFLINE_FAIL });

					logger.info("退款状态跟踪====R3====order_id：" + order_id + ",更新后：" + CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.BSS_REFUND_STATUS));

				} else if (refund_flag.equals(EcsOrderConsts.REFUND_FLAG_ONRETURN)) {
					logger.info("退款状态跟踪====R4====order_id：" + order_id + ",更新前：" + CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.BSS_REFUND_STATUS));

					CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.BSS_REFUND_STATUS }, new String[] { EcsOrderConsts.BSS_REFUND_STATUS_ONLINE_FAIL });

					logger.info("退款状态跟踪====R4====order_id：" + order_id + ",更新后：" + CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.BSS_REFUND_STATUS));

				}
			}
		}
		return busiCompResponse;
	}

	@ZteMethodAnnontion(name = "BSS退款", group_name = "通用组件", order = "20", page_show = true, path = "zteCommonTraceRule.bssRefund")
	public BusiCompResponse bssRefund(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		String refundResults = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.BSS_REFUND_STATUS);
		if (StringUtils.equals(refundResults, EcsOrderConsts.BSS_REFUND_STATUS_ONLINE_SUCC) || StringUtils.equals(refundResults, EcsOrderConsts.BSS_REFUND_STATUS_OFFLINE_SUCC)) {
			logger.info("退款状态跟踪====R11====order_id：" + order_id + ",更新前：" + CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.BSS_REFUND_STATUS));

			OrderExtBusiRequest orderExt = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest();
			orderExt.setRefund_status(EcsOrderConsts.REFUND_STATUS_04);
			orderExt.setRefund_time("sysdate");
			orderExt.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			orderExt.setDb_action(ConstsCore.DB_ACTION_UPDATE);
			orderExt.store();

			resp.setError_code("0");
			resp.setError_msg("执行成功");

			logger.info("退款状态跟踪====R11====order_id：" + order_id + ",更新后：" + CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.BSS_REFUND_STATUS));

		} else if (StringUtils.equals(refundResults, EcsOrderConsts.BSS_REFUND_STATUS_OFFLINE_FAIL)) {
			// 状态失败的，规则卡住，等待执行
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.REFUND_RULE_EXECUTE }, new String[] { EcsOrderConsts.IS_DEFAULT_VALUE });
			CommonTools.addBusiError("-1", "线下退款失败");
		} else if (StringUtils.equals(refundResults, EcsOrderConsts.BSS_REFUND_STATUS_ONLINE_FAIL)) {
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.REFUND_RULE_EXECUTE }, new String[] { EcsOrderConsts.IS_DEFAULT_VALUE });
			// 状态失败的，规则卡住，等待执行
			CommonTools.addBusiError("-1", "线上退款失败");
		} else {
			// 规则卡住，等待执行
			// 同时，初始化退款状态，该状态可以用来判断是该规则的以来规则是否都已经完成，从而控制商城退款状态接收后的后续动作
			// logger.info("退款状态跟踪====R9====order_id："+order_id+",更新前："+CommonDataFactory.getInstance().getAttrFieldValue(order_id,
			// AttrConsts.BSS_REFUND_STATUS));

			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.REFUND_RULE_EXECUTE }, new String[] { EcsOrderConsts.IS_DEFAULT_VALUE });
			// logger.info("退款状态跟踪====R9====order_id："+order_id+",更新后："+CommonDataFactory.getInstance().getAttrFieldValue(order_id,
			// AttrConsts.BSS_REFUND_STATUS));

			CommonTools.addBusiError("-1", "待退款");
		}
		return resp;
	}

	@ZteMethodAnnontion(name = "设置已退单", group_name = "通用组件", order = "21", page_show = true, path = "zteCommonTraceRule.setOrdRefund")
	public BusiCompResponse setOrdRefund(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		OrderBusiRequest order = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderBusiRequest();
		order.setStatus(EcsOrderConsts.DIC_ORDER_STATUS_14);
		order.setIs_dirty(ConstsCore.IS_DIRTY_YES);
		order.setDb_action(ConstsCore.DB_ACTION_UPDATE);
		order.store();
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}

	/**
	 * 获取总部写卡数据
	 * 
	 * @throws ApiBusiException
	 */
	@ZteMethodAnnontion(name = "获取总部写卡数据", group_name = "通用组件", order = "22", page_show = true, path = "zteCommonTraceRule.writeCardData")
	public BusiCompResponse writeCardData(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();

		String trace_id = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest().getFlow_trace_id();
		if (!EcsOrderConsts.DIC_ORDER_NODE_X.equals(trace_id)) {// 重新写卡失败:订单环节非写卡环节
			CommonTools.addBusiError("-1", "订单系统非写卡环节不允许获取写卡脚本数据，请检查是否开户完成。");
		}

		BusiDealResult dealResult = ordWriteCardTacheManager.getWriteCardInfFromZB(order_id);
		if (!dealResult.getError_code().equals(EcsOrderConsts.ZB_INF_RESP_CODE_0000))
			CommonTools.addBusiError(dealResult.getError_code(), dealResult.getError_msg());
		resp.setResponse(dealResult.getResponse());
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;

	}

	/**
	 * 通知信息到新商城
	 * 
	 * @param busiCompRequest
	 * @return
	 * @throws ApiBusiException
	 */
	@ZteMethodAnnontion(name = "通知信息到新商城", group_name = "通用组件", order = "23", page_show = true, path = "zteCommonTraceRule.notifyWYG")
	public BusiCompResponse notifyWYG(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		Map params = busiCompRequest.getQueryParams();
		TacheFact fact = (TacheFact) params.get("fact");
		StatuSynchReq statuSyn = (StatuSynchReq) fact.getRequest();
		BusiDealResult dealResult = ordWriteCardTacheManager.notifyWYG(statuSyn);
		// 开户环节，通知接口失败不用抛出异常(不抛出异常流程继续流转)——在业务组件调用层控制;
		// 写卡环节，通知接口失败，需抛出异常(中断流程流转)
		if (EcsOrderConsts.DIC_ORDER_NODE_X.equals(statuSyn.getTRACE_CODE()) && !dealResult.getError_code().equals(EcsOrderConsts.BUSI_DEAL_RESULT_0000))
			CommonTools.addBusiError(dealResult.getError_code(), dealResult.getError_msg());
		//
		resp.setResponse(dealResult.getResponse());
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}

	/**
	 * 订单同步旧
	 * 
	 * @throws ApiBusiException
	 */
	@ZteMethodAnnontion(name = "订单信息同步商品系统（旧）", group_name = "通用组件", order = "22", page_show = true, path = "zteCommonTraceRule.orderSyOld")
	public BusiCompResponse orderSyOld(BusiCompRequest busiCompRequest) throws ApiBusiException {
		// 获取规则对象
		logger.info("旧订单开始标准化========================>");
		BusiCompResponse resp = new BusiCompResponse();
		Map params = busiCompRequest.getQueryParams();
		OrderSyFact fact = (OrderSyFact) params.get("fact");
		CoQueue coQueueReq = (CoQueue) fact.getRequest();
		coQueueReq.setUserSessionId(busiCompRequest.getUserSessionId());
		orderStanding(coQueueReq);
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;

	}

	/**
	 * 订单同步新,设置service_code等
	 * 
	 * @throws ApiBusiException
	 */
	@ZteMethodAnnontion(name = "订单信息同步商品系统（新）", group_name = "通用组件", order = "22", page_show = true, path = "zteCommonTraceRule.orderSyNew")
	public BusiCompResponse orderSyNew(BusiCompRequest busiCompRequest) throws ApiBusiException {

		// add by wui方便测试，buyer_message 广东沃云购2.0测试单勿动生产订单不做订单 归集，需要测试环境搬迁处理
		BusiCompResponse resp = new BusiCompResponse();
		resp.setError_code("0");
		resp.setError_msg("执行成功");

		Map params = busiCompRequest.getQueryParams();
		Outer outer = (Outer) params.get("outer");
		// add by wui广东沃云购2.0测试单勿动 备注 测试单，通过数据归集订单
		String receiver_name = outer.getReceiver_name();
		if (EcsOrderConsts.BUYER_MESSAGE_CONSTS_FTEST.equals(receiver_name))
			return resp;
		String buyer_message = outer.getBuyer_message();
		if (StringUtil.isEmpty(buyer_message))
			buyer_message = outer.getService_remarks();
		if (EcsOrderConsts.BUYER_MESSAGE_CONSTS_FTEST.equals(buyer_message))
			return resp;

		OrderSyFact fact = (OrderSyFact) params.get("fact");
		CoQueue coQueueReq = (CoQueue) fact.getRequest();
		coQueueReq.setUserSessionId(busiCompRequest.getUserSessionId());
		// 归档数据处理
		// ecsOrdManager.newOrderArchivesForStanding(coQueueReq);
		// 订单标准化
		orderStanding(coQueueReq);

		return resp;
	}

	/**
	 * 订单标准化流程开启
	 * 
	 * @param coQueueReq
	 */
	public void orderStanding(final CoQueue coQueue) {
		// add by wui修改为异步方式处理
		// TaskThreadPool taskThreadPool = new TaskThreadPool(new Task(coQueue)
		// {
		// public ZteResponse execute(ZteRequest coQueueReq) {
		// 全量查询订单树'
		// CoQueue coQueue = (CoQueue)coQueueReq;
		CoQueueRuleReq coQueueRuleReq = new CoQueueRuleReq();
		coQueueRuleReq.setCo_id(coQueue.getCo_id());
		coQueueRuleReq.setService_code(coQueue.getService_code());
		coQueueRuleReq.setSource_from(coQueue.getSource_from());
		coQueueRuleReq.setObject_id(coQueue.getObject_id());
		coQueueRuleReq.setCoQueue(coQueue);
		try {
			// 触发规则标准化规则
			GlobalThreadLocalHolder.getInstance().clear();
			coQueueRuleReq.setUserSessionId(coQueue.getUserSessionId());
			RuleInvoker.coQueue(coQueueRuleReq);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// return new ZteResponse();
		// }
		// });
		// ThreadPoolFactory.getOrderStandingExecutor().execute(taskThreadPool);
	}

	/**
	 * 订单同步新,设置service_code等
	 * 
	 * @throws ApiBusiException
	 */
	@ZteMethodAnnontion(name = "订单分流匹配", group_name = "通用组件", order = "22", page_show = true, path = "zteCommonTraceRule.orderSeparteFlow")
	public BusiCompResponse OrderSeparteFlow(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}

	@ZteMethodAnnontion(name = "物流公司模板匹配", group_name = "通用组件", order = "22", page_show = true, path = "zteCommonTraceRule.logoCompanyModelMatch")
	public BusiCompResponse logoCompanyModelMatch(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		Map params = busiCompRequest.getQueryParams();
		LogisticsFact fact = (LogisticsFact) params.get("fact");
		resp.setFact(fact);
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}

	@ZteMethodAnnontion(name = "物流公司匹配", group_name = "通用组件", order = "22", page_show = true, path = "zteCommonTraceRule.logoCompanyMatch")
	public BusiCompResponse logoCompanyMatch(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		Map params = busiCompRequest.getQueryParams();
		LogisticsFact fact = (LogisticsFact) params.get("fact");
		String company_code = fact.getCompany_code();
		OrderDeliveryBusiRequest req = CommonDataFactory.getInstance().getDeliveryBusiRequest(order_id, EcsOrderConsts.LOGIS_NORMAL);
		req.setShipping_company(company_code);
		req.setIs_dirty(ConstsCore.IS_DIRTY_YES);
		req.setDb_action(ConstsCore.DB_ACTION_UPDATE);
		req.store();
		CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.SHIPPING_COMPANY }, new String[] { company_code });
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}

	/**
	 * 订单返销完成(余额转移通知BSS)
	 */
	@ZteMethodAnnontion(name = "订单返销完成(余额转移通知BSS)", group_name = "通用组件", order = "23", page_show = true, path = "zteCommonTraceRule.orderBuybackInform")
	public BusiCompResponse orderBuybackInform(BusiCompRequest busiCompRequest) throws ApiBusiException {
		// this.init();
		BusiCompResponse resp = new BusiCompResponse();
		// 0-开户 1-返销
		BusiDealResult dealResult = commonManager.orderAccountOrBuybackInform(busiCompRequest.getOrder_id(), EcsOrderConsts.ORDER_BUYBACK);
		if (!EcsOrderConsts.INF_RESP_CODE_0000.equals(dealResult.getError_code()))
			CommonTools.addBusiError(dealResult.getError_code(), dealResult.getError_msg());
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}

	@ZteMethodAnnontion(name = "物流派工号接收", group_name = "通用组件", order = "24", page_show = true, path = "zteCommonTraceRule.lockOrderByWlUser")
	public BusiCompResponse lockOrderByWlUser(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		Map params = busiCompRequest.getQueryParams();
		TacheFact fact = (TacheFact) params.get("fact");
		DispatchingNumReceivingNDRequset request = (DispatchingNumReceivingNDRequset) fact.getRequest();
		resp = orderSDSModelManager.lockOrderByWlUser(request);
		return resp;
	}

	@ZteMethodAnnontion(name = "顺丰人工筛单结果同步", group_name = "通用组件", order = "28", page_show = true, path = "zteCommonTraceRule.sfOrderRespSyn")
	public BusiCompResponse sfOrderRespSyn(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		Map params = busiCompRequest.getQueryParams();
		TacheFact fact = (TacheFact) params.get("fact");
		ArtificialSelectRequest request = (ArtificialSelectRequest) fact.getRequest();
		resp = orderSDSModelManager.sfOrderRespSyn(request);
		return resp;
	}

	@ZteMethodAnnontion(name = "顺丰路由信息推送", group_name = "通用组件", order = "25", page_show = true, path = "zteCommonTraceRule.sfRoutePush")
	public BusiCompResponse sfRoutePush(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		TacheFact fact = (TacheFact) busiCompRequest.getQueryParams().get("fact");
		RoutePushServiceRequset req = (RoutePushServiceRequset) fact.getRequest();
		resp = orderSDSModelManager.sfRoutePush(req);
		return resp;
	}

	@ZteMethodAnnontion(name = "EMS路由信息推送", group_name = "通用组件", order = "25", page_show = true, path = "zteCommonTraceRule.emsRoutePush")
	public BusiCompResponse emsRoutePush(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		TacheFact fact = (TacheFact) busiCompRequest.getQueryParams().get("fact");
		EmsRoutePushServiceReq req = (EmsRoutePushServiceReq) fact.getRequest();
		BusiDealResult result = commonManager.emsRoutePush(req);
		if (EcsOrderConsts.BUSI_DEAL_RESULT_0000.equals(result.getError_code())) {
			resp.setError_code("0");
			resp.setError_msg("同步成功");
		} else {
			resp.setError_code("-1");
			resp.setError_msg(result.getError_msg());
		}
		return resp;
	}

	@ZteMethodAnnontion(name = "订单状态通知（南都）", group_name = "通用组件", order = "26", page_show = true, path = "zteCommonTraceRule.statusNoticFromND")
	public BusiCompResponse statusNoticFromND(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		Map params = busiCompRequest.getQueryParams();
		TacheFact fact = (TacheFact) params.get("fact");
		NotifyOrderStatuNDRequset request = (NotifyOrderStatuNDRequset) fact.getRequest();
		resp = orderSDSModelManager.statusNoticFromND(request);
		return resp;
	}

	@ZteMethodAnnontion(name = "订单完成通知（南都）", group_name = "通用组件", order = "27", page_show = true, path = "zteCommonTraceRule.ndFinishNotic")
	public BusiCompResponse ndFinishNotic(BusiCompRequest busiCompRequest) throws ApiBusiException {
		/**
		 * 需要记录补寄内容 商品信息 物流信息 记录以上信息后，清空订单锁定 调用可见性组件 等待后续处理
		 */

		BusiCompResponse resp = new BusiCompResponse();
		Map params = busiCompRequest.getQueryParams();
		TacheFact fact = (TacheFact) params.get("fact");
		OrderDealSuccessNDRequset request = (OrderDealSuccessNDRequset) fact.getRequest();
		resp = orderSDSModelManager.ndFinishNotic(request);
		return resp;
	}

	@ZteMethodAnnontion(name = "订单分单", group_name = "通用组件", order = "28", page_show = true, path = "zteCommonTraceRule.orderSplit")
	public BusiCompResponse orderSplit(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		String sys_code = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.SYS_CODE);

		// 目前所有订单都是新系统处理 update by shusx 2016/1/18
		if (StringUtil.isEmpty(sys_code)) {
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.SYS_CODE }, new String[] { EcsOrderConsts.ORDER_SFLOW_NEW });
		}
		resp.setError_code(ConstsCore.ERROR_SUCC);
		resp.setError_msg("执行成功");

		/*
		 * 目前所有订单都是新系统处理 update by shusx 2016/1/18
		 * if(!StringUtil.isEmpty(sys_code)){ CommonTools.addBusiError("-9999",
		 * "订单不能重复分单"); } TacheFact fact = new TacheFact();
		 * fact.setOrder_id(order_id); PlanRuleTreeExeReq planReq = new
		 * PlanRuleTreeExeReq(); planReq.setFact(fact);
		 * planReq.setPlan_id(EcsOrderConsts.ORDER_SEPARTER_FLOW_2);
		 * PlanRuleTreeExeResp planResp =
		 * CommonDataFactory.getInstance().exePlanRuleTree(planReq);
		 * 
		 * //判断新老系统执行与否 boolean isExec = planResp.isRuleExecute(); if(isExec){
		 * CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new
		 * String[]{AttrConsts.SYS_CODE}, new
		 * String[]{EcsOrderConsts.ORDER_SFLOW_NEW}); }else{
		 * CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new
		 * String[]{AttrConsts.SYS_CODE}, new
		 * String[]{EcsOrderConsts.ORDER_SFLOW_OLD}); } planReq = new
		 * PlanRuleTreeExeReq(); planReq.setFact(fact);
		 * planReq.setPlan_id(EcsOrderConsts.SYN_ORDER_OLD_SYS); planResp =
		 * CommonDataFactory.getInstance().exePlanRuleTree(planReq);
		 * BusiCompResponse busiResp =
		 * CommonDataFactory.getInstance().getRuleTreeresult(planResp);
		 * if(ConstsCore.ERROR_SUCC.equals(busiResp.getError_code())){
		 * resp.setError_code(ConstsCore.ERROR_SUCC); resp.setError_msg("执行成功");
		 * }else{ resp.setError_code(ConstsCore.ERROR_FAIL);
		 * resp.setError_msg("订单分单失败"+busiResp.getError_msg()); //add by wui }
		 */

		return resp;
	}

	@ZteMethodAnnontion(name = "新商城模拟异常单测试", group_name = "通用组件", order = "27", page_show = true, path = "zteCommonTraceRule.wygTest")
	public BusiCompResponse wygTest(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		Map params = busiCompRequest.getQueryParams();
		TacheFact fact = (TacheFact) params.get("fact");
		WYGTestRequest request = (WYGTestRequest) fact.getRequest();
		WYGTestResponse wygTestResponse = ecsOrdManager.getWygTestReturnMsg(request);
		if (null != wygTestResponse) {
			OrderTreeBusiRequest orderTree = new OrderTreeBusiRequest();
			String json = CommonTools.beanToJson(wygTestResponse);
			orderTree.setOrder_id(request.getOrderId());
			orderTree.setCol7(json);
			orderTree.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			orderTree.setDb_action(ConstsCore.DB_ACTION_UPDATE);
			orderTree.store();
		}
		return resp;
	}

	@ZteMethodAnnontion(name = "订单信息同步到老系统", group_name = "通用组件", order = "28", page_show = true, path = "zteCommonTraceRule.synOrderToOldSys")
	public BusiCompResponse synOrderToOldSys(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		resp.setError_code("0");
		resp.setError_msg("同步成功");
		return resp;
	}

	@ZteMethodAnnontion(name = "环节跳转，标记异常和恢复异常通知老系统", group_name = "通用组件", order = "29", page_show = true, path = "zteCommonTraceRule.synOrderStatusToOldSys")
	public BusiCompResponse synOrderStatusToOldSys(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		BusiDealResult result = commonManager.synOrderStatusToOldSys(busiCompRequest);
		if (EcsOrderConsts.BUSI_DEAL_RESULT_0000.equals(result.getError_code())) {
			resp.setError_code("0");
			resp.setError_msg("同步成功");
		} else {
			resp.setError_code("-1");
			resp.setError_msg(result.getError_msg());
		}
		return resp;
	}

	@ZteMethodAnnontion(name = "[AOP]客户校验", group_name = "通用组件", order = "30", page_show = true, path = "zteCommonTraceRule.custInfoCheckAop")
	public BusiCompResponse custInfoCheckAop(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		BusiDealResult custResp = ordVisitTacheManager.custInfoCheck(order_id);
		if (!custResp.getError_code().equals(EcsOrderConsts.AOP_SUCCESS_0000)) {
			CommonTools.addBusiError(custResp.getError_code(), custResp.getError_msg());
		}
		resp.setError_code("0");
		resp.setError_msg("客户校验成功");
		resp.setResponse(custResp.getResponse());
		return resp;
	}

	@ZteMethodAnnontion(name = "[AOP]ESS客户校验", group_name = "通用组件", order = "30", page_show = true, path = "zteCommonTraceRule.custInfoCheckAopFromESS")
	public BusiCompResponse custInfoCheckAopFromESS(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		BusiDealResult custResp = ordVisitTacheManager.custInfoCheckFromESS(order_id, EcsOrderConsts.AOP_OPE_SYS_TYPE_1);
		if (!custResp.getError_code().equals(EcsOrderConsts.AOP_SUCCESS_0000)) {
			CommonTools.addBusiError(custResp.getError_code(), custResp.getError_msg());
		}
		resp.setError_code("0");
		resp.setError_msg("ESS客户校验成功");
		resp.setResponse(custResp.getResponse());
		return resp;
	}

	@ZteMethodAnnontion(name = "[AOP]CB客户校验", group_name = "通用组件", order = "30", page_show = true, path = "zteCommonTraceRule.custInfoCheckAopFromCB")
	public BusiCompResponse custInfoCheckAopFromCB(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		BusiDealResult custResp = ordVisitTacheManager.custInfoCheckFromCB(order_id, EcsOrderConsts.AOP_OPE_SYS_TYPE_2);
		if (!custResp.getError_code().equals(EcsOrderConsts.AOP_SUCCESS_0000)) {
			CommonTools.addBusiError(custResp.getError_code(), custResp.getError_msg());
		}
		resp.setError_code("0");
		resp.setError_msg("CB客户校验成功");
		resp.setResponse(custResp.getResponse());
		return resp;
	}

	/**
	 * @author song.qi
	 * @param busiCompRequest
	 * @return
	 * @throws ApiBusiException
	 */
	@ZteMethodAnnontion(name = "黑名单校验", group_name = "通用组件", order = "30", page_show = true, path = "zteCommonTraceRule.blackListCheck")
	public BusiCompResponse blackListCheck(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		BusiDealResult dealResult = ordVisitTacheManager.blackListCheck(order_id);
		if (!StringUtils.equals(dealResult.getError_code(), EcsOrderConsts.EMS_INF_SUCC_CODE)) {
			CommonTools.addBusiError(dealResult.getError_code(), dealResult.getError_msg());
		}
		resp.setResponse(dealResult.getResponse());
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}
	/**
	 * 只实现了黑名单校验功能
	 * @param busiCompRequest
	 * @return
	 * @throws ApiBusiException
	 */
	@ZteMethodAnnontion(name="[BSS]用户资料校验",group_name="通用组件",order="30",page_show=true,path="zteCommonTraceRule.bssCustomerCheck")
	public BusiCompResponse bssCustomerCheck(BusiCompRequest busiCompRequest) throws ApiBusiException{
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		BusiDealResult dealResult = ordVisitTacheManager.blackListCheck(order_id);
		if(!StringUtils.equals(dealResult.getError_code(), EcsOrderConsts.AOP_SUCCESS_0000)){
			CommonTools.addBusiError(dealResult.getError_code(),dealResult.getError_msg());		
		}
		resp.setResponse(dealResult.getResponse());
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}
	
	@ZteMethodAnnontion(name = "[BSS]本地商城状态同步", group_name = "通用组件", order = "30", page_show = true, path = "zteCommonTraceRule.bssLocalGoodsStatusSynchronization")
	public BusiCompResponse bssLocalGoodsStatusSynchronization(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		BusiDealResult dealResult = commonManager.localGoodsStatusSynchronization(order_id);
		if (!StringUtils.equals(dealResult.getError_code(), EcsOrderConsts.AOP_SUCCESS_0000)) {
			CommonTools.addBusiError(dealResult.getError_code(), dealResult.getError_msg());
		}
		resp.setResponse(dealResult.getResponse());
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}

	@ZteMethodAnnontion(name = "[BSS]客户校验", group_name = "通用组件", order = "30", page_show = true, path = "zteCommonTraceRule.custInfoCheckBSS")
	public BusiCompResponse custInfoCheckBSS(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		BusiDealResult custResp = ordVisitTacheManager.custInfoCheckBSS(order_id);
		if (!StringUtils.equals(custResp.getError_code(), EcsOrderConsts.AOP_SUCCESS_0000)) {
			CommonTools.addBusiError(custResp.getError_code(), custResp.getError_msg());
		}
		resp.setError_code("0");
		resp.setError_msg("[BSS]客户校验成功");
		resp.setResponse(custResp.getResponse());
		return resp;
	}

	@ZteMethodAnnontion(name = "[AOP]CB(ESS)工号信息查询", group_name = "通用组件", order = "31", page_show = true, path = "zteCommonTraceRule.essInfoQuery")
	public BusiCompResponse essInfoQuery(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		String essID = busiCompRequest.getQueryParams().get("essID").toString();
		String orderGonghao = busiCompRequest.getQueryParams().get("orderGonghao").toString();
		String orderId = busiCompRequest.getOrder_id();
		// 员工信息校验
		BusiDealResult essResp = ordVisitTacheManager.essOperatorInfoQuery(essID, orderGonghao, orderId);
		if (!essResp.getError_code().equals(EcsOrderConsts.AOP_SUCCESS_0000))
			CommonTools.addBusiError(essResp.getError_code(), essResp.getError_msg());
		resp.setError_code("0");
		resp.setError_msg("CB(ESS)工号信息查询成功");
		resp.setResponse(essResp.getResponse());
		return resp;
	}

	@ZteMethodAnnontion(name = "[AOP]主卡号码预占", group_name = "通用组件", order = "32", page_show = true, path = "zteCommonTraceRule.numberPreOccupancyAop")
	public BusiCompResponse numberPreOccupancyAop(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		String proKey = (new SimpleDateFormat("yyyyMMddHHmmss")).format(new Date()) + ManagerUtils.genRandowNum(6);

		// 处理淘宝订单过来的单子号码为空的情况
		String phone_num = CommonDataFactory.getInstance().getAttrFieldValue(busiCompRequest.getOrder_id(), AttrConsts.PHONE_NUM);
		if (StringUtil.isEmpty(phone_num)) {
			resp.setError_code("0");
			resp.setError_msg("主卡号码预占成功(没号码，神马都没做)");
			return resp;
		}

		BusiDealResult numberInfo = ordCollectManager.numberStatesChangeAop(busiCompRequest.getOrder_id(), EcsOrderConsts.OCCUPIEDFLAG_1, proKey);
		// 保存号码信息
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(busiCompRequest.getOrder_id());
		OrderPhoneInfoBusiRequest orderPhoneInfoBusiRequest = orderTree.getPhoneInfoBusiRequest();
		if (orderPhoneInfoBusiRequest == null) {
			orderPhoneInfoBusiRequest = new OrderPhoneInfoBusiRequest();
		}
		if (!numberInfo.getError_code().equals(EcsOrderConsts.AOP_SUCCESS_0000)) {
			orderPhoneInfoBusiRequest.setOperatorState(EcsOrderConsts.OPERATOR_STATE_1);
			orderPhoneInfoBusiRequest.setOccupiedFlag(EcsOrderConsts.OCCUPIEDFLAG_5);
			orderPhoneInfoBusiRequest.setPhone_num(phone_num);
			orderPhoneInfoBusiRequest.setOccupied_msg(numberInfo.getError_msg());
			if (orderPhoneInfoBusiRequest.getOrder_id() == null) {
				orderPhoneInfoBusiRequest.setDb_action(ConstsCore.DB_ACTION_INSERT);
			} else {
				orderPhoneInfoBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
			}
			orderPhoneInfoBusiRequest.setOrder_id(busiCompRequest.getOrder_id());
			orderPhoneInfoBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			orderPhoneInfoBusiRequest.store();
			CommonTools.addBusiError(numberInfo.getError_code(), numberInfo.getError_msg());
		} else {
			orderPhoneInfoBusiRequest.setOperatorState(EcsOrderConsts.OPERATOR_STATE_0);
			orderPhoneInfoBusiRequest.setOccupiedFlag(EcsOrderConsts.OCCUPIEDFLAG_1);
			orderPhoneInfoBusiRequest.setPhone_num(phone_num);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			orderPhoneInfoBusiRequest.setOccupiedTime(sdf.format(new Date()));
			orderPhoneInfoBusiRequest.setProKey(proKey);
			orderPhoneInfoBusiRequest.setProKeyMode("0");
			if (orderPhoneInfoBusiRequest.getOrder_id() == null) {
				orderPhoneInfoBusiRequest.setDb_action(ConstsCore.DB_ACTION_INSERT);
			} else {
				orderPhoneInfoBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
			}
			orderPhoneInfoBusiRequest.setOrder_id(busiCompRequest.getOrder_id());
			orderPhoneInfoBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			orderPhoneInfoBusiRequest.store();
		}
		resp.setError_code("0");
		resp.setError_msg("主卡号码预占成功");
		resp.setResponse(numberInfo.getResponse());
		return resp;
	}

	@ZteMethodAnnontion(name = "[AOP]主卡号码预定", group_name = "通用组件", order = "33", page_show = true, path = "zteCommonTraceRule.numberOccupancyAop")
	public BusiCompResponse numberOccupancyAop(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		String phone_num = CommonDataFactory.getInstance().getAttrFieldValue(busiCompRequest.getOrder_id(), AttrConsts.PHONE_NUM);
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(busiCompRequest.getOrder_id());
		OrderPhoneInfoBusiRequest orderPhoneInfoBusiRequest = orderTree.getPhoneInfoBusiRequest();
		String proKey = orderPhoneInfoBusiRequest.getProKey();
		String occupiedTime = null;
		String pay_status = CommonDataFactory.getInstance().getAttrFieldValue(busiCompRequest.getOrder_id(), AttrConsts.PAY_STATUS);
		if (EcsOrderConsts.AOP_PAY_STUTAS_0.equals(pay_status)) {
			// 未付费预定7天
			DateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			Date date = new Date();
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			c.add(Calendar.MINUTE, EcsOrderConsts.AOP_OCCUPIED_TIME * 2 * 24 * 8);
			occupiedTime = sdf.format(c.getTime());
		} else if (EcsOrderConsts.AOP_PAY_STUTAS_1.equals(pay_status)) {
			// 已付费预定到2059年
			occupiedTime = "20590101010100";
		}
		orderPhoneInfoBusiRequest.setOccupiedTime(occupiedTime);
		orderPhoneInfoBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
		orderPhoneInfoBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
		orderPhoneInfoBusiRequest.store();
		BusiDealResult numberInfo = ordCollectManager.numberStatesChangeAop(busiCompRequest.getOrder_id(), EcsOrderConsts.OCCUPIEDFLAG_2, proKey);
		if (!numberInfo.getError_code().equals(EcsOrderConsts.AOP_SUCCESS_0000)) {
			orderPhoneInfoBusiRequest.setOperatorState(EcsOrderConsts.OPERATOR_STATE_3);
			orderPhoneInfoBusiRequest.setOccupiedFlag(EcsOrderConsts.OCCUPIEDFLAG_6);
			orderPhoneInfoBusiRequest.setPhone_num(phone_num);
			orderPhoneInfoBusiRequest.setOccupied_msg(numberInfo.getError_msg());
			orderPhoneInfoBusiRequest.setOrder_id(busiCompRequest.getOrder_id());
			orderPhoneInfoBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
			orderPhoneInfoBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			orderPhoneInfoBusiRequest.store();
			CommonTools.addBusiError(numberInfo.getError_code(), numberInfo.getError_msg());
		} else {
			orderPhoneInfoBusiRequest.setOperatorState(EcsOrderConsts.OPERATOR_STATE_2);
			orderPhoneInfoBusiRequest.setOccupiedFlag(EcsOrderConsts.OCCUPIEDFLAG_2);
			orderPhoneInfoBusiRequest.setPhone_num(phone_num);
			orderPhoneInfoBusiRequest.setOrder_id(busiCompRequest.getOrder_id());
			orderPhoneInfoBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
			orderPhoneInfoBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			orderPhoneInfoBusiRequest.store();
		}
		resp.setError_code("0");
		resp.setError_msg("主卡号码预定成功");
		resp.setResponse(numberInfo.getResponse());
		return resp;
	}

	@ZteMethodAnnontion(name = "[AOP]终端资源释放", group_name = "通用组件", order = "34", page_show = true, path = "zteCommonTraceRule.termiResourceReleaseAop")
	public BusiCompResponse termiResourceReleaseAop(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		BusiDealResult recProd = ordPickingTacheManager.releaseResource(order_id, EcsOrderConsts.AOP_TERMINAL_OCCUPIEDFLAG_3);
		if (!recProd.getError_code().equals(EcsOrderConsts.AOP_SUCCESS_0000)) {
			CommonTools.addBusiError(recProd.getError_code(), recProd.getError_msg());
		}

		resp.setResponse(recProd.getResponse());
		resp.setError_code("0");
		resp.setError_msg("终端资源释放成功");
		return resp;
	}

	@ZteMethodAnnontion(name = "[AOP]终端资源释放--批量", group_name = "通用组件", order = "35", page_show = true, path = "zteCommonTraceRule.termiResourceReleaseBatchAop")
	public BusiCompResponse termiResourceReleaseBatchAop(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		BusiDealResult recProd = ordPickingTacheManager.releaseResourceBatch(order_id, EcsOrderConsts.AOP_TERMINAL_OCCUPIEDFLAG_3, new ArrayList<String>());
		if (!recProd.getError_code().equals(EcsOrderConsts.AOP_SUCCESS_0000)) {
			CommonTools.addBusiError(recProd.getError_code(), recProd.getError_msg());
		}

		resp.setResponse(recProd.getResponse());
		resp.setError_code("0");
		resp.setError_msg("终端资源释放成功");
		return resp;
	}

	@ZteMethodAnnontion(name = "[AOP]主卡号码释放", group_name = "通用组件", order = "35", page_show = true, path = "zteCommonTraceRule.numberReleaseAop")
	public BusiCompResponse numberReleaseAop(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		OrderPhoneInfoBusiRequest orderPhoneInfoBusiRequest = orderTree.getPhoneInfoBusiRequest();
		String proKey = orderPhoneInfoBusiRequest.getProKey();
		BusiDealResult numberInfo = ordCollectManager.numberStatesChangeAop(busiCompRequest.getOrder_id(), EcsOrderConsts.OCCUPIEDFLAG_4, proKey);
		if (!numberInfo.getError_code().equals(EcsOrderConsts.AOP_SUCCESS_0000) && !numberInfo.getError_code().equals(EcsOrderConsts.AOP_PHONE_0006)) {
			CommonTools.addBusiError(numberInfo.getError_code(), numberInfo.getError_msg());
		} else {
			// 释放成功更新号码信息
			orderPhoneInfoBusiRequest.setOperatorState(ConstsCore.NULL_VAL);
			orderPhoneInfoBusiRequest.setOccupiedFlag(EcsOrderConsts.OCCUPIEDFLAG_4);
			orderPhoneInfoBusiRequest.setOccupied_msg(ConstsCore.NULL_VAL);
			orderPhoneInfoBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
			orderPhoneInfoBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			orderPhoneInfoBusiRequest.store();
		}
		resp.setError_code("0");
		resp.setError_msg("主卡号码释放成功");
		resp.setResponse(numberInfo.getResponse());
		return resp;
	}

	@ZteMethodAnnontion(name = "[bss]主卡号码预占", group_name = "通用组件", order = "32", page_show = true, path = "zteCommonTraceRule.numberPreOccupancyBss")
	public BusiCompResponse numberPreOccupancyBss(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		String phone_num = CommonDataFactory.getInstance().getAttrFieldValue(busiCompRequest.getOrder_id(), AttrConsts.PHONE_NUM);
		// 号码为空或者号码不是186号段的，不用通知bss
		String occupancySysOld = CommonDataFactory.getInstance().sectionNoOccupancySysPlan(busiCompRequest.getOrder_id(), phone_num);
		// 非BSS号码不需要预占
		if (!(StringUtils.equals(occupancySysOld, EcsOrderConsts.NUMBER_OCCUPANCY_TYPE_0) || StringUtils.equals(occupancySysOld, EcsOrderConsts.NUMBER_OCCUPANCY_TYPE_1))) {
			resp.setError_code("0");
			resp.setError_msg("号码预占成功,非BSS号段");
			return resp;
		}
		/**
		 * 1、如果订单不走ESS预占,只走BSS预占,需要生成流水号
		 * 2、如果订单既走ESS预占,也走BSS预占,不需要生成流水号,直接用ESS的预占流水号
		 */
		if (StringUtils.equals(occupancySysOld, EcsOrderConsts.NUMBER_OCCUPANCY_TYPE_0)) {
			OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(busiCompRequest.getOrder_id());
			OrderPhoneInfoBusiRequest orderPhoneInfoBusiRequest = orderTree.getPhoneInfoBusiRequest();
			orderPhoneInfoBusiRequest.setProKey((new SimpleDateFormat("yyyyMMddHHmmss")).format(new Date()) + ManagerUtils.genRandowNum(6));
			orderPhoneInfoBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
			orderPhoneInfoBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			orderPhoneInfoBusiRequest.store();
		}
		BusiDealResult numberInfo = ordCollectManager.numberStatesChangeBss(busiCompRequest.getOrder_id(), EcsOrderConsts.BSS_OCCUPIEDFLAG_1);
		// 保存号码信息
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(busiCompRequest.getOrder_id());
		OrderPhoneInfoBusiRequest orderPhoneInfoBusiRequest = orderTree.getPhoneInfoBusiRequest();
		if (!numberInfo.getError_code().equals(EcsOrderConsts.AOP_SUCCESS_0000)) {
			orderPhoneInfoBusiRequest.setBssOccupiedFlag(EcsOrderConsts.OCCUPIEDFLAG_5);
			orderPhoneInfoBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
			orderPhoneInfoBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			orderPhoneInfoBusiRequest.store();
			CommonTools.addBusiError(numberInfo.getError_code(), "bss号码资源预占失败");
		} else {
			orderPhoneInfoBusiRequest.setBssOccupiedFlag(EcsOrderConsts.OCCUPIEDFLAG_1);
			orderPhoneInfoBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
			orderPhoneInfoBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			orderPhoneInfoBusiRequest.store();
		}
		resp.setError_code("0");
		resp.setError_msg("主卡号码预占成功");
		resp.setResponse(numberInfo.getResponse());
		return resp;
	}

	@ZteMethodAnnontion(name = "[bss]主卡号码释放", group_name = "通用组件", order = "32", page_show = true, path = "zteCommonTraceRule.numberReleaseBss")
	public BusiCompResponse numberReleaseBss(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		String phone_num = CommonDataFactory.getInstance().getAttrFieldValue(busiCompRequest.getOrder_id(), AttrConsts.PHONE_NUM);
		// 号码为空或者号码不是186号段的，不用通知bss
		String occupancySysOld = CommonDataFactory.getInstance().sectionNoOccupancySysPlan(busiCompRequest.getOrder_id(), phone_num);
		// 非BSS号码不需要预占
		if (!(StringUtils.equals(occupancySysOld, EcsOrderConsts.NUMBER_OCCUPANCY_TYPE_0) || StringUtils.equals(occupancySysOld, EcsOrderConsts.NUMBER_OCCUPANCY_TYPE_1))) {
			resp.setError_code("0");
			resp.setError_msg("主卡号码释放成功");
			return resp;
		}
		BusiDealResult numberInfo = ordCollectManager.numberStatesChangeBss(busiCompRequest.getOrder_id(), EcsOrderConsts.BSS_OCCUPIEDFLAG_2);
		// 保存号码信息
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(busiCompRequest.getOrder_id());
		OrderPhoneInfoBusiRequest orderPhoneInfoBusiRequest = orderTree.getPhoneInfoBusiRequest();
		if (!numberInfo.getError_code().equals(EcsOrderConsts.AOP_SUCCESS_0000)) {
			CommonTools.addBusiError(numberInfo.getError_code(), numberInfo.getError_msg());
		} else {
			orderPhoneInfoBusiRequest.setBssOccupiedFlag(EcsOrderConsts.OCCUPIEDFLAG_4);
			orderPhoneInfoBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
			orderPhoneInfoBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			orderPhoneInfoBusiRequest.store();
		}
		resp.setError_code("0");
		resp.setError_msg("主卡号码释放成功");
		resp.setResponse(numberInfo.getResponse());
		return resp;
	}

	@ZteMethodAnnontion(name = "[AOP]发展人信息查询", group_name = "通用组件", order = "39", page_show = true, path = "zteCommonTraceRule.developInfoQueryAop")
	public BusiCompResponse developInfoQueryAop(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		// 发展人查询
		BusiDealResult developResp = ordVisitTacheManager.developPersonCheck(order_id);
		if (!developResp.getError_code().equals(EcsOrderConsts.AOP_SUCCESS_0000)) {
			CommonTools.addBusiError(developResp.getError_code(), developResp.getError_msg());
		}
		resp.setError_code("0");
		resp.setError_msg("发展人信息查询成功");
		resp.setResponse(developResp.getResponse());
		return resp;
	}

	/**
	 * 匹配订单是否走AOP开户流程
	 * 
	 * @param busiCompRequest
	 * @return
	 */
	@ZteMethodAnnontion(name = "匹配订单是否走AOP开户", group_name = "通用组件", order = "40", page_show = true, path = "zteCommonTraceRule.matchingIsAop")
	public BusiCompResponse matchingIsAop(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		// 获取规则对象
		Map params = busiCompRequest.getQueryParams();
		TacheFact fact = (TacheFact) params.get("fact");
		String is_aop = fact.getCalc_open_channel();
		String order_id = busiCompRequest.getOrder_id();

		CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.IS_AOP }, new String[] { is_aop });
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		OrderExtBusiRequest orderExtBusiRequest = orderTree.getOrderExtBusiRequest();
		orderExtBusiRequest.setIs_aop(is_aop);
		String set_sql = " is_aop = '"+is_aop+"'";
		if (!StringUtils.equals(EcsOrderConsts.NO_DEFAULT_VALUE, is_aop)) {
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.SEND_ZB }, new String[] { EcsOrderConsts.NO_DEFAULT_VALUE });
			orderExtBusiRequest.setSend_zb(EcsOrderConsts.NO_DEFAULT_VALUE);
			set_sql += ", send_zb = '"+EcsOrderConsts.NO_DEFAULT_VALUE+"'";
		}
		this.updateOrderTree(set_sql, "es_order_ext", order_id, orderTree);
		
		resp.setFact(fact);
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}
	
	/***
	 * 前置规则  -- 匹配订单是否走AOP开户
	 * add by hxm 2018.5.23
	 * @param busiCompRequest
	 * @return
	 * @throws ApiBusiException
	 */
	@ZteMethodAnnontion(name = "匹配订单是否走AOP开户(新)", group_name = "通用组件", order = "61", page_show = true, path = "zteCommonTraceRule.matchingIsAopNew")
	public BusiCompResponse matchingIsAopNew(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		//获取规则对象
		Map params = busiCompRequest.getQueryParams();
		PreRuleFact fact = (PreRuleFact) params.get("fact");
		Map<String,String> paraObj = fact.getParamMap();
		String is_aop = paraObj.get("is_aop");
//		String order_id = busiCompRequest.getOrder_id();
		Map<String,Object> valueResponse = new HashMap<String,Object>();
//		CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.IS_AOP }, new String[] { is_aop });
//
		if (!StringUtils.equals(EcsOrderConsts.NO_DEFAULT_VALUE, is_aop)) {
			valueResponse.put("send_zb", EcsOrderConsts.NO_DEFAULT_VALUE);
		}
		
		valueResponse.put("is_aop", is_aop);
		fact.setValueResponse(valueResponse);
		resp.setFact(fact);
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}
	
	// ==============老用户代码不能动==================================

	/**
	 * 2-3G转4G校验
	 * 
	 * @param busiCompRequest
	 * @return
	 */
	@ZteMethodAnnontion(name = "[AOP]23转4G校验", group_name = "通用组件", order = "41", page_show = true, path = "zteCommonTraceRule.preCheck23to4")
	public BusiCompResponse preCheck23to4(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		BusiDealResult toFourCheckResponse = ordVisitTacheManager.preCheck23to4(order_id);
		if (!toFourCheckResponse.getError_code().equals(EcsOrderConsts.AOP_SUCCESS_0000)) {
			CommonTools.addBusiError(toFourCheckResponse.getError_code(), toFourCheckResponse.getError_msg());
		}
		resp.setError_code("0");
		resp.setError_msg("23转4G校验成功");
		resp.setResponse(toFourCheckResponse.getResponse());
		return resp;
	}

	/**
	 * 3G老用户业务校验
	 * 
	 * @param busiCompRequest
	 * @return
	 */
	@ZteMethodAnnontion(name = "[AOP]3G老用户业务校验", group_name = "通用组件", order = "42", page_show = true, path = "zteCommonTraceRule.oldUserCheck3G")
	public BusiCompResponse oldUserCheck3G(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		BusiDealResult developResp = ordVisitTacheManager.oldUserCheck3G(busiCompRequest.getOrder_id());
		if (!developResp.getError_code().equals(EcsOrderConsts.AOP_SUCCESS_0000)) {
			CommonTools.addBusiError(developResp.getError_code(), developResp.getError_msg());
		}
		resp.setError_code("0");
		resp.setError_msg("3G老用户业务校验成功");
		resp.setResponse(developResp.getResponse());
		return resp;
	}

	/**
	 * 用户资料检验三户返回
	 * 
	 * @param busiCompRequest
	 * @return
	 * @throws ApiBusiException
	 */
	@ZteMethodAnnontion(name = "[AOP]用户资料校验三户返回", group_name = "通用组件", order = "43", page_show = true, path = "zteCommonTraceRule.userInfoCheck3Back")
	public BusiCompResponse userInfoCheck3Back(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		BusiDealResult oldSubmitResp = ordVisitTacheManager.userInfoCheck3Back(order_id);
		if (!oldSubmitResp.getError_code().equals(EcsOrderConsts.AOP_SUCCESS_0000)) {
			CommonTools.addBusiError(oldSubmitResp.getError_code(), oldSubmitResp.getError_msg());
		}
		resp.setError_code("0");
		resp.setError_msg("用户资料三户查询成功");
		resp.setResponse(oldSubmitResp.getResponse());
		return resp;
	}

	/**
	 * 用户资料检验三户返回--浙江
	 * 
	 * @param busiCompRequest
	 * @return
	 * @throws ApiBusiException
	 */
	@ZteMethodAnnontion(name = "[AOP]用户资料校验三户返回浙江", group_name = "通用组件", order = "43", page_show = true, path = "zteCommonTraceRule.userInfoCheck3BackZJ")
	public BusiCompResponse userInfoCheck3BackZJ(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		BusiDealResult oldSubmitResp = ordVisitTacheManager.userInfoCheck3BackZJ(order_id);
		if (!oldSubmitResp.getError_code().equals(EcsOrderConsts.AOP_SUCCESS_0000)) {
			CommonTools.addBusiError(oldSubmitResp.getError_code(), oldSubmitResp.getError_msg());
		}
		resp.setError_code("0");
		resp.setError_msg("用户资料三户查询成功");
		resp.setResponse(oldSubmitResp.getResponse());
		return resp;
	}

	/**
	 * 23to4撤单
	 * 
	 * @param busiCompRequest
	 * @return
	 * @throws ApiBusiException
	 */
	@ZteMethodAnnontion(name = "[AOP]23to4撤单", group_name = "通用组件", order = "44", page_show = true, path = "zteCommonTraceRule.cannel23to4")
	public BusiCompResponse cannel23to4(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		BusiDealResult toFourCheckResponse = ordVisitTacheManager.cannel23to4(order_id);
		if (!toFourCheckResponse.getError_code().equals(EcsOrderConsts.AOP_SUCCESS_0000)) {
			CommonTools.addBusiError(toFourCheckResponse.getError_code(), toFourCheckResponse.getError_msg());
		}
		resp.setError_code("0");
		resp.setError_msg("23to4撤单成功");
		resp.setResponse(toFourCheckResponse.getResponse());
		return resp;
	}

	/**
	 * 套餐变更,撤单
	 * 
	 * @param busiCompRequest
	 * @return
	 * @throws ApiBusiException
	 */
	@ZteMethodAnnontion(name = "[AOP]套餐变更撤单", group_name = "通用组件", order = "45", page_show = true, path = "zteCommonTraceRule.prodChangeCancel")
	public BusiCompResponse prodChangeCancel(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		BusiDealResult toFourCheckResponse = ordVisitTacheManager.prodChangeCancel(order_id);
		if (!toFourCheckResponse.getError_code().equals(EcsOrderConsts.AOP_SUCCESS_0000)) {
			CommonTools.addBusiError(toFourCheckResponse.getError_code(), toFourCheckResponse.getError_msg());
		}
		resp.setError_code("0");
		resp.setError_msg("套餐变更撤单成功");
		resp.setResponse(toFourCheckResponse.getResponse());
		return resp;
	}

	@ZteMethodAnnontion(name = "退单确认结果设置", group_name = "通用组件", order = "46", page_show = true, path = "zteCommonTraceRule.ordRefundResultSet")
	public BusiCompResponse ordRefundResultSet(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		OrderExtBusiRequest orderExt = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest();
		if (!EcsOrderConsts.REFUND_STATUS_00.equals(orderExt.getRefund_status())) {// 非驳回则设置为退单确认通过
			orderExt.setRefund_status(EcsOrderConsts.REFUND_STATUS_01);
			orderExt.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			orderExt.setDb_action(ConstsCore.DB_ACTION_UPDATE);
			orderExt.store();// 更新订单树
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.REFUND_STATUS }, new String[] { EcsOrderConsts.REFUND_STATUS_01 });// 更新属性
		}
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}

	@ZteMethodAnnontion(name = "订单环节同步本地商城", group_name = "通用组件", order = "47", page_show = true, path = "zteCommonTraceRule.ordFlowTraceSyn")
	public BusiCompResponse ordFlowTraceSyn(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		BusiDealResult dealResult = commonManager.ordFlowTraceSyn(order_id, null);
		if (!dealResult.getError_code().equals(EcsOrderConsts.BUSI_DEAL_RESULT_0000)) {
			CommonTools.addBusiError(dealResult.getError_code(), dealResult.getError_msg());
		}
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}

	@ZteMethodAnnontion(name = "根据订单来源商城、固定的物理仓库编码、货品ID扣减库存", group_name = "通用组件", order = "48", page_show = true, path = "zteCommonTraceRule.subtractInventoryNum")
	public BusiCompResponse subtractInventoryNum(BusiCompRequest busiCompRequest) {
		BusiCompResponse resp = new BusiCompResponse();
		try {
			String order_id = busiCompRequest.getOrder_id();
			OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
			OrderExtBusiRequest orderExt = orderTree.getOrderExtBusiRequest();
			List<OrderItemProdBusiRequest> prod_list = orderTree.getOrderItemBusiRequests().get(0).getOrderItemProdBusiRequests();
			List<Map<String, String>> list = new ArrayList<Map<String, String>>();
			/**
			 * 获取货主信息
			 */
			String channel_id = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.RESERVE3);// 渠道信息
			String product_comp_code = ChannelidProductcompRefUtil.getCompCode(channel_id);// 由渠道映射货主
			/**
			 * 获取货主信息
			 */
			if (EcsOrderConsts.ORDER_FROM_10053.equals(orderExt.getOrder_from())) {
				List<OrderItemExtvtlBusiRequest> extvtl_list = orderTree.getOrderItemExtvtlBusiRequests();
				for (OrderItemExtvtlBusiRequest extvtl : extvtl_list) {
					if (EcsOrderConsts.PRODUCT_TYPE_TERMINAL.equals(extvtl.getGoods_type())) {
						String org_id = orderExt.getOrder_from();
						// B2B的单 product_id 对应 货品表的goods_id
						Map pro_map = CommonDataFactory.getInstance().getProductSpecMapByGoodsId(extvtl.getGoods_id(), SpecConsts.TYPE_ID_10000);
						Map<String, String> map = new HashMap<String, String>();
						map.put("num", "1");
						map.put("org_id", org_id);
						map.put("goods_id", pro_map != null ? pro_map.get("prod_goods_id").toString() : "");
						map.put("product_id", pro_map != null ? pro_map.get("product_id").toString() : "");
						map.put("sku", pro_map != null ? pro_map.get("sku").toString() : "");
						map.put("comp_code", product_comp_code == null ? "" : product_comp_code);
						map.put("order_id", order_id);
						list.add(map);
					}
				}
			} else {
				for (OrderItemProdBusiRequest prod : prod_list) {
					if (prod.getProd_spec_type_code().equals("0")) {
						String goods_id = orderTree.getOrderItemBusiRequests().get(0).getGoods_id();
						String org_id = orderExt.getOrder_from();
						Map pro_map = CommonDataFactory.getInstance().getProductSpecMapByGoodsId(goods_id, SpecConsts.TYPE_ID_10000);
						Map<String, String> map = new HashMap<String, String>();
						map.put("num", "1");
						map.put("org_id", org_id);
						map.put("goods_id", pro_map != null && pro_map.size() > 0 ? pro_map.get("prod_goods_id").toString() : "");
						map.put("product_id", pro_map != null && pro_map.size() > 0 ? pro_map.get("product_id").toString() : "");
						map.put("sku", pro_map != null ? pro_map.get("sku").toString() : "");
						map.put("comp_code", product_comp_code == null ? "" : product_comp_code);
						map.put("order_id", order_id);
						list.add(map);
						break;
					}
				}
			}
			SubtractInventoryNumReq req = new SubtractInventoryNumReq();
			req.setList(list);
			ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
			SubtractInventoryNumResp response = client.execute(req, SubtractInventoryNumResp.class);

			if ("0".equals(response.getError_code())) {// 记录为已扣减
				CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.STOCK_STATE }, new String[] { EcsOrderConsts.STOCK_STATE_REDUCED });
			}

			resp.setError_code("0"/** response.getError_code() **/
			); // 不管错误正确，返回码都是‘0’以确其他规则正常流转
			resp.setError_msg(response.getError_msg());
		} catch (Exception e) {
			// TODO: handle exception
			resp.setError_code("0"); // 不管错误正确，返回码都是‘0’以确其他规则正常流转
			resp.setError_msg("扣减库存异常！");
		}
		return resp;
	}

	@ZteMethodAnnontion(name = "根据订单来源商城、固定的物理仓库编码、货品ID增加库存", group_name = "通用组件", order = "49", page_show = true, path = "zteCommonTraceRule.increaseInventoryNum")
	public BusiCompResponse increaseInventoryNum(BusiCompRequest busiCompRequest) {
		BusiCompResponse resp = new BusiCompResponse();
		try {
			String order_id = busiCompRequest.getOrder_id();
			OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
			OrderExtBusiRequest orderExt = orderTree.getOrderExtBusiRequest();
			List<OrderItemProdBusiRequest> prod_list = orderTree.getOrderItemBusiRequests().get(0).getOrderItemProdBusiRequests();
			List<Map<String, String>> list = new ArrayList<Map<String, String>>();
			/**
			 * 获取货主信息
			 */
			String channel_id = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.RESERVE3);// 渠道信息
			String product_comp_code = ChannelidProductcompRefUtil.getCompCode(channel_id);// 由渠道映射货主
			/**
			 * 获取货主信息
			 */
			if (EcsOrderConsts.ORDER_FROM_10053.equals(orderExt.getOrder_from())) {
				List<OrderItemExtvtlBusiRequest> extvtl_list = orderTree.getOrderItemExtvtlBusiRequests();
				for (OrderItemExtvtlBusiRequest extvtl : extvtl_list) {
					if (EcsOrderConsts.PRODUCT_TYPE_TERMINAL.equals(extvtl.getGoods_type())) {
						String org_id = orderExt.getOrder_from();
						Map pro_map = CommonDataFactory.getInstance().getProductSpecMapByGoodsId(extvtl.getGoods_id(), SpecConsts.TYPE_ID_10000);
						Map<String, String> map = new HashMap<String, String>();
						map.put("num", "1");
						map.put("org_id", org_id);
						map.put("goods_id", pro_map != null ? pro_map.get("prod_goods_id").toString() : "");
						map.put("product_id", pro_map != null ? pro_map.get("product_id").toString() : "");
						map.put("sku", pro_map != null ? pro_map.get("sku").toString() : "");
						map.put("comp_code", product_comp_code == null ? "" : product_comp_code);
						map.put("order_id", order_id);
						list.add(map);
					}
				}
			} else {
				for (OrderItemProdBusiRequest prod : prod_list) {
					if (prod.getProd_spec_type_code().equals("0")) {
						String goods_id = orderTree.getOrderItemBusiRequests().get(0).getGoods_id();
						String org_id = orderExt.getOrder_from();
						Map pro_map = CommonDataFactory.getInstance().getProductSpecMapByGoodsId(goods_id, SpecConsts.TYPE_ID_10000);
						Map<String, String> map = new HashMap<String, String>();
						map.put("num", "1");
						map.put("org_id", org_id);
						map.put("goods_id", pro_map != null ? pro_map.get("prod_goods_id").toString() : "");
						map.put("product_id", pro_map != null ? pro_map.get("product_id").toString() : "");
						map.put("sku", pro_map != null ? pro_map.get("sku").toString() : "");
						map.put("comp_code", product_comp_code == null ? "" : product_comp_code);
						map.put("order_id", order_id);
						list.add(map);
						break;
					}
				}
			}

			IncreaseInventoryNumReq req = new IncreaseInventoryNumReq();
			req.setList(list);
			ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
			IncreaseInventoryNumResp response = client.execute(req, IncreaseInventoryNumResp.class);

			if ("0".equals(response.getError_code())) {// 记录为已回退
				CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.STOCK_STATE }, new String[] { EcsOrderConsts.STOCK_STATE_FALL_BAK });
			}

			resp.setError_code("0"/** response.getError_code() **/
			); // 不管错误正确，返回码都是‘0’以确其他规则正常流转
			resp.setError_msg(response.getError_msg());
		} catch (Exception e) {
			// TODO: handle exception
			resp.setError_code("0"); // 不管错误正确，返回码都是‘0’以确其他规则正常流转
			resp.setError_msg("扣减回退异常！");
		}
		return resp;
	}

	@ZteMethodAnnontion(name = "[AOP]订单查询", group_name = "通用组件", order = "50", page_show = true, path = "zteCommonTraceRule.orderQuery")
	public BusiCompResponse orderQuery(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		BusiDealResult response = ordVisitTacheManager.orderQuery(order_id);
		if (!response.getError_code().equals(EcsOrderConsts.AOP_SUCCESS_0000)) {
			CommonTools.addBusiError(response.getError_code(), response.getError_msg());
		}
		resp.setError_code("0");
		resp.setError_msg("订单查询成功");
		resp.setResponse(response.getResponse());
		return resp;
	}

	/**
	 * 匹配订单生产中心
	 * 
	 * @param busiCompRequest
	 * @return
	 * @throws ApiBusiException
	 */
	@ZteMethodAnnontion(name = "匹配订单生产中心", group_name = "通用组件", order = "47", page_show = true, path = "zteCommonTraceRule.matchingProduceCenter")
	public BusiCompResponse matchingProduceCenter(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		// 获取规则对象
		Map params = busiCompRequest.getQueryParams();
		TacheFact fact = (TacheFact) params.get("fact");
		String produce_center = fact.getCalc_input();
		String order_id = busiCompRequest.getOrder_id();

		CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.PRODUCE_CENTER }, new String[] { produce_center });

		resp.setFact(fact);
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}

	/**
	 * 社会渠道购机券换手机BSS端支持(加锁)
	 * 
	 * @param busiCompRequest
	 * @return
	 * @throws ApiBusiException
	 */
	@ZteMethodAnnontion(name = "社会渠道购机券换手机BSS端支持(加锁)common", group_name = "通用组件", order = "48", page_show = true, path = "zteCommonTraceRule.purchaseCouponsLock")
	public BusiCompResponse purchaseCouponsLock(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		boolean existPurchaseCoupons = false;
		List<AttrDiscountInfoBusiRequest> discounts = CommonDataFactory.getInstance().getOrderTree(order_id).getDiscountInfoBusiRequests();
		if (null != discounts && discounts.size() > 0) {
			List<Map<String, String>> discount_types = CommonDataFactory.getInstance().listDcPublicData(StypeConsts.DISCOUNTTYPE);
			List<String> need_types = new ArrayList<String>();
			for (Map<String, String> map : discount_types) {
				if ("1".equals(map.get("codea"))) {
					need_types.add(map.get("pkey"));
				}
			}
			for (AttrDiscountInfoBusiRequest discount : discounts) {
				if (need_types.contains(discount.getActivity_type())) {
					existPurchaseCoupons = true;// 存在代金券
				}
			}
		}
		if (!existPurchaseCoupons) {// 不存在代金券
			resp.setError_code("0");
			resp.setError_msg("不存在代金券");
			return resp;
		}
		BusiDealResult dealResult = commonManager.purchaseCouponsLock(order_id);
		if (!EcsOrderConsts.BUSI_DEAL_RESULT_0000.equals(dealResult.getError_code())) {// 业务组件失败
			CommonTools.addBusiError(dealResult.getError_code(), dealResult.getError_msg());
		}
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}

	/**
	 * 社会渠道购机券换手机BSS端支持(转兑)
	 * 
	 * @param busiCompRequest
	 * @return
	 * @throws ApiBusiException
	 */
	@SuppressWarnings("unchecked")
	@ZteMethodAnnontion(name = "社会渠道购机券换手机BSS端支持(转兑)common", group_name = "通用组件", order = "49", page_show = true, path = "zteCommonTraceRule.purchaseCouponsExchange")
	public BusiCompResponse purchaseCouponsExchange(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		boolean existPurchaseCoupons = false;
		List<AttrDiscountInfoBusiRequest> discounts = CommonDataFactory.getInstance().getOrderTree(order_id).getDiscountInfoBusiRequests();// 优惠信息列表
		if (null != discounts && discounts.size() > 0) {
			List<Map<String, String>> discount_types = CommonDataFactory.getInstance().listDcPublicData(StypeConsts.DISCOUNTTYPE);
			List<String> need_types = new ArrayList<String>();
			for (Map<String, String> map : discount_types) {
				if ("1".equals(map.get("codea"))) {
					need_types.add(map.get("pkey"));
				}
			}
			for (AttrDiscountInfoBusiRequest discount : discounts) {
				if (need_types.contains(discount.getActivity_type())) {
					existPurchaseCoupons = true;// 存在需要调接口的代金券
				}
			}
		}
		if (!existPurchaseCoupons) {// 不存在代金券
			resp.setError_code("0");
			resp.setError_msg("不存在代金券");
			return resp;
		}
		BusiDealResult dealResult = commonManager.purchaseCouponsExchange(order_id);
		if (!EcsOrderConsts.BUSI_DEAL_RESULT_0000.equals(dealResult.getError_code())) {// 业务组件失败
			// 标记业务办理异常，异常描述为“代金券转兑失败+接口返回描述”
			BusiCompRequest busi = new BusiCompRequest();
			Map queryParams = new HashMap();
			queryParams.put(EcsOrderConsts.EXCEPTION_FROM, EcsOrderConsts.EXCEPTION_FROM_ORD);
			queryParams.put("order_id", order_id);
			queryParams.put(EcsOrderConsts.EXCEPTION_TYPE, EcsOrderConsts.ABNORMAL_TYPE_BSS);
			queryParams.put(EcsOrderConsts.EXCEPTION_REMARK, "代金券转兑失败:" + dealResult.getError_msg());
			busi.setEnginePath("zteCommonTraceRule.markedException");
			busi.setOrder_id(order_id);
			busi.setQueryParams(queryParams);
			try {
				ZteResponse response = orderServices.execBusiComp(busi);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// 抛出错误
			CommonTools.addBusiError(dealResult.getError_code(), dealResult.getError_msg());
		}
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}

	/**
	 * [AOP]副卡批量预占 phoneNum有值时，对该号码预占；否则检索副卡表进行批量预占
	 * 
	 * @param busiCompRequest
	 * @return
	 * @throws ApiBusiException
	 */
	@ZteMethodAnnontion(name = "[AOP]副卡批量预占", group_name = "通用组件", order = "36", page_show = true, path = "zteCommonTraceRule.numberPreOccBatchAopFuKa")
	public BusiCompResponse numberPreOccBatchAopFuKa(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		Map map = busiCompRequest.getQueryParams();

		// 获取所有副卡
		List<OrderPhoneInfoFukaBusiRequest> phonelist = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderPhoneInfoFukaBusiRequests();

		// 筛选副卡数据
		phonelist = AttrUtils.checkPhonNumInfo(phonelist, map, EcsOrderConsts.OCCUPIEDFLAG_1, EcsOrderConsts.AOP);

		if (phonelist.size() == 0) {
			resp.setError_code("0");
			resp.setError_msg("副卡批量预占成功(没有查到需要预占的号码，神马都没做)");
			return resp;
		}

		BusiDealResult numberInfo = ordCollectManager.numberChangeBatchAopFuKa(order_id, EcsOrderConsts.OCCUPIEDFLAG_1, phonelist);

		if (!numberInfo.getError_code().equals(EcsOrderConsts.AOP_SUCCESS_0000)) {
			CommonTools.addBusiError(numberInfo.getError_code(), numberInfo.getError_msg());
		}

		resp.setError_code("0");
		resp.setError_msg("副卡批量预占成功");
		resp.setResponse(numberInfo.getResponse());
		return resp;
	}

	/**
	 * [AOP]副卡批量预定
	 * 
	 * @param phoneNum有值时，对该号码释放（当phoneNum找不到时，找prePhoneNum）；否则操作所有附属卡
	 * @param busiCompRequest
	 * @return
	 * @throws ApiBusiException
	 */
	@ZteMethodAnnontion(name = "[AOP]副卡批量预定", group_name = "通用组件", order = "36", page_show = true, path = "zteCommonTraceRule.numberOccupancyBatchAopFuKa")
	public BusiCompResponse numberOccupancyBatchAopFuKa(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		Map map = busiCompRequest.getQueryParams();

		// 获取所有副卡
		List<OrderPhoneInfoFukaBusiRequest> phonelist = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderPhoneInfoFukaBusiRequests();

		// 筛选副卡数据
		phonelist = AttrUtils.checkPhonNumInfo(phonelist, map, EcsOrderConsts.OCCUPIEDFLAG_2, EcsOrderConsts.AOP);

		if (phonelist.size() == 0) {
			resp.setError_code("0");
			resp.setError_msg("副卡批量预定成功(没有查到需要预定的号码，神马都没做)");
			return resp;
		}

		BusiDealResult numberInfo = ordCollectManager.numberChangeBatchAopFuKa(order_id, EcsOrderConsts.OCCUPIEDFLAG_2, phonelist);

		if (!numberInfo.getError_code().equals(EcsOrderConsts.AOP_SUCCESS_0000)) {
			CommonTools.addBusiError(numberInfo.getError_code(), numberInfo.getError_msg());
		}

		resp.setError_code("0");
		resp.setError_msg("副卡批量预定成功");
		resp.setResponse(numberInfo.getResponse());
		return resp;
	}

	/**
	 * [AOP]副卡批量释放
	 * 
	 * @param phoneNum有值时，对该号码释放（当phoneNum找不到时，找prePhoneNum）；否则操作所有附属卡
	 * @param busiCompRequest
	 * @return
	 * @throws ApiBusiException
	 */
	@ZteMethodAnnontion(name = "[AOP]副卡批量释放", group_name = "通用组件", order = "36", page_show = true, path = "zteCommonTraceRule.numberReleaseBatchAopFuKa")
	public BusiCompResponse numberReleaseBatchAopFuKa(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		Map map = busiCompRequest.getQueryParams();

		// 获取所有副卡
		// OrderTreeBusiRequest tress =
		// CommonDataFactory.getInstance().getOrderTree(order_id);
		List<OrderPhoneInfoFukaBusiRequest> phonelist = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderPhoneInfoFukaBusiRequests();

		// 筛选副卡数据
		phonelist = AttrUtils.checkPhonNumInfo(phonelist, map, EcsOrderConsts.OCCUPIEDFLAG_4, EcsOrderConsts.AOP);

		if (phonelist.size() == 0) {
			resp.setError_code("0");
			resp.setError_msg("副卡批量释放成功(没有查到需要释放的号码，神马都没做)");
			return resp;
		}

		BusiDealResult numberInfo = ordCollectManager.numberReleaseBatchAopFuKa(order_id, EcsOrderConsts.OCCUPIEDFLAG_4, phonelist);

		if (!numberInfo.getError_code().equals(EcsOrderConsts.AOP_SUCCESS_0000)) {
			CommonTools.addBusiError(numberInfo.getError_code(), numberInfo.getError_msg());
		}

		resp.setError_code("0");
		resp.setError_msg("副卡批量释放成功");
		resp.setResponse(numberInfo.getResponse());
		return resp;
	}

	/**
	 * [bss]副卡号码预占
	 * 
	 * @param busiCompRequest
	 * @param phoneNum有值时，对该号码释放（当phoneNum找不到时，找prePhoneNum）；否则操作所有附属卡
	 * @return
	 * @throws ApiBusiException
	 */
	@ZteMethodAnnontion(name = "[bss]副卡号码批量预占", group_name = "通用组件", order = "32", page_show = true, path = "zteCommonTraceRule.numberPreOccupancyBatchFukaBss")
	public BusiCompResponse numberPreOccupancySingleBss(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		Map map = busiCompRequest.getQueryParams();

		// 获取所有副卡
		List<OrderPhoneInfoFukaBusiRequest> phonelist = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderPhoneInfoFukaBusiRequests();

		// 筛选副卡数据
		phonelist = AttrUtils.checkPhonNumInfo(phonelist, map, EcsOrderConsts.BSS_OCCUPIEDFLAG_1, EcsOrderConsts.ESS);

		if (phonelist.size() == 0) {
			resp.setError_code("0");
			resp.setError_msg("副卡批量预占成功(没有查到需要预占的186号码，神马都没做)");
			return resp;
		}

		for (OrderPhoneInfoFukaBusiRequest vo : phonelist) {
			BusiDealResult numberInfo = ordCollectManager.numberStatesChangeSingleBssFuka(order_id, vo, EcsOrderConsts.BSS_OCCUPIEDFLAG_1);
			if (!numberInfo.getError_code().equals(EcsOrderConsts.AOP_SUCCESS_0000)) {
				CommonTools.addBusiError(numberInfo.getError_code(), "bss号码资源预占失败");
			}
			resp.setResponse(numberInfo.getResponse());
		}
		resp.setError_code("0");
		resp.setError_msg("bss号码预占成功");
		return resp;
	}

	/**
	 * [bss]副卡号码释放
	 * 
	 * @param busiCompRequest
	 * @param phoneNum有值时，对该号码释放（当phoneNum找不到时，找prePhoneNum）；否则操作所有附属卡
	 * @param prePhoneNum
	 * @return
	 * @throws ApiBusiException
	 */
	@ZteMethodAnnontion(name = "[bss]副卡号码批量释放", group_name = "通用组件", order = "38", page_show = true, path = "zteCommonTraceRule.numberReleaseBatchFukaBss")
	public BusiCompResponse numberReleaseSingleBss(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		Map map = busiCompRequest.getQueryParams();

		// 获取所有副卡
		List<OrderPhoneInfoFukaBusiRequest> phonelist = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderPhoneInfoFukaBusiRequests();

		// 筛选副卡数据
		phonelist = AttrUtils.checkPhonNumInfo(phonelist, map, EcsOrderConsts.BSS_OCCUPIEDFLAG_2, EcsOrderConsts.ESS);

		if (phonelist.size() == 0) {
			resp.setError_code("0");
			resp.setError_msg("副卡批量释放成功(没有查到需要释放的186号码，神马都没做)");
			return resp;
		}

		for (OrderPhoneInfoFukaBusiRequest vo : phonelist) {
			BusiDealResult numberInfo = ordCollectManager.numberStatesChangeSingleBssFuka(order_id, vo, EcsOrderConsts.BSS_OCCUPIEDFLAG_2);
			if (!numberInfo.getError_code().equals(EcsOrderConsts.AOP_SUCCESS_0000)) {
				CommonTools.addBusiError(numberInfo.getError_code(), "bss号码资源释放失败");
			}
			resp.setResponse(numberInfo.getResponse());
		}

		resp.setError_code("0");
		resp.setError_msg("bss号码释放成功");
		return resp;
	}

	/**
	 * 三网短信
	 * 
	 * @param busiCompRequest
	 * @return
	 * @throws ApiBusiException
	 */
	@ZteMethodAnnontion(name = "发送短信(新)", group_name = "通用组件", order = "51", page_show = true, path = "zteCommonTraceRule.send3NetSms")
	public BusiCompResponse send3NetSms(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		SmsSendReq smsSend = new SmsSendReq();
		String order_id = busiCompRequest.getOrder_id();
		Map params = busiCompRequest.getQueryParams();
		TacheFact fact = (TacheFact) params.get("fact");
		String template = fact.getCalc_input();
		// 计算结果如：SMS_OF_3NET|SMS_LATER_SEND_GOODS
		String templateValue = "";
		String[] templateArr = template.split("\\|");
		OrderRealNameInfoBusiRequest orderRealNameBusi = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderRealNameInfoBusiRequest();
		if (templateArr.length >= 2 && null != orderRealNameBusi && StringUtils.equals(EcsOrderConsts.IS_DEFAULT_VALUE, orderRealNameBusi.getLater_active_flag())) {
			// 后向激活取计算结果中的第二个模板
			String goods_cat_id = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.GOODS_CAT_ID);
			// 根据商品小类获取短信模板
			templateValue = CommonDataFactory.getInstance().getOtherDictVodeValue("SEND_GOODS_SMS_TMPL", goods_cat_id);
			if (StringUtils.isEmpty(templateValue)) {
				logger.info("订单[" + order_id + "],商品小类[" + goods_cat_id + "]未配置发货短信模板。");
				resp.setError_code("-1");
				resp.setError_msg("执行失败,未配置发货短信模板");
				return resp;
			}
		} else {
			templateValue = templateArr[0];
		}
		BusiDealResult dealResult = ordShipTacheManager.send3NetSms(order_id, template);
		if (EcsOrderConsts.BUSI_DEAL_RESULT_FAIL.equals(dealResult.getError_code())) {
			CommonTools.addBusiError("-1", dealResult.getError_msg());
		}
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}

	/**
	 * ZX add 2016-01-05 start 订单状态通知
	 * 
	 * @param busiCompRequest
	 * @return
	 * @throws ApiBusiException
	 */
	@ZteMethodAnnontion(name = "订单状态通知总部", group_name = "通用组件", order = "52", page_show = true, path = "zteCommonTraceRule.stateSynToZB")
	public BusiCompResponse stateSynToZB(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();

		String order_id = busiCompRequest.getOrder_id();
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		OrderExtBusiRequest orderExt = orderTree.getOrderExtBusiRequest();
		String flow_trace_id = orderExt.getFlow_trace_id();
		StateSynToZBRequest request = new StateSynToZBRequest();
		// 获取质检稽核状态
		String prodAuditStatus = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.PROD_AUDIT_STATUS);
		request.setNotNeedReqStrOrderId(order_id); // 订单号
		if (flow_trace_id.equals(EcsOrderConsts.DIC_ORDER_NODE_B)) { // 客户回访
			request.setNotNeedReqStrStateTag(EcsOrderConsts.STATE_CHG_REVIEWED); // 状态标记
			request.setNotNeedReqStrStateChgReason(EcsOrderConsts.STATE_CHG_REASON_QRWC); // 变更原因
			request.setNotNeedReqStrStateChgDesc("预处理完成"); // 变更描述
		}
		// else if (flow_trace_id.equals(EcsOrderConsts.DIC_ORDER_NODE_D)) { //
		// 开户
		// IUosService service = SpringContextHolder.getBean("uosService");
		// String processInstanceId =
		// orderTree.getOrderExtBusiRequest().getFlow_inst_id();
		// UosFlowReq req = new UosFlowReq();
		// req.setProcessInstanceId(processInstanceId);
		// UosFlowResp uosFlowResp = service.queryFlow(req);
		// List<UosNode> nodes = uosFlowResp.getNodes();
		// if (null!=nodes && nodes.size()>0) {
		// for (int i=0; i<nodes.size(); i++) {
		// UosNode node = nodes.get(i);
		// if (node.getTacheCode().equals(EcsOrderConsts.DIC_ORDER_NODE_X)) {
		// request.setNotNeedReqStrStateTag(EcsOrderConsts.STATE_CHG_REASON_OPENED);
		// // 状态标记
		// request.setNotNeedReqStrStateChgReason(EcsOrderConsts.STATE_CHG_REASON_OPENED);
		// // 变更原因
		// request.setNotNeedReqStrStateChgDesc("开户完成"); // 变更描述
		// }
		// }
		// }
		// } else if (flow_trace_id.equals(EcsOrderConsts.DIC_ORDER_NODE_X)) {
		// // 写卡
		// request.setNotNeedReqStrStateTag(EcsOrderConsts.STATE_CHG_WRITED); //
		// 状态标记
		// request.setNotNeedReqStrStateChgReason(EcsOrderConsts.STATE_CHG_REASON_OPENED);
		// // 变更原因
		// request.setNotNeedReqStrStateChgDesc("开户完成"); // 变更描述
		// }
		else if (flow_trace_id.equals(EcsOrderConsts.DIC_ORDER_NODE_F)) { // 稽核完成
			if (!EcsOrderConsts.PROD_AUDIT_STATUS_1.equals(prodAuditStatus)) {// 未质检稽核通过
				request.setNotNeedReqStrStateTag(EcsOrderConsts.STATE_CHG_REASON_OPENED); // 状态标记
				request.setNotNeedReqStrStateChgReason(EcsOrderConsts.STATE_CHG_REASON_OPENED); // 变更原因
				request.setNotNeedReqStrStateChgDesc("开户完成"); // 变更描述
			} else {// 质检稽核通过则提前通知总部发货状态
				request.setNotNeedReqStrStateTag(EcsOrderConsts.STATE_CHG_REASON_DELIVERED); // 状态标记
				request.setNotNeedReqStrStateChgReason(EcsOrderConsts.STATE_CHG_REASON_DELIVERED); // 变更原因
				request.setNotNeedReqStrStateChgDesc("发货完成"); // 变更描述
			}
		}
		/*
		 * else if (flow_trace_id.equals(EcsOrderConsts.DIC_ORDER_NODE_H)) { //
		 * 发货 request.setNotNeedReqStrStateTag(EcsOrderConsts.
		 * STATE_CHG_REASON_DELIVERED); // 状态标记
		 * request.setNotNeedReqStrStateChgReason(EcsOrderConsts.
		 * STATE_CHG_REASON_DELIVERED); // 变更原因
		 * request.setNotNeedReqStrStateChgDesc("发货完成"); // 变更描述 }
		 */ else if (flow_trace_id.equals(EcsOrderConsts.DIC_ORDER_NODE_L)) { // 订单归档通知回单
			request.setNotNeedReqStrStateTag(EcsOrderConsts.STATE_CHG_RECEIPT); // 状态标记
			request.setNotNeedReqStrStateChgReason(EcsOrderConsts.STATE_CHG_RECEIPT); // 变更原因
			request.setNotNeedReqStrStateChgDesc("回单完成"); // 变更描述
		} else if (flow_trace_id.equals(EcsOrderConsts.DIC_ORDER_NODE_J)) { // 资料归档通知归档完成
			request.setNotNeedReqStrStateTag(EcsOrderConsts.STATE_CHG_REASON_FILEED); // 状态标记
			request.setNotNeedReqStrStateChgReason(EcsOrderConsts.STATE_CHG_REASON_FILEED); // 变更原因
			request.setNotNeedReqStrStateChgDesc("归档完成"); // 变更描述
		}
		if (StringUtils.isNotBlank(request.getNotNeedReqStrStateTag()) && StringUtils.isNotBlank(request.getNotNeedReqStrStateChgReason())) {
			/*
			 * if (EcsOrderConsts.STATE_CHG_REASON_DELIVERED.equals(request.
			 * getNotNeedReqStrStateTag())) { // 发货走实时 ZteClient client =
			 * ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
			 * StateSynToZBResponse response = client.execute(request,
			 * StateSynToZBResponse.class);
			 * if(!EcsOrderConsts.INF_RESP_CODE_0000.equals(response.getRespCode
			 * ())) { CommonTools.addBusiError("-1",
			 * "发货通知总部失败(可能总部已退单)："+response.getRespDesc());
			 * resp.setError_code(response.getRespCode());
			 * resp.setError_msg("执行失败"); } else { resp.setError_code("0");
			 * resp.setError_msg("执行成功"); } } else {
			 */
			try {
				JSONObject json = new JSONObject();
				json.put("stateTag", request.getNotNeedReqStrStateTag());
				json.put("stateChgReason", request.getNotNeedReqStrStateChgReason());
				json.put("stateChgDesc", request.getNotNeedReqStrStateChgDesc());

				// 添加到定时任务队列
				CoQueue queBak = new CoQueue();
				queBak.setService_code("CO_NOTIFYSTATUS_ZB"); // service_code改为老系统
				queBak.setCo_id("");
				queBak.setCo_name("订单状态同步总部商城");
				queBak.setObject_id(order_id);
				queBak.setObject_type("DINGDAN");
				queBak.setContents(json.toString());
				queBak.setStatus(Consts.CO_QUEUE_STATUS_WFS);
				String create_date = null;
				if (EcsOrderConsts.STATE_CHG_REASON_DELIVERED.equals(request.getNotNeedReqStrStateTag())) { // //开户完成、发货状态同步+5秒、发货信息同步+10秒，定时任务执行的时候按时间排序执行
					create_date = DateUtil.addDate(DateUtil.getTime2(), DateUtil.DATE_FORMAT_2, ZjEcsOrderConsts.ZS_ES_CO_QUEUE_TIME_STATE_SYN, "second");
					logger.info("create_date  " + DateUtil.getTime2());
					logger.info("create_date  2  秒 " + create_date);
				} else {
					create_date = DateUtil.getTime2();
				}

				queBak.setCreated_date(create_date);
				coQueueManager.add(queBak);

				resp.setError_code("0");
				resp.setError_msg("执行成功,后台将会自动同步状态至总部商城");
			} catch (Exception e) {
				e.printStackTrace();
				CommonTools.addBusiError("-9999", "es_co_queue定时任务插入失败;" + e.getMessage());
			}
			// 测试联调时使用
			/*
			 * ZteClient client =
			 * ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
			 * String ret_msg = ""; StateSynToZBResponse response =
			 * client.execute(request, StateSynToZBResponse.class); if
			 * (response.getOrders()!=null&&response.getOrders().size()>0) {
			 * ret_msg = response.getOrders().get(0).getRespDesc(); }else{
			 * ret_msg = response.getRespDesc(); } if (ret_msg!=null) ret_msg =
			 * ret_msg.length()>100?ret_msg.substring(0, 99):ret_msg;
			 * if(!EcsOrderConsts.INF_RESP_CODE_0000.equals(response.getRespCode
			 * ())) {
			 * CommonTools.addBusiError("-9999","调用订单状态同步接口失败，失败信息"+"code:"+
			 * response.getRespCode()+";msg:"+ret_msg); }else{
			 * resp.setError_code("0"); resp.setError_msg("执行成功!"); }
			 */
			// }
		} else {
			CommonTools.addBusiError("-9999", "未匹配到正确状态");
		}
		return resp;
	}

	/**
	 * ZX add 2016-01-05 end 订单状态通知
	 */

	public IOrdWriteCardTacheManager getOrdWriteCardTacheManager() {
		return ordWriteCardTacheManager;
	}

	public void setOrdWriteCardTacheManager(IOrdWriteCardTacheManager ordWriteCardTacheManager) {
		this.ordWriteCardTacheManager = ordWriteCardTacheManager;
	}

	public IOrdShipTacheManager getOrdShipTacheManager() {
		return ordShipTacheManager;
	}

	public void setOrdShipTacheManager(IOrdShipTacheManager ordShipTacheManager) {
		this.ordShipTacheManager = ordShipTacheManager;
	}

	public IEcsOrdManager getEcsOrdManager() {
		return ecsOrdManager;
	}

	public void setEcsOrdManager(IEcsOrdManager ecsOrdManager) {
		this.ecsOrdManager = ecsOrdManager;
	}

	public ICommonManager getCommonManager() {
		return commonManager;
	}

	public void setCommonManager(ICommonManager commonManager) {
		this.commonManager = commonManager;
	}

	public ICacheUtil getCacheUtil() {
		return cacheUtil;
	}

	public void setCacheUtil(ICacheUtil cacheUtil) {
		this.cacheUtil = cacheUtil;
	}

	/**
	 * BSS使用人数量校验接口
	 * 
	 * @param busiCompRequest
	 * @return
	 * @throws ApiBusiException
	 */
	@ZteMethodAnnontion(name = "[BSS]BSS使用人数量校验接口", group_name = "通用组件", order = "14", page_show = true, path = "zteCommonTraceRule.userCountCheckFromBSS")
	public BusiCompResponse userCountCheckFromBSS(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		BusiDealResult dealResult = ordVisitTacheManager.userCountCheckFromBSS(order_id);
		if (!StringUtils.equals(dealResult.getError_code(), EcsOrderConsts.BUSI_DEAL_RESULT_0000)) {
			CommonTools.addBusiError(dealResult.getError_code(), dealResult.getError_msg());
		}
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		resp.setResponse(dealResult.getResponse());
		return resp;
	}

	/**
	 * 线上退款通知总部
	 * 
	 * @param busiCompRequest
	 * @return
	 * @throws ApiBusiException
	 */
	@ZteMethodAnnontion(name = "线上退款通知总部", group_name = "通用组件", order = "14", page_show = true, path = "zteCommonTraceRule.refundOnlineToZB")
	public BusiCompResponse refundOnlineToZB(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		StateUtil vo = new StateUtil();
		vo.setNotNeedReqStrStateTag(EcsOrderConsts.STATE_CHG_ONLINE); // 状态标记
		vo.setNotNeedReqStrStateChgReason(EcsOrderConsts.STATE_CHG_REASON_CANLODR); // 变更原因
		vo.setNotNeedReqStrStateChgDesc("线上退款"); // 变更描述
		BusiDealResult dealResult = ordVisitTacheManager.StatusNoticeZB(order_id, vo);
		if (!StringUtils.equals(dealResult.getError_code(), EcsOrderConsts.BUSI_DEAL_RESULT_0000)) {
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.BSS_REFUND_STATUS }, new String[] { EcsOrderConsts.BSS_REFUND_STATUS_ONLINE_FAIL });

			CommonTools.addBusiError(dealResult.getError_code(), dealResult.getError_msg());
		} else {
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.BSS_REFUND_STATUS }, new String[] { EcsOrderConsts.BSS_REFUND_STATUS_ONLINE_SUCC });
		}
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		resp.setResponse(dealResult.getResponse());
		return resp;
	}

	/**
	 * 线下退款通知总部
	 * 
	 * @param busiCompRequest
	 * @return
	 * @throws ApiBusiException
	 */
	@ZteMethodAnnontion(name = "线下退款通知总部", group_name = "通用组件", order = "14", page_show = true, path = "zteCommonTraceRule.refundOfflineToZB")
	public BusiCompResponse refundOfflineToZB(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		StateUtil vo = new StateUtil();
		vo.setNotNeedReqStrStateTag(EcsOrderConsts.STATE_CHG_OFFLINE); // 状态标记
		vo.setNotNeedReqStrStateChgReason(EcsOrderConsts.STATE_CHG_REASON_CANLODR); // 变更原因
		vo.setNotNeedReqStrStateChgDesc("线下退款"); // 变更描述
		BusiDealResult dealResult = ordVisitTacheManager.StatusNoticeZB(order_id, vo);
		if (!StringUtils.equals(dealResult.getError_code(), EcsOrderConsts.BUSI_DEAL_RESULT_0000)) {
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.BSS_REFUND_STATUS }, new String[] { EcsOrderConsts.BSS_REFUND_STATUS_OFFLINE_FAIL });

			CommonTools.addBusiError(dealResult.getError_code(), dealResult.getError_msg());
		} else {
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.BSS_REFUND_STATUS }, new String[] { EcsOrderConsts.BSS_REFUND_STATUS_OFFLINE_SUCC });
		}
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		resp.setResponse(dealResult.getResponse());
		return resp;
	}

	@ZteMethodAnnontion(name = "[爬虫]总商退单申请提交", group_name = "通用组件", order = "20", page_show = true, path = "zteCommonTraceRule.zsRefundApply")
	public BusiCompResponse zsRefundApply(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();

		return resp;
	}

	/**
	 * 线上退款通知新商城
	 * 
	 * @param busiCompRequest
	 * @return
	 * @throws ApiBusiException
	 */
	@ZteMethodAnnontion(name = "线上退款通知新商城", group_name = "通用组件", order = "14", page_show = true, path = "zteCommonTraceRule.refundOnlineToWYG")
	public BusiCompResponse refundOnlineToWYG(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		StateUtil vo = new StateUtil();
		vo.setNotNeedReqStrStateTag(EcsOrderConsts.DIC_ORDER_NODE_R); // 状态标记
		vo.setNotNeedReqStrStateChgDesc(EcsOrderConsts.DIC_ORDER_NODE_R_DESC); // 变更描述
		BusiDealResult dealResult = commonManager.ordFlowTraceSyn(order_id, vo);
		if (!dealResult.getError_code().equals(EcsOrderConsts.BUSI_DEAL_RESULT_0000)) {
			CommonTools.addBusiError(dealResult.getError_code(), dealResult.getError_msg());
		}

		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}

	/**
	 * 线下退款通知新商城
	 * 
	 * @param busiCompRequest
	 * @return
	 * @throws ApiBusiException
	 */
	@ZteMethodAnnontion(name = " 线下退款通知新商城", group_name = "通用组件", order = "14", page_show = true, path = "zteCommonTraceRule.refundOfflineToWYG")
	public BusiCompResponse refundOfflineToWYG(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		StateUtil vo = new StateUtil();
		vo.setNotNeedReqStrStateTag(EcsOrderConsts.DIC_ORDER_NODE_S); // 状态标记
		vo.setNotNeedReqStrStateChgDesc(EcsOrderConsts.DIC_ORDER_NODE_S_DESC); // 变更描述
		BusiDealResult dealResult = commonManager.ordFlowTraceSyn(order_id, vo);
		if (!dealResult.getError_code().equals(EcsOrderConsts.BUSI_DEAL_RESULT_0000)) {
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.BSS_REFUND_STATUS }, new String[] { EcsOrderConsts.BSS_REFUND_STATUS_OFFLINE_FAIL });
			CommonTools.addBusiError(dealResult.getError_code(), dealResult.getError_msg());
		} else {
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.BSS_REFUND_STATUS }, new String[] { EcsOrderConsts.BSS_REFUND_STATUS_OFFLINE_SUCC });
		}
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}

	/**
	 * 匹配BSS号码段
	 * 
	 * @param busiCompRequest
	 * @return
	 */
	/*@ZteMethodAnnontion(name = "匹配BSS号码段", group_name = "通用组件", order = "40", page_show = true, path = "zteCommonTraceRule.matchingPhoneNumBss")
	public BusiCompResponse matchingPhoneNumBss(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		// 获取规则对象
		Map params = busiCompRequest.getQueryParams();
		TacheFact fact = (TacheFact) params.get("fact");
		String phone_para_bss = fact.getPhone_para();
		String order_id = busiCompRequest.getOrder_id();

		CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.PHONE_PARA_BSS }, new String[] { "," + phone_para_bss + "," });

		resp.setFact(fact);
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}*/

	/**
	 * 匹配ESS号码段
	 * 
	 * @param busiCompRequest
	 * @return
	 */
	/*@ZteMethodAnnontion(name = "匹配ESS号码段", group_name = "通用组件", order = "40", page_show = true, path = "zteCommonTraceRule.matchingPhoneNumEss")
	public BusiCompResponse matchingPhoneNumEss(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		// 获取规则对象
		Map params = busiCompRequest.getQueryParams();
		TacheFact fact = (TacheFact) params.get("fact");
		String phone_para_ess = fact.getPhone_para();
		String order_id = busiCompRequest.getOrder_id();

		CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.PHONE_PARA_ESS }, new String[] { "," + phone_para_ess + "," });

		resp.setFact(fact);
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}*/

	/**
	 * 出库信息回传SAP
	 * 
	 * @param busiCompRequest
	 * @return
	 */
	@ZteMethodAnnontion(name = "出库信息回传SAP", group_name = "通用组件", order = "41", page_show = true, path = "zteCommonTraceRule.deliveNotifyHS")
	public BusiCompResponse deliveNotifyHS(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		BusiDealResult dealResult = commonManager.deliveNotifyHS(order_id);
		if (!dealResult.getError_code().equals(EcsOrderConsts.BUSI_DEAL_RESULT_0000)) {
			CommonTools.addBusiError(dealResult.getError_code(), dealResult.getError_msg());
		}

		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}

	/**
	 * 退货入库传输SAP
	 * 
	 * @param busiCompRequest
	 * @return
	 */
	@ZteMethodAnnontion(name = "退货入库传输SAP", group_name = "通用组件", order = "42", page_show = true, path = "zteCommonTraceRule.returnWarehousingHS")
	public BusiCompResponse returnWarehousingHS(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		BusiDealResult dealResult = commonManager.returnWarehousingHS(order_id);
		if (!dealResult.getError_code().equals(EcsOrderConsts.BUSI_DEAL_RESULT_0000)) {
			CommonTools.addBusiError(dealResult.getError_code(), dealResult.getError_msg());
		}

		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}

	/**
	 * 退货入库传输SAP
	 * 
	 * @param busiCompRequest
	 * @return
	 */
	@ZteMethodAnnontion(name = "仓储商采购退货订单出库回传SAP接口", group_name = "通用组件", order = "43", page_show = true, path = "zteCommonTraceRule.orderCheckOutB2B")
	public BusiCompResponse orderCheckOutB2B(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		BusiDealResult dealResult = commonManager.orderCheckOutB2B(order_id);
		if (!dealResult.getError_code().equals(EcsOrderConsts.BUSI_DEAL_RESULT_0000)) {
			CommonTools.addBusiError(dealResult.getError_code(), dealResult.getError_msg());
		}

		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}

	/**
	 * 自动启动生产（即自动客户回访）
	 * 
	 * @param busiCompRequest
	 * @return
	 */
	@ZteMethodAnnontion(name = "自动启动生产（即自动客户回访）", group_name = "通用组件", order = "44", page_show = true, path = "zteCommonTraceRule.autoStartProduction")
	public BusiCompResponse autoStartProduction(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();

		BusiCompRequest busiCompReq = new BusiCompRequest();
		busiCompReq.setOrder_id(order_id);
		Map queryParams = new HashMap();
		queryParams.put("order_id", order_id);
		queryParams.put("is_auto", EcsOrderConsts.RULE_EXE_ALL);
		busiCompReq.setQueryParams(queryParams);
		busiCompReq.setEnginePath("enterTraceRule.exec");
		BusiCompResponse busiCompResp = CommonDataFactory.getInstance().execBusiComp(busiCompReq);
		BusiCompResponse busiResp = CommonDataFactory.getInstance().getRuleTreeresult(busiCompResp);
		if (!ConstsCore.ERROR_SUCC.equals(busiResp.getError_code())) {
			CommonTools.addBusiError("-9999", busiResp.getError_msg());
		}

		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}

	/**
	 * 修改订单状态（全部归档）
	 * 
	 * @throws ApiBusiException
	 */
	/**
	 * PC批量写卡队列匹配设置
	 * 
	 * @throws ApiBusiException
	 */
	@ZteMethodAnnontion(name = "PC批量写卡队列匹配设置", group_name = "开户环节", order = "47", page_show = true, path = "zteCommonTraceRule.writeCardSetQueueByPc")
	public BusiCompResponse writeCardSetQueueByPc(BusiCompRequest busiCompRequest) throws ApiBusiException {
		this.init();
		BusiCompResponse resp = new BusiCompResponse();
		BusiDealResult result = new BusiDealResult();
		String order_id = busiCompRequest.getOrder_id();
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		OrderExtBusiRequest order_ext = orderTree.getOrderExtBusiRequest();

		try {
			Map params = busiCompRequest.getQueryParams();
			TacheFact fact = (TacheFact) params.get("fact");
			result = ordOpenAccountTacheManager.writeCardSetQueueByPc(order_id, fact.getCalc_input());
			if (!StringUtils.equals(result.getError_code(), EcsOrderConsts.BUSI_DEAL_RESULT_0000)) {
				CommonTools.addBusiError(result.getError_code(), result.getError_msg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			// 异常则转人工集中生产模式
			String new_order_model = null;
			String order_model = order_ext.getOrder_model();
			if (EcsOrderConsts.ORDER_MODEL_08.equals(order_model)) {
				new_order_model = EcsOrderConsts.ORDER_MODEL_07;
			} else if (EcsOrderConsts.ORDER_MODEL_06.equals(order_model)) {
				new_order_model = EcsOrderConsts.ORDER_MODEL_02;
			}
			order_ext.setOrder_model(new_order_model);
			order_ext.setOld_order_model(order_model);
			order_ext.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			order_ext.setDb_action(ConstsCore.DB_ACTION_UPDATE);
			order_ext.store();
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.ORDER_MODEL }, new String[] { new_order_model });

			// 未匹配到队列记录es_order_handlog日志
			InsertOrderHandLogReq logReq = new InsertOrderHandLogReq();
			logReq.setOrder_id(order_id);
			logReq.setOp_id("1");
			logReq.setOp_name("超级管理员");
			logReq.setFlow_id(order_ext.getFlow_id());
			logReq.setFlow_trace_id(order_ext.getFlow_trace_id());
			logReq.setHandler_type(com.ztesoft.ibss.common.util.Const.ORDER_HANDLER_TYPE_DIC_HANDLE_TYPE);
			logReq.setType_code("");
			logReq.setComments("匹配到PC批量写卡队列异常,转自动化生产模式处理!");
			logReq.setCreate_time("sysdate");
			logReq.setSource_from(ManagerUtils.getSourceFrom());
			CommonDataFactory.getInstance().insertOrderHandlerLogs(logReq);

		}
		resp.setResponse(result.getResponse());
		resp.setError_code("0");
		resp.setError_msg("PC批量写卡队列匹配设置");
		return resp;
	}

	@ZteMethodAnnontion(name = "修改订单状态（全部归档）", group_name = "通用组件", order = "45", page_show = true, path = "zteCommonTraceRule.setOrderStatusAllArchive")
	public BusiCompResponse setOrderStatusAllArchive(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		BusiDealResult dealResult = commonManager.setOrderStatusAllArchive(order_id);
		if (!ConstsCore.ERROR_SUCC.equals(dealResult.getError_code())) {
			CommonTools.addBusiError(dealResult.getError_code(), dealResult.getError_msg());
		}
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;

	}

	@ZteMethodAnnontion(name = "BSS返销订单状态查询", group_name = "通用组件", order = "46", page_show = true, path = "zteCommonTraceRule.cancelOrderStatusQry")
	public BusiCompResponse cancelOrderStatusQry(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		BusiDealResult dealResult = commonManager.cancelOrderStatusQry(order_id);
		if (!ConstsCore.ERROR_SUCC.equals(dealResult.getError_code())) {
			CommonTools.addBusiError(dealResult.getError_code(), dealResult.getError_msg());
		}
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;

	}

	@ZteMethodAnnontion(name = "BSS工号转CB工号", group_name = "通用组件", order = "47", page_show = true, path = "zteCommonTraceRule.tabuserBtocbSub")
	public BusiCompResponse tabuserBtocbSub(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		BusiDealResult dealResult = commonManager.tabuserBtocbSub(order_id);
		if (!ConstsCore.ERROR_SUCC.equals(dealResult.getError_code())) {
			String order_model = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest().getOrder_model();
			if (!StringUtils.isEmpty(order_model) && StringUtil.equals("06", order_model)) {
				Map params = new HashMap();
				BusiCompRequest bcr = new BusiCompRequest();
				bcr.setEnginePath("zteCommonTraceRule.markedException");
				bcr.setOrder_id(order_id);
				params.put(EcsOrderConsts.EXCEPTION_FROM, EcsOrderConsts.EXCEPTION_FROM_ORD);
				params.put(EcsOrderConsts.NOTIFY_SR_DK, EcsOrderConsts.NOTIFY_SR_DK_VAL);
				params.put(EcsOrderConsts.EXCEPTION_TYPE, "");
				params.put(EcsOrderConsts.EXCEPTION_REMARK, dealResult.getError_msg());
				bcr.setQueryParams(params);
				CommonDataFactory.getInstance().execBusiComp(bcr);
			}
			CommonTools.addBusiError(dealResult.getError_code(), dealResult.getError_msg());

		}
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;

	}

	@ZteMethodAnnontion(name = "二次选占", group_name = "通用组件", order = "48", page_show = true, path = "zteCommonTraceRule.resourCecenterApp")
	public BusiCompResponse resourCecenterApp(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		BusiDealResult dealResult = commonManager.resourCecenterApp(order_id);
		if (!ConstsCore.ERROR_SUCC.equals(dealResult.getError_code())) {
			String order_model = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest().getOrder_model();
			if (!StringUtils.isEmpty(order_model) && StringUtil.equals("06", order_model)) {
				Map params = new HashMap();
				BusiCompRequest bcr = new BusiCompRequest();
				bcr.setEnginePath("zteCommonTraceRule.markedException");
				bcr.setOrder_id(order_id);
				params.put(EcsOrderConsts.EXCEPTION_FROM, EcsOrderConsts.EXCEPTION_FROM_ORD);
				params.put(EcsOrderConsts.NOTIFY_SR_DK, EcsOrderConsts.NOTIFY_SR_DK_VAL);
				params.put(EcsOrderConsts.EXCEPTION_TYPE, "");
				params.put(EcsOrderConsts.EXCEPTION_REMARK, dealResult.getError_msg());
				bcr.setQueryParams(params);
				CommonDataFactory.getInstance().execBusiComp(bcr);
			}
			CommonTools.addBusiError(dealResult.getError_code(), dealResult.getError_msg());

		}
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;

	}

	@ZteMethodAnnontion(name = "状态同步码上购", group_name = "通用组件", order = "49", page_show = true, path = "zteCommonTraceRule.o2OStatusUpdate")
	public BusiCompResponse o2OStatusUpdate(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = getOrderId(busiCompRequest);
		O2OStatusUpdateReq req = new O2OStatusUpdateReq();
		req.setNotNeedReqStrOrderId(order_id);
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		O2OStatusUpdateResp refundResp = client.execute(req, O2OStatusUpdateResp.class);
		if (!EcsOrderConsts.INF_RESP_CODE_00000.equals(refundResp.getError_code())) {
			CommonTools.addBusiError(EcsOrderConsts.BUSI_DEAL_RESULT_FAIL, refundResp.getError_msg());
		} else {
			resp.setError_code("0");
			resp.setError_msg("执行成功");
		}

		return resp;

	}

	@ZteMethodAnnontion(name = "活动费用查询", group_name = "通用组件", order = "49", page_show = true, path = "zteCommonTraceRule.schemeFeeQry")
	public BusiCompResponse schemeFeeQry(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = getOrderId(busiCompRequest);
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		SchemeFeeQryResp rsp = new SchemeFeeQryResp();
		SchemeFeeQryReq req = new SchemeFeeQryReq();
		req.setNotNeedReqStrOrderId(order_id);
		// 接口调用开户申请
		rsp = client.execute(req, SchemeFeeQryResp.class);
		if (!EcsOrderConsts.INF_RESP_CODE_00000.equals(rsp.getCode())) {
			CommonTools.addBusiError(EcsOrderConsts.BUSI_DEAL_RESULT_FAIL, rsp.getError_msg());
		} else {
			OrderItemExtBusiRequest orderItemExtBusiRequest = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderItemBusiRequests().get(0).getOrderItemExtBusiRequest();
			orderItemExtBusiRequest.setPhone_deposit(rsp.getRespJson().getScheme_fee());
			;
			orderItemExtBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			orderItemExtBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
			orderItemExtBusiRequest.store();
			resp.setError_code("0");
			resp.setError_msg("执行成功");
		}

		return resp;

	}

	// --songqi
	@ZteMethodAnnontion(name = "营业费用查询", group_name = "通用组件", order = "49", page_show = true, path = "zteCommonTraceRule.businessFeeQry")
	public BusiCompResponse businessFeeQry(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = getOrderId(busiCompRequest);
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());

		IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");

		BusinessFeeQryResp rsp = new BusinessFeeQryResp();
		BusinessFeeQryReq req = new BusinessFeeQryReq();
		req.setNotNeedReqStrOrderId(order_id);

		List<Map<String, String>> serviceList = new ArrayList<Map<String, String>>();
		String service_type = "";
		// 沃TV数量下限大于0 ，则调两次营业查询接口
		String woTVNum = CommonDataFactory.getInstance().getGoodSpec(order_id, "", "wotv_qty_lower");
		if (woTVNum == null || woTVNum.equals("")) {
			woTVNum = "0";
		}
		if (Integer.valueOf(woTVNum) > 0) {
			// 先查沃TV营业费用 service_type = "6130"
			service_type = "6130";
			Map<String, String> map = new HashMap<String, String>();
			map.put(service_type, "TV");
			serviceList.add(map);
		}

		// 宽带数量
		String kdNum = CommonDataFactory.getInstance().getGoodSpec(order_id, "", "broadband_qty_lower");

		if (kdNum == null || kdNum.equals("")) {
			kdNum = "0";
		}
		if (Integer.valueOf(kdNum) > 0) {
			Map<String, String> map = new HashMap<String, String>();
			service_type = CommonDataFactory.getInstance().getGoodSpec(order_id, "", AttrConsts.ORDER_SERVICE_TYPE);
			map.put(service_type, "KD");
			serviceList.add(map);
		}
		List<AttrFeeInfoBusiRequest> feeInfoBusiRequests = CommonDataFactory.getInstance()
				.getOrderTree(req.getNotNeedReqStrOrderId()).getFeeInfoBusiRequests();
		for (int i = 0; i < feeInfoBusiRequests.size(); i++) {
			/*if (!StringUtils.isEmpty(feeInfoBusiRequests.get(i).getIs_aop_fee())
					&& StringUtil.equals("2", feeInfoBusiRequests.get(i).getIs_aop_fee())) {*/
				feeInfoBusiRequests.get(i).setIs_dirty(ConstsCore.IS_DIRTY_YES);
				feeInfoBusiRequests.get(i).setDb_action(ConstsCore.DB_ACTION_DELETE);
				feeInfoBusiRequests.get(i).store();
			/*}*/
		}
		for (int i = 0; i < serviceList.size(); i++) {
			service_type = serviceList.get(i).keySet().toString().replace("[", "");
			service_type = service_type.replace("]", "");
			req.setService_type(service_type);

			rsp = client.execute(req, BusinessFeeQryResp.class);

			if (!EcsOrderConsts.INF_RESP_CODE_00000.equals(rsp.getCode())) {
				resp.setError_code(EcsOrderConsts.BUSI_DEAL_RESULT_FAIL);
				resp.setError_msg(rsp.getMsg());

				return resp;
			} else {
				// 业务逻辑保存营业费用
				List<BusinessFeeVO> fee_list = rsp.getRespJson().getFee_list();
				Double need_amount = 0.00;
				for (BusinessFeeVO businessFeeVO : fee_list) {
					AttrFeeInfoBusiRequest attrFeeInfoBusiRequest = new AttrFeeInfoBusiRequest();
					attrFeeInfoBusiRequest.setFee_inst_id(baseDaoSupport.getSequences("seq_attr_fee_info"));
					attrFeeInfoBusiRequest.setOrder_id(order_id);// 订单id
					attrFeeInfoBusiRequest.setInst_id(order_id);
					attrFeeInfoBusiRequest.setFee_item_code(businessFeeVO.getSubject_id());// 账本id
					attrFeeInfoBusiRequest.setFee_item_name(businessFeeVO.getFee_desc());// 费用名称
					attrFeeInfoBusiRequest.setO_fee_num(Double.valueOf(businessFeeVO.getNeed_amount()).doubleValue());// 应收
					attrFeeInfoBusiRequest.setDiscount_fee(Double.valueOf(businessFeeVO.getDeration_amount()).doubleValue());// 减免金额
					attrFeeInfoBusiRequest.setDiscount_reason("");// 减免原因
					attrFeeInfoBusiRequest.setN_fee_num(Double.valueOf(businessFeeVO.getReal_amount()).doubleValue());// 实收
					attrFeeInfoBusiRequest.setOrigin_mall(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ORDER_FROM));
					attrFeeInfoBusiRequest.setFee_category(businessFeeVO.getFee_rule_id());
					attrFeeInfoBusiRequest.setIs_aop_fee(MapUtils.getString(serviceList.get(i), service_type));
					attrFeeInfoBusiRequest.setMax_relief(Double.valueOf(businessFeeVO.getMax_deration_value()).doubleValue());// 最大减免金额

					attrFeeInfoBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
					attrFeeInfoBusiRequest.setDb_action(ConstsCore.DB_ACTION_INSERT);
					attrFeeInfoBusiRequest.store();
				}

			}

		}
		resp.setError_code("0");
		resp.setError_msg("执行成功");

		return resp;
	}

	@ZteMethodAnnontion(name = "BSS产品受理", group_name = "通用组件", order = "50", page_show = true, path = "zteCommonTraceRule.promoteProductBSS")
	public BusiCompResponse promoteProductBSS(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = getOrderId(busiCompRequest);
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		PromoteProductResp rsp = new PromoteProductResp();
//		PromoteProductReq req = new PromoteProductReq();
//		req.setNotNeedReqStrOrderId(order_id);
		
		Map params = busiCompRequest.getQueryParams();
		TacheFact fact = (TacheFact) params.get("fact");
		PromoteProductReq req = null;
		
		if(fact.getRequest()!=null && fact.getRequest() instanceof PromoteProductReq){
			req = (PromoteProductReq) fact.getRequest();
		}else{
			req = new PromoteProductReq();
			req.setNotNeedReqStrOrderId(order_id);
		}
			
		// 接口调用开户申请
		rsp = client.execute(req, PromoteProductResp.class);
		if (!EcsOrderConsts.INF_RESP_CODE_00000.equals(rsp.getCode())) {
			CommonTools.addBusiError(EcsOrderConsts.BUSI_DEAL_RESULT_FAIL, rsp.getMsg());
		} else {
			OrderItemExtBusiRequest orderItemExtBusiRequest = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderItemBusiRequests().get(0).getOrderItemExtBusiRequest();
			orderItemExtBusiRequest.setBssOrderId(rsp.getRespJson().getBms_accept_id());
			orderItemExtBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			orderItemExtBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
			orderItemExtBusiRequest.store();
			resp.setError_code("0");
			resp.setError_msg("执行成功");
		}

		return resp;

	}

	@ZteMethodAnnontion(name = "获取宽带账号/编码", group_name = "通用组件", order = "51", page_show = true, path = "zteCommonTraceRule.kdnumberQry")
	public BusiCompResponse kdnumberQry(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = getOrderId(busiCompRequest);
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		KdnumberQryResp rsp = new KdnumberQryResp();
		KdnumberQryReq req = new KdnumberQryReq();
		req.setNotNeedReqStrOrderId(order_id);
		// 接口调用开户申请
		rsp = client.execute(req, KdnumberQryResp.class);
		if (!EcsOrderConsts.INF_RESP_CODE_0000.equals(rsp.getRespJson().getRESP_CODE())) {
			CommonTools.addBusiError(EcsOrderConsts.BUSI_DEAL_RESULT_FAIL, rsp.getRespJson().getRESP_DESC());
		} else {
			OrderAdslBusiRequest orderAdslBusiRequest = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderAdslBusiRequest().get(0);
			orderAdslBusiRequest.setAdsl_number(rsp.getRespJson().getACCT_INFO().getAUTH_ACCT_ID());
			orderAdslBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			orderAdslBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
			orderAdslBusiRequest.store();
			resp.setError_code("0");
			resp.setError_msg("执行成功");
		}

		return resp;

	}

	@ZteMethodAnnontion(name = "客户资料与订单绑定", group_name = "通用组件", order = "52", page_show = true, path = "zteCommonTraceRule.custInfoOrderBind")
	public BusiCompResponse custInfoOrderBind(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = getOrderId(busiCompRequest);
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		CustInfoOrderBindResp rsp = new CustInfoOrderBindResp();
		CustInfoOrderBindReq req = new CustInfoOrderBindReq();
		req.setOrder_id(order_id);
		rsp = client.execute(req, CustInfoOrderBindResp.class);

		if (!EcsOrderConsts.INF_RESP_CODE_00000.equals(rsp.getCode()) && !"00503".equals(rsp.getCode())) {
			CommonTools.addBusiError(EcsOrderConsts.BUSI_DEAL_RESULT_FAIL, rsp.getMsg());
		} else {
			resp.setError_code("0");
			resp.setError_msg("执行成功");
		}

		return resp;
	}
	

	@ZteMethodAnnontion(name = "新老客户校验", group_name = "通用组件", order = "54", page_show = true, path = "zteCommonTraceRule.oldUserCheck")
	public BusiCompResponse oldUserCheck(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = getOrderId(busiCompRequest);
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		OldUserCheckResp rsp = new OldUserCheckResp();
		OldUserCheckReq req = new OldUserCheckReq();
		req.setNotNeedReqStrOrderId(order_id);
		rsp = client.execute(req, OldUserCheckResp.class);
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		if ("00000".equals(rsp.getCode())) {// 老客户
			OldUserCheckVO respJson = rsp.getRespJson();
			List<OldUserInfo> old_user_info = respJson.getOld_user_info();
			for (OldUserInfo userInfo : old_user_info) {
				if (!StringUtil.isEmpty(userInfo.getSubs_id())) {
					Map map = new HashMap();
					Map mapwhere = new HashMap();
					map.put("subs_id", userInfo.getSubs_id());
					mapwhere.put("order_id", order_id);
					IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
					baseDaoSupport.update("es_order_extvtl", map, mapwhere);
				}
			}
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.IS_OLD }, new String[] { "1" });
		} else if ("99999".equals(rsp.getCode())) {// 新客户
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.IS_OLD }, new String[] { "0" });
		} else {// 异常
			resp.setError_code(rsp.getCode());
			resp.setError_msg(rsp.getMsg());
			CommonTools.addBusiError(rsp.getCode(), rsp.getMsg());
		}
		return resp;
	}

	@ZteMethodAnnontion(name = "光猫ID查询", group_name = "通用组件", order = "55", page_show = true, path = "zteCommonTraceRule.qryFtthObjId")
	public BusiCompResponse qryFtthObjId(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = getOrderId(busiCompRequest);
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		QryFtthObjIDResp rsp = new QryFtthObjIDResp();
		QryFtthObjIdReq req = new QryFtthObjIdReq();
		req.setNotNeedReqStrOrderId(order_id);
		rsp = client.execute(req, QryFtthObjIDResp.class);

		if(rsp.getCode().equals(EcsOrderConsts.INF_RESP_CODE_00000)) {
			resp.setError_code("0");
			resp.setError_msg("执行成功");
		}else {
			resp.setError_code("-9999");
			resp.setError_msg("查询失败");
		}
		return resp;
	}

	@ZteMethodAnnontion(name = "宽带账号校验", group_name = "通用组件", order = "56", page_show = true, path = "zteCommonTraceRule.kdNumberCheck")
	public BusiCompResponse kdNumberCheck(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = getOrderId(busiCompRequest);
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		String AUTH_ACCT_ID = "";// 宽带认证账号
		String SERIAL_NUMBER = "";// 宽带统一编码
		if (orderTree.getOrderAdslBusiRequest().size() > 0) {
			AUTH_ACCT_ID = orderTree.getOrderAdslBusiRequest().get(0).getAdsl_account();
			SERIAL_NUMBER = orderTree.getOrderAdslBusiRequest().get(0).getAdsl_number();
		}
		if (StringUtil.isEmpty(SERIAL_NUMBER)) {
			resp.setError_code("0");
			resp.setError_msg("账号为空");
		}else{
			ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
			KdNumberCheckResp rsp = new KdNumberCheckResp();
			KdNumberCheckReq req = new KdNumberCheckReq();
			req.setNotNeedReqStrOrderId(order_id);
			rsp = client.execute(req, KdNumberCheckResp.class);
			boolean flag = false;
			if ("00000".equals(rsp.getCode())) {
				if (null != rsp.getRespJson()) {
					if ("0000".equals(rsp.getRespJson().getRESP_CODE())) {
						flag = true;
					}
				}
			}
			if(!flag){
				Map map = new HashMap();
				Map mapwhere = new HashMap();
				map.put("adsl_number", "");
				map.put("adsl_account", "");
				mapwhere.put("order_id", order_id);
				IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
				baseDaoSupport.update("es_order_zhwq_adsl", map, mapwhere);
				resp.setError_msg("宽带账号不唯一");
			}else{
				resp.setError_msg("账号校验成功");
			}
			resp.setResponse(rsp);
			resp.setError_code("0");
		}
		return resp;
	}
	
	@ZteMethodAnnontion(name="赠品查询",group_name="通用组件",order="57",page_show=true,path="zteCommonTraceRule.objectQry")
	public BusiCompResponse objectQry(BusiCompRequest busiCompRequest) throws ApiBusiException{
		BusiCompResponse resp = new BusiCompResponse();
		BusiDealResult dealResult= commonManager.objectQry(busiCompRequest.getOrder_id());
		if(!EcsOrderConsts.INF_RESP_CODE_0000.equals(dealResult.getError_code()))
			CommonTools.addBusiError(dealResult.getError_code(),dealResult.getError_msg());
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		
		return resp;
	}
	
	@ZteMethodAnnontion(name="施工进度查询",group_name="通用组件",order="57",page_show=true,path="zteCommonTraceRule.rateStatusQry")
	public BusiCompResponse rateStatusQry(BusiCompRequest busiCompRequest) throws ApiBusiException{
		BusiCompResponse resp = new BusiCompResponse();
		BusiDealResult dealResult= commonManager.rateStatusQry(busiCompRequest.getOrder_id());
		if(!EcsOrderConsts.INF_RESP_CODE_0000.equals(dealResult.getError_code()))
			CommonTools.addBusiError(dealResult.getError_code(),dealResult.getError_msg());
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		
		return resp;
	}
	
	@ZteMethodAnnontion(name="资源预判",group_name="通用组件",order="57",page_show=true,path="zteCommonTraceRule.resourcePreCheck")
	public BusiCompResponse resourcePreCheck(BusiCompRequest busiCompRequest) throws ApiBusiException{
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = getOrderId(busiCompRequest);
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		ResourcePreCheckReq req = new ResourcePreCheckReq();	
		ResourcePreCheckResp rsp = new ResourcePreCheckResp();
		req.setNotNeedReqStrOrderId(order_id);
		rsp = client.execute(req, ResourcePreCheckResp.class);
		
		String service_type = CommonDataFactory.getInstance().getGoodSpec(order_id, null, AttrConsts.ORDER_SERVICE_TYPE);
		if(rsp.getCode().equals(EcsOrderConsts.INF_RESP_CODE_00000)) {
			List<BrandInfoList> brand_list = rsp.getRespJson().getBRAND_LIST();
			String extra_info = rsp.getRespJson().getEXTRA_INFO() ;
			if(StringUtils.isNotBlank(extra_info)) {
				//Todo: 
			}
			for (BrandInfoList brandInfoList : brand_list) {
				if(brandInfoList.getBRAND_CODE().equals(service_type)) {
					resp.setError_code("0");
					resp.setError_msg("执行成功");
					
					break;
				}else {
					resp.setError_code("-9999");
					resp.setError_msg("资源预判结果与商品类型不一致。");
				}
			}
		}else {
			resp.setError_code("-9999");
			resp.setError_msg(rsp.getMsg());
		}
		return resp;
	}
	
	@ZteMethodAnnontion(name="实名校验",group_name="通用组件",order="58",page_show=true,path="zteCommonTraceRule.isRealNameCheck")
	public BusiCompResponse isRealNameCheck(BusiCompRequest busiCompRequest) throws ApiBusiException{ 
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = getOrderId(busiCompRequest);
		
		String cert_num = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.CERT_CARD_NUM);
		String cert_addr = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.CERT_ADDRESS);
		String cust_birthday = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.CERT_CARD_BIRTH);
		String cert_issuedat = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.CERT_ISSUER);
		String cert_expire_date = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.CERT_FAILURE_TIME);
		String cert_effected_date = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.CERT_EFF_TIME);
		String cert_nation = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.CERT_CARD_NATION);
		String cert_sex = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.CERTI_SEX);
		
		if(StringUtils.isNotBlank(cert_sex) && StringUtils.isNotBlank(cert_num)&& StringUtils.isNotBlank(cert_addr)&& StringUtils.isNotBlank(cust_birthday)&& StringUtils.isNotBlank(cert_issuedat)&& StringUtils.isNotBlank(cert_expire_date)&& StringUtils.isNotBlank(cert_nation)&& StringUtils.isNotBlank(cert_effected_date)) {
			resp.setError_code("0");
			resp.setError_msg("执行成功");
		}else {
			resp.setError_code("-9999");
			resp.setError_msg("实名制校验失败");
			CommonTools.addBusiError(resp.getError_code(), resp.getError_msg());
		}
		return resp;
	}
	
	@ZteMethodAnnontion(name="信息同步沃创富",group_name="通用组件",order="59",page_show=true,path="zteCommonTraceRule.selfspreadOrderinfoSyn")
	public BusiCompResponse selfspreadOrderinfoSyn(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = getOrderId(busiCompRequest);
		String qry_sql = "select decode(v.share_svc_num, '', i.share_svc_num, v.share_svc_num) as share_svc_num,decode(v.seed_user_id, '', i.seed_user_id, v.seed_user_id) as seed_user_id from es_order_extvtl v left join es_order_intent i on v.order_id = i.order_id where v.source_from = '"
				+ ManagerUtils.getSourceFrom() + "' and v.order_id = '" + order_id + "'";
		IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
		List<Map<String, String>> list = baseDaoSupport.queryForList(qry_sql);
		if (!StringUtils.isEmpty(list.get(0).get("share_svc_num"))
				|| !StringUtils.isEmpty(list.get(0).get("seed_user_id"))) {
			SelfspreadOrderinfoSynReq req = new SelfspreadOrderinfoSynReq();
			SelfspreadOrderinfoSynResp rep = new SelfspreadOrderinfoSynResp();
			ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
			req.setNotNeedReqStrOrderId(order_id);
			rep = client.execute(req, SelfspreadOrderinfoSynResp.class);
			String code = rep.getCode();
			if (!EcsOrderConsts.INF_RESP_CODE_00000.equals(code)) {
				CommonTools.addBusiError(rep.getCode(), rep.getMsg());
			}
			resp.setError_code("0");
			resp.setError_msg("执行成功!");
		} else {
			resp.setError_code("0");
			resp.setError_msg("种子用户号码为空跳过接口调用!");
		}
		return resp;
	}
	
	@ZteMethodAnnontion(name="校验照片上传状态",group_name="通用组件",order="60",page_show=true,path="zteCommonTraceRule.checkPhotoStatus")
	public BusiCompResponse checkPhotoStatus(BusiCompRequest busiCompRequest) throws ApiBusiException{
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = getOrderId(busiCompRequest);
		OrderTreeBusiRequest ordertree = CommonDataFactory.getInstance().getOrderTree(order_id);
		String if_photo_status = ordertree.getOrderExtBusiRequest().getIf_Send_Photos();
		if(!StringUtil.isEmpty(if_photo_status)){
			if("1".equals(if_photo_status)||"2".equals(if_photo_status)||"9".equals(if_photo_status)){
				String insert_sql = " insert into es_order_photos_audit_logs (photos_audit_id,order_id,audit_user_id,audit_time,audit_msg,source_from) values (?,?,?,sysdate,?,'ECS') ";
				IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
				AdminUser user = ManagerUtils.getAdminUser();
				baseDaoSupport.execute(insert_sql,new String[]{baseDaoSupport.getSequences("s_es_order"),order_id,user.getUserid(),"审核通过"});
				resp.setError_code("0");
				resp.setError_msg("执行成功!");
			}else{
				CommonTools.addBusiError("-9999","证件照片未上传！");
			}
		}else{
			resp.setError_code("0");
			resp.setError_msg("照片上传状态为空，跳过判断!");
		}
		return resp;
	}
	@ZteMethodAnnontion(name="[AOP]客户资料创建",group_name="通用组件",order="61",page_show=true,path="zteCommonTraceRule.custInfoCreate")
    public BusiCompResponse custInfoCreate(BusiCompRequest busiCompRequest) throws ApiBusiException{
	    BusiCompResponse resp = new BusiCompResponse();
        String order_id = busiCompRequest.getOrder_id();
        BusiDealResult custResp = ordVisitTacheManager.custInfoCreate(order_id);
        if (!custResp.getError_code().equals(EcsOrderConsts.AOP_SUCCESS_0000)) {
            CommonTools.addBusiError(custResp.getError_code(), custResp.getError_msg());
        }
        resp.setError_code("0");
        resp.setError_msg("客户创建成功");
        resp.setResponse(custResp.getResponse());
        return resp;
    }
	
	@ZteMethodAnnontion(name = "获取宽带账号/校验", group_name = "通用组件", order = "62", page_show = true, path = "zteCommonTraceRule.kdnumberQryCheck")
    public BusiCompResponse kdnumberQryCheck(BusiCompRequest busiCompRequest) throws ApiBusiException {
        BusiCompResponse resp = new BusiCompResponse();
        String order_id = getOrderId(busiCompRequest);
        ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
        IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
        KdnumberQryReq req = new KdnumberQryReq();
        req.setNotNeedReqStrOrderId(order_id);
        KdnumberQryResp rsp = new KdnumberQryResp();
        String city_id = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ORDER_CITY_CODE);//地市
        EmpOperInfoVo essInfo= commonManager.findEssOperatorInfo("WFGROUPUPINTENT",city_id,order_id);
        if(essInfo!=null){
             req.setOperator_id(essInfo.getEss_emp_id());
             req.setOffice_id(essInfo.getDep_id());
        }
        req.setSerial_num(order_id);
        // 接口调用开户申请
        rsp = client.execute(req, KdnumberQryResp.class);
        if(!"00000".equals(rsp.getCode())){
            resp.setError_msg(rsp.getMsg());
            resp.setError_code("1");
            return resp;
        }
        if (!EcsOrderConsts.INF_RESP_CODE_0000.equals(rsp.getRespJson().getRESP_CODE())) {
            resp.setError_msg(rsp.getRespJson().getRESP_DESC()+rsp.getMsg());
            resp.setError_code("1");
            return resp;
/*            CommonTools.addBusiError(EcsOrderConsts.BUSI_DEAL_RESULT_FAIL, rsp.getRespJson().getRESP_DESC());*/
        } else {    
            String AUTH_ACCT_ID = rsp.getRespJson().getACCT_INFO().getAUTH_ACCT_ID();// 宽带账号
            String SERIAL_NUMBER = rsp.getRespJson().getACCT_INFO().getSERIAL_NUMBER();// 宽带号码
            KdNumberCheckResp rspcheck = new KdNumberCheckResp();
            KdNumberCheckReq reqcheck = new KdNumberCheckReq();
            if(essInfo!=null){
                reqcheck.setOperator_id(essInfo.getEss_emp_id());
                reqcheck.setOffice_id(essInfo.getDep_id());
            }

            String serial_num=CommonDataFactory.getInstance().getOrderTree(order_id).getOrderDeliveryBusiRequests().get(0).getShip_mobile();//联系电话
            reqcheck.setSerial_num(serial_num);
            Map<String, Object> hashmap = new HashMap<String, Object>();
            hashmap.put("SERVICE_CLASS_CODE", "0040");
            hashmap.put("AUTH_ACCT_ID", AUTH_ACCT_ID);
            hashmap.put("SERIAL_NUMBER", SERIAL_NUMBER);
            hashmap.put("PARA_LIST", new ArrayList());
            JSONObject json = JSONObject.fromObject(hashmap);
            reqcheck.setMsg(json);
            reqcheck.setNotNeedReqStrOrderId(order_id);
            rspcheck = client.execute(reqcheck, KdNumberCheckResp.class);
            boolean flag = false;
            if ("00000".equals(rspcheck.getCode())) {
                if (null != rspcheck.getRespJson()) {
                    if ("0000".equals(rspcheck.getRespJson().getRESP_CODE())) {
                        flag = true;
                    }
                }
            }
            if(!flag){
                resp.setError_msg("无线宽带账号不唯一");
                resp.setError_code("1");
                return resp;
            }else{
                OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
                List<OrderInternetBusiRequest> orderInternet = orderTree.getOrderInternetBusiRequest();
                Boolean flags = true;
                if(orderInternet.size()>0){
                    for (int i = 0; i < orderInternet.size(); i++) {
                        OrderInternetBusiRequest orderintentbusi = orderInternet.get(i);
                        String is_news = orderInternet.get(i).getIs_new();
                        if("1".equals(is_news)){
                            orderintentbusi.setEvdo_num(SERIAL_NUMBER);
                            orderintentbusi.setSource_from(ManagerUtils.getSourceFrom());
                            orderintentbusi.setIs_dirty(ConstsCore.IS_DIRTY_YES);
                            orderintentbusi.setDb_action(ConstsCore.DB_ACTION_UPDATE);
                            orderintentbusi.store();
                            flags = false;
                            break;
                        }
                    }
                }
                if(flags){
                    OrderInternetBusiRequest orderInternetBusiRequest = new OrderInternetBusiRequest();
                    orderInternetBusiRequest.setInternet_phone_info_id(baseDaoSupport.getSequences("INTERNET_PHONE_INFO_ID"));
                    orderInternetBusiRequest.setIs_new("1");//是否是新装  新装是1  纳入是0
                    orderInternetBusiRequest.setOrder_id(order_id);
                    orderInternetBusiRequest.setIs_blankcard("1");
                    orderInternetBusiRequest.setIs_main_number("0");
                    orderInternetBusiRequest.setUser_kind("27");
                    orderInternetBusiRequest.setEvdo_num(SERIAL_NUMBER);
                    orderInternetBusiRequest.setSource_from(ManagerUtils.getSourceFrom());
                    orderInternetBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
                    orderInternetBusiRequest.setDb_action(ConstsCore.DB_ACTION_INSERT);
                    orderInternetBusiRequest.store();
                }
/*                CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.PHONE_NUM}, new String[] { SERIAL_NUMBER });
*/                resp.setError_msg("账号校验成功");
                  resp.setError_code("0");
            }
            return resp;
        }
    }
	
	@ZteMethodAnnontion(name = "无线宽带后激活", group_name = "通用组件", order = "63", page_show = true, path = "zteCommonTraceRule.kdUserActivation")
    public BusiCompResponse kdUserActivation(BusiCompRequest busiCompRequest) throws ApiBusiException {
        BusiCompResponse resp = new BusiCompResponse();
        String order_id = getOrderId(busiCompRequest);
        ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
        UserActivationReq userRequ = new UserActivationReq();
        userRequ.setNotNeedReqStrOrderId(order_id);
        userRequ.setOper_type("0");
        OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(busiCompRequest.getOrder_id());
        List<OrderInternetBusiRequest> orderInternet = orderTree.getOrderInternetBusiRequest();
        for (int i = 0; i < orderInternet.size(); i++) {
            OrderInternetBusiRequest orderInternetBusiRequest = orderInternet.get(i);
            if("1".equals(orderInternetBusiRequest.getIs_new())){
                userRequ.setService_num(orderInternetBusiRequest.getEvdo_num());
            }
        }
        String city_id = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ORDER_CITY_CODE);//地市
        EmpOperInfoVo essInfo= commonManager.findEssOperatorInfo("WFGROUPUPINTENT",city_id,order_id);
        if(essInfo!=null){
             userRequ.setOperator_id(essInfo.getEss_emp_id());
             userRequ.setOffice_id(essInfo.getDep_id());
        }else{
            String OUT_OPERATOR = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.OUT_OPERATOR);//操作员
            String OUT_OFFICE = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.OUT_OFFICE);//操作点
            userRequ.setOperator_id(OUT_OPERATOR);
            userRequ.setOffice_id(OUT_OFFICE);
        }
        UserActivationResp rsp = new UserActivationResp();
        rsp = client.execute(userRequ, UserActivationResp.class);
        if(EcsOrderConsts.BSS_SUCCESS_00000.equals(rsp.getCode())){
            resp.setError_msg("激活成功");
            resp.setError_code("0");
        }else{
            resp.setError_code("1");
            resp.setError_msg(rsp.getMsg());
        }
        return resp;
    }
	
	   @ZteMethodAnnontion(name = "移网号码选择",group_name = "通用组件", order = "64", page_show = true, path = "zteCommonTraceRule.phoneNumChange")
	   public BusiCompResponse phoneNumChange(BusiCompRequest busiCompRequest) throws ApiBusiException {
	        BusiCompResponse resp = new BusiCompResponse();
	        String order_id = busiCompRequest.getOrder_id();
	        IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
	        OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
	        String order_from = orderTree.getOrderExtBusiRequest().getOrder_from();
	        ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
	        NumberResourceQueryZjBssResponse number = new NumberResourceQueryZjBssResponse();
	        // 接口调用开户申请
	        List list = new ArrayList();
	        NumberResourceQueryZjBssRequest req = new NumberResourceQueryZjBssRequest();
	        String provinceQueryPara_001 = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ORDER_CITY_CODE);//地市
	        if ("330100".equals(provinceQueryPara_001)) {
	            req.setRegion_id("A");// 杭州市
	        } else if ("330500".equals(provinceQueryPara_001)) {
	            req.setRegion_id("D");// 湖州
	        } else if ("330400".equals(provinceQueryPara_001)) {
	            req.setRegion_id("E");// 嘉兴
	        } else if ("330700".equals(provinceQueryPara_001)) {
	            req.setRegion_id("I");// 金华
	        } else if ("331100".equals(provinceQueryPara_001)) {
	            req.setRegion_id("H");// 丽水
	        } else if ("330200".equals(provinceQueryPara_001)) {
	            req.setRegion_id("K");// 宁波
	        } else if ("330600".equals(provinceQueryPara_001)) {
	            req.setRegion_id("F");// 绍兴
	        } else if ("331000".equals(provinceQueryPara_001)) {
	            req.setRegion_id("G");// 台州
	        } else if ("330300".equals(provinceQueryPara_001)) {
	            req.setRegion_id("B");// 温州
	        } else if ("330900".equals(provinceQueryPara_001)) {
	            req.setRegion_id("J");// 舟山
	        } else if ("330800".equals(provinceQueryPara_001)) {
	            req.setRegion_id("C");// 衢州
	        } else if ("330000".equals(provinceQueryPara_001)) {
	            req.setRegion_id("Z");// 浙江
	        } else {
	            req.setRegion_id("Z");// 浙江
	        }
	        req.setQuery_key("04");// 01：移网随机 02：移网关键字 暂时先设置为随机查询  参数值待定 O4  无线宽带专业选号
	        req.setQuery_value("");// 随机类型
	        req.setNumber("02");// 返回号码数
	        req.setNotNeedReqStrOrderId(order_id);
	        // EmpOperInfoVo essInfo= commonManager.findEssOperatorInfo("WFGROUPUPINTENT",city_id,order_id);

	        EmpOperInfoVo essInfo= commonManager.findEssOperatorInfo("WFGROUPUPINTENT",provinceQueryPara_001,order_id);
	        if(essInfo!=null){
	            req.setOperator_id(essInfo.getEss_emp_id());
	            req.setOffice_id(essInfo.getDep_id());
	        }else{
	            String OUT_OPERATOR = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.OUT_OPERATOR);//操作员
	            String OUT_OFFICE = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.OUT_OFFICE);//操作点
	            req.setOffice_id(OUT_OFFICE);// 设置操作点
	            req.setOperator_id(OUT_OPERATOR);//操作员id
	        }
	        NumberResourceQueryZjBssResponse respBss = client.execute(req, NumberResourceQueryZjBssResponse.class);
	        if (resp != null && EcsOrderConsts.BSS_SUCCESS_00000.equals(respBss.getCode())) {
	            list = respBss.getRespJson();// 拿到号码
	        }
	        if(list.size()>0){
	            NumInfoZjBss numInfo = (NumInfoZjBss) list.get(0);//这一步是需要进行保存到数据库的
                String is_spenum = numInfo.getIs_spenum();//是否靓号
                String num_lvl = numInfo.getNum_lvl();//靓号等级
                String number_num  = numInfo.getNumber();//手机号码
	            List<OrderInternetBusiRequest> orderInternet = orderTree.getOrderInternetBusiRequest();
	            Boolean flag = true;
	            if(StringUtils.isNotEmpty(number_num)){
	                if(orderInternet.size()>0){
	                    for (int i = 0; i < orderInternet.size(); i++) {
	                        OrderInternetBusiRequest orderintentbusi = orderInternet.get(i);
	                        String is_news = orderInternet.get(i).getIs_new();
	                        if("1".equals(is_news)){
	                            orderintentbusi.setService_num(number_num);
	                            orderintentbusi.setSource_from(ManagerUtils.getSourceFrom());
	                            orderintentbusi.setIs_dirty(ConstsCore.IS_DIRTY_YES);
	                            orderintentbusi.setDb_action(ConstsCore.DB_ACTION_UPDATE);
	                            orderintentbusi.store();
	                            flag = false;
	                            break;
	                        }
	                    }
	                }
	                if(flag){
	                    OrderInternetBusiRequest orderInternetBusiRequest = new OrderInternetBusiRequest();
	                    orderInternetBusiRequest.setInternet_phone_info_id(baseDaoSupport.getSequences("INTERNET_PHONE_INFO_ID"));
	                    orderInternetBusiRequest.setIs_new("1");//是否是新装  新装是1  纳入是0
	                    orderInternetBusiRequest.setOrder_id(order_id);
	                    orderInternetBusiRequest.setOrder_from(order_from);
	                    orderInternetBusiRequest.setService_num(number_num);
	                    orderInternetBusiRequest.setIs_blankcard("0");
	                    orderInternetBusiRequest.setUser_kind("27");
	                    orderInternetBusiRequest.setIs_main_number("1");
	                    orderInternetBusiRequest.setSale_mode("04");
	                    orderInternetBusiRequest.setSource_flag("0");
	                    orderInternetBusiRequest.setSource_from(ManagerUtils.getSourceFrom());
	                    orderInternetBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
	                    orderInternetBusiRequest.setDb_action(ConstsCore.DB_ACTION_INSERT);
	                    orderInternetBusiRequest.store();
	                }
/*	                CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.PHONE_NUM}, new String[] { number_num });
*/	                resp.setError_code("0");
	                resp.setError_msg("执行成功");
	            }else{
	                resp.setError_code("1");
                    resp.setError_msg("号码选择失败,接口返回无号码值");
	            }
	        }else{
	            resp.setError_code("1");
	            resp.setError_msg("号码选择失败");
	        }
	        return resp;
	    }
	   
	   @ZteMethodAnnontion(name = "移网号码预占", group_name = "通用组件", order = "32", page_show = true, path = "zteCommonTraceRule.numberBssPreempted")
	    public BusiCompResponse numberBssPreempted(BusiCompRequest busiCompRequest) throws ApiBusiException {
	        BusiCompResponse resp = new BusiCompResponse();
	        String phone_num = CommonDataFactory.getInstance().getAttrFieldValue(busiCompRequest.getOrder_id(), AttrConsts.PHONE_NUM);
	        BusiDealResult numberInfo = ordCollectManager.numberBssPreempted(busiCompRequest.getOrder_id());
	        // 保存号码信息
	        OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(busiCompRequest.getOrder_id());
	        OrderPhoneInfoBusiRequest orderPhoneInfoBusiRequest = orderTree.getPhoneInfoBusiRequest();
	        if (!numberInfo.getError_code().equals("00000")) {
	            orderPhoneInfoBusiRequest.setBssOccupiedFlag(EcsOrderConsts.OCCUPIEDFLAG_5);
	            orderPhoneInfoBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
	            orderPhoneInfoBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
	            orderPhoneInfoBusiRequest.store();
	            CommonTools.addBusiError(numberInfo.getError_code(), numberInfo.getError_msg());
	        } else {
	            orderPhoneInfoBusiRequest.setBssOccupiedFlag(EcsOrderConsts.OCCUPIEDFLAG_1);
	            orderPhoneInfoBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
	            orderPhoneInfoBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
	            orderPhoneInfoBusiRequest.store();
	        }
	        resp.setError_code("0");
	        resp.setError_msg("预占成功");
	        resp.setResponse(numberInfo.getResponse());
	        return resp;
	    }
	   
	   @ZteMethodAnnontion(name = "发货短信发送组件", group_name = "通用组件", order = "65", page_show = true, path = "zteCommonTraceRule.logisticsSMS")
	    public BusiCompResponse logisticsSMS(BusiCompRequest busiCompRequest) throws ApiBusiException {
	        BusiCompResponse resp = new BusiCompResponse();
	        String order_id = busiCompRequest.getOrder_id();
	        ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
	        IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
	        Map data = new HashMap<String, String>();
	        OrderTreeBusiRequest ordertree = CommonDataFactory.getInstance().getOrderTree(order_id);
			String shipping_company = ordertree.getOrderDeliveryBusiRequests().get(0).getShipping_company();
			String logi_no = ordertree.getOrderDeliveryBusiRequests().get(0).getLogi_no();
			String ship_tel = ordertree.getOrderDeliveryBusiRequests().get(0).getShip_mobile();
			if(StringUtil.isEmpty(ship_tel)){
				ship_tel = CommonDataFactory.getInstance().getAttrFieldValue(order_id, "receiver_mobile");
			}
			if(StringUtil.isEmpty(ship_tel)){
				//记录失败短信及次数
				ecsOrdManager.messages_send_count(order_id);
				CommonTools.addBusiError("-9999", "短信发送失败，未查询到联系号码！");
			}
			if(StringUtil.isEmpty(shipping_company)){
				//记录失败短信及次数
				ecsOrdManager.messages_send_count(order_id);
				CommonTools.addBusiError("-9999", "短信发送失败，未查询到物流公司！");
			}
			if(StringUtil.isEmpty(logi_no)){
				//记录失败短信及次数
				ecsOrdManager.messages_send_count(order_id);
				CommonTools.addBusiError("-9999", "短信发送失败，未查询到物流单号！");
			}
			List<Logi> logiCompanyList = ecsOrdManager.logi_company(order_id);
			if(logiCompanyList.size()>0){
				String flag = "";
				for (int i = 0; i < logiCompanyList.size(); i++) {
					Logi logi = logiCompanyList.get(i);
					String id = logi.getId();
					if(id.equals(shipping_company)){
						flag = "";
						shipping_company = logi.getName();
						break;
					}else{
						flag = "1";
					}
				}
				if(!StringUtil.isEmpty(flag)){
					CommonTools.addBusiError("-9999", "短信发送失败，未配置物流公司！");
				}
			}else{
				CommonTools.addBusiError("-9999", "短信发送失败，未配置物流公司！");
			}
			//url = "1232123";
			data.put("shipping_company", shipping_company);
			data.put("logi_no", logi_no);
			// 您的【${orgname}订单池】收到待处理订单${orderid}（订单编号），可用工号【${userid}】登录订单中心领取处理。
			
			// 根据种子用户号码判断是否是杭州银行的单子

			IRemoteSmsService localRemoteSmsService = ApiContextHolder.getBean("localRemoteSmsService");
			String smsContent = "";
			
			ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
			//hangzhouBank,订单树查
			String sql  = cacheUtil.getConfigInfo("hangzhou_bank_send_sms") + " and order_id = '"+ order_id + "'";
			List<?> listRes = baseDaoSupport.queryForList(sql);
			if(null != listRes && 0 < listRes.size()) {
				smsContent = localRemoteSmsService.getSMSTemplate("SMS_HANGZHOU_CODE", data);
			}else {
				smsContent = localRemoteSmsService.getSMSTemplate("SMS_LOGISTICS_CODE", data);
			}
			String UNICOM_NUM_SECTION = cacheUtil.getConfigInfo("UNICOM_NUM_SECTION");
			String[] unicom_num_section = UNICOM_NUM_SECTION.split(",");
			boolean flag = false;
			for (int i = 0; i < unicom_num_section.length; i++) {
				if(unicom_num_section[i].equals(ship_tel.substring(0,unicom_num_section[i].length()))){
					flag = true;
					break;
				}
			}
			if(flag){
				AopSmsSendReq smsSendReq = new AopSmsSendReq();
				smsSendReq.setBill_num("10010");// 短信发送号码
				smsSendReq.setService_num(ship_tel);// 接受号码--省内联通号码
				smsSendReq.setSms_data(smsContent);
				smsSendReq.setOrder_id(order_id);
				AopSmsSendResp sms_resp = client.execute(smsSendReq, AopSmsSendResp.class);
				if(StringUtil.isEmpty(sms_resp.getCode())||!"00000".equals(sms_resp.getCode())){
					//记录失败短信及次数
					ecsOrdManager.messages_send_count(order_id);
				}
			}else{
				CmcSmsSendReq cmcSendReq = new CmcSmsSendReq();
				cmcSendReq.setNotNeedReqStrOrderId(order_id);
				cmcSendReq.setMessageContent(smsContent);
				cmcSendReq.setUserNumber(ship_tel);
				CmcSmsSendResp cmcSendResp = client.execute(cmcSendReq, CmcSmsSendResp.class);
				
				if(StringUtil.isEmpty(cmcSendResp.getResultcode())||!"0".equals(cmcSendResp.getResultcode())){
					String msg = "";
					List<Map> list = this.getConsts("DIC_YXT_SMS_RESULT");
						for (Map m : list) {
							String pkey = (String) m.get("pkey");
							if (cmcSendResp.getResultcode().equals(pkey)) {
								msg = (String) m.get("pname");
								break;
							}
						}
						//记录失败短信及次数
						ecsOrdManager.messages_send_count(order_id);
				}
			}
			
			//记录成功短信及次数
			ecsOrdManager.messages_send_count(order_id);
			
	        return resp;
	   }
}