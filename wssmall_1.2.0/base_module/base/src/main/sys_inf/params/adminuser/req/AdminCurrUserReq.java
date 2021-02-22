package params.adminuser.req;

import com.ztesoft.api.ApiRuleException;

import params.ZteRequest;
import params.adminuser.resp.AdminUserResp;

public class AdminCurrUserReq extends ZteRequest<AdminUserResp>{

	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getApiMethodName() {
		return "adminUserServ.getCurrentUser";
	}

}
