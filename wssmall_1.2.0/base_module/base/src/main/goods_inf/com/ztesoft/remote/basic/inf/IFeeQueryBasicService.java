package com.ztesoft.remote.basic.inf;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotation;
import com.ztesoft.remote.basic.params.req.AccBalanceRequest;
import com.ztesoft.remote.basic.params.req.AlreadyChargeFeeRequest;
import com.ztesoft.remote.basic.params.req.ArrearageRequest;
import com.ztesoft.remote.basic.params.req.BussinessAcceptRequest;
import com.ztesoft.remote.basic.params.req.CurrMonthBillRequest;
import com.ztesoft.remote.basic.params.req.CurrentPackageRequest;
import com.ztesoft.remote.basic.params.req.DataUDRQueryRequest;
import com.ztesoft.remote.basic.params.req.FeeCycleShowRequest;
import com.ztesoft.remote.basic.params.req.FeeNumberShowRequest;
import com.ztesoft.remote.basic.params.req.FlowSumRequest;
import com.ztesoft.remote.basic.params.req.HisBillsRequest;
import com.ztesoft.remote.basic.params.req.IncomeCountRequest;
import com.ztesoft.remote.basic.params.req.IntegralConsumeRequest;
import com.ztesoft.remote.basic.params.req.IntegralRequest;
import com.ztesoft.remote.basic.params.req.LastMonthBillRequest;
import com.ztesoft.remote.basic.params.req.PackageCycleShowRequest;
import com.ztesoft.remote.basic.params.req.PackageDetailsRequest;
import com.ztesoft.remote.basic.params.req.PackageNumberShowRequest;
import com.ztesoft.remote.basic.params.req.PackageUseRequest;
import com.ztesoft.remote.basic.params.req.PayFeeRecordsRequest;
import com.ztesoft.remote.basic.params.req.PaymentInformationRequest;
import com.ztesoft.remote.basic.params.req.PreOrderRequest;
import com.ztesoft.remote.basic.params.req.PukQryRequest;
import com.ztesoft.remote.basic.params.req.QryCustOrderRequest;
import com.ztesoft.remote.basic.params.req.QryProdInstListRequest;
import com.ztesoft.remote.basic.params.req.QryProdInstRequest;
import com.ztesoft.remote.basic.params.req.RealTimeFeeRequest;
import com.ztesoft.remote.basic.params.req.RemianingTrafficFlowRequest;
import com.ztesoft.remote.basic.params.req.SmsudrQueryRequest;
import com.ztesoft.remote.basic.params.req.SpudrQueryRequest;
import com.ztesoft.remote.basic.params.req.SubInstQryRequest;
import com.ztesoft.remote.basic.params.req.TrafficCardRecordsRequest;
import com.ztesoft.remote.basic.params.req.VoiceUDRQueryRequest;
import com.ztesoft.remote.basic.params.req.WalletBalanceRequest;
import com.ztesoft.remote.basic.params.resp.AccBalanceResponse;
import com.ztesoft.remote.basic.params.resp.AlreadyChargeFeeResponse;
import com.ztesoft.remote.basic.params.resp.ArrearageResponse;
import com.ztesoft.remote.basic.params.resp.BussinessAcceptResponse;
import com.ztesoft.remote.basic.params.resp.CurrMonthBillResponse;
import com.ztesoft.remote.basic.params.resp.CurrentPackageResponse;
import com.ztesoft.remote.basic.params.resp.DataUDRQueryResponse;
import com.ztesoft.remote.basic.params.resp.FeeCycleShowResponse;
import com.ztesoft.remote.basic.params.resp.FeeNumberShowResponse;
import com.ztesoft.remote.basic.params.resp.FlowSumResponse;
import com.ztesoft.remote.basic.params.resp.HisBillsResponse;
import com.ztesoft.remote.basic.params.resp.IncomeCountResponse;
import com.ztesoft.remote.basic.params.resp.IntegralConsumeResponse;
import com.ztesoft.remote.basic.params.resp.IntegralResponse;
import com.ztesoft.remote.basic.params.resp.LastMonthBillResponse;
import com.ztesoft.remote.basic.params.resp.PackageCycleShowResponse;
import com.ztesoft.remote.basic.params.resp.PackageDetailsResponse;
import com.ztesoft.remote.basic.params.resp.PackageNumberShowResponse;
import com.ztesoft.remote.basic.params.resp.PackageUseResponse;
import com.ztesoft.remote.basic.params.resp.PayFeeRecordsResponse;
import com.ztesoft.remote.basic.params.resp.PaymentInformationResponse;
import com.ztesoft.remote.basic.params.resp.PreOrderResponse;
import com.ztesoft.remote.basic.params.resp.PukQryResponse;
import com.ztesoft.remote.basic.params.resp.QryCustOrderResponse;
import com.ztesoft.remote.basic.params.resp.QryProdInstListResponse;
import com.ztesoft.remote.basic.params.resp.QryProdInstResponse;
import com.ztesoft.remote.basic.params.resp.RealTimeFeeResponse;
import com.ztesoft.remote.basic.params.resp.RemianingTrafficFlowResponse;
import com.ztesoft.remote.basic.params.resp.SmsudrQueryResponse;
import com.ztesoft.remote.basic.params.resp.SpudrQueryResponse;
import com.ztesoft.remote.basic.params.resp.SubInstQryResponse;
import com.ztesoft.remote.basic.params.resp.TrafficCardRecordsResponse;
import com.ztesoft.remote.basic.params.resp.VoiceUDRQueryResponse;
import com.ztesoft.remote.basic.params.resp.WalletBalanceResponse;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2014-02-11 11:12
 * To change this template use File | Settings | File Templates.
 * 费用查询基础类
 */
@ZteSoftCommentAnnotation(type = "class", desc = "费用查询API", summary = "费用查询API[账户余额、实时话费、欠费查询、缴费记录、流量卡充值记录、流量卡剩余流量、上月账单、本月账单、PUK查询、账单查询、订单进度查询、短信清单查询、数据清单查询、语音清单查询、增值业务清单查询、产品资料查询、增值订购关系查询、产品列表查询]")
public interface IFeeQueryBasicService {

    @ZteSoftCommentAnnotation(type = "method", desc = "账户余额", summary = "账户余额")
    public AccBalanceResponse accBalance(AccBalanceRequest request);

    @ZteSoftCommentAnnotation(type = "method", desc = "实时话费", summary = "实时话费")
    public RealTimeFeeResponse realTimeFee(RealTimeFeeRequest request);

    @ZteSoftCommentAnnotation(type = "method", desc = "欠费查询", summary = "欠费查询")
    public ArrearageResponse arrearage(ArrearageRequest request);

    @ZteSoftCommentAnnotation(type = "method", desc = "缴费记录", summary = "缴费记录")
    public PayFeeRecordsResponse payFeeRecords(PayFeeRecordsRequest request);

    @ZteSoftCommentAnnotation(type = "method", desc = "流量卡充值记录", summary = "流量卡充值记录")
    public TrafficCardRecordsResponse trafficCardRecords(TrafficCardRecordsRequest request);

    @ZteSoftCommentAnnotation(type = "method", desc = "流量卡剩余流量", summary = "流量卡剩余流量")
    public RemianingTrafficFlowResponse remianingTrafficFlow(RemianingTrafficFlowRequest request);

    @ZteSoftCommentAnnotation(type = "method", desc = "上月账单", summary = "上月账单")
    public LastMonthBillResponse lastMonthBill(LastMonthBillRequest request);

    @ZteSoftCommentAnnotation(type = "method", desc = "本月账单", summary = "本月账单")
    public CurrMonthBillResponse currMonthBill(CurrMonthBillRequest request);

    @ZteSoftCommentAnnotation(type = "method", desc = "历史账单", summary = "历史账单")
    public HisBillsResponse hisBills(HisBillsRequest request);

    @ZteSoftCommentAnnotation(type = "method", desc = "当前套餐查询", summary = "当前套餐查询")
    public CurrentPackageResponse qryCurrentPackage(CurrentPackageRequest request);

    @ZteSoftCommentAnnotation(type = "method", desc = "套餐使用情况查询", summary = "套餐使用情况查询")
    public PackageUseResponse qryPackageUse(PackageUseRequest request);

    @ZteSoftCommentAnnotation(type = "method", desc = "流量汇总查询", summary = "流量汇总查询")
    public FlowSumResponse qryFlowSum(FlowSumRequest request);

    @ZteSoftCommentAnnotation(type = "method", desc = "积分查询", summary = "积分查询")
    public IntegralResponse queryIntegral(IntegralRequest request);

    @ZteSoftCommentAnnotation(type = "method", desc = "积分消费记录", summary = "积分消费记录")
    public IntegralConsumeResponse queryIntegralConsumeRecords(IntegralConsumeRequest request);

    @ZteSoftCommentAnnotation(type = "method", desc = "缴费号码展示", summary = "缴费号码展示")
    public FeeNumberShowResponse feeNumberShow(FeeNumberShowRequest request);

    @ZteSoftCommentAnnotation(type = "method", desc = "话费周期展示", summary = "话费周期展示")
    public FeeCycleShowResponse feeCycleShow(FeeCycleShowRequest request);

    @ZteSoftCommentAnnotation(type = "method", desc = "已充话费", summary = "已充话费")
    public AlreadyChargeFeeResponse alreadyChargeFee(AlreadyChargeFeeRequest request);

    @ZteSoftCommentAnnotation(type = "method", desc = "套餐周期展示", summary = "套餐周期展示")
    public PackageCycleShowResponse packageCycleShow(PackageCycleShowRequest request);

    @ZteSoftCommentAnnotation(type = "method", desc = "套餐号码展示", summary = "套餐号码展示")
    public PackageNumberShowResponse packageNumberShow(PackageNumberShowRequest request);

    @ZteSoftCommentAnnotation(type = "method", desc = "套餐详情", summary = "套餐详情")
    public PackageDetailsResponse packageDetails(PackageDetailsRequest request);

    @ZteSoftCommentAnnotation(type = "method", desc = "收益查询", summary = "收益查询")
    public BussinessAcceptResponse queryIncome(BussinessAcceptRequest request);

    @ZteSoftCommentAnnotation(type = "method", desc = "钱包余额", summary = "钱包余额")
    public WalletBalanceResponse queryWalletBalance(WalletBalanceRequest request);

    @ZteSoftCommentAnnotation(type = "method", desc = "收益统计", summary = "收益统计")
    public IncomeCountResponse queryIncomeCount(IncomeCountRequest request);
    
    @ZteSoftCommentAnnotation(type = "method", desc = "PUK查询", summary = "PUK查询")
    public PukQryResponse pukQry(PukQryRequest request);
    
    @ZteSoftCommentAnnotation(type = "method", desc = "订单进度查询", summary = "订单进度查询")
    public QryCustOrderResponse qryCustOrder(QryCustOrderRequest request);
    
    @ZteSoftCommentAnnotation(type = "method", desc = "语音清单查询", summary = "语音清单查询")
    public VoiceUDRQueryResponse voiceUDRQuery(VoiceUDRQueryRequest request);
    
    @ZteSoftCommentAnnotation(type = "method", desc = "短信清单查询", summary = "短信清单查询")
    public SmsudrQueryResponse smsudrQuery(SmsudrQueryRequest request);
    
    @ZteSoftCommentAnnotation(type = "method", desc = "数据清单查询", summary = "数据清单查询")
    public DataUDRQueryResponse dataUDRQuery(DataUDRQueryRequest request);
    
    @ZteSoftCommentAnnotation(type = "method", desc = "增值业务清单查询", summary = "增值业务清单查询")
    public SpudrQueryResponse spudrQuery(SpudrQueryRequest request);
    
    @ZteSoftCommentAnnotation(type = "method", desc = "产品资料查询", summary = "产品资料查询")
    public QryProdInstResponse qryProdInst(QryProdInstRequest request);
    
    @ZteSoftCommentAnnotation(type = "method", desc = "增值订购关系查询", summary = "增值订购关系查询")
    public SubInstQryResponse subInstQry(SubInstQryRequest request);
    
    @ZteSoftCommentAnnotation(type = "method", desc = "产品列表查询", summary = "产品列表查询")
    public QryProdInstListResponse qryProdInstList(QryProdInstListRequest request);
    
    @ZteSoftCommentAnnotation(type = "method", desc = "充值记录查询", summary = "充值记录查询")
    public PaymentInformationResponse paymentInformationList(PaymentInformationRequest request);
    
    @ZteSoftCommentAnnotation(type = "method", desc = "订单列表查询", summary = "订单列表查询")
    public PreOrderResponse preOrderList(PreOrderRequest request);
}
