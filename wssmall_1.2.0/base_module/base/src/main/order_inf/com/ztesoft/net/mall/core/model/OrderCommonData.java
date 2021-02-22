package com.ztesoft.net.mall.core.model;

import com.ztesoft.net.eop.resource.model.AdminUser;


public class OrderCommonData {

	@Deprecated
	
    private AppSource appSource=new AppSource();//app来源
	
	private AdminUser adminUser = null; //用户下单，根据商品id获取商品归属对象信息。 add by wui
	
	
	public AdminUser getAdminUser() {
		return adminUser;
	}

	public void setAdminUser(AdminUser adminUser) {
		this.adminUser = adminUser;
	}

	
	public AppSource getAppSource() {
		return appSource;
	}

	public void setAppSource(AppSource outEntryLocal) {
		this.appSource = appSource;
	}

	/**
	 * 是否外系统订单
	 * @作者 MoChunrun 
	 * @创建日期 2013-10-12 
	 * @return
	 */
	public boolean isOuterOrder(OrderOuter orderOuter){
		return false;
	}
}
