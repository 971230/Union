package params.adminuser.resp;

import params.ZteResponse;

import com.ztesoft.net.eop.resource.model.AdminUser;

public class UserPasswordUpdateResp extends ZteResponse{
         private AdminUser adminUsers;

		public AdminUser getAdminUsers() {
			return adminUsers;
		}

		public void setAdminUsers(AdminUser adminUsers) {
			this.adminUsers = adminUsers;
		}

	
         
}
