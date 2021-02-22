package com.ztesoft.remote.params.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.api.internal.utils.ApiUtils;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.remote.params.resp.SmsTempleteResponse;
import params.ZteRequest;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2014-03-11 09:55
 * To change this template use File | Settings | File Templates.
 * 按照模板发送短信
 */
public class SmsTempleteRequest extends ZteRequest<SmsTempleteResponse> {
    @ZteSoftCommentAnnotationParam(name = "模板编码", type = "String", isNecessary = "Y", desc = "模板编码")
    private String code;

    @ZteSoftCommentAnnotationParam(name = "号码", type = "String", isNecessary = "Y", desc = "号码")
    private String accNbr;

    @ZteSoftCommentAnnotationParam(name = "参数", type = "String", isNecessary = "Y", desc = "参数")
    private Map params;

    @Override
    public void check() throws ApiRuleException {
        if (ApiUtils.isBlank(code)) {
            throw new ApiRuleException("-1", "短信模板编码不能为空!");
        }
    }

    @Override
    public String getApiMethodName() {
        return "com.ztesoft.remote.sms.sendTemplete";
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAccNbr() {
        return accNbr;
    }

    public void setAccNbr(String accNbr) {
        this.accNbr = accNbr;
    }

    public Map getParams() {
        return params;
    }

    public void setParams(Map params) {
        this.params = params;
    }
}
