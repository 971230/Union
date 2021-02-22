package zte.service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import params.RuleParams;
import params.ZteResponse;
import params.coqueue.resp.CoQueueAddResp;
import params.member.req.OrderListReq;
import params.member.resp.OrderListResp;
import params.order.req.ConfirmShippingReq;
import params.order.req.MemberOrdReq;
import params.order.req.OrderExceptionCollectReq;
import params.order.req.OrderHandleLogsReq;
import params.order.req.OrderIdReq;
import params.order.req.OrderOuterSyAttrReq;
import params.order.req.OrderTotalReq;
import params.order.req.OrderWorkItemQueryReq;
import params.order.req.OutcallLogsReq;
import params.order.resp.AcceptResp;
import params.order.resp.ConfirmShippingResp;
import params.order.resp.MemberOrdResp;
import params.order.resp.OrderIdResp;
import params.order.resp.OrderOuterResp;
import params.order.resp.OrderTotalResp;
import params.order.resp.OrderWorkItemQueryResp;
import params.orderqueue.req.CheckOpenServiceReq;
import params.orderqueue.req.OrderCollectionReq;
import params.orderqueue.req.OrderCreateReq;
import params.orderqueue.req.OrderHandleLogsAddReq;
import params.orderqueue.req.OrderQueueFailSaveReq;
import params.orderqueue.req.OrderQueueHisSaveReq;
import params.orderqueue.req.OrderUpdateReq;
import params.orderqueue.resp.CheckOpenServiceResp;
import params.orderqueue.resp.OrderCollectionResp;
import params.orderqueue.resp.OrderCreateResp;
import params.orderqueue.resp.OrderHandleLogsAddResp;
import params.orderqueue.resp.OrderQueueFailSaveResp;
import params.orderqueue.resp.OrderQueueHisSaveResp;
import params.orderqueue.resp.OrderUpdateResp;
import rule.params.accept.req.AcceptRuleReq;
import services.DeliveryInf;
import services.OrderInf;
import services.PromotionInf;
import services.ServiceBase;
import util.OrderRoleDataUtil;
import utils.CoreThreadLocalHolder;
import utils.GlobalThreadLocalHolder;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.attr.req.AttrInstLoadReq;
import zte.net.ecsord.params.attr.resp.AttrInstLoadResp;
import zte.net.ecsord.params.base.req.BusiCompRequest;
import zte.net.ecsord.params.base.resp.BusiCompResponse;
import zte.net.ecsord.params.busi.req.OrderDeliveryBusiRequest;
import zte.net.ecsord.params.busi.req.OrderDeliveryItemBusiRequest;
import zte.net.ecsord.params.busi.req.OrderExtBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;
import zte.net.ecsord.params.logs.req.MatchDictLogsReq;
import zte.net.ecsord.params.logs.req.MatchInfLogStateReq;
import zte.net.ecsord.params.logs.resp.MatchDictLogsResp;
import zte.net.ecsord.params.logs.resp.MatchInfLogStateResp;
import zte.net.ecsord.rule.tache.TacheFact;
import zte.net.ecsord.rule.workflow.WorkFlowFact;
import zte.net.iservice.IEcsOrdServices;
import zte.net.iservice.IGoodsService;
import zte.net.iservice.IGoodsTypeService;
import zte.net.iservice.IOrderServices;
import zte.net.iservice.IUosService;
import zte.net.params.req.RuleTreeExeReq;
import zte.net.params.resp.RuleTreeExeResp;
import zte.params.ecsord.req.AttrTableCacheGetReq;
import zte.params.ecsord.req.GetOrderByLogiNoReq;
import zte.params.ecsord.req.HasAutoExceptionReq;
import zte.params.ecsord.req.InsertOrderHandLogReq;
import zte.params.ecsord.req.ZBStartOpenAccountReq;
import zte.params.ecsord.resp.AttrTableCacheGetResp;
import zte.params.ecsord.resp.GetOrderByLogiNoResp;
import zte.params.ecsord.resp.HasAutoExceptionResp;
import zte.params.ecsord.resp.ZBStartOpenAccountResp;
import zte.params.goodstype.req.GoodsTypeGetReq;
import zte.params.goodstype.resp.GoodsTypeGetResp;
import zte.params.order.req.AdjustListReq;
import zte.params.order.req.AdjustOrderPriceReq;
import zte.params.order.req.AttrDefGetReq;
import zte.params.order.req.AttrInstValueEditReq;
import zte.params.order.req.AttrValueGetReq;
import zte.params.order.req.ChargebackStatuChgReq;
import zte.params.order.req.DeliveryFlowListReq;
import zte.params.order.req.DeliveryItemAddReq;
import zte.params.order.req.DeliveryItemDelReq;
import zte.params.order.req.DeliveryItemsQueryByDeIdReq;
import zte.params.order.req.DeliveryItemsQueryReq;
import zte.params.order.req.GnotifyAddReq;
import zte.params.order.req.GnotifyDeleteReq;
import zte.params.order.req.GnotifyPageListReq;
import zte.params.order.req.GoodsCartItemListReq;
import zte.params.order.req.HandlerOrderRoleReq;
import zte.params.order.req.HttpApiDateReq;
import zte.params.order.req.MemberReturnedOrderListReq;
import zte.params.order.req.OrderAddReq;
import zte.params.order.req.OrderAuthorityByUserReq;
import zte.params.order.req.OrderCheckTraceFlowFinishReq;
import zte.params.order.req.OrderCommitReq;
import zte.params.order.req.OrderConfirmReq;
import zte.params.order.req.OrderCountGetReq;
import zte.params.order.req.OrderDeliveryListReq;
import zte.params.order.req.OrderExceptionLogsListReq;
import zte.params.order.req.OrderExceptionReq;
import zte.params.order.req.OrderExtInfoGetReq;
import zte.params.order.req.OrderFlowExceptionBusiDoReq;
import zte.params.order.req.OrderGetReq;
import zte.params.order.req.OrderGiftListReq;
import zte.params.order.req.OrderHandleLogsListReq;
import zte.params.order.req.OrderItemGetReq;
import zte.params.order.req.OrderItemListHisReq;
import zte.params.order.req.OrderItemListReq;
import zte.params.order.req.OrderItemsAddReq;
import zte.params.order.req.OrderItemsDelReq;
import zte.params.order.req.OrderMetaListReq;
import zte.params.order.req.OrderOuterAttrQueryReq;
import zte.params.order.req.OrderOuterQryReq;
import zte.params.order.req.OrderOuterSyncReq;
import zte.params.order.req.OrderPageListReq;
import zte.params.order.req.OrderPriceEditReq;
import zte.params.order.req.OrderQryBatchReq;
import zte.params.order.req.OrderShipReq;
import zte.params.order.req.OrderStandardPushReq;
import zte.params.order.req.OrderStatusEditReq;
import zte.params.order.req.OrderStockingReq;
import zte.params.order.req.OrderSyncReq;
import zte.params.order.req.OutcallLogsListReq;
import zte.params.order.req.PayExcetionReq;
import zte.params.order.req.PayQryReq;
import zte.params.order.req.PayReq;
import zte.params.order.req.PaySuccReq;
import zte.params.order.req.PayUptReq;
import zte.params.order.req.PaymentLogReq;
import zte.params.order.req.PromotionAddReq;
import zte.params.order.req.PromotionDeleteReq;
import zte.params.order.req.PromotionReq;
import zte.params.order.req.RefreshAttrDefReq;
import zte.params.order.req.RefreshOrderTreeAttrReq;
import zte.params.order.req.ReissueGoodsShippingAddReq;
import zte.params.order.req.ShipItemSyncReq;
import zte.params.order.req.ShippingConfirmReq;
import zte.params.order.req.TaobaoOrderSyncReq;
import zte.params.order.req.WorkFlowCallBackReq;
import zte.params.order.resp.AdjustListResp;
import zte.params.order.resp.AdjustOrderPriceResp;
import zte.params.order.resp.AttrDefGetResp;
import zte.params.order.resp.AttrInstValueEditResp;
import zte.params.order.resp.AttrValueGetResp;
import zte.params.order.resp.ChargebackStatuChgResp;
import zte.params.order.resp.DeliveryFlowListResp;
import zte.params.order.resp.DeliveryItemAddResp;
import zte.params.order.resp.DeliveryItemDelResp;
import zte.params.order.resp.DeliveryItemsQueryByDeIdResp;
import zte.params.order.resp.DeliveryItemsQueryResp;
import zte.params.order.resp.GnotifyAddResp;
import zte.params.order.resp.GnotifyDeleteResp;
import zte.params.order.resp.GnotifyPageListResp;
import zte.params.order.resp.GoodsCartItemListResp;
import zte.params.order.resp.HandlerOrderRoleResp;
import zte.params.order.resp.HttpApiDataResp;
import zte.params.order.resp.MemberReturnedOrderListResp;
import zte.params.order.resp.OrderAddResp;
import zte.params.order.resp.OrderAuthorityByUserResp;
import zte.params.order.resp.OrderCheckTraceFlowFinishResp;
import zte.params.order.resp.OrderCommitResp;
import zte.params.order.resp.OrderConfirmResp;
import zte.params.order.resp.OrderCountGetResp;
import zte.params.order.resp.OrderDeliveryListResp;
import zte.params.order.resp.OrderExceptionLogsListResp;
import zte.params.order.resp.OrderExceptionResp;
import zte.params.order.resp.OrderExtInfoGetResp;
import zte.params.order.resp.OrderFlowExceptionBusiDoResp;
import zte.params.order.resp.OrderGetResp;
import zte.params.order.resp.OrderGiftListResp;
import zte.params.order.resp.OrderHandleLogsListResp;
import zte.params.order.resp.OrderItemGetResp;
import zte.params.order.resp.OrderItemListHisResp;
import zte.params.order.resp.OrderItemListResp;
import zte.params.order.resp.OrderItemsAddResp;
import zte.params.order.resp.OrderItemsDelResp;
import zte.params.order.resp.OrderMetaListResp;
import zte.params.order.resp.OrderOuterAttrQueryResp;
import zte.params.order.resp.OrderOuterQryResp;
import zte.params.order.resp.OrderOuterSyncResp;
import zte.params.order.resp.OrderPageListResp;
import zte.params.order.resp.OrderPriceEditResp;
import zte.params.order.resp.OrderQryBatchResp;
import zte.params.order.resp.OrderShipResp;
import zte.params.order.resp.OrderStandardPushResp;
import zte.params.order.resp.OrderStatusEditResp;
import zte.params.order.resp.OrderStockingResp;
import zte.params.order.resp.OrderTaobaoSyncResp;
import zte.params.order.resp.OutcallLogsListResp;
import zte.params.order.resp.PayExcetionResp;
import zte.params.order.resp.PayQryResp;
import zte.params.order.resp.PayResp;
import zte.params.order.resp.PaySuccResp;
import zte.params.order.resp.PayUptResp;
import zte.params.order.resp.PaymentLogResp;
import zte.params.order.resp.PromotionAddResp;
import zte.params.order.resp.PromotionDeleteResp;
import zte.params.order.resp.PromotionResp;
import zte.params.order.resp.RefreshAttrDefResp;
import zte.params.order.resp.ReissueGoodsShippingAddResp;
import zte.params.order.resp.ShipItemSyncResp;
import zte.params.order.resp.ShippingConfirmResp;
import zte.params.order.resp.WorkFlowCallBackResp;
import zte.params.process.req.UosDealReq;
import zte.params.process.resp.UosDealResp;
import zte.params.process.resp.WorkItem;

import com.powerise.ibss.util.DBTUtil;
import com.ztesoft.api.ApiBusiException;
import com.ztesoft.api.AppKeyEnum;
import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.ibss.common.util.Const;
import com.ztesoft.ibss.common.util.ListUtil;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotation;
import com.ztesoft.net.app.base.core.model.RoleData;
import com.ztesoft.net.attr.service.IAttrDefNManager;
import com.ztesoft.net.consts.OrderStatus;
import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.eop.sdk.user.UserServiceFactory;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.model.CoQueue;
import com.ztesoft.net.mall.core.model.Delivery;
import com.ztesoft.net.mall.core.model.DeliveryFlow;
import com.ztesoft.net.mall.core.model.DeliveryItem;
import com.ztesoft.net.mall.core.model.HttpReqData;
import com.ztesoft.net.mall.core.model.OpenServiceCfg;
import com.ztesoft.net.mall.core.model.Order;
import com.ztesoft.net.mall.core.model.OrderChange;
import com.ztesoft.net.mall.core.model.OrderItem;
import com.ztesoft.net.mall.core.model.OrderLog;
import com.ztesoft.net.mall.core.model.OrderMeta;
import com.ztesoft.net.mall.core.model.OrderOuter;
import com.ztesoft.net.mall.core.model.OrderQueryParam;
import com.ztesoft.net.mall.core.model.PaymentLog;
import com.ztesoft.net.mall.core.model.Promotion;
import com.ztesoft.net.mall.core.model.PromotionActivity;
import com.ztesoft.net.mall.core.model.ReturnsOrder;
import com.ztesoft.net.mall.core.model.support.CartItem;
import com.ztesoft.net.mall.core.service.ICartManager;
import com.ztesoft.net.mall.core.service.ICoQueueManager;
import com.ztesoft.net.mall.core.service.IDcPublicInfoManager;
import com.ztesoft.net.mall.core.service.IDeliveryFlow;
import com.ztesoft.net.mall.core.service.IDeliveryManager;
import com.ztesoft.net.mall.core.service.IDictMatchManager;
import com.ztesoft.net.mall.core.service.IGnotifyManager;
import com.ztesoft.net.mall.core.service.IOrderManager;
import com.ztesoft.net.mall.core.service.IOrderMetaManager;
import com.ztesoft.net.mall.core.service.IOrderNFlowManager;
import com.ztesoft.net.mall.core.service.IOrderQueueManager;
import com.ztesoft.net.mall.core.service.IOrderReportManager;
import com.ztesoft.net.mall.core.service.IOrderWorkItemManager;
import com.ztesoft.net.mall.core.service.IPromotionManager;
import com.ztesoft.net.mall.core.service.IRuleManager;
import com.ztesoft.net.mall.core.service.ITplInstManager;
import com.ztesoft.net.mall.core.service.impl.OrderRoleDataManager;
import com.ztesoft.net.mall.core.service.impl.cache.DcPublicInfoCacheProxy;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.model.AttrDef;
import com.ztesoft.net.model.AttrInst;
import com.ztesoft.net.model.AttrTableCache;
import com.ztesoft.net.model.OrderItemAddAccept;
import com.ztesoft.net.model.OrderRole;
import com.ztesoft.net.model.Outer;
import com.ztesoft.net.outter.inf.iservice.IOuterService;
import com.ztesoft.net.sqls.SF;
import com.ztesoft.parser.BusiCompPraser;
import com.ztesoft.remote.params.activity.req.PromotionActEditReq;
import com.ztesoft.remote.params.activity.req.PromotionActReq;
import com.ztesoft.remote.params.activity.resp.PromotionActResp;
import com.ztesoft.rop.annotation.ServiceMethodBean;
import com.ztesoft.util.CacheUtils;

import commons.CommonTools;
import consts.ConstsCore;

@ServiceMethodBean(version = "1.0")
@ZteSoftCommentAnnotation(type = "class", desc = "订单服务", summary = "订单服务")
public class OrderServices extends ServiceBase implements IOrderServices {

	@Resource
	private OrderInf orderServ;
	@Resource
	private IOrderManager orderManager;
	@Resource
	private IDeliveryManager deliveryManager;
	@Resource
	private IOrderNFlowManager orderNFlowManager;
	@Resource
	private DeliveryInf deliveryServ;
	@Resource
	private ITplInstManager tplInstManager;
	@Resource
	private IPromotionManager promotionManager;
	@Resource
	private IRuleManager ruleManager;
	@Resource
	private ICartManager cartManager;
	@Resource
	private PromotionInf promotionServ;
	@Resource
	private IOrderMetaManager orderMetaManager;
	@Resource
	private IDeliveryFlow deliveryFlowManager;
	@Resource
	private IOrderReportManager orderReportManager;
	@Resource
	private IGnotifyManager gnotifyManager;
	@Resource
	private IOuterService outerService;
	@Resource
	private IGoodsService goodsService;
	@Resource
	private IAttrDefNManager attrDefNManager;
	@Resource
	protected IDcPublicInfoManager dcPublicInfoManager;
	@Resource
	protected IUosService uosService;
	@Resource
	private OrderRoleDataManager orderRoleDataManager;

	@Resource
	private IOrderQueueManager orderQueueManager;
	@Resource
	private IOrderWorkItemManager orderWorkItemManager;
	
	@Resource
	private IGoodsTypeService goodsTypeService;
	@Resource
	private IDictMatchManager dictMatchManager;

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	@ZteSoftCommentAnnotation(type = "method", desc = "创建订单", summary = "订单服务")
	public OrderAddResp addOrder(OrderAddReq orderAddReq) throws Exception {
		// IDataLogManager dataLogManager =
		// SpringContextHolder.getBean("datasLogManager");
		// dataLogManager.insertDataBeforeLog(null);
		//
		// add by wui设置session
		if (StringUtils.isEmpty(orderAddReq.userSessionId)) {
			GlobalThreadLocalHolder.getInstance().clear();
			orderAddReq.setUserSessionId(orderAddReq.getUserSessionId());
		}
		OrderAddResp resp = orderServ.createOuterOrder(orderAddReq);
		this.addReturn(orderAddReq, resp);
		return resp;
	}

	@Override
	public MemberOrdResp listOrderByMemberId(MemberOrdReq req) {
		return orderServ.listOrderByMemberId(req);
	}

	@Override
	public AcceptResp saveTplInst(AcceptRuleReq acceptRuleReq)
			throws ApiBusiException {
		return tplInstManager.saveTplInst(acceptRuleReq);
	}

	@Override
	public OrderPageListResp queryOrderForPage(OrderPageListReq pageReq) {
		OrderPageListResp resp = new OrderPageListResp();
		OrderQueryParam ordParam = pageReq.getOrderQueryParam();
		Page webpage = null;
		if (pageReq.isSecurity()) {
			webpage = this.orderManager.listGroupOrder(pageReq.getPageNo(),
					pageReq.getPageSize(), 0, ordParam, "");
		} else {
			webpage = this.orderManager.listc(pageReq.getPageNo(),
					pageReq.getPageSize(), 0, ordParam, "");
		}
		resp.setWebPage(webpage);
		resp.setError_code("0");
		resp.setError_msg("成功");
		addReturn(pageReq, resp);
		return resp;
	}

	@Override
	public OrderStatusEditResp editOrderStatus(OrderStatusEditReq req) {
		List param = new ArrayList();
		String sql = "update es_order set ";
		if (!StringUtils.isEmpty(req.getOrder_status())) {
			sql += " status=?";
			param.add(req.getOrder_status());
		}
		if (!StringUtils.isEmpty(req.getPay_status())) {
			if (param.size() > 0)
				sql += ",";
			sql += " pay_status=?";
			param.add(req.getPay_status());
		}
		if (!StringUtils.isEmpty(req.getShip_status())) {
			if (param.size() > 0)
				sql += ",";
			sql += " ship_status=?";
			param.add(req.getShip_status());
		}
		sql += " where order_id=?";
		param.add(req.getOrder_id());
		this.baseDaoSupport.execute(sql, param.toArray());
		OrderStatusEditResp resp = new OrderStatusEditResp();
		resp.setError_code("0");
		resp.setError_msg("成功");
		addReturn(req, resp);
		return resp;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public OrderPriceEditResp editOrderPrice(OrderPriceEditReq req) {
		Order order = orderManager.get(req.getOrder_id());
		if (order == null)
			CommonTools.addFailError("没有找到订单");
		if (req.getOrder_price() != -1)
			orderManager.savePrice(req.getOrder_price(), req.getOrder_id());
		if (req.getShip_price() != -1) {
			double oprice = order.getShipping_amount() - req.getShip_price();
			this.baseDaoSupport
					.execute(
							"update order set shipping_amount=?,order_amount=order_amount-? where order_id=?",
							req.getShip_price(), oprice, req.getOrder_id());
			OrderChange orderChange = new OrderChange();
			orderChange.setOrder_id(req.getOrder_id());
			orderChange.setItem_id(req.getOrder_id());
			orderChange.setNew_value(req.getShip_price() + "");
			orderChange.setNew_value_desc(req.getShip_price() + "");
			orderChange.setOld_value(String.valueOf(order.getOrder_amount()));
			orderChange.setOld_value_desc(String.valueOf(order
					.getOrder_amount()));
			orderChange.setCreate_date(DBTUtil.current());
			orderChange.setField_name("shipping_amount");
			orderChange.setTable_name("ES_ORDER");
			orderChange.setSequ(0);
			orderManager.saveChange(orderChange);
		}
		OrderPriceEditResp resp = new OrderPriceEditResp();
		resp.setError_code("0");
		resp.setError_msg("成功");
		addReturn(req, resp);
		return resp;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public OrderConfirmResp confirmOrder(OrderConfirmReq req) {
		OrderConfirmResp resp = new OrderConfirmResp();
		Order order = orderManager.get(req.getOrder_id());
		if (OrderStatus.ORDER_SHIP == order.getStatus()) {
			// 已去付可备货
			this.baseDaoSupport.execute(
					"update es_order t set t.status=? where t.order_id=?",
					OrderStatus.ORDER_CONFIRM_SHIP, order.getOrder_id());
			// ===写日志===
			OrderLog log = new OrderLog();
			log.setMessage("确认收货");
			log.setOp_id(req.getConfirm_userid());
			log.setOp_name(req.getConfirm_username());
			log.setOp_time("sysdate");
			log.setOrder_id(req.getOrder_id());
			this.baseDaoSupport.insert("order_log", log);
			resp.setError_code("0");
			resp.setError_msg("成功");
		} else {
			// 非备货状态
			resp.setError_code("1");
			resp.setError_msg("该订单不能确认");
		}
		addReturn(req, resp);
		return resp;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public OrderStockingResp stokingOrder(OrderStockingReq req) {
		OrderStockingResp resp = new OrderStockingResp();
		Order order = orderManager.get(req.getOrder_id());
		if (OrderStatus.ORDER_PAY == order.getStatus()) {
			// 已去付可备货
			this.baseDaoSupport.execute(
					"update es_order t set t.status=? where t.order_id=?",
					OrderStatus.ORDER_ACCEPT, order.getOrder_id());
			// ===写日志===
			OrderLog log = new OrderLog();
			log.setMessage("订单备货");
			log.setOp_id(req.getConfirm_userid());
			log.setOp_name(req.getConfirm_username());
			log.setOp_time("sysdate");
			log.setOrder_id(req.getOrder_id());
			this.baseDaoSupport.insert("order_log", log);
			resp.setError_code("0");
			resp.setError_msg("成功");
		} else {
			// 非备货状态
			resp.setError_code("1");
			resp.setError_msg("该订单不能备货");
		}
		addReturn(req, resp);
		return resp;
	}

	// 提供团购秒杀　添加记录到promotion_activity & promotion表
	@Override
	public PromotionActResp addPromtionActivity(PromotionActReq req) {
		PromotionActResp resp = new PromotionActResp();
		Map map = new HashMap();
		map = req.getMap();
		promotionManager.addT(map);
		return resp;
	}

	// 团购秒杀同步更新promotion_activity enable 状态
	@Override
	public PromotionActResp promotionActEdit(PromotionActEditReq req) {
		PromotionActResp resp = new PromotionActResp();
		String pmt_solution = req.getPmt_solution();
		String enable = req.getEnable();
		promotionManager.editTM(pmt_solution, enable);
		return resp;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public OrderShipResp shipOrder(OrderShipReq req) {
		Delivery dv = deliveryManager.getByOrderId(req.getOrder_id());
		dv.setLogi_id(req.getLogi_id());
		dv.setLogi_name(req.getLogi_name());
		dv.setHouse_id(req.getHouse_id());
		dv.setLogi_no(req.getLogi_no());
		dv.setShip_name(req.getShip_name());
		dv.setShip_mobile(req.getShip_mobile());
		dv.setShip_zip(req.getShip_zip());
		dv.setShip_addr(req.getShip_addr());

		List<OrderItem> orderItems = orderNFlowManager.listNotShipGoodsItem(req
				.getOrder_id());
		// List<Map> giftList =
		// this.orderNFlowManager.listNotShipGiftItem(req.getOrder_id());

		List<DeliveryItem> itemList = new ArrayList<DeliveryItem>();
		int totalNum = 0;
		for (OrderItem oi : orderItems) {
			DeliveryItem item = new DeliveryItem();
			item.setGoods_id(oi.getGoods_id());
			item.setName(oi.getName());
			item.setNum(oi.getNum() - oi.getShip_num());
			item.setProduct_id(oi.getProduct_id());
			item.setSn(oi.getSn());
			item.setItemtype(0);
			item.setOrder_item_id(oi.getItem_id());
			itemList.add(item);
			if (item.getNum() != null) {
				totalNum += item.getNum();
			}
		}
		OrderShipResp resp = new OrderShipResp();

		if (totalNum == 0) {
			resp.setError_code(ConstsCore.ERROR_FAIL);
			resp.setError_msg("此订单已经发货完成，不能发货。");
		}
		/*
		 * i=0; List<DeliveryItem> giftitemList = new ArrayList<DeliveryItem>();
		 * if(giftList!=null && giftList.size()>0){ for(Map map :giftList){
		 * DeliveryItem item = new DeliveryItem(); item.setGoods_id(giftid);
		 * item.setName((String)map.get("gift_name")); int num = 0; int ship_num
		 * = 0; if(map.get("num")!=null) num =
		 * Integer.parseInt(map.get("num").toString());
		 * if(map.get("shipnum")!=null) ship_num =
		 * Integer.parseInt(map.get("shipnum").toString());
		 * item.setNum(num-ship_num); item.setProduct_id(giftid);
		 * item.setItemtype(2); giftitemList.add(item);
		 * item.setOrder_item_id((String)map.get("gift_id")); i++; } }
		 */

		ConfirmShippingReq sreq = new ConfirmShippingReq();
		sreq.setDelivery_id(dv.getDelivery_id());
		sreq.setDeliveryItems(itemList);
		// sreq.setGiftitemList(giftitemList);
		sreq.setDelivery(dv);
		sreq.setShippingType("1");
		sreq.setHouse_id(req.getHouse_id());
		sreq.setConfirm_userid(req.getConfirm_userid());
		sreq.setConfirm_username(req.getConfirm_username());
		ConfirmShippingResp sresp = deliveryServ.confirmShipping(sreq);

		resp.setError_code("0");
		resp.setError_msg("成功");
		addReturn(req, resp);
		return resp;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public OrderExceptionResp saveOrderFail(OrderExceptionReq orderExceptionReq) {

		this.orderManager.saveOrderFail(orderExceptionReq
				.getOrderExcepCollect());

		OrderExceptionResp orderExceptionResp = new OrderExceptionResp();
		addReturn(orderExceptionReq, orderExceptionResp);
		return orderExceptionResp;
	}

	/**
	 * 通用反射工具类
	 */
	@Override
	public ZteResponse processrule(RuleParams ruleParams)
			throws ApiBusiException {
		return ruleManager.process(ruleParams);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public OrderGiftListResp listOrderGift(OrderGiftListReq req) {
		// 显示可享受的优惠规则
		Double originalTotal = cartManager.countGoodsTotal(UserServiceFactory
				.getUserService().getCurrentMember(), req.getUserSessionId(),
				true);
		List pmtList = this.promotionServ.list(originalTotal,
				req.getMember_lv_id(), null);
		// 显示可获得的赠品
		List giftList = this.promotionServ.listGift(pmtList);
		OrderGiftListResp resp = new OrderGiftListResp();
		resp.setPmtList(pmtList);
		resp.setGiftList(giftList);
		resp.setError_code("0");
		resp.setError_msg("成功");
		addReturn(req, resp);
		return resp;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public GoodsCartItemListResp listGoodsCartItem(GoodsCartItemListReq req) {

		List<CartItem> itemList = this.cartManager.listGoods(UserServiceFactory
				.getUserService().getCurrentMember(), req.getUserSessionId(),
				null);
		GoodsCartItemListResp resp = new GoodsCartItemListResp();
		resp.setItemList(itemList);
		resp.setError_code("0");
		resp.setError_msg("成功");
		addReturn(req, resp);
		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "购物车商品总价", summary = "购物车商品总价")
	public OrderTotalResp cartOrderTotalPrice(OrderTotalReq req) {
		return orderServ.showOrderTotal(req);
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "订单属性", summary = "订单属性")
	public OrderMetaListResp listOrderMeta(OrderMetaListReq req) {
		List<OrderMeta> metaList = this.orderMetaManager
				.list(req.getOrder_id());
		OrderMetaListResp resp = new OrderMetaListResp();
		resp.setMetaList(metaList);
		resp.setError_code("0");
		resp.setError_msg("成功");
		addReturn(req, resp);
		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "物流信息列表查询", summary = "物流信息列表查询")
	public DeliveryFlowListResp listDeliveryList(DeliveryFlowListReq req) {
		List<DeliveryFlow> deliveryList = deliveryFlowManager
				.qryDeliveryFlowByDeliveryID(req.getDelivery_id());
		DeliveryFlowListResp resp = new DeliveryFlowListResp();
		resp.setDeliveryFlow(deliveryList);
		resp.setError_code("0");
		resp.setError_msg("成功");
		addReturn(req, resp);
		return resp;
	}

	@Override
	public OrderDeliveryListResp listOrderDelivery(OrderDeliveryListReq req) {
		List list = orderReportManager.listDelivery(req.getOrder_id(),
				req.getType());
		OrderDeliveryListResp resp = new OrderDeliveryListResp();
		resp.setDeliveryList(list);
		resp.setError_code("0");
		resp.setError_msg("成功");
		addReturn(req, resp);
		return resp;
	}

	@Override
	public MemberReturnedOrderListResp listMemberReturnedOrders(
			MemberReturnedOrderListReq req) {
		List<ReturnsOrder> list = orderManager.memberReturnedOrders(req
				.getMenmber_id());
		MemberReturnedOrderListResp resp = new MemberReturnedOrderListResp();
		resp.setReturnedOrders(list);
		resp.setError_code("0");
		resp.setError_msg("成功");
		addReturn(req, resp);
		return resp;
	}

	@Override
	public GnotifyPageListResp queryGnotifyForPage(GnotifyPageListReq req) {
		Page gnotifyPage = gnotifyManager.pageGnotify(req.getPageNo(),
				req.getPageSize());
		GnotifyPageListResp resp = new GnotifyPageListResp();
		resp.setPage(gnotifyPage);
		resp.setError_code("0");
		resp.setError_msg("成功");
		addReturn(req, resp);
		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "删除缺货登记", summary = "删除缺货登记")
	public GnotifyDeleteResp deleteGontify(GnotifyDeleteReq req) {
		gnotifyManager.deleteGnotify(req.getGnotify_id());
		GnotifyDeleteResp resp = new GnotifyDeleteResp();
		resp.setError_code("0");
		resp.setError_msg("成功");
		addReturn(req, resp);
		return resp;
	}

	@Override
	public GnotifyAddResp addGnotify(GnotifyAddReq req) {
		gnotifyManager.addGnotify(req.getProduct_id());
		GnotifyAddResp resp = new GnotifyAddResp();
		resp.setError_code("0");
		resp.setError_msg("成功");
		addReturn(req, resp);
		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "获取子订单", summary = "获取子订单")
	public OrderItemGetResp getOrderItem(OrderItemGetReq req) {
		OrderItem item = orderManager.getOrderItemByItemId(req.getItem_id());
		OrderItemGetResp resp = new OrderItemGetResp();
		resp.setOrderItem(item);
		resp.setError_code("0");
		resp.setError_msg("成功");
		addReturn(req, resp);
		return resp;
	}

	@Override
	// @ZteSoftCommentAnnotation(type = "method", desc = "外系统订单同步", summary =
	// "外系统订单同步")
	public OrderOuterSyncResp syncOrderOuter(OrderOuterSyncReq req) {
		String sql = SF.orderSql("SYNC_OUTER_ORDER");
		String countSql = sql.replace("*", "counot(*)");
		/*
		 * List<OrderOuter> list = (List<OrderOuter>)
		 * this.baseDaoSupport.queryForCPage(sql, req.getPage_no(),
		 * req.getPage_size(), OrderOuter.class, countSql,
		 * ManagerUtils.getSourceFrom(),req.getBegin_time(),req.getEnd_time());
		 * List<OrderSync> orderSyncList = new ArrayList<OrderSync>();
		 * if(list!=null && list.size()>0){ for(OrderOuter oo:list){ OrderSync
		 * os = new OrderSync(); os.setOrderOuter(oo);
		 * if(!StringUtils.isEmpty(oo.getOrder_attrs())){ OrderJsonAttr
		 * otherAttr = JSON.parseObject(oo.getOrder_attrs(),
		 * OrderJsonAttr.class); os.setOtherAttr(otherAttr); }
		 * if(!StringUtils.isEmpty(oo.getGoods_attrs())){ List<GoodsJsonAttr>
		 * goodsAttrList = JSON.parseArray(oo.getGoods_attrs(),
		 * GoodsJsonAttr.class); os.setGoodsAttrList(goodsAttrList); }
		 * orderSyncList.add(os); } }
		 */
		OrderOuterSyncResp resp = new OrderOuterSyncResp();
		// resp.setOrderSyncList(orderSyncList);
		resp.setError_code("0");
		resp.setError_msg("成功");
		addReturn(req, resp);
		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "http请求api", summary = "http请求api")
	public HttpApiDataResp addHttpData(HttpApiDateReq acceptRuleReq)
			throws ApiBusiException {
		HttpReqData httpReqData = acceptRuleReq.getHttpReqData();
		this.baseDaoSupport.execute(
				"delete from es_http_api where method_name = ? ",
				httpReqData.getMethod_name());
		this.baseDaoSupport.insert("es_http_api", httpReqData);
		return null;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "查询个人订单", summary = "查询个人订单")
	public OrderListResp pageOrders(OrderListReq req) {
		Page page = orderManager.pageMemberOrders(req.getMember_id(),
				String.valueOf(req.getPageNo()),
				String.valueOf(req.getPageSize()), req.getOrderStatus());
		OrderListResp resp = new OrderListResp();
		resp.setPage(page);
		resp.setError_code("0");
		resp.setError_msg("成功");
		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "淘宝订单同步", summary = "淘宝订单同步")
	public OrderTaobaoSyncResp taobaoOrderSync(TaobaoOrderSyncReq req) {
		String date_type = req.getDate_type();
		String order_id_req = req.getOrder_id();
		String begin_time = req.getBegin_time();
		String end_time = req.getEnd_time();
		int pageNo = req.getPage_no();
		int pageSize = req.getPage_size();

		try {
			begin_time = URLDecoder.decode(begin_time, "UTF-8");
			end_time = URLDecoder.decode(end_time, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		List<String> params = new ArrayList<String>();
		Long totalCount = (long) 0;

		OrderTaobaoSyncResp resp = new OrderTaobaoSyncResp();
		// String orderSql =
		// "select * from es_order t where (t.app_key='taobao' or t.app_key='edb') and exists (select 1 from es_outer_accept a where a.order_id = t.order_id and a.pro_type is not null) ";//""?
		// SF.orderSql("OrderListForOut");
		String orderSql = "select * from es_order t where exists (select 1 from es_outer_accept a where a.order_id = t.order_id and a.pro_type is not null) ";// ""?
																																								// SF.orderSql("OrderListForOut");
		String itemSql = SF.orderSql("OrderItmeForOut");
		String acceptSql = SF.orderSql("OrderAcceptForOut");
		List<Order> orderlist = new ArrayList<Order>();

		boolean isQuery = false; // 标志是否执行查询

		if (StringUtils.isNotEmpty(order_id_req)) {
			orderSql += " and  t.order_id=?";
			params.add(order_id_req);
			isQuery = true;
		} else {
			if (StringUtils.isNotEmpty(begin_time)
					&& StringUtils.isNotEmpty(end_time)) {

				orderSql += " and  t.create_time >= "
						+ DBTUtil.to_date(begin_time);
				orderSql += " and  t.create_time <= "
						+ DBTUtil.to_date(end_time);
				isQuery = true;
			}
		}

		if (isQuery) {
			Page webpage = baseDaoSupport.queryForPage(orderSql, pageNo,
					pageSize, Order.class, params.toArray(new String[] {}));
			orderlist = webpage.getResult();
			totalCount = webpage.getTotalCount();
			for (int i = 0; i < orderlist.size(); i++) {
				Order order = orderlist.get(i);
				String order_id = order.getOrder_id();
				if (StringUtils.isNotEmpty(order_id)) {
					List<OrderItem> itemList = baseDaoSupport
							.queryForList(itemSql, OrderItem.class,
									new String[] { order_id });
					List<OrderItemAddAccept> acceptList = baseDaoSupport
							.queryForList(acceptSql, OrderItemAddAccept.class,
									new String[] { order_id });
					order.setOrderItemList(itemList);
					order.setOrderItemAcceptList(acceptList);
				}
			}
		}

		resp.setError_code("0");
		resp.setError_msg("成功");
		resp.setTotalCount(totalCount);
		resp.setOrderSyncList(orderlist);
		addReturn(req, resp);
		return resp;
	}

	@SuppressWarnings("unused")
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "订单归集推送", summary = "订单归集推送")
	public OrderStandardPushResp pushOrderStandard(OrderStandardPushReq pushReq) {
		
		//add by wui
		CoreThreadLocalHolder.getInstance().getZteCommonData().setZteRequest(pushReq);
		if (StringUtils.isEmpty(pushReq.userSessionId)) {
			pushReq.setUserSessionId(pushReq.getUserSessionId());
		}
		
		long start = System.currentTimeMillis();
		OrderStandardPushResp resp = new OrderStandardPushResp();
		List<Outer> outerList = pushReq.getOuterList();

		Map<String, List<Outer>> pushMap = new HashMap<String, List<Outer>>();

		for (Outer oo : outerList) {
			List<Outer> oList = pushMap
					.get(Consts.SERVICE_CODE_CO_GUIJI_NEW);
			if (oList == null) {
				oList = new ArrayList<Outer>();
				pushMap.put(Consts.SERVICE_CODE_CO_GUIJI_NEW, oList);
			}
			oList.add(oo);
		}

		resp.setError_code("0");
		resp.setError_msg("成功");
		Iterator it = pushMap.keySet().iterator();
		while (it.hasNext()) {
			String key = (String) it.next();
			String service_name = "订单归集";
			if (Consts.SERVICE_CODE_CO_GUIJI_NEW.equals(key)) {
				service_name = "新订单归集";
			}
			List<Outer> oList = pushMap.get(key);
			List respList =null;
			if (oList != null && oList.size() > 0) {
				List<CoQueueAddResp> coQueueAddResps=null;
				try{
				coQueueAddResps = outerService.perform(
						oList, key, service_name);
				}catch(Exception e){
					throw new RuntimeException("订单同步失败");
				}
				if(ListUtil.isEmpty(coQueueAddResps))
					resp.setError_msg("订单重复执行");
				OrderOuter orderOuter = null;
				if(!ListUtil.isEmpty(coQueueAddResps) && coQueueAddResps.size()>0 
						&& coQueueAddResps.get(0).getCo() !=null 
						&& coQueueAddResps.get(0).getCo().getOrderOuterList() !=null 
						&& coQueueAddResps.get(0).getCo().getOrderOuterList().size()>0)
					orderOuter = coQueueAddResps.get(0).getCo().getOrderOuterList().get(0);
				String send_type = oList.get(0).getSending_type(); //现场交付订单实时处理失败则插入队列，作为日志跟踪
				// 异常捕获，有问题不影响订单同步
				try {
					long end = System.currentTimeMillis();
					logger.info("订单同步结束，总耗时========================="+ (end - start));
					logger.info("订单标准化开始==================================>,co_qid"+ coQueueAddResps.get(0).getCoQueue().getCo_id());
					coQueueAddResps.get(0).setUserSessionId(pushReq.getUserSessionId());
					respList = outerService.orderStanding(coQueueAddResps, key, service_name);
					resp.setOrderAddRespList(respList);
					long end1 = System.currentTimeMillis();
					logger.info("订单标准化结束，总耗时===============================>"+ (end1 - end)+ "订单操作总耗时："+ (end1 - start));
				} catch (Exception e) {
					e.printStackTrace();
					//add by wui新订单归集 写入失败，则插入队列，优化性能插入后移
					String msg =e.getMessage();
					int msgLength = 3500;
					if(StringUtils.isEmpty(msg)){
						msg = CommonTools.getErrorStr();
						if(!StringUtils.isEmpty(msg) && msg.length()>msgLength)
							msg = msg.substring(0,msgLength);
					}
					if (Consts.SERVICE_CODE_CO_GUIJI_NEW.equals(key) &&EcsOrderConsts.SHIPPING_TYPE_XJ.equals(send_type) ) {
						ICoQueueManager coQueueManager = SpringContextHolder.getBean("coQueueManager");
						for (CoQueueAddResp coQueueAddResp:coQueueAddResps) {
							CoQueue coQueue = coQueueAddResp.getCoQueue();
							coQueue.setFailure_desc(msg);
							coQueue.setStatus(Consts.CO_QUEUE_STATUS_XYSB);
							this.baseDaoSupport.insert("es_co_queue_bak", coQueue);
						}
					}
				}finally{
					try{
						if((EcsOrderConsts.SHIPPING_TYPE_XJ.equals(send_type) && respList !=null &&respList.size()>0 && respList.get(0) ==null) 
						||( EcsOrderConsts.SHIPPING_TYPE_XJ.equals(send_type) && respList !=null && respList.size()>0 && respList.get(0) !=null
						&&( ConstsCore.ERROR_FAIL.equals(((ZteResponse)respList.get(0)).getError_code())
						||  ConstsCore.RULE_ERROR_FAIL.equals(((ZteResponse)respList.get(0)).getError_code())))){
							if (Consts.SERVICE_CODE_CO_GUIJI_NEW.equals(key)) {
								ICoQueueManager coQueueManager = SpringContextHolder.getBean("coQueueManager");
								for (CoQueueAddResp coQueueAddResp:coQueueAddResps) {
									CoQueue coQueue = coQueueAddResp.getCoQueue();
									coQueue.setStatus(Consts.CO_QUEUE_STATUS_XYSB);
									this.baseDaoSupport.insert("es_co_queue_bak", coQueue);
								}
							}
					}
					}catch(Exception e){
						e.printStackTrace();
					}
					
				}
			}
		}
		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "外系统订单同步", summary = "外系统订单同步")
	@Transactional(propagation = Propagation.REQUIRED)
	public OrderAddResp syncAddOrder(OrderSyncReq req) throws ApiBusiException {

		OrderAddResp orderAddResp = new OrderAddResp();
		orderAddResp.setUserSessionId(req.getUserSessionId());
		List<OrderOuterResp> orderOuterResps = new ArrayList<OrderOuterResp>();
		for (OrderOuter or : req.getOrderOuterList()) {
			OrderOuterResp osresp = new OrderOuterResp();
			osresp.setOrderOuter(or);
			orderOuterResps.add(osresp);
		}
		orderServ.initParam(req);
		List<Order> list = orderServ.syOuterOrder(req, orderAddResp,
				orderOuterResps);
		orderAddResp.setOrderList(list);
		orderAddResp.setError_code("0");
		orderAddResp.setError_msg("成功");
		addReturn(req, orderAddResp);
		return orderAddResp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "获取外系统订单属性", summary = "获取外系统订单属性")
	public OrderOuterAttrQueryResp queryOuterAttrInsts(
			OrderOuterAttrQueryReq req) {
		OrderOuterAttrQueryResp resp = new OrderOuterAttrQueryResp();
		List<AttrInst> attrInsts = tplInstManager.getOuterAttrInst(req
				.getOrder_id());
		resp.setAttrInsts(attrInsts);
		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "获取支付日志", summary = "获取支付日志")
	public PaymentLogResp qryNotPayPaymentLog(PaymentLogReq req) {
		PaymentLogResp resp = new PaymentLogResp();
		PaymentLog paymentLog = orderManager.qryNotPayPaymentLog(
				req.getPayType(), req.getOrderId());
		resp.setPaymentLog(paymentLog);
		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "更新支付日志", summary = "更新支付日志")
	public PaySuccResp paySucc(PaySuccReq req) {

		PaySuccResp resp = new PaySuccResp();
		PaymentLog payLog = null;
		String logType = req.getLogType();
		if (Consts.PAY_LOG_TYPE_ALL.equals(logType.trim())) {
			payLog = this.orderNFlowManager.paySucc(req.getPaymentLog());
		} else if (Consts.PAY_LOG_TYPE_PRIMARY_KEY.equals(logType.trim())) {
			this.orderNFlowManager.paySucc(req.getPaymentLog()
					.getTransaction_id());
		} else if (Consts.PAY_LOG_TYPE_PRIMARY_KEY_TYPE.equals(logType.trim())) {
			this.orderNFlowManager.paySucc(req.getPaymentLog()
					.getTransaction_id(), req.getTypeCode());
		}
		resp.setPaymentLog(payLog);

		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "更新支付日志", summary = "更新支付日志")
	public PayResp pay(PayReq req) {
		PayResp resp = new PayResp();

		// .pay(paymentLog, "0".equals(onlineFlag),false);

		Map param = req.getParam();
		boolean flag = (Boolean) param.get("pay_flag");
		this.orderNFlowManager.pay(req.getPaymentLog(), req.isOnLine(), flag);

		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "支付异常处理方法", summary = "支付异常处理方法")
	public PayExcetionResp payException(PayExcetionReq req) {

		PayExcetionResp resp = new PayExcetionResp();
		// orderNFlowManager.payExcetion(paymentLog.getTransaction_id(),
		// orderAmount, null);
		this.orderNFlowManager.payExcetion(req.getPaymentLog()
				.getTransaction_id(), req.getOrderAmount(), null);
		resp.setError_code("0");
		resp.setError_msg("成功");

		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "批量查询订单信息", summary = "批量查询订单信息")
	public OrderQryBatchResp gerOrderByBatchId(OrderQryBatchReq req) {

		OrderQryBatchResp resp = new OrderQryBatchResp();
		// list = orderManager.getByBatchID(orderId);
		List<Order> list = this.orderManager.getByBatchID(req.getBatchId());
		resp.setOrderList(list);

		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "提交单个订单", summary = "提交单个订单")
	public OrderCommitResp commitOrder(OrderCommitReq req) {

		OrderCommitResp resp = new OrderCommitResp();
		// order = orderManager.get(orderId);
		Order order = this.orderManager.get(req.getOrderId());
		resp.setOrder(order);
		resp.setError_code("0");
		resp.setError_msg("成功");

		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "修改支付订单", summary = "修改支付订单")
	public PayUptResp updatePaymentMoney(PayUptReq req) {
		PayUptResp resp = new PayUptResp();
		// orderManager.updatePaymentMoney(order_id,transactionId,pay_money,paymentLog.getPayment_id());
		this.orderManager.updatePaymentMoney(req.getOrderId(),
				req.getTransactionId(), req.getPayAmount(), req.getPaymentId());
		resp.setError_code("0");
		resp.setError_msg("成功");

		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "查询支付信息", summary = "查询支付信息")
	public PayQryResp getPayment(PayQryReq req) {
		PayQryResp resp = new PayQryResp();
		// PaymentLog paymentLog =
		// orderManager.qryPaymentLogByTransactionId(transactionId);
		PaymentLog paymentLog = this.orderManager
				.qryPaymentLogByTransactionId(req.getTransactionId());
		resp.setPaymentLog(paymentLog);
		return resp;
	}

	@Override
	public OrderCountGetResp countOrders(OrderCountGetReq req) {
		long count = this.orderManager.countOrders(0, req.getOrderQueryParam(),
				req.getEvent());
		OrderCountGetResp resp = new OrderCountGetResp();
		resp.setCount(count);
		resp.setError_code("0");
		resp.setError_msg("成功");
		this.addReturn(req, resp);
		return resp;
	}

	@Override
	public OrderGetResp getOrder(OrderGetReq req) {
		OrderGetResp resp = new OrderGetResp();
		Order order = orderManager.get(req.getOrder_id());
		resp.setOrder(order);
		resp.setError_code("0");
		resp.setError_msg("成功");
		this.addReturn(req, resp);
		return resp;
	}

	@Override
	public OrderItemListResp listOrderItems(OrderItemListReq req) {
		OrderItemListResp resp = new OrderItemListResp();
		List<Map> list = orderManager.listGoodsItems(req.getOrder_id().trim());
		resp.setOrderItems(list);
		resp.setError_code("0");
		resp.setError_msg("成功");
		this.addReturn(req, resp);
		return resp;
	}

	@Override
	public OrderItemListHisResp listOrderItemsHis(OrderItemListHisReq req) {
		OrderItemListHisResp resp = new OrderItemListHisResp();
		List<Map> list = orderManager.listGoodsItemsHis(req.getOrder_id()
				.trim());
		resp.setOrderItems(list);
		resp.setError_code("0");
		resp.setError_msg("成功");
		this.addReturn(req, resp);
		return resp;
	}

	@Override
	public OrderOuterQryResp qryOrderOuter(OrderOuterQryReq req) {

		OrderOuter orderOuter = orderManager.qryOrderOuter(req.getOrder_id());

		OrderOuterQryResp orderOuterQryResp = new OrderOuterQryResp();
		orderOuterQryResp.setOrderOuter(orderOuter);
		addReturn(req, orderOuterQryResp);
		return orderOuterQryResp;
	}

	@Override
	public OrderExtInfoGetResp getOrderExtInfo(OrderExtInfoGetReq req) {
		String sql = "select t.field_name,t.field_value from es_attr_inst t where t.order_id=? and t.source_from=?";
		List<Map> list = this.baseDaoSupport.queryForList(sql,
				req.getOrder_id(), ManagerUtils.getSourceFrom());
		Map extMap = new HashMap();
		if (list != null && list.size() > 0) {
			for (Map m : list) {
				extMap.put(m.get("field_name"), m.get("field_value"));
			}
		}
		OrderExtInfoGetResp resp = new OrderExtInfoGetResp();
		resp.setExtMap(extMap);
		resp.setError_code("0");
		resp.setError_msg("成功");
		this.addReturn(req, resp);
		return resp;
	}

	@Override
	@SuppressWarnings("unchecked")
	public AdjustOrderPriceResp adjustOrderPrice(AdjustOrderPriceReq req) {
		AdjustOrderPriceResp resp = new AdjustOrderPriceResp();
		String sql = SF.orderSql("ADJECT_ORDER_PRICE");
		try {
			List<Map<String, Object>> list = this.baseDaoSupport.queryForList(
					sql, req.getAdjust_key(), req.getAdjust_type());
			if (null != list && list.size() == 1) {
				Map<String, Object> result = list.get(0);
				String price = Const.getStrValue(result, "price");
				resp.setAjust_amount(Double.valueOf(price));
				resp.setError_code("0");
				resp.setError_msg("成功");
			} else {
				resp.setError_msg("调价配置信息错误，请检查");
			}
		} catch (Exception e) {
			resp.setError_msg("调价异常");
		}
		return resp;
	}

	@Override
	@SuppressWarnings("unchecked")
	public AdjustListResp getAdjustList(AdjustListReq req) {
		AdjustListResp resp = new AdjustListResp();
		String sql = SF.orderSql("ADJECT_LIST_BY_TYPE");
		try {
			List<Map<String, String>> list = this.baseDaoSupport.queryForList(
					sql, new RowMapper() {
						@Override
						public Object mapRow(ResultSet rs, int arg1)
								throws SQLException {
							Map<String, String> map = new HashMap<String, String>(); // 实体类为本地类,掌厅无法解析,转化为Map结构
							map.put("adjust_key", rs.getObject("adjust_key")
									+ "");
							map.put("adjust_type", rs.getObject("adjust_type")
									+ "");
							map.put("price", rs.getObject("price") + "");
							map.put("adjust_name", rs.getObject("adjust_name")
									+ "");
							return map;
						}
					}, req.getAdjust_type());
			resp.setAjust_list(list);
			resp.setError_code("0");
			resp.setError_msg("成功");
		} catch (Exception e) {
			e.printStackTrace();
			resp.setError_msg("异常");
		}
		return resp;
	}

	/**
	 * 获取调度流程
	 * 
	 * @param order_id
	 * @return
	 * @throws Exception
	 */
	@Override
	public BusiCompResponse execBusiComp(BusiCompRequest busiCompRequest)
			throws Exception {

		if ("zteCommonTraceRule.matchingWorkflow".equals(busiCompRequest
				.getEnginePath())) {
			Map params = busiCompRequest.getQueryParams();
			WorkFlowFact fact = (WorkFlowFact) params.get("fact");
			String flow_id = (String) params.get("flow_id");
			fact.toStringN();
			fact.setFlow_id(flow_id);
		}
		return BusiCompPraser.performBusiComp(busiCompRequest);
	}

	@Override
	public BusiCompResponse execOrderStandingPlanBusiComp(BusiCompRequest busiCompRequest){
		return BusiCompPraser.execOrderStandingPlanBusiComp(busiCompRequest);
	}
	@Override
	public AttrDefGetResp getAttrDef(AttrDefGetReq req) {
//		INetCache cache = CacheFactory.getCacheByType("");
//		String field_id = req.getField_attr_id();
//		String key = CacheUtils.ATTR_DEF_CACHE_ID + field_id;
		AttrDef attrDef = CacheUtils.getCacheAttrDefByFiledAttrId(req.getField_attr_id());
//		int space = Const.CACHE_SPACE;
//		AttrDef attrDef = (AttrDef) cache.get(space, key);
//		if (attrDef == null) {
//			attrDef = attrDefNManager.getAttrDef(req.getField_attr_id());
//			if (attrDef != null)
//				cache.set(space, key, attrDef);
//		}
		AttrDefGetResp resp = new AttrDefGetResp();
		resp.setAttrDef(attrDef);
		resp.setError_code("0");
		resp.setError_msg("成功");
		this.addReturn(req, resp);
		return resp;
	}

	@Override
	public List<AttrDef> refreshOrderTreeAttr(RefreshOrderTreeAttrReq req) {
		CacheUtils.initCacheAttrDef();
		/*List<AttrDef> attrDefList = new ArrayList<AttrDef>();
		String sql = SF.orderSql("ORDER_TREE_ATTR_LIST");
		List<AttrDef> attr_def_list = baseDaoSupport.queryForList(sql,
				AttrDef.class, ManagerUtils.getSourceFrom());
		if (attr_def_list != null && attr_def_list.size() > 0) {
			INetCache cache = CacheFactory.getCacheByType("");
			for (int i = 0; i < attr_def_list.size(); i++) {
				AttrDef attrDef = attr_def_list.get(i);
				String field_id = attrDef.getField_attr_id();
				String key = CacheUtils.ATTR_DEF_CACHE_ID + field_id;
				int space = Const.CACHE_SPACE;
				if (attrDef != null) {
					cache.set(space, key, attrDef);
					cache.set(space,attrDef.getField_name() + attrDef.getAttr_spec_id(),attrDef);
				}
				attrDefList.add(attrDef);
			}
		}*/
		return null;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "页面属性数据装载渲染", summary = "页面属性数据装载渲染")
	public AttrInstLoadResp pageLoadAttr(AttrInstLoadReq req) {
		return tplInstManager.pageLoadAttr(req);
	}

	@Override
	public WorkFlowCallBackResp workFlowCallBack(WorkFlowCallBackReq req) {
		WorkFlowCallBackResp resp = new WorkFlowCallBackResp();
		BusiCompRequest busi = new BusiCompRequest();
		Map queryParams = new HashMap();
		queryParams.put("order_id", req.getOrder_id());
		queryParams.put("next_trace_id", req.getNext_trace_id());
		queryParams.put("curr_trace_id", req.getCurr_trace_id());
		queryParams.put("flow_id", req.getFlow_id());
		queryParams.put("flow_req", req);
		busi.setEnginePath("flowCallback.exec");
		busi.setQueryParams(queryParams);
		try {
			BusiCompResponse response = this.execBusiComp(busi);
			resp.setError_code(response.getError_code());
			resp.setError_msg(response.getError_msg());
		} catch (Exception e) {
			e.printStackTrace();
			resp.setError_code("1");
			resp.setError_msg("失败");
		}
		this.addReturn(req, resp);
		return resp;
	}

	@Override
	public AttrValueGetResp getAttrValue(AttrValueGetReq req) {
		String order_id = req.getOrder_id();
		String field_name = req.getField_name();
		String field_value = null;
		AttrValueGetResp resp = new AttrValueGetResp();
		try {
			field_value = attrDefNManager
					.getAttrInstValue(order_id, field_name);
			resp.setField_value(field_value);
			resp.setResult(true);
			resp.setError_code("0");
			resp.setError_msg("成功");
		} catch (Exception e) {
			e.printStackTrace();
			resp.setResult(false);
			resp.setError_code("-1");
			resp.setError_msg("失败");
		}
		return resp;
	}

	@Override
	public OrderItemsAddResp addOrderItems(OrderItemsAddReq req) {
		for (OrderItem oi : req.getOrderItemList()) {
			orderManager.addOrderItem(oi);
		}
		OrderItemsAddResp resp = new OrderItemsAddResp();
		resp.setOrderItemList(req.getOrderItemList());
		resp.setError_code("0");
		resp.setError_msg("成功");
		this.addReturn(req, resp);
		return resp;
	}

	@Override
	public OrderItemsDelResp delOrderItems(OrderItemsDelReq req) {
		OrderItemsDelResp resp = new OrderItemsDelResp();
		orderManager.delOrderItem(req.getOrder_item_id());
		resp.setError_code("0");
		resp.setError_msg("成功");
		this.addReturn(req, resp);
		return resp;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public ShipItemSyncResp syncShipItems(ShipItemSyncReq req) {
		ShipItemSyncResp resp = new ShipItemSyncResp();
		Delivery dv = deliveryManager.getByOrderId(req.getOrder_id(),
				OrderStatus.DELIVERY_TYPE_0);
		if (dv == null)
			CommonTools.addError("1", "没有找到订单物流信息");
		List<OrderItem> orderItems = orderNFlowManager.listNotShipGoodsItem(req
				.getOrder_id());
		List<DeliveryItem> itemList = new ArrayList<DeliveryItem>();
		int totalNum = 0;
		for (OrderItem oi : orderItems) {
			DeliveryItem item = new DeliveryItem();
			int ship_num = oi.getNum() - oi.getShip_num();
			item.setGoods_id(oi.getGoods_id());
			item.setName(oi.getName());
			item.setNum(ship_num);
			item.setProduct_id(oi.getProduct_id());
			item.setSn(oi.getSn());
			item.setItemtype(0);
			item.setOrder_item_id(oi.getItem_id());
			itemList.add(item);
			if (ship_num > 0) {// 有没发货商品才加到物流item表
				totalNum += item.getNum();
				// 修改子单发货数量为全发货
				orderManager.updateOrderItemShipNum(oi.getItem_id(),
						oi.getNum());
				deliveryManager.addDeliveryItem(item);
			}
		}
		resp.setError_code("0");
		resp.setError_msg("成功");
		this.addReturn(req, resp);
		return resp;
	}

	@Override
	public ShippingConfirmResp confirmShippingStatus(ShippingConfirmReq req) {
		ShippingConfirmResp resp = new ShippingConfirmResp();
		Delivery dv = deliveryManager.getByOrderId(req.getOrder_id());
		if (dv == null)
			CommonTools.addError("1", "没有找到订单物流信息");
		deliveryManager.updateShipStatus(dv.getDelivery_id(), 1);
		orderManager.updateShipStatus(req.getOrder_id(), 1);
		resp.setError_code("0");
		resp.setError_msg("成功");
		this.addReturn(req, resp);
		return resp;
	}

	@Override
	public DeliveryItemAddResp addDeliveryItem(DeliveryItemAddReq req) {
		deliveryManager.addDeliveryItem(req.getDeliveryItem());
		DeliveryItemAddResp resp = new DeliveryItemAddResp();
		resp.setDeliveryItem(req.getDeliveryItem());
		resp.setError_code("0");
		resp.setError_msg("成功");
		this.addReturn(req, resp);
		return resp;
	}

	@Override
	public DeliveryItemsQueryResp queryDeliveryItems(DeliveryItemsQueryReq req) {
		DeliveryItemsQueryResp resp = new DeliveryItemsQueryResp();
		List<OrderDeliveryItemBusiRequest> deliveryItems = deliveryManager
				.queryDeliveryItems(req.getOrder_id(), req.getItemType());
		resp.setDeliveryItems(deliveryItems);
		resp.setError_code("0");
		resp.setError_msg("成功");
		this.addReturn(req, resp);
		return resp;
	}

	@Override
	public OrderCheckTraceFlowFinishResp finishCheckTraceFlow(
			OrderCheckTraceFlowFinishReq req) {
		OrderCheckTraceFlowFinishResp resp = new OrderCheckTraceFlowFinishResp();
		Delivery dv = deliveryManager.getByOrderId(req.getOrder_id(),
				OrderStatus.DELIVERY_TYPE_0);
		if (dv != null) {
			deliveryManager.updateReissueDeliveryItemId(dv.getDelivery_id(),
					req.getOrder_id());
		}
		resp.setError_code("0");
		resp.setError_msg("成功");
		this.addReturn(req, resp);
		return resp;
	}

	@Override
	public ReissueGoodsShippingAddResp addReissueGoodsShippingDelivery(
			ReissueGoodsShippingAddReq req) {
		ReissueGoodsShippingAddResp resp = new ReissueGoodsShippingAddResp();
		OrderDeliveryBusiRequest delivery = req.getDelivery();
		deliveryManager.addDelivery(delivery);
		String delivery_id = delivery.getDelivery_id();
		for (String item_id : req.getDeliveri_item_idArray()) {
			deliveryManager.updateDItemDeliveryId(item_id, delivery_id);
		}
		resp.setError_code("0");
		resp.setError_msg("成功");
		this.addReturn(req, resp);
		return resp;
	}

	@Override
	public OrderFlowExceptionBusiDoResp doOrderFlowExceptionBusi(
			OrderFlowExceptionBusiDoReq req) {
		OrderFlowExceptionBusiDoResp resp = new OrderFlowExceptionBusiDoResp();
		String order_id = req.getOrder_id();
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance()
				.getOrderTree(order_id);
		OrderExtBusiRequest orderExtBusiRequest = orderTree
				.getOrderExtBusiRequest();
		String abnormal_type = orderExtBusiRequest.getAbnormal_type();
		
		HasAutoExceptionReq autoreq = new HasAutoExceptionReq();
		autoreq.setOrder_id(order_id);
		IEcsOrdServices ecsOrdServices = SpringContextHolder
				.getBean("ecsOrdServices");
		HasAutoExceptionResp autoresp = ecsOrdServices.hasAutoException(autoreq);
//		if (EcsOrderConsts.NO_DEFAULT_VALUE.equals(autoresp.getHasAutoException()))

		if (EcsOrderConsts.NO_DEFAULT_VALUE.equals(autoresp.getHasAutoException())&&EcsOrderConsts.ORDER_ABNORMAL_TYPE_1.equals(orderExtBusiRequest
				.getAbnormal_type())) {
			// 如果是人工标志 异常修改为正常单	
			orderExtBusiRequest
					.setAbnormal_status(EcsOrderConsts.ORDER_ABNORMAL_STATUS_0);// 修改为正常单
			orderExtBusiRequest
					.setAbnormal_type(EcsOrderConsts.ORDER_ABNORMAL_TYPE_0);// 修改为异常单
			orderExtBusiRequest.setOrder_id(order_id);

			orderExtBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			orderExtBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
			// 修改订单流程状态
			orderExtBusiRequest.store();
			try {
				/*//恢复异常通知老系统
				RuleExeLogDelReq ruleExeLogDelReq = new RuleExeLogDelReq();
				ruleExeLogDelReq.setObj_id(order_id);
				ruleExeLogDelReq.setPlan_id(new String[]{EcsOrderConsts.ORDER_STATUS_SYN_OLD_SYS_PLAN});
				ruleExeLogDelReq.setRule_id(EcsOrderConsts.ORDER_STATUS_SYN_OLD_SYS_RULE);
				ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
				RuleExeLogDelResp ruleExeLogDelResp = client.execute(ruleExeLogDelReq, RuleExeLogDelResp.class);
				
				PlanRuleTreeExeReq plan = new PlanRuleTreeExeReq();
				TacheFact fact = new TacheFact();
				fact.setOrder_id(order_id);
				//fact.setSrc_tache_code(CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest().getFlow_trace_id());
				//fact.setTar_tache_code(CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest().getFlow_trace_id());
				//fact.setProcess_type(EcsOrderConsts.PROCESS_TYPE_5);
				//fact.setException_from("ORD");
				//fact.setException_type("");
				//fact.setException_desc("恢复异常");
				fact.setAbnormal_type(abnormal_type);
				plan.setFact(fact);
				plan.setPlan_id(EcsOrderConsts.ORDER_STATUS_SYN_OLD_SYS_PLAN);
				PlanRuleTreeExeResp planResp = CommonDataFactory.getInstance().exePlanRuleTree(plan);*/
				
				// 记录异常恢复记录 只有人工标志异常才记录日志
				// 写日志
//				IEcsOrdServices ecsOrdServices = SpringContextHolder
//						.getBean("ecsOrdServices");
				AdminUser user = ManagerUtils.getAdminUser();
				InsertOrderHandLogReq lreq = new InsertOrderHandLogReq();
				String flow_id = orderTree.getOrderExtBusiRequest()
						.getFlow_id();
				String flowTraceId = orderTree.getOrderExtBusiRequest()
						.getFlow_trace_id();
				lreq.setOrder_id(order_id);
				lreq.setFlow_id(flow_id);
				lreq.setFlow_trace_id(flowTraceId);
				lreq.setComments("人工标志异常恢复");
				lreq.setHandler_type(Const.ORDER_HANDLER_TYPE_EXCEPTION_RECOVER);
				lreq.setType_code(EcsOrderConsts.ORDER_ABNORMAL_STATUS_0);
				if (user != null) {
					lreq.setOp_id(user.getUserid());
					lreq.setOp_name(user.getUsername());
				}
				ecsOrdServices.insertOrderHandLog(lreq);
			} catch (Exception ex) {
				ex.printStackTrace();
			}

		} else if (EcsOrderConsts.ORDER_ABNORMAL_TYPE_3
				.equals(orderExtBusiRequest.getAbnormal_type())
				|| EcsOrderConsts.ORDER_ABNORMAL_TYPE_2
						.equals(orderExtBusiRequest.getAbnormal_type())||EcsOrderConsts.IS_DEFAULT_VALUE.equals(autoresp.getHasAutoException())) {
			// 自动化异常 调用恢复异常业务组件 订单跳转到质检环节
			BusiCompRequest busi = new BusiCompRequest();
			Map queryParams = new HashMap();
			queryParams.put("order_id", order_id);
			queryParams.put(EcsOrderConsts.EXCEPTION_FROM,
					EcsOrderConsts.EXCEPTION_FROM_ORD);
			queryParams.put(EcsOrderConsts.EXCEPTION_TYPE,
					orderExtBusiRequest.getException_type());
			queryParams.put(EcsOrderConsts.EXCEPTION_REMARK,
					orderExtBusiRequest.getException_desc());
			busi.setEnginePath("zteCommonTraceRule.restorationException");
			busi.setOrder_id(order_id);
			busi.setQueryParams(queryParams);
			ZteResponse response = null;
			try {
				response = this.execBusiComp(busi);
			} catch (Exception e) {
				CommonTools.addError("1", "恢复异常失败");
			}

			if (!EcsOrderConsts.RULE_EXE_FLAG_SUCC.equals(response
					.getError_code())) {
				CommonTools.addError("-1", response.getError_msg());
			}
		}
		orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		String trace_id = orderTree.getOrderExtBusiRequest().getFlow_trace_id();
		// 自动化异常：调用异常恢复业务组件，订单强制流转到质检稽核环节。
		if (EcsOrderConsts.ORDER_ABNORMAL_TYPE_3.equals(abnormal_type)
				&& !EcsOrderConsts.DIC_ORDER_NODE_F.equals(trace_id)
				&& !EcsOrderConsts.DIC_ORDER_NODE_H.equals(trace_id)
				&& !EcsOrderConsts.DIC_ORDER_NODE_J.equals(trace_id)
				&& !EcsOrderConsts.DIC_ORDER_NODE_L.equals(trace_id) && EcsOrderConsts.ORDER_MODEL_01.equals(orderTree.getOrderExtBusiRequest().getOrder_model())) {
			DcPublicInfoCacheProxy dcPublicCache = new DcPublicInfoCacheProxy(
					dcPublicInfoManager);
			List<Map> workNo = dcPublicCache
					.getList(EcsOrderConsts.work_flow_staff_no);
			Map workNoMap = workNo.get(0);
			int staffId = Integer.parseInt(workNoMap.get("pkey").toString());
			String staffName = (String) workNoMap.get("pname");
			String processInstanceId = orderTree.getOrderExtBusiRequest()
					.getFlow_inst_id();
			String flowStatus = Const.FLOW_DEAL_TYPE_AM;
			// 如果是自动化异常单直接跳到质检环节
			String next_trace_id = EcsOrderConsts.DIC_ORDER_NODE_F;
			while (true) {
				UosDealReq ureq = new UosDealReq();
				ureq.setStaffId(staffId);
				ureq.setStaffName("");
				ureq.setProcessInstanceId(processInstanceId);
				ureq.setTacheCode(trace_id);
				ureq.setDealType(flowStatus);// 环节处理状态
				UosDealResp flowresp = uosService.dealProcess(ureq);// 工作流返回下一个环节的信息
				ArrayList<WorkItem> workItems = flowresp.getWorkItems();
				// 修改环节为下一个环节
				// 修改订单处理状态为未处理
				if (workItems != null && workItems.size() > 0) {
					// 目前只有一个不节，所以只拿第一个就行了
					WorkItem wi = workItems.get(0);
					trace_id = wi.getTacheCode();
					if (EcsOrderConsts.DIC_ORDER_NODE_F.equals(trace_id)) {
						next_trace_id = EcsOrderConsts.DIC_ORDER_NODE_F;
						// 跳到质检环节
						break;
					} else if (flowresp.isEnd()) {
						next_trace_id = trace_id;
						// 结束流程
						break;
					}
				}
			}

			orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
			orderExtBusiRequest = orderTree.getOrderExtBusiRequest();
			orderExtBusiRequest.setFlow_trace_id(next_trace_id);
			orderExtBusiRequest.setOrder_id(order_id);
			orderExtBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			orderExtBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
			// 修改订单流程状态
			orderExtBusiRequest.store();
		}

		resp.setError_code("0");
		resp.setError_msg("成功");
		this.addReturn(req, resp);
		return resp;
	}

	@Override
	public DeliveryItemsQueryByDeIdResp queryDeliveryItemsByDeId(
			DeliveryItemsQueryByDeIdReq req) {
		DeliveryItemsQueryByDeIdResp resp = new DeliveryItemsQueryByDeIdResp();
		List<DeliveryItem> deliveryItems = deliveryManager.qryDeliveryItems(req
				.getDelivery_id());
		resp.setDeliveryItems(deliveryItems);
		resp.setError_code("0");
		resp.setError_msg("成功");
		this.addReturn(req, resp);
		return resp;
	}

	@Override
	public DeliveryItemDelResp delDeliveryItem(DeliveryItemDelReq req) {
		DeliveryItemDelResp resp = new DeliveryItemDelResp();
		deliveryManager.delDeliveryItem(req.getItem_id());
		resp.setError_code("0");
		resp.setError_msg("成功");
		this.addReturn(req, resp);
		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "刷新订单数属性", summary = "刷新订单数属性", isOpen = false)
	public RefreshAttrDefResp refreshAttrDef(RefreshAttrDefReq req) {
		// TODO Auto-generated method stub
		RefreshAttrDefResp resp = new RefreshAttrDefResp();
		attrDefNManager.resetAttrCache();
		resp.setError_code("0");
		resp.setError_msg("成功");
		this.addReturn(req, resp);
		return resp;
	}

	@Override
	public OrderHandleLogsListResp listOrderHandlerLogs(
			OrderHandleLogsListReq req) {
		OrderHandleLogsListResp resp = new OrderHandleLogsListResp();
		List<OrderHandleLogsReq> list = orderManager.queryOrderHandlerLogs(
				req.getOrder_id(), req.getHandler_type(),req.getOrder_is_his());
		resp.setLogList(list);
		resp.setError_code("0");
		resp.setError_msg("成功");
		this.addReturn(req, resp);
		return resp;
	}

	@Override
	public OrderExceptionLogsListResp listOrderExceptionLogs(
			OrderExceptionLogsListReq req) {
		OrderExceptionLogsListResp resp = new OrderExceptionLogsListResp();
		List<OrderExceptionCollectReq> list = orderManager
				.queryOrderExceptionLogs(req);
		resp.setExceptionList(list);
		resp.setError_code("0");
		resp.setError_msg("成功");
		this.addReturn(req, resp);
		return resp;
	}
	
	@Override
	public OutcallLogsListResp listOutcallLogs(
			OutcallLogsListReq req) {
		OutcallLogsListResp resp = new OutcallLogsListResp();
		List<OutcallLogsReq> list = orderManager
				.queryOutcallLogs(req.getOrder_id());
		resp.setOutcallLogList(list);
		resp.setError_code("0");
		resp.setError_msg("成功");
		this.addReturn(req, resp);
		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "查询订单处理日志", summary = "查询订单处理日志", isOpen = false)
	public GoodsTypeGetResp getGoodsTypeName(GoodsTypeGetReq req) {
		// TODO Auto-generated method stub
		GoodsTypeGetResp resp = goodsTypeService.getGoodsType(req);
		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "订单同步插入外系统属性数据", summary = "订单同步插入外系统属性数据", isOpen = false)
	public ZteResponse insertOuterInstAttr(OrderOuterSyAttrReq req)
			throws Exception {
		return outerService.insertOuterInstAttr(req);
	}

	@Override
	public HandlerOrderRoleResp handlerOrderRole(HandlerOrderRoleReq req) {
		HandlerOrderRoleResp resp = new HandlerOrderRoleResp();
		List<OrderRole> orderRoleList = OrderRoleDataUtil.handlerOrderRole(req
				.getOrder_id(),req.getOrder_city_code(),req.getFlow_trace_id(),req.getOrder_model());
		resp.setOrderRoleList(orderRoleList);
		resp.setError_code("0");
		resp.setError_msg("成功");
		this.addReturn(req, resp);
		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "刷新权限数据", summary = "刷新权限数据", isOpen = false)
	public List<RoleData> cacheOrderRoleData() {
		List<RoleData> list = orderRoleDataManager.cacheOrderRoleData();
		return list;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "获取用户是否有权限处理当前状态的订单", summary = "获取用户是否有权限处理当前状态的订单", isOpen = false)
	public OrderAuthorityByUserResp getOrderAuthorityByUser(OrderAuthorityByUserReq req) {
		OrderAuthorityByUserResp resp = new OrderAuthorityByUserResp();
		resp.setOrderAuthorityFlag(OrderRoleDataUtil.getOrderAuthorityByUser(req.getOrder_id(),req.getUser_id()));
		resp.setError_code("0");
		resp.setError_msg("成功");
		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "订单手动退单申请成功，总部回单状态修改", summary = "订单手动退单申请成功，总部回单状态修改", isOpen = false)
	public ChargebackStatuChgResp chargebackStackChg(ChargebackStatuChgReq req) {
		String flag = req.getFlag();
		String zb_order_id = req.getZb_order_id();
		orderManager.chargebackStackChg(flag, zb_order_id);
		return new ChargebackStatuChgResp();
	}

	@Override
	public AttrTableCacheGetResp getCacheAttrTable(AttrTableCacheGetReq req) {
		AttrTableCache attrTable = CacheUtils.getAttrTable(req.getField_name(),
				req.getType());
		AttrTableCacheGetResp resp = new AttrTableCacheGetResp();
		resp.setAttrTable(attrTable);
		resp.setError_code("0");
		resp.setError_msg("成功");
		this.addReturn(req, resp);
		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "添加打折", summary = "添加打折", isOpen = false)
	public PromotionAddResp addPromotion(PromotionAddReq req) {
		PromotionAddResp resp = promotionServ.add(req);
		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "删除打折", summary = "删除打折", isOpen = false)
	public PromotionDeleteResp deletePromotion(PromotionDeleteReq req) {
		PromotionDeleteResp promotionDeleteResp = new PromotionDeleteResp();
		promotionServ.delete(req.getIdStr());
		promotionDeleteResp.setError_code("0");
		promotionDeleteResp.setError_msg("成功");
		return promotionDeleteResp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "打折列表", summary = "打折列表", isOpen = false)
	public PromotionResp listPromotion(PromotionReq req) {
//		req.setPromotion(new Promotion());
//		req.getPromotion().setPromotion_type("001");
//		req.setGoodsid("");
		PromotionActivity promotionActivity = new PromotionActivity();
		promotionActivity.setBegin_time(req.getBegin_time());
		promotionActivity.setEnd_time(req.getEnd_time());
//		promotionActivity.setPartner_id(req.getPartnerid());
		promotionActivity.setUserid(req.getUserid());
		promotionActivity.setName(req.getName());
		
		Promotion promotion = new Promotion();
		promotion.setPromotion_type(req.getPromotion_type());
//		req.setActivity(promotionActivity);
		
		PromotionResp promotionResp = new PromotionResp();
		
		Page page = promotionManager.queryPromotionAct(
				promotionActivity, promotion, req.getGoodsid(),
				req.getPageNo(), req.getPageSize());
		promotionResp.setWebpage(page);
		promotionResp.setError_code("0");
		promotionResp.setError_msg("成功");
		return promotionResp;
	}


	

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "检查webService服务是否配置有", summary = "检查webService服务是否配置有")
	public CheckOpenServiceResp checkOpenService(CheckOpenServiceReq req) {
		CheckOpenServiceResp  resp=new CheckOpenServiceResp();
		OpenServiceCfg openServiceCfg=orderQueueManager.getOpenServiceCfgByName(req.getMethod(), req.getVersion());
		if(openServiceCfg!=null){
			resp.setFlag(true);
		}
		resp.setError_code("0");
		resp.setError_msg("成功");
		this.addReturn(req, resp);
		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "订单标准化", summary = "订单标准化")
	public OrderCreateResp OrderCreate(OrderCreateReq req) {
		OrderCreateResp  resp=new OrderCreateResp();
		
		
		orderQueueManager.OrderCreate(req.getBase_co_id(),req.getBase_order_id());
		//待加代码
		resp.setError_code("0");
		resp.setError_msg("成功");
		this.addReturn(req, resp);
		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "订单操作日志保存", summary = "订单操作日志保存")
	public OrderHandleLogsAddResp OrderHandleLogsAdd(OrderHandleLogsAddReq req) {
		OrderHandleLogsAddResp  resp=new OrderHandleLogsAddResp();
		orderQueueManager.OrderHandLogSave(req.getOrderHandleLogs());
		resp.setError_code("0");
		resp.setError_msg("成功");
		this.addReturn(req, resp);
		return resp;
	}
	
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "消息队列数据转移到失败表", summary = "消息队列数据转移到失败表")
	public OrderQueueFailSaveResp OrderQueueFailSave(OrderQueueFailSaveReq req) {
		OrderQueueFailSaveResp  resp=new OrderQueueFailSaveResp();
		orderQueueManager.OrderQueueFailSave(req.getCo_id());
		resp.setError_code("0");
		resp.setError_msg("成功");
		this.addReturn(req, resp);
		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "消息队列数据归档", summary = "消息队列数据归档")
	public OrderQueueHisSaveResp OrderQueueHisSave(OrderQueueHisSaveReq req) {
		OrderQueueHisSaveResp  resp=new OrderQueueHisSaveResp();
		orderQueueManager.OrderQueueHisSave(req.getCo_id());
		resp.setError_code("0");
		resp.setError_msg("成功");
		this.addReturn(req, resp);
		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "订单修改", summary = "订单修改")
	public OrderUpdateResp OrderUpdate(OrderUpdateReq req) {
		OrderUpdateResp  resp=new OrderUpdateResp();
		String respStr=orderQueueManager.OrderUpdate(req.getOrder_id(), req.getCo_id());
		resp.setError_code("0");
		resp.setError_msg("成功");
		this.addReturn(req, resp);
		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "订单归集", summary = "订单归集")
	public OrderCollectionResp orderCollection(OrderCollectionReq req) {
		OrderCollectionResp resp=new OrderCollectionResp();
		String respStr=orderQueueManager.orderCollection(req);
		
		resp.setRespStr(respStr);
		resp.setError_code("0");
		resp.setError_msg("成功");
		this.addReturn(req, resp);
		return resp;
	}


	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "查询没处理的订单工作项", summary = "查询没处理的订单工作项")
	public OrderWorkItemQueryResp orderWorkItemQuery(OrderWorkItemQueryReq req) {
		
		OrderWorkItemQueryResp resp=orderWorkItemManager.QueryOrderWorkItemByOrderId(req.getOrder_id());

		resp.setError_code("0");
		resp.setError_msg("成功");
		this.addReturn(req, resp);
		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "根据外部订单号获取内部订单号", summary = "根据外部订单号获取内部订单号")
	public OrderIdResp orderId(OrderIdReq req) {
		OrderIdResp orderIdResp = new OrderIdResp();
		orderIdResp.setOrder_id(orderManager.getOrderIdByOrderOutId(req.getOrder_out_id()));
		orderIdResp.setError_code("0");
		orderIdResp.setError_msg("成功");
		return orderIdResp;
	}


	public AttrInstValueEditResp editAttrInstValue(AttrInstValueEditReq req) {
		return null;
	}
	
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "匹配错误字典", summary = "匹配字典")
	public MatchDictLogsResp matchDictLogs(MatchDictLogsReq req)
			throws ApiBusiException {
		MatchDictLogsResp resp = new MatchDictLogsResp();
		dictMatchManager.matchDictLogs(req);
		resp.setError_code("0");
		resp.setError_msg("成功");
		return resp;
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "修改接口日志状态", summary = "修改接口日志状态")
	public MatchInfLogStateResp matchInfLogState(MatchInfLogStateReq req) {
		MatchInfLogStateResp resp = new MatchInfLogStateResp();
		dictMatchManager.matchInfLogState(req, resp);
		resp.setError_code("0");
		resp.setError_msg("成功");
		return resp;
	}
	
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "根据物流单号获取order_id", summary = "根据物流单号获取order_id")
	public GetOrderByLogiNoResp getOrdByLogiNo(GetOrderByLogiNoReq req)
			throws ApiBusiException {
		String logi_no = req.getLogi_no();
		String order_id = orderManager.getOrderIdByLogiNo(logi_no);
		GetOrderByLogiNoResp resp = new GetOrderByLogiNoResp();
		if(!StringUtils.isEmpty(order_id)){
			resp.setError_code("0");
			resp.setError_msg("成功");
			resp.setOrder_id(order_id);
		}
		else{
			resp.setError_code("-1");
			resp.setError_msg("失败");
		}
		this.addReturn(req, resp);
		return resp;
	}
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "总商触发自动开户环节调用", summary = "总商触发自动开户环节调用")
	public ZBStartOpenAccountResp zbStartOpenAccount(ZBStartOpenAccountReq req) throws ApiBusiException{
		ZBStartOpenAccountResp resp = new ZBStartOpenAccountResp();
		String out_tid=req.getOrderId();
		logger.info("----------------------"+out_tid);
		if("4725006341".equals(out_tid)){
			logger.info("==============");
		}
		
		OrderTreeBusiRequest orderTree= CommonDataFactory.getInstance().getOrderTreeByOutId(out_tid);;
		if(null==orderTree||StringUtils.isEmpty(orderTree.getOrder_id())){
			resp.setError_code("-1"); 
			resp.setError_msg("订单不存在");
			return resp; 
		}
		OrderExtBusiRequest orderExt = orderTree.getOrderExtBusiRequest();
		if(StringUtils.equals(orderExt.getFlow_trace_id(), EcsOrderConsts.DIC_ORDER_NODE_D)){
			RuleTreeExeReq reqNext = new RuleTreeExeReq();
			String rule_id="170141553590000472";//总商进入开户订单详情页面自动
			reqNext.setRule_id(rule_id);
			TacheFact factNext = new TacheFact();
			factNext.setOrder_id(orderTree.getOrder_id());
			reqNext.setFact(factNext);
			reqNext.setCheckAllRelyOnRule(true);
			reqNext.setCheckCurrRelyOnRule(true);
			ZteClient client = ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ESC);
			RuleTreeExeResp rsp = client.execute(reqNext, RuleTreeExeResp.class);
			BusiCompResponse busiResp = CommonDataFactory.getInstance().getRuleTreeresult(rsp);
		}else{
			resp.setError_code("-1");
			resp.setError_msg("订单不在开户环节");
			return resp;
		}
		resp.setError_code("0");
		resp.setError_msg("成功");
		return resp;
	}

}