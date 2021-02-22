package zte.net.common.rule;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import params.ZteRequest;
import params.ZteResponse;
import params.order.req.OrderActionLogReq;
import params.order.req.OrderQueueCardActionLogReq;
import zte.net.common.annontion.context.action.ZteMethodAnnontion;
import zte.net.common.annontion.context.action.ZteServiceAnnontion;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.base.req.BusiCompRequest;
import zte.net.ecsord.params.base.resp.BusiCompResponse;
import zte.net.ecsord.params.busi.req.OrderBusiRequest;
import zte.net.ecsord.params.busi.req.OrderExtBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;
import zte.net.ecsord.params.busi.req.OrderWMSKeyInfoBusiRequest;
import zte.net.ecsord.params.writecard.MachinesModel;
import zte.net.ecsord.rule.tache.TacheFact;
import zte.net.ecsord.utils.AutoWriteCardCacheTools;
import zte.net.ecsord.utils.PCWriteCardTools;
import zte.net.params.req.RuleTreeExeReq;
import zte.net.params.resp.RuleTreeExeResp;

import com.ztesoft.api.ApiBusiException;
import com.ztesoft.api.AppKeyEnum;
import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.Task;
import com.ztesoft.api.TaskThreadPool;
import com.ztesoft.api.ThreadPoolFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.ibss.common.util.StringUtils;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.IDaoSupport;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.consts.ZjEcsOrderConsts;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.model.BusiDealResult;
import com.ztesoft.net.service.IOrdWriteCardTacheManager;

import commons.CommonTools;
import consts.ConstsCore;

/**
 *
 * 3.5.6 订单写卡规则集_写卡规则处理
 * @author wu.i
 */
@ZteServiceAnnontion(trace_name="ZteWriteCardTraceRule",trace_id="1",version="1.0",desc="写卡环节业务通知组件")
public  class ZteWriteCardTraceRule extends ZteTraceBaseRule {
	private static Logger logger = Logger.getLogger(ZteWriteCardTraceRule.class);
	@Resource
	private IOrdWriteCardTacheManager ordWriteCardTacheManager;
	/**
	 *写卡环节入口
	 */
	@Override
	@ZteMethodAnnontion(name="写卡环节入口",group_name="写卡环节",order="1",page_show=true,path="writeCardTraceRule.exec")
	public BusiCompResponse engineDo(BusiCompRequest busiCompRequest) {
		String orderId = busiCompRequest.getOrder_id();
		OrderBusiRequest orderBusiRequest = CommonDataFactory.getInstance().getOrderTree(orderId).getOrderBusiRequest();
		orderBusiRequest.setStatus(ZjEcsOrderConsts.DIC_ORDER_STATUS_5);
		orderBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
		orderBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
		orderBusiRequest.store();
		
		BusiCompResponse resp = new BusiCompResponse();
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}
	

	/**
	 * 接收写卡机编号
	 * @throws ApiBusiException 
	 */
	@ZteMethodAnnontion(name="接收写卡机编号(写卡环节)",group_name="写卡环节",order="2",page_show=true,path="ZteWriteCardTraceRule.wmsSynMachineNumNotify")
	public BusiCompResponse wmsSynMachineNumNotify(BusiCompRequest busiCompRequest) throws ApiBusiException{
		BusiCompResponse resp = new BusiCompResponse();
		BusiDealResult dealResult = ordWriteCardTacheManager.recWriteCardInf(busiCompRequest);
		if(!dealResult.getError_code().equals(EcsOrderConsts.WMS_INF_RESP_CODE_0000))
			CommonTools.addBusiError(dealResult.getError_code(),dealResult.getError_msg());
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}
	/**
	 *获取ICCID(自动化)
	 * @throws ApiBusiException 
	 */
	@ZteMethodAnnontion(name="获取ICCID(从森锐获取)",group_name="写卡环节",order="3",page_show=true,path="ZteWriteCardTraceRule.readCard")
	public BusiCompResponse readCard(BusiCompRequest busiCompRequest) throws ApiBusiException{
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		BusiDealResult dealResult = ordWriteCardTacheManager.getICCIDInf(order_id);
		if(!dealResult.getError_code().equals(EcsOrderConsts.BUSI_DEAL_RESULT_0000))
			CommonTools.addBusiError(dealResult.getError_code(),dealResult.getError_msg());
		resp.setResponse(dealResult.getResponse());
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}
	/**
	 * 
	 * @param busiCompRequest
	 * @return
	 * @throws ApiBusiException
	 */
	@ZteMethodAnnontion(name="获取ICCID(订单系统)",group_name="写卡环节",order="4",page_show=true,path="ZteWriteCardTraceRule.readCardFromOrd")
	public BusiCompResponse readCardFromOrd(BusiCompRequest busiCompRequest) throws ApiBusiException{
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
	
	/**
	 *同步写卡数据到森锐系统
	 * @throws ApiBusiException 
	 */
	@ZteMethodAnnontion(name="同步写卡数据到森锐系统(写卡环节)",group_name="写卡环节",order="5",page_show=true,path="ZteWriteCardTraceRule.writeCardDataSynToSenRui")
	public BusiCompResponse writeCardDataSynToSenRui(BusiCompRequest busiCompRequest) throws ApiBusiException{
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		BusiDealResult dealResult = ordWriteCardTacheManager.synWriteCardInfToSR(order_id);
		if(!dealResult.getError_code().equals(EcsOrderConsts.SR_INF_SUCC_CODE))
			CommonTools.addBusiError(dealResult.getError_code(),dealResult.getError_msg(),"1");
		resp.setResponse(dealResult.getResponse());
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
		
	}
	/**
	 *写卡结果通知总部
	 * @throws ApiBusiException 
	 */
	@ZteMethodAnnontion(name="写卡结果通知总部(写卡环节)",group_name="写卡环节",order="6",page_show=true,path="ZteWriteCardTraceRule.writeCardFinishToZb")
	public BusiCompResponse writeCardFinishToZb(BusiCompRequest busiCompRequest) throws ApiBusiException{
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		BusiDealResult dealResult = ordWriteCardTacheManager.synWriteCardInfToZB(order_id);
		if(!dealResult.getError_code().equals(EcsOrderConsts.ZB_INF_RESP_CODE_0000))
			CommonTools.addBusiError(dealResult.getError_code(),dealResult.getError_msg());
		resp.setResponse(dealResult.getResponse());
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
		
	}
	/**
	 *写卡完成通知WMS
	 * @throws ApiBusiException 
	 */
	@ZteMethodAnnontion(name="写卡完成通知WMS(写卡环节)",group_name="写卡环节",order="7",page_show=true,path="ZteWriteCardTraceRule.writeCardCompleteToWms")
	public BusiCompResponse writeCardCompleteToWms(BusiCompRequest busiCompRequest) throws ApiBusiException{
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		BusiDealResult dealResult = ordWriteCardTacheManager.synWriteCardInfoToWMS(order_id,EcsOrderConsts.ORDER_STATUS_WMS_0);
		if(!dealResult.getError_code().equals(EcsOrderConsts.WMS_INF_RESP_CODE_0000))
			CommonTools.addBusiError(dealResult.getError_code(),dealResult.getError_msg());
		resp.setResponse(dealResult.getResponse());
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
		
	}
	
	@ZteMethodAnnontion(name="通知WMS物理写卡完成(写卡环节)",group_name="写卡环节",order="8",page_show=true,path="ZteWriteCardTraceRule.notifyWMSBackCard")
	public BusiCompResponse notifyWMSBackCard(BusiCompRequest busiCompRequest) throws ApiBusiException{
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		//TODO
		BusiDealResult dealResult = ordWriteCardTacheManager.synWriteCardInfoToWMS(order_id, EcsOrderConsts.ORDER_STATUS_WRITE_CARD_2);
		if(!dealResult.getError_code().equals(EcsOrderConsts.WMS_INF_RESP_CODE_0000))
			CommonTools.addBusiError(dealResult.getError_code(),dealResult.getError_msg());
		resp.setResponse(dealResult.getResponse());
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}
	
	@ZteMethodAnnontion(name="通知森锐退卡(写卡环节)",group_name="写卡环节",order="9",page_show=true,path="ZteWriteCardTraceRule.notifySrBackCard")
	public BusiCompResponse notifySrBackCard(BusiCompRequest busiCompRequest) throws ApiBusiException{
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		String write_card_result = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.WRITE_CARD_RESULT);
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		OrderWMSKeyInfoBusiRequest orderWMS = orderTree.getOrderWMSKeyInfoBusiRequest();
		String temp_arrive = AutoWriteCardCacheTools.getTempCache(AutoWriteCardCacheTools.TEMP_CACHE_ARRIVE_KEY_PREFIX, order_id);
		String temp_write_s = AutoWriteCardCacheTools.getTempCache(AutoWriteCardCacheTools.TEMP_CACHE_WRITE_S_KEY_PREFIX, order_id);
		if (!EcsOrderConsts.WRITE_CARD_STATUS_SUCCESS.equals(write_card_result) && StringUtil.isEmpty(temp_write_s)) {
			CommonTools.addBusiError("-9999","未物理写卡完成!"+write_card_result+"-"+temp_write_s);
		}
		if (!EcsOrderConsts.IS_DEFAULT_VALUE.equals(orderWMS.getWas_arrived()) && StringUtil.isEmpty(temp_arrive)) {
			CommonTools.addBusiError("-9999","料箱未到达写卡机位!"+orderWMS.getWas_arrived()+"-"+temp_arrive);
		}

		String readId = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.READID);
		String workStation = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.WORK_STATION);
		if (StringUtils.isEmpty(workStation)) workStation = orderWMS.getWrite_machine_code();
		if (StringUtils.isEmpty(workStation)) workStation = AutoWriteCardCacheTools.getTempCache(AutoWriteCardCacheTools.TEMP_CACHE_WRITE_STAT_KEY_PREFIX, order_id);
		MachinesModel model = AutoWriteCardCacheTools.getMachineStatus(workStation, readId);
		if (!StringUtil.equals(model.getValue(), order_id)) {
			CommonTools.addBusiError("-9999","订单对应写卡机(station:"+workStation+",readId:"+readId+")的当前状态："+model.getValue());
		}
		//
		BusiDealResult dealResult = ordWriteCardTacheManager.refundCardToSR(order_id);
		if(!dealResult.getError_code().equals(EcsOrderConsts.WMS_INF_RESP_CODE_0000))
			CommonTools.addBusiError(dealResult.getError_code(),dealResult.getError_msg());
		resp.setResponse(dealResult.getResponse());
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		AutoWriteCardCacheTools.removeTempCache(AutoWriteCardCacheTools.TEMP_CACHE_ARRIVE_KEY_PREFIX, order_id);
		AutoWriteCardCacheTools.removeTempCache(AutoWriteCardCacheTools.TEMP_CACHE_WRITE_S_KEY_PREFIX, order_id);
		return resp;
	}
	
	/**
	 *获取总部写卡数据
	 * @throws ApiBusiException 
	 */
	@ZteMethodAnnontion(name="获取总部写卡数据(写卡环节)",group_name="写卡环节",order="10",page_show=true,path="ZteWriteCardTraceRule.writeCardData")
	public BusiCompResponse writeCardData(BusiCompRequest busiCompRequest) throws ApiBusiException{
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		//获取到写卡数据要通知新商城，通知新商城失败要跑出异常流程
		BusiDealResult dealResult = ordWriteCardTacheManager.getWriteCardInfFromZB(order_id);
		if(!dealResult.getError_code().equals(EcsOrderConsts.ZB_INF_RESP_CODE_0000))
			CommonTools.addBusiError(dealResult.getError_code(),dealResult.getError_msg());
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		resp.setResponse(dealResult.getResponse());
		return resp;
		
	}
	/**
	 *获取白卡数据
	 * @throws ApiBusiException 
	 */
	@ZteMethodAnnontion(name="获取白卡数据(写卡环节)",group_name="写卡环节",order="10",page_show=true,path="ZteWriteCardTraceRule.writeBlankCardData")
	public BusiCompResponse writeBlankCardData(BusiCompRequest busiCompRequest) throws ApiBusiException{
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		//获取到写卡数据要通知新商城，通知新商城失败要跑出异常流程
		BusiDealResult dealResult = ordWriteCardTacheManager.getBlankCardInf(order_id);
		if(!dealResult.getError_code().equals("00000"))
			CommonTools.addBusiError(dealResult.getError_code(),dealResult.getError_msg());
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		resp.setResponse(dealResult.getResponse());
		return resp;
		
	}
	/**
	 * 下一步(环节跳转)-自动化模式
	 */
	@ZteMethodAnnontion(name="下一步(写卡环节)",group_name="写卡环节",order="11",page_show=true,path="ZteWriteCardTraceRule.nextStep")
	public BusiCompResponse nextStep(BusiCompRequest busiCompRequest){
		
		Map map = busiCompRequest.getQueryParams();
		if(map==null)CommonTools.addError("1", "参数不能为空");
		String order_id = (String)map.get("order_id");
		if(StringUtil.isEmpty(order_id))order_id = busiCompRequest.getOrder_id();
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		String is_exception_flow = orderTree.getOrderExtBusiRequest().getIs_exception_flow();
		//异常流程写卡下一步 恢复异常--
		if(EcsOrderConsts.IS_EXCEPTION_FLOW_1.equals(is_exception_flow)){
			BusiCompRequest busi = new BusiCompRequest();
			Map queryParams = new HashMap();
			queryParams.put("order_id", order_id);
			queryParams.put(EcsOrderConsts.EXCEPTION_FROM,EcsOrderConsts.EXCEPTION_FROM_ORD);
			queryParams.put(EcsOrderConsts.EXCEPTION_TYPE,orderTree.getOrderExtBusiRequest().getException_type());
			queryParams.put(EcsOrderConsts.EXCEPTION_REMARK,orderTree.getOrderExtBusiRequest().getException_desc());
			busi.setEnginePath("zteCommonTraceRule.restorationException");
			busi.setOrder_id(order_id);
			busi.setQueryParams(queryParams);
			ZteResponse response = null;
			try {
				response = orderServices.execBusiComp(busi);
			} catch (Exception e) {
				CommonTools.addError("1", "恢复异常失败");
			}
		}
		
		BusiCompResponse resp = this.nextflow(busiCompRequest,false,"X");
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}
	
	/**
	 * 下一步(环节跳转)-自动化模式
	 */
	@ZteMethodAnnontion(name="下一步[next异步](写卡环节)",group_name="写卡环节",order="12",page_show=true,path="ZteWriteCardTraceRule.nextStepAsyn")
	public BusiCompResponse nextStepAsyn(BusiCompRequest busiCompRequest){
		
		Map map = busiCompRequest.getQueryParams();
		if(map==null)CommonTools.addError("1", "参数不能为空");
		String order_id = (String)map.get("order_id");
		if(StringUtil.isEmpty(order_id))order_id = busiCompRequest.getOrder_id();
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		String is_exception_flow = orderTree.getOrderExtBusiRequest().getIs_exception_flow();
		//异常流程写卡下一步 恢复异常--
		if(EcsOrderConsts.IS_EXCEPTION_FLOW_1.equals(is_exception_flow)){
			BusiCompRequest busi = new BusiCompRequest();
			Map queryParams = new HashMap();
			queryParams.put("order_id", order_id);
			queryParams.put(EcsOrderConsts.EXCEPTION_FROM,EcsOrderConsts.EXCEPTION_FROM_ORD);
			queryParams.put(EcsOrderConsts.EXCEPTION_TYPE,orderTree.getOrderExtBusiRequest().getException_type());
			queryParams.put(EcsOrderConsts.EXCEPTION_REMARK,orderTree.getOrderExtBusiRequest().getException_desc());
			busi.setEnginePath("zteCommonTraceRule.restorationException");
			busi.setOrder_id(order_id);
			busi.setQueryParams(queryParams);
			ZteResponse response = null;
			try {
				response = orderServices.execBusiComp(busi);
			} catch (Exception e) {
				CommonTools.addError("1", "恢复异常失败");
			}
		}

		// 下一步后面的操作异步处理
		if (busiCompRequest.getQueryParams() != null) {
			busiCompRequest.getQueryParams().put("deal_from", EcsOrderConsts.DEAL_FROM_INF);
		}
		BusiCompResponse resp = this.nextflow(busiCompRequest,false,"X");
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}
	
	/**
	 * 从AOP获取写卡数据
	 * @throws ApiBusiException 
	 */
	@ZteMethodAnnontion(name="[AOP]获取写卡数据(写卡环节)",group_name="写卡环节",order="12",page_show=true,path="zteWriteCardTraceRule.queryCardDataAop")
	public BusiCompResponse queryCardDataAop(BusiCompRequest busiCompRequest) throws ApiBusiException{
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		BusiDealResult dealResult = ordWriteCardTacheManager.queryCardDataFromAop(order_id);
		if(!dealResult.getError_code().equals(EcsOrderConsts.AOP_SUCCESS_0000)){
			CommonTools.addBusiError(dealResult.getError_code(),dealResult.getError_msg());			
		}
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		resp.setResponse(dealResult.getResponse());
		return resp;
	}
	
	/**
	 * 通知AOP写卡结果
	 * @throws ApiBusiException 
	 */
	@ZteMethodAnnontion(name="[AOP]写卡结果通知(写卡环节)",group_name="写卡环节",order="13",page_show=true,path="zteWriteCardTraceRule.WriteCardResultsNoticeAop")
	public BusiCompResponse WriteCardResultsNoticeAop(BusiCompRequest cardEesultNotiRequest) throws ApiBusiException{
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = cardEesultNotiRequest.getOrder_id();
		BusiDealResult dealResult = ordWriteCardTacheManager.noticeWriteCardResultToAop(order_id);		

		if(!StringUtils.equals(dealResult.getError_code(), EcsOrderConsts.AOP_SUCCESS_0000)){
			CommonTools.addBusiError(dealResult.getError_code(),dealResult.getError_msg());
		}
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		resp.setResponse(dealResult.getResponse());
		return resp;
	}
	
	/**
	 * 通知AOP写卡结果--浙江
	 * @throws ApiBusiException 
	 */
	@ZteMethodAnnontion(name="[AOP]写卡结果通知(写卡环节)浙江",group_name="写卡环节",order="13",page_show=true,path="zteWriteCardTraceRule.WriteCardResultsNoticeAopZJ")
	public BusiCompResponse WriteCardResultsNoticeAopZJ(BusiCompRequest cardEesultNotiRequest) throws ApiBusiException{
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = cardEesultNotiRequest.getOrder_id();
		BusiDealResult dealResult = ordWriteCardTacheManager.noticeWriteCardResultToAopZJ(order_id);		

		if(!StringUtils.equals(dealResult.getError_code(), EcsOrderConsts.AOP_SUCCESS_0000)){
			CommonTools.addBusiError(dealResult.getError_code(),dealResult.getError_msg());
		}
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		resp.setResponse(dealResult.getResponse());
		return resp;
	}
	
	/**
	 * 写卡数据激活
	 * @throws ApiBusiException 
	 */
	@ZteMethodAnnontion(name="写卡数据激活(写卡环节)",group_name="写卡环节",order="13",page_show=true,path="zteWriteCardTraceRule.SynWriteCardResults")
	public BusiCompResponse SynWriteCardResults(BusiCompRequest cardEesultNotiRequest) throws ApiBusiException{
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = cardEesultNotiRequest.getOrder_id();
		BusiDealResult dealResult = ordWriteCardTacheManager.SynWriteCardResults(order_id);		

		if(!StringUtils.equals(dealResult.getError_code(), EcsOrderConsts.AOP_SUCCESS_0000)){
			CommonTools.addBusiError(dealResult.getError_code(),dealResult.getError_msg());
		}
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		resp.setResponse(dealResult.getResponse());
		return resp;
	}
	/**
	 * 通知BSS写卡结果
	 * @throws ApiBusiException 
	 */
	@ZteMethodAnnontion(name="[BSS]写卡结果通知(写卡环节)",group_name="写卡环节",order="13",page_show=true,path="zteWriteCardTraceRule.WriteCardResultsNoticeBSS")
	public BusiCompResponse WriteCardResultsNoticeBSS(BusiCompRequest cardEesultNotiRequest) throws ApiBusiException{
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = cardEesultNotiRequest.getOrder_id();
		BusiDealResult dealResult = ordWriteCardTacheManager.noticeWriteCardResultToBSS(order_id);		

		if(!StringUtils.equals(dealResult.getError_code(), EcsOrderConsts.AOP_SUCCESS_0000)){
			CommonTools.addBusiError(dealResult.getError_code(),dealResult.getError_msg());
		}
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		resp.setResponse(dealResult.getResponse());
		return resp;
	}
	
	/**
	 * 向AOP同步写卡数据
	 * @throws ApiBusiException 
	 */
	@ZteMethodAnnontion(name="[AOP]同步写卡数据(写卡环节)",group_name="写卡环节",order="14",page_show=true,path="zteWriteCardTraceRule.syncCardDataAop")
	public BusiCompResponse syncCardDataAop(BusiCompRequest busiCompRequest) throws ApiBusiException{
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		BusiDealResult dealResult = ordWriteCardTacheManager.syncCardDataToAop(order_id);
		if(!StringUtils.equals(dealResult.getError_code(), EcsOrderConsts.AOP_SUCCESS_0000)){
			CommonTools.addBusiError(dealResult.getError_code(),dealResult.getError_msg());
		}
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		resp.setResponse(dealResult.getResponse());
		return resp;
	}
	
	/**
	 * 异步向AOP同步写卡数据
	 * @throws ApiBusiException 
	 */
	@ZteMethodAnnontion(name="[AOP]异步同步写卡数据(写卡环节)",group_name="写卡环节",order="14",page_show=true,path="zteWriteCardTraceRule.syncCardDataAopNew")
	public BusiCompResponse syncCardDataAopNew(BusiCompRequest busiCompRequest) throws ApiBusiException{
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		BusiDealResult dealResult = ordWriteCardTacheManager.syncCardDataToAopNew(order_id);
		if(!StringUtils.equals(dealResult.getError_code(), EcsOrderConsts.AOP_SUCCESS_0000)){
			CommonTools.addBusiError(dealResult.getError_code(),dealResult.getError_msg());
		}
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		resp.setResponse(dealResult.getResponse());
		return resp;
	}
	
	/**
	 * 向BSS同步卡数据
	 * @throws ApiBusiException 
	 */
	@ZteMethodAnnontion(name="[BSS]向BSS同步卡数据(写卡环节)",group_name="写卡环节",order="14",page_show=true,path="zteWriteCardTraceRule.writeCardDataSync2BSS")
	public BusiCompResponse writeCardDataSync2BSS(BusiCompRequest busiCompRequest) throws ApiBusiException{
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		BusiDealResult dealResult = ordWriteCardTacheManager.writeCardDataSync2BSS(order_id);
		if(!StringUtils.equals(dealResult.getError_code(), EcsOrderConsts.BUSI_DEAL_RESULT_0000)){
			CommonTools.addBusiError(dealResult.getError_code(),dealResult.getError_msg());
		}
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		resp.setResponse(dealResult.getResponse());
		return resp;
	}
	
	/**
	 * 获取BSS写卡数据
	 * @throws ApiBusiException 
	 */
	@ZteMethodAnnontion(name="[BSS]获取写卡数据(写卡环节)",group_name="写卡环节",order="14",page_show=true,path="zteWriteCardTraceRule.getCardDataFromBSS")
	public BusiCompResponse getCardDataFromBSS(BusiCompRequest busiCompRequest) throws ApiBusiException{
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		BusiDealResult dealResult = ordWriteCardTacheManager.getCardDataFromBSS(order_id);
		if(!StringUtils.equals(dealResult.getError_code(), EcsOrderConsts.BUSI_DEAL_RESULT_0000)){
			CommonTools.addBusiError(dealResult.getError_code(),dealResult.getError_msg());
		}
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		resp.setResponse(dealResult.getResponse());
		return resp;
	}
	
	/**
	 * 多写卡料箱队列分配
	 * @throws ApiBusiException 
	 */
	@ZteMethodAnnontion(name="多写卡料箱队列分配(写卡环节)",group_name="写卡环节",order="19",page_show=true,path="zteWriteCardTraceRule.dealMaterielBox")
	public BusiCompResponse dealMaterielBox(BusiCompRequest busiCompRequest) throws ApiBusiException{
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		
		OrderActionLogReq logReq = new OrderActionLogReq();
		logReq.setAction(EcsOrderConsts.AUTO_WRITE_ORDER_ACTION_03);
		logReq.setAction_desc("进入分配料箱队列");
		logReq.setOrder_id(order_id);
		AutoWriteCardCacheTools.saveWriteCardActiveLog(logReq);
		
		String work_station = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.WORK_STATION);
		if (org.apache.commons.lang3.StringUtils.isEmpty(work_station)) {
			work_station = AutoWriteCardCacheTools.getTempCache(AutoWriteCardCacheTools.TEMP_CACHE_WRITE_STAT_KEY_PREFIX, order_id);
		}
		if(StringUtils.isEmpty(work_station)){
			logger.info("==============订单["+order_id+"]没有分配写卡机位,不触发出队列");
			return resp;
		}		
		BusiDealResult dealResult = ordWriteCardTacheManager.dealMaterielBox(work_station);
		if(!StringUtils.equals(dealResult.getError_code(), EcsOrderConsts.BUSI_DEAL_RESULT_0000)){
			CommonTools.addBusiError(dealResult.getError_code(),dealResult.getError_msg());
		}
		resp.setResponse(dealResult.getResponse());
		return resp;
	}
	
	/**
	 *同步写卡数据到森锐系统(非流水线，批量写卡)
	 * @throws ApiBusiException 
	 */
	@ZteMethodAnnontion(name="同步写卡数据到森锐系统(非流水线，批量写卡)",group_name="写卡环节",order="20",page_show=true,path="ZteWriteCardTraceRule.rgwriteCardDataSynToSenRui")
	public BusiCompResponse writeCardDataSynToSenRuiRG(BusiCompRequest busiCompRequest) throws ApiBusiException{
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		BusiDealResult dealResult = ordWriteCardTacheManager.synWriteCardInfToSRRG(order_id);
		if(!dealResult.getError_code().equals(EcsOrderConsts.BUSI_DEAL_RESULT_0000))
			CommonTools.addBusiError(dealResult.getError_code(),dealResult.getError_msg());
		resp.setResponse(dealResult.getResponse());
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
		
	}
	
	/**
	 *通知森锐退卡、回收卡(非流水线，批量写卡)
	 * @throws ApiBusiException 
	 */
	@ZteMethodAnnontion(name="通知森锐退卡(非流水线，批量写卡)",group_name="写卡环节",order="21",page_show=true,path="ZteWriteCardTraceRule.rgnotifySrBackCard")
	public BusiCompResponse notifySrBackCardRG(BusiCompRequest busiCompRequest) throws ApiBusiException{
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		BusiDealResult dealResult = ordWriteCardTacheManager.refundCardToSRRG(order_id);
		if(!dealResult.getError_code().equals(EcsOrderConsts.BUSI_DEAL_RESULT_0000))
			CommonTools.addBusiError(dealResult.getError_code(),dealResult.getError_msg());
		resp.setResponse(dealResult.getResponse());
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
		
	}	
	
	/**
	 *同步写卡数据到森锐系统(非流水线，批量写卡)
	 * @throws ApiBusiException 
	 */
	@ZteMethodAnnontion(name="[爬虫]同步写卡数据到森锐系统(非流水线，批量写卡)",group_name="写卡环节",order="20",page_show=true,path="ZteWriteCardTraceRule.crawlerWriteCardDataSynToSenRuiRG")
	public BusiCompResponse crawlerWriteCardDataSynToSenRuiRG(BusiCompRequest busiCompRequest) throws ApiBusiException{
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		String machineNo =CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.READID);
		String ICCID =CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ICCID);
		String queue_code="";
		List list=ordWriteCardTacheManager.getAutoOrderList(order_id);
		if(null!=list&&list.size()>0){
    		Map map = (Map)list.get(0);
    		queue_code = map.get("queue_code").toString();
		}
		//标记为写卡中
		PCWriteCardTools.modifyOrderStatus(order_id, EcsOrderConsts.IS_DEFAULT_VALUE, EcsOrderConsts.QUEUE_ORDER_WRITE_1);
		
		//记录正在写卡的订单
		modifyCurrentOrderId(order_id, queue_code, machineNo);
		
    	//记录日志
		OrderQueueCardActionLogReq logReq1 = new OrderQueueCardActionLogReq();
		logReq1.setAction_code(EcsOrderConsts.QUEUE_ACTION_08);
		logReq1.setAction_desc("队列订单准备写卡");
		logReq1.setOrder_id(order_id);
		logReq1.setQueue_code(queue_code);
		logReq1.setIccid(ICCID);
		logReq1.setMachine_no(machineNo);
		saveQueueCardActionLog(logReq1);
		
		BusiDealResult dealResult = ordWriteCardTacheManager.synWriteCardInfToSRRG(order_id);
		if(!dealResult.getError_code().equals(EcsOrderConsts.BUSI_DEAL_RESULT_0000)){
			logger.info("同步写卡数据到森锐系统(非流水线，批量写卡)失败"+order_id+"================="); 
			PCWriteCardTools.queueFailNumsAdd(queue_code, PCWriteCardTools.CARD_QUEUE_FAIL_1);
			CommonTools.addBusiError(dealResult.getError_code(),dealResult.getError_msg());
			
		}else{
			OrderQueueCardActionLogReq logReq = new OrderQueueCardActionLogReq();
			logReq.setAction_code(EcsOrderConsts.QUEUE_ACTION_03);
			logReq.setAction_desc("队列订单写卡完成");
			logReq.setOrder_id(order_id);
			logReq.setQueue_code(queue_code);
			logReq.setIccid(ICCID);
			logReq.setMachine_no(machineNo);
			saveQueueCardActionLog(logReq);
		}
		resp.setResponse(dealResult.getResponse());
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
		
	}
	
	/**
	 *通知森锐退卡、回收卡(非流水线，批量写卡)
	 * @throws ApiBusiException 
	 */
	@ZteMethodAnnontion(name="[爬虫]通知森锐退卡(非流水线，批量写卡)",group_name="写卡环节",order="21",page_show=true,path="ZteWriteCardTraceRule.crawlerNotifySrBackCardRG")
	public BusiCompResponse crawlerNotifySrBackCardRG(BusiCompRequest busiCompRequest) throws ApiBusiException{
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		String machineNo =CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.READID);
		String ICCID =CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ICCID);
		String queue_code="";
		List list=ordWriteCardTacheManager.getAutoOrderList(order_id);
		if(null!=list&&list.size()>0){
    		Map map = (Map)list.get(0);
    		queue_code = map.get("queue_code").toString();
		}
		BusiDealResult dealResult = ordWriteCardTacheManager.refundCardToSRRG(order_id);
		if(!dealResult.getError_code().equals(EcsOrderConsts.BUSI_DEAL_RESULT_0000)){
			logger.info("通知森锐退卡失败"+order_id+"================="); 
			PCWriteCardTools.queueFailNumsAdd(queue_code, PCWriteCardTools.CARD_QUEUE_FAIL_1);
			CommonTools.addBusiError(dealResult.getError_code(),dealResult.getError_msg());
		}else{
			//写卡成功清空连续失败次数
			PCWriteCardTools.queueFailNumsAdd(queue_code, PCWriteCardTools.CARD_QUEUE_FAIL_3);
			//更新写卡时间
			modifyOpenOrWriteDate(order_id, EcsOrderConsts.IS_DEFAULT_VALUE, dateformat.format(new Date()));
			OrderQueueCardActionLogReq logReq = new OrderQueueCardActionLogReq();
			logReq.setAction_code(EcsOrderConsts.QUEUE_ACTION_03);
			logReq.setAction_desc("队列订单退卡完成");
			logReq.setOrder_id(order_id);
			logReq.setQueue_code(queue_code);
			logReq.setIccid(ICCID);
			logReq.setMachine_no(machineNo);
			saveQueueCardActionLog(logReq);
			//写卡成功踢出队列
			archiveWriteCardData(order_id);
		}
		//写卡完成后清空订单ID
		modifyCurrentOrderId("", queue_code, machineNo);
		resp.setResponse(dealResult.getResponse());
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
		
	}	
	
	
	
	/**
	 * [爬虫]ICCID回填到总商
	 * @throws ApiBusiException 
	 */
	@ZteMethodAnnontion(name="[爬虫]ICCID回填到总商(写卡环节)",group_name="写卡环节",order="15",page_show=true,path="zteWriteCardTraceRule.synIccidToZS")
	public BusiCompResponse synIccidToZS(BusiCompRequest busiCompRequest) throws ApiBusiException{
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		BusiDealResult dealResult = ordWriteCardTacheManager.synIccidToZS(order_id);
		if(!StringUtils.equals(dealResult.getError_code(), EcsOrderConsts.BUSI_DEAL_RESULT_0000)){
			CommonTools.addBusiError(dealResult.getError_code(),dealResult.getError_msg(),"1");
		}
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		resp.setResponse(dealResult.getResponse());
		return resp;
	}
	
	/**
	 * [爬虫]调总商获取写卡数据
	 * @throws ApiBusiException 
	 */
	@ZteMethodAnnontion(name="[爬虫]调总商获取写卡数据(写卡环节)",group_name="写卡环节",order="16",page_show=true,path="zteWriteCardTraceRule.getCardDataFromZS")
	public BusiCompResponse getCardDataFromZS(BusiCompRequest busiCompRequest) throws ApiBusiException{
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		BusiDealResult dealResult = ordWriteCardTacheManager.getCardDataFromZS(order_id);
		if(!StringUtils.equals(dealResult.getError_code(), EcsOrderConsts.BUSI_DEAL_RESULT_0000)){
			CommonTools.addBusiError(dealResult.getError_code(),dealResult.getError_msg(),"1");
		}
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		resp.setResponse(dealResult.getResponse());
		return resp;
	}
	
	/**
	 *[爬虫]写卡完成提交订单(写卡环节)
	 * @throws ApiBusiException 
	 */
	@ZteMethodAnnontion(name="[爬虫]写卡完成提交订单(写卡环节)",group_name="写卡环节",order="17",page_show=true,path="ZteWriteCardTraceRule.writeCardFinishSubmitOrderToZS")
	public BusiCompResponse writeCardFinishSubmitOrderToZS(BusiCompRequest busiCompRequest) throws ApiBusiException{
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		BusiDealResult dealResult = ordWriteCardTacheManager.writeCardFinishSubmitOrderToZS(order_id);
		if(!dealResult.getError_code().equals(EcsOrderConsts.ZB_INF_RESP_CODE_0000))
			CommonTools.addBusiError(dealResult.getError_code(),dealResult.getError_msg(),"1");
		resp.setResponse(dealResult.getResponse());
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
		
	}
	/**
	 *[爬虫]写卡确认处理完毕(写卡环节)
	 * @throws ApiBusiException 
	 */
	@ZteMethodAnnontion(name="[爬虫]写卡确认处理完毕(写卡环节)",group_name="写卡环节",order="18",page_show=true,path="ZteWriteCardTraceRule.writeCardConfirmDealFinishToZS")
	public BusiCompResponse writeCardConfirmDealFinishToZS(BusiCompRequest busiCompRequest) throws ApiBusiException{
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		BusiDealResult dealResult = ordWriteCardTacheManager.writeCardConfirmDealFinishToZS(order_id);
		if(!dealResult.getError_code().equals(EcsOrderConsts.ZB_INF_RESP_CODE_0000))
			CommonTools.addBusiError(dealResult.getError_code(),dealResult.getError_msg(),"1");
		resp.setResponse(dealResult.getResponse());
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
		
	}
	
	/**
	 *[AOP]卡数据同步CBSS(写卡环节)
	 * @throws ApiBusiException 
	 */
	@ZteMethodAnnontion(name="[AOP]卡数据同步CBSS（写卡环节）",group_name="写卡环节",order="17",page_show=true,path="ZteWriteCardTraceRule.cardDataSyncCBss")
	public BusiCompResponse cardDataSyncCBss(BusiCompRequest busiCompRequest) throws ApiBusiException{
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		BusiDealResult dealResult = ordWriteCardTacheManager.syncCardDataToCBss(order_id);
		if(!dealResult.getError_code().equals(EcsOrderConsts.ZB_INF_RESP_CODE_0000))
			CommonTools.addBusiError(dealResult.getError_code(),dealResult.getError_msg());
		resp.setResponse(dealResult.getResponse());
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
		
	}
	
	/**
	 *[AOP]卡数据同步CBSS(写卡环节)--浙江
	 * @throws ApiBusiException 
	 */
	@ZteMethodAnnontion(name="[AOP]卡数据同步CBSS（写卡环节）浙江",group_name="写卡环节",order="17",page_show=true,path="ZteWriteCardTraceRule.cardDataSyncCBssZJ")
	public BusiCompResponse cardDataSyncCBssZJ(BusiCompRequest busiCompRequest) throws ApiBusiException{
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		BusiDealResult dealResult = ordWriteCardTacheManager.syncCardDataToCBssZJ(order_id);
		if(!dealResult.getError_code().equals(EcsOrderConsts.ZB_INF_RESP_CODE_0000))
			CommonTools.addBusiError(dealResult.getError_code(),dealResult.getError_msg());
		resp.setResponse(dealResult.getResponse());
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
		
	}
	
	/**
	 * [BSS]开数据同步
	 * @throws ApiBusiException 
	 */
	@ZteMethodAnnontion(name="[BSS]卡数据同步（写卡环节）",group_name="写卡环节",order="14",page_show=true,path="zteActTraceRule.CardDataSyncBss")
	public BusiCompResponse CardDataSyncBss(BusiCompRequest busiCompRequest) throws ApiBusiException{
		this.init();
		BusiCompResponse resp = new BusiCompResponse();
		String order_id = busiCompRequest.getOrder_id();
		BusiDealResult dealResult = ordWriteCardTacheManager.CardDataSyncBss(order_id);
		if(!StringUtils.equals(dealResult.getError_code(), EcsOrderConsts.BUSI_DEAL_RESULT_0000)){
			CommonTools.addBusiError(dealResult.getError_code(),dealResult.getError_msg());
		}
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		resp.setResponse(dealResult.getResponse());
		return resp;
	}
	@ZteMethodAnnontion(name="森锐自动写卡开始",group_name="写卡环节",order="12",page_show=true,path="zteWriteCardTraceRule.srStartAutoWriteCard")
	public BusiCompResponse srStartAutoWriteCard(BusiCompRequest busiCompRequest)throws ApiBusiException{
		BusiCompResponse resp = new BusiCompResponse();
		final String order_id = busiCompRequest.getOrder_id();
		String ICCID =  CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ICCID);
		if(StringUtils.isEmpty(order_id) || StringUtils.isEmpty(ICCID)){
			logger.info("PC批量写卡时order_id["+order_id+"]为空或iccid["+ICCID+"]为空.");
			CommonTools.addError("1", "PC批量写卡时order_id["+order_id+"]为空或iccid["+ICCID+"]为空.");
		}
		OrderTreeBusiRequest orderTree= CommonDataFactory.getInstance().getOrderTree(order_id);
		OrderExtBusiRequest orderExt = orderTree.getOrderExtBusiRequest();
        //爬虫生产模式 
        if(StringUtils.equals(orderExt.getOrder_model(), ZjEcsOrderConsts.ORDER_MODEL_08) 
				&& StringUtils.equals(orderExt.getFlow_trace_id(), EcsOrderConsts.DIC_ORDER_NODE_X)
				&& (StringUtils.equals(orderExt.getIs_aop(), EcsOrderConsts.ACCOUNT_OPEN_CHANNEL_AOP) 
						|| StringUtils.equals(orderExt.getIs_aop(), EcsOrderConsts.ACCOUNT_OPEN_CHANNEL_BSS)) ){
	        //保存写卡机编码 start
        	
        	List list=ordWriteCardTacheManager.getAutoOrderList(order_id);
        	if(null!=list&&list.size()>0){
        		Map map = (Map)list.get(0);
        		String queue_code = map.get("queue_code").toString();
//		        		List<OrderItemProdBusiRequest> prods = orderTree.getOrderItemBusiRequests().get(0).getOrderItemProdBusiRequests();
        		String machineNo=CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.READID);
//		        		if(null!=prods&&prods.size()>0){
//		        			machineNo =prods.get(0).getOrderItemProdExtBusiRequest().getReadId();
//		        		}
//				        String is_write_card = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.IS_WRITE_CARD);
//						if(EcsOrderConsts.IS_DEFAULT_VALUE.equals(is_write_card)){		
//							//已接受写卡机编码
//							CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[]{AttrConsts.READID},new String[]{machineNo});
//						}
				//记录写卡状态
				CommonDataFactory.getInstance().updateAttrFieldValue(order_id, 
						new String[]{AttrConsts.WRITE_CARD_STATUS}, 
						new String[]{EcsOrderConsts.WRITE_CARD_STATUS_1});
				//保存写卡机编码 end
	        	
				//标记为写卡中
				PCWriteCardTools.modifyOrderStatus(order_id, EcsOrderConsts.IS_DEFAULT_VALUE, EcsOrderConsts.QUEUE_ORDER_WRITE_1);
				
				//记录正在写卡的订单
				modifyCurrentOrderId(order_id, queue_code, machineNo);
				
	        	//记录日志
				OrderQueueCardActionLogReq logReq1 = new OrderQueueCardActionLogReq();
				logReq1.setAction_code(EcsOrderConsts.QUEUE_ACTION_08);
				logReq1.setAction_desc("队列订单准备写卡");
				logReq1.setOrder_id(order_id);
				logReq1.setQueue_code(queue_code);
				logReq1.setIccid(ICCID);
				logReq1.setMachine_no(machineNo);
				saveQueueCardActionLog(logReq1);
				
				String ruleid = "";
				if(StringUtils.equals(EcsOrderConsts.ACCOUNT_OPEN_CHANNEL_AOP, orderExt.getIs_aop())){
					ruleid = ZjEcsOrderConsts.ORDER_MODEL_07_ICCID_RULE_ID_AOP;
				}else if(StringUtils.equals(EcsOrderConsts.ACCOUNT_OPEN_CHANNEL_BSS, orderExt.getIs_aop())){
					ruleid = EcsOrderConsts.ORDER_MODEL_06_ICCID_RULE_ID_BSSKL;
				}
				RuleTreeExeReq reqNext = new RuleTreeExeReq();
				reqNext.setRule_id(ruleid);
				TacheFact factNext = new TacheFact();
				factNext.setOrder_id(order_id);
				reqNext.setFact(factNext);
				reqNext.setCheckAllRelyOnRule(true);
				reqNext.setCheckCurrRelyOnRule(true);
				ZteClient client = ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ESC);
				RuleTreeExeResp rsp = client.execute(reqNext, RuleTreeExeResp.class);
				BusiCompResponse busiResp = CommonDataFactory.getInstance().getRuleTreeresult(rsp);
				if(!ConstsCore.ERROR_SUCC.equals(busiResp.getError_code())){
					logger.info("订单["+order_id+"]执行写卡规则失败");
					//写卡失败次数计算
					PCWriteCardTools.queueFailNumsAdd(queue_code, PCWriteCardTools.CARD_QUEUE_FAIL_1);
					//标记为等待回退  标记异常时统一回退
					//PCWriteCardTools.modifyOrderQueueStatus(order_id, EcsOrderConsts.IS_DEFAULT_VALUE);
				}else{
					logger.info("orderorderorder["+order_id+"],error_code["+busiResp.getError_code()+"],error_msg["+busiResp.getError_msg()+"]");
					//写卡成功清空连续失败次数
					PCWriteCardTools.queueFailNumsAdd(queue_code, PCWriteCardTools.CARD_QUEUE_FAIL_3);
					//更新写卡时间
					modifyOpenOrWriteDate(order_id, EcsOrderConsts.IS_DEFAULT_VALUE, dateformat.format(new Date()));
					OrderQueueCardActionLogReq logReq = new OrderQueueCardActionLogReq();
					logReq.setAction_code(EcsOrderConsts.QUEUE_ACTION_03);
					logReq.setAction_desc("队列订单写卡完成");
					logReq.setOrder_id(order_id);
					logReq.setQueue_code(queue_code);
					logReq.setIccid(ICCID);
					logReq.setMachine_no(machineNo);
					saveQueueCardActionLog(logReq);
					//写卡成功踢出队列
					archiveWriteCardData(order_id);
				}

				//写卡完成后清空订单ID
				modifyCurrentOrderId("", queue_code, machineNo);
        		
        	}
        	
	        
        }else{
        	logger.info("订单["+order_id+"]数据不正确["+orderExt.getOrder_model()+","+orderExt.getFlow_trace_id()+","+orderExt.getIs_aop()+"]");
        }
		resp.setError_code("0");
		resp.setError_msg("执行成功");
		return resp;
	}
	 public static void modifyCurrentOrderId(String order_id, String queue_code, String machine_no){
	    	try{
				IDaoSupport support = SpringContextHolder.getBean("baseDaoSupport");
				support.execute("update es_queue_card_mate_manager c set c.curr_order_id = ? where c.queue_code = ? and c.card_mate_code = ? and c.source_from = '"+ManagerUtils.getSourceFrom()+"'", order_id,queue_code,machine_no);
			}catch(Exception e){
				logger.info("更新开户或写卡完成时间失败" + order_id);
				e.printStackTrace();
			}
	}
	 /**
		 * 更新开户或写卡完成时间及状态
		 * @param order_id
		 * @param oper_type 0:开户,1:写卡
		 * @param times
		 */
		public static void modifyOpenOrWriteDate(String order_id, String oper_type, String str_date){
			try{
				IDaoSupport support = SpringContextHolder.getBean("baseDaoSupport");
				if(StringUtils.equals(oper_type, EcsOrderConsts.NO_DEFAULT_VALUE)){
					//开户
					support.execute("update es_queue_write_card c set c.open_account_status = '2', c.open_account_time = to_date(?,'yyyy-mm-dd hh24:mi:ss') where c.order_id = ? and c.source_from = ?", str_date,order_id,ManagerUtils.getSourceFrom());
				}else if(StringUtils.equals(oper_type, EcsOrderConsts.IS_DEFAULT_VALUE)){
					//写卡
					support.execute("update es_queue_write_card c set c.write_card_status = '2',c.write_card_time = to_date(?,'yyyy-mm-dd hh24:mi:ss') where c.order_id = ? and c.source_from = ?", str_date,order_id,ManagerUtils.getSourceFrom());
				}
			}catch(Exception e){
				logger.info("更新开户或写卡完成时间失败" + order_id);
				e.printStackTrace();
			}
		}
		public static void saveQueueCardActionLog(OrderQueueCardActionLogReq logReq){
			try{
				logReq.setSource_from(ManagerUtils.getSourceFrom());			
				TaskThreadPool taskThreadPool = new TaskThreadPool(new Task(logReq) {
					@Override
					public ZteResponse execute(ZteRequest zteRequest) {
						IDaoSupport support = SpringContextHolder.getBean("baseDaoSupport");
						OrderQueueCardActionLogReq req = (OrderQueueCardActionLogReq)zteRequest;
						support.execute("insert into es_queue_card_action_log (ACTION_CODE, ACTION_DESC, ACTION_TIME, ORDER_ID, QUEUE_CODE, ICCID, MACHINE_NO, BATCH_ID, SOURCE_FROM) values (?, ?, sysdate, ?, ?, ?, ?, to_char(sysdate,'yyyymmddhh24'), '"+ManagerUtils.getSourceFrom()+"')", 
								req.getAction_code(),req.getAction_desc(),req.getOrder_id(),req.getQueue_code(),req.getIccid(),req.getMachine_no());
						return null;
					}
				});
				ThreadPoolFactory.getOrderThreadPoolExector().execute(taskThreadPool);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		private final static String QUEUE_TABLE_NAME = "ES_QUEUE_WRITE_CARD";
		private final static String QUEUE_TABLE_NAME_HIS = "ES_QUEUE_WRITE_CARD_HIS";
		private final static SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		/**
		 * 归档写卡成功的队列数据
		 * @param order_id
		 */
		public static void archiveWriteCardData(String order_id){
			try{
				IDaoSupport support = SpringContextHolder.getBean("baseDaoSupport");
				String columns = queryTableColumns(QUEUE_TABLE_NAME);
				if(StringUtils.isNotEmpty(columns)){
					//备份
					String insert_sql = "insert into " + QUEUE_TABLE_NAME_HIS + " ("+columns+") select " + columns + " from " + QUEUE_TABLE_NAME + " where order_id = ? and source_from = ?";
					support.execute(insert_sql, order_id,ManagerUtils.getSourceFrom());
					//删除
					String delete_sql = "delete from " + QUEUE_TABLE_NAME + " where order_id = ? and source_from = ?";
					support.execute(delete_sql, order_id,ManagerUtils.getSourceFrom());
					
					OrderQueueCardActionLogReq logReq = new OrderQueueCardActionLogReq();
					logReq.setAction_code(EcsOrderConsts.QUEUE_ACTION_06);
					logReq.setAction_desc("归档写卡成功的队列数据");
					logReq.setOrder_id(order_id);
					saveQueueCardActionLog(logReq);
				}
			}catch(Exception e){
				logger.info("归档写卡成功的队列数据["+order_id+"]失败");
				e.printStackTrace();
			}
		}
		/**
		 * 获取表字段列表
		 * @param table_name
		 * @return
		 */
		public static String queryTableColumns(String table_name){
			StringBuffer columnsBuff = new StringBuffer();
			try{
				IDaoSupport support = SpringContextHolder.getBean("baseDaoSupport");
				List<Map<String,String>> colList = support.queryForList("select column_name from USER_COL_COMMENTS where table_name = ?",table_name.toUpperCase());
				if(null != colList && colList.size() >= 0){
					for(int i = 0; i < colList.size(); i++){
						if(i != colList.size() - 1){
							columnsBuff.append(colList.get(i).get("column_name")).append(",");
						}else{
							columnsBuff.append(colList.get(i).get("column_name"));
						}
					}
				}
			}catch(Exception e){
				logger.info("获取表结构失败["+table_name+"]");
				e.printStackTrace();
			}
			return columnsBuff.toString();
		}
		@ZteMethodAnnontion(name="PC自动写卡",group_name="写卡环节",order="18",page_show=true,path="ZteWriteCardTraceRule.autoWriteCard")
		public BusiCompResponse autoWriteCard(BusiCompRequest busiCompRequest) throws ApiBusiException{
			BusiCompResponse resp = new BusiCompResponse();
			String order_id = busiCompRequest.getOrder_id();
			BusiDealResult dealResult = ordWriteCardTacheManager.autoWriteCard(order_id);
			if(!dealResult.getError_code().equals(EcsOrderConsts.ZB_INF_RESP_CODE_0000))
				CommonTools.addBusiError(dealResult.getError_code(),dealResult.getError_msg(),"1");
			resp.setResponse(dealResult.getResponse());
			resp.setError_code("0");
			resp.setError_msg("执行成功");
			return resp;
		}
		@ZteMethodAnnontion(name="爬虫写卡完毕",group_name="写卡环节",order="18",page_show=true,path="ZteWriteCardTraceRule.crawlerWriteCardSuc")
		public BusiCompResponse crawlerWriteCardSuc(BusiCompRequest busiCompRequest) throws ApiBusiException{
			BusiCompResponse resp = new BusiCompResponse();
			String order_id = busiCompRequest.getOrder_id();
			BusiDealResult dealResult = ordWriteCardTacheManager.crawlerWriteCardSuc(order_id);
			if(!dealResult.getError_code().equals(EcsOrderConsts.ZB_INF_RESP_CODE_0000))
				CommonTools.addBusiError(dealResult.getError_code(),dealResult.getError_msg(),"1");
			resp.setResponse(dealResult.getResponse());
			resp.setError_code("0");
			resp.setError_msg("执行成功");
			return resp;
		}
}
