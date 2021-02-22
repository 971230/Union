package com.ztesoft.net.mall.core.service;

import com.ztesoft.api.spring.ApiContextHolder;
import com.ztesoft.net.model.SendSms;
import com.ztesoft.remote.inf.IRemoteSmsService;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbsLoginCheck implements ISmsCode {

	private IRemoteSmsService localRemoteSmsService;
	private static Logger logger = Logger.getLogger(AbsLoginCheck.class);
	private void init(){
		if(null == localRemoteSmsService) localRemoteSmsService = ApiContextHolder.getBean("localRemoteSmsService");
	}
	
	@Override
	public String createRandCode() {
		String randcode = String.valueOf(Math.random());
		randcode = randcode.substring(randcode.length()-6);
		logger.info("验证码：="+randcode);
		return randcode;
	}

	@Override
	public boolean checkSmsCode(HttpSession session,String sms_code) {
		String session_code = (String) session.getAttribute(SESSION_SMS_CODE);
		if(sms_code==null){
			return false;
		}else{
			return sms_code.equals(session_code);
		}
	}

	@Override
	public void sendSmsCode(String mobile, String randCode) {
		Map map = new HashMap();
		map.put("randCode", randCode);
		//String smsContent = smsManager.getSMSTemplate(SMS_RANDOM_CODE, map);
		//logger.info(smsContent);
		//smsManager.save(smsContent, mobile);`
		sendMsg(mobile, map, SMS_RANDOM_CODE);
	}
	@Override
	public void sendMsg(String mobile,Map data,String tb_key){
		//初始化beans
		init();
		String smsContent = this.localRemoteSmsService.getSMSTemplate(tb_key, data);
		this.localRemoteSmsService.save(smsContent, mobile);
	}
	
	@Override
	public String sendSms(String mobile, String randCode) {
		//初始化beans
		init();
		String smsContent = getSmsContent(randCode);
		return this.localRemoteSmsService.save(smsContent, mobile, randCode);
	}
	
	@Override
	public abstract boolean checkUser(String username,String password);
	
	@Override
	public String getSmsContent(String randCode) {
		//初始化beans
		init();
		Map map = new HashMap();
		map.put("randCode", randCode);
		return this.localRemoteSmsService.getSMSTemplate(SMS_RANDOM_CODE, map);
	}

	@Override
	public boolean updateState(String sendNo) {
		//初始化beans
		init();
		return this.localRemoteSmsService.updateState(sendNo);
	}
	
	@Override
	public SendSms querySmsById(String sendNo) {
		//初始化beans
		init();
		return this.localRemoteSmsService.querySmsById(sendNo);
	}
	
	@Override
	public boolean deleteSms(List<String> list) {
		//初始化beans
		init();
		return  this.localRemoteSmsService.deleteSms(list);
	}
	
}
