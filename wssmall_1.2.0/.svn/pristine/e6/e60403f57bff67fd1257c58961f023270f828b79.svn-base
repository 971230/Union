package params.adminuser.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.eop.resource.model.AdminUser;

import params.ZteRequest;
import params.adminuser.resp.AdminUserResp;

public class AdminUserFillReq extends ZteRequest<AdminUserResp>{

	private AdminUser adminUser;
	
	
	public AdminUser getAdminUser() {
		return adminUser;
	}

	public void setAdminUser(AdminUser adminUser) {
		this.adminUser = adminUser;
	}

	
	@Override
	public void check() throws ApiRuleException {
		if(null == adminUser){
			throw new ApiRuleException("-1","adminsuer实体不能为空");
		}
	}

	@Override
	public String getApiMethodName() {
		return "adminUserServ.fillAdminUser";
	}

}
