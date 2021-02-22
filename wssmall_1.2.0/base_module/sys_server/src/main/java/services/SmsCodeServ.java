package services;

import java.util.List;

import params.adminuser.req.SmsActNumReq;
import params.adminuser.req.SmsRandCodeReq;
import params.adminuser.resp.SmsActNumResp;
import params.adminuser.resp.SmsRandCodeResp;

import com.ztesoft.net.mall.core.service.ISmsCode;
import com.ztesoft.net.model.SendSms;


public class SmsCodeServ extends ServiceBase implements SmsCodeInf{
	
	private ISmsCode smsCode;
	
	
	@Override
	public SmsRandCodeResp createRandCode(SmsRandCodeReq smsRandCodeReq){
		
		String code = smsCode.createRandCode();
		SmsRandCodeResp smsRandCodeResp = new SmsRandCodeResp();
		smsRandCodeResp.setRandCode(code);
		addReturn(smsRandCodeReq, smsRandCodeResp);
		return smsRandCodeResp;
	}
	
	@Override
	public SmsActNumResp querySmsById(SmsActNumReq smsActNumReq,String sendNo) {
		SendSms sendSms = smsCode.querySmsById(sendNo);
		SmsActNumResp smsActNumResp = new SmsActNumResp();
		if(null!=sendSms){
			smsActNumResp.setActNum(sendSms.getAcct_num());
			addReturn(smsActNumReq, smsActNumResp);
		}
		return smsActNumResp;
	}
	
	public ISmsCode getSmsCode() {
		return smsCode;
	}

	public void setSmsCode(ISmsCode smsCode) {
		this.smsCode = smsCode;
	}

	@Override
	public boolean deleteSms(List<String> list) {
		return smsCode.deleteSms(list);
	}
}