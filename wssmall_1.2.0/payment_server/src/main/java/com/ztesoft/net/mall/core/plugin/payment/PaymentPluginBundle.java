package com.ztesoft.net.mall.core.plugin.payment;

import java.util.List;

import com.ztesoft.net.framework.plugin.AutoRegisterPluginsBundle;

public class PaymentPluginBundle extends AutoRegisterPluginsBundle {

	
	@Override
	public String getName() {
		
		return "支付插件桩";
	}
	
	
	public List getPluginList(){
		
		return this.plugins;
	}
	
	
}
