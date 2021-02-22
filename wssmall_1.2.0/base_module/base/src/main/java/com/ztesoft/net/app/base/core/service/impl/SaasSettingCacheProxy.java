package com.ztesoft.net.app.base.core.service.impl;

import java.util.List;
import java.util.Map;

import com.ztesoft.net.app.base.core.service.ISettingService;
import com.ztesoft.net.app.base.core.service.SettingRuntimeException;
import com.ztesoft.net.eop.resource.model.EopSite;
import com.ztesoft.net.eop.sdk.context.EopContext;
import com.ztesoft.net.framework.cache.ICache;

/**
 * SAAS式的设置缓存代理类。
 * @author kingapex
 * 2010-1-15下午03:12:29
 */
public class SaasSettingCacheProxy implements ISettingService {

	private ISettingService settingService;
	private ICache<Map<String,Map<String,String>>> cache;
	public SaasSettingCacheProxy(ISettingService settingService){
		this.settingService = settingService;
	}
	
	
	@Override
	public Map<String,Map<String ,String>>  getSetting() {
		String uKey =   this.getCurrentUserid() +"_"+ this.getCurrentSiteid() ;
		Map<String,Map<String,String>> settings  = cache.get(uKey);
		//未命中，由库中取出设置并压入缓存
		if(settings==null || settings.size()<=0){
			settings= this.settingService.getSetting();
			cache.put(uKey,settings);
		}
		return settings;
	}
 
	
	//保存库同时保存缓存
	
	@Override
	public void save(Map<String,Map<String,String>> settings ) throws SettingRuntimeException {
		String uKey =   this.getCurrentUserid() +"_"+ this.getCurrentSiteid() ;
		this.settingService.save(settings);
		cache.put(uKey,  settingService.getSetting());
	}
	
	
	/**
	 * 获取当前用户id
	 * @return
	 */
	protected String getCurrentUserid(){
		EopSite site = EopContext.getContext().getCurrentSite();
		String userid = null;
		if(site!=null && site.getUserid()!=null){
			userid = site.getUserid();
		}
		return userid;
	}
	
	
	/**
	 * 获取当前站点
	 * @return
	 */
	protected String getCurrentSiteid(){
		EopSite site = EopContext.getContext().getCurrentSite();
		return site.getId();
	}

	
	@Override
	public String getSetting(String group, String code) {
		Map<String,Map<String ,String>> settings  = this.getSetting();
		if(settings==null) return null;
		
		Map<String ,String> setting = settings.get(group);		
		if(setting== null )return null;
		
		return setting.get(code);
	}

	public void setCache(ICache<Map<String, Map<String, String>>> cache) {
		this.cache = cache;
	}

	
	@Override
	public List<String> onInputShow() {
		
		return this.settingService.onInputShow();
	}
	
	
}
