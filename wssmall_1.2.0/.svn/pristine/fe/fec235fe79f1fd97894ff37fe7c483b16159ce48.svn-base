package com.ztesoft.cms.page.context;

import org.apache.log4j.Logger;

/**
 * @author Reason.Yea
 * @version Created Oct 26, 2012
 */
public class CmsContext {
	private static Logger logger = Logger.getLogger(CmsContext.class);
	private static ThreadLocal data = new ThreadLocal();
	
	public static void setCmsObj(CmsObj cms){
		data.set(cms);
	}
	public static CmsObj getCmsObj(){
		return (CmsObj) data.get();
	}
}
