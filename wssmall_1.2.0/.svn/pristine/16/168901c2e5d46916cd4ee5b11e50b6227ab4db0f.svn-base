package com.ztesoft.remote.basic.inf;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotation;
import com.ztesoft.remote.basic.params.req.*;
import com.ztesoft.remote.basic.params.resp.*;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2014-02-11 11:04
 * To change this template use File | Settings | File Templates.
 * <p/>
 * 充值卡类服务基础，充值卡 流量卡
 */
@ZteSoftCommentAnnotation(type = "class", desc = "充值卡,流量卡API", summary = "充值卡,流量卡API[]")
public interface IChargeBasicService {

    @ZteSoftCommentAnnotation(type = "metod", desc = "充值卡", summary = "充值卡")
    public RechargeCardResponse cardRecharge(RechargeCardRequest request);

    @ZteSoftCommentAnnotation(type = "metod", desc = "流量卡", summary = "流量卡")
    public CardTrafficReponse cardTraffic(CardTrafficRequest request);


    @ZteSoftCommentAnnotation(type = "metod", desc = "充值", summary = "充值")
    public ChargeFeeResponse chargeFee(ChargeFeeRequest request);

    @ZteSoftCommentAnnotation(type = "metod", desc = "充流量", summary = "充流量")
    public ChargeFlowResponse chargeFlow(ChargeFlowRequest request);

    @ZteSoftCommentAnnotation(type = "metod", desc = "充钱包", summary = "充钱包")
    public ChargeWalletResponse chargeWallet(ChargeWalletRequest request);

    @ZteSoftCommentAnnotation(type = "metod", desc = "缴费充值快捷", summary = "缴费充值快捷")
    public QuickChargeFeeResponse  quickChargeFee(QuickChargeFeeRequest request);
    
    @ZteSoftCommentAnnotation(type = "metod", desc = "LBS充值接口", summary = "LBS充值接口")
    public LbsRechargeResp  lbsRecharge(LbsRechargeReq request);
}
