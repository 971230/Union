package com.ztesoft.net.mall.plugin.standard.point;

import com.ztesoft.net.app.base.core.plugin.IOnSettingInputShow;
import com.ztesoft.net.framework.plugin.AutoRegisterPlugin;
import com.ztesoft.net.framework.plugin.page.JspPageTabs;

public class PointSettingPluginN extends AutoRegisterPlugin implements
		IOnSettingInputShow {
	
	@Override
	public void register() {
		JspPageTabs.addTab("setting", 3, "积分设置");
	}
	
	@Override
	public String getSettingGroupName() {
		return "point";
	}

	@Override
	public String onShow() {
		return "setting";
	}
	
	@Override
	public String getAuthor() {
		return "zou.qh";
	}

	@Override
	public String getId() {
		return "point_settingN";
	}
	
	@Override
	public String getName() {
		return "积分设置";
	}
	
	@Override
	public String getType() {
		return "setting";
	}
	
	@Override
	public String getVersion() {
		return "1.0";
	}
	
	@Override
	public void perform(Object... params) {
		
	}

}
