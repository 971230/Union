package params.adminuser.req;

import params.ZteRequest;
import params.adminuser.resp.AdminUserLoginResp;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.form.util.StringUtil;

public class AdminUserLoginReq extends ZteRequest<AdminUserLoginResp>{
	private String  username;
	private String password;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public void check() throws ApiRuleException {
		if(StringUtil.isEmpty(username)){
			throw new ApiRuleException("-1","username不能为空");
		}
		if(StringUtil.isEmpty(password)){
			throw new ApiRuleException("-1","password不能为空");
		}
	}

	@Override
	public String getApiMethodName() {
		return "adminUserServ.loginBySys";
	}
	
}
