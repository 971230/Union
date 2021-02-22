package com.ztesoft.net.eop.sdk.listener;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;

public class EopSessionListener implements HttpSessionListener {
	
	protected final Logger logger = Logger.getLogger(getClass());
	
	@Override
	public void sessionCreated(HttpSessionEvent se) {
		
	}
	
	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		logger.info("=========================================================================session超时了=============================================================");
//		if(logger.isDebugEnabled()){
//			logger.debug("session destroyed..");
//		}
		//add by wui设置失效
//		ThreadContextHolder.getHttpSessionContext().invalidateSession(); //设置session失效
		
		//如果是已经安装状态
//		if("YES".equals( EopSetting.INSTALL_LOCK.toUpperCase())){
			
//			if(logger.isDebugEnabled()){
//				logger.debug("installed...");
//			}
//			EopSite site = (EopSite) se.getSession().getAttribute("site_key");
//			String sessionid = se.getSession().getId();
//			IAppManager appManager = SpringContextHolder.getBean("appManager");
//			List<EopApp> appList  = appManager.list();
//			for(EopApp eopApp:appList){
//
//				String appid  = eopApp.getAppid();
//				
//				if(logger.isDebugEnabled()){
//					logger.debug("call app["+appid+"] destory...");
//				}
//				
//				
//				IApp app = SpringContextHolder.getBean(appid);
//				app.sessionDestroyed(sessionid,site);
			}
//		}
//	else{
//			if(logger.isDebugEnabled()){
//				logger.debug("not installed...");
//			}
//		}
//	}
}
