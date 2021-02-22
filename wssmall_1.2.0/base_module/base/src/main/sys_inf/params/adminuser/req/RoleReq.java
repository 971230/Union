package params.adminuser.req;

import params.ZteRequest;
import params.adminuser.resp.RoleListResp;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.framework.util.StringUtil;


/**
 * 角色获取传参实体
 * @author hu.yi
 * @date 2013.12.23 
 */
public class RoleReq extends ZteRequest<RoleListResp>{

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
		return "permissionServ.getUserRoles";
	}
}
