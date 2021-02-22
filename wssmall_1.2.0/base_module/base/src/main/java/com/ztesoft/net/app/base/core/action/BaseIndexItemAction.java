package com.ztesoft.net.app.base.core.action;

import com.ztesoft.net.app.base.core.service.IAccessRecorder;
import com.ztesoft.net.eop.resource.ISiteManager;
import com.ztesoft.net.eop.resource.model.EopSite;
import com.ztesoft.net.eop.sdk.context.EopContext;
import com.ztesoft.net.framework.action.WWAction;

import java.util.Map;

/**
 * 首页项(基本)
 * @author kingapex
 * 2010-10-13下午05:16:45
 */
public class BaseIndexItemAction extends WWAction {
	private ISiteManager siteManager;
	private IAccessRecorder accessRecorder; 
	private Map access; 
	private EopSite site;
	public String base(){
		site  = EopContext.getContext().getCurrentSite();
		return "base";
	}
	
	public String access(){
		this.access = this.accessRecorder.census();
		return "access";
	}
	
	private int canget;
	public String point(){
		site  = EopContext.getContext().getCurrentSite();
		EopSite site  = EopContext.getContext().getCurrentSite();
		long lastgetpoint =  site.getLastgetpoint();//上次领取积分的时间
		long now  = System.currentTimeMillis()/1000; //当前时间
		int onemonth =60*60*24*30;
		if(now-lastgetpoint>onemonth){//已经有一个月未领取积分
			canget=1;
		}else{						  //本月已经领取过积分
			canget=0;
		}
		return "point";
	}
	
	
	public ISiteManager getSiteManager() {
		return siteManager;
	}
	public void setSiteManager(ISiteManager siteManager) {
		this.siteManager = siteManager;
	}
	public EopSite getSite() {
		return site;
	}
	public void setSite(EopSite site) {
		this.site = site;
	}

	public IAccessRecorder getAccessRecorder() {
		return accessRecorder;
	}

	public void setAccessRecorder(IAccessRecorder accessRecorder) {
		this.accessRecorder = accessRecorder;
	}

	public Map getAccess() {
		return access;
	}

	public void setAccess(Map access) {
		this.access = access;
	}

	public int getCanget() {
		return canget;
	}

	public void setCanget(int canget) {
		this.canget = canget;
	}
	
}
