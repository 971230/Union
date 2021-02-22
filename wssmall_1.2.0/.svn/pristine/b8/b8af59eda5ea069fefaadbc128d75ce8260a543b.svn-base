package com.ztesoft.net.framework.plugin;

import java.util.ArrayList;
import java.util.List;

import com.ztesoft.net.framework.plugin.IPlugin;

public class PluginContext {
	
	 private static List<IPlugin> pluginContext;
	static{
		pluginContext =  new ArrayList<IPlugin>();
	}
	 public static void registerPlugin(IPlugin plugin){
		 pluginContext.add( plugin);
	 }
	 
	 public static List<IPlugin> getPlugins(){
		 return pluginContext;
	 }
	 
}
