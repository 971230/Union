package com.ztesoft.net.eop.resource.impl;

import java.util.List;

import com.ztesoft.net.eop.resource.IWidgetBundleManager;
import com.ztesoft.net.eop.resource.model.WidgetBundle;
import com.ztesoft.net.eop.sdk.database.BaseSupport;

public class WidgetBundleManagerImpl extends BaseSupport<WidgetBundle> implements
		 IWidgetBundleManager {

	
	@Override
	public WidgetBundle getWidgetBundle(String widgetType) {
		String sql ="select * from widgetbundle where widgettype=?";
		return this.baseDaoSupport.queryForObject(sql, WidgetBundle.class, widgetType);
	}

	
	@Override
	public List<WidgetBundle> getWidgetbundleList() {
		String sql ="select * from widgetbundle";
		return baseDaoSupport.queryForList(sql, WidgetBundle.class);
	}

	
	@Override
	public void add(WidgetBundle widgetBundle) {
		this.baseDaoSupport.insert("widgetbundle", widgetBundle);
	}

 

}
