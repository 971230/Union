package zte.net.common.rule;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.ztesoft.api.ApiBusiException;
import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.common.util.date.DateUtil;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.consts.ZjEcsOrderConsts;
import com.ztesoft.net.mall.core.service.impl.RequestStoreManager;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.model.BusiDealResult;
import com.ztesoft.net.service.IOrdPickingTacheManager;
import com.ztesoft.net.service.IOrdVisitTacheManager;
import com.ztesoft.net.service.IOrdWorkManager;
import com.ztesoft.net.service.IOrderFlowManager;

import commons.CommonTools;
import consts.ConstsCore;
import params.ZteResponse;
import params.req.CrawlerUpdateGoodsInfoReq;
import params.req.CrawlerUpdatePostInfoReq;
import params.req.ZbAuditStatusUpdateReq;
import params.req.ZbOrderDistributeReq;
import params.req.ZbOrderVerifyReq;
import params.resp.ZbAuditStatusUpdateResp;
import params.resp.ZbOrderDistributeResp;
import params.resp.ZbOrderVerifyResp;
import zte.net.common.annontion.context.action.ZteMethodAnnontion;
import zte.net.common.annontion.context.action.ZteServiceAnnontion;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.base.req.BusiCompRequest;
import zte.net.ecsord.params.base.resp.BusiCompResponse;
import zte.net.ecsord.params.bss.req.PreCommitReq;
import zte.net.ecsord.params.busi.req.OrderBusiRequest;
import zte.net.ecsord.params.busi.req.OrderDeliveryBusiRequest;
import zte.net.ecsord.params.busi.req.OrderExtBusiRequest;
import zte.net.ecsord.params.busi.req.OrderItemProdBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;
import zte.net.ecsord.rule.mode.ModeFact;
import zte.net.ecsord.rule.tache.TacheFact;
import zte.net.ecsord.rule.workflow.WorkFlowFact;
import zte.net.params.req.CataloguePlanExeReq;
import zte.net.params.req.PlanRuleTreeExeReq;
import zte.net.params.resp.PlanRuleTreeExeResp;

/**
 * 
 * 3.5.3 订单预处理规则集_客户回访规则处理
 * 
 * @author wu.i
 */
@ZteServiceAnnontion(trace_name = "ZteCustomVisitTraceRule", trace_id = "1", version = "1.0", desc = "客户回访环节业务通知组件")
public class ZteCustomVisitTraceRule extends ZteTraceBaseRule {
	private static Logger logger = Logger.getLogger(ZteCustomVisitTraceRule.class);
	@Resource
	private IOrdVisitTacheManager ordVisitTacheManager;

	@Resource
	private IOrderFlowManager ordFlowManager;

	@Resource
	private IOrdPickingTacheManager ordPickingTacheManager;

	@Resource
	private IOrdWorkManager ordWorkManager;
	
	/**
	 * 客户回访环节入口
	 */
	@Override
	@ZteMethodAnnontion(name = "客户回访环节入口", group_name = "订单预处理", order = "1", page_show = true, path = "customVisitTraceRule.exec")
	public BusiCompResponse engineDo(BusiCompRequest busiCompRequest) {
		BusiCompResponse resp = new BusiCompResponse();
		String orderId = busiCompRequest.getOrder_id();
		OrderTreeBusiRequest orderTree= CommonDataFactory.getInstance().getOrderTree(orderId);
		OrderBusiRequest orderBusiRequest = orderTree.getOrderBusiRequest();
		orderBusiRequest.setStatus(ZjEcsOrderConsts.DIC_ORDER_STATUS_2);
//		orderBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
//		orderBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
//		orderBusiRequest.store();
		
		String sql ="update es_order set status=?  where order_id=?";
		if(this.baseDaoSupport==null){
			this.baseDaoSupport=SpringContextHolder.getBean("baseDaoSupport");
		}
		this.baseDaoSupport.execute(sql, ZjEcsOrderConsts.DIC_ORDER_STATUS_2,orderId);
		//更新订单树缓存
		cache.set(RequestStoreManager.NAMESPACE, RequestStoreManager.DC_TREE_CACHE_NAME_KEY+"order_id"+ orderId,orderTree, RequestStoreManager.time);
		
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}

	/**
	 * 预校验
	 * 
	 * @throws ApiBusiException
	 */
	@ZteMethodAnnontion(name = "预校验（订单预处理）", group_name = "订单预处理", order = "1", page_show = true, path = "ZteCustomVisitTraceRule.preCheckToZb")
	public BusiCompResponse preCheckToZb(BusiCompRequest busiCompRequest) throws ApiBusiException {

		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();

		String vali_code = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ACCOUNT_VALI);
		String send_type = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.SENDING_TYPE);
		if (!EcsOrderConsts.SHIPPING_TYPE_XJ.equals(send_type) && (StringUtil.isEmpty(vali_code) || EcsOrderConsts.ACCOUNT_VALI_0.equals(vali_code))) {
			// 身份证预校验
			BusiDealResult certi_valide = ordVisitTacheManager.certiValide(order_id);
			if (!certi_valide.getError_code().equals(EcsOrderConsts.BUSI_DEAL_RESULT_0000))
				CommonTools.addBusiError(certi_valide.getError_code(), certi_valide.getError_msg());
		}

		// 总部开户预校验(ESS预校验)
		// 2016-4-26 只有跟总部交互才去总部校验
		String isSendZb = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.SEND_ZB);
		if (StringUtils.equals(EcsOrderConsts.IS_DEFAULT_VALUE, isSendZb)) {
			BusiDealResult pre_open_act = ordVisitTacheManager.preOpenAccountValide(order_id);
			if (!pre_open_act.getError_code().equals(EcsOrderConsts.BUSI_DEAL_RESULT_0000))
				CommonTools.addBusiError(pre_open_act.getError_code(), pre_open_act.getError_msg());
		}
		resp.setError_code("0");
		resp.setError_msg("预校验成功");
		return resp;
	}

	/**
	 * 模式匹配
	 */
	@ZteMethodAnnontion(name = "模式匹配（订单预处理）", group_name = "订单预处理", order = "2", page_show = true, path = "ZteCustomVisitTraceRule.matchingOrderModel")
	public BusiCompResponse matchingOrderModel(BusiCompRequest busiCompRequest) {
		OrderTreeBusiRequest orderTreeBusiRequest = CommonDataFactory.getInstance().getOrderTree(busiCompRequest.getOrder_id());
		BusiCompResponse resp = new BusiCompResponse();
		if (EcsOrderConsts.REP_EXEC_YES.equals(orderTreeBusiRequest.getCol1())) { // 客户回访、物流公司、是否闪电送值变更才需要重新匹配模式
																					// add
																					// bywui
			CataloguePlanExeReq req = new CataloguePlanExeReq();
			ModeFact fact = new ModeFact();
			fact.setOrder_id(busiCompRequest.getOrder_id());
			req.setCatalogue_id(EcsOrderConsts.ORDER_MODEL_MATCH_DIR);
			req.setFact(fact);
			CommonDataFactory.getInstance().exeCataloguePlan(req);
		}
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}

	/**
	 * 匹配工作流
	 */
	@ZteMethodAnnontion(name = "匹配工作流（订单预处理）", group_name = "订单预处理", order = "3", page_show = true, path = "ZteCustomVisitTraceRule.matchingWorkflow")
	public BusiCompResponse matchingWorkflow(BusiCompRequest busiCompRequest) {

		OrderTreeBusiRequest orderTreeBusiRequest = CommonDataFactory.getInstance().getOrderTree(busiCompRequest.getOrder_id());
		BusiCompResponse resp = new BusiCompResponse();
		if (EcsOrderConsts.REP_EXEC_YES.equals(orderTreeBusiRequest.getCol2())) { // 是否重新匹配流程，根据界面设置控制
																					// add
																					// by
																					// wui
			PlanRuleTreeExeReq req = new PlanRuleTreeExeReq();
			WorkFlowFact fact = new WorkFlowFact();
			fact.setOrder_id(busiCompRequest.getOrder_id());
			req.setPlan_id(EcsOrderConsts.WORK_FLOW_MATCH_PLAN);
			req.setFact(fact);
			CommonDataFactory.getInstance().exePlanRuleTree(req);
		}
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}

	/**
	 * 同步订单信息到物流
	 * 
	 * @throws ApiBusiException
	 */
	@ZteMethodAnnontion(name = "同步订单信息到物流（订单预处理）", group_name = "订单预处理", order = "4", page_show = true, path = "ZteCustomVisitTraceRule.orderSynToWl")
	public BusiCompResponse orderSynToWl(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		BusiDealResult dealResult = ordVisitTacheManager.orderSynToWl(order_id);
		if (EcsOrderConsts.INF_RESP_CODE_0000.equals(dealResult.getError_code())) {// 处理成功：
																					// 、记录同步物流公司时间
			OrderExtBusiRequest orderExt = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest();
			// orderExt.setVisible_status(EcsOrderConsts.VISIBLE_STATUS_1);
			// //没有用【sf人工筛单接口】，不再设置订单不可见，只记录时间
			orderExt.setSyn_wl_time(DateUtil.currentDateTime());
			orderExt.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			orderExt.setDb_action(ConstsCore.DB_ACTION_UPDATE);
			orderExt.store();

		} else {
			CommonTools.addBusiError("-999999", "物流公司处理失败:" + dealResult.getError_msg(), "1");
		}

		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;

	}

	/**
	 * 同步订单变更信息到总部
	 * 
	 * @throws ApiBusiException
	 */
	@ZteMethodAnnontion(name = "同步订单变更信息到总部", group_name = "订单预处理", order = "5", page_show = true, path = "ZteCustomVisitTraceRule.orderChangeWaitSynToZb")
	public BusiCompResponse orderChangeWaitSynToZb(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		BusiDealResult sysResp = ordVisitTacheManager.synOrdChangeToZB(order_id);
		if (!sysResp.getError_code().equals(EcsOrderConsts.ZB_INF_RESP_CODE_0000))
			CommonTools.addBusiError(sysResp.getError_code(), sysResp.getError_msg());
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;

	}

	/**
	 * 通知总部状态变更
	 * 
	 * @throws ApiBusiException
	 */
	@ZteMethodAnnontion(name = "通知总部状态变更（订单预处理）", group_name = "订单预处理", order = "6", page_show = true, path = "ZteCustomVisitTraceRule.preChangeStatusToZb")
	public BusiCompResponse preChangeStatusToZb(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		BusiDealResult notifyResp = ordVisitTacheManager.notifyAuditStatusToZB(order_id);
		if (!notifyResp.getError_code().equals(EcsOrderConsts.ZB_INF_RESP_CODE_0000))
			CommonTools.addBusiError(notifyResp.getError_code(), notifyResp.getError_msg());
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}

	/**
	 * 下一步（环节跳转）-自动化模式
	 */
	@ZteMethodAnnontion(name = "下一步（订单预处理）", group_name = "订单预处理", order = "7", page_show = true, path = "ZteCustomVisitTraceRule.nextStep")
	public BusiCompResponse nextStep(BusiCompRequest busiCompRequest) {
		try {
			if(ordWorkManager.isKDYQ(busiCompRequest.getOrder_id(), null)){
				Map queryParams = new HashMap();
				queryParams.put("deal_from", "page");//宽带电商化一期流程,下一步同步返回下一环节规则执行报错
				busiCompRequest.setQueryParams(queryParams);
			}
		} catch (ApiBusiException e) {
			e.printStackTrace();
		}
		
		BusiCompResponse resp = this.nextflow(busiCompRequest, false, "B");
		/**
		 * 商城一键开户，客户回访无需通知 modify by shusx String orderId =
		 * busiCompRequest.getOrder_id(); String isAop =
		 * CommonDataFactory.getInstance().getAttrFieldValue(orderId,
		 * AttrConsts.IS_AOP);
		 * if(StringUtils.equals(EcsOrderConsts.IS_DEFAULT_VALUE,
		 * isAop)){//aop的在这里通知新商城 StatuSynchReq statuSyn = new
		 * StatuSynchReq(orderId,EcsOrderConsts.DIC_ORDER_NODE_B,EcsOrderConsts.
		 * DIC_ORDER_NODE_B_DESC,EcsOrderConsts.IS_EASY_ACCOUNT_YES,
		 * EcsOrderConsts.ORDER_NODE_B_SUCCESS,""); //通知商城
		 * CommonDataFactory.getInstance().notifyNewShop(statuSyn); }
		 */
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}

	/**
	 * 转手工开户
	 * 
	 * @throws ApiBusiException
	 */
	@ZteMethodAnnontion(name = "转手工开户（订单预处理）", group_name = "订单预处理", order = "8", page_show = true, path = "ZteCustomVisitTraceRule.offLineOpenAccount")
	public BusiCompResponse offLineOpenAccount(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		BusiDealResult sysResp = ordFlowManager.offLineOpenAccount(order_id);
		if (!sysResp.getError_code().equals(EcsOrderConsts.BUSI_DEAL_RESULT_0000))
			CommonTools.addBusiError(sysResp.getError_code(), sysResp.getError_msg());
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}

	@ZteMethodAnnontion(name = "[AOP]预校验", group_name = "订单预处理", order = "9", page_show = true, path = "zteCustomVisitTraceRule.preCheckFromAop")
	public BusiCompResponse preCheckFromAop(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		PlanRuleTreeExeReq req = new PlanRuleTreeExeReq();
		TacheFact fact = new TacheFact();
		fact.setOrder_id(busiCompRequest.getOrder_id());
		req.setPlan_id(EcsOrderConsts.ORDER_PRE_VALIDATE_AOP);
		req.setFact(fact);
		req.setDeleteLogs(true);
		PlanRuleTreeExeResp planResp = CommonDataFactory.getInstance().exePlanRuleTree(req);
		BusiCompResponse busiResp = CommonDataFactory.getInstance().getRuleTreeresult(planResp);
		if (!StringUtils.equals(busiResp.getError_code(), ConstsCore.ERROR_SUCC)) {
			CommonTools.addBusiError("-99999", busiResp.getError_msg());
		}
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}

	@ZteMethodAnnontion(name = "[AOP]主卡号码预占[方案]", group_name = "订单预处理", order = "10", page_show = true, path = "zteCustomVisitTraceRule.numberPreOccupancyAop")
	public BusiCompResponse numberPreOccupancyAop(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		String phone_num = CommonDataFactory.getInstance().getAttrFieldValue(busiCompRequest.getOrder_id(), AttrConsts.PHONE_NUM);
		// String occupancySysNew =
		// CommonDataFactory.getInstance().sectionNoOccupancySysPlan(busiCompRequest.getOrder_id(),
		// phone_num);
		// and zhangjun 根据是否是aop 通道判断
		String is_aop = CommonDataFactory.getInstance().getAttrFieldValue(busiCompRequest.getOrder_id(), AttrConsts.IS_AOP);
		if (EcsOrderConsts.ACCOUNT_OPEN_CHANNEL_AOP.equals(is_aop)) {
			PlanRuleTreeExeReq req = new PlanRuleTreeExeReq();
			TacheFact fact = new TacheFact();
			fact.setOrder_id(busiCompRequest.getOrder_id());
			req.setPlan_id(EcsOrderConsts.NUMBER_PRE_OCCUPANCY_AOP);
			req.setFact(fact);
			req.setDeleteLogs(true);
			PlanRuleTreeExeResp planResp = CommonDataFactory.getInstance().exePlanRuleTree(req);
			BusiCompResponse busiResp = CommonDataFactory.getInstance().getRuleTreeresult(planResp);
			if (!StringUtils.equals(busiResp.getError_code(), ConstsCore.ERROR_SUCC)) {
				CommonTools.addBusiError("-99999", busiResp.getError_msg());
			}
		}
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}

	@ZteMethodAnnontion(name = "[AOP]主卡号码预定[方案]", group_name = "订单预处理", order = "11", page_show = true, path = "zteCustomVisitTraceRule.numberOccupancyAop")
	public BusiCompResponse numberOccupancyAop(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		String phone_num = CommonDataFactory.getInstance().getAttrFieldValue(busiCompRequest.getOrder_id(), AttrConsts.PHONE_NUM);
		// String occupancySysNew =
		// CommonDataFactory.getInstance().sectionNoOccupancySysPlan(busiCompRequest.getOrder_id(),
		// phone_num);
		// and zhangjun 根据是否是aop 通道判断
		String is_aop = CommonDataFactory.getInstance().getAttrFieldValue(busiCompRequest.getOrder_id(), AttrConsts.IS_AOP);
		if (EcsOrderConsts.ACCOUNT_OPEN_CHANNEL_AOP.equals(is_aop)) {
			PlanRuleTreeExeReq req = new PlanRuleTreeExeReq();
			TacheFact fact = new TacheFact();
			fact.setOrder_id(busiCompRequest.getOrder_id());
			req.setPlan_id(EcsOrderConsts.NUMBER_OCCUPANCY_AOP);
			req.setFact(fact);
			req.setDeleteLogs(true);
			PlanRuleTreeExeResp planResp = CommonDataFactory.getInstance().exePlanRuleTree(req);
			BusiCompResponse busiResp = CommonDataFactory.getInstance().getRuleTreeresult(planResp);
			if (!StringUtils.equals(busiResp.getError_code(), ConstsCore.ERROR_SUCC)) {
				CommonTools.addBusiError("-99999", busiResp.getError_msg());
			}
		}
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}

	/**
	 * 匹配订单生产中心
	 * 
	 * @param busiCompRequest
	 * @return
	 */
	@ZteMethodAnnontion(name = "匹配订单生产中心", group_name = "订单预处理", order = "12", page_show = true, path = "zteCustomVisitTraceRule.matchingProduceCenter")
	public BusiCompResponse matchingProduceCenter(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		PlanRuleTreeExeReq req = new PlanRuleTreeExeReq();
		TacheFact fact = new TacheFact();
		fact.setOrder_id(busiCompRequest.getOrder_id());
		req.setPlan_id(EcsOrderConsts.MATCH_PRODUCE_CENTER);
		req.setFact(fact);
		req.setDeleteLogs(true);
		PlanRuleTreeExeResp planResp = CommonDataFactory.getInstance().exePlanRuleTree(req);
		BusiCompResponse busiResp = CommonDataFactory.getInstance().getRuleTreeresult(planResp);
		if (!StringUtils.equals(busiResp.getError_code(), ConstsCore.ERROR_SUCC)) {
			CommonTools.addBusiError("-99999", busiResp.getError_msg());
		}
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}

	/**
	 * 社会渠道购机券换手机BSS端支持(加锁)
	 * 
	 * @param busiCompRequest
	 * @return
	 */
	@ZteMethodAnnontion(name = "社会渠道购机券换手机BSS端支持(加锁)visit", group_name = "订单预处理", order = "13", page_show = true, path = "zteCustomVisitTraceRule.purchaseCouponsLock")
	public BusiCompResponse purchaseCouponsLock(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		PlanRuleTreeExeReq req = new PlanRuleTreeExeReq();
		TacheFact fact = new TacheFact();
		fact.setOrder_id(busiCompRequest.getOrder_id());
		req.setPlan_id(EcsOrderConsts.PURCHASE_COUPONS_LOCK);
		req.setFact(fact);
		req.setDeleteLogs(true);
		PlanRuleTreeExeResp planResp = CommonDataFactory.getInstance().exePlanRuleTree(req);
		BusiCompResponse busiResp = CommonDataFactory.getInstance().getRuleTreeresult(planResp);
		if (!StringUtils.equals(busiResp.getError_code(), ConstsCore.ERROR_SUCC)) {// 失败时卡流程
			CommonTools.addBusiError("-99999", busiResp.getError_msg());
		}
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}

	@ZteMethodAnnontion(name = "[AOP]副卡号码批量预占[方案]", group_name = "订单预处理", order = "12", page_show = true, path = "zteCustomVisitTraceRule.numberPreOccupancyFukaAop")
	public BusiCompResponse numberPreOccupancyFukaAop(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		PlanRuleTreeExeReq req = new PlanRuleTreeExeReq();
		TacheFact fact = new TacheFact();
		fact.setOrder_id(busiCompRequest.getOrder_id());
		req.setPlan_id(EcsOrderConsts.NUMBER_PRE_OCCUPANCY_AOP_FUKA);
		req.setFact(fact);
		req.setDeleteLogs(true);
		PlanRuleTreeExeResp planResp = CommonDataFactory.getInstance().exePlanRuleTree(req);
		BusiCompResponse busiResp = CommonDataFactory.getInstance().getRuleTreeresult(planResp);
		if (!StringUtils.equals(busiResp.getError_code(), ConstsCore.ERROR_SUCC)) {
			CommonTools.addBusiError("-99999", busiResp.getError_msg());
		}
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}

	@ZteMethodAnnontion(name = "[AOP]副卡号码批量预定[方案]", group_name = "订单预处理", order = "13", page_show = true, path = "zteCustomVisitTraceRule.numberOccupancyFukaAop")
	public BusiCompResponse numberOccupancyFukaAop(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		String phone_num = CommonDataFactory.getInstance().getAttrFieldValue(busiCompRequest.getOrder_id(), AttrConsts.PHONE_NUM);
		String occupancySysNew = CommonDataFactory.getInstance().sectionNoOccupancySysPlan(busiCompRequest.getOrder_id(), phone_num);
		if (StringUtils.equals(occupancySysNew, EcsOrderConsts.NUMBER_OCCUPANCY_TYPE_1) || StringUtils.equals(occupancySysNew, EcsOrderConsts.NUMBER_OCCUPANCY_TYPE_2)) {
			PlanRuleTreeExeReq req = new PlanRuleTreeExeReq();
			TacheFact fact = new TacheFact();
			fact.setOrder_id(busiCompRequest.getOrder_id());
			req.setPlan_id(EcsOrderConsts.NUMBER_OCCUPANCY_AOP_FUKA);
			req.setFact(fact);
			req.setDeleteLogs(true);
			PlanRuleTreeExeResp planResp = CommonDataFactory.getInstance().exePlanRuleTree(req);
			BusiCompResponse busiResp = CommonDataFactory.getInstance().getRuleTreeresult(planResp);
			if (!StringUtils.equals(busiResp.getError_code(), ConstsCore.ERROR_SUCC)) {
				CommonTools.addBusiError("-99999", busiResp.getError_msg());
			}
		}
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}

	@ZteMethodAnnontion(name = "[BSS]副卡号码批量预占[方案]", group_name = "订单预处理", order = "14", page_show = true, path = "zteCustomVisitTraceRule.numberPreOccupancyFukaBss")
	public BusiCompResponse numberPreOccupancyFukaBss(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		String phone_num = CommonDataFactory.getInstance().getAttrFieldValue(busiCompRequest.getOrder_id(), AttrConsts.PHONE_NUM);
		String occupancySysNew = CommonDataFactory.getInstance().sectionNoOccupancySysPlan(busiCompRequest.getOrder_id(), phone_num);
		if (StringUtils.equals(occupancySysNew, EcsOrderConsts.NUMBER_OCCUPANCY_TYPE_0) || StringUtils.equals(occupancySysNew, EcsOrderConsts.NUMBER_OCCUPANCY_TYPE_1)) {
			PlanRuleTreeExeReq req = new PlanRuleTreeExeReq();
			TacheFact fact = new TacheFact();
			fact.setOrder_id(busiCompRequest.getOrder_id());
			req.setPlan_id(EcsOrderConsts.NUMBER_OCCUPANCY_BSS_FUKA);
			req.setFact(fact);
			req.setDeleteLogs(true);
			PlanRuleTreeExeResp planResp = CommonDataFactory.getInstance().exePlanRuleTree(req);
			BusiCompResponse busiResp = CommonDataFactory.getInstance().getRuleTreeresult(planResp);
			if (!StringUtils.equals(busiResp.getError_code(), ConstsCore.ERROR_SUCC)) {
				CommonTools.addBusiError("-99999", busiResp.getError_msg());
			}
		}
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}

	@ZteMethodAnnontion(name = "[BSS]预校验", group_name = "订单预处理", order = "9", page_show = true, path = "zteCustomVisitTraceRule.preCheckFromBSS")
	public BusiCompResponse preCheckFromBSS(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		PlanRuleTreeExeReq req = new PlanRuleTreeExeReq();
		TacheFact fact = new TacheFact();
		fact.setOrder_id(busiCompRequest.getOrder_id());
		req.setPlan_id(EcsOrderConsts.ORDER_PRE_VALIDATE_BSS);
		req.setFact(fact);
		req.setDeleteLogs(true);
		PlanRuleTreeExeResp planResp = CommonDataFactory.getInstance().exePlanRuleTree(req);
		BusiCompResponse busiResp = CommonDataFactory.getInstance().getRuleTreeresult(planResp);
		if (!StringUtils.equals(busiResp.getError_code(), ConstsCore.ERROR_SUCC)) {
			CommonTools.addBusiError("-99999", busiResp.getError_msg());
		}
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}

	@ZteMethodAnnontion(name = "PC批量写卡匹配队列", group_name = "订单预处理", order = "20", page_show = true, path = "zteCustomVisitTraceRule.writeCardQueueDistribute")
	public BusiCompResponse writeCardQueueDistribute(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		PlanRuleTreeExeReq req = new PlanRuleTreeExeReq();
		TacheFact fact = new TacheFact();
		fact.setOrder_id(busiCompRequest.getOrder_id());
		req.setPlan_id(EcsOrderConsts.PC_BATCH_WRITE_CARD_QUEUE_ZJ);
		req.setFact(fact);
		req.setDeleteLogs(true);
		PlanRuleTreeExeResp planResp = CommonDataFactory.getInstance().exePlanRuleTree(req);
		BusiCompResponse busiResp = CommonDataFactory.getInstance().getRuleTreeresult(planResp);
		if (!StringUtils.equals(busiResp.getError_code(), ConstsCore.ERROR_SUCC)) {
			CommonTools.addBusiError("-9999", busiResp.getError_msg(), "1");
		}
		this.setOrderVisable(busiCompRequest.getOrder_id());
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}

	/**
	 * 获取EMS物流单号
	 * 
	 * @throws ApiBusiException
	 */
	@ZteMethodAnnontion(name = "获取EMS物流单号", group_name = "订单预处理", order = "6", page_show = true, path = "zteCustomVisitTraceRule.getEmsLogisticsNumber")
	public BusiCompResponse getEmsLogisticsNumber(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		BusiDealResult dealResult = ordVisitTacheManager.getEmsLogisticsNumber(order_id);
		if (!dealResult.getError_code().equals(ConstsCore.ERROR_SUCC))
			CommonTools.addBusiError(dealResult.getError_code(), dealResult.getError_msg());
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;

	}

	/**
	 * 获取EMS自助服务平台物流单号
	 * 
	 * @throws ApiBusiException
	 */
	@ZteMethodAnnontion(name = "获取EMS自助服务平台物流单号", group_name = "订单预处理", order = "6", page_show = true, path = "ZteShipTraceRule.getLogisticsNumber")
	public BusiCompResponse getLogisticsNumber(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		BusiDealResult dealResult = ordVisitTacheManager.getLogisticsNumber(order_id);
		if (!dealResult.getError_code().equals(EcsOrderConsts.EMS_INF_SUCC_CODE))
			CommonTools.addBusiError(dealResult.getError_code(), dealResult.getError_msg());
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;

	}

	/**
	 * 物流信息同步到EMS
	 * 
	 * @throws ApiBusiException
	 */
	@ZteMethodAnnontion(name = "物流信息同步到EMS", group_name = "订单预处理", order = "6", page_show = true, path = "ZteShipTraceRule.syncLogisticsInfo")
	public BusiCompResponse syncLogisticsInfo(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		BusiDealResult dealResult = ordVisitTacheManager.syncLogisticsInfo(order_id);
		if (!dealResult.getError_code().equals(EcsOrderConsts.EMS_INF_SUCC_CODE))
			CommonTools.addBusiError(dealResult.getError_code(), dealResult.getError_msg());
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;

	}

//	/**
//	 * 用户资料校验
//	 * 
//	 * @throws ApiBusiException
//	 */
//	@ZteMethodAnnontion(name = "BSS用户资料校验", group_name = "订单预处理", order = "6", page_show = true, path = "ZteShipTraceRule.bssCustomerCheckInfo")
//	public BusiCompResponse bssCustomerCheckInfo(BusiCompRequest busiCompRequest) throws ApiBusiException {
//		BusiCompResponse resp = new BusiCompResponse();
//		String order_id = getOrderId(busiCompRequest);
//		BusiDealResult dealResult = ordVisitTacheManager.bssCustomerCheckInfo(order_id);
//		if (!dealResult.getError_code().equals(EcsOrderConsts.EMS_INF_SUCC_CODE)) {
//			CommonTools.addBusiError(dealResult.getError_code(), dealResult.getError_msg());
//		}
//		resp.setError_code(dealResult.getError_code());
//		resp.setError_msg(dealResult.getError_msg());
//		return resp;
//	}

	/**
	 * 开户预提交
	 * 
	 * @throws ApiBusiException
	 */
	@ZteMethodAnnontion(name = "BSS开户预提交", group_name = "订单预处理", order = "6", page_show = true, path = "ZteShipTraceRule.bssPreCommitInfo")
	public BusiCompResponse bssPreCommitInfo(PreCommitReq req) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();

		BusiDealResult dealResult = ordVisitTacheManager.bssPreCommit(req);
		if (!dealResult.getError_code().equals(EcsOrderConsts.EMS_INF_SUCC_CODE)) {
			CommonTools.addBusiError(resp.getError_code(), resp.getError_msg());
		} else {
			resp.setError_code("0");
			resp.setError_msg("执行成功");
		}
		return resp;

	}

	/**
	 * 开户预提交
	 * 
	 * @throws ApiBusiException
	 */
	@ZteMethodAnnontion(name = "总商外呼确认", group_name = "订单预处理", order = "26", page_show = true, path = "zteCustomVisitTraceRule.zbOutCallConfirm")
	public BusiCompResponse zbOutCallConfirm(BusiCompRequest busiCompRequest) throws ApiBusiException {
		String order_id = busiCompRequest.getOrder_id();
		BusiCompResponse resp = new BusiCompResponse();
		BusiDealResult dealResult = ordVisitTacheManager.zbOutCallConfirm(order_id);
		if (!StringUtils.equals(dealResult.getError_code(), ConstsCore.ERROR_SUCC)) {
			CommonTools.addBusiError("-99999", dealResult.getError_msg(), "1");
		}
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;

	}

	@ZteMethodAnnontion(name = "爬虫总部订单审核", group_name = "订单预处理", order = "20", page_show = true, path = "zteCustomVisitTraceRule.zbOrderVerify")
	public BusiCompResponse zbOrderVerify(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		// 1、调用爬虫审核能力
		ZbOrderVerifyReq request = new ZbOrderVerifyReq();
		String order_id = busiCompRequest.getOrder_id();
		String orderNum = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ORDER_NUM);
		String outTid = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.OUT_TID);
		request.setOrderId(orderNum);
		request.setOrderNo(outTid);
		int num = this.ordVisitTacheManager.queryZbOrderAuditNum(outTid);
		if (num <= 0) {
			this.ordVisitTacheManager.insertZbOrderAudit(outTid, orderNum, "0", "0", "0", "0");// 写订单审核表
		}
		String orderModel = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ORDER_MODEL);// 生产模式
		if (ZjEcsOrderConsts.ORDER_MODEL_08.equals(orderModel)) {// 爬虫自动生成模式对部分字段进行前置校验
			OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
			String ship_mobile = orderTree.getOrderBusiRequest().getShip_mobile();
			if (StringUtils.isBlank(ship_mobile) && ship_mobile.length() != 11) {
				CommonTools.addBusiError("-99999", "客户手机格式不正确", "1");
			}
		}
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		ZbOrderVerifyResp verifyResp = client.execute(request, ZbOrderVerifyResp.class);

		ZbAuditStatusUpdateReq req = new ZbAuditStatusUpdateReq();
		req.setOut_tid(outTid);
		req.setZb_id(orderNum);
		req.setAudit_type("auto");

		if (!ConstsCore.ERROR_SUCC.equals(verifyResp.getError_code())) {
			// 2、如果审核不通过，写异常单
			Map exception = verifyResp.getExceptionMap();
			String err = "";
			if (null != exception) {
				Iterator<String> it = exception.values().iterator();
				while (it.hasNext()) {
					err += it.next() + ";";
				}
			}
			req.setAudit_status("2");// 2 审核失败
			ZbAuditStatusUpdateResp updateResp = client.execute(req, ZbAuditStatusUpdateResp.class);
			CommonTools.addBusiError("-9999", err + (verifyResp.getError_msg() == null ? "" : verifyResp.getError_msg()), "1");// 异常写入异常单
		} else {
			// 3、审核通过，写入审核成功表
			req.setAudit_status("1");// 1 审核成功
		}
		ZbAuditStatusUpdateResp updateResp = client.execute(req, ZbAuditStatusUpdateResp.class);
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}

	@ZteMethodAnnontion(name = "爬虫订单分配", group_name = "订单预处理", order = "20", page_show = true, path = "zteCustomVisitTraceRule.zbOrderDistribution")
	public BusiCompResponse zbOrderDistribution(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		// 调用订单分配能力
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		ZbOrderDistributeReq request = new ZbOrderDistributeReq();
		String order_id = busiCompRequest.getOrder_id();
		String orderNum = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ORDER_NUM);
		String outTid = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.OUT_TID);
		request.setOrderId(orderNum);
		request.setOrderNo(outTid);
		ZbOrderDistributeResp distributeResp = client.execute(request, ZbOrderDistributeResp.class);
		int num = this.ordVisitTacheManager.queryZbOrderAuditNum(outTid);
		if (num <= 0) {
			// 老用户无订单审核，因此只能到订单分配环节插入该表
			// 写订单审核表,如果此时表里面无数据则说明是老用户，不能填未审核
			this.ordVisitTacheManager.insertZbOrderAudit(outTid, orderNum, "3", "1", "1", "0");
		}

		if (ConstsCore.ERROR_FAIL.equals(distributeResp.getError_code())) {
			logger.info("订单" + order_id + "分配失败，失败原因" + distributeResp.getError_code());
			Map map = new HashMap();
			map.put("distribute_status", "2");
			map.put("out_tid", outTid);
			this.ordVisitTacheManager.upZbOrderAuditStatus(map);
			CommonTools.addBusiError("-9999", distributeResp.getError_msg(), "1");// 异常写入异常单
		} else if (ConstsCore.ERROR_BUSI_MID.equals(distributeResp.getError_code())) {// 订单分配按钮为灰色,或者未登陆,或者在分配界面未查询到订单等待下次分配job
			CommonTools.addBusiError("-9999", distributeResp.getError_msg());// 不写异常单
		} else {// 分配成功
			Map map = new HashMap();
			map.put("distribute_status", "1");
			map.put("out_tid", outTid);
			this.ordVisitTacheManager.upZbOrderAuditStatus(map);
		}
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}

	@ZteMethodAnnontion(name = "爬虫订单审核修改配送信息", group_name = "订单预处理", order = "28", page_show = true, path = "zteCustomVisitTraceRule.modifyZbOrderPostInfo")
	public BusiCompResponse modifyZbOrderPostInfo(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		OrderTreeBusiRequest newOrderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		List<OrderDeliveryBusiRequest> deliveryList = newOrderTree.getOrderDeliveryBusiRequests();
		if (null != deliveryList) {
			OrderDeliveryBusiRequest delivery = deliveryList.get(0);

			CrawlerUpdatePostInfoReq postInfoReq = new CrawlerUpdatePostInfoReq();
			postInfoReq.setOrderNo(newOrderTree.getOrderExtBusiRequest().getOut_tid());
			postInfoReq.setReceiverName(delivery.getShip_name());
			postInfoReq.setProviceCode(delivery.getProvince_id() == null ? "" : delivery.getProvince_id().toString());
			postInfoReq.setCityCode(delivery.getCity_id() == null ? "" : delivery.getCity_id().toString());
			postInfoReq.setDistrictCode(delivery.getRegion_id() == null ? "" : delivery.getRegion_id().toString());
			postInfoReq.setPostAddr(delivery.getShip_addr());
			postInfoReq.setWishLgtsCode(delivery.getShipping_company().equals(EcsOrderConsts.LOGI_COMPANY_SFFYZQYF) ? "1002" : "1001");
			String deliveryType = delivery.getShipping_time();
			if (ZjEcsOrderConsts.SHIPPING_TIME_NOLIMIT.equals(deliveryType)) {
				postInfoReq.setDeliverDateType("01");// 配送类型 04：延时配送，01：
														// 不限时配送，02：只工作日送货，03只有双休日、节假日送货
			} else if (ZjEcsOrderConsts.SHIPPING_TIME_HOLIDAY.equals(deliveryType)) {
				postInfoReq.setDeliverDateType("03");
			} else if (ZjEcsOrderConsts.SHIPPING_TIME_WORKDAY.equals(deliveryType)) {
				postInfoReq.setDeliverDateType("02");
			}
			postInfoReq.setMobilePhone(delivery.getShip_tel());
			String shipType = newOrderTree.getOrderBusiRequest().getShipping_type();
			if (ZjEcsOrderConsts.SHIP_TYPE_KD.equals(shipType)) {
				postInfoReq.setDeliverTypeCode("01"); // 01:第三方物流 04：现场提货
														// 05：自行配送
				postInfoReq.setDispatchName("快递");// 配送方式名称
			} else if (ZjEcsOrderConsts.SHIP_TYPE_XCTH.equals(shipType)) {
				postInfoReq.setDeliverTypeCode("04");
			} else if (ZjEcsOrderConsts.SHIP_TYPE_ZYPS.equals(shipType)) {
				postInfoReq.setDeliverTypeCode("05");
			}

			ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
			ZteResponse zteResp = client.execute(postInfoReq, ZteResponse.class);
			if (!ConstsCore.ERROR_SUCC.equals(zteResp.getError_code())) {
				logger.info("修改总商配送信息出错【orderId：" + order_id + ",errorMsg:" + zteResp.getError_msg() + "】");
				CommonTools.addBusiError("-99999", zteResp.getError_msg(), "1");
			}
		}
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}

	@ZteMethodAnnontion(name = "爬虫订单审核修改商品信息", group_name = "订单预处理", order = "30", page_show = true, path = "zteCustomVisitTraceRule.modifyZbOrderGoodsInfo")
	public BusiCompResponse modifyZbOrderGoodsInfo(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		String orderId = busiCompRequest.getOrder_id();
		OrderTreeBusiRequest newOrderTree = CommonDataFactory.getInstance().getOrderTree(orderId);
		CrawlerUpdateGoodsInfoReq goodsInfoReq = new CrawlerUpdateGoodsInfoReq();
		goodsInfoReq.setOrderNo(newOrderTree.getOrderExtBusiRequest().getOut_tid());
		goodsInfoReq.setCustName(CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.PHONE_OWNER_NAME));
		goodsInfoReq.setCertAddr(CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.CERT_ADDRESS));
		goodsInfoReq.setPsptNo(CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.CERT_CARD_NUM));
		List<OrderItemProdBusiRequest> itemProdList = newOrderTree.getOrderItemBusiRequests().get(0).getOrderItemProdBusiRequests();
		if (null != itemProdList) {
			String certType = itemProdList.get(0).getOrderItemProdExtBusiRequest().getCerti_type();
			String firstMonthFree = itemProdList.get(0).getOrderItemProdExtBusiRequest().getFirst_payment();
			if (ZjEcsOrderConsts.CERTI_TYPE_SFZ15.equals(certType)) {
				goodsInfoReq.setPsptType("01");
			} else if (ZjEcsOrderConsts.CERTI_TYPE_SFZ18.equals(certType)) {
				goodsInfoReq.setPsptType("02");
			}

			if (ZjEcsOrderConsts.FIRST_FEE_TYPE_ALLM.equals(firstMonthFree)) {
				goodsInfoReq.setFirstMonthFee("A000011V000001");
				goodsInfoReq.setFirstMonthFeeName("全月");
			} else if (ZjEcsOrderConsts.FIRST_FEE_TYPE_HALF.equals(firstMonthFree)) {
				goodsInfoReq.setFirstMonthFee("A000011V000002");
				goodsInfoReq.setFirstMonthFeeName("半月");
			} else if (ZjEcsOrderConsts.FIRST_FEE_TYPE_OTHER.equals(firstMonthFree)) {
				goodsInfoReq.setFirstMonthFee("A000011V000003");
				goodsInfoReq.setFirstMonthFeeName("套餐包外");
			}
		}
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		ZteResponse goodsInfoResp = client.execute(goodsInfoReq, ZteResponse.class);
		if (!ConstsCore.ERROR_SUCC.equals(goodsInfoResp.getError_code())) {
			logger.info("修改总商商品信息出错【orderId：" + orderId + ",errorMsg:" + goodsInfoResp.getError_msg() + "】");
			CommonTools.addBusiError("-99999", goodsInfoResp.getError_msg(), "1");
		}
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}

	@ZteMethodAnnontion(name = "爬虫写入总部订单状态队列", group_name = "订单预处理", order = "31", page_show = true, path = "zteCustomVisitTraceRule.writeInZbOrderStatus")
	public BusiCompResponse writeInZbOrderStatus(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();

		String orderId = busiCompRequest.getOrder_id();

		String outTid = CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.OUT_TID);
		String zbId = CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.ORDER_NUM);
		String status = "1";
		String zbLastModifyTime = null;
		String createTime = "sysdate";
		String remark = null;
		String sourceFrom = null;
		ordVisitTacheManager.insertZbOrderStatus(orderId, outTid, zbId, status, createTime, zbLastModifyTime, remark, sourceFrom);
		return resp;
	}
	
	@ZteMethodAnnontion(name = "工单状态修改", group_name = "工单状态修改", order = "31", page_show = true, path = "zteCustomVisitTraceRule.upWorkStatus")
	public BusiCompResponse upWorkStatus(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = getOrderId(busiCompRequest);
		String result = ordWorkManager.upWorkStatus(order_id);
		if (!"".equals(result)) {
			CommonTools.addBusiError("-999", result);
		}
		resp.setError_code("0");
		resp.setError_msg("处理成功");
		return resp;
	}

}