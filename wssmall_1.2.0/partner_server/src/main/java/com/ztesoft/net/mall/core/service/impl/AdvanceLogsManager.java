package com.ztesoft.net.mall.core.service.impl;

import java.util.List;

import com.ztesoft.net.app.base.core.model.Member;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.eop.sdk.user.IUserService;
import com.ztesoft.net.eop.sdk.user.UserServiceFactory;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.model.AdvanceLogs;
import com.ztesoft.net.mall.core.service.IAdvanceLogsManager;

/**
 * 预存款日志
 * 
 * @author lzf<br/>
 *         2010-3-25 下午01:36:37<br/>
 *         version 1.0<br/>
 */
public class AdvanceLogsManager extends BaseSupport implements
		IAdvanceLogsManager {

	
	@Override
	public Page pageAdvanceLogs(int pageNo, int pageSize) {
		IUserService userService = UserServiceFactory.getUserService();
		Member member = userService.getCurrentMember();
		Page page = this.baseDaoSupport.queryForPage("select * from advance_logs where member_id=? order by mtime DESC", pageNo, pageSize, member.getMember_id());
		return page;
	}

	
	@Override
	public void add(AdvanceLogs advanceLogs) {
		this.baseDaoSupport.insert("advance_logs", advanceLogs);
		
	}

	
	@Override
	public List listAdvanceLogsByMemberId(String member_id) {
		return this.baseDaoSupport.queryForList("select * from advance_logs where member_id=? order by mtime desc", AdvanceLogs.class, member_id);
	}

}
