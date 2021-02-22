package com.ztesoft.net.mall.core.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.model.SendSms;


/**
 * 获取短信验证码
 * @作者 MoChunrun
 * @创建日期 2013-10-30 
 * @版本 V 1.0
 */
public interface ISmsCode {
	
	public static final String SESSION_SMS_CODE = "SESSION_SMS_CODE";
	public static final String SESSION_SMS_CODE_APPLY_TIME = "SESSION_SMS_CODE_APPLY_TIME";
	public static final String SMS_RANDOM_CODE = "SMS_RANDOM_CODE";//短信模板
	public static final String TMP_ADMIN_USER = "TMP_ADMIN_USER";
	public static final String LOGIN_SUCESS_MSG = "LOGIN_SUCCESS_MSG";
	public static final String SMS_OPEN_ACCT_SUCC = "SMS_OPEN_ACCT_SUCC";
	public static final String SMS_SEND_NO = "SMS_SEND_NO";
	
	
	/**
	 * 生成随机码
	 * @作者 MoChunrun
	 * @创建日期 2013-11-2 
	 * @return
	 */
	public String createRandCode();
	
	public boolean checkSmsCode(HttpSession session,String sms_code);
	
	public void sendSmsCode(String mobile,String randCode);
	
	public String sendSms(String mobile,String randCode);
	
	public void sendMsg(String mobile,Map data,String tb_key);
	
	public boolean checkUser(String username,String password);
	
	/**
	 * 是否允许同时在线，false不允许 true允许
	 * @作者 MoChunrun
	 * @创建日期 2013-11-4 
	 * @return
	 */
	public boolean isMultiOnline();
	
	/**
	 * 分销商是否锁定
	 * @作者 MoChunrun
	 * @创建日期 2013-11-6 
	 * @param admin
	 * @return
	 */
	public boolean partenerIsLock(AdminUser admin);
	/**
	 * 分销商是否注消
	 * @作者 MoChunrun
	 * @创建日期 2013-11-6 
	 * @param admin
	 * @return
	 */
	public boolean partenerIsLogAble(AdminUser admin);
	
	/**
	 * 获取短信内容
	 * @作者 zengxianlian
	 * @创建日期 2015-06-29
	 * @param randCode
	 * @return
	 */
	public String getSmsContent(String randCode);
	
	/**
	 * 修改短信状态
	 * @作者 zengxianlian
	 * @创建日期 2015-06-29
	 * @param sendNo
	 * @return
	 */
	public boolean updateState(String sendNo);
	
	/**
	 * 修改短信状态
	 * @作者 zengxianlian
	 * @创建日期 2015-06-29
	 * @param sendNo
	 * @return
	 */
	public SendSms querySmsById(String sendNo);
	
	/**
	 * 删除短信
	 * @作者 zengxianlian
	 * @创建日期 2015-06-29
	 * @param sendNo
	 * @return
	 */
	public boolean deleteSms(List<String> list);
	
}
