package com.ztesoft.api;

import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.search.conf.EsearchValues;

/**
 * 应用平台调用工厂类
 * 
 * @author wui
 * @since 1.0, 2013-12-25
 */

public enum AppKeyEnum {
	//System.getProperty(Consts.REMOTE_ROP_ADDRESS) 启动的时候设置http://10.123.188.164:9999/router
	//http://10.45.46.169:8083/rop/router http://10.45.46.169:8083/rop/router" http://10.45.46.169:8083/rop/router
	APP_KEY_WSSMALL_WAREHOUSE("wssmall_ecsord", "123456", "广东联通仓库测试环境","http://10.123.99.69:7001/router"),
	APP_KEY_WSSMALL_YDZQ("wssmall_ydzq", "123456", "移动政企",System.getProperty(Consts.REMOTE_ROP_ADDRESS)),
	APP_KEY_WSSMALL_ESC("wssmall_ecs", "123456", "广东联动ECS","http://localhost:8083/router"), 
	APP_KEY_WSSMALL_ESEARCH("wssmall_ecs", "123456", "ESEARCH调用地址",EsearchValues.ESEARCH_ROP_URL),
	APP_KEY_WSSMALL_ECSORD("wssmall_ecsord", "123456", "广东联动ECS订单","http://localhost:8083/router"),
	APP_KEY_WSSMALL_SHP("wssmall_shp", "123456", "CMS","http://localhost:8080/router"),
	APP_KEY_WSSMALL_HB("wssmall_hb", "123456", "河北o2o","http://localhost:8080/router"),
	APP_KEY_WSSMALL_ECC("wssmall_ecc", "123456", "校验系统","http://localhost:8080/router"),
	APP_KEY_WSSMALL_JSORD("wssmall_jsord", "123456", "江苏移动订单中心","http://localhost:8080/router"),
	APP_KEY_WSSMALL_ECSORD_TEST("wssmall_ecsord", "123456", "广东联动ECS订单测试环境","http://10.123.99.69:10004/router"),
	APP_KEY_WSSMALL_ECSORD_RELESE("wssmall_ecsord", "123456", "广东联动ECS订单生产环境","http://10.123.180.120:8086/router"),
	APP_KEY_WSSMALL_LLKJ_WT("wssmall_llkj_wt", "123456", "连连科技网厅",System.getProperty(Consts.REMOTE_ROP_ADDRESS)), 
	APP_KEY_WSSMALL_FJ("wssmall_fj", "123456", "福建",System.getProperty(Consts.REMOTE_ROP_ADDRESS)), 
	WSSMALL_WEBZT("wssmall_webzt", "123456", "连连科技掌厅",System.getProperty(Consts.REMOTE_ROP_ADDRESS)),
	APP_KEY_WSSMALL_LLKJ_WSSMALL("wssmall", "123456", "连连科技统一管理平台",System.getProperty(Consts.REMOTE_ROP_ADDRESS)),
	APP_KEY_WSSMALL_LLKJ_AGENT("wssmall_llkj_agent", "123456", "连连科技代理商",System.getProperty(Consts.REMOTE_ROP_ADDRESS)), 
	APP_KEY_WSSMALL_LLKJ_LBS("wssmall_llkj_lbs", "123456", "连连科LBS",System.getProperty(Consts.REMOTE_ROP_ADDRESS)),
	APP_KEY_WSSMALL_LLKJ_IVR("wssmall_llkj_ivr", "123456", "连连科技IVR",System.getProperty(Consts.REMOTE_ROP_ADDRESS)),
	APP_KEY_WSSMALL_LLKJ_FT("wssmall_llkj_ft", "123456", "连连科技代FT",System.getProperty(Consts.REMOTE_ROP_ADDRESS)),
	APP_KEY_WSSMALL("wssmall", "123456", "电商平台",System.getProperty(Consts.REMOTE_ROP_ADDRESS)),
	APP_KEY_WSSMALL_HN("wssmall_hnqd", "123456", "湖南","http://localhost:8080/wssmall/router");
	private String appKey = "";
	private String appSec = "";
	private String appName = "";
	private String appUrl ="";
	AppKeyEnum(String appKeyP, String appSecP, String appNameP,String appUrlP) {
		this.appKey = appKeyP;
		this.appSec = appSecP;
		this.appName = appNameP;
		this.appUrl =appUrlP;
	}

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getAppSec() {
		return appSec;
	}

	public void setAppSec(String appSec) {
		this.appSec = appSec;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getAppUrl() {
		return appUrl;
	}

	public void setAppUrl(String appUrl) {
		this.appUrl = appUrl;
	}
}
