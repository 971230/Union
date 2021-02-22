package zte.net.iservice.impl;

import javax.annotation.Resource;

import zte.net.ecsord.params.base.req.SimulatorDDGJReq;
import zte.net.ecsord.params.base.resp.SimulatorDDGJResp;
import zte.net.ecsord.params.bss.req.ActionRecvBSSReq;
import zte.net.ecsord.params.bss.req.AgencyAcctPayNotifyReq;
import zte.net.ecsord.params.bss.req.CBssLoginCertReq;
import zte.net.ecsord.params.bss.req.FeeInformReq;
import zte.net.ecsord.params.bss.req.MobileCBssOutReq;
import zte.net.ecsord.params.bss.req.MobileNetworkServiceHandleReq;
import zte.net.ecsord.params.bss.req.OrderAccountOrBuybackInformReq;
import zte.net.ecsord.params.bss.req.OrderFinAccountSyncReq;
import zte.net.ecsord.params.bss.req.PageCallVerifyBSSReq;
import zte.net.ecsord.params.bss.req.SPBuyCBssOutReq;
import zte.net.ecsord.params.bss.req.SPBuyServiceHandleReq;
import zte.net.ecsord.params.bss.req.SSOLoginBSSReq;
import zte.net.ecsord.params.bss.req.SSOLoginReqVerifyBSSReq;
import zte.net.ecsord.params.bss.resp.ActionRecvBSSResp;
import zte.net.ecsord.params.bss.resp.AgencyAcctPayNotifyRsp;
import zte.net.ecsord.params.bss.resp.FeeInformResp;
import zte.net.ecsord.params.bss.resp.MobileCBssOutResp;
import zte.net.ecsord.params.bss.resp.MobileNetworkServiceHandleResp;
import zte.net.ecsord.params.bss.resp.OrderAccountOrBuybackInformResp;
import zte.net.ecsord.params.bss.resp.OrderFinAccountSyncResp;
import zte.net.ecsord.params.bss.resp.PageCallVerifyBSSResp;
import zte.net.ecsord.params.bss.resp.SPBuyCBssOutResp;
import zte.net.ecsord.params.bss.resp.SPBuyServiceHandleResp;
import zte.net.ecsord.params.bss.resp.SSOLoginBSSResp;
import zte.net.ecsord.params.bss.resp.SSOLoginReqVerifyBSSResp;
import zte.net.ecsord.params.busiopen.account.req.OrderAKeyActReq;
import zte.net.ecsord.params.busiopen.account.resp.OrderAKeyActResp;
import zte.net.ecsord.params.busiopen.ordinfo.req.OrderInfoReq;
import zte.net.ecsord.params.busiopen.ordinfo.req.OrderTreesInfoReq;
import zte.net.ecsord.params.busiopen.ordinfo.resp.OrderInfoResp;
import zte.net.ecsord.params.busiopen.ordinfo.resp.OrderTreesInfoResp;
import zte.net.ecsord.params.busiopen.separteflow.req.OrderSeparteReq;
import zte.net.ecsord.params.busiopen.separteflow.req.PushOrderID2MemcacheReq;
import zte.net.ecsord.params.busiopen.separteflow.resp.OrderSeparteResp;
import zte.net.ecsord.params.busiopen.separteflow.resp.PushOrderID2MemcacheResp;
import zte.net.ecsord.params.busiopen.zbinfexec.req.OrderInfExecReq;
import zte.net.ecsord.params.busiopen.zbinfexec.resp.OrderInfExecResp;
import zte.net.ecsord.params.ecaop.req.AccountPhotoRequest;
import zte.net.ecsord.params.ecaop.req.CheckIDECAOPRequset;
import zte.net.ecsord.params.ecaop.resp.AccountPhotoResponse;
import zte.net.ecsord.params.ecaop.resp.CheckIDECAOPResponse;
import zte.net.ecsord.params.nd.req.DispatchingNumReceivingNDRequset;
import zte.net.ecsord.params.nd.req.NotifyOrderInfoNDRequset;
import zte.net.ecsord.params.nd.req.NotifyOrderStatuNDRequset;
import zte.net.ecsord.params.nd.req.OrderDealSuccessNDRequset;
import zte.net.ecsord.params.nd.resp.DispatchingNumReceivingNDResponse;
import zte.net.ecsord.params.nd.resp.NotifyOrderInfoNDResponse;
import zte.net.ecsord.params.nd.resp.NotifyOrderStatuNDResponse;
import zte.net.ecsord.params.nd.resp.OrderDealSuccessNDResponse;
import zte.net.ecsord.params.sf.req.ArtificialSelectRequest;
import zte.net.ecsord.params.sf.req.NotifyOrderInfoSFRequset;
import zte.net.ecsord.params.sf.req.OrderFilterServiceRequset;
import zte.net.ecsord.params.sf.req.RoutePushServiceRequset;
import zte.net.ecsord.params.sf.resp.ArtificialSelectResponse;
import zte.net.ecsord.params.sf.resp.NotifyOrderInfoSFResponse;
import zte.net.ecsord.params.sf.resp.OrderFilterServiceResponse;
import zte.net.ecsord.params.sf.resp.RoutePushServiceResponse;
import zte.net.ecsord.params.sr.req.ReadICCIDSRRequset;
import zte.net.ecsord.params.sr.req.RevertCardRequset;
import zte.net.ecsord.params.sr.req.WriteICCIDSRRequset;
import zte.net.ecsord.params.sr.resp.ReadICCIDSRResponse;
import zte.net.ecsord.params.sr.resp.RevertCardResponse;
import zte.net.ecsord.params.sr.resp.WriteICCIDSRResponse;
import zte.net.ecsord.params.taobao.req.GetTaobaoOrderInfoTAOBAORequset;
import zte.net.ecsord.params.taobao.req.LogisticsTAOBAORequset;
import zte.net.ecsord.params.taobao.req.SynchronousOrderTBRequset;
import zte.net.ecsord.params.taobao.resp.GetTaobaoOrderInfoTAOBAOResponse;
import zte.net.ecsord.params.taobao.resp.LogisticsTAOBAOResponse;
import zte.net.ecsord.params.taobao.resp.SynchronousOrderTBResponse;
import zte.net.ecsord.params.wms.req.GetOrdByBoxIdFromWMSReq;
import zte.net.ecsord.params.wms.req.NotifyOrderAccountWMSReq;
import zte.net.ecsord.params.wms.req.NotifyOrderInfoWMSReq;
import zte.net.ecsord.params.wms.req.NotifyOrderStatusFromWMSReq;
import zte.net.ecsord.params.wms.req.NotifyOrderStatusToWMSReq;
import zte.net.ecsord.params.wms.req.NotifyWriteCardResultWMSReq;
import zte.net.ecsord.params.wms.req.SynCardInfoWMSReq;
import zte.net.ecsord.params.wms.req.SynSerialNumWMSReq;
import zte.net.ecsord.params.wms.req.SynchronousCheckFromWMSReq;
import zte.net.ecsord.params.wms.resp.GetOrdByBoxIdFromWMSResp;
import zte.net.ecsord.params.wms.resp.NotifyOrderAccountWMSResp;
import zte.net.ecsord.params.wms.resp.NotifyOrderInfoWMSResp;
import zte.net.ecsord.params.wms.resp.NotifyOrderStatusFromWMSResp;
import zte.net.ecsord.params.wms.resp.NotifyOrderStatusToWMSResp;
import zte.net.ecsord.params.wms.resp.NotifyWriteCardResultWMSResp;
import zte.net.ecsord.params.wms.resp.SynCardInfoWMSResp;
import zte.net.ecsord.params.wms.resp.SynSerialNumWMSResp;
import zte.net.ecsord.params.wms.resp.SynchronousCheckFromWMSResp;
import zte.net.ecsord.params.wyg.req.ChargebackApplyWYGRequset;
import zte.net.ecsord.params.wyg.req.NotifyOrderInfoWYGRequset;
import zte.net.ecsord.params.wyg.req.StatuSynchReq;
import zte.net.ecsord.params.wyg.resp.ChargebackApplyWYGResponse;
import zte.net.ecsord.params.wyg.resp.NotifyOrderInfoWYGResponse;
import zte.net.ecsord.params.wyg.resp.StatuSynchResp;
import zte.net.ecsord.params.zb.req.BatchRefundReq;
import zte.net.ecsord.params.zb.req.NotifyDeliveryGDRequest;
import zte.net.ecsord.params.zb.req.NotifyDeliveryZBRequest;
import zte.net.ecsord.params.zb.req.NotifyOpenAccountGDRequest;
import zte.net.ecsord.params.zb.req.NotifyOrderAbnormalToSystemRequest;
import zte.net.ecsord.params.zb.req.NotifyOrderAbnormalToZBRequest;
import zte.net.ecsord.params.zb.req.NotifyOrderInfoZBRequest;
import zte.net.ecsord.params.zb.req.NotifyStringGDRequest;
import zte.net.ecsord.params.zb.req.NotifyStringZBRequest;
import zte.net.ecsord.params.zb.req.NotifyWriteCardInfoRequest;
import zte.net.ecsord.params.zb.req.PrecheckOpenAcctRequest;
import zte.net.ecsord.params.zb.req.QryCRMInfo2CardRequest;
import zte.net.ecsord.params.zb.req.ReplacementNoticeRequest;
import zte.net.ecsord.params.zb.req.StateSynToZBRequest;
import zte.net.ecsord.params.zb.req.StateSynchronizationToSystemRequest;
import zte.net.ecsord.params.zb.req.SynchronousOrderGDRequest;
import zte.net.ecsord.params.zb.req.SynchronousOrderZBRequest;
import zte.net.ecsord.params.zb.resp.NotifyDeliveryResponse;
import zte.net.ecsord.params.zb.resp.NotifyOpenAccountGDResponse;
import zte.net.ecsord.params.zb.resp.NotifyOrderAbnormalToSystemResponse;
import zte.net.ecsord.params.zb.resp.NotifyOrderAbnormalToZBResponse;
import zte.net.ecsord.params.zb.resp.NotifyOrderInfoZBResponse;
import zte.net.ecsord.params.zb.resp.NotifyStringGDResponse;
import zte.net.ecsord.params.zb.resp.NotifyStringZBResponse;
import zte.net.ecsord.params.zb.resp.NotifyWriteCardInfoResponse;
import zte.net.ecsord.params.zb.resp.PrecheckOpenAcctResponse;
import zte.net.ecsord.params.zb.resp.QryCRMInfo2CardResponse;
import zte.net.ecsord.params.zb.resp.ReplacementNoticeResponse;
import zte.net.ecsord.params.zb.resp.StateSynToZBResponse;
import zte.net.ecsord.params.zb.resp.StateSynchronizationToSystemResponse;
import zte.net.ecsord.params.zb.resp.SynchronousOrderZBResponse;
import zte.net.iservice.IInfServices;

import com.ztesoft.api.ApiBusiException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotation;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.rop.annotation.NeedInSessionType;
import com.ztesoft.rop.annotation.ServiceMethod;
import com.ztesoft.rop.annotation.ServiceMethodBean;
@ServiceMethodBean(version="1.0")
public class ZteInfOpenService implements IInfServices {
//	@Resource
	private IInfServices infServices;
	
	 private void init(){
		  infServices= SpringContextHolder.getBean("infServices");
	 }
	  
	@Override
	@ServiceMethod(method="com.zte.unicomService.zb.notifyOrderInfo",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public NotifyOrderInfoZBResponse notifyOrderInfoZB(
			NotifyOrderInfoZBRequest request) throws ApiBusiException {
		this.init();
		return infServices.notifyOrderInfoZB(request);
	}
	
	@Override
	@ServiceMethod(method="com.zte.unicomService.zb.SynchronousOrderZB",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public SynchronousOrderZBResponse synchronousOrderZB(
			SynchronousOrderZBRequest request) throws Exception {
		this.init();
		return infServices.synchronousOrderZB(request);
	}


	@Override
	@ServiceMethod(method="com.zte.unicomService.zb.SynchronousOrderGD",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public SynchronousOrderZBResponse synchronousOrderGD(
			SynchronousOrderGDRequest request) throws ApiBusiException {
		this.init();
		return infServices.synchronousOrderGD(request);
	}
	
	@Override
	@ServiceMethod(method="com.zte.unicomService.zb.NotifyStringZB",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public NotifyStringZBResponse notifyStringZB(NotifyStringZBRequest request)
			throws ApiBusiException {
		this.init();
		return infServices.notifyStringZB(request);
	}

	@Override
	@ServiceMethod(method="com.zte.unicomService.zb.NotifyStringGD",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public NotifyStringGDResponse notifyStringGD(NotifyStringGDRequest request)
			throws ApiBusiException {
		this.init();
		return infServices.notifyStringGD(request);
	}

	@Override
	@ServiceMethod(method="com.zte.unicomService.zb.NotifyOpenAccountGD",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public NotifyOpenAccountGDResponse notifyOpenAccountGD(
			NotifyOpenAccountGDRequest request) throws ApiBusiException {
		this.init();
		return infServices.notifyOpenAccountGD(request);
	}

	@Override
	@ServiceMethod(method="com.zte.unicomService.zb.PrecheckOpenAcct",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public PrecheckOpenAcctResponse precheckOpenAcctZB(
			PrecheckOpenAcctRequest request) throws ApiBusiException {
		this.init();
		return infServices.precheckOpenAcctZB(request);
	}

	@Override
	@ServiceMethod(method="com.zte.unicomService.zb.QryCRMInfo2Card",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public QryCRMInfo2CardResponse qryCRMInfo2CardZB(
			QryCRMInfo2CardRequest request) throws ApiBusiException {
		this.init();
		return infServices.qryCRMInfo2CardZB(request);
	}

	@Override
	@ServiceMethod(method="com.zte.unicomService.zb.NotifyWriteCardInfo",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public NotifyWriteCardInfoResponse notifyWriteCardInfoZB(
			NotifyWriteCardInfoRequest request) throws ApiBusiException {
		this.init();
		return infServices.notifyWriteCardInfoZB(request);
	}

	@Override
	@ServiceMethod(method="com.zte.unicomService.zb.NotifyDeliveryGD",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public NotifyDeliveryResponse notifyDeliveryGD(NotifyDeliveryGDRequest request)
			throws ApiBusiException {
		this.init();
		return infServices.notifyDeliveryGD(request);
	}

	@Override
	@ServiceMethod(method="com.zte.unicomService.zb.NotifyDeliveryZB",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public NotifyDeliveryResponse notifyDeliveryZB(NotifyDeliveryZBRequest request)
			throws ApiBusiException {
		this.init();
		return infServices.notifyDeliveryZB(request);
	}
	
	@Override
	@ServiceMethod(method="com.zte.unicomService.zb.NotifyOrderAbnormalToZB",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public NotifyOrderAbnormalToZBResponse notifyOrderAbnormalToZB(
			NotifyOrderAbnormalToZBRequest request) throws ApiBusiException {
		this.init();
		return infServices.notifyOrderAbnormalToZB(request);
	}

	@Override
	@ServiceMethod(method="com.zte.unicomService.zb.NotifyOrderAbnormalToSystem",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public NotifyOrderAbnormalToSystemResponse notifyOrderAbnormalToGD(
			NotifyOrderAbnormalToSystemRequest request) throws ApiBusiException {
		this.init();
		return infServices.notifyOrderAbnormalToGD(request);
	}

	@Override
	@ServiceMethod(method="com.zte.unicomService.zb.ReplacementNotice",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public ReplacementNoticeResponse replacementNoticeGD(
			ReplacementNoticeRequest request) throws ApiBusiException {
		this.init();
		return infServices.replacementNoticeGD(request);
	}

	@Override
	@ServiceMethod(method="com.zte.unicomService.zb.StateSynchronizationToSystem",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public StateSynchronizationToSystemResponse stateSynchronizationToSystem(
			StateSynchronizationToSystemRequest request)
			throws ApiBusiException {
		this.init();
		return infServices.stateSynchronizationToSystem(request);
	}

	@Override
	@ServiceMethod(method="com.zte.unicomService.zb.stateSynToZBRequest",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public StateSynToZBResponse stateSynToZB(StateSynToZBRequest request)
			throws ApiBusiException {
		this.init();
		return infServices.stateSynToZB(request);
	}
	
	@Override
	@ServiceMethod(method="com.zte.unicomService.wyg.notifyorderinfo",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public NotifyOrderInfoWYGResponse notifyOrderInfoWYG(
			NotifyOrderInfoWYGRequset req) throws ApiBusiException {
		this.init();
		return this.infServices.notifyOrderInfoWYG(req);
	}

	@Override
	@ServiceMethod(method="com.zte.unicomService.wyg.chargebackApply",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public ChargebackApplyWYGResponse chargebackApplyWYG(
			ChargebackApplyWYGRequset req) throws ApiBusiException {
		this.init();
		return this.infServices.chargebackApplyWYG(req);
	}

	@Override
	@ServiceMethod(method="com.zte.unicomService.wyg.statuSynch",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public StatuSynchResp statuSynchToWYG(StatuSynchReq req)
			throws ApiBusiException {
		this.init();
		return this.infServices.statuSynchToWYG(req);
	}

	@Override
	@ServiceMethod(method="zte.net.infservice.order.busi.akeyact",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public OrderAKeyActResp orderAKeyActWYG(OrderAKeyActReq req)
			throws ApiBusiException {
		this.init();
		return this.infServices.orderAKeyActWYG(req);
	}
	
	@Override
	@ServiceMethod(method="com.zte.unicomService.wms.notifyOrderInfoWMS",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public NotifyOrderInfoWMSResp notifyOrderInfoWMS(NotifyOrderInfoWMSReq req)
			throws ApiBusiException {
		this.init();
		req.setRopRequestContext(null);
		return infServices.notifyOrderInfoWMS(req);
	}

	@Override
	@ServiceMethod(method="com.zte.unicomService.wms.synchronousSerialNumberWMS",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public SynSerialNumWMSResp synchronousSerialNumberWMS(SynSerialNumWMSReq req)
			throws ApiBusiException {
		req.setRopRequestContext(null);
		this.init();
		return infServices.synchronousSerialNumberWMS(req);
	}

	@Override
	@ServiceMethod(method="com.zte.unicomService.wms.notifyOrderAccountWMS",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public NotifyOrderAccountWMSResp notifyOrderAccountWMS(
			NotifyOrderAccountWMSReq req) throws ApiBusiException {
		req.setRopRequestContext(null);
		this.init();
		return infServices.notifyOrderAccountWMS(req);
	}

	@Override
	@ServiceMethod(method="com.zte.unicomService.wms.synchronousCardInfoWMS",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public SynCardInfoWMSResp synchronousCardInfoWMS(SynCardInfoWMSReq req)
			throws ApiBusiException {
		req.setRopRequestContext(null);
		this.init();
		return infServices.synchronousCardInfoWMS(req);
	}
	
	@Override
	@ServiceMethod(method="com.zte.unicomService.wms.notifyWriteCardResultWMS",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public NotifyWriteCardResultWMSResp notifyWriteCardResultWMS(NotifyWriteCardResultWMSReq req)
			throws ApiBusiException {
		req.setRopRequestContext(null);
		this.init();
		return infServices.notifyWriteCardResultWMS(req);
	}

	@Override
	@ServiceMethod(method="com.zte.unicomService.wms.notifyOrderStatusToWMS",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public NotifyOrderStatusToWMSResp notifyOrderStatusToWMS(
			NotifyOrderStatusToWMSReq req) throws ApiBusiException {
		req.setRopRequestContext(null);
		this.init();
		return infServices.notifyOrderStatusToWMS(req);
	}

	@Override
	@ServiceMethod(method="com.zte.unicomService.wms.notifyOrderStatusFromWMS",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public NotifyOrderStatusFromWMSResp notifyOrderStatusFromWMS(
			NotifyOrderStatusFromWMSReq req) throws ApiBusiException {
		req.setRopRequestContext(null);
		this.init();
		return infServices.notifyOrderStatusFromWMS(req);
	}

	@Override
	@ServiceMethod(method="com.zte.unicomService.wms.synchronousCheckFromWMS",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public SynchronousCheckFromWMSResp synchronousCheckFromWMS(
			SynchronousCheckFromWMSReq req) throws ApiBusiException {
		req.setRopRequestContext(null);
		this.init();
		return infServices.synchronousCheckFromWMS(req);
	}
	
	@Override
	@ServiceMethod(method="com.zte.unicomService.wms.getOrdByBoxIdFromWMS",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public GetOrdByBoxIdFromWMSResp getOrdByBoxIdFromWMS(
			GetOrdByBoxIdFromWMSReq req) throws ApiBusiException {
		this.init();
		return infServices.getOrdByBoxIdFromWMS(req);
	}	
	
	@Override
	@ServiceMethod(method="com.zte.unicomService.bss.SSOLoginBSS",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public SSOLoginBSSResp SSOLoginBSS(SSOLoginBSSReq req)
			throws ApiBusiException {
		req.setRopRequestContext(null);
		this.init();
		return infServices.SSOLoginBSS(req);
	}

	@Override
	@ServiceMethod(method="com.zte.unicomService.bss.SSOLoginReqVerify",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public SSOLoginReqVerifyBSSResp SSOLoginReqVerifyBSS(
			SSOLoginReqVerifyBSSReq req) throws ApiBusiException {
		req.setRopRequestContext(null);
		this.init();
		return infServices.SSOLoginReqVerifyBSS(req);
	}

	@Override
	@ServiceMethod(method="com.zte.unicomService.bss.PageCallVerify",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public PageCallVerifyBSSResp PageCallVerifyBSS(PageCallVerifyBSSReq req)
			throws ApiBusiException {
		req.setRopRequestContext(null);
		this.init();
		return infServices.PageCallVerifyBSS(req);
	}	

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "BSS对账信息同步", summary = "BSS对账信息同步")
	@ServiceMethod(method="com.zte.unicomService.bss.orderFinAccountSync",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public OrderFinAccountSyncResp orderFinAccountSync(
			OrderFinAccountSyncReq req) throws ApiBusiException {
		req.setRopRequestContext(null);
		this.init();
		return infServices.orderFinAccountSync(req);
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "BSS业务办理", summary = "BSS业务办理")
	@ServiceMethod(method="com.zte.unicomService.bss.ActionRecv",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public ActionRecvBSSResp actionRecvBSS(ActionRecvBSSReq req)
			throws ApiBusiException {
		this.init();
		return infServices.actionRecvBSS(req);
	}
	
	@Override
	@ServiceMethod(method="com.zte.unicomService.bss.agencyAcctPayNotifyReq",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public AgencyAcctPayNotifyRsp agencyAcctPayNotify(AgencyAcctPayNotifyReq req)
			throws ApiBusiException {
		this.init();
		return infServices.agencyAcctPayNotify(req);
	}
	
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "J51 读取ICCID值接口", summary = "订单系统请求写卡机服务读取ICCID值。")
	@ServiceMethod(method="com.zte.unicomService.sr.readICCID",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public ReadICCIDSRResponse readICCIDSR(ReadICCIDSRRequset req)
			throws ApiBusiException {
		this.init();
		return infServices.readICCIDSR(req);
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "J52 写卡接口", summary = "外部针对每次写卡机访问请求对写卡机管理服务发起一个HTTP请求，写卡机管理服务接收到请求之后会对该请求进行合法性校验，如果校验不通过，将返回校验失败并中断流程；如果校验通过，则启动对写卡机的操作，处理完成后将返回处理结果。")
	@ServiceMethod(method="com.zte.unicomService.sr.writeICCID",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public WriteICCIDSRResponse writeICCIDSR(WriteICCIDSRRequset req)
			throws ApiBusiException {
		this.init();
		return infServices.writeICCIDSR(req);
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "J53回收卡", summary = "回收卡")
	@ServiceMethod(method="com.zte.unicomService.sr.revertCard",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public RevertCardResponse revertCard(RevertCardRequset req)
			throws ApiBusiException {
		this.init();
		return infServices.revertCard(req);
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "J61身份校验接口", summary = "验证身份证信息是否正确")
	@ServiceMethod(method="com.zte.unicomService.ecaop.checkID",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public CheckIDECAOPResponse checkIDECAOP(CheckIDECAOPRequset req)
			throws ApiBusiException {
		this.init();
		return infServices.checkIDECAOP(req);
	}
	
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "J62获取证件照图片路径", summary = "获取证件照图片路径")
	@ServiceMethod(method="WYG.cert.photo.url",version="1.0",needInSession=NeedInSessionType.NO,timeout = 6000)
	public AccountPhotoResponse getAccountPhotoFromWYG(AccountPhotoRequest req)
			throws ApiBusiException {
		this.init();
		return infServices.getAccountPhotoFromWYG(req);
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "J71 订单详情查询接口", summary = "订单系统将订单信息通知淘宝")
	@ServiceMethod(method="com.zte.unicomService.taobao.getTaobaoOrder",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public GetTaobaoOrderInfoTAOBAOResponse getTaobaoOrderInfo(
			GetTaobaoOrderInfoTAOBAORequset req) throws ApiBusiException {
		this.init();
		return infServices.getTaobaoOrderInfo(req);
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "J72 订单信息变更", summary = "订单系统调用该接口可实现无需物流（虚拟）发货,使用该接口发货，交易订单状态会直接变成卖家已发货；用户调用该接口可实现在线订单发货（支持货到付款） 调用该接口实现在线下单发货。")
	@ServiceMethod(method="com.zte.unicomService.taobao.logistics",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public LogisticsTAOBAOResponse LogisticsTB(LogisticsTAOBAORequset req)
			throws ApiBusiException {
		this.init();
		return infServices.LogisticsTB(req);
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "J73 订单信息变更", summary = "订单系统调用该接口可实现无需物流（虚拟）发货,使用该接口发货，交易订单状态会直接变成卖家已发货；用户调用该接口可实现在线订单发货（支持货到付款） 调用该接口实现在线下单发货。")
	@ServiceMethod(method="com.zte.unicomService.taobao.SynchronousOrder",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public SynchronousOrderTBResponse SynchronousOrderTB(SynchronousOrderTBRequset req)
			throws ApiBusiException {
		this.init();
		return infServices.SynchronousOrderTB(req);
	}
	
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "J81 订单信息变更", summary = "订单系统将订单信息通知南都")
	@ServiceMethod(method="com.zte.unicomService.nd.notifyorderinfo",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public NotifyOrderInfoNDResponse notifyOrderInfoND(
			NotifyOrderInfoNDRequset req) throws ApiBusiException {
		this.init();
		return infServices.notifyOrderInfoND(req);
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "J82 订单信息变更", summary = "物流公司接到订单系统订单后，给订单分配派工号时，调用该接口，通知订单系统，该订单由哪个工号进行处理；只有此工号登陆订单系统，才能看到此订单进行开户、写卡操作。")
	@ServiceMethod(method="com.zte.unicomService.nd.dispatchingNumReceiving",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public DispatchingNumReceivingNDResponse dispatchingNumReceivingND(
			DispatchingNumReceivingNDRequset req) throws ApiBusiException {
		this.init();
		return infServices.dispatchingNumReceivingND(req);
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "J83 订单状态通知接口", summary = "物流公司获取订单后，将订单的状态通知给订单系统。状态包括：成功接收、拒绝接收、开户失败、用户拒收、确认配送。")
	@ServiceMethod(method="com.zte.unicomService.nd.notifyOrderStatu",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public NotifyOrderStatuNDResponse notifyOrderStatuND(
			NotifyOrderStatuNDRequset req) throws ApiBusiException {
		this.init();
		return infServices.notifyOrderStatuND(req);
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "J84处理完成通知", summary = "物流公司获取订单后，将订单的状态通知给订单系统。状态包括：成功接收、拒绝接收、开户失败、用户拒收、确认配送。")
	@ServiceMethod(method="com.zte.unicomService.nd.orderDealSuccess",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public OrderDealSuccessNDResponse orderDealSuccessND(
			OrderDealSuccessNDRequset req) throws ApiBusiException {
		this.init();
		return infServices.orderDealSuccessND(req);
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "J91 订单信息变更", summary = "订单系统将订单信息变更需要通知顺丰")
	@ServiceMethod(method="com.zte.unicomService.sf.notifyOrderInfo",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public NotifyOrderInfoSFResponse notifyOrderInfoSF(
			NotifyOrderInfoSFRequset req) throws ApiBusiException {
		this.init();
		return infServices.notifyOrderInfoSF(req);
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "J92 订单筛选接口", summary = "1、\t订单系统通过此接口向顺丰企业服务平台发送自动筛单请求，用于判断客户的收、派地址是否属于顺丰的收派范围。顺丰系统会根据收派双方的地址自动判断是否在顺丰的收派范围内。如果属于范围内则返回可收派，否则返回不可收派。")
	@ServiceMethod(method="com.zte.unicomService.sf.orderFilterService",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public OrderFilterServiceResponse orderFilterServiceSF(
			OrderFilterServiceRequset req) throws ApiBusiException {
		this.init();
		return infServices.orderFilterServiceSF(req);
	}

	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "J93 订单信息变更", summary = "1、\t向订单系统定时推送运单的路由信息。需要客户系统提供服务以便顺丰向客户系统推送路由数据。")
	@ServiceMethod(method="com.zte.unicomService.sf.routePushService",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public RoutePushServiceResponse routePushServiceSF(
			RoutePushServiceRequset req) throws ApiBusiException {
		this.init();
		return infServices.routePushServiceSF(req);
	}

	/////////////////////////add by wui 接口机调用
	/**
	* 订单分流判断
	*/
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "订单分流判断", summary = "沃云购2.0新老系统订单分流、接口机分流")
	@ServiceMethod(method="zte.net.infservice.order.busi.separteflow.ordersepartereq",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public OrderSeparteResp orderSeparterFlow(OrderSeparteReq req) throws ApiBusiException {
		this.init();
	return infServices.orderSeparterFlow(req);
	}
	/**
	* 订单分流判断
	*/
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "订单分流判断", summary = "沃云购2.0新老系统订单分流、接口机分流")
	@ServiceMethod(method="zte.net.infservice.order.busi.separteflow.ordersepartereq2",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public OrderSeparteResp orderSeparterFlow2(OrderSeparteReq req) throws ApiBusiException {
		this.init();
	return infServices.orderSeparterFlow2(req);
	}	
	/**
	* 订单分流，接口机调用信息通知新订单系统
	*/
	@ZteSoftCommentAnnotation(type = "method", desc = "订单分流，总部接口调用", summary = "订单分流，接口机调用信息通知新订单系统")
	@ServiceMethod(method="zte.net.infservice.order.busi.zbinfexec.orderinfexec",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public OrderInfExecResp orderInfExec(OrderInfExecReq req) throws ApiBusiException {
		this.init();
	return infServices.orderInfExec(req);
	}
	
	/**
	* 订单分流，接口机调用信息通知新订单系统
	*/
	@ZteSoftCommentAnnotation(type = "method", desc = "订单分流，总部接口调用", summary = "订单分流，接口机调用信息通知新订单系统")
	@ServiceMethod(method="zte.net.infservice.order.busi.zbinfexec.orderinfexec2",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public OrderInfExecResp orderInfExec2(OrderInfExecReq req) throws ApiBusiException {
		this.init();
	return infServices.orderInfExec2(req);
	}
	
	/**
	* 订单分流，接口机调用信息通知新订单系统
	*/
	@ZteSoftCommentAnnotation(type = "method", desc = "订单分流", summary = "订单分流，接口机调用信息通知新订单系统")
	@ServiceMethod(method="zte.net.infservice.order.busi.ordinfo.ordertreesimpinforeq",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public OrderTreesInfoResp getOrderTreeInfo(OrderTreesInfoReq req) throws ApiBusiException {
		this.init();
	return infServices.getOrderTreeInfo(req);
	}
	
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "模拟订单归集请求", summary = "模拟订单归集请求")
	@ServiceMethod(method="zte.net.ecsord.base.simuDDGJ",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public SimulatorDDGJResp simulatorDDGJ(SimulatorDDGJReq req)
			throws ApiBusiException {
		this.init();
		return infServices.simulatorDDGJ(req);
	}

	@ZteSoftCommentAnnotation(type = "method", desc = "订单信息查询接口", summary = "接收外部订单查询请求，返回订单信息")
	@ServiceMethod(method="zte.net.infservice.order.busi.queryOrderInfo",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public OrderInfoResp queryOrderInfo(OrderInfoReq req)throws ApiBusiException{
		this.init();
		return infServices.queryOrderInfo(req);
	}
	
	@ZteSoftCommentAnnotation(type = "method", desc = "测试单退单api", summary = "测试单退单api")
	@ServiceMethod(method="com.zte.unicomService.zb.batchRefund",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public StateSynToZBResponse batchRefund(BatchRefundReq req)throws ApiBusiException{
		this.init();
		return infServices.batchRefund(req);
	}
	
	
	@ZteSoftCommentAnnotation(type = "method", desc = "商城收费、退款成功触发接口", summary = "商城收费、退款成功触发接口")
	@ServiceMethod(method="com.zte.unicomService.bss.feeInform",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public FeeInformResp feeInform(FeeInformReq req)throws ApiBusiException{
		this.init();
		return infServices.feeInform(req);
	}
	
	@ZteSoftCommentAnnotation(type = "method", desc = "商城收费、退款成功触发接口", summary = "商城收费、退款成功触发接口")
	@ServiceMethod(method="com.zte.unicomService.bss.orderAccountOrBuybackInform",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public OrderAccountOrBuybackInformResp orderAccountOrBuybackInform(OrderAccountOrBuybackInformReq req)throws ApiBusiException{
		this.init();
		return infServices.orderAccountOrBuybackInform(req);
	}
	
	@Override
	@ZteSoftCommentAnnotation(type = "method", desc = "接收顺丰推送人工筛选订单信息", summary = "商城收费、退款成功触发接口")
	@ServiceMethod(method="com.zte.unicomService.sf.artificialSelect",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public ArtificialSelectResponse artificialSelectServiceSF(ArtificialSelectRequest req) throws ApiBusiException {
		this.init();
		return infServices.artificialSelectServiceSF(req);
	}

	@Override
	@ServiceMethod(method="zte.net.ecsord.params.busiopen.separteflow.req.pushOrderID2Memcache",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public PushOrderID2MemcacheResp pushOrderID2Memcache(
			PushOrderID2MemcacheReq request) throws ApiBusiException {
		this.init();
		return infServices.pushOrderID2Memcache(request);
	}
	
	
	@ZteSoftCommentAnnotation(type = "method", desc = "CBSS-移网产品服务变更", summary = "CBSS-移网产品服务变更")
	@ServiceMethod(method="com.zte.unicomService.bss.mobilenetworkservicehandle",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public MobileNetworkServiceHandleResp mobileNetworkServiceHandle(MobileNetworkServiceHandleReq req) throws ApiBusiException{
		this.init();
		return infServices.mobileNetworkServiceHandle(req);
	}
	
	@ZteSoftCommentAnnotation(type = "method", desc = "CBSS-SP订购", summary = "CBSS-SP订购")
	@ServiceMethod(method="com.zte.unicomService.bss.spbuyservicehandle",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public SPBuyServiceHandleResp spBuyServiceHandle(SPBuyServiceHandleReq req)
			throws ApiBusiException {
		// TODO Auto-generated method stub
		this.init();
		return infServices.spBuyServiceHandle(req);
	}

	@Override
	@ZteSoftCommentAnnotation(type = "com.zte.unicomService.bss.loginandcert", desc = "CBSS-系统登录认证", summary = "CBSS-系统登录认证")
	public Object toLoginAndCert(CBssLoginCertReq req) {
		// TODO Auto-generated method stub
		this.init();
		return infServices.toLoginAndCert(req);
	}

	@Override
	@ZteSoftCommentAnnotation(type = "zte.net.ecsord.params.bss.req.spbuycbsslogincert", desc = "CBSS-SP订购", summary = "CBSS-SP订购")
	public SPBuyCBssOutResp spBuyServiceHandle(SPBuyCBssOutReq req)
			throws ApiBusiException {
		// TODO Auto-generated method stub
		this.init();
		return infServices.spBuyServiceHandle(req);
	}

	@Override
	@ZteSoftCommentAnnotation(type = "zte.net.ecsord.params.bss.req.mobilecbsslogincert", desc = "CBSS-移网", summary = "CBSS-移网")
	public MobileCBssOutResp MobileServiceHandle(MobileCBssOutReq req)
			throws ApiBusiException {
		// TODO Auto-generated method stub
		this.init();
		return infServices.MobileServiceHandle(req);
	}
}
