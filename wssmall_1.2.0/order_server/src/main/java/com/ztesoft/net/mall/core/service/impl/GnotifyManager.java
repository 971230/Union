package com.ztesoft.net.mall.core.service.impl;

import com.powerise.ibss.util.DBTUtil;
import com.ztesoft.net.app.base.core.model.Member;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.eop.sdk.user.IUserService;
import com.ztesoft.net.eop.sdk.user.UserServiceFactory;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.model.Gnotify;
import com.ztesoft.net.mall.core.service.IGnotifyManager;
import com.ztesoft.net.sqls.SF;

public class GnotifyManager extends BaseSupport implements IGnotifyManager {
	 
	
	@Override
	public Page pageGnotify(int pageNo, int pageSize) {
		IUserService userService = UserServiceFactory.getUserService();
		Member member = userService.getCurrentMember();
		String sql = SF.orderSql("SERVICE_GNOTIFY_SELECT");
		sql += " and a.member_id = " + member.getMember_id();
 		Page webpage = this.daoSupport.queryForPage(sql, pageNo, pageSize);
		return webpage;
	}

	
	@Override
	public void deleteGnotify(String gnotify_id) {
		this.baseDaoSupport.execute(SF.orderSql("SERVICE_GNOIFY_DELETE"), gnotify_id);
	}
	
	@Override
	public void addGnotify(String goodsid){
		IUserService userService = UserServiceFactory.getUserService();
		Member member = null;
		if(userService!=null){
			member = userService.getCurrentMember();
		}
		Gnotify gnotify = new Gnotify();
		gnotify.setCreate_time(DBTUtil.current());
		gnotify.setGoods_id(goodsid);
		if(member!=null){
			gnotify.setMember_id(member.getMember_id());
			gnotify.setEmail(member.getEmail());
		}
		this.baseDaoSupport.insert("gnotify", gnotify);
		
	}

 


 

}
