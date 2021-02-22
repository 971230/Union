/**
 * 
 */
package com.ztesoft.net.action;

import com.powerise.ibss.framework.FrameException;
import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.service.IInfLogMonitManager;

/**
 * @author ZX
 * @version 2015-10-26
 * @see 接口日志监控管理
 *
 */
public class InfLogMonitManagerAction extends WWAction {
	
	private IInfLogMonitManager iInfLogMonitManager;
	private Page pageWeb;
	private String startTime;
	private String endTime;
	
	public String toInfLogPage() {
		try {
			pageWeb = iInfLogMonitManager.toInfLogPage(startTime, endTime, this.getPage(), this.getPageSize());
		} catch (FrameException e) {
			e.printStackTrace();
		}
		return "toInfLogPage";
	}
	
	public String toInfLogAnalyze() {
		String code = iInfLogMonitManager.toInfLogAnalyze(startTime, endTime);
		if (code.equals("0")) {
			json = "{'result':0,'message':'成功！'}";
		} else {
			json = "{'result':1,'message':'失败！'}";
		}
		return JSON_MESSAGE;
	}
	
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public IInfLogMonitManager getiInfLogMonitManager() {
		return iInfLogMonitManager;
	}
	public void setiInfLogMonitManager(IInfLogMonitManager iInfLogMonitManager) {
		this.iInfLogMonitManager = iInfLogMonitManager;
	}
	public Page getPageWeb() {
		return pageWeb;
	}
	public void setPageWeb(Page pageWeb) {
		this.pageWeb = pageWeb;
	}
	
}
