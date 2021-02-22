package com.ztesoft.net.mall.core.plugin.search;


import java.util.List;

import com.ztesoft.net.framework.plugin.AutoRegisterPluginsBundle;

public class GoodsSearchPluginBundle extends AutoRegisterPluginsBundle {

	@Override
	public String getName() {
		 
		return "商品搜索插件桩";
	}
	
	public List getPluginList(){
		
		return this.plugins;
	}
	
 
	

}
