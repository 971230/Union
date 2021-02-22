package com.ztesoft.net.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;












import params.ZteRequest;
import params.ZteResponse;
import params.order.req.OrderActionLogReq;
import params.req.CardSubmitInfoReq;
import params.req.CrawlerFreeDesc;
import params.req.CrawlerWriteCardSucReq;
import params.req.GetCardDatasReq;
import params.req.OrderSubmitReq;
import params.req.SubmitOrderReq;
import params.resp.CrawlerFeeInfo;
import params.resp.GetCardDatasResp;
import params.resp.OrderSubmitResp;
import util.ThreadLocalUtil;
import util.Utils;
import zte.net.card.params.req.WriteCardReq;
import zte.net.card.params.req.WriteQueueReq;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.InfConst;
import zte.net.ecsord.common.SpecConsts;
import zte.net.ecsord.params.base.req.BusiCompRequest;
import zte.net.ecsord.params.base.resp.BusiCompResponse;
import zte.net.ecsord.params.busi.req.AttrFeeInfoBusiRequest;
import zte.net.ecsord.params.busi.req.AttrGiftInfoBusiRequest;
import zte.net.ecsord.params.busi.req.OrderExtBusiRequest;
import zte.net.ecsord.params.busi.req.OrderItemBusiRequest;
import zte.net.ecsord.params.busi.req.OrderItemExtBusiRequest;
import zte.net.ecsord.params.busi.req.OrderItemProdBusiRequest;
import zte.net.ecsord.params.busi.req.OrderItemProdExtBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;
import zte.net.ecsord.params.busi.req.OrderWMSKeyInfoBusiRequest;
import zte.net.ecsord.params.ecaop.req.BSSGetCardMsgReq;
import zte.net.ecsord.params.ecaop.req.BSSWriteCardResultNoticeReq;
import zte.net.ecsord.params.ecaop.req.CardDataQryRequest;
import zte.net.ecsord.params.ecaop.req.CardDataSynRequest;
import zte.net.ecsord.params.ecaop.req.CardDataSynRequestZJ;
import zte.net.ecsord.params.ecaop.req.GetBlanckCardDataReq;
import zte.net.ecsord.params.ecaop.req.SynCardDataReq;
import zte.net.ecsord.params.ecaop.req.WriteCardPreReq;
import zte.net.ecsord.params.ecaop.req.WriteCardResultNoticeReq;
import zte.net.ecsord.params.ecaop.req.WriteCardResultNoticeReqZJ;
import zte.net.ecsord.params.ecaop.resp.BSSGetCardMsgRsp;
import zte.net.ecsord.params.ecaop.resp.BSSWriteCardResultNoticeResp;
import zte.net.ecsord.params.ecaop.resp.CardDataQryResponse;
import zte.net.ecsord.params.ecaop.resp.CardDataSynResponse;
import zte.net.ecsord.params.ecaop.resp.GetBlanckCardDataRsp;
import zte.net.ecsord.params.ecaop.resp.SynCardDataRsp;
import zte.net.ecsord.params.ecaop.resp.WriteCardPreRsp;
import zte.net.ecsord.params.ecaop.resp.WriteCardResultNoticeResp;
import zte.net.ecsord.params.sr.req.ReadICCIDSRRequset;
import zte.net.ecsord.params.sr.req.RevertCardRGRequset;
import zte.net.ecsord.params.sr.req.RevertCardRequset;
import zte.net.ecsord.params.sr.req.WriteICCIDSRRGRequset;
import zte.net.ecsord.params.sr.req.WriteICCIDSRRequset;
import zte.net.ecsord.params.sr.resp.ReadICCIDSRResponse;
import zte.net.ecsord.params.sr.resp.RevertCardRGResponse;
import zte.net.ecsord.params.sr.resp.RevertCardResponse;
import zte.net.ecsord.params.sr.resp.WriteICCIDSRRGResponse;
import zte.net.ecsord.params.sr.resp.WriteICCIDSRResponse;
import zte.net.ecsord.params.sr.vo.RecycleCardResp;
import zte.net.ecsord.params.sr.vo.WritCardResp;
import zte.net.ecsord.params.wms.req.NotifyOrderStatusToWMSReq;
import zte.net.ecsord.params.wms.req.NotifyWriteCardResultWMSReq;
import zte.net.ecsord.params.wms.req.SynCardInfoWMSReq;
import zte.net.ecsord.params.wms.resp.NotifyOrderStatusToWMSResp;
import zte.net.ecsord.params.wms.resp.NotifyWriteCardResultWMSResp;
import zte.net.ecsord.params.wms.vo.SynCardInfoGoodInfoVo;
import zte.net.ecsord.params.wms.vo.SynCardInfoOrderInfoVo;
import zte.net.ecsord.params.writecard.MachinesGroup;
import zte.net.ecsord.params.writecard.MachinesModel;
import zte.net.ecsord.params.writecard.MaterielBox;
import zte.net.ecsord.params.writecard.MaterielBoxOrder;
import zte.net.ecsord.params.wyg.req.StatuSynchReq;
import zte.net.ecsord.params.wyg.resp.StatuSynchResp;
import zte.net.ecsord.params.zb.req.NotifyWriteCardInfoRequest;
import zte.net.ecsord.params.zb.req.QryCRMInfo2CardRequest;
import zte.net.ecsord.params.zb.resp.NotifyWriteCardInfoResponse;
import zte.net.ecsord.params.zb.resp.QryCRMInfo2CardResponse;
import zte.net.ecsord.rule.tache.TacheFact;
import zte.net.ecsord.utils.AutoWriteCardCacheTools;
import zte.net.ecsord.utils.SpecUtils;
import zte.net.params.req.RuleTreeExeReq;
import zte.net.params.resp.RuleTreeExeResp;

import com.ztesoft.api.ApiBusiException;
import com.ztesoft.api.AppKeyEnum;
import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.Task;
import com.ztesoft.api.TaskThreadPool;
import com.ztesoft.api.ThreadPoolFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.common.util.StringUtils;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.net.ecsord.params.ecaop.req.CardDataSyncBssReq;
import com.ztesoft.net.ecsord.params.ecaop.resp.CardDataSyncBssResp;
import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.consts.ZjEcsOrderConsts;
import com.ztesoft.net.mall.core.consts.ZjEcsOrderConsts;
import com.ztesoft.net.mall.core.model.CoQueue;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.model.BusiDealResult;
import com.ztesoft.net.service.IOrdArchiveTacheManager;
import com.ztesoft.net.service.IOrdWriteCardTacheManager;

import consts.ConstsCore;

/**
 * 写卡环节处理类
 * @author xuefeng
 */
public class OrdWriteCardTacheManager extends BaseSupport implements IOrdWriteCardTacheManager {
	@Resource
	private IOrdArchiveTacheManager ordArchiveTacheManager;
	private static Map mutexLocks = Collections.synchronizedMap(new HashMap());
    public static  Map getMutexLock(String lockName) {
    	HashMap lock = (HashMap) mutexLocks.get(lockName);
        if (lock == null) {
            lock = new HashMap();
            mutexLocks.put(lockName, lock);
        }
        return lock;
    }
    public static  void removeMutexLock(String lockName) {
        mutexLocks.remove(lockName);
    }
	
	@Override
	public BusiDealResult recWriteCardInf(BusiCompRequest busiCompRequest) {
		BusiDealResult result = new BusiDealResult();
		// 获取规则对象
		Map params = busiCompRequest.getQueryParams();
		TacheFact fact = (TacheFact) params.get("fact");
		SynCardInfoWMSReq req = (SynCardInfoWMSReq)fact.getRequest();
		String order_id = fact.getOrder_id();
		String readId = ""; 
		if(null != req){
			SynCardInfoOrderInfoVo orderInfo = req.getOrderInfo();
			List<SynCardInfoGoodInfoVo> goodInfos = orderInfo.getGoodInfo();
			List<OrderItemProdBusiRequest> prods = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderItemBusiRequests().get(0).getOrderItemProdBusiRequests();
			List<AttrGiftInfoBusiRequest> gifts = CommonDataFactory.getInstance().getOrderTree(order_id).getGiftInfoBusiRequests();
			for(SynCardInfoGoodInfoVo goodInfo : goodInfos){
				readId = goodInfo.getReadId();
				for(OrderItemProdBusiRequest prod:prods){
					if(goodInfo.getOrderProductId().equals(prod.getProd_spec_goods_id())){
						OrderItemProdExtBusiRequest prodExt = prod.getOrderItemProdExtBusiRequest();
						prodExt.setReadId(goodInfo.getReadId());
						prodExt.setDb_action(ConstsCore.DB_ACTION_UPDATE);
						prodExt.setIs_dirty(ConstsCore.IS_DIRTY_YES);
						prodExt.store();
					}
				}
				for(AttrGiftInfoBusiRequest gift : gifts){
					if(goodInfo.getOrderProductId().equals(gift.getGift_inst_id())){
						gift.setReadid(goodInfo.getReadId());
						gift.setDb_action(ConstsCore.DB_ACTION_UPDATE);
						gift.setIs_dirty(ConstsCore.IS_DIRTY_YES);
						gift.store();
					}
				}
			}
			String is_write_card = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.IS_WRITE_CARD);
			if(EcsOrderConsts.IS_DEFAULT_VALUE.equals(is_write_card)){		
				//已接受写卡机编码
				CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[]{AttrConsts.READID},new String[]{readId});
			}
			logger.info("\n接收WMS写卡机编号："+readId);
		}else {
			ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
			String is_run = cacheUtil.getConfigInfo(EcsOrderConsts.ORDER_FLOW_NODE_REC_WCN);
			if(EcsOrderConsts.ORDER_FLOW_RUN.equals(is_run)){
				readId = "11111111111111";
			}else {
				result.setError_code("-99999");
				return result;
			}
			String is_write_card = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.IS_WRITE_CARD);
			if(EcsOrderConsts.IS_DEFAULT_VALUE.equals(is_write_card)){		
				//已接受写卡机编码
				CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[]{AttrConsts.READID},new String[]{readId});
			}
		}
		//记录写卡状态
		CommonDataFactory.getInstance().updateAttrFieldValue(order_id, 
				new String[]{AttrConsts.WRITE_CARD_STATUS}, 
				new String[]{EcsOrderConsts.WRITE_CARD_STATUS_1});
		
		return result;
	}

	@Override
	public BusiDealResult getICCIDInf(String order_id) {
		BusiDealResult result = new BusiDealResult();
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils
				.getSourceFrom());
		ReadICCIDSRRequset req = new ReadICCIDSRRequset();
		req.setNotNeedReqStrOrderId(order_id);
		ReadICCIDSRResponse infResp = client.execute(req, ReadICCIDSRResponse.class);
		
		if(!EcsOrderConsts.SR_INF_SUCC_CODE.equals(infResp.getSr_response().getHead().getCode())){
//			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, 
//					new String[]{AttrConsts.ICCID}, new String[]{infResp.getSr_response().getBody().getCardNo()});//防止空指针
//			logger.info("\n从森锐读取ICCID：" + infResp.getSr_response().getBody().getCardNo());//防止空指针
			result.setError_msg("错误编码：" + infResp.getError_code() + ";错误信息：" + infResp.getError_msg());
			result.setError_code(infResp.getError_code());
			
			//获取ICCID失败，标记异常
			Map params = new HashMap();
			BusiCompRequest bcr = new BusiCompRequest();
			bcr.setEnginePath("zteCommonTraceRule.markedException");
			bcr.setOrder_id(order_id);
			params.put(EcsOrderConsts.EXCEPTION_FROM, EcsOrderConsts.EXCEPTION_FROM_ORD);
			bcr.setQueryParams(params);
			CommonDataFactory.getInstance().execBusiComp(bcr);
			
		}else{
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, 
					new String[]{AttrConsts.ICCID}, new String[]{infResp.getSr_response().getBody().getCardNo()});
			//已读取卡数据
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, 
					new String[]{AttrConsts.SR_STATUS,AttrConsts.WRITE_CARD_STATUS}, 
					new String[]{EcsOrderConsts.SR_STATUS_0,EcsOrderConsts.WRITE_CARD_STATUS_2});
			//记录多写卡获取ICCID操作日志
			OrderActionLogReq logReq = new OrderActionLogReq();
			logReq.setAction(EcsOrderConsts.AUTO_WRITE_ORDER_ACTION_08);
			logReq.setAction_desc(infResp.getSr_response().getBody().getCardNo());
			logReq.setOrder_id(order_id);
			AutoWriteCardCacheTools.saveWriteCardActiveLog(logReq);
		}
		result.setResponse(infResp);
		return result;
	}

	@Override
	public BusiDealResult getWriteCardInfFromZB(String order_id) {
		BusiDealResult result = new BusiDealResult();
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils
				.getSourceFrom());
		
		QryCRMInfo2CardRequest req = new QryCRMInfo2CardRequest();
		req.setNotNeedReqStrOrderId(order_id);
		QryCRMInfo2CardResponse infResp = client.execute(req, QryCRMInfo2CardResponse.class);
		//记录写卡数据,暂时没有字段
		if(!infResp.getRespCode().equals(EcsOrderConsts.ZB_INF_RESP_CODE_0000)){
			result.setError_msg("错误编码：" + infResp.getRespCode() + ";错误信息：" + infResp.getRespDesc());
			result.setError_code(infResp.getRespCode());
			//标记异常
			String orderMode = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest().getOrder_model();
			
			//自动化标记异常
			Map params = new HashMap();
			BusiCompRequest bcr = new BusiCompRequest();
			bcr.setEnginePath("zteCommonTraceRule.markedException");
			bcr.setOrder_id(order_id);
			params.put(EcsOrderConsts.EXCEPTION_FROM, EcsOrderConsts.EXCEPTION_FROM_ORD);
			if(EcsOrderConsts.OPER_MODE_ZD.equals(orderMode)){
				params.put(EcsOrderConsts.NOTIFY_SR_DK, EcsOrderConsts.NOTIFY_SR_DK_VAL);
			}
			params.put(EcsOrderConsts.EXCEPTION_REMARK, infResp.getRespDesc()); //异常原因传递
			bcr.setQueryParams(params);
			CommonDataFactory.getInstance().execBusiComp(bcr);
			
		}else{
			logger.info("\n从总部获取写卡数据："+infResp.getScriptSeq());
			//保存IMSI,写卡脚本
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[]{AttrConsts.SIMID,AttrConsts.SCRIPT_SEQ,AttrConsts.WRITE_CARD_STATUS}, new String[]{infResp.getIMSI(),infResp.getScriptSeq(),EcsOrderConsts.WRITE_CARD_STATUS_3});
			//获取写卡数据，将此数据通知给新商城
			StatuSynchReq statuSyn = new StatuSynchReq(order_id,EcsOrderConsts.DIC_ORDER_NODE_X,EcsOrderConsts.DIC_ORDER_NODE_X_DESC,EcsOrderConsts.IS_WRITE_CARD_YES,infResp.getIMSI(),infResp.getScriptSeq());
			CommonDataFactory.getInstance().notifyNewShop(statuSyn);
		}
		result.setResponse(infResp);
		return result;
	}
	@Override
	public BusiDealResult getBlankCardInf(String order_id)  throws ApiBusiException{
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils
				.getSourceFrom());
		BusiDealResult result = new BusiDealResult();
		OrderTreeBusiRequest order_tree = CommonDataFactory.getInstance().getOrderTree(order_id);
		   String phone_num = order_tree.getOrderItemBusiRequests().get(0).getOrderItemExtBusiRequest().getPhone_num();
		   String order_city_code = order_tree.getOrderExtBusiRequest().getOrder_city_code();
		   String iccid = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ICCID);
		   String out_operator = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.OUT_OPERATOR);
		   String out_office = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.OUT_OFFICE);
		   GetBlanckCardDataReq req = new GetBlanckCardDataReq();
		   req.setService_num(phone_num);
		   req.setRegion_id(order_city_code);
		   req.setIccid(iccid);
		   req.setOffice_id(out_office);
		   req.setOperator_id(out_operator);
		   GetBlanckCardDataRsp infResp = client.execute(req, GetBlanckCardDataRsp.class);
		//记录写卡数据,暂时没有字段
		if(!infResp.getCode().equals("00000")){
			result.setError_msg("错误编码：" + infResp.getCode() + ";错误信息：" + infResp.getMsg());
			result.setError_code(infResp.getCode());
			//标记异常
			String orderMode = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest().getOrder_model();
			
			//自动化标记异常
			Map params = new HashMap();
			BusiCompRequest bcr = new BusiCompRequest();
			bcr.setEnginePath("zteCommonTraceRule.markedException");
			bcr.setOrder_id(order_id);
			params.put(EcsOrderConsts.EXCEPTION_FROM, EcsOrderConsts.EXCEPTION_FROM_ORD);
			if(EcsOrderConsts.OPER_MODE_ZD.equals(orderMode)){
				params.put(EcsOrderConsts.NOTIFY_SR_DK, EcsOrderConsts.NOTIFY_SR_DK_VAL);
			}
			params.put(EcsOrderConsts.EXCEPTION_REMARK, infResp.getMsg()); //异常原因传递
			bcr.setQueryParams(params);
			CommonDataFactory.getInstance().execBusiComp(bcr);
			
		}else{
			logger.info("\n获取白卡卡数据："+infResp.getResp().toString());
			//保存IMSI,写卡脚本
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[]{AttrConsts.SIMID,AttrConsts.SCRIPT_SEQ,AttrConsts.WRITE_CARD_STATUS}, new String[]{infResp.getRsp().getImsi(),infResp.getRsp().getScriptseq(),EcsOrderConsts.WRITE_CARD_STATUS_3});
			//获取写卡数据，将此数据通知给新商城
			StatuSynchReq statuSyn = new StatuSynchReq(order_id,EcsOrderConsts.DIC_ORDER_NODE_X,EcsOrderConsts.DIC_ORDER_NODE_X_DESC,EcsOrderConsts.IS_WRITE_CARD_YES,infResp.getRsp().getImsi(),infResp.getRsp().getScriptseq());
			CommonDataFactory.getInstance().notifyNewShop(statuSyn);
		}
		result.setResponse(infResp);
		return result;
	}
	@Override
	public BusiDealResult notifyWYG(StatuSynchReq statuSyn) {
		
		BusiDealResult result = new BusiDealResult();
		if(StringUtil.isEmpty(statuSyn.getDEAL_DESC()) || StringUtil.isEmpty(statuSyn.getORDER_ID()))
			return result;
		
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils
				.getSourceFrom());
		StatuSynchResp resp = client.execute(statuSyn, StatuSynchResp.class);
		if(!resp.getResp_code().equals(EcsOrderConsts.BUSI_DEAL_RESULT_0000)){
			result.setError_msg("错误编码：" + resp.getResp_code() + ";错误信息：" + resp.getResp_msg());
			result.setError_code(resp.getResp_code());
		}else{
			/*CommonDataFactory.getInstance().updateAttrFieldValue(statuSyn.getORDER_ID(), 
					new String[]{AttrConsts.WYG_STATUS}, new String[]{EcsOrderConsts.WYG_STATUS_1});*/
			result.setError_msg("状态通知成功！");
			result.setError_code(EcsOrderConsts.BUSI_DEAL_RESULT_0000);			
		}
		result.setResponse(resp);
		return result;
	}
	
	@Override
	public BusiDealResult synWriteCardInfToSR(String order_id) {
		BusiDealResult result = new BusiDealResult();
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils
				.getSourceFrom());
		
		WriteICCIDSRRequset req = new WriteICCIDSRRequset();
		req.setNotNeedReqStrOrderId(order_id);
		WriteICCIDSRResponse infResp = client.execute(req, WriteICCIDSRResponse.class);
		WritCardResp srResp = infResp.getSr_response();
		if(EcsOrderConsts.SR_INF_SUCC_CODE.equals(srResp.getBody().getResult())){//接口返回成功即是写卡成功
			OrderExtBusiRequest orderExtBusiReq = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest();
			orderExtBusiReq.setWrite_card_result(EcsOrderConsts.WRITE_CARD_STATUS_SUCCESS);
			orderExtBusiReq.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			orderExtBusiReq.setDb_action(ConstsCore.DB_ACTION_UPDATE);
			orderExtBusiReq.store();
			AutoWriteCardCacheTools.setTempCache(AutoWriteCardCacheTools.TEMP_CACHE_WRITE_S_KEY_PREFIX, order_id);
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, 
					new String[]{AttrConsts.SR_STATUS,AttrConsts.WRITE_CARD_RESULT}, new String[]{EcsOrderConsts.SR_STATUS_1,EcsOrderConsts.WRITE_CARD_STATUS_SUCCESS});
			//记录多写卡获取写卡机位操作日志
			OrderActionLogReq logReq = new OrderActionLogReq();
			logReq.setAction(EcsOrderConsts.AUTO_WRITE_ORDER_ACTION_10);
			logReq.setAction_desc("同步写卡数据到写卡机");
			logReq.setOrder_id(order_id);
			AutoWriteCardCacheTools.saveWriteCardActiveLog(logReq);
		}else {
			result.setError_msg("错误编码：" + srResp.getHead().getCode() + ";错误信息：" + srResp.getHead().getMessage());
			OrderExtBusiRequest orderExtBusiReq = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest();
			orderExtBusiReq.setWrite_card_result(EcsOrderConsts.WRITE_CARD_STATUS_EXCEPTION);
			orderExtBusiReq.setError_comments(srResp.getHead().getMessage());
			orderExtBusiReq.setWrite_card_status(EcsOrderConsts.WRITE_CARD_STATUS_EXCEPTION);
			orderExtBusiReq.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			orderExtBusiReq.setDb_action(ConstsCore.DB_ACTION_UPDATE);
			orderExtBusiReq.store();
			//标记异常
			Map params = new HashMap();
			BusiCompRequest bcr = new BusiCompRequest();
			bcr.setEnginePath("zteCommonTraceRule.markedException");
			bcr.setOrder_id(order_id);
			params.put(EcsOrderConsts.EXCEPTION_FROM, EcsOrderConsts.EXCEPTION_FROM_ORD);
			params.put(EcsOrderConsts.NOTIFY_SR_DK, EcsOrderConsts.NOTIFY_SR_DK_VAL);
			bcr.setQueryParams(params);
			CommonDataFactory.getInstance().execBusiComp(bcr);
			//记录多写卡获取写卡机位操作日志
			OrderActionLogReq logReq = new OrderActionLogReq();
			logReq.setAction(EcsOrderConsts.AUTO_WRITE_ORDER_ACTION_11);
			logReq.setAction_desc("写卡异常");
			logReq.setOrder_id(order_id);
			AutoWriteCardCacheTools.saveWriteCardActiveLog(logReq);
		}
		result.setError_code(srResp.getHead().getCode());
		result.setResponse(infResp);
		return result;
	}

	@Override
	public BusiDealResult synWriteCardInfToZB(String order_id) {
		BusiDealResult result = new BusiDealResult();
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils
				.getSourceFrom());
		NotifyWriteCardInfoRequest req = new NotifyWriteCardInfoRequest();
		req.setNotNeedReqStrOrderId(order_id);
		NotifyWriteCardInfoResponse infResp = client.execute(req, NotifyWriteCardInfoResponse.class);
		if(!infResp.getRespCode().equals(EcsOrderConsts.ZB_INF_RESP_CODE_0000)){
			result.setError_msg("错误编码：" + infResp.getRespCode() + ";错误信息：" + infResp.getRespDesc());
			result.setError_code(infResp.getRespCode());
			
			//记录通知结
			OrderExtBusiRequest orderExtBusi = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest();
			orderExtBusi.setCol3(EcsOrderConsts.WRITE_CARD_ZB_FAIL);
			orderExtBusi.setDb_action(ConstsCore.DB_ACTION_UPDATE);
			orderExtBusi.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			orderExtBusi.store();

			String orderMode = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest().getOrder_model();
			Map params = new HashMap();
			BusiCompRequest bcr = new BusiCompRequest();
			bcr.setEnginePath("zteCommonTraceRule.markedException");
			bcr.setOrder_id(order_id);
			params.put(EcsOrderConsts.EXCEPTION_FROM, EcsOrderConsts.EXCEPTION_FROM_ORD);
			bcr.setQueryParams(params);
			CommonDataFactory.getInstance().execBusiComp(bcr);
		}else {
			//记录通知结果
			OrderExtBusiRequest orderExtBusi = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest();
			orderExtBusi.setCol3(EcsOrderConsts.WRITE_CARD_ZB_SUCC);
			orderExtBusi.setDb_action(ConstsCore.DB_ACTION_UPDATE);
			orderExtBusi.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			orderExtBusi.store();
			
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, 
					new String[]{AttrConsts.ZB_STATUS, AttrConsts.WRITE_CARD_STATUS}, 
					new String[]{EcsOrderConsts.ZB_ORDER_STATE_N07, EcsOrderConsts.WRITE_CARD_STATUS_5});
			
			List<AttrGiftInfoBusiRequest> giftInfoList = CommonDataFactory.getInstance().getOrderTree(order_id).getGiftInfoBusiRequests();
			for(AttrGiftInfoBusiRequest giftInfo : giftInfoList){
				String bss_status = giftInfo.getBss_status();
				if(EcsOrderConsts.GIFT_BSS_STATUS_1.equals(bss_status)||EcsOrderConsts.GIFT_BSS_STATUS_3.equals(bss_status)){
					logger.info("更新订单bss办理状态，id："+order_id+"  ");
					CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[]{AttrConsts.BSS_STATUS}, new String[]{EcsOrderConsts.BSS_STATUS_0});
					break;
				}
			}
			
			//获取写卡数据，将此数据通知给新商城
			//4G场景：收到写卡通知总部结果，不同3G要走BSS业务办理，模拟BSS办理成功。
			/*StatuSynchReq statuSyn = new StatuSynchReq(order_id,EcsOrderConsts.DIC_ORDER_NODE_Y,EcsOrderConsts.DIC_ORDER_NODE_Y_DESC,EcsOrderConsts.BSS_STATUS_1,EcsOrderConsts.BSS_STATUS_1_DESC,"");
			CommonDataFactory.getInstance().notifyNewShop(statuSyn);*/
			
		}
		result.setResponse(infResp);
		return result;
	}

	@Override
	public BusiDealResult synWriteCardInfoToWMS(String order_id, String status) {
		BusiDealResult result = new BusiDealResult();
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils
				.getSourceFrom());
		NotifyWriteCardResultWMSReq req = new NotifyWriteCardResultWMSReq();
		req.setOrderId(order_id);
		req.setNotNeedReqStrStatus(status);
		NotifyWriteCardResultWMSResp infResp = client.execute(req, NotifyWriteCardResultWMSResp.class);
		//记录WMS状态
		CommonDataFactory.getInstance().updateAttrFieldValue(order_id, 
				new String[]{AttrConsts.WMS_STATUS}, new String[]{EcsOrderConsts.WMS_STATUS_7});
		if(!infResp.getErrorCode().equals(EcsOrderConsts.WMS_INF_RESP_CODE_0000)){
			result.setError_msg("错误编码：" + infResp.getErrorCode() + ";错误信息：" + infResp.getErrorMessage());
			result.setError_code(infResp.getErrorCode());
			//标记异常
			Map params = new HashMap();
			BusiCompRequest bcr = new BusiCompRequest();
			bcr.setEnginePath("zteCommonTraceRule.markedException");
			bcr.setOrder_id(order_id);
			params.put(EcsOrderConsts.EXCEPTION_FROM, EcsOrderConsts.EXCEPTION_FROM_ORD);
			bcr.setQueryParams(params);
			CommonDataFactory.getInstance().execBusiComp(bcr);
		}else {
			OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
			OrderWMSKeyInfoBusiRequest orderWmsKey = orderTree.getOrderWMSKeyInfoBusiRequest();
			String readId = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.READID);
			
			//通知WMS物理写卡完成才更新状态
			if(EcsOrderConsts.ORDER_STATUS_WRITE_CARD_2.equals(status)){
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, 
					new String[]{AttrConsts.WRITE_CARD_STATUS}, 
					new String[]{EcsOrderConsts.WRITE_CARD_STATUS_4});
			}
			AutoWriteCardCacheTools.removeArriveWorkStationOrder(orderWmsKey.getWrite_machine_code());
			
			if(EcsOrderConsts.ORDER_STATUS_WRITE_CARD_2.equals(status)){
				OrderWMSKeyInfoBusiRequest wmsBusiReq = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderWMSKeyInfoBusiRequest();
				wmsBusiReq.setNotify_time("sysdate");
				wmsBusiReq.setDb_action(ConstsCore.DB_ACTION_UPDATE);
				wmsBusiReq.setIs_dirty(ConstsCore.IS_DIRTY_YES);
				wmsBusiReq.store();
				
				//记录多写卡获取写卡机位操作日志
				OrderActionLogReq logReq = new OrderActionLogReq();
				logReq.setAction(EcsOrderConsts.AUTO_WRITE_ORDER_ACTION_14);
				logReq.setAction_desc("物理写卡完成通知WMS");
				logReq.setWrite_station_code(orderWmsKey.getWrite_machine_code());
				logReq.setWrite_machine_code(readId);
				logReq.setOrder_id(order_id);
				AutoWriteCardCacheTools.saveWriteCardActiveLog(logReq);
			}
		}
		result.setResponse(infResp);
		return result;
	}
	
	@Override
	public BusiDealResult refundCardToSR(String order_id) {
		String lock_name ="SR-REVERTCARD" + order_id;
		Map lock = getMutexLock(lock_name);
		synchronized (lock) {
			BusiDealResult result = new BusiDealResult();
			//设置缓存锁
			String flag_lock = String.valueOf((int)(Math.random()*999999)) + String.valueOf(System.currentTimeMillis());
			String lock_value = order_id+flag_lock;
			boolean f = AutoWriteCardCacheTools.addCacheLock(AutoWriteCardCacheTools.OUTCARD_CACHE_LOCK_KEY_PREFIX + order_id , lock_value);
			if(!f){
				//已有缓存锁，有进程正在处理退卡
				removeMutexLock(lock_name);
				result.setError_msg("已有缓存锁，有进程正在处理退卡-1");
				result.setError_code("-9999");
				return result;
			}
			try {
				String tmp = AutoWriteCardCacheTools.getCacheLock(AutoWriteCardCacheTools.OUTCARD_CACHE_LOCK_KEY_PREFIX + order_id);
				if (tmp != null && !"".equals(tmp)) {
					if (!(lock_value).equals(tmp)) { // 如果不是当前线程加的锁，则退出
						//已有缓存锁，有进程正在处理退卡
						removeMutexLock(lock_name);
						result.setError_msg("已有缓存锁，有进程正在处理退卡-2");
						result.setError_code("-9999");
						return result;
					}
				} else {
					Thread.sleep(2000l); // 休眠2秒后再取出锁进行比较
					tmp = AutoWriteCardCacheTools.getCacheLock(AutoWriteCardCacheTools.OUTCARD_CACHE_LOCK_KEY_PREFIX + order_id);
					if (!(lock_value).equals(tmp)) { // 如果不是当前线程加的锁，则退出
						//已有缓存锁，有进程正在处理退卡
						removeMutexLock(lock_name);
						result.setError_msg("已有缓存锁，有进程正在处理退卡-3");
						result.setError_code("-9999");
						return result;
					}
				}
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			
		
		try {
			ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils
					.getSourceFrom());
			RevertCardRequset req = new RevertCardRequset();
			req.setNotNeedReqStrOrderId(order_id);
			req.setRequest(InfConst.SR_OUT_CARD);
			RevertCardResponse infResp = client.execute(req, RevertCardResponse.class);
			RecycleCardResp srResp = infResp.getSr_response();
			if(!EcsOrderConsts.SR_INF_SUCC_CODE.equals(srResp.getBody().getResult())){
				result.setError_msg("错误编码：" + srResp.getHead().getCode() + ";错误信息：" + srResp.getHead().getMessage());
				result.setError_code(srResp.getHead().getCode());
				//标记异常
				Map params = new HashMap();
				BusiCompRequest bcr = new BusiCompRequest();
				bcr.setEnginePath("zteCommonTraceRule.markedException");
				bcr.setOrder_id(order_id);
				params.put(EcsOrderConsts.EXCEPTION_FROM, EcsOrderConsts.EXCEPTION_FROM_ORD);
				params.put(EcsOrderConsts.NOTIFY_SR_DK, EcsOrderConsts.NOTIFY_SR_DK_VAL);
				bcr.setQueryParams(params);
				CommonDataFactory.getInstance().execBusiComp(bcr);
			}else{
				OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
				String readId = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.READID);
				String workStation = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.WORK_STATION);
				if (StringUtils.isEmpty(workStation)) workStation = AutoWriteCardCacheTools.getTempCache(AutoWriteCardCacheTools.TEMP_CACHE_WRITE_STAT_KEY_PREFIX, order_id);
					
				// 更新写卡机状态为空闲
				MachinesModel mm = new MachinesModel();
				mm.setWorkStation(workStation);
				mm.setKey(readId);
				mm.setValue("");
				AutoWriteCardCacheTools.modifyMachineStatus(mm);
					
				//记录多写卡退卡操作日志
				OrderActionLogReq logReq = new OrderActionLogReq();
				logReq.setAction(EcsOrderConsts.AUTO_WRITE_ORDER_ACTION_12);
				logReq.setAction_desc("退卡");
				logReq.setWrite_station_code(workStation);
				logReq.setWrite_machine_code(readId);
				logReq.setOrder_id(order_id);
				AutoWriteCardCacheTools.saveWriteCardActiveLog(logReq);
					
				OrderWMSKeyInfoBusiRequest wmsBusiReq = orderTree.getOrderWMSKeyInfoBusiRequest();
				wmsBusiReq.setIs_revert(EcsOrderConsts.IS_DEFAULT_VALUE);
				wmsBusiReq.setRevert_time("sysdate");
				wmsBusiReq.setDb_action(ConstsCore.DB_ACTION_UPDATE);
				wmsBusiReq.setIs_dirty(ConstsCore.IS_DIRTY_YES);
				wmsBusiReq.store();
				String [] name={AttrConsts.SR_STATUS};
				String [] value={EcsOrderConsts.WRITE_CARD_STATUS_6};
				CommonDataFactory.getInstance().updateAttrFieldValue(order_id,name,value);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setError_msg(e.getMessage());
			result.setError_code("-9999");
		} finally {
			removeMutexLock(lock_name);
			/** 不清除缓存锁,让缓存锁到期自己失效(因为写卡完成、料箱到达两个并发的时候,有可能第一次执行完了,第二次刚进来)
			AutoWriteCardCacheTools.removeCacheLock(AutoWriteCardCacheTools.OUTCARD_CACHE_LOCK_KEY_PREFIX + order_id);
			*/
		}
		return result;
		}
	}

	@Override
	public BusiDealResult refundCardToWMS(String order_id) {
		BusiDealResult result = new BusiDealResult();
		
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		NotifyOrderStatusToWMSReq req = new NotifyOrderStatusToWMSReq();
		req.setOrderId(order_id);
		req.setNotNeedReqStrWms_status(EcsOrderConsts.WMS_WRITE_CARD_STATUS_2);
		NotifyOrderStatusToWMSResp infResp = client.execute(req, NotifyOrderStatusToWMSResp.class);
		
		if(!infResp.getErrorCode().equals(EcsOrderConsts.WMS_INF_RESP_CODE_0000)){
			result.setError_msg("错误编码：" + infResp.getErrorCode() + ";错误信息：" + infResp.getErrorMessage());
			result.setError_code(infResp.getErrorCode());
			
			Map params = new HashMap();
			BusiCompRequest bcr = new BusiCompRequest();
			bcr.setEnginePath("zteCommonTraceRule.markedException");
			bcr.setOrder_id(order_id);
			params.put(EcsOrderConsts.EXCEPTION_FROM, EcsOrderConsts.EXCEPTION_FROM_ORD);
			bcr.setQueryParams(params);
			CommonDataFactory.getInstance().execBusiComp(bcr);
		}else{
			String [] name={AttrConsts.WMS_STATUS};
			String [] value={EcsOrderConsts.WMS_STATUS_6};
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id,name,value);
		}
		return result;
	}

	@Override
	public BusiDealResult queryCardDataFromAop(String order_id) throws ApiBusiException {
		BusiDealResult result = new BusiDealResult();
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		
		CardDataQryRequest req = new CardDataQryRequest();
		req.setNotNeedReqStrOrderId(order_id);
		
		CardDataQryResponse rsp = client.execute(req, CardDataQryResponse.class);
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		if(!StringUtils.equals(rsp.getError_code(), EcsOrderConsts.AOP_HTTP_STATUS_CODE_200+"")){			
			//标记异常
			String orderMode = orderTree.getOrderExtBusiRequest().getOrder_model();
			boolean is_notice_sr_dk = false;
			if(EcsOrderConsts.OPER_MODE_ZD.equals(orderMode) 
					|| EcsOrderConsts.OPER_MODE_XK.equals(orderMode)){
				is_notice_sr_dk = true;
			}	
			
			result.setError_code(rsp.getCode());
			result.setError_msg(rsp.getDetail());	
			Utils.markException(order_id,is_notice_sr_dk,"",result.getError_msg());	
		}else{
			//保存IMSI,写卡脚本
			List<OrderItemBusiRequest> orderItemBusiRequests = orderTree.getOrderItemBusiRequests();
			if (orderItemBusiRequests != null && orderItemBusiRequests.size()>0) {
				OrderItemExtBusiRequest orderItemExtBusiRequest = orderItemBusiRequests.get(0).getOrderItemExtBusiRequest();
				orderItemExtBusiRequest.setCard_data(rsp.getCardData());
				orderItemExtBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
				orderItemExtBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
				orderItemExtBusiRequest.store();
			}
			
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, 
					new String[]{AttrConsts.ICCID,AttrConsts.OLD_ICCID,AttrConsts.SIMID,AttrConsts.PROC_ID,AttrConsts.ACTIVE_ID,AttrConsts.SCRIPT_SEQ,AttrConsts.CAPACITY_TYPE_CODE,AttrConsts.RES_KIND_CODE,AttrConsts.WRITE_CARD_STATUS}, 
					new String[]{req.getIccid(),rsp.getIccid(),rsp.getImsi(),rsp.getProcId(),rsp.getActiveId(),rsp.getScriptSeq(),rsp.getCapacityTypeCode(),rsp.getResKindCode(),EcsOrderConsts.WRITE_CARD_STATUS_3});
			
//			//通知给新商城(浙江不需要同步新商城)
//			StatuSynchReq statuSyn = new StatuSynchReq(order_id,EcsOrderConsts.DIC_ORDER_NODE_X,EcsOrderConsts.DIC_ORDER_NODE_X_DESC,EcsOrderConsts.IS_WRITE_CARD_YES,rsp.getImsi(),rsp.getScriptSeq());
//			CommonDataFactory.getInstance().notifyNewShop(statuSyn);
			
			result.setError_code(EcsOrderConsts.AOP_SUCCESS_0000);
			result.setError_msg("成功");	
			//记录多写卡获取取写卡数据操作日志
			OrderActionLogReq logReq = new OrderActionLogReq();
			logReq.setAction(EcsOrderConsts.AUTO_WRITE_ORDER_ACTION_09);
			logReq.setAction_desc("获取取写卡数据");
			logReq.setOrder_id(order_id);
			AutoWriteCardCacheTools.saveWriteCardActiveLog(logReq);
		}
		result.setResponse(rsp);
		return result;
	}
	
	@Override
	public BusiDealResult noticeWriteCardResultToAop(String order_id) throws ApiBusiException {
		BusiDealResult result = new BusiDealResult();
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		WriteCardResultNoticeReq req = new WriteCardResultNoticeReq();
		req.setNotNeedReqStrOrderId(order_id);

		WriteCardResultNoticeResp infResp = client.execute(req, WriteCardResultNoticeResp.class);//调用接口 
		if(!infResp.getError_code().equals(EcsOrderConsts.AOP_HTTP_STATUS_CODE_200+"")){
			result.setError_code(infResp.getCode());
			result.setError_msg(infResp.getDetail());

			OrderExtBusiRequest orderExtBusi = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest();
			orderExtBusi.setCol3(EcsOrderConsts.WRITE_CARD_ZB_FAIL);
			orderExtBusi.setDb_action(ConstsCore.DB_ACTION_UPDATE);
			orderExtBusi.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			orderExtBusi.store();
			
			//标记异常
			//Utils.markException(order_id,false);	
			Utils.markException(order_id,false,"",result.getError_msg());
		}else{
			result.setError_code(EcsOrderConsts.AOP_SUCCESS_0000);
			result.setError_msg("成功");	
		}
		result.setResponse(infResp);
		return result;
	}
	@Override
	public BusiDealResult noticeWriteCardResultToAopZJ(String order_id) throws ApiBusiException {
		BusiDealResult result = new BusiDealResult();
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		WriteCardResultNoticeReqZJ req = new WriteCardResultNoticeReqZJ();
		req.setNotNeedReqStrOrderId(order_id);

		WriteCardResultNoticeResp infResp = client.execute(req, WriteCardResultNoticeResp.class);//调用接口 
		if(!infResp.getError_code().equals(EcsOrderConsts.AOP_HTTP_STATUS_CODE_200+"")||!StringUtils.isEmpty(infResp.getCode())){
			result.setError_code(infResp.getCode());
			result.setError_msg(infResp.getDetail());

			OrderExtBusiRequest orderExtBusi = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest();
			orderExtBusi.setCol3(EcsOrderConsts.WRITE_CARD_ZB_FAIL);
			orderExtBusi.setDb_action(ConstsCore.DB_ACTION_UPDATE);
			orderExtBusi.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			orderExtBusi.store();
			
			//标记异常
			//Utils.markException(order_id,false);	
			Utils.markException(order_id,false,"",result.getError_msg());
		}else{
			result.setError_code(EcsOrderConsts.AOP_SUCCESS_0000);
			result.setError_msg("成功");	
		}
		result.setResponse(infResp);
		return result;
	}
	@Override
	public BusiDealResult SynWriteCardResults(String order_id) throws ApiBusiException {
		BusiDealResult result = new BusiDealResult();
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		SynCardDataReq req = new SynCardDataReq();
		OrderTreeBusiRequest order_tree = CommonDataFactory.getInstance().getOrderTree(order_id);
		   String phone_num = order_tree.getOrderItemBusiRequests().get(0).getOrderItemExtBusiRequest().getPhone_num();
		   String order_city_code = order_tree.getOrderExtBusiRequest().getOrder_city_code();
		   String iccid = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ICCID);
		   String out_operator = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.OUT_OPERATOR);
		   String out_office = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.OUT_OFFICE);
		   String write_status = "";
		   String reason_id = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.WRITE_CARD_RESULT);
		   String imsi = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.SIMID);
		   if(!StringUtils.isEmpty(reason_id)&&"0".equals(reason_id)){
			   write_status = "9";
		   }else{
			   write_status = "1";
		   }
		   req.setService_num(phone_num);
		   req.setRegion_id(order_city_code);
		   req.setIccid(iccid);
		   req.setOffice_id(out_office);
		   req.setOperator_id(out_operator);
		   req.setWrite_status(write_status);
		   req.setReason_id(reason_id);
		   req.setImsi(imsi);
		   req.setProc_id(order_id);
		SynCardDataRsp infResp = client.execute(req, SynCardDataRsp.class);//调用接口 
		if(!infResp.getCode().equals("00000")){
			result.setError_code(infResp.getCode());
			result.setError_msg(infResp.getMsg());

			OrderExtBusiRequest orderExtBusi = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest();
			orderExtBusi.setCol3(EcsOrderConsts.WRITE_CARD_ZB_FAIL);
			orderExtBusi.setDb_action(ConstsCore.DB_ACTION_UPDATE);
			orderExtBusi.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			orderExtBusi.store();
			
			//标记异常
			Utils.markException(order_id,false);			
		}else{
			result.setError_code(EcsOrderConsts.AOP_SUCCESS_0000);
			result.setError_msg("成功");	
		}
		result.setResponse(infResp);
		return result;
	}
	@Override
	public BusiDealResult syncCardDataToAop(String order_id) throws ApiBusiException {
		BusiDealResult result = new BusiDealResult();
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		
		CardDataSynRequest req = new CardDataSynRequest();
		req.setNotNeedReqStrOrderId(order_id);

		CardDataSynResponse rsp = client.execute(req, CardDataSynResponse.class);
		if(!rsp.getError_code().equals(EcsOrderConsts.AOP_HTTP_STATUS_CODE_200+"")){//失败
			result.setError_code(rsp.getCode());
			result.setError_msg(rsp.getDetail());
						
			//记录通知结果
			OrderExtBusiRequest orderExtBusi = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest();
			orderExtBusi.setCol3(EcsOrderConsts.WRITE_CARD_ZB_FAIL);
			orderExtBusi.setDb_action(ConstsCore.DB_ACTION_UPDATE);
			orderExtBusi.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			orderExtBusi.store();
			
			//标记异常
			Utils.markException(order_id,false);			
		}else {//成功		
			//记录通知结果
			OrderExtBusiRequest orderExtBusi = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest();
			orderExtBusi.setCol3(EcsOrderConsts.WRITE_CARD_ZB_SUCC);
			orderExtBusi.setDb_action(ConstsCore.DB_ACTION_UPDATE);
			orderExtBusi.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			orderExtBusi.store();	
			
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, 
					new String[]{AttrConsts.TAX_NO,AttrConsts.CARD_REAL_FEE,AttrConsts.WRITE_CARD_STATUS}, 
					new String[]{rsp.getTaxNo(),rsp.getCardRealFee(),EcsOrderConsts.WRITE_CARD_STATUS_5});			
			
			List<AttrGiftInfoBusiRequest> giftInfoList = CommonDataFactory.getInstance().getOrderTree(order_id).getGiftInfoBusiRequests();
			for(AttrGiftInfoBusiRequest giftInfo : giftInfoList){
				String bss_status = giftInfo.getBss_status();
				if(EcsOrderConsts.GIFT_BSS_STATUS_1.equals(bss_status)||EcsOrderConsts.GIFT_BSS_STATUS_3.equals(bss_status)){
					//logger.info("更新订单bss办理状态，id："+order_id+"  ");
					CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[]{AttrConsts.BSS_STATUS}, new String[]{EcsOrderConsts.BSS_STATUS_0});
					break;
				}
			}
			
			//4G场景：收到写卡通知新商城，不同3G要走BSS业务办理，模拟BSS办理成功。
			/*StatuSynchReq statuSyn = new StatuSynchReq(order_id,EcsOrderConsts.DIC_ORDER_NODE_Y,EcsOrderConsts.DIC_ORDER_NODE_Y_DESC,EcsOrderConsts.BSS_STATUS_1,EcsOrderConsts.BSS_STATUS_1_DESC,"");
			CommonDataFactory.getInstance().notifyNewShop(statuSyn);*/
			
			result.setError_code(EcsOrderConsts.AOP_SUCCESS_0000);
			result.setError_msg("成功");				
		}
		result.setResponse(rsp);
		return result;
	}

	@Override
	public BusiDealResult getCardDataFromBSS(String order_id)
			throws ApiBusiException {
		BusiDealResult result = new BusiDealResult();
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		//获取写卡数据 
		BSSGetCardMsgReq req = new BSSGetCardMsgReq();
		req.setNotNeedReqStrOrderId(order_id);
		BSSGetCardMsgRsp rsp = client.execute(req, BSSGetCardMsgRsp.class);
		if(!EcsOrderConsts.BSS_ANSWER_CODE_0000.equals(rsp.getError_code())){
			//标记异常
			String orderMode = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest().getOrder_model();
			Utils.markException(order_id,EcsOrderConsts.OPER_MODE_ZD.equals(orderMode));	
			
			result.setError_code(rsp.getError_code());
			result.setError_msg(rsp.getError_msg());
		}else{
			//保存IMSI,写卡脚本
			List<OrderItemBusiRequest> orderItemBusiRequests = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderItemBusiRequests();
			if (orderItemBusiRequests != null && orderItemBusiRequests.size()>0) {
				OrderItemExtBusiRequest orderItemExtBusiRequest = orderItemBusiRequests.get(0).getOrderItemExtBusiRequest();
				orderItemExtBusiRequest.setCard_data(rsp.getCardData());
				orderItemExtBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
				orderItemExtBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
				orderItemExtBusiRequest.store();
			}
			//获取写卡数据 调拔
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, 
					new String[]{AttrConsts.ICCID,AttrConsts.OLD_ICCID,AttrConsts.SIMID,AttrConsts.PROC_ID,AttrConsts.ACTIVE_ID,AttrConsts.SCRIPT_SEQ,AttrConsts.CAPACITY_TYPE_CODE,AttrConsts.RES_KIND_CODE,AttrConsts.WRITE_CARD_STATUS}, 
					new String[]{req.getIccid(),rsp.getIccid(),rsp.getImsi(),rsp.getProcId(),rsp.getActiveId(),rsp.getScriptSeq(),rsp.getCapacityTypeCode(),rsp.getResKindCode(),EcsOrderConsts.WRITE_CARD_STATUS_3});
			
			//通知给新商城
			StatuSynchReq statuSyn = new StatuSynchReq(order_id,EcsOrderConsts.DIC_ORDER_NODE_X,EcsOrderConsts.DIC_ORDER_NODE_X_DESC,EcsOrderConsts.IS_WRITE_CARD_YES,rsp.getImsi(),rsp.getScriptSeq());
			CommonDataFactory.getInstance().notifyNewShop(statuSyn);
			
			result.setError_code(EcsOrderConsts.BUSI_DEAL_RESULT_0000);
			result.setError_msg("成功");
			//记录多写卡获取取写卡数据操作日志
			OrderActionLogReq logReq = new OrderActionLogReq();
			logReq.setAction(EcsOrderConsts.AUTO_WRITE_ORDER_ACTION_09);
			logReq.setAction_desc("获取取写卡数据");
			logReq.setOrder_id(order_id);
			AutoWriteCardCacheTools.saveWriteCardActiveLog(logReq);
		}
		result.setResponse(rsp);
		return result;
	}

	@Override
	public BusiDealResult writeCardDataSync2BSS(String order_id)
			throws ApiBusiException {
		BusiDealResult result = new BusiDealResult();
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		WriteCardPreReq req = new WriteCardPreReq();
		req.setNotNeedReqStrOrderId(order_id);
		WriteCardPreRsp rsp = client.execute(req, WriteCardPreRsp.class);
		if(!EcsOrderConsts.BSS_ANSWER_CODE_0000.equals(rsp.getError_code())){
			result.setError_msg(rsp.getError_msg());
			result.setError_code(rsp.getError_code());			
			//记录通知结果
			OrderExtBusiRequest orderExtBusi = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest();
			orderExtBusi.setCol3(EcsOrderConsts.WRITE_CARD_ZB_FAIL);
			orderExtBusi.setDb_action(ConstsCore.DB_ACTION_UPDATE);
			orderExtBusi.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			orderExtBusi.store();
			
			//标记异常
			Utils.markException(order_id,false);
		}else{
			//记录通知结果
			OrderExtBusiRequest orderExtBusi = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest();
			orderExtBusi.setCol3(EcsOrderConsts.WRITE_CARD_ZB_SUCC);
			orderExtBusi.setDb_action(ConstsCore.DB_ACTION_UPDATE);
			orderExtBusi.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			orderExtBusi.store();	
			
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, 
					new String[]{AttrConsts.CARD_REAL_FEE,AttrConsts.WRITE_CARD_STATUS}, 
					new String[]{rsp.getTotalFee(),EcsOrderConsts.WRITE_CARD_STATUS_5});			
			
			List<AttrGiftInfoBusiRequest> giftInfoList = CommonDataFactory.getInstance().getOrderTree(order_id).getGiftInfoBusiRequests();
			for(AttrGiftInfoBusiRequest giftInfo : giftInfoList){
				String bss_status = giftInfo.getBss_status();
				if(EcsOrderConsts.GIFT_BSS_STATUS_1.equals(bss_status)||EcsOrderConsts.GIFT_BSS_STATUS_3.equals(bss_status)){
					//logger.info("更新订单bss办理状态，id："+order_id+"  ");
					CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[]{AttrConsts.BSS_STATUS}, new String[]{EcsOrderConsts.BSS_STATUS_0});
					break;
				}
			}
			/*StatuSynchReq statuSyn = new StatuSynchReq(order_id,EcsOrderConsts.DIC_ORDER_NODE_Y,EcsOrderConsts.DIC_ORDER_NODE_Y_DESC,EcsOrderConsts.BSS_STATUS_1,EcsOrderConsts.BSS_STATUS_1_DESC,"");
			CommonDataFactory.getInstance().notifyNewShop(statuSyn);*/			
			result.setError_code(EcsOrderConsts.BUSI_DEAL_RESULT_0000);
			result.setError_msg("成功");			
		}
		result.setResponse(rsp);
		return result;
	}		
	
	@Override
	public BusiDealResult noticeWriteCardResultToBSS(String order_id) throws ApiBusiException {
		BusiDealResult result = new BusiDealResult();
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		BSSWriteCardResultNoticeReq req = new BSSWriteCardResultNoticeReq();
		req.setNotNeedReqStrOrderId(order_id);

		BSSWriteCardResultNoticeResp infResp = client.execute(req, BSSWriteCardResultNoticeResp.class);//调用接口 
		if(!infResp.getError_code().equals(EcsOrderConsts.BSS_ANSWER_CODE_0000)){
			result.setError_code(infResp.getError_code());
			result.setError_msg(infResp.getError_msg());

			OrderExtBusiRequest orderExtBusi = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest();
			orderExtBusi.setCol3(EcsOrderConsts.WRITE_CARD_ZB_FAIL);
			orderExtBusi.setDb_action(ConstsCore.DB_ACTION_UPDATE);
			orderExtBusi.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			orderExtBusi.store();
			
			//标记异常
			Utils.markException(order_id,false);			
		}else{
			result.setError_code(EcsOrderConsts.AOP_SUCCESS_0000);
			result.setError_msg("成功");	
		}
		result.setResponse(infResp);
		return result;
	}

	@Override
	public BusiDealResult dealMaterielBox(String work_station) throws ApiBusiException {
		logger.info("log===============dealMaterielBox分配料箱["+work_station+"]");
		BusiDealResult result = new BusiDealResult();
		result.setError_code(EcsOrderConsts.AOP_SUCCESS_0000);
		result.setError_msg("成功");	
		if(StringUtils.isEmpty(work_station)){
			logger.info("写卡机位为空,退出");
			return result;
		}
		String order_id;
		MachinesGroup group = null;
		MachinesModel machine = null;
		OrderTreeBusiRequest orderTree = null;
		OrderWMSKeyInfoBusiRequest wmsBusi = null;

		try{
			//设置缓存锁
			String flag_lock = String.valueOf((int)(Math.random()*999999)) + String.valueOf(System.currentTimeMillis());
			String lock_value = work_station+flag_lock;
			boolean flag = AutoWriteCardCacheTools.addCacheLock(AutoWriteCardCacheTools.MACHINE_CACHE_LOCK_KEY_PREFIX + work_station,lock_value);
			if(!flag){ //已有缓存锁，有进程正在处理料箱队列
				logger.info("log===============dealMaterielBox分配料箱-1,料箱队列["+work_station+"]已锁["+flag+"],退出");
				return result;
			}
			try {
				String tmp = AutoWriteCardCacheTools.getCacheLock(AutoWriteCardCacheTools.MACHINE_CACHE_LOCK_KEY_PREFIX + work_station);
				if (tmp != null && !"".equals(tmp)) {
					if (!(lock_value).equals(tmp)) { // 如果不是当前线程加的锁，则退出
						logger.info("log===============dealMaterielBox分配料箱-2,料箱队列["+work_station+"]已锁["+flag+"],退出");
						return result;
					}
				} else {
					Thread.sleep(2000l); // 休眠2秒后再取出锁进行比较
					tmp = AutoWriteCardCacheTools.getCacheLock(AutoWriteCardCacheTools.MACHINE_CACHE_LOCK_KEY_PREFIX + work_station);
					if (!(lock_value).equals(tmp)) { // 如果不是当前线程加的锁，则退出
						logger.info("log===============dealMaterielBox分配料箱-3,料箱队列["+work_station+"]已锁["+flag+"],退出");
						return result;
					}
				}
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			
			//获取料箱队列
			MaterielBox box = AutoWriteCardCacheTools.getMaterielBoxByWorkStation(work_station);
//			logger.info("log===============dealMaterielBox分配料箱,获取料箱队列["+JsonUtil.toJson(box)+"]");
			if(null != box && box.getOrders().size() > 0){
				//获取所有空闲写卡机 （初始化写卡机组缓存）
				group = new MachinesGroup();
				group.setWorkStation(work_station);		
				ArrayList<MachinesModel> idleMachines = AutoWriteCardCacheTools.getIdleMachine(group);
				int index = 0;
				//待出队列订单
				for(int i = 0; i < box.getOrders().size(); i++){
					MaterielBoxOrder order = box.getOrders().get(i);
					order_id = order.getOrder_id();
					
					//记录多写卡获取写卡机位操作日志
					
					orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
					wmsBusi = orderTree.getOrderWMSKeyInfoBusiRequest();
					logger.info("log===============dealMaterielBox分配料箱,处理订单["+order_id+"]");
					//（1）如果是异常订单（自动移化异常），直接出料箱队列，继续遍历；
					if(null != wmsBusi && StringUtils.equals(wmsBusi.getIs_excp(), EcsOrderConsts.IS_DEFAULT_VALUE)){
						logger.info("log===============dealMaterielBox分配料箱,异常单移出队列["+order_id+"]");
						//异常单移出退队列
						AutoWriteCardCacheTools.removeOrderFromMaterielBox(box.getOrders().get(i));
						OrderActionLogReq logReq = new OrderActionLogReq();
						logReq.setAction(EcsOrderConsts.AUTO_WRITE_ORDER_ACTION_06);
						logReq.setAction_desc("异常订单移出队列");
						logReq.setWrite_station_code(box.getWorkStation());
						logReq.setOrder_id(order_id);
						AutoWriteCardCacheTools.saveActiveLog(logReq);
						continue;
					}
					//（3）如果是正常订单，并且所有写卡机的硬件状态都是异常状态，则标记订单异常（自动化写卡异常），并且移出料箱队列，继续遍历；
					if(AutoWriteCardCacheTools.machineISAllException(group)){
						logger.info("log===============dealMaterielBox分配料箱,写卡机组异常订单["+order_id+"]移出队列["+group.toString()+"]" );
						//写卡机组全部异常，标记订单异常（自动化写卡异常），并且移出料箱队列，继续遍历
						Utils.markException(order_id,false,"","自动化写卡机位["+work_station+"]的所有写卡异常,无法写卡");
						//异常单移出退队列
						AutoWriteCardCacheTools.removeOrderFromMaterielBox(box.getOrders().get(i));
						OrderActionLogReq logReq = new OrderActionLogReq();
						logReq.setAction(EcsOrderConsts.AUTO_WRITE_ORDER_ACTION_06);
						logReq.setAction_desc("写卡机全部异常订单移出队列");
						logReq.setWrite_station_code(box.getWorkStation());
						logReq.setOrder_id(order_id);
						AutoWriteCardCacheTools.saveActiveLog(logReq);
						continue;
					}
					//（2）如果可使用写卡机列表为null或者大小为0，则停止遍历；
					if(null == idleMachines || idleMachines.size() <= 0){
						logger.info("log===============dealMaterielBox分配料箱,没有空闲写卡机,订单["+order_id+"]停止分配");
//						ArrayList<MachinesModel> mm = AutoWriteCardCacheTools.getIdleMachinesFromGroup(group);
//						for(MachinesModel m : mm){
//							logger.info("log===============dealMaterielBox分配料箱,没有空闲写卡机退出.["+m.toString()+"],管理状态:" + AutoWriteCardCacheTools.getMachineStatus(m.getWorkStation(), m.getKey()).toString());			
//						}
						break;
					}
					if(index >= idleMachines.size()){
						logger.info("log===============dealMaterielBox分配料箱,没有空闲写卡机，订单["+order_id+"]停止分配，退出["+index+","+idleMachines.size()+"]");
						break;
					}
					//订单不在写卡环节 退出队列
					OrderExtBusiRequest orderExt = orderTree.getOrderExtBusiRequest();
					if(!StringUtils.equals(orderExt.getFlow_trace_id(), EcsOrderConsts.DIC_ORDER_NODE_X)){
						logger.info("log===============dealMaterielBox分配料箱,订单不在写卡环节["+order_id+"]退出");
						break;
					}
					//取第一个空闲写卡机
					machine = idleMachines.get(index);
//					logger.info("log===============dealMaterielBox分配料箱,["+order_id+"]获取到空闲写卡机["+machine.toString()+"]");
					//修改写卡机管理状态为订单正在写卡
					machine.setValue(order_id);
					AutoWriteCardCacheTools.modifyMachineStatus(machine);
//					logger.info("log===============dealMaterielBox分配料箱,["+order_id+"]标记写卡机正在使用["+machine.toString()+"]");
					
					//记录多写卡获取写卡机位操作日志
					OrderActionLogReq logReq = new OrderActionLogReq();
					logReq.setAction(EcsOrderConsts.AUTO_WRITE_ORDER_ACTION_07);
					logReq.setAction_desc("分配写卡机(分配料箱队列)");
					logReq.setWrite_station_code(machine.getWorkStation());
					logReq.setWrite_machine_code(machine.getKey());
					logReq.setOrder_id(order_id);
					AutoWriteCardCacheTools.saveWriteCardActiveLog(logReq);
					
					//从料箱中移除订单
					AutoWriteCardCacheTools.removeOrderFromMaterielBox(order);
					index ++;
					
					//异步调写卡方案
					// 开启异步线程执行后续规则
					try {
						String ruleid = "";
						String is_aop = orderExt.getIs_aop();
						if (EcsOrderConsts.ACCOUNT_OPEN_CHANNEL_ZB.equals(is_aop)) {
							ruleid = EcsOrderConsts.WMS_RECIVE_MATHIN_ID_GD_RULEID;
						}else if(EcsOrderConsts.ACCOUNT_OPEN_CHANNEL_BSS.equals(is_aop)){
							ruleid = EcsOrderConsts.WMS_RECIVE_MATHIN_ID_GD_RULEID_BSSKL;
						} else {
							ruleid = EcsOrderConsts.WMS_RECIVE_MATHIN_ID_GD_RULEID_AOP;
						}
						logger.info("log===============dealMaterielBox分配料箱,订单["+order_id+"]异步执行后续规则["+ruleid+"]");
						RuleTreeExeReq reqNext = new RuleTreeExeReq();
						reqNext.setRule_id(ruleid);
						TacheFact factNext = new TacheFact();
						factNext.setOrder_id(order_id);
						//构造WMS请求对象
						SynCardInfoWMSReq request = new SynCardInfoWMSReq();
						SynCardInfoOrderInfoVo orderInfo = new SynCardInfoOrderInfoVo();
						SynCardInfoGoodInfoVo goodInfo = new SynCardInfoGoodInfoVo();
						List<SynCardInfoGoodInfoVo> goodInfoList = new ArrayList<SynCardInfoGoodInfoVo>();
						goodInfo.setReadId(machine.getKey());
						goodInfoList.add(goodInfo);
						orderInfo.setGoodInfo(goodInfoList);
						request.setOrderInfo(orderInfo);
						factNext.setRequest(request);
						reqNext.setFact(factNext);
						
						TaskThreadPool taskThreadPool = new TaskThreadPool(new Task(reqNext) {
							public ZteResponse execute(ZteRequest zteRequest) {
								RuleTreeExeReq exereq = (RuleTreeExeReq)zteRequest;
								ZteClient client = ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ESC);
								RuleTreeExeResp rsp = client.execute(exereq, RuleTreeExeResp.class);
								BusiCompResponse busiResp = CommonDataFactory.getInstance().getRuleTreeresult(rsp);
								return busiResp;
							}
						});
						ThreadPoolFactory.getWriteCardThreadPoolExector().execute(taskThreadPool);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}else{
				logger.info("log===============dealMaterielBox分配料箱,料箱队列["+work_station+"]为空");
			}
		}catch(Exception e){
			logger.info("log===============dealMaterielBox处理料箱队列失败["+work_station+"]" + e.getMessage());
			e.printStackTrace();
		}finally{
			//分配料箱队列后删除缓存锁
			try{
				AutoWriteCardCacheTools.removeCacheLock(AutoWriteCardCacheTools.MACHINE_CACHE_LOCK_KEY_PREFIX + work_station);
			}catch(Exception e){
				logger.info("log===============dealMaterielBox删除缓存锁失败["+work_station+"]" + e.getMessage());
				e.printStackTrace();
			}
		}
	
		return result;
	}	

	@Override
	public BusiDealResult recWriteCardInfNew(BusiCompRequest busiCompRequest) {
		BusiDealResult result = new BusiDealResult();
		// 获取规则对象
		Map params = busiCompRequest.getQueryParams();
		TacheFact fact = (TacheFact) params.get("fact");
		SynCardInfoWMSReq req = (SynCardInfoWMSReq)fact.getRequest();
		String order_id = fact.getOrder_id();
		String readId = ""; 
		if(null != req){
			SynCardInfoOrderInfoVo orderInfo = req.getOrderInfo();
			List<SynCardInfoGoodInfoVo> goodInfos = orderInfo.getGoodInfo();
			OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
			List<OrderItemProdBusiRequest> prods = orderTree.getOrderItemBusiRequests().get(0).getOrderItemProdBusiRequests();
			for(SynCardInfoGoodInfoVo goodInfo : goodInfos){
				readId = goodInfo.getReadId();
				for(OrderItemProdBusiRequest prod:prods){
					OrderItemProdExtBusiRequest prodExt = prod.getOrderItemProdExtBusiRequest();
					prodExt.setReadId(goodInfo.getReadId());
					prodExt.setDb_action(ConstsCore.DB_ACTION_UPDATE);
					prodExt.setIs_dirty(ConstsCore.IS_DIRTY_YES);
					prodExt.store();
				}
			}
			//保存分配写卡机时间
			OrderWMSKeyInfoBusiRequest wmsReq = orderTree.getOrderWMSKeyInfoBusiRequest();
			wmsReq.setAllocate_time("sysdate");
			wmsReq.setDb_action(ConstsCore.DB_ACTION_UPDATE);
			wmsReq.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			wmsReq.store();
			
			String is_write_card = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.IS_WRITE_CARD);
			if(EcsOrderConsts.IS_DEFAULT_VALUE.equals(is_write_card)){		
				//已接受写卡机编码
				CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[]{AttrConsts.READID},new String[]{readId});
			}
			logger.info("\n接收WMS写卡机编号："+readId);
		}else {
			ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
			String is_run = cacheUtil.getConfigInfo(EcsOrderConsts.ORDER_FLOW_NODE_REC_WCN);
			if(EcsOrderConsts.ORDER_FLOW_RUN.equals(is_run)){
				readId = "11111111111111";
			}else {
				result.setError_code("-99999");
				return result;
			}
			String is_write_card = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.IS_WRITE_CARD);
			if(EcsOrderConsts.IS_DEFAULT_VALUE.equals(is_write_card)){		
				//已接受写卡机编码
				CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[]{AttrConsts.READID},new String[]{readId});
			}
		}
		//记录写卡状态
		CommonDataFactory.getInstance().updateAttrFieldValue(order_id, 
				new String[]{AttrConsts.WRITE_CARD_STATUS}, 
				new String[]{EcsOrderConsts.WRITE_CARD_STATUS_1});
		
		return result;
	}	

	/**
	 * 踢出自动化生产线
	 * @param orderId
	 * @return
	 */
	@Override
	public void shotOffAutoProduction(String order_id) {
		boolean flag= AutoWriteCardCacheTools.addCacheLock(AutoWriteCardCacheTools.SHOTOFF_CACHE_LOCK_KEY_PREFIX + order_id,order_id);
		if(!flag){
			logger.info("shotOffAutoProduction===============踢出自动化生产线["+order_id+"]已锁["+flag+"],退出");
			//已有缓存锁，有进程正在处理料箱队列
			return;
		}
		
		try {
			OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
			if (orderTree == null) {
				logger.info("shotOffAutoProduction["+order_id+"]========订单不存在或者已归档");
				AutoWriteCardCacheTools.removeCacheLock(AutoWriteCardCacheTools.SHOTOFF_CACHE_LOCK_KEY_PREFIX + order_id);
				return;
			}
			
			OrderExtBusiRequest orderExt = orderTree.getOrderExtBusiRequest();
			OrderWMSKeyInfoBusiRequest wmsReq = orderTree.getOrderWMSKeyInfoBusiRequest();
			String readId = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.READID);
			String syn_ord_wms = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.SYN_ORD_WMS);
			String order_model = orderExt.getOrder_model();
			if (StringUtils.equals(EcsOrderConsts.OPER_MODE_ZD, order_model) 
					&& StringUtils.equals(EcsOrderConsts.SYN_ORD_WMS_1, syn_ord_wms)) {
				boolean flag_exist = false;
				MachinesGroup group = AutoWriteCardCacheTools.getMachinesGroupByWorkStation(wmsReq.getWrite_machine_code());
				if (group != null) {
					List<MachinesModel> list = group.getMachines();
					if (null != list && list.size() > 0) {
						for (MachinesModel machine : list) {
							if (machine != null) {
								MachinesModel model = AutoWriteCardCacheTools.getMachineStatus(wmsReq.getWrite_machine_code(), machine.getKey());
								if (model != null && StringUtil.equals(order_id, model.getValue())) { // 如果订单对应写卡位上给当前订单分配了写卡机
									flag_exist = true;
									break;
								}
							}
						}
					}
				}
				logger.info("shotOffAutoProduction["+order_id+","+flag_exist+"]========长时间卡单踢出自动化线");
				Utils.markException(order_id, flag_exist, "", "长时间卡单踢出自动化线");
				
				//记录多写卡获取写卡机位操作日志
				AdminUser user = ManagerUtils.getAdminUser();
				if (user == null) {
					user.setUserid("admin");
					user.setUsername("超时踢出");
				}
				OrderActionLogReq logReq = new OrderActionLogReq();
				logReq.setAction(EcsOrderConsts.AUTO_WRITE_ORDER_ACTION_17);
				logReq.setAction_desc(user.getUserid()+"("+user.getUsername()+")操作踢出自动化线!是否回收卡["+flag_exist+"]");
				logReq.setWrite_station_code(wmsReq.getWrite_machine_code());
				logReq.setWrite_machine_code(readId);
				logReq.setOrder_id(order_id);
				AutoWriteCardCacheTools.saveWriteCardActiveLog(logReq);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("shotOffAutoProduction["+order_id+"]========长时间卡单踢出自动化线出错;" + e.getMessage());
		} finally {
			AutoWriteCardCacheTools.removeCacheLock(AutoWriteCardCacheTools.SHOTOFF_CACHE_LOCK_KEY_PREFIX + order_id);
		}
		return;
	}

	@Override
	public BusiDealResult synWriteCardInfToSRRG(String order_id) {
		BusiDealResult result = new BusiDealResult();
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		WriteICCIDSRRGRequset req = new WriteICCIDSRRGRequset();
		req.setNotNeedReqStrOrderId(order_id);
		WriteICCIDSRRGResponse resp = client.execute(req, WriteICCIDSRRGResponse.class);
		if(EcsOrderConsts.INF_RESP_CODE_0000.equals(resp.getError_code())){//接口返回成功即是写卡成功
			OrderExtBusiRequest orderExtBusiReq = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest();
			orderExtBusiReq.setWrite_card_result(EcsOrderConsts.WRITE_CARD_STATUS_SUCCESS);
			orderExtBusiReq.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			orderExtBusiReq.setDb_action(ConstsCore.DB_ACTION_UPDATE);
			orderExtBusiReq.store();
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, 
					new String[]{AttrConsts.SR_STATUS,AttrConsts.WRITE_CARD_RESULT}, new String[]{EcsOrderConsts.SR_STATUS_1,EcsOrderConsts.WRITE_CARD_STATUS_SUCCESS});

			result.setError_code(EcsOrderConsts.BUSI_DEAL_RESULT_0000);//业务组件成功
		}else {
			result.setError_msg("错误编码：" + resp.getError_code() + ";错误信息：" + resp.getError_msg());
			OrderExtBusiRequest orderExtBusiReq = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest();
			orderExtBusiReq.setWrite_card_result(EcsOrderConsts.WRITE_CARD_STATUS_EXCEPTION);
			orderExtBusiReq.setError_comments(resp.getError_msg());
			orderExtBusiReq.setWrite_card_status(EcsOrderConsts.WRITE_CARD_STATUS_EXCEPTION);
			orderExtBusiReq.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			orderExtBusiReq.setDb_action(ConstsCore.DB_ACTION_UPDATE);
			orderExtBusiReq.store();
			//标记异常
			Map params = new HashMap();
			BusiCompRequest bcr = new BusiCompRequest();
			bcr.setEnginePath("zteCommonTraceRule.markedException");
			bcr.setOrder_id(order_id);
			params.put(EcsOrderConsts.EXCEPTION_FROM, EcsOrderConsts.EXCEPTION_FROM_ORD);
			params.put(EcsOrderConsts.NOTIFY_SR_DK, EcsOrderConsts.NOTIFY_SR_DK_VAL);
			params.put(EcsOrderConsts.EXCEPTION_TYPE, "");
			params.put(EcsOrderConsts.EXCEPTION_REMARK, result.getError_msg());
			bcr.setQueryParams(params);
			CommonDataFactory.getInstance().execBusiComp(bcr);

			result.setError_code(EcsOrderConsts.BUSI_DEAL_RESULT_FAIL);//业务组件失败
		}
		return result;
	}	
	@Override
	public BusiDealResult refundCardToSRRG(String order_id) {
		BusiDealResult result = new BusiDealResult();
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		RevertCardRGRequset req = new RevertCardRGRequset();
		req.setNotNeedReqStrOrderId(order_id);
		req.setRequest(InfConst.SR_OUT_CARD);
		RevertCardRGResponse resp = client.execute(req, RevertCardRGResponse.class);
		if(EcsOrderConsts.INF_RESP_CODE_0000.equals(resp.getError_code())){//接口成功
			result.setError_code(EcsOrderConsts.BUSI_DEAL_RESULT_0000);//业务组件成功
		}else{//接口失败
			result.setError_code(EcsOrderConsts.BUSI_DEAL_RESULT_FAIL);//业务组件失败
		}
		if(!EcsOrderConsts.INF_RESP_CODE_0000.equals(resp.getError_code())){
			result.setError_msg("错误编码：" + resp.getError_code() + ";错误信息：" + resp.getError_msg());
			result.setError_code(EcsOrderConsts.BUSI_DEAL_RESULT_FAIL);
			//标记异常
			Map params = new HashMap();
			BusiCompRequest bcr = new BusiCompRequest();
			bcr.setEnginePath("zteCommonTraceRule.markedException");
			bcr.setOrder_id(order_id);
			params.put(EcsOrderConsts.EXCEPTION_FROM, EcsOrderConsts.EXCEPTION_FROM_ORD);
			params.put(EcsOrderConsts.NOTIFY_SR_DK, EcsOrderConsts.NOTIFY_SR_DK_VAL);
			params.put(EcsOrderConsts.EXCEPTION_TYPE, "");
			params.put(EcsOrderConsts.EXCEPTION_REMARK, result.getError_msg());
			bcr.setQueryParams(params);
			CommonDataFactory.getInstance().execBusiComp(bcr);
		}else{
			OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
			
			String [] name={AttrConsts.SR_STATUS};
			String [] value={EcsOrderConsts.WRITE_CARD_STATUS_6};
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id,name,value);
			result.setError_code(EcsOrderConsts.BUSI_DEAL_RESULT_0000);//业务组件成功
		}
		return result;
	}	
	
	@Override
	public BusiDealResult synIccidToZS(String order_id)throws ApiBusiException {
		BusiDealResult result = new BusiDealResult();
		//调用爬虫服务
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		CardSubmitInfoReq req=new CardSubmitInfoReq();

		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		String orderNo = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.OUT_TID);
		String isCardChange = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.IS_OLD);
		String orderId = orderTree.getOrderExtBusiRequest().getOrder_num();
		String invoiceNo = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.INVOICE_NO);
		String simID= CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ICCID);
		String numId = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.PHONE_NUM);
		String usimFee = "0";
		List<AttrFeeInfoBusiRequest> list=orderTree.getFeeInfoBusiRequests();
		for(AttrFeeInfoBusiRequest attrReq :list){
			if(StringUtils.equals(SpecConsts.FEE_DETAIL_CRAWLER002, attrReq.getFee_item_code())){//USIM卡费用
				usimFee=attrReq.getO_fee_num()+"";;
			}	
		}
		String isZFKNewOrder = "0";
		//如果是新用户，且特殊业务类型为副卡，则认为是主副卡新带新且副卡带有号码的订单
		String is_old = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.IS_OLD);
		String specialBusiType = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.SPECIAL_BUSI_TYPE);
		if(!EcsOrderConsts.IS_DEFAULT_VALUE.equals(is_old) && "02".equals(specialBusiType)){
			isZFKNewOrder = "1";
		}
		String preNum = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.PHONE_NUM);
		
		req.setNotNeedReqStrOrderId(order_id);
		req.setOrderNo(orderNo);
		req.setIsCardChange(isCardChange);
		req.setOrderId(orderId);
		req.setInvoiceNo(invoiceNo);
		req.setSimID(simID);
		req.setNumId(numId);
		req.setUsimFee(usimFee);
		req.setIsZFKNewOrder(isZFKNewOrder);
		req.setPreNum(preNum);
		
		ZteResponse rsp = client.execute(req, ZteResponse.class);
		if(!ConstsCore.ERROR_SUCC.equals(rsp.getError_code())){
			logger.info("=====OrdWriteCardTacheManager synIccidToZS 提交写卡接口调用出错【errorMsg:"+rsp.getError_msg()+"】");
			OrderExtBusiRequest orderExtBusiReq = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest();
			orderExtBusiReq.setWrite_card_result(EcsOrderConsts.WRITE_CARD_STATUS_EXCEPTION);
			orderExtBusiReq.setError_comments(rsp.getError_msg());
			orderExtBusiReq.setWrite_card_status(EcsOrderConsts.WRITE_CARD_STATUS_EXCEPTION);
			orderExtBusiReq.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			orderExtBusiReq.setDb_action(ConstsCore.DB_ACTION_UPDATE);
			orderExtBusiReq.store();
			//爬虫自动开户失败，生产模式转为爬虫手工写卡生产模式
			String order_model = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ORDER_MODEL);
			if(ZjEcsOrderConsts.ORDER_MODEL_08.equals(order_model)){
				CommonDataFactory.getInstance().updateAttrFieldValue(order_id, 
						new String[]{AttrConsts.ORDER_MODEL}, new String[]{ZjEcsOrderConsts.ORDER_MODEL_07});
				//标记异常
				Utils.markException(order_id,false,EcsOrderConsts.ABNORMAL_TYPE_WRITE,rsp.getError_msg());
			}
			//记录通知结
			OrderExtBusiRequest orderExtBusi = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest();
			orderExtBusi.setCol3(EcsOrderConsts.WRITE_CARD_ZB_FAIL);
			orderExtBusi.setDb_action(ConstsCore.DB_ACTION_UPDATE);
			orderExtBusi.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			orderExtBusi.store();
			
			result.setError_code(EcsOrderConsts.BUSI_DEAL_RESULT_FAIL);
			result.setError_msg(rsp.getError_msg());
		}else{
			//记录通知结果
			OrderExtBusiRequest orderExtBusi = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest();
			orderExtBusi.setCol3(EcsOrderConsts.WRITE_CARD_ZB_SUCC);
			orderExtBusi.setDb_action(ConstsCore.DB_ACTION_UPDATE);
			orderExtBusi.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			orderExtBusi.store();
			
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, 
					new String[]{AttrConsts.ZB_STATUS, AttrConsts.WRITE_CARD_STATUS}, 
					new String[]{EcsOrderConsts.ZB_ORDER_STATE_N07, EcsOrderConsts.WRITE_CARD_STATUS_5});
			
			List<AttrGiftInfoBusiRequest> giftInfoList = CommonDataFactory.getInstance().getOrderTree(order_id).getGiftInfoBusiRequests();
			for(AttrGiftInfoBusiRequest giftInfo : giftInfoList){
				String bss_status = giftInfo.getBss_status();
				if(EcsOrderConsts.GIFT_BSS_STATUS_1.equals(bss_status)||EcsOrderConsts.GIFT_BSS_STATUS_3.equals(bss_status)){
					logger.info("更新订单bss办理状态，id："+order_id+"  ");
					CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[]{AttrConsts.BSS_STATUS}, new String[]{EcsOrderConsts.BSS_STATUS_0});
					break;
				}
			}
			result.setError_code(EcsOrderConsts.BUSI_DEAL_RESULT_0000);
			result.setError_msg("成功");
		}
		result.setResponse(rsp);
		return result;
	}
	@Override
	public BusiDealResult getCardDataFromZS(String order_id)
			throws ApiBusiException {
		BusiDealResult result = new BusiDealResult();
		//调用爬虫服务
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());

		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		
		String orderNo = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.OUT_TID);
		String iccid = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ICCID);
		String orderId = orderTree.getOrderExtBusiRequest().getOrder_num();
		String isCardChange = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.IS_OLD);
		String isZFKNewOrder = "0";
		//如果是新用户，且特殊业务类型为副卡，则认为是主副卡新带新且副卡带有号码的订单
		String is_old = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.IS_OLD);
		String specialBusiType = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.SPECIAL_BUSI_TYPE);
		if(!EcsOrderConsts.IS_DEFAULT_VALUE.equals(is_old) && "02".equals(specialBusiType)){
			isZFKNewOrder = "1";
		}
		String preNum = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.PHONE_NUM);
		
		GetCardDatasReq req = new GetCardDatasReq();
		req.setNotNeedReqStrOrderId(order_id);
		req.setOrderNo(orderNo);
		req.setIccid(iccid);
		req.setOrderId(orderId);
		req.setIsCardChange(isCardChange);
		req.setIsZFKNewOrder(isZFKNewOrder);
		req.setPreNum(preNum);
		
		GetCardDatasResp rsp = client.execute(req, GetCardDatasResp.class);
		if(!ConstsCore.ERROR_SUCC.equals(rsp.getError_code())){//失败
			logger.info("=====OrdWriteCardTacheManager getCardDataFromZS 获取写卡数据接口调用出错【errorMsg:"+rsp.getError_msg()+"】");
			OrderExtBusiRequest orderExtBusiReq = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest();
			orderExtBusiReq.setWrite_card_result(EcsOrderConsts.WRITE_CARD_STATUS_EXCEPTION);
			orderExtBusiReq.setError_comments(rsp.getError_msg());
			orderExtBusiReq.setWrite_card_status(EcsOrderConsts.WRITE_CARD_STATUS_EXCEPTION);
			orderExtBusiReq.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			orderExtBusiReq.setDb_action(ConstsCore.DB_ACTION_UPDATE);
			orderExtBusiReq.store();
			
			//爬虫自动开户失败，生产模式转为爬虫手工写卡生产模式
			String order_model = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ORDER_MODEL);
			if(ZjEcsOrderConsts.ORDER_MODEL_08.equals(order_model)){
				CommonDataFactory.getInstance().updateAttrFieldValue(order_id, 
						new String[]{AttrConsts.ORDER_MODEL}, new String[]{ZjEcsOrderConsts.ORDER_MODEL_07});
				//标记异常
				Utils.markException(order_id,false,EcsOrderConsts.ABNORMAL_TYPE_WRITE,rsp.getError_msg());
			}
			
			result.setError_code(EcsOrderConsts.BUSI_DEAL_RESULT_FAIL);
			result.setError_msg(rsp.getError_msg());	
		}else{
			//保存IMSI,写卡脚本
			/*List<OrderItemBusiRequest> orderItemBusiRequests = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderItemBusiRequests();
			if (orderItemBusiRequests != null && orderItemBusiRequests.size()>0) {
				OrderItemExtBusiRequest orderItemExtBusiRequest = orderItemBusiRequests.get(0).getOrderItemExtBusiRequest();
				//orderItemExtBusiRequest.setCard_data(rsp.getCardData());   //【爬虫缺少这个】
				orderItemExtBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
				orderItemExtBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
				orderItemExtBusiRequest.store();
			}*/
			
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, 
					new String[]{AttrConsts.ICCID,AttrConsts.OLD_ICCID,AttrConsts.SIMID,AttrConsts.SCRIPT_SEQ,AttrConsts.WRITE_CARD_STATUS}, 
					new String[]{req.getIccid(),rsp.getIccid(),rsp.getImsi(),rsp.getScriptSeq(),EcsOrderConsts.WRITE_CARD_STATUS_3});
			
			result.setError_code(EcsOrderConsts.AOP_SUCCESS_0000);
			result.setError_msg("成功");	
		}
		result.setResponse(rsp);
		return result;
	}
	@Override
	public BusiDealResult writeCardFinishSubmitOrderToZS(String order_id) {
		BusiDealResult result = new BusiDealResult();
		//调用爬虫服务
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());

		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		
		OrderSubmitReq req = new OrderSubmitReq();
		String orderNo = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.OUT_TID);
		String orderId = orderTree.getOrderExtBusiRequest().getOrder_num();
		String invoiceNo = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.INVOICE_NO);
		String simNo= CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ICCID);
		String preNum = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.PHONE_NUM);
		String isZFKNewOrder = "0";
		//如果是新用户，且特殊业务类型为副卡，则认为是主副卡新带新且副卡带有号码的订单
		String is_old = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.IS_OLD);
		String specialBusiType = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.SPECIAL_BUSI_TYPE);
		if(!EcsOrderConsts.IS_DEFAULT_VALUE.equals(is_old) && "02".equals(specialBusiType)){
			isZFKNewOrder = "1";
		}
		CrawlerFreeDesc freeDesc=new CrawlerFreeDesc(); 
		List<CrawlerFeeInfo> crawlerFeeInfoList=new ArrayList<CrawlerFeeInfo>();
		Double totalFee=0.0;
		List<AttrFeeInfoBusiRequest> list=orderTree.getFeeInfoBusiRequests();
		for(AttrFeeInfoBusiRequest attrReq :list){
			
			if(attrReq.getO_fee_num()!=null){//总计
				totalFee=totalFee+attrReq.getO_fee_num();
			}
			
			if(StringUtils.equals(SpecConsts.FEE_DETAIL_CRAWLER002, attrReq.getFee_item_code())){//应收USIM卡费用
				if(attrReq.getO_fee_num()!=null){
					freeDesc.setUsimFee(attrReq.getO_fee_num()+"");
				}
				
			}	
			if(StringUtils.equals(SpecConsts.FEE_DETAIL_CRAWLER001, attrReq.getFee_item_code())){//应收多交预存款费用
				if(attrReq.getO_fee_num()!=null){
				    freeDesc.setOtherFee(attrReq.getO_fee_num()+"");
				}
			}	
			
			if(StringUtils.isEmpty(attrReq.getFee_category())){// list
				CrawlerFeeInfo fFeeInfo=new CrawlerFeeInfo();
				fFeeInfo.setFeeID(attrReq.getFee_inst_id());
				fFeeInfo.setFeeDes(attrReq.getFee_item_name());
				if(attrReq.getO_fee_num()!=null){
					fFeeInfo.setOrigFee(attrReq.getO_fee_num()+"");
				}
				if(attrReq.getDiscount_fee()!=null){
					fFeeInfo.setReliefFee(attrReq.getDiscount_fee()+"");
				}
				fFeeInfo.setReliefResult(attrReq.getDiscount_reason());
				//fFeeInfo.setFeeCategory(req.getFee_category());
				if(attrReq.getMax_relief()!=null){
					fFeeInfo.setMaxRelief(attrReq.getMax_relief()+"");
				}
				else{
					fFeeInfo.setMaxRelief("0.00");
				}
				if(attrReq.getN_fee_num()!=null){
					fFeeInfo.setRealFee(attrReq.getN_fee_num()+"");
				}
				crawlerFeeInfoList.add(fFeeInfo);
			}
			
		}
		freeDesc.setTotleFee(totalFee+"");
		freeDesc.setCrawlerFeeInfo(crawlerFeeInfoList);
		
		
		req.setNotNeedReqStrOrderId(order_id);
		req.setOrderNo(orderNo);
		req.setOrderId(orderId);
		req.setInvoiceNo(invoiceNo);
		req.setSimNo(simNo);
		req.setPreNum(preNum);
		req.setIsZFKNewOrder(isZFKNewOrder);
		req.setFreeDesc(freeDesc);
		
		OrderSubmitResp rsp = client.execute(req, OrderSubmitResp.class);
		if(!ConstsCore.ERROR_SUCC.equals(rsp.getError_code())){
			logger.info("=====OrdWriteCardTacheManager writeCardFinishSubmitOrderToZS 订单提交按钮（开户提交，预开户）接口调用出错【errorMsg:"+rsp.getError_msg()+"】");
			OrderExtBusiRequest orderExtBusiReq = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest();
			orderExtBusiReq.setWrite_card_result(EcsOrderConsts.WRITE_CARD_STATUS_EXCEPTION);
			orderExtBusiReq.setError_comments(rsp.getError_msg());
			orderExtBusiReq.setWrite_card_status(EcsOrderConsts.WRITE_CARD_STATUS_EXCEPTION);
			orderExtBusiReq.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			orderExtBusiReq.setDb_action(ConstsCore.DB_ACTION_UPDATE);
			orderExtBusiReq.store();
			
			//爬虫自动开户失败，生产模式转为爬虫手工写卡生产模式
			String order_model = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ORDER_MODEL);
			if(ZjEcsOrderConsts.ORDER_MODEL_08.equals(order_model)){
				CommonDataFactory.getInstance().updateAttrFieldValue(order_id, 
						new String[]{AttrConsts.ORDER_MODEL}, new String[]{ZjEcsOrderConsts.ORDER_MODEL_07});
				
				//标记异常
				Utils.markException(order_id,false,EcsOrderConsts.ABNORMAL_TYPE_WRITE,rsp.getError_msg());
			}
			result.setError_code(EcsOrderConsts.BUSI_DEAL_RESULT_FAIL);
			result.setError_msg(rsp.getError_msg());
		}else{
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[]{AttrConsts.ACTIVE_NO}, new String[]{rsp.getEssOrderId()});
			result.setError_code(EcsOrderConsts.BUSI_DEAL_RESULT_0000);
			result.setError_msg("成功");
		}
		result.setResponse(rsp);
		return result;
	}

	@Override
	public BusiDealResult writeCardConfirmDealFinishToZS(String order_id){
		BusiDealResult result = new BusiDealResult();
		//调用爬虫服务
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		
		SubmitOrderReq req=new SubmitOrderReq();
		String orderNo = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.OUT_TID);
		String orderId = orderTree.getOrderExtBusiRequest().getOrder_num();
		String certType = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.CERTI_TYPE);
		String certNum = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.CERT_CARD_NUM);
		String goodsType=SpecUtils.getProductSpec(order_id, SpecConsts.TYPE_ID_10002, null, SpecConsts.NET_TYPE);//2/3/4G
		String manualImeiCode=CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.TERMINAL_NUM);
		String manualNetCardNum = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.PHONE_NUM);
		String manualUsimCardNum= CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ICCID);
		String manualOrderNo =CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ACTIVE_NO);
		String isZFKNewOrder = "0";
		//如果是新用户，且特殊业务类型为副卡，则认为是主副卡新带新且副卡带有号码的订单
		String is_old = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.IS_OLD);
		String specialBusiType = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.SPECIAL_BUSI_TYPE);
		if(!EcsOrderConsts.IS_DEFAULT_VALUE.equals(is_old) && "02".equals(specialBusiType)){
			isZFKNewOrder = "1";
		}
		
		req.setNotNeedReqStrOrderId(order_id);
		req.setOrderNo(orderNo);
		req.setOrderId(orderId);
		req.setCertType(certType);
		req.setCertNum(certNum);
		req.setGoodsType(goodsType);
		req.setManualImeiCode(manualImeiCode);
		req.setManualNetCardNum(manualNetCardNum);
		req.setManualUsimCardNum(manualUsimCardNum);
		req.setManualOrderNo(manualOrderNo);
		req.setIsZFKNewOrder(isZFKNewOrder);
		
		ZteResponse rsp = client.execute(req, ZteResponse.class);
		if(!ConstsCore.ERROR_SUCC.equals(rsp.getError_code())){

			logger.info("=====OrdWriteCardTacheManager writeCardConfirmDealFinishToZS 确认提交订单按钮接口调用出错【errorMsg:"+rsp.getError_msg()+"】");
			OrderExtBusiRequest orderExtBusiReq = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest();
			orderExtBusiReq.setWrite_card_result(EcsOrderConsts.WRITE_CARD_STATUS_EXCEPTION);
			orderExtBusiReq.setError_comments(rsp.getError_msg());
			orderExtBusiReq.setWrite_card_status(EcsOrderConsts.WRITE_CARD_STATUS_EXCEPTION);
			orderExtBusiReq.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			orderExtBusiReq.setDb_action(ConstsCore.DB_ACTION_UPDATE);
			orderExtBusiReq.store();
			//爬虫自动开户失败，生产模式转为爬虫手工写卡生产模式
			String order_model = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ORDER_MODEL);
			if(ZjEcsOrderConsts.ORDER_MODEL_08.equals(order_model)){
				CommonDataFactory.getInstance().updateAttrFieldValue(order_id, 
						new String[]{AttrConsts.ORDER_MODEL}, new String[]{ZjEcsOrderConsts.ORDER_MODEL_07});
				//标记异常
				Utils.markException(order_id,false,EcsOrderConsts.ABNORMAL_TYPE_WRITE,rsp.getError_msg());
			}
			
			result.setError_code(EcsOrderConsts.BUSI_DEAL_RESULT_FAIL);
			result.setError_msg(rsp.getError_msg());
		}else{
			result.setError_code(EcsOrderConsts.BUSI_DEAL_RESULT_0000);
			result.setError_msg("成功");
		}
		result.setResponse(rsp);
		return result;
	}
	
	@Override
	public BusiDealResult syncCardDataToCBss(String order_id) throws ApiBusiException {
		BusiDealResult result = new BusiDealResult();
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		
		CardDataSynRequest req = new CardDataSynRequest();
		req.setNotNeedReqStrOrderId(order_id);
		
		CardDataSynResponse rsp = client.execute(req, CardDataSynResponse.class);
		if(!rsp.getError_code().equals(EcsOrderConsts.AOP_HTTP_STATUS_CODE_200+"")){//失败
			result.setError_code(rsp.getCode());
			result.setError_msg(rsp.getDetail());
						
			//记录通知结果
			OrderExtBusiRequest orderExtBusi = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest();
			orderExtBusi.setCol3(EcsOrderConsts.WRITE_CARD_ZB_FAIL);
			orderExtBusi.setDb_action(ConstsCore.DB_ACTION_UPDATE);
			orderExtBusi.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			orderExtBusi.store();
			
			//标记异常
			Utils.markException(order_id,false);			
		}else {//成功		
			//记录通知结果
			OrderExtBusiRequest orderExtBusi = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest();
			orderExtBusi.setCol3(EcsOrderConsts.WRITE_CARD_ZB_SUCC);
			orderExtBusi.setDb_action(ConstsCore.DB_ACTION_UPDATE);
			orderExtBusi.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			orderExtBusi.store();	
			
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, 
					new String[]{AttrConsts.TAX_NO,AttrConsts.CARD_REAL_FEE,AttrConsts.WRITE_CARD_STATUS}, 
					new String[]{rsp.getTaxNo(),rsp.getCardRealFee(),EcsOrderConsts.WRITE_CARD_STATUS_5});			
			
			List<AttrGiftInfoBusiRequest> giftInfoList = CommonDataFactory.getInstance().getOrderTree(order_id).getGiftInfoBusiRequests();
			for(AttrGiftInfoBusiRequest giftInfo : giftInfoList){
				String bss_status = giftInfo.getBss_status();
				if(EcsOrderConsts.GIFT_BSS_STATUS_1.equals(bss_status)||EcsOrderConsts.GIFT_BSS_STATUS_3.equals(bss_status)){
					//logger.info("更新订单bss办理状态，id："+order_id+"  ");
					CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[]{AttrConsts.BSS_STATUS}, new String[]{EcsOrderConsts.BSS_STATUS_0});
					break;
				}
			}
			
			//4G场景：收到写卡通知新商城，不同3G要走BSS业务办理，模拟BSS办理成功。
			/*StatuSynchReq statuSyn = new StatuSynchReq(order_id,EcsOrderConsts.DIC_ORDER_NODE_Y,EcsOrderConsts.DIC_ORDER_NODE_Y_DESC,EcsOrderConsts.BSS_STATUS_1,EcsOrderConsts.BSS_STATUS_1_DESC,"");
			CommonDataFactory.getInstance().notifyNewShop(statuSyn);*/
			
			result.setError_code(EcsOrderConsts.AOP_SUCCESS_0000);
			result.setError_msg("成功");				
		}
		result.setResponse(rsp);
		return result;
	}
	@Override
	public BusiDealResult syncCardDataToCBssZJ(String order_id) throws ApiBusiException {
		BusiDealResult result = new BusiDealResult();
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		
		CardDataSynRequestZJ req = new CardDataSynRequestZJ();
		req.setNotNeedReqStrOrderId(order_id);
		
		CardDataSynResponse rsp = client.execute(req, CardDataSynResponse.class);
		if(!rsp.getError_code().equals(EcsOrderConsts.AOP_HTTP_STATUS_CODE_200+"")||!StringUtils.isEmpty(rsp.getCode())){//失败
			result.setError_code(rsp.getCode());
			result.setError_msg(rsp.getDetail());
						
			//记录通知结果
			OrderExtBusiRequest orderExtBusi = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest();
			orderExtBusi.setCol3(EcsOrderConsts.WRITE_CARD_ZB_FAIL);
			orderExtBusi.setDb_action(ConstsCore.DB_ACTION_UPDATE);
			orderExtBusi.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			orderExtBusi.store();
			
			//标记异常
			Utils.markException(order_id,false);			
		}else {//成功		
			//记录通知结果
			OrderExtBusiRequest orderExtBusi = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest();
			orderExtBusi.setCol3(EcsOrderConsts.WRITE_CARD_ZB_SUCC);
			orderExtBusi.setDb_action(ConstsCore.DB_ACTION_UPDATE);
			orderExtBusi.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			orderExtBusi.store();	
			
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, 
					new String[]{AttrConsts.TAX_NO,AttrConsts.CARD_REAL_FEE,AttrConsts.WRITE_CARD_STATUS}, 
					new String[]{rsp.getTaxNo(),rsp.getCardRealFee(),EcsOrderConsts.WRITE_CARD_STATUS_5});			
			
			List<AttrGiftInfoBusiRequest> giftInfoList = CommonDataFactory.getInstance().getOrderTree(order_id).getGiftInfoBusiRequests();
			for(AttrGiftInfoBusiRequest giftInfo : giftInfoList){
				String bss_status = giftInfo.getBss_status();
				if(EcsOrderConsts.GIFT_BSS_STATUS_1.equals(bss_status)||EcsOrderConsts.GIFT_BSS_STATUS_3.equals(bss_status)){
					//logger.info("更新订单bss办理状态，id："+order_id+"  ");
					CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[]{AttrConsts.BSS_STATUS}, new String[]{EcsOrderConsts.BSS_STATUS_0});
					break;
				}
			}
			
			//4G场景：收到写卡通知新商城，不同3G要走BSS业务办理，模拟BSS办理成功。
			/*StatuSynchReq statuSyn = new StatuSynchReq(order_id,EcsOrderConsts.DIC_ORDER_NODE_Y,EcsOrderConsts.DIC_ORDER_NODE_Y_DESC,EcsOrderConsts.BSS_STATUS_1,EcsOrderConsts.BSS_STATUS_1_DESC,"");
			CommonDataFactory.getInstance().notifyNewShop(statuSyn);*/
			
			result.setError_code(EcsOrderConsts.AOP_SUCCESS_0000);
			result.setError_msg("成功");				
		}
		result.setResponse(rsp);
		return result;
	}
	@Override
	public BusiDealResult syncCardDataToAopNew(String order_id) throws ApiBusiException {
		BusiDealResult result = new BusiDealResult();
		ZteResponse rsp =  new ZteResponse();
		try {
			// 添加到定时任务队列
			CoQueue queBak = new CoQueue();
			queBak.setService_code("CO_CARDDATA_NOTIFY_ZB");			//service_code改为老系统
			queBak.setCo_id("");
			queBak.setCo_name("写卡信息同步总部商城");
			queBak.setObject_id(order_id);
			queBak.setObject_type("DINGDAN");
			queBak.setStatus(Consts.CO_QUEUE_STATUS_WFS);
			String   create_date= com.ztesoft.ibss.common.util.DateUtil.addDate(com.ztesoft.ibss.common.util.DateUtil.getTime2(), com.ztesoft.ibss.common.util.DateUtil.DATE_FORMAT_2, ZjEcsOrderConsts.ZS_ES_CO_QUEUE_TIME_CHANGE, "second");
			queBak.setCreated_date(create_date);
			this.baseDaoSupport.insert("co_queue", queBak);
			
			result.setError_code(EcsOrderConsts.ZB_INF_RESP_CODE_0000);
			result.setError_msg("执行成功,后台将会自动同步变更信息至总部商城");
		} catch (Exception e) {
			e.printStackTrace();
			result.setError_code("-1");
			result.setError_msg("es_co_queue定时任务插入失败;"+e.getMessage());
		}
		result.setResponse(rsp);
		return result;
	}
	
	/**
	 * [号卡]BSS 卡数据同步
	 * @param order_id
	 * @return
	 */
	@Override
	public BusiDealResult CardDataSyncBss(String order_id) throws ApiBusiException {
		BusiDealResult result = new BusiDealResult();
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		CommonDataFactory dataFactory = CommonDataFactory.getInstance();
		// 设置参数
		CardDataSyncBssReq requ = new CardDataSyncBssReq();
		requ.setService_num(dataFactory.getAttrFieldValue(order_id, AttrConsts.PHONE_NUM));
		String regionId = (String) ThreadLocalUtil.getProperty(order_id+"_region_id");
		ThreadLocalUtil.removeProperty(order_id+"_region_id");
		requ.setRegion_id(regionId);
		String write_status = "";
		String reason_id = (String) ThreadLocalUtil.getProperty(order_id+"_reasonId");
		ThreadLocalUtil.removeProperty(order_id+"_reasonId");
		if (!StringUtils.isEmpty(reason_id) && "0".equals(reason_id)) {
			write_status = "9";
		} else {
			write_status = "1";
		}
		requ.setNotNeedReqStrOrderId(order_id);
		requ.setWrite_status(write_status);
		requ.setProc_id(dataFactory.getAttrFieldValue(order_id, AttrConsts.PROC_ID));
		requ.setIccid(dataFactory.getAttrFieldValue(order_id, AttrConsts.ICCID));
		requ.setReason_id(reason_id);
		requ.setImsi(dataFactory.getAttrFieldValue(order_id, AttrConsts.SIMID));
		requ.setOffice_id(dataFactory.getAttrFieldValue(order_id, AttrConsts.OUT_OFFICE));
		requ.setOperator_id(dataFactory.getAttrFieldValue(order_id, AttrConsts.OUT_OPERATOR));
		//根据ICCID和收货人号码查询订单信息
		CardDataSyncBssResp rsp = client.execute(requ, CardDataSyncBssResp.class);
		
		if(!EcsOrderConsts.BSS_SUCCESS_00000.equals(rsp.getCode())){//失败
			//设置错误返回码
			result.setError_code(rsp.getCode());
			result.setError_msg((StringUtils.isEmpty(rsp.getMsg())?rsp.getCode():rsp.getMsg()));
			
			long start1 = System.currentTimeMillis();
				//标记异常
			//Utils.markException(order_id,false);
			Utils.markException(order_id,false,"",result.getError_msg());
			logger.info(order_id+"调用卡数据同步接口【CardDataSyncBss】耗时："+(System.currentTimeMillis()-start1));
		}else {//成功		
			//记录通知结果
			OrderExtBusiRequest orderExtBusi = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest();
			orderExtBusi.setCol3(EcsOrderConsts.WRITE_CARD_ZB_SUCC);
			orderExtBusi.setDb_action(ConstsCore.DB_ACTION_UPDATE);
			orderExtBusi.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			orderExtBusi.store();			
			
			List<AttrGiftInfoBusiRequest> giftInfoList = CommonDataFactory.getInstance().getOrderTree(order_id).getGiftInfoBusiRequests();
			for(AttrGiftInfoBusiRequest giftInfo : giftInfoList){
				String bss_status = giftInfo.getBss_status();
				if(EcsOrderConsts.GIFT_BSS_STATUS_1.equals(bss_status)||EcsOrderConsts.GIFT_BSS_STATUS_3.equals(bss_status)){
					//logger.info("更新订单bss办理状态，id："+order_id+"  ");
					CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[]{AttrConsts.BSS_STATUS}, new String[]{EcsOrderConsts.BSS_STATUS_0});
					break;
				}
			}
			/*StatuSynchReq statuSyn = new StatuSynchReq(order_id,EcsOrderConsts.DIC_ORDER_NODE_Y,EcsOrderConsts.DIC_ORDER_NODE_Y_DESC,EcsOrderConsts.BSS_STATUS_1,EcsOrderConsts.BSS_STATUS_1_DESC,"");
			CommonDataFactory.getInstance().notifyNewShop(statuSyn);*/			
			result.setError_code(EcsOrderConsts.BUSI_DEAL_RESULT_0000);
			result.setError_msg("成功");				
		}
		result.setResponse(rsp);
		return result;
	}
	@Override
	public List getAutoOrderList(String orderId) {
		// TODO Auto-generated method stub
		String sql="select queue_code, order_id, source_from from (select q.queue_code"
				+ " , q.order_id, q.source_from from ES_QUEUE_MANAGER c,es_queue_card_mate_manager d,"
				+ " es_queue_write_card q where  d.queue_code = c.queue_no"
				+ " and c.queue_switch = '0' and q.queue_code = d.queue_code and q.open_account_status = '2'"
				+ " and nvl(q.write_card_status, '0') = '0' and c.source_from = d.source_from"
				+ " and d.source_from = ? and nvl(q.queue_status,'0') = '0' and q.order_id=? "
				+ " ) where source_from = ? and rownum < 2";
		return  this.baseDaoSupport.queryForList(sql, ManagerUtils.getSourceFrom(),orderId,ManagerUtils.getSourceFrom());
	}
	@Override
	public BusiDealResult autoWriteCard(String orderId) {
		// TODO Auto-generated method stub
		BusiDealResult result = new BusiDealResult();
		WriteCardReq req = new WriteCardReq();
		req.setOrder_id(orderId);
		req.setMachine_no(CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.READID));
		req.setIccid(CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.ICCID));
		req.setImsi(CommonDataFactory.getInstance().getAttrFieldValue(orderId,AttrConsts.SIMID));
		req.setOption(CommonDataFactory.getInstance().getAttrFieldValue(orderId,AttrConsts.SCRIPT_SEQ));
		req.setPhone_num(CommonDataFactory.getInstance().getAttrFieldValue(orderId,AttrConsts.PHONE_NUM));
		
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		ZteResponse resp = client.execute(req, ZteResponse.class);
		if(!resp.getError_code().equals(ConstsCore.ERROR_SUCC)){
			result.setError_code(EcsOrderConsts.BUSI_DEAL_RESULT_FAIL);
			result.setError_msg(resp.getError_msg());
		}else{
			result.setError_code(EcsOrderConsts.BUSI_DEAL_RESULT_0000);
			result.setError_msg("成功");
		}
		return result;
	}
	@Override
	public BusiDealResult insertWriteCardQueueByPc(String orderId) {
		// TODO Auto-generated method stub
		BusiDealResult result = new BusiDealResult();
		OrderTreeBusiRequest tree = CommonDataFactory.getInstance().getOrderTree(orderId);
		String queue_no =  tree.getOrderWMSKeyInfoBusiRequest().getWrite_machine_code();
		WriteQueueReq req = new WriteQueueReq();
		req.setOrder_id(orderId);
		req.setGroup_no(queue_no);
		
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		ZteResponse resp = client.execute(req, ZteResponse.class);
		if(!resp.getError_code().equals(ConstsCore.ERROR_SUCC)){
			result.setError_code(EcsOrderConsts.BUSI_DEAL_RESULT_FAIL);
			result.setError_msg(resp.getError_msg());
		}else{
			result.setError_code(EcsOrderConsts.BUSI_DEAL_RESULT_0000);
			result.setError_msg("成功");
		}
		return result;
	}
	public BusiDealResult  crawlerWriteCardSuc(String orderId){
		BusiDealResult result = new BusiDealResult();
		String isZFKNewOrder = "0";
		//如果是新用户，且特殊业务类型为副卡，则认为是主副卡新带新且副卡带有号码的订单
		String is_old = CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.IS_OLD);
		String specialBusiType = CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.SPECIAL_BUSI_TYPE);
		if(!EcsOrderConsts.IS_DEFAULT_VALUE.equals(is_old) && "02".equals(specialBusiType)){
			isZFKNewOrder = "1";
		}
		CrawlerWriteCardSucReq req = new CrawlerWriteCardSucReq();
		req.setIccid(CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.ICCID));
		req.setImsi(CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.SIMID));
		req.setIsCardChange(is_old);
		req.setIsZFKNewOrder(isZFKNewOrder);
		req.setOrderId(CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.ORDER_NUM));
		req.setOrderNo(CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.OUT_TID));
		req.setPreNum(CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.PHONE_NUM));
		
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		ZteResponse resp =client.execute(req,  ZteResponse.class);
		if(!resp.getError_code().equals(ConstsCore.ERROR_SUCC)){
			result.setError_code(EcsOrderConsts.BUSI_DEAL_RESULT_FAIL);
			result.setError_msg(resp.getError_msg());
		}else{
			result.setError_code(EcsOrderConsts.BUSI_DEAL_RESULT_0000);
			result.setError_msg("成功");
		}
		return result;
	}
}
