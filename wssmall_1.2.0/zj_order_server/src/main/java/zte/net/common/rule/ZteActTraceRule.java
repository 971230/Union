package zte.net.common.rule;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import com.ztesoft.api.ApiBusiException;
import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.ibss.common.util.StringUtils;
import com.ztesoft.net.ecsord.params.ecaop.req.BandUserDataReq;
import com.ztesoft.net.ecsord.params.ecaop.req.O2OStatusUpdateReq;
import com.ztesoft.net.ecsord.params.ecaop.req.OrderChargeReq;
import com.ztesoft.net.ecsord.params.ecaop.resp.BandUserDataResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.O2OStatusUpdateResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.OrderChargeResp;
import com.ztesoft.net.ecsord.params.ecaop.vo.MSGPKGVO;
import com.ztesoft.net.ecsord.params.ecaop.vo.PKGBODYVO;
import com.ztesoft.net.ecsord.params.ecaop.vo.PKGHEADVO;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.IDaoSupport;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.consts.ZjEcsOrderConsts;
import com.ztesoft.net.mall.core.service.impl.RequestStoreManager;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.model.BusiDealResult;
import com.ztesoft.net.service.ICommonManager;
import com.ztesoft.net.service.IOrdOpenAccountTacheManager;
import com.ztesoft.net.service.IOrdWorkManager;
import com.ztesoft.net.service.IOrderFlowManager;
import com.ztesoft.net.sqls.SF;

import commons.CommonTools;
import consts.ConstsCore;
import params.ZteResponse;
import params.order.req.OrderQueueCardActionLogReq;
import zte.net.card.params.req.WriteQueueReq;
import zte.net.common.annontion.context.action.ZteMethodAnnontion;
import zte.net.common.annontion.context.action.ZteServiceAnnontion;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.SpecConsts;
import zte.net.ecsord.params.base.req.BusiCompRequest;
import zte.net.ecsord.params.base.resp.BusiCompResponse;
import zte.net.ecsord.params.busi.req.AttrFeeInfoBusiRequest;
import zte.net.ecsord.params.busi.req.OrderBusiRequest;
import zte.net.ecsord.params.busi.req.OrderItemExtBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;
import zte.net.ecsord.params.busi.req.OrderWMSKeyInfoBusiRequest;
import zte.net.ecsord.params.wyg.req.StatuSynchReq;
import zte.net.ecsord.params.zb.resp.NotifyOpenAccountGDResponse;
import zte.net.ecsord.rule.tache.TacheFact;
import zte.net.ecsord.utils.PCWriteCardTools;

/**
 *
 * 3.5.5订单开户规则集_开户规则处理
 * @author wu.i
 */
@ZteServiceAnnontion(trace_name="ZteActTraceRule",trace_id="1",version="1.0",desc="开户环节业务通知组件")
public  class ZteActTraceRule extends ZteTraceBaseRule {
	private static Logger logger = Logger.getLogger(ZteActTraceRule.class);
	
	@Resource
	private IOrdOpenAccountTacheManager ordOpenAccountTacheManager;
	
	@Resource
	private ICommonManager commonManager;
	
	@Resource
	private IOrderFlowManager ordFlowManager;
	
	@Resource
	private IOrdWorkManager ordWorkManager;
	
	/**
	 *开户环节
	 */
	@Override
	@ZteMethodAnnontion(name="开户环节入口",group_name="开户环节",order="1",page_show=true,path="actTraceRule.exec")
	public BusiCompResponse engineDo(BusiCompRequest busiCompRequest) {
		this.init();
		String orderId = busiCompRequest.getOrder_id();
		OrderBusiRequest orderBusiRequest = CommonDataFactory.getInstance().getOrderTree(orderId).getOrderBusiRequest();
		orderBusiRequest.setStatus(ZjEcsOrderConsts.DIC_ORDER_STATUS_4);
		orderBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
		orderBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
		orderBusiRequest.store();
		
		BusiCompResponse resp = new BusiCompResponse();
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}
	
	/**
	 * 总部开户结果接收
	 * @throws ApiBusiException 
	 */
	@ZteMethodAnnontion(name="总部开户结果接收（开户环节）",group_name="开户环节",order="1",page_show=true,path="ZteActTraceRule.zbSynAccountNotify")
	public BusiCompResponse zbSynAccountNotify(BusiCompRequest busiCompRequest) throws ApiBusiException{
		this.init();
		BusiCompResponse resp = new BusiCompResponse();
		try{
			//开户结果要通知新商城：仅通知，不用考虑新商城接受的状态。流程运行到写卡环节，获取到总部的写卡数据时还要通知新商城
			BusiDealResult openActResp = ordOpenAccountTacheManager.recOpenAcctResult(busiCompRequest);
			NotifyOpenAccountGDResponse infResp = new NotifyOpenAccountGDResponse();
			if(openActResp.getError_code().equals(EcsOrderConsts.ZB_INF_RESP_CODE_0000)){
				infResp.setError_code(EcsOrderConsts.ZB_INF_RESP_CODE_0000);
				infResp.setError_msg(openActResp.getError_msg());
				
				StatuSynchReq statuSyn = new StatuSynchReq(busiCompRequest.getOrder_id(),EcsOrderConsts.DIC_ORDER_NODE_D,EcsOrderConsts.DIC_ORDER_NODE_D_DESC,EcsOrderConsts.ACCOUNT_STATUS_1,EcsOrderConsts.ACCOUNT_STATUS_1_DESC,"");
				CommonDataFactory.getInstance().notifyNewShop(statuSyn);
			
			}else{
				CommonTools.addBusiError(openActResp.getError_code(),openActResp.getError_msg());
			}
			resp.setResponse(infResp);
		}catch(Exception e){
			//标记异常
			Map params = busiCompRequest.getQueryParams();
			TacheFact fact = (TacheFact) params.get("fact");
			String order_id = fact.getOrder_id();
			BusiCompRequest busi = new BusiCompRequest();
			Map queryParams = new HashMap();
			queryParams.put(EcsOrderConsts.EXCEPTION_FROM, EcsOrderConsts.EXCEPTION_FROM_ORD);
			queryParams.put("order_id", order_id);
			busi.setEnginePath("zteCommonTraceRule.markedException");
			busi.setOrder_id(order_id);
			busi.setQueryParams(queryParams);
			try {
				orderServices.execBusiComp(busi);
			}catch(Exception f){
				CommonTools.addBusiError("-99999","异常标记失败");
			}
			CommonTools.addBusiError("-9999","开户处理异常");
		}
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}

	/**
	 * 通知WMS开户完成
	 * @throws ApiBusiException 
	 */
	@ZteMethodAnnontion(name="通知WMS开户完成（开户环节）",group_name="开户环节",order="2",page_show=true,path="ZteActTraceRule.accountCompleteNotifyToWms")
	public BusiCompResponse accountCompleteNotifyToWms(BusiCompRequest busiCompRequest) throws ApiBusiException{
		this.init();
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		BusiDealResult dealResult = ordOpenAccountTacheManager.notifyOpenAcctToWMS(order_id);
		if(!dealResult.getError_code().equals(EcsOrderConsts.WMS_INF_RESP_CODE_0000))
			CommonTools.addBusiError(dealResult.getError_code(),dealResult.getError_msg());
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}
	
	/**
	 * 下一步(环节跳转)-自动化模式
	 */
	@ZteMethodAnnontion(name="下一步(开户环节)",group_name="开户环节",order="3",page_show=true,path="ZteActTraceRule.nextStep")
	public BusiCompResponse nextStep(BusiCompRequest busiCompRequest){
		this.init();
		BusiCompResponse resp = this.nextflow(busiCompRequest, false,"D");
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}
	
	/**
	 * 下一步(环节跳转)
	 */
	@ZteMethodAnnontion(name="下一步[next异步](开户环节)",group_name="开户环节",order="3",page_show=true,path="ZteActTraceRule.nextStepAsyn")
	public BusiCompResponse nextStepAsyn(BusiCompRequest busiCompRequest){
		// 下一步后面的操作异步处理
		if (busiCompRequest.getQueryParams() != null) {
			busiCompRequest.getQueryParams().put("deal_from", EcsOrderConsts.DEAL_FROM_INF);
		}
		BusiCompResponse resp = this.nextflow(busiCompRequest, false,"D");
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}	
	
	/**
	 * 订单开户完成(余额转移通知BSS)
	 */
	@ZteMethodAnnontion(name="订单开户完成(余额转移通知BSS)",group_name="开户环节",order="4",page_show=true,path="ZteActTraceRule.orderAccountInform")
	public BusiCompResponse orderAccountInform(BusiCompRequest busiCompRequest) throws ApiBusiException{
		this.init();
		BusiCompResponse resp = new BusiCompResponse();
		//0-开户 1-返销
		BusiDealResult dealResult= commonManager.orderAccountOrBuybackInform(busiCompRequest.getOrder_id(),EcsOrderConsts.ORDER_ACCOUNT);
		if(!EcsOrderConsts.INF_RESP_CODE_0000.equals(dealResult.getError_code()))
			CommonTools.addBusiError(dealResult.getError_code(),dealResult.getError_msg());
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}
	
	/**
	 * 开户处理申请
	 * @throws ApiBusiException 
	 */
	@ZteMethodAnnontion(name="[AOP]开户处理申请（开户环节）",group_name="开户环节",order="5",page_show=true,path="zteActTraceRule.openAccountApplyAop")
	public BusiCompResponse openAccountApplyAop(BusiCompRequest busiCompRequest) throws ApiBusiException{
		this.init();
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		BusiDealResult openActResp = ordOpenAccountTacheManager.openApplyToAop(busiCompRequest.getOrder_id());
		if(!StringUtils.equals(openActResp.getError_code(), EcsOrderConsts.AOP_SUCCESS_0000)){
			CommonTools.addBusiError(openActResp.getError_code(),openActResp.getError_msg());			
		}
		resp.setResponse(openActResp.getResponse());		
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}


	@ZteMethodAnnontion(name="[AOP]开户处理提交（开户环节）",group_name="开户环节",order="6",page_show=true,path="zteActTraceRule.openAccountSubmitAop")
	public BusiCompResponse openAccountSubmitAop(BusiCompRequest busiCompRequest) throws ApiBusiException{
		this.init();
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		BusiDealResult dealResult = ordOpenAccountTacheManager.openDealSubmitToAop(order_id);
		if(!StringUtils.equals(dealResult.getError_code(), EcsOrderConsts.AOP_SUCCESS_0000)){
			CommonTools.addBusiError(dealResult.getError_code(),dealResult.getError_msg());		
			//标记办理失败
			ordOpenAccountTacheManager.modifyAttrPackageStatus(order_id, EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_1);	
		}else{
			//标记办理
			ordOpenAccountTacheManager.modifyAttrPackageStatus(order_id, EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_3);
		}
		resp.setResponse(dealResult.getResponse());
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}
		
	
	/**
	 * [AOP]老用户优惠购机处理申请
	 * @param busiCompRequest
	 * @return
	 * @throws ApiBusiException
	 */
	@ZteMethodAnnontion(name = "[AOP]老用户优惠购机申请", group_name = "开户环节", order = "7", page_show = true, path = "zteActTraceRule.oldActivityMobileApply")
	public BusiCompResponse oldActivityMobileApply(BusiCompRequest busiCompRequest)  throws ApiBusiException {
		this.init();
		BusiCompResponse resp = new BusiCompResponse();
		BusiDealResult developResp = ordOpenAccountTacheManager.oldActivityMobileApply(busiCompRequest.getOrder_id());
		if(!StringUtils.equals(developResp.getError_code(), EcsOrderConsts.AOP_SUCCESS_0000)){
			CommonTools.addBusiError(developResp.getError_code(),developResp.getError_msg());
		}
		resp.setError_code("0");
		resp.setError_msg("老用户优惠购机申请执行成功");
		resp.setResponse(developResp.getResponse());
		return resp;
	}
	
	/**
	 * [AOP]老用户优惠购机处理提交
	 * @param busiCompRequest
	 * @return
	 * @throws ApiBusiException
	 */
	@ZteMethodAnnontion(name = "[AOP]老用户优惠购机提交", group_name = "开户环节", order = "8", page_show = true, path = "zteActTraceRule.oldActivityMobileSubmit")
	public BusiCompResponse oldActivityMobileSubmit(BusiCompRequest busiCompRequest) throws ApiBusiException {	
		this.init();
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		BusiDealResult oldSubmitResp = ordOpenAccountTacheManager.oldActivityMobileSubmit(order_id);
		if(!StringUtils.equals(oldSubmitResp.getError_code(), EcsOrderConsts.AOP_SUCCESS_0000)){
			CommonTools.addBusiError(oldSubmitResp.getError_code(),oldSubmitResp.getError_msg());
			//标记办理失败
			ordOpenAccountTacheManager.modifyAttrPackageStatus(order_id, EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_1);	
		}else{
			//标记办理
			ordOpenAccountTacheManager.modifyAttrPackageStatus(order_id, EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_3);
		}
		resp.setError_code("0");
		resp.setError_msg("老用户优惠购机提交执行成功");
		resp.setResponse(oldSubmitResp.getResponse());
		return resp;
	}
	
	/**
	 * [AOP]套餐变更申请
	 * @param busiCompRequest
	 * @return
	 * @throws ApiBusiException
	 */
	@ZteMethodAnnontion(name="[AOP]套餐变更申请",group_name="开户环节",order="9",page_show=true,path="zteActTraceRule.prodChangeApply")
	public BusiCompResponse prodChangeApply(BusiCompRequest busiCompRequest) throws ApiBusiException{
		this.init();
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		BusiDealResult toFourCheckResponse = ordOpenAccountTacheManager.productChangeApply(order_id);
		if(!StringUtils.equals(toFourCheckResponse.getError_code(), EcsOrderConsts.AOP_SUCCESS_0000)){
			CommonTools.addBusiError(toFourCheckResponse.getError_code(),toFourCheckResponse.getError_msg());
		} else {
			resp.setError_code("0");
			resp.setError_msg("套餐变更执行成功");
		}
		resp.setResponse(toFourCheckResponse.getResponse());
		return resp;
	}
	
	/**
	 * [AOP]套餐变更提交
	 * @param busiCompRequest
	 * @return
	 * @throws ApiBusiException
	 */
	@ZteMethodAnnontion(name="[AOP]套餐变更提交",group_name="开户环节",order="10",page_show=true,path="zteActTraceRule.prodChangeSubmit")
	public BusiCompResponse prodChangeSubmit(BusiCompRequest busiCompRequest) throws ApiBusiException{
		this.init();
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();	
		BusiDealResult toFourCheckResponse = ordOpenAccountTacheManager.productChangeSubmit(order_id);
		if(!StringUtils.equals(toFourCheckResponse.getError_code(), EcsOrderConsts.AOP_SUCCESS_0000)){
			CommonTools.addBusiError(toFourCheckResponse.getError_code(),toFourCheckResponse.getError_msg());
		}else{
			resp.setError_code("0");
			resp.setError_msg("套餐变更执行成功");
		}
		resp.setResponse(toFourCheckResponse.getResponse());
		return resp;
	}
	
	
	/**
	 * [AOP]23转4G开户申请
	 * @throws ApiBusiException  
	 */
	@ZteMethodAnnontion(name="[AOP]23转4G开户申请",group_name="开户环节",order="11",page_show=true,path="zteActTraceRule.openAccApply23to4")
	public BusiCompResponse openAccApply23to4(BusiCompRequest busiCompRequest) throws ApiBusiException{
		this.init();
		BusiCompResponse resp = new BusiCompResponse();
		BusiDealResult rsp = ordOpenAccountTacheManager.openAccApply23to4(busiCompRequest.getOrder_id());
		if(!StringUtils.equals(rsp.getError_code(), EcsOrderConsts.AOP_SUCCESS_0000)){
			CommonTools.addBusiError(rsp.getError_code(),rsp.getError_msg());			
		}
		resp.setError_code("0");
		resp.setError_msg("23转4G开户处理申请执行成功");
		return resp;
	}
	
	/**
	 * [AOP]23转4G开户提交
	 * @param busiCompRequest
	 * @return
	 * @throws ApiBusiException
	 */
	@ZteMethodAnnontion(name="[AOP]23转4G开户提交",group_name="开户环节",order="12",page_show=true,path="zteActTraceRule.openAccSubmit23to4")
	public BusiCompResponse openAccSubmit23to4(BusiCompRequest busiCompRequest) throws ApiBusiException{
		this.init();
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		BusiDealResult dealResult = ordOpenAccountTacheManager.openAccSubmit23to4(order_id);
		if(!StringUtils.equals(dealResult.getError_code(), EcsOrderConsts.AOP_SUCCESS_0000)){
			CommonTools.addBusiError(dealResult.getError_code(),dealResult.getError_msg());			
		}
		resp.setResponse(dealResult.getResponse());
		resp.setError_code("0");
		resp.setError_msg("23转4G开户处理提交执行成功");
		return resp;
	}
	
	/**
	 * [AOP]退机申请
	 * @param busiCompRequest
	 * @return
	 * @throws ApiBusiException
	 */
	@ZteMethodAnnontion(name = "[AOP]退机申请", group_name = "开户环节", order = "13", page_show = true, path = "zteActTraceRule.returnMachineApply")
	public BusiCompResponse returnMachineApply(BusiCompRequest busiCompRequest)  throws ApiBusiException {	
		this.init();
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		BusiDealResult rsp = ordOpenAccountTacheManager.returnMachineApply(order_id);
		if(!StringUtils.equals(rsp.getError_code(), EcsOrderConsts.AOP_SUCCESS_0000)){
			CommonTools.addBusiError(rsp.getError_code(),rsp.getError_msg());
		}
		resp.setError_code("0");
		resp.setError_msg("退机申请");
		resp.setResponse(rsp.getResponse());
		return resp;
	}
	
	/**
	 * [AOP]退机提交
	 * @param busiCompRequest
	 * @return
	 * @throws ApiBusiException
	 */
	@ZteMethodAnnontion(name = "[AOP]退机提交", group_name = "开户环节", order = "14", page_show = true, path = "zteActTraceRule.returnMachineSubmit")
	public BusiCompResponse returnMachineSubmit(BusiCompRequest busiCompRequest)  throws ApiBusiException {	
		this.init();
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		BusiDealResult rsp = ordOpenAccountTacheManager.returnMachineSubmit(order_id);
		if(!StringUtils.equals(rsp.getError_code(), EcsOrderConsts.AOP_SUCCESS_0000)){
			CommonTools.addBusiError(rsp.getError_code(),rsp.getError_msg());
		}
		resp.setError_code("0");
		resp.setError_msg("退机提交");
		resp.setResponse(rsp.getResponse());
		return resp;
	}
	

	/**
	 * 4G老用户存费送费
	 * @param busiCompRequest
	 * @return
	 * @throws ApiBusiException
	 */
	@ZteMethodAnnontion(name = "[AOP]4G老用户存费送费", group_name = "开户环节", order = "15", page_show = true, path = "zteActTraceRule.cunFeeSongFee4GAop")
	public BusiCompResponse cunFeeSongFee4GAop(BusiCompRequest busiCompRequest)  throws ApiBusiException {	
		this.init();
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		BusiDealResult rsp = ordOpenAccountTacheManager.cunFeeSongFee4GAop(order_id);
		if(!StringUtils.equals(rsp.getError_code(), EcsOrderConsts.AOP_SUCCESS_0000)){
			CommonTools.addBusiError(rsp.getError_code(),rsp.getError_msg());
		}
		resp.setError_code("0");
		resp.setError_msg("4G老用户存费送费");
		resp.setResponse(rsp.getResponse());
		return resp;
	}
	
	/**
	 * 4G老用户存费送费--浙江
	 * @param busiCompRequest
	 * @return
	 * @throws ApiBusiException
	 */
	@ZteMethodAnnontion(name = "[AOP]4G老用户存费送费浙江", group_name = "开户环节", order = "15", page_show = true, path = "zteActTraceRule.cunFeeSongFee4GAopZJ")
	public BusiCompResponse cunFeeSongFee4GAopZJ(BusiCompRequest busiCompRequest)  throws ApiBusiException {	
		this.init();
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		BusiDealResult rsp = ordOpenAccountTacheManager.cunFeeSongFee4GAopZJ(order_id);
		if(!StringUtils.equals(rsp.getError_code(), EcsOrderConsts.AOP_SUCCESS_0000)){
			CommonTools.addBusiError(rsp.getError_code(),rsp.getError_msg());
		}
		resp.setError_code("0");
		resp.setError_msg("4G老用户存费送费");
		resp.setResponse(rsp.getResponse());
		return resp;
	}
	
	/**
	 * 4G老用户存费送费正式提交
	 * @param busiCompRequest
	 * @return
	 * @throws ApiBusiException
	 */
	@ZteMethodAnnontion(name="[AOP]开4G老用户存费送费正式提交",group_name="开户环节",order="6",page_show=true,path="zteActTraceRule.cunFeeSongFee4GSubmitAop")
	public BusiCompResponse cunFeeSongFee4GSubmitAop(BusiCompRequest busiCompRequest) throws ApiBusiException{
		this.init();
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		BusiDealResult dealResult = ordOpenAccountTacheManager.cunFeeSongFee4GSubmitAop(order_id);
		if(!StringUtils.equals(dealResult.getError_code(), EcsOrderConsts.AOP_SUCCESS_0000)){
			CommonTools.addBusiError(dealResult.getError_code(),dealResult.getError_msg());	
			//标记办理失败
			ordOpenAccountTacheManager.modifyAttrPackageStatus(order_id, EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_1);	
		}else{
			//标记办理
			ordOpenAccountTacheManager.modifyAttrPackageStatus(order_id, EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_3);
		}
		resp.setResponse(dealResult.getResponse());
		resp.setError_code("0");
		resp.setError_msg("4G老用户存费送费正式提交成功");
		return resp;
	}
	
	/**
	 * 4G老用户存费送费正式提交--浙江
	 * @param busiCompRequest
	 * @return
	 * @throws ApiBusiException
	 */
	@ZteMethodAnnontion(name="[AOP]开4G老用户存费送费正式提交浙江",group_name="开户环节",order="6",page_show=true,path="zteActTraceRule.cunFeeSongFee4GSubmitAopZJ")
	public BusiCompResponse cunFeeSongFee4GSubmitAopZJ(BusiCompRequest busiCompRequest) throws ApiBusiException{
		this.init();
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		BusiDealResult dealResult = ordOpenAccountTacheManager.cunFeeSongFee4GSubmitAopZJ(order_id);
		if(!StringUtils.equals(dealResult.getError_code(), EcsOrderConsts.AOP_SUCCESS_0000)){
			CommonTools.addBusiError(dealResult.getError_code(),dealResult.getError_msg());	
			//标记办理失败
			ordOpenAccountTacheManager.modifyAttrPackageStatus(order_id, EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_1);	
		}else{
			//标记办理
			ordOpenAccountTacheManager.modifyAttrPackageStatus(order_id, EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_3);
		}
		resp.setResponse(dealResult.getResponse());
		resp.setError_code("0");
		resp.setError_msg("4G老用户存费送费正式提交成功");
		return resp;
	}
	
	/**
	 * 开户处理申请
	 * @throws ApiBusiException 
	 */
	@ZteMethodAnnontion(name="[BSS]开户处理申请（开户环节）",group_name="开户环节",order="5",page_show=true,path="zteActTraceRule.openAccountApplyBSS")
	public BusiCompResponse openAccountApplyBSS(BusiCompRequest busiCompRequest) throws ApiBusiException{
		this.init();
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		BusiDealResult openActResp = ordOpenAccountTacheManager.openApplyToBSS(busiCompRequest.getOrder_id());
		if(!StringUtils.equals(openActResp.getError_code(), EcsOrderConsts.AOP_SUCCESS_0000)){
			CommonTools.addBusiError(openActResp.getError_code(),openActResp.getError_msg());			
		}
		resp.setResponse(openActResp.getResponse());		
		resp.setError_code("0");
		resp.setError_msg("[BSS]开户处理申请成功");
		resp.setResponse(openActResp.getResponse());
		return resp;
	}


	@ZteMethodAnnontion(name="[BSS]开户处理提交（开户环节）",group_name="开户环节",order="6",page_show=true,path="zteActTraceRule.openAccountSubmitBSS")
	public BusiCompResponse openAccountSubmitBSS(BusiCompRequest busiCompRequest) throws ApiBusiException{
		this.init();
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		BusiDealResult dealResult = ordOpenAccountTacheManager.openDealSubmitToBSS(order_id);
		if(!StringUtils.equals(dealResult.getError_code(), EcsOrderConsts.AOP_SUCCESS_0000)){
			CommonTools.addBusiError(dealResult.getError_code(),dealResult.getError_msg());		
			//标记办理失败
			ordOpenAccountTacheManager.modifyAttrPackageStatus(order_id, EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_1);		
		}else{
			//标记办理
			ordOpenAccountTacheManager.modifyAttrPackageStatus(order_id, EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_3);
		}
		resp.setResponse(dealResult.getResponse());
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		resp.setError_msg("[BSS]开户处理提交成功");
		resp.setResponse(dealResult.getResponse());
		return resp;
	}
	
	/**
	 * 老用户续约申请（AOP）
	 * @param busiCompRequest
	 * @return
	 * @throws ApiBusiException
	 */
	@ZteMethodAnnontion(name = "老用户续约申请（AOP）", group_name = "开户环节", order = "7", page_show = true, path = "zteActTraceRule.oldRenActivityApply")
	public BusiCompResponse oldRenActivityApply(BusiCompRequest busiCompRequest)  throws ApiBusiException {
		this.init();
		BusiCompResponse resp = new BusiCompResponse();
		BusiDealResult developResp = ordOpenAccountTacheManager.oldRenActivityApply(busiCompRequest.getOrder_id());
		if(!StringUtils.equals(developResp.getError_code(), EcsOrderConsts.AOP_SUCCESS_0000)){
			CommonTools.addBusiError(developResp.getError_code(),developResp.getError_msg());
		}
		resp.setError_code("0");
		resp.setError_msg("老用户续约申请执行成功");
		resp.setResponse(developResp.getResponse());
		return resp;
	}
	
	/**
	 * 老用户续约提交（AOP）
	 * @param busiCompRequest
	 * @return
	 * @throws ApiBusiException
	 */
	@ZteMethodAnnontion(name = "老用户续约提交（AOP）", group_name = "开户环节", order = "7", page_show = true, path = "zteActTraceRule.oldRenActivitySubmit")
	public BusiCompResponse oldRenActivitySubmit(BusiCompRequest busiCompRequest)  throws ApiBusiException {
		this.init();
		BusiCompResponse resp = new BusiCompResponse();
		BusiDealResult developResp = ordOpenAccountTacheManager.oldRenActivitySubmit(busiCompRequest.getOrder_id());
		if(!StringUtils.equals(developResp.getError_code(), EcsOrderConsts.AOP_SUCCESS_0000)){
			CommonTools.addBusiError(developResp.getError_code(),developResp.getError_msg());
			//标记办理失败
			ordOpenAccountTacheManager.modifyAttrPackageStatus(busiCompRequest.getOrder_id(), EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_1);	
		}else{
			//标记办理
			ordOpenAccountTacheManager.modifyAttrPackageStatus(busiCompRequest.getOrder_id(), EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_3);
		}
		resp.setError_code("0");
		resp.setError_msg("老用户续约提交执行成功");
		resp.setResponse(developResp.getResponse());
		return resp;
	}
	
	@ZteMethodAnnontion(name="PC批量写卡匹配队列",group_name="开户环节",order="20",page_show=true,path="zteActTraceRule.writeCardSetQueueByPc")
	public BusiCompResponse writeCardSetQueueByPc(BusiCompRequest busiCompRequest) throws ApiBusiException{
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		OrderWMSKeyInfoBusiRequest orderWMSReq = orderTree.getOrderWMSKeyInfoBusiRequest();

		IDaoSupport support = SpringContextHolder.getBean("baseDaoSupport");
		logger.info("=================zteActTraceRule.writeCardSetQueueByPc开户环节PC批量写卡匹配队列缓存："+orderWMSReq.getWrite_machine_code());
		//如果缓存中取不到数据，则直接查数据库
		if(null != orderWMSReq && StringUtils.isEmpty(orderWMSReq.getWrite_machine_code())){
			String sql = SF.ecsordSql("SELECT_ORDER_PC_QUEUE");
			String queue_code = support.queryForString(sql, order_id);
			if(StringUtils.isNotEmpty(queue_code)){
				orderWMSReq.setWrite_machine_code(queue_code);
			}
			logger.info("=================zteActTraceRule.writeCardSetQueueByPc开户环节PC批量写卡匹配队列数据库："+orderWMSReq.getWrite_machine_code());
		}
		logger.info("=================开户环节PC批量写卡匹配队列获取的队列编码："+orderWMSReq.getWrite_machine_code());
		if(null != orderWMSReq && StringUtils.isNotEmpty(orderWMSReq.getWrite_machine_code())){
			String sql = SF.ecsordSql("INSERT_ORDER_TO_PC_QUEUE");
			support.execute(sql, orderWMSReq.getWrite_machine_code(), order_id,orderTree.getOrderExtBusiRequest().getOut_tid());
			
			OrderQueueCardActionLogReq logReq = new OrderQueueCardActionLogReq();
			logReq.setAction_code(EcsOrderConsts.QUEUE_ACTION_01);
			logReq.setAction_desc("订单插入批量写卡队列");
			logReq.setOrder_id(order_id);
			logReq.setQueue_code(orderWMSReq.getWrite_machine_code());
			PCWriteCardTools.saveQueueCardActionLog(logReq);
		}else{
			CommonTools.addBusiError("-9999","订单["+order_id+"]队列编码为空","1");
		}
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}	
	
	
	@ZteMethodAnnontion(name="[BSS]开户正式提交（开户环节）",group_name="[BSS]开户正式提交（开户环节）",order="6",page_show=true,path="zteActTraceRule.subAccountOfficial")
	public BusiCompResponse subAccountOfficial(BusiCompRequest busiCompRequest) throws ApiBusiException{
		this.init();
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		BusiDealResult dealResult = ordOpenAccountTacheManager.subAccountOfficial(order_id);
		if(!StringUtils.equals(dealResult.getError_code(), EcsOrderConsts.AOP_SUCCESS_0000)){
			CommonTools.addBusiError(dealResult.getError_code(),dealResult.getError_msg());		
			//标记办理失败
			ordOpenAccountTacheManager.modifyAttrPackageStatus(order_id, EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_1);	
		}else{
			//标记办理
			ordOpenAccountTacheManager.modifyAttrPackageStatus(order_id, EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_3);
		}
		resp.setResponse(dealResult.getResponse());
		resp.setError_code("0");
		resp.setError_msg("[BSS]开户正式提交执行成功");
		return resp;
	}

	
	@ZteMethodAnnontion(name="[BSS]开户预提交（开户环节）",group_name="开户环节",order="6",page_show=true,path="zteActTraceRule.bssPreCommit")
	public BusiCompResponse bssPreCommit(BusiCompRequest busiCompRequest) throws ApiBusiException{
		this.init();
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		BusiDealResult dealResult = ordOpenAccountTacheManager.bssPreCommit(order_id);
		if(!StringUtils.equals(dealResult.getError_code(), EcsOrderConsts.AOP_SUCCESS_0000)){
			CommonTools.addBusiError(dealResult.getError_code(),dealResult.getError_msg());
		}
		resp.setResponse(dealResult.getResponse());
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}
	
	@ZteMethodAnnontion(name="[爬虫]总商订单二次分配",group_name="开户环节",order="6",page_show=true,path="zteActTraceRule.zbReAllotOrder")
	public BusiCompResponse zbReAllotOrder(BusiCompRequest busiCompRequest) throws ApiBusiException{
		this.init();
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		BusiDealResult dealResult = ordOpenAccountTacheManager.zbReAllotOrder(order_id);
		if(!StringUtils.equals(dealResult.getError_code(), ConstsCore.ERROR_SUCC)){
			CommonTools.addBusiError(dealResult.getError_code(),dealResult.getError_msg());
		}
		resp.setResponse(dealResult.getResponse());
		resp.setError_code(ConstsCore.ERROR_SUCC);
		resp.setError_msg("执行成功");
		return resp;
	}
	
	@ZteMethodAnnontion(name="总商进入开户订单详情页面",group_name="开户环节",order="6",page_show=true,path="zteActTraceRule.zbEnterAccountDetailPage")
	public BusiCompResponse zbEnterAccountDetailPage(BusiCompRequest busiCompRequest) throws ApiBusiException{
		this.init();
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		//BusiDealResult dealResult = ordOpenAccountTacheManager.zbEnterAccountDetailPage(order_id);
		String zbOpenType = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ZB_OPEN_TYPE);
		if(StringUtil.isEmpty(zbOpenType)){
			CommonTools.addBusiError(ConstsCore.ERROR_FAIL,"总商系统开户类型为空","1");
		}
		resp.setError_code(ConstsCore.ERROR_SUCC);
		resp.setError_msg("执行成功");
		return resp;
	}
	
	@ZteMethodAnnontion(name="爬虫回填商品信息到总商",group_name="开户环节",order="6",page_show=true,path="zteActTraceRule.modifyGoodsInfoToZb")
	public BusiCompResponse modifyGoodsInfoToZb(BusiCompRequest busiCompRequest) throws ApiBusiException{
		this.init();
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		BusiDealResult dealResult = ordOpenAccountTacheManager.modifyGoodsInfoToZb(order_id);
		if(!StringUtils.equals(dealResult.getError_code(), ConstsCore.ERROR_SUCC)){
			CommonTools.addBusiError(dealResult.getError_code(),dealResult.getError_msg(),"1");
		}
		resp.setResponse(dealResult.getResponse());
		resp.setError_code(ConstsCore.ERROR_SUCC);
		resp.setError_msg("执行成功");
		return resp;
	}
	
	@ZteMethodAnnontion(name="爬虫回填配送信息到总商",group_name="开户环节",order="6",page_show=true,path="zteActTraceRule.modifyDeliveryInfoToZb")
	public BusiCompResponse modifyDeliveryInfoToZb(BusiCompRequest busiCompRequest) throws ApiBusiException{
		this.init();
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		BusiDealResult dealResult = ordOpenAccountTacheManager.modifyDeliveryInfoToZb(order_id);
		if(!StringUtils.equals(dealResult.getError_code(), ConstsCore.ERROR_SUCC)){
			CommonTools.addBusiError(dealResult.getError_code(),dealResult.getError_msg(),"1");
		}
		resp.setResponse(dealResult.getResponse());
		resp.setError_code(ConstsCore.ERROR_SUCC);
		resp.setError_msg("执行成功");
		return resp;
	}
	
	@ZteMethodAnnontion(name="爬虫模拟总商开户校验",group_name="开户环节",order="26",page_show=true,path="zteActTraceRule.openAccountValidateZb")
	public BusiCompResponse openAccountValidateZb(BusiCompRequest busiCompRequest) throws ApiBusiException{
		this.init();
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		BusiDealResult dealResult = ordOpenAccountTacheManager.openAccountValidateZb(order_id);
		if(!StringUtils.equals(dealResult.getError_code(), ConstsCore.ERROR_SUCC)){
			CommonTools.addBusiError(dealResult.getError_code(),dealResult.getError_msg(),"1");
		}
		resp.setResponse(dealResult.getResponse());
		resp.setError_code(ConstsCore.ERROR_SUCC);
		resp.setError_msg("执行成功");
		return resp;
	}
	@ZteMethodAnnontion(name="爬虫手动开户提交",group_name="开户环节",order="26",page_show=true,path="zteActTraceRule.manualOpenAccountSubmit")
	public BusiCompResponse  manualOpenAccountSubmit(BusiCompRequest busiCompRequest) throws ApiBusiException{
		this.init();
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		BusiDealResult dealResult = ordOpenAccountTacheManager.manualOpenAccountSubmit(order_id);
		if(!StringUtils.equals(dealResult.getError_code(), ConstsCore.ERROR_SUCC)){
			CommonTools.addBusiError(dealResult.getError_code(),dealResult.getError_msg(),"1");
		}
		resp.setResponse(dealResult.getResponse());
		resp.setError_code(ConstsCore.ERROR_SUCC);
		resp.setError_msg("执行成功");
		return resp;
	}
	
	/**
	 * [宽带用户]宽带受理费用查询
	 * @throws ApiBusiException 
	 */
	@ZteMethodAnnontion(name="[宽带用户]宽带受理费用查询（开户环节）",group_name="开户环节",order="4",page_show=true,path="zteActTraceRule.openAccountBroadbandFee")
	public BusiCompResponse openAccountBroadbandFee(BusiCompRequest busiCompRequest) throws ApiBusiException{
		this.init();
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		BusiDealResult dealResult = ordOpenAccountTacheManager.openAccountBroadbandFee(order_id);
		if(!StringUtils.equals(dealResult.getError_code(), EcsOrderConsts.BSS_SUCCESS_00000)){
			CommonTools.addBusiError(dealResult.getError_code(),dealResult.getError_msg());
		}
		resp.setResponse(dealResult.getResponse());
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}
	
	/**
	 * [宽带用户]开户预受理申请
	 * @throws ApiBusiException 
	 */
	@ZteMethodAnnontion(name="[宽带用户]开户预受理申请（开户环节）",group_name="开户环节",order="5",page_show=true,path="zteActTraceRule.openAccountApplyBroadband")
	public BusiCompResponse openAccountApplyBroadband(BusiCompRequest busiCompRequest) throws ApiBusiException{
		this.init();
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		OrderTreeBusiRequest ordertree = CommonDataFactory.getInstance().getOrderTree(order_id);
		String type_id = CommonDataFactory.getInstance().getGoodSpec(order_id, null, SpecConsts.TYPE_ID);
		String order_from = ordertree.getOrderExtBusiRequest().getOrder_from();
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		String check_fee = cacheUtil.getConfigInfo("CHECK_FEE_"+order_from+"_"+type_id);
		if(!StringUtil.isEmpty(check_fee)){
			Double paymoney = ordertree.getOrderBusiRequest().getPaymoney()*1000;
			int orderFee = paymoney.intValue();
			List<AttrFeeInfoBusiRequest> attrFeeList = ordertree.getFeeInfoBusiRequests();
			String Phone_deposit = ordertree.getOrderItemBusiRequests().get(0).getOrderItemExtBusiRequest().getPhone_deposit();
			Double schemefee = new Double(Phone_deposit==null?"0.00":Phone_deposit)*1000;
			int schemeFee = schemefee.intValue();
			int businessFee = 0;
			if(attrFeeList!=null&&attrFeeList.size()>0){
				for (int i = 0; i < attrFeeList.size(); i++) {
					AttrFeeInfoBusiRequest attrFee = attrFeeList.get(i);
					Double N_fee_num = attrFee.getN_fee_num()*1000;
					businessFee += N_fee_num.intValue();
				}
			}
			/*if(orderFee == (businessFee+schemeFee)){
			}else{
				resp.setError_code("1");
				resp.setError_msg("订单费用("+new Double(orderFee/1000)+")与实际费用("+new Double((businessFee+schemeFee)/1000)+")不一致，请联系业务管理员。");
				return resp;
			}*/
			ordertree.getOrderBusiRequest().setPaymoney(new Double(businessFee+schemeFee)/1000);
			String sql ="update es_order set paymoney=?  where order_id=?";
			IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
			baseDaoSupport.execute(sql, (businessFee+schemeFee)/1000,order_id);
			//更新订单树缓存
			cache.set(RequestStoreManager.NAMESPACE, RequestStoreManager.DC_TREE_CACHE_NAME_KEY+"order_id"+ order_id,ordertree, RequestStoreManager.time);
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[]{"order_realfee"}, new String[]{((businessFee+schemeFee)/1000)+""});
		}
		
		BusiDealResult dealResult = ordOpenAccountTacheManager.openAccountApplyBroadband(order_id);
		if(!StringUtils.equals(dealResult.getError_code(), EcsOrderConsts.BSS_SUCCESS_00000)){
			OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
			if (ordWorkManager.isKDYQ(order_id, orderTree)) {
				ordWorkManager.updateOrderState(order_id,"4",dealResult.getError_msg());
			}
			CommonTools.addBusiError(dealResult.getError_code(),dealResult.getError_msg());
		}
		resp.setResponse(dealResult.getResponse());
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}

	/**
	 * [宽带用户]正式开户提交
	 * @throws ApiBusiException 
	 */
	@ZteMethodAnnontion(name="[宽带用户]正式开户提交（开户环节）",group_name="开户环节",order="6",page_show=true,path="zteActTraceRule.openAccountSubmitBroadband")
	public BusiCompResponse openAccountSubmitBroadband(BusiCompRequest busiCompRequest) throws ApiBusiException{
		this.init();
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		BusiDealResult dealResult = ordOpenAccountTacheManager.openAccountSubmitBroadband(order_id);
		if(!StringUtils.equals(dealResult.getError_code(), EcsOrderConsts.BSS_SUCCESS_00000)){
			//标记办理失败
			ordOpenAccountTacheManager.modifyAttrPackageStatus(order_id, EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_1);	
			OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
			if (ordWorkManager.isKDYQ(order_id, orderTree)) {
				ordWorkManager.updateOrderState(order_id,"4",dealResult.getError_msg());
			}
			CommonTools.addBusiError(dealResult.getError_code(),dealResult.getError_msg());		
		}else{
			//标记办理
			ordOpenAccountTacheManager.modifyAttrPackageStatus(order_id, EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_3);
		}
		resp.setResponse(dealResult.getResponse());
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}
	
	/**
	 * [宽带用户]开户状态同步码上购系统
	 * @param busiCompRequest
	 * @return
	 * @throws ApiBusiException
	 */
	@ZteMethodAnnontion(name="[宽带用户]开户状态同步码上购（开户环节）",group_name="开户环节",order="7",page_show=true,path="zteActTraceRule.accountStatusSynToO2M")
	public BusiCompResponse accountStatusSynToO2M(BusiCompRequest busiCompRequest) throws ApiBusiException{
		this.init();
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		BusiDealResult dealResult = ordOpenAccountTacheManager.accountStatusSynToO2M(order_id);
		if(!StringUtils.equals(dealResult.getError_code(), EcsOrderConsts.BSS_SUCCESS_00000)){
			CommonTools.addBusiError(dealResult.getError_code(),dealResult.getError_msg());
		}
		resp.setResponse(dealResult.getResponse());
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}
	
	/**
	 * [宽带用户]接收省分BSS施工结果
	 * @param busiCompRequest
	 * @return
	 * @throws ApiBusiException
	 */
	@ZteMethodAnnontion(name="[宽带用户]接收省分BSS施工结果（开户环节）",group_name="开户环节",order="8",page_show=true,path="zteActTraceRule.receiveConstructionStatus")
	public BusiCompResponse receiveConstructionStatus(BusiCompRequest busiCompRequest) throws ApiBusiException{
		this.init();
		BusiCompResponse resp = new BusiCompResponse();
		BusiDealResult dealResult = ordOpenAccountTacheManager.receiveConstructionStatus(busiCompRequest);
		if(!StringUtils.equals(dealResult.getError_code(), EcsOrderConsts.BSS_SUCCESS_00000)){
			CommonTools.addBusiError(dealResult.getError_code(),dealResult.getError_msg());
		}
		resp.setResponse(dealResult.getResponse());
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}
	
	/**
	 * [宽带用户]BSS施工结果同步码上购
	 * @param busiCompRequest
	 * @return
	 * @throws ApiBusiException
	 */
	@ZteMethodAnnontion(name="[宽带用户]BSS施工结果同步码上购（开户环节）",group_name="开户环节",order="9",page_show=true,path="zteActTraceRule.constructionStatusSynToO2M")
	public BusiCompResponse constructionStatusSynToO2M(BusiCompRequest busiCompRequest) throws ApiBusiException{
		this.init();
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		BusiDealResult dealResult = ordOpenAccountTacheManager.constructionStatusSynToO2M(order_id);
		if(!StringUtils.equals(dealResult.getError_code(), EcsOrderConsts.BSS_SUCCESS_00000)){
			CommonTools.addBusiError(dealResult.getError_code(),dealResult.getError_msg());
		}
		resp.setResponse(dealResult.getResponse());
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}
	
	/**
	 * BSS订单收费
	 * @param busiCompRequest
	 * @return
	 * @throws ApiBusiException
	 */
	@ZteMethodAnnontion(name="BSS订单收费",group_name="开户环节",order="9",page_show=true,path="zteActTraceRule.orderCharge")
	public BusiCompResponse orderCharge(BusiCompRequest busiCompRequest) throws ApiBusiException{
		BusiCompResponse resp = new BusiCompResponse();
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		OrderChargeReq requ  = new OrderChargeReq();
		requ.setNotNeedReqStrOrderId(busiCompRequest.getOrder_id());
		OrderChargeResp bssResp = client.execute(requ, OrderChargeResp.class);
		if(!StringUtils.equals(bssResp.getCode(), EcsOrderConsts.BSS_SUCCESS_00000)){
			CommonTools.addBusiError(bssResp.getError_code(),bssResp.getMsg());
		}
		/**
		 * Add Wcl
		 * 收费接口将返回的收费单号入库
		 */
		String bss_accemp_id = bssResp.getRespJson().getBms_accept_id();
		if(!StringUtils.isEmpty(bss_accemp_id)) {
			CommonDataFactory.getInstance().updateAttrFieldValue(busiCompRequest.getOrder_id(), new String[]{"order_charge_id"}, new String[]{bss_accemp_id});
		}
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}
	

	/**
	 * ECS省份业务用户提速续费改资料
	 * @param busiCompRequest
	 * @return
	 * @throws ApiBusiException
	 */
	@ZteMethodAnnontion(name="ECS省份业务用户提速续费改资料",group_name="开户环节",order="10",page_show=true,path="zteActTraceRule.bandUserData")
	public BusiCompResponse bandUserData(BusiCompRequest busiCompRequest) throws ApiBusiException{
		BusiCompResponse resp = new BusiCompResponse();
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		BandUserDataReq requ  = new BandUserDataReq();
		requ.setNotNeedReqStrOrderId(busiCompRequest.getOrder_id());
		BandUserDataResp bssResp = client.execute(requ, BandUserDataResp.class);
		if(!StringUtils.equals(bssResp.getCode(), EcsOrderConsts.BSS_SUCCESS_00000)){
			CommonTools.addBusiError(bssResp.getError_code(),bssResp.getMsg());
		}
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}
	/**
	 * [AOP]号卡开户预提交
	 * author huang.zhisheng@ztesoft.com
	 * @throws ApiBusiException 
	 */
	@ZteMethodAnnontion(name="[AOP]号卡开户预提交（开户环节）",group_name="开户环节",order="10",page_show=true,path="zteActTraceRule.openAccountPreCommitApplyAop")
	public BusiCompResponse openAccountPreCommitApplyAop(BusiCompRequest busiCompRequest) throws ApiBusiException{
		this.init();
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		BusiDealResult openActResp = ordOpenAccountTacheManager.openAccountPreCommitApplyAop(order_id);
		if(!StringUtils.equals(openActResp.getError_code(), EcsOrderConsts.AOP_SUCCESS_0000)){
			CommonTools.addBusiError(openActResp.getError_code(),openActResp.getError_msg());			
		}
		resp.setResponse(openActResp.getResponse());		
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}
	
	/**
	 * [AOP]号卡开户预提交--浙江
	 * author huang.zhisheng@ztesoft.com
	 * @throws ApiBusiException 
	 */
	@ZteMethodAnnontion(name="[AOP]号卡开户预提交（开户环节）浙江",group_name="开户环节",order="10",page_show=true,path="zteActTraceRule.openAccountPreCommitApplyAopZJ")
	public BusiCompResponse openAccountPreCommitApplyAopZJ(BusiCompRequest busiCompRequest) throws ApiBusiException{
		this.init();
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		BusiDealResult openActResp = ordOpenAccountTacheManager.openAccountPreCommitApplyAopZJ(order_id);
		if(!StringUtils.equals(openActResp.getError_code(), EcsOrderConsts.AOP_SUCCESS_0000)){
			CommonTools.addBusiError(openActResp.getError_code(),openActResp.getError_msg());			
		}
		resp.setResponse(openActResp.getResponse());		
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}
	
	/**
	 * [BSS]号卡开户预提交
	 * author huang.zhisheng@ztesoft.com
	 * @throws ApiBusiException 
	 */
	@ZteMethodAnnontion(name="[BSS]号卡开户预提交（开户环节）",group_name="开户环节",order="10",page_show=true,path="zteActTraceRule.openAccountPreCommitApplyBss")
	public BusiCompResponse openAccountPreCommitApplyBss(BusiCompRequest busiCompRequest) throws ApiBusiException{
		this.init();
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		
		// add by zhaochen 生产上发现数据库中有靓号信息，但是预提交时从缓存中取到的靓号信息为空。
		// 因此预提交之前先全量更新订单树
		CommonDataFactory.getInstance().updateOrderTree(order_id);
		
		BusiDealResult openActResp = new BusiDealResult();
		String cat_id = CommonDataFactory.getInstance().getGoodSpec(order_id, null, SpecConsts.CAT_ID);
		String typeId = CommonDataFactory.getInstance().getGoodSpec(order_id, null, SpecConsts.TYPE_ID);
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		String card_open_cat = cacheUtil.getConfigInfo("CARD_OPEN_CAT_"+cat_id);
		if(StringUtils.equals(typeId, EcsOrderConsts.GOODS_TYPE_RONGHE)) {
			openActResp = ordOpenAccountTacheManager.openAccountApplyBroadband(order_id);
		}else {
			if(!StringUtils.isEmpty(card_open_cat)&&"0".equals(card_open_cat)){
				openActResp = ordOpenAccountTacheManager.kuKaPreOpenBSS(busiCompRequest.getOrder_id());
			}else{
				openActResp = ordOpenAccountTacheManager.openAccountPreCommitApplyBss(busiCompRequest.getOrder_id());
			}
		}
		if(!StringUtils.equals(openActResp.getError_code(), EcsOrderConsts.BSS_SUCCESS_00000)){
			
			String order_model = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest().getOrder_model();
			if(!StringUtils.isEmpty(order_model)&&StringUtil.equals("06", order_model)){
				Map params = new HashMap();
				BusiCompRequest bcr = new BusiCompRequest();
				bcr.setEnginePath("zteCommonTraceRule.markedException");
				bcr.setOrder_id(order_id);
				params.put(EcsOrderConsts.EXCEPTION_FROM, EcsOrderConsts.EXCEPTION_FROM_ORD);
				params.put(EcsOrderConsts.NOTIFY_SR_DK, EcsOrderConsts.NOTIFY_SR_DK_VAL);
				params.put(EcsOrderConsts.EXCEPTION_TYPE, "");
				params.put(EcsOrderConsts.EXCEPTION_REMARK, openActResp.getError_msg());
				bcr.setQueryParams(params);
				CommonDataFactory.getInstance().execBusiComp(bcr);
			}
			CommonTools.addBusiError(openActResp.getError_code(),openActResp.getError_msg());	
		}
		resp.setResponse(openActResp.getResponse());		
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}
	
	
	/**
	 * [AOP]号卡开户正式提交
	 * @throws ApiBusiException 
	 */
	@ZteMethodAnnontion(name="[AOP]号卡开户正式提交（开户环节）",group_name="开户环节",order="11",page_show=true,path="zteActTraceRule.openAccountFormalCommitApplyAop")
	public BusiCompResponse openAccountFormalCommitApplyAop(BusiCompRequest busiCompRequest) throws ApiBusiException{
		this.init();
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		BusiDealResult dealResult = ordOpenAccountTacheManager.openAccountFormalCommitApplyAop(order_id);
		if(!StringUtils.equals(dealResult.getError_code(), EcsOrderConsts.AOP_SUCCESS_0000)){
			CommonTools.addBusiError(dealResult.getError_code(),dealResult.getError_msg());		
			//标记办理失败
			ordOpenAccountTacheManager.modifyAttrPackageStatus(order_id, EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_1);	
		}else{
			//标记办理
			ordOpenAccountTacheManager.modifyAttrPackageStatus(order_id, EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_3);
		}
		resp.setResponse(dealResult.getResponse());
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}
	
	/**
	 * [AOP]号卡开户正式提交--浙江
	 * @throws ApiBusiException 
	 */
	@ZteMethodAnnontion(name="[AOP]号卡开户正式提交（开户环节）浙江",group_name="开户环节",order="11",page_show=true,path="zteActTraceRule.openAccountFormalCommitApplyAopZJ")
	public BusiCompResponse openAccountFormalCommitApplyAopZJ(BusiCompRequest busiCompRequest) throws ApiBusiException{
		this.init();
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		BusiDealResult dealResult = ordOpenAccountTacheManager.openAccountFormalCommitApplyAopZJ(order_id);
		if(!StringUtils.equals(dealResult.getError_code(), EcsOrderConsts.AOP_SUCCESS_0000)){
			CommonTools.addBusiError(dealResult.getError_code(),dealResult.getError_msg());		
			//标记办理失败
			ordOpenAccountTacheManager.modifyAttrPackageStatus(order_id, EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_1);	
		}else{
			//标记办理
			ordOpenAccountTacheManager.modifyAttrPackageStatus(order_id, EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_3);
		}
		resp.setResponse(dealResult.getResponse());
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}
	
	
	/**
	 *  [AOP]获取卡信息
	 * @throws ApiBusiException 
	 */
	@ZteMethodAnnontion(name="[AOP]获取卡信息（开户环节）",group_name="开户环节",order="12",page_show=true,path="zteActTraceRule.cardInfoGet")
	public BusiCompResponse cardInfoGet(BusiCompRequest busiCompRequest) throws ApiBusiException{
		this.init();
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		BusiDealResult dealResult = ordOpenAccountTacheManager.cardInfoGet(order_id);
		if(!StringUtils.equals(dealResult.getError_code(), EcsOrderConsts.AOP_SUCCESS_0000)){
			CommonTools.addBusiError(dealResult.getError_code(),dealResult.getError_msg());		
		}
		resp.setResponse(dealResult.getResponse());
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}
	
	/**
	 *  [AOP]获取卡信息--浙江
	 * @throws ApiBusiException 
	 */
	@ZteMethodAnnontion(name="[AOP]获取卡信息（开户环节）浙江",group_name="开户环节",order="12",page_show=true,path="zteActTraceRule.cardInfoGetZJ")
	public BusiCompResponse cardInfoGetZJ(BusiCompRequest busiCompRequest) throws ApiBusiException{
		this.init();
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		BusiDealResult dealResult = ordOpenAccountTacheManager.cardInfoGetZJ(order_id);
		if(!StringUtils.equals(dealResult.getError_code(), EcsOrderConsts.AOP_SUCCESS_0000)){
			CommonTools.addBusiError(dealResult.getError_code(),dealResult.getError_msg());		
		}
		resp.setResponse(dealResult.getResponse());
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}
	
	
	/**
	 *  [BSS]获取卡信息
	 * @throws ApiBusiException 
	 */
	@ZteMethodAnnontion(name="[BSS]获取卡信息（开户环节）",group_name="开户环节",order="12",page_show=true,path="zteActTraceRule.cardInfoGetBss")
	public BusiCompResponse cardInfoGetBss(BusiCompRequest busiCompRequest) throws ApiBusiException{
		this.init();
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		BusiDealResult dealResult = ordOpenAccountTacheManager.cardInfoGetBss(order_id);
		if(!StringUtils.equals(dealResult.getError_code(), EcsOrderConsts.AOP_SUCCESS_0000)){
			String order_model = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest().getOrder_model();
			if(!StringUtils.isEmpty(order_model)&&StringUtil.equals("06", order_model)){
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
			CommonTools.addBusiError(dealResult.getError_code(),dealResult.getError_msg());	
			
		}
		resp.setResponse(dealResult.getResponse());
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}
	
	/**
	 * [AOP]写卡结果通知
	 * @throws ApiBusiException 
	 */
	@ZteMethodAnnontion(name="[BSS]写卡结果通知（开户环节）",group_name="开户环节",order="12",page_show=true,path="zteActTraceRule.writeCardResult")
	public BusiCompResponse writeCardResult(BusiCompRequest busiCompRequest) throws ApiBusiException{
		this.init();
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		BusiDealResult dealResult = ordOpenAccountTacheManager.writeCardResult(order_id);
		if(!StringUtils.equals(dealResult.getError_code(), EcsOrderConsts.AOP_SUCCESS_0000)){
			CommonTools.addBusiError(dealResult.getError_code(),dealResult.getError_msg());		
		}
		resp.setResponse(dealResult.getResponse());
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}
	
	/**
	 * [BSS]号卡开户正式提交
	 * @throws ApiBusiException 
	 */
	@ZteMethodAnnontion(name="[BSS]号卡开户正式提交（开户环节）",group_name="开户环节",order="13",page_show=true,path="zteActTraceRule.openAcctFormalSubBss")
	public BusiCompResponse openAcctFormalSubBss(BusiCompRequest busiCompRequest) throws ApiBusiException{
		this.init();
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		BusiDealResult dealResult = ordOpenAccountTacheManager.openAccountSubmitBroadband(order_id);
		if(!StringUtils.equals(dealResult.getError_code(), EcsOrderConsts.BSS_SUCCESS_00000)){
			String order_model = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest().getOrder_model();
			if(!StringUtils.isEmpty(order_model)&&StringUtil.equals("06", order_model)){
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
			CommonTools.addBusiError(dealResult.getError_code(),dealResult.getError_msg());		
			//标记办理失败
			ordOpenAccountTacheManager.modifyAttrPackageStatus(order_id, EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_1);	
			
		}else{
			//标记办理
			ordOpenAccountTacheManager.modifyAttrPackageStatus(order_id, EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_3);
		}
		resp.setResponse(dealResult.getResponse());
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}
	
	/**
	 * [宽带用户]开户预受理申请
	 * @throws ApiBusiException 
	 */
	@ZteMethodAnnontion(name="沃TV绑定（开户环节）",group_name="开户环节",order="5",page_show=true,path="zteActTraceRule.wotvBroadbandBind")
	public BusiCompResponse wotvBroadbandBind(BusiCompRequest busiCompRequest) throws ApiBusiException{
		this.init();
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		BusiDealResult dealResult = ordOpenAccountTacheManager.wotvBroadbandBind(order_id);
		if(!StringUtils.equals(dealResult.getError_code(), EcsOrderConsts.BSS_SUCCESS_00000)){
			CommonTools.addBusiError(dealResult.getError_code(),dealResult.getError_msg());
		}
		resp.setResponse(dealResult.getResponse());
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}
//	@ZteMethodAnnontion(name="总商爬虫PC自动化开户订单详情页面",group_name="开户环节",order="6",page_show=true,path="zteActTraceRule.zbPCEnterAccountDetailPage")
//	public BusiCompResponse zbPCEnterAccountDetailPage(BusiCompRequest busiCompRequest) throws ApiBusiException{
//		this.init();
//		BusiCompResponse resp = new BusiCompResponse();
//		String order_id = busiCompRequest.getOrder_id();
//		BusiDealResult dealResult = ordOpenAccountTacheManager.zbEnterAccountDetailPage(order_id);
//		if(!StringUtils.equals(dealResult.getError_code(), ConstsCore.ERROR_SUCC)){
//			CommonTools.addBusiError(dealResult.getError_code(),dealResult.getError_msg());
//		}
//		resp.setResponse(dealResult.getResponse());
//		resp.setError_code(ConstsCore.ERROR_SUCC);
//		resp.setError_msg("执行成功");
//		return resp;
//	}
	@ZteMethodAnnontion(name="爬虫PC批量写写卡队列",group_name="开户环节",order="20",page_show=true,path="zteActTraceRule.insertWriteCardQueueByPc")
	public BusiCompResponse insertWriteCardQueueByPc(BusiCompRequest busiCompRequest) throws ApiBusiException{
		BusiCompResponse result = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		
		OrderTreeBusiRequest tree = CommonDataFactory.getInstance().getOrderTree(order_id);
		String queue_no =  tree.getOrderWMSKeyInfoBusiRequest().getWrite_machine_code();
		WriteQueueReq req = new WriteQueueReq();
		req.setOrder_id(order_id);
		req.setGroup_no(queue_no);
		
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		ZteResponse resp = client.execute(req, ZteResponse.class);
		if(!resp.getError_code().equals(ConstsCore.ERROR_SUCC)){
			CommonTools.addBusiError(resp.getError_code(),resp.getError_msg(),"1");
		}
		result.setResponse(resp);
		result.setError_code("0");
		result.setError_msg("执行成功");
		return result;
	}
	/**
	 * 
	 * @param busiCompRequest
	 * @return
	 * @throws ApiBusiException
	 */
	@ZteMethodAnnontion(name="爬虫自动开户获取ICCID",group_name="写卡环节",order="4",page_show=true,path="zteActTraceRule.readICCIDFromOrder")
	public BusiCompResponse readICCIDFromOrder(BusiCompRequest busiCompRequest) throws ApiBusiException{
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		String iccId = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ICCID);
		if(StringUtil.isEmpty(iccId))
			CommonTools.addBusiError("-9999","未获取到ICCID","1");
		
		CommonDataFactory.getInstance().updateAttrFieldValue(order_id, 
				new String[]{AttrConsts.WRITE_CARD_STATUS}, 
				new String[]{EcsOrderConsts.WRITE_CARD_STATUS_2});
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}
	@ZteMethodAnnontion(name="PC批量写开户队列",group_name="开户环节",order="20",page_show=true,path="zteActTraceRule.setOpenAccountQueueByPc")
	public BusiCompResponse setOpenAccountQueueByPc(BusiCompRequest busiCompRequest) throws ApiBusiException{
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		OrderWMSKeyInfoBusiRequest orderWMSReq = orderTree.getOrderWMSKeyInfoBusiRequest();

		IDaoSupport support = SpringContextHolder.getBean("baseDaoSupport");
		//如果缓存中取不到数据，则直接查数据库
		if(null != orderWMSReq && StringUtils.isEmpty(orderWMSReq.getWrite_machine_code())){
			String sql = SF.ecsordSql("SELECT_ORDER_PC_QUEUE");
			String queue_code = support.queryForString(sql, order_id);
			if(StringUtils.isNotEmpty(queue_code)){
				orderWMSReq.setWrite_machine_code(queue_code);
			}
		}
		
		if(null != orderWMSReq && StringUtils.isNotEmpty(orderWMSReq.getWrite_machine_code())){
			String sql = SF.ecsordSql("INSERT_ORDER_TO_OPEN_ACCOUNT");
			support.execute(sql, order_id,orderWMSReq.getWrite_machine_code(),orderTree.getOrderExtBusiRequest().getOut_tid());
			
			OrderQueueCardActionLogReq logReq = new OrderQueueCardActionLogReq();
			logReq.setAction_code(EcsOrderConsts.QUEUE_ACTION_01);
			logReq.setAction_desc("订单插入批量开户队列");
			logReq.setOrder_id(order_id);
			logReq.setQueue_code(orderWMSReq.getWrite_machine_code());
			PCWriteCardTools.saveQueueCardActionLog(logReq);
		}else{
			CommonTools.addBusiError("-9999","订单["+order_id+"]队列编码为空","1");
		}
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}
	@ZteMethodAnnontion(name="[爬虫]写开户队列",group_name="开户环节",order="20",page_show=true,path="zteActTraceRule.setOpenAccountQueue")
	public BusiCompResponse setOpenAccountQueue(BusiCompRequest busiCompRequest) throws ApiBusiException{
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		IDaoSupport support = SpringContextHolder.getBean("baseDaoSupport");
		String sql = SF.ecsordSql("INSERT_ORDER_TO_PC_QUEUE");
		String out_tid=CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.OUT_TID);
		support.execute(sql, "manual_write_card", order_id,out_tid);
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}
	
	@ZteMethodAnnontion(name="电子卷发放接口",group_name="开户环节",order="21",page_show=true,path="zteActTraceRule.electronicVolumeSend")
	public BusiCompResponse electronicVolumeSend(BusiCompRequest busiCompRequest) throws ApiBusiException{
		this.init();
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		BusiDealResult dealResult = ordOpenAccountTacheManager.electronicVolumeSend(order_id);
		if(!StringUtils.equals(dealResult.getError_code(), EcsOrderConsts.RMP0000)){
			CommonTools.addBusiError(dealResult.getError_code(),dealResult.getError_msg());
		}
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}
	
	/**
	 * [AOP]宽带开户处理申请
	 * @throws ApiBusiException 
	 */
	@ZteMethodAnnontion(name="[AOP]宽带开户处理申请（开户环节）",group_name="开户环节",order="5",page_show=true,path="zteActTraceRule.broadBandOpenApplyAop")
	public BusiCompResponse broadBandOpenApplyAop(BusiCompRequest busiCompRequest) throws ApiBusiException{
		this.init();
		
		BusiCompResponse resp = new BusiCompResponse();
		
		BusiDealResult openActResp = ordOpenAccountTacheManager.
				broadBandOpenApplyAop(busiCompRequest.getOrder_id());
		
		if(!"0".equals(openActResp.getError_code())){
//			OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(busiCompRequest.getOrder_id());
//			if (ordWorkManager.isKDYQ(busiCompRequest.getOrder_id(), orderTree)) {
//				ordWorkManager.updateOrderState(busiCompRequest.getOrder_id(),"4",openActResp.getError_msg());
//			}
			CommonTools.addBusiError(openActResp.getError_code(),openActResp.getError_msg());
		}
		
		resp.setResponse(openActResp.getResponse());		
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		
		return resp;
	}
	
	/**
	 * [AOP]宽带正式开户提交
	 * @throws ApiBusiException 
	 */
	@ZteMethodAnnontion(name="[AOP]宽带正式开户提交（开户环节）",group_name="开户环节",order="5",page_show=true,path="zteActTraceRule.broadBandOpenSubmitAop")
	public BusiCompResponse broadBandOpenSubmitAop(BusiCompRequest busiCompRequest) throws ApiBusiException{
		this.init();
		
		BusiCompResponse resp = new BusiCompResponse();
		
		String order_id = busiCompRequest.getOrder_id();
		
		BusiDealResult openActResp = ordOpenAccountTacheManager.
				broadBandOpenSubmitAop(busiCompRequest.getOrder_id());
		
		if(!"0".equals(openActResp.getError_code())){
			//标记办理失败
			ordOpenAccountTacheManager.modifyAttrPackageStatus(order_id, EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_1);
//			OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
//			if (ordWorkManager.isKDYQ(order_id, orderTree)) {
//				ordWorkManager.updateOrderState(order_id,"4",openActResp.getError_msg());
//			}
			CommonTools.addBusiError(openActResp.getError_code(),openActResp.getError_msg());
		}else{
			//标记办理
			ordOpenAccountTacheManager.modifyAttrPackageStatus(order_id, EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_3);
		}
		
		resp.setResponse(openActResp.getResponse());		
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		
		return resp;
	}
	/**
	 * @author song.qi
	 * @see 号卡、副卡、产品变更正式提交 2018年5月21日
	 * @param busiCompRequest
	 * @return
	 * @throws ApiBusiException
	 */
	@ZteMethodAnnontion(name="[AOP]号卡、副卡、产品变更正式提交",group_name="开户环节",order="9",page_show=true,path="zteActTraceRule.openAccountSubmit")
	public BusiCompResponse openAccountSubmit(BusiCompRequest busiCompRequest) throws ApiBusiException{
		this.init();
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		BusiDealResult toFourCheckResponse = ordOpenAccountTacheManager.openAccountSubmit(order_id);
		if(!StringUtils.equals(toFourCheckResponse.getError_code(), EcsOrderConsts.AOP_SUCCESS_0000)){
			CommonTools.addBusiError(toFourCheckResponse.getError_code(),toFourCheckResponse.getError_msg());
		} else {
			resp.setError_code("0");
			resp.setError_msg("执行成功");
		}
		resp.setResponse(toFourCheckResponse.getResponse());
		return resp;
	}
	
	/**
	 * @author song.qi
	 * @see [AOP]老用户加入主副卡 2018年5月21日
	 * @param busiCompRequest
	 * @return
	 * @throws ApiBusiException
	 */
	@ZteMethodAnnontion(name="[AOP]老用户加入主副卡",group_name="开户环节",order="9",page_show=true,path="zteActTraceRule.joinMVCard")
	public BusiCompResponse joinMVCard(BusiCompRequest busiCompRequest) throws ApiBusiException{
		this.init();
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		BusiDealResult toFourCheckResponse = ordOpenAccountTacheManager.joinMVCard(order_id);
	    ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
	    String infoString =   cacheUtil.getConfigInfo("access_info");
		if(!StringUtils.equals(toFourCheckResponse.getError_code(), EcsOrderConsts.AOP_SUCCESS_0000)){
			if(!StringUtils.isEmpty(infoString) && toFourCheckResponse.getError_msg().contains(infoString)){
			    resp.setError_code("0");
			    resp.setError_msg("执行成功");
			}else{
			    CommonTools.addBusiError(toFourCheckResponse.getError_code(),toFourCheckResponse.getError_msg());
			}
		} else {
		    resp.setError_code("0");
            resp.setError_msg("执行成功");
		}
		resp.setResponse(toFourCheckResponse.getResponse());
		return resp;
	}
	
	/**
	 * 
	 * @throws ApiBusiException 
	 */
	@ZteMethodAnnontion(name="[BSS]分享卡 副卡新开户预提交接口",group_name="开户环节",order="22",page_show=true,path="zteActTraceRule.kuKaPreOpenBSS")
	public BusiCompResponse kuKaPreOpenBSS(BusiCompRequest busiCompRequest) throws ApiBusiException{
		this.init();
		
		BusiCompResponse resp = new BusiCompResponse();
		
		BusiDealResult openActResp = ordOpenAccountTacheManager.kuKaPreOpenBSS(busiCompRequest.getOrder_id());
		
		if(!EcsOrderConsts.BSS_SUCCESS_00000.equals(openActResp.getError_code())){
			CommonTools.addBusiError(openActResp.getError_code(),openActResp.getError_msg());
		}
		
		//resp.setResponse(openActResp.getResponse());		
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		
		return resp;
	}
	
	@ZteMethodAnnontion(name="[BSS]统一补换卡预提交",group_name="开户环节",order="23",page_show=true,path="zteActTraceRule.changeAppPre")
	public BusiCompResponse changeAppPre(BusiCompRequest busiCompRequest) throws ApiBusiException{
		this.init();
		
		BusiCompResponse resp = new BusiCompResponse();
		
		BusiDealResult openActResp = ordOpenAccountTacheManager.changeAppPre(busiCompRequest.getOrder_id());
		
		if(!EcsOrderConsts.BSS_SUCCESS_00000.equals(openActResp.getError_code())){
			CommonTools.addBusiError(openActResp.getError_code(),openActResp.getError_msg());
		}
		
		//resp.setResponse(openActResp.getResponse());		
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		
		return resp;
	}
	
	@ZteMethodAnnontion(name="[BSS]统一补换卡正式提交",group_name="开户环节",order="24",page_show=true,path="zteActTraceRule.changeSub")
	public BusiCompResponse changeSub(BusiCompRequest busiCompRequest) throws ApiBusiException{
		this.init();
		
		BusiCompResponse resp = new BusiCompResponse();
		
		BusiDealResult openActResp = ordOpenAccountTacheManager.changeSub(busiCompRequest.getOrder_id());
		
		if(!EcsOrderConsts.BSS_SUCCESS_00000.equals(openActResp.getError_code())){
			CommonTools.addBusiError(openActResp.getError_code(),openActResp.getError_msg());
		}
		
		//resp.setResponse(openActResp.getResponse());		
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		
		return resp;
	}
	
	/**
	 * [AOP]副卡开户预提交
	 * @throws ApiBusiException 
	 */
	@ZteMethodAnnontion(name="[AOP]副卡开户预提交",group_name="开户环节",order="10",page_show=true,path="zteActTraceRule.mainViceOpen")
	public BusiCompResponse mainViceOpen(BusiCompRequest busiCompRequest) throws ApiBusiException{
		this.init();
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		BusiDealResult openActResp = ordOpenAccountTacheManager.mainViceOpen(order_id,orderTree);
		if(!StringUtils.equals(openActResp.getError_code(), "0")){
			CommonTools.addBusiError(openActResp.getError_code(),openActResp.getError_msg());
		}
		OrderItemExtBusiRequest orderItemExt = orderTree.getOrderItemBusiRequests().get(0)
				.getOrderItemExtBusiRequest();
		String set_sql = "active_no='"+orderItemExt.getActive_no()+"' , bssOrderId='"+orderItemExt.getBssOrderId()+"' , totalFee='"+orderItemExt.getTotalFee()+"'";
		this.updateOrderTree(set_sql, "es_order_items_ext", order_id, orderTree);
		CommonDataFactory.getInstance().updateOrderTree(order_id, "active_no");
		resp.setResponse(openActResp.getResponse());		
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}
	   /**
     * @author song.qi
     * @see 号卡、副卡、产品变更正式提交 2018年5月21日
     * @param busiCompRequest
     * @return
     * @throws ApiBusiException
     */
    @ZteMethodAnnontion(name="[AOP]主卡打标正式提交",group_name="开户环节",order="10",page_show=true,path="zteActTraceRule.openAccountMasterChg")
    public BusiCompResponse openAccountMasterChg(BusiCompRequest busiCompRequest) throws ApiBusiException{
        this.init();
        BusiCompResponse resp = new BusiCompResponse();
        String order_id = busiCompRequest.getOrder_id();
        BusiDealResult toFourCheckResponse = ordOpenAccountTacheManager.openAccountMasterSubmit(order_id);
        ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
        String infoString =   cacheUtil.getConfigInfo("access_chg_info");
        if(!StringUtils.equals(toFourCheckResponse.getError_code(), EcsOrderConsts.AOP_SUCCESS_0000)){
            if(!StringUtils.isEmpty(infoString)&&toFourCheckResponse.getError_msg().contains(infoString)){
                resp.setError_code("0");
                resp.setError_msg("执行成功");
            }else{
                CommonTools.addBusiError(toFourCheckResponse.getError_code(),toFourCheckResponse.getError_msg());
            }
        } else {
            resp.setError_code("0");
            resp.setError_msg("执行成功");
        }
        resp.setResponse(toFourCheckResponse.getResponse());
        return resp;
    }
    
    /**
	 * @author 王昌磊
	 * 固网用户更换光猫/机顶盒接口
	 * @throws ApiBusiException 
	 */
	@ZteMethodAnnontion(name="固网用户更换光猫/机顶盒接口",group_name="开户环节",order="10",page_show=true,path="zteActTraceRule.objectReplace")
	public BusiCompResponse objectReplace(BusiCompRequest busiCompRequest) throws ApiBusiException {
		this.init();
		BusiCompResponse resp = new BusiCompResponse() ;
		String orderId = busiCompRequest.getOrder_id() ;
		
		BusiDealResult subResult = ordOpenAccountTacheManager.objectReplaceSub(orderId);
		if(!StringUtils.equals(subResult.getError_code(), EcsOrderConsts.AIP_STATUS_CODE_SUCC)) {
			CommonTools.addBusiError(subResult.getError_code(),subResult.getError_msg());
		}
		
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		
		return resp ;
	}
	
	@ZteMethodAnnontion(name = "交易成功通知手机商城", group_name = "通用组件", order = "30", page_show = true, path = "ZteActTraceRule.o2OStatusUpdate")
	public BusiCompResponse o2OStatusUpdate(BusiCompRequest busiCompRequest) throws ApiBusiException {
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = getOrderId(busiCompRequest);
		O2OStatusUpdateReq req = new O2OStatusUpdateReq();
		req.setNotNeedReqStrOrderId(order_id);
		MSGPKGVO new_MSGPKG = new MSGPKGVO();
		PKGHEADVO PKG_HEAD = new PKGHEADVO();
		PKGBODYVO PKG_BODY = new PKGBODYVO();
		PKG_HEAD.setACTION_ID("M10001");
		String order_code = "";
		order_code = CommonDataFactory.getInstance().getAttrFieldValue(order_id, "intent_order_id");
		if(StringUtil.isEmpty(order_code)){
			ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
			String orderFroms = cacheUtil.getConfigInfo("REFUND_STATUS_SYN_ORDER_FROM");
			if(!StringUtil.isEmpty(orderFroms)&&orderFroms.contains(CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest().getOrder_from()+",")){
				order_code = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest().getOut_tid();
			}else{
				resp.setError_code("0");
				resp.setError_msg("执行成功[无需同步]");
				return resp;
			}
			
		}
		PKG_BODY.setORDER_CODE(order_code);
		PKG_BODY.setORDER_STATUS("05");
		new_MSGPKG.setPKG_BODY(PKG_BODY);
		new_MSGPKG.setPKG_HEAD(PKG_HEAD);
		req.setMSGPKG(new_MSGPKG);
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
	
}
