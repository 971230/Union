package com.ztesoft.remote.params.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.api.internal.utils.ApiUtils;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.remote.params.resp.SmsInfoResponse;
import params.ZteRequest;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2014-03-11 10:11
 * To change this template use File | Settings | File Templates.
 */
public class SmsInfoRequest extends ZteRequest<SmsInfoResponse>{
    @ZteSoftCommentAnnotationParam(name="短信编码",type="String",isNecessary="Y",desc="短信编码")
    private String send_no;

    @ZteSoftCommentAnnotationParam(name="时间秒",type="String",isNecessary="Y",desc="获取时间内的短信信息")
    private int time=0;


    @Override
    public void check() throws ApiRuleException {
        if(ApiUtils.isBlank(send_no)){
            throw new ApiRuleException("-1","短信编码不能为空!");
        }
    }

    @Override
    public String getApiMethodName() {
        return "com.ztesoft.remote.sms.getById";
    }

    public String getSend_no() {
        return send_no;
    }

    public void setSend_no(String send_no) {
        this.send_no = send_no;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
