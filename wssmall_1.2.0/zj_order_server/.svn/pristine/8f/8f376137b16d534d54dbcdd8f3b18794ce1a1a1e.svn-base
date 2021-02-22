package com.ztesoft.net.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import params.ZteRequest;
import util.Utils;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.base.req.BusiCompRequest;
import zte.net.ecsord.params.base.resp.BusiCompResponse;
import zte.net.ecsord.params.busi.req.AttrFeeInfoBusiRequest;
import zte.net.ecsord.params.busi.req.AttrGiftInfoBusiRequest;
import zte.net.ecsord.params.busi.req.AttrTmResourceInfoBusiRequest;
import zte.net.ecsord.params.busi.req.OrderExtBusiRequest;
import zte.net.ecsord.params.busi.req.OrderItemExtvtlBusiRequest;
import zte.net.ecsord.params.busi.req.OrderItemProdBusiRequest;
import zte.net.ecsord.params.busi.req.OrderItemProdExtBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;
import zte.net.ecsord.params.ecaop.req.TerminalStatusQueryChanageBatchReq;
import zte.net.ecsord.params.ecaop.req.TerminalStatusQueryChanageReq;
import zte.net.ecsord.params.ecaop.req.vo.ResourcesRspVo;
import zte.net.ecsord.params.ecaop.resp.TerminalStatusQueryChanageResp;
import zte.net.ecsord.params.taobao.req.SynchronousOrderTBRequset;
import zte.net.ecsord.params.taobao.resp.SynchronousOrderTBResponse;
import zte.net.ecsord.params.wms.req.NotifyOrderInfoWMSReq;
import zte.net.ecsord.params.wms.req.NotifyOrderStatusFromWMSReq;
import zte.net.ecsord.params.wms.req.SynSerialNumWMSReq;
import zte.net.ecsord.params.wms.resp.NotifyOrderInfoWMSResp;
import zte.net.ecsord.params.wms.resp.NotifyOrderStatusFromWMSResp;
import zte.net.ecsord.params.wms.resp.SynSerialNumWMSResp;
import zte.net.ecsord.params.wms.vo.NotifyStatusFromWMSOrderInfoVo;
import zte.net.ecsord.params.wms.vo.SynSerialGoodInfoVo;
import zte.net.ecsord.params.wyg.req.StatuSynchReq;
import zte.net.ecsord.params.zb.req.NotifyStringZBRequest;
import zte.net.ecsord.params.zb.resp.NotifyStringZBResponse;
import zte.net.ecsord.rule.tache.TacheFact;

import com.ztesoft.api.ApiBusiException;
import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.common.util.JsonUtil;
import com.ztesoft.common.util.StringUtils;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.model.OrderReleaseRecord;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.model.BusiDealResult;
import com.ztesoft.net.service.IOrdPickingTacheManager;

import consts.ConstsCore;

/**
 * 预检货环节处理类
 * 
 * @author xuefeng
 */
public class OrdPickingTacheManager extends BaseSupport implements
		IOrdPickingTacheManager { 

	@Override
	public BusiDealResult synOrdInfToWMS(String order_id) {
		BusiDealResult result = new BusiDealResult();
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		NotifyOrderInfoWMSReq req = new NotifyOrderInfoWMSReq();
		req.setOrderId(order_id);
		NotifyOrderInfoWMSResp infResp = client.execute(req,
				NotifyOrderInfoWMSResp.class);
		if (!EcsOrderConsts.WMS_INF_RESP_CODE_0000.equals(infResp.getErrorCode())){
			result.setError_msg("错误编码：" + infResp.getErrorCode() + ";错误信息："
					+ infResp.getErrorMessage());
			result.setError_code(infResp.getErrorCode());
			
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, 
					new String[]{AttrConsts.IS_SEND_WMS}, 
					new String[]{EcsOrderConsts.SYN_ORD_WMS_0});
			//标记异常
//			Map params = new HashMap();
//			BusiCompRequest bcr = new BusiCompRequest();
//			bcr.setEnginePath("zteCommonTraceRule.markedException");
//			bcr.setOrder_id(order_id);
//			params.put(EcsOrderConsts.EXCEPTION_FROM, EcsOrderConsts.EXCEPTION_FROM_ORD);
//			bcr.setQueryParams(params);
//			CommonDataFactory.getInstance().execBusiComp(bcr);
			
		}else{
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, 
					new String[]{AttrConsts.WMS_STATUS, AttrConsts.SYN_ORD_WMS, AttrConsts.IS_SEND_WMS}, 
					new String[]{EcsOrderConsts.ORDER_STATUS_WMS_20, EcsOrderConsts.SYN_ORD_WMS_1, EcsOrderConsts.SYN_ORD_WMS_1});
		}
		result.setResponse(infResp);
		return result;
	}

	@Override
	public BusiDealResult recProdInfFromWMS(BusiCompRequest busiCompRequest) throws ApiBusiException{
		BusiDealResult result = new BusiDealResult();
		Map params = busiCompRequest.getQueryParams();
		TacheFact fact = (TacheFact) params.get("fact");
		ZteRequest req =  fact.getRequest();
		if(null == req){
			return result;
		}
		try{
			if(req instanceof SynSerialNumWMSReq){		//货品通知
				result = this.recGoodsInfo(params, req);
			}else if(req instanceof NotifyOrderStatusFromWMSReq){ //接收WMS订单状态通知 1:缺货 ...
				result = this.recStockStatus(params, req);
			}
		}catch(Exception e){
			throw new ApiBusiException(e.getMessage());
		}
		return result;
	}

	@Override
	public BusiDealResult notifyProdInfToZB(String order_id) {
		BusiDealResult result = new BusiDealResult();
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils
				.getSourceFrom());
		NotifyStringZBRequest req = new NotifyStringZBRequest();
		req.setNotNeedReqStrOrderId(order_id);
		
		NotifyStringZBResponse infResp = client.execute(req, NotifyStringZBResponse.class);
		if (!infResp.getRespCode().equals(
				EcsOrderConsts.ZB_INF_RESP_CODE_0000)){
			result.setError_msg("错误编码：" + infResp.getRespCode() + ";错误信息："
					+ infResp.getRespDesc());
			result.setError_code("-99999");
			String orderMode = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest().getOrder_model();
			//自动化标记异常
			if(EcsOrderConsts.OPER_MODE_ZD.equals(orderMode)){
				Map params = new HashMap();
				BusiCompRequest bcr = new BusiCompRequest();
				bcr.setEnginePath("zteCommonTraceRule.markedException");
				bcr.setOrder_id(order_id);
				params.put(EcsOrderConsts.EXCEPTION_FROM, EcsOrderConsts.EXCEPTION_FROM_ORD);
				bcr.setQueryParams(params);
				CommonDataFactory.getInstance().execBusiComp(bcr);
			}
		}else {
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, 
					new String[]{AttrConsts.ZB_STATUS}, 
					new String[]{EcsOrderConsts.ZB_ORDER_STATE_N05});
			StatuSynchReq statuSyn = new StatuSynchReq(order_id,EcsOrderConsts.DIC_ORDER_NODE_C,EcsOrderConsts.DIC_ORDER_NODE_C_DESC,EcsOrderConsts.ORDER_STANDARDING_1,EcsOrderConsts.ORDER_NODE_C_SUCCESS,"");
			CommonDataFactory.getInstance().notifyNewShop(statuSyn);
		}
		result.setError_code(infResp.getRespCode());
		return result;
	}

	@Override
	public BusiDealResult synOrdToTBMall(String order_id) {
		BusiDealResult result = new BusiDealResult();
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils
				.getSourceFrom());
		SynchronousOrderTBRequset req = new SynchronousOrderTBRequset();
		req.setNotNeedReqStrOrderId(order_id);
		SynchronousOrderTBResponse infResp = client.execute(req,
				SynchronousOrderTBResponse.class);
		if (!infResp.getError_code().equals(
				EcsOrderConsts.WMS_INF_RESP_CODE_0000)){
			result.setError_msg("错误编码：" + infResp.getError_code() + ";错误信息："
					+ infResp.getError_msg());
			//接口调用成功设置淘宝商城发货状态为"已通知新淘宝发货失败" 
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[]{AttrConsts.TB_STATUS}, new String[]{EcsOrderConsts.TB_STATUS_0});
			result.setError_code("-99999");
			result.setError_msg("通知新淘宝发货失败");
		}else{
			result.setError_code(infResp.getError_code());
			result.setError_msg("通知新淘宝发货成功");
			//接口调用成功设置淘宝商城发货状态为"已通知新淘宝发货成功"
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[]{AttrConsts.TB_STATUS}, new String[]{EcsOrderConsts.TB_STATUS_1});
		}
		
		return result;
	}
	
	//货品通知
	@SuppressWarnings("unchecked")
	private BusiDealResult recGoodsInfo(Map params, ZteRequest req) throws Exception{
		BusiDealResult result = new BusiDealResult();
		SynSerialNumWMSResp resp = new SynSerialNumWMSResp();
		String terminal_num = null;
		String order_id = ((SynSerialNumWMSReq)req).getOrderInfo().getOrderId();
		String goods_type = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.GOODS_TYPE);
		String sim_type = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.SIM_TYPE);
		//这里需要改造，按照列表处理，需要一个根据货品id查询货品信息的api
		List<SynSerialGoodInfoVo> goodsList = ((SynSerialNumWMSReq)req).getOrderInfo().getGoodInfo();
		//更新串号到货品扩展表和礼品表
		List<OrderItemProdBusiRequest> prodBusis = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderItemBusiRequests().get(0).getOrderItemProdBusiRequests();
		List<AttrGiftInfoBusiRequest> giftInfos = CommonDataFactory.getInstance().getOrderTree(order_id).getGiftInfoBusiRequests();
		
		//获取多商品数据
		List<OrderItemExtvtlBusiRequest> orderItemExtvtls = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderItemExtvtlBusiRequests();
		String order_type = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderBusiRequest().getOrder_type();
		//B2B或华盛订单接收WMS串号
		String platType = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest().getPlat_type();
		//是否批量更新串号
		boolean isBatchUpdate = false;
		//批量更新串号SQL
		String updateTerminalImeiSql = "UPDATE ES_ORDER_ITEMS_EXTVTL SET RESOURCES_CODE = :RESOURCES_CODE WHERE ITEMS_ID = :ITEMS_ID";
		List<Map<String,String>> batchMap = new ArrayList<Map<String,String>>();
		for(SynSerialGoodInfoVo goodsInfoVo : goodsList){
			if(EcsOrderConsts.WMS_PRODUCT_TYPE_1.equals(goodsInfoVo.getItemsType())){//实物货品
				//多商品
				if(EcsOrderConsts.ORDER_TYPE_09.equals(order_type) 
						|| StringUtils.equals(platType, EcsOrderConsts.PLAT_TYPE_10061)){
					isBatchUpdate = true;
					for(OrderItemExtvtlBusiRequest orderItemExtvtl : orderItemExtvtls){
						if(goodsInfoVo.getOrderProductId().equals(orderItemExtvtl.getItems_id())){
							Map<String,String> m = new HashMap<String, String>();
							m.put("RESOURCES_CODE", goodsInfoVo.getTerminalImei());
							m.put("ITEMS_ID", orderItemExtvtl.getItems_id());
							batchMap.add(m);
							//orderItemExtvtl.setResources_code(goodsInfoVo.getTerminalImei());
							//orderItemExtvtl.setIs_dirty(ConstsCore.IS_DIRTY_YES);
							//orderItemExtvtl.setDb_action(ConstsCore.DB_ACTION_UPDATE);
							//orderItemExtvtl.store();
						}
					}
					terminal_num = goodsInfoVo.getTerminalImei();
				}
				for(OrderItemProdBusiRequest prodBusi : prodBusis){
					if(goodsInfoVo.getOrderProductId().equals(prodBusi.getProd_spec_goods_id())){//根据货品ID更新串号
						OrderItemProdExtBusiRequest prodExt = prodBusi.getOrderItemProdExtBusiRequest();
						if(EcsOrderConsts.PRODUCT_TYPE_CODE_OFFER.equals(prodBusi.getProd_spec_type_code())
								&& EcsOrderConsts.SIM_TYPE_CK.equals(sim_type)){//成卡需要更新ICCID，白卡从森锐读取
							prodExt.setICCID(goodsInfoVo.getTerminalImei());
						}
						if(EcsOrderConsts.PRODUCT_TYPE_CODE_TERMINAL.equals(prodBusi.getProd_spec_type_code()) && !StringUtils.isEmpty(goodsInfoVo.getTerminalImei())){
							terminal_num = goodsInfoVo.getTerminalImei();
						}
//						prodExt.setTerminal_num(goodsInfoVo.getTerminalImei());
						prodExt.setDb_action(ConstsCore.DB_ACTION_UPDATE);
						prodExt.setIs_dirty(ConstsCore.IS_DIRTY_YES);
						prodExt.store();
						
					}
				}
			}
			else if(EcsOrderConsts.WMS_PRODUCT_TYPE_0.equals(goodsInfoVo.getItemsType())){//实物礼品
				for(AttrGiftInfoBusiRequest giftInfo : giftInfos){
					if(giftInfo.getGift_inst_id().equals(goodsInfoVo.getOrderProductId())){
						giftInfo.setSeries_num(goodsInfoVo.getTerminalImei());
						giftInfo.setDb_action(ConstsCore.DB_ACTION_UPDATE);
						giftInfo.setIs_dirty(ConstsCore.IS_DIRTY_YES);
						giftInfo.store();
					}
				}
			}
		}
		//华盛及B2B订单批量更新串号及刷新缓存
		if(isBatchUpdate && batchMap.size() > 0){
			//更新串号
			this.baseDaoSupport.batchExecuteByListMap(updateTerminalImeiSql, batchMap);
			//刷新订单树
			CommonDataFactory.getInstance().updateOrderTree(order_id);
		}
		if(!StringUtils.isEmpty(terminal_num)){
			prodBusis = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderItemBusiRequests().get(0).getOrderItemProdBusiRequests();
			for(OrderItemProdBusiRequest prodBusi : prodBusis){
				OrderItemProdExtBusiRequest prodExt = prodBusi.getOrderItemProdExtBusiRequest();
				prodExt.setTerminal_num(terminal_num);
				prodExt.setDb_action(ConstsCore.DB_ACTION_UPDATE);
				prodExt.setIs_dirty(ConstsCore.IS_DIRTY_YES);
				prodExt.store();
			}
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[]{AttrConsts.TERMINAL_NUM}, new String[]{terminal_num});
		}
		resp.setActiveNo(((SynSerialNumWMSReq)req).getActiveNo());
		resp.setError_code(EcsOrderConsts.WMS_INF_RESP_CODE_0000);
		resp.setError_msg("货品信息接收成功");
		result.setResponse(resp);
		
		//如果已经标记为缺货异常，则恢复订单异常
		String wms_status = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.WMS_STATUS);
		if(EcsOrderConsts.ORDER_STATUS_WMS_1.equals(wms_status)){
			OrderExtBusiRequest orderExtBusiReq = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest();
			BusiCompRequest busi = new BusiCompRequest();
			Map queryParams = new HashMap();
			queryParams.put("order_id", order_id);
			queryParams.put(EcsOrderConsts.EXCEPTION_FROM, EcsOrderConsts.EXCEPTION_FROM_ORD);
			queryParams.put(EcsOrderConsts.EXCEPTION_TYPE, orderExtBusiReq.getException_type());
			queryParams.put(EcsOrderConsts.EXCEPTION_REMARK, orderExtBusiReq.getException_desc());
			busi.setEnginePath("zteCommonTraceRule.restorationException");
			busi.setOrder_id(order_id);
			busi.setQueryParams(queryParams);
			try{
				BusiCompResponse busiResp = CommonDataFactory.getInstance().execBusiComp(busi);
				if(!EcsOrderConsts.RULE_EXE_FLAG_SUCC.equals(busiResp.getError_code())){
					result.setError_code("-9999");
					result.setError_msg(busiResp.getError_msg());
				}
			}catch(Exception e){
				result.setError_code("-9999");
				result.setError_msg("异常恢复失败");
			}
		}
		return result;
	}
	
	//缺货登记
	@SuppressWarnings("unchecked")
	private BusiDealResult recStockStatus(Map params, ZteRequest req) throws Exception{
		BusiDealResult result = new BusiDealResult();
		NotifyStatusFromWMSOrderInfoVo v = ((NotifyOrderStatusFromWMSReq)req).getOrderInfo();
		CommonDataFactory.getInstance().updateAttrFieldValue(v.getOrderId(), 
				new String[]{AttrConsts.ABNORMAL_STATUS, AttrConsts.WMS_STATUS}, 
				new String[]{EcsOrderConsts.EXCEPTION_FROM_WMS, v.getStatus()});
		
		String order_id = ((NotifyOrderStatusFromWMSReq)req).getOrderInfo().getOrderId();
		String wms_status = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.WMS_STATUS);
		String abnormal_type = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest().getAbnormal_type();
		if(v.getStatus().equals(EcsOrderConsts.ORDER_STATUS_WMS_1) && EcsOrderConsts.ORDER_ABNORMAL_TYPE_0.equals(abnormal_type)){
			//调用订单异常标记组件
			BusiCompRequest bcr=new BusiCompRequest();
			bcr.setEnginePath("zteCommonTraceRule.markedException");
			bcr.setOrder_id(v.getOrderId());
			params.put(EcsOrderConsts.EXCEPTION_FROM, EcsOrderConsts.EXCEPTION_FROM_WMS);
			bcr.setQueryParams(params);
			CommonDataFactory.getInstance().execBusiComp(bcr);
			
			result.setError_code("-99999");
			result.setError_msg("WMS缺货");
		}
		else if(EcsOrderConsts.ORDER_STATUS_WMS_1.equals(wms_status) && EcsOrderConsts.ORDER_STATUS_WMS_2.equals(v.getStatus())){
			//如果标记了缺货异常、并接收到了WMS缺货恢复通知，则恢复异常
			OrderExtBusiRequest orderExtBusiReq = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest();
			BusiCompRequest busi = new BusiCompRequest();
			Map queryParams = new HashMap();
			queryParams.put("order_id", order_id);
			queryParams.put(EcsOrderConsts.EXCEPTION_FROM, EcsOrderConsts.EXCEPTION_FROM_ORD);
			queryParams.put(EcsOrderConsts.EXCEPTION_TYPE, orderExtBusiReq.getException_type());
			queryParams.put(EcsOrderConsts.EXCEPTION_REMARK, orderExtBusiReq.getException_desc());
			busi.setEnginePath("zteCommonTraceRule.restorationException");
			busi.setOrder_id(order_id);
			busi.setQueryParams(queryParams);
			try{
				BusiCompResponse busiResp = CommonDataFactory.getInstance().execBusiComp(busi);
				if(!EcsOrderConsts.RULE_EXE_FLAG_SUCC.equals(busiResp.getError_code())){
					result.setError_code("-9999");
					result.setError_msg(busiResp.getError_msg());
				}
			}catch(Exception e){
				result.setError_code("-9999");
				result.setError_msg("异常恢复失败");
			}
		}
		NotifyOrderStatusFromWMSResp resp = new NotifyOrderStatusFromWMSResp();
		resp.setError_code(EcsOrderConsts.WMS_INF_RESP_CODE_0000);
		result.setResponse(resp);
		return result;
	}

	@Override
	/**
	 * 正常终端变更（预占、查询操作（对当前串号）
	 * @param order_id
	 * @return
	 * @throws ApiBusiException 
	 */
	public BusiDealResult synQueryTmResourceInfo(String order_id,String occupiedFlag) throws ApiBusiException  {
		BusiDealResult result = new BusiDealResult();
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		TerminalStatusQueryChanageReq req = new TerminalStatusQueryChanageReq();
		req.setNotNeedReqStrOrderId(order_id);
		req.setOperFlag(occupiedFlag);

		OrderTreeBusiRequest orderTree= CommonDataFactory.getInstance().getOrderTree(order_id);
		List<AttrTmResourceInfoBusiRequest> oldlist = orderTree.getTmResourceInfoBusiRequests();
		String orderMode = orderTree.getOrderExtBusiRequest().getOrder_model();
		
		TerminalStatusQueryChanageResp infResp = client.execute(req,TerminalStatusQueryChanageResp.class);
		
		//为了方便处理，把200接口成功响应的各种返回状态码解析出来，设置到code里
		if(StringUtils.equals(infResp.getError_code(), EcsOrderConsts.AOP_HTTP_STATUS_CODE_200+"")){
			ResourcesRspVo rspvo = infResp.getResourcesRsp().get(0);
			infResp.setCode(rspvo.getRscStateCode());
			infResp.setDetail(rspvo.getRscStateDesc());
		}
		
		//经过处理后，接口报文的code，只有code=0000才算执行成功
		if(StringUtils.equals(infResp.getCode(), EcsOrderConsts.AOP_TERMI_0000) ){	
			//报文返回的是资源列表,我们只去第一个
			ResourcesRspVo rspvo = infResp.getResourcesRsp().get(0);
			
			result.setError_code(EcsOrderConsts.AOP_SUCCESS_0000);
			result.setError_msg("成功");						

			AttrTmResourceInfoBusiRequest vo = new AttrTmResourceInfoBusiRequest();
			if(oldlist==null || oldlist.size()==0){//无则新增
				vo.setTm_resource_id(daoSupport.getSequences("ES_ATTR_TERMINAL_EXT_SEQ", "1", 18));
				vo.setOccupied_essId(req.getOperatorId());
				vo.setOperation_status(occupiedFlag);
				vo.setOrder_id(order_id);	
				vo.setDb_action(ConstsCore.DB_ACTION_INSERT);	
				vo.setIs_dirty(ConstsCore.IS_DIRTY_YES);						
			}else{
				vo = oldlist.get(0);
				vo.setOccupied_essId(req.getOperatorId());
				vo.setOperation_status(occupiedFlag);
				vo.setDb_action(ConstsCore.DB_ACTION_UPDATE);	
				vo.setIs_dirty(ConstsCore.IS_DIRTY_YES);	
			}
			
			//将接口数据拷贝到终端vo中
			vo.setTerminal_type(rspvo.getTerminalType());
			vo.setTerminal_t_sub_type(rspvo.getTerminalTSubType());
			vo.setService_number(rspvo.getServiceNumber());
			vo.setSale_price(rspvo.getSalePrice());
			vo.setRscstate_desc(rspvo.getRscStateDesc());
			vo.setRscstate_code(rspvo.getRscStateCode());
			vo.setResources_type(rspvo.getResourcesType());
			vo.setResources_supply_corp(rspvo.getResourcesSupplyCorp());
			vo.setResources_src_name(rspvo.getResourcesSrcName());
			vo.setResources_src_code(rspvo.getResourcesSrcCode());
			vo.setResources_service_corp(rspvo.getResourcesServiceCorp());
			vo.setResources_model_name(rspvo.getResourcesModelName());
			vo.setResources_model_code(rspvo.getResourcesModelCode());
			vo.setResources_color(rspvo.getResourcesColor());
			vo.setResources_code(rspvo.getResourcesCode());
			vo.setResources_brand_name(rspvo.getResourcesBrandName());
			vo.setResources_brand_code(rspvo.getResourcesBrandCode());
			vo.setReserva_price(rspvo.getReservaPrice());
			vo.setRes_rele(rspvo.getResRele());
			if(rspvo.getProductActivityInfo().size()>0){//活动信息拼成json串保存
				vo.setProduct_activity_info(JsonUtil.toJson(rspvo.getProductActivityInfo()));
			}
			if(rspvo.getPara()!=null){
				vo.setPara(JsonUtil.toJson(rspvo.getPara()));
			}
			vo.setOrg_device_brand_code(rspvo.getOrgDeviceBrandCode());
			vo.setMachine_type_name(rspvo.getMachineTypeName());
			vo.setMachine_type_code(rspvo.getMachineTypeCode());
			vo.setC_cost(rspvo.getCost());
			vo.setCard_price(rspvo.getCardPrice());

			vo.store();
			
			//预占成功
			if(StringUtils.equals(occupiedFlag, EcsOrderConsts.AOP_TERMINAL_OCCUPIEDFLAG_1)){
				//保存旧资源串码
				CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[]{AttrConsts.OLD_TERMINAL_NUM}, 
					new String[]{CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.TERMINAL_NUM)});
			
				//更新拣货状态
				CommonDataFactory.getInstance().updateAttrFieldValue(order_id, 
																new String[]{AttrConsts.ZB_STATUS}, 
																	new String[]{EcsOrderConsts.ZB_ORDER_STATE_N05});
			}
			//裸机、非B2B订单预占费用处理
			String goodsType = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.GOODS_TYPE);
			String orderType = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ORDER_TYPE);
			if(!EcsOrderConsts.ORDER_TYPE_09.equals(orderType) 
					&& EcsOrderConsts.GOODS_TYPE_PHONE.equals(goodsType)){
				List<ResourcesRspVo> resourcesRsp = infResp.getResourcesRsp();
				if(null != resourcesRsp && resourcesRsp.size() > 0){
					dealTerminalFee(order_id, resourcesRsp);
				}
			}
		}else{
			//失败			
			result.setError_code(infResp.getCode());
			result.setError_msg(infResp.getDetail());	
			
			//更新终端状态为预占失败（即便是查询失败，也设置为预占失败状态，因为这个组件的查询是为了比较机型，真正的目的是为了预占）
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, 
																	new String[]{AttrConsts.TERMINAL_OPERATION_STATUS}, 
																	new String[]{EcsOrderConsts.OCCUPIEDFLAG_5});			
			//自动化标记异常
			if(EcsOrderConsts.OPER_MODE_ZD.equals(orderMode)){
				Utils.markException(order_id,false);
			}		
		}
	
		result.setResponse(infResp);
		return result;
	}
	
	/**
	 * 批量预占终端
	 * @param order_id
	 * @param occupiedFlag
	 * @return
	 * @throws ApiBusiException
	 */
	public BusiDealResult termiResourcePreOccBatchAop(String order_id,String occupiedFlag) throws ApiBusiException  {
		BusiDealResult result = new BusiDealResult();
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		
		//获取订单相关信息
		OrderTreeBusiRequest orderTree= CommonDataFactory.getInstance().getOrderTree(order_id);
		
		String orderMode = orderTree.getOrderExtBusiRequest().getOrder_model();
		List<OrderItemExtvtlBusiRequest> orderItemExtvtlBusiRequests = orderTree.getOrderItemExtvtlBusiRequests();
		
		TerminalStatusQueryChanageBatchReq req = new TerminalStatusQueryChanageBatchReq();
		req.setNotNeedReqStrOrderId(order_id);
		req.setOperFlag(occupiedFlag);
		req.setOssOperId(CommonDataFactory.getInstance().getAttrFieldValue(order_id,"oss_operator"));
		
		TerminalStatusQueryChanageResp infResp = client.execute(req,TerminalStatusQueryChanageResp.class);
		
		//初始化返回结果
		result.setError_code(EcsOrderConsts.AOP_SUCCESS_0000);
		result.setError_msg("成功");	
		
		//需要释放的串号
		List<String> releaseCodes = new ArrayList<String>();
		if(StringUtils.equals(infResp.getError_code(), EcsOrderConsts.AOP_HTTP_STATUS_CODE_200+"")){
			for(ResourcesRspVo rspvo : infResp.getResourcesRsp()){				
				//只有code=0000 资源可用 才算执行成功
				if(StringUtils.equals(rspvo.getRscStateCode(), EcsOrderConsts.AOP_TERMI_0000)){	
					//接口机型
					String modecode = rspvo.getMachineTypeCode();
					
					for(OrderItemExtvtlBusiRequest itemvo : orderItemExtvtlBusiRequests){
						if(StringUtils.equals(itemvo.getResources_code(), rspvo.getResourcesCode())){
							itemvo.setEss_oper_id(req.getOssOperId());
							
							//比较接口机型和订单机型
							if(StringUtils.equals(modecode, itemvo.getMachine_type_code())){
								//机型匹配,变更状态,保存信息
								itemvo.setOperation_status(EcsOrderConsts.TERMI_OCCUPIED_SUCC_FLAG);
								itemvo.setOperation_desc("预占成功");
							}else{//机型不符,释放
								releaseCodes.add(rspvo.getResourcesCode());
								
								itemvo.setOperation_status(EcsOrderConsts.TERMI_OCCUPIED_FAIL_FLAG);
								itemvo.setOperation_desc("机型不符！(购买机型："+itemvo.getMachine_type_code()+"，拣货机型："+modecode+")");
							}
							itemvo.setDb_action(ConstsCore.DB_ACTION_UPDATE);	
							itemvo.setIs_dirty(ConstsCore.IS_DIRTY_YES);	
							itemvo.store();
						}
					}					
				}else{
					//失败			
					result.setError_code(rspvo.getRscStateCode());
					result.setError_msg("预占失败,请查看页面提示信息");	
					
					for(OrderItemExtvtlBusiRequest itemvo : orderItemExtvtlBusiRequests){
						if(StringUtils.equals(itemvo.getResources_code(), rspvo.getResourcesCode())){
							itemvo.setEss_oper_id(req.getOssOperId());
							itemvo.setOperation_status(EcsOrderConsts.TERMI_OCCUPIED_FAIL_FLAG);
							itemvo.setOperation_desc(rspvo.getRscStateDesc());
							itemvo.setDb_action(ConstsCore.DB_ACTION_UPDATE);	
							itemvo.setIs_dirty(ConstsCore.IS_DIRTY_YES);	
							itemvo.store();
						}
					}	
				}
				
			}
		}else{
			//失败			
			result.setError_code(infResp.getCode());
			result.setError_msg(infResp.getDetail());
			for(OrderItemExtvtlBusiRequest itemvo : orderItemExtvtlBusiRequests){
				itemvo.setEss_oper_id(req.getOssOperId());
				itemvo.setOperation_status(EcsOrderConsts.TERMI_OCCUPIED_FAIL_FLAG);
				itemvo.setOperation_desc(infResp.getDetail());
				itemvo.setDb_action(ConstsCore.DB_ACTION_UPDATE);	
				itemvo.setIs_dirty(ConstsCore.IS_DIRTY_YES);	
				itemvo.store();
			}
		}
		
		//全部成功才算成功
		if(!StringUtils.equals(result.getError_code(), EcsOrderConsts.AOP_TERMI_0000) ){
			//自动化标记异常
			if(EcsOrderConsts.OPER_MODE_ZD.equals(orderMode)){
				Utils.markException(order_id,false);
			}		
		}else{
			//更新拣货状态
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, 
															    new String[]{AttrConsts.ZB_STATUS}, 
																new String[]{EcsOrderConsts.ZB_ORDER_STATE_N05});
		}
		
		if(releaseCodes.size()>0){
			this.releaseResourceBatch(order_id, EcsOrderConsts.AOP_TERMINAL_OCCUPIEDFLAG_3, releaseCodes);
		}
	
		result.setResponse(infResp);
		return result;
	}
	
	/**
	 * 终端释放操作（对当前串号）
	 * @param order_id
	 * @return
	 * @throws ApiBusiException 
	 */
	public BusiDealResult releaseResource(String order_id,String occupiedFlag) throws ApiBusiException  {
		BusiDealResult result = new BusiDealResult();
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		
		TerminalStatusQueryChanageReq req = new TerminalStatusQueryChanageReq();
		req.setNotNeedReqStrOrderId(order_id);
		req.setOperFlag(occupiedFlag);
		
		TerminalStatusQueryChanageResp infResp = client.execute(req,TerminalStatusQueryChanageResp.class);
		
		//为了方便处理，把200接口成功响应的各种返回状态码解析出来，设置到code里
		if(StringUtils.equals(infResp.getError_code(), EcsOrderConsts.AOP_HTTP_STATUS_CODE_200+"")){
			ResourcesRspVo rspvo = infResp.getResourcesRsp().get(0);
			infResp.setCode(rspvo.getRscStateCode());
			infResp.setDetail(rspvo.getRscStateDesc());
		}
				
		//经过处理后，接口报文的code，只有0000资源可用和非预占状态，才是成功 
		if(StringUtils.equals(infResp.getCode(), EcsOrderConsts.AOP_TERMI_0000) ||
			StringUtils.equals(infResp.getDetail(), "终端的状态不是预占状态不能进行释放")){	
			
			result.setError_code(EcsOrderConsts.AOP_SUCCESS_0000);
			result.setError_msg("成功");						
			
			//更新终端状态
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, 
																	new String[]{AttrConsts.TERMINAL_OPERATION_STATUS}, 
																	new String[]{EcsOrderConsts.OCCUPIEDFLAG_4});
		}else{
			//失败			
			result.setError_code(infResp.getCode());
			result.setError_msg(infResp.getDetail());
			
			//释放失败更新终端状态
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, 
																new String[]{AttrConsts.TERMINAL_OPERATION_STATUS}, 
																new String[]{EcsOrderConsts.OCCUPIEDFLAG_7});
			
			//下面的动作是让释放始终当作成功返回
			result.setError_code(EcsOrderConsts.AOP_SUCCESS_0000);
			result.setError_msg("伪成功");	
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, 
																new String[]{AttrConsts.TERMINAL_OPERATION_STATUS}, 
																new String[]{EcsOrderConsts.OCCUPIEDFLAG_4});	
			//记录数据，供释放定时器调用				
			OrderReleaseRecord record = new OrderReleaseRecord();
			record.setOrder_id(order_id);
			record.setType(EcsOrderConsts.TERMINAL_RELEASE_FAIL);
			record.setSerial_or_num(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.TERMINAL_NUM));
			//List<AttrTmResourceInfoBusiRequest> tmResourceInfoBusiRequests = CommonDataFactory.getInstance().getOrderTree(order_id).getTmResourceInfoBusiRequests();
			if(req.getEssOperInfo()!=null){
				record.setOccupied_essid(req.getOperatorId());
				record.setFirst_req_json(JsonUtil.toJson(req));
			}
			record.setDeal_desc(infResp.getDetail());
			Utils.saveReleaseRecord(record);
		}
		result.setResponse(infResp);
		return result;
	}
	
	/**
	 * 终端释放操作-批量释放
	 * @param order_id
	 * @return
	 * @throws ApiBusiException 
	 */
	public BusiDealResult releaseResourceBatch(String order_id,String occupiedFlag,List<String> releaseCodes) throws ApiBusiException  {
		
		BusiDealResult result = new BusiDealResult();
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());

		//获取订单相关信息
		OrderTreeBusiRequest orderTree= CommonDataFactory.getInstance().getOrderTree(order_id);
		
		List<OrderItemExtvtlBusiRequest> orderItemExtvtlBusiRequests = orderTree.getOrderItemExtvtlBusiRequests();

		TerminalStatusQueryChanageBatchReq req = new TerminalStatusQueryChanageBatchReq();
		req.setNotNeedReqStrOrderId(order_id);
		req.setOperFlag(occupiedFlag);
		req.setReleaseCodes(releaseCodes);
		req.setOssOperId(CommonDataFactory.getInstance().getAttrFieldValue(order_id,"oss_operator"));

		if(releaseCodes==null){
			releaseCodes = new ArrayList<String>();
		}
		
		//获取需要释放的串号
		if(releaseCodes.size()==0){
			for(OrderItemExtvtlBusiRequest vo : orderItemExtvtlBusiRequests){
				if(StringUtils.equals(vo.getOperation_status(), EcsOrderConsts.TERMI_OCCUPIED_SUCC_FLAG) || 
						StringUtils.equals(vo.getOperation_status(), EcsOrderConsts.TERMI_RELEASE_FAIL_FLAG)){
					releaseCodes.add(vo.getResources_code());
				}
			}
		}

		//初始化返回结果
		result.setError_code(EcsOrderConsts.AOP_SUCCESS_0000);
		result.setError_msg("成功");	
		
		if(releaseCodes.size()>0){
			TerminalStatusQueryChanageResp infResp = client.execute(req,TerminalStatusQueryChanageResp.class);		
			
			if(StringUtils.equals(infResp.getError_code(), EcsOrderConsts.AOP_HTTP_STATUS_CODE_200+"")){
				for(ResourcesRspVo rspvo : infResp.getResourcesRsp()){				
					//只有code=0000 资源可用 才算执行成功
					if(StringUtils.equals(rspvo.getRscStateCode(), EcsOrderConsts.AOP_TERMI_0000)){						
						for(OrderItemExtvtlBusiRequest itemvo : orderItemExtvtlBusiRequests){
							if(StringUtils.equals(itemvo.getResources_code(), rspvo.getResourcesCode())){
								itemvo.setEss_oper_id(req.getOssOperId());
								itemvo.setOperation_status(EcsOrderConsts.TERMI_RELEASE_SUCC_FLAG);
								itemvo.setOperation_desc(itemvo.getOperation_desc()+";释放成功！");
								itemvo.setDb_action(ConstsCore.DB_ACTION_UPDATE);	
								itemvo.setIs_dirty(ConstsCore.IS_DIRTY_YES);	
								itemvo.store();
							}
						}					
					}else{					
						//记录数据，供释放定时器调用				
						for(OrderItemExtvtlBusiRequest itemvo : orderItemExtvtlBusiRequests){
							if(StringUtils.equals(itemvo.getResources_code(), rspvo.getResourcesCode())){
								OrderReleaseRecord record = new OrderReleaseRecord();
								record.setOrder_id(order_id);
								record.setType(EcsOrderConsts.TERMINAL_RELEASE_FAIL);
								record.setIs_deal(EcsOrderConsts.NOT_RELEASE_YET);
								record.setSerial_or_num(itemvo.getResources_code());
								record.setOccupied_essid(itemvo.getEss_oper_id());
								record.setFirst_req_json(null);
								record.setDeal_desc(rspvo.getRscStateDesc());
								Utils.saveReleaseRecord(record);							
	
								itemvo.setEss_oper_id(req.getOssOperId());
								itemvo.setOperation_status(EcsOrderConsts.TERMI_RELEASE_SUCC_FLAG);
								itemvo.setOperation_desc(itemvo.getOperation_desc()+";释放伪成功！");
								itemvo.setDb_action(ConstsCore.DB_ACTION_UPDATE);	
								itemvo.setIs_dirty(ConstsCore.IS_DIRTY_YES);	
								itemvo.store();
							}
						}
					}				
				}
			}else{
				//接口失败，释放操作转移到任务表，并记录释放成功		
				for(OrderItemExtvtlBusiRequest itemvo : orderItemExtvtlBusiRequests){
					if(releaseCodes.contains(itemvo.getResources_code())){
						OrderReleaseRecord record = new OrderReleaseRecord();
						record.setOrder_id(order_id);
						record.setType(EcsOrderConsts.TERMINAL_RELEASE_FAIL);
						record.setIs_deal(EcsOrderConsts.NOT_RELEASE_YET);
						record.setSerial_or_num(itemvo.getResources_code());
						record.setOccupied_essid(itemvo.getEss_oper_id());
						record.setFirst_req_json(null);
						record.setDeal_desc(infResp.getDetail());
						Utils.saveReleaseRecord(record);					
	
						itemvo.setEss_oper_id(req.getOssOperId());
						itemvo.setOperation_status(EcsOrderConsts.TERMI_RELEASE_SUCC_FLAG);
						itemvo.setOperation_desc(itemvo.getOperation_desc()+";释放伪成功！");
						itemvo.setDb_action(ConstsCore.DB_ACTION_UPDATE);	
						itemvo.setIs_dirty(ConstsCore.IS_DIRTY_YES);	
						itemvo.store();
					}
				}
			}
			result.setResponse(infResp);
		}
		return result;
	}
	
	/**
	 * 拣货换终端时的释放操作（对旧串号）
	 * @param order_id
	 * @return
	 * @throws ApiBusiException 
	 */
	public BusiDealResult releaseResourceForOldTerminal(String order_id,String occupiedFlag) throws ApiBusiException{		
		BusiDealResult result = new BusiDealResult();
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		TerminalStatusQueryChanageReq req = new TerminalStatusQueryChanageReq();
		req.setNotNeedReqStrOrderId(order_id);
		req.setOperFlag(occupiedFlag);

		//当前串号
		String terminal_num = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.TERMINAL_NUM);
		//旧串号
		String old_terminal_num = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.OLD_TERMINAL_NUM);
		//当新旧不一致时,表示串号更换,则执行释放旧串号;新旧一致时,说明串号未更换,则成功结束规则
		if(!StringUtils.isEmpty(old_terminal_num) && !StringUtils.equals(terminal_num, old_terminal_num)){
			TerminalStatusQueryChanageResp infResp = client.execute(req,TerminalStatusQueryChanageResp.class);
	
			//为了方便处理，把200接口成功响应的各种返回状态码解析出来，设置到code里
			if(StringUtils.equals(infResp.getError_code(), EcsOrderConsts.AOP_HTTP_STATUS_CODE_200+"")){
				ResourcesRspVo rspvo = infResp.getResourcesRsp().get(0);
				infResp.setCode(rspvo.getRscStateCode());
				infResp.setDetail(rspvo.getRscStateDesc());
			}
			
			//经过处理后，接口报文的code，只有0000资源可用和非预占状态，才是成功
			if(StringUtils.equals(infResp.getCode(), EcsOrderConsts.AOP_TERMI_0000) || 
					StringUtils.equals(infResp.getDetail(), "终端的状态不是预占状态不能进行释放")){
				result.setError_code(EcsOrderConsts.AOP_SUCCESS_0000);
				result.setError_msg("成功");		
				
				//更新终端状态，更新为待预占
				CommonDataFactory.getInstance().updateAttrFieldValue(order_id, 
																		new String[]{AttrConsts.TERMINAL_OPERATION_STATUS}, 
																		new String[]{EcsOrderConsts.OCCUPIEDFLAG_0});
			}else{		
				//失败
				result.setError_code(infResp.getCode());
				result.setError_msg(infResp.getDetail());
				
				//释放失败更新终端状态
				CommonDataFactory.getInstance().updateAttrFieldValue(order_id, 
																		new String[]{AttrConsts.TERMINAL_OPERATION_STATUS}, 
																		new String[]{EcsOrderConsts.OCCUPIEDFLAG_99});	
				
				//下面的动作是让释放始终当作成功返回
				result.setError_code(EcsOrderConsts.AOP_SUCCESS_0000);
				result.setError_msg("伪成功");	
				CommonDataFactory.getInstance().updateAttrFieldValue(order_id, 
																		new String[]{AttrConsts.TERMINAL_OPERATION_STATUS}, 
																		new String[]{EcsOrderConsts.OCCUPIEDFLAG_0});	
				//记录数据，供释放定时器调用				
				OrderReleaseRecord record = new OrderReleaseRecord();
				record.setOrder_id(order_id);
				record.setType(EcsOrderConsts.TERMINAL_RELEASE_FAIL);
				
				//这里要给旧终端串号
				record.setSerial_or_num(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.OLD_TERMINAL_NUM));
				List<AttrTmResourceInfoBusiRequest> tmResourceInfoBusiRequests = CommonDataFactory.getInstance().getOrderTree(order_id).getTmResourceInfoBusiRequests();
				record.setOccupied_essid(tmResourceInfoBusiRequests.get(0).getOccupied_essId());
				record.setFirst_req_json(JsonUtil.toJson(req));
				record.setDeal_desc(infResp.getDetail());
				Utils.saveReleaseRecord(record);
			}
			result.setResponse(infResp);
		}else{
			result.setError_code(EcsOrderConsts.AOP_SUCCESS_0000);
			result.setError_msg("成功");	
		}
		return result;
	}
	
	/**
	 * 裸机、非B2B订单预占费用处理
	 * @param order_id
	 * @param resourcesRsp
	 */
	private void dealTerminalFee(String order_id , List<ResourcesRspVo> resourcesRsp){
		//获取订单费用
		List<AttrFeeInfoBusiRequest> oldfeelist = CommonDataFactory.getInstance().getOrderTree(order_id).getFeeInfoBusiRequests();
		//删除旧的开户预提交返回的费用is_aop_fee=1
		for(AttrFeeInfoBusiRequest oldvo : oldfeelist){
			if(StringUtils.equals(oldvo.getIs_aop_fee(), EcsOrderConsts.BASE_YES_FLAG_1)){
				oldvo.setDb_action(ConstsCore.DB_ACTION_DELETE);
				oldvo.setIs_dirty(ConstsCore.IS_DIRTY_YES);
				oldvo.store();//更新到数据库，同时更新到缓存
			}
		}
		for(ResourcesRspVo resVo : resourcesRsp){
			AttrFeeInfoBusiRequest req = new AttrFeeInfoBusiRequest();
			req.setOrder_id(order_id);
			req.setInst_id(order_id);
			req.setFee_inst_id(daoSupport.getSequences("SEQ_ATTR_FEE_INFO", "1", 18));
			req.setIs_aop_fee(EcsOrderConsts.BASE_YES_FLAG_1);
			req.setFee_item_code(EcsOrderConsts.AOP_FEE_ID_96);
			req.setFee_item_name("终端费用");
			req.setFee_category(EcsOrderConsts.AOP_FEE_CATEGORY_6);
			//销售金额
			Double sellPrice = 0d;
			//优惠金额
			Double discountFee = 0d;
			if(!StringUtils.isEmpty(resVo.getSalePrice())){
				sellPrice = Double.parseDouble(resVo.getSalePrice());
			}
			req.setO_fee_num(sellPrice/1000);
			req.setMax_relief(sellPrice/1000);
			req.setDiscount_fee(discountFee/1000);
			req.setDb_action(ConstsCore.DB_ACTION_INSERT);
			req.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			req.setN_fee_num(req.getO_fee_num()-req.getDiscount_fee());
			req.store();//更新到数据库，同时更新到缓存
		}
	}
}
