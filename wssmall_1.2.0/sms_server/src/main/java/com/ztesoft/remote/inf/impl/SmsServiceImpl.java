package com.ztesoft.remote.inf.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;

import com.powerise.ibss.framework.Const;
import com.ztesoft.net.model.SendSms;
import com.ztesoft.net.sms.service.ISmsManager;
import com.ztesoft.remote.inf.ISmsService;
import com.ztesoft.remote.params.req.SmsAddRequest;
import com.ztesoft.remote.params.req.SmsInfoRequest;
import com.ztesoft.remote.params.req.SmsTempleteRequest;
import com.ztesoft.remote.params.resp.SmsAddResponse;
import com.ztesoft.remote.params.resp.SmsInfoResponse;
import com.ztesoft.remote.params.resp.SmsTempleteResponse;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2014-01-22 16:35
 * To change this template use File | Settings | File Templates.
 */
public class SmsServiceImpl implements ISmsService {

    @Resource
    private ISmsManager smsManager;

    @Override
    public SmsAddResponse sendSms(SmsAddRequest req) {
        SmsAddResponse response=new SmsAddResponse();
        SendSms sms=new SendSms();
        sms.setSend_content(req.getMsg());
        sms.setRecv_num(req.getAccNbr());
        sms.setAcct_num(req.getRandomCode());
       // String send_no=this.smsManager.save(req.getMsg(),req.getAccNbr());
        String send_no=this.smsManager.save(sms);
        response.setSendNo(send_no);
        if(StringUtils.isNotBlank(send_no)){
           response.setResult(true);
        }
        response.setError_code("0");
        response.setError_msg("发送短信成功!");
        return response;
    }

    @Override
    public SmsTempleteResponse sendSms(SmsTempleteRequest request) {
        SmsTempleteResponse response=new SmsTempleteResponse();
        try {
            SendSms sms=new SendSms();
            Map param = request.getParams();
            //类似  public SmsAddResponse sendSms(SmsAddRequest req) 一样，把随机码数字写到 acct_num 字段 --- by Musoon
            String randomCode = Const.getStrValue(param, "randCode");
            sms.setAcct_num(randomCode);
            //sms.setAcct_num(request.getAccNbr());
            sms.setRecv_num(request.getAccNbr());
            sms.setSend_content(this.smsManager.getSMSTemplate(request.getCode(), param));
            
            //add by chen.lijun 添加发送号码的信息，先前默认为10001,现在修改成传过来的号码
            if(StringUtils.isNotEmpty(Const.getStrValue(param, "send_num"))){
            	sms.setSend_num(Const.getStrValue(param, "send_num"));
            }
            
            String send_no=this.smsManager.save(sms);
            response.setResult(true);
            response.setError_code("0");
            response.setError_msg("发送短信成功!");
            response.setSendNo(send_no);
        } catch (RuntimeException e){
            e.printStackTrace();
            response.setError_code("-1");
            response.setError_msg("发送短信出错!");
        }
        return response;
    }

    @Override
    public SmsInfoResponse getSms(SmsInfoRequest request) {
        SmsInfoResponse response=new SmsInfoResponse();
        try{
            SendSms sms=this.smsManager.querySmsCodeById(request.getSend_no(),request.getTime());

            response.setResult(true);
            response.setSend_no(sms.getSend_no());
            response.setAcct_num(sms.getAcct_num());
            response.setRecv_num(sms.getRecv_num());
            response.setRandomCode(sms.getAcct_num());
            response.setSend_content(sms.getSend_content());
            response.setState(sms.getState());
            response.setSend_count(sms.getSend_count());
            response.setSend_date(sms.getSend_date());
        }catch (RuntimeException e){
            e.printStackTrace();
            response.setError_msg("-1");
            response.setError_msg("获取短信内容出错!");
        }

        return response;
    }
}
