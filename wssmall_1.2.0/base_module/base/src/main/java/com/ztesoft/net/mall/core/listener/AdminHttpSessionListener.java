package com.ztesoft.net.mall.core.listener;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class AdminHttpSessionListener implements HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
//
//	private AdminUserInf adminUserServ;
//	
//	
//	@Override
//	public void sessionCreated(HttpSessionEvent arg0) {
//		logger.info("创建session===="+arg0.getSession().getId());
//	}
//
//	@Override
//	public void sessionDestroyed(HttpSessionEvent arg0) {
//		try{
//			AdminUser user = (AdminUser) arg0.getSession().getAttribute(SmsCodeInf.TMP_ADMIN_USER);
//			logger.info("当前用户===="+user);
//			if(user!=null){
//				AdminStatusUpdateReq adminStatusUpdateReq = new AdminStatusUpdateReq();
//				adminStatusUpdateReq.setUserid(user.getUserid());
//				adminStatusUpdateReq.setSessionId(null);
//				adminStatusUpdateReq.setStatus(0);
//				if(user != null){
//					adminStatusUpdateReq.setUserid(user.getUserid());
//				}
//				adminUserServ.updateLoginStatus(adminStatusUpdateReq);
//			}
//			HttpSessionContextHolder.getInstance().invalidateSession(arg0.getSession().getId());
//			//WebSessionContext<UserContext> sessonContext = ThreadContextHolder.getSessionContext();	
//			//sessonContext.removeAttribute(UserContext.CONTEXT_KEY);
//			//sessonContext.removeAttribute("admin_user_key");
//		}catch(Exception ex){
//			ex.printStackTrace();
//		}
//		logger.info("session================退出================");
//		
//	}
//
//	public AdminUserInf getAdminUserServ() {
//		return adminUserServ;
//	}
//
//	public void setAdminUserServ(AdminUserInf adminUserServ) {
//		this.adminUserServ = adminUserServ;
//	}

}
