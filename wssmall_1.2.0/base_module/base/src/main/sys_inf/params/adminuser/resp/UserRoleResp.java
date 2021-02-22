package params.adminuser.resp;

import params.ZteResponse;

/**
 * 用户角色返回对象
 * @author hu.yi
 * @date 2013.12.24
 */
public class UserRoleResp extends ZteResponse{

	private String roles;

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}
}
