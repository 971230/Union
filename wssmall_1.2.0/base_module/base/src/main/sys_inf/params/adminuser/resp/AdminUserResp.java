package params.adminuser.resp;

import com.ztesoft.net.eop.resource.model.AdminUser;

import params.ZteResponse;


/**
 * 查询用户返回实体
 * @author hu.yi
 * @date 2013.12.30
 */
public class AdminUserResp extends ZteResponse {

	private AdminUser adminUser;

	public AdminUser getAdminUser() {
		return adminUser;
	}

	public void setAdminUser(AdminUser adminUser) {
		this.adminUser = adminUser;
	}
}
