package com.ztesoft.net.app.base.core.service.impl;

import com.alibaba.fastjson.JSON;
import com.ztesoft.net.app.base.core.plugin.SettingPluginBundle;
import com.ztesoft.net.app.base.core.service.ISettingService;
import com.ztesoft.net.app.base.core.service.SettingRuntimeException;
import com.ztesoft.net.framework.database.IDaoSupport;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 系统设置业务类
 * 
 * @author apexking
 * 
 */
public class SettingService  implements ISettingService  {
	private IDaoSupport daoSupport;
	private SettingPluginBundle settingPluginBundle;
 
	

	/* (non-Javadoc)
	 * @see com.enation.app.setting.service.ISettingService#save()
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void save( Map<String,Map<String,String>> settings ) throws SettingRuntimeException {
		Iterator<String> settingkeyItor = settings.keySet().iterator();
 
		while ( settingkeyItor.hasNext() ) {
			String settingKey = settingkeyItor.next();
			this.daoSupport.execute("update settings set cfg_value=? where cfg_group=?",JSON.toJSONString(settings),settingKey);
		}
	}
	
	
 
	

	/* (non-Javadoc)
	 * @see com.enation.app.setting.service.ISettingService#getSetting()
	 */
	@Override
	public Map<String,Map<String,String>> getSetting() {
		String sql = "select * from settings where 1 = 1 ";
		List<Map<String, String>> list = this.daoSupport.queryForList(sql);
		Map cfg = new HashMap();
		
		for (Map<String,String> map : list) {
			String setting_value = map.get("cfg_value");
			cfg.put( map.get("cfg_group"), JSON.parseArray(setting_value, Map.class));
		}
		
		return cfg;
	}

	public SettingPluginBundle getSettingPluginBundle() {
		return settingPluginBundle;
	}

	public void setSettingPluginBundle(SettingPluginBundle settingPluginBundle) {
		this.settingPluginBundle = settingPluginBundle;
	}
	



	public void setDaoSupport(IDaoSupport daoSupport) {
		this.daoSupport = daoSupport;
	}


	
	@Override
	public String getSetting(String group, String code) {
		
		return this.getSetting().get(group).get(code);
	}


	
	@Override
	public List<String> onInputShow() {
		 Map<String,Map<String ,String>>  settings  = this.getSetting();
		return this.settingPluginBundle.onInputShow(settings);
	}

}
