package com.ztesoft.net.eop.processor.core;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import com.ztesoft.net.eop.sdk.context.EopContext;

/**
 * eop权限拦截器
 * @author kingapex
 * 2010-10-23下午11:55:03
 */
public class EopPermissionInterceptor implements Interceptor {

	@Override
	public void destroy() {
	}

	@Override
	public void init() {
	}

	@Override
	public String intercept(ActionInvocation inv) throws Exception {
		
		String userid  = EopContext.getContext().getCurrentSite().getUserid();
		if(!userid.equals("1")){
			return "error";
		}
		
		String result = inv.invoke();
		return result;
	}

}
