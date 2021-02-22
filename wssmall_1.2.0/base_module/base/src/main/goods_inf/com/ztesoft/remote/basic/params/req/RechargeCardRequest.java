package com.ztesoft.remote.basic.params.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.remote.basic.params.resp.RechargeCardResponse;
import com.ztesoft.remote.basic.utils.BasicConst;
import params.ZteRequest;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2014-02-11 17:43
 * To change this template use File | Settings | File Templates.
 */
public class RechargeCardRequest extends ZteRequest<RechargeCardResponse> {

    @ZteSoftCommentAnnotationParam(name="卡号",type="String",isNecessary="Y",desc="卡号")
    private String cardNo;

    @ZteSoftCommentAnnotationParam(name="卡号密码",type="String",isNecessary="Y",desc="卡号密码")
    private String cardPwd;

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getCardPwd() {
        return cardPwd;
    }

    public void setCardPwd(String cardPwd) {
        this.cardPwd = cardPwd;
    }

    @Override
    public void check() throws ApiRuleException {

    }

    @Override
    public String getApiMethodName() {
        return BasicConst.API_PREFIX+"cardRecharge";
    }
}
