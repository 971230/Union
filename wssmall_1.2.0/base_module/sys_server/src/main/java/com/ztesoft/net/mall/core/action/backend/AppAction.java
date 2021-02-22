package com.ztesoft.net.mall.core.action.backend;

import java.util.List;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;

import com.ztesoft.net.app.base.core.service.auth.IAppPermissionManager;
import com.ztesoft.net.eop.sdk.context.EopSetting;
import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.remote.app.service.IAppLocalService;
import com.ztesoft.remote.pojo.AppInfo;

/**
 * 应用中心
 */
public class AppAction extends WWAction{
	
	private IAppPermissionManager appPermissionManager;
	private List<AppInfo> appList;
	private String curAppSource;
	private String appInfo;

	public String getUserAppList(){
		curAppSource = ManagerUtils.getSourceFrom();
		String user_name = ManagerUtils.getAdminUser().getUsername();
		appList = appPermissionManager.getUserAppList(user_name);
		return "list";
	}
	
	public String reloadEopSetting(){
		if(StringUtils.isEmpty(appInfo) || "null".equals(appInfo)){
			this.json = "{code:'-1',message:'EopSetting初始化失败'}";
			return WWAction.JSON_MESSAGE;
		}
		JSONObject app = JSONObject.fromObject(appInfo);
		AppInfo app_info = (AppInfo)JSONObject.toBean(app, AppInfo.class);
		boolean result = appPermissionManager.reloadEopSetting(app_info) && appPermissionManager.setAppInfo(app_info);
//		try {
//			DefaultZteRopClient client = new DefaultZteRopClient("http://10.45.47.170:9007/router", "wssmall_fj","123456");
//			OttCommunityIndexReq req = new OttCommunityIndexReq();
//			req.setUser_id("wuhuimac");
//			req.setReqCode("getApp");
//			OttCommunityIndexResp resp = client.execute(req, OttCommunityIndexResp.class);
//			logger.info("1111111");
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return null;
//		}
		
		if(result){
			this.json = "{code:'0000',message:'初始化成功'}";
		}else {
			this.json = "{code:'-1',message:'EopSetting初始化失败'}";
		}	
		return WWAction.JSON_MESSAGE;
	}
	
	public String jumpSystem(){
		
		IAppLocalService appLocalService = SpringContextHolder.getBean("appLocalService");
		List<AppInfo> appList = appLocalService.queryAllList();
		String curIp = "";
		JSONObject app = JSONObject.fromObject(appInfo);
		AppInfo app_info = (AppInfo)JSONObject.toBean(app, AppInfo.class);
		String tarIp = app_info.getAppAddress();
		tarIp = getIpPort(tarIp);
		
		if(null != appList && !appList.isEmpty()){
			for(int i = 0; i < appList.size(); i++){
				AppInfo temp = appList.get(i);
				if(temp.getSourceFrom().equals(EopSetting.SOURCE_FROM)){
					curIp = temp.getAppAddress();
					curIp = getIpPort(curIp);
					break;
				}
			}
		}
		
		if(tarIp.equals(curIp)){
			appPermissionManager.reloadEopSetting(app_info);
			appPermissionManager.clearAppInfo();
			this.json = "{code:'0000',message:'初始化成功'}";
		}else {
			this.json = "{code:'-1',message:'跳转页面'}";
		}
		return WWAction.JSON_MESSAGE;
	}
	
	private String getIpPort(String url){
		url = url.replaceAll("http[s]?://", "");
		url = url.substring(0, url.indexOf("/"));
		return url;
	}
	
	public IAppPermissionManager getAppPermissionManager() {
		return appPermissionManager;
	}

	public void setAppPermissionManager(IAppPermissionManager appPermissionManager) {
		this.appPermissionManager = appPermissionManager;
	}

	public List<AppInfo> getAppList() {
		return appList;
	}

	public void setAppList(List<AppInfo> appList) {
		this.appList = appList;
	}
	
	public String getCurAppSource() {
		return curAppSource;
	}

	public void setCurAppSource(String curAppSource) {
		this.curAppSource = curAppSource;
	}
	
	public String getAppInfo() {
		return appInfo;
	}

	public void setAppInfo(String appInfo) {
		this.appInfo = appInfo;
	}
}
