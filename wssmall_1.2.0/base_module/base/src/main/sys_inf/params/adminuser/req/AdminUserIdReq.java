package params.adminuser.req;

import com.ztesoft.api.ApiRuleException;

import params.ZteRequest;
import params.adminuser.resp.AdminUserIdResp;

public class AdminUserIdReq extends ZteRequest<AdminUserIdResp>{

	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getApiMethodName() {
		return "adminUserServ.getAdminUserSequences";
	}

}
