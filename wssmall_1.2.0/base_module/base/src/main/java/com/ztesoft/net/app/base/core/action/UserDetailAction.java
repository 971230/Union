package com.ztesoft.net.app.base.core.action;

import com.ztesoft.net.eop.resource.IUserDetailManager;
import com.ztesoft.net.eop.resource.model.EopUserDetail;
import com.ztesoft.net.eop.sdk.context.EopContext;
import com.ztesoft.net.framework.action.WWAction;

/**
 * @author lzf
 * <p>created_time 2009-12-4 下午03:55:33</p>
 * @version 1.0
 */
public class UserDetailAction extends WWAction {
	
	private EopUserDetail eopUserDetail;
	private IUserDetailManager userDetailManager;
	private String userid;
	@Override
	public String execute() throws Exception {
		this.userid =EopContext.getContext().getCurrentSite().getUserid();
		eopUserDetail =userDetailManager.get(userid);
		return "input";
	} 
	
	public String save() throws Exception {
		try{
		userDetailManager.edit(eopUserDetail);
		this.msgs.add("修改成功");
		} catch (RuntimeException e) {
			this.msgs.add(e.getMessage());

		}
		this.urls.put("用户信息页面", "userDetail.do");
 
		return WWAction.MESSAGE;
	}

	public EopUserDetail getEopUserDetail() {
		return eopUserDetail;
	}

	public void setEopUserDetail(EopUserDetail eopUserDetail) {
		this.eopUserDetail = eopUserDetail;
	}

	public IUserDetailManager getUserDetailManager() {
		return userDetailManager;
	}

	public void setUserDetailManager(IUserDetailManager userDetailManager) {
		this.userDetailManager = userDetailManager;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}
	
	
}
