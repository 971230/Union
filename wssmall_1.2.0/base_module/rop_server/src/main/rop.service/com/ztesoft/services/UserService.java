package com.ztesoft.services;

import com.ztesoft.parameters.login.LoginRquest;
import com.ztesoft.parameters.login.LogonResponse;
import com.ztesoft.rop.annotation.NeedInSessionType;
import com.ztesoft.rop.annotation.ObsoletedType;
import com.ztesoft.rop.annotation.ServiceMethod;
import com.ztesoft.rop.annotation.ServiceMethodBean;
import com.ztesoft.rop.session.DefaultSession;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@ServiceMethodBean(version = "1.0")
public class UserService {


	@ServiceMethod(method = "user.logon", version = "1.0", needInSession = NeedInSessionType.NO,timeout=100)
	public Object logon(LoginRquest request) {
		String sessionId = UUID.randomUUID().toString();
		
		DefaultSession session = new DefaultSession(sessionId);
		request.getRopRequestContext().getRopContext().getSessionManager().addSession(sessionId, session);
				
		Map<String,String> map = new HashMap<String,String>();
		map.put("sessionId", sessionId);
		
		return map;
	}

	/**
	 * 过期版本的服务方法
	 * 
	 * @param request
	 * @return
	 */
	@ServiceMethod(method = "user.add", version = "0.9", needInSession = NeedInSessionType.NO, obsoleted = ObsoletedType.NO,timeout=500)
	public Object addUserOfV0_9(LoginRquest request) {
		
		LogonResponse response = new LogonResponse();
		response.setSessionId("0000000000");

		return response;

	}

}
