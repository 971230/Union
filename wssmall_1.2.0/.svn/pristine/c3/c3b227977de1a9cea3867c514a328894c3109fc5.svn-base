package params.adminuser.req;

import params.ZteRequest;
import params.adminuser.resp.AdminAddResp;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.eop.resource.model.AdminUser;



/**
 * 新增管理员实体
 * @author hu.yi
 * @date 2013.12.23
 */
public class AdminAddReq extends ZteRequest<AdminAddResp>{

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
			throw new ApiRuleException("-1","adminuser实体不能为空");
		}
	}
	@Override
	public String getApiMethodName() {
		return "adminUserServ.add";
	}
}
