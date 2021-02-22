package params.adminuser.req;

import params.ZteRequest;
import params.adminuser.resp.RoleResp;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.app.base.core.model.Role;


/**
 * 新增角色参数实体
 * @author hu.yi
 * @date 2013.12.23
 */
public class RoleAddReq extends ZteRequest<RoleResp>{

	private Role role;
	private String[] acts;
	
	
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public String[] getActs() {
		return acts;
	}
	public void setActs(String[] acts) {
		this.acts = acts;
	}
	@Override
	public void check() throws ApiRuleException {
		if(null == role || null == acts){
			throw new ApiRuleException("-1","缺失参数");
		}
	}
	@Override
	public String getApiMethodName() {
		return "roleServ.add";
	}
}
