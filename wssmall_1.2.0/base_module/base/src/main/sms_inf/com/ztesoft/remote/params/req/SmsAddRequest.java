package com.ztesoft.remote.params.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.api.internal.utils.ApiUtils;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.remote.params.resp.SmsAddResponse;
import params.ZteRequest;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2014-01-22 16:00
 * To change this template use File | Settings | File Templates.
 */
public class SmsAddRequest extends ZteRequest<SmsAddResponse> {
    @ZteSoftCommentAnnotationParam(name="消息内容",type="String",isNecessary="Y",desc="消息内容")
    private String msg;

    @ZteSoftCommentAnnotationParam(name="号码",type="String",isNecessary="Y",desc="号码")
    private String accNbr;

    @ZteSoftCommentAnnotationParam(name="随机码",type="String",isNecessary="Y",desc="随机码")
    private String randomCode;


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getAccNbr() {
        return accNbr;
    }

    public void setAccNbr(String accNbr) {
        this.accNbr = accNbr;
    }

    public String getRandomCode() {
        return randomCode;
    }

    public void setRandomCode(String randomCode) {
        this.randomCode = randomCode;
    }

    @Override
    public void check() throws ApiRuleException {
        if(ApiUtils.isBlank(accNbr) || ApiUtils.isBlank(msg)){
            throw new ApiRuleException("-1","短信内容或号码,不能为空!");
        }
    }

    @Override
    public String getApiMethodName() {
        return "com.ztesoft.remote.sms.send";
    }
}
