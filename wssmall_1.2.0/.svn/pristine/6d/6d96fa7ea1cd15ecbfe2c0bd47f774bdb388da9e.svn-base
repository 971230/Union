package com.ztesoft.remote.inf;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotation;
import com.ztesoft.remote.params.req.SmsAddRequest;
import com.ztesoft.remote.params.req.SmsInfoRequest;
import com.ztesoft.remote.params.req.SmsTempleteRequest;
import com.ztesoft.remote.params.resp.SmsAddResponse;
import com.ztesoft.remote.params.resp.SmsInfoResponse;
import com.ztesoft.remote.params.resp.SmsTempleteResponse;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2014-01-22 16:32
 * To change this template use File | Settings | File Templates.
 */
@ZteSoftCommentAnnotation(type = "class", desc = "短信API", summary = "短信API")
public interface ISmsService {
    @ZteSoftCommentAnnotation(type = "method", desc = "短信", summary = "短信")
    public SmsAddResponse sendSms(SmsAddRequest req);

    @ZteSoftCommentAnnotation(type = "method", desc = "模板发送短信", summary = "模板发送短信")
    public SmsTempleteResponse sendSms(SmsTempleteRequest request);

    @ZteSoftCommentAnnotation(type = "method", desc = "获取短信内容", summary = "获取短信内容")
    public SmsInfoResponse getSms(SmsInfoRequest request);
}
