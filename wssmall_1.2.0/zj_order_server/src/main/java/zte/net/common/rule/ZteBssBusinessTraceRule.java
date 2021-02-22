package zte.net.common.rule;

import javax.annotation.Resource;

import util.Utils;
import zte.net.common.annontion.context.action.ZteMethodAnnontion;
import zte.net.common.annontion.context.action.ZteServiceAnnontion;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.base.req.BusiCompRequest;
import zte.net.ecsord.params.base.resp.BusiCompResponse;
import zte.net.ecsord.params.busi.req.OrderBusiRequest;
import zte.net.ecsord.rule.tache.TacheFact;
import zte.net.params.req.PlanRuleTreeExeReq;
import zte.net.params.resp.PlanRuleTreeExeResp;

import com.ztesoft.api.ApiBusiException;
import com.ztesoft.common.util.StringUtils;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.consts.ZjEcsOrderConsts;
import com.ztesoft.net.model.BusiDealResult;
import com.ztesoft.net.service.IOrdBSSTacheManager;

import commons.CommonTools;
import consts.ConstsCore;

//import org.apache.log4j.Logger;

/**
 *
 * BSS业务办理规则处理
 * 
 * @author wu.i
 */
@ZteServiceAnnontion(trace_name = "ZteBssBusinessTraceRule", trace_id = "1", version = "1.0", desc = "BSS业务办理环节业务通知组件")
public class ZteBssBusinessTraceRule extends ZteTraceBaseRule {

	@Resource
	private IOrdBSSTacheManager ordBSSTacheManager;

	/**
	 * BSS业务办理环节入口
	 */
	@Override
	@ZteMethodAnnontion(name = "BSS业务办理环节入口", group_name = "BSS业务办理", order = "1", page_show = true, path = "bssBusinessTraceRule.exec")
	public BusiCompResponse engineDo(BusiCompRequest busiCompRequest) {
		String orderId = busiCompRequest.getOrder_id();
		OrderBusiRequest orderBusiRequest = CommonDataFactory.getInstance().getOrderTree(orderId).getOrderBusiRequest();
		orderBusiRequest.setStatus(ZjEcsOrderConsts.DIC_ORDER_STATUS_55);
		orderBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
		orderBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
		orderBusiRequest.store();

		BusiCompResponse resp = new BusiCompResponse();
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}

	@ZteMethodAnnontion(name = "业务办理并行环节入口", group_name = "BSS业务办理", order = "1", page_show = true, path = "bssBusinessTraceRule.bssParallelGo")
	public BusiCompResponse bssParallelGo(BusiCompRequest busiCompRequest) {
		BusiCompResponse resp = new BusiCompResponse();
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}

	@ZteMethodAnnontion(name = "业务办理并行处理", group_name = "BSS业务办理", order = "1", page_show = true, path = "bssBusinessTraceRule.bssParallelPerform")
	public BusiCompResponse bssParallelPerform(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		PlanRuleTreeExeReq req = new PlanRuleTreeExeReq();
		TacheFact fact = new TacheFact();
		fact.setOrder_id(busiCompRequest.getOrder_id());
		req.setPlan_id(EcsOrderConsts.BUSI_HANDLE_PLAN_ID);
		req.setFact(fact);
		req.setDeleteLogs(false);// 不删除日志，不允许二次操作
		req.setAuto_exe(0);
		PlanRuleTreeExeResp planResp = CommonDataFactory.getInstance().exePlanRuleTree(req);
		BusiCompResponse busiResp = CommonDataFactory.getInstance().getRuleTreeresult(planResp);
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}

	/**
	 * BSS社会机TAC码、商品折扣包录入
	 * 
	 * @param busiCompRequest
	 * @return
	 * @throws ApiBusiException
	 */
	@ZteMethodAnnontion(name = "BSS社会机TAC码、商品折扣包录入", group_name = "BSS业务办理", order = "1", page_show = true, path = "ZteBssBusinessTraceRule.actionRecv")
	public BusiCompResponse actionRecv(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		BusiDealResult dealResult = ordBSSTacheManager.actionRecv(order_id);
		if (!dealResult.getError_code().equals(EcsOrderConsts.BUSI_DEAL_RESULT_0000)) {
			CommonTools.addBusiError(dealResult.getError_code(), dealResult.getError_msg());
		}
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}

	/**
	 * BSS办理状态设置（未办理）
	 * 
	 * @throws ApiBusiException
	 */
	@ZteMethodAnnontion(name = "BSS办理状态设置未办理(BSS业务办理)", group_name = "BSS业务办理", order = "1", page_show = true, path = "ZteBssBusinessTraceRule.setStatusNoFinish")
	public BusiCompResponse setStatusNoFinish(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		int giftNum = CommonDataFactory.getInstance().getOrderTree(order_id).getGiftInfoBusiRequests().size();
		String is_old = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.IS_OLD);
		String prod_cat_type = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.PROD_CAT_ID);
		BusiDealResult dealResult;
		if (EcsOrderConsts.IS_OLD_1.equals(is_old) && (EcsOrderConsts.GOODS_CAT_FEE_GIVE_MACHINE.equals(prod_cat_type)
				|| EcsOrderConsts.GOODS_CAT_FEE_GIVE_FEE.equals(prod_cat_type))) {
			dealResult = ordBSSTacheManager.setStatusNoFinish(order_id);
		} else {
			// if(giftNum==0){ //add zhangJun 去掉
			// dealResult = ordBSSTacheManager.setStatusFinish(order_id);
			// }else{
			dealResult = ordBSSTacheManager.setStatusNoFinish(order_id);
			// }
		}
		if (!dealResult.getError_code().equals(EcsOrderConsts.BUSI_DEAL_RESULT_0000)) {
			CommonTools.addBusiError(dealResult.getError_code(), dealResult.getError_msg());
		}
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;

	}

	/**
	 * BSS办理状态设置（已办理）
	 * 
	 * @throws ApiBusiException
	 */
	@ZteMethodAnnontion(name = "BSS办理状态设置已办理(BSS业务办理)", group_name = "BSS业务办理", order = "2", page_show = true, path = "ZteBssBusinessTraceRule.setStatusFinish")
	public BusiCompResponse setStatusFinish(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		ordBSSTacheManager.setStatusFinish(order_id);
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;

	}

	/**
	 * 代理商资金扣减
	 * 
	 * @throws ApiBusiException
	 */
	@ZteMethodAnnontion(name = "代理商资金扣减(BSS业务办理)", group_name = "BSS业务办理", order = "3", page_show = true, path = "ZteBssBusinessTraceRule.agentDeduct")
	public BusiCompResponse agentDeduct(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();

		BusiDealResult dealResult = ordBSSTacheManager.agentDeduct(order_id);
		if (!dealResult.getError_code().equals(EcsOrderConsts.BSS_DEAL_RESULT_0000))
			CommonTools.addBusiError(dealResult.getError_code(), dealResult.getError_msg());

		resp.setError_code("0");
		resp.setError_msg("执行成功");
		resp.setResponse(dealResult.getResponse());
		return resp;

	}

	@ZteMethodAnnontion(name = "代理商资金返销(BSS业务办理)", group_name = "BSS业务办理", order = "4", page_show = true, path = "ZteBssBusinessTraceRule.agentRefund")
	public BusiCompResponse agentRefund(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		// 返销失败，直接走对账
		try {
			ordBSSTacheManager.agentRefund(order_id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;

	}

	@ZteMethodAnnontion(name = "通知新商城写卡完成(BSS业务办理)", group_name = "BSS业务办理", order = "5", page_show = true, path = "ZteBssBusinessTraceRule.notifyNewShopWriteCard")
	public BusiCompResponse notifyNewShopWriteCard(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		// 通知新商城写卡完成，接口 不成功不影响主流程，直接返回成功
		try {
			// 获取写卡数据，将此数据通知给新商城
			// 4G场景：收到写卡通知总部结果，不同3G要走BSS业务办理，模拟BSS办理成功。
			/*
			 * StatuSynchReq statuSyn = new
			 * StatuSynchReq(order_id,EcsOrderConsts.DIC_ORDER_NODE_Y,
			 * EcsOrderConsts.DIC_ORDER_NODE_Y_DESC,ZjEcsOrderConsts.
			 * BSS_STATUS_2,EcsOrderConsts.BSS_STATUS_1_DESC,"");
			 * CommonDataFactory.getInstance().notifyNewShop(statuSyn);
			 */
		} catch (Exception e) {
			e.printStackTrace();
		}
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;

	}

	/**
	 * 下一步（BSS业务办理）
	 */
	@ZteMethodAnnontion(name = "下一步（BSS业务办理）", group_name = "BSS业务办理", order = "2.1", page_show = true, path = "ZteBssBusinessTraceRule.nextStep")
	public BusiCompResponse nextStep(BusiCompRequest busiCompRequest) {
		BusiCompResponse resp = this.nextflow(busiCompRequest, false, "Y");
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}

	@ZteMethodAnnontion(name = "[AOP]3G老用户存费送费", group_name = "业务办理", order = "6", page_show = true, path = "ZteBssBusinessTraceRule.cunFeeSongFeeGDAop")
	public BusiCompResponse cunFeeSongFeeGDAop(BusiCompRequest busiCompRequest) throws ApiBusiException {
		this.init();
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		BusiDealResult dealResult = ordBSSTacheManager.cunFeeSongFeeGDAop(order_id);
		if (!StringUtils.equals(dealResult.getError_code(), EcsOrderConsts.AOP_SUCCESS_0000)) {
			CommonTools.addBusiError(dealResult.getError_code(), dealResult.getError_msg());
		}
		resp.setResponse(dealResult.getResponse());
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}

	/**
	 * [AOP]换机提交
	 * 
	 * @param busiCompRequest
	 * @return
	 * @throws ApiBusiException
	 */
	@ZteMethodAnnontion(name = "[AOP]换机提交", group_name = "业务办理", order = "7", page_show = true, path = "ZteBssBusinessTraceRule.changeMachineSubmit")
	public BusiCompResponse changeMachineSubmit(BusiCompRequest busiCompRequest) throws ApiBusiException {
		this.init();
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		BusiDealResult rsp = ordBSSTacheManager.changeMachineSubmit(order_id);
		if (!StringUtils.equals(rsp.getError_code(), EcsOrderConsts.AOP_SUCCESS_0000)) {
			CommonTools.addBusiError(rsp.getError_code(), rsp.getError_msg());
		}
		resp.setError_code("0");
		resp.setError_msg("换机提交执行成功");
		resp.setResponse(rsp.getResponse());
		return resp;
	}

	/**
	 * 流量包订购
	 * 
	 * @param busiCompRequest
	 * @return
	 * @throws ApiBusiException
	 */
	@ZteMethodAnnontion(name = "[AOP]流量包订购", group_name = "流量包订购", order = "8", page_show = true, path = "ZteBssBusinessTraceRule.trafficOrder")
	public BusiCompResponse trafficOrder(BusiCompRequest busiCompRequest) throws ApiBusiException {
		this.init();
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		BusiDealResult rsp = ordBSSTacheManager.trafficOrder(order_id);
		if (!StringUtils.equals(rsp.getError_code(), EcsOrderConsts.AOP_SUCCESS_0000)) {
			CommonTools.addBusiError(rsp.getError_code(), rsp.getError_msg());
		}
		resp.setError_code("0");
		resp.setError_msg("流量包订购执行成功");
		resp.setResponse(rsp.getResponse());
		return resp;
	}

	/**
	 * 流量包订购/退订 日包/夜猫包/月包: ecaop.trades.sell.mob.comm.traffic.sub
	 * 
	 * @param busiCompRequest
	 * @return
	 * @throws ApiBusiException
	 */
	@ZteMethodAnnontion(name = "[AOP]日包/夜猫包/月包订购", group_name = "日包/夜猫包/月包订购", order = "8", page_show = true, path = "ZteBssBusinessTraceRule.flowPacket")
	public BusiCompResponse flowPacket(BusiCompRequest busiCompRequest) throws ApiBusiException {
		this.init();
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		BusiDealResult rsp = ordBSSTacheManager.flowPacket(order_id);
		if (!StringUtils.equals(rsp.getError_code(), EcsOrderConsts.AOP_SUCCESS_0000)) {
			Utils.markException(order_id, false,rsp.getError_code(),rsp.getError_msg());
			CommonTools.addBusiError(rsp.getError_code(), rsp.getError_msg());
		}
		
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		resp.setResponse(rsp.getResponse());
		return resp;
	}

	/**
	 * 视频包： ecaop.trades.sell.mob.sp.order.app
	 * 
	 * @param busiCompRequest
	 * @return
	 * @throws ApiBusiException
	 */
	@ZteMethodAnnontion(name = "[AOP]视频包订购", group_name = "视频包订购", order = "8", page_show = true, path = "ZteBssBusinessTraceRule.spPacket")
	public BusiCompResponse spPacket(BusiCompRequest busiCompRequest) throws ApiBusiException {
		this.init();
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		BusiDealResult rsp = ordBSSTacheManager.spPacket(order_id);
		if (!StringUtils.equals(rsp.getError_code(), EcsOrderConsts.AOP_SUCCESS_0000)) {
			Utils.markException(order_id, false,rsp.getError_code(),rsp.getError_msg());
			CommonTools.addBusiError(rsp.getError_code(), rsp.getError_msg());
		}
		
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		resp.setResponse(rsp.getResponse());
		return resp;
	}

	/**
	 * 订单系统访问森锐仿真系统
	 * 
	 * @param busiCompRequest
	 * @return
	 * @throws ApiBusiException
	 */
	@ZteMethodAnnontion(name = "仿真派单", group_name = "业务办理", order = "9", page_show = true, path = "ZteBssBusinessTraceRule.simulation")
	public BusiCompResponse simulation(BusiCompRequest busiCompRequest) throws ApiBusiException {
		this.init();
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		BusiDealResult rsp = ordBSSTacheManager.simulation(order_id);
		if (!StringUtils.equals(rsp.getError_code(), EcsOrderConsts.AOP_SUCCESS_0000)) {
			CommonTools.addBusiError(rsp.getError_code(), rsp.getError_msg());
		}
		resp.setError_code("0");
		resp.setError_msg("向森锐仿真派单成功");
		resp.setResponse(rsp.getResponse());
		return resp;
	}

	/**
	 * 裸机销售
	 * 
	 * @param busiCompRequest
	 * @return
	 */
	@ZteMethodAnnontion(name = "[AOP]裸机销售", group_name = "业务办理", order = "15", page_show = true, path = "ZteBssBusinessTraceRule.bareMachineSale")
	public BusiCompResponse bareMachineSale(BusiCompRequest busiCompRequest) throws ApiBusiException {
		this.init();
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		BusiDealResult rsp = ordBSSTacheManager.bareMachineSale(order_id);
		if (!StringUtils.equals(rsp.getError_code(), EcsOrderConsts.AOP_SUCCESS_0000)) {
			CommonTools.addBusiError(rsp.getError_code(), rsp.getError_msg());
		}
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		resp.setResponse(rsp.getResponse());
		return resp;
	}

	/**
	 * 社会渠道购机券换手机BSS端支持(转兑)
	 * 
	 * @param busiCompRequest
	 * @return
	 */
	@ZteMethodAnnontion(name = "社会渠道购机券换手机BSS端支持(转兑)bssBusiness", group_name = "订单预处理", order = "16", page_show = true, path = "ZteBssBusinessTraceRule.purchaseCouponsExchange")
	public BusiCompResponse purchaseCouponsExchange(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		PlanRuleTreeExeReq req = new PlanRuleTreeExeReq();
		TacheFact fact = new TacheFact();
		fact.setOrder_id(busiCompRequest.getOrder_id());
		req.setPlan_id(EcsOrderConsts.PURCHASE_COUPONS_EXCHANGE);
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

	/**
	 * SP 服务办理业务组件
	 * 
	 * @param busiCompRequest
	 * @return
	 * @throws ApiBusiException
	 */
	@ZteMethodAnnontion(name = "SP 服务办理业务组件", group_name = "业务办理", order = "17", page_show = true, path = "ZteBssBusinessTraceRule.handleSpServies")
	public BusiCompResponse handleSpServies(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		// 获取内部订单号
		String orderId = busiCompRequest.getOrder_id();
		// SP服务办理
		BusiDealResult rsp = this.ordBSSTacheManager.handleSpServices(orderId);
		// 失败时不卡流程
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}

	/**
	 * 业务办理公用组件
	 * 
	 * @param busiCompRequest
	 * @return
	 * @throws ApiBusiException
	 */
	@ZteMethodAnnontion(name = "业务办理公用组件", group_name = "业务办理", order = "18", page_show = true, path = "ZteBssBusinessTraceRule.businessCommonHandler")
	public BusiCompResponse businessCommonHandler(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		PlanRuleTreeExeReq req = new PlanRuleTreeExeReq();
		TacheFact fact = new TacheFact();
		fact.setOrder_id(busiCompRequest.getOrder_id());
		req.setPlan_id(EcsOrderConsts.OLD_USER_BUSINESS);
		req.setFact(fact);
		req.setDeleteLogs(false);// 不删除日志，不允许二次操作
		PlanRuleTreeExeResp planResp = CommonDataFactory.getInstance().exePlanRuleTree(req);
		BusiCompResponse busiResp = CommonDataFactory.getInstance().getRuleTreeresult(planResp);
		if (!StringUtils.equals(busiResp.getError_code(), ConstsCore.ERROR_SUCC)) {// 失败时卡流程
			CommonTools.addBusiError("-99999", busiResp.getError_msg());
		}
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}

	/**
	 * 业务办理环节状态通知商城
	 * 
	 * @param busiCompRequest
	 * @return
	 * @throws ApiBusiException
	 */
	@ZteMethodAnnontion(name = "业务办理环节状态通知商城(现场交付)组件", group_name = "业务办理", order = "19", page_show = true, path = "ZteBssBusinessTraceRule.businessStatusSync")
	public BusiCompResponse businessStatusSync(BusiCompRequest busiCompRequest) throws ApiBusiException {
		this.init();
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		BusiDealResult rsp = ordBSSTacheManager.businessStatusSync(order_id);
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		resp.setResponse(rsp.getResponse());
		return resp;
	}

	/**
	 * 附加产品订购
	 * 
	 * @param busiCompRequest
	 * @return
	 * @throws ApiBusiException
	 */
	@ZteMethodAnnontion(name = "[广东AOP]附加产品订购", group_name = "业务办理", order = "20", page_show = true, path = "ZteBssBusinessTraceRule.subProOrder")
	public BusiCompResponse subProOrder(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		// 获取内部订单号
		String order_id = busiCompRequest.getOrder_id();
		// 附加产品订购
		BusiDealResult dealResult = this.ordBSSTacheManager.subProOrder(order_id);
		if (!dealResult.getError_code().equals(EcsOrderConsts.BUSI_DEAL_RESULT_0000)) {// 失败时卡流程
			CommonTools.addBusiError(dealResult.getError_code(), dealResult.getError_msg());
		}
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}

	/**
	 * SP 服务办理业务组件
	 * 
	 * @param busiCompRequest
	 * @return
	 * @throws ApiBusiException
	 */
	@ZteMethodAnnontion(name = "SP 服务办理(增值业务订单),卡流程", group_name = "业务办理", order = "21", page_show = true, path = "ZteBssBusinessTraceRule.handleSpServiesFor")
	public BusiCompResponse handleSpServiesFor(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		// 获取内部订单号
		String orderId = busiCompRequest.getOrder_id();
		// SP服务办理
		BusiDealResult dealResult = this.ordBSSTacheManager.handleSpServices(orderId);
		if (!dealResult.getError_code().equals(EcsOrderConsts.BUSI_DEAL_RESULT_0000)) {// 失败时卡流程
			Utils.markException(orderId, false);// 标记异常
			CommonTools.addBusiError(dealResult.getError_code(), dealResult.getError_msg());// 卡流程
		}
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}

	/**
	 * BSS办理状态设置（已办理已竣工）
	 * 
	 * @throws ApiBusiException
	 */
	@ZteMethodAnnontion(name = "BSS办理状态设置已办理已竣工(BSS业务办理)", group_name = "BSS业务办理", order = "22", page_show = true, path = "ZteBssBusinessTraceRule.setStatusFinishComplete")
	public BusiCompResponse setStatusFinishComplete(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		ordBSSTacheManager.setStatusFinishComplete(order_id);
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;

	}

	/**
	 * BSS办理状态设置（已办理未竣工）
	 * 
	 * @throws ApiBusiException
	 */
	@ZteMethodAnnontion(name = "BSS办理状态设置已办理未竣工(BSS业务办理)", group_name = "BSS业务办理", order = "22", page_show = true, path = "ZteBssBusinessTraceRule.setStatusFinishNotComplete")
	public BusiCompResponse setStatusFinishNotComplete(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		ordBSSTacheManager.setStatusFinishNotComplete(order_id);
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;

	}

	/**
	 * 续约活动校验和受理组件
	 */
	@ZteMethodAnnontion(name = "BSS续约活动校验和受理", group_name = "BSS业务办理", order = "23", page_show = true, path = "ZteBssBusinessTraceRule.businessAcceptenceAndVerification")
	public BusiCompResponse businessAcceptenceAndVerification(BusiCompRequest busiCompRequest) throws ApiBusiException {

		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		BusiDealResult dealResult = this.ordBSSTacheManager.businessAcceptenceAndVerification(order_id);

		if (!dealResult.getError_code().equals(EcsOrderConsts.BUSI_DEAL_RESULT_0000)) {
			CommonTools.addBusiError(dealResult.getError_code(), dealResult.getError_msg());
		}
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}

	@ZteMethodAnnontion(name = "BSS老用户业务办理", group_name = "业务办理", order = "24", page_show = true, path = "ZteBssBusinessTraceRule.businessHandlerBss")
	public BusiCompResponse businessHandlerBss(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		PlanRuleTreeExeReq req = new PlanRuleTreeExeReq();
		TacheFact fact = new TacheFact();
		fact.setOrder_id(busiCompRequest.getOrder_id());
		req.setPlan_id(ZjEcsOrderConsts.ZJ_BSS_OLD_USER_BUSINESS);
		req.setFact(fact);
		req.setDeleteLogs(false);// 不删除日志，不允许二次操作
		PlanRuleTreeExeResp planResp = CommonDataFactory.getInstance().exePlanRuleTree(req);
		BusiCompResponse busiResp = CommonDataFactory.getInstance().getRuleTreeresult(planResp);
		if (!StringUtils.equals(busiResp.getError_code(), ConstsCore.ERROR_SUCC)) {// 失败时卡流程
			CommonTools.addBusiError("-99999", busiResp.getError_msg());
		}
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}
	
	@ZteMethodAnnontion(name = "用户缴费接口（支持4G用户）", group_name = "业务办理", order = "25", page_show = true, path = "ZteBssBusinessTraceRule.paymentByBSS")
	public BusiCompResponse paymentByBSS(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		BusiDealResult dealResult = this.ordBSSTacheManager.paymentByBSS(order_id);
		if (!StringUtils.equals(dealResult.getError_code(), ConstsCore.ERROR_SUCC)) {// 失败时卡流程
			CommonTools.addBusiError(dealResult.getError_code(), dealResult.getError_msg());
		}
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}
}
