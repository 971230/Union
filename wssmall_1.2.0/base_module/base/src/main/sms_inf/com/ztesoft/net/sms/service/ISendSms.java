package com.ztesoft.net.sms.service;

import com.ztesoft.net.model.SendSms;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2013-10-10 09:20
 * To change this template use File | Settings | File Templates.
 */
public interface ISendSms {
    public static final String SMS10001 = "ESB.SMS10001";


    public boolean sendSms(SendSms sms);


}
