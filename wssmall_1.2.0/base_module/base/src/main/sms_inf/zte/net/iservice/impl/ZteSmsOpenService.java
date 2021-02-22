package zte.net.iservice.impl;

import com.ztesoft.api.spring.ApiContextHolder;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotation;
import com.ztesoft.remote.inf.ISmsService;
import com.ztesoft.remote.params.req.SmsAddRequest;
import com.ztesoft.remote.params.req.SmsInfoRequest;
import com.ztesoft.remote.params.req.SmsTempleteRequest;
import com.ztesoft.remote.params.resp.SmsAddResponse;
import com.ztesoft.remote.params.resp.SmsInfoResponse;
import com.ztesoft.remote.params.resp.SmsTempleteResponse;
import com.ztesoft.rop.annotation.NeedInSessionType;
import com.ztesoft.rop.annotation.ServiceMethod;
import com.ztesoft.rop.annotation.ServiceMethodBean;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2014-01-22 15:54
 * To change this template use File | Settings | File Templates.
 */
@ServiceMethodBean(version="1.0")
@ZteSoftCommentAnnotation(type="class",desc="短信",summary="短信API[发送]")
public class ZteSmsOpenService {

//    @Resource
    private ISmsService remoteSmsService;
    
    private void init() {
    	if (null == remoteSmsService) remoteSmsService = ApiContextHolder.getBean("remoteSmsService");
    }

    @ServiceMethod(method="com.ztesoft.remote.sms.send",version="1.0",needInSession= NeedInSessionType.NO,timeout = 600000)
    public SmsAddResponse sendSms(SmsAddRequest req) {
    	this.init();
        req.setRopRequestContext(null);
        return this.remoteSmsService.sendSms(req);
    }

    @ServiceMethod(method="com.ztesoft.remote.sms.sendTemplete",version="1.0",needInSession= NeedInSessionType.NO,timeout = 600000)
    public SmsTempleteResponse sendSms(SmsTempleteRequest req) {
    	this.init();
        req.setRopRequestContext(null);
        return this.remoteSmsService.sendSms(req);
    }

    @ServiceMethod(method="com.ztesoft.remote.sms.getById",version="1.0",needInSession= NeedInSessionType.NO,timeout = 600000)
    public SmsInfoResponse sendSms(SmsInfoRequest request) {
    	this.init();
        request.setRopRequestContext(null);
        return this.remoteSmsService.getSms(request);
    }
}
