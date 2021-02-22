package services;

import java.util.List;

import params.adminuser.req.SmsActNumReq;
import params.adminuser.req.SmsRandCodeReq;
import params.adminuser.resp.SmsActNumResp;
import params.adminuser.resp.SmsRandCodeResp;

/**
 * 登录验证接口
 * @author hu.yi
 * @date 2013.12.23
 */
public interface SmsCodeInf {

	
	public static final String SESSION_SMS_CODE = "SESSION_SMS_CODE";
	public static final String SESSION_SMS_CODE_APPLY_TIME = "SESSION_SMS_CODE_APPLY_TIME";
	public static final String SMS_RANDOM_CODE = "SMS_RANDOM_CODE";//短信模板
	public static final String TMP_ADMIN_USER = "TMP_ADMIN_USER";
	public static final String LOGIN_SUCESS_MSG = "LOGIN_SUCCESS_MSG";
	
	
	/**
	 * 获取验证码
	 * @return
	 */
	public SmsRandCodeResp createRandCode(SmsRandCodeReq smsRandCodeReq);
	
	/**
	 * 获取短信
	 * @return
	 */
	public SmsActNumResp querySmsById(SmsActNumReq smsActNumReq,String sendNo);
	
	/**
	 * 删除短信
	 * @return
	 */
	public boolean deleteSms(List<String> list);
}
