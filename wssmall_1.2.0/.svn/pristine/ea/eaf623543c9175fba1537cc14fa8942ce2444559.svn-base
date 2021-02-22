
package com.ztesoft.inf.communication.client.vo;

import java.util.Map;

import com.ztesoft.common.util.StringUtils;



/**
 * @author xiaof 
 * 接口用户配置
 */
public class ClientRequestUser {
	private String user_id="";
	private String user_code="";
	private String user_name="";
	private String user_pwd=""; 
	private String user_param="";
	private String user_desc="";
	
	public ClientRequestUser() {
	}

	public ClientRequestUser(Map map) {
			setValues(map);
	}

	private void setValues(Map map) {
		if (map!=null) {
			this.user_id=StringUtils.getStrValue(map, "user_id");
			this.user_code=StringUtils.getStrValue(map, "user_code");
			this.user_name=StringUtils.getStrValue(map, "user_name");
			this.user_pwd=StringUtils.getStrValue(map, "user_pwd");
			this.user_param=StringUtils.getStrValue(map, "user_param");
			this.user_desc=StringUtils.getStrValue(map, "user_desc");
		}
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getUser_code() {
		return user_code;
	}

	public void setUser_code(String user_code) {
		this.user_code = user_code;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getUser_pwd() {
		return user_pwd;
	}

	public void setUser_pwd(String user_pwd) {
		this.user_pwd = user_pwd;
	}

	public String getUser_param() {
		return user_param;
	}

	public void setUser_param(String user_param) {
		this.user_param = user_param;
	}

	public String getUser_desc() {
		return user_desc;
	}

	public void setUser_desc(String user_desc) {
		this.user_desc = user_desc;
	}
	
	

}
