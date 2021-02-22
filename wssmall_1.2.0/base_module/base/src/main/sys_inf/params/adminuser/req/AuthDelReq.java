package params.adminuser.req;

import params.ZteRequest;
import params.adminuser.resp.AuthDelResp;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.form.util.StringUtil;

/**
 * 权限点获取参数实体
 * @author hu.yi
 * @date 2013.12.23
 */
public class AuthDelReq extends ZteRequest<AuthDelResp>{

	private String authid;
	
	
	public String getAuthid() {
		return authid;
	}
	public void setAuthid(String authid) {
		this.authid = authid;
	}
	@Override
	public void check() throws ApiRuleException {
		if(StringUtil.isEmpty(authid)){
			throw new ApiRuleException("-1","缺失参数");
		}
	}
	@Override
	public String getApiMethodName() {
		return "authServ.delete";
	}
}
