package zte.net.iservice;

import java.util.List;

import params.RuleParams;
import params.ZteResponse;
import params.member.req.OrderListReq;
import params.member.resp.OrderListResp;
import params.order.req.MemberOrdReq;
import params.order.req.OrderIdReq;
import params.order.req.OrderOuterSyAttrReq;
import params.order.req.OrderTotalReq;
import params.order.req.OrderWorkItemQueryReq;
import params.order.resp.AcceptResp;
import params.order.resp.MemberOrdResp;
import params.order.resp.OrderIdResp;
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
import zte.net.ecsord.params.attr.req.AttrInstLoadReq;
import zte.net.ecsord.params.attr.resp.AttrInstLoadResp;
import zte.net.ecsord.params.base.req.BusiCompRequest;
import zte.net.ecsord.params.base.resp.BusiCompResponse;
import zte.net.ecsord.params.logs.req.MatchDictLogsReq;
import zte.net.ecsord.params.logs.req.MatchInfLogStateReq;
import zte.net.ecsord.params.logs.resp.MatchDictLogsResp;
import zte.net.ecsord.params.logs.resp.MatchInfLogStateResp;
import zte.params.ecsord.req.AttrTableCacheGetReq;
import zte.params.ecsord.req.GetOrderByLogiNoReq;
import zte.params.ecsord.req.ZBStartOpenAccountReq;
import zte.params.ecsord.resp.AttrTableCacheGetResp;
import zte.params.ecsord.resp.GetOrderByLogiNoResp;
import zte.params.ecsord.resp.ZBStartOpenAccountResp;
import zte.params.goodstype.req.GoodsTypeGetReq;
import zte.params.goodstype.resp.GoodsTypeGetResp;
import zte.params.order.req.AdjustListReq;
import zte.params.order.req.AdjustOrderPriceReq;
import zte.params.order.req.AttrDefGetReq;
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

import com.ztesoft.api.ApiBusiException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotation;
import com.ztesoft.net.app.base.core.model.RoleData;
import com.ztesoft.net.model.AttrDef;
import com.ztesoft.remote.params.activity.req.PromotionActEditReq;
import com.ztesoft.remote.params.activity.req.PromotionActReq;
import com.ztesoft.remote.params.activity.resp.PromotionActResp;

@ZteSoftCommentAnnotation(type="class",desc="订单管理API",summary="订单管理API[添加订单、分页查询订单、修改订单状态、修改订单金额、订单收货确认、订单发货]")
public interface IOrderServices {


/**
	 * 查询会员订单
	 * @作者 wui
	 * @创建日期 2014-1-8 
	 * @param orderAddReq
	 * @return
	 */
	@ZteSoftCommentAnnotation(type="method",desc="查询会员订单",summary="查询会员订单")
	public MemberOrdResp listOrderByMemberId(MemberOrdReq req);
	
	/**
	 * 添加订单
	 * @作者 MoChunrun
	 * @创建日期 2014-1-8 
	 * @param orderAddReq
	 * @return
	 */
	@ZteSoftCommentAnnotation(type="method",desc="添加订单",summary="添加订单")
	public OrderAddResp addOrder(OrderAddReq orderAddReq) throws Exception;
	
	/**
	 * 分页查询订单
	 * @作者 MoChunrun
	 * @创建日期 2014-1-8 
	 * @param pageReq
	 * @return
	 */
	@ZteSoftCommentAnnotation(type="method",desc="分页查询订单",summary="分页查询订单")
	public OrderPageListResp queryOrderForPage(OrderPageListReq pageReq);
	
	/**
	 * 修改订单状态
	 * @作者 MoChunrun
	 * @创建日期 2014-1-8 
	 * @param req
	 * @return
	 */
	@ZteSoftCommentAnnotation(type="method",desc="修改订单状态",summary="修改订单状态")
	public OrderStatusEditResp editOrderStatus(OrderStatusEditReq req);
	
	/**
	 * 修改订单金额
	 * @作者 MoChunrun
	 * @创建日期 2014-1-8 
	 * @param req
	 * @return
	 */
	@ZteSoftCommentAnnotation(type="method",desc="修改订单金额",summary="修改订单金额")
	public OrderPriceEditResp editOrderPrice(OrderPriceEditReq req);
	
	/**
	 * 订单收货确认
	 * @作者 MoChunrun
	 * @创建日期 2014-1-8 
	 * @param req
	 * @return
	 */
	@ZteSoftCommentAnnotation(type="method",desc="订单收货确认",summary="订单收货确认")
	public OrderConfirmResp confirmOrder(OrderConfirmReq req);
	
	/**
	 * 订单备货
	 * @作者 MoChunrun
	 * @创建日期 2014-1-8 
	 * @param req
	 * @return
	 */
	@ZteSoftCommentAnnotation(type="method",desc="订单备货",summary="订单备货")
	public OrderStockingResp stokingOrder(OrderStockingReq req);
	
	/**
	 * 订单发货
	 * @作者 MoChunrun
	 * @创建日期 2014-1-8 
	 * @param req
	 * @return
	 */
	@ZteSoftCommentAnnotation(type="method",desc="订单发货",summary="订单发货")
	public OrderShipResp shipOrder(OrderShipReq req);
	
	@ZteSoftCommentAnnotation(type="method",desc="模板属性",summary="模板属性")
	AcceptResp saveTplInst(AcceptRuleReq acceptRuleReq)  throws ApiBusiException;
	
	@ZteSoftCommentAnnotation(type="method",desc="订单异常状态增加",summary="订单异常状态增加")
	public OrderExceptionResp saveOrderFail(OrderExceptionReq orderExceptionReq);
	
/**
	 * 执行调用规则
	 * @作者 wu.i
	 * @创建日期 2014-3-4 
	 * @param RuleParams
	 * @return
	 */
	@ZteSoftCommentAnnotation(type="method",desc="执行应用平台规则",summary="执行应用平台规则")
	public ZteResponse processrule(RuleParams ruleParams) throws ApiBusiException;
	
	@ZteSoftCommentAnnotation(type="method",desc="当前购物车订单优惠",summary="当前购物车订单优惠")
	public OrderGiftListResp listOrderGift(OrderGiftListReq req);
	
	@ZteSoftCommentAnnotation(type="method",desc="查询购物车商品",summary="查询购物车商品")
	public GoodsCartItemListResp listGoodsCartItem(GoodsCartItemListReq req);
	
	@ZteSoftCommentAnnotation(type="method",desc="购物车商品总价",summary="购物车商品总价")
	public OrderTotalResp cartOrderTotalPrice(OrderTotalReq req);
	
	@ZteSoftCommentAnnotation(type="method",desc="订单附言",summary="订单附言")
	public OrderMetaListResp listOrderMeta(OrderMetaListReq req);
	
	@ZteSoftCommentAnnotation(type="method",desc="物流信息列表查询",summary="物流信息列表查询")
	public DeliveryFlowListResp listDeliveryList(DeliveryFlowListReq req);
	
	@ZteSoftCommentAnnotation(type="method",desc="订单物流信息列表查询",summary="订单物流信息列表查询")
	public OrderDeliveryListResp listOrderDelivery(OrderDeliveryListReq req);
	
	@ZteSoftCommentAnnotation(type="method",desc="退货订单列表",summary="退货订单列表")
	public MemberReturnedOrderListResp listMemberReturnedOrders(MemberReturnedOrderListReq req);
	
	@ZteSoftCommentAnnotation(type="method",desc="缺货登记列表",summary="缺货登记列表")
	public GnotifyPageListResp queryGnotifyForPage(GnotifyPageListReq req);
	
	@ZteSoftCommentAnnotation(type="method",desc="删除缺货登记",summary="删除缺货登记")
	public GnotifyDeleteResp deleteGontify(GnotifyDeleteReq req);
	
	@ZteSoftCommentAnnotation(type="method",desc="添加缺货登记",summary="添加缺货登记")
	public GnotifyAddResp addGnotify(GnotifyAddReq req);
	
	@ZteSoftCommentAnnotation(type="method",desc="获取子订单",summary="获取子订单")
	public OrderItemGetResp getOrderItem(OrderItemGetReq req);
	
	//@ZteSoftCommentAnnotation(type="method",desc="外系统订单同步",summary="外系统订单同步")
	public OrderOuterSyncResp syncOrderOuter(OrderOuterSyncReq req);
	
	@ZteSoftCommentAnnotation(type="method",desc="查询个人订单",summary="查询个人订单")
	public OrderListResp pageOrders(OrderListReq req);
	
	public HttpApiDataResp addHttpData(HttpApiDateReq acceptRuleReq)throws ApiBusiException;
	@ZteSoftCommentAnnotation(type="method",desc="淘宝订单同步",summary="淘宝订单同步")
	public OrderTaobaoSyncResp taobaoOrderSync(TaobaoOrderSyncReq req);
	
	@ZteSoftCommentAnnotation(type="method",desc="订单归集推送",summary="订单归集推送")
	public OrderStandardPushResp pushOrderStandard(OrderStandardPushReq req);
	
	@ZteSoftCommentAnnotation(type="method",desc="外系统订单同步",summary="外系统订单同步")
	public OrderAddResp syncAddOrder(OrderSyncReq req)throws ApiBusiException;
	@ZteSoftCommentAnnotation(type="method",desc="提供团购秒杀　添加记录到promotion_activity & promotion表",summary="提供团购秒杀　添加记录到promotion_activity & promotion表")
	public PromotionActResp addPromtionActivity(PromotionActReq req);
	@ZteSoftCommentAnnotation(type="method",desc="根据promotion的 pmt_solution 找到对应的活动id 团购秒杀同步更新promotion_activity enable 状态",summary="团购秒杀同步更新promotion_activity enable 状态")
	public PromotionActResp promotionActEdit(PromotionActEditReq req);
	
	@ZteSoftCommentAnnotation(type="method",desc="获取外系统订单属性",summary="获取外系统订单属性")
	public OrderOuterAttrQueryResp queryOuterAttrInsts(OrderOuterAttrQueryReq req);
	
	@ZteSoftCommentAnnotation(type = "method", desc = "获取支付日志", summary = "获取支付日志")
	public PaymentLogResp qryNotPayPaymentLog(PaymentLogReq req);
	
	@ZteSoftCommentAnnotation(type = "method", desc = "更新支付日志", summary = "更新支付日志")
	public PaySuccResp paySucc(PaySuccReq req);
	
	@ZteSoftCommentAnnotation(type = "method", desc = "支付方法", summary = "支付方法")
	public PayResp pay(PayReq req);
	
	@ZteSoftCommentAnnotation(type = "method", desc = "支付异常方法", summary = "支付异常方法")
	public PayExcetionResp payException(PayExcetionReq req);
	
	@ZteSoftCommentAnnotation(type = "method", desc = "批量查询订单信息", summary = "批量查询订单信息")
	public OrderQryBatchResp gerOrderByBatchId(OrderQryBatchReq req);
	
	@ZteSoftCommentAnnotation(type = "method", desc = "提交单个订单", summary = "提交单个订单")
	public OrderCommitResp commitOrder(OrderCommitReq req);
	
	@ZteSoftCommentAnnotation(type = "method", desc = "修改支付订单", summary = "修改支付订单")
	public PayUptResp updatePaymentMoney(PayUptReq req);
	
	@ZteSoftCommentAnnotation(type = "method", desc = "查询支付信息", summary = "查询支付信息")
	public PayQryResp getPayment(PayQryReq req);	
	
	@ZteSoftCommentAnnotation(type = "method", desc = "统计订单数量", summary = "统计订单数量")
	public OrderCountGetResp countOrders(OrderCountGetReq req);
	@ZteSoftCommentAnnotation(type = "method", desc = "按订单ID查询订单", summary = "按订单ID查询订单")
	public OrderGetResp getOrder(OrderGetReq req);
	@ZteSoftCommentAnnotation(type = "method", desc = "查询子订单列表", summary = "查询子订单列表")
	public OrderItemListResp listOrderItems(OrderItemListReq req);
	@ZteSoftCommentAnnotation(type = "method", desc = "查询子订单列表-历史表", summary = "查询子订单列表-历史表")
	public OrderItemListHisResp listOrderItemsHis(OrderItemListHisReq req);
	
	@ZteSoftCommentAnnotation(type = "method", desc = "查询外系统订单", summary = "查询外系统订单")
	public OrderOuterQryResp qryOrderOuter(OrderOuterQryReq req);
	@ZteSoftCommentAnnotation(type = "method", desc = "查询订单受理扩展信息", summary = "查询订单受理扩展信息")
	public OrderExtInfoGetResp getOrderExtInfo(OrderExtInfoGetReq req);
	
	@ZteSoftCommentAnnotation(type = "method", desc = "调整订单价格", summary = "调整订单价格")
	public AdjustOrderPriceResp adjustOrderPrice(AdjustOrderPriceReq req);
	@ZteSoftCommentAnnotation(type = "method", desc = "调整列表规格数据", summary = "调整列表规格数据")
	public AdjustListResp getAdjustList(AdjustListReq req);
	
	@ZteSoftCommentAnnotation(type = "method", desc = "获取订单属性定义信息", summary = "获取订单属性定义信息")
	public AttrDefGetResp getAttrDef(AttrDefGetReq req);
	@ZteSoftCommentAnnotation(type = "method", desc = "刷新订单树属性", summary = "刷新订单树属性")
	public List<AttrDef> refreshOrderTreeAttr(RefreshOrderTreeAttrReq req);
	
	/**
	 * 页面装载属性数据
	 * @param req
	 * @return
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "页面属性数据装载渲染", summary = "页面属性数据装载渲染")
	public AttrInstLoadResp pageLoadAttr(AttrInstLoadReq req);
	
	/**
	 * 获取调度流程
	 * @param order_id
	 * @return
	 */
	public BusiCompResponse execBusiComp(BusiCompRequest busiCompRequest)throws Exception;
	
	@ZteSoftCommentAnnotation(type = "method", desc = "工作流回调API", summary = "工作流回调API")
	public WorkFlowCallBackResp workFlowCallBack(WorkFlowCallBackReq req);
	
	@ZteSoftCommentAnnotation(type = "method", desc = "取属性值", summary = "取属性值",isOpen=false)
	public AttrValueGetResp getAttrValue(AttrValueGetReq req);
	
	@ZteSoftCommentAnnotation(type = "method", desc = "保存子订单项", summary = "保存子订单项",isOpen=false)
	public OrderItemsAddResp addOrderItems(OrderItemsAddReq req);
	@ZteSoftCommentAnnotation(type = "method", desc = "删除子订单项", summary = "删除子订单项",isOpen=false)
	public OrderItemsDelResp delOrderItems(OrderItemsDelReq req);
	@ZteSoftCommentAnnotation(type = "method", desc = "同步子订单到物流子单表", summary = "同步子订单到物流子单表",isOpen=false)
	public ShipItemSyncResp syncShipItems(ShipItemSyncReq req);
	@ZteSoftCommentAnnotation(type = "method", desc = "确认订单发货", summary = "确认订单发货",isOpen=false)
	public ShippingConfirmResp confirmShippingStatus(ShippingConfirmReq req);
	@ZteSoftCommentAnnotation(type = "method", desc = "添加子物流单", summary = "添加子物流单",isOpen=false)
	public DeliveryItemAddResp addDeliveryItem(DeliveryItemAddReq req);
	@ZteSoftCommentAnnotation(type = "method", desc = "查询子物流单", summary = "查询子物流单",isOpen=false)
	public DeliveryItemsQueryResp queryDeliveryItems(DeliveryItemsQueryReq req);
	@ZteSoftCommentAnnotation(type = "method", desc = "查询子物流单", summary = "查询子物流单",isOpen=false)
	public DeliveryItemsQueryByDeIdResp queryDeliveryItemsByDeId(DeliveryItemsQueryByDeIdReq req);
	
	@ZteSoftCommentAnnotation(type = "method", desc = "完成质检环节", summary = "完成质检环节[修改补发物流单ID]",isOpen=false)
	public OrderCheckTraceFlowFinishResp finishCheckTraceFlow(OrderCheckTraceFlowFinishReq req);
	
	//补发打印时调用
	@ZteSoftCommentAnnotation(type = "method", desc = "添加补发商品物流信息", summary = "添加补发商品物流信息",isOpen=false)
	public ReissueGoodsShippingAddResp addReissueGoodsShippingDelivery(ReissueGoodsShippingAddReq req);
	
	@ZteSoftCommentAnnotation(type = "method", desc = "异常订单流程环节跳转", summary = "异常订单流程环节跳转",isOpen=false)
	public OrderFlowExceptionBusiDoResp doOrderFlowExceptionBusi(OrderFlowExceptionBusiDoReq req);
	@ZteSoftCommentAnnotation(type = "method", desc = "删除物流子单", summary = "删除物流子单",isOpen=false)
	public DeliveryItemDelResp delDeliveryItem(DeliveryItemDelReq req);
	
	@ZteSoftCommentAnnotation(type = "method", desc = "刷新订单数属性", summary = "刷新订单数属性",isOpen=false)
	public RefreshAttrDefResp refreshAttrDef(RefreshAttrDefReq req);
	
	@ZteSoftCommentAnnotation(type = "method", desc = "查询外呼处理记录", summary = "查询外呼处理记录",isOpen=false)
	public OutcallLogsListResp listOutcallLogs(OutcallLogsListReq req);
	
	@ZteSoftCommentAnnotation(type = "method", desc = "查询订单处理日志", summary = "查询订单处理日志",isOpen=false)
	public OrderHandleLogsListResp listOrderHandlerLogs(OrderHandleLogsListReq req);
	
	@ZteSoftCommentAnnotation(type = "method", desc = "查询订单处理日志", summary = "查询订单处理日志",isOpen=false)
	public OrderExceptionLogsListResp listOrderExceptionLogs(OrderExceptionLogsListReq req);
	
	@ZteSoftCommentAnnotation(type = "method", desc = "查询实物类型名", summary = "查询实物类型名",isOpen=false)
	public GoodsTypeGetResp getGoodsTypeName(GoodsTypeGetReq req);
	
	@ZteSoftCommentAnnotation(type = "method", desc = "订单同步插入外系统属性数据", summary = "订单同步插入外系统属性数据", isOpen = false)
	public ZteResponse insertOuterInstAttr(OrderOuterSyAttrReq req) throws Exception;
	
	@ZteSoftCommentAnnotation(type = "method", desc = "订单处理权限处理", summary = "订单处理权限处理", isOpen = false)
	public HandlerOrderRoleResp handlerOrderRole(HandlerOrderRoleReq req);
	
	@ZteSoftCommentAnnotation(type = "method", desc = "刷新权限数据", summary = "刷新权限数据", isOpen = false)
	public List<RoleData> cacheOrderRoleData();

	@ZteSoftCommentAnnotation(type = "method", desc = "获取用户是否有权限处理当前状态的订单", summary = "获取用户是否有权限处理当前状态的订单", isOpen = false)
	public OrderAuthorityByUserResp getOrderAuthorityByUser(OrderAuthorityByUserReq req);
	
	@ZteSoftCommentAnnotation(type = "method", desc = "订单手动退单申请成功，总部回单状态修改", summary = "订单手动退单申请成功，总部回单状态修改", isOpen = false)
	public ChargebackStatuChgResp chargebackStackChg(ChargebackStatuChgReq req);
	
	@ZteSoftCommentAnnotation(type = "method", desc = "获取缓存的订单属性", summary = "获取缓存的订单属性", isOpen = false)
	public AttrTableCacheGetResp getCacheAttrTable(AttrTableCacheGetReq req);

	@ZteSoftCommentAnnotation(type = "method", desc = "添加打折", summary = "添加打折", isOpen = false)
	public PromotionAddResp addPromotion(PromotionAddReq req);

	@ZteSoftCommentAnnotation(type = "method", desc = "删除打折", summary = "删除打折", isOpen = false)
	public PromotionDeleteResp deletePromotion(PromotionDeleteReq req);

	@ZteSoftCommentAnnotation(type = "method", desc = "打折列表", summary = "打折列表", isOpen = false)
	public PromotionResp listPromotion(PromotionReq req);

	
	
	
	@ZteSoftCommentAnnotation(type="method",desc="订单归集",summary="订单归集")
	public OrderCollectionResp orderCollection(OrderCollectionReq req);
	
	
	
	@ZteSoftCommentAnnotation(type="method",desc="检查webService服务是否配置有",summary="检查webService服务是否配置有")
	public CheckOpenServiceResp checkOpenService(CheckOpenServiceReq req);
	
	@ZteSoftCommentAnnotation(type="method",desc="订单标准化",summary="订单标准化")
	public OrderCreateResp OrderCreate(OrderCreateReq req);
	
	@ZteSoftCommentAnnotation(type="method",desc="订单操作日志保存",summary="订单操作日志保存")
	public OrderHandleLogsAddResp OrderHandleLogsAdd(OrderHandleLogsAddReq req);
	
	@ZteSoftCommentAnnotation(type="method",desc="消息队列数据转移到失败表",summary="消息队列数据转移到失败表")
	public OrderQueueFailSaveResp OrderQueueFailSave(OrderQueueFailSaveReq req);
	
	@ZteSoftCommentAnnotation(type="method",desc="消息队列数据归档",summary="消息队列数据归档")
	public OrderQueueHisSaveResp OrderQueueHisSave(OrderQueueHisSaveReq req);
	
	@ZteSoftCommentAnnotation(type="method",desc="订单修改",summary="订单修改")
	public OrderUpdateResp OrderUpdate(OrderUpdateReq req);
	@ZteSoftCommentAnnotation(type="method",desc="查询没处理的订单工作项",summary="查询没处理的订单工作项")
	public OrderWorkItemQueryResp orderWorkItemQuery(OrderWorkItemQueryReq req);
	
	@ZteSoftCommentAnnotation(type="method",desc="根据外部订单号获取内部订单号",summary="根据外部订单号获取内部订单号")
	public OrderIdResp orderId(OrderIdReq req);
	/**
	 * 匹配错误字典
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "匹配错误字典", summary = "匹配字典")
	public MatchDictLogsResp matchDictLogs(MatchDictLogsReq req) throws ApiBusiException;

	/**
	 * 修改日志状态
	 * @param req
	 * @return
	 */
	public MatchInfLogStateResp matchInfLogState(MatchInfLogStateReq req);
	
	@ZteSoftCommentAnnotation(type = "method", desc = "根据物流单号获取order_id", summary = "根据物流单号获取order_id")
	public GetOrderByLogiNoResp getOrdByLogiNo(GetOrderByLogiNoReq req) throws ApiBusiException;
	
	@ZteSoftCommentAnnotation(type = "method", desc = "总商触发自动开户环节调用", summary = "总商触发自动开户环节调用")
	public ZBStartOpenAccountResp zbStartOpenAccount(ZBStartOpenAccountReq req) throws ApiBusiException;
	
	@ZteSoftCommentAnnotation(type = "method", desc = "执行标准化方案组件入口", summary = "执行标准化方案组件入口")
	public BusiCompResponse execOrderStandingPlanBusiComp(BusiCompRequest busiCompRequest);
}
