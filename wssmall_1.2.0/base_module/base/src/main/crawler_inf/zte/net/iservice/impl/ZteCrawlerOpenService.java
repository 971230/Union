package zte.net.iservice.impl;

import params.ZteResponse;
import params.req.BackOrderLayerReq;
import params.req.BindCustInfo2OrderReq;
import params.req.CallOutOperationReq;
import params.req.CallRefundReq;
import params.req.CardSubmitInfoReq;
import params.req.CrawlerAccountInfoReq;
import params.req.CrawlerCodeReq;
import params.req.CrawlerDeliveryInfoReq;
import params.req.CrawlerMiFiNumberReq;
import params.req.CrawlerReq;
import params.req.CrawlerTerminalInfoReq;
import params.req.CrawlerUpdateGoodsInfoReq;
import params.req.CrawlerUpdatePostInfoReq;
import params.req.CrawlerWriteCardSucReq;
import params.req.GetCardDatasReq;
import params.req.ManualModifyBuyerInfoReq;
import params.req.ModifyOpenAccountGoodsReq;
import params.req.OpenAccountDetailReq;
import params.req.OpenAccountSubmitOrderReq;
import params.req.OperationRecordReq;
import params.req.OrderSubmitReq;
import params.req.QueryOrderAllocationStatusReq;
import params.req.QueryRunThreadStatusReq;
import params.req.ReAllotOrderReq;
import params.req.RejectLayerReq;
import params.req.SingleApplicationReq;
import params.req.SubmitOrderReq;
import params.req.UpdateCrawlerSettingReq;
import params.req.ZbBackfillLogisticsReq;
import params.req.ZbOrderAuditStatusReq;
import params.req.ZbOrderDeliveryCodeQueryReq;
import params.req.ZbOrderDeliveryReq;
import params.req.ZbOrderDistributeReq;
import params.req.ZbOrderStateQueryReq;
import params.req.QueryOrderProcessReq;
import params.req.ZbOrderVerifyReq;
import params.req.ZbQueryOrderDetailReq;
import params.resp.BindCustInfo2OrderResp;
import params.resp.CrawlerResp;
import params.resp.GetCardDatasResp;
import params.resp.ManualModifyBuyerInfoResp;
import params.resp.OpenAccountDetailResp;
import params.resp.OpenAccountSubmitOrderResp;
import params.resp.OperationRecordResp;
import params.resp.OrderSubmitResp;
import params.resp.QueryOrderProcessResp;
import params.resp.QueryRunThreadStatusrResp;
import params.resp.ZbOrderDistributeResp;
import params.resp.ZbOrderVerifyResp;
import params.resp.ZbQueryOrderDetailResp;
import zte.net.iservice.ICrawlerService;

import com.ztesoft.api.spring.ApiContextHolder;
import com.ztesoft.rop.annotation.NeedInSessionType;
import com.ztesoft.rop.annotation.ServiceMethod;
import com.ztesoft.rop.annotation.ServiceMethodBean;


@ServiceMethodBean(version="1.0")
public class ZteCrawlerOpenService implements ICrawlerService{
	
	private ICrawlerService crawlerService;
	private void init() {
		if (null == crawlerService) crawlerService = ApiContextHolder.getBean("crawlerService");
	}
	@Override
	@ServiceMethod(method="zte.net.iservice.impl.ZteCrawlerOpenService.zbClientLogin",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public CrawlerResp zbClientLogin(CrawlerReq req) {
		init();
		return crawlerService.zbClientLogin(req);
	}
	@Override
	@ServiceMethod(method="zte.net.iservice.impl.ZteCrawlerOpenService.doSendValidate",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public CrawlerResp doSendValidate(CrawlerCodeReq req) {
		init();
		return crawlerService.doSendValidate(req);
	}
	@Override
	@ServiceMethod(method="zte.net.iservice.impl.ZteCrawlerOpenService.openAccountDetail",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public OpenAccountDetailResp openAccountDetail(OpenAccountDetailReq req) {
		// TODO Auto-generated method stub
		init();
		return crawlerService.openAccountDetail(req);
	}
	@Override
	@ServiceMethod(method="zte.net.iservice.impl.ZteCrawlerOpenService.modifyOpenAccountGoods",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public ZteResponse modifyOpenAccountGoods(ModifyOpenAccountGoodsReq req) {
		// TODO Auto-generated method stub
		init();
		return crawlerService.modifyOpenAccountGoods(req);
	}
	
	@Override
	@ServiceMethod(method="zte.net.iservice.impl.ZteCrawlerOpenService.updateZbDeliveryInfo",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public CrawlerResp updateZbDeliveryInfo(CrawlerDeliveryInfoReq req) {
		init();
		return crawlerService.updateZbDeliveryInfo(req);
	}
	@Override
	@ServiceMethod(method="zte.net.iservice.impl.ZteCrawlerOpenService.callOutOperation",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public CrawlerResp callOutOperation(CallOutOperationReq req) {
		init();
		return crawlerService.callOutOperation(req);
	}
	@Override
	@ServiceMethod(method="zte.net.iservice.impl.ZteCrawlerOpenService.checkZbAccountInfo",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public CrawlerResp checkZbAccountInfo(CrawlerAccountInfoReq req) {
		init();
		return crawlerService.checkZbAccountInfo(req);
	}
	@Override
	@ServiceMethod(method="zte.net.iservice.impl.ZteCrawlerOpenService.zbBackfillLogistics",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public ZteResponse zbBackfillLogistics(ZbBackfillLogisticsReq req) {
		// TODO Auto-generated method stub
		init();
		return crawlerService.zbBackfillLogistics(req);
	}
	@Override
	@ServiceMethod(method="zte.net.iservice.impl.ZteCrawlerOpenService.zbOrderDelivery",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public ZteResponse zbOrderDelivery(ZbOrderDeliveryReq req) {
		// TODO Auto-generated method stub
		init();
		return crawlerService.zbOrderDelivery(req);
	}
	
	@Override
	@ServiceMethod(method="zte.net.iservice.impl.ZteCrawlerOpenService.submitOrder",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public ZteResponse submitOrder(SubmitOrderReq req) {
		// TODO Auto-generated method stub
		init();
		return crawlerService.submitOrder(req);
	}
	@Override
	@ServiceMethod(method="zte.net.iservice.impl.ZteCrawlerOpenService.callSubmit",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public OrderSubmitResp callSubmit(OrderSubmitReq req) {
		// TODO Auto-generated method stub
		init();
		return crawlerService.callSubmit(req);
	}
	@Override
	@ServiceMethod(method="zte.net.iservice.impl.ZteCrawlerOpenService.callCardSubmit",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public ZteResponse callCardSubmit(CardSubmitInfoReq req) {
		// TODO Auto-generated method stub
		init();
		return crawlerService.callCardSubmit(req);
	}
	@Override
	@ServiceMethod(method="zte.net.iservice.impl.ZteCrawlerOpenService.getCardDatas",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public GetCardDatasResp getCardDatas(GetCardDatasReq req) {
		// TODO Auto-generated method stub
		init();
		return crawlerService.getCardDatas(req);
	}

	@Override
	@ServiceMethod(method="zte.net.iservice.impl.ZteCrawlerOpenService.singleApplication",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public ZteResponse singleApplication(SingleApplicationReq req) {
		// TODO Auto-generated method stub
		init();
		return crawlerService.singleApplication(req);
	}

	@Override
	@ServiceMethod(method="zte.net.iservice.impl.ZteCrawlerOpenService.orderAuditStatusModify",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public ZteResponse orderAuditStatusModify(ZbOrderAuditStatusReq req) {
		// TODO Auto-generated method stub
		init();
		return crawlerService.orderAuditStatusModify(req);
	}

	@Override
	@ServiceMethod(method="zte.net.iservice.impl.ZteCrawlerOpenService.backOrderLayer",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public ZteResponse backOrderLayer(BackOrderLayerReq req) {
		// TODO Auto-generated method stub
		return crawlerService.backOrderLayer(req);
	}
	@Override
	@ServiceMethod(method="zte.net.iservice.impl.ZteCrawlerOpenService.rejectLayer",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public ZteResponse rejectLayer(RejectLayerReq req) {
		// TODO Auto-generated method stub
		return crawlerService.rejectLayer(req);
	}
	@Override
	@ServiceMethod(method="zte.net.iservice.impl.ZteCrawlerOpenService.callRefund",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public ZteResponse callRefund(CallRefundReq req) {
		// TODO Auto-generated method stub
		return crawlerService.callRefund(req);
	}

	@Override
	@ServiceMethod(method="zte.net.iservice.impl.ZteCrawlerOpenService.checkMiFiNumber",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public CrawlerResp checkMiFiNumber(CrawlerMiFiNumberReq req) {
		init();
		return crawlerService.checkMiFiNumber(req);
	}
	@Override
	@ServiceMethod(method="zte.net.iservice.impl.ZteCrawlerOpenService.checkTerminalInfo",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public CrawlerResp checkTerminalInfo(CrawlerTerminalInfoReq req) {
		init();
		return crawlerService.checkTerminalInfo(req);
	}
	@Override
	@ServiceMethod(method="zte.net.iservice.impl.ZteCrawlerOpenService.reAllotOrder",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public CrawlerResp reAllotOrder(ReAllotOrderReq req) {
		this.init();
		return crawlerService.reAllotOrder(req);
	}

	@Override
	@ServiceMethod(method="zte.net.iservice.impl.ZteCrawlerOpenService.orderStateQuery",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public ZteResponse orderStateQuery(ZbOrderStateQueryReq req) {
		this.init();
		return crawlerService.orderStateQuery(req);
	}
	
	@Override
	@ServiceMethod(method="zte.net.iservice.impl.ZteCrawlerOpenService.zbOrderQueryDeliveryNum",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public ZteResponse zbOrderQueryDeliveryNum(ZbOrderDeliveryCodeQueryReq req) {
		this.init();
		return crawlerService.zbOrderQueryDeliveryNum(req);
	}
	
	@Override
	@ServiceMethod(method="zte.net.iservice.impl.ZteCrawlerOpenService.queryOrderProcess",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public QueryOrderProcessResp queryOrderProcess(QueryOrderProcessReq req) {
		init();
		return crawlerService.queryOrderProcess(req);
	}
	
	@Override
	@ServiceMethod(method="zte.net.iservice.impl.ZteCrawlerOpenService.updateGoodsInfo",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public ZteResponse updateGoodsInfo(CrawlerUpdateGoodsInfoReq req) {
		init();
		return crawlerService.updateGoodsInfo(req);
	}
	
	@Override
	@ServiceMethod(method="zte.net.iservice.impl.ZteCrawlerOpenService.updatePostInfo",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public ZteResponse updatePostInfo(CrawlerUpdatePostInfoReq req) {
		init();
		return crawlerService.updatePostInfo(req);
	}
	
	@Override
	@ServiceMethod(method="zte.net.iservice.impl.ZteCrawlerOpenService.queryOrderAllocationStatusByOrderNo",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public ZteResponse queryOrderAllocationStatusByOrderNo(QueryOrderAllocationStatusReq req) {
		init();
		return crawlerService.queryOrderAllocationStatusByOrderNo(req);
	}

	@Override
	@ServiceMethod(method="zte.net.iservice.impl.ZteCrawlerOpenService.orderVerify",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public ZbOrderVerifyResp orderVerify(ZbOrderVerifyReq verifyReq) {
		// TODO Auto-generated method stub
		init();
		return crawlerService.orderVerify(verifyReq);
	}
	@Override
	@ServiceMethod(method="zte.net.iservice.impl.ZteCrawlerOpenService.orderDistribute",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public ZbOrderDistributeResp orderDistribute(ZbOrderDistributeReq req) {
		// TODO Auto-generated method stub
		 init();
		return crawlerService.orderDistribute(req);
	}
	@Override
	@ServiceMethod(method="zte.net.iservice.impl.ZteCrawlerOpenService.updateCrawlerSetting",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public ZteResponse updateCrawlerSetting(UpdateCrawlerSettingReq req) {
		init();
		return crawlerService.updateCrawlerSetting(req);
	}
	
	@Override
	@ServiceMethod(method="zte.net.iservice.impl.ZteCrawlerOpenService.queryZBLogisticsInformation",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public OperationRecordResp queryZBLogisticsInformation(OperationRecordReq req){
		init();
		return crawlerService.queryZBLogisticsInformation(req);
	}

	@Override
	@ServiceMethod(method="zte.net.iservice.impl.ZteCrawlerOpenService.queryRunThreadStatus",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public QueryRunThreadStatusrResp queryRunThreadStatus(QueryRunThreadStatusReq req){
		init();
		return crawlerService.queryRunThreadStatus(req);
	}
	
	@Override
	@ServiceMethod(method="zte.net.iservice.impl.ZteCrawlerOpenService.bindCustInfo2Order",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public BindCustInfo2OrderResp bindCustInfo2Order(BindCustInfo2OrderReq req) {
		init();
		return crawlerService.bindCustInfo2Order(req);
	}
	
	@Override
	@ServiceMethod(method="zte.net.iservice.impl.ZteCrawlerOpenService.openAccountSubmitOrder",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public OpenAccountSubmitOrderResp openAccountSubmitOrder(
			OpenAccountSubmitOrderReq req) {
		init();
		return crawlerService.openAccountSubmitOrder(req);
	}
	@Override
	@ServiceMethod(method="zte.net.iservice.impl.ZteCrawlerOpenService.manualModifyBuyerInfo",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public ManualModifyBuyerInfoResp manualModifyBuyerInfo(
			ManualModifyBuyerInfoReq req) {
		init();
		return crawlerService.manualModifyBuyerInfo(req);
	}
	
	@Override
	@ServiceMethod(method="zte.net.iservice.impl.ZteCrawlerOpenService.queryOrderDetail",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public ZbQueryOrderDetailResp queryOrderDetail(
			ZbQueryOrderDetailReq req) {
		init();
		return crawlerService.queryOrderDetail(req);
	}
	@Override
	@ServiceMethod(method="zte.net.iservice.impl.ZteCrawlerOpenService.writeCardSuc",version="1.0",needInSession=NeedInSessionType.NO,timeout = 600000)
	public ZteResponse writeCardSuc(CrawlerWriteCardSucReq req) {
		// TODO Auto-generated method stub
		init();
		return crawlerService.writeCardSuc(req);
	}
	
}
