package params.adminuser.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.framework.util.StringUtil;

import params.ZteRequest;
import params.adminuser.resp.RoleDelResp;

public class RoleDelReq extends ZteRequest<RoleDelResp>{

	private String roleid;
	
	public String getRoleid() {
		return roleid;
	}

	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}

	@Override
	public void check() throws ApiRuleException {
		if(StringUtil.isEmpty(roleid)){
			throw new ApiRuleException("-1","roleid不能为空");
		}
	}

	@Override
	public String getApiMethodName() {
		return "roleServ.delete";
	}

}
