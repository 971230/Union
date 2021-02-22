package com.ztesoft.net.mall.core.service.impl.cache;

import com.ztesoft.common.util.JsonUtil;
import com.ztesoft.net.framework.cache.AbstractCacheProxy;
import com.ztesoft.net.framework.cache.CacheFactory;
import com.ztesoft.net.mall.core.model.ConfigInfo;
import com.ztesoft.net.mall.core.service.IConfigInfoManager;

import java.util.ArrayList;
import java.util.List;

public class ConfigInfoCacheProxy extends AbstractCacheProxy<ConfigInfo>{
	private IConfigInfoManager configInfoManager;
	
	public static final String LIST_KEY_PREFIX ="config_info_list";
	public ConfigInfoCacheProxy(IConfigInfoManager configInfoManager) {
		
		super(CacheFactory.CONFIG_INFO_CACHE_NAME_KEY);
		
		this.configInfoManager = configInfoManager;
	}
	
	/**
	 * 获取es_config_info配置信息
	 * @param cfId
	 */
	public String getConfig(String cfId){
		
		String cfValue = "";
		//首先获取列表
//		List<ConfigInfo> configList  = this.cache.get(LIST_KEY_PREFIX);
//		if(configList == null || configList.isEmpty()){
//			configList = this.configInfoManager.list();
//			this.cache.put(LIST_KEY_PREFIX, configList);
//		}
//		
//		//得到列表后获取key值
//		if(configList != null && !configList.isEmpty()){
//			ConfigInfo configInfo = null;
//			for (int i = 0; i < configList.size(); i++) {
//				configInfo = configList.get(i);
//				if(configInfo.getCf_id().equals(cfId)){
//					cfValue = configInfo.getCf_value();
//				}
//			}
//		}
		ConfigInfo configInfo = this.cache.get(LIST_KEY_PREFIX+cfId);
		if(configInfo == null){
			configInfo = this.configInfoManager.getCofigByKey(cfId);
			if(configInfo==null){
				configInfo = new ConfigInfo();
			}
			this.cache.put(LIST_KEY_PREFIX+cfId, configInfo);
		}
		cfValue = configInfo.getCf_value();
		return cfValue;
	}
	
	public List getConfigList(String preId){
		List list = new ArrayList();
		// 首先获取列表
//		List<ConfigInfo> configList = this.cache.get(LIST_KEY_PREFIX);
//		
//		if (configList == null || configList.isEmpty()) {
//			configList = this.configInfoManager.list();
//			this.cache.put(LIST_KEY_PREFIX, configList);
//		}
		// 得到列表后获取key值
		List<ConfigInfo> configList = this.configInfoManager.list();
		if (configList != null && !configList.isEmpty()) {
			ConfigInfo configInfo = null;
			for (int i = 0; i < configList.size(); i++) {
				configInfo = configList.get(i);
				if (configInfo.getCf_id().startsWith(preId)) {
					list.add(configInfo);
				}
			}
		}
		logger.info("----list"+JsonUtil.toJson(list));
		return list;
	}
	
	/**
	 * 
	 * 刷新缓存方法，可添加其他数据表缓存刷新
	 */
	public void refreshCache(){
		
		List<ConfigInfo> configList = this.configInfoManager.list();
		for (int i = 0; i < configList.size(); i++) {
				ConfigInfo configInfo = configList.get(i);
				this.cache.put(LIST_KEY_PREFIX+configInfo.getCf_id(), configInfo);
		}
		
	}
}
