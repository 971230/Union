package com.ztesoft.net.service.impl;


import org.apache.log4j.Logger;

import zte.net.ecsord.params.ecaop.req.AopSmsSendReq;
import zte.net.ecsord.params.ecaop.resp.AopSmsSendResp;

import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.model.SendSms;
import com.ztesoft.net.sms.service.ISendSms;

import consts.ConstsCore;

/**
 * @Description 通过AOP接口发送短信
 * @author  zhangJun
 * @date    2017年1月3日
 */
public class ZJSendSmsImpl implements ISendSms {
    protected static Logger logger = Logger.getLogger(ZJSendSmsImpl.class);

    @Override
    public boolean sendSms(SendSms sms) {
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		AopSmsSendReq smsSendReq = new AopSmsSendReq();
		smsSendReq.setService_num(sms.getRecv_num());//接收号码
		smsSendReq.setSms_data(sms.getSend_content());
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		String bill_num=cacheUtil.getConfigInfo("SMS_BILL_NUM");
		smsSendReq.setBill_num(bill_num);//发送号码
		AopSmsSendResp resp = client.execute(smsSendReq, AopSmsSendResp.class);
		if(resp!=null&&ConstsCore.ERROR_SUCC.equals(resp.getError_code())){
			return true;
		}
		return false;
    }
}
