package com.ztesoft.net.eop.processor.backend;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.axis.utils.StringUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;

import params.adminuser.req.AdminUserLoginReq;
import services.AdminUserInf;
import com.ztesoft.common.util.DesEncrypt;
import com.ztesoft.common.util.EncryptUtil;
import com.ztesoft.net.app.base.core.service.auth.IAppPermissionManager;
import com.ztesoft.net.consts.MblConsts;
import com.ztesoft.net.eop.processor.Processor;
import com.ztesoft.net.eop.processor.core.EopException;
import com.ztesoft.net.eop.processor.core.Response;
import com.ztesoft.net.eop.processor.core.StringResponse;
import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.eop.sdk.utils.ValidCodeServlet;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.context.webcontext.ThreadContextHolder;
import com.ztesoft.net.framework.context.webcontext.WebSessionContext;
import com.ztesoft.net.framework.util.EncryptionUtil;
import com.ztesoft.net.framework.util.HttpUtil;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.service.ISmsCode;
import com.ztesoft.net.mall.core.utils.CacheUtil;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.remote.pojo.AppInfo;

/**
 * 用户登录处理器
 * @author kingapex
 * <p>2009-12-16 上午10:20:33</p>
 * @version 1.0
 */
public class LoginProcessor implements Processor {

	protected final Logger logger = Logger.getLogger(getClass());
	
	/**
	 * 进行用户登录操作<br/>
	 * 如果登录成功，则生成应用的domain json供SSO的javascript使用
	 */
	@Override
	@SuppressWarnings("unchecked")
	
	public Response process(int mode,  HttpServletResponse httpResponse,
			HttpServletRequest httpRequest) {
		String type = httpRequest.getParameter("type");
		if(type==null || "".equals(type)){
			return this.userLogin(httpResponse, httpRequest);
		}else if(type!=null && type.equals("singleLogin") ){
			return this.singleLogin(httpResponse, httpRequest);
		}else if(type != null && type.equals("smsLogin")){
			return this.smsLogin(httpResponse, httpRequest);
		}else if(type != null && type.equals("singleLogin1")){
			return this.singleLogin1(httpResponse, httpRequest);
		}else {
			return this.sysLogin(httpResponse, httpRequest);
		}
		
	
	}
	
	/**
	 * 平台切换，单点登录
	 * @param httpResponse
	 * @param httpRequest
	 * @return
	 */
	public Response singleLogin(HttpServletResponse httpResponse,
			HttpServletRequest httpRequest){
		Response response = new StringResponse();
		try {
			String username = httpRequest.getParameter("username");
			String password = httpRequest.getParameter("password");
			//获取当前应用信息
			String app_info = httpRequest.getParameter("app_info");
			JSONObject app = JSONObject.fromObject(app_info);
			AppInfo appInfo = (AppInfo)JSONObject.toBean(app, AppInfo.class);
			
			WebSessionContext sessonContext = ThreadContextHolder.getSessionContext();
			sessonContext.setAttribute(MblConsts.CURRENT_APP_INFO, appInfo);		//存储当前应用信息
			
			//登录校验 
			AdminUserLoginReq adminUserLoginReq = new AdminUserLoginReq();
			adminUserLoginReq.setUsername(username);
			adminUserLoginReq.setPassword(password);
			AdminUserInf adminUserServ = SpringContextHolder.getBean("adminUserServ");
			String result = adminUserServ.loginBySys(adminUserLoginReq).getResult();
			
			if(result.equals("1")){
				response.setContent( "<script>location.href='main.jsp'</script>正在转向后台...");
			}else{
				response.setContent("{'result':1,'message':'登录失败：用户名或密码错误'}");
			}
			return response;
			
		} catch (EopException exception) {
			response.setContent("{'result':1,'message':'"+exception.getMessage()+"'}");
			return response;
		}
	}
	
	/**
	 * 系统跳转登录后台
	 * @param httpResponse
	 * @param httpRequest
	 * @return
	 */
	public Response sysLogin(HttpServletResponse httpResponse,
			HttpServletRequest httpRequest){
		Response response = new StringResponse();
		
		String endata =  httpRequest.getParameter("s");
		if(endata==null){ response.setContent("非法数据"); return response;}
		
		endata =  EncryptionUtil.authCode(endata, "DECODE");
		String[] ar = endata.split(",");
		//if(ar==null||ar.length!=3){ response.setContent("非法数据"); return response;}
		
		String username = ar [0];
		String password = ar [1];

		 
		try {
			
			/*
			 * 登录校验
			 */
			
			AdminUserLoginReq adminUserLoginReq = new AdminUserLoginReq();
			adminUserLoginReq.setUsername(username);
			adminUserLoginReq.setPassword(password);
			AdminUserInf adminUserServ = SpringContextHolder.getBean("adminUserServ");
			String result = adminUserServ.login(adminUserLoginReq).getResult();
			
//			IAdminUserManager userManager =SpringContextHolder.getBean("adminUserManager");
//			String result = userManager.loginBySys(username, password);
			if(result.equals("1")){
				response.setContent( "<script>location.href='main.jsp'</script>正在转向后台...");
			}else{
				response.setContent("{'result':1,'message':'登录失败：用户名或密码错误'}");
			}
			return response;
			
		} catch (EopException exception) {
			
			response.setContent("{'result':1,'message':'"+exception.getMessage()+"'}");
			return response;
		}	 
		
	}
	
	/**
	 * 用户手工登录后台
	 * @param httpResponse
	 * @param httpRequest
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Response userLogin(HttpServletResponse httpResponse, HttpServletRequest httpRequest) {
		
		//验证码是否校验标识，如果没配置有值，则在登录时做验证码校验；如果配置有值，则不做校验。主要是压力测试时跳过验证码、验证码出问题时屏蔽掉验证码校验这一关---add by Musoon
		CacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		String validCodeFlag = cacheUtil.getConfigInfo(Consts.VALID_CODE_FLAG);
		EncryptUtil encryptUtil = SpringContextHolder.getBean("encryptUtil");
		String username = httpRequest.getParameter("username");
		String password = httpRequest.getParameter("password");
		if(ManagerUtils.getSourceFrom().equals("ECS") || ManagerUtils.getSourceFrom().equals("ECSORD")){
			//明文解密。
//			String encryptionLength = httpRequest.getParameter("encryptionLength");
//			int pwdLen = Integer.valueOf(encryptionLength);
			username = (String) encryptUtil.decrypt(username); 
			password = (String) encryptUtil.decrypt(password); 
			DesEncrypt encrypt = new DesEncrypt();
			password =  encrypt.decrypt(password);
//			password = password.substring(0, pwdLen);
		}
		
		String valid_code = httpRequest.getParameter("valid_code");
		if("".equals(username)){
			Response response = new StringResponse();
			String json =  "{'result':-1,'message':'用户名不能为空！'}";
			response.setContent( json.toString() );
			return response;
		}
		if("".equals(password)){
			Response response = new StringResponse();
			String json =  "{'result':-1,'message':'密码不能为空！'}";
			response.setContent( json.toString() );
			return response;
		}	
		
		if (StringUtils.isEmpty(validCodeFlag) && "".equals(valid_code)) {
			Response response = new StringResponse();
			String json =  "{'result':-1,'message':'验证码不能为空！'}";
			response.setContent( json.toString() );
			return response;
		}
		WebSessionContext sessonContext = ThreadContextHolder.getHttpSessionContext();
		
		if (null == sessonContext) {
			logger.info("sessonContext is null ...... ");
		} else {
			logger.info("sessonContext is not null ...... ");
		}
		
		//获取当前应用信息
		AppInfo appInfo = new AppInfo();
		String app_info = httpRequest.getParameter("app_info");
//		AppInfo appInfo =CommonTools.jsonToBean(app_info, AppInfo.class);
		if(!StringUtil.isEmpty(app_info)){
			String []aps = app_info.split(","); 
			try {
				for (int i = 0; i < aps.length; i++) {
					String [] apss = aps[i].split(":");
					if("appaddress".equalsIgnoreCase(apss[0])){
						BeanUtils.setProperty(appInfo,apss[0],apss[1]+":"+apss[2]) ;
					}else{
						String v = apss[1];
						String k = apss[0];
						if(v.indexOf("}")!=-1){
							v = v.substring(0,v.length()-1);
						}
						if((k.indexOf("{"))!=-1){
							k = k.substring(1,k.length());
						}
						if(k.indexOf("\"")>-1)
							k = k.substring(1, k.length()-1);
						if(v.indexOf("\"")>-1)
							v = v.substring(1, v.length()-1);
						BeanUtils.setProperty(appInfo,k,v);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			appInfo = (AppInfo) sessonContext.getAttribute(MblConsts.CURRENT_APP_INFO);
		}
		
		logger.info("app_info==>" + app_info);
		//应用访问权限校验
		IAppPermissionManager appPermissionManager =SpringContextHolder.getBean("appPermissionManager");
		List<AppInfo> appInfoList = appPermissionManager.getUserAppList(username);
		boolean appInfoFlag=false;
		if(appInfoList!=null&&!appInfoList.isEmpty()){
			for(AppInfo info :appInfoList){
				if(info.getAppId()==appInfo.getAppId()){
					appInfoFlag=true;
					break;
				}
			}
		}
		if(!appInfoFlag){
			Response response = new StringResponse();
			String json =  "{'result':-1,'message':'没有访问应用的权限！'}";
			response.setContent( json.toString() );
			return response;
		}
		try {
			/*
			 * 校验验证码
			 */
			if (StringUtils.isEmpty(validCodeFlag) && valid_code == null) throw new EopException("验证码输入错误");	
			
			sessonContext.setAttribute(MblConsts.CURRENT_APP_INFO, appInfo);		//存储当前应用信息
			Object realCode = ThreadContextHolder.getHttpSessionContext().getAttribute(ValidCodeServlet.SESSION_VALID_CODE+"admin");
			
			Object p_realCode = ThreadContextHolder.getHttpSessionContext().getAttribute(ValidCodeServlet.SESSION_VALID_CODE);
			
			logger.info(realCode+":"+p_realCode+":====================================:"+valid_code);
			
		
			if (StringUtils.isEmpty(validCodeFlag) && ! ((String)realCode).toLowerCase().equals((valid_code).toLowerCase())) {
				ThreadContextHolder.getHttpSessionContext().setAttribute(ValidCodeServlet.SESSION_VALID_CODE+"admin","");
				throw new EopException("验证码输入错误");
			}
			
			/*
			 * 短信验证码校验
			 */
			/*
			if(valid_code==null) throw new EopException("验证码输入错误");	
			ISmsManager smsManager=SpringContextHolder.getBean("smsManager");
			IAdminUserManager userManager =SpringContextHolder.getBean("adminUserManager");
			AdminUser user=userManager.getAdminUserByUserName(username);
			if (user!=null) {
				if(!smsManager.checkSmsByRecvNum(user.getPhone_num(), valid_code)){
					throw new EopException("验证码输入错误");
				}
			}
			*/
			/*
			 * 登录校验
			 */
			
			AdminUserLoginReq adminUserLoginReq = new AdminUserLoginReq();
			adminUserLoginReq.setUsername(username);
			adminUserLoginReq.setPassword(password);
			AdminUserInf adminUserServ = SpringContextHolder.getBean("adminUserServ");
			String result = adminUserServ.login(adminUserLoginReq).getResult();
//			IAdminUserManager userManager =SpringContextHolder.getBean("adminUserManager");
//			userManager.login(username, password);
//          
			if("1".equals(result))
				ThreadContextHolder.getHttpSessionContext().setAttribute(ValidCodeServlet.SESSION_VALID_CODE+"admin","");
			StringBuffer json = new StringBuffer();
			AdminUser user = ManagerUtils.getAdminUser();
			String  is_login_first=user.getIs_login_first();
			if(EcsOrderConsts.IS_LOGIN_FIRST_0.equals(is_login_first)){
				json.append("{'result':2}");
			}else{
				json.append("{'result':0}");
			}
						
			Response response = new StringResponse();
			response.setContent( json.toString() );
			
			String remember_login_name = httpRequest.getParameter("remember_login_name");
			if(!StringUtil.isEmpty(remember_login_name)){ //记住用户名
				HttpUtil.addCookie(httpResponse, "loginname", username, 365*24*60*60);
			}else{  //删除用户名
				HttpUtil.addCookie(httpResponse, "loginname", "", 0);
			}
			
			
			return response;
			 
		} catch (RuntimeException exception) {
			ThreadContextHolder.getHttpSessionContext().setAttribute(ValidCodeServlet.SESSION_VALID_CODE+"admin","");
			exception.printStackTrace();
			this.logger.info(exception.getMessage(),exception.fillInStackTrace());
			Response response = new StringResponse();
			response.setContent("{'result':1,'message':'"+exception.getMessage()+"'}");
			return response;
		}	 
	}
	
	/**
	 * 平台切换，单点登录
	 * @param httpResponse
	 * @param httpRequest
	 * @return
	 */
	public Response smsLogin(HttpServletResponse httpResponse, HttpServletRequest httpRequest) {
		//验证码是否校验标识，如果没配置有值，则在登录时做验证码校验；如果配置有值，则不做校验。主要是压力测试时跳过验证码、验证码出问题时屏蔽掉验证码校验这一关---add by Musoon
		CacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		String validCodeFlag = cacheUtil.getConfigInfo(Consts.VALID_CODE_FLAG);
		EncryptUtil encryptUtil = SpringContextHolder.getBean("encryptUtil");
		String username = httpRequest.getParameter("username");
		String password = httpRequest.getParameter("password");
				
		if(ManagerUtils.getSourceFrom().equals("ECS") || ManagerUtils.getSourceFrom().equals("ECSORD")){
			//明文解密。
			username = (String) encryptUtil.decrypt(username); 
			password = (String) encryptUtil.decrypt(password); 

			DesEncrypt encrypt = new DesEncrypt();
			password =  encrypt.decrypt(password);
		}
				
				String valid_code = httpRequest.getParameter("valid_code");
				if("".equals(username)){
					Response response = new StringResponse();
					String json =  "{'result':-1,'message':'用户名不能为空！'}";
					response.setContent( json.toString() );
					return response;
				}
				if("".equals(password)){
					Response response = new StringResponse();
					String json =  "{'result':-1,'message':'密码不能为空！'}";
					response.setContent( json.toString() );
					return response;
				}	
				
				/*if (StringUtils.isEmpty(validCodeFlag) && "".equals(valid_code)) {
					Response response = new StringResponse();
					String json =  "{'result':-1,'message':'验证码不能为空！'}";
					response.setContent( json.toString() );
					return response;
				}	*/		
				WebSessionContext sessonContext = ThreadContextHolder.getHttpSessionContext();
				
				if (null == sessonContext) {
					logger.info("sessonContext is null ...... ");
				} else {
					logger.info("sessonContext is not null ...... ");
				}
				
				//获取当前应用信息
				AppInfo appInfo = new AppInfo();
				String app_info = httpRequest.getParameter("app_info");
//				AppInfo appInfo =CommonTools.jsonToBean(app_info, AppInfo.class);
				if(!StringUtil.isEmpty(app_info)){
					String []aps = app_info.split(","); 
					try {
						for (int i = 0; i < aps.length; i++) {
							String [] apss = aps[i].split(":");
							if("appaddress".equalsIgnoreCase(apss[0])){
								BeanUtils.setProperty(appInfo,apss[0],apss[1]+":"+apss[2]) ;
							}else{
								String v = apss[1];
								String k = apss[0];
								if(v.indexOf("}")!=-1){
									v = v.substring(0,v.length()-1);
								}
								if((k.indexOf("{"))!=-1){
									k = k.substring(1,k.length());
								}
								if(k.indexOf("\"")>-1)
									k = k.substring(1, k.length()-1);
								if(v.indexOf("\"")>-1)
									v = v.substring(1, v.length()-1);
								BeanUtils.setProperty(appInfo,k,v);
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}else{
					appInfo = (AppInfo) sessonContext.getAttribute(MblConsts.CURRENT_APP_INFO);
				}
				
				logger.info("app_info==>" + app_info);
				
				try {
					/*
					 * 校验验证码
					 */
					/*if (StringUtils.isEmpty(validCodeFlag) && valid_code == null) throw new EopException("验证码输入错误");	*/
					
					sessonContext.setAttribute(MblConsts.CURRENT_APP_INFO, appInfo);		//存储当前应用信息
					/*Object obj = sessonContext.getAttribute(ISmsCode.SMS_SEND_NO);
					
					if (null == obj) throw new EopException("请点击 获取验证码");	
					
					SmsCodeServ smsCodeServ = SpringContextHolder.getBean("smsCodeServ");
					String sendNos = obj.toString();
					String sendNo = sendNos;
					List<String> sendNoList = new ArrayList<String>();
					if(sendNos.contains(",")){
						sendNo = sendNos.substring(sendNos.lastIndexOf(",")+1, sendNos.length());
					}
					sendNoList.add(sendNo);
					SmsActNumReq smsActNumReq = new SmsActNumReq();
					SmsActNumResp smsActNumResp = smsCodeServ.querySmsById(smsActNumReq,sendNo);
					if(null == smsActNumResp){
						throw new EopException("验证码获取出错!");
					}else{
						if(null == smsActNumResp.getActNum()){
							throw new EopException("验证码已经失效,请重新获取!");
						}else{
							if(StringUtils.isEmpty(validCodeFlag) && !valid_code.equals(smsActNumResp.getActNum())){
								throw new EopException("验证码输入错误!");
							}
						}
					}*/
					ThreadContextHolder.getHttpSessionContext().removeAttribute(ISmsCode.SMS_SEND_NO);
					/*smsCodeServ.deleteSms(sendNoList);*/
					
					
					AdminUserLoginReq adminUserLoginReq = new AdminUserLoginReq();
					adminUserLoginReq.setUsername(username);
					adminUserLoginReq.setPassword(password);
					AdminUserInf adminUserServ = SpringContextHolder.getBean("adminUserServ");
					String result = adminUserServ.login(adminUserLoginReq).getResult();
					if("1".equals(result))
						ThreadContextHolder.getHttpSessionContext().setAttribute(ValidCodeServlet.SESSION_VALID_CODE+"admin","");
					StringBuffer json = new StringBuffer();
					AdminUser user = ManagerUtils.getAdminUser();
					String  is_login_first=user.getIs_login_first();
					if(EcsOrderConsts.IS_LOGIN_FIRST_0.equals(is_login_first)){
						json.append("{'result':2}");
					}else{
						json.append("{'result':0}");
					}
				 
					
					Response response = new StringResponse();
					response.setContent( json.toString() );
					
					String remember_login_name = httpRequest.getParameter("remember_login_name");
					if(!StringUtil.isEmpty(remember_login_name)){ //记住用户名
						HttpUtil.addCookie(httpResponse, "loginname", username, 365*24*60*60);
					}else{  //删除用户名
						HttpUtil.addCookie(httpResponse, "loginname", "", 0);
					}
					
					
					return response;
					 
				} catch (RuntimeException exception) {
					ThreadContextHolder.getHttpSessionContext().setAttribute(ValidCodeServlet.SESSION_VALID_CODE+"admin","");
					exception.printStackTrace();
					this.logger.info(exception.getMessage(),exception.fillInStackTrace());
					Response response = new StringResponse();
					response.setContent("{'result':1,'message':'"+exception.getMessage()+"'}");
					return response;
				}	 
				/*
				//验证码是否校验标识，如果没配置有值，则在登录时做验证码校验；如果配置有值，则不做校验。主要是压力测试时跳过验证码、验证码出问题时屏蔽掉验证码校验这一关---add by Musoon
				CacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
				String validCodeFlag = cacheUtil.getConfigInfo(Consts.VALID_CODE_FLAG);
				logger.info("smsLogin.validCodeFlag===>" + validCodeFlag);
				Response response = new StringResponse();
				try {
					String username = httpRequest.getParameter("username");
					String password = httpRequest.getParameter("password");
					String valid_code = httpRequest.getParameter("valid_code");
					//获取当前应用信息
					String app_info = httpRequest.getParameter("app_info");
					JSONObject app = JSONObject.fromObject(app_info);
					AppInfo appInfo = (AppInfo)JSONObject.toBean(app, AppInfo.class);
					WebSessionContext sessonContext = ThreadContextHolder.getHttpSessionContext();
					Object realCode = sessonContext.getAttribute(ValidCodeServlet.SESSION_VALID_CODE+"admin");
					
					Object p_realCode = sessonContext.getAttribute(ValidCodeServlet.SESSION_VALID_CODE);
					//logger.info(realCode+":"+p_realCode+":====================================:"+valid_code);
					String sms_code = null==sessonContext.getAttribute(ISmsCode.SESSION_SMS_CODE) ? "":(String)sessonContext.getAttribute(ISmsCode.SESSION_SMS_CODE);
					
					if (StringUtils.isEmpty(validCodeFlag) && !valid_code.equals(realCode)) {
						sessonContext.setAttribute(ValidCodeServlet.SESSION_VALID_CODE+"admin","");
						throw new EopException("验证码输入错误");
					}
					sessonContext.setAttribute(MblConsts.CURRENT_APP_INFO, appInfo);		//存储当前应用信息
					
					//登录校验 
					AdminUserLoginReq adminUserLoginReq = new AdminUserLoginReq();
					adminUserLoginReq.setUsername(username);
					adminUserLoginReq.setPassword(password);
					AdminUserInf adminUserServ = SpringContextHolder.getBean("adminUserServ");
					String result = adminUserServ.loginBySysOrRandCode(sms_code, adminUserLoginReq).getResult();
					
					if("1".equals(result))
						sessonContext.setAttribute(ValidCodeServlet.SESSION_VALID_CODE+"admin","");
					response.setContent("{'result':0,'message':'登录成功'}");
					
					String remember_login_name = httpRequest.getParameter("remember_login_name");
					if(!StringUtil.isEmpty(remember_login_name)){ //记住用户名
						HttpUtil.addCookie(httpResponse, "loginname", username, 365*24*60*60);
					}else{  //删除用户名
						HttpUtil.addCookie(httpResponse, "loginname", "", 0);
					}
					return response;
					
				} catch (Exception exception) {
					response.setContent("{'result':1,'message':'"+exception.getMessage()+"'}");
					return response;
				}*/
		
	}
	
	/**
	 * 平台切换，单点登录
	 * @param httpResponse
	 * @param httpRequest
	 * @return
	 */
	public Response singleLogin1(HttpServletResponse httpResponse,
			HttpServletRequest httpRequest){
		Response response = new StringResponse();
		try {
			String username = httpRequest.getParameter("username");
			String password = httpRequest.getParameter("password");
			//获取当前应用信息
			String app_info = httpRequest.getParameter("app_info");
			JSONObject app = JSONObject.fromObject(app_info);
			AppInfo appInfo = (AppInfo)JSONObject.toBean(app, AppInfo.class);
			
			WebSessionContext sessonContext = ThreadContextHolder.getSessionContext();
			sessonContext.setAttribute(MblConsts.CURRENT_APP_INFO, appInfo);		//存储当前应用信息
			
			//登录校验 
			AdminUserLoginReq adminUserLoginReq = new AdminUserLoginReq();
			adminUserLoginReq.setUsername(username);
			adminUserLoginReq.setPassword(password);
			AdminUserInf adminUserServ = SpringContextHolder.getBean("adminUserServ");
			String result = adminUserServ.loginBySys(adminUserLoginReq).getResult();
			
			response.setContent( "<script>location.href='main.jsp'</script>正在转向后台...");
			return response;
		} catch (EopException exception) {
			response.setContent("{'result':1,'message':'"+exception.getMessage()+"'}");
			return response;
		}
	}
}
