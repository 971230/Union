package params.adminuser.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.framework.util.StringUtil;

import params.ZteRequest;
import params.ZteResponse;

/**
 * 用户赋权参数实体
 * @author hu.yi
 * @date 2013.12.23
 */
public class UserPermissionReq extends ZteRequest<ZteResponse>{

	private String userid;
	private String[] roleids;
	
	
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String[] getRoleids() {
		return roleids;
	}
	public void setRoleids(String[] roleids) {
		this.roleids = roleids;
	}
	@Override
	public void check() throws ApiRuleException {
		if(StringUtil.isEmpty(userid) || null == roleids){
			 throw new ApiRuleException("-1","缺失参数");
		}
	}
	@Override
	public String getApiMethodName() {
		return "permissionServ.giveRolesToUser";
	}
}
