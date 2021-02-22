package com.ztesoft.net.eop.sdk.user.impl;

import com.ztesoft.net.app.base.core.model.Member;
import com.ztesoft.net.eop.sdk.user.IUserService;
import com.ztesoft.net.eop.sdk.user.UserContext;
import com.ztesoft.net.framework.context.webcontext.ThreadContextHolder;
import com.ztesoft.net.framework.context.webcontext.WebSessionContext;
import org.apache.log4j.Logger;

public final class UserServiceImpl implements IUserService {
	private UserContext userContext;
	
	protected static final Logger logger = Logger.getLogger(UserServiceImpl.class);
	
	@SuppressWarnings("unchecked")
	public UserServiceImpl() {

		WebSessionContext<UserContext> webSessionContext = ThreadContextHolder.getHttpSessionContext();
		userContext = webSessionContext.getAttribute(UserContext.CONTEXT_KEY);
	}

	
	@Override
	public String getCurrentSiteId() {
		if (isUserLoggedIn()) {
			return userContext.getSiteid();
		}
		throw new IllegalStateException("The current user is not logged in.");
	}

	
	@Override
	public String getCurrentUserId() {
		if (isUserLoggedIn()) {
			return userContext.getUserid();
		}
		throw new IllegalStateException("The current user is not logged in.");
	}

	
	@Override
	public boolean isUserLoggedIn() {
		if (userContext == null)
			return false;
		else
			return true;
	}

	
	@Override
	public String getCurrentManagerId() {
		if (isUserLoggedIn()) {
			return userContext.getManagerid();
		}
		throw new IllegalStateException("The current user is not logged in.");
	}

	
	@Override
	public Member getCurrentMember() {
		Object member = ThreadContextHolder.getHttpSessionContext().getAttribute(IUserService.CURRENT_MEMBER_KEY);
		if(null != member){
			return (Member)member;
		}else {
			return (Member)ThreadContextHolder.getSessionContext().getAttribute(IUserService.CURRENT_MEMBER_KEY);
		}
	}

}
