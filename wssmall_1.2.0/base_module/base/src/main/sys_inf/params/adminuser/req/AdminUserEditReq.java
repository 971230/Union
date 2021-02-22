package params.adminuser.req;

import params.ZteRequest;
import params.adminuser.resp.AdminEditUserResp;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.eop.resource.model.AdminUser;

public class AdminUserEditReq extends ZteRequest<AdminEditUserResp>{
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
			throw new ApiRuleException("-1","adminUser实体不能为空");
		}
		
	}

	@Override
	public String getApiMethodName() {
		return "adminUserServ.edit";
	}
}
