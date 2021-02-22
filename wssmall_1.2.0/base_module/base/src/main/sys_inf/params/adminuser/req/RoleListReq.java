package params.adminuser.req;

import com.ztesoft.api.ApiRuleException;

import params.ZteRequest;
import params.adminuser.resp.RoleListResp;

public class RoleListReq extends ZteRequest<RoleListResp>{
    private String rolename;
    
    public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}
	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public String getApiMethodName() {
		return "roleServ.listRole";
	}

	

}
