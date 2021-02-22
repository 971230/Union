package params.adminuser.req;

import params.ZteRequest;
import params.adminuser.resp.AdminUserCountResp;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.form.util.StringUtil;

/**
 * 查找用户记录实体
 * @author hu.yi
 * @date 2013.12.23
 */
public class AdminUserCountByIdReq extends ZteRequest<AdminUserCountResp>{

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
			throw new ApiRuleException("-1","user_id不能为空");
		}
	}

	@Override
	public String getApiMethodName() {
		return "adminUserServ.getCountById";
	}
	
}
