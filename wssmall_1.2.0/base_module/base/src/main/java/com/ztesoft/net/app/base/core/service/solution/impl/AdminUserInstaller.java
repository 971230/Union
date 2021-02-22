package com.ztesoft.net.app.base.core.service.solution.impl;

import org.w3c.dom.Node;

import com.ztesoft.net.app.base.core.service.solution.IInstaller;
import com.ztesoft.net.eop.resource.IUserManager;
import com.ztesoft.net.framework.database.IDaoSupport;

public class AdminUserInstaller implements IInstaller {

	private IUserManager userManager;
	private IDaoSupport daoSupport;

	@Override
	public void install(String productId, Node fragment) {
//		if ("base".equals(productId)) {
//			EopUser user = this.userManager.getCurrentUser();
//			EopSite site = EopContext.getContext().getCurrentSite();
//			String userid = site.getUserid();
//			String siteid = site.getId();
//			if (user != null) { // 站点本身导入user会为空
//				// this.adminUserManager.clean();
//				// //为站点添加管理员，此管理员为创始人。
//				AdminUser adminUser = new AdminUser();
//				adminUser.setUsername(user.getUsername());
//				adminUser.setPassword(user.getPassword());
//				adminUser.setFounder(1);
//				
//				AdminUserAddReq adminUserAddReq = new AdminUserAddReq();
//				adminUserAddReq.setUserid(site.getUserid());
//				adminUserAddReq.setSiteid(siteid);
//				adminUserAddReq.setAdminUser(adminUser);
//				
//				AdminUserIdResp adminUserAddResp = new AdminUserIdResp();
//				adminUserAddResp = this.adminUserServ.addUser(adminUserAddReq);
//				
//				String adminUserId = "";
//				if(adminUserAddResp != null){
//					adminUserId = adminUserAddResp.getAdminUserId();
//				}
//				// 创建管理员时的密码为双md5了，更新为md5码
//				if (EopSetting.RUNMODE.equals("2")) {
//					this.daoSupport.execute("update es_adminuser_" + userid
//							+ "_" + siteid + " set password=? where userid=?",
//							user.getPassword(), adminUserId);
//				} else {
//					this.daoSupport
//							.execute(
//									"update es_adminuser  set password=? where userid=?",
//									user.getPassword(), adminUserId);
//				}
//			} else { // 如果是本地导入，adminuser表已经清空，重新插入当前用户
//				AdminCurrUserReq adminCurrUserReq = new AdminCurrUserReq();
//				AdminUserResp adminUserResp = adminUserServ.getCurrentUser(adminCurrUserReq);
//				AdminUser adminUser = new AdminUser();
//	    		if(adminUserResp != null){
//	    			adminUser = adminUserResp.getAdminUser();
//	    		}
//				String tablename = "es_adminuser";
//				if (EopSetting.RUNMODE.equals("2")) { // saas式时表名变更
//					tablename = tablename + "_" + userid + "_" + siteid;
//				}
//				this.daoSupport.insert(tablename, adminUser);
//				String adminuserid = adminUser.getUserid();
//
//				// 创建管理员时的密码为双md5了，更新为md5码
//				if (EopSetting.RUNMODE.equals("2")) {
//					this.daoSupport.execute("update es_adminuser_" + userid
//							+ "_" + siteid + " set password=? where userid=?",
//							adminUser.getPassword(), adminuserid);
//				} else {
//					this.daoSupport
//							.execute(
//									"update es_adminuser  set password=? where userid=?",
//									adminUser.getPassword(), userid);
//				}
//			}
//		}
	}

	public IUserManager getUserManager() {
		return userManager;
	}

	public void setUserManager(IUserManager userManager) {
		this.userManager = userManager;
	}




	public IDaoSupport getDaoSupport() {
		return daoSupport;
	}

	public void setDaoSupport(IDaoSupport daoSupport) {
		this.daoSupport = daoSupport;
	}

}
