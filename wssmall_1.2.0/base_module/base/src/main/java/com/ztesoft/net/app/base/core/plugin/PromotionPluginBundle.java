package com.ztesoft.net.app.base.core.plugin;

import java.util.List;

import com.ztesoft.net.framework.plugin.AutoRegisterPluginsBundle;
import com.ztesoft.net.framework.plugin.IPlugin;

/**
 * 优惠规则插件桩
 * @author kingapex
 *2010-4-15下午03:50:35
 */
public class PromotionPluginBundle extends AutoRegisterPluginsBundle {

	
	@Override
	public String getName() {
		 
		return "优惠规则插件桩";
	}
	
	public List<IPlugin> getPluginList(){
		
		return this.plugins;
	}
	
	

}
