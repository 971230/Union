package zte.net.iservice.impl;

import com.ztesoft.api.ApiBusiException; 
import com.ztesoft.net.annotation.ZteSoftCommentAnnotation;
import com.ztesoft.net.ecsord.params.ecaop.req.AmountPayReq;
import com.ztesoft.net.ecsord.params.ecaop.req.AppOrderCancelReq;
import com.ztesoft.net.ecsord.params.ecaop.req.AuditOrderCancelReq;
import com.ztesoft.net.ecsord.params.ecaop.req.BandUserDataReq;
import com.ztesoft.net.ecsord.params.ecaop.req.BroadbandOrderInfoRefundReq;
import com.ztesoft.net.ecsord.params.ecaop.req.BroadbandOrderSubReq;
import com.ztesoft.net.ecsord.params.ecaop.req.BroadbandPreSubReq;
import com.ztesoft.net.ecsord.params.ecaop.req.BroadbandrefundReq;
import com.ztesoft.net.ecsord.params.ecaop.req.BusiHandleAPPReq;
import com.ztesoft.net.ecsord.params.ecaop.req.BusiHandleBSSReq;
import com.ztesoft.net.ecsord.params.ecaop.req.BusiHandleCheckAPPReq;
import com.ztesoft.net.ecsord.params.ecaop.req.BusiHandleCheckBSSReq;
import com.ztesoft.net.ecsord.params.ecaop.req.BusinessFeeQryReq;
import com.ztesoft.net.ecsord.params.ecaop.req.CancelOrderStatusQryReq;
import com.ztesoft.net.ecsord.params.ecaop.req.CardDataSyncBssReq;
import com.ztesoft.net.ecsord.params.ecaop.req.CardInfoGetAPPReq;
import com.ztesoft.net.ecsord.params.ecaop.req.CardInfoGetBSSReq;
import com.ztesoft.net.ecsord.params.ecaop.req.CbssrefundFeeQryReq;
import com.ztesoft.net.ecsord.params.ecaop.req.ChangeAppReq;
import com.ztesoft.net.ecsord.params.ecaop.req.ChangeSubReq;
import com.ztesoft.net.ecsord.params.ecaop.req.ChgcardnoPrecommitReq;
import com.ztesoft.net.ecsord.params.ecaop.req.CmcSmsSendReq;
import com.ztesoft.net.ecsord.params.ecaop.req.CustInfoOrderBindReq;
import com.ztesoft.net.ecsord.params.ecaop.req.DeliveryInfoUpdateReq;
import com.ztesoft.net.ecsord.params.ecaop.req.DepositOrderReq;
import com.ztesoft.net.ecsord.params.ecaop.req.DownloadRecordReq;
import com.ztesoft.net.ecsord.params.ecaop.req.DwzCnCreateReq;
import com.ztesoft.net.ecsord.params.ecaop.req.ElectronicVolumeSendReq;
import com.ztesoft.net.ecsord.params.ecaop.req.FtthPreSubReq;
import com.ztesoft.net.ecsord.params.ecaop.req.FuKaPreOpenReq;
import com.ztesoft.net.ecsord.params.ecaop.req.GeneralOrderQueryReq;
import com.ztesoft.net.ecsord.params.ecaop.req.GroupOrderCardCheckReq;
import com.ztesoft.net.ecsord.params.ecaop.req.GroupOrderFixedNetworkCheckReq;
import com.ztesoft.net.ecsord.params.ecaop.req.GroupOrderSubReq;
import com.ztesoft.net.ecsord.params.ecaop.req.GuWangPreSubReq;
import com.ztesoft.net.ecsord.params.ecaop.req.InitiationCallReq;
import com.ztesoft.net.ecsord.params.ecaop.req.IntentOrderQueryReq;
import com.ztesoft.net.ecsord.params.ecaop.req.KdNumberCheckReq;
import com.ztesoft.net.ecsord.params.ecaop.req.KdnumberQryReq;
import com.ztesoft.net.ecsord.params.ecaop.req.O2OStatusUpdateReq;
import com.ztesoft.net.ecsord.params.ecaop.req.ObjectQryReq;
import com.ztesoft.net.ecsord.params.ecaop.req.ObjectReplacePreSubReq;
import com.ztesoft.net.ecsord.params.ecaop.req.OldUserCheckReq;
import com.ztesoft.net.ecsord.params.ecaop.req.OrderCancelPreCheckReq;
import com.ztesoft.net.ecsord.params.ecaop.req.OrderCancelSubmitReq;
import com.ztesoft.net.ecsord.params.ecaop.req.OrderChargeReq;
import com.ztesoft.net.ecsord.params.ecaop.req.OrderDepositReq;
import com.ztesoft.net.ecsord.params.ecaop.req.OrderDetailReq;
import com.ztesoft.net.ecsord.params.ecaop.req.OrderFormalSubReq;
import com.ztesoft.net.ecsord.params.ecaop.req.OrderGoodsQueryReq;
import com.ztesoft.net.ecsord.params.ecaop.req.OrderInfoListQueryReq;
import com.ztesoft.net.ecsord.params.ecaop.req.OrderInfoQueryReq;
import com.ztesoft.net.ecsord.params.ecaop.req.OrderInfoRefundUpdateReq;
import com.ztesoft.net.ecsord.params.ecaop.req.OrderInfoUpdateNewReq;
import com.ztesoft.net.ecsord.params.ecaop.req.OrderInfoUpdateReq;
import com.ztesoft.net.ecsord.params.ecaop.req.OrderListActivateReq;
import com.ztesoft.net.ecsord.params.ecaop.req.OrderListByWorkQueryReq;
import com.ztesoft.net.ecsord.params.ecaop.req.OrderMakeupReq;
import com.ztesoft.net.ecsord.params.ecaop.req.OrderPayPreCheckReq;
import com.ztesoft.net.ecsord.params.ecaop.req.OrderPhotoInfoReq;
import com.ztesoft.net.ecsord.params.ecaop.req.OrderPreSubReq;
import com.ztesoft.net.ecsord.params.ecaop.req.OrderResultQueryReq;
import com.ztesoft.net.ecsord.params.ecaop.req.OrderStatusQryReq;
import com.ztesoft.net.ecsord.params.ecaop.req.OrderStatusQueryReq;
import com.ztesoft.net.ecsord.params.ecaop.req.PayResultAPPReq;
import com.ztesoft.net.ecsord.params.ecaop.req.PayWorkOrderUpdateReq;
import com.ztesoft.net.ecsord.params.ecaop.req.PreCommitAPPReq;
import com.ztesoft.net.ecsord.params.ecaop.req.PromoteProductReq;
import com.ztesoft.net.ecsord.params.ecaop.req.QryBroadbandFeeReq;
import com.ztesoft.net.ecsord.params.ecaop.req.QryFtthObjIdReq;
import com.ztesoft.net.ecsord.params.ecaop.req.QueryCalllogReq;
import com.ztesoft.net.ecsord.params.ecaop.req.QueryGoodsListReq;
import com.ztesoft.net.ecsord.params.ecaop.req.QuerySelectNumberReq;
import com.ztesoft.net.ecsord.params.ecaop.req.RateStatusReq;
import com.ztesoft.net.ecsord.params.ecaop.req.RelSelectionNumReq;
import com.ztesoft.net.ecsord.params.ecaop.req.ResourCecenterAppReq;
import com.ztesoft.net.ecsord.params.ecaop.req.SchemeFeeQryReq;
import com.ztesoft.net.ecsord.params.ecaop.req.SelfspreadOrderinfoSynReq;
import com.ztesoft.net.ecsord.params.ecaop.req.TabuserBtocbSubReq;
import com.ztesoft.net.ecsord.params.ecaop.req.TradeQueryReq;
import com.ztesoft.net.ecsord.params.ecaop.req.UserActivationReq;
import com.ztesoft.net.ecsord.params.ecaop.req.UserDataQryReq;
import com.ztesoft.net.ecsord.params.ecaop.req.WeiBoShortUrlReq;
import com.ztesoft.net.ecsord.params.ecaop.req.WisdomHomePreSubReq;
import com.ztesoft.net.ecsord.params.ecaop.req.WorkOrderListQueryReq;
import com.ztesoft.net.ecsord.params.ecaop.req.WorkOrderMixOrdUpdateReq;
import com.ztesoft.net.ecsord.params.ecaop.req.WorkOrderUpdateReq;
import com.ztesoft.net.ecsord.params.ecaop.req.WotvBroadbandBindReq;
import com.ztesoft.net.ecsord.params.ecaop.req.WotvUserSubReq;
import com.ztesoft.net.ecsord.params.ecaop.req.WriteCardResultAPPReq;
import com.ztesoft.net.ecsord.params.ecaop.req.orderInfoBackfill.OrderInfoBackfillReq;
import com.ztesoft.net.ecsord.params.ecaop.req.orderReceiveBack.OrderReceiveBackReq;
import com.ztesoft.net.ecsord.params.ecaop.resp.AmountPayResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.AppOrderCancelResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.AuditOrderCancelResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.BandUserDataResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.BroadbandOrderInfoRefundResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.BroadbandOrderSubResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.BroadbandPreSubResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.BroadbandRefundResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.BusiHandleAPPResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.BusiHandleBSSResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.BusiHandleCheckAPPResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.BusiHandleCheckBSSResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.BusinessFeeQryResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.CancelOrderStatusQryResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.CardDataSyncBssResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.CardInfoGetAPPResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.CardInfoGetBSSResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.CbssrefundFeeQryResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.ChangeAppResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.ChangeSubResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.ChgcardnoPrecommitResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.CmcSmsSendResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.CustInfoOrderBindResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.DeliveryInfoUpdateResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.DepositSubResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.DownloadRecordResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.DwzCnCreateResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.ElectronicVolumeSendResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.FtthPreSubResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.FuKaPreOpenResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.GeneralOrderQueryResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.GoodsListQueryResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.GroupOrderSubResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.GuWangPreSubResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.InitiationCallResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.IntentOrderQueryResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.KdNumberCheckResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.KdnumberQryResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.O2OStatusUpdateResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.ObjectQryResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.ObjectReplacePreSubResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.OldUserCheckResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.OrderCancelPreCheckResponse;
import com.ztesoft.net.ecsord.params.ecaop.resp.OrderCancelSubmitResponse;
import com.ztesoft.net.ecsord.params.ecaop.resp.OrderChargeResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.OrderDetailResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.OrderGoodsQueryResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.OrderInfoListQueryResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.OrderInfoQueryResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.OrderInfoRefundUpdateResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.OrderInfoUpdateNewResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.OrderInfoUpdateResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.OrderListActivateResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.OrderListByWorkQueryResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.OrderMakeupResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.OrderPhotoInfoResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.OrderPreSubResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.OrderResultQueryResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.OrderStatusQryResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.OrderStatusQueryResponse;
import com.ztesoft.net.ecsord.params.ecaop.resp.PayPreCheckResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.PayResultAPPResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.PayWorkOrderUpdateResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.PreCommitAPPResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.PromoteProductResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.QryBroadbandFeeResponse;
import com.ztesoft.net.ecsord.params.ecaop.resp.QryFtthObjIDResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.QueryCalllogResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.RateStatusResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.ResourCecenterAppResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.SchemeFeeQryResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.SelfspreadOrderinfoSynResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.TabuserBtocbSubResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.TradeQueryResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.UserActivationResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.UserDataQryResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.WeiBoShortUrlResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.WisdomHomeSubResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.WorkOrderListQueryResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.WorkOrderMixOrdUpdateResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.WorkOrderUpdateResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.WotvBroadbandBindResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.WotvUserSubResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.WriteCardResultAPPResp;
import com.ztesoft.net.ecsord.params.ecaop.resp.orderInfoBackfill.OrderInfoBackfillRsp;
import com.ztesoft.net.ecsord.params.ecaop.resp.orderReceiveBack.OrderReceiveBackResp;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.rop.annotation.NeedInSessionType;
import com.ztesoft.rop.annotation.ServiceMethod;
import com.ztesoft.rop.annotation.ServiceMethodBean;

import zte.net.ecsord.params.ecaop.req.ecaop_trades_sell_brd_sinp_open_app.AopBrdOpenAppReq;
import zte.net.ecsord.params.ecaop.req.ecaop_trades_sell_brd_sinp_open_sub.AopBrdOpenSubReq;
import zte.net.ecsord.params.ecaop.resp.CommAopApplyRsp;
import zte.net.ecsord.params.ecaop.resp.CommAopSubmitRsp;
import zte.net.ecsord.params.nd.req.OrderResultNotifyReq;
import zte.net.ecsord.params.nd.resp.OrderResultNotifyRsp;
import zte.net.iservice.IZJInfServices;

@ServiceMethodBean(version = "1.0")
public class ZteZJInfOpenService implements IZJInfServices {

	private IZJInfServices zjInfServices;

	private void init() {
		zjInfServices = SpringContextHolder.getBean("zjInfServices");
	}

	// @Override
	// @ServiceMethod(method="zte.net.iservice.impl.zjTestInf",version="1.0",needInSession=NeedInSessionType.NO,timeout
	// = 600000)
	// public ZJTestInfResponse zjTestInf(ZJTestInfRequest req) {
	// this.init();
	// return this.zjInfServices.zjTestInf(req);
	// }

	// 订单撤销申请接口
	@ZteSoftCommentAnnotation(type = "method", desc = "订单撤销申请接口", summary = "订单撤销申请接口")
	@ServiceMethod(method = "com.zte.unicomService.zj.broadband.orderCancelPreCheck", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	@Override
	public OrderCancelPreCheckResponse orderCancelPreCheck(OrderCancelPreCheckReq req) throws ApiBusiException {
		this.init();
		return this.zjInfServices.orderCancelPreCheck(req);
	}

	// 订单正式撤单接口
	@ZteSoftCommentAnnotation(type = "method", desc = "订单正式撤单接口", summary = "订单正式撤单接口")
	@ServiceMethod(method = "com.zte.unicomService.zj.broadband.orderCancelSubmit", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	@Override
	public OrderCancelSubmitResponse orderCancelSubmit(OrderCancelSubmitReq req) throws ApiBusiException {
		this.init();
		return this.zjInfServices.orderCancelSubmit(req);
	}

	// 宽带受理费用查询
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "宽带受理费用查询", summary = "宽带受理费用查询")
	@ServiceMethod(method = "com.zte.unicomService.zj.broadband.qryBroadbandFeeReq", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public QryBroadbandFeeResponse qryBroadbandFee(QryBroadbandFeeReq req) throws ApiBusiException {
		this.init();
		return this.zjInfServices.qryBroadbandFee(req);
	}

	// 宽带受理预提交接口
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "宽带受理预提交接口", summary = "宽带受理预提交接口")
	@ServiceMethod(method = "com.zte.unicomService.zj.broadband.broadbandPreSubReq", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public BroadbandPreSubResp broadbandPreSub(BroadbandPreSubReq req) throws ApiBusiException {
		this.init();
		return this.zjInfServices.broadbandPreSub(req);
	}

	// 固网预提交接口
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "固网预提交接口", summary = "固网预提交接口")
	@ServiceMethod(method = "com.zte.unicomService.zj.broadband.guWangPreSubReq", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public GuWangPreSubResp guWangPreSub(GuWangPreSubReq req) throws ApiBusiException {
		this.init();
		return this.zjInfServices.guWangPreSub(req);
	}

	// 智慧到家预提交接口
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "智慧到家预提交接口", summary = "智慧到家预提交接口")
	@ServiceMethod(method = "ecaop.trades.serv.zhdj.newopen.sub", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public WisdomHomeSubResp wisdomHomePreSub(WisdomHomePreSubReq req) throws ApiBusiException {
		this.init();
		return this.zjInfServices.wisdomHomePreSub(req);
	}
	
	// 押金预提交接口
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "押金预提交接口", summary = "押金预提交接口")
	@ServiceMethod(method = "ecaop.trades.serv.busi.deposit.sub", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public DepositSubResp depositOrderSub(DepositOrderReq req) throws ApiBusiException {
		this.init();
		return this.zjInfServices.depositOrderSub(req);
	}
	
	// 融合预提交接口
    @Override
    @ZteSoftCommentAnnotation(type = "method", desc = "融合预提交接口", summary = "融合预提交接口")
    @ServiceMethod(method = "com.zte.unicomService.zj.broadband.groupOrderSubReq", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
    public GroupOrderSubResp groupOrderSub(GroupOrderSubReq req) throws ApiBusiException {
        this.init();
        return this.zjInfServices.groupOrderSub(req);
    }

	// 沃TV新装接口
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "沃TV新装接口", summary = "沃TV新装接口")
	@ServiceMethod(method = "com.zte.unicomService.zj.broadband.wotvUserSubReq", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public WotvUserSubResp wotvUserSub(WotvUserSubReq req) throws ApiBusiException {
		this.init();
		return this.zjInfServices.wotvUserSub(req);
	}

	// 宽带绑沃TV接口
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "宽带绑沃TV接口", summary = "宽带绑沃TV接口")
	@ServiceMethod(method = "com.zte.unicomService.zj.broadband.wotvBroadbandBindReq", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public WotvBroadbandBindResp wotvBroadbandBind(WotvBroadbandBindReq req) throws ApiBusiException {
		this.init();
		return this.zjInfServices.wotvBroadbandBind(req);
	}

	// 光改预提交接口
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "光改预提交接口", summary = "光改预提交接口")
	@ServiceMethod(method = "com.zte.unicomService.zj.broadband.ftthPreSubReq", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public FtthPreSubResp ftthPreSub(FtthPreSubReq req) throws ApiBusiException {
		this.init();
		return this.zjInfServices.ftthPreSub(req);
	}

	// 宽带户正式提交接口
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "宽带户正式提交接口", summary = "宽带户正式提交接口")
	@ServiceMethod(method = "com.zte.unicomService.zj.broadband.broadbandOrderSubReq", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public BroadbandOrderSubResp broadbandOrdSub(BroadbandOrderSubReq req) throws ApiBusiException {
		this.init();
		return this.zjInfServices.broadbandOrdSub(req);
	}
	
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "电子卷发放接口", summary = "电子卷发放接口")
	@ServiceMethod(method = "com.zte.yrservice.zj.electronic.volumesend", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public ElectronicVolumeSendResp electronicVolumeSend(ElectronicVolumeSendReq req) throws ApiBusiException {
		this.init();
		return this.zjInfServices.electronicVolumeSend(req);
	}
	/**
	 * 根据不同模板对应的不同查询参数查询订单信息
	 *
	 * @param templateId
	 *            模板id
	 * @param queryInfoJson
	 *            查询参数
	 * @return
	 *
	 * @author liu.quan68@ztesoft.com
	 * @throws Exception
	 *
	 * @date 2017年2月27日
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "订单查询接口", summary = "订单查询接口")
	@ServiceMethod(method = "zte.net.iservice.impl.ZJInfServices.queryOrderInfoByRequ", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	@Override
	public OrderInfoQueryResp queryOrderInfoByRequ(OrderInfoQueryReq requ) throws Exception {
		this.init();
		return this.zjInfServices.queryOrderInfoByRequ(requ);
	}

	/**
	 * 根据不同模板对应的不同查询参数查询订单信息列表
	 *
	 * @param templateId
	 *            模板id
	 * @param queryInfoJson
	 *            查询参数
	 * @return
	 *
	 * @author 宋琪
	 * @throws Exception
	 *
	 * @date 2017年6月1日
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "订单列表查询接口", summary = "订单列表查询接口")
	@ServiceMethod(method = "zte.net.iservice.impl.ZJInfServices.queryOrderInfoListByRequ", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	@Override
	public OrderInfoListQueryResp queryOrderInfoListByRequ(OrderInfoListQueryReq requ) throws Exception {
		this.init();
		return this.zjInfServices.queryOrderInfoListByRequ(requ);
	}

	/**
	 * 工单列表查询
	 * 
	 * @author 宋琪
	 * @date 2017年12月1日
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "工单列表查询接口", summary = "工单列表查询接口")
	@ServiceMethod(method = "zte.net.iservice.impl.ZJInfServices.queryWorkOrderListByRequ", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	@Override
	public WorkOrderListQueryResp queryWorkOrderListByRequ(WorkOrderListQueryReq requ) throws Exception {
		this.init();
		return this.zjInfServices.queryWorkOrderListByRequ(requ);
	}

	/**
	 * 工单状态同步接口
	 * 
	 * @author 宋琪
	 * @date 2017年12月1日
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "工单状态同步接口", summary = "工单状态同步接口")
	@ServiceMethod(method = "zte.net.iservice.impl.ZJInfServices.updateWorkOrderUpdateByRequ", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	@Override
	public WorkOrderUpdateResp updateWorkOrderUpdateByRequ(WorkOrderUpdateReq requ) throws Exception {
		this.init();
		return this.zjInfServices.updateWorkOrderUpdateByRequ(requ);
	}

	/**
	 * 收费单同步接口
	 * 
	 * @author 宋琪
	 * @date 2017年12月1日
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "收费单同步接口", summary = "收费单同步接口")
	@ServiceMethod(method = "zte.net.iservice.impl.ZJInfServices.updatePayWorkOrderUpdateByRequ", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	@Override
	public PayWorkOrderUpdateResp updatePayWorkOrderUpdateByRequ(PayWorkOrderUpdateReq requ) throws Exception {
		this.init();
		return this.zjInfServices.updatePayWorkOrderUpdateByRequ(requ);
	}

	/**
	 * 
	 * @author 宋琪
	 * @throws Exception
	 * @date 2017年6月29日
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "订单收单结果查询接口", summary = "订单收单结果查询接口")
	@ServiceMethod(method = "zte.net.iservice.impl.ZJInfServices.queryOrderResultByRequ", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	@Override
	public OrderResultQueryResp queryOrderResultByRequ(OrderResultQueryReq requ) throws Exception {
		this.init();
		return this.zjInfServices.queryOrderResultByRequ(requ);
	}

	/**
	 * 根据不同模板对应的不同查询参数查询订单信息
	 *
	 * @param templateId
	 *            模板id
	 * @param queryInfoJson
	 *            查询参数
	 * @return
	 *
	 * @author liu.quan68@ztesoft.com
	 * @throws Exception
	 *
	 * @date 2017年2月27日
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "订单更新接口", summary = "订单更新接口")
	@ServiceMethod(method = "zte.net.iservice.impl.ZJInfServices.updateOrderInfoByRequ", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	@Override
	public OrderInfoUpdateResp updateOrderInfoByRequ(OrderInfoUpdateReq requ) throws Exception {
		this.init();
		return this.zjInfServices.updateOrderInfoByRequ(requ);
	}

	/**
	 * 
	 * @param requ
	 * @return
	 * @throws Exception
	 * zte.net.iservice.impl.ZJInfServices.cancelOrderByRequ
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "行销APP撤单接口", summary = "行销APP撤单接口")
	@ServiceMethod(method = "zte.net.iservice.impl.ZJInfServices.cancelOrderByRequ", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	@Override
	public AppOrderCancelResp cancelOrderByRequ(AppOrderCancelReq requ) throws Exception {
		this.init();
		return this.zjInfServices.cancelOrderByRequ(requ);
	}
	
	/**
	 * 根据订单信息更新订单状态 new
	 * 
	 * @return
	 * @author songqi
	 * @throws Exception
	 * @date 2017年10月19日 11:43:27
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "订单更新接口new", summary = "订单更新接口")
	@ServiceMethod(method = "zte.net.iservice.impl.ZJInfServices.newUpdateOrderInfoByRequ", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	@Override
	public OrderInfoUpdateNewResp newUpdateOrderInfoByRequ(OrderInfoUpdateNewReq requ) throws Exception {
		this.init();
		return this.zjInfServices.newUpdateOrderInfoByRequ(requ);
	}

	/**
	 * 用户激活接口
	 *
	 * @param requ
	 * @return
	 * @throws Exception
	 *
	 * @author liu.quan68@ztesoft.com
	 *
	 * @date 2017年2月28日
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "用户激活接口", summary = "用户激活接口")
	@ServiceMethod(method = "zte.net.iservice.impl.ZJInfServices.callUserActivation", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	@Override
	public UserActivationResp callUserActivation(UserActivationReq requ) throws Exception {
		this.init();
		return this.zjInfServices.callUserActivation(requ);
	}

	/**
	 * 订单协议图片信息接口
	 *
	 * @param requ
	 * @return
	 * @throws Exception
	 *
	 * @author liu.quan68@ztesoft.com
	 *
	 * @date 2017年2月28日
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "订单协议图片信息接口", summary = "订单协议图片信息接口")
	@ServiceMethod(method = "zte.net.iservice.impl.ZJInfServices.dealOrderPhotoInfoByRequ", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	@Override
	public OrderPhotoInfoResp dealOrderPhotoInfoByRequ(OrderPhotoInfoReq requ) throws Exception {
		this.init();
		return this.zjInfServices.dealOrderPhotoInfoByRequ(requ);
	}

	/**
	 * 客户资料与订单绑定接口
	 *
	 * @param requ
	 * @return
	 * @throws Exception
	 *
	 * @author liu.quan68@ztesoft.com
	 *
	 * @date 2017年2月28日
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "客户资料与订单绑定接口", summary = "客户资料与订单绑定接口")
	@ServiceMethod(method = "zte.net.iservice.impl.ZJInfServices.callCustInfoOrderBind", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	@Override
	public CustInfoOrderBindResp callCustInfoOrderBind(CustInfoOrderBindReq requ) throws Exception {
		this.init();
		return this.zjInfServices.callCustInfoOrderBind(requ);
	}

	/**
	 * 订单接收接口
	 *
	 * @param requ
	 * @return
	 * @throws Exception
	 *
	 * @author liu.quan68@ztesoft.com
	 *
	 * @date 2017年2月28日
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "订单接收接口", summary = "订单接收接口")
	@ServiceMethod(method = "zte.net.iservice.impl.ZJInfServices.orderMakeupInsert", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	@Override
	public OrderMakeupResp orderMakeupInsert(OrderMakeupReq requ) throws Exception {
		this.init();
		return this.zjInfServices.orderMakeupInsert(requ);
	}

	/**
	 * 业务办理校验接口(APP)
	 *
	 * @param requ
	 * @return
	 * @throws Exception
	 *
	 * @author liu.quan68@ztesoft.com
	 *
	 * @date 2017年2月28日
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "业务办理校验接口(APP)", summary = "业务办理校验接口(APP)")
	@ServiceMethod(method = "zte.net.iservice.impl.ZJInfServices.busiHandleCheckAPP", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	@Override
	public BusiHandleCheckAPPResp busiHandleCheckAPP(BusiHandleCheckAPPReq requ) throws Exception {
		this.init();
		return this.zjInfServices.busiHandleCheckAPP(requ);
	}

	/**
	 * 业务办理校验接口(BSS)
	 *
	 * @param requ
	 * @return
	 * @throws Exception
	 *
	 * @author liu.quan68@ztesoft.com
	 *
	 * @date 2017年2月28日
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "业务办理校验接口(BSS)", summary = "业务办理校验接口(BSS)")
	@ServiceMethod(method = "zte.net.iservice.impl.ZJInfServices.busiHandleCheck", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	@Override
	public BusiHandleCheckBSSResp busiHandleCheckBSS(BusiHandleCheckBSSReq requ) throws Exception {
		this.init();
		return this.zjInfServices.busiHandleCheckBSS(requ);
	}

	/**
	 * 业务办理预提交接口(APP)
	 *
	 * @param requ
	 * @return
	 * @throws Exception
	 *
	 * @author liu.quan68@ztesoft.com
	 *
	 * @date 2017年2月28日
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "业务办理预提交接口(APP)", summary = "业务办理预提交接口(APP)")
	@ServiceMethod(method = "zte.net.iservice.impl.ZJInfServices.busiHandleAPP", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	@Override
	public BusiHandleAPPResp busiHandleAPP(BusiHandleAPPReq requ) throws Exception {
		this.init();
		return this.zjInfServices.busiHandleAPP(requ);
	}

	/**
	 * 业务办理预提交接口(BSS)
	 *
	 * @param requ
	 * @return
	 * @throws Exception
	 *
	 * @author liu.quan68@ztesoft.com
	 *
	 * @date 2017年2月28日
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "业务办理预提交接口(BSS)", summary = "业务办理预提交接口(BSS)")
	@ServiceMethod(method = "zte.net.iservice.impl.ZJInfServices.busiHandleBSS", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	@Override
	public BusiHandleBSSResp busiHandleBSS(BusiHandleBSSReq requ) throws Exception {
		this.init();
		return this.zjInfServices.busiHandleBSS(requ);
	}

	/**
	 * 订单收费接口
	 *
	 * @param requ
	 * @return
	 * @throws Exception
	 *
	 * @author liu.quan68@ztesoft.com
	 *
	 * @date 2017年2月28日
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "业务办理预提交接口(BSS)", summary = "业务办理预提交接口(BSS)")
	@ServiceMethod(method = "zte.net.iservice.impl.ZJInfServices.callOrderCharge", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	@Override
	public OrderChargeResp callOrderCharge(OrderChargeReq requ) throws Exception {
		this.init();
		return this.zjInfServices.callOrderCharge(requ);
	}

	// 订单撤单接口
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "订单撤单接口", summary = "订单撤单接口")
	@ServiceMethod(method = "com.zte.unicomService.zj.broadband.orderInfoRefundUpdateReq", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public OrderInfoRefundUpdateResp orderInfoRefundUpdate(OrderInfoRefundUpdateReq req) throws ApiBusiException {
		this.init();
		return this.zjInfServices.orderInfoRefundUpdate(req);
	}

	// 订单状态查询接口
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "订单状态查询接口", summary = "订单状态查询接口")
	@ServiceMethod(method = "com.zte.unicomService.zj.broadband.orderStatusQuery", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public OrderStatusQueryResponse orderStatusQuery(OrderStatusQueryReq req) throws ApiBusiException {
		this.init();
		return this.zjInfServices.orderStatusQuery(req);
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "商品信息获取接口", summary = "商品信息获取接口")
	@ServiceMethod(method = "zte.net.iservice.impl.ZJInfServices.queryOrderGoodsByRequ", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public OrderGoodsQueryResp queryOrderGoodsByRequ(OrderGoodsQueryReq req) throws ApiBusiException {
		this.init();
		return this.zjInfServices.queryOrderGoodsByRequ(req);
	}
	
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "商品筛选接口", summary = "商品筛选接口")
	@ServiceMethod(method = "zte.net.iservice.impl.ZJInfServices.queryGoodsListByRequ", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public GoodsListQueryResp queryGoodsListByRequ(QueryGoodsListReq req) throws ApiBusiException {
		this.init();
		return this.zjInfServices.queryGoodsListByRequ(req);
	}
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "订单支付前校验", summary = "订单支付前校验")
	@ServiceMethod(method="zte.net.iservice.impl.ZJInfServices.orderPayPreCheck",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public PayPreCheckResp orderPayPreCheck(OrderPayPreCheckReq req) throws ApiBusiException {
		this.init();
		return this.zjInfServices.orderPayPreCheck(req);
	}
	
	//订单退款接口
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "订单退款接口", summary = "订单退款接口")
	@ServiceMethod(method = "com.zte.unicomService.zj.broadband.broadbandOrderInfoRefundReq", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public BroadbandOrderInfoRefundResp broadbandOrderInfoRefund(BroadbandOrderInfoRefundReq req) throws ApiBusiException {
		this.init();
		return this.zjInfServices.broadbandOrderInfoRefund(req);
	}
	

	// 订单退款接口
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "订单退款接口(APP)", summary = "订单退款接口(APP)")
	@ServiceMethod(method = "com.zte.unicomService.zj.broadband.broadbandrefundReq", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public BroadbandRefundResp broadbandRefund(BroadbandrefundReq req) throws ApiBusiException {
		this.init();
		return this.zjInfServices.broadbandRefund(req);
	}

	/**
	 * 订单正式提交
	 */
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "订单正式提交(APP)", summary = "订单正式提交(APP)")
	@ServiceMethod(method = "zte.net.iservice.impl.ZJInfServices.OrderFormalSubReq", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public BroadbandOrderSubResp orderFormalSub(OrderFormalSubReq requ) throws Exception {
		this.init();
		return this.zjInfServices.orderFormalSub(requ);
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "卡数据同步(省内)", summary = "卡数据同步(省内)")
	@ServiceMethod(method = "zte.net.iservice.impl.ZJInfServices.CardDataSyncBssReq", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public CardDataSyncBssResp cardDataSyncBss(CardDataSyncBssReq requ) throws Exception {
		this.init();
		return this.zjInfServices.cardDataSyncBss(requ);
	}

	// 接收外系统预开户申请
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "接收外系统预开户申请", summary = "接收外系统预开户申请")
	@ServiceMethod(method = "zte.net.iservice.impl.ZJInfServices.orderPreSubReq", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public OrderPreSubResp orderPreSub(OrderPreSubReq req) throws ApiBusiException {
		this.init();
		return this.zjInfServices.orderPreSub(req);
	}

	// ECS省份业务用户提速续费改资料接口
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "ECS省份业务用户提速续费改资料接口", summary = "ECS省份业务用户提速续费改资料接口")
	@ServiceMethod(method = "zte.net.iservice.impl.ZJInfServices.bandUserDataReq", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public BandUserDataResp bandUserData(BandUserDataReq req) throws ApiBusiException {
		this.init();
		return this.zjInfServices.bandUserData(req);
	}

	// 白卡获取接口
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "白卡获取接口", summary = "白卡获取接口")
	@ServiceMethod(method = "com.ztesoft.net.ecsord.params.ecaop.req.WriteCardInfoReq", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public CardInfoGetBSSResp getWhiteCardInfo(CardInfoGetBSSReq req) throws ApiBusiException {
		this.init();
		return this.zjInfServices.getWhiteCardInfo(req);
	}

	// 订单预提交接口
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "订单预提交接口", summary = "订单预提交接口")
	@ServiceMethod(method = "com.ztesoft.net.ecsord.params.ecaop.req.PreCommitReq", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public PreCommitAPPResp preCommitService(PreCommitAPPReq req) throws ApiBusiException {
		this.init();
		return this.zjInfServices.preCommitService(req);
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "卡信息获取接口", summary = "卡信息获取接口")
	@ServiceMethod(method = "com.ztesoft.net.ecsord.params.ecaop.req.CardInfoGet", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public CardInfoGetAPPResp cardInfoGet(CardInfoGetAPPReq req) throws ApiBusiException {
		this.init();
		return this.zjInfServices.cardInfoGet(req);
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "写卡结果通知接口", summary = "写卡结果通知接口")
	@ServiceMethod(method = "com.ztesoft.net.ecsord.params.ecaop.req.writeCardResultService", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public WriteCardResultAPPResp writeCardResultService(WriteCardResultAPPReq req) throws ApiBusiException {
		this.init();
		return this.zjInfServices.writeCardResultService(req);
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "等待支付结果接口", summary = "等待支付结果接口")
	@ServiceMethod(method = "com.ztesoft.net.ecsord.params.ecaop.req.PayResultService", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public PayResultAPPResp PayResultService(PayResultAPPReq req) throws ApiBusiException {
		this.init();
		return this.zjInfServices.PayResultService(req);
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "订单状态查询接口", summary = "订单状态查询接口")
	@ServiceMethod(method = "zte.net.iservice.impl.ZJInfServices.orderStatusQry", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public OrderStatusQryResp orderStatusQry(OrderStatusQryReq req) throws ApiBusiException {
		this.init();
		return this.zjInfServices.orderStatusQry(req);
	}

		@Override
		@ZteSoftCommentAnnotation(type = "method", desc = "CBSS返销费用查询接口", summary = "CBSS返销费用查询接口")
		@ServiceMethod(method="com.zte.unicomService.zj.broadband.cbssrefundFeeQryReq",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
		public CbssrefundFeeQryResp cbssrefundFeeQry(CbssrefundFeeQryReq req) throws ApiBusiException{
			this.init();
			return this.zjInfServices.cbssrefundFeeQry(req);
		}
		
		@Override
		@ZteSoftCommentAnnotation(type = "method", desc = "BSS工号转CB工号接口", summary = "BSS工号转CB工号接口")
		@ServiceMethod(method="com.zte.unicomService.zj.broadband.tabuserBtocbSubReq",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
		public TabuserBtocbSubResp tabuserBtocbSub(TabuserBtocbSubReq req) throws ApiBusiException{
			this.init();
			return this.zjInfServices.tabuserBtocbSub(req);
		}
	/**
	 * 宋琪 2017年7月5日 17:36:12
	 */
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "客户下用户数据查询接口", summary = "客户下用户数据查询接口")
	@ServiceMethod(method = "zte.net.iservice.impl.ZJInfServices.userDataQry", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public UserDataQryResp userDataQry(UserDataQryReq req) throws ApiBusiException {
		this.init();
		return this.zjInfServices.userDataQry(req);
	}
		
		@Override
		@ZteSoftCommentAnnotation(type = "method", desc = "订单信息查询接口", summary = "订单信息查询接口")
		@ServiceMethod(method="zte.net.iservice.impl.ZJInfServices.queryOrderDetailByRequ",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
		public OrderDetailResp queryOrderDetailByRequ(OrderDetailReq requ) throws Exception {
			this.init();
			return this.zjInfServices.queryOrderDetailByRequ(requ);
		}

		@Override
		@ZteSoftCommentAnnotation(type = "method", desc = "赠品查询接口", summary = "赠品查询接口")
		@ServiceMethod(method="com.ztesoft.net.ecsord.params.ecaop.req.ObjectQryReq",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
		public ObjectQryResp objectQry(ObjectQryReq req) throws ApiBusiException {
			this.init();
			return this.zjInfServices.objectQry(req);
		}
		
		@Override
		@ZteSoftCommentAnnotation(type = "method", desc = "宽带新装、移机、修障进度查询接口", summary = "宽带新装、移机、修障进度查询接口")
		@ServiceMethod(method="com.ztesoft.net.ecsord.params.ecaop.req.RateStatusQry",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
		public RateStatusResp rateStatusQry(RateStatusReq req) throws ApiBusiException {
			this.init();
			return this.zjInfServices.rateStatusQry(req);
		}
		

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "返销订单状态查询接口", summary = "返销订单状态查询接口")
	@ServiceMethod(method = "zte.net.iservice.impl.ZJInfServices.cancelOrderStatusQry", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public CancelOrderStatusQryResp cancelOrderStatusQry(CancelOrderStatusQryReq req) throws ApiBusiException {
		this.init();
		return this.zjInfServices.cancelOrderStatusQry(req);
	}

	// 2.2.3 产品受理接口
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "产品受理接口", summary = "产品受理接口")
	@ServiceMethod(method = "com.zte.unicomService.zj.broadband.promoteProductReq", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public PromoteProductResp promoteProduct(PromoteProductReq req) throws ApiBusiException {
		this.init();
		return this.zjInfServices.promoteProduct(req);
	}



	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "二次选占接口", summary = "二次选占接口")
	@ServiceMethod(method = "com.zte.unicomService.zj.broadband.resourCecenterAppReq", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public ResourCecenterAppResp resourCecenterApp(ResourCecenterAppReq req) throws ApiBusiException {
		this.init();
		return this.zjInfServices.resourCecenterApp(req);
	}
	
	@Override
    @ZteSoftCommentAnnotation(type = "method", desc = "成卡卡号校验", summary = "成卡卡号校验")
    @ServiceMethod(method = "com.zte.unicomService.zj.grouporder.checkCreateCard", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
    public ResourCecenterAppResp checkCreateCard(GroupOrderCardCheckReq req) throws ApiBusiException {
        this.init();
        return this.zjInfServices.checkCreateCard(req);
    }
	
	@Override
    @ZteSoftCommentAnnotation(type = "method", desc = "终端串号校验", summary = "终端串号校验")
    @ServiceMethod(method = "com.zte.unicomService.zj.grouporder.checkApiTerminal", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
    public ResourCecenterAppResp checkApiTerminal(GroupOrderFixedNetworkCheckReq req) throws ApiBusiException {
        this.init();
        return this.zjInfServices.checkApiTerminal(req);
    }
	
	// 码上购状态同步接口
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "码上购状态同步接口", summary = "码上购状态同步接口")
	@ServiceMethod(method = "com.zte.unicomService.zj.broadband.o2OStatusUpdateReq", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public O2OStatusUpdateResp o2OStatusUpdate(O2OStatusUpdateReq req) throws ApiBusiException {
		this.init();
		return this.zjInfServices.o2OStatusUpdate(req);
	}

	// 活动费用查询接口
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "活动费用查询接口", summary = "活动费用查询接口")
	@ServiceMethod(method = "com.zte.unicomService.zj.broadband.schemeFeeQry", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public SchemeFeeQryResp schemeFeeQry(SchemeFeeQryReq req) throws ApiBusiException {
		this.init();
		return this.zjInfServices.schemeFeeQry(req);
	}

	// 营业费用查询接口
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "营业费用查询接口", summary = "营业费用查询接口")
	@ServiceMethod(method = "com.zte.unicomService.zj.broadband.businessFeeQry", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public BusinessFeeQryResp businessFeeQry(BusinessFeeQryReq req) throws ApiBusiException {
		this.init();
		return this.zjInfServices.businessFeeQry(req);
	}

	// 获取宽带账号/编码
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "获取宽带账号/编码", summary = "获取宽带账号/编码")
	@ServiceMethod(method = "com.zte.unicomService.zj.broadband.kdnumberQryReq", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public KdnumberQryResp kdnumberQry(KdnumberQryReq req) throws ApiBusiException {
		this.init();
		return this.zjInfServices.kdnumberQry(req);
	}

	// 新老客户校验
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "新老客户校验", summary = "新老客户校验")
	@ServiceMethod(method = "zte.net.iservice.impl.ZJInfServices.oldUserCheck", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public OldUserCheckResp oldUserCheck(OldUserCheckReq req) throws ApiBusiException {
		this.init();
		return this.zjInfServices.oldUserCheck(req);
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "查询光猫物品ID", summary = "查询光猫物品ID")
	@ServiceMethod(method = "zte.net.iservice.impl.ZJInfServices.qryFtthObjID", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public QryFtthObjIDResp qryFtthObjID(QryFtthObjIdReq req) throws ApiBusiException {
		this.init();
		return this.zjInfServices.qryFtthObjID(req);
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "宽带账号校验", summary = "宽带账号校验")
	@ServiceMethod(method = "zte.net.iservice.impl.ZJInfServices.kdNumberCheck", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public KdNumberCheckResp kdNumberCheck(KdNumberCheckReq req) throws ApiBusiException {
		this.init();
		return this.zjInfServices.kdNumberCheck(req);
	}
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "工单相关订单列表查询接口", summary = "工单相关订单列表查询接口")
	@ServiceMethod(method = "zte.net.iservice.impl.ZJInfServices.queryOrderListByWork", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public OrderListByWorkQueryResp queryOrderListByWork(OrderListByWorkQueryReq requ) throws Exception {
		this.init();
		return this.zjInfServices.queryOrderListByWork(requ);
	}

	/**
	 * 固移融合单工单状态更新接口
	 */
	@ZteSoftCommentAnnotation(type = "method", desc = "固移融合单工单状态更新接口", summary = "固移融合单工单状态更新接口")
	@ServiceMethod(method = "zte.net.iservice.impl.ZJInfServices.doWorkOrderMixOrdUpdate", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	@Override
	public WorkOrderMixOrdUpdateResp doWorkOrderMixOrdUpdate(
			WorkOrderMixOrdUpdateReq requ) throws Exception {
		this.init();
		return this.zjInfServices.doWorkOrderMixOrdUpdate(requ);
	}
	
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "用户缴费接口（支持4G用户）", summary = "用户缴费接口（支持4G用户）")
	@ServiceMethod(method = "zte.net.iservice.impl.ZJInfServices.amountPayReq", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public AmountPayResp paymentByBSS (AmountPayReq req) throws Exception{
		this.init();
		return this.zjInfServices.paymentByBSS(req);
	}
	
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "意向单查询接口", summary = "意向单查询接口")
	@ServiceMethod(method = "com.ztesoft.net.ecsord.params.ecaop.req.IntentOrderQueryReq", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public IntentOrderQueryResp intentOrderQuery (IntentOrderQueryReq req) throws Exception{
		this.init();
		return this.zjInfServices.intentOrderQuery(req);
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "冰淇淋宽带预提交", summary = "冰淇淋宽带预提交")
	@ServiceMethod(method = "ecaop.trades.sell.brd.sinp.open.app_aop", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public CommAopApplyRsp doAopBrdOpenApp(AopBrdOpenAppReq req)
			throws ApiBusiException {
		this.init();
		return this.zjInfServices.doAopBrdOpenApp(req);
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "冰淇淋宽带正式提交", summary = "冰淇淋宽带正式提交")
	@ServiceMethod(method = "ecaop.trades.sell.brd.sinp.open.sub_aop", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public CommAopSubmitRsp doAopBrdOpenSub(AopBrdOpenSubReq req)
			throws ApiBusiException {
		this.init();
		return this.zjInfServices.doAopBrdOpenSub(req);
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "订单生产结果通知外围", summary = "订单生产结果通知外围")
	@ServiceMethod(method = "wssmall.order.result.notify", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public OrderResultNotifyRsp doOrderResultNotify(OrderResultNotifyReq req)
			throws ApiBusiException {
		this.init();
		return this.zjInfServices.doOrderResultNotify(req);
	}
	
	/**
	 * 
	 * @param requ
	 * @return
	 * @throws Exception
	 * zte.net.iservice.impl.ZJInfServices.cancelOrderByRequ
	 */
//	@ZteSoftCommentAnnotation(type = "method", desc = "行销APP撤单接口", summary = "行销APP撤单接口")
//	@ServiceMethod(method = "zte.net.iservice.impl.ZJInfServices.cancelOrderByRequ", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
//	@Override
//	public AppOrderCancelResp cancelOrderByRequ(AppOrderCancelReq requ) throws Exception {
//		this.init();
//		return this.zjInfServices.cancelOrderByRequ(requ);
//	}
	
	
	
	
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "分享卡 副卡新开户预提交接口", summary = "分享卡 副卡新开户预提交接口")
	@ServiceMethod(method = "com.ztesoft.net.ecsord.params.ecaop.req.fuKaPreOpenReq", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public FuKaPreOpenResp kuKaPreOpen(FuKaPreOpenReq req) throws ApiBusiException {
		this.init();
		return this.zjInfServices.kuKaPreOpen(req);
	}
	
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "统一补换卡预提交", summary = "统一补换卡预提交")
	@ServiceMethod(method = "com.ztesoft.net.ecsord.params.ecaop.req.changeAppReq", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public ChangeAppResp changeAppPre(ChangeAppReq req) throws ApiBusiException{
		this.init();
		return this.zjInfServices.changeAppPre(req);
	}
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "统一补换卡预提交", summary = "统一补换卡预提交")
	@ServiceMethod(method = "com.ztesoft.net.ecsord.params.ecaop.req.changeSubReq", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public ChangeSubResp changeSub(ChangeSubReq req) throws ApiBusiException{
		this.init();
		return this.zjInfServices.changeSub(req);
	}
	
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "订单中心补换卡预提交", summary = "订单中心补换卡预提交")
	@ServiceMethod(method = "com.ztesoft.net.ecsord.params.ecaop.req.chgcardnoPrecommitReq", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public ChgcardnoPrecommitResp chgcardnoPrecommit(ChgcardnoPrecommitReq req) throws ApiBusiException{
		this.init();
		return this.zjInfServices.chgcardnoPrecommit(req);
	}
	
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "稽核退款", summary = "稽核退款")
	@ServiceMethod(method = "zte.net.iservice.impl.ZJInfServices.auditOrderCancel", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public AuditOrderCancelResp auditOrderCancel(AuditOrderCancelReq requ) {
		this.init();
		return this.zjInfServices.auditOrderCancel(requ);
	}
	
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "订单中心自传播平台订单同步接口", summary = "订单中心自传播平台订单同步接口")
	@ServiceMethod(method = "com.ztesoft.net.ecsord.params.ecaop.req.SelfspreadOrderinfoSynReq", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public SelfspreadOrderinfoSynResp selfspreadOrderinfoSyn(SelfspreadOrderinfoSynReq req){
		this.init();
		return this.zjInfServices.selfspreadOrderinfoSyn(req);
	}
	
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "商品预定回填接口", summary = "商品预定回填接口")
	@ServiceMethod(method = "order.Receive.Back", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public OrderReceiveBackResp orderReceiveBack(OrderReceiveBackReq req){
		this.init();
		return this.zjInfServices.orderReceiveBack(req);
	}
	
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "交易结果查询接口", summary = "交易结果查询接口")
	@ServiceMethod(method = "pc.user.page.trade-query", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public TradeQueryResp tradeQuery (TradeQueryReq req) throws ApiBusiException{
		this.init();
		return this.zjInfServices.tradeQuery(req);
	}
	
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "短网址生成接口", summary = "短网址生成接口")
	@ServiceMethod(method = "baidu.user.message.send", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public DwzCnCreateResp dwzCnCreate(DwzCnCreateReq req) throws ApiBusiException{
		this.init();
		return this.zjInfServices.dwzCnCreate(req);
	}
	
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "异网短信接口", summary = "异网短信接口")
	@ServiceMethod(method = "yeion.user.message.send", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public CmcSmsSendResp cmcSmsSend(CmcSmsSendReq req) throws Exception{
		this.init();
		return this.zjInfServices.cmcSmsSend(req);
	}
	
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "订单信息回填接口", summary = "订单信息回填接口")
	@ServiceMethod(method = "orderInfoBackfill", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public OrderInfoBackfillRsp orderInfoBackfill(OrderInfoBackfillReq req) throws ApiBusiException{
		this.init();
		return this.zjInfServices.orderInfoBackfill(req);
	}

	
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "自动外呼IVR请求接口", summary = "自动外呼IVR请求接口")
	@ServiceMethod(method = "com.ztesoft.net.ecsord.params.ecaop.req.initiationCallReq", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public InitiationCallResp initiationCall (InitiationCallReq req) throws ApiBusiException{
		this.init();
		return this.zjInfServices.initiationCall(req);
	}
	
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "外呼记录查询接口", summary = "外呼记录查询接口")
	@ServiceMethod(method = "com.ztesoft.net.ecsord.params.ecaop.req.queryCalllogReq", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public QueryCalllogResp queryCalllog (QueryCalllogReq req) throws ApiBusiException{
		this.init();
		return this.zjInfServices.queryCalllog(req);
	}
	
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "外呼录音下载接口", summary = "外呼录音下载接口")
	@ServiceMethod(method = "com.ztesoft.net.ecsord.params.ecaop.req.downloadRecordReq", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public DownloadRecordResp downloadRecord (DownloadRecordReq req) throws ApiBusiException{
		this.init();
		return this.zjInfServices.downloadRecord(req);
	}

	
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "终端更换预提交", summary = "终端更换预提交")
	@ServiceMethod(method = "com.zte.unicomService.zj.ZJInfServices.objectReplace",version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public ObjectReplacePreSubResp objectReplace(ObjectReplacePreSubReq req) {
		this.init();
		return this.zjInfServices.objectReplace(req);
	}
    @Override
    @ZteSoftCommentAnnotation(type = "method", desc = "简单订单信息查询", summary = "简单订单信息查询")
    @ServiceMethod(method = "zte.net.iservice.impl.ZJInfServices.queryOrderActivate",version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
    public OrderListActivateResp queryOrderActivate(OrderListActivateReq req) {
        this.init();
        return this.zjInfServices.queryOrderActivate(req);
    }

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "押金业务收单接口", summary = "押金业务收单接口")
	@ServiceMethod(method = "zte.net.iservice.impl.ZJInfServices.orderDepositInsert",version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public OrderMakeupResp orderDepositInsert(OrderDepositReq req) throws Exception {
		this.init();
		return this.zjInfServices.orderDepositInsert(req);
	}

	
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "物理信息跟新接口", summary = "物流信息跟新接口")
	@ServiceMethod(method = "zte.net.iservice.impl.ZJInfServices.DeliveryInfoUpdateReq",version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public DeliveryInfoUpdateResp deliveryInfoUpdate(DeliveryInfoUpdateReq req) {
		this.init();
		 return this.zjInfServices.deliveryInfoUpdate(req);
	}
	
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "选、预占号码查询", summary = "选、预占号码查询")
	@ServiceMethod(method = "com.zte.unicomService.zj.broadband.queryselectnumber", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public ResourCecenterAppResp QuerySelectNumber(QuerySelectNumberReq req) throws Exception {
		this.init();
		return this.zjInfServices.QuerySelectNumber(req);
	}
	//com.zte.unicomService.zj.broadband.relselectionnum
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "号码手动释放", summary = "号码手动释放")
	@ServiceMethod(method = "com.zte.unicomService.zj.broadband.relselectionnum", version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public ResourCecenterAppResp RelSelectionNum(RelSelectionNumReq req) throws Exception {
		this.init();
		return this.zjInfServices.RelSelectionNum(req);
	}

	
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "微博短链接", summary = "微博短链接")
	@ServiceMethod(method = "com.ztesoft.net.ecsord.params.ecaop.req.weiBoShortUrlReq",version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public WeiBoShortUrlResp getShortUrl (WeiBoShortUrlReq req){
		this.init();
		return this.zjInfServices.getShortUrl(req);
	}
	
	/*@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "裸机销售", summary = "裸机销售")
	@ServiceMethod(method = "com.ztesoft.net.ecsord.params.ecaop.req.mobileComteSaleReq",version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public MobileComteSaleResp mobileComteSale(MobileComteSaleReq req){
		this.init();
		return this.zjInfServices.mobileComteSale(req);
	}*/

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "通用查询接口", summary = "通用查询接口")
	@ServiceMethod(method = "com.ztesoft.net.ecsord.params.ecaop.req.GeneralOrderQueryReq",version = "1.0", needInSession = NeedInSessionType.NO, timeout = 600000)
	public GeneralOrderQueryResp generalOrderQuery(GeneralOrderQueryReq req) {
		this.init();
		 return this.zjInfServices.generalOrderQuery(req);
	}

}
