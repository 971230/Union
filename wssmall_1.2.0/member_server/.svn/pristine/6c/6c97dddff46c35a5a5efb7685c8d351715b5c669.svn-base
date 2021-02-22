package com.ztesoft.net.mall.core.action.backend;

import java.text.SimpleDateFormat;
import java.util.Date;
import com.ztesoft.net.eop.resource.IUserManager;
import com.ztesoft.net.eop.resource.model.EopUser;
import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.framework.context.webcontext.ThreadContextHolder;
import com.ztesoft.net.framework.context.webcontext.WebSessionContext;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.service.IMemberManager;

public class MemberAuthAction extends WWAction {
	
    
	private static final String AUTH_IP = "134.225.99.66";
	private IMemberManager memberManager;
	private IUserManager userManager ;
	private String action;
	private String loginName;
	private String ip;
	private String password;
	
/**
 * A4平台单点登陆检验
 * @author rwh 2013-09-16
 * 
 * @return
 */
	public String authLogin(){
		try{
//            //获得客户端
//			String remoteIP = ServletActionContext.getRequest().getRemoteAddr();
//			if(!AUTH_IP.equals(remoteIP))
//				//登陆页面
//				return "login";
			
			
			//检验uri传递过来的"ip"值
			if(!AUTH_IP.equals(ip))
				//登陆页面
				return "login";
			
			//检验校验码是否过期
			if(!checkMd5())
				return "login";
			//检验是否是4A平台发过来的请求
			
			if (!action.equals("4Asystemlogin")) {
				return "login";
			}
			
			//检验用户名
			int r  = this.memberManager.authLogin(loginName);
			if(r==1){
				EopUser user  = this.userManager.get(loginName);
				if(user!=null){
					WebSessionContext<EopUser> sessonContext = ThreadContextHolder.getSessionContext();	
					sessonContext.setAttribute(IUserManager.USER_SESSION_KEY, user);
				}
				//前台页面
				return "authlogin";
			}
			return "login";
		}catch(RuntimeException e){
			e.printStackTrace();
			return "login";
		}
	}
	/**
	 * MD5认证
	 * @作者 rwh 
	 * @创建日期 2013-9-16
	 * @return
	 */
	public boolean checkMd5(){
		
		// 计算验证码： 员工工号 + 日期(到小时) 组成的字符串，通过MD5产生消息摘要，
        String currTime = new SimpleDateFormat("yyyyMMddHH").format(new Date());
        String oriStr = loginName + currTime;
        oriStr = StringUtil.md5(oriStr);

        if (!oriStr.equals(password)) {
            return false;
        }
        return true;
	}
	public IMemberManager getMemberManager() {
		return memberManager;
	}

	public void setMemberManager(IMemberManager memberManager) {
		this.memberManager = memberManager;
	}

	public IUserManager getUserManager() {
		return userManager;
	}

	public void setUserManager(IUserManager userManager) {
		this.userManager = userManager;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
