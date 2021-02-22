package params.adminuser.req;

import params.ZteRequest;
import params.ZteResponse;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.form.util.StringUtil;

public class AdminUserDelReq extends ZteRequest<ZteResponse>{
	private String user_id;
	
	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	
	@Override
	public void check() throws ApiRuleException {
		if(StringUtil.isEmpty(user_id)){
			throw new ApiRuleException("-1","缺失参数");
		}
		
	}

	@Override
	public String getApiMethodName() {
		return "adminUserServ.deleteAdminUsesr";
	}
}
