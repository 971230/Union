package com.ztesoft.remote.inf.impl;

import com.ztesoft.net.model.SendSms;
import com.ztesoft.net.sms.service.ISmsManager;
import com.ztesoft.remote.inf.IRemoteSmsService;

import javax.annotation.Resource;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2014-01-23 15:01
 * To change this template use File | Settings | File Templates.
 */
public class RemoteSmsServiceImpl implements IRemoteSmsService {

    @Resource
    private ISmsManager smsManager;

    @Override
    public String getSMSTemplate(String key, Map param) {
        return this.smsManager.getSMSTemplate(key,param);
    }

    @Override
    public String save(SendSms sms) {
        return this.smsManager.save(sms);
    }

    @Override
    public String save(String msgStr, String acc_nbr) {
        return this.smsManager.save(msgStr,acc_nbr);
    }
    
    @Override
    public String save(String msgStr, String acc_nbr, String code) {
        return this.smsManager.save(msgStr,acc_nbr, code);
    }

    @Override
    public boolean updateState(String sendNo) {
    	 return this.smsManager.updateState(sendNo);
    }
    
    @Override
    public SendSms querySmsById(String sendNo) {
    	return this.smsManager.querySmsById(sendNo);
    }

	@Override
	public boolean deleteSms(List<String> list) {
		return this.smsManager.delete(list);
	}
}
