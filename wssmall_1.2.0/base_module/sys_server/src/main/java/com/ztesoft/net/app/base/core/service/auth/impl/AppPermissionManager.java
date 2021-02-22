package com.ztesoft.net.app.base.core.service.auth.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

import com.ztesoft.ibss.common.util.Const;
import com.ztesoft.net.app.base.core.service.auth.IAppPermissionManager;
import com.ztesoft.net.consts.MblConsts;
import com.ztesoft.net.eop.sdk.context.EopSetting;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.context.webcontext.ThreadContextHolder;
import com.ztesoft.net.framework.context.webcontext.WebSessionContext;
import com.ztesoft.net.framework.util.FileBaseUtil;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.sqls.SF;
import com.ztesoft.remote.app.service.IAppLocalService;
import com.ztesoft.remote.pojo.AppInfo;

/**
 * 应用权限管理
 */
public class AppPermissionManager extends BaseSupport implements IAppPermissionManager {
	
	/**
	 * @param username
	 * @return 用户应用权限列表
	 */
	@Override
	public List<AppInfo> getUserAppList(String userName){
/*		ZteClient client = ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ESC);
		NotifyOrderInfoSFRequset req = new NotifyOrderInfoSFRequset();
		NotifyOrderInfoSFResponse resp = client.execute(req, NotifyOrderInfoSFResponse.class); */
		
//		String order_id = "SW201410233391860640";
//		CommonDataFactory.getInstance().callInterface(order_id);
		List<AppInfo> result = new ArrayList<AppInfo>();
		String sql = SF.sysSql("GET_USER_APP_LIST");
		String appIds = "";
		List<Map<String, Object>> list = this.baseDaoSupport.queryForList(sql, userName);
		
		if(null != list && list.size() > 0){
			for(int i = 0; i < list.size(); i++){
				Map<String, Object> temp = list.get(i);
				String value = Const.getStrValue(temp, "objvalue");
				String[] values = value.split(",");
				for(int j = 0; j < values.length; j++){
					if(appIds.indexOf(values[j]) < 0){
						appIds += values[j] + ",";
					}
				}
			}
		}
		String[] appArray = appIds.split(",");
		
		
		IAppLocalService appLocalService = SpringContextHolder.getBean("appLocalService");
//		logger.info(Thread.currentThread().getId()+"IAppLocalService appLocalService = SpringContextHolder.getBeanappLocalService");
		List<AppInfo> appList = appLocalService.queryAllList();
		
		//超级管理员拥有所有系统的权限
		String sql_founder = SF.sysSql("GET_USER_FOUNDER");
		String curFounder = this.daoSupport.queryForString(sql_founder, new String[]{userName});
		if(Consts.CURR_FOUNDER_1.equals(curFounder))
			return appList;
		
		//非超级管理员根据权限获取应用列表
		if(null != appArray && appArray.length > 0){
			for(int i = 0; i < appArray.length; i++){
				if(null != appList && appList.size() > 0){
					for(int j = 0; j < appList.size(); j++){
						AppInfo app = appList.get(j);
						String appId = appList.get(j).getAppId() + "";
						if(appId.equals(appArray[i])){
							result.add(app);
							continue;
						}
					}
				}
			}
		}
		return result;
	}
	
	@Override
	public boolean reloadEopSetting(AppInfo app_info){
		try{
			//刷新配置文件
			InputStream in  = FileBaseUtil.getResourceAsStream("eop.properties");
			Properties props = new Properties();
			props.load(in);
			String  theme_source_from = app_info.getThemeSourceFrom();
			if(StringUtils.isEmpty(theme_source_from))
				theme_source_from =app_info.getSourceFrom();
			props.setProperty("source_from", app_info.getSourceFrom());
			props.setProperty("theme_source_from",theme_source_from);
			EopSetting.init(props);
			
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	@Override
	@SuppressWarnings("unchecked")
	public boolean setAppInfo(AppInfo app_info){
		
		//刷新缓存内容
		
		WebSessionContext sessonContext = ThreadContextHolder.getHttpSessionContext();
		sessonContext.setAttribute(MblConsts.CURRENT_APP_INFO, app_info);		//存储当前应用信息
		
		return true;
	}
	@Override
	@SuppressWarnings("unchecked")
	public boolean clearAppInfo(){
		
		WebSessionContext sessonContext = ThreadContextHolder.getHttpSessionContext();
		sessonContext.setAttribute(MblConsts.CURRENT_APP_INFO, null);		//存储当前应用信息
		
		return true;
	}

	@Override
	public List<AppInfo> getAllAppList() {
		// TODO Auto-generated method stub
		IAppLocalService appLocalService = SpringContextHolder.getBean("appLocalService");
		List<AppInfo> appList = appLocalService.queryAllList();
		return appList;
	}
	
}
