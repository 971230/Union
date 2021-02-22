package com.ztesoft.net.app.base.core.action;

import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.framework.plugin.PluginContext;

import java.util.List;

public class PluginAction extends WWAction {
	private List plugins;
	public String list() {
		plugins = PluginContext.getPlugins();
		return "list";
	}
	public List getPlugins() {
		return plugins;
	}
	public void setPlugins(List plugins) {
		this.plugins = plugins;
	}
	
	
}
