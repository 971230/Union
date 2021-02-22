package com.ztesoft.orderstd.rule;

import java.util.Map;

import params.RuleParams;
import params.ZteResponse;
import params.order.req.DeliveryReq;
import params.order.req.PaymentLogReq;
import params.order.resp.DeliveryResp;
import params.order.resp.PaymentLogResp;
import rule.impl.AbsOrderFinishRule;
import rule.impl.AbsOrderPackageRule;
import rule.impl.AbsOrderPayRule;
import rule.impl.AbsOrderShippingRule;
import rule.params.accept.req.AcceptRuleReq;
import rule.params.accept.resp.AcceptRuleResp;
import rule.params.confirm.req.ConfirmRuleReq;
import rule.params.confirm.resp.ConfirmRuleResp;
import rule.params.coqueue.req.CoQueueRuleReq;
import rule.params.coqueue.resp.CoQueueRuleResp;
import rule.params.order.req.OrderFinishRuleReq;
import rule.params.order.req.OrderPackageRuleReq;
import rule.params.order.req.OrderPayRuleReq;
import rule.params.order.req.OrderShippingRuleReq;
import rule.params.order.resp.OrderFinishRuleResp;
import rule.params.order.resp.OrderPackageRuleResp;
import rule.params.order.resp.OrderPayRuleResp;
import rule.params.order.resp.OrderShippingRuleResp;
import rule.params.pay.req.PayRuleReq;
import rule.params.pay.resp.PayRuleResp;
import rule.params.userstaff.req.OrdUserRuleReq;
import rule.params.userstaff.req.OrderOwnerUserReq;
import rule.params.userstaff.resp.OrdUserRuleResp;
import rule.params.userstaff.resp.OrderOwnerUserResp;
import utils.CoreThreadLocalHolder;
import zte.net.iservice.IEcsServices;
import zte.params.req.MidDataProcessReq;
import zte.params.resp.MidDataProcessResp;

import com.ztesoft.api.ApiBusiException;
import com.ztesoft.api.ZteClient;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

import consts.ConstsCore;

@SuppressWarnings("unchecked")
public class StdRuleInvoker {


	public final static String VALIDATEACCEPTINST ="validateAcceptInst"; //受理验证
	
	public final static String AFSAVEACCEPTINST ="afSaveAcceptInst"; //受理单信息采集
	
	public final static String COMPUTECONFIRMSTATUS ="computeConfirmStatus"; //计算订单确认状态，判断是否免确认
	
	public final static String COMPUTEPAYSTATUS ="computePayStatus"; //计算订单支付状态，判断是否免确认
	
	public final static String SETORDERUSERS ="setOrderUsers"; //订单工号信息设置规则
	
	public final static String COQUEUE ="coQueue"; //消息队列规则处理
	
	public final static String IMPORT = "import";
	
	public final static String AFSAVEDRLIVERY = "afSaveDeliverInst";
	
	public final static String AFSAVEPAYMENT = "afSavePaymentInst";
	
	//==============================================================受理验证规则开始=================================================================
	//受理验证规则
	
	@SuppressWarnings("static-access")
	public static AcceptRuleResp validateAcceptInst(Map pMap) throws ApiBusiException {
		AcceptRuleReq acceptRuleReq = new AcceptRuleReq();
		acceptRuleReq.setParams(pMap);
		//规则通用入参
		RuleParams ruleParams = new RuleParams();
		ruleParams.setGoods_id((String)pMap.get("goods_id"));
		ruleParams.setService_code((String) pMap.get("service_code"));
		ruleParams.setAppKey((String)pMap.get("app_key")); //解决dubbo调用app_key为空的问题
		ruleParams.setSourceFrom((String)pMap.get("source_from"));
		ruleParams.setZteRequest(acceptRuleReq);
		ruleParams.setUserSessionId(pMap.get("userSessionId")+"");
		ruleParams.setRule_type(ConstsCore.RULE_TYPE_ACCEPT);
		ruleParams.setMethod_name(VALIDATEACCEPTINST);
		ruleParams.setUserSessionId((String)pMap.get("userSessionId"));
		//调用附加被动规则代码
		//触发规则
		CoreThreadLocalHolder.getInstance().getZteCommonData().setZteRequest(ruleParams);
		RuleFactory.getInstance();
		AcceptRuleResp  acceptRuleResp = (AcceptRuleResp) RuleFactory.perform(ruleParams);
		CoreThreadLocalHolder.getInstance().getZteCommonData().setZteRequest(ruleParams);
		return acceptRuleResp;
		
	}
	
	//订单
	private static int afSaveAcceptInst_index =0;
	@SuppressWarnings("static-access")
	public static AcceptRuleResp afSaveAcceptInst(AcceptRuleReq acceptRuleReq) throws ApiBusiException {
		
//		afSaveAcceptInst_index++;
//		logger.info(afSaveAcceptInst_index+"========================================>afSaveAcceptInst_index");
		//规则通用入参
		RuleParams ruleParams = new RuleParams();
		ruleParams.setGoods_id(acceptRuleReq.getOrderItem().getGoods_id());
		ruleParams.setZteRequest(acceptRuleReq);
		ruleParams.setService_code((String)acceptRuleReq.getParams().get("service_code"));
		ruleParams.setAppKey((String)acceptRuleReq.getParams().get("app_key"));
		ruleParams.setSourceFrom((String)acceptRuleReq.getParams().get("source_from"));
		ruleParams.setUserSessionId(acceptRuleReq.getUserSessionId());
		ruleParams.setRule_type(ConstsCore.RULE_TYPE_ACCEPT);
		ruleParams.setMethod_name(AFSAVEACCEPTINST);
		//触发规则
		CoreThreadLocalHolder.getInstance().getZteCommonData().setZteRequest(ruleParams);
		RuleFactory.getInstance();
		AcceptRuleResp  acceptRuleResp = (AcceptRuleResp) RuleFactory.perform(ruleParams);
		CoreThreadLocalHolder.getInstance().getZteCommonData().setZteRequest(ruleParams);
		return acceptRuleResp;
		
	}
	//==============================================================受理验证规则结束=================================================================
	
	
	//==============================================================订单确认验证规则开始=============================================================
	//订单确认状态计算，判断是否需要订单确认
	@SuppressWarnings("static-access")
	public static ConfirmRuleResp computeConfirmStatus(ConfirmRuleReq confirmRuleReq) throws ApiBusiException {
		
		//规则通用入参
		RuleParams ruleParams = new RuleParams();
		ruleParams.setGoods_id(confirmRuleReq.getGoods_id());
		ruleParams.setZteRequest(confirmRuleReq);
		ruleParams.setAppKey((String)confirmRuleReq.getParams().get("app_key"));
		ruleParams.setSourceFrom((String)confirmRuleReq.getParams().get("source_from"));
		ruleParams.setService_code((String)confirmRuleReq.getParams().get("service_code"));
		ruleParams.setUserSessionId(confirmRuleReq.getUserSessionId());
		ruleParams.setRule_type(ConstsCore.RULE_TYPE_CONFIRM);
		ruleParams.setMethod_name(COMPUTECONFIRMSTATUS);
		//触发规则
		
		CoreThreadLocalHolder.getInstance().getZteCommonData().setZteRequest(ruleParams);
		RuleFactory.getInstance();
		ConfirmRuleResp confirmRuleResp =  (ConfirmRuleResp) RuleFactory.perform(ruleParams);
		CoreThreadLocalHolder.getInstance().getZteCommonData().setZteRequest(ruleParams);
		return confirmRuleResp;
		
		
		
	}
	//==============================================================订单确认验证规则结束=============================================================
	
	
	
	//==============================================================订单支付验证规则开始=============================================================
	//订单支付状态计算，判断是否需要订单支付
	@SuppressWarnings("static-access")
	public static PayRuleResp computePayStatus(PayRuleReq payRuleReq) throws ApiBusiException {
		
		//规则通用入参
		RuleParams ruleParams = new RuleParams();
		ruleParams.setGoods_id(payRuleReq.getGoods_id());
		ruleParams.setZteRequest(payRuleReq);
		ruleParams.setAppKey(payRuleReq.getOrder().getApp_key());
		ruleParams.setSourceFrom(payRuleReq.getOrder().getSource_from());
		ruleParams.setService_code(payRuleReq.getOrder().getService_code());
		ruleParams.setRule_type(ConstsCore.RULE_TYPE_PAY);
		ruleParams.setUserSessionId(payRuleReq.getUserSessionId());
		ruleParams.setMethod_name(COMPUTEPAYSTATUS);
		//触发规则
		CoreThreadLocalHolder.getInstance().getZteCommonData().setZteRequest(ruleParams);
		RuleFactory.getInstance();
		PayRuleResp payRuleResp =  (PayRuleResp) RuleFactory.perform(ruleParams);
		CoreThreadLocalHolder.getInstance().getZteCommonData().setZteRequest(ruleParams);
		return payRuleResp;
		
	}
	
	@SuppressWarnings("static-access")
	public static PaymentLogResp afSavePaymentInst(PaymentLogReq paymentLogReq) throws ApiBusiException {
		//规则通用入参
		RuleParams ruleParams = new RuleParams();
		ruleParams.setGoods_id(paymentLogReq.getOrder_id());
		ruleParams.setZteRequest(paymentLogReq);
		ruleParams.setAppKey(paymentLogReq.getOrder().getApp_key());
		ruleParams.setSourceFrom(paymentLogReq.getOrder().getSource_from());
		ruleParams.setService_code(paymentLogReq.getOrder().getService_code());
		ruleParams.setRule_type(ConstsCore.RULE_TYPE_PAY);
		ruleParams.setUserSessionId(paymentLogReq.getUserSessionId());
		ruleParams.setMethod_name(AFSAVEPAYMENT);
		
		//触发规则
		CoreThreadLocalHolder.getInstance().getZteCommonData().setZteRequest(ruleParams);
		RuleFactory.getInstance();
		PaymentLogResp paymentLogResp =  (PaymentLogResp) RuleFactory.perform(ruleParams);
		CoreThreadLocalHolder.getInstance().getZteCommonData().setZteRequest(ruleParams);
		return paymentLogResp;
		//触发规则
	}
	
	
	//========================================================物流规则开始=========================================================================
	@SuppressWarnings("static-access")
	public static DeliveryResp afSaveDeliverInst(DeliveryReq deliveryReq) throws ApiBusiException {
		//规则通用入参
		RuleParams ruleParams = new RuleParams();
		ruleParams.setGoods_id(deliveryReq.getOrder_id());
		ruleParams.setZteRequest(deliveryReq);
		ruleParams.setSourceFrom(deliveryReq.getOrder().getSource_from());
		ruleParams.setService_code(deliveryReq.getOrder().getService_code());
		ruleParams.setRule_type(ConstsCore.RULE_TYPE_DELVERY);
		ruleParams.setUserSessionId(deliveryReq.getUserSessionId());
		ruleParams.setMethod_name(AFSAVEDRLIVERY);
		//触发规则
		
		//触发规则
		CoreThreadLocalHolder.getInstance().getZteCommonData().setZteRequest(ruleParams);
		RuleFactory.getInstance();
		DeliveryResp deliveryResp =  (DeliveryResp) RuleFactory.perform(ruleParams);
		CoreThreadLocalHolder.getInstance().getZteCommonData().setZteRequest(ruleParams);
		return deliveryResp;
		//触发规则
				
	}
	//========================================================物流规则结束=========================================================================
	
	
	
	
	//========================================================设置订单用户信息开始==================================================================
	@SuppressWarnings("static-access")
	//订单工号信息设置基础规则
	public static OrdUserRuleResp setOrderUsers(OrdUserRuleReq ordUserRuleReq) throws ApiBusiException {
		
		//规则通用入参,缺省取第一个商品的规则，
		//TODO 后期考虑生成发货单、受理单、支付单后，循环商品自定单项设置基础工号的规则
		RuleParams ruleParams = new RuleParams();
		ruleParams.setGoods_id(ordUserRuleReq.getOrderItem().getGoods_id());
		ruleParams.setZteRequest(ordUserRuleReq);
		ruleParams.setSourceFrom(ordUserRuleReq.getOrderItem().getSource_from());
		ruleParams.setService_code(ordUserRuleReq.getService_code());
		ruleParams.setRule_type(ConstsCore.RULE_TYPE_USERSTAFF);
		ruleParams.setUserSessionId(ordUserRuleReq.getUserSessionId());
		ruleParams.setMethod_name(SETORDERUSERS);
		//触发规则
		
		CoreThreadLocalHolder.getInstance().getZteCommonData().setZteRequest(ruleParams);
		RuleFactory.getInstance();
		OrdUserRuleResp ordUserRuleResp =  (OrdUserRuleResp) RuleFactory.perform(ruleParams);
		CoreThreadLocalHolder.getInstance().getZteCommonData().setZteRequest(ruleParams);
		return ordUserRuleResp;
	}
	
	
	
	//订单字段userid信息设置
	@SuppressWarnings("static-access")
	public static OrderOwnerUserResp setOrderOwnerUser(OrderOwnerUserReq orderOwnerUserReq) throws ApiBusiException {
		
		//设置订单归属userid
		RuleParams ruleParams = new RuleParams();
		ruleParams.setGoods_id((String)orderOwnerUserReq.getInMap().get("goods_id"));
		ruleParams.setZteRequest(orderOwnerUserReq);
		ruleParams.setAppKey((String)orderOwnerUserReq.getInMap().get("app_key"));
		ruleParams.setSourceFrom((String)orderOwnerUserReq.getInMap().get("source_from"));
		ruleParams.setService_code((String)orderOwnerUserReq.getInMap().get("service_code"));
		ruleParams.setRule_type(ConstsCore.RULE_TYPE_USERSTAFF);
		ruleParams.setMethod_name(SETORDERUSERS);
		ruleParams.setUserSessionId(orderOwnerUserReq.getUserSessionId());
		//触发规则
		CoreThreadLocalHolder.getInstance().getZteCommonData().setZteRequest(ruleParams);
		RuleFactory.getInstance();
		ZteResponse zteResponse = RuleFactory.perform(ruleParams);
		CoreThreadLocalHolder.getInstance().getZteCommonData().setZteRequest(ruleParams);
		if(zteResponse ==null)
			return null;
		return (OrderOwnerUserResp)zteResponse;
		
	}
	//========================================================设置订单用户信息结束==================================================================
	
	
	/**
	 * 消息同步规则
	 * @param coQueueRuleReq
	 * @return
	 * @throws ApiBusiException
	 */
	@SuppressWarnings("static-access")
	public static CoQueueRuleResp coQueue(CoQueueRuleReq coQueueRuleReq) throws ApiBusiException {
		
		RuleParams ruleParams = new RuleParams();
		ruleParams.setZteRequest(coQueueRuleReq);
		ruleParams.setSourceFrom(coQueueRuleReq.getSource_from());
		ruleParams.setService_code(coQueueRuleReq.getService_code());
		ruleParams.setRule_type(ConstsCore.SERVICE_TYPE_SERVICE);
		ruleParams.setMethod_name(COQUEUE);
		ruleParams.setUserSessionId(coQueueRuleReq.getUserSessionId());
		
		//触发规则
		CoreThreadLocalHolder.getInstance().getZteCommonData().setZteRequest(ruleParams);
		RuleFactory.getInstance();
		ZteResponse zteResponse = RuleFactory.perform(ruleParams);
		CoreThreadLocalHolder.getInstance().getZteCommonData().setZteRequest(ruleParams);
		if (zteResponse == null) {
			return null;
		}
		
		return (CoQueueRuleResp)zteResponse;
	}
	
	/**
	 * 消息同步规则
	 * @param coQueueRuleReq
	 * @return
	 * @throws ApiBusiException
	 */
	@SuppressWarnings("static-access")
	public static CoQueueRuleResp coQueueGProd(CoQueueRuleReq coQueueRuleReq) throws ApiBusiException {
		
		RuleParams ruleParams = new RuleParams();
		ruleParams.setZteRequest(coQueueRuleReq);
		ruleParams.setSourceFrom(coQueueRuleReq.getSource_from());
		ruleParams.setService_code("CO_GUIJI_AUTO");
		ruleParams.setRule_type(ConstsCore.SERVICE_TYPE_SERVICE);
		ruleParams.setMethod_name(COQUEUE);
		
		//触发规则
		CoreThreadLocalHolder.getInstance().getZteCommonData().setZteRequest(ruleParams);
		RuleFactory.getInstance();
		ZteResponse zteResponse = RuleFactory.perform(ruleParams);
		CoreThreadLocalHolder.getInstance().getZteCommonData().setZteRequest(ruleParams);
		if (zteResponse == null) {
			return null;
		}
		
		return (CoQueueRuleResp)zteResponse;
	}
	
	
	@SuppressWarnings("static-access")
	public static MidDataProcessResp importProcess(MidDataProcessReq req) throws ApiBusiException {
		RuleParams ruleParams = new RuleParams();
		ruleParams.setZteRequest(req);
		ruleParams.setSourceFrom(req.getSource_from());
		ruleParams.setService_code(null);
		ruleParams.setRule_type(ConstsCore.SERVICE_TYPE_IMPORT);
		ruleParams.setMethod_name(IMPORT);
		ruleParams.setExec_rule(req.getExec_rule());
		//触发规则
		CoreThreadLocalHolder.getInstance().getZteCommonData().setZteRequest(ruleParams);
		RuleFactory.getInstance();
		ZteResponse zteResponse = RuleFactory.perform(ruleParams);
		CoreThreadLocalHolder.getInstance().getZteCommonData().setZteRequest(ruleParams);
		if (zteResponse == null) {
			return null;
		}
		
		return (MidDataProcessResp)zteResponse;
	}
	
	@SuppressWarnings("static-access")
	public static OrderFinishRuleResp afOrderFinish(OrderFinishRuleReq req) throws ApiBusiException{
		RuleParams ruleParams = new RuleParams();
		ruleParams.setZteRequest(req);
		ruleParams.setSourceFrom(ManagerUtils.getSourceFrom());
		ruleParams.setService_code(AbsOrderFinishRule.SERVICE_CODE);
		ruleParams.setRule_type(AbsOrderFinishRule.SERVICE_TYPE);
		ruleParams.setMethod_name(COQUEUE);
		
		CoreThreadLocalHolder.getInstance().getZteCommonData().setZteRequest(ruleParams);
		RuleFactory.getInstance();
		ZteResponse zteResponse = RuleFactory.perform(ruleParams);
		CoreThreadLocalHolder.getInstance().getZteCommonData().setZteRequest(ruleParams);
		if (zteResponse == null) {
			return null;
		}
		return (OrderFinishRuleResp)zteResponse;
	}
	
	@SuppressWarnings("static-access")
	public static OrderPackageRuleResp afOrderPackage(OrderPackageRuleReq req) throws ApiBusiException{
		RuleParams ruleParams = new RuleParams();
		ruleParams.setZteRequest(req);
		ruleParams.setSourceFrom(ManagerUtils.getSourceFrom());
		ruleParams.setService_code(AbsOrderPackageRule.SERVICE_CODE);
		ruleParams.setRule_type(AbsOrderPackageRule.SERVICE_TYPE);
		ruleParams.setMethod_name(COQUEUE);
		
		CoreThreadLocalHolder.getInstance().getZteCommonData().setZteRequest(ruleParams);
		RuleFactory.getInstance();
		ZteResponse zteResponse = RuleFactory.perform(ruleParams);
		CoreThreadLocalHolder.getInstance().getZteCommonData().setZteRequest(ruleParams);
		if (zteResponse == null) {
			return null;
		}
		return (OrderPackageRuleResp)zteResponse;
	}
	
	@SuppressWarnings("static-access")
	public static OrderPayRuleResp afOrderPay(OrderPayRuleReq req) throws ApiBusiException{
		RuleParams ruleParams = new RuleParams();
		ruleParams.setZteRequest(req);
		ruleParams.setSourceFrom(ManagerUtils.getSourceFrom());
		ruleParams.setService_code(AbsOrderPayRule.SERVICE_CODE);
		ruleParams.setRule_type(AbsOrderPayRule.SERVICE_TYPE);
		ruleParams.setMethod_name(COQUEUE);
		
		CoreThreadLocalHolder.getInstance().getZteCommonData().setZteRequest(ruleParams);
		RuleFactory.getInstance();
		ZteResponse zteResponse = RuleFactory.perform(ruleParams);
		CoreThreadLocalHolder.getInstance().getZteCommonData().setZteRequest(ruleParams);
		if (zteResponse == null) {
			return null;
		}
		return (OrderPayRuleResp)zteResponse;
	}
	
	@SuppressWarnings("static-access")
	public static OrderShippingRuleResp afOrderShipping(OrderShippingRuleReq req) throws ApiBusiException{
		RuleParams ruleParams = new RuleParams();
		ruleParams.setZteRequest(req);
		ruleParams.setSourceFrom(ManagerUtils.getSourceFrom());
		ruleParams.setService_code(AbsOrderShippingRule.SERVICE_CODE);
		ruleParams.setRule_type(AbsOrderShippingRule.SERVICE_TYPE);
		ruleParams.setMethod_name(COQUEUE);
		
		CoreThreadLocalHolder.getInstance().getZteCommonData().setZteRequest(ruleParams);
		RuleFactory.getInstance();
		ZteResponse zteResponse = RuleFactory.perform(ruleParams);
		CoreThreadLocalHolder.getInstance().getZteCommonData().setZteRequest(ruleParams);
		if (zteResponse == null) {
			return null;
		}
		return (OrderShippingRuleResp)zteResponse;
	}
	
	//规则处理器
	public static class RuleFactory{
		public static RuleFactory getInstance(){
			return new RuleFactory();
		}
		public static ZteResponse perform(RuleParams ruleParams){
			ZteClient client = null;
			String source_from =ruleParams.getSourceFrom();
			//client = ClientFactory.getZteDubboClient(source_from);
			if(!StringUtil.isEmpty(source_from) && source_from.indexOf("_")>-1)
				source_from = source_from.substring(0, source_from.indexOf("_"));// add by wui
			
			ruleParams.setApiMethodName("zte.service."+source_from.toLowerCase()+".process.ruleparams");//""  zte.service.fj.process.ruleparams zte.service."+source_from.toLowerCase()+".process.ruleparams
			//logger.info(ruleParams.getApiMethodName()+"============================="+source_from);
			//ZteResponse zteResponse = client.execute(ruleParams, ZteResponse.class);
			IEcsServices ecsService = SpringContextHolder.getBean("ecsServices");
			ZteResponse zteResponse = new ZteResponse();
			try {
				zteResponse = ecsService.processrule(ruleParams);
			} catch (ApiBusiException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return zteResponse;
			
		}
	}
}
