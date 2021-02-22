package zte.net.iservice;

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
import params.resp.QueryRunThreadStatusrResp;
import params.resp.QueryOrderProcessResp;
import params.resp.ZbOrderDistributeResp;
import params.resp.ZbOrderVerifyResp;
import params.resp.ZbQueryOrderDetailResp;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotation;

@ZteSoftCommentAnnotation(type="class",desc="爬虫API",summary="爬虫API")
public interface ICrawlerService {
	
	@ZteSoftCommentAnnotation(type="method",desc="爬虫登录",summary="爬虫登录")
	public CrawlerResp zbClientLogin(CrawlerReq req);
	@ZteSoftCommentAnnotation(type="method",desc="爬虫登录获取验证码",summary="爬虫登录获取验证码")
	public CrawlerResp doSendValidate(CrawlerCodeReq req);
	@ZteSoftCommentAnnotation(type="method",desc="订单二次分配",summary="订单二次分配")
	public CrawlerResp reAllotOrder(ReAllotOrderReq req);
	@ZteSoftCommentAnnotation(type="method",desc="进入总部开户详情页",summary="进入总部开户详情页")
	public OpenAccountDetailResp openAccountDetail(OpenAccountDetailReq req);
	@ZteSoftCommentAnnotation(type="method",desc="修改开户环节商品信息",summary="修改开户环节商品信息")
	public ZteResponse modifyOpenAccountGoods(ModifyOpenAccountGoodsReq req);
	@ZteSoftCommentAnnotation(type="method",desc="爬虫修改总商配送信息",summary="爬虫修改总商配送信息")
	public CrawlerResp updateZbDeliveryInfo(CrawlerDeliveryInfoReq req);	
	@ZteSoftCommentAnnotation(type="method",desc="爬虫外呼确认操作",summary="爬虫外呼确认操作")
	public CrawlerResp callOutOperation(CallOutOperationReq req);
	@ZteSoftCommentAnnotation(type="method",desc="爬虫开户校验信息",summary="爬虫开户校验信息")
	public CrawlerResp checkZbAccountInfo(CrawlerAccountInfoReq req);
	@ZteSoftCommentAnnotation(type="method",desc="总部订单回填物流单接口",summary="总部订单回填物流单接口")
	public ZteResponse zbBackfillLogistics(ZbBackfillLogisticsReq req);
	@ZteSoftCommentAnnotation(type="method",desc="总部订单发货接口",summary="总部订单发货接口")
	public ZteResponse zbOrderDelivery(ZbOrderDeliveryReq req);
	
	@ZteSoftCommentAnnotation(type="method",desc="确认提交订单按钮接口",summary="确认提交订单按钮接口")
	public ZteResponse submitOrder(SubmitOrderReq req);
	@ZteSoftCommentAnnotation(type="method",desc="订单提交按钮（开户提交，预开户）接口",summary="订单提交按钮（开户提交，预开户）接口")
	public OrderSubmitResp callSubmit(OrderSubmitReq req);
	@ZteSoftCommentAnnotation(type="method",desc="提交写卡接口",summary="提交写卡接口")
	public ZteResponse callCardSubmit(CardSubmitInfoReq req);
	@ZteSoftCommentAnnotation(type="method",desc="获取写卡数据接口",summary="获取写卡数据接口")

	public GetCardDatasResp getCardDatas(GetCardDatasReq req);
	@ZteSoftCommentAnnotation(type="method",desc="退单申请接口",summary="退单申请接口")
	public ZteResponse singleApplication(SingleApplicationReq req);
	

	@ZteSoftCommentAnnotation(type="method",desc="退单审核退单接口",summary="退单审核退单接口")
	public ZteResponse backOrderLayer(BackOrderLayerReq req);
	@ZteSoftCommentAnnotation(type="method",desc="退单审核驳回接口",summary="退单审核驳回接口")
	public ZteResponse rejectLayer(RejectLayerReq req);
	@ZteSoftCommentAnnotation(type="method",desc="退款审核退款接口",summary="退款审核退款接口")
	public ZteResponse callRefund(CallRefundReq req);
	
	@ZteSoftCommentAnnotation(type="method",desc="订单激活环节更新审核状态",summary="订单激活环节更新审核状态")
	public ZteResponse orderAuditStatusModify(ZbOrderAuditStatusReq req);
	
	@ZteSoftCommentAnnotation(type="method",desc="上网卡号码预占接口",summary="上网卡号码预占接口")
	public CrawlerResp checkMiFiNumber(CrawlerMiFiNumberReq req);
	@ZteSoftCommentAnnotation(type="method",desc="终端资源变更接口",summary="终端资源变更接口")
	public CrawlerResp checkTerminalInfo(CrawlerTerminalInfoReq req);

	@ZteSoftCommentAnnotation(type="method",desc="总商客服订单状态查询接口",summary="总商客服订单状态查询接口")
	public ZteResponse orderStateQuery(ZbOrderStateQueryReq req);
	@ZteSoftCommentAnnotation(type="method",desc="总商获取电子物流单号接口",summary="总商获取电子物流单号接口")
	public ZteResponse zbOrderQueryDeliveryNum(ZbOrderDeliveryCodeQueryReq req);
	
	
	@ZteSoftCommentAnnotation(type="method",desc="获取自动开户列表",summary="获取自动开户列表")
	public QueryOrderProcessResp queryOrderProcess(QueryOrderProcessReq req);	

	@ZteSoftCommentAnnotation(type="method",desc="修改总商商品清单",summary="修改总商商品清单")
	public ZteResponse updateGoodsInfo(CrawlerUpdateGoodsInfoReq req);

	@ZteSoftCommentAnnotation(type="method",desc="修改总商配送信息",summary="修改总商配送信息")
	public ZteResponse updatePostInfo(CrawlerUpdatePostInfoReq req);
	
	@ZteSoftCommentAnnotation(type="method",desc="查询总商订单分配状态",summary="查询总商订单分配状态")
	public ZteResponse queryOrderAllocationStatusByOrderNo(QueryOrderAllocationStatusReq req);


	@ZteSoftCommentAnnotation(type="method",desc="订单审核",summary="订单审核")
	public ZbOrderVerifyResp orderVerify(ZbOrderVerifyReq verifyReq);
	@ZteSoftCommentAnnotation(type="method",desc="订单分配",summary="订单分配")
	public ZbOrderDistributeResp orderDistribute(ZbOrderDistributeReq req); 

	@ZteSoftCommentAnnotation(type="method",desc="更新爬虫查询条件",summary="更新爬虫查询条件")
	public ZteResponse updateCrawlerSetting(UpdateCrawlerSettingReq req); 
	
	@ZteSoftCommentAnnotation(type="method",desc="查询总商物流路由信息",summary="查询总商物流路由信息")
	public OperationRecordResp queryZBLogisticsInformation(OperationRecordReq req); 

	@ZteSoftCommentAnnotation(type="method",desc="查询运行中的线程状状态",summary="查询运行中的线程状状态")
	public QueryRunThreadStatusrResp queryRunThreadStatus(QueryRunThreadStatusReq req);
	
	@ZteSoftCommentAnnotation(type="method",desc="客户资料与订单绑定信息",summary="客户资料与订单绑定信息")
	public BindCustInfo2OrderResp bindCustInfo2Order(BindCustInfo2OrderReq req);
	
	@ZteSoftCommentAnnotation(type="method",desc="手工开户信息补录",summary="手工开户信息补录")
	public OpenAccountSubmitOrderResp openAccountSubmitOrder(OpenAccountSubmitOrderReq req);
	
	@ZteSoftCommentAnnotation(type="method",desc="手工修改买家信息",summary="手工修改买家信息")
	public ManualModifyBuyerInfoResp manualModifyBuyerInfo(ManualModifyBuyerInfoReq req);
	
	@ZteSoftCommentAnnotation(type="method",desc="查询总商订单路由信息",summary="查询总商订单路由信息")
	public ZbQueryOrderDetailResp queryOrderDetail(ZbQueryOrderDetailReq req);
	@ZteSoftCommentAnnotation(type="method",desc="写卡成功",summary="写卡成功")
	public ZteResponse writeCardSuc(CrawlerWriteCardSucReq req);
}
