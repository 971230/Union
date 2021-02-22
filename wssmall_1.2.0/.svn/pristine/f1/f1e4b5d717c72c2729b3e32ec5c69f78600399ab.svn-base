package com.ztesoft.net.framework.plugin;

import java.util.List;

import org.apache.log4j.Logger;

import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.plugin.IPlugin;

public abstract class AutoRegisterPlugin extends BaseSupport implements IPlugin {
	protected final Logger logger = Logger.getLogger(getClass());
	protected List<IPluginBundle> bundleList;

	public List<IPluginBundle> getBundleList() {
		return bundleList;
	}

	public void setBundleList(List<IPluginBundle> bundleList) {
		this.bundleList = bundleList;
	} 
	
	public abstract void register();
}
