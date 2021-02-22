package params.adminuser.req;

import params.ZteRequest;
import params.adminuser.resp.UserRoleResp;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.framework.util.StringUtil;

/**
 * 用户角色查询参数
 * @author hu.yi
 * @date 2013.12.23
 */
public class UserRoleReq extends ZteRequest<UserRoleResp>{

	private Integer stype;
	private String pkey;
	
	
	public int getStype() {
		return stype;
	}
	public void setStype(int stype) {
		this.stype = stype;
	}
	public String getPkey() {
		return pkey;
	}
	public void setPkey(String pkey) {
		this.pkey = pkey;
	}
	@Override
	public void check() throws ApiRuleException {
		if(null == stype && StringUtil.isEmpty(pkey)){
			throw new ApiRuleException("-1","缺失参数");
		}
	}
	@Override
	public String getApiMethodName() {
		return "adminUserServ.getRolesToUser";
	}
}
