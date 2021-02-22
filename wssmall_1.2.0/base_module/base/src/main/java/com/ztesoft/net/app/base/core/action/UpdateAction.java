package com.ztesoft.net.app.base.core.action;

import com.ztesoft.net.app.base.core.model.VersionState;
import com.ztesoft.net.app.base.core.service.IUpdateManager;
import com.ztesoft.net.framework.action.WWAction;

import net.sf.json.JSONObject;


/**
 * 版本更新action
 * @author kingapex
 *
 */
public class UpdateAction extends WWAction {
	
	private IUpdateManager updateManager;
	
	 
	
	/**
	 * 检测是否有新版本
	 * 供ajax调用使用
	 * @return
	 */
	public String checkNewVersion(){
		VersionState versionState = null;
		try {
			versionState = updateManager.checkNewVersion();
		} catch(Exception e){
			versionState = new VersionState();
		}
		this.json=JSONObject.fromObject(versionState).toString();
		return WWAction.JSON_MESSAGE;
	}
	
	public String update(){
		
		try{
			this.updateManager.update();
			this.json="{result:1}";
		}catch(RuntimeException e){
			this.logger.info(e);
			e.printStackTrace();
			this.json="{result:0,message:'"+e.getMessage()+"'}";
		}	
		return WWAction.JSON_MESSAGE;
	}
	
	
	
	public IUpdateManager getUpdateManager() {
		return updateManager;
	}
	public void setUpdateManager(IUpdateManager updateManager) {
		this.updateManager = updateManager;
	}
	
	
}
