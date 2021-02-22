package com.ztesoft.remote.basic.params.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.api.internal.utils.ApiUtils;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.remote.basic.params.resp.ChargeFlowResponse;
import com.ztesoft.remote.basic.utils.BasicConst;
import params.ZteRequest;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2014-02-13 11:15
 * To change this template use File | Settings | File Templates.
 */
public class ChargeFlowRequest extends ZteRequest<ChargeFlowResponse> {
    @ZteSoftCommentAnnotationParam(name="号码",type="String",isNecessary="Y",desc="号码")
    private String accNbr;


    @ZteSoftCommentAnnotationParam(name="流量包金额",type="long",isNecessary="Y",desc="流量包金额")
    private long chargeMoney=0;

    public String getAccNbr() {
        return accNbr;
    }

    public void setAccNbr(String accNbr) {
        this.accNbr = accNbr;
    }

    public long getChargeMoney() {
        return chargeMoney;
    }

    public void setChargeMoney(long chargeMoney) {
        this.chargeMoney = chargeMoney;
    }

    @Override
    public void check() throws ApiRuleException {
        if(ApiUtils.isBlank(accNbr)){
            throw new ApiRuleException("-1","充值号码不能为空!");
        }

        if(chargeMoney==0){
            throw new ApiRuleException("-1","流量包金额不合法!");
        }
    }

    @Override
    public String getApiMethodName() {
        return BasicConst.API_PREFIX+"chargeFlow";
    }
}
