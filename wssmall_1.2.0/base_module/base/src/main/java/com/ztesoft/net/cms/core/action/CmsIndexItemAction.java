package com.ztesoft.net.cms.core.action;

import com.ztesoft.net.cms.core.service.IDataManager;
import com.ztesoft.net.framework.action.WWAction;

import java.util.Map;

/**
 * cms信息首页项
 * @author kingapex
 * 2010-10-14上午09:39:35
 */
public class CmsIndexItemAction extends WWAction {

	private IDataManager dataManager;
	private Map datass;
	
	public String article(){
		datass= dataManager.census();
		return "article";
	}
	public IDataManager getDataManager() {
		return dataManager;
	}
	public void setDataManager(IDataManager dataManager) {
		this.dataManager = dataManager;
	}
	public Map getDatass() {
		return datass;
	}
	public void setDatass(Map datass) {
		this.datass = datass;
	}
	

}
