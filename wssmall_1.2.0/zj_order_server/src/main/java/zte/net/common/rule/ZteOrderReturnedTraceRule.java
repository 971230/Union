package zte.net.common.rule;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import params.ZteResponse;
import params.order.req.OrderHandleLogsReq;
import params.orderqueue.req.OrderHandleLogsAddReq;
import params.orderqueue.resp.OrderHandleLogsAddResp;
import params.req.BackOrderLayerReq;
import params.req.RejectLayerReq;
import zte.net.common.annontion.context.action.ZteMethodAnnontion;
import zte.net.common.annontion.context.action.ZteServiceAnnontion;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.base.req.BusiCompRequest;
import zte.net.ecsord.params.base.resp.BusiCompResponse;
import zte.net.ecsord.params.busi.req.OrderExtBusiRequest;
import zte.net.ecsord.params.busi.req.OrderRealNameInfoBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;
import zte.net.ecsord.params.ecaop.req.AopSmsSendReq;
import zte.net.ecsord.params.ecaop.req.OrderCancelReq;
import zte.net.ecsord.params.ecaop.req.OrderInfoSynReq;
import zte.net.ecsord.params.ecaop.resp.AopSmsSendResp;
import zte.net.ecsord.params.ecaop.resp.OrderCancelResp;
import zte.net.ecsord.params.ecaop.resp.OrderInfoSynRsp;
import zte.net.ecsord.params.taobao.req.TbTianjiOrderStatusNoticeReq;
import zte.net.ecsord.params.taobao.resp.TbTianjiOrderStatusNoticeResp;
import zte.net.ecsord.params.wms.req.NotifyOrderStatusToWMSReq;
import zte.net.ecsord.params.wms.resp.NotifyOrderStatusToWMSResp;
import zte.net.ecsord.params.wyg.req.NotifyOrderInfoWYGRequset;
import zte.net.ecsord.params.wyg.resp.NotifyOrderInfoWYGResponse;
import zte.net.ecsord.params.zb.req.StateSynToZBRequest;
import zte.net.ecsord.params.zb.resp.StateSynToZBResponse;
import zte.net.ecsord.rule.tache.TacheFact;
import zte.net.iservice.IInfServices;
import zte.net.iservice.IZJInfServices;
import zte.net.params.req.PlanRuleTreeExeReq;
import zte.net.params.resp.PlanRuleTreeExeResp;

import com.ztesoft.api.ApiBusiException;
import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.net.ecsord.params.ecaop.req.BroadbandOrderInfoRefundReq;
import com.ztesoft.net.ecsord.params.ecaop.req.BroadbandrefundReq;
import com.ztesoft.net.ecsord.params.ecaop.req.CbssrefundFeeQryReq;
import com.ztesoft.net.ecsord.params.ecaop.req.OrderCancelPreCheckReq;
import com.ztesoft.net.ecsord.params.ecaop.req.OrderCancelSubmitReq;
import com.ztesoft.net.ecsord.params.ecaop.resp.BroadbandOrderInfoRefundResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.BroadbandRefundResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.CbssrefundFeeQryResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.OrderCancelPreCheckResponse;
import com.ztesoft.net.ecsord.params.ecaop.resp.OrderCancelSubmitResponse;
import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.consts.ZjEcsOrderConsts;
import com.ztesoft.net.mall.core.model.OrderHandleLogs;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.model.BusiDealResult;
import com.ztesoft.net.service.IOrdCollectManagerManager;
import com.ztesoft.net.service.IOrdIntentManager;
import com.ztesoft.net.service.IOrdPickingTacheManager;
import com.ztesoft.net.service.IOrdVisitTacheManager;
import com.ztesoft.net.service.IOrderFlowManager;

import commons.CommonTools;
import consts.ConstsCore;

@ZteServiceAnnontion(trace_name = "ZteOrderReturnedTraceRule", trace_id = "0", version = "1.0", desc = "订单退单")
public class ZteOrderReturnedTraceRule extends ZteTraceBaseRule {
	private static Logger logger = Logger.getLogger(ZteOrderReturnedTraceRule.class);
	// @Resource
	// private IOrderFlowManager ordFlowManager;

	@Resource
	private IInfServices infServices;

	@Resource
	private IZJInfServices zjInfServices;

	@Resource
	private IOrdPickingTacheManager ordPickingTacheManager;

	@Resource
	private IOrdCollectManagerManager ordCollectManager;

	@Resource
	private IOrdVisitTacheManager ordVisitTacheManager;
	@Resource
	private IOrderFlowManager ordFlowManager;
	
	@Resource
	private IOrdIntentManager ordIntentManager;

	@Override
	// @ZteMethodAnnontion(name="接收退单申请",group_name="所有模式",order="1",page_show=true,path="ZteOrderReturnedTraceRule.apply")
	public BusiCompResponse engineDo(BusiCompRequest traceRequest) {
		return null;
	}

	@ZteMethodAnnontion(name = "退单申请通知WMS", group_name = "所有模式", order = "1", page_show = true, path = "ZteOrderReturnedTraceRule.noticeWmsReturnApply")
	public BusiCompResponse noticeWmsReturnApply(BusiCompRequest traceRequest) throws ApiBusiException {
		String order_id = getOrderId(traceRequest);
		if (StringUtil.isEmpty(order_id))
			order_id = traceRequest.getOrder_id();
		BusiCompResponse resp = new BusiCompResponse();
		try {
			NotifyOrderStatusToWMSReq req = new NotifyOrderStatusToWMSReq();
			req.setOrderId(order_id);
			req.setNotNeedReqStrWms_status(EcsOrderConsts.ORDER_STATUS_WMS_13);// 退单申请通知总部后，直接通知退单确认到WMS
			NotifyOrderStatusToWMSResp sresp = infServices.notifyOrderStatusToWMS(req);
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.WMS_REFUND_STATUS }, new String[] { EcsOrderConsts.ORDER_STATUS_WMS_13 });
			resp.setError_code(sresp.getError_code());
			resp.setError_msg(sresp.getError_msg());
			resp.setError_code("0");
			resp.setError_msg("成功");
		} catch (Exception ex) {
			ex.printStackTrace();
			resp.setError_code("0");
			resp.setError_msg("失败[" + ex.getMessage() + "]");
		}
		return resp;
	}

	@ZteMethodAnnontion(name = "退单申请通知总部", group_name = "所有模式", order = "2", page_show = true, path = "ZteOrderReturnedTraceRule.noticeZBReturnApply")
	public BusiCompResponse noticeZBReturnApply(BusiCompRequest traceRequest) throws ApiBusiException {
		// IInfServices.stateSynToZB，通知总部退单
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = getOrderId(traceRequest);
		if (StringUtil.isEmpty(order_id))
			order_id = traceRequest.getOrder_id();
		try {
			StateSynToZBRequest req = new StateSynToZBRequest();
			req.setNotNeedReqStrOrderId(order_id);
			req.setNotNeedReqStrStateTag(EcsOrderConsts.STATE_CHG_CANLODR);
			StateSynToZBResponse sresp = infServices.stateSynToZB(req);
			if (!EcsOrderConsts.INF_RESP_CODE_0000.equals(sresp.getRespCode())) {
				CommonTools.addBusiError("-1", "退单申请通知总部失败：" + sresp.getRespDesc());
			} else {
				resp.setError_code("0");
				resp.setError_msg("执行成功");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			CommonTools.addBusiError("-1", "退单申请通知总部失败：" + ex.getMessage());
		}
		return resp;
	}

	/**
	 * 订单变更通知新商城(发货)
	 * 
	 * @throws ApiBusiException
	 */
	@ZteMethodAnnontion(name = "订单退单申请通知新商城", group_name = "退单", order = "3", page_show = true, path = "ZteOrderReturnedTraceRule.orderBackApplyUpdateStatuToMall")
	public BusiCompResponse orderBackApplyUpdateStatuToMall(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = getOrderId(busiCompRequest);
		if (StringUtil.isEmpty(order_id))
			order_id = busiCompRequest.getOrder_id();
		String field_value = "";
		BusiDealResult result = new BusiDealResult();
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		NotifyOrderInfoWYGRequset req = new NotifyOrderInfoWYGRequset();
		req.setTrace_code(EcsOrderConsts.NEW_SHOP_ORDER_STATE_11); // 退单申请
		req.setDeal_desc(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.REFUND_DESC));
		req.setNotNeedReqStrOrderId(order_id);
		NotifyOrderInfoWYGResponse infResp = client.execute(req, NotifyOrderInfoWYGResponse.class);
		if (!infResp.getResp_code().equals(EcsOrderConsts.NEW_SHOP_INF_SUCC_CODE)) {
			result.setError_msg("错误编码：" + infResp.getResp_code() + ";错误信息：" + infResp.getResp_msg());
			result.setError_code(infResp.getResp_code());
			field_value = EcsOrderConsts.NEW_SHOP_STATUS_5;
		} else {
			field_value = EcsOrderConsts.NEW_SHOP_STATUS_6;
		}
		String[] name = { AttrConsts.NEW_SHOP_STATUS };
		String[] value = { field_value };
		CommonDataFactory.getInstance().updateAttrFieldValue(order_id, name, value);

		// 商城返回失败要卡流程
		if (!infResp.getResp_code().equals(EcsOrderConsts.NEW_SHOP_INF_SUCC_CODE)) {
			CommonTools.addBusiError(infResp.getResp_code(), infResp.getResp_msg());
		}
		resp.setError_code("0");
		resp.setError_msg("成功");
		return resp;

	}

	/**
	 * 订单变更通知新商城(退单)
	 * 
	 * @throws ApiBusiException
	 */
	@ZteMethodAnnontion(name = "订单退单完成通知新商城", group_name = "退单", order = "4", page_show = true, path = "ZteOrderReturnedTraceRule.orderBackfinishUpdateStatuToMall")
	public BusiCompResponse orderBackfinishUpdateStatuToMall(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = getOrderId(busiCompRequest);
		if (StringUtil.isEmpty(order_id))
			order_id = busiCompRequest.getOrder_id();
		String field_value = "";
		BusiDealResult result = new BusiDealResult();
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		NotifyOrderInfoWYGRequset req = new NotifyOrderInfoWYGRequset();
		// 退单驳回.在约定一个值
		OrderExtBusiRequest orderExt = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest();
		if (EcsOrderConsts.REFUND_STATUS_00.equals(orderExt.getRefund_status())) {
			req.setTrace_code(EcsOrderConsts.NEW_SHOP_ORDER_STATE_15); // 退单驳回(值暂定)
		} else {
			req.setTrace_code(EcsOrderConsts.NEW_SHOP_ORDER_STATE_12); // 退单完成
		}
		req.setDeal_desc(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.REFUND_DESC));
		req.setNotNeedReqStrOrderId(order_id);

		NotifyOrderInfoWYGResponse infResp = client.execute(req, NotifyOrderInfoWYGResponse.class);

		if (!infResp.getResp_code().equals(EcsOrderConsts.NEW_SHOP_INF_SUCC_CODE)) {
			result.setError_msg("错误编码：" + infResp.getResp_code() + ";错误信息：" + infResp.getResp_msg());
			result.setError_code(infResp.getResp_code());
			field_value = EcsOrderConsts.NEW_SHOP_STATUS_7;
		} else {
			field_value = EcsOrderConsts.NEW_SHOP_STATUS_8;
		}
		String[] name = { AttrConsts.NEW_SHOP_STATUS };
		String[] value = { field_value };
		CommonDataFactory.getInstance().updateAttrFieldValue(order_id, name, value);

		// 商城返回失败要卡流程
		if (!infResp.getResp_code().equals(EcsOrderConsts.NEW_SHOP_INF_SUCC_CODE)) {
			CommonTools.addBusiError(infResp.getResp_code(), infResp.getResp_msg());
		}
		resp.setError_code("0");
		resp.setError_msg("成功");
		return resp;

	}

	/**
	 * 取消退单
	 * 
	 * @throws ApiBusiException
	 */
	@ZteMethodAnnontion(name = "取消退单", group_name = "退单", order = "5", page_show = true, path = "ZteShipTraceRule.cancelReturned")
	public BusiCompResponse cancelReturned(BusiCompRequest busiCompRequest) throws ApiBusiException {

		BusiCompResponse resp = new BusiCompResponse();
		String order_id = getOrderId(busiCompRequest);
		if (StringUtil.isEmpty(order_id))
			order_id = busiCompRequest.getOrder_id();
		try {
			OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
			OrderExtBusiRequest orderExtBusiRequest = orderTree.getOrderExtBusiRequest();
			String order_model = orderExtBusiRequest.getOrder_model();
			if (EcsOrderConsts.OPER_MODE_ZD.equals(order_model)) {
				ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
				NotifyOrderStatusToWMSReq notifyWMS = new NotifyOrderStatusToWMSReq();
				notifyWMS.setOrderId(order_id);
				notifyWMS.setNotNeedReqStrWms_status(EcsOrderConsts.ORDER_STATUS_WMS_19);
				client.execute(notifyWMS, NotifyOrderStatusToWMSResp.class);
			}
			orderExtBusiRequest.setRefund_deal_type(EcsOrderConsts.REFUND_DEAL_TYPE_01);
			orderExtBusiRequest.setRefund_status(EcsOrderConsts.REFUND_STATUS_00);
			orderExtBusiRequest.setVisible_status(EcsOrderConsts.VISIBLE_STATUS_0);
			orderExtBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			orderExtBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
			orderExtBusiRequest.store();

			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, // 取消退单，设置未执行退款规则
					new String[] { AttrConsts.REFUND_RULE_EXECUTE, AttrConsts.ZB_REFUND_STATUS }, new String[] { EcsOrderConsts.NO_DEFAULT_VALUE, "" });

			// 退單取消通知老系統
			/*
			 * RuleFlowUtil.delRuleExeLogs(EcsOrderConsts.
			 * ORDER_STATUS_SYN_OLD_SYS_PLAN,
			 * EcsOrderConsts.ORDER_STATUS_SYN_OLD_SYS_RULE, order_id);
			 * PlanRuleTreeExeReq plan = new PlanRuleTreeExeReq(); TacheFact
			 * fact2 = new TacheFact(); fact2.setOrder_id(order_id);
			 * fact2.setSrc_tache_code("T"); fact2.setTar_tache_code("B");
			 * plan.setFact(fact2);
			 * plan.setPlan_id(EcsOrderConsts.ORDER_STATUS_SYN_OLD_SYS_PLAN);
			 * PlanRuleTreeExeResp planResp =
			 * CommonDataFactory.getInstance().exePlanRuleTree(plan);
			 */

			resp.setError_code("0");
			resp.setError_msg("成功");
		} catch (Exception e) {
			// TODO: handle exception
			resp.setError_code("0");
			resp.setError_msg("失败[" + e.getMessage() + "]");
		}
		return resp;
	}

	@ZteMethodAnnontion(name = "退单确认通知WMS", group_name = "所有模式", order = "6", page_show = true, path = "ZteOrderReturnedTraceRule.noticeWmsReturnComit")
	public BusiCompResponse noticeWmsReturnComit(BusiCompRequest traceRequest) throws ApiBusiException {
		String order_id = getOrderId(traceRequest);
		if (StringUtil.isEmpty(order_id))
			order_id = traceRequest.getOrder_id();
		BusiCompResponse resp = new BusiCompResponse();
		try {
			NotifyOrderStatusToWMSReq req = new NotifyOrderStatusToWMSReq();
			req.setOrderId(order_id);
			req.setNotNeedReqStrWms_status(EcsOrderConsts.ORDER_STATUS_WMS_15);// 退单申请通知总部后，直接通知退单确认到WMS
			NotifyOrderStatusToWMSResp sresp = infServices.notifyOrderStatusToWMS(req);
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.WMS_REFUND_STATUS }, new String[] { EcsOrderConsts.ORDER_STATUS_WMS_15 });
			resp.setError_code(sresp.getError_code());
			resp.setError_msg(sresp.getError_msg());
			resp.setError_code("0");
			resp.setError_msg("成功");
		} catch (Exception ex) {
			ex.printStackTrace();
			resp.setError_code("0");
			resp.setError_msg("失败[" + ex.getMessage() + "]");
		}
		return resp;
	}

	@ZteMethodAnnontion(name = "WMS回库确认", group_name = "所有模式", order = "6", page_show = true, path = "ZteOrderReturnedTraceRule.notifyBackFromWms")
	public BusiCompResponse notifyBackFromWms(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.WMS_REFUND_STATUS }, new String[] { EcsOrderConsts.ORDER_STATUS_WMS_25 });
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}

	/**
	 * 总部退单驳回
	 * 
	 * @throws ApiBusiException
	 */
	@ZteMethodAnnontion(name = "总部退单驳回", group_name = "退单", order = "6", page_show = true, path = "ZteOrderReturnedTraceRule.zbReturnedReject")
	public BusiCompResponse zbReturnedReject(BusiCompRequest busiCompRequest) throws ApiBusiException {

		BusiCompResponse resp = new BusiCompResponse();
		String order_id = getOrderId(busiCompRequest);
		if (StringUtil.isEmpty(order_id))
			order_id = busiCompRequest.getOrder_id();
		try {
			// 掉接口通知总部，退单驳回
			StateSynToZBRequest req = new StateSynToZBRequest();
			req.setNotNeedReqStrOrderId(order_id);
			req.setNotNeedReqStrStateTag(EcsOrderConsts.STATE_CHG_REJECT);
			StateSynToZBResponse sresp = infServices.stateSynToZB(req);
			if (!StringUtils.equals(sresp.getRespCode(), EcsOrderConsts.ZB_INF_RESP_CODE_0000)) {
				CommonTools.addBusiError("-99999", sresp.getRespDesc());
			}

			// 标记自动化异常
			OrderExtBusiRequest orderExt = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest();
			String syn_ord_wms = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.SYN_ORD_WMS);
			final String orderMode = orderExt.getOrder_model();
			if (EcsOrderConsts.OPER_MODE_ZD.equals(orderMode) && EcsOrderConsts.SYN_ORD_WMS_1.equals(syn_ord_wms) && !EcsOrderConsts.DIC_ORDER_NODE_B.equals(orderExt.getFlow_trace_id())) {
				// 总部退单驳回->自动化订单，转自动化异常
				BusiCompRequest busi = new BusiCompRequest();
				Map queryParams = new HashMap();
				queryParams.put(EcsOrderConsts.EXCEPTION_FROM, EcsOrderConsts.EXCEPTION_FROM_ORD);
				queryParams.put("order_id", order_id);
				queryParams.put("exception_type", EcsOrderConsts.ABNORMAL_TYPE_OPEN);
				queryParams.put("exception_remark", "退单驳回后,标记开户异常");
				busi.setEnginePath("zteCommonTraceRule.markedException");
				busi.setOrder_id(order_id);
				busi.setQueryParams(queryParams);
				ZteResponse response = orderServices.execBusiComp(busi);
			}
			resp.setError_code("0");
			resp.setError_msg("成功");
		} catch (Exception e) {
			resp.setError_code("0");
			resp.setError_msg("失败[" + e.getMessage() + "]");
		}
		return resp;
	}

	@ZteMethodAnnontion(name = "[AOP]终端资源释放[方案]", group_name = "退单", order = "7", page_show = true, path = "zteOrderReturnedTraceRule.termiResourceReleaseAop")
	public BusiCompResponse termiResourceReleaseAop(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		PlanRuleTreeExeReq req = new PlanRuleTreeExeReq();
		TacheFact fact = new TacheFact();
		fact.setOrder_id(busiCompRequest.getOrder_id());
		req.setPlan_id(EcsOrderConsts.TERMI_RELEASE_AOP);
		req.setFact(fact);
		req.setDeleteLogs(true);
		PlanRuleTreeExeResp planResp = CommonDataFactory.getInstance().exePlanRuleTree(req);
		BusiCompResponse busiResp = CommonDataFactory.getInstance().getRuleTreeresult(planResp);
		if (!StringUtils.equals(busiResp.getError_code(), ConstsCore.ERROR_SUCC)) {
			CommonTools.addBusiError("-99999", planResp.getError_msg());
		}
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}

	@ZteMethodAnnontion(name = "[AOP]主卡释放[方案]", group_name = "退单", order = "8", page_show = true, path = "zteOrderReturnedTraceRule.numberReleaseAop")
	public BusiCompResponse numberReleaseAop(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		String phone_num = CommonDataFactory.getInstance().getAttrFieldValue(busiCompRequest.getOrder_id(), AttrConsts.PHONE_NUM);
		String occupancySysNew = CommonDataFactory.getInstance().sectionNoOccupancySysPlan(busiCompRequest.getOrder_id(), phone_num);
		if (StringUtils.equals(occupancySysNew, EcsOrderConsts.NUMBER_OCCUPANCY_TYPE_1) || StringUtils.equals(occupancySysNew, EcsOrderConsts.NUMBER_OCCUPANCY_TYPE_2)) {
			PlanRuleTreeExeReq req = new PlanRuleTreeExeReq();
			TacheFact fact = new TacheFact();
			fact.setOrder_id(busiCompRequest.getOrder_id());
			req.setPlan_id(EcsOrderConsts.NUMBER_RELEASE_AOP);
			req.setFact(fact);
			req.setDeleteLogs(true);
			PlanRuleTreeExeResp planResp = CommonDataFactory.getInstance().exePlanRuleTree(req);
			BusiCompResponse busiResp = CommonDataFactory.getInstance().getRuleTreeresult(planResp);
			if (!StringUtils.equals(busiResp.getError_code(), ConstsCore.ERROR_SUCC)) {
				CommonTools.addBusiError("-99999", planResp.getError_msg());
			}
		}
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}

	@ZteMethodAnnontion(name = "[bss]主卡释放[方案]", group_name = "退单", order = "9", page_show = true, path = "zteOrderReturnedTraceRule.numberReleaseBss")
	public BusiCompResponse numberReleaseBss(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		String phone_num = CommonDataFactory.getInstance().getAttrFieldValue(busiCompRequest.getOrder_id(), AttrConsts.PHONE_NUM);
		String occupancySysNew = CommonDataFactory.getInstance().sectionNoOccupancySysPlan(busiCompRequest.getOrder_id(), phone_num);
		if (StringUtils.equals(occupancySysNew, EcsOrderConsts.NUMBER_OCCUPANCY_TYPE_0) || StringUtils.equals(occupancySysNew, EcsOrderConsts.NUMBER_OCCUPANCY_TYPE_1)) {
			PlanRuleTreeExeReq req = new PlanRuleTreeExeReq();
			TacheFact fact = new TacheFact();
			fact.setOrder_id(busiCompRequest.getOrder_id());
			req.setPlan_id(EcsOrderConsts.NUMBER_RELEASE_BSS);
			req.setFact(fact);
			req.setDeleteLogs(true);
			PlanRuleTreeExeResp planResp = CommonDataFactory.getInstance().exePlanRuleTree(req);
			BusiCompResponse busiResp = CommonDataFactory.getInstance().getRuleTreeresult(planResp);
			if (!StringUtils.equals(busiResp.getError_code(), ConstsCore.ERROR_SUCC)) {
				CommonTools.addBusiError("-99999", planResp.getError_msg());
			}
		}
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}

	/**
	 * [AOP]订单返销
	 * 
	 * @param busiCompRequest
	 * @return
	 */
	@ZteMethodAnnontion(name = "[AOP]订单返销", group_name = "退单", order = "10", page_show = true, path = "zteOrderReturnedTraceRule.orderReverseSales")
	public BusiCompResponse orderReverseSales(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		BusiDealResult orderResp = ordVisitTacheManager.orderReverseSales(order_id);
		if (!orderResp.getError_code().equals(EcsOrderConsts.AOP_SUCCESS_0000)) {
			CommonTools.addBusiError(orderResp.getError_code(), orderResp.getError_msg());
		}
		resp.setError_code("0");
		resp.setError_msg("AOP返销成功");
		resp.setResponse(orderResp.getResponse());
		return resp;
	}

	/**
	 * [AOP]后激活订单信息同步接口
	 * 
	 * @param busiCompRequest
	 * @return songqi
	 */
	@ZteMethodAnnontion(name = "[AOP]后激活订单信息同步接口", group_name = "同步", order = "10", page_show = true, path = "zteOrderReturnedTraceRule.orderInfoSynToAOP")
	public BusiCompResponse orderInfoSynToAOP(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		OrderInfoSynReq orderInfoSynReq = new OrderInfoSynReq();
		OrderInfoSynRsp orderInfoSynRsp = new OrderInfoSynRsp();
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		try {
			orderInfoSynReq.setNotNeedReqStrOrderId(order_id);
			orderInfoSynRsp = client.execute(orderInfoSynReq, OrderInfoSynRsp.class);
			if (!orderInfoSynRsp.getRes_code().equals(EcsOrderConsts.BSS_SUCCESS_00000) || null == orderInfoSynRsp.getResultMsg() || null == orderInfoSynRsp.getResultMsg().getOrderId()) {
				CommonTools.addBusiError(orderInfoSynRsp.getError_code(), orderInfoSynRsp.getError_msg());
			}
			String orderId = orderInfoSynRsp.getResultMsg().getOrderId();// 返回的号码商城订单ID，正确应答必传,如果已传的话不会返回单号
			if (!StringUtil.isEmpty(orderId)) {
				CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.SRC_OUT_TID }, new String[] { orderId });
			}
			// syn_ord_zb同步总商标记1
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.SYN_ORD_ZB }, new String[] { "1" });

			resp.setError_code("0");
			resp.setError_msg("后激活订单同步成功");
			resp.setResponse(orderInfoSynRsp);
		} catch (Exception ex) {
			ex.printStackTrace();
			resp.setError_code("-1");
			resp.setError_msg("同步失败[" + orderInfoSynRsp.getDetail() + ex.getMessage() + "]");

			ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
			String bill_num = cacheUtil.getConfigInfo("SYN_BILL_NUM");
			String info1 = cacheUtil.getConfigInfo("SYN_NUM_INFO_L");
			String info2 = cacheUtil.getConfigInfo("SYN_NUM_INFO_M");
			String info3 = cacheUtil.getConfigInfo("SYN_NUM_INFO_R");
			// 短信接收号码,逗号分割号码
			String receive_phonenums = cacheUtil.getConfigInfo("ORD_SYN_AOP_NUM");
			if (!StringUtils.isEmpty(receive_phonenums)) {
				String[] receive_phonenums_array = receive_phonenums.split(",");
				for (String receive_phonenum : receive_phonenums_array) {
					AopSmsSendReq smsSendReq = new AopSmsSendReq();
					String msg = info1 + order_id + info2 + orderInfoSynReq.getNumId().get(0).getSerialNumber() + info3;
					smsSendReq.setSms_data(msg);
					smsSendReq.setBill_num(bill_num);// 短信发送号码
					smsSendReq.setOrder_id(order_id);
					smsSendReq.setService_num(receive_phonenum);
					AopSmsSendResp smsSendResp = client.execute(smsSendReq, AopSmsSendResp.class);
					if (smsSendResp != null && ConstsCore.ERROR_SUCC.equals(smsSendResp.getError_code())) {
						logger.info("订单信息同步定时任务失败数据提醒内容发送成功！");
					} else {
						logger.info("订单信息同步定时任务失败数据提醒内容发送失败！");
					}
				}
			}
		}
		return resp;
	}

	/**
	 * [BSS]订单返销
	 * 
	 * @param busiCompRequest
	 * @return
	 */
	@ZteMethodAnnontion(name = "[BSS]订单返销", group_name = "退单", order = "10", page_show = true, path = "zteOrderReturnedTraceRule.orderReverseSalesBSS")
	public BusiCompResponse orderReverseSalesBSS(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		BusiDealResult orderResp = ordVisitTacheManager.orderReverseSalesBSS(order_id);
		if (!orderResp.getError_code().equals(EcsOrderConsts.AOP_SUCCESS_0000)) {
			CommonTools.addBusiError(orderResp.getError_code(), orderResp.getError_msg());
		}
		resp.setError_code("0");
		resp.setError_msg("BSS返销成功");
		resp.setResponse(orderResp.getResponse());
		return resp;
	}

	@ZteMethodAnnontion(name = "订单返销[方案]", group_name = "退单", order = "11", page_show = true, path = "zteOrderReturnedTraceRule.orderReverseSalesPlan")
	public BusiCompResponse orderReverseSalesPlan(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		PlanRuleTreeExeReq req = new PlanRuleTreeExeReq();
		TacheFact fact = new TacheFact();
		fact.setOrder_id(busiCompRequest.getOrder_id());
		req.setPlan_id(EcsOrderConsts.ORDER_REVERSE_SALE_AOP);
		req.setFact(fact);
		req.setDeleteLogs(true);
		PlanRuleTreeExeResp planResp = CommonDataFactory.getInstance().exePlanRuleTree(req);
		BusiCompResponse busiResp = CommonDataFactory.getInstance().getRuleTreeresult(planResp);
		if (!StringUtils.equals(busiResp.getError_code(), ConstsCore.ERROR_SUCC)) {
			CommonTools.addBusiError("-99999", planResp.getError_msg());
		}
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}

	@ZteMethodAnnontion(name = "[AOP]副卡号码批量释放[方案]", group_name = "退单", order = "12", page_show = true, path = "zteOrderReturnedTraceRule.numberReleaseFukaAop")
	public BusiCompResponse numberReleaseFukaAop(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		String phone_num = CommonDataFactory.getInstance().getAttrFieldValue(busiCompRequest.getOrder_id(), AttrConsts.PHONE_NUM);
		String occupancySysNew = CommonDataFactory.getInstance().sectionNoOccupancySysPlan(busiCompRequest.getOrder_id(), phone_num);
		if (StringUtils.equals(occupancySysNew, EcsOrderConsts.NUMBER_OCCUPANCY_TYPE_1) || StringUtils.equals(occupancySysNew, EcsOrderConsts.NUMBER_OCCUPANCY_TYPE_2)) {
			PlanRuleTreeExeReq req = new PlanRuleTreeExeReq();
			TacheFact fact = new TacheFact();
			fact.setOrder_id(busiCompRequest.getOrder_id());
			req.setPlan_id(EcsOrderConsts.NUMBER_RELEASE_AOP_FUKA);
			req.setFact(fact);
			req.setDeleteLogs(true);
			PlanRuleTreeExeResp planResp = CommonDataFactory.getInstance().exePlanRuleTree(req);
			BusiCompResponse busiResp = CommonDataFactory.getInstance().getRuleTreeresult(planResp);
			if (!StringUtils.equals(busiResp.getError_code(), ConstsCore.ERROR_SUCC)) {
				CommonTools.addBusiError("-99999", planResp.getError_msg());
			}
		}
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}

	@ZteMethodAnnontion(name = "[bss]副卡号码批量释放[方案]", group_name = "退单", order = "13", page_show = true, path = "zteOrderReturnedTraceRule.numberReleaseFukaBss")
	public BusiCompResponse numberReleaseFukaBss(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		String phone_num = CommonDataFactory.getInstance().getAttrFieldValue(busiCompRequest.getOrder_id(), AttrConsts.PHONE_NUM);
		String occupancySysNew = CommonDataFactory.getInstance().sectionNoOccupancySysPlan(busiCompRequest.getOrder_id(), phone_num);
		if (StringUtils.equals(occupancySysNew, EcsOrderConsts.NUMBER_OCCUPANCY_TYPE_0) || StringUtils.equals(occupancySysNew, EcsOrderConsts.NUMBER_OCCUPANCY_TYPE_1)) {
			PlanRuleTreeExeReq req = new PlanRuleTreeExeReq();
			TacheFact fact = new TacheFact();
			fact.setOrder_id(busiCompRequest.getOrder_id());
			req.setPlan_id(EcsOrderConsts.NUMBER_RELEASE_BSS_FUKA);
			req.setFact(fact);
			req.setDeleteLogs(true);
			PlanRuleTreeExeResp planResp = CommonDataFactory.getInstance().exePlanRuleTree(req);
			BusiCompResponse busiResp = CommonDataFactory.getInstance().getRuleTreeresult(planResp);
			if (!StringUtils.equals(busiResp.getError_code(), ConstsCore.ERROR_SUCC)) {
				CommonTools.addBusiError("-99999", planResp.getError_msg());
			}
		}
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}

	@ZteMethodAnnontion(name = "退单结果通知总部", group_name = "通用组件", order = "14", page_show = true, path = "zteOrderReturnedTraceRule.stateSynReturnStaToZB")
	public BusiCompResponse stateSynReturnStaToZB(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = getOrderId(busiCompRequest);
		if (StringUtil.isEmpty(order_id))
			order_id = busiCompRequest.getOrder_id();
		StateSynToZBRequest req = new StateSynToZBRequest();
		req.setNotNeedReqStrOrderId(order_id);
		req.setNotNeedReqStrStateTag(EcsOrderConsts.STATE_CHG_RETURN);
		StateSynToZBResponse sresp = infServices.stateSynToZB(req);
		if (StringUtils.equals(sresp.getRespCode(), EcsOrderConsts.ZB_INF_RESP_CODE_0000) || StringUtils.equals(sresp.getRespCode(), EcsOrderConsts.ZB_INF_RESP_CODE_0001)) {
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.ZB_REFUND_STATUS, AttrConsts.REFUND_STATUS },
					new String[] { EcsOrderConsts.ZB_REFUND_STATUS_2, EcsOrderConsts.REFUND_STATUS_01 });

			// 设置退款状态
			String results = EcsOrderConsts.NO_DEFAULT_VALUE;
			if (StringUtils.equals(sresp.getRespCode(), EcsOrderConsts.ZB_INF_RESP_CODE_0000)) {
				results = EcsOrderConsts.BSS_REFUND_STATUS_ONLINE_SUCC;
			} else {
				results = EcsOrderConsts.BSS_REFUND_STATUS_ONLINE_FAIL;
				// 退款失败记录轨迹信息
				OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
				OrderHandleLogsAddReq handleReq = new OrderHandleLogsAddReq();
				String flow_id = orderTree.getOrderExtBusiRequest().getFlow_id();
				String flowTraceId = orderTree.getOrderExtBusiRequest().getFlow_trace_id();
				OrderHandleLogs logReq = new OrderHandleLogs();
				logReq.setOrder_id(order_id);
				logReq.setFlow_id(flow_id);
				logReq.setFlow_trace_id(flowTraceId);
				logReq.setOp_id("1");
				logReq.setOp_name("admin");
				logReq.setComments("退款失败原因：" + sresp.getRespDesc());
				logReq.setHandler_type(EcsOrderConsts.ORDER_HANDLER_TYPE_RETURNED);
				logReq.setType_code(EcsOrderConsts.REFUND_STATUS_09);
				handleReq.setOrderHandleLogs(logReq);
				ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
				OrderHandleLogsAddResp handleResp = client.execute(handleReq, OrderHandleLogsAddResp.class);
			}
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.BSS_REFUND_STATUS }, new String[] { results });

		} else {
			CommonTools.addBusiError("-99999", sresp.getRespDesc());
		}
		resp.setError_code("0");
		resp.setError_msg("成功");
		return resp;
	}

	/**
	 * 订单变更通知淘宝(发货)
	 * 
	 * @throws ApiBusiException
	 */
	@ZteMethodAnnontion(name = "订单状态变更通知天猫天机(退单)", group_name = "退单", order = "5", page_show = true, path = "ZteOrderReturnedTraceRule.notifyOrderStatusToTaobaoTianji")
	public BusiCompResponse notifyOrderStatusToTaobaoTianji(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();

		TbTianjiOrderStatusNoticeReq req = new TbTianjiOrderStatusNoticeReq();
		req.setNotNeedReqStrOrderId(order_id);

		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		TbTianjiOrderStatusNoticeResp taobaoResp = client.execute(req, TbTianjiOrderStatusNoticeResp.class);
		if (!EcsOrderConsts.BUSI_DEAL_RESULT_0000.equals(taobaoResp.getError_code())) {
			CommonTools.addBusiError("-99999", taobaoResp.getError_msg());
		} else {
			resp.setError_code("0");
			resp.setError_msg("执行成功");
		}
		return resp;

	}

	/**
	 * [爬虫]退单申请调总商
	 * 
	 * @param busiCompRequest
	 * @return
	 * @throws ApiBusiException
	 */
	@ZteMethodAnnontion(name = "[爬虫]退单申请调总商", group_name = "通用组件", order = "14", page_show = true, path = "zteOrderReturnedTraceRule.crawlerStateSynReturnStaToZS")
	public BusiCompResponse crawlerStateSynReturnStaToZS(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		BusiDealResult dealResult = ordVisitTacheManager.crawlerStateSynReturnStaToZS(order_id);
		if (!dealResult.getError_code().equals(EcsOrderConsts.BUSI_DEAL_RESULT_0000))
			CommonTools.addBusiError(dealResult.getError_code(), dealResult.getError_msg());
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;

	}

	/**
	 * [爬虫]确认退单调总商
	 * 
	 * @param busiCompRequest
	 * @return
	 * @throws ApiBusiException
	 */
	@ZteMethodAnnontion(name = "[爬虫]确认退单调总商", group_name = "通用组件", order = "14", page_show = true, path = "zteOrderReturnedTraceRule.crawlerOrdRefundConfirmToZS")
	public BusiCompResponse crawlerOrdRefundConfirmToZS(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		// 由于爬虫和订单分开部署，爬虫无法访问数据库所以将所有爬虫测取数据的方法全部移动到订单测传递
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		String orderNo = orderTree.getOrderExtBusiRequest().getOut_tid();
		String backReasonDesc = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.REFUND_DESC);
		String orderDelayTag = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.IS_REFUND);

		BackOrderLayerReq req = new BackOrderLayerReq();
		req.setNotNeedReqStrOrderId(order_id);
		req.setOrderId(orderNo);
		req.setBackReasonDesc(backReasonDesc);
		req.setOrderDelayTag(orderDelayTag);

		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		ZteResponse rsp = client.execute(req, ZteResponse.class);
		if (!ConstsCore.ERROR_SUCC.equals(rsp.getError_code())) {
			logger.info("=====ZteOrderReturnedTraceRule crawlerOrdRefundConfirmToZS 退单审核退单接口调用出错【errorMsg:" + resp.getError_msg() + "】");
			CommonTools.addBusiError(EcsOrderConsts.BUSI_DEAL_RESULT_FAIL, rsp.getError_msg());
		}
		resp.setError_code("0");
		resp.setError_msg("成功");
		return resp;
	}

	/**
	 * [爬虫]确认退单状态更改
	 * 
	 * @param busiCompRequest
	 * @return
	 * @throws ApiBusiException
	 */
	@ZteMethodAnnontion(name = "[爬虫]确认退单状态更改", group_name = "通用组件", order = "14", page_show = true, path = "zteOrderReturnedTraceRule.crawlerOrdRefundConfirm")
	public BusiCompResponse crawlerOrdRefundConfirm(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		Map params = busiCompRequest.getQueryParams();
		String deal_desc = params.get("deal_desc") == null ? "" : params.get("deal_desc").toString();
		String order_id = busiCompRequest.getOrder_id();

		OrderExtBusiRequest orderExt = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest();
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		orderExt.setRefund_status(EcsOrderConsts.REFUND_STATUS_01);
		orderExt.setLast_deal_time(Consts.SYSDATE);
		orderExt.setIs_dirty(ConstsCore.IS_DIRTY_YES);
		orderExt.setDb_action(ConstsCore.DB_ACTION_UPDATE);
		orderExt.store();

		CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.REFUND_STATUS }, new String[] { EcsOrderConsts.REFUND_STATUS_01 });// 更新属性

		// 写日志
		OrderHandleLogsReq req = new OrderHandleLogsReq();
		String flow_id = orderTree.getOrderExtBusiRequest().getFlow_id();
		String flowTraceId = orderTree.getOrderExtBusiRequest().getFlow_trace_id();
		req.setOrder_id(order_id);
		req.setFlow_id(flow_id);
		req.setFlow_trace_id(flowTraceId);
		req.setComments("手动退单确认：" + deal_desc);
		req.setHandler_type(com.ztesoft.ibss.common.util.Const.ORDER_HANDLER_TYPE_RETURNED);
		req.setType_code(EcsOrderConsts.REFUND_STATUS_01);
		AdminUser user = ManagerUtils.getAdminUser();
		if (null != user) {
			req.setOp_id(user.getUserid());
			req.setOp_name(user.getUsername());
		} else {
			req.setOp_id("1");
			req.setOp_name("admin");
		}
		this.ordFlowManager.insertOrderHandLog(req);
		resp.setError_code("0");
		resp.setError_msg("成功");
		return resp;

	}

	/**
	 * [爬虫]取消退单调总商
	 * 
	 * @throws ApiBusiException
	 */
	@ZteMethodAnnontion(name = "[爬虫]取消退单调总商", group_name = "退单", order = "5", page_show = true, path = "zteOrderReturnedTraceRule.crawlerCancelReturnedToZS")
	public BusiCompResponse crawlerCancelReturnedToZS(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();

		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		// 调用爬虫
		RejectLayerReq req = new RejectLayerReq();
		String orderId = orderTree.getOrderExtBusiRequest().getOrder_num();
		String rejectReasonDesc = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.REFUND_DESC);

		req.setNotNeedReqStrOrderId(order_id);
		req.setOrderId(orderId);
		req.setRejectReasonDesc(rejectReasonDesc);

		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		ZteResponse rsp = client.execute(req, ZteResponse.class);
		if (!ConstsCore.ERROR_SUCC.equals(rsp.getError_code())) {
			logger.info("=====ZteOrderReturnedTraceRule crawlerCancelReturnedToZS 退单审核驳回接口调用出错【errorMsg:" + resp.getError_msg() + "】");
			CommonTools.addBusiError(EcsOrderConsts.BUSI_DEAL_RESULT_FAIL, rsp.getError_msg());
		}
		resp.setError_code("0");
		resp.setError_msg("成功");
		return resp;

	}

	/**
	 * [爬虫]取消退单状态更改
	 * 
	 * @throws ApiBusiException
	 */
	@ZteMethodAnnontion(name = "[爬虫]取消退单状态更改", group_name = "退单", order = "5", page_show = true, path = "zteOrderReturnedTraceRule.crawlerCancelReturned")
	public BusiCompResponse crawlerCancelReturned(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = getOrderId(busiCompRequest);
		if (StringUtil.isEmpty(order_id))
			order_id = busiCompRequest.getOrder_id();
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		OrderExtBusiRequest orderExtBusiRequest = orderTree.getOrderExtBusiRequest();
		orderExtBusiRequest.setRefund_deal_type(EcsOrderConsts.REFUND_DEAL_TYPE_01);
		orderExtBusiRequest.setRefund_status(EcsOrderConsts.REFUND_STATUS_00);
		orderExtBusiRequest.setVisible_status(EcsOrderConsts.VISIBLE_STATUS_0);
		orderExtBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
		orderExtBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
		orderExtBusiRequest.store();
		/*
		 * CommonDataFactory.getInstance().updateAttrFieldValue(order_id,
		 * //取消退单，设置未执行退款规则 new String[]{AttrConsts.REFUND_RULE_EXECUTE}, new
		 * String[]{EcsOrderConsts.NO_DEFAULT_VALUE});
		 */
		resp.setError_code("0");
		resp.setError_msg("成功");
		return resp;
	}

	@ZteMethodAnnontion(name = "[总商爬虫同步]总商系统退单", group_name = "退单", order = "5", page_show = true, path = "zteOrderReturnedTraceRule.ZbSystemReturned")
	public BusiCompResponse ZbSystemReturned(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = getOrderId(busiCompRequest);
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		OrderExtBusiRequest orderExt = orderTree.getOrderExtBusiRequest();
		orderExt.setRefund_deal_type(EcsOrderConsts.REFUND_DEAL_TYPE_02);
		orderExt.setRefund_status(EcsOrderConsts.REFUND_STATUS_08);
		orderExt.setVisible_status(EcsOrderConsts.VISIBLE_STATUS_0);
		orderExt.setIs_dirty(ConstsCore.IS_DIRTY_YES);
		orderExt.setDb_action(ConstsCore.DB_ACTION_UPDATE);
		orderExt.store();
		// 记录退单原因
		CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.RETURNED_REASON_CODE, AttrConsts.ZB_REFUND_STATUS, AttrConsts.REFUND_AUDIT_MODE },
				new String[] { "总商系统发起退单", ZjEcsOrderConsts.IS_DEFAULT_VALUE, EcsOrderConsts.REFUND_AUDIT_HAND });

		resp.setError_code("0");
		resp.setError_msg("成功");
		return resp;
	}

	/**
	 * 订单退单申请通知支付中心
	 * 
	 * @throws ApiBusiException
	 */
	@ZteMethodAnnontion(name = "订单退单申请通知支付中心", group_name = "退单", order = "3", page_show = true, path = "ZteOrderReturnedTraceRule.orderBackApplyUpdateStatuToPayment")
	public BusiCompResponse orderBackApplyUpdateStatuToPayment(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = getOrderId(busiCompRequest);
		// 调[订单退单申请通知支付中心]接口【待开发】
		BroadbandrefundReq req = new BroadbandrefundReq();
		req.setNotNeedReqStrOrderId(order_id);
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		BroadbandRefundResp refundResp = client.execute(req, BroadbandRefundResp.class);
		if (!EcsOrderConsts.INF_RESP_CODE_00000.equals(refundResp.getError_code())) {
			CommonTools.addBusiError(EcsOrderConsts.BUSI_DEAL_RESULT_FAIL, refundResp.getError_msg());
		} else {
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.BSS_REFUND_STATUS }, new String[] { EcsOrderConsts.BSS_REFUND_STATUS_ONLINE_SUCC });
			OrderExtBusiRequest orderExt = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest();
			orderExt.setRefund_time(Consts.SYSDATE);
			orderExt.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			orderExt.setDb_action(ConstsCore.DB_ACTION_UPDATE);
			orderExt.store();
			resp.setError_code("0");
			resp.setError_msg("执行成功");
		}

		return resp;

	}

	/**
	 * 订单退单申请通知支付中心
	 * 
	 * @throws ApiBusiException
	 */
	@ZteMethodAnnontion(name = "订单退单申请支付中心", group_name = "退单", order = "3", page_show = true, path = "ZteOrderReturnedTraceRule.orderBackApplyToPayment")
	public BusiCompResponse orderBackApplyToPayment(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = getOrderId(busiCompRequest);
		// 调[订单退单申请通知支付中心]接口【待开发】
		BroadbandOrderInfoRefundReq req = new BroadbandOrderInfoRefundReq();
		req.setNotNeedReqStrOrderId(order_id);
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		BroadbandOrderInfoRefundResp refundResp = client.execute(req, BroadbandOrderInfoRefundResp.class);
		if (!EcsOrderConsts.INF_RESP_CODE_0000.equals(refundResp.getResp_code())) {
			CommonTools.addBusiError(EcsOrderConsts.BUSI_DEAL_RESULT_FAIL, refundResp.getResp_msg());
		} else {
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.BSS_REFUND_STATUS, "refundplatformorderid" },
					new String[] { EcsOrderConsts.BSS_REFUND_STATUS_ONLINE_SUCC, refundResp.getOutRefundNo() });
			OrderExtBusiRequest orderExt = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest();
			orderExt.setRefund_time(Consts.SYSDATE);
			orderExt.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			orderExt.setDb_action(ConstsCore.DB_ACTION_UPDATE);
			orderExt.store();
			resp.setError_code("0");
			resp.setError_msg("执行成功");
		}

		return resp;

	}

	@ZteMethodAnnontion(name = "退单审核结果通知码上购", group_name = "退单", order = "15", page_show = true, path = "zteOrderReturnedTraceRule.syncReturnAuditResultToMSG")
	public BusiCompResponse syncReturnAuditResultToMSG(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = getOrderId(busiCompRequest);
		ZteResponse rsp = new ZteResponse();
		// TODO 调退单审核结果通知码上购接口
		if (!ConstsCore.ERROR_SUCC.equals(rsp.getError_code())) {
			CommonTools.addBusiError(EcsOrderConsts.BUSI_DEAL_RESULT_FAIL, rsp.getError_msg());
		}
		resp.setError_code("0");
		resp.setError_msg("成功");
		return resp;
	}

	@ZteMethodAnnontion(name = "订单撤销申请通知BSS", group_name = "退单", order = "16", page_show = true, path = "zteOrderReturnedTraceRule.syncOrderCancelApplyToBSS")
	public BusiCompResponse syncOrderCancelApplyToBSS(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = getOrderId(busiCompRequest);
		// 调订单撤销申请通知BSS接口
		OrderCancelPreCheckReq req = new OrderCancelPreCheckReq();
		req.setNotNeedReqStrOrderId(order_id);
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		OrderCancelPreCheckResponse ocPreCheckResp = client.execute(req, OrderCancelPreCheckResponse.class);

		if (!EcsOrderConsts.AIP_STATUS_CODE_SUCC.equals(ocPreCheckResp.getCode())) {
			CommonTools.addBusiError(EcsOrderConsts.BUSI_DEAL_RESULT_FAIL, ocPreCheckResp.getMsg());
		}
		resp.setError_code("0");
		resp.setError_msg("成功");
		return resp;
	}

	@ZteMethodAnnontion(name = "订单正式撤单通知BSS", group_name = "退单", order = "17", page_show = true, path = "zteOrderReturnedTraceRule.syncOrderCancelCfmToBSS")
	public BusiCompResponse syncOrderCancelCfmToBSS(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = getOrderId(busiCompRequest);
		ZteResponse rsp = new ZteResponse();
		// 调订单正式撤单通知BSS接口
		OrderCancelSubmitReq req = new OrderCancelSubmitReq();
		req.setNotNeedReqStrOrderId(order_id);
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		OrderCancelSubmitResponse ocSubmitResp = client.execute(req, OrderCancelSubmitResponse.class);
		if (!EcsOrderConsts.AIP_STATUS_CODE_SUCC.equals(ocSubmitResp.getCode())) {
			CommonTools.addBusiError(EcsOrderConsts.BUSI_DEAL_RESULT_FAIL, ocSubmitResp.getMsg());
		}
		resp.setError_code("0");
		resp.setError_msg("成功");
		return resp;
	}

	/**
	 * 后向激活订单撤单
	 * 
	 * @param busiCompRequest
	 * @return
	 * @throws ApiBusiException
	 */
	@ZteMethodAnnontion(name = "后向激活订单撤单AOP", group_name = "通用组件", order = "14", page_show = true, path = "zteOrderReturnedTraceRule.laterActiveOrderCancel")
	public BusiCompResponse laterActiveOrderCancel(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		OrderRealNameInfoBusiRequest orderRealNameBusi = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderRealNameInfoBusiRequest();
		if (EcsOrderConsts.NO_DEFAULT_VALUE.equals(orderRealNameBusi.getCancel_type()) || StringUtils.isEmpty(orderRealNameBusi.getCancel_type())) {
			// 线上调撤单接口
			ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
			OrderCancelReq cancelReq = new OrderCancelReq();
			cancelReq.setNotNeedReqStrOrderId(order_id);
			OrderCancelResp rsp = client.execute(cancelReq, OrderCancelResp.class);
			if (!StringUtils.equals(rsp.getError_code(), EcsOrderConsts.AOP_HTTP_STATUS_CODE_200 + "")) {
				// 撤单失败
				resp.setError_code(rsp.getCode());
				resp.setError_msg("订单撤单失败," + rsp.getDetail());
				return resp;
			}
			orderRealNameBusi.setCancel_type(EcsOrderConsts.NO_DEFAULT_VALUE);// 线上
			orderRealNameBusi.setCancel_flag(EcsOrderConsts.LATER_CANCEL_STATUS_3);// 线上撤单成功
		} else {
			orderRealNameBusi.setCancel_type(EcsOrderConsts.IS_DEFAULT_VALUE);// 线下
			orderRealNameBusi.setCancel_flag(EcsOrderConsts.LATER_CANCEL_STATUS_2);// 线下撤单成功
		}
		// 修改状态
		orderRealNameBusi.setIs_dirty(ConstsCore.IS_DIRTY_YES);
		orderRealNameBusi.setDb_action(ConstsCore.DB_ACTION_UPDATE);
		orderRealNameBusi.store();

		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}

	/**
	 * CBSS返销费用查询接口
	 * 
	 * @throws ApiBusiException
	 */
	@ZteMethodAnnontion(name = "CBSS返销费用查询接口", group_name = "退单", order = "3", page_show = true, path = "ZteOrderReturnedTraceRule.cbssrefundFeeQry")
	public BusiCompResponse cbssrefundFeeQry(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = getOrderId(busiCompRequest);
		CbssrefundFeeQryReq req = new CbssrefundFeeQryReq();
		req.setNotNeedReqStrOrderId(order_id);
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		CbssrefundFeeQryResp refundResp = client.execute(req, CbssrefundFeeQryResp.class);
		String paymoney = Math.round(CommonDataFactory.getInstance().getOrderTree(order_id).getOrderBusiRequest().getPaymoney() * 100) + "";
		if (!EcsOrderConsts.INF_RESP_CODE_00000.equals(refundResp.getCode())) {
			CommonTools.addBusiError(EcsOrderConsts.BUSI_DEAL_RESULT_FAIL, refundResp.getMsg());
		} else {
			if (!StringUtil.isEmpty(refundResp.getRespJson().getFee()) && paymoney.equals(refundResp.getRespJson().getFee())) {
				CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.BIP_CODE }, new String[] { refundResp.getRespJson().getFx_order_id() });
				resp.setError_code("0");
				resp.setError_msg("执行成功");
			} else {
				resp.setError_code("-1");
				resp.setError_msg("未查询到费用");
			}

		}

		return resp;

	}
	
	/**
	 * 商品预定回填接口
	 * 
	 * @throws ApiBusiException
	 */
	@ZteMethodAnnontion(name = "商品预定回填接口", group_name = "商品预定回填接口", order = "3", page_show = true, path = "ZteOrderReturnedTraceRule.orderReceiveBack")
	public BusiCompResponse orderReceiveBack(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = getOrderId(busiCompRequest);
		BusiDealResult result = ordIntentManager.orderReceiveBack(order_id);
		if (!result.getError_code().equals(EcsOrderConsts.AOP_SUCCESS_0000)) {
			CommonTools.addBusiError(result.getError_code(), result.getError_msg());
		}
		resp.setError_code("0");
		resp.setError_msg(result.getError_msg());
		resp.setResponse(result.getResponse());
		return resp;
	}
	
	/**
	 * 3.1.17.	订单信息回填接口
	 * 
	 * @throws ApiBusiException
	 */
	@ZteMethodAnnontion(name = "订单信息回填接口", group_name = "订单信息回填接口", order = "3", page_show = true, path = "ZteOrderReturnedTraceRule.orderInfoBackfill")
	public BusiCompResponse orderInfoBackfill(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = getOrderId(busiCompRequest);
		BusiDealResult result = new BusiDealResult();
		try {
			result = ordIntentManager.orderInfoBackfill(order_id);
			if (!result.getError_code().equals(EcsOrderConsts.AOP_SUCCESS_0000)) {
				CommonTools.addBusiError(result.getError_code(), result.getError_msg());
			}
			resp.setError_code("0");
			resp.setError_msg(result.getError_msg());
			resp.setResponse(result.getResponse());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}

}
