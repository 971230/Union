package zte.net.iservice.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import params.RuleParams;
import params.ZteResponse;
import params.adminuser.req.UserWdLoginReq;
import params.adminuser.resp.UserWdLoginResp;
import params.cart.req.CartAddReq;
import params.cart.req.CartListReq;
import params.cart.req.CartTotalReq;
import params.cart.resp.CartAddResp;
import params.cart.resp.CartListResp;
import params.cart.resp.CartTotalResp;
import params.coqueue.req.CoQueueAddReq;
import params.coqueue.req.CoQueueModifyReq;
import params.coqueue.resp.CoQueueAddResp;
import params.coqueue.resp.CoQueueModifyResp;
import params.member.req.OrderListReq;
import params.member.resp.OrderListResp;
import params.onlinepay.req.OnlinePayReq;
import params.onlinepay.resp.OnlinePayResp;
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
import params.order.resp.PaymentListResp;
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
import params.paycfg.req.PaymentCfgBankReq;
import params.paycfg.resp.PaymentCfgBankResp;
import rule.params.accept.req.AcceptRuleReq;
import utils.CoreThreadLocalHolder;
import utils.GlobalThreadLocalHolder;
import zte.net.ecsord.params.attr.req.AttrInstLoadReq;
import zte.net.ecsord.params.attr.resp.AttrInstLoadResp;
import zte.net.ecsord.params.base.req.BusiCompRequest;
import zte.net.ecsord.params.base.resp.BusiCompResponse;
import zte.net.ecsord.params.logs.req.MatchDictLogsReq;
import zte.net.ecsord.params.logs.req.MatchInfLogStateReq;
import zte.net.ecsord.params.logs.resp.MatchDictLogsResp;
import zte.net.ecsord.params.logs.resp.MatchInfLogStateResp;
import zte.net.iservice.ICartServices;
import zte.net.iservice.ICoQueueService;
import zte.net.iservice.IDlyTypeAddressService;
import zte.net.iservice.IOrderApplyService;
import zte.net.iservice.IOrderServices;
import zte.net.iservice.IPaymentServices;
import zte.params.cart.req.CartDeleteReq;
import zte.params.cart.resp.CartDeleteResp;
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
import zte.params.order.req.CartBarPriceTotalReq;
import zte.params.order.req.CartUpdateReq;
import zte.params.order.req.ChargebackStatuChgReq;
import zte.params.order.req.DeliveryFlowListReq;
import zte.params.order.req.DeliveryItemAddReq;
import zte.params.order.req.DeliveryItemDelReq;
import zte.params.order.req.DeliveryItemsQueryByDeIdReq;
import zte.params.order.req.DeliveryItemsQueryReq;
import zte.params.order.req.DlyTypeAddressListReq;
import zte.params.order.req.DlyTypeListReq;
import zte.params.order.req.DlyTypePriceListReq;
import zte.params.order.req.GnotifyAddReq;
import zte.params.order.req.GnotifyDeleteReq;
import zte.params.order.req.GnotifyPageListReq;
import zte.params.order.req.GoodsCartItemListReq;
import zte.params.order.req.HandlerOrderRoleReq;
import zte.params.order.req.HttpApiDateReq;
import zte.params.order.req.MemberReturnedOrderListReq;
import zte.params.order.req.OrderAddReq;
import zte.params.order.req.OrderApplyAddReq;
import zte.params.order.req.OrderApplyPageListReq;
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
import zte.params.order.req.PaymentListReq;
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
import zte.params.order.resp.CartBarPriceTotalResp;
import zte.params.order.resp.CartUpdateResp;
import zte.params.order.resp.ChargebackStatuChgResp;
import zte.params.order.resp.DeliveryFlowListResp;
import zte.params.order.resp.DeliveryItemAddResp;
import zte.params.order.resp.DeliveryItemDelResp;
import zte.params.order.resp.DeliveryItemsQueryByDeIdResp;
import zte.params.order.resp.DeliveryItemsQueryResp;
import zte.params.order.resp.DlyTypeAddressListResp;
import zte.params.order.resp.DlyTypeListResp;
import zte.params.order.resp.DlyTypePriceListResp;
import zte.params.order.resp.GnotifyAddResp;
import zte.params.order.resp.GnotifyDeleteResp;
import zte.params.order.resp.GnotifyPageListResp;
import zte.params.order.resp.GoodsCartItemListResp;
import zte.params.order.resp.HandlerOrderRoleResp;
import zte.params.order.resp.HttpApiDataResp;
import zte.params.order.resp.MemberReturnedOrderListResp;
import zte.params.order.resp.OrderAddResp;
import zte.params.order.resp.OrderApplyAddResp;
import zte.params.order.resp.OrderApplyPageListResp;
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
import com.ztesoft.api.AppKeyEnum;
import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotation;
import com.ztesoft.net.app.base.core.model.RoleData;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.model.AttrDef;
import com.ztesoft.remote.params.activity.req.PromotionActEditReq;
import com.ztesoft.remote.params.activity.req.PromotionActReq;
import com.ztesoft.remote.params.activity.resp.PromotionActResp;
import com.ztesoft.rop.annotation.NeedInSessionType;
import com.ztesoft.rop.annotation.ServiceMethod;
import com.ztesoft.rop.annotation.ServiceMethodBean;

@ServiceMethodBean(version="1.0")
public class ZteOrderOpenService implements IOrderServices,ICartServices,IDlyTypeAddressService,IOrderApplyService,IPaymentServices {
	
    private IOrderServices orderServices;
    private ICartServices cartServices;
    private IDlyTypeAddressService dlyTypeAddressService;
    private IOrderApplyService orderApplyService;
    private IPaymentServices paymentServices;
   
    
//    @Resource
    private ICoQueueService coQueueService;
    
    private void init(){
        cartServices= SpringContextHolder.getBean("cartServices");
        orderServices = SpringContextHolder.getBean("orderServices");
        dlyTypeAddressService = SpringContextHolder.getBean("dlyTypeAddressService");
        orderApplyService = SpringContextHolder.getBean("orderApplyService");
        paymentServices = SpringContextHolder.getBean("paymentServices");
        coQueueService = SpringContextHolder.getBean("coQueueService");
        coQueueService = SpringContextHolder.getBean("coQueueService");
    }
    

	@Override
	@ServiceMethod(method="zte.orderService.order.add",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public OrderAddResp addOrder(OrderAddReq orderAddReq) throws Exception {
		
		 //拦截器中设置到线程变量中去
		//orderAddReq.setSourceFrom("LLKJ_AGENT");
        CoreThreadLocalHolder.getInstance().getZteCommonData().setZteRequest(orderAddReq);
        
		this.init();
		orderAddReq.setRopRequestContext(null);
		return orderServices.addOrder(orderAddReq);
	}

	@Override
	@ServiceMethod(method="zte.orderService.order.confirm",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public OrderConfirmResp confirmOrder(OrderConfirmReq req) {
		this.init();
		req.setRopRequestContext(null);
		return orderServices.confirmOrder(req);
	}

	@Override
	@ServiceMethod(method="zte.orderService.orderPrice.edit",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public OrderPriceEditResp editOrderPrice(OrderPriceEditReq req) {
		this.init();
		req.setRopRequestContext(null);
		return orderServices.editOrderPrice(req);
	}

	@Override
	@ServiceMethod(method="zte.orderService.orderStatus.edit",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public OrderStatusEditResp editOrderStatus(OrderStatusEditReq req) {
		this.init();
		req.setRopRequestContext(null);
		return orderServices.editOrderStatus(req);
	}

	@Override
	@ServiceMethod(method="zte.orderService.order.page",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public OrderPageListResp queryOrderForPage(OrderPageListReq pageReq) {
		this.init();
		pageReq.setRopRequestContext(null);
		return orderServices.queryOrderForPage(pageReq);
	}

	@Override
	@ServiceMethod(method="zte.orderService.order.ship",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public OrderShipResp shipOrder(OrderShipReq req) {
		this.init();
		req.setRopRequestContext(null);
		return orderServices.shipOrder(req);
	}

	@Override
	@ServiceMethod(method="zte.orderService.order.stocking",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public OrderStockingResp stokingOrder(OrderStockingReq req) {
		this.init();
		req.setRopRequestContext(null);
		return orderServices.stokingOrder(req);
	}

	@Override
    @ServiceMethod(method="zte.cartService.cart.add",version="1.0",needInSession= NeedInSessionType.NO,timeout = 600000)
    public CartAddResp addCart(CartAddReq req) {
        this.init();
        req.setRopRequestContext(null);
    	CartAddResp resp = cartServices.addCart(req);
        return resp;
    }

    @Override
    @ServiceMethod(method="zte.cartService.cart.list",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
    public CartListResp listCart(CartListReq req) {
    	
        this.init();
        req.setRopRequestContext(null);
        CartListResp resp = cartServices.listCart(req);
        return resp;
    }

    @Override
    @ServiceMethod(method="zte.cartService.cart.delete",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
    public CartDeleteResp deleteCart(CartDeleteReq req) {
        this.init();
        req.setRopRequestContext(null);
        return cartServices.deleteCart(req);
    }


	@Override
	@ServiceMethod(method="zte.orderservice.tplinst.save",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public AcceptResp saveTplInst(AcceptRuleReq acceptRuleReq) throws ApiBusiException {
		this.init();
		acceptRuleReq.setRopRequestContext(null);
		return orderServices.saveTplInst(acceptRuleReq);
	}


	@Override
	@ServiceMethod(method="zte.orderService.order.listorder.member",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public MemberOrdResp listOrderByMemberId(MemberOrdReq req) {
		this.init();
		req.setRopRequestContext(null);
		return orderServices.listOrderByMemberId(req);
	}

	
	@Override
	@ServiceMethod(method = "zte.orderService.orderException.add", version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public OrderExceptionResp saveOrderFail(OrderExceptionReq orderExceptionReq) {
		this.init();
		orderExceptionReq.setRopRequestContext(null);
		return orderServices.saveOrderFail(orderExceptionReq);
	}

	@Override
	@ServiceMethod(method = "zte.service.core.process.ruleParams", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public ZteResponse processrule(RuleParams ruleParams) throws ApiBusiException {
		this.init();
		ruleParams.setRopRequestContext(null);
		return orderServices.processrule(ruleParams);
	}


	@Override
	@ServiceMethod(method="zte.dlyTypeAddressService.list",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public DlyTypeAddressListResp listTypeAddress(DlyTypeAddressListReq req) {
		this.init();
		req.setRopRequestContext(null);
		return dlyTypeAddressService.listTypeAddress(req);
	}


	@Override
	@ServiceMethod(method="zte.dlyTypeAddressService.dlyType.list",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public DlyTypeListResp listDlyType(DlyTypeListReq req) {
		this.init();
		req.setRopRequestContext(null);
		return dlyTypeAddressService.listDlyType(req);
	}


	@Override
	@ServiceMethod(method="com.ztesoft.remote.activity.addPromtionActivity",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public PromotionActResp addPromtionActivity(PromotionActReq req) {
		this.init();
		req.setRopRequestContext(null);
		return orderServices.addPromtionActivity(req);
	}
	
	@Override
	@ServiceMethod(method="com.ztesoft.remote.activity.promotionActEdit",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public PromotionActResp promotionActEdit(PromotionActEditReq req) {
		this.init();
		req.setRopRequestContext(null);
		return orderServices.promotionActEdit(req);
	}
	@Override
	@ServiceMethod(method="zte.orderService.order.gift.list",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public OrderGiftListResp listOrderGift(OrderGiftListReq req) {
		this.init();
		req.setRopRequestContext(null);
		return orderServices.listOrderGift(req);
	}

	@Override
	@ServiceMethod(method="zte.orderService.order.goodsitem.list",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public GoodsCartItemListResp listGoodsCartItem(GoodsCartItemListReq req) {
		this.init();
		req.setRopRequestContext(null);
		return orderServices.listGoodsCartItem(req);
	}


	@Override
	@ServiceMethod(method="zte.orderService.order.goodsitem.totalprice",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public OrderTotalResp cartOrderTotalPrice(OrderTotalReq req) {
		this.init();
		req.setRopRequestContext(null);
		return orderServices.cartOrderTotalPrice(req);
	}


	@Override
	@ServiceMethod(method="zte.cartService.cart.updatenum",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public CartUpdateResp updateCartNum(CartUpdateReq req) {
		this.init();
		req.setRopRequestContext(null);
		return cartServices.updateCartNum(req);
	}


	@Override
	@ServiceMethod(method="zte.cartService.cart.gettotalprice",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public CartTotalResp getCartTotalPrice(CartTotalReq req) {
		this.init();
		req.setRopRequestContext(null);
		return cartServices.getCartTotalPrice(req);
	}


	@Override
	@ServiceMethod(method="zte.cartService.cart.cartBarPrice",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public CartBarPriceTotalResp getCartBarTotalPrice(CartBarPriceTotalReq req) {
		this.init();
		req.setRopRequestContext(null);
		return cartServices.getCartBarTotalPrice(req);
	}


	@Override
	@ServiceMethod(method="zte.orderService.order.metalist",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public OrderMetaListResp listOrderMeta(OrderMetaListReq req) {
		this.init();
		req.setRopRequestContext(null);
		return orderServices.listOrderMeta(req);
	}


	@Override
	@ServiceMethod(method="zte.dlyTypeAddressService.dlytype.pricelist",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public DlyTypePriceListResp listDlyTypePrice(DlyTypePriceListReq req) {
		this.init();
		req.setRopRequestContext(null);
		return dlyTypeAddressService.listDlyTypePrice(req);
	}


	@Override
	@ServiceMethod(method="zte.orderService.delivery.flow.list",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public DeliveryFlowListResp listDeliveryList(DeliveryFlowListReq req) {
		this.init();
		req.setRopRequestContext(null);
		return orderServices.listDeliveryList(req);
	}


	@Override
	@ServiceMethod(method="zte.orderService.order.deliverylist",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public OrderDeliveryListResp listOrderDelivery(OrderDeliveryListReq req) {
		this.init();
		req.setRopRequestContext(null);
		return orderServices.listOrderDelivery(req);
	}


	@Override
	@ServiceMethod(method="zte.orderService.order.returneds",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public MemberReturnedOrderListResp listMemberReturnedOrders(
			MemberReturnedOrderListReq req) {
		this.init();
		req.setRopRequestContext(null);
		return orderServices.listMemberReturnedOrders(req);
	}


	@Override
	@ServiceMethod(method="zte.orderService.order.gnoift.page",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public GnotifyPageListResp queryGnotifyForPage(GnotifyPageListReq req) {
		this.init();
		req.setRopRequestContext(null);
		return orderServices.queryGnotifyForPage(req);
	}


	@Override
	@ServiceMethod(method="zte.orderService.order.gnoift.delete",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public GnotifyDeleteResp deleteGontify(GnotifyDeleteReq req) {
		this.init();
		req.setRopRequestContext(null);
		return orderServices.deleteGontify(req);
	}


	@Override
	@ServiceMethod(method="zte.orderService.order.gnoift.add",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public GnotifyAddResp addGnotify(GnotifyAddReq req) {
		this.init();
		req.setRopRequestContext(null);
		return orderServices.addGnotify(req);
	}


	@Override
	@ServiceMethod(method="zte.orderService.apply.page",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public OrderApplyPageListResp queryOrderApplyForPage(
			OrderApplyPageListReq req) {
		this.init();
		req.setRopRequestContext(null);
		return orderApplyService.queryOrderApplyForPage(req);
	}


	@Override
	@ServiceMethod(method="zte.orderService.apply.add",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public OrderApplyAddResp addOrderApply(OrderApplyAddReq req) {
		this.init();
		req.setRopRequestContext(null);
		return orderApplyService.addOrderApply(req);
	}


	@Override
	@ServiceMethod(method="zte.orderService.order.item.get",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public OrderItemGetResp getOrderItem(OrderItemGetReq req) {
		this.init();
		req.setRopRequestContext(null);
		return orderServices.getOrderItem(req);
	}
	
	@ServiceMethod(method="zte.service.coqueue.add",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public CoQueueAddResp add(CoQueueAddReq coQueueAddReq) {
		this.init();
		return coQueueService.add(coQueueAddReq);
	}

	@ServiceMethod(method="zte.service.coqueue.modify",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public CoQueueModifyResp modify(CoQueueModifyReq req){
		this.init();
		req.setRopRequestContext(null);
		return coQueueService.modifyStatusToWFS(req);
	}

	@Override
	@ServiceMethod(method="zte.orderService.outer.ordersync",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public OrderOuterSyncResp syncOrderOuter(OrderOuterSyncReq req) {
		this.init();
		req.setRopRequestContext(null);
		return this.orderServices.syncOrderOuter(req);
	}

	@Override
	@ServiceMethod(method="zte.orderService.outer.orderoutersyattrreq",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public ZteResponse insertOuterInstAttr(OrderOuterSyAttrReq req) throws Exception {
		this.init();
		req.setRopRequestContext(null);
		return this.orderServices.insertOuterInstAttr(req);
	}
	
	@Override
	@ServiceMethod(method="zte.orderService.httpdata.add",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public HttpApiDataResp addHttpData(HttpApiDateReq acceptRuleReq)throws ApiBusiException {
		return orderServices.addHttpData(acceptRuleReq);	
	}

	@Override
	@ServiceMethod(method="zte.orderService.order.member.pageOrder",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public OrderListResp pageOrders(OrderListReq req) {
		this.init();	
		req.setRopRequestContext(null);
		return orderServices.pageOrders(req);
	}

	

	@Override
	@ServiceMethod(method="zte.orderService.taobao.ordersync",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public OrderTaobaoSyncResp taobaoOrderSync(TaobaoOrderSyncReq req) {
		this.init();
		req.setRopRequestContext(null);
		return orderServices.taobaoOrderSync(req);
	}

	
	@Override
	@ServiceMethod(method="zte.orderService.orderStandard.push",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public OrderStandardPushResp pushOrderStandard(OrderStandardPushReq req) {
		this.init();
		req.setRopRequestContext(null);
		
		CoreThreadLocalHolder.getInstance().getZteCommonData().setZteRequest(req);
		if (StringUtils.isEmpty(req.userSessionId)) {
			GlobalThreadLocalHolder.getInstance().clear();
			req.setUserSessionId(req.getUserSessionId());
		}
		return orderServices.pushOrderStandard(req);
	}


	@Override
	@ServiceMethod(method="zte.orderService.order.syncadd",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public OrderAddResp syncAddOrder(OrderSyncReq req) throws ApiBusiException {
		this.init();
		req.setRopRequestContext(null);
		return orderServices.syncAddOrder(req);
	}
	
	@Override
	@ServiceMethod(method="zte.paymentService.payment.qryNotPayPaymentLog",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public PaymentLogResp qryNotPayPaymentLog(PaymentLogReq req){
		this.init();
		req.setRopRequestContext(null);
		return orderServices.qryNotPayPaymentLog(req);
	}
	
	@Override
	@ServiceMethod(method="zte.paymentService.payment.paysucc",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public PaymentListResp addPaymentList(PaymentListReq req) {
		this.init();
		req.setRopRequestContext(null);
		return paymentServices.addPaymentList(req);
	}


	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "获取外系统订单属性", summary = "获取外系统订单属性")
	public OrderOuterAttrQueryResp queryOuterAttrInsts(OrderOuterAttrQueryReq req) {
		this.init();
		req.setRopRequestContext(null);
		return null;
	}


	@Override
	@ServiceMethod(method="zte.paymentService.payment.paySucc",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public PaySuccResp paySucc(PaySuccReq req) {
		this.init();
		return orderServices.paySucc(req);
	}


	@Override
	@ServiceMethod(method="zte.paymentService.payment.pay",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public PayResp pay(PayReq req) {
		this.init();
		return orderServices.pay(req);
	}


	@Override
	@ServiceMethod(method="zte.paymentService.payment.payExcetion",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public PayExcetionResp payException(PayExcetionReq req) {
		this.init();
		return orderServices.payException(req);
	}


	@Override
	@ServiceMethod(method="zte.orderService.order.getOrderByBatchId",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public OrderQryBatchResp gerOrderByBatchId(OrderQryBatchReq req) {
		this.init();
		return orderServices.gerOrderByBatchId(req);
	}


	@Override
	@ServiceMethod(method="zte.orderService.order.commitOrder",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public OrderCommitResp commitOrder(OrderCommitReq req) {
		this.init();
		return orderServices.commitOrder(req);
	}


	@Override
	@ServiceMethod(method="zte.paymentService.payment.updatePaymentMoney",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public PayUptResp updatePaymentMoney(PayUptReq req) {
		this.init();
		return orderServices.updatePaymentMoney(req);
	}


	@Override
	@ServiceMethod(method="zte.paymentService.payment.getPayment",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public PayQryResp getPayment(PayQryReq req) {
		this.init();
		return orderServices.getPayment(req);
	}
	
	@Override
	@ServiceMethod(method="zte.paymentService.payment.goToPay",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public OnlinePayResp goToPay(OnlinePayReq req){
		this.init();
		return paymentServices.goToPay(req);
	}


	@Override
	@ServiceMethod(method="zte.orderService.order.count",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public OrderCountGetResp countOrders(OrderCountGetReq req) {
		this.init();
		return orderServices.countOrders(req);
	}
	
	@Override
	@ServiceMethod(method="zte.orderService.order.get.id",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public OrderGetResp getOrder(OrderGetReq req){
		this.init();
		return orderServices.getOrder(req);
	}
	
	@Override
	@ServiceMethod(method="zte.orderService.order.itemlist.get",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public OrderItemListResp listOrderItems(OrderItemListReq req){
		this.init();
		return orderServices.listOrderItems(req);
	}
	@Override
	@ServiceMethod(method="zte.orderService.order.itemlistHis.get",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public OrderItemListHisResp listOrderItemsHis(OrderItemListHisReq req){
		this.init();
		return orderServices.listOrderItemsHis(req);
	}
	
	@Override
	@ServiceMethod(method="zte.orderService.order.orderOuter.qry",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public OrderOuterQryResp qryOrderOuter(OrderOuterQryReq req){
		this.init();
		return orderServices.qryOrderOuter(req);
	}
	
	@Override
	@ServiceMethod(method="zte.orderService.order.orderOuter.getext.info",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public OrderExtInfoGetResp getOrderExtInfo(OrderExtInfoGetReq req){
		return orderServices.getOrderExtInfo(req);
	}
	
	@Override
	@ServiceMethod(method="zte.orderService.order.adjustOrderPrice",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public AdjustOrderPriceResp adjustOrderPrice(AdjustOrderPriceReq req) {
		this.init();
		req.setRopRequestContext(null);
		return orderServices.adjustOrderPrice(req);
	}
	
	@Override
	@ServiceMethod(method="zte.orderService.order.getAdjustList",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public AdjustListResp getAdjustList(AdjustListReq req) {
		this.init();
		req.setRopRequestContext(null);
		return orderServices.getAdjustList(req);
	}
	
	
	/**
	 * 调度业务组建
	 */
	@Override
	@ServiceMethod(method="zte.net.ecsord.get.busicomp",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public BusiCompResponse execBusiComp(BusiCompRequest busiCompRequest) throws Exception{
		this.init();
		return orderServices.execBusiComp(busiCompRequest);
	}

	@Override
	@ServiceMethod(method="zte.net.ord.params.attr.req.AttrInstLoadReq",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public AttrInstLoadResp pageLoadAttr(AttrInstLoadReq req) {
		this.init();
		return orderServices.pageLoadAttr(req);
	}

	@Override
	@ServiceMethod(method="zte.orderService.attr.def.get",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public AttrDefGetResp getAttrDef(AttrDefGetReq req) {
		this.init();
		return orderServices.getAttrDef(req);
	}


	@Override
	@ServiceMethod(method="zte.orderService.workflow.callback",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public WorkFlowCallBackResp workFlowCallBack(WorkFlowCallBackReq req) {
		this.init();
		return orderServices.workFlowCallBack(req);
	}
	
	@Override
	@ServiceMethod(method="zte.orderService.attr.value.get",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public AttrValueGetResp getAttrValue(AttrValueGetReq req){
		this.init();
		if(orderServices==null)
			orderServices = SpringContextHolder.getBean("orderServices");
		return orderServices.getAttrValue(req);
	}


	@Override
	@ServiceMethod(method="zte.orderService.order.items.add",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public OrderItemsAddResp addOrderItems(OrderItemsAddReq req) {
		this.init();
		return orderServices.addOrderItems(req);
	}


	@Override
	@ServiceMethod(method="zte.orderService.order.items.del.id",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public OrderItemsDelResp delOrderItems(OrderItemsDelReq req) {
		this.init();
		return orderServices.delOrderItems(req);
	}
	
	@Override
	@ServiceMethod(method="zte.orderService.delivery.shipping.order.items.sync",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public ShipItemSyncResp syncShipItems(ShipItemSyncReq req){
		this.init();
		return orderServices.syncShipItems(req);
	}
	
	@Override
	@ServiceMethod(method="zte.orderService.order.shipping.status.confirm",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public ShippingConfirmResp confirmShippingStatus(ShippingConfirmReq req){
		this.init();
		return orderServices.confirmShippingStatus(req);
	}
	
	@Override
	@ServiceMethod(method="zte.orderService.delivery.item.add",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public DeliveryItemAddResp addDeliveryItem(DeliveryItemAddReq req){
		this.init();
		return orderServices.addDeliveryItem(req);
	}
	
	@Override
	@ServiceMethod(method="zte.ordrService.order.delivery.items.type.query",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public DeliveryItemsQueryResp queryDeliveryItems(DeliveryItemsQueryReq req){
		this.init();
		return orderServices.queryDeliveryItems(req);
	}
	@Override
	@ServiceMethod(method="zte.ordrService.order.delivery.items.type.queryByDeId",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public DeliveryItemsQueryByDeIdResp queryDeliveryItemsByDeId(DeliveryItemsQueryByDeIdReq req){
		this.init();
		return orderServices.queryDeliveryItemsByDeId(req);
	}


	
	@Override
	@ServiceMethod(method="zte.orderService.orderFlow.check.finish",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public OrderCheckTraceFlowFinishResp finishCheckTraceFlow(OrderCheckTraceFlowFinishReq req){
		this.init();
		return orderServices.finishCheckTraceFlow(req);
	}
	
	@Override
	@ServiceMethod(method="zte.orderService.delivery.reissue.goods.shipping.add",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public ReissueGoodsShippingAddResp addReissueGoodsShippingDelivery(ReissueGoodsShippingAddReq req){
		this.init();
		return orderServices.addReissueGoodsShippingDelivery(req);
	}
	
	@Override
	@ServiceMethod(method="zte.orderService.workflow.exception.next.do",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public OrderFlowExceptionBusiDoResp doOrderFlowExceptionBusi(OrderFlowExceptionBusiDoReq req){
		this.init();
		return orderServices.doOrderFlowExceptionBusi(req);
	}
	
	@Override
	@ServiceMethod(method="zte.orderService.order.delivery.item.delete",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public DeliveryItemDelResp delDeliveryItem(DeliveryItemDelReq req){
		this.init();
		return orderServices.delDeliveryItem(req);
	}

	

	@Override
	@ZteSoftCommentAnnotation(type = "zte.orderService.refresh.order.tree.attr.req", desc = "刷新订单树属性", summary = "刷新订单树属性")
	public List<AttrDef> refreshOrderTreeAttr(RefreshOrderTreeAttrReq req) {
		this.init();
		return orderServices.refreshOrderTreeAttr(req);
	}


	@Override
	@ZteSoftCommentAnnotation(type = "zte.orderService.refresh.attr.def.req", desc = "刷新订单数属性", summary = "刷新订单数属性", isOpen = false)
	public RefreshAttrDefResp refreshAttrDef(RefreshAttrDefReq req) {
		this.init();
		return orderServices.refreshAttrDef(req);
	}
	
	@Override
	@ZteSoftCommentAnnotation(type = "zte.orderService.outcall.log.list", desc = "查询外呼处理记录", summary = "查询外呼处理记录", isOpen = false)
	public OutcallLogsListResp listOutcallLogs(OutcallLogsListReq req){
		this.init();
		return orderServices.listOutcallLogs(req);
	}
	
	@Override
	@ZteSoftCommentAnnotation(type = "zte.orderService.order.handler.log.list", desc = "刷新订单数属性", summary = "刷新订单数属性", isOpen = false)
	public OrderHandleLogsListResp listOrderHandlerLogs(OrderHandleLogsListReq req){
		this.init();
		return orderServices.listOrderHandlerLogs(req);
	}
	
	@Override
	@ZteSoftCommentAnnotation(type = "zte.orderService.order.exception.log.list", desc = "异常跟踪", summary = "异常跟踪", isOpen = false)
	public OrderExceptionLogsListResp listOrderExceptionLogs(OrderExceptionLogsListReq req){
		this.init();
		return orderServices.listOrderExceptionLogs(req);
	}
	
	@Override
	@ZteSoftCommentAnnotation(type = "zte.goodsService.goodsType.get", desc = "查询实物类型名", summary = "查询实物类型名", isOpen = false)
	public GoodsTypeGetResp getGoodsTypeName(GoodsTypeGetReq req) {
		this.init();
		return orderServices.getGoodsTypeName(req);
	}
	@Override
	@ServiceMethod(method="zte.orderService.order.data.role.handler",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public HandlerOrderRoleResp handlerOrderRole(HandlerOrderRoleReq req){
		this.init();
		return orderServices.handlerOrderRole(req);
	}

	@Override
	@ZteSoftCommentAnnotation(type = "zte.orderService.refresh.clear.limit.req", desc = "刷新权限数据", summary = "刷新权限数据", isOpen = false)
	public List<RoleData> cacheOrderRoleData() {
		this.init();
		return orderServices.cacheOrderRoleData();
	}

	@Override
	@ServiceMethod(method="zte.orderService.order.authority.by.user",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public OrderAuthorityByUserResp getOrderAuthorityByUser(OrderAuthorityByUserReq req){
		this.init();
		return orderServices.getOrderAuthorityByUser(req);
	}
	
	@Override
	@ServiceMethod(method="zte.orderService.chargebackStatu.chg",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public ChargebackStatuChgResp chargebackStackChg(ChargebackStatuChgReq req) {
		this.init();
		return orderServices.chargebackStackChg(req);
	}
	
	@Override
	@ZteSoftCommentAnnotation(type = "zte.orderService.attrTable.cache.get", desc = "获取缓存的订单属性", summary = "获取缓存的订单属性", isOpen = false)
	public AttrTableCacheGetResp getCacheAttrTable(AttrTableCacheGetReq req){
		this.init();
		return orderServices.getCacheAttrTable(req);
	}

	@Override
	@ServiceMethod(method="zte.orderService.promotion.add",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public PromotionAddResp addPromotion(PromotionAddReq req) {
		this.init();
		return orderServices.addPromotion(req);
	}

	@Override
	@ServiceMethod(method="zte.orderService.promotion.delete",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public PromotionDeleteResp deletePromotion(PromotionDeleteReq req) {
		this.init();
		return orderServices.deletePromotion(req);
	}
	
	@Override
	@ServiceMethod(method="zte.orderService.promotion.list",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public PromotionResp listPromotion(PromotionReq req) {
		this.init();
		PromotionResp promotionResp= orderServices.listPromotion(req);
		return promotionResp;
	}
	
	public static void main(String[] args) {
//		String idStr = "201502043702000754";
//		ZteClient client = ClientFactory
//				.getZteRopClient(AppKeyEnum.APP_KEY_WSSMALL_SHP);
//		PromotionDeleteReq promotionDeleteReq = new PromotionDeleteReq();
//		promotionDeleteReq.setIdStr(idStr);
//		PromotionDeleteResp promotionDeleteResp = client.execute(
//				promotionDeleteReq, PromotionDeleteResp.class);
//		logger.info(promotionDeleteResp != null);
		
		UserWdLoginReq userWdLoginReq = new UserWdLoginReq();
	    userWdLoginReq.setUsername("admin");
	    userWdLoginReq.setPassword("123");
	    ZteClient client = ClientFactory.getZteRopClient(AppKeyEnum.APP_KEY_WSSMALL_SHP);
	    UserWdLoginResp resp = client.execute(userWdLoginReq,UserWdLoginResp.class);
	    System.out.print("===="+resp.getUserSessionId());

	    
//		ZteClient client = ClientFactory
//				.getZteRopClient(AppKeyEnum.APP_KEY_WSSMALL_SHP);
		PromotionReq promotionReq = new PromotionReq();
		promotionReq.setPageNo(1);
		promotionReq.setPageSize(10);
		PromotionResp promotionResp = client.execute(
				promotionReq, PromotionResp.class);
	}


	@Override
	@ServiceMethod(method="serv.paymentcfg.banklist",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public PaymentCfgBankResp queryCfgBankList(PaymentCfgBankReq req) {
		this.init();
		PaymentCfgBankResp paymentCfgBankResp = paymentServices.queryCfgBankList(req);
		return paymentCfgBankResp;
	}



	


	@Override
	@ServiceMethod(method="zte.service.orderqueue.checkOpenService",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public CheckOpenServiceResp checkOpenService(CheckOpenServiceReq req) {
		this.init();
		return orderServices.checkOpenService(req);
		
	}


	@Override
	@ServiceMethod(method="zte.service.orderqueue.orderCreate",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public OrderCreateResp OrderCreate(OrderCreateReq req) {
		this.init();
		return orderServices.OrderCreate(req);
	}


	@Override
	@ServiceMethod(method="zte.service.orderqueue.orderHandleLogsAdd",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public OrderHandleLogsAddResp OrderHandleLogsAdd(OrderHandleLogsAddReq req) {
		this.init();
		return orderServices.OrderHandleLogsAdd(req);
	}


	@Override
	@ServiceMethod(method="zte.service.orderqueue.orderQueueFailSave",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public OrderQueueFailSaveResp OrderQueueFailSave(OrderQueueFailSaveReq req) {
		this.init();
		return orderServices.OrderQueueFailSave(req);
	}


	@Override
	@ServiceMethod(method="zte.service.orderqueue.orderQueueHisSave",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public OrderQueueHisSaveResp OrderQueueHisSave(OrderQueueHisSaveReq req) {
		this.init();
		return orderServices.OrderQueueHisSave(req);
	}


	@Override
	@ServiceMethod(method="zte.service.orderqueue.orderUpdate",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public OrderUpdateResp OrderUpdate(OrderUpdateReq req) {
		this.init();
		return orderServices.OrderUpdate(req);
	}


	@Override
	@ServiceMethod(method="zte.service.orderqueue.orderCollection",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public OrderCollectionResp orderCollection(OrderCollectionReq req) {
		this.init();
		return orderServices.orderCollection(req);
	}
	@Override
	@ServiceMethod(method="zte.service.order.orderWorkItemQuery",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public OrderWorkItemQueryResp orderWorkItemQuery(OrderWorkItemQueryReq req) {
		this.init();
		return orderServices.orderWorkItemQuery(req);
	}


	@Override
	@ServiceMethod(method="zte.service.order.orderId", version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public OrderIdResp orderId(OrderIdReq req) {
		this.init();
		return orderServices.orderId(req);
	}

	@Override
	@ServiceMethod(method = "zte.net.ecsord.iorderservices.matchdictlogs.req", version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public MatchDictLogsResp matchDictLogs(MatchDictLogsReq req)
			throws ApiBusiException {
		this.init();
		return orderServices.matchDictLogs(req);
	}

	@Override
	@ServiceMethod(method = "zte.net.ecsord.updateinflogstate.req", version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public MatchInfLogStateResp matchInfLogState(MatchInfLogStateReq req) {
		this.init();
		return orderServices.matchInfLogState(req);
	}
	
	@Override
	@ServiceMethod(method="zte.service.order.getOrderByLogiNo",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public GetOrderByLogiNoResp getOrdByLogiNo(GetOrderByLogiNoReq req)
			throws ApiBusiException {
		this.init();
		return orderServices.getOrdByLogiNo(req);
	}
	@Override
	@ServiceMethod(method="zte.service.order.zbStartOpenAccount",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public ZBStartOpenAccountResp zbStartOpenAccount(ZBStartOpenAccountReq req) throws ApiBusiException{
		this.init();
		return orderServices.zbStartOpenAccount(req);
	}
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "执行标准化方案组件入口(规则前置)", summary = "执行标准化方案组件入口")
	public BusiCompResponse execOrderStandingPlanBusiComp(BusiCompRequest busiCompRequest){
		this.init();
		return orderServices.execOrderStandingPlanBusiComp(busiCompRequest);
	}


}
