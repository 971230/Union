package params.adminuser.req;

import params.ZteRequest;
import params.adminuser.resp.AuthResp;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.form.util.StringUtil;

/**
 * 权限点获取参数实体
 * @author hu.yi
 * @date 2013.12.23
 */
public class AuthReq extends ZteRequest<AuthResp>{

	private String roleid;
	private String authid;
	private String type;
	
	
	public String getRoleid() {
		return roleid;
	}
	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}
	public String getAuthid() {
		return authid;
	}
	public void setAuthid(String authid) {
		this.authid = authid;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public void check() throws ApiRuleException {
		if(StringUtil.isEmpty(roleid) && StringUtil.isEmpty(authid)){
			throw new ApiRuleException("-1","缺失参数");
		}
	}
	@Override
	public String getApiMethodName() {
		if(!StringUtil.isEmpty(roleid) && StringUtil.isEmpty(authid)){
			return "adminUserServ.getByRoleId";
		}else{
			return "adminUserServ.get";
		}
		
	}
}
