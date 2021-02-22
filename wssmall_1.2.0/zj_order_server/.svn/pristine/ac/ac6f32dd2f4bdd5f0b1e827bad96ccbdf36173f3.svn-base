package com.ztesoft.net.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import params.order.req.OrderHandleLogsReq;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.base.req.BusiCompRequest;
import zte.net.ecsord.params.base.resp.BusiCompResponse;
import zte.net.ecsord.params.busi.req.OrderDeliveryBusiRequest;
import zte.net.ecsord.params.busi.req.OrderDeliveryItemBusiRequest;
import zte.net.ecsord.params.busi.req.OrderExtBusiRequest;
import zte.net.ecsord.params.busi.req.OrderItemBusiRequest;
import zte.net.ecsord.params.busi.req.OrderItemExtBusiRequest;
import zte.net.ecsord.params.busi.req.OrderItemProdBusiRequest;
import zte.net.ecsord.params.busi.req.OrderItemProdExtBusiRequest;
import zte.net.ecsord.params.busi.req.OrderLockBusiRequest;
import zte.net.ecsord.params.busi.req.OrderRouteLogBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;
import zte.net.ecsord.params.nd.req.DispatchingNumReceivingNDRequset;
import zte.net.ecsord.params.nd.req.NotifyOrderStatuNDRequset;
import zte.net.ecsord.params.nd.req.OrderDealSuccessNDRequset;
import zte.net.ecsord.params.nd.resp.OrderDealSuccessNDResponse;
import zte.net.ecsord.params.nd.vo.DealOrderInfo;
import zte.net.ecsord.params.nd.vo.GoodInfo;
import zte.net.ecsord.params.nd.vo.OrderInfo;
import zte.net.ecsord.params.nd.vo.PostInfo;
import zte.net.ecsord.params.nd.vo.StatuOrderInfo;
import zte.net.ecsord.params.sf.req.ArtificialSelectRequest;
import zte.net.ecsord.params.sf.req.RoutePushServiceRequset;
import zte.net.ecsord.params.sf.resp.ArtificialSelectResponse;
import zte.net.ecsord.params.sf.resp.RoutePushServiceResponse;
import zte.net.ecsord.params.sf.vo.OrderFilterResult;
import zte.net.ecsord.params.sf.vo.WaybillRoute;
import zte.net.ecsord.rule.tache.TacheFact;
import zte.net.params.req.PlanRuleTreeExeReq;
import zte.net.params.resp.PlanRuleTreeExeResp;
import zte.params.ecsord.req.GetOrderByOutTidReq;
import zte.params.ecsord.resp.GetOrderByOutTidResp;

import com.ztesoft.api.ApiBusiException;
import com.ztesoft.api.AppKeyEnum;
import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.common.util.StringUtils;
import com.ztesoft.common.util.date.DateUtil;
import com.ztesoft.ibss.common.util.Const;
import com.ztesoft.net.cache.common.CacheFactory;
import com.ztesoft.net.cache.common.INetCache;
import com.ztesoft.net.consts.OrderStatus;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.consts.ZjEcsOrderConsts;
import com.ztesoft.net.mall.core.model.CoQueue;
import com.ztesoft.net.mall.core.service.ICoQueueManager;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.model.SDSStatusLogVO;
import com.ztesoft.net.rule.util.RuleFlowUtil;
import com.ztesoft.net.service.IEcsOrdManager;
import com.ztesoft.net.service.IOrderFlowManager;
import com.ztesoft.net.service.IOrderSDSModelManager;
import com.ztesoft.net.sqls.SF;

import commons.CommonTools;
import consts.ConstsCore;

/**
 * 闪电送业务处理实现类
 * @author 吴家勇
 * 2015.01.20
 */
public class OrderSDSModelManager extends BaseSupport implements IOrderSDSModelManager {

	@Resource
	private IEcsOrdManager ecsOrdManager;
	@Resource
	private ICoQueueManager coQueueManager;
	@Resource
	private IOrderFlowManager ordFlowManager;
	
	public void insertLogByVO(SDSStatusLogVO sdsStatusLogVO) {
		this.baseDaoSupport.insert("sds_status_log", sdsStatusLogVO);
	}
	
	public BusiCompResponse lockOrderByWlUser(DispatchingNumReceivingNDRequset request) throws ApiBusiException{
		BusiCompResponse resp = new BusiCompResponse();
		SDSStatusLogVO sdsStatusLog = new SDSStatusLogVO();
		sdsStatusLog.setSds_id(Long.valueOf(DateUtil.currentTimstamp()));
		sdsStatusLog.setCreate_time(DateUtil.currentDateTime());
		sdsStatusLog.setRequest_initiator("ND");
		if(null!=request){
			String dealAgent = request.getDealAgent();
			String opid = request.getOperCode();
			if(EcsOrderConsts.SDS_COMP_SF.equals(dealAgent)){
				opid = "SF"+opid;
			}
			String orderId = request.getOrderId();
			sdsStatusLog.setOrig_order_id(request.getOrigOrderId());
			sdsStatusLog.setCreate_user(opid);
			sdsStatusLog.setWl_comp_code(EcsOrderConsts.SDS_COMP_ND);
			sdsStatusLog.setWl_comp_name("南都");
			unLockOrder(orderId);
			boolean isLock = ecsOrdManager.orderLockByWl(orderId, opid);
			if(isLock){
				CommonDataFactory.getInstance().updateAttrFieldValue(orderId,new String[]{AttrConsts.ND_STATUS},new String[]{EcsOrderConsts.SDS_STATUS_04});
				setOrderVisible(orderId);
				resp.setError_code("0");
				resp.setError_msg("订单锁定成功");
				sdsStatusLog.setLight_ning_status(EcsOrderConsts.SDS_STATUS_04);
				sdsStatusLog.setStatus_name("派工号接收");
				sdsStatusLog.setRe_code("0");
				sdsStatusLog.setRe_message("订单锁定成功");
			}else{
				resp.setError_code("0001");
				resp.setError_msg("工号"+opid+"不存在");
				sdsStatusLog.setRe_code("0001");
				sdsStatusLog.setRe_message("工号"+opid+"不存在");
				CommonDataFactory.getInstance().updateAttrFieldValue(orderId,new String[]{AttrConsts.ND_STATUS_DESC},new String[]{"工号"+opid+"不存在"});
			}
		}else{
			CommonTools.addBusiError(ConstsCore.RULE_ERROR_FAIL, "派工号接口入参为空");
		}
		this.insertLogByVO(sdsStatusLog);
		return resp;
	}
	
	public BusiCompResponse sfOrderRespSyn(ArtificialSelectRequest request) throws ApiBusiException{
		BusiCompResponse resp = new BusiCompResponse();
		StringBuffer failSB = new StringBuffer();
		StringBuffer succSB = new StringBuffer();
		SDSStatusLogVO sdsStatusLog = new SDSStatusLogVO();//记录
		sdsStatusLog.setSds_id(Long.valueOf(DateUtil.currentTimstamp()));
		sdsStatusLog.setCreate_time(DateUtil.currentDateTime());
		sdsStatusLog.setRequest_initiator("SF");
		ArtificialSelectResponse response = new ArtificialSelectResponse();
		for(OrderFilterResult filterResult : request.getOrderFilterResultList()){
			//String outOrderId = filterResult.getOrderid();
			String order_id=filterResult.getOrderid();
			try{
				sdsStatusLog.setOrig_order_id(order_id);	//内部订单号，不是外部订单号			
				sdsStatusLog.setWl_comp_code(EcsOrderConsts.SDS_COMP_SF);
				sdsStatusLog.setWl_comp_name("顺丰");
				OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
				if(orderTree==null){//订单不存在
					failSB.append(order_id+",");
					continue;
				}
				int resultCode = filterResult.getFilterResult();
				if(2==resultCode){//可收派
					String opid = filterResult.getDestCode();
					opid = "SF"+opid;
					//opid="GZ000254";//测试代码
					sdsStatusLog.setCreate_user(opid);
					unLockOrder(order_id);
					boolean isLock = ecsOrdManager.orderLockByWl(order_id, opid);
					if(isLock){
						CommonDataFactory.getInstance().updateAttrFieldValue(order_id,new String[]{AttrConsts.SF_STATUS},new String[]{EcsOrderConsts.SDS_STATUS_04});
						succSB.append(order_id+",");
						sdsStatusLog.setLight_ning_status(EcsOrderConsts.SDS_STATUS_04);
						sdsStatusLog.setStatus_name("派工号接收");
						sdsStatusLog.setRe_code("0");
						sdsStatusLog.setRe_message("订单锁定成功");
					}else{
						failSB.append(order_id+",");
						sdsStatusLog.setRe_code("0001");
						sdsStatusLog.setRe_message("工号"+opid+"不存在");
					}						
				}else if(3==resultCode){//不可以收派
					CommonDataFactory.getInstance().updateAttrFieldValue(order_id, 
							new String[]{AttrConsts.ORDER_MODEL,AttrConsts.SHIPPING_QUICK, AttrConsts.SF_STATUS,AttrConsts.SF_STATUS_DESC}, 
							new String[]{EcsOrderConsts.ORDER_MODEL_02, EcsOrderConsts.SHIPPING_QUICK_02, EcsOrderConsts.SDS_STATUS_03,filterResult.getRemark()});
					succSB.append(order_id+",");
					sdsStatusLog.setLight_ning_status(EcsOrderConsts.SDS_STATUS_03);
					sdsStatusLog.setStatus_name("拒绝接受");
				}
				setOrderVisible(order_id);
			}catch(Exception e){
				failSB.append(order_id+",");
				e.printStackTrace();
				//CommonTools.addBusiError(ConstsCore.RULE_ERROR_FAIL, e.getMessage());				
			}
		}
		this.insertLogByVO(sdsStatusLog);
		resp.setError_code("0");
		resp.setError_msg("订单处理成功");
		if(failSB.length()>2){
			response.setOrderid_error(failSB.substring(0, failSB.length()-1));
		}
		if(succSB.length()>2){
			response.setOrderid(succSB.substring(0, succSB.length()-1));
		}
		resp.setResponse(response);
		resp.setError_code("0");
		resp.setError_msg("派工号接收处理成功");
		return resp;
	}
	
	public BusiCompResponse sfRoutePush(RoutePushServiceRequset req) throws ApiBusiException{
		BusiCompResponse resp = new BusiCompResponse();
		RoutePushServiceResponse response = new RoutePushServiceResponse();
		StringBuffer failSB = new StringBuffer();
		StringBuffer succSB = new StringBuffer();		
		try{
			for (WaybillRoute waybillRoute : req.getWaybillRoute()) {
				String order_id = waybillRoute.getOrderid();
				SDSStatusLogVO sdsStatusLog = new SDSStatusLogVO();
				sdsStatusLog.setSds_id(Long.valueOf(DateUtil.currentTimstamp()));
				sdsStatusLog.setCreate_time(DateUtil.currentDateTime());
				sdsStatusLog.setRequest_initiator("SF");
				sdsStatusLog.setOrig_order_id(order_id);
				sdsStatusLog.setCreate_user(waybillRoute.getAcceptAddress());
				sdsStatusLog.setWl_comp_code(EcsOrderConsts.SDS_COMP_SF);
				sdsStatusLog.setWl_comp_name("顺丰");
				sdsStatusLog.setRe_code(waybillRoute.getOpCode());
				sdsStatusLog.setRe_message(waybillRoute.getRemark());
				OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
				if(null==orderTree){
					failSB.append(order_id+",");
					continue;
					//throw new Exception("不存在订单号"+order_id);
				}
				//已接收过的路由不再重复接收
				//continue;
				// 更新物流签收状态
				updateOrderDelivery(waybillRoute, orderTree);
				//状态 
				String status = waybillRoute.getOpCode();
				String statusDesc = waybillRoute.getRemark();	
				
				//保存顺丰路由明细到表
				OrderRouteLogBusiRequest route = new OrderRouteLogBusiRequest();
				String order_route_id = baseDaoSupport.getSequences("ES_ORDER_ROUTE_LOG_SEQ");
				route.setId(order_route_id);
				route.setOrder_id(order_id);
				route.setRoute_id(waybillRoute.getId());
				route.setMail_no(waybillRoute.getMailno());
				route.setOp_code(waybillRoute.getOpCode());
				route.setRoute_acceptime(waybillRoute.getAcceptTime());
				route.setRoute_acceptadress(waybillRoute.getAcceptAddress());
				route.setRoute_remark(waybillRoute.getRemark());
				route.setCreate_time(DateUtil.currentDateTime());
				route.setCreate_user("root");
				route.setSource_from(ManagerUtils.getSourceFrom());
				route.setDb_action(ConstsCore.DB_ACTION_INSERT);
				route.setIs_dirty(ConstsCore.IS_DIRTY_YES);
				route.store();
				
				//总部订单，判断是否顺丰签收成功，如果是，则添加定时任务到队列
				if(StringUtils.equals(status, EcsOrderConsts.SF_DEAL_STATUS_80) && StringUtils.equals(orderTree.getOrderExtBusiRequest().getOrder_from(), EcsOrderConsts.ORDER_FROM_10003)){
					try {					
						// 添加到定时任务队列
						CoQueue queBak = new CoQueue();
						queBak.setService_code("CO_ROUTE_NOTIFY_ZB");	
						queBak.setCo_id("");
						queBak.setCo_name("订单路由明细同步总部商城");
						queBak.setObject_id(order_id);
						queBak.setObject_type("DINGDAN");
						queBak.setStatus(Consts.CO_QUEUE_STATUS_WFS);
						coQueueManager.add(queBak);	
						//数据归档
						insertHis(order_id);
					} catch (Exception e) {
						e.printStackTrace();
						CommonTools.addBusiError("-9999","es_co_queue定时任务插入失败;"+e.getMessage());
					}
				}
				// 自动回单
				autoReceipt(waybillRoute, orderTree);
				/*String sdsStatus = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.SF_STATUS);
				if (EcsOrderConsts.SDS_STATUS_04.equals(sdsStatus)) {// 已派工号接收--有可能没锁单成功
					if (waybillRoute.getMailno().startsWith(EcsOrderConsts.RETURN_TRACKING_NO_START_WITH)) {
						doOrderByStatus(order_id, status, statusDesc, AttrConsts.BACK_STATUS, sdsStatusLog);
					}
					else {
						doOrderByStatus(order_id, status, statusDesc, AttrConsts.SF_STATUS, sdsStatusLog);
					}
					succSB.append(order_id + ",");
					this.insertLogByVO(sdsStatusLog);
				} else {
					continue;
				}*/
			}
			resp.setError_code("0");
			resp.setError_msg("路由信息处理成功");
		}catch(Exception e){
			e.printStackTrace();
			resp.setError_code("0001");
			resp.setError_msg("路由信息处理异常："+e.getMessage());
			//CommonTools.addBusiError(ConstsCore.RULE_ERROR_FAIL, e.getMessage());	
		}finally{
			if(failSB.length()>2){
				response.setId_error(failSB.substring(0, failSB.length()-1));
			}
			if(succSB.length()>2){
				response.setId(succSB.substring(0, succSB.length()-1));
			}
			resp.setResponse(response);
		}	
		return resp;
	}
	
	private void insertHis(String order_id){
		String insertSql = " insert into es_order_route_log_his(id,order_id,route_id,mail_no,op_code,route_acceptime, "
				+ " route_acceptadress,route_remark,create_time,create_user,source_from,effect) "
				+ " select id,order_id,route_id,mail_no,op_code,route_acceptime,route_acceptadress,route_remark, "
				+ " create_time,create_user,source_from,effect from es_order_route_log "
				+ " where order_id = ? and op_code <> '80' ";
		String delSql = " delete from es_order_route_log t where t.order_id = ? and op_code <> '80' ";
		this.baseDaoSupport.execute(insertSql, order_id);
		this.baseDaoSupport.execute(delSql, order_id);
	}
	
	private void updateOrderDelivery(WaybillRoute waybillRoute, OrderTreeBusiRequest orderTree) {
		List<OrderDeliveryBusiRequest> deliveryList = orderTree.getOrderDeliveryBusiRequests();
		if (deliveryList != null && !deliveryList.isEmpty()) {
			for (OrderDeliveryBusiRequest orderDeliveryBusiRequest : deliveryList) {
				if (waybillRoute.getMailno().equals(orderDeliveryBusiRequest.getLogi_no()) ) {
					if (EcsOrderConsts.SIGN_STATUS_1.equals(orderDeliveryBusiRequest.getSign_status())) {
						break;
					}
					if (EcsOrderConsts.SF_DEAL_STATUS_80.equals(waybillRoute.getOpCode())) {
						orderDeliveryBusiRequest.setSign_status(EcsOrderConsts.SIGN_STATUS_1);
					}else if(!EcsOrderConsts.SIGN_STATUS_1.equals(orderDeliveryBusiRequest.getSign_status())){//已签收状态不允许变回未签收
						orderDeliveryBusiRequest.setSign_status(EcsOrderConsts.SIGN_STATUS_0);
					}
				}
				else if (waybillRoute.getMailno().equals(orderDeliveryBusiRequest.getReceipt_no())) {
					if (EcsOrderConsts.SIGN_STATUS_1.equals(orderDeliveryBusiRequest.getReceipt_status())) {
						break;
					}
					if (EcsOrderConsts.SF_DEAL_STATUS_80.equals(waybillRoute.getOpCode())) {
						orderDeliveryBusiRequest.setReceipt_status(EcsOrderConsts.SIGN_STATUS_1);
					}else if(!EcsOrderConsts.SIGN_STATUS_1.equals(orderDeliveryBusiRequest.getReceipt_status())){//已签收状态不允许变回未签收
						orderDeliveryBusiRequest.setReceipt_status(EcsOrderConsts.SIGN_STATUS_0);
					}
				}
				if (waybillRoute.getMailno().equals(orderDeliveryBusiRequest.getLogi_no()) || waybillRoute.getMailno().equals(orderDeliveryBusiRequest.getReceipt_no())) {
					orderDeliveryBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
					orderDeliveryBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
					orderDeliveryBusiRequest.store();
				}
			}
		}
	}
	
	/**
	 * 满足条件时，自动执行回单方案
	 * @param waybillRoute
	 * @param orderTree
	 */
	private void autoReceipt(WaybillRoute waybillRoute, OrderTreeBusiRequest orderTree){//此方法尽量使逻辑易懂，牺牲一点效率
		String order_id = orderTree.getOrder_id();
		OrderDeliveryBusiRequest deliver = null;
		List<OrderDeliveryBusiRequest> deliveryList = orderTree.getOrderDeliveryBusiRequests();
		for(OrderDeliveryBusiRequest del : deliveryList){
			if(EcsOrderConsts.LOGIS_NORMAL.equals(del.getDelivery_type())){//获得正常发货的记录
				deliver = del;
			}
		}
		
		String flow_trace_id = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest().getFlow_trace_id();//订单环节
		String order_from = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ORDER_FROM);//订单来源
		String goods_type = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.GOODS_TYPE);//商品类型
		String order_type = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ORDER_TYPE);//订单类型
		String later_active_flag = orderTree.getOrderRealNameInfoBusiRequest().getLater_active_flag();
		if(EcsOrderConsts.SF_DEAL_STATUS_80.equals(waybillRoute.getOpCode())//用户签收
				&&null!=deliver&&StringUtils.isNotEmpty(waybillRoute.getMailno())&&StringUtils.equals(waybillRoute.getMailno(), deliver.getLogi_no())//正常发货记录的运单号
				&&EcsOrderConsts.DIC_ORDER_NODE_L.equals(flow_trace_id) && EcsOrderConsts.NO_DEFAULT_VALUE.equals(later_active_flag)
				&& !order_from.equals(EcsOrderConsts.ORDER_FROM_10012)){//L订单归档环节，前向实名制,订单来源为天机的订单不进行归档操作
			//前向实名制，根据签收结果回单
			PlanRuleTreeExeReq req = new PlanRuleTreeExeReq();
			TacheFact fact = new TacheFact();
			fact.setOrder_id(orderTree.getOrder_id());
			req.setPlan_id(EcsOrderConsts.ORDER_ARCHIVE_PLAN);
			req.setFact(fact);
			req.setDeleteLogs(false);//不删除日志，不允许二次操作
			req.setAuto_exe(-1);
			PlanRuleTreeExeResp planResp = CommonDataFactory.getInstance().exePlanRuleTree(req);
		}
	}
	
	public BusiCompResponse statusNoticFromND(NotifyOrderStatuNDRequset request) throws ApiBusiException{
		BusiCompResponse resp = new BusiCompResponse();
		List<StatuOrderInfo> statusList = request.getOrderInfo();
		for(StatuOrderInfo statusInfo : statusList){
			try{
				String status = statusInfo.getStatus();
				String outOrderId = statusInfo.getOrigOrderId();
				SDSStatusLogVO sdsStatusLog = new SDSStatusLogVO();
				sdsStatusLog.setSds_id(Long.valueOf(DateUtil.currentTimstamp()));
				sdsStatusLog.setCreate_time(DateUtil.currentDateTime());
				sdsStatusLog.setRequest_initiator("ND");
				sdsStatusLog.setOrig_order_id(outOrderId);
				sdsStatusLog.setWl_comp_code(EcsOrderConsts.SDS_COMP_ND);
				sdsStatusLog.setWl_comp_name("南都");
				String order_id = getOrderIdByOut(outOrderId);
				if("".equals(order_id)){
					throw new Exception("订单号"+outOrderId+"不存在");
				}
				String sdsStatus = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ND_STATUS);
				if(status.equals(sdsStatus)){
					continue;
				}else if(EcsOrderConsts.SDS_STATUS_04.equals(sdsStatus)||EcsOrderConsts.SDS_STATUS_03.equals(status)){
					doOrderByStatus(order_id,status,statusInfo.getDesc(),AttrConsts.ND_STATUS,sdsStatusLog);
					this.insertLogByVO(sdsStatusLog);
				}else{
					continue;
				}
			}catch(Exception e){
				e.printStackTrace();
				resp.setError_code("9999");
				resp.setError_msg("状态同步失败："+e.getMessage());
				break;
			}
		}
		resp.setError_code("0");
		resp.setError_msg("状态同步成功");
		return resp;
	}
	
	public BusiCompResponse ndFinishNotic(OrderDealSuccessNDRequset request) throws ApiBusiException{
		BusiCompResponse resp = new BusiCompResponse();
		OrderDealSuccessNDResponse response = new OrderDealSuccessNDResponse();
		List<DealOrderInfo> dealorderList = new ArrayList<DealOrderInfo>();
		SDSStatusLogVO sdsStatusLog = new SDSStatusLogVO();
		sdsStatusLog.setSds_id(Long.valueOf(DateUtil.currentTimstamp()));
		sdsStatusLog.setCreate_time(DateUtil.currentDateTime());
		sdsStatusLog.setRequest_initiator("ND");
		sdsStatusLog.setWl_comp_code(EcsOrderConsts.SDS_COMP_ND);
		sdsStatusLog.setWl_comp_name("南都");
		try{
			List<OrderInfo> orderInfoList = request.getOrderInfo();			
			for(OrderInfo orderInfo : orderInfoList){
				DealOrderInfo dealOrder = new DealOrderInfo();
				String outOrderId = orderInfo.getOrigOrderId();
				if(null==sdsStatusLog.getOrig_order_id()||"".equals(sdsStatusLog.getOrig_order_id())){
					sdsStatusLog.setOrig_order_id(outOrderId);
				}
				OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTreeByOutId(outOrderId);
				dealOrder.setOrigOrderId(outOrderId);
				if(null==orderTree){					
					dealOrder.setRespOrderStatus("0001");
					dealOrder.setRespOrderDesc("订单号"+outOrderId+"不存在");
					dealorderList.add(dealOrder);
					continue;
				}
				String order_id = orderTree.getOrder_id();
				//添加补寄内容
				String reissueInfo = orderInfo.getReissueInfo();
				if(!"".equals(reissueInfo)){
					
					//TO
					OrderDeliveryBusiRequest deliveryBusi = orderTree.getOrderDeliveryBusiRequests().get(0);
					OrderDeliveryItemBusiRequest deliveryItemBusi = new OrderDeliveryItemBusiRequest();
					deliveryItemBusi.setDelivery_id(deliveryBusi.getDelivery_id());
					deliveryItemBusi.setName(reissueInfo);
					deliveryItemBusi.setNum(1);
					deliveryItemBusi.setOrder_id(order_id);
					deliveryItemBusi.setItemtype(OrderStatus.DELIVERY_ITEM_TYPE_3);
					deliveryItemBusi.setIs_dirty(ConstsCore.IS_DIRTY_YES);
					deliveryItemBusi.setDb_action(ConstsCore.DB_ACTION_INSERT);
					deliveryItemBusi.store();
				}
				//更新商品信息
				List<GoodInfo> goodInfoList = orderInfo.getGoodInfo();
				for(GoodInfo good : goodInfoList){
					String seriesNumSw = good.getSeriesNumSw();
					String seriesNumCard = good.getSeriesNumCard();
					OrderItemBusiRequest itemBusiRequest = orderTree.getOrderItemBusiRequests().get(0);
					List<OrderItemProdBusiRequest> prodBusisList = itemBusiRequest.getOrderItemProdBusiRequests();
					for(OrderItemProdBusiRequest prodBusis : prodBusisList){
						OrderItemProdExtBusiRequest prodExtBusi = prodBusis.getOrderItemProdExtBusiRequest();
						if(null!=seriesNumSw&&!"".equals(seriesNumSw)){
							if(EcsOrderConsts.PRODUCT_TYPE_CODE_TERMINAL.equals(prodBusis.getProd_spec_type_code())
									&&"".equals(prodExtBusi.getTerminal_num())){								
								prodExtBusi.setTerminal_num(seriesNumSw);
							}else{
								continue;
							}
						}else if(null!=seriesNumCard&&!"".equals(seriesNumCard)){
							if(EcsOrderConsts.PRODUCT_TYPE_CODE_OFFER.equals(prodBusis.getProd_spec_type_code())
									&&"".equals(prodExtBusi.getICCID())){
								prodExtBusi.setICCID(seriesNumCard);
							}else{
								continue;
							}
						}else{
							continue;
						}
						prodExtBusi.setIs_dirty(ConstsCore.IS_DIRTY_YES);
						prodExtBusi.setDb_action(ConstsCore.DB_ACTION_UPDATE);
						prodExtBusi.store();
					}
					OrderItemExtBusiRequest itemExtBusiRequest = itemBusiRequest.getOrderItemExtBusiRequest();
					boolean flag = false;
					if("".equals(itemExtBusiRequest.getPhone_num())){
						itemExtBusiRequest.setPhone_num(good.getMobileTel());
						flag = true;
					}
					if("".equals(itemExtBusiRequest.getBss_account_time())){
						itemExtBusiRequest.setBss_account_time(good.getBssAccountTime());
						flag = true;
					}
					if("".equals(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.BSS_OPERATOR))){
						CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[]{AttrConsts.BSS_OPERATOR}, new String[]{good.getBssAccount()});
					}					
					if(flag){
						itemExtBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
						itemExtBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
						itemExtBusiRequest.store();
					}
				}
				//更新物流信息
				PostInfo postInfo = orderInfo.getPostInfo().get(0);
				OrderDeliveryBusiRequest deliveryBusi = orderTree.getOrderDeliveryBusiRequests().get(0);
				deliveryBusi.setShipping_company(postInfo.getPostId());
				deliveryBusi.setLogi_receiver(postInfo.getCarryPerson());
				deliveryBusi.setLogi_receiver_phone(postInfo.getCarryPersonTel());
				deliveryBusi.setShipping_time(postInfo.getCarryTime());
				deliveryBusi.setLogi_no(postInfo.getPostNo());
				String protectFee = postInfo.getProtectFee();
				String exg = "0.[\\d+]{1,4}";
				try{
					if(protectFee.matches(exg)){
						deliveryBusi.setProtect_price(Double.parseDouble(protectFee));
					}else{
						throw new Exception("保费["+protectFee+"]格式错误");
					}
				}catch(Exception e){
					e.printStackTrace();
					dealOrder.setRespOrderStatus("0001");
					dealOrder.setRespOrderDesc("处理异常"+e.getMessage());
					dealorderList.add(dealOrder);
					continue;
				}
				deliveryBusi.setPost_fee(postInfo.getCarryFee());
				deliveryBusi.setUser_recieve_time(postInfo.getReceiptTime());
				deliveryBusi.setIs_dirty(ConstsCore.IS_DIRTY_YES);
				deliveryBusi.setDb_action(ConstsCore.DB_ACTION_UPDATE);
				deliveryBusi.store();
				dealOrder.setRespOrderStatus("0000");
				dealOrder.setRespOrderDesc("处理成功");
				dealorderList.add(dealOrder);
				unLockOrder(order_id);
				setOrderVisible(order_id);
				CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[]{AttrConsts.ND_STATUS}, new String[]{EcsOrderConsts.SDS_STATUS_08});
			}
			sdsStatusLog.setLight_ning_status(EcsOrderConsts.SDS_STATUS_08);
			sdsStatusLog.setStatus_name("操作完成");
			response.setError_code("0000");
			response.setError_msg("处理成功");
			response.setOrderInfo(dealorderList);
			resp.setError_code("0");
		}catch(Exception e){
			e.printStackTrace();
			response.setError_code("0001");
			response.setError_msg("处理失败："+e.getMessage());
			response.setOrderInfo(dealorderList);
			resp.setError_code("9999");
			resp.setError_msg(e.getMessage());
		}
		resp.setResponse(response);
		return resp;
	}
	
	private String getOrderIdByOut(String outOrderId) {
		GetOrderByOutTidReq oreq = new GetOrderByOutTidReq();
		oreq.setOut_tid(outOrderId);
		ZteClient client= ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ECSORD);
		GetOrderByOutTidResp oresp = client.execute(oreq, GetOrderByOutTidResp.class);
		if(null==oresp){
			return "";		
		}
		String order_id = oresp.getOrder_id();
		return order_id;
	}
	private void unLockOrder(String orderId){		
		OrderTreeBusiRequest orderTree =  CommonDataFactory.getInstance().getOrderTree(orderId);// CommonDataFactory.getInstance().getOrderTree(order_id);
  	    OrderExtBusiRequest orderExt = orderTree.getOrderExtBusiRequest();
/*  	    orderExt.setLock_user_id(ConstsCore.NULL_VAL);
  	    orderExt.setLock_user_name(ConstsCore.NULL_VAL);
  	    orderExt.setLock_status(EcsOrderConsts.UNLOCK_STATUS);*/
  	    
  	    String trace_id = orderExt.getFlow_trace_id();
		List<OrderLockBusiRequest> orderLockBusiRequestList = orderTree.getOrderLockBusiRequests();
		for (int i =0;i< orderLockBusiRequestList.size();i++) {
			OrderLockBusiRequest orderLockBusiRequest = orderLockBusiRequestList.get(i);
			if(trace_id.equals(orderLockBusiRequest.getTache_code()) ) {
				orderLockBusiRequest.setLock_user_id(ConstsCore.NULL_VAL);
				orderLockBusiRequest.setLock_user_name(ConstsCore.NULL_VAL);
				orderLockBusiRequest.setLock_status(EcsOrderConsts.UNLOCK_STATUS);
				orderLockBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
				orderLockBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
				//解锁订单释放工号池和锁单结束时间
				orderLockBusiRequest.setPool_id(ConstsCore.NULL_VAL);
				orderLockBusiRequest.setLock_end_time(ConstsCore.NULL_VAL);
				orderLockBusiRequest.store();
			}
		}
  	    
  	   /* orderExt.setIs_dirty(ConstsCore.IS_DIRTY_YES);
  	    orderExt.setDb_action(ConstsCore.DB_ACTION_UPDATE);
  	    orderExt.store();*/
	}
	
	private void doOrderByStatus(String order_id,String status,String statusDesc,String attrField,SDSStatusLogVO sdsStatusLog)throws ApiBusiException{
		/**
		 * 闪电送状态分以下几种：02 成功接收  03 拒绝接收  05 用户拒收  06 开户失败  07 确认配送  99其它状态
		 * 如状态为02则只记录订单和状态变更信息，不进行其它操作
		 * 如状态为03则表示物流公司不能配送此订单，将订单转为人工集中生产模式，设置为非闪电送
		 * 如状态为05则需要用户提起退单，物流公司再同步状态给订单系统，需要转退单模块进行处理
		 * 如状态为06和99则转异常单，清空订单锁定情况，进行人工处理
		 * 如状态为07则清空订单锁定情况，等待后续处理
		 */
		if(EcsOrderConsts.SDS_STATUS_03.equals(status)){
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, 
					new String[]{AttrConsts.ORDER_MODEL,AttrConsts.SHIPPING_QUICK, AttrConsts.ND_STATUS,AttrConsts.ND_STATUS_DESC}, 
					new String[]{EcsOrderConsts.ORDER_MODEL_02, EcsOrderConsts.SHIPPING_QUICK_02, EcsOrderConsts.SDS_STATUS_03,statusDesc});
			sdsStatusLog.setLight_ning_status(EcsOrderConsts.SDS_STATUS_03);
			sdsStatusLog.setStatus_name("拒绝接受");
		}else if(EcsOrderConsts.SDS_STATUS_05.equals(status)||EcsOrderConsts.SF_DEAL_STATUS_7082.equals(status)){//7082-用户拒收-顺丰
			//记录退单原因
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, 
					new String[]{AttrConsts.REFUND_DESC}, 
					new String[]{"退单申请原因：用户拒收，物流公司退单"});
			
			OrderHandleLogsReq logReq = new OrderHandleLogsReq();
			logReq.setComments("用户拒收，物流公司退单");
			logReq.setHandler_type(com.ztesoft.ibss.common.util.Const.ORDER_HANDLER_TYPE_RETURNED);
			logReq.setType_code(EcsOrderConsts.REFUND_STATUS_08);
			logReq.setOp_id("");
			logReq.setOp_name("");
			boolean flag = ordFlowManager.executeOrderReRule(order_id, logReq);
			if(flag){
				CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[]{attrField}, new String[]{EcsOrderConsts.SDS_STATUS_05});
				sdsStatusLog.setLight_ning_status(EcsOrderConsts.SDS_STATUS_05);
				sdsStatusLog.setStatus_name("用户拒收");
			}
		}else if(EcsOrderConsts.SDS_STATUS_06.equals(status)||EcsOrderConsts.SDS_STATUS_99.equals(status)||status.startsWith(EcsOrderConsts.SF_DEAL_STATUS_70)){//
			//异常标记
			Map params = new HashMap();
			BusiCompRequest bcr = new BusiCompRequest();
			bcr.setEnginePath("zteCommonTraceRule.markedException");
			bcr.setOrder_id(order_id);
			params.put(EcsOrderConsts.EXCEPTION_FROM, EcsOrderConsts.EXCEPTION_FROM_ORD);
			bcr.setQueryParams(params);
			CommonDataFactory.getInstance().execBusiComp(bcr);
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[]{attrField}, new String[]{status});
			sdsStatusLog.setLight_ning_status(EcsOrderConsts.SDS_STATUS_06);
			sdsStatusLog.setStatus_name("物流公司处理失败");
		}else if(EcsOrderConsts.SF_DEAL_STATUS_80.equals(status)){//80-顺丰的
			unLockOrder(order_id);
			setOrderVisible(order_id);
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[]{attrField}, new String[]{EcsOrderConsts.SDS_STATUS_08});
			sdsStatusLog.setLight_ning_status(EcsOrderConsts.SDS_STATUS_08);
			sdsStatusLog.setStatus_name("操作完成");
		}else{
			sdsStatusLog.setLight_ning_status("00");
			sdsStatusLog.setStatus_name("其它");
		}
	}
	
	private void setOrderVisible(String order_id){
		//调用订单可见性业务组件
		BusiCompRequest bcr=new BusiCompRequest();
		bcr.setEnginePath("zteCommonTraceRule.setOrderVisible");
		bcr.setOrder_id(order_id);
		CommonDataFactory.getInstance().execBusiComp(bcr);
	}
	
	@Override
	public String findSfMonthAccount(String orderid) {
		String orderModel = CommonDataFactory.getInstance().getAttrFieldValue(orderid, AttrConsts.ORDER_MODEL);
		//物流模式，根据归属地市关联月结账号
		String accountOwner = CommonDataFactory.getInstance().getAttrFieldValue(orderid, AttrConsts.ORDER_CITY_CODE);
		if (!EcsOrderConsts.ORDER_MODEL_04.equalsIgnoreCase(orderModel)) {
			//非物流模式，根据物流公司+归属地市关联月结账号
			String shipping_company = CommonDataFactory.getInstance().getAttrFieldValue(orderid, AttrConsts.SHIPPING_COMPANY); 
			if(StringUtils.equals(shipping_company, EcsOrderConsts.LOGI_COMPANY_SFFYZQYF)||
					StringUtils.equals(shipping_company, EcsOrderConsts.LOGI_COMPANY_SFHS)){
				accountOwner = shipping_company;
			}else{
				accountOwner =  shipping_company + accountOwner;
			}
			orderModel = EcsOrderConsts.ORDER_MODEL_00;
		}
		return findSfMonthAccount(orderModel, accountOwner);
	}

	/**
	 * 物流模式编码，账号归属编码
	 */
	private String findSfMonthAccount(String orderModel, String accountOwner) {
		String account = "";
		String key = "Month_account" + orderModel + accountOwner;
		INetCache cache = CacheFactory.getCacheByType("");
		Object obj = cache.get(Const.CACHE_SPACE, key);
		if (obj == null) {
			account = findSfMonthAccountFromDB(orderModel, accountOwner);
			if (!account.equals("")) {
				cache.set(key, account);
			}
			return account;
		}
		return (String)obj;
	}

	private String findSfMonthAccountFromDB(String orderModel,
			String accountOwner) {
		String sql = SF.ecsordSql("Month_account");
		List account = 
		this.baseDaoSupport.queryForList(sql,  orderModel, accountOwner);
		if (account == null || account.isEmpty()) {
			return "";
		}
		Map map = (Map)account.get(0);
		return map.get("account").toString();
	}
}
