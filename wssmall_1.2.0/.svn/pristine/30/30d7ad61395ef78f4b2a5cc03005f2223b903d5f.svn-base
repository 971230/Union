package com.ztesoft.rop.session;

import java.util.Date;

public class DefaultSession implements Session {

	private String session_id = "";

	private Date begingLoginTime = null;

	public DefaultSession(String session_id) {
		this.session_id = session_id;
		begingLoginTime = new Date(System.currentTimeMillis());
	}

	public Date getBegingLoginTime() {
		return begingLoginTime;
	}

	public void setBegingLoginTime(Date begingLoginTime) {
		this.begingLoginTime = begingLoginTime;
	}

	public String getSession_id() {
		return session_id;
	}

	public void setSession_id(String session_id) {
		this.session_id = session_id;
	}

}
