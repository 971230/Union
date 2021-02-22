package com.ztesoft.zsmart.hound.client;

import com.alibaba.dubbo.common.utils.ConfigUtils;

public class DemoApplicationContext implements HoundApplicationContext {

	private String hostInfo;

	private String pid;

	private String appName;
	
	public DemoApplicationContext() {
		pid = String.valueOf(ConfigUtils.getPid());
	}
	
	public void setPid(String pid) {
		this.pid = pid;
	}
	
	public void setHostInfo(String hostInfo) {
		this.hostInfo = hostInfo;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	@Override
	public String getHostInfo() {
		return hostInfo;
	}

	@Override
	public String getPid() {
		return pid;
	}

	@Override
	public String getAppName() {
		return appName;
	}

}
